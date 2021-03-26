package com.huihe.eg.mall.service.web.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;

/**
 * zwx
 * 2018年9月11日14:34:28
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport implements WebSocketMessageBrokerConfigurer {

    /**
     * 将"/hello"路径注册为STOMP端点，这个路径与发送和接收消息的目的路径有所不同，这是一个端点，客户端在订阅或发布消息到目的地址前，要连接该端点，
     * 即用户发送请求url="/applicationName/hello"与STOMP server进行连接。之后再转发到订阅url；
     * PS：端点的作用——客户端在订阅或发布消息到目的地址前，要连接该端点。
     * @param stompEndpointRegistry
     */
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //在网页上可以通过"/applicationName/hello"来和服务器的WebSocket连接
        //stompEndpointRegistry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS();
        stompEndpointRegistry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS().setInterceptors(httpSessionIdHandshakeInterceptor()); ;


    }

    /**
     * 配置了一个简单的消息代理，如果不重载，默认情况下回自动配置一个简单的内存消息代理，用来处理以"/topic"为前缀的消息。这里重载configureMessageBroker()方法，
     * 消息代理将会处理前缀为"/topic"和"/queue"的消息。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
         //应用程序以/app为前缀，代理目的地以/topic、/queue为前缀
            registry.enableSimpleBroker("/topic", "/user");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
    /**
     * 消息传输参数配置
     */

    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(8192) //设置消息字节数大小
                .setSendBufferSizeLimit(8192)//设置消息缓存大小
                .setSendTimeLimit(10000); //设置消息发送时间限制毫秒
    }


    /**
     * 输入通道参数设置
     */

    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4) //设置消息输入通道的线程池线程数
                .maxPoolSize(8)//最大线程数
                .keepAliveSeconds(60);//线程活动时间
        registration.setInterceptors(presenceChannelInterceptor());
    }

    /**
     * 输出通道参数设置
     */

    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
        registration.setInterceptors(presenceChannelInterceptor());
    }

    @Bean
    public HandshakeInterceptor httpSessionIdHandshakeInterceptor() {
        return new HandshakeInterceptor();
    }

    @Bean
    public PresenceChannelInterceptor presenceChannelInterceptor() {
        return new PresenceChannelInterceptor();
    }

   /* @Bean
    public WebSocketHandler subProtocolWebSocketHandler() {
        return new CustomSubProtocolWebSocketHandler(clientInboundChannel(), clientOutboundChannel());
    }*/


    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return super.configureMessageConverters(messageConverters);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

}
