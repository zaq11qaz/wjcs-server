package com.huihe.eg.cloud.zuul.swagger;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * zuul集成swagger，实现微服务接口管理
 */
@Component
@Primary
public class Swagger2Config implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
//        resources.add(swaggerResource("订单相关接口", "/eg-api/order/v2/api-docs"));
//        resources.add(swaggerResource("查询统计相关接口", "/eg-api/sum/v2/api-docs");
        resources.add(swaggerResource("即时通讯相关接口", "/eg-api/message/v2/api-docs"));
        resources.add(swaggerResource("权限管理以及菜单相关接口", "/eg-api/authorization/v2/api-docs"));
        resources.add(swaggerResource("用户相关接口", "/eg-api/user/v2/api-docs"));
        resources.add(swaggerResource("内容境界相关接口", "/eg-api/news/v2/api-docs"));
        resources.add(swaggerResource("推送相关接口", "/eg-api/push/v2/api-docs"));
        resources.add(swaggerResource("抓取相关接口", "/eg-api/grab/v2/api-docs"));
        resources.add(swaggerResource("商城相关接口", "/eg-api/mall/v2/api-docs"));
        resources.add(swaggerResource("定时推送接口文档", "/eg-api/schedule/v2/api-docs"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("1.0");
        return swaggerResource;
    }
}
