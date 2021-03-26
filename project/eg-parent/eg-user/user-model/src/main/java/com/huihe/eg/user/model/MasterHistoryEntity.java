package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="助学师评论操作记录",description="助学师评论操作记录属性说明")
public class MasterHistoryEntity extends PageInfo {
    private static final long serialVersionUID = -5894799337800844398L;
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID",example="1")
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
     * 操作类型1点赞:like2分享:share3收藏:collect
     */
    @ApiModelProperty(value="操作类型1点赞:like2分享:share3收藏:collect")
    private String operation_type;

    /**
     * 操作对象的id
     */
    @ApiModelProperty(value="操作对象的id",example="1")
    private Long history_id;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 状态1.点赞2.取消点赞
     */
    @ApiModelProperty(value="状态1.点赞2.取消点赞",example="1")
    private Integer status;

    /**
     * 类型 mastercomment
     */
    @ApiModelProperty(value="类型 mastercomment")
    private String history_type;

    /**
     * 分享目标
     */
    @ApiModelProperty(value="分享目标")
    private String destination;

    /**
     * 主键ID
     * @return id 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户ID
     * @return user_id 用户ID
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户ID
     * @param user_id 用户ID
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
     * 操作类型1点赞:like2分享:share3收藏:collect
     * @return operation_type 操作类型1点赞:like2分享:share3收藏:collect
     */
    public String getOperation_type() {
        return operation_type;
    }

    /**
     * 操作类型1点赞:like2分享:share3收藏:collect
     * @param operation_type 操作类型1点赞:like2分享:share3收藏:collect
     */
    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type == null ? null : operation_type.trim();
    }

    /**
     * 操作对象的id
     * @return history_id 操作对象的id
     */
    public Long getHistory_id() {
        return history_id;
    }

    /**
     * 操作对象的id
     * @param history_id 操作对象的id
     */
    public void setHistory_id(Long history_id) {
        this.history_id = history_id;
    }

    /**
     * 地址
     * @return address 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 状态1.点赞2.取消点赞
     * @return status 状态1.点赞2.取消点赞
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.点赞2.取消点赞
     * @param status 状态1.点赞2.取消点赞
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型 mastercomment
     * @return history_type 类型 mastercomment
     */
    public String getHistory_type() {
        return history_type;
    }

    /**
     * 类型 mastercomment
     * @param history_type 类型 mastercomment
     */
    public void setHistory_type(String history_type) {
        this.history_type = history_type == null ? null : history_type.trim();
    }

    /**
     * 分享目标
     * @return destination 分享目标
     */
    public String getDestination() {
        return destination;
    }

    /**
     * 分享目标
     * @param destination 分享目标
     */
    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }
}