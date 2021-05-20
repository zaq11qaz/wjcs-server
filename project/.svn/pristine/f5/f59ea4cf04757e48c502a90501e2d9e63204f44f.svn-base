package com.huihe.eg.user.service.web.webSocket;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/19 14:10
 * @ Description：群发消息功能
 * @ since: JDk1.8
 */
@Api(value = "群发消息功能", description = "群发消息功能", tags = {"群发消息功能"})
@Component
@ServerEndpoint("/websocket")
public class SpringWebSocketHandler extends TextWebSocketHandler  {

    private static final Logger log = LoggerFactory.getLogger(SpringWebSocketHandler.class);
    /** 记录当前在线连接数 */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /** 存放所有在线的客户端 */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        clients.put(session.getId(), session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
        clients.remove(session.getId());
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        sendMessage(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("断开连接");
//        error.printStackTrace();
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/25 15:47
     * @ Description：群发消息
     * @ since: JDk1.8
     */
    @ApiOperation(value = "群发消息")
    @ResponseBody
    public static void sendMessage(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session toSession = sessionEntry.getValue();
            // 排除掉自己
                log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
                toSession.getAsyncRemote().sendText(message);
            System.out.println(message);
        }
    }

    public static Integer getOnlineCount(){
        return onlineCount.intValue();
    }

}