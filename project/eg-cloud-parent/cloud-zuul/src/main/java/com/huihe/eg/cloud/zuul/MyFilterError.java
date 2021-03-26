package com.huihe.eg.cloud.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangchengfu
 * @Description: zuul 的过滤器
 * @Date : 2017/7/27 14:35
 */
public class MyFilterError extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(MyFilterError.class);

    /**
     * @Description: 过滤的类型
     * @author yangchengfu
     * @Date : 2017/7/27 14:37
     */
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 1;
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
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }

}
