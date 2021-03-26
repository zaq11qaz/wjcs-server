package com.huihe.eg.user.model.pay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 14:35
 */
@Component
@ConfigurationProperties(
        prefix = "service.pay.wx"
)
public class WxPayParam {
    private String gAppId;
    private String xAppid;
    private String gAppSecret;
    private String xAppSecret;
    private String appId;
    private String appSecret;
    private String machId;
    private String key;
    private String certPath;
    private String notify_url;
    private String wxip;
    private String tAppidH;
    private String tAppSecretH;
    private String tappId;
    private String tappSecret;

    public String getTappId() {
        return tappId;
    }

    public void setTappId(String tappId) {
        this.tappId = tappId;
    }

    public String getTappSecret() {
        return tappSecret;
    }

    public void setTappSecret(String tappSecret) {
        this.tappSecret = tappSecret;
    }

    public String gettAppidH() {
        return tAppidH;
    }

    public void settAppidH(String tAppidH) {
        this.tAppidH = tAppidH;
    }

    public String gettAppSecretH() {
        return tAppSecretH;
    }

    public void settAppSecretH(String tAppSecretH) {
        this.tAppSecretH = tAppSecretH;
    }

    public WxPayParam() {
    }

    public String getWxip() {
        return wxip;
    }

    public void setWxip(String wxip) {
        this.wxip = wxip;
    }

    public String getNotify_url() {
        return this.notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getgAppId() {
        return this.gAppId;
    }

    public void setgAppId(String gAppId) {
        this.gAppId = gAppId;
    }

    public String getxAppid() {
        return this.xAppid;
    }

    public void setxAppid(String xAppid) {
        this.xAppid = xAppid;
    }

    public String getgAppSecret() {
        return this.gAppSecret;
    }

    public void setgAppSecret(String gAppSecret) {
        this.gAppSecret = gAppSecret;
    }

    public String getxAppSecret() {
        return this.xAppSecret;
    }

    public void setxAppSecret(String xAppSecret) {
        this.xAppSecret = xAppSecret;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMachId() {
        return this.machId;
    }

    public void setMachId(String machId) {
        this.machId = machId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCertPath() {
        return this.certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }




}
