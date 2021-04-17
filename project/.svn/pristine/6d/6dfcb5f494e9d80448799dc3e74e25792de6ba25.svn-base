package com.huihe.eg.user.model.pay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/9 13:50
 * @ Description：
 * @ since: JDk1.8
 */
@Component
@ConfigurationProperties(
        prefix = "service.pay.qrcode"
)
public class GenerateQRCodeParam {
    private String appid;

    private String secret;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
