package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="用户关注直播讲师记录表",description="用户关注直播讲师记录表属性说明")
public class MasterFollowEntity extends PageInfo {
    private static final long serialVersionUID = 109024944273218679L;
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
     * 直播讲师的user_id
     */
    @ApiModelProperty(value="直播讲师的user_id",example="1")
    private Long master_id;

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
     * 状态 1关注 2取消
     */
    @ApiModelProperty(value="状态 1关注 2取消",example="1")
    private Integer status;

    /**
     * live直播讲师/private私教
     */
    @ApiModelProperty(value="live直播讲师/private私教")
    private String type;

    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

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
     * 直播讲师的user_id
     * @return master_id 直播讲师的user_id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 直播讲师的user_id
     * @param master_id 直播讲师的user_id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
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
     * 状态 1关注 2取消
     * @return status 状态 1关注 2取消
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1关注 2取消
     * @param status 状态 1关注 2取消
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * live直播讲师/private私教
     * @return type live直播讲师/private私教
     */
    public String getType() {
        return type;
    }

    /**
     * live直播讲师/private私教
     * @param type live直播讲师/private私教
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}