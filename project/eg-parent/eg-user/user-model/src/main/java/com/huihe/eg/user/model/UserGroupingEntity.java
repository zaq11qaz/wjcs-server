package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="用户拼团记录",description="用户拼团记录属性说明")
public class UserGroupingEntity extends PageInfo {
    private static final long serialVersionUID = 7211385068703905672L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

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
     * 商品id
     */
    @ApiModelProperty(value="商品id",example="1")
    private Long master_set_price_id;

    /**
     * 当前课节数
     */
    @ApiModelProperty(value="当前课节数",example="1")
    private Integer present_lesson;

    /**
     * 团id
     */
    @ApiModelProperty(value="团id")
    private String group_id;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 学习卡id
     */
    @ApiModelProperty(value="学习卡id")
    private Long study_card_id;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动id")
    private Long activity_id;

    /**
     * 验证码
     */
    @ApiModelProperty(value="验证码")
    private String verification_code;

    /**
     * 是否全额购
     */
    @ApiModelProperty(value="是否全额购")
    private Boolean is_one_time_payment;

    /**
     * 全额购是否结算
     */
    @ApiModelProperty(value="全额购是否结算")
    private Boolean is_settlement;

    @ApiModelProperty(value="map杂项")
    private Map<String,Object> map;

    public Boolean getIs_settlement() {
        return is_settlement;
    }

    public void setIs_settlement(Boolean is_settlement) {
        this.is_settlement = is_settlement;
    }

    public Boolean getIs_one_time_payment() {
        return is_one_time_payment;
    }

    public void setIs_one_time_payment(Boolean is_one_time_payment) {
        this.is_one_time_payment = is_one_time_payment;
    }

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    public Long getStudy_card_id() {
        return study_card_id;
    }

    public void setStudy_card_id(Long study_card_id) {
        this.study_card_id = study_card_id;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
     * 商品id
     * @return master_set_price_id 商品id
     */
    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    /**
     * 商品id
     * @param master_set_price_id 商品id
     */
    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }

    /**
     * 当前课节数
     * @return present_lesson 当前课节数
     */
    public Integer getPresent_lesson() {
        return present_lesson;
    }

    /**
     * 当前课节数
     * @param present_lesson 当前课节数
     */
    public void setPresent_lesson(Integer present_lesson) {
        this.present_lesson = present_lesson;
    }

    /**
     * 团id
     * @return group_id 团id
     */
    public String getGroup_id() {
        return group_id;
    }

    /**
     * 团id
     * @param group_id 团id
     */
    public void setGroup_id(String group_id) {
        this.group_id = group_id == null ? null : group_id.trim();
    }
}