package com.huihe.eg.push.model;

import mob.push.api.config.MobPushConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author : zwy
 * @Description:
 * @Date: Create in  2020/10/24
 */

@Component
@ConfigurationProperties(prefix = "service.mob")
@RefreshScope
public class MyMobPushConfig extends MobPushConfig {

    /**
     * appkey: 需要先设置此数据，怎样获取appkey请至http://www.mob.com官网
     */
    private String appKey;

    /**
     * appSecret: appkey对应密钥,需要先设置此数据
     */
    private String appSecret;

    /**
     * baseUrl: webapi http 接口基础url
     */
    private String baseUrl;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void init(){
        MobPushConfig.appkey = appKey; //给父类中i赋值
        MobPushConfig.appSecret = appSecret; //给父类中i赋值
        MobPushConfig.baseUrl = baseUrl; //给父类中i赋值
    }
}
