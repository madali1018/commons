package com.mada.elasticsearch.impl;

import com.mada.elasticsearch.client.EsClient;
import com.mada.elasticsearch.contarct.index.IEsIndex;
import com.mada.elasticsearch.utils.CollectionUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by madali on 2019/1/16 16:36
 */
@Log4j2
public class EsIndexImpl implements IEsIndex {

    @Override
    public void createIndex(String indexName) {
        EsClient.getInstance().getEsClient().admin().indices()
                .prepareCreate(indexName)
                .execute().actionGet();
    }

    @Override
    public boolean createIndex(String indexName, String jsonData) {
        IndexResponse indexResponse = EsClient.getInstance().getEsClient()
                // type的名称和index一样，也可以单独指定type名称。
                // _id 是 Elasticsearch 自动生成的。自动生成的ID是 URL-safe、 基于 Base64 编码且长度为20个字符的 GUID 字符串。 这些 GUID 字符串由可修改的 FlakeID 模式生成，这种模式允许多个节点并行生成唯一 ID ，且互相之间的冲突概率几乎为零。
                .prepareIndex(indexName, indexName)
                .setSource(jsonData)
                .execute().actionGet();

        return indexResponse.isCreated();
    }

    @Override
    public boolean createIndex(String indexName, String id, String jsonData) {
        UpdateRequestBuilder updateRequestBuilder = EsClient.getInstance().getEsClient()
                // type的名称和index一样，也可以单独指定type名称。
                .prepareUpdate(indexName, indexName, id)
                .setDoc(jsonData)
                .setUpsert(jsonData);

        try {
            updateRequestBuilder.execute().actionGet();
        } catch (Exception e) {
            return false;
        }

        log.info("创建索引成功.索引名称:{},记录id:{}", indexName, id);
        return true;
    }

    @Override
    public Map<String, Boolean> createMultiplyDocument(String indexName, Map<String, String> map) {
        Map<String, Boolean> resultMap = new HashMap<>();

        if (CollectionUtil.isEmpty(map)) {
            throw new RuntimeException("map为空,无法批量添加索引.");
        }
        if (map.size() > 500) {
            throw new RuntimeException("批量添加索引接口暂只支持一次添加少于500条数据的请求,请重新请求该接口.");
        }

        TransportClient transportClient = EsClient.getInstance().getEsClient();
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String id = entry.getKey();
            String jsonData = entry.getValue();
            if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(jsonData)) {
                bulkRequest.add(transportClient.prepareUpdate(indexName, indexName, id).setDoc(jsonData).setUpsert(jsonData));
            }
        }
        if (bulkRequest.numberOfActions() == 0) {
            throw new RuntimeException("批量添加索引的map参数格式非法,请重新请求该接口.");
        }

        BulkItemResponse[] responses = bulkRequest.get().getItems();
        for (BulkItemResponse bulkItemResponse : responses) {
            if (bulkItemResponse.isFailed()) {
                resultMap.put(bulkItemResponse.getId(), false);
            } else {
                resultMap.put(bulkItemResponse.getId(), true);
            }
        }

        return resultMap;
    }

    @Override
    public boolean deleteIndex(String indexName) {
        EsClient.getInstance().getEsClient().admin().indices().prepareDelete(indexName).execute();
        log.info("删除索引成功.索引名称:{}", indexName);
        return true;
    }

    @Override
    public boolean deleteDocument(String indexName, String id) {
        EsClient.getInstance().getEsClient().prepareDelete(indexName, indexName, id).execute();
        log.info("删除索引中的文档记录成功.索引名称:{},文档记录id:{}", indexName, id);
        return true;
    }

    @Override
    public Map<String, Boolean> deleteDocumentList(String indexName, List<String> idList) {
        Map<String, Boolean> resultMap = new HashMap<>();

        if (CollectionUtil.isEmpty(idList)) {
            throw new RuntimeException("idList为空,无需删除.");
        }
        // list中id过多时，该接口请求量若过大，可能会导致es批量删除请求阻塞
        if (idList.size() > 500) {
            throw new RuntimeException("批量删除索引中文档接口暂只支持一次删除少于500条数据的请求,请重新请求该接口.");
        }

        TransportClient transportClient = EsClient.getInstance().getEsClient();
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        for (String id : idList) {
            bulkRequest.add(transportClient.prepareDelete(indexName, indexName, id));
        }

        BulkItemResponse[] responses = bulkRequest.get().getItems();
        for (BulkItemResponse bulkItemResponse : responses) {
            DeleteResponse deleteResponse = bulkItemResponse.getResponse();
            boolean found = deleteResponse.isFound();
            if (found) {
                if (bulkItemResponse.isFailed()) {
                    resultMap.put(bulkItemResponse.getId(), false);
                } else {
                    resultMap.put(bulkItemResponse.getId(), true);
                }
            } else {
                // 对应接口定义中的  2.以下两种情况下的key仍为id，value为true。删除一个不存在的index(es内部状态码50000)，重复删除已经删除的数据。
                resultMap.put(bulkItemResponse.getId(), true);
            }
        }

        return resultMap;
    }

    public boolean updateIndex(String indexName, String id, String json) {
        log.info("更新索引:{},文档id:{}", indexName, id);

        EsClient.getInstance().getEsClient()
                .prepareUpdate(indexName, indexName, id)
                .setDoc(json)
                .execute();

        return true;
    }

    @Override
    public boolean updateIndex(String indexName, String id, Map<String, Object> fieldMap) {
        log.info("更新索引:{},文档id:{}", indexName, id);

        if (CollectionUtil.isNotEmpty(fieldMap)) {
            XContentBuilder source;
            try {
                source = XContentFactory.jsonBuilder().startObject();
                for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                    source.field(entry.getKey(), entry.getValue());
                }
                source.endObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            UpdateRequest updateRequest = new UpdateRequest(indexName, indexName, id).doc(source);
            try {
                EsClient.getInstance().getEsClient().update(updateRequest).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

}
