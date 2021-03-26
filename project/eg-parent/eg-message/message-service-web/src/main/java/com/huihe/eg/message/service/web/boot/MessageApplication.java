package com.huihe.eg.message.service.web.boot;

import com.cy.framework.service.web.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients(basePackages = {"com.huihe.eg.cloud.feign"})
@ComponentScan(basePackages = {"com.huihe.eg.message", "com.cy.framework"})
@MapperScan(basePackages = {"com.huihe.eg.message.mybatis", "com.cy.framework.mybaties"})
public class MessageApplication extends BaseApplication {
    @Bean
    public Queue insertNoteToEs() {
        return new Queue("insertNoteToEs");
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Queue updateGroupDuration() {
        return new Queue("updateGroupDuration");
    }
    @Bean
    public Queue insertEsMessageGroup() {
        return new Queue("insertEsMessageGroup");
    }
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        ConfigurableApplicationContext context = SpringApplication.run(MessageApplication.class, args);;
//        context.getEnvironment();
        //todo
    }
}
