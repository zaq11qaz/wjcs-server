package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="个人提现信息",description="个人提现信息属性说明")
public class RechargeInfoEntity extends PageInfo {
    private static final long serialVersionUID = 1461264100168346139L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * user_id
     */
    @ApiModelProperty(value="user_id",example="1")
    private Long user_id;

    /**
     * mechanism_id 机构id
     */
    @ApiModelProperty(value="mechanism_id",example="1")
    private Long mechanism_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 支付宝帐号
     */
    @ApiModelProperty(value="支付宝帐号")
    private String aliPay;

    /**
     * 微信帐号
     */
    @ApiModelProperty(value="微信帐号")
    private String wxPay;

    /**
     * 实名
     */
    @ApiModelProperty(value="实名")
    private String name;

    /**
     * 1、ALIPAY_USER_ID 支付宝的会员ID2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式
     */
    @ApiModelProperty(value="1、ALIPAY_USER_ID 支付宝的会员ID2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式")
    private String identity_type;

    /**
     * 提现金额
     */
    @ApiModelProperty(value="提现金额",example="1")
    private BigDecimal cash;

    /**
     * 临时授权码
     */
    @ApiModelProperty(value="临时授权码")
    private String auth_code;

    /**
     * 客户端生成唯一标识
     */
    @ApiModelProperty(value="客户端生成唯一标识")
    private String state;

    /**
     * 令牌
     */
    @ApiModelProperty(value="令牌")
    private String access_token;

    /**
     * 验证方式
     */
    @ApiModelProperty(value="验证方式")
    private String scope;

    /**
     * 刷新token
     */
    @ApiModelProperty(value="刷新token")
    private String refresh_token;

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * user_id
     * @return user_id user_id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * user_id
     * @param user_id user_id
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
     * 支付宝帐号
     * @return aliPay 支付宝帐号
     */
    public String getAliPay() {
        return aliPay;
    }

    /**
     * 支付宝帐号
     * @param aliPay 支付宝帐号
     */
    public void setAliPay(String aliPay) {
        this.aliPay = aliPay == null ? null : aliPay.trim();
    }

    /**
     * 微信帐号
     * @return wxPay 微信帐号
     */
    public String getWxPay() {
        return wxPay;
    }

    /**
     * 微信帐号
     * @param wxPay 微信帐号
     */
    public void setWxPay(String wxPay) {
        this.wxPay = wxPay == null ? null : wxPay.trim();
    }

    /**
     * 实名
     * @return name 实名
     */
    public String getName() {
        return name;
    }

    /**
     * 实名
     * @param name 实名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 1、ALIPAY_USER_ID 支付宝的会员ID2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式
     * @return identity_type 1、ALIPAY_USER_ID 支付宝的会员ID2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式
     */
    public String getIdentity_type() {
        return identity_type;
    }

    /**
     * 1、ALIPAY_USER_ID 支付宝的会员ID2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式
     * @param identity_type 1、ALIPAY_USER_ID 支付宝的会员ID2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式
     */
    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type == null ? null : identity_type.trim();
    }

    /**
     * 提现金额
     * @return cash 提现金额
     */
    public BigDecimal getCash() {
        return cash;
    }

    /**
     * 提现金额
     * @param cash 提现金额
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    /**
     * 临时授权码
     * @return auth_code 临时授权码
     */
    public String getAuth_code() {
        return auth_code;
    }

    /**
     * 临时授权码
     * @param auth_code 临时授权码
     */
    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code == null ? null : auth_code.trim();
    }

    /**
     * 客户端生成唯一标识
     * @return state 客户端生成唯一标识
     */
    public String getState() {
        return state;
    }

    /**
     * 客户端生成唯一标识
     * @param state 客户端生成唯一标识
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 令牌
     * @return access_token 令牌
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * 令牌
     * @param access_token 令牌
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token == null ? null : access_token.trim();
    }

    /**
     * 验证方式
     * @return scope 验证方式
     */
    public String getScope() {
        return scope;
    }

    /**
     * 验证方式
     * @param scope 验证方式
     */
    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    /**
     * 刷新token
     * @return refresh_token 刷新token
     */
    public String getRefresh_token() {
        return refresh_token;
    }

    /**
     * 刷新token
     * @param refresh_token 刷新token
     */
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token == null ? null : refresh_token.trim();
    }
}