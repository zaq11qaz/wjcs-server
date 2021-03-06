package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户关注机构列表",description="用户关注机构列表属性说明")
public class UserFollowMechanismEntity extends PageInfo {
    private static final long serialVersionUID = -4591263741248223354L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

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
     * 1 关注 2 取关
     */
    @ApiModelProperty(value="1 关注 2 取关",example="1")
    private Integer status;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 机构名
     */
    @ApiModelProperty(value="机构名")
    private String mechanism_name;

    /**
     * 机构地址
     */
    @ApiModelProperty(value="机构地址")
    private String mechanism_addr;

    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String contact_telephone;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id")
    private Long master_set_price_id;

    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
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
     * 1 关注 2 取关
     * @return status 1 关注 2 取关
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 关注 2 取关
     * @param status 1 关注 2 取关
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 机构名
     * @return mechanism_name 机构名
     */
    public String getMechanism_name() {
        return mechanism_name;
    }

    /**
     * 机构名
     * @param mechanism_name 机构名
     */
    public void setMechanism_name(String mechanism_name) {
        this.mechanism_name = mechanism_name == null ? null : mechanism_name.trim();
    }

    /**
     * 机构地址
     * @return mechanism_addr 机构地址
     */
    public String getMechanism_addr() {
        return mechanism_addr;
    }

    /**
     * 机构地址
     * @param mechanism_addr 机构地址
     */
    public void setMechanism_addr(String mechanism_addr) {
        this.mechanism_addr = mechanism_addr == null ? null : mechanism_addr.trim();
    }

    /**
     * 联系电话
     * @return contact_telephone 联系电话
     */
    public String getContact_telephone() {
        return contact_telephone;
    }

    /**
     * 联系电话
     * @param contact_telephone 联系电话
     */
    public void setContact_telephone(String contact_telephone) {
        this.contact_telephone = contact_telephone == null ? null : contact_telephone.trim();
    }
}