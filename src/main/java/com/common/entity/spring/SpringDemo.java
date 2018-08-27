package com.common.entity.spring;

import com.common.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by madali on 2017/5/3.
 */
public class SpringDemo {

    public static void main(String[] args) {
        //启动spring容器
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Student student = applicationContext.getBean(Student.class);
        System.out.println(student);
    }
}
