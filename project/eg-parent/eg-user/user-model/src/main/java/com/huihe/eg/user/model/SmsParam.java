package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "service.sms")
@RefreshScope
@ApiModel(value="短信配置",description="短信配置")
public class SmsParam implements Serializable {
    private static final long serialVersionUID = 5462006702503027003L;
    @ApiModelProperty(value = "名称")
    private String sname;
    @ApiModelProperty(value = "密码")
    private String spwd;
    @ApiModelProperty(value = "api地址")
    private String url;
    @ApiModelProperty(value = "sprdid")
    private String sprdid;
    @ApiModelProperty(value = "psd")
    private String psd;
    @ApiModelProperty(value = "smsg")
    private String smsg;
    @ApiModelProperty(value = "smsi")
    private String smsi;
    @ApiModelProperty(value = "psd")
    private String smsv;
    @ApiModelProperty(value = "psd",example = "1")
    private Integer seconds;
    @ApiModelProperty(value = "类型")
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSmsv() {
        return smsv;
    }

    public void setSmsv(String smsv) {
        this.smsv = smsv;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpwd() {
        return spwd;
    }

    public void setSpwd(String spwd) {
        this.spwd = spwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSprdid() {
        return sprdid;
    }

    public void setSprdid(String sprdid) {
        this.sprdid = sprdid;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getSmsg() {
        return smsg;
    }

    public String getSmsi() {
        return smsi;
    }

    public void setSmsi(String smsi) {
        this.smsi = smsi;
    }

    public void setSmsg(String smsg) {
        this.smsg = smsg;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
}
