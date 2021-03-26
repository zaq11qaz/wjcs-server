package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="直播课堂学习卡使用记录",description="直播课堂学习卡使用记录属性说明")
public class ClassRecordEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

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
     * 类别 minute分钟/curriculum课程
     */
    @ApiModelProperty(value="类别 minute分钟/curriculum课程")
    private String type;

    /**
     * 1 正常 2失效
     */
    @ApiModelProperty(value="1 正常 2失效 3体验 4工作人员",example="1")
    private Integer status;

    /**
     * 使用课程数
     */
    @ApiModelProperty(value="使用课程数",example="1")
    private Integer curriculum_num;

    /**
     * 使用分钟数
     */
    @ApiModelProperty(value="使用分钟数",example="1")
    private Integer minute_num;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 群id
     */
    @ApiModelProperty(value="群id",example="1")
    private Long group_id;

    /**
     * 课程安排id
     */
    @ApiModelProperty(value="课程安排id",example="1")
    private Long curriculum_id;

    /**
     * 免费分钟记录
     */
    @ApiModelProperty(value="免费分钟记录",example="1")
    private Integer free_minute;
    /**
     * map数据
     */
    @ApiModelProperty(value="map数据")
    private Map<String,Object> map;

    @ApiModelProperty(value = "nickNameIDs id集合",example = "1")
    private List nickNameIDs;

    @ApiModelProperty(value = "昵称",example = "1")
    private String nick_name;

    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public List getNickNameIDs() {
        return nickNameIDs;
    }

    public void setNickNameIDs(List nickNameIDs) {
        this.nickNameIDs = nickNameIDs;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 类别 minute分钟/curriculum课程
     * @return type 类别 minute分钟/curriculum课程
     */
    public String getType() {
        return type;
    }

    /**
     * 类别 minute分钟/curriculum课程
     * @param type 类别 minute分钟/curriculum课程
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 1 正常 2失效
     * @return status 1 正常 2失效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 正常 2失效
     * @param status 1 正常 2失效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 使用课程数
     * @return curriculum_num 使用课程数
     */
    public Integer getCurriculum_num() {
        return curriculum_num;
    }

    /**
     * 使用课程数
     * @param curriculum_num 使用课程数
     */
    public void setCurriculum_num(Integer curriculum_num) {
        this.curriculum_num = curriculum_num;
    }

    /**
     * 使用分钟数
     * @return minute_num 使用分钟数
     */
    public Integer getMinute_num() {
        return minute_num;
    }

    /**
     * 使用分钟数
     * @param minute_num 使用分钟数
     */
    public void setMinute_num(Integer minute_num) {
        this.minute_num = minute_num;
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
     * 群id
     * @return group_id 群id
     */
    public Long getGroup_id() {
        return group_id;
    }

    /**
     * 群id
     * @param group_id 群id
     */
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    /**
     * 课程安排id
     * @return curriculum_id 课程安排id
     */
    public Long getCurriculum_id() {
        return curriculum_id;
    }

    /**
     * 课程安排id
     * @param curriculum_id 课程安排id
     */
    public void setCurriculum_id(Long curriculum_id) {
        this.curriculum_id = curriculum_id;
    }

    /**
     * 免费分钟记录
     * @return free_minute 免费分钟记录
     */
    public Integer getFree_minute() {
        return free_minute;
    }

    /**
     * 免费分钟记录
     * @param free_minute 免费分钟记录
     */
    public void setFree_minute(Integer free_minute) {
        this.free_minute = free_minute;
    }
}