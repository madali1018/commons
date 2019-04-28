package com.mada.es.impl;

import com.mada.es.client.EsClient;
import com.mada.es.entity.EsGroupByRangeRequestEntity;
import com.mada.es.entity.EsGroupByRangeResponseEntity;
import com.mada.es.search.IEsGroupByRangeSearch;
import com.mada.es.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2019/3/12 14:42
 */
@Slf4j
public class EsGroupByRangeSearchImpl implements IEsGroupByRangeSearch {

    @Override
    public EsGroupByRangeResponseEntity groupByRangeFiledValue(EsGroupByRangeRequestEntity request) {
        EsGroupByRangeResponseEntity result = new EsGroupByRangeResponseEntity();

        String indexName = request.getIndexName();
        String fieldName = request.getFieldName();
        List<String> groupList = request.getGroupList();
        /*
         * 以下情况不查询es，直接返回一个空的EsGroupByRangeResponseEntity
         *  1.未传索引名
         *  2.未传fieldName
         *  3.未传groupList
         */
        if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(fieldName) || CollectionUtil.isEmpty(groupList)) {
            return result;
        }

        int groupByNum = request.getGroupByNum() == 0 ? 100 : request.getGroupByNum();
        groupByNum = Math.min(groupByNum, 3500);

        SearchRequestBuilder searchRequestBuilder = EsClient.getInstance().getEsClient()
                .prepareSearch(indexName)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFrom(0)
                .setSize(groupByNum)
                .addFields(fieldName);

        BoolQueryBuilder queryBuilder = buildQuery(request.getQuery());
        queryBuilder = buildFilter(request.getFilter(), queryBuilder);
        searchRequestBuilder.setQuery(queryBuilder);
        searchRequestBuilder.addAggregation(buildGroupBy(fieldName, request.getGroupList()));

        SearchResponse searchResponse = searchRequestBuilder.setExplain(false).execute().actionGet();
        result.setMap(buildResponse(searchResponse));
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

    private AggregationBuilder buildGroupBy(String fieldName, List<String> groupList) {
        // fieldAgg是自定义的聚合名称，可以用其他任意字符串代替
        RangeBuilder rangeBuilder = AggregationBuilders.range("fieldAgg").field(fieldName);

        for (String rangeStr : groupList) {
            String[] rangeArr = rangeStr.split("-");
            if (rangeArr.length == 2) {
                rangeBuilder.addRange(Double.valueOf(rangeArr[0]), Double.valueOf(rangeArr[1]));
            }
        }

        return rangeBuilder;
    }

    private Map<String, Long> buildResponse(SearchResponse searchResponse) {
        Map<String, Long> map = new HashMap<>();

        InternalRange internalRange = searchResponse.getAggregations().get("fieldAgg");
        List<InternalRange.Bucket> list = internalRange.getBuckets();
        if (CollectionUtil.isEmpty(list)) {
            return map;
        }

        for (InternalRange.Bucket bucket : list) {
//            System.out.println("group by分组条件:" + bucket.getKey() + ",该分组下文档个数:" + bucket.getDocCount());
            map.put(bucket.getKey(), bucket.getDocCount());
        }

        return map;
    }

}
