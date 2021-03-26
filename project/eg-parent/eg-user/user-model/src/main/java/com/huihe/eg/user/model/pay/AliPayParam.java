package com.huihe.eg.user.model.pay;

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
    private String api_name;
    private String method;
    private String scope;
    private String pid;
    private String app_name;
    private String biz_type;
    private String product_id;
    private String auth_type;
    private String auth_url;
    private String redirect_url;
    private String notify_url;
    private String return_url;
    private String app_id;
    private String app_private_key;
    private String app_public_key;
    private String alipay_public_key;
    private String app_cert_path;
    private String alipay_cert_path;
    private String alipay_root_cert_path;
    private String sign_type;
    private String format = "JSON";
    private String charset = "UTF-8";
    private String payeeLogonId;
    private String prepayment_url;

    public String getPrepayment_url() {
        return prepayment_url;
    }

    public void setPrepayment_url(String prepayment_url) {
        this.prepayment_url = prepayment_url;
    }

    public String getPayeeLogonId() {
        return payeeLogonId;
    }

    public void setPayeeLogonId(String payeeLogonId) {
        this.payeeLogonId = payeeLogonId;
    }

    public AliPayParam() {
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(String biz_type) {
        this.biz_type = biz_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getApp_cert_path() {
        return app_cert_path;
    }

    public void setApp_cert_path(String app_cert_path) {
        this.app_cert_path = app_cert_path;
    }

    public String getAlipay_cert_path() {
        return alipay_cert_path;
    }

    public void setAlipay_cert_path(String alipay_cert_path) {
        this.alipay_cert_path = alipay_cert_path;
    }

    public String getAlipay_root_cert_path() {
        return alipay_root_cert_path;
    }

    public void setAlipay_root_cert_path(String alipay_root_cert_path) {
        this.alipay_root_cert_path = alipay_root_cert_path;
    }
}
