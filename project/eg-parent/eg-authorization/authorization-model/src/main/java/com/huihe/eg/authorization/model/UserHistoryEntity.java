package com.huihe.eg.authorization.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户请求客服记录表",description="用户请求客服记录表属性说明")
public class UserHistoryEntity extends PageInfo {
    /**
     * id
     */
    @ApiModelProperty(value="id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 请求用户id
     */
    @ApiModelProperty(value="请求用户id",example="1")
    private Long user_id;

    /**
     * 状态 1 发起请求 2 对话中 3 结束
     */
    @ApiModelProperty(value="状态 1 发起请求 2 对话中 3 结束",example="1")
    private Integer status;

    /**
     * 分配的客服id
     */
    @ApiModelProperty(value="分配的客服id",example="1")
    private Long manager_id;

    /**
     * id
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     * @param id id
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
     * 请求用户id
     * @return user_id 请求用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 请求用户id
     * @param user_id 请求用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 状态 1 发起请求 2 对话中 3 结束
     * @return status 状态 1 发起请求 2 对话中 3 结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 发起请求 2 对话中 3 结束
     * @param status 状态 1 发起请求 2 对话中 3 结束
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 分配的客服id
     * @return manager_id 分配的客服id
     */
    public Long getManager_id() {
        return manager_id;
    }

    /**
     * 分配的客服id
     * @param manager_id 分配的客服id
     */
    public void setManager_id(Long manager_id) {
        this.manager_id = manager_id;
    }
}