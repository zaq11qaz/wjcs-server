package com.huihe.eg.news.service.web.boot;

import com.cy.framework.service.web.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
@EnableFeignClients(basePackages = {"com.huihe.eg.cloud.feign"})
@ComponentScan(basePackages = {"com.huihe.eg.news", "com.cy.framework"})
@MapperScan(basePackages = {"com.huihe.eg.news.mybatis", "com.cy.framework.mybaties"})
public class NewsApplication extends BaseApplication {

    @Bean
    public Queue insertESCuriosity() {
        return new Queue("insertESCuriosity");
    }
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(NewsApplication.class, args);
    }
}
