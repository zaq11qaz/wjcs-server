package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="活动列表",description="活动列表属性说明")
public class BusinessActivityEntity extends PageInfo {
    private static final long serialVersionUID = 9084972227939531689L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 主键id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Long business_activity_type_id;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * insertIds
     */
    @ApiModelProperty(value="insertIds")
    private List<Long> insertIds;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 1未开始 2已开始 3已结束
     */
    @ApiModelProperty(value="1未开始 2已开始 3已结束",example="1")
    private Integer status;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 活动说明
     */
    @ApiModelProperty(value="活动说明")
    private String activity_show;

    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 背景图
     */
    @ApiModelProperty(value="背景图")
    private String background_pic;

    /**
     * 图片
     */
    @ApiModelProperty(value="图片")
    private String pic;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String url;

    /**
     * 来源
     */
    @ApiModelProperty(value="来源")
    private String source;

    /**
     * 价格
     */
    @ApiModelProperty(value="价格")
    private BigDecimal price;

    /**
     * 价格
     */
    @ApiModelProperty(value="价格")
    private BigDecimal discount_amount;

    /**
     * 参加活动商品id
     */
    @ApiModelProperty(value="参加活动商品id")
    private Long master_set_price_id;

    /**
     * 是否仅新客可用
     */
    @ApiModelProperty(value="是否仅新客可用")
    private Boolean is_new_customers;

    /**
     * 商品id集合
     */
    @ApiModelProperty(value="商品id集合")
    private String master_set_price_ids;

    /**
     * 商品价格集合
     */
    @ApiModelProperty(value="商品价格集合")
    private String price_list;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id")
    private Long mechanism_id;

    /**
     * 可用券次数
     */
    @ApiModelProperty(value="可用券次数")
    private Integer coupon_time;

    /**
     * 每多少人算一次
     */
    @ApiModelProperty(value="每多少人算一次")
    private Integer number_of_people;

    /**
     * 每次百分之多少
     */
    @ApiModelProperty(value="每次百分之多少")
    private Double each_time_percentage;

    /**
     * 最大百分之多少
     */
    @ApiModelProperty(value="最大百分之多少")
    private Double each_time_percentage_max;

    /**
     * 标签
     */
    @ApiModelProperty(value="标签")
    private String tags;

    /**
     * 标题列表
     */
    @ApiModelProperty(value="标题列表")
    private String titleList;

    /**
     * map杂项
     */
    @ApiModelProperty(value="map杂项")
    private Map<String,Object> map;

    public List<Long> getInsertIds() {
        return insertIds;
    }

    public void setInsertIds(List<Long> insertIds) {
        this.insertIds = insertIds;
    }

    public String getPrice_list() {
        return price_list;
    }

    public void setPrice_list(String price_list) {
        this.price_list = price_list;
    }

    public String getTitleList() {
        return titleList;
    }

    public void setTitleList(String titleList) {
        this.titleList = titleList;
    }

    public Long getBusiness_activity_type_id() {
        return business_activity_type_id;
    }

    public void setBusiness_activity_type_id(Long business_activity_type_id) {
        this.business_activity_type_id = business_activity_type_id;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getTags() {
        return tags;
    }

    public BigDecimal getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(BigDecimal discount_amount) {
        this.discount_amount = discount_amount;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getEach_time_percentage() {
        return each_time_percentage;
    }

    public void setEach_time_percentage(Double each_time_percentage) {
        this.each_time_percentage = each_time_percentage;
    }

    public Double getEach_time_percentage_max() {
        return each_time_percentage_max;
    }

    public void setEach_time_percentage_max(Double each_time_percentage_max) {
        this.each_time_percentage_max = each_time_percentage_max;
    }

    public Integer getNumber_of_people() {
        return number_of_people;
    }

    public void setNumber_of_people(Integer number_of_people) {
        this.number_of_people = number_of_people;
    }

    public Integer getCoupon_time() {
        return coupon_time;
    }

    public void setCoupon_time(Integer coupon_time) {
        this.coupon_time = coupon_time;
    }

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    public String getMaster_set_price_ids() {
        return master_set_price_ids;
    }

    public void setMaster_set_price_ids(String master_set_price_ids) {
        this.master_set_price_ids = master_set_price_ids;
    }

    public Boolean getIs_new_customers() {
        return is_new_customers;
    }

    public void setIs_new_customers(Boolean is_new_customers) {
        this.is_new_customers = is_new_customers;
    }

    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * 1未开始 2已开始 3已结束
     * @return status 1未开始 2已开始 3已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1未开始 2已开始 3已结束
     * @param status 1未开始 2已开始 3已结束
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 活动说明
     * @return activity_show 活动说明
     */
    public String getActivity_show() {
        return activity_show;
    }

    /**
     * 活动说明
     * @param activity_show 活动说明
     */
    public void setActivity_show(String activity_show) {
        this.activity_show = activity_show == null ? null : activity_show.trim();
    }

    /**
     * 内容
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 背景图
     * @return background_pic 背景图
     */
    public String getBackground_pic() {
        return background_pic;
    }

    /**
     * 背景图
     * @param background_pic 背景图
     */
    public void setBackground_pic(String background_pic) {
        this.background_pic = background_pic == null ? null : background_pic.trim();
    }

    /**
     * 图片
     * @return pic 图片
     */
    public String getPic() {
        return pic;
    }

    /**
     * 图片
     * @param pic 图片
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * 地址
     * @return url 地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 地址
     * @param url 地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 来源
     * @return source 来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 来源
     * @param source 来源
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}