package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/18 14:48
 */
@Component
@ConfigurationProperties(prefix = "service.mail")
@RefreshScope
@ApiModel(value="邮箱配置",description="邮箱配置")
public class MailParam {

    /**
     * 标题
     */

    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * ；邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String host;
    /**
     * 邮箱账号
     */
    @ApiModelProperty(value = "邮箱账号")
    private String account;
    /**
     * 邮箱密码
     */
    @ApiModelProperty(value = "邮箱密码")
    private String password;
    /**
     * 发送类型
     */
    @ApiModelProperty(value = "发送类型")
    private String personal;
    /**
     * 验证码有效期
     */
    @ApiModelProperty(value = "验证码有效期",example = "1")
    private Integer seconds;

    /**
     * 申请推送
     */
    @ApiModelProperty(value = "申请推送",example = "1")
    private String passContent;

    /**
     * 推荐官邀请申请推送
     */
    @ApiModelProperty(value = "推荐官邀请申请推送",example = "1")
    private String passContentI;

    public String getPassContent() {
        return passContent;
    }

    public void setPassContent(String passContent) {
        this.passContent = passContent;
    }

    public String getPassContentI() {
        return passContentI;
    }

    public void setPassContentI(String passContentI) {
        this.passContentI = passContentI;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
}
