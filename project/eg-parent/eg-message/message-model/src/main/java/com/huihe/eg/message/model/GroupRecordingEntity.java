package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="群课堂录制记录",description="群课堂录制记录属性说明")
public class GroupRecordingEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 群课堂id
     */
    @ApiModelProperty(value="群课堂id",example="1")
    private Long group_id;

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
     * 录制视频地址
     */
    @ApiModelProperty(value="录制视频地址")
    private String record_url;

    /**
     * 视频格式
     */
    @ApiModelProperty(value="视频格式")
    private String video_format;

    /**
     * 封面
     */
    @ApiModelProperty(value="封面")
    private String cover;

    /**
     * 群主id
     */
    @ApiModelProperty(value="群主id",example="1")
    private Long user_id;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 课节id
     */
    @ApiModelProperty(value="课节id",example="1")
    private Long appointment_id;

    /**
     * 直播流名称
     */
    @ApiModelProperty(value="直播流名称")
    private String stream_id;

    /**
     * 状态 1未完成  2已完成
     */
    @ApiModelProperty(value="状态 1未完成  2已完成",example="1")
    private Integer status;

    /**
     * 任务ID，全局唯一标识录制任务。
     */
    @ApiModelProperty(value="任务ID，全局唯一标识录制任务。")
    private String task_id;

    /**
     * 是否教付保
     */
    @ApiModelProperty(value="是否教付保")
    private Boolean is_teach_paypal;

    /**
     * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    @ApiModelProperty(value="唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。")
    private String RequestId;
    @ApiModelProperty(value = "map其他")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    /**
     * 被观看次数
     */
    @ApiModelProperty(value="被观看次数",example="1")
    private Integer watch_count;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
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
     * 群课堂id
     * @return group_id 群课堂id
     */
    public Long getGroup_id() {
        return group_id;
    }

    /**
     * 群课堂id
     * @param group_id 群课堂id
     */
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
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
     * 录制视频地址
     * @return record_url 录制视频地址
     */
    public String getRecord_url() {
        return record_url;
    }

    /**
     * 录制视频地址
     * @param record_url 录制视频地址
     */
    public void setRecord_url(String record_url) {
        this.record_url = record_url == null ? null : record_url.trim();
    }

    /**
     * 视频格式
     * @return video_format 视频格式
     */
    public String getVideo_format() {
        return video_format;
    }

    /**
     * 视频格式
     * @param video_format 视频格式
     */
    public void setVideo_format(String video_format) {
        this.video_format = video_format == null ? null : video_format.trim();
    }

    /**
     * 封面
     * @return cover 封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * 封面
     * @param cover 封面
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * 群主id
     * @return user_id 群主id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 群主id
     * @param user_id 群主id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
     * 直播流名称
     * @return stream_id 直播流名称
     */
    public String getStream_id() {
        return stream_id;
    }

    /**
     * 直播流名称
     * @param stream_id 直播流名称
     */
    public void setStream_id(String stream_id) {
        this.stream_id = stream_id == null ? null : stream_id.trim();
    }

    /**
     * 状态 1未完成  2已完成
     * @return status 状态 1未完成  2已完成
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1未完成  2已完成
     * @param status 状态 1未完成  2已完成
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 任务ID，全局唯一标识录制任务。
     * @return task_id 任务ID，全局唯一标识录制任务。
     */
    public String getTask_id() {
        return task_id;
    }

    /**
     * 任务ID，全局唯一标识录制任务。
     * @param task_id 任务ID，全局唯一标识录制任务。
     */
    public void setTask_id(String task_id) {
        this.task_id = task_id == null ? null : task_id.trim();
    }

    /**
     * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @return RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public String getRequestId() {
        return RequestId;
    }

    /**
     * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @param RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public void setRequestId(String RequestId) {
        this.RequestId = RequestId == null ? null : RequestId.trim();
    }

    /**
     * 被观看次数
     * @return watch_count 被观看次数
     */
    public Integer getWatch_count() {
        return watch_count;
    }

    /**
     * 被观看次数
     * @param watch_count 被观看次数
     */
    public void setWatch_count(Integer watch_count) {
        this.watch_count = watch_count;
    }
}