package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="充值记录",description="充值记录属性说明")
public class RechargeRecordEntity extends PageInfo {
    private static final long serialVersionUID = 3159520176779316146L;
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
     * 数量
     */
    @ApiModelProperty(value="数量",example="1")
    private Long count;

    /**
     * 充值描述
     */
    @ApiModelProperty(value="充值描述")
    private String rcharge_abstract;

    /**
     * 充值用户
     */
    @ApiModelProperty(value="充值用户")
    private String rcharge_account;

    /**
     * 创建类型 single_session 单节课 full_purchase 全额购 use_of_vouchers 用券
     */
    @ApiModelProperty(value="创建类型 single_session 单节课 full_purchase 全额购 use_of_vouchers 用券")
    private String type;

    /**
     * map
     */
    @ApiModelProperty(value="map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }


    /**
     * 充值来源
     */
    @ApiModelProperty(value="充值来源")
    private String source;

    /**
     * 选中商品id
     */
    @ApiModelProperty(value="选中商品id")
    private String selected_id;

    /**
     * 支付密码
     */
    @ApiModelProperty(value="支付密码")
    private String pay_pass;

    /**
     * 充值金额
     */
    @ApiModelProperty(value="充值金额",example="1")
    private BigDecimal amount;

    /**
     * 充值内容的加密信息，用于验证
     */
    @ApiModelProperty(value="充值内容的加密信息，用于验证")
    private String rcharge_valid;

    @ApiModelProperty(value = "帐号" , example="1")
    private String login_name;

    @ApiModelProperty(value = "帐号s" , example="1")
    private List<Long> loginIDs;

    /**
     * 充值时间
     */
    @ApiModelProperty(value="充值时间")
    private Date rcharge_time;

    /**
     * 订单编号
     */
    @ApiModelProperty(value="订单编号")
    private String flowing_no;

    /**
     * 账户号
     */
    @ApiModelProperty(value="账户号")
    private String trans_no;

    /**
     * 充值成功时间
     */
    @ApiModelProperty(value="充值成功时间")
    private Date finished_time;

    /**
     * member 会员充值  authentication 认证充值  account  账户充值 master提现  study_card 学习卡购买  class_card直播课程学习卡
     */
    @ApiModelProperty(value="member 会员充值  authentication 认证充值  account  账户充值 master提现  study_card 学习卡购买  class_card直播课程学习卡")
    private String rcharge_type;

    /**
     * 是否充值成功  true 成功  false 是吧
     */
    @ApiModelProperty(value="是否充值成功  true 成功  false 是吧",example="false")
    private Boolean finished;

    /**
     * 是否机器人 true 是 false 不是
     */
    @ApiModelProperty(value="是否机器人 true 是 false 不是",example="false")
    private Boolean is_robot;

    /**
     * 是否教付宝 true 是 false 不是
     */
    @ApiModelProperty(value="是否教付宝 true 是 false 不是",example="false")
    private Boolean is_teach_paypal;

    /**
     * 是否结算 true 是 false 不是
     */
    @ApiModelProperty(value="是否结算 true 是 false 不是",example="false")
    private Boolean is_settlement;

    /**
     * 订单状态1创建订单状态 2 付款成功 3 付款失败
     */
    @ApiModelProperty(value="订单状态1创建订单状态 2 付款成功 3 付款失败",example="1")
    private Integer status;

    /**
     * 活动在线人数
     */
    @ApiModelProperty(value="活动在线人数",example="1")
    private Integer onLineCount;

    /**
     * 会员等级
     */
    @ApiModelProperty(value="会员等级",example="1")
    private Integer member_level;

    /**
     * true 充值  false 提现
     */
    @ApiModelProperty(value="true 充值  false 提现",example="false")
    private Boolean account;

    /**
     * ios订单效验唯一标识
     */
    @ApiModelProperty(value="ios订单效验唯一标识")
    private String receipt_data;

    /**
     * 充值会员时长(月)
     */
    @ApiModelProperty(value="充值会员时长(月)",example="1")
    private Integer member_duration;

    /**
     * 学习课类型
     */
    @ApiModelProperty(value="学习课类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  major_special 专业小班,cross_border_special 跨境小班,mother_tongue_special 母语小班")
    private String study_type;

    /**
     * 课数
     */
    @ApiModelProperty(value="课数",example="1")
    private Integer course_num;

    /**
     * 学习卡id
     */
    @ApiModelProperty(value="学习卡id",example="1")
    private Long studycard_id;

    /**
     * 支付押金id
     */
    @ApiModelProperty(value="支付押金id",example="1")
    private Long pay_deposit_id;

    /**
     * 直播课程学习卡id
     */
    @ApiModelProperty(value="直播课程学习卡id",example="1")
    private Long class_card_id;
    /**
     * 老师课程id
     */
    @ApiModelProperty(value="老师课程id",example="1")
    private Long appointment_id;
    /**
     * 用户预约课程id
     */
    @ApiModelProperty(value="用户预约课程id",example="1")
    private Long user_appointment_id;
    @ApiModelProperty(value="统计时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date statistics_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    public Long getPay_deposit_id() {
        return pay_deposit_id;
    }

    public void setPay_deposit_id(Long pay_deposit_id) {
        this.pay_deposit_id = pay_deposit_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @ApiModelProperty(value="券id",example="1")
    private Long coupon_id;

    @ApiModelProperty(value="券ids",example="1")
    private String coupon_ids;

    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    @ApiModelProperty(value="记录用户观看录播当前页标题",example="1")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ApiModelProperty(value="外教id",example="1")
    private Long master_id;

    @ApiModelProperty(value = "nickNameIDs id集合",example = "1")
    private List<Long> nickNameIDs;

    @ApiModelProperty(value = "昵称",example = "1")
    private String nick_name;

    @ApiModelProperty(value = "code",example = "1")
    private String code;

    @ApiModelProperty(value = "grantType",example = "1")
    private String grantType;

    @ApiModelProperty(value = "临时授权码",example = "1")
    private String auth_code;

    @ApiModelProperty(value = "刷新令牌",example = "1")
    private String refresh_token;

    @ApiModelProperty(value = "是否体验课",example = "1")
    private Boolean is_experience;

    @ApiModelProperty(value = "是否一次性付",example = "1")
    private Boolean is_one_time_payment;

    @ApiModelProperty(value = "线下绑定机构id")
    private Long binding_mechanism_id;

    @ApiModelProperty(value = "机构收款帐号")
    private String payee_logon_id;

    @ApiModelProperty(value = "单节课一次性支付身份  mechanism 机构 user 用户")
    private String paying_for_identity;

    @ApiModelProperty(value = "支付宝返回预支付user_id")
    private String payer_userid;

    @ApiModelProperty(value = "购买学习卡id")
    private Long user_study_card_id;

    @ApiModelProperty(value = "是否转长单")
    private Boolean is_turning_long_orders;

    @ApiModelProperty(value = "使用积分数")
    private Integer points;

    @ApiModelProperty(value = "支付方式")
    private String pay_type;

    @ApiModelProperty(value = "是否免费")
    private Boolean is_free;

    @ApiModelProperty(value = "生产随机号")
    private String out_request_no;

    @ApiModelProperty(value = "支付宝资金订单号")
    private String auth_no;

    @ApiModelProperty(value = "支付宝操作流水号")
    private String operation_id;

    @ApiModelProperty(value = "用户拼团id")
    private String user_group_id;

    @ApiModelProperty(value = "机构名")
    private String mechanism_name;

    @ApiModelProperty(value = "商品名")
    private String commodities_name;

    @ApiModelProperty(value = "老师邀请码")
    private String invite_code;

    @ApiModelProperty(value = "活动id")
    private Long activity_id;

    @ApiModelProperty(value = "直播id")
    private Long live_streaming_id;

    @ApiModelProperty(value = "当前课节数")
    private Integer number_of_lessons;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getOnLineCount() {
        return onLineCount;
    }

    public void setOnLineCount(Integer onLineCount) {
        this.onLineCount = onLineCount;
    }

    public Boolean getIs_robot() {
        return is_robot;
    }

    public void setIs_robot(Boolean is_robot) {
        this.is_robot = is_robot;
    }

    public String getPay_pass() {
        return pay_pass;
    }

    public void setPay_pass(String pay_pass) {
        this.pay_pass = pay_pass;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public List<Long> getLoginIDs() {
        return loginIDs;
    }

    public void setLoginIDs(List<Long> loginIDs) {
        this.loginIDs = loginIDs;
    }

    public String getSelected_id() {
        return selected_id;
    }

    public void setSelected_id(String selected_id) {
        this.selected_id = selected_id;
    }

    public Boolean getIs_settlement() {
        return is_settlement;
    }

    public void setIs_settlement(Boolean is_settlement) {
        this.is_settlement = is_settlement;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getCoupon_ids() {
        return coupon_ids;
    }

    public void setCoupon_ids(String coupon_ids) {
        this.coupon_ids = coupon_ids;
    }

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public Long getLive_streaming_id() {
        return live_streaming_id;
    }

    public void setLive_streaming_id(Long live_streaming_id) {
        this.live_streaming_id = live_streaming_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumber_of_lessons() {
        return number_of_lessons;
    }

    public void setNumber_of_lessons(Integer number_of_lessons) {
        this.number_of_lessons = number_of_lessons;
    }

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    public String getCommodities_name() {
        return commodities_name;
    }

    public void setCommodities_name(String commodities_name) {
        this.commodities_name = commodities_name;
    }

    public String getMechanism_name() {
        return mechanism_name;
    }

    public void setMechanism_name(String mechanism_name) {
        this.mechanism_name = mechanism_name;
    }

    public String getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(String user_group_id) {
        this.user_group_id = user_group_id;
    }

    public String getAuth_no() {
        return auth_no;
    }

    public void setAuth_no(String auth_no) {
        this.auth_no = auth_no;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public Boolean getIs_free() {
        return is_free;
    }

    public void setIs_free(Boolean is_free) {
        this.is_free = is_free;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getIs_turning_long_orders() {
        return is_turning_long_orders;
    }

    public void setIs_turning_long_orders(Boolean is_turning_long_orders) {
        this.is_turning_long_orders = is_turning_long_orders;
    }

    public Long getUser_study_card_id() {
        return user_study_card_id;
    }

    public void setUser_study_card_id(Long user_study_card_id) {
        this.user_study_card_id = user_study_card_id;
    }

    public String getPayer_userid() {
        return payer_userid;
    }

    public void setPayer_userid(String payer_userid) {
        this.payer_userid = payer_userid;
    }

    public Long getUser_appointment_id() {
        return user_appointment_id;
    }

    public void setUser_appointment_id(Long user_appointment_id) {
        this.user_appointment_id = user_appointment_id;
    }

    public String getPaying_for_identity() {
        return paying_for_identity;
    }

    public void setPaying_for_identity(String paying_for_identity) {
        this.paying_for_identity = paying_for_identity;
    }

    public String getPayee_logon_id() {
        return payee_logon_id;
    }

    public void setPayee_logon_id(String payee_logon_id) {
        this.payee_logon_id = payee_logon_id;
    }

    public Long getBinding_mechanism_id() {
        return binding_mechanism_id;
    }

    public void setBinding_mechanism_id(Long binding_mechanism_id) {
        this.binding_mechanism_id = binding_mechanism_id;
    }

    public Boolean getIs_experience() {
        return is_experience;
    }

    public void setIs_experience(Boolean is_experience) {
        this.is_experience = is_experience;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public List<Long> getNickNameIDs() {
        return nickNameIDs;
    }

    public void setNickNameIDs(List<Long> nickNameIDs) {
        this.nickNameIDs = nickNameIDs;
    }

    public Long getMaster_id() {
        return master_id;
    }

    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    public Boolean getIs_one_time_payment() {
        return is_one_time_payment;
    }

    public void setIs_one_time_payment(Boolean is_one_time_payment) {
        this.is_one_time_payment = is_one_time_payment;
    }

    public Long getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Long coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Date getStatistics_time() {
        return statistics_time;
    }

    public void setStatistics_time(Date statistics_time) {
        this.statistics_time = statistics_time;
    }

    public Long getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
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
     * 充值描述
     * @return rcharge_abstract 充值描述
     */
    public String getRcharge_abstract() {
        return rcharge_abstract;
    }

    /**
     * 充值描述
     * @param rcharge_abstract 充值描述
     */
    public void setRcharge_abstract(String rcharge_abstract) {
        this.rcharge_abstract = rcharge_abstract == null ? null : rcharge_abstract.trim();
    }

    /**
     * 充值用户
     * @return rcharge_account 充值用户
     */
    public String getRcharge_account() {
        return rcharge_account;
    }

    /**
     * 充值用户
     * @param rcharge_account 充值用户
     */
    public void setRcharge_account(String rcharge_account) {
        this.rcharge_account = rcharge_account == null ? null : rcharge_account.trim();
    }

    /**
     * 充值来源
     * @return source 充值来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 充值来源
     * @param source 充值来源
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 充值金额
     * @return amount 充值金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 充值金额
     * @param amount 充值金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 充值内容的加密信息，用于验证
     * @return rcharge_valid 充值内容的加密信息，用于验证
     */
    public String getRcharge_valid() {
        return rcharge_valid;
    }

    /**
     * 充值内容的加密信息，用于验证
     * @param rcharge_valid 充值内容的加密信息，用于验证
     */
    public void setRcharge_valid(String rcharge_valid) {
        this.rcharge_valid = rcharge_valid == null ? null : rcharge_valid.trim();
    }

    /**
     * 充值时间
     * @return rcharge_time 充值时间
     */
    public Date getRcharge_time() {
        return rcharge_time;
    }

    /**
     * 充值时间
     * @param rcharge_time 充值时间
     */
    public void setRcharge_time(Date rcharge_time) {
        this.rcharge_time = rcharge_time;
    }

    /**
     * 订单编号
     * @return flowing_no 订单编号
     */
    public String getFlowing_no() {
        return flowing_no;
    }

    /**
     * 订单编号
     * @param flowing_no 订单编号
     */
    public void setFlowing_no(String flowing_no) {
        this.flowing_no = flowing_no == null ? null : flowing_no.trim();
    }

    /**
     * 账户号
     * @return trans_no 账户号
     */
    public String getTrans_no() {
        return trans_no;
    }

    /**
     * 账户号
     * @param trans_no 账户号
     */
    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no == null ? null : trans_no.trim();
    }

    /**
     * 充值成功时间
     * @return finished_time 充值成功时间
     */
    public Date getFinished_time() {
        return finished_time;
    }

    /**
     * 充值成功时间
     * @param finished_time 充值成功时间
     */
    public void setFinished_time(Date finished_time) {
        this.finished_time = finished_time;
    }

    /**
     * member 会员充值  authentication 认证充值  account  账户充值 master提现  study_card 学习卡购买  class_card直播课程学习卡
     * @return rcharge_type member 会员充值  authentication 认证充值  account  账户充值 master提现  study_card 学习卡购买  class_card直播课程学习卡
     */
    public String getRcharge_type() {
        return rcharge_type;
    }

    /**
     * member 会员充值  authentication 认证充值  account  账户充值 master提现  study_card 学习卡购买  class_card直播课程学习卡
     * @param rcharge_type member 会员充值  authentication 认证充值  account  账户充值 master提现  study_card 学习卡购买  class_card直播课程学习卡
     */
    public void setRcharge_type(String rcharge_type) {
        this.rcharge_type = rcharge_type == null ? null : rcharge_type.trim();
    }

    /**
     * 是否充值成功  true 成功  false 是吧
     * @return finished 是否充值成功  true 成功  false 是吧
     */
    public Boolean getFinished() {
        return finished;
    }

    /**
     * 是否充值成功  true 成功  false 是吧
     * @param finished 是否充值成功  true 成功  false 是吧
     */
    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    /**
     * 订单状态1创建订单状态 2 付款成功 3 付款失败
     * @return status 订单状态1创建订单状态 2 付款成功 3 付款失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 订单状态1创建订单状态 2 付款成功 3 付款失败
     * @param status 订单状态1创建订单状态 2 付款成功 3 付款失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 会员等级
     * @return member_level 会员等级
     */
    public Integer getMember_level() {
        return member_level;
    }

    /**
     * 会员等级
     * @param member_level 会员等级
     */
    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
    }

    /**
     * true 充值  false 提现
     * @return account true 充值  false 提现
     */
    public Boolean getAccount() {
        return account;
    }

    /**
     * true 充值  false 提现
     * @param account true 充值  false 提现
     */
    public void setAccount(Boolean account) {
        this.account = account;
    }

    /**
     * ios订单效验唯一标识
     * @return receipt_data ios订单效验唯一标识
     */
    public String getReceipt_data() {
        return receipt_data;
    }

    /**
     * ios订单效验唯一标识
     * @param receipt_data ios订单效验唯一标识
     */
    public void setReceipt_data(String receipt_data) {
        this.receipt_data = receipt_data == null ? null : receipt_data.trim();
    }

    /**
     * 充值会员时长(月)
     * @return member_duration 充值会员时长(月)
     */
    public Integer getMember_duration() {
        return member_duration;
    }

    /**
     * 充值会员时长(月)
     * @param member_duration 充值会员时长(月)
     */
    public void setMember_duration(Integer member_duration) {
        this.member_duration = member_duration;
    }

    /**
     * 学习课类型
     * @return study_type 学习课类型
     */
    public String getStudy_type() {
        return study_type;
    }

    /**
     * 学习课类型
     * @param study_type 学习课类型
     */
    public void setStudy_type(String study_type) {
        this.study_type = study_type == null ? null : study_type.trim();
    }

    /**
     * 课数
     * @return course_num 课数
     */
    public Integer getCourse_num() {
        return course_num;
    }

    /**
     * 课数
     * @param course_num 课数
     */
    public void setCourse_num(Integer course_num) {
        this.course_num = course_num;
    }

    /**
     * 学习卡id
     * @return studycard_id 学习卡id
     */
    public Long getStudycard_id() {
        return studycard_id;
    }

    /**
     * 学习卡id
     * @param studycard_id 学习卡id
     */
    public void setStudycard_id(Long studycard_id) {
        this.studycard_id = studycard_id;
    }

    /**
     * 直播课程学习卡id
     * @return class_card_id 直播课程学习卡id
     */
    public Long getClass_card_id() {
        return class_card_id;
    }

    /**
     * 直播课程学习卡id
     * @param class_card_id 直播课程学习卡id
     */
    public void setClass_card_id(Long class_card_id) {
        this.class_card_id = class_card_id;
    }
}