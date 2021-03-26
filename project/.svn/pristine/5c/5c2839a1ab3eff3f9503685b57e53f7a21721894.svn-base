package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="反馈记录",description="反馈记录属性说明")
public class SystemFeedBackEntity extends PageInfo {
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
     * 反馈建议
     */
    @ApiModelProperty(value="反馈建议")
    private String content;

    /**
     * 截图
     */
    @ApiModelProperty(value="截图")
    private String pic;

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
     * 状态 1：未处理 2已处理
     */
    @ApiModelProperty(value="状态 1：未处理 2已处理",example="1")
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
     * 反馈建议
     * @return content 反馈建议
     */
    public String getContent() {
        return content;
    }

    /**
     * 反馈建议
     * @param content 反馈建议
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 截图
     * @return pic 截图
     */
    public String getPic() {
        return pic;
    }

    /**
     * 截图
     * @param pic 截图
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
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
     * 状态 1：未处理 2已处理
     * @return status 状态 1：未处理 2已处理
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1：未处理 2已处理
     * @param status 状态 1：未处理 2已处理
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}