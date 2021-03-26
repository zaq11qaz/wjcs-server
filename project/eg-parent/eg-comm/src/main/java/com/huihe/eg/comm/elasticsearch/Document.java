package com.huihe.eg.comm.elasticsearch;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

@Persistent//将函数申明的局部变量保存到硬盘中，只能被定义的函数使用 防止被其他函数改变
@Inherited//自动查询到超类
@Retention(RetentionPolicy.RUNTIME)//注解在jvm加载.class文件后依然存在
@Target({ElementType.TYPE})//用于描述类
public @interface Document {

    String indexName();//索引库的名称，个人建议以项目的名称命名

    String type() default "";//类型，个人建议以实体的名称命名

    short shards() default 5;//默认分区数

    short replicas() default 1;//每个分区默认的备份数

    String refreshInterval() default "1s";//刷新间隔

    String indexStoreType() default "fs";//索引文件存储类型
}
