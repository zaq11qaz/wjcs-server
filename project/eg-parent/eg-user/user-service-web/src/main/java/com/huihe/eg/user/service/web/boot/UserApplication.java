package com.huihe.eg.user.service.web.boot;

import com.cy.framework.service.web.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
@EnableFeignClients(basePackages = {"com.huihe.eg.cloud.feign"})
@ComponentScan(basePackages = {"com.huihe.eg.user", "com.cy.framework"})
@MapperScan(basePackages = {"com.huihe.eg.user.mybatis", "com.cy.framework.mybaties"})
public class UserApplication extends BaseApplication {
        @Bean
        public Queue membermonthly() {
            return new Queue("membermonthly");
        }
        @Bean
        public Queue updateUserOnlineState() {
            return new Queue("updateUserOnlineState");
        }
        @Bean
        public Queue insertUserOrder(){
            return new Queue("insertUserOrder");
        }
        @Bean
        public Queue insertEsUserOrder() {
            return new Queue("insertEsUserOrder");
        }
        @Bean
        public Queue queryUserinfoInsertRedis() {
            return new Queue("queryUserinfoInsertRedis");
        }
        @Bean
        public Queue queueUpdateUser() {
        return new Queue("queueUpdateUser");
    }
        @Bean
        public Queue queueUpdateUserInfo() {
            return new Queue("queueUpdateUserInfo");
        }
        @Bean
        public Queue membershipExpired() {
            return new Queue("membershipExpired");
        }
        @Bean
        public Queue timeTaskVideoChat() {
        return new Queue("timeTaskVideoChat");
    }
        @Bean
        public Queue insertEsUserInfo() {
            return new Queue("insertEsUserInfo");
        }
        @Bean
        public Queue insertEsMasterAppointment() {
            return new Queue("insertEsMasterAppointment");
        }
        @Bean
        public Queue insertEsMasterMechanism() {
            return new Queue("insertEsMasterMechanism");
        }
        @Bean
        public Queue insertEsMasterInfo() {
            return new Queue("insertEsMasterInfo");
        }
        @Bean
        public Queue insertEsMasterSetPrice() {
            return new Queue("insertEsMasterSetPrice");
        }
        @Bean
        public Queue updateSettlementCash() {
            return new Queue("updateSettlementCash");
        }
        @Bean
        public Queue updateOnPaymentSettlementCash() {
            return new Queue("updateOnPaymentSettlementCash");
        }
        @Bean
        public Queue updateInviteCouponCash() {
            return new Queue("updateInviteCouponCash");
        }
        @Bean
        public Queue updateInviteCode() {
            return new Queue("updateInviteCode");
        }

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        ConfigurableApplicationContext context = SpringApplication.run(UserApplication.class, args);
        context.getEnvironment();
    }
}
