package com.huihe.eg.comm.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//什么时候使用该注解，我们定义为运行时
@Target({ElementType.METHOD})//注解用于什么地方，我们定义为作用于方法上
@Documented//注解是否将包含在 JavaDoc 中
public @interface WebLog {
    /**
     * 日志描述信息
     */
    String description() default "";//定义一个属性，默认为空字符串
}
