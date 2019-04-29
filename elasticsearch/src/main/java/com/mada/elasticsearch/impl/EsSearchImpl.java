package com.mada.elasticsearch.impl;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.mada.elasticsearch.client.EsClient;
import com.mada.elasticsearch.contarct.search.IEsSearch;
import com.mada.elasticsearch.util.CollectionUtil;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by madali on 2019/1/16 19:11
 */
public class EsSearchImpl implements IEsSearch {

    @Override
    public String getIndexData(String indexName, String id) {
        GetResponse response = EsClient.getInstance().getEsClient()
                .prepareGet(indexName, indexName, id)
                .execute()
                .actionGet();

        return response.getSourceAsString();
    }

    @Override
    public List<String> getInOneFieldData(String indexName, String fieldName, Set<Object> set) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // must相当于and，mustNot相当于Not，should相当于or
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (Object obj : set) {
            boolQuery.should(QueryBuilders.termQuery(fieldName, obj));
        }

        SearchResponse response = EsClient.getInstance().getEsClient()
                .prepareSearch(indexName, indexName)
                // 初始搜索请求在查询中指定scroll参数，告诉es需要保持搜索的上下文环境多长时间（滚动时间）
                .setScroll(new TimeValue(60000))
                .setQuery(queryBuilder.must(boolQuery))
                // 最多查询到的记录数
                .setSize(10000)
                .execute().actionGet();

        SearchHit[] hits = response.getHits().getHits();
        List<String> result = new ArrayList<>();
        if (hits.length > 0) {
            for (SearchHit hit : hits) {
                result.add(hit.getSourceAsString());
            }
        }

        return result;
    }

    @Override
    public List<String> getInMultiFieldData(String indexName, Map<String, Set<Object>> map) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        SearchRequestBuilder searchRequestBuilder = EsClient.getInstance().getEsClient()
                .prepareSearch(indexName, indexName)
                .setScroll(new TimeValue(60000));

        if (CollectionUtil.isNotEmpty(map)) {
            for (Map.Entry<String, Set<Object>> entry : map.entrySet()) {
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                String fieldName = entry.getKey();
                Set<Object> set = entry.getValue();
                for (Object obj : set) {
                    boolQuery.should(QueryBuilders.termQuery(fieldName, obj));
                    searchRequestBuilder.setQuery(queryBuilder.must(boolQuery));
                }
            }
        }

        SearchResponse response = searchRequestBuilder.setSize(10000).execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        List<String> result = new ArrayList<>();
        if (hits.length > 0) {
            for (SearchHit hit : hits) {
                result.add(hit.getSourceAsString());
            }
        }

        return result;
    }

    @Override
    public List<String> getNotInOneFieldData(String indexName, String fieldName, Set<Object> set) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (Object obj : set) {
            boolQuery.mustNot(QueryBuilders.termQuery(fieldName, obj));
        }

        SearchResponse response = EsClient.getInstance().getEsClient()
                .prepareSearch(indexName, indexName)
                .setScroll(new TimeValue(60000))
                .setQuery(queryBuilder.must(boolQuery))
                .setSize(10000)
                .execute().actionGet();

        SearchHit[] hits = response.getHits().getHits();
        List<String> result = new ArrayList<>();
        if (hits.length > 0) {
            for (SearchHit hit : hits) {
                result.add(hit.getSourceAsString());
            }
        }

        return result;
    }

    @Override
    public List<String> getNotInMultiFieldData(String indexName, Map<String, Set<Object>> map) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        SearchRequestBuilder searchRequestBuilder = EsClient.getInstance().getEsClient()
                .prepareSearch(indexName, indexName)
                .setScroll(new TimeValue(60000));

        if (CollectionUtil.isNotEmpty(map)) {
            for (Map.Entry<String, Set<Object>> entry : map.entrySet()) {
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                String fieldName = entry.getKey();
                Set<Object> set = entry.getValue();
                for (Object obj : set) {
                    boolQuery.mustNot(QueryBuilders.termQuery(fieldName, obj));
                    searchRequestBuilder.setQuery(queryBuilder.must(boolQuery));
                }
            }
        }

        SearchResponse response = searchRequestBuilder.setSize(10000).execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        List<String> result = new ArrayList<>();
        if (hits.length > 0) {
            for (SearchHit hit : hits) {
                result.add(hit.getSourceAsString());
            }
        }

        return result;
    }

    @Override
    public List<String> getOrderData(String indexName, Map<String, Boolean> fieldMap) {
        List<String> result = new ArrayList<>();

        SearchRequestBuilder searchRequestBuilder = EsClient.getInstance().getEsClient()
                .prepareSearch(indexName)
                .setSize(10000); // 最多查询到的记录数默认为10，不指定的话则最多只查询出10条记录。

        if (CollectionUtil.isNotEmpty(fieldMap)) {
            for (Map.Entry<String, Boolean> entry : fieldMap.entrySet()) {
                FieldSortBuilder sortBuilder = SortBuilders.fieldSort(entry.getKey());
                if (entry.getValue()) {
                    sortBuilder.order(SortOrder.ASC);
                } else {
                    sortBuilder.order(SortOrder.DESC);
                }

                searchRequestBuilder.addSort(sortBuilder);
            }
        }

        SearchResponse response = searchRequestBuilder.execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        if (hits.length > 0) {
            for (SearchHit hit : hits) {
                result.add(hit.getSourceAsString());
            }
        }

        return result;
    }

    @Override
    public Map<String, String> getIndexSettings(String indexName) {
        Settings stat = EsClient.getInstance().getEsClient()
                .admin()
                .cluster()
                .prepareState()
                .execute()
                .actionGet()
                .getState()
                .getMetaData()
                .getIndices()
                .get(indexName)
                .getSettings();

        return stat.getAsMap();
    }

    /**
     * Mapping,就是对索引库中索引的字段名称及其数据类型进行定义，类似于mysql中的表结构信息。
     * 不过es的mapping比数据库灵活很多，它可以动态识别字段。
     * 一般不需要指定mapping都可以，因为es会自动根据数据格式识别它的类型，如果你需要对某些字段添加特殊属性
     * （如：定义使用其它分词器、是否分词、是否存储等），就必须手动添加mapping。
     * <p>
     * 我们在es中添加索引数据时不需要指定数据类型，es中有自动影射机制，字符串映射为string，数字映射为long。
     * 通过mappings可以指定数据类型是否存储等属性。
     */
    @Override
    public Map<String, Object> getIndexMappings(String indexName) {
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.indices(indexName).types(new String[0]);

        GetMappingsResponse response = EsClient.getInstance().getEsClient()
                .admin()
                .indices()
                .getMappings(getMappingsRequest)
                .actionGet();

        ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = response.getMappings();
        if (mappings.size() == 1) {
            for (ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>> indexEntry : mappings) {
                if (indexEntry.value.isEmpty()) {
                    continue;
                }

                for (ObjectObjectCursor<String, MappingMetaData> typeEntry : indexEntry.value) {
//                System.out.println(typeEntry.value.sourceAsMap());
                    Map<String, Object> filedNameAndTypeMap;
                    try {
                        filedNameAndTypeMap = typeEntry.value.sourceAsMap();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return filedNameAndTypeMap;
                }
            }
        }

        return null;
    }

}
