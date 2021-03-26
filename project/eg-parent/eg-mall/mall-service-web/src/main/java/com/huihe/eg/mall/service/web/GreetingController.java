package com.huihe.eg.mall.service.web;

import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.mall.service.web.boot.MallApplication;

import com.huihe.eg.mall.service.web.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by haoyuyang on 2016/11/25.
 */
@RestController
@EnableScheduling
public class GreetingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    @Autowired
    private SimpUserRegistry userRegistry;

    /**
     * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息
     * @param topic
     * @param headers
     */
    @MessageMapping("/hello") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的
    @SendTo("/topic/greetings")
    public void greeting(@Header("atytopic") String topic, @Headers Map<String, Object> headers ) {

        System.out.println("connected successfully....");
        System.out.println(topic);
        System.out.println(JSONUtils.obj2Json(headers));
        Greeting greeting = new Greeting("wwwwwwwwwwwww");
        simpMessageSendingOperations.convertAndSend("/topic/hello", greeting);

    }
    /**
     * 这里用的是@SendToUser，这就是发送给单一客户端的标志。本例中，
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
     * @return
     */
    @MessageMapping("/message")
    public void handleSubscribe(String name) {
       // System.out.println(principal.getName());
        System.out.println("this is the @SubscribeMapping('/marco')");
        System.out.println("123456");
     this.simpMessageSendingOperations.convertAndSendToUser("1", "/message", new Greeting(name));


    }

    /**
     * 测试对指定用户发送消息方法
     * @return
     */
    @RequestMapping(path = "/send", method = RequestMethod.GET)
    public Greeting send(String name) {
        System.out.println(">.send.<");
        simpMessageSendingOperations.convertAndSendToUser("1", "/message", new Greeting(name));
        logger.info("当前在线人数:" + userRegistry.getUserCount());
        System.out.println("当前在线人数:" + userRegistry.getUserCount());
        Set<SimpUser> simpUsers=userRegistry.getUsers();
        System.out.println(JSONUtils.obj2Json(simpUsers));
        return new Greeting(name+"greee");
    }
    @Scheduled(cron="4 * * * * ?")
    public void send1() {
        Greeting greeting = new Greeting("com.huhe.qmall");
        simpMessageSendingOperations.convertAndSendToUser("1", "/message", new Greeting("sdsfsd"));
        simpMessageSendingOperations.convertAndSend("/topic/1", greeting);
    }

}
