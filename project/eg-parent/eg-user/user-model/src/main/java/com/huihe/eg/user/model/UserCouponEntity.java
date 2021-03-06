package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="用户优惠劵领取记录",description="用户优惠劵领取记录属性说明")
public class UserCouponEntity extends PageInfo {
    private static final long serialVersionUID = 8937989587779663847L;
    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 劵id
     */
    @ApiModelProperty(value="商品劵id",example="1")
    private Long coupon_id;

    /**
     * 创建时间,领取时间
     */
    @ApiModelProperty(value="创建时间,领取时间")
    private Date create_time;

    /**
     * 1可用2 失效 3已使用
     */
    @ApiModelProperty(value="1可用2 失效 3已使用",example="1")
    private Integer status;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 类型
     */
    @ApiModelProperty(value="选中ids")
    private String ids;

    /**
     * 类型
     */
    @ApiModelProperty(value="选中商品ids")
    private String master_set_price_ids;

    /**
     * 二维码
     */
    @ApiModelProperty(value="二维码")
    private String qrcode;

    /**
     * 过期时间
     */
    @ApiModelProperty(value="过期时间")
    private Date overdue_time;

    /**
     * 课节数
     */
    @ApiModelProperty(value="课节数",example="1")
    private Integer course_num;

    @ApiModelProperty(value="兑换码",example="1")
    private String coupon_code;

    @ApiModelProperty(value="集合",example="1")
    private Map<String,Object> map;

    @ApiModelProperty(value="券id",example="1")
    private Long coupon_list_id;

    @ApiModelProperty(value = "课堂id",example = "1")
    private Long appointment_id;

    @ApiModelProperty(value = "订单id",example = "1")
    private Long recharge_id;

    @ApiModelProperty(value = "名",example = "1")
    private String full_name;

    @ApiModelProperty(value = "fullName id集合",example = "1")
    private List<Long> name_ids;

    @ApiModelProperty(value = "liginName id集合",example = "1")
    private List<Long> loginIDs;

    @ApiModelProperty(value = "帐号",example = "1")
    private String Login_name;

    /**
     * 选中的id
     */
    @ApiModelProperty(value="选中的id",example = "1")
    private String selected_id;


    @ApiModelProperty(value = "机构id",example = "1")
    private Long mechanism_id;

    @ApiModelProperty(value = "绑定商品id",example = "1")
    private Long master_set_price_id;

    @ApiModelProperty(value = "绑定学习卡id",example = "1")
    private Long user_study_card_id;

    @ApiModelProperty(value = "券名字",example = "1")
    private String coup_name;

    @ApiModelProperty(value = "折扣",example = "1")
    private BigDecimal discount;

    @ApiModelProperty(value = "券值",example = "1")
    private BigDecimal cash;

    @ApiModelProperty(value = "是否教付宝 true 是 false 不是",example = "1")
    private Boolean is_teach_paypal;

    @ApiModelProperty(value = "是否到店 true 是 false 否",example = "1")
    private Boolean is_instore;

    public Boolean getIs_instore() {
        return is_instore;
    }

    public void setIs_instore(Boolean is_instore) {
        this.is_instore = is_instore;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getMaster_set_price_ids() {
        return master_set_price_ids;
    }

    public void setMaster_set_price_ids(String master_set_price_ids) {
        this.master_set_price_ids = master_set_price_ids;
    }

    public String getSelected_id() {
        return selected_id;
    }

    public void setSelected_id(String selected_id) {
        this.selected_id = selected_id;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    public Long getUser_study_card_id() {
        return user_study_card_id;
    }

    public void setUser_study_card_id(Long user_study_card_id) {
        this.user_study_card_id = user_study_card_id;
    }

    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }

    public String getCoup_name() {
        return coup_name;
    }

    public void setCoup_name(String coup_name) {
        this.coup_name = coup_name;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    public String getLogin_name() {
        return Login_name;
    }

    public void setLogin_name(String login_name) {
        Login_name = login_name;
    }

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<Long> getName_ids() {
        return name_ids;
    }

    public void setName_ids(List<Long> name_ids) {
        this.name_ids = name_ids;
    }

    public List<Long> getLoginIDs() {
        return loginIDs;
    }

    public void setLoginIDs(List<Long> loginIDs) {
        this.loginIDs = loginIDs;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Long getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    public Long getRecharge_id() {
        return recharge_id;
    }

    public void setRecharge_id(Long recharge_id) {
        this.recharge_id = recharge_id;
    }

    public Long getCoupon_list_id() {
        return coupon_list_id;
    }

    public void setCoupon_list_id(Long coupon_list_id) {
        this.coupon_list_id = coupon_list_id;
    }

    public Map<String,Object> getMap() {
        return map;
    }

    public void setMap(Map<String,Object> map) {
        this.map = map;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
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
     * 劵id
     * @return coupon_id 劵id
     */
    public Long getCoupon_id() {
        return coupon_id;
    }

    /**
     * 劵id
     * @param coupon_id 劵id
     */
    public void setCoupon_id(Long coupon_id) {
        this.coupon_id = coupon_id;
    }

    /**
     * 创建时间,领取时间
     * @return create_time 创建时间,领取时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间,领取时间
     * @param create_time 创建时间,领取时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 1可用2 失效 3已使用
     * @return status 1可用2 失效 3已使用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1可用2 失效 3已使用
     * @param status 1可用2 失效 3已使用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 过期时间
     * @return overdue_time 过期时间
     */
    public Date getOverdue_time() {
        return overdue_time;
    }

    /**
     * 过期时间
     * @param overdue_time 过期时间
     */
    public void setOverdue_time(Date overdue_time) {
        this.overdue_time = overdue_time;
    }

    /**
     * 课节数
     * @return course_num 课节数
     */
    public Integer getCourse_num() {
        return course_num;
    }

    /**
     * 课节数
     * @param course_num 课节数
     */
    public void setCourse_num(Integer course_num) {
        this.course_num = course_num;
    }
}