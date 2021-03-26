package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="是否喜欢该用户操作记录",description="是否喜欢该用户操作记录属性说明")
public class UserLikeEntity extends PageInfo {
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
     * 是否喜欢
     */
    @ApiModelProperty(value="是否喜欢",example="false")
    private Boolean is_like;

    /**
     * 被喜欢人的用户id
     */
    @ApiModelProperty(value="被喜欢人的用户id",example="1")
    private Long other_user_id;

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

    @ApiModelProperty(value = "数据map")
    Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 是否喜欢
     * @return is_like 是否喜欢
     */
    public Boolean getIs_like() {
        return is_like;
    }

    /**
     * 是否喜欢
     * @param is_like 是否喜欢
     */
    public void setIs_like(Boolean is_like) {
        this.is_like = is_like;
    }

    /**
     * 被喜欢人的用户id
     * @return other_user_id 被喜欢人的用户id
     */
    public Long getOther_user_id() {
        return other_user_id;
    }

    /**
     * 被喜欢人的用户id
     * @param other_user_id 被喜欢人的用户id
     */
    public void setOther_user_id(Long other_user_id) {
        this.other_user_id = other_user_id;
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