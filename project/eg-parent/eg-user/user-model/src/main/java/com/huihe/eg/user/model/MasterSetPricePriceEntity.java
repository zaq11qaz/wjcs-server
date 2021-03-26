package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="商品价格区间",description="商品价格区间属性说明")
public class MasterSetPricePriceEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id",example="1")
    private Long master_set_price_id;

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
     * 状态 1 不可用 2 可用
     */
    @ApiModelProperty(value="状态 1 不可用 2 可用",example="1")
    private Integer status;

    /**
     * 类型 whole 整个课付费 Segmentation 分段付费
     */
    @ApiModelProperty(value="类型 whole 整个课付费 Segmentation 分段付费")
    private String type;

    /**
     * 起始课时数
     */
    @ApiModelProperty(value="起始课时数",example="1")
    private Integer upper_limit;

    /**
     * 结束课时数
     */
    @ApiModelProperty(value="结束课时数",example="1")
    private Integer lower_limit;

    /**
     * 价格
     */
    @ApiModelProperty(value="价格",example="1")
    private BigDecimal price;

    /**
     * 当前课时数
     */
    @ApiModelProperty(value="当前课时数",example="1")
    private Long course_num;

    public Long getCourse_num() {
        return course_num;
    }

    public void setCourse_num(Long course_num) {
        this.course_num = course_num;
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
     * 状态 1 不可用 2 可用
     * @return status 状态 1 不可用 2 可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 不可用 2 可用
     * @param status 状态 1 不可用 2 可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型 whole 整个课付费 Segmentation 分段付费
     * @return type 类型 whole 整个课付费 Segmentation 分段付费
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 whole 整个课付费 Segmentation 分段付费
     * @param type 类型 whole 整个课付费 Segmentation 分段付费
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 起始课时数
     * @return upper_limit 起始课时数
     */
    public Integer getUpper_limit() {
        return upper_limit;
    }

    /**
     * 起始课时数
     * @param upper_limit 起始课时数
     */
    public void setUpper_limit(Integer upper_limit) {
        this.upper_limit = upper_limit;
    }

    /**
     * 结束课时数
     * @return lower_limit 结束课时数
     */
    public Integer getLower_limit() {
        return lower_limit;
    }

    /**
     * 结束课时数
     * @param lower_limit 结束课时数
     */
    public void setLower_limit(Integer lower_limit) {
        this.lower_limit = lower_limit;
    }

    /**
     * 价格
     * @return price 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 价格
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}