package com.mada.es.impl;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.mada.es.client.EsClient;
import com.mada.es.search.IEsSearchService;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.Map;

/**
 * Created by madali on 2019/1/16 19:11
 */
public class EsSearchServiceImpl implements IEsSearchService {

    @Override
    public String getIndexData(String indexName, String id) {
        GetResponse response = EsClient.getInstance().getEsClient()
                .prepareGet(indexName, indexName, id)
                .execute()
                .actionGet();

        return response.getSourceAsString();
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
