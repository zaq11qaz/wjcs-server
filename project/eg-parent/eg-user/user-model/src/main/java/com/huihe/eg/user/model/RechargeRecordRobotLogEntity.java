package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="假数据添加记录",description="假数据添加记录属性说明")
public class RechargeRecordRobotLogEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Integer id;

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
     * 帐号
     */
    @ApiModelProperty(value="帐号")
    private String mobile;

    /**
     * 插入数量
     */
    @ApiModelProperty(value="插入数量",example="1")
    private Integer add_count;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Long activity_id;

    /**
     * 主键id
     * @return id 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
     * @param id 主键id
     */
    public void setId(Integer id) {
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
     * 帐号
     * @return mobile 帐号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 帐号
     * @param mobile 帐号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 插入数量
     * @return add_count 插入数量
     */
    public Integer getAdd_count() {
        return add_count;
    }

    /**
     * 插入数量
     * @param add_count 插入数量
     */
    public void setAdd_count(Integer add_count) {
        this.add_count = add_count;
    }

    /**
     * 活动id
     * @return activity_id 活动id
     */
    public Long getActivity_id() {
        return activity_id;
    }

    /**
     * 活动id
     * @param activity_id 活动id
     */
    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }
}