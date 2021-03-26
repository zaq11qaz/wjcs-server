package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.cy.framework.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huihe.eg.comm.elasticsearch.Field;
import com.huihe.eg.comm.util.EmojiUtil;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="用户基本信息",description="用户基本信息属性说明")
@Document(indexName = "user",type = "userinfo",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class UserInfoEntity extends PageInfo {
    private static final long serialVersionUID = 1320013091569722774L;
    /**
     * 用户id
     */
    @Id
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;


    /**
     * 用户创建时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="用户创建时间")
    private Date create_time;

    /**
     * 手机号
     */
    @Field
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 邮箱号
     */
    @ApiModelProperty(value="邮箱号")
    @Field
    private String mail;

    /**
     * 支付密码
     */
    @Field(store=false)
    @ApiModelProperty(value="支付密码")
    private String pay_pass;

    /**
     * 修改时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 性别 1男2女
     */
    @Field
    @ApiModelProperty(value="性别 1男2女",example="1")
    private Integer sex;

    /**
     * 头像
     */
    @Field
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 出生日期
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value="出生日期")
    private Date birth;

    /**
     * 用户标签
     */
    @Field
    @ApiModelProperty(value="用户标签")
    private String lable;

    /**
     * 个人简介
     */
    @Field
    @ApiModelProperty(value="个人简介")
    private String intro;

    /**
     * 账户余额
     */
    @ApiModelProperty(value="账户余额",example="1")
    private BigDecimal cash;

    /**
     * 个性签名
     */
    @Field
    @ApiModelProperty(value="个性签名")
    private String signature;

    /**
     * 会员等级  1畅聊会员 2普通会员
     */
    @Field
    @ApiModelProperty(value="会员等级  1畅聊会员 2普通会员",example="1")
    private Integer member_level;

    /**
     * 是否是会员  false 不是会员  true 是会员
     */
    @Field
    @ApiModelProperty(value="是否是会员  false 不是会员  true 是会员",example="false")
    private Boolean is_member;

    /**
     * 是否实名认证  false 未认证  true 已认证
     */
    @Field
    @ApiModelProperty(value="是否实名认证  false 未认证  true 已认证",example="false")
    private Boolean identity_auth;

    /**
     * 猫币余额
     */
    @ApiModelProperty(value="猫币余额",example="1")
    private BigDecimal cat_coin;

    /**
     * 用户头像
     */
    @Field
    @ApiModelProperty(value="用户头像")
    private String nick_name;

    /**
     * 国旗
     */
    @Field
    @ApiModelProperty(value="国旗")
    private String national_flag;

    /**
     * 留学生认证
     */
    @Field
    @ApiModelProperty(value="境外身份认证",example="false")
    private Boolean overseas_auth;

    /**
     * 故乡
     */
    @Field
    @ApiModelProperty(value="故乡")
    private String hometown;

    /**
     * 偏好
     */
    @Field
    @ApiModelProperty(value="偏好")
    private String preference;

    /**
     * 是否是机器人
     */
    @Field
    @ApiModelProperty(value="是否是机器人",example="false")
    private Boolean is_robot;

    /**
     * 机器人管理员id
     */
    @Field
    @ApiModelProperty(value="所属机构id",example="1")
    private Long admin_id;

    /**
     * 机器人管理员id
     */
    @Field
    @ApiModelProperty(value="qq",example="1")
    private Long qq;

    /**
     * 是否是广告位  false不是 true 是
     */
    @Field
    @ApiModelProperty(value="是否是广告位  false不是 true 是",example="false")
    private Boolean advertising_position;

    /**
     * 邀请码
     */
    @ApiModelProperty(value="邀请码")
    private String invite_code;

    /**--
     * 用户二维码
     */
    @Field
    @ApiModelProperty(value="用户二维码")
    private String qrcode;

    /**
     * 分销二维码
     */
    @Field
    @ApiModelProperty(value="分销二维码")
    private String distrib_qrcode;

    /**
     * 喜欢数量
     */
    @Field
    @ApiModelProperty(value="喜欢数量",example="1")
    private Integer like_num;

    /**
     * 所在国家
     */
    @Field
    @ApiModelProperty(value="所在国家")
    private String country;

    /**
     * 所在城市
     */
    @Field
    @ApiModelProperty(value="所在城市")
    private String city;

    /**
     * 喜欢类别
     */
    @Field
    @ApiModelProperty(value="喜欢类别")
    private String like_category;

    @ApiModelProperty(value="搜索类")
    @Field
    private SearchParam searchParam;

    /**
     * 通讯录数量
     */
    @ApiModelProperty(value="通讯录数量",example="1")
    private Integer contacts_num;
    /**
     * 短信发送数量
     */
    @ApiModelProperty(value="短信发送数量",example="1")
    private Integer message_num;
    /**
     * 注册数量
     */
    @ApiModelProperty(value="注册数量",example="1")
    private Integer registr_num;

    @ApiModelProperty(value="下线时间，date形式")
    private Date log_out_time;

    @ApiModelProperty(value="下线时间，计算",example="1")
    private Integer out_time;

    @ApiModelProperty(value="认证的境外身份名称")
    private String overseas_identity_name;

    @ApiModelProperty(value="距离",example="1")
    private BigDecimal distance;

    @ApiModelProperty(value="列表用户当地时间")
    private Date local_time = new Date();

    @ApiModelProperty(value="关系链")
    private String relation_chain = "同事";
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "开始时间")
    private Date begin_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "结束时间")
    private Date end_time;
    @ApiModelProperty(value = "操作用户(判断喜欢)",example = "1")
    private Long oper_id;
    @ApiModelProperty(value = "id集合",example = "[]")
    private List<Long> user_idList;
    @ApiModelProperty(value = "验证码")
    private String vali_code;
    @ApiModelProperty(value = "语言")
    private String languages;

    @ApiModelProperty(value = "关系")
    private String relationship;
    @ApiModelProperty(value="在线状态",example="1")
    private Integer online_state;
    @ApiModelProperty(value="账号状态",example="1")
    private Integer account_state;

    public Integer getAccount_state() {
        return account_state;
    }

    public void setAccount_state(Integer account_state) {
        this.account_state = account_state;
    }

    @ApiModelProperty(value = "畅聊人数",example="1")
    private Integer chatting_count;

    @ApiModelProperty(value = "详细偏好")
    private UserPreferenceEntity userPreferenceEntity;

    @Field
    @ApiModelProperty(value = "是否可以通过id找到我",example="false")
    private Boolean is_id;

    @Field
    @ApiModelProperty(value = "是否可以通过手机号找到我",example="false")
    private Boolean is_mobile;

    @Field
    @ApiModelProperty(value = "是否可以通过昵称找到我",example="false")
    private Boolean is_name;

    @Field
    @ApiModelProperty(value = "是否可以通过邮箱找到我",example="false")
    private Boolean is_mail;
    @ApiModelProperty(value = "im签名")
    private String sign;

    @ApiModelProperty(value = "母语")
    private String  mother_tongue;
    @ApiModelProperty(value = "背景图片")
    private String background_pic;

    @ApiModelProperty(value = "名片网页地址")
    private String url;

    @ApiModelProperty(value = "是否优质",example = "false")
    private Boolean is_high_quality;
    @ApiModelProperty(value = "评分",example = "1")
    private Integer rating;
    @ApiModelProperty(value = "是否认证助学师",example = "false")
    private Boolean is_help;
    @ApiModelProperty(value = "是否有未读消息",example = "false")
    private Boolean is_unread;
    @ApiModelProperty(value = "视频时长",example = "1")
    private Integer duration;
    @ApiModelProperty(value = "数据map")
    private Map<String,Object> map;
    @ApiModelProperty(value = "登录统计",example = "1")
    private Integer loginStatistical;

    public Integer getLoginStatistical() {
        return loginStatistical;
    }

    public void setLoginStatistical(Integer loginStatistical) {
        this.loginStatistical = loginStatistical;
    }
    @ApiModelProperty(value = "助学师类型" )
    private String master_type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "统计开始时间")
    private Date begin_time_statistics;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "统计结束时间")
    private Date end_time_statistics;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "统计时间")
    private Date statistics_time;
    @ApiModelProperty(value = "是否青少年",example = "false")
    private Boolean is_teenagers;
    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;
    /**
     * 切换绑定的用户id
     */
    @ApiModelProperty(value="切换绑定的用户id",example="1")
    private Long binding_id;

    @ApiModelProperty(value="机构招募师id",example="false")
    private Long mechanism_recommender_id;

    @ApiModelProperty(value="外教招募师id",example="false")
    private Long master_recommender_id;

    @ApiModelProperty(value="邀请人id",example="1")
    private Long invitees_id;

    @ApiModelProperty(value="使用规则id",example="1")
    private Long role_id;

    @ApiModelProperty(value="身份 0 用户 1 助教 2房管",example="1")
    private Integer worker_identity;

    @ApiModelProperty(value = "名",example = "1")
    private String full_name;

    @ApiModelProperty(value = "fullName id集合",example = "1")
    private List<Long> ids;

    @ApiModelProperty(value = "liginName id集合",example = "1")
    private List<Long> loginIDs;

    @ApiModelProperty(value = "帐号",example = "1")
    private String login_name;

    @ApiModelProperty(value="绑定老师id",example="1")
    private Long teacher_id;

    @ApiModelProperty(value="绑定身份id",example="1")
    private Long identity_id;

    @ApiModelProperty(value="wx二维码",example="1")
    private String wx_code_url;

    @ApiModelProperty(value="wx帐号",example="1")
    private String wx_code;

    @ApiModelProperty(value="老师类型",example="1")
    private String teacher_type;

    @ApiModelProperty(value="是否领取优惠券",example="1")
    private Boolean is_collection;

    @ApiModelProperty(value="积分",example="1")
    private Integer points;

    @ApiModelProperty(value="关系",example="1")
    private String relationships;

    @ApiModelProperty(value="年龄",example="1")
    private Integer age;

    @ApiModelProperty(value="注册来源",example="1")
    private String register_platform;

    @ApiModelProperty(value="教付宝",example="1")
    private Boolean is_teach_paypal;

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public String getRegister_platform() {
        return register_platform;
    }

    public void setRegister_platform(String register_platform) {
        this.register_platform = register_platform;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRelationships() {
        return relationships;
    }

    public void setRelationships(String relationships) {
        this.relationships = relationships;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(Boolean is_collection) {
        this.is_collection = is_collection;
    }

    public String getTeacher_type() {
        return teacher_type;
    }

    public void setTeacher_type(String teacher_type) {
        this.teacher_type = teacher_type;
    }

    public String getWx_code() {
        return wx_code;
    }

    public void setWx_code(String wx_code) {
        this.wx_code = wx_code;
    }

    public List<Long> getIds() {
        return ids;
    }

    public List<Long> getLoginIDs() {
        return loginIDs;
    }

    public String getWx_code_url() {
        return wx_code_url;
    }

    public void setWx_code_url(String wx_code_url) {
        this.wx_code_url = wx_code_url;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public void setLoginIDs(List<Long> loginIDs) {
        this.loginIDs = loginIDs;
    }

    public Long getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(Long identity_id) {
        this.identity_id = identity_id;
    }

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Integer getWorker_identity() {
        return worker_identity;
    }

    public void setWorker_identity(Integer worker_identity) {
        this.worker_identity = worker_identity;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public Long getInvitees_id() {
        return invitees_id;
    }

    public void setInvitees_id(Long invitees_id) {
        this.invitees_id = invitees_id;
    }

    public Long getMechanism_recommender_id() {
        return mechanism_recommender_id;
    }

    public void setMechanism_recommender_id(Long mechanism_recommender_id) {
        this.mechanism_recommender_id = mechanism_recommender_id;
    }

    public Long getMaster_recommender_id() {
        return master_recommender_id;
    }

    public void setMaster_recommender_id(Long master_recommender_id) {
        this.master_recommender_id = master_recommender_id;
    }


    public Long getBinding_id() {
        return binding_id;
    }

    public void setBinding_id(Long binding_id) {
        this.binding_id = binding_id;
    }

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    public Boolean getIs_teenagers() {
        return is_teenagers;
    }

    public void setIs_teenagers(Boolean is_teenagers) {
        this.is_teenagers = is_teenagers;
    }

    public Date getStatistics_time() {
        return statistics_time;
    }

    public void setStatistics_time(Date statistics_time) {
        this.statistics_time = statistics_time;
    }

    public Date getBegin_time_statistics() {
        return begin_time_statistics;
    }

    public void setBegin_time_statistics(Date begin_time_statistics) {
        this.begin_time_statistics = begin_time_statistics;
    }

    public Date getEnd_time_statistics() {
        return end_time_statistics;
    }

    public void setEnd_time_statistics(Date end_time_statistics) {
        this.end_time_statistics = end_time_statistics;
    }

    public String getMaster_type() {
        return master_type;
    }

    public void setMaster_type(String master_type) {
        this.master_type = master_type;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getIs_unread() {
        return is_unread;
    }

    public void setIs_unread(Boolean is_unread) {
        this.is_unread = is_unread;
    }

    public Boolean getIs_help() {
        return is_help;
    }

    public void setIs_help(Boolean is_help) {
        this.is_help = is_help;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getIs_high_quality() {
        return is_high_quality;
    }

    public void setIs_high_quality(Boolean is_high_quality) {
        this.is_high_quality = is_high_quality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBackground_pic() {
        return background_pic;
    }

    public void setBackground_pic(String background_pic) {
        this.background_pic = background_pic;
    }

    public String getMother_tongue() {
        return mother_tongue;
    }

    public void setMother_tongue(String mother_tongue) {
        this.mother_tongue = mother_tongue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Boolean getIs_id() {
        return is_id;
    }

    public void setIs_id(Boolean is_id) {
        this.is_id = is_id;
    }

    public Boolean getIs_mobile() {
        return is_mobile;
    }

    public void setIs_mobile(Boolean is_mobile) {
        this.is_mobile = is_mobile;
    }

    public Boolean getIs_name() {
        return is_name;
    }

    public void setIs_name(Boolean is_name) {
        this.is_name = is_name;
    }

    public Boolean getIs_mail() {
        return is_mail;
    }

    public void setIs_mail(Boolean is_mail) {
        this.is_mail = is_mail;
    }


    public UserPreferenceEntity getUserPreferenceEntity() {
        return userPreferenceEntity;
    }

    public void setUserPreferenceEntity(UserPreferenceEntity userPreferenceEntity) {
        this.userPreferenceEntity = userPreferenceEntity;
    }

    public Integer getChatting_count() {
        return chatting_count;
    }

    public void setChatting_count(Integer chatting_count) {
        this.chatting_count = chatting_count;
    }

    public Integer getOnline_state() {
        return online_state;
    }

    public void setOnline_state(Integer online_state) {
        this.online_state = online_state;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getVali_code() {
        return vali_code;
    }

    public void setVali_code(String vali_code) {
        this.vali_code = vali_code;
    }

    public List<Long> getUser_idList() {
        return user_idList;
    }

    public void setUser_idList(List<Long> user_idList) {
        this.user_idList = user_idList;
    }

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
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
     * 用户创建时间
     * @return create_time 用户创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 用户创建时间
     * @param create_time 用户创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 手机号
     * @return mobile 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 邮箱号
     * @return mail 邮箱号
     */
    public String getMail() {
        return mail;
    }

    /**
     * 邮箱号
     * @param mail 邮箱号
     */
    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    /**
     * 支付密码
     * @return pay_pass 支付密码
     */
    public String getPay_pass() {
        return pay_pass;
    }

    /**
     * 支付密码
     * @param pay_pass 支付密码
     */
    public void setPay_pass(String pay_pass) {
        this.pay_pass = pay_pass == null ? null : pay_pass.trim();
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 修改时间
     * @param update_time 修改时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 性别 1男2女
     * @return sex 性别 1男2女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 性别 1男2女
     * @param sex 性别 1男2女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 头像
     * @return avatar 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 出生日期
     * @return birth 出生日期
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * 出生日期
     * @param birth 出生日期
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 用户标签
     * @return lable 用户标签
     */
    public String getLable() {
        return lable;
    }

    /**
     * 用户标签
     * @param lable 用户标签
     */
    public void setLable(String lable) {
        this.lable = lable == null ? null : lable.trim();
    }

    /**
     * 个人简介
     * @return intro 个人简介
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 个人简介
     * @param intro 个人简介
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * 账户余额
     * @return cash 账户余额
     */
    public BigDecimal getCash() {
        return cash;
    }

    /**
     * 账户余额
     * @param cash 账户余额
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    /**
     * 个性签名
     * @return signature 个性签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 个性签名
     * @param signature 个性签名
     */
    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    /**
     * 会员等级  1畅聊会员 2普通会员
     * @return member_level 会员等级  1畅聊会员 2普通会员
     */
    public Integer getMember_level() {
        return member_level;
    }

    /**
     * 会员等级  1畅聊会员 2普通会员
     * @param member_level 会员等级  1畅聊会员 2普通会员
     */
    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
    }

    /**
     * 是否是会员  false 不是会员  true 是会员
     * @return is_member 是否是会员  false 不是会员  true 是会员
     */
    public Boolean getIs_member() {
        return is_member;
    }

    /**
     * 是否是会员  false 不是会员  true 是会员
     * @param is_member 是否是会员  false 不是会员  true 是会员
     */
    public void setIs_member(Boolean is_member) {
        this.is_member = is_member;
    }

    /**
     * 是否实名认证  false 未认证  true 已认证
     * @return identity_auth 是否实名认证  false 未认证  true 已认证
     */
    public Boolean getIdentity_auth() {
        return identity_auth;
    }

    /**
     * 是否实名认证  false 未认证  true 已认证
     * @param identity_auth 是否实名认证  false 未认证  true 已认证
     */
    public void setIdentity_auth(Boolean identity_auth) {
        this.identity_auth = identity_auth;
    }

    /**
     * 猫币余额
     * @return cat_coin 猫币余额
     */
    public BigDecimal getCat_coin() {
        return cat_coin;
    }

    /**
     * 猫币余额
     * @param cat_coin 猫币余额
     */
    public void setCat_coin(BigDecimal cat_coin) {
        this.cat_coin = cat_coin;
    }

    /**
     * 用户头像
     * @return nick_name 用户昵称
     */
    public String getNick_name() {
//        return StringUtil.isNotEmpty(nick_name)?EmojiUtil.resolveToEmojiFromByte(nick_name):nick_name;
//        return  StringUtil.isNotEmpty(nick_name)?EmojiParser.parseToUnicode(nick_name):nick_name;
        return nick_name;
    }

    /**
     * 用户头像
     * @param nick_name 用户昵称
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name == null ? null : nick_name.trim();
//        this.nick_name = nick_name == null ? null : EmojiUtil.resolveToByteFromEmoji(nick_name.trim());
    }

    /**
     * 国旗
     * @return national_flag 国旗
     */
    public String getNational_flag() {
        return national_flag;
    }

    /**
     * 国旗
     * @param national_flag 国旗
     */
    public void setNational_flag(String national_flag) {
        this.national_flag = national_flag == null ? null : national_flag.trim();
    }

    /**
     * 境外身份认证
     * @return overseas_auth 境外身份认证认证
     */
    public Boolean getOverseas_auth() {
        return overseas_auth;
    }

    /**
     * 境外身份认证认证
     * @param overseas_auth 境外身份认证认证
     */
    public void setOverseas_auth(Boolean overseas_auth) {
        this.overseas_auth = overseas_auth;
    }


    /**
     * 故乡
     * @return hometown 故乡
     */
    public String getHometown() {
        return hometown;
    }

    /**
     * 故乡
     * @param hometown 故乡
     */
    public void setHometown(String hometown) {
        this.hometown = hometown == null ? null : hometown.trim();
    }

    /**
     * 偏好
     * @return preference 偏好
     */
    public String getPreference() {
        return preference;
    }

    /**
     * 偏好
     * @param preference 偏好
     */
    public void setPreference(String preference) {
        this.preference = preference == null ? null : preference.trim();
    }

    /**
     * 是否是机器人
     * @return is_robot 是否是机器人
     */
    public Boolean getIs_robot() {
        return is_robot;
    }

    /**
     * 是否是机器人
     * @param is_robot 是否是机器人
     */
    public void setIs_robot(Boolean is_robot) {
        this.is_robot = is_robot;
    }

    /**
     * 所属机构id
     * @return admin_id 所属机构id
     */
    public Long getAdmin_id() {
        return admin_id;
    }

    /**
     * 所属机构id
     * @param admin_id 所属机构id
     */
    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    /**
     * 是否是广告位  false不是 true 是
     * @return advertising_position 是否是广告位  false不是 true 是
     */
    public Boolean getAdvertising_position() {
        return advertising_position;
    }

    /**
     * 是否是广告位  false不是 true 是
     * @param advertising_position 是否是广告位  false不是 true 是
     */
    public void setAdvertising_position(Boolean advertising_position) {
        this.advertising_position = advertising_position;
    }

    /**
     * 邀请码
     * @return invite_code 邀请码
     */
    public String getInvite_code() {
        return invite_code;
    }

    /**
     * 邀请码
     * @param invite_code 邀请码
     */
    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code == null ? null : invite_code.trim();
    }

    /**
     * 用户二维码
     * @return qrcode 用户二维码
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 用户二维码
     * @param qrcode 用户二维码
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    /**
     * 分销二维码
     * @return distrib_qrcode 分销二维码
     */
    public String getDistrib_qrcode() {
        return distrib_qrcode;
    }

    /**
     * 分销二维码
     * @param distrib_qrcode 分销二维码
     */
    public void setDistrib_qrcode(String distrib_qrcode) {
        this.distrib_qrcode = distrib_qrcode == null ? null : distrib_qrcode.trim();
    }

    /**
     * 喜欢数量
     * @return like_num 喜欢数量
     */
    public Integer getLike_num() {
        return like_num;
    }

    /**
     * 喜欢数量
     * @param like_num 喜欢数量
     */
    public void setLike_num(Integer like_num) {
        this.like_num = like_num;
    }

    /**
     * 所在国家
     * @return country 所在国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 所在国家
     * @param country 所在国家
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 所在城市
     * @return city 所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 所在城市
     * @param city 所在城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 喜欢类别
     * @return like_category 喜欢类别
     */
    public String getLike_category() {
        return like_category;
    }

    /**
     * 喜欢类别
     * @param like_category 喜欢类别
     */
    public void setLike_category(String like_category) {
        this.like_category = like_category == null ? null : like_category.trim();
    }

    /**
     * 搜索参数
     * @return
     */
    public SearchParam getSearchParam() {
        return searchParam;
    }

    /**
     * 搜索参数
     * @param searchParam
     */
    public void setSearchParam(SearchParam searchParam) {
        this.searchParam = searchParam;
    }

    /**
     * 通讯录数量
     * @return
     */
    public Integer getContacts_num() {
        return contacts_num;
    }


    /**
     * 通讯录数量
     * @param contacts_num
     */
    public void setContacts_num(Integer contacts_num) {
        this.contacts_num = contacts_num;
    }

    /**
     * 短信发送数量
     * @return
     */
    public Integer getMessage_num() {
        return message_num;
    }

    /**
     * 短信发送数量
     * @param message_num
     */
    public void setMessage_num(Integer message_num) {
        this.message_num = message_num;
    }

    /**
     * 注册数量
     * @return
     */
    public Integer getRegistr_num() {
        return registr_num;
    }

    /**
     * 注册数量
     * @param registr_num
     */
    public void setRegistr_num(Integer registr_num) {
        this.registr_num = registr_num;
    }

    public Date getLog_out_time() {
        return log_out_time;
    }

    public void setLog_out_time(Date log_out_time) {
        this.log_out_time = log_out_time;
    }

    public Integer getOut_time() {
        return out_time;
    }

    public void setOut_time(Integer out_time) {
        this.out_time = out_time;
    }

    public String getOverseas_identity_name() {
        return overseas_identity_name;
    }

    public void setOverseas_identity_name(String overseas_identity_name) {
        this.overseas_identity_name = overseas_identity_name;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Date getLocal_time() {
        return local_time;
    }

    public void setLocal_time(Date local_time) {
        this.local_time = local_time;
    }
}