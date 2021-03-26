package com.huihe.eg.grab.service.web.boot;

import com.cy.framework.service.web.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.huihe.eg.grab", "com.cy.framework"})
@MapperScan(basePackages = {"com.huihe.eg.grab.mybatis", "com.cy.framework.mybaties"})
public class GrabApplication extends BaseApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GrabApplication.class, args);
        context.getEnvironment();
    }
}
