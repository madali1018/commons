package com.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by madali on 2017/4/28.
 */
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
//@ComponentScan配置扫描多个包
@ComponentScan(basePackages = "com.common.*,test.*")
@Configuration
public class AppConfig {

}
