package com.huihe.eg.push.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="",description="属性说明")
public class PushHistoryEntity extends PageInfo {
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 大类型
     */
    @ApiModelProperty(value="大类型")
    private String large_type;

    /**
     * 小类型
     */
    @ApiModelProperty(value="小类型")
    private String opera_type;

    /**
     * 发起人 user/master pic/video
     */
    @ApiModelProperty(value="发起人 user/master pic/video")
    private String view_type;

    /**
     * 相关主键id
     */
    @ApiModelProperty(value="相关主键id",example="1")
    private Integer type_id;

    /**
     * 发送人id 0系统
     */
    @ApiModelProperty(value="发送人id 0系统",example="1")
    private Integer send_id;

    /**
     * auth 课程消息 event 事件消息 dynamic 动态消息 system 系统消息 大类型
     */
    @ApiModelProperty(value="auth 课程消息 event 事件消息 dynamic 动态消息 system 系统消息 大类型")
    private String type;

    /**
     * 目标id
     */
    @ApiModelProperty(value="目标id",example="1")
    private Long target_id;

    /**
     * 用户状态 1 等待处理 2 看过
     */
    @ApiModelProperty(value="用户状态 1 等待处理 2 看过",example="1")
    private Integer user_status;

    /**
     * 外教状态 1 等待处理 2 看过
     */
    @ApiModelProperty(value="外教状态 1 等待处理 2 看过",example="1")
    private Integer master_status;

    /**
     * 通知标题
     */
    @ApiModelProperty(value="通知标题")
    private String title;

    /**
     * 通知内容
     */
    @ApiModelProperty(value="通知内容")
    private String context;

    /**
     * 用户id
     * @return id 用户id
     */
    public Long getId() {
        return id;
    }

    /**
     * 用户id
     * @param id 用户id
     */
    public void setId(Long id) {
        this.id = id;
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
     * 大类型
     * @return large_type 大类型
     */
    public String getLarge_type() {
        return large_type;
    }

    /**
     * 大类型
     * @param large_type 大类型
     */
    public void setLarge_type(String large_type) {
        this.large_type = large_type == null ? null : large_type.trim();
    }

    /**
     * 小类型
     * @return opera_type 小类型
     */
    public String getOpera_type() {
        return opera_type;
    }

    /**
     * 小类型
     * @param opera_type 小类型
     */
    public void setOpera_type(String opera_type) {
        this.opera_type = opera_type == null ? null : opera_type.trim();
    }

    /**
     * 发起人 user/master pic/video
     * @return view_type 发起人 user/master pic/video
     */
    public String getView_type() {
        return view_type;
    }

    /**
     * 发起人 user/master pic/video
     * @param view_type 发起人 user/master pic/video
     */
    public void setView_type(String view_type) {
        this.view_type = view_type == null ? null : view_type.trim();
    }

    /**
     * 相关主键id
     * @return type_id 相关主键id
     */
    public Integer getType_id() {
        return type_id;
    }

    /**
     * 相关主键id
     * @param type_id 相关主键id
     */
    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    /**
     * 发送人id 0系统
     * @return send_id 发送人id 0系统
     */
    public Integer getSend_id() {
        return send_id;
    }

    /**
     * 发送人id 0系统
     * @param send_id 发送人id 0系统
     */
    public void setSend_id(Integer send_id) {
        this.send_id = send_id;
    }

    /**
     * auth 课程消息 event 事件消息 dynamic 动态消息 system 系统消息 大类型
     * @return type auth 课程消息 event 事件消息 dynamic 动态消息 system 系统消息 大类型
     */
    public String getType() {
        return type;
    }

    /**
     * auth 课程消息 event 事件消息 dynamic 动态消息 system 系统消息 大类型
     * @param type auth 课程消息 event 事件消息 dynamic 动态消息 system 系统消息 大类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 目标id
     * @return target_id 目标id
     */
    public Long getTarget_id() {
        return target_id;
    }

    /**
     * 目标id
     * @param target_id 目标id
     */
    public void setTarget_id(Long target_id) {
        this.target_id = target_id;
    }

    /**
     * 用户状态 1 等待处理 2 看过
     * @return user_status 用户状态 1 等待处理 2 看过
     */
    public Integer getUser_status() {
        return user_status;
    }

    /**
     * 用户状态 1 等待处理 2 看过
     * @param user_status 用户状态 1 等待处理 2 看过
     */
    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    /**
     * 外教状态 1 等待处理 2 看过
     * @return master_status 外教状态 1 等待处理 2 看过
     */
    public Integer getMaster_status() {
        return master_status;
    }

    /**
     * 外教状态 1 等待处理 2 看过
     * @param master_status 外教状态 1 等待处理 2 看过
     */
    public void setMaster_status(Integer master_status) {
        this.master_status = master_status;
    }

    /**
     * 通知标题
     * @return title 通知标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 通知标题
     * @param title 通知标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 通知内容
     * @return context 通知内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 通知内容
     * @param context 通知内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }
}