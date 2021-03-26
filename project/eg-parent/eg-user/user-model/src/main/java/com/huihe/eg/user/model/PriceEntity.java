package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="价格",description="价格属性说明")
public class PriceEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 价格/猫币
     */
    @ApiModelProperty(value="价格/猫币",example="1")
    private BigDecimal price;

    /**
     * live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看  class 上课  video 普通视视频
     */
    @ApiModelProperty(value="live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看  class 上课  video 普通视视频")
    private String type;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态",example="1")
    private Integer status;

    /**
     * 
     */
    @ApiModelProperty(value="")
    private Date create_time;

    /**
     * 
     */
    @ApiModelProperty(value="")
    private Date update_time;

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
     * 价格/猫币
     * @return price 价格/猫币
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 价格/猫币
     * @param price 价格/猫币
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看  class 上课  video 普通视视频
     * @return type live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看  class 上课  video 普通视视频
     */
    public String getType() {
        return type;
    }

    /**
     * live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看  class 上课  video 普通视视频
     * @param type live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看  class 上课  video 普通视视频
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 状态
     * @return status 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}