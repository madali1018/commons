package com.mada.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mada.zookeeper.enumeration.ServerStateEnum;
import com.mada.zookeeper.enumeration.ServiceEnum;
import com.mada.zookeeper.exception.EnvironmentVariableNotExistException;
import com.mada.zookeeper.utils.zk.ZkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by madali on 2019/4/28 14:48
 */
@Slf4j
public abstract class Application {

    private final ServiceEnum currentService;
    private final ServiceEnum[] dependentServices;

    protected Application(ServiceEnum currentService, ServiceEnum... dependentServices) {
        this.currentService = currentService;
        this.dependentServices = dependentServices;
    }

    /**
     * 初始化应用
     *
     * @param args
     */
    protected abstract void init(String[] args);

    /**
     * 部署应用
     *
     * @param args
     */
    protected abstract void deploy(String[] args);

    /**
     * 关闭应用
     */
    protected abstract void close(String[] args);

    /**
     * 运行应用
     *
     * @param args
     */
    public final void run(final String[] args) throws Exception {
        this.beforeRun(args);

        //部署应用
        this.deploy(args);

        this.afterRun();

        log.info("Succeeded in deploying " + this.currentService.description() + ".");
    }

    private void checkEnv() throws Exception {
        String zookeeperHost = System.getenv("ZOOKEEPER_HOST");
        if (StringUtils.isEmpty(zookeeperHost)) {
            throw new EnvironmentVariableNotExistException("Environment variable named \"ZOOKEEPER_HOST\" does not exist.");
        }
    }

    private void beforeRun(String[] args) throws Exception {
        //全局关闭fastjson循环引用检测的功能
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();

        //检查环境变量
        checkEnv();

        //初始化应用
        this.init(args);

        //在this.init(args)方法之后执行，因为初始化应用时，可能修改Zookeeper配置
        //连接Zookeeper
        ZkUtil.connect(this.currentService, this.dependentServices);

        //注册到Zookeeper
        ZkUtil.addConnection(ServerStateEnum.Loading);

        //应用关闭时，执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.beforeClose();

            //关闭应用
            this.close(args);

            this.afterClose();

            log.info("Succeeded in closing " + this.currentService.description() + ".");
        }));

        //服务依赖
        ZkUtil.dependConnection();
    }

    private void afterRun() {
        //注册到Zookeeper
        ZkUtil.updateConnection(ServerStateEnum.Running);
    }

    private void beforeClose() {
        //注册到Zookeeper
        ZkUtil.updateConnection(ServerStateEnum.ClearPreExit);
    }

    private void afterClose() {
        try {
            //从Zookeeper删除
            ZkUtil.deleteConnection();
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }

        try {
            //断开Zookeeper
            ZkUtil.disconnect();
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

}
