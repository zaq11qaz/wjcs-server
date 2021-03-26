
package com.huihe.eg.mall.service.web.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * @author zhjh256@163.com
 * {@link} http://www.cnblogs.com/zhjh256
 */



    @Component
    public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {


        @Override
        public boolean beforeHandshake(ServerHttpRequest request,
                                       ServerHttpResponse response, WebSocketHandler wsHandler,
                                       Map<String, Object> attributes) throws Exception {
            //解决The extension [x-webkit-deflate-frame] is not supported问题
            if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
                request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
            }
            //检查session的值是否存在
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                HttpSession session = servletRequest.getServletRequest().getSession(false);
               // String accountId = (String) session.getAttribute("accountId");
                //把session和accountId存放起来
             //   attributes.put("sessionid", session.getId());
                //attributes.put(accountId, accountId);
                System.out.println("HandshakeInterceptor1");
            }
            return super.beforeHandshake(request, response, wsHandler, attributes);
        }


        @Override
        public void afterHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Exception ex) {
            super.afterHandshake(request, response, wsHandler, ex);
            System.out.println("HandshakeInterceptor2");
        }
    }
