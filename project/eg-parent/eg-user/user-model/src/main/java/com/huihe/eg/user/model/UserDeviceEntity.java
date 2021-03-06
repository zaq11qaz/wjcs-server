package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value="",description="属性说明")
public class UserDeviceEntity extends PageInfo {
    private static final long serialVersionUID = -2475944114673895956L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 设备的国际移动设备身份码Android (支持) iOS (不支持)
     */
    @ApiModelProperty(value="设备的国际移动设备身份码Android (支持) iOS (不支持)")
    private String imei;

    /**
     * 设备的国际移动用户识别码 sim卡信息 
     */
    @ApiModelProperty(value="设备的国际移动用户识别码 sim卡信息 ")
    private String imsi;

    /**
     * 设备的型号
     */
    @ApiModelProperty(value="设备的型号")
    private String model;

    /**
     * 设备的生产厂商
     */
    @ApiModelProperty(value="设备的生产厂商")
    private String vendor;

    /**
     * 设备的唯一标识 ios 在设备重置后会重新生成
     */
    @ApiModelProperty(value="设备的唯一标识 ios 在设备重置后会重新生成")
    private String uuid;

    /**
     * 设备令牌（iOS设备唯一标识），用于APNS服务推送中标识设备的身份
     */
    @ApiModelProperty(value="设备令牌（iOS设备唯一标识），用于APNS服务推送中标识设备的身份")
    private String token;

    /**
     * 推送服务令牌（设备唯一标识），用于标识推送信息接收者身份
     */
    @ApiModelProperty(value="推送服务令牌（设备唯一标识），用于标识推送信息接收者身份")
    private String clientid;

    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    @ApiModelProperty(value = "苹果广告id")
    private String idfa;

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }
    /**
     * 系统的名称
     */
    @ApiModelProperty(value="系统的名称")
    private String name;

    /**
     * 系统的版本号
     */
    @ApiModelProperty(value="系统的版本号")
    private String version;

    /**
     * 系统语言信息
     */
    @ApiModelProperty(value="系统语言信息")
    private String language;

    /**
     * 第三方推送服务的应用标识
     */
    @ApiModelProperty(value="第三方推送服务的应用标识")
    private String appid;

    /**
     * 第三方推送服务器的应用键值
     */
    @ApiModelProperty(value="第三方推送服务器的应用键值")
    private String appkey;

    /**
     * 个推voip
     */
    @ApiModelProperty(value="个推voip")
    private String voip_token;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value="唯一标识")
    private String unique_id;

    /**
     * mob推送标识
     */
    @ApiModelProperty(value="mob推送标识")
    private String rid;

    /**
     * 苹果广告id
     */
    @ApiModelProperty(value="苹果广告id")
    private String teach_pay_token;

    /**
     * 苹果广告id
     */
    @ApiModelProperty(value="苹果广告id")
    private String teach_pay_rid;

    public String getTeach_pay_token() {
        return teach_pay_token;
    }

    public void setTeach_pay_token(String teach_pay_token) {
        this.teach_pay_token = teach_pay_token;
    }

    public String getTeach_pay_rid() {
        return teach_pay_rid;
    }

    public void setTeach_pay_rid(String teach_pay_rid) {
        this.teach_pay_rid = teach_pay_rid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    /**
     * 主键id
     * @return id 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键id
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
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
     * 设备的国际移动设备身份码Android (支持) iOS (不支持)
     * @return imei 设备的国际移动设备身份码Android (支持) iOS (不支持)
     */
    public String getImei() {
        return imei;
    }

    /**
     * 设备的国际移动设备身份码Android (支持) iOS (不支持)
     * @param imei 设备的国际移动设备身份码Android (支持) iOS (不支持)
     */
    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    /**
     * 设备的国际移动用户识别码 sim卡信息 
     * @return imsi 设备的国际移动用户识别码 sim卡信息 
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * 设备的国际移动用户识别码 sim卡信息 
     * @param imsi 设备的国际移动用户识别码 sim卡信息 
     */
    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    /**
     * 设备的型号
     * @return model 设备的型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 设备的型号
     * @param model 设备的型号
     */
    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    /**
     * 设备的生产厂商
     * @return vendor 设备的生产厂商
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * 设备的生产厂商
     * @param vendor 设备的生产厂商
     */
    public void setVendor(String vendor) {
        this.vendor = vendor == null ? null : vendor.trim();
    }

    /**
     * 设备的唯一标识 ios 在设备重置后会重新生成
     * @return uuid 设备的唯一标识 ios 在设备重置后会重新生成
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设备的唯一标识 ios 在设备重置后会重新生成
     * @param uuid 设备的唯一标识 ios 在设备重置后会重新生成
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 设备令牌（iOS设备唯一标识），用于APNS服务推送中标识设备的身份
     * @return token 设备令牌（iOS设备唯一标识），用于APNS服务推送中标识设备的身份
     */
    public String getToken() {
        return token;
    }

    /**
     * 设备令牌（iOS设备唯一标识），用于APNS服务推送中标识设备的身份
     * @param token 设备令牌（iOS设备唯一标识），用于APNS服务推送中标识设备的身份
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 推送服务令牌（设备唯一标识），用于标识推送信息接收者身份
     * @return clientid 推送服务令牌（设备唯一标识），用于标识推送信息接收者身份
     */
    public String getClientid() {
        return clientid;
    }

    /**
     * 推送服务令牌（设备唯一标识），用于标识推送信息接收者身份
     * @param clientid 推送服务令牌（设备唯一标识），用于标识推送信息接收者身份
     */
    public void setClientid(String clientid) {
        this.clientid = clientid == null ? null : clientid.trim();
    }

    /**
     * 添加时间
     * @return create_time 添加时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 添加时间
     * @param create_time 添加时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 系统的名称
     * @return name 系统的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 系统的名称
     * @param name 系统的名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 系统的版本号
     * @return version 系统的版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * 系统的版本号
     * @param version 系统的版本号
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * 系统语言信息
     * @return language 系统语言信息
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 系统语言信息
     * @param language 系统语言信息
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    /**
     * 第三方推送服务的应用标识
     * @return appid 第三方推送服务的应用标识
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 第三方推送服务的应用标识
     * @param appid 第三方推送服务的应用标识
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 第三方推送服务器的应用键值
     * @return appkey 第三方推送服务器的应用键值
     */
    public String getAppkey() {
        return appkey;
    }

    /**
     * 第三方推送服务器的应用键值
     * @param appkey 第三方推送服务器的应用键值
     */
    public void setAppkey(String appkey) {
        this.appkey = appkey == null ? null : appkey.trim();
    }

    /**
     * 个推voip
     * @return voip_token 个推voip
     */
    public String getVoip_token() {
        return voip_token;
    }

    /**
     * 个推voip
     * @param voip_token 个推voip
     */
    public void setVoip_token(String voip_token) {
        this.voip_token = voip_token == null ? null : voip_token.trim();
    }
}