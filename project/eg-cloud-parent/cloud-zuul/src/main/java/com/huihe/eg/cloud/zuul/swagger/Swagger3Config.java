package com.huihe.eg.cloud.zuul.swagger;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * zuul集成swagger，实现微服务接口管理
 */
@Component
@Primary
public class Swagger3Config implements SwaggerResourcesProvider {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    public Docket createRestApi() {

        logger.info("加载Swagger3");

        return new Docket(DocumentationType.OAS_30) // 注意此处改动，需要将SWAGGER_2改成OAS_30
                .apiInfo(apiInfo()).select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger3接口文档")
                .description("接口文档")
                .contact(new Contact("zwy", "#", "1191769395@qq.com"))
                .version("0.3.0")
                .build();
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
//        resources.add(swaggerResource("订单相关接口", "/eg-api/order/v2/api-docs"));
//        resources.add(swaggerResource("查询统计相关接口", "/eg-api/sum/v2/api-docs"));
        resources.add(swaggerResource("即时通讯相关接口", "/eg-api/message/v2/api-docs"));
        resources.add(swaggerResource("权限管理以及菜单相关接口", "/eg-api/authorization/v2/api-docs"));
        resources.add(swaggerResource("用户相关接口", "/eg-api/user/v2/api-docs"));
        resources.add(swaggerResource("内容境界相关接口", "/eg-api/news/v2/api-docs"));
        resources.add(swaggerResource("推送相关接口", "/eg-api/push/v2/api-docs"));
//        resources.add(swaggerResource("抓取相关接口", "/eg-api/grab/v2/api-docs"));
//        resources.add(swaggerResource("商城相关接口", "/eg-api/mall/v2/api-docs"));
        resources.add(swaggerResource("定时推送接口文档", "/eg-api/schedule/v2/api-docs"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}
