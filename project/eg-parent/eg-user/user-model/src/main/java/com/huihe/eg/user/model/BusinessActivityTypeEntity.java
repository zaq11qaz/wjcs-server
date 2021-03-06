package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value="活动类型",description="活动类型属性说明")
public class BusinessActivityTypeEntity extends PageInfo {
    private static final long serialVersionUID = 7603266179727922361L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Integer id;

    /**
     * 绑定海报id
     */
    @ApiModelProperty(value="绑定海报id",example="1")
    private Long business_mechanism_activity_id;

    /**
     * 类型 salesCourse 活动商品 experience_specials 新用户体验
     */
    @ApiModelProperty(value="类型 salesCourse 活动商品 experience_specials 新用户体验")
    private String type;

    /**
     * 名
     */
    @ApiModelProperty(value="名")
    private String name;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 分享描述
     */
    @ApiModelProperty(value="分享描述")
    private String share_description;

    /**
     * 标题
     */
    @ApiModelProperty(value="描述")
    private String descr;

    /**
     * 参加活动机构ids
     */
    @ApiModelProperty(value="参加活动机构ids")
    private String entry_mechanism_ids;

    /**
     * 1 不可用 2可用 3删除
     */
    @ApiModelProperty(value="1 不可用 2可用 3删除",example="1")
    private Integer status;

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
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="活动开始时间")
    private Date activity_starttime;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="活动结束时间")
    private Date activity_endtime;

    /**
     * 是否仅新人可用 true 是 false 不是
     */
    @ApiModelProperty(value="是否仅新人可用 true 是 false 不是",example="false")
    private Boolean is_new_customers;

    /**
     * 活动可用券数量
     */
    @ApiModelProperty(value="活动可用券数量",example="1")
    private Integer coupon_time;

    /**
     * 课节数
     */
    @ApiModelProperty(value="课节数",example="1")
    private Integer sessions;

    /**
     * 标签
     */
    @ApiModelProperty(value="标签")
    private String tags;

    /**
     * 标签
     */
    @ApiModelProperty(value="图片简介")
    private String image_description;
    /**
     * 标签
     */
    @ApiModelProperty(value="图标")
    private String logo;
    /**
     * 标签
     */
    @ApiModelProperty(value="广告")
    private String banner;
    /**
     * 标签
     */
    @ApiModelProperty(value="赞助图片")
    private String sponsorship_image;
    /**
     * 标签
     */
    @ApiModelProperty(value="礼物图片")
    private String gift_image;

    /**
     * 背景图
     */
    @ApiModelProperty(value="背景图")
    private String background_image;

    /**
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String activity_description;

    /**
     * 来源
     */
    @ApiModelProperty(value="来源")
    private String source;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String url;

    /**
     * 活动类型 mechanism 机构 system 系统
     */
    @ApiModelProperty(value="活动类型 mechanism 机构 system 系统")
    private String activity_type;

    /**
     * 每多少人算一次
     */
    @ApiModelProperty(value="每多少人算一次",example="1")
    private Integer number_of_people;

    /**
     * 每次百分之多少
     */
    @ApiModelProperty(value="每次百分之多少",example="1")
    private BigDecimal each_time_percentage;

    /**
     * 选择数量
     */
    @ApiModelProperty(value="选择数量",example="1")
    private Integer choose_num;

    /**
     * 秒杀状态 1 未开始 2 开始 3 结束
     */
    @ApiModelProperty(value="秒杀状态 1 未开始 2 开始 3 结束 4 未开启",example="1")
    private Integer spike_status;

    /**
     * 点击次数
     */
    @ApiModelProperty(value="点击次数",example="1")
    private Long click_count;

    /**
     * 购买次数
     */
    @ApiModelProperty(value="购买次数",example="1")
    private Long pay_count;

    /**
     * 选择数量价格
     */
    @ApiModelProperty(value="选择数量价格",example="1")
    private BigDecimal pay_amount;

    /**
     * 增加一科金额
     */
    @ApiModelProperty(value="增加一科金额",example="1")
    private BigDecimal add_amount;

    /**
     * 押金
     */
    @ApiModelProperty(value="押金",example="1")
    private BigDecimal pay_deposit;

    /**
     * 最大百分之多少
     */
    @ApiModelProperty(value="最大百分之多少",example="1")
    private BigDecimal each_time_percentage_max;

    /**
     * 原价
     */
    @ApiModelProperty(value="原价",example="1")
    private BigDecimal amount;

    /**
     * 折扣价
     */
    @ApiModelProperty(value="折扣价",example="1")
    private BigDecimal discount_amount;

    /**
     * 单独购买价格
     */
    @ApiModelProperty(value="单独购买价格",example="1")
    private BigDecimal separate_amount;

    /**
     * map
     */
    @ApiModelProperty(value="map",example="1")
    private Map<String,Object> map;

    public BigDecimal getSeparate_amount() {
        return separate_amount;
    }

    public void setSeparate_amount(BigDecimal separate_amount) {
        this.separate_amount = separate_amount;
    }

    public String getShare_description() {
        return share_description;
    }

    public void setShare_description(String share_description) {
        this.share_description = share_description;
    }

    public Integer getSpike_status() {
        return spike_status;
    }

    public void setSpike_status(Integer spike_status) {
        this.spike_status = spike_status;
    }

    public Date getActivity_starttime() {
        return activity_starttime;
    }

    public void setActivity_starttime(Date activity_starttime) {
        this.activity_starttime = activity_starttime;
    }

    public Date getActivity_endtime() {
        return activity_endtime;
    }

    public void setActivity_endtime(Date activity_endtime) {
        this.activity_endtime = activity_endtime;
    }

    public Long getClick_count() {
        return click_count;
    }

    public void setClick_count(Long click_count) {
        this.click_count = click_count;
    }

    public Long getPay_count() {
        return pay_count;
    }

    public void setPay_count(Long pay_count) {
        this.pay_count = pay_count;
    }

    public BigDecimal getPay_deposit() {
        return pay_deposit;
    }

    public void setPay_deposit(BigDecimal pay_deposit) {
        this.pay_deposit = pay_deposit;
    }

    public Long getBusiness_mechanism_activity_id() {
        return business_mechanism_activity_id;
    }

    public void setBusiness_mechanism_activity_id(Long business_mechanism_activity_id) {
        this.business_mechanism_activity_id = business_mechanism_activity_id;
    }

    public Integer getChoose_num() {
        return choose_num;
    }

    public void setChoose_num(Integer choose_num) {
        this.choose_num = choose_num;
    }

    public BigDecimal getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(BigDecimal pay_amount) {
        this.pay_amount = pay_amount;
    }

    public BigDecimal getAdd_amount() {
        return add_amount;
    }

    public void setAdd_amount(BigDecimal add_amount) {
        this.add_amount = add_amount;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getEntry_mechanism_ids() {
        return entry_mechanism_ids;
    }

    public void setEntry_mechanism_ids(String entry_mechanism_ids) {
        this.entry_mechanism_ids = entry_mechanism_ids;
    }

    public Integer getSessions() {
        return sessions;
    }

    public void setSessions(Integer sessions) {
        this.sessions = sessions;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(BigDecimal discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getImage_description() {
        return image_description;
    }

    public void setImage_description(String image_description) {
        this.image_description = image_description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getSponsorship_image() {
        return sponsorship_image;
    }

    public void setSponsorship_image(String sponsorship_image) {
        this.sponsorship_image = sponsorship_image;
    }

    public String getGift_image() {
        return gift_image;
    }

    public void setGift_image(String gift_image) {
        this.gift_image = gift_image;
    }

    /**
     * 主键id
     * @return id 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 类型 salesCourse 活动商品 experience_specials 新用户体验
     * @return type 类型 salesCourse 活动商品 experience_specials 新用户体验
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 salesCourse 活动商品 experience_specials 新用户体验
     * @param type 类型 salesCourse 活动商品 experience_specials 新用户体验
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 名
     * @return name 名
     */
    public String getName() {
        return name;
    }

    /**
     * 名
     * @param name 名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 1 不可用 2可用 3删除
     * @return status 1 不可用 2可用 3删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 不可用 2可用 3删除
     * @param status 1 不可用 2可用 3删除
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 是否仅新人可用 true 是 false 不是
     * @return is_new_customers 是否仅新人可用 true 是 false 不是
     */
    public Boolean getIs_new_customers() {
        return is_new_customers;
    }

    /**
     * 是否仅新人可用 true 是 false 不是
     * @param is_new_customers 是否仅新人可用 true 是 false 不是
     */
    public void setIs_new_customers(Boolean is_new_customers) {
        this.is_new_customers = is_new_customers;
    }

    /**
     * 活动可用券数量
     * @return coupon_time 活动可用券数量
     */
    public Integer getCoupon_time() {
        return coupon_time;
    }

    /**
     * 活动可用券数量
     * @param coupon_time 活动可用券数量
     */
    public void setCoupon_time(Integer coupon_time) {
        this.coupon_time = coupon_time;
    }

    /**
     * 标签
     * @return tags 标签
     */
    public String getTags() {
        return tags;
    }

    /**
     * 标签
     * @param tags 标签
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    /**
     * 背景图
     * @return background_image 背景图
     */
    public String getBackground_image() {
        return background_image;
    }

    /**
     * 背景图
     * @param background_image 背景图
     */
    public void setBackground_image(String background_image) {
        this.background_image = background_image == null ? null : background_image.trim();
    }

    /**
     * 说明
     * @return activity_description 说明
     */
    public String getActivity_description() {
        return activity_description;
    }

    /**
     * 说明
     * @param activity_description 说明
     */
    public void setActivity_description(String activity_description) {
        this.activity_description = activity_description == null ? null : activity_description.trim();
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
     * 每多少人算一次
     * @return number_of_people 每多少人算一次
     */
    public Integer getNumber_of_people() {
        return number_of_people;
    }

    /**
     * 每多少人算一次
     * @param number_of_people 每多少人算一次
     */
    public void setNumber_of_people(Integer number_of_people) {
        this.number_of_people = number_of_people;
    }

    /**
     * 每次百分之多少
     * @return each_time_percentage 每次百分之多少
     */
    public BigDecimal getEach_time_percentage() {
        return each_time_percentage;
    }

    /**
     * 每次百分之多少
     * @param each_time_percentage 每次百分之多少
     */
    public void setEach_time_percentage(BigDecimal each_time_percentage) {
        this.each_time_percentage = each_time_percentage;
    }

    /**
     * 最大百分之多少
     * @return each_time_percentage_max 最大百分之多少
     */
    public BigDecimal getEach_time_percentage_max() {
        return each_time_percentage_max;
    }

    /**
     * 最大百分之多少
     * @param each_time_percentage_max 最大百分之多少
     */
    public void setEach_time_percentage_max(BigDecimal each_time_percentage_max) {
        this.each_time_percentage_max = each_time_percentage_max;
    }
}