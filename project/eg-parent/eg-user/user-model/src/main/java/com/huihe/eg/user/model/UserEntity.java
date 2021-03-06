package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@ApiModel(value="用户",description="用户属性说明")
public class UserEntity extends PageInfo {
    private static final long serialVersionUID = 5412389537396212037L;
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
     * 个人简介
     */
    @ApiModelProperty(value="个人简介")
    private String introduction;

    /**
     * 登录ip
     */
    @ApiModelProperty(value="登录ip")
    private String login_ip;

    /**
     * 状态  1正常  2 限制登录 3账号冻结
     */
    @ApiModelProperty(value="状态  1正常  2 限制登录 3 账号冻结 4 注销",example="1")
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
     * 教龄
     */
    @ApiModelProperty(value="教龄",example="1")
    private Integer master_age;

    /**
     * 1密码登录 2 验证码登录 3第三方登录
     */
    @ApiModelProperty(value="1密码登录 2 验证码登录 3第三方登录")
    private String login_type;

    /**
     * 老师特长
     */
    @ApiModelProperty(value="老师特长")
    private String specialty;

    /**
     * 安全密码
     */
    @ApiModelProperty(value="安全密码")
    private String security_pass;

    /**
     * 简介
     */
    @ApiModelProperty(value="简介")
    private String introduction_content;

    /**
     * 安全密码
     */
    @ApiModelProperty(value="年龄")
    private Integer age;

    /**
     * 苹果标识
     */
    @ApiModelProperty(value="苹果标识")
    private String apple_id;

    /**
     * 子类目
     */
    @ApiModelProperty(value="子类目")
    private String categories_child;
    @ApiModelProperty(value = "Login:登录 Logout：退出")
    private String logonStatus;

    public String getLogonStatus() {
        return logonStatus;
    }

    public void setLogonStatus(String logonStatus) {
        this.logonStatus = logonStatus;
    }

    @ApiModelProperty(value="验证码")
    private String verification_code;

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
    @ApiModelProperty(value="是否开启/关闭青年模式")
    private Boolean is_teenagers;

    public Boolean getIs_teenagers() {
        return is_teenagers;
    }

    public void setIs_teenagers(Boolean is_teenagers) {
        this.is_teenagers = is_teenagers;
    }

    @Field
    @ApiModelProperty(value = "注册来源",example = "ios")
    private String register_platform;

    @Field
    @ApiModelProperty(value = "登录来源",example = "ios")
    private String login_platform;

    @Field
    @ApiModelProperty(value = "登录来源",example = "ios")
    private String platform;

    @ApiModelProperty(value = "偏好",example = "ios")
    private String preference;

    @ApiModelProperty(value = "是否教付宝",example = "ios")
    private Boolean is_teach_paypal;

    @ApiModelProperty(value = "是否机器人 true 是 false 不是",example = "true")
    private Boolean is_robot;

    @ApiModelProperty(value = "关系",example = "ios")
    private String relationships;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCategories_child() {
        return categories_child;
    }

    public void setCategories_child(String categories_child) {
        this.categories_child = categories_child;
    }

    public String getRelationships() {
        return relationships;
    }

    public void setRelationships(String relationships) {
        this.relationships = relationships;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRegister_platform() {
        return register_platform;
    }

    public void setRegister_platform(String register_platform) {
        this.register_platform = register_platform;
    }


    public String getLogin_platform() {
        return login_platform;
    }

    public void setLogin_platform(String login_platform) {
        this.login_platform = login_platform;
    }

    public Boolean getIs_robot() {
        return is_robot;
    }

    public void setIs_robot(Boolean is_robot) {
        this.is_robot = is_robot;
    }

    /**
     * 性别 1男2女
     */

    @ApiModelProperty(value="性别 1男2女",example="1")
    private Integer sex;

    /**
     * 头像
     */

    @ApiModelProperty(value="头像")
    private String avatar;
    /**
     * 用户头像
     */

    @ApiModelProperty(value="用户头像")
    private String nick_name;
    /**
     * 出生日期
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value="出生日期")
    private Date birth;

    @ApiModelProperty(value = "邀请码")
    private String invitation_code;
    @ApiModelProperty(value = "wx验证code")
    private String wx_code;
    @ApiModelProperty(value="类型  get_live_tickets 领取直播卷")
    private String type;

    @ApiModelProperty(value="小程序code")
    private String code;

    @ApiModelProperty(value="wx帐号")
    private String wx_login_name;

    @ApiModelProperty(value="手机号")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWx_login_name() {
        return wx_login_name;
    }

    public void setWx_login_name(String wx_login_name) {
        this.wx_login_name = wx_login_name;
    }

    /**
     * 机器人管理员id
     */
    @Field(ignoreFields={"admin_id"})
    @ApiModelProperty(value="机器人管理员id",example="1")
    private Long admin_id;

    public String getIntroduction_content() {
        return introduction_content;
    }

    public void setIntroduction_content(String introduction_content) {
        this.introduction_content = introduction_content;
    }

    public Integer getMaster_age() {
        return master_age;
    }

    public void setMaster_age(Integer master_age) {
        this.master_age = master_age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private Long mechanism_id;

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWx_code() {
        return wx_code;
    }

    public void setWx_code(String wx_code) {
        this.wx_code = wx_code;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    /**
     * 安全密码
     * @return security_pass 安全密码
     */
    public String getSecurity_pass() {
        return security_pass;
    }

    /**
     * 安全密码
     * @param security_pass 安全密码
     */
    public void setSecurity_pass(String security_pass) {
        this.security_pass = security_pass == null ? null : security_pass.trim();
    }

    /**
     * 苹果标识
     * @return apple_id 苹果标识
     */
    public String getApple_id() {
        return apple_id;
    }

    /**
     * 苹果标识
     * @param apple_id 苹果标识
     */
    public void setApple_id(String apple_id) {
        this.apple_id = apple_id == null ? null : apple_id.trim();
    }
}