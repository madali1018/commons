package com.mada.springdemo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by madali on 2017/4/28.
 */
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@ComponentScan(basePackages = "com.mada.springdemo.*") //@ComponentScan可以配置扫描多个包
@Configuration
public class ProjectConfig {

}
