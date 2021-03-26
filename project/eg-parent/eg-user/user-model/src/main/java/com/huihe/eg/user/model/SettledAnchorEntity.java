package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="入驻主播名单",description="入驻主播名单属性说明")
public class SettledAnchorEntity extends PageInfo {
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
     * 状态  1 正常 2 冻结
     */
    @ApiModelProperty(value="状态  1 正常 2 冻结",example="1")
    private Integer status;

    /**
     * 免费次数
     */
    @ApiModelProperty(value="免费次数",example="1")
    private Integer free_count;

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
     * 状态  1 正常 2 冻结
     * @return status 状态  1 正常 2 冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态  1 正常 2 冻结
     * @param status 状态  1 正常 2 冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 免费次数
     * @return free_count 免费次数
     */
    public Integer getFree_count() {
        return free_count;
    }

    /**
     * 免费次数
     * @param free_count 免费次数
     */
    public void setFree_count(Integer free_count) {
        this.free_count = free_count;
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
}