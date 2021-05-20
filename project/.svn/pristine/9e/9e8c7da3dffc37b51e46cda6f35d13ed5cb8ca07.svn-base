package com.huihe.eg.user.service.web.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/19 9:44
 * @ Description：WebSocketConfig 群发消息配置
 * @ since: JDk1.8
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig {

    /**
     * 注入一个ServerEndpointExporter,该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
