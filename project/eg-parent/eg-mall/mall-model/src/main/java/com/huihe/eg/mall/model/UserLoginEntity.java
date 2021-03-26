package com.huihe.eg.mall.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户登录注册信息",description="用户登录注册信息属性说明")
public class UserLoginEntity extends PageInfo {
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 最近一次登录时间
     */
    @ApiModelProperty(value="最近一次登录时间")
    private Date login_time;

    /**
     * 微信openid
     */
    @ApiModelProperty(value="微信openid")
    private String wx_openid;

    /**
     * QQopenid
     */
    @ApiModelProperty(value="QQopenid")
    private String qq_openid;

    /**
     * 登录密码
     */
    @ApiModelProperty(value="登录密码")
    private String login_pass;

    /**
     * 登录ip
     */
    @ApiModelProperty(value="登录ip")
    private String login_ip;

    /**
     * 状态  1正常  2 限制登录 3账号冻结
     */
    @ApiModelProperty(value="状态  1正常  2 限制登录 3账号冻结",example="1")
    private Integer status;

    /**
     * 登录名
     */
    @ApiModelProperty(value="登录名")
    private String login_name;

    /**
     * 在线状态
     */
    @ApiModelProperty(value="在线状态",example="1")
    private Integer online_state;

    /**
     * 1密码登录 2 验证码登录 3第三方登录
     */
    @ApiModelProperty(value="1密码登录 2 验证码登录 3第三方登录")
    private String login_type;
    @ApiModelProperty(value="验证码")
    private String verification_code;

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
    /**
     * 用户id
     * @return user_id 用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户id
     * @param user_id 用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间
     * @param create_time 创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 最近一次登录时间
     * @return login_time 最近一次登录时间
     */
    public Date getLogin_time() {
        return login_time;
    }

    /**
     * 最近一次登录时间
     * @param login_time 最近一次登录时间
     */
    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    /**
     * 微信openid
     * @return wx_openid 微信openid
     */
    public String getWx_openid() {
        return wx_openid;
    }

    /**
     * 微信openid
     * @param wx_openid 微信openid
     */
    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid == null ? null : wx_openid.trim();
    }

    /**
     * QQopenid
     * @return qq_openid QQopenid
     */
    public String getQq_openid() {
        return qq_openid;
    }

    /**
     * QQopenid
     * @param qq_openid QQopenid
     */
    public void setQq_openid(String qq_openid) {
        this.qq_openid = qq_openid == null ? null : qq_openid.trim();
    }

    /**
     * 登录密码
     * @return login_pass 登录密码
     */
    public String getLogin_pass() {
        return login_pass;
    }

    /**
     * 登录密码
     * @param login_pass 登录密码
     */
    public void setLogin_pass(String login_pass) {
        this.login_pass = login_pass == null ? null : login_pass.trim();
    }

    /**
     * 登录ip
     * @return login_ip 登录ip
     */
    public String getLogin_ip() {
        return login_ip;
    }

    /**
     * 登录ip
     * @param login_ip 登录ip
     */
    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip == null ? null : login_ip.trim();
    }

    /**
     * 状态  1正常  2 限制登录 3账号冻结
     * @return status 状态  1正常  2 限制登录 3账号冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态  1正常  2 限制登录 3账号冻结
     * @param status 状态  1正常  2 限制登录 3账号冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 登录名
     * @return login_name 登录名
     */
    public String getLogin_name() {
        return login_name;
    }

    /**
     * 登录名
     * @param login_name 登录名
     */
    public void setLogin_name(String login_name) {
        this.login_name = login_name == null ? null : login_name.trim();
    }

    /**
     * 在线状态
     * @return online_state 在线状态
     */
    public Integer getOnline_state() {
        return online_state;
    }

    /**
     * 在线状态
     * @param online_state 在线状态
     */
    public void setOnline_state(Integer online_state) {
        this.online_state = online_state;
    }

    /**
     * 1密码登录 2 验证码登录 3第三方登录
     * @return login_type 1密码登录 2 验证码登录 3第三方登录
     */
    public String getLogin_type() {
        return login_type;
    }

    /**
     * 1密码登录 2 验证码登录 3第三方登录
     * @param login_type 1密码登录 2 验证码登录 3第三方登录
     */
    public void setLogin_type(String login_type) {
        this.login_type = login_type == null ? null : login_type.trim();
    }
}