package com.huihe.eg.cloud.zuul;

import com.alibaba.fastjson.JSONObject;
import com.huihe.eg.cloud.zuul.authorization.AuthorizationService;
import com.huihe.eg.cloud.zuul.redis.RedisService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yangchengfu
 * @Description: zuul 的过滤器
 * @Date : 2017/7/27 14:35
 */
public class MyFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(MyFilter.class);
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private RedisService redisService;

    /**
     * @Description: 过滤的类型
     * @author yangchengfu
     * @Date : 2017/7/27 14:37
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * @Description: 是否过滤
     * @author yangchengfu
     * @Date : 2017/7/27 14:39
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * @Description: 过滤的逻辑
     * @author yangchengfu
     * @Date : 2017/7/27 14:39
     */
    @Override
    @CrossOrigin
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        /*String body = null;
        try {
            InputStream in = ctx.getRequest().getInputStream();
            body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("body:" + body);*/
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        String access_token = request.getParameter("token");
        if (access_token == null) {
            access_token = request.getHeader("token");
        }
        //此处判断是否要拦截**************
        //过滤登录方法
        if(request.getRequestURI().contains("user/user/login")){
            return null;
        }
        if(request.getRequestURI().contains("authorization/managerUser/login")){
            return null;
        }
        if(request.getRequestURI().contains("user/user/openIdLoginOrRegist")){
            return null;
        }
        if(request.getRequestURI().contains("user/user/openIdAppVerification")){
            return null;
        }
        if(request.getRequestURI().contains("user/user/openIdVerification")){
            return null;
        }
        if(request.getRequestURI().contains("user/user/openIdLoginVerification")){
            return null;
        }
        //回调路径
        if(request.getRequestURI().contains("message/messageCallback/TIMCallbackCommand")){
            return null;
        }
        if(request.getRequestURI().contains("user/pay/aliPayNotify")){
            return null;
        }
        if(request.getRequestURI().contains("/user/pay/wxPayNotify")){
            return null;
        }
        //二维码登录接口
        if(request.getRequestURI().contains("/eg-api/user/qrCodeLogin/")){
            return null;
        }
//        文档路径不开放
//        if(request.getRequestURI().contains("/api-docs")){
//            return null;
//        }

        /*
        Object token=null;
        if(access_token!=null&&!access_token.equals("")){//token非空判断
            //获取redis存储的token
            if (redisService.exists(access_token.toString())){
                token=redisService.get(access_token.toString());
                if(token!=null){
                    redisService.set(access_token.toString(),token,86400);
                }
            }else{
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody("{\"code\":401,\"message\":\"没有访问权限！\",\"data\":null}");
                ctx.getResponse().setContentType("text/html;charset=UTF-8");
                return ctx;
            }
        }else{
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"code\":401,\"message\":\"没有访问权限！\",\"data\":null}");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            return ctx;
        }

         */

        /*
        String ip = getIpAddr2(request);
        String key = "rate.limit:" + ip + request.getRequestURI() + request.getParameterNames().toString();
        if (redisService.exists(key)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            ctx.setResponseBody("{\"code\":403,\"message\":\"系统繁忙,请稍后重试！\",\"data\":null}");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            return ctx;
        }else {
            redisService.set(key, "key", 1L);
        }

         */

        Map<String, Object> object = authorizationService.authorization(request.getRequestURI(), request.getMethod(), access_token);
//        System.out.println(JSONObject.toJSONString(object));
        if (object == null || !"0".equals(object.get("code").toString())) {
            log.warn("author result is not success:" + JSONObject.toJSONString(object));
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;UTF-8");
                object.put("url", request.getRequestURI());
                response.getWriter().write(JSONObject.toJSONString(object));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static String getIpAddr2(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
//        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
//            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
//            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
//            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
//            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
//            System.out.println("getRemoteAddr ip: " + ip);
        }
//        System.out.println("获取客户端ip: " + ip);
        return ip;
    }
}
