package com.mada.es.index;

import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2019/1/16 16:34
 */
public interface IEsIndex {

    /**
     * 创建一个空的索引库
     *
     * @param indexName 索引名称
     */
    void createIndex(String indexName);

    /**
     * 不指定id
     * <p>
     * 创建索引并添加一条记录，或 向已有的索引中添加文档记录
     *
     * @param indexName 索引名称
     * @param jsonData  本条记录json串
     */
    boolean createIndex(String indexName, String jsonData);

    /**
     * 指定id
     * <p>
     * 创建索引并添加一条记录，或 向已有的索引中添加文档记录，或 更新已有索引的文档记录
     *
     * @param indexName 索引名称
     * @param id        本条记录id：唯一
     * @param jsonData  本条记录json串
     */
    boolean createIndex(String indexName, String id, String jsonData);

    /**
     * 批量添加索引记录，bulkRequest里面和createIndex(String indexName, String id, String jsonData) 一样，使用的是prepareUpdate，即不区分是添加还是修改，均可使用此接口。
     * 1.map为空，map大小超过500，接口均抛异常信息提示。
     *
     * @param indexName 索引名称
     * @param map       key为索引id，value为记录json，map大小不能超过500
     * @return key为索引id, value为true/false，即代表接口执行完后es索引中是否已经有了这条数据
     */
    Map<String, Boolean> createMultiplyDocument(String indexName, Map<String, String> map);

    /**
     * 删除整个索引（慎用）
     *
     * @param indexName 索引名称
     * @return
     */
    boolean deleteIndex(String indexName);

    /**
     * 删除索引中某一条记录
     *
     * @param indexName 索引名称
     * @param id        记录id
     */
    boolean deleteDocument(String indexName, String id);

    /**
     * 删除索引中多条记录
     * <p>
     * 1.token为空，idList为空，idList大小超过500，接口均抛异常信息提示。
     * 2.以下两种情况下的key仍为id，value为true。删除一个不存在的index(es内部状态码50000)，重复删除已经删除的数据。
     *
     * @param indexName 索引名称
     * @param idList    记录idList
     * @return key为idList中的id, value为true/false，即代表接口执行完后es索引中是否已经没有了这条数据
     */
    Map<String, Boolean> deleteDocumentList(String indexName, List<String> idList);

    /**
     * 更新索引，支持只更新部分字段的值
     *
     * @param indexName 索引名称
     * @param id        索引记录id
     * @param json      要更新的json串
     * @return
     */
    boolean updateIndex(String indexName, String id, String json);

    /**
     * 更新索引中的字段的值
     *
     * @param indexName 索引名称
     * @param id        索引记录id
     * @param fieldMap  map的key为要更新的字段的name，value为要更新的字段的值。
     *                  1.只修改fieldMap中指定的字段，其他字段不变。
     *                  2.字段在索引中不存在时，会新增字段。
     * @return
     */
    boolean updateIndex(String indexName, String id, Map<String, Object> fieldMap);

    /**
     * 修改字段名，删除字段。es2以后已不支持。
     * Elasticsearch中的mapping一旦创建，就不能再修改，但是添加字段是可以的。
     * 其实很简单，只需在原来的mapping上面直接新增加一个field，然后重新创建一下mapping就可以了
     */

}
