package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="任务奖励列表",description="任务奖励列表")
public class UserGoldTypeEntity extends PageInfo {
    /**
     * 
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 金币值
     */
    @ApiModelProperty(value="金币值",example="1")
    private BigDecimal gold_num;

    /**
     * 图片地址
     */
    @ApiModelProperty(value="图片地址")
    private String pic_path;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String introduce;

    /**
     * 排行
     */
    @ApiModelProperty(value="排行",example="1")
    private Integer level;

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
     * 次数
     */
    @ApiModelProperty(value="次数",example="1")
    private Integer frequency;

    /**
     * 状态1正常 2失效
     */
    @ApiModelProperty(value="状态1正常 2失效",example="1")
    private Integer status;

    @ApiModelProperty(value = "完成次数情况" ,example = "1")
    private  Integer Completed_frequency;

    @ApiModelProperty(value = "是否已完成",example="false")
    private Boolean is_complete;

    @ApiModelProperty(value = "可领次数",example = "1")
    private Integer canbeled_count;

    @ApiModelProperty(value = "是否教付保任务",example = "1")
    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public Integer getCanbeled_count() {
        return canbeled_count;
    }

    public void setCanbeled_count(Integer canbeled_count) {
        this.canbeled_count = canbeled_count;
    }

    public Boolean getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(Boolean is_complete) {
        this.is_complete = is_complete;
    }

    public Integer getCompleted_frequency() {
        return Completed_frequency;
    }

    public void setCompleted_frequency(Integer completed_frequency) {
        Completed_frequency = completed_frequency;
    }

    @ApiModelProperty(value = "用户id",example = "1")
    private Long oper_id;

    @ApiModelProperty(value = "是否每日任务",example = "1")
    private Boolean is_daily;

    public Boolean getIs_daily() {
        return is_daily;
    }

    public void setIs_daily(Boolean is_daily) {
        this.is_daily = is_daily;
    }

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 金币值
     * @return gold_num 金币值
     */
    public BigDecimal getGold_num() {
        return gold_num;
    }

    /**
     * 金币值
     * @param gold_num 金币值
     */
    public void setGold_num(BigDecimal gold_num) {
        this.gold_num = gold_num;
    }

    /**
     * 图片地址
     * @return pic_path 图片地址
     */
    public String getPic_path() {
        return pic_path;
    }

    /**
     * 图片地址
     * @param pic_path 图片地址
     */
    public void setPic_path(String pic_path) {
        this.pic_path = pic_path == null ? null : pic_path.trim();
    }

    /**
     * 类型
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 描述
     * @return introduce 描述
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 描述
     * @param introduce 描述
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    /**
     * 排行
     * @return level 排行
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 排行
     * @param level 排行
     */
    public void setLevel(Integer level) {
        this.level = level;
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
     * 次数
     * @return frequency 次数
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * 次数
     * @param frequency 次数
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    /**
     * 状态1正常 2失效
     * @return status 状态1正常 2失效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1正常 2失效
     * @param status 状态1正常 2失效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}