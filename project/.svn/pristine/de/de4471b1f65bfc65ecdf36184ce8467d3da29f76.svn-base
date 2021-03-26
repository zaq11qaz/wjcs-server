package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="可领劵清单列表",description="可领劵清单列表属性说明")
public class CouponListEntity extends PageInfo {
    private static final long serialVersionUID = -888909759001789211L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 劵id
     */
    @ApiModelProperty(value="劵id",example="1")
    private Long coupon_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 态状1被领取 2 未领取  3 被查看
     */
    @ApiModelProperty(value="态状1被领取 2 未领取  3 被查看",example="1")
    private Integer status;

    /**
     * 劵码
     */
    @ApiModelProperty(value="劵码")
    private String coupon_code;

    /**
     * 过期时间
     */
    @ApiModelProperty(value="过期时间")
    private Date overdue_time;

    /**
     * 劵类型
     */
    @ApiModelProperty(value="劵类型")
    private String coupon_type;

    @ApiModelProperty(value = "用户id",example = "1")
    private Long user_id;

    @ApiModelProperty(value = "课程id",example = "1")
    private Long appointment_id;

    @ApiModelProperty(value = "课程id",example = "1")
    private Long get_code_user_id;

    @ApiModelProperty(value = "这个码id",example = "1")
    private Long coupon_list_id;

    /**
     * 劵的时长/日
     */
    @ApiModelProperty(value="劵的时长/日",example="1")
    private Long duration;

    @ApiModelProperty(value="map",example="1")
    private Map map;

    @ApiModelProperty(value="ids",example="1")
    private Integer[] ids;

    @ApiModelProperty(value = "名",example = "1")
    private String full_name;

    @ApiModelProperty(value = "fullName id集合",example = "1")
    private List name_ids;

    @ApiModelProperty(value = "liginName id集合",example = "1")
    private List loginIDs;

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

    public List getLoginIDs() {
        return loginIDs;
    }

    public void setLoginIDs(List loginIDs) {
        this.loginIDs = loginIDs;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List getName_ids() {
        return name_ids;
    }

    public void setName_ids(List name_ids) {
        this.name_ids = name_ids;
    }

    public String getLogin_name() {
        return Login_name;
    }

    public void setLogin_name(String login_name) {
        Login_name = login_name;
    }

    @ApiModelProperty(value = "帐号",example = "1")
    private String Login_name;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getCoupon_list_id() {
        return coupon_list_id;
    }

    public void setCoupon_list_id(Long coupon_list_id) {
        this.coupon_list_id = coupon_list_id;
    }

    public Long getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    public Long getGet_code_user_id() {
        return get_code_user_id;
    }

    public void setGet_code_user_id(Long get_code_user_id) {
        this.get_code_user_id = get_code_user_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
     * 态状1被领取 2 未领取  3 被查看
     * @return status 态状1被领取 2 未领取  3 被查看
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 态状1被领取 2 未领取  3 被查看
     * @param status 态状1被领取 2 未领取  3 被查看
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 劵码
     * @return coupon_code 劵码
     */
    public String getCoupon_code() {
        return coupon_code;
    }

    /**
     * 劵码
     * @param coupon_code 劵码
     */
    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code == null ? null : coupon_code.trim();
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
     * 劵类型
     * @return coupon_type 劵类型
     */
    public String getCoupon_type() {
        return coupon_type;
    }

    /**
     * 劵类型
     * @param coupon_type 劵类型
     */
    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type == null ? null : coupon_type.trim();
    }
}