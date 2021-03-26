package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="友圈时间轴",description="友圈时间轴属性说明")
public class NoteTimelineEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 动态id
     */
    @ApiModelProperty(value="动态id",example="1")
    private Long note_id;

    /**
     * 是否是自己的 1.是 2.不是
     */
    @ApiModelProperty(value="是否是自己的 1.是 2.不是",example="false")
    private Boolean is_own;

    /**
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 状态1.可用2 不可用
     */
    @ApiModelProperty(value="状态1.可用2 不可用",example="1")
    private Integer status;

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
     * 动态id
     * @return note_id 动态id
     */
    public Long getNote_id() {
        return note_id;
    }

    /**
     * 动态id
     * @param note_id 动态id
     */
    public void setNote_id(Long note_id) {
        this.note_id = note_id;
    }

    /**
     * 是否是自己的 1.是 2.不是
     * @return is_own 是否是自己的 1.是 2.不是
     */
    public Boolean getIs_own() {
        return is_own;
    }

    /**
     * 是否是自己的 1.是 2.不是
     * @param is_own 是否是自己的 1.是 2.不是
     */
    public void setIs_own(Boolean is_own) {
        this.is_own = is_own;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 状态1.可用2 不可用
     * @return status 状态1.可用2 不可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.可用2 不可用
     * @param status 状态1.可用2 不可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}