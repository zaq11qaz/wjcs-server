package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="助学师类型",description="助学师类型属性说明")
public class MasterTypeEntity extends PageInfo {
    private static final long serialVersionUID = 3670625508412913590L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 职位详情
     */
    @ApiModelProperty(value="职位详情")
    private String position_details;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 要求
     */
    @ApiModelProperty(value="要求")
    private String requirement;

    /**
     * 价格/课时
     */
    @ApiModelProperty(value="价格/课时",example="1")
    private Integer price;

    /**
     * 课时
     */
    @ApiModelProperty(value="课时",example="1")
    private Integer class_hour;

    /**
     * 课时时长/s
     */
    @ApiModelProperty(value="课时时长/s",example="1")
    private Integer class_duration;

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
     * 单节课收益
     */
    @ApiModelProperty(value="单节课收益",example="1")
    private BigDecimal earnings;

    /**
     * 收益比例
     */
    @ApiModelProperty(value="收益比例",example="1")
    private BigDecimal proportion;

    /**
     * 是否教付保
     */
    @ApiModelProperty(value="是否教付保",example="1")
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
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 职位详情
     * @return position_details 职位详情
     */
    public String getPosition_details() {
        return position_details;
    }

    /**
     * 职位详情
     * @param position_details 职位详情
     */
    public void setPosition_details(String position_details) {
        this.position_details = position_details == null ? null : position_details.trim();
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
     * 要求
     * @return requirement 要求
     */
    public String getRequirement() {
        return requirement;
    }

    /**
     * 要求
     * @param requirement 要求
     */
    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    /**
     * 价格/课时
     * @return price 价格/课时
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 价格/课时
     * @param price 价格/课时
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 课时
     * @return class_hour 课时
     */
    public Integer getClass_hour() {
        return class_hour;
    }

    /**
     * 课时
     * @param class_hour 课时
     */
    public void setClass_hour(Integer class_hour) {
        this.class_hour = class_hour;
    }

    /**
     * 课时时长/s
     * @return class_duration 课时时长/s
     */
    public Integer getClass_duration() {
        return class_duration;
    }

    /**
     * 课时时长/s
     * @param class_duration 课时时长/s
     */
    public void setClass_duration(Integer class_duration) {
        this.class_duration = class_duration;
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
     * 单节课收益
     * @return earnings 单节课收益
     */
    public BigDecimal getEarnings() {
        return earnings;
    }

    /**
     * 单节课收益
     * @param earnings 单节课收益
     */
    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }

    /**
     * 收益比例
     * @return proportion 收益比例
     */
    public BigDecimal getProportion() {
        return proportion;
    }

    /**
     * 收益比例
     * @param proportion 收益比例
     */
    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }
}