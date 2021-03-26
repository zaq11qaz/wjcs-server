package com.huihe.eg.cloud.zuul.authorization;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "eg-authorization")
public interface AuthorizationService {
    /**
     * 检测授权认证
     *
     * @param url 路径
     * @param method 方法
     * @param token 令牌
     * @return
     */
    @RequestMapping(value = "/apiUrl/authorization", method = RequestMethod.GET)
    Map<String, Object> authorization(@RequestParam(value = "url") String url, @RequestParam("method") String method, @RequestParam("token") String token);


    /**
     * 插入日志
     * @param requestMethod 请求类型
     * @param requestURI 请求路径
     * @param body 原参数
     * @param userID 修改用户id
     * @return null
     */
    @RequestMapping(value = "/log/insertLog", method = RequestMethod.POST)
    Object insertLog(@RequestParam(value = "requestMethod")String requestMethod ,@RequestParam("requestURI") String requestURI ,
                     @RequestParam("body") String body , @RequestParam("userID") String userID
    );
}

