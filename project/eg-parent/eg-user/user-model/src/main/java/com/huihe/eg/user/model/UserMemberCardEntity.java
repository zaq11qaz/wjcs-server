package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="会员时长",description="会员时长属性说明")
public class UserMemberCardEntity extends PageInfo {
    private static final long serialVersionUID = -9045709912406531550L;
    /**
     * 主键Id
     */
    @ApiModelProperty(value="主键Id",example="1")
    private Long id;

    /**
     * 等级
     */
    @ApiModelProperty(value="等级",example="1")
    private Long level_id;

    /**
     * 折扣
     */
    @ApiModelProperty(value="折扣",example="1")
    private BigDecimal discount;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额",example="1")
    private BigDecimal amount;

    /**
     * 会员名称
     */
    @ApiModelProperty(value="会员名称")
    private String member_name;

    /**
     * 折扣价
     */
    @ApiModelProperty(value="折扣价",example="1")
    private BigDecimal discount_amout;

    /**
     * 是否折扣
     */
    @ApiModelProperty(value="是否折扣",example="false")
    private Boolean is_discount;

    /**
     * 赠送金币
     */
    @ApiModelProperty(value="赠送金币",example="1")
    private Integer give_coin;

    /**
     * 时长
     */
    @ApiModelProperty(value="时长",example="1")
    private Integer duration;

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
     * 状态1上架2下架
     */
    @ApiModelProperty(value="状态1上架2下架",example="1")
    private Integer status;

    /**
     * 平台
     */
    @ApiModelProperty(value="平台")
    private String platform;

    /**
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String Introduction;

    /**
     * ios商品Id
     */
    @ApiModelProperty(value="ios商品Id")
    private String transaction_id;

    /**
     * 主键Id
     * @return id 主键Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键Id
     * @param id 主键Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 等级
     * @return level_id 等级
     */
    public Long getLevel_id() {
        return level_id;
    }

    /**
     * 等级
     * @param level_id 等级
     */
    public void setLevel_id(Long level_id) {
        this.level_id = level_id;
    }

    /**
     * 折扣
     * @return discount 折扣
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 折扣
     * @param discount 折扣
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 金额
     * @return amount 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 金额
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 会员名称
     * @return member_name 会员名称
     */
    public String getMember_name() {
        return member_name;
    }

    /**
     * 会员名称
     * @param member_name 会员名称
     */
    public void setMember_name(String member_name) {
        this.member_name = member_name == null ? null : member_name.trim();
    }

    /**
     * 折扣价
     * @return discount_amout 折扣价
     */
    public BigDecimal getDiscount_amout() {
        return discount_amout;
    }

    /**
     * 折扣价
     * @param discount_amout 折扣价
     */
    public void setDiscount_amout(BigDecimal discount_amout) {
        this.discount_amout = discount_amout;
    }

    /**
     * 是否折扣
     * @return is_discount 是否折扣
     */
    public Boolean getIs_discount() {
        return is_discount;
    }

    /**
     * 是否折扣
     * @param is_discount 是否折扣
     */
    public void setIs_discount(Boolean is_discount) {
        this.is_discount = is_discount;
    }

    /**
     * 赠送金币
     * @return give_coin 赠送金币
     */
    public Integer getGive_coin() {
        return give_coin;
    }

    /**
     * 赠送金币
     * @param give_coin 赠送金币
     */
    public void setGive_coin(Integer give_coin) {
        this.give_coin = give_coin;
    }

    /**
     * 时长
     * @return duration 时长
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * 时长
     * @param duration 时长
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
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
     * 状态1上架2下架
     * @return status 状态1上架2下架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1上架2下架
     * @param status 状态1上架2下架
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 平台
     * @return platform 平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 平台
     * @param platform 平台
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 说明
     * @return Introduction 说明
     */
    public String getIntroduction() {
        return Introduction;
    }

    /**
     * 说明
     * @param Introduction 说明
     */
    public void setIntroduction(String Introduction) {
        this.Introduction = Introduction == null ? null : Introduction.trim();
    }

    /**
     * ios商品Id
     * @return transaction_id ios商品Id
     */
    public String getTransaction_id() {
        return transaction_id;
    }

    /**
     * ios商品Id
     * @param transaction_id ios商品Id
     */
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id == null ? null : transaction_id.trim();
    }
}