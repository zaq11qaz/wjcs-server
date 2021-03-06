package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="礼物列表",description="属性说明")
public class UserGiftEntity extends PageInfo {
    private static final long serialVersionUID = -8229446034701440770L;
    /**
     * 主键Id
     */
    @ApiModelProperty(value="主键Id",example="1")
    private Long id;

    /**
     * 礼物名称
     */
    @ApiModelProperty(value="礼物名称")
    private String gift_name;

    /**
     * 创建日期
     */
    @ApiModelProperty(value="创建日期")
    private Date create_time;

    /**
     * 修改日期
     */
    @ApiModelProperty(value="修改日期")
    private Date update_time;

    /**
     * member 会员充值  authentication 认证充值  account  账户充值 withdraw_deposit 提现
     */
    @ApiModelProperty(value="member 会员充值  authentication 认证充值  account  账户充值 withdraw_deposit 提现")
    private String type;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额",example="1")
    private BigDecimal amount;

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
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String Introduction;

    /**
     * 状态1上架2下架
     */
    @ApiModelProperty(value="状态1上架2下架",example="1")
    private Integer status;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Integer activity_id;

    /**
     * 礼物图标
     */
    @ApiModelProperty(value="礼物图标")
    private String pic;

    public Integer getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }

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
     * 礼物名称
     * @return gift_name 礼物名称
     */
    public String getGift_name() {
        return gift_name;
    }

    /**
     * 礼物名称
     * @param gift_name 礼物名称
     */
    public void setGift_name(String gift_name) {
        this.gift_name = gift_name == null ? null : gift_name.trim();
    }

    /**
     * 创建日期
     * @return create_time 创建日期
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建日期
     * @param create_time 创建日期
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 修改日期
     * @return update_time 修改日期
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 修改日期
     * @param update_time 修改日期
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * member 会员充值  authentication 认证充值  account  账户充值 withdraw_deposit 提现
     * @return type member 会员充值  authentication 认证充值  account  账户充值 withdraw_deposit 提现
     */
    public String getType() {
        return type;
    }

    /**
     * member 会员充值  authentication 认证充值  account  账户充值 withdraw_deposit 提现
     * @param type member 会员充值  authentication 认证充值  account  账户充值 withdraw_deposit 提现
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 礼物图标
     * @return pic 礼物图标
     */
    public String getPic() {
        return pic;
    }

    /**
     * 礼物图标
     * @param pic 礼物图标
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }
}