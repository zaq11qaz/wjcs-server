package com.huihe.eg.push.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service.push")
@RefreshScope
public class PushConfigParam {
    private String AppID;
    private String TechpayAppID;
    private String AppSecret;
    private String TechpayAppSecret;
    private String AppKey;
    private String MasterSecret;
    private String url;
    private String PushSecret;
    private String PassWord;
    private String SandBoxApnsHost;
    private String Topic;

    public String getTechpayAppID() {
        return TechpayAppID;
    }

    public void setTechpayAppID(String techpayAppID) {
        TechpayAppID = techpayAppID;
    }

    public String getTechpayAppSecret() {
        return TechpayAppSecret;
    }

    public void setTechpayAppSecret(String techpayAppSecret) {
        TechpayAppSecret = techpayAppSecret;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getSandBoxApnsHost() {
        return SandBoxApnsHost;
    }

    public void setSandBoxApnsHost(String sandBoxApnsHost) {
        SandBoxApnsHost = sandBoxApnsHost;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        this.PassWord = passWord;
    }

    public String getPushSecret() {
        return PushSecret;
    }

    public void setPushSecret(String pushSecret) {
        this.PushSecret = pushSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getAppSecret() {
        return AppSecret;
    }

    public void setAppSecret(String appSecret) {
        AppSecret = appSecret;
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public String getMasterSecret() {
        return MasterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        MasterSecret = masterSecret;
    }
}
