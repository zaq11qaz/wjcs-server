package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value="直播记录表",description="直播记录表属性说明")
public class MasterSetPriceStreamEntity extends PageInfo {
    /**
     * id
     */
    @ApiModelProperty(value="id",example="1")
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
     * 状态 1 待开播 2 开播 3 结束
     */
    @ApiModelProperty(value="状态 1 待开播 2 开播 3 结束",example="1")
    private Integer status;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id",example="1")
    private Long master_set_price_id;

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
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 直播房间号id
     */
    @ApiModelProperty(value="直播房间号id")
    private String room_id;

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
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String titles;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 观看人数
     */
    @ApiModelProperty(value="观看人数",example="1")
    private Long click_num;

    /**
     * 直播价格
     */
    @ApiModelProperty(value="直播价格",example="1")
    private BigDecimal live_stream_price;

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
     * 直播价格
     */
    @ApiModelProperty(value="直播价格",example="1")
    private String live_stream_prices;

    /**
     * 老师id
     */
    @ApiModelProperty(value="老师id",example="1")
    private Long master_id;

    /**
     * map杂项
     */
    @ApiModelProperty(value="map杂项",example="1")
    private Map<String,Object> map;

    /**
     * 选中商品id
     */
    @ApiModelProperty(value="选中商品id",example="1")
    private Long display_id;

    /**
     * 购买人数
     */
    @ApiModelProperty(value="购买人数",example="1")
    private Integer pay_num;

    public Integer getPay_num() {
        return pay_num;
    }

    public void setPay_num(Integer pay_num) {
        this.pay_num = pay_num;
    }

    public Long getClick_num() {
        return click_num;
    }

    public void setClick_num(Long click_num) {
        this.click_num = click_num;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getPic() {
        return pic;
    }

    public Long getDisplay_id() {
        return display_id;
    }

    public void setDisplay_id(Long display_id) {
        this.display_id = display_id;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
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

    public Long getMaster_id() {
        return master_id;
    }

    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    /**
     * id
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     * @param id id
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
     * 状态 1 待开播 2 开播 3 结束
     * @return status 状态 1 待开播 2 开播 3 结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 待开播 2 开播 3 结束
     * @param status 状态 1 待开播 2 开播 3 结束
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 开始时间
     * @return start_time 开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 开始时间
     * @param start_time 开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 结束时间
     * @return end_time 结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 结束时间
     * @param end_time 结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 直播房间号id
     * @return room_id 直播房间号id
     */
    public String getRoom_id() {
        return room_id;
    }

    /**
     * 直播房间号id
     * @param room_id 直播房间号id
     */
    public void setRoom_id(String room_id) {
        this.room_id = room_id == null ? null : room_id.trim();
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
}