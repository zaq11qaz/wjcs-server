package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="",description="属性说明")
public class MasterSetPriceDisplayEntity extends PageInfo {
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
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id",example="1")
    private Long master_set_price_id;

    /**
     * 直播id
     */
    @ApiModelProperty(value="直播id",example="1")
    private Long live_streaming_id;

    /**
     * 状态 1 下架 2 上架
     */
    @ApiModelProperty(value="状态 1 下架 2 上架",example="1")
    private Integer status;

    /**
     * 课时数
     */
    @ApiModelProperty(value="课时数",example="1")
    private Integer course_num;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序",example="1")
    private Integer sorted;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品ids",example="1")
    private String master_set_price_ids;

    /**
     * 图片
     */
    @ApiModelProperty(value="图片",example="1")
    private String pic;


    /**
     * 直播价格
     */
    @ApiModelProperty(value="直播价格",example="1")
    private String live_stream_prices;

    /**
     * 原价
     */
    @ApiModelProperty(value="原价",example="1")
    private BigDecimal original_price;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String titles;


    /**
     * 直播价格
     */
    @ApiModelProperty(value="直播价格",example="1")
    private BigDecimal live_stream_price;

    /**
     * 收益
     */
    @ApiModelProperty(value="收益",example="1")
    private BigDecimal earnings;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 直播单节课价格
     */
    @ApiModelProperty(value="直播单节课价格",example="1")
    private BigDecimal living_single_session_price;

    /**
     * 直播单节课价格集合
     */
    @ApiModelProperty(value="直播单节课价格集合",example="1")
    private String living_single_session_prices;


    /**
     * 是否正在直播
     */
    @ApiModelProperty(value="是否正在直播",example="false")
    private Boolean is_live_streaming;

    public Integer getCourse_num() {
        return course_num;
    }

    public void setCourse_num(Integer course_num) {
        this.course_num = course_num;
    }

    public String getPic() {
        return pic;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public BigDecimal getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(BigDecimal original_price) {
        this.original_price = original_price;
    }

    public BigDecimal getLiving_single_session_price() {
        return living_single_session_price;
    }

    public void setLiving_single_session_price(BigDecimal living_single_session_price) {
        this.living_single_session_price = living_single_session_price;
    }

    public String getLiving_single_session_prices() {
        return living_single_session_prices;
    }

    public void setLiving_single_session_prices(String living_single_session_prices) {
        this.living_single_session_prices = living_single_session_prices;
    }

    public Long getLive_streaming_id() {
        return live_streaming_id;
    }

    public void setLive_streaming_id(Long live_streaming_id) {
        this.live_streaming_id = live_streaming_id;
    }

    public String getMaster_set_price_ids() {
        return master_set_price_ids;
    }

    public void setMaster_set_price_ids(String master_set_price_ids) {
        this.master_set_price_ids = master_set_price_ids;
    }

    public String getLive_stream_prices() {
        return live_stream_prices;
    }

    public void setLive_stream_prices(String live_stream_prices) {
        this.live_stream_prices = live_stream_prices;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
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
     * 状态 1 下架 2 上架
     * @return status 状态 1 下架 2 上架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 下架 2 上架
     * @param status 状态 1 下架 2 上架
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 直播价格
     * @return live_stream_price 直播价格
     */
    public BigDecimal getLive_stream_price() {
        return live_stream_price;
    }

    /**
     * 直播价格
     * @param live_stream_price 直播价格
     */
    public void setLive_stream_price(BigDecimal live_stream_price) {
        this.live_stream_price = live_stream_price;
    }

    /**
     * 收益
     * @return earnings 收益
     */
    public BigDecimal getEarnings() {
        return earnings;
    }

    /**
     * 收益
     * @param earnings 收益
     */
    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
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
     * 是否正在直播
     * @return is_live_streaming 是否正在直播
     */
    public Boolean getIs_live_streaming() {
        return is_live_streaming;
    }

    /**
     * 是否正在直播
     * @param is_live_streaming 是否正在直播
     */
    public void setIs_live_streaming(Boolean is_live_streaming) {
        this.is_live_streaming = is_live_streaming;
    }
}