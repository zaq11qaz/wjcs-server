package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="消息中心",description="消息中心属性说明")
public class MasterNoticeEntity extends PageInfo {
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
     * 对方用户id
     */
    @ApiModelProperty(value="对方用户id",example="1")
    private Long master_id;

    /**
     * 课节id
     */
    @ApiModelProperty(value="课节id",example="1")
    private Long appointment_id;

    /**
     * 状态 1  等待处理   2 已处理
     */
    @ApiModelProperty(value="状态 1  等待处理   2 已处理",example="1")
    private Integer status;

    /**
     * 类别  curriculumcancel课程取消   curriculumupdate课程修改  curriculum_appointment课程预约
     */
    @ApiModelProperty(value="类别  curriculumcancel课程取消   curriculumupdate课程修改  curriculumappointment课程预约  ")
    private String type;

    /**
     * 处理结果 1 同意  2拒绝 3等待处理
     */
    @ApiModelProperty(value="处理结果 1 同意  2拒绝 3等待处理",example="1")
    private Integer handle;

    /**
     * 身份  user学生  master老师
     */
    @ApiModelProperty(value="身份  user学生  master老师")
    private String identity;

    /**
     * 已读类型   masterread 需要助学师拉取,   userread  需要学生拉取
     */
    @ApiModelProperty(value="已读类型   masterread 需要助学师拉取,   userread  需要学生拉取")
    private String read_type;

    /**
     * 服务类型 线上:online/线下offline
     */
    @ApiModelProperty(value="服务类型 线上:online/线下offline")
    private String service_type;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 对方用户id
     * @return master_id 对方用户id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 对方用户id
     * @param master_id 对方用户id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
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
     * 状态 1  等待处理   2 已处理
     * @return status 状态 1  等待处理   2 已处理
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1  等待处理   2 已处理
     * @param status 状态 1  等待处理   2 已处理
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类别  curriculumcancel课程取消   curriculumupdate课程修改  curriculumappointment课程预约
     * @return type 类别  curriculumcancel课程取消   curriculumupdate课程修改  curriculumappointment课程预约
     */
    public String getType() {
        return type;
    }

    /**
     * 类别  curriculumcancel课程取消   curriculumupdate课程修改  curriculumappointment课程预约
     * @param type 类别  curriculumcancel课程取消   curriculumupdate课程修改  curriculumappointment课程预约
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 处理结果 1 同意  2拒绝 3等待处理
     * @return handle 处理结果 1 同意  2拒绝 3等待处理
     */
    public Integer getHandle() {
        return handle;
    }

    /**
     * 处理结果 1 同意  2拒绝 3等待处理
     * @param handle 处理结果 1 同意  2拒绝 3等待处理
     */
    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    /**
     * 身份  user学生  master老师
     * @return identity 身份  user学生  master老师
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 身份  user学生  master老师
     * @param identity 身份  user学生  master老师
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * 已读类型   masterread 需要助学师拉取,   userread  需要学生拉取
     * @return read_type 已读类型   masterread 需要助学师拉取,   userread  需要学生拉取
     */
    public String getRead_type() {
        return read_type;
    }

    /**
     * 已读类型   masterread 需要助学师拉取,   userread  需要学生拉取
     * @param read_type 已读类型   masterread 需要助学师拉取,   userread  需要学生拉取
     */
    public void setRead_type(String read_type) {
        this.read_type = read_type == null ? null : read_type.trim();
    }

    /**
     * 服务类型 线上:online/线下offline
     * @return service_type 服务类型 线上:online/线下offline
     */
    public String getService_type() {
        return service_type;
    }

    /**
     * 服务类型 线上:online/线下offline
     * @param service_type 服务类型 线上:online/线下offline
     */
    public void setService_type(String service_type) {
        this.service_type = service_type == null ? null : service_type.trim();
    }

    /**
     * 机构id
     * @return mechanism_id 机构id
     */
    public Long getMechanism_id() {
        return mechanism_id;
    }

    /**
     * 机构id
     * @param mechanism_id 机构id
     */
    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }
}