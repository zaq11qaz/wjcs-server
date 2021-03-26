package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="视频使用记录",description="视频使用记录属性说明")
public class VideoDurationEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 视频发起的用户id
     */
    @ApiModelProperty(value="视频发起的用户id",example="1")
    private Long user_id;

    /**
     * 时长/s
     */
    @ApiModelProperty(value="时长/s",example="1")
    private Integer duration;

    /**
     * 类型 class 上课  video 普通聊天
     */
    @ApiModelProperty(value="类型 live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看  " +
            "class 上课  video 普通视视频,recreation_connection 生活娱乐直播连麦 recreation_broadcast  生活娱乐直播观看")
    private String type;

    /**
     * 来源 ios android
     */
    @ApiModelProperty(value="来源 ios android")
    private String source;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date create_time;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 状态 1 未结束 2 已结束  3未接通
     */
    @ApiModelProperty(value="状态 1 未结束 2 已结束  3未接通",example="1")
    private Integer status;

    /**
     * 腾讯云房间号
     */
    @ApiModelProperty(value="腾讯云房间号")
    private String room_number;

    /**
     * 对方的用户id
     */
    @ApiModelProperty(value="对方的用户id",example="1")
    private Long other_id;

    /**
     * 课id
     */
    @ApiModelProperty(value="课id",example="1")
    private Long appointment_id;

    /**
     * 订单id
     */
    @ApiModelProperty(value="订单id/订单/记录id",example="1")
    private Long order_id;

    @ApiModelProperty(value = "是否助学师授课",example = "false")
    private Boolean is_master;

    @ApiModelProperty(value = "所剩时间",example = "1")
    private Integer surplus_duration;

    @ApiModelProperty("学习卡类型")
    private String  card_type;

    @ApiModelProperty("是否老师")
    private Boolean is_teacher;

    public Boolean getIs_teacher() {
        return is_teacher;
    }

    public void setIs_teacher(Boolean is_teacher) {
        this.is_teacher = is_teacher;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public Integer getSurplus_duration() {
        return surplus_duration;
    }

    public void setSurplus_duration(Integer surplus_duration) {
        this.surplus_duration = surplus_duration;
    }

    public Boolean getIs_master() {
        return is_master;
    }

    public void setIs_master(Boolean is_master) {
        this.is_master = is_master;
    }
    @ApiModelProperty("体验卷")
    private String experience_volume;

    public String getExperience_volume() {
        return experience_volume;
    }

    public void setExperience_volume(String experience_volume) {
        this.experience_volume = experience_volume;
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
     * 视频发起的用户id
     * @return user_id 视频发起的用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 视频发起的用户id
     * @param user_id 视频发起的用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 时长/s
     * @return duration 时长/s
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * 时长/s
     * @param duration 时长/s
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * 类型 class 上课  video 普通聊天
     * @return type 类型 class 上课  video 普通聊天
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 class 上课  video 普通聊天
     * @param type 类型 class 上课  video 普通聊天
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 来源 ios android
     * @return source 来源 ios android
     */
    public String getSource() {
        return source;
    }

    /**
     * 来源 ios android
     * @param source 来源 ios android
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 开始时间
     * @return create_time 开始时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 开始时间
     * @param create_time 开始时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 结束时间
     * @return end_time 结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 结束时间
     * @param end_time 结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 状态 1 未结束 2 已结束  3未接通
     * @return status 状态 1 未结束 2 已结束  3未接通
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 未结束 2 已结束  3未接通
     * @param status 状态 1 未结束 2 已结束  3未接通
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 腾讯云房间号
     * @return room_number 腾讯云房间号
     */
    public String getRoom_number() {
        return room_number;
    }

    /**
     * 腾讯云房间号
     * @param room_number 腾讯云房间号
     */
    public void setRoom_number(String room_number) {
        this.room_number = room_number == null ? null : room_number.trim();
    }

    /**
     * 对方的用户id
     * @return other_id 对方的用户id
     */
    public Long getOther_id() {
        return other_id;
    }

    /**
     * 对方的用户id
     * @param other_id 对方的用户id
     */
    public void setOther_id(Long other_id) {
        this.other_id = other_id;
    }

    /**
     * 课id
     * @return appointment_id 课id
     */
    public Long getAppointment_id() {
        return appointment_id;
    }

    /**
     * 课id
     * @param appointment_id 课id
     */
    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * 订单id
     * @return order_id 订单id
     */
    public Long getOrder_id() {
        return order_id;
    }

    /**
     * 订单id
     * @param order_id 订单id
     */
    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
}