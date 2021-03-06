package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户、助学师评分",description="用户、助学师评分属性说明")
public class UserRatingEntity extends PageInfo {
    private static final long serialVersionUID = -8769728445237035460L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 评分用户id
     */
    @ApiModelProperty(value="评分用户id",example="1")
    private Long user_id;

    /**
     * 被评分的用户id
     */
    @ApiModelProperty(value="被评分的用户id",example="1")
    private Long passive_id;

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
     * 状态1正常，2失效
     */
    @ApiModelProperty(value="状态1正常，2失效",example="1")
    private Integer status;

    /**
     * 评分
     */
    @ApiModelProperty(value="评分",example="1")
    private Integer rating;

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
     * 评分用户id
     * @return user_id 评分用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 评分用户id
     * @param user_id 评分用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 被评分的用户id
     * @return passive_id 被评分的用户id
     */
    public Long getPassive_id() {
        return passive_id;
    }

    /**
     * 被评分的用户id
     * @param passive_id 被评分的用户id
     */
    public void setPassive_id(Long passive_id) {
        this.passive_id = passive_id;
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
     * 状态1正常，2失效
     * @return status 状态1正常，2失效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1正常，2失效
     * @param status 状态1正常，2失效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 评分
     * @return rating 评分
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * 评分
     * @param rating 评分
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }
}