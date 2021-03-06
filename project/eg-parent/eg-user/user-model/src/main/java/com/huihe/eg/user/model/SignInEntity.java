package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="签到",description="签到属性说明")
public class SignInEntity extends PageInfo {
    private static final long serialVersionUID = -5899792782014701942L;
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
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 猫币值
     */
    @ApiModelProperty(value="猫币值",example="1")
    private BigDecimal cat_coin;

    @ApiModelProperty(value="今天是否签到",example = "false")
    private boolean today_sign_in;

    @ApiModelProperty(value="连续签到天数",example = "1")
    private int day;

    @ApiModelProperty(value="是否邀请过",example = "false")
    private boolean is_invitation;

    @ApiModelProperty(value="类型",example = "false")
    private String type;

    @ApiModelProperty(value="类型",example = "false")
    private Integer point;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
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
     * 猫币值
     * @return cat_coin 猫币值
     */
    public BigDecimal getCat_coin() {
        return cat_coin;
    }

    /**
     * 猫币值
     * @param cat_coin 猫币值
     */
    public void setCat_coin(BigDecimal cat_coin) {
        this.cat_coin = cat_coin;
    }

    public boolean isToday_sign_in() {
        return today_sign_in;
    }

    public void setToday_sign_in(boolean today_sign_in) {
        this.today_sign_in = today_sign_in;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isIs_invitation() {
        return is_invitation;
    }

    public void setIs_invitation(boolean is_invitation) {
        this.is_invitation = is_invitation;
    }
}