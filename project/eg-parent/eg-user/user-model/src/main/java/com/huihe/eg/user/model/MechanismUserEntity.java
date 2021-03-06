package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="",description="属性说明")
public class MechanismUserEntity extends PageInfo {
    private static final long serialVersionUID = 502766988047627486L;
    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

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
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 是否新客 true 是 false 不是
     */
    @ApiModelProperty(value="是否新客 true 是 false 不是",example="false")
    private Boolean is_new;

    /**
     * 免费课节数
     */
    @ApiModelProperty(value="免费课节数",example="1")
    private Integer free_course;

    /**
     * 1 未确认 2 已确认
     */
    @ApiModelProperty(value="1 未确认 2 已确认",example="1")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注",example="1")
    private String remarks;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型",example="1")
    private String type;

    /**
     * 学籍状态
     */
    @ApiModelProperty(value="学籍状态",example="1")
    private String academic_status;

    /**
     * 券id
     */
    @ApiModelProperty(value="券id",example="1")
    private Long coupon_id;

    /**
     * map
     */
    @ApiModelProperty(value="map",example="1")
    private Map<String,Object> map;

    public String getAcademic_status() {
        return academic_status;
    }

    public void setAcademic_status(String academic_status) {
        this.academic_status = academic_status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Long coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Map<String,Object> getMap() {
        return map;
    }

    public void setMap(Map<String,Object> map) {
        this.map = map;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
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
     * 机构id
     * @return mechanism_id 机构id
     */
    public Long getMechanism_id() {
        return mechanism_id;
    }

    /**
     * 机构id
     * @param mechanism_id 机构id
     */
    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    /**
     * 是否新客 true 是 false 不是
     * @return is_new 是否新客 true 是 false 不是
     */
    public Boolean getIs_new() {
        return is_new;
    }

    /**
     * 是否新客 true 是 false 不是
     * @param is_new 是否新客 true 是 false 不是
     */
    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }

    /**
     * 免费课节数
     * @return free_course 免费课节数
     */
    public Integer getFree_course() {
        return free_course;
    }

    /**
     * 免费课节数
     * @param free_course 免费课节数
     */
    public void setFree_course(Integer free_course) {
        this.free_course = free_course;
    }
}