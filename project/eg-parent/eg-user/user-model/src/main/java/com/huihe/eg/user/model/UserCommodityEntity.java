package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="猫币商品列表",description="猫币商品列表属性说明")
public class UserCommodityEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

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
     * 
     */
    @ApiModelProperty(value="")
    private String commodity_name;

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
     * 商品ID
     */
    @ApiModelProperty(value="商品ID")
    private String transaction_id;

    /**
     * 来源
     */
    @ApiModelProperty(value="来源")
    private String source;

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
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String Introduction;

    /**
     * 金币数
     */
    @ApiModelProperty(value="金币数",example="1")
    private BigDecimal value_coin;

    /**
     * 状态1上架2下架
     */
    @ApiModelProperty(value="状态1上架2下架",example="1")
    private Integer status;

    /**
     * 折扣
     */
    @ApiModelProperty(value="折扣",example="1")
    private BigDecimal discount;

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
     * 
     * @return commodity_name 
     */
    public String getCommodity_name() {
        return commodity_name;
    }

    /**
     * 
     * @param commodity_name 
     */
    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name == null ? null : commodity_name.trim();
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
     * 商品ID
     * @return transaction_id 商品ID
     */
    public String getTransaction_id() {
        return transaction_id;
    }

    /**
     * 商品ID
     * @param transaction_id 商品ID
     */
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id == null ? null : transaction_id.trim();
    }

    /**
     * 来源
     * @return source 来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 来源
     * @param source 来源
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
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
     * 金币数
     * @return value_coin 金币数
     */
    public BigDecimal getValue_coin() {
        return value_coin;
    }

    /**
     * 金币数
     * @param value_coin 金币数
     */
    public void setValue_coin(BigDecimal value_coin) {
        this.value_coin = value_coin;
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
}