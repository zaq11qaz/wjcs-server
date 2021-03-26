package com.huihe.eg.mall.model.pay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 14:34
 */
@Component
@ConfigurationProperties(
        prefix = "service.pay.ali"
)
public class AliPayParam {
    private String url;
    private String scope;
    private String auth_url;
    private String redirect_url;
    private String notify_url;
    private String return_url;
    private String app_id;
    private String app_private_key;
    private String app_public_key;
    private String alipay_public_key;
    private String sign_type;
    private String format = "JSON";
    private String charset = "UTF-8";

    public AliPayParam() {
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuth_url() {
        return this.auth_url;
    }

    public void setAuth_url(String auth_url) {
        this.auth_url = auth_url;
    }

    public String getRedirect_url() {
        return this.redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotify_url() {
        return this.notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return this.return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_private_key() {
        return this.app_private_key;
    }

    public void setApp_private_key(String app_private_key) {
        this.app_private_key = app_private_key;
    }

    public String getApp_public_key() {
        return this.app_public_key;
    }

    public void setApp_public_key(String app_public_key) {
        this.app_public_key = app_public_key;
    }

    public String getAlipay_public_key() {
        return this.alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getSign_type() {
        return this.sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}
