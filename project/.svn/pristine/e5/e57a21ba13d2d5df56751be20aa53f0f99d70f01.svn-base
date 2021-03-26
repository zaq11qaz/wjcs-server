package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="用户的群白名单",description="用户的群白名单属性说明")
public class GroupWhitelistedEntity extends PageInfo {
    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 用户创建时间
     */
    @ApiModelProperty(value="用户创建时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 群id
     */
    @ApiModelProperty(value="群id",example="1")
    private Long group_id;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态",example="1")
    private Integer status;
    @ApiModelProperty(value = "map集合")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    @ApiModelProperty(value="多个群id")
    private String  group_ids;

    public String getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(String group_ids) {
        this.group_ids = group_ids;
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
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
     * 用户创建时间
     * @return create_time 用户创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 用户创建时间
     * @param create_time 用户创建时间
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
     * 状态
     * @return status 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}