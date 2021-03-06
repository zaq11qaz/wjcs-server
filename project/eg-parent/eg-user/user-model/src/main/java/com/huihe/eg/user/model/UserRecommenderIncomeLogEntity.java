package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value="",description="属性说明")
public class UserRecommenderIncomeLogEntity extends PageInfo {
    private static final long serialVersionUID = 6120561354565188736L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 推荐官id
     */
    @ApiModelProperty(value="推荐官id",example="1")
    private Long recommender_id;

    /**
     * 类型 mechanism 机构 master 外教 live 直播 mechanism_offline 机构线下课 mechanism_master 机构外教 mechansim_appointment 机构商品
     */
    @ApiModelProperty(value="类型 mechanism 机构 master 外教 live 直播 mechanism_offline 机构线下课 mechanism_master 机构外教 mechansim_appointment 机构商品")
    private String type;

    /**
     * 奖金类型 红包 佣金
     */
    @ApiModelProperty(value="奖金类型 红包 佣金")
    private String cash_type;

    /**
     * 是否核销
     */
    @ApiModelProperty(value="是否核销",example="false")
    private Boolean is_settlement;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 外教id
     */
    @ApiModelProperty(value="外教id",example="1")
    private Long master_id;

    /**
     * master课程id
     */
    @ApiModelProperty(value="master课程id",example="1")
    private Long appointment_id;

    /**
     * 用户课程id
     */
    @ApiModelProperty(value="用户课程id",example="1")
    private Long user_appointment_id;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id",example="1")
    private Long mastersetprice_id;

    /**
     * 收益
     */
    @ApiModelProperty(value="收益",example="1")
    private BigDecimal cash;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String cash_describe;

    @ApiModelProperty(value = "推荐官类型")
    private String recommender_type;

    @ApiModelProperty(value = "邀请人id")
    private Long user_id;

    @ApiModelProperty(value = "被邀请人id")
    private Long invitation_id;

    @ApiModelProperty(value = "活动id")
    private Long activity_id;

    @ApiModelProperty(value = "购买记录id")
    private Long recharge_record_id;

    @ApiModelProperty(value = "规则id")
    private Long role_id;

    @ApiModelProperty(value = "结算时长")
    private Long duration;

    @ApiModelProperty(value = "邀请人数")
    private Integer invite_count;

    public String getCash_type() {
        return cash_type;
    }

    public void setCash_type(String cash_type) {
        this.cash_type = cash_type;
    }

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public Long getRecharge_record_id() {
        return recharge_record_id;
    }

    public void setRecharge_record_id(Long recharge_record_id) {
        this.recharge_record_id = recharge_record_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setInvitation_id(Long invitation_id) {
        this.invitation_id = invitation_id;
    }

    public Long getInvitation_id() {
        return invitation_id;
    }

    public String getRecommender_type() {
        return recommender_type;
    }

    public void setRecommender_type(String recommender_type) {
        this.recommender_type = recommender_type;
    }

    private Map map;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Long getUser_appointment_id() {
        return user_appointment_id;
    }

    public void setUser_appointment_id(Long user_appointment_id) {
        this.user_appointment_id = user_appointment_id;
    }


    public Integer getInvite_count() {
        return invite_count;
    }

    public void setInvite_count(Integer invite_count) {
        this.invite_count = invite_count;
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
     * 推荐官id
     * @return recommender_id 推荐官id
     */
    public Long getRecommender_id() {
        return recommender_id;
    }

    /**
     * 推荐官id
     * @param recommender_id 推荐官id
     */
    public void setRecommender_id(Long recommender_id) {
        this.recommender_id = recommender_id;
    }

    /**
     * 类型 mechanism 机构 master 外教 live 直播 mechanism_offline 机构线下课 mechanism_master 机构外教 mechansim_appointment 机构商品
     * @return type 类型 mechanism 机构 master 外教 live 直播 mechanism_offline 机构线下课 mechanism_master 机构外教 mechansim_appointment 机构商品
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 mechanism 机构 master 外教 live 直播 mechanism_offline 机构线下课 mechanism_master 机构外教 mechansim_appointment 机构商品
     * @param type 类型 mechanism 机构 master 外教 live 直播 mechanism_offline 机构线下课 mechanism_master 机构外教 mechansim_appointment 机构商品
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 是否核销
     * @return is_settlement 是否核销
     */
    public Boolean getIs_settlement() {
        return is_settlement;
    }

    /**
     * 是否核销
     * @param is_settlement 是否核销
     */
    public void setIs_settlement(Boolean is_settlement) {
        this.is_settlement = is_settlement;
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
     * 外教id
     * @return master_id 外教id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 外教id
     * @param master_id 外教id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    /**
     * 课程id
     * @return appointment_id 课程id
     */
    public Long getAppointment_id() {
        return appointment_id;
    }

    /**
     * 课程id
     * @param appointment_id 课程id
     */
    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * 商品id
     * @return mastersetprice_id 商品id
     */
    public Long getMastersetprice_id() {
        return mastersetprice_id;
    }

    /**
     * 商品id
     * @param mastersetprice_id 商品id
     */
    public void setMastersetprice_id(Long mastersetprice_id) {
        this.mastersetprice_id = mastersetprice_id;
    }

    /**
     * 收益
     * @return cash 收益
     */
    public BigDecimal getCash() {
        return cash;
    }

    /**
     * 收益
     * @param cash 收益
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public String getCash_describe() {
        return cash_describe;
    }

    public void setCash_describe(String cash_describe) {
        this.cash_describe = cash_describe;
    }
}