package com.mada.elasticsearch.contarct.search;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by madali on 2019/1/16 18:18
 */
public interface IEsSearch {

    /**
     * 根据id获取文档内容
     * <p>
     * http请求方式：curl -XGET "http://127.0.0.1:9200/hirdata/hirdata/35302499264591"
     *
     * @param indexName 索引名称
     * @param id        索引id
     * @return 索引中文档的原始数据（String类型的json串）
     */
    String getIndexData(String indexName, String id);

    /**
     * in 某个字段查询
     *
     * @param indexName 索引名称
     * @param fieldName 字段名称
     * @param set       属性值set
     * @return
     */
    List<String> getInOneFieldData(String indexName, String fieldName, Set<Object> set);

    /**
     * in 多个字段查询
     *
     * @param indexName 索引名称
     * @param map       map的key为字段名称，value为字段名称in的集合
     * @return
     */
    List<String> getInMultiFieldData(String indexName, Map<String, Set<Object>> map);

    /**
     * not in 某个字段查询
     *
     * @param indexName 索引名称
     * @param fieldName 字段名称
     * @param set       属性值set
     * @return
     */
    List<String> getNotInOneFieldData(String indexName, String fieldName, Set<Object> set);

    /**
     * not in 多个字段查询
     *
     * @param indexName 索引名称
     * @param map       map的key为字段名称，value为字段名称not in的集合
     * @return
     */
    List<String> getNotInMultiFieldData(String indexName, Map<String, Set<Object>> map);

    /**
     * 多字段排序
     *
     * @param indexName 索引名称
     * @param fieldMap  字段fieldMap：key为字段名称，value为true时表示升序，false时表示降序，未传值则默认按照该字段降序进行查询
     * @return
     */
    List<String> getOrderData(String indexName, Map<String, Boolean> fieldMap);

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
