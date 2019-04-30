package com.mada.elasticsearch.impl;

import com.mada.elasticsearch.client.EsClient;
import com.mada.elasticsearch.contarct.search.IEsGroupBySearch;
import com.mada.elasticsearch.entity.EsGroupByRequestEntity;
import com.mada.elasticsearch.entity.EsGroupByResponseEntity;
import com.mada.elasticsearch.utils.CollectionUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by madali on 2019/3/12 14:33
 */
@Log4j2
public class EsGroupBySearchImpl implements IEsGroupBySearch {

    @Override
    public EsGroupByResponseEntity groupByFiledValue(EsGroupByRequestEntity request) {
        EsGroupByResponseEntity result = new EsGroupByResponseEntity();

        String indexName = request.getIndexName();
        String fieldName = request.getFieldName();
        /*
         * 以下情况不查询es，直接返回一个空的EsGroupByResponseEntity
         *  1.未传索引名
         *  2.未传fieldName
         */
        if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(fieldName)) {
            return result;
        }

        int requiredSize = request.getRequiredSize() == 0 ? 10 : request.getRequiredSize();
        requiredSize = Math.min(requiredSize, 3500);

        SearchRequestBuilder searchRequestBuilder = EsClient.getInstance().getEsClient()
                .prepareSearch(indexName)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .addFields(fieldName);

        BoolQueryBuilder queryBuilder = buildQuery(request.getQuery());
        queryBuilder = buildFilter(request.getFilter(), queryBuilder);
        searchRequestBuilder.setQuery(queryBuilder);
        searchRequestBuilder.addAggregation(buildGroupBy(fieldName, requiredSize));

        SearchResponse searchResponse = searchRequestBuilder.setExplain(false).execute().actionGet();
        result.setMap(buildResponse(fieldName, searchResponse));
        if (request.isNeedRealEsJson()) {
            result.setRealQueryJson(searchRequestBuilder.toString());
        }

        return result;
    }

    private BoolQueryBuilder buildQuery(String query) {
        if (StringUtils.isEmpty(query)) {
            return QueryBuilders.boolQuery();
        }

        BoolQueryBuilder result = QueryBuilders.boolQuery();
        String[] strArr = query.split(",");
        for (int i = 0; i < strArr.length; i++) {
            //数组长度为 2  如：a:123
            String[] fieldKeyArr = strArr[i].split(":");
            if (fieldKeyArr.length != 2) {
                continue;
            }

            String fieldName = fieldKeyArr[0];
            String fieldValue = fieldKeyArr[1];
            // a:*123
            if (fieldValue.startsWith("*")) {
                String[] fieldValueArr = fieldValue.split("\\*");
                if (fieldValueArr.length == 2) {
                    result.must(QueryBuilders.matchQuery(fieldName, fieldValueArr[1]));
                }
            }
        }
        return result;
    }

    private BoolQueryBuilder buildFilter(String filter, BoolQueryBuilder queryBuilder) {
        if (StringUtils.isEmpty(filter)) {
            return queryBuilder;
        }

        // a:123,-b:1
        String[] strArr = filter.split(",");
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
        for (int i = 0; i < strArr.length; i++) {
            //数组长度为 2  如：a:123
            String[] fieldKeyArr = strArr[i].split(":");
            if (fieldKeyArr.length != 2) {
                continue;
            }

            if (fieldKeyArr[0].startsWith("-")) {
                String fieldName = fieldKeyArr[0].substring(1);
                String fieldValue = fieldKeyArr[1];

                // -a:1|2|3
                if (fieldValue.contains("|")) {
                    String[] s = fieldValue.split("\\|");
                    BoolQueryBuilder query1 = QueryBuilders.boolQuery();
                    for (int j = 0; j < s.length; j++) {
                        // must相当于and，mustNot相当于Not，should相当于or
                        query1.mustNot(QueryBuilders.termQuery(fieldName, s[j]));
                    }
                    filterBuilder.must(query1);
                } else if (!fieldValue.contains("_")) {
                    // -a:1
                    filterBuilder.mustNot(QueryBuilders.termQuery(fieldName, fieldValue));
                } else {
                    // 暂不支持其他格式，如 -a not in 10_100
                    log.warn("查询filter暂不支持字段:{},格式:{}.", fieldName, fieldValue);
                }
            } else {
                String fieldName = fieldKeyArr[0];
                String fieldValue = fieldKeyArr[1];
                if (fieldValue.contains("|") && fieldValue.contains("_")) {
                    // a:10_100|200_300
                    String[] fieldValueArr1 = fieldValue.split("\\|");

                    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                    // 10_100 200_300 400_500
                    for (int j = 0; j < fieldValueArr1.length; j++) {
                        String fieldValue2 = fieldValueArr1[j];
                        // 10_100
                        String[] rangeArr = fieldValue2.split("_");
                        boolQueryBuilder.should(QueryBuilders.rangeQuery(fieldName).from(rangeArr[0]).to(rangeArr[1]));
                    }

                    filterBuilder.must(boolQueryBuilder);
                } else if (fieldValue.contains("|")) {
                    // a:1|2|3
                    String[] s = fieldValue.split("\\|");
                    BoolQueryBuilder query1 = QueryBuilders.boolQuery();
                    query1.should(QueryBuilders.termsQuery(fieldName, s));
                    filterBuilder.must(query1);
                } else if (fieldValue.contains("_")) {
                    // a:10_100
                    String[] rangeArr = fieldValue.split("_");
                    filterBuilder.must(QueryBuilders.rangeQuery(fieldName).from(rangeArr[0]).to(rangeArr[1]));
                } else {
                    // a:1
                    filterBuilder.must(QueryBuilders.termQuery(fieldName, fieldValue));
                }
            }
        }

        queryBuilder.filter(filterBuilder);
        return queryBuilder;
    }

    private TermsBuilder buildGroupBy(String fieldName, int requiredSize) {
        /**
         * 1.fieldAgg是自定义的聚合名称，可以用其他任意字符串代替
         * 2.size指的是返回字段group by的size，如age字段有1000个值，size设为100，则只返回100个age值
         */
        TermsBuilder termsBuilder = AggregationBuilders.terms("fieldAgg").field(fieldName).size(requiredSize)
                .order(Terms.Order.aggregation("sum_" + fieldName, false));
        SumBuilder fieldSum = AggregationBuilders.sum("sum_" + fieldName).field(fieldName);
        termsBuilder.subAggregation(fieldSum);//把sum聚合器放入到Term聚合器中，相当于先group by再sum

        return termsBuilder;
    }

    private Map<String, Long> buildResponse(String fieldName, SearchResponse searchResponse) {
        Map<String, Long> map = new TreeMap<>();

        Terms terms = searchResponse.getAggregations().get("fieldAgg");
        List<Terms.Bucket> bucketList = terms.getBuckets();
        if (CollectionUtil.isEmpty(bucketList)) {
            return map;
        }

        for (Terms.Bucket bucket : terms.getBuckets()) {
//            InternalSum internalSum = bucket.getAggregations().get("sum_" + fieldName);//注意从bucket而不是searchResponse
//            System.out.println("group by字段:age 值:" + bucket.getKeyAsString() + " 个数:" + bucket.getDocCount() + " sum:" + internalSum.getValue());
            map.put(bucket.getKeyAsString(), bucket.getDocCount());
        }

        return map;
    }

}
