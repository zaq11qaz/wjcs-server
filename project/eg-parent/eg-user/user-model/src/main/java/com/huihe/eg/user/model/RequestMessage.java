package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/19 9:58
 * @ Description：
 * @ since: JDk1.8
 */
@ApiModel(value="监听消息对象",description="监听消息对象")
public class RequestMessage implements Serializable {
    private static final long serialVersionUID = -6055674056447284339L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
