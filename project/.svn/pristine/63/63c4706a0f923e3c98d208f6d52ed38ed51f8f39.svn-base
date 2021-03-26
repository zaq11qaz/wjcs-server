package com.huihe.eg.mall.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value="用户竞拍记录",description="用户竞拍记录属性说明")
public class AuctionRecordEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

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
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long user_id;

    /**
     * 竞拍商品id
     */
    @ApiModelProperty(value="竞拍商品id",example="1")
    private Long auction_commondity_id;

    /**
     * 状态 1领先  2出局
     */
    @ApiModelProperty(value="状态 1领先  2出局",example="1")
    private Integer status;

    /**
     * 出价
     */
    @ApiModelProperty(value="出价",example="1")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    /**
     * 用户地址
     */
    @ApiModelProperty(value="用户地址")
    private String address;

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
     * 
     * @return user_id 
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 
     * @param user_id 
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 竞拍商品id
     * @return auction_commondity_id 竞拍商品id
     */
    public Long getAuction_commondity_id() {
        return auction_commondity_id;
    }

    /**
     * 竞拍商品id
     * @param auction_commondity_id 竞拍商品id
     */
    public void setAuction_commondity_id(Long auction_commondity_id) {
        this.auction_commondity_id = auction_commondity_id;
    }

    /**
     * 状态 1领先  2出局
     * @return status 状态 1领先  2出局
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1领先  2出局
     * @param status 状态 1领先  2出局
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 出价
     * @return offer_price 出价
     */
    public BigDecimal getOffer_price() {
        return offer_price;
    }

    /**
     * 出价
     * @param offer_price 出价
     */
    public void setOffer_price(BigDecimal offer_price) {
        this.offer_price = offer_price;
    }

    /**
     * 用户地址
     * @return address 用户地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 用户地址
     * @param address 用户地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}