package com.mada.es.client;

import com.mada.es.Constants;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Created by madali on 2019/1/16 16:28
 */
@Slf4j
public class EsClient {

    private TransportClient transportClient;

    private EsClient() {
    }

    public static EsClient getInstance() {
        return SingletonFactory.singletonInstance;
    }

    /**
     * 利用私有的内部工厂类
     */
    private static class SingletonFactory {
        private static EsClient singletonInstance = new EsClient();
    }

    public TransportClient getEsClient() {
        return transportClient;
    }

    /**
     * 1.TransportClient内置了连接池，无需手动关闭连接。本地资源消耗少，性能比NodeClient方式更高。
     * 2.TransportClient初始化连接时，ElasticSearch必须已启动，且必须知道集群中一个节点的IP地址和端口；并且可能存在"连跳"，浪费一定的网络资源。
     *
     * @param hosts
     */
    public synchronized void initEs(String hosts) {
        if (Objects.nonNull(transportClient)) {
            return;
        }

        Settings settings = Settings.builder()
                .put("cluster.name", Constants.ES_CLUSTER_NAME)//设置集群名称
                .put("client.transport.sniff", true)//自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
                .build();

        transportClient = TransportClient.builder()
                .settings(settings)
                .build();

        log.info("读取到的es集群hosts配置:{}", hosts);
        String[] hostArr = hosts.split(",");
        if (hostArr.length > 0) {
            addTransportAddress(hostArr);
        }
    }

    private void addTransportAddress(String[] hostArr) {
        for (int i = 0; i < hostArr.length; i++) {
            String[] nodes = hostArr[i].split(":");
            boolean initFlag = false;
            int retryTime = 0;

            while (!initFlag) {
                try {
                    transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodes[0]), Integer.parseInt(nodes[1])));
                    initFlag = true;
                    log.info("es集群{}的transportClient与{}:{}的es连接成功.", Constants.ES_CLUSTER_NAME, nodes[0], nodes[1]);
                } catch (UnknownHostException e) {
                    log.warn("es集群{}的transportClient与{}:{}的es连接失败.重试次数:{}", Constants.ES_CLUSTER_NAME, nodes[0], nodes[1], retryTime);
                    retryTime++;
                }

                if (retryTime > 3) {
                    // 此处不向外抛出异常，抛出异常后其他机器的配置不会读取，es集群的连接会因部分机器的连接失败而获取不到。
                    log.error("es集群{}的transportClient与{}:{}的es连接经过三次重试后依然失败,不再重试,继续连接集群中其他机器.", Constants.ES_CLUSTER_NAME, nodes[0], nodes[1]);
                    // 获取集群中其他机器的连接
                    if (i <= nodes.length - 2) {
                        nodes = hostArr[(i + 1)].split(":");
                        retryTime = 0;
                    }
                    if (i == nodes.length - 1) {
                        initFlag = true;
                    }
                    i++;
                }
            }
        }
    }

}
