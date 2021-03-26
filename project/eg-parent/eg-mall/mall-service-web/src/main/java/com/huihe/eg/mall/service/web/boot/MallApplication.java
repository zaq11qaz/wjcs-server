package com.huihe.eg.mall.service.web.boot;

import com.cy.framework.service.web.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackages = {"com.huihe.eg.cloud.feign"})
@ComponentScan(basePackages = {"com.huihe.eg.mall", "com.cy.framework"})
@MapperScan(basePackages = {"com.huihe.eg.mall.mybatis", "com.cy.framework.mybaties"})
@EnableScheduling
public class MallApplication extends BaseApplication {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        ConfigurableApplicationContext context = SpringApplication.run(MallApplication.class, args);
        context.getEnvironment();
    }
}
