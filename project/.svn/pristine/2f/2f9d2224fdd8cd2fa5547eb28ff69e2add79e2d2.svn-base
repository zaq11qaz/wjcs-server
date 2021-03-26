package com.huihe.eg.authorization.service.web.boot;

import com.cy.framework.service.web.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.huihe.eg.authorization", "com.cy.framework"})
@MapperScan(basePackages = {"com.huihe.eg.authorization.mybatis", "com.cy.framework.mybaties"})
public class AuthorizationApplication extends BaseApplication {
    @Bean
    public Queue queryManagerInfoInsertRedis() {
        return new Queue("queryManagerInfoInsertRedis");
    }
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
