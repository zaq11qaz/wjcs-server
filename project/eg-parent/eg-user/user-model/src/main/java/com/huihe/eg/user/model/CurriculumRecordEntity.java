package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="课程安排记录",description="课程安排记录属性说明")
public class CurriculumRecordEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 创建
     */
    @ApiModelProperty(value="创建")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 类别 
     */
    @ApiModelProperty(value="类别 ")
    private String type;

    /**
     * 1 过期 2 可用 3 取消
     */
    @ApiModelProperty(value="1 过期 2 可用 3 取消",example="1")
    private Integer status;

    /**
     * 学习卡开始时间
     */
    @ApiModelProperty(value="学习卡开始时间")
    private Date start_time;

    /**
     * 学习卡结束时间
     */
    @ApiModelProperty(value="学习卡结束时间")
    private Date end_time;

    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long group_id;

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
     * 创建
     * @return create_time 创建
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建
     * @param create_time 创建
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
     * 类别 
     * @return type 类别 
     */
    public String getType() {
        return type;
    }

    /**
     * 类别 
     * @param type 类别 
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 1 过期 2 可用 3 取消
     * @return status 1 过期 2 可用 3 取消
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 过期 2 可用 3 取消
     * @param status 1 过期 2 可用 3 取消
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 学习卡开始时间
     * @return start_time 学习卡开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 学习卡开始时间
     * @param start_time 学习卡开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 学习卡结束时间
     * @return end_time 学习卡结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 学习卡结束时间
     * @param end_time 学习卡结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 
     * @return group_id 
     */
    public Long getGroup_id() {
        return group_id;
    }

    /**
     * 
     * @param group_id 
     */
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }
}