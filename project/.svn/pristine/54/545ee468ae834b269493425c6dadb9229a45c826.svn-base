package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@ApiModel(value="优惠、商品劵列表",description="优惠、商品劵列表属性说明")
public class CommodityCouponEntity extends PageInfo {
    private static final long serialVersionUID = -2667718090666272779L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 劵类型
     */
    @ApiModelProperty(value="劵类型")
    private String type;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date creat_time;

    /**
     * 1失效,2可用
     */
    @ApiModelProperty(value="1失效,2可用",example="1")
    private Integer status;

    /**
     * 折扣
     */
    @ApiModelProperty(value="折扣",example="1")
    private BigDecimal discount;

    /**
     * 现金卷的劵值
     */
    @ApiModelProperty(value="现金卷的劵值",example="1")
    private BigDecimal cash;

    /**
     * 劵的时长/日
     */
    @ApiModelProperty(value="劵的时长/日",example="1")
    private Long duration;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Long activity_id;

    /**
     * 满减(满额)
     */
    @ApiModelProperty(value="满减(满额)",example="1")
    private BigDecimal full_amount;

    /**
     * 开始领取时间
     */
    @ApiModelProperty(value="开始领取时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_time;

    /**
     * 失效时间
     */
    @ApiModelProperty(value="失效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_time;

    /**
     * 课节数
     */
    @ApiModelProperty(value="课节数",example="1")
    private Integer course_num;

    @ApiModelProperty(value = "用户id",example = "1")
    private Long user_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @ApiModelProperty(value = "生成券的数量",example = "1")
    private int number;

    /**
     * 劵码
     */
    @ApiModelProperty(value="劵码",example = "1")
    private String coupon_code;

    @ApiModelProperty(value="来源",example = "1")
    private String platform;

    @ApiModelProperty(value="会员等级",example = "1")
    private String member_level;

    @ApiModelProperty(value="描述",example = "1")
    private String descr;



    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMember_level() {
        return member_level;
    }

    public void setMember_level(String member_level) {
        this.member_level = member_level;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
     * 劵类型
     * @return type 劵类型
     */
    public String getType() {
        return type;
    }

    /**
     * 劵类型
     * @param type 劵类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 创建时间
     * @return creat_time 创建时间
     */
    public Date getCreat_time() {
        return creat_time;
    }

    /**
     * 创建时间
     * @param creat_time 创建时间
     */
    public void setCreat_time(Date creat_time) {
        this.creat_time = creat_time;
    }

    /**
     * 1失效,2可用
     * @return status 1失效,2可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1失效,2可用
     * @param status 1失效,2可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 折扣
     * @return discount 折扣
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 折扣
     * @param discount 折扣
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 现金卷的劵值
     * @return cash 现金卷的劵值
     */
    public BigDecimal getCash() {
        return cash;
    }

    /**
     * 现金卷的劵值
     * @param cash 现金卷的劵值
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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
     * 活动id
     * @return activity_id 活动id
     */
    public Long getActivity_id() {
        return activity_id;
    }

    /**
     * 活动id
     * @param activity_id 活动id
     */
    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    /**
     * 满减(满额)
     * @return full_amount 满减(满额)
     */
    public BigDecimal getFull_amount() {
        return full_amount;
    }

    /**
     * 满减(满额)
     * @param full_amount 满减(满额)
     */
    public void setFull_amount(BigDecimal full_amount) {
        this.full_amount = full_amount;
    }

    /**
     * 开始领取时间
     * @return start_time 开始领取时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 开始领取时间
     * @param start_time 开始领取时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 失效时间
     * @return end_time 失效时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 失效时间
     * @param end_time 失效时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
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