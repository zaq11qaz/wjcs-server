package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="推荐官群组关联表",description="推荐官群组关联表属性说明")
public class UserRecommenderGroupInterlinkEntity extends PageInfo {
    private static final long serialVersionUID = 3433505090174742114L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 推荐官id
     */
    @ApiModelProperty(value="推荐官id",example="1")
    private Long recommender_id;

    /**
     * 推荐官ids
     */
    @ApiModelProperty(value="推荐官ids",example="1")
    private String recommender_ids;

    /**
     * 群组id
     */
    @ApiModelProperty(value="群组id",example="1")
    private Long group_id;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecommender_ids() {
        return recommender_ids;
    }

    public void setRecommender_ids(String recommender_ids) {
        this.recommender_ids = recommender_ids;
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
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     * @param update_time 更新时间
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
     * 推荐官id
     * @return recommender_id 推荐官id
     */
    public Long getRecommender_id() {
        return recommender_id;
    }

    /**
     * 推荐官id
     * @param recommender_id 推荐官id
     */
    public void setRecommender_id(Long recommender_id) {
        this.recommender_id = recommender_id;
    }

    /**
     * 群组id
     * @return group_id 群组id
     */
    public Long getGroup_id() {
        return group_id;
    }

    /**
     * 群组id
     * @param group_id 群组id
     */
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }
}