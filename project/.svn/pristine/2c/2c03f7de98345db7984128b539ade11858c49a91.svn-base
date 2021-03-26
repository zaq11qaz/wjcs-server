package com.huihe.eg.boot;

import com.cy.framework.service.web.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"com.huihe.eg.push", "com.cy.framework"})
@MapperScan(basePackages = {"com.huihe.eg.push", "com.cy.framework.mybaties"})
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@EnableScheduling
@Import(value = MultipartAutoConfiguration.class)
public class PushApplication extends BaseApplication {

    @Bean
    public Queue classNewsMessage() {
        return new Queue("classNewsMessage");
    }
    @Bean
    public Queue videoPush() {
        return new Queue("videoPush");
    }
    @Bean
    public Queue payPushMessage() {
        return new Queue("payPushMessage");
    }
    @Bean
    public Queue classStartMessage() {
        return new Queue("classStartMessage");
    }
    @Bean
    public Queue addNewsMessage() {
        return new Queue("addNewsMessage");
    }
    @Bean
    public Queue userAuthMessage() {
        return new Queue("userAuthMessage");
    }
    @Bean
    public Queue assetsChangeMessage() {
        return new Queue("assetsChangeMessage");
    }
    @Bean
    public Queue updateAuthMessage() {
        return new Queue("updateAuthMessage");
    }
    @Bean
    public Queue frozenStatusMessage() {
        return new Queue("frozenStatusMessage");
    }
    @Bean
    public Queue insertEsMessageGroup() {
        return new Queue("insertEsMessageGroup");
    }
    @Bean
    public Queue systemUpdate() {
        return new Queue("systemUpdate");
    }
    @Bean
    public Queue pushVideo() {
        return new Queue("pushVideo");
    }
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(PushApplication.class, args);
    }
}
