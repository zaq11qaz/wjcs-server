package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/19 10:01
 * @ Description：
 * @ since: JDk1.8
 */
@ApiModel(value="监听返回消息对象",description="监听返回消息对象")
public class ResponseMessage implements Serializable {
    private static final long serialVersionUID = 1114946181320870822L;
    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
