package com.common.model;

import com.common.config.AppConfig;
import com.common.util.log.jdk.JdkLogUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by madl on 2017/5/3.
 */
public class SpringDemo {

    private static final Logger LOGGER = JdkLogUtil.getLogger(SpringDemo.class, true);

    public static void main(String[] args) {

        //启动spring容器
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student = applicationContext.getBean(Student.class);

        LOGGER.info("student: {}", student);
    }
}
