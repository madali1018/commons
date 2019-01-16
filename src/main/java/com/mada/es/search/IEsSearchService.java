package com.mada.es.search;

import java.util.Map;

/**
 * Created by madali on 2019/1/16 18:18
 */
public interface IEsSearchService {

    /**
     * 根据id获取文档内容
     *
     * @param indexName 索引名称
     * @param id        索引id
     * @return 索引中文档的原始数据（String类型的json串）
     */
    String getIndexData(String indexName, String id);

    /**
     * 获取索引分片，副本数，创建时间等信息
     * <p>
     * http请求方式：curl -XGET "http://127.0.0.1:9200/index1-mdl/_settings?pretty"
     *
     * @param indexName 索引名称
     * @return
     */
    Map<String, String> getIndexSettings(String indexName);

    /**
     * 获取索引中所有的字段及类型
     * <p>
     * http请求方式：curl -XGET "http://127.0.0.1:9200/index1-mdl/_mapping?pretty"
     *
     * @param indexName 索引名称
     * @return
     */
    Map<String, Object> getIndexMappings(String indexName);

}
