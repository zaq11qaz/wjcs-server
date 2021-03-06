package com.huihe.eg.cloud.zuul;

import com.huihe.eg.cloud.zuul.authorization.AuthorizationService;
import com.huihe.eg.cloud.zuul.redis.RedisService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.huihe.eg.cloud.zuul.MyFilter.getIpAddr2;

/**
 * @author yangchengfu
 * @Description: zuul 的过滤器
 * @Date : 2017/7/27 14:35
 */
public class MyFilterPost extends ZuulFilter {

    private final Logger log = LoggerFactory.getLogger(MyFilterPost.class);

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
        return "post";
    }

    @Override
    public int filterOrder() {
        return 2;
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
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        try {
            /*
            String access_token = request.getParameter("token");
            if (access_token == null) {
                access_token = request.getHeader("token");
                if (access_token==null){
                    return null;
                }
            }
            String userID = redisService.get(access_token).toString();
            if (userID==null||"".equals(userID)||!access_token.contains("managerToken")) {
                return null;
            }else{
            access_token ="admin"+access_token;
            }

             */

            String body = "";
            String requestMethod = request.getMethod();
            if ("POST".equals(requestMethod.toUpperCase())) {

                String requestURI = request.getRequestURI();
                try (InputStream inputStream = ctx.getRequest().getInputStream()) {
                    body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String requestURL = request.getRequestURL().toString();
                if (!"".equals(requestURL) && !requestURL.contains("insert") && !requestURL.contains("versionIteration")
                        && !requestURL.contains("TIMCallbackCommand") && !requestURL.contains("getToken")
                        && !requestURL.contains("cutOff") && !requestURL.contains("plugFlow")) {
//                    authorizationService.insertLog(requestMethod, requestURI, body, userID);
                    authorizationService.insertLog(requestMethod, requestURI, body, "0");
                }
                                /*
                Map<String, Object> object = null;
                //大屏登录
                if (request.getRequestURI().contains("eg-api/authorization/authorizationScreenWhite/loginScreen")) {
                    object = authorizationService.loginScreen(getIpAddr2(request), body);
                    log.warn("author result is success:" + JSONObject.toJSONString(object));
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(200);
                    try {
                        request.setCharacterEncoding("UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json;UTF-8");
                        object.put("url", request.getRequestURI());
                        response.getWriter().print(JSONObject.toJSONString(object));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }

                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
