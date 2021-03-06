package com.huihe.eg.user.model.pay;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
public class WxPayParam implements WXPayConfig {
    private byte[] certData;

    private String gAppId;
    private String xAppid;
    private String gAppSecret;
    private String xAppSecret;
    private String aappId;
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
    private String apiclient_cert_p12;

    public byte[] getCertData() {
        return certData;
    }

    public void setCertData(byte[] certData) {
        this.certData = certData;
    }

    public String getApiclient_cert_p12() {
        return apiclient_cert_p12;
    }

    public void setApiclient_cert_p12(String apiclient_cert_p12) {
        this.apiclient_cert_p12 = apiclient_cert_p12;
    }

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
        return gAppId;
    }

    public void setgAppId(String gAppId) {
        this.gAppId = gAppId;
    }

    public String getxAppid() {
        return xAppid;
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

    public String getAappId() {
        return aappId;
    }

    public void setAappId(String aappId) {
        this.aappId = aappId;
    }

    public String getMachId() {
        return this.machId;
    }

    public void setMachId(String machId) {
        this.machId = machId;
    }

    public void init(){
        try {
        String certPath = getApiclient_cert_p12();
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public String getAppID() {
        return this.aappId;
    }

    @Override
    public String getMchID() {
        return this.machId;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
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
