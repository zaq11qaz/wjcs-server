package com.huihe.eg.push.upload;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 在spring boot中加入相应配置的扫描
 */
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/tripPictstorage/**").addResourceLocations("/home/img/tripPictstorage/");



        /*
         * 说明：增加虚拟路径(经过本人测试：在此处配置的虚拟路径，用springboot内置的tomcat时有效，
         * 用外部的tomcat也有效;所以用到外部的tomcat时不需在tomcat/config下的相应文件配置虚拟路径了,阿里云linux也没问题)
         */
        /*
        registry.addResourceHandler("/tripPictstorage/**").addResourceLocations("/home/img/tripPictstorage/");


        //阿里云(映射路径去除盘符)
        //registry.addResourceHandler("/ueditor/image/**").addResourceLocations("/upload/image/");
        //registry.addResourceHandler("/ueditor/video/**").addResourceLocations("/upload/video/");
        super.addResourceHandlers(registry);

         */


    }
}