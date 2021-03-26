package com.huihe.eg.mall.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="商品信息",description="商品信息属性说明")
public class CommodityInfoEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 市场价
     */
    @ApiModelProperty(value="市场价",example="1")
    private BigDecimal market_price;

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
     * 状态 1下架  2 上架  
     */
    @ApiModelProperty(value="状态 1下架  2 上架  ",example="1")
    private Integer status;

    /**
     * 展示图
     */
    @ApiModelProperty(value="展示图")
    private String show_case;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型",example="1")
    private Long type_id;

    /**
     * 产品参数
     */
    @ApiModelProperty(value="产品参数")
    private String product_parameters;

    /**
     * 围观人数
     */
    @ApiModelProperty(value="围观人数",example="1")
    private Integer watch_number;

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
     * 标题
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 市场价
     * @return market_price 市场价
     */
    public BigDecimal getMarket_price() {
        return market_price;
    }

    /**
     * 市场价
     * @param market_price 市场价
     */
    public void setMarket_price(BigDecimal market_price) {
        this.market_price = market_price;
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
     * 状态 1下架  2 上架  
     * @return status 状态 1下架  2 上架  
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1下架  2 上架  
     * @param status 状态 1下架  2 上架  
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 展示图
     * @return show_case 展示图
     */
    public String getShow_case() {
        return show_case;
    }

    /**
     * 展示图
     * @param show_case 展示图
     */
    public void setShow_case(String show_case) {
        this.show_case = show_case == null ? null : show_case.trim();
    }

    /**
     * 类型
     * @return type_id 类型
     */
    public Long getType_id() {
        return type_id;
    }

    /**
     * 类型
     * @param type_id 类型
     */
    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    /**
     * 产品参数
     * @return product_parameters 产品参数
     */
    public String getProduct_parameters() {
        return product_parameters;
    }

    /**
     * 产品参数
     * @param product_parameters 产品参数
     */
    public void setProduct_parameters(String product_parameters) {
        this.product_parameters = product_parameters == null ? null : product_parameters.trim();
    }

    /**
     * 围观人数
     * @return watch_number 围观人数
     */
    public Integer getWatch_number() {
        return watch_number;
    }

    /**
     * 围观人数
     * @param watch_number 围观人数
     */
    public void setWatch_number(Integer watch_number) {
        this.watch_number = watch_number;
    }
}