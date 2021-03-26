package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="会员提交记录",description="会员提交记录属性说明")
public class MemberRecordEntity extends PageInfo {
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
     * 订单编号
     */
    @ApiModelProperty(value="订单编号")
    private String order_no;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 会员开始时间
     */
    @ApiModelProperty(value="会员开始时间")
    private Date start_time;

    /**
     * 会员结束时间
     */
    @ApiModelProperty(value="会员结束时间")
    private Date end_time;

    /**
     * 会员等级
     */
    @ApiModelProperty(value="会员等级 1畅聊会员 2铂金会员 3砖石会员",example="1")
    private Integer member_level;

    /**
     * 是否成功开通会员 true 成功  false 失败
     */
    @ApiModelProperty(value="是否成功开通会员 true 成功  false 失败",example="false")
    private Boolean is_member;

    /**
     * 开通会员  充值金额
     */
    @ApiModelProperty(value="开通会员  充值金额",example="1")
    private BigDecimal money;

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
     * 订单编号
     * @return order_no 订单编号
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 订单编号
     * @param order_no 订单编号
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
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
     * 会员开始时间
     * @return start_time 会员开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 会员开始时间
     * @param start_time 会员开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 会员结束时间
     * @return end_time 会员结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 会员结束时间
     * @param end_time 会员结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 会员等级
     * @return member_level 会员等级
     */
    public Integer getMember_level() {
        return member_level;
    }

    /**
     * 会员等级
     * @param member_level 会员等级
     */
    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
    }

    /**
     * 是否成功开通会员 true 成功  false 失败
     * @return is_member 是否成功开通会员 true 成功  false 失败
     */
    public Boolean getIs_member() {
        return is_member;
    }

    /**
     * 是否成功开通会员 true 成功  false 失败
     * @param is_member 是否成功开通会员 true 成功  false 失败
     */
    public void setIs_member(Boolean is_member) {
        this.is_member = is_member;
    }

    /**
     * 开通会员  充值金额
     * @return money 开通会员  充值金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 开通会员  充值金额
     * @param money 开通会员  充值金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}