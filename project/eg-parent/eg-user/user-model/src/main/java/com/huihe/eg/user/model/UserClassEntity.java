package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value="用户直播课程订阅记录",description="用户直播课程订阅记录属性说明")
public class UserClassEntity extends PageInfo {
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
     * 助学师的user_id
     */
    @ApiModelProperty(value="助学师的user_id",example="1")
    private Long master_id;

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
     * 课节id
     */
    @ApiModelProperty(value="课节id",example="1")
    private Long appointment_id;

    /**
     * 状态 1 取消 2 预定 3 课程完成  4 修改中 5 取消中
     */
    @ApiModelProperty(value="状态 1 取消 2 预定 ",example="1")
    private Integer status;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 与GMT的偏移量
     */
    @ApiModelProperty(value="与GMT的偏移量",example="1")
    private BigDecimal offset;

    /**
     * 时区id
     */
    @ApiModelProperty(value="时区id",example="1")
    private Long timezone_id;

    /**
     * 将要修改成的课程id
     */
    @ApiModelProperty(value="将要修改成的课程id",example="1")
    private Long update_appointment_id;

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    @ApiModelProperty(value="助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教")
    private String class_type;

    /**
     * 语言
     */
    @ApiModelProperty(value="语言")
    private String language;

    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    /**
     * 课堂类型
     */
    @ApiModelProperty(value="课堂类型")
    private String classroom_type;

    public String getClassroom_type() {
        return classroom_type;
    }

    public void setClassroom_type(String classroom_type) {
        this.classroom_type = classroom_type;
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
     * 助学师的user_id
     * @return master_id 助学师的user_id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 助学师的user_id
     * @param master_id 助学师的user_id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
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
     * 课节id
     * @return appointment_id 课节id
     */
    public Long getAppointment_id() {
        return appointment_id;
    }

    /**
     * 课节id
     * @param appointment_id 课节id
     */
    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * 状态 1 取消 2 预定 3 课程完成  4 修改中 5 取消中
     * @return status 状态 1 取消 2 预定 3 课程完成  4 修改中 5 取消中
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 取消 2 预定 3 课程完成  4 修改中 5 取消中
     * @param status 状态 1 取消 2 预定 3 课程完成  4 修改中 5 取消中
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 开始时间
     * @return start_time 开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 开始时间
     * @param start_time 开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
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
     * 标题
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 与GMT的偏移量
     * @return offset 与GMT的偏移量
     */
    public BigDecimal getOffset() {
        return offset;
    }

    /**
     * 与GMT的偏移量
     * @param offset 与GMT的偏移量
     */
    public void setOffset(BigDecimal offset) {
        this.offset = offset;
    }

    /**
     * 时区id
     * @return timezone_id 时区id
     */
    public Long getTimezone_id() {
        return timezone_id;
    }

    /**
     * 时区id
     * @param timezone_id 时区id
     */
    public void setTimezone_id(Long timezone_id) {
        this.timezone_id = timezone_id;
    }

    /**
     * 将要修改成的课程id
     * @return update_appointment_id 将要修改成的课程id
     */
    public Long getUpdate_appointment_id() {
        return update_appointment_id;
    }

    /**
     * 将要修改成的课程id
     * @param update_appointment_id 将要修改成的课程id
     */
    public void setUpdate_appointment_id(Long update_appointment_id) {
        this.update_appointment_id = update_appointment_id;
    }

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @return class_type 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public String getClass_type() {
        return class_type;
    }

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @param class_type 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public void setClass_type(String class_type) {
        this.class_type = class_type == null ? null : class_type.trim();
    }

    /**
     * 语言
     * @return language 语言
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 语言
     * @param language 语言
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }
}