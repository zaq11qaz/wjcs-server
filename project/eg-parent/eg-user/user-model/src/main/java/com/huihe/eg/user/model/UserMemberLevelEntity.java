package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value="会员等级",description="会员等级属性说明")
public class UserMemberLevelEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String member_name;

    /**
     * 等级
     */
    @ApiModelProperty(value="等级",example="1")
    private Integer member_level;

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
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String Introduction;

    /**
     * 平台
     */
    @ApiModelProperty(value="平台")
    private String platform;

    /**
     * 折扣
     */
    @ApiModelProperty(value="折扣",example="1")
    private BigDecimal discount;

    /**
     * 是否折扣
     */
    @ApiModelProperty(value="是否折扣",example="false")
    private Boolean is_discount;

    /**
     * 权益
     */
    @ApiModelProperty(value="权益")
    private String member_equity;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ApiModelProperty(value ="时长集合")
    private List<UserMemberCardEntity> cardEntityList;

    @ApiModelProperty(value="赠送免费聊天次数")
    private Integer chatting_count;

    public Integer getChatting_count() {
        return chatting_count;
    }

    public void setChatting_count(Integer chatting_count) {
        this.chatting_count = chatting_count;
    }

    public List<UserMemberCardEntity> getCardEntityList() {
        return cardEntityList;
    }

    public void setCardEntityList(List<UserMemberCardEntity> cardEntityList) {
        this.cardEntityList = cardEntityList;
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
     * 名称
     * @return member_name 名称
     */
    public String getMember_name() {
        return member_name;
    }

    /**
     * 名称
     * @param member_name 名称
     */
    public void setMember_name(String member_name) {
        this.member_name = member_name == null ? null : member_name.trim();
    }

    /**
     * 等级
     * @return member_level 等级
     */
    public Integer getMember_level() {
        return member_level;
    }

    /**
     * 等级
     * @param member_level 等级
     */
    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
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
     * 权益
     * @return member_equity 权益
     */
    public String getMember_equity() {
        return member_equity;
    }

    /**
     * 权益
     * @param member_equity 权益
     */
    public void setMember_equity(String member_equity) {
        this.member_equity = member_equity == null ? null : member_equity.trim();
    }
}