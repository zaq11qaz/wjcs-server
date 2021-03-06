package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="直播招募",description="直播招募属性说明")
public class LiveApplyEntity extends PageInfo {
    private static final long serialVersionUID = -1652244386749336696L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

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
     * 用户ID
     */
    @ApiModelProperty(value="用户ID",example="1")
    private Long user_id;

    /**
     * 联系方式
     */
    @ApiModelProperty(value="联系方式")
    private String contact_information;
    @ApiModelProperty(value = "map")
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
     * 联系方式
     * @return contact_information 联系方式
     */
    public String getContact_information() {
        return contact_information;
    }

    /**
     * 联系方式
     * @param contact_information 联系方式
     */
    public void setContact_information(String contact_information) {
        this.contact_information = contact_information == null ? null : contact_information.trim();
    }
}