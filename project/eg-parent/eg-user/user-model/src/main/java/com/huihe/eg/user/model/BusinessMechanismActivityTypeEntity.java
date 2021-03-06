package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value="机构活动列表",description="机构活动列表属性说明")
public class BusinessMechanismActivityTypeEntity extends PageInfo {
    private static final long serialVersionUID = 6122564967378277415L;
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
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date activity_starttime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value="活动结束时间")
    private Date activity_endtime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 状态 1 未开始 2 开始 3 过期 4 删除 5 违规
     */
    @ApiModelProperty(value="状态 1 未开始 2 开始 3 过期 4 删除 5 违规",example="1")
    private Integer status;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 机构旗帜
     */
    @ApiModelProperty(value="机构旗帜")
    private String mechanism_banner;

    /**
     * 分享描述
     */
    @ApiModelProperty(value="分享描述")
    private String share_description;

    /**
     * 机构地址
     */
    @ApiModelProperty(value="机构地址")
    private String mechanism_address;

    /**
     * 弹窗图片
     */
    @ApiModelProperty(value="弹窗图片")
    private String alert_pic;

    /**
     * 课程表图片
     */
    @ApiModelProperty(value="课程表图片")
    private String schedule_pic;

    /**
     * 机构描述
     */
    @ApiModelProperty(value="机构描述")
    private String mechanism_description;

    /**
     * 活动地址
     */
    @ApiModelProperty(value="活动地址")
    private String activity_address;

    /**
     * 学生图片
     */
    @ApiModelProperty(value="学生图片")
    private String students_pic;

    /**
     * 平台
     */
    @ApiModelProperty(value="平台")
    private String platform;

    /**
     * 环境图片
     */
    @ApiModelProperty(value="环境图片")
    private String environment_pic;

    /**
     * 类型  mechanism 机构 system 系统
     */
    @ApiModelProperty(value="类型  mechanism 机构 system 系统")
    private String activity_type;

    /**
     * 活动描述
     */
    @ApiModelProperty(value="活动描述")
    private String activity_description;

    /**
     * 赞助商ids
     */
    @ApiModelProperty(value="赞助商ids")
    private String sponsors_ids;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Long activity_id;

    /**
     * 秒杀状态 1 未开始 2 开始 3 结束
     */
    @ApiModelProperty(value="秒杀状态 1 未开始 2 开始 3 结束",example="1")
    private Integer spike_status;

    /**
     * 赞助商id
     */
    @ApiModelProperty(value="赞助商id",example="1")
    private Long sponsor_id;

    /**
     * 坐标纬度值
     */
    @ApiModelProperty(value="坐标纬度值",example="1")
    private BigDecimal latitude;


    /**
     * 坐标经度值
     */
    @ApiModelProperty(value="坐标经度值",example="1")
    private BigDecimal longitude;

    @ApiModelProperty(value="距离",example="1")
    private BigDecimal distance;

    @ApiModelProperty(value="每多少人算一次",example="1")
    private Integer number_of_people;

    @ApiModelProperty(value="每算一次多少钱",example="1")
    private BigDecimal each_time_percentage;


    /**
     * 活动标题
     */
    @ApiModelProperty(value="活动标题")
    private String activity_title;

    /**
     * 邀请码
     */
    @ApiModelProperty(value="邀请码")
    private String invite_code;

    /**
     * 礼物图片
     */
    @ApiModelProperty(value="礼物图片")
    private String gift_pic;

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
     * 科目
     */
    @ApiModelProperty(value="科目")
    private String categories;

    /**
     * 参加的活动类型
     */
    @ApiModelProperty(value="参加的活动类型")
    private String type;

    /**
     * 二维码图片
     */
    @ApiModelProperty(value="二维码图片")
    private String qrcode;

    /**
     * 图标
     */
    @ApiModelProperty(value="图标")
    private String logo;

    /**
     * 声明 ? 换行
     */
    @ApiModelProperty(value="声明 ? 换行")
    private String statement;

    /**
     * 赞助商名字
     */
    @ApiModelProperty(value="赞助商名字")
    private String sponsors_name;

    /**
     * 赞助商简介
     */
    @ApiModelProperty(value="赞助商简介")
    private String sponsors_description;

    /**
     * 赞助商图片
     */
    @ApiModelProperty(value="赞助商图片")
    private String sponsors_pic;

    /**
     * 分享浮标
     */
    @ApiModelProperty(value="分享浮标")
    private String share_buoy_pic;

    /**
     * 定金浮标
     */
    @ApiModelProperty(value="定金浮标")
    private String deposit_float_pic;

    /**
     * 赞助商联系人
     */
    @ApiModelProperty(value="赞助商联系人")
    private String sponsors_contect;

    /**
     * 赞助商手机号
     */
    @ApiModelProperty(value="赞助商手机号")
    private String sponsors_phone;

    /**
     * 赞助商地址
     */
    @ApiModelProperty(value="赞助商地址")
    private String sponsors_addr;

    /**
     * 赞助商二维码
     */
    @ApiModelProperty(value="赞助商二维码")
    private String sponsors_qrcode;

    /**
     * map
     */
    @ApiModelProperty(value="map")
    private Map<String,Object> map;

    public Integer getNumber_of_people() {
        return number_of_people;
    }

    public void setNumber_of_people(Integer number_of_people) {
        this.number_of_people = number_of_people;
    }

    public BigDecimal getEach_time_percentage() {
        return each_time_percentage;
    }

    public void setEach_time_percentage(BigDecimal each_time_percentage) {
        this.each_time_percentage = each_time_percentage;
    }

    public Date getActivity_endtime() {
        return activity_endtime;
    }

    public void setActivity_endtime(Date activity_endtime) {
        this.activity_endtime = activity_endtime;
    }

    public String getShare_description() {
        return share_description;
    }

    public void setShare_description(String share_description) {
        this.share_description = share_description;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getActivity_starttime() {
        return activity_starttime;
    }

    public void setActivity_starttime(Date activity_starttime) {
        this.activity_starttime = activity_starttime;
    }

    public Integer getSpike_status() {
        return spike_status;
    }

    public void setSpike_status(Integer spike_status) {
        this.spike_status = spike_status;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getSponsors_ids() {
        return sponsors_ids;
    }

    public void setSponsors_ids(String sponsors_ids) {
        this.sponsors_ids = sponsors_ids;
    }

    public Long getSponsor_id() {
        return sponsor_id;
    }

    public void setSponsor_id(Long sponsor_id) {
        this.sponsor_id = sponsor_id;
    }

    public String getShare_buoy_pic() {
        return share_buoy_pic;
    }

    public void setShare_buoy_pic(String share_buoy_pic) {
        this.share_buoy_pic = share_buoy_pic;
    }

    public String getDeposit_float_pic() {
        return deposit_float_pic;
    }

    public void setDeposit_float_pic(String deposit_float_pic) {
        this.deposit_float_pic = deposit_float_pic;
    }

    public String getSchedule_pic() {
        return schedule_pic;
    }

    public void setSchedule_pic(String schedule_pic) {
        this.schedule_pic = schedule_pic;
    }

    public String getAlert_pic() {
        return alert_pic;
    }

    public void setAlert_pic(String alert_pic) {
        this.alert_pic = alert_pic;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getSeparate_amount() {
        return separate_amount;
    }

    public void setSeparate_amount(BigDecimal separate_amount) {
        this.separate_amount = separate_amount;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 状态 1 未开始 2 开始 3 过期 4 删除 5 违规
     * @return status 状态 1 未开始 2 开始 3 过期 4 删除 5 违规
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 未开始 2 开始 3 过期 4 删除 5 违规
     * @param status 状态 1 未开始 2 开始 3 过期 4 删除 5 违规
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
     * 机构旗帜
     * @return mechanism_banner 机构旗帜
     */
    public String getMechanism_banner() {
        return mechanism_banner;
    }

    /**
     * 机构旗帜
     * @param mechanism_banner 机构旗帜
     */
    public void setMechanism_banner(String mechanism_banner) {
        this.mechanism_banner = mechanism_banner == null ? null : mechanism_banner.trim();
    }

    /**
     * 机构地址
     * @return mechanism_address 机构地址
     */
    public String getMechanism_address() {
        return mechanism_address;
    }

    /**
     * 机构地址
     * @param mechanism_address 机构地址
     */
    public void setMechanism_address(String mechanism_address) {
        this.mechanism_address = mechanism_address == null ? null : mechanism_address.trim();
    }

    /**
     * 机构描述
     * @return mechanism_description 机构描述
     */
    public String getMechanism_description() {
        return mechanism_description;
    }

    /**
     * 机构描述
     * @param mechanism_description 机构描述
     */
    public void setMechanism_description(String mechanism_description) {
        this.mechanism_description = mechanism_description == null ? null : mechanism_description.trim();
    }

    /**
     * 活动地址
     * @return activity_address 活动地址
     */
    public String getActivity_address() {
        return activity_address;
    }

    /**
     * 活动地址
     * @param activity_address 活动地址
     */
    public void setActivity_address(String activity_address) {
        this.activity_address = activity_address == null ? null : activity_address.trim();
    }

    /**
     * 学生图片
     * @return students_pic 学生图片
     */
    public String getStudents_pic() {
        return students_pic;
    }

    /**
     * 学生图片
     * @param students_pic 学生图片
     */
    public void setStudents_pic(String students_pic) {
        this.students_pic = students_pic == null ? null : students_pic.trim();
    }

    /**
     * 环境图片
     * @return environment_pic 环境图片
     */
    public String getEnvironment_pic() {
        return environment_pic;
    }

    /**
     * 环境图片
     * @param environment_pic 环境图片
     */
    public void setEnvironment_pic(String environment_pic) {
        this.environment_pic = environment_pic == null ? null : environment_pic.trim();
    }

    /**
     * 类型  mechanism 机构 system 系统
     * @return activity_type 类型  mechanism 机构 system 系统
     */
    public String getActivity_type() {
        return activity_type;
    }

    /**
     * 类型  mechanism 机构 system 系统
     * @param activity_type 类型  mechanism 机构 system 系统
     */
    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type == null ? null : activity_type.trim();
    }

    /**
     * 活动描述
     * @return activity_description 活动描述
     */
    public String getActivity_description() {
        return activity_description;
    }

    /**
     * 活动描述
     * @param activity_description 活动描述
     */
    public void setActivity_description(String activity_description) {
        this.activity_description = activity_description == null ? null : activity_description.trim();
    }

    /**
     * 活动id
     * @return activity_id 活动id
     */
    public Long getActivity_id() {
        return activity_id;
    }

    /**
     * 活动id
     * @param activity_id 活动id
     */
    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    /**
     * 活动标题
     * @return activity_title 活动标题
     */
    public String getActivity_title() {
        return activity_title;
    }

    /**
     * 活动标题
     * @param activity_title 活动标题
     */
    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title == null ? null : activity_title.trim();
    }

    /**
     * 礼物图片
     * @return gift_pic 礼物图片
     */
    public String getGift_pic() {
        return gift_pic;
    }

    /**
     * 礼物图片
     * @param gift_pic 礼物图片
     */
    public void setGift_pic(String gift_pic) {
        this.gift_pic = gift_pic == null ? null : gift_pic.trim();
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
     * 原价
     * @return amount 原价
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 原价
     * @param amount 原价
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 折扣价
     * @return discount_amount 折扣价
     */
    public BigDecimal getDiscount_amount() {
        return discount_amount;
    }

    /**
     * 折扣价
     * @param discount_amount 折扣价
     */
    public void setDiscount_amount(BigDecimal discount_amount) {
        this.discount_amount = discount_amount;
    }

    /**
     * 科目
     * @return categories 科目
     */
    public String getCategories() {
        return categories;
    }

    /**
     * 科目
     * @param categories 科目
     */
    public void setCategories(String categories) {
        this.categories = categories == null ? null : categories.trim();
    }

    /**
     * 参加的活动类型
     * @return type 参加的活动类型
     */
    public String getType() {
        return type;
    }

    /**
     * 参加的活动类型
     * @param type 参加的活动类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 二维码图片
     * @return qrcode 二维码图片
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 二维码图片
     * @param qrcode 二维码图片
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    /**
     * 图标
     * @return logo 图标
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 图标
     * @param logo 图标
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * 声明 ? 换行
     * @return statement 声明 ? 换行
     */
    public String getStatement() {
        return statement;
    }

    /**
     * 声明 ? 换行
     * @param statement 声明 ? 换行
     */
    public void setStatement(String statement) {
        this.statement = statement == null ? null : statement.trim();
    }

    /**
     * 赞助商名字
     * @return sponsors_name 赞助商名字
     */
    public String getSponsors_name() {
        return sponsors_name;
    }

    /**
     * 赞助商名字
     * @param sponsors_name 赞助商名字
     */
    public void setSponsors_name(String sponsors_name) {
        this.sponsors_name = sponsors_name == null ? null : sponsors_name.trim();
    }

    /**
     * 赞助商简介
     * @return sponsors_description 赞助商简介
     */
    public String getSponsors_description() {
        return sponsors_description;
    }

    /**
     * 赞助商简介
     * @param sponsors_description 赞助商简介
     */
    public void setSponsors_description(String sponsors_description) {
        this.sponsors_description = sponsors_description == null ? null : sponsors_description.trim();
    }

    /**
     * 赞助商图片
     * @return sponsors_pic 赞助商图片
     */
    public String getSponsors_pic() {
        return sponsors_pic;
    }

    /**
     * 赞助商图片
     * @param sponsors_pic 赞助商图片
     */
    public void setSponsors_pic(String sponsors_pic) {
        this.sponsors_pic = sponsors_pic == null ? null : sponsors_pic.trim();
    }

    /**
     * 赞助商联系人
     * @return sponsors_contect 赞助商联系人
     */
    public String getSponsors_contect() {
        return sponsors_contect;
    }

    /**
     * 赞助商联系人
     * @param sponsors_contect 赞助商联系人
     */
    public void setSponsors_contect(String sponsors_contect) {
        this.sponsors_contect = sponsors_contect == null ? null : sponsors_contect.trim();
    }

    /**
     * 赞助商手机号
     * @return sponsors_phone 赞助商手机号
     */
    public String getSponsors_phone() {
        return sponsors_phone;
    }

    /**
     * 赞助商手机号
     * @param sponsors_phone 赞助商手机号
     */
    public void setSponsors_phone(String sponsors_phone) {
        this.sponsors_phone = sponsors_phone == null ? null : sponsors_phone.trim();
    }

    /**
     * 赞助商地址
     * @return sponsors_addr 赞助商地址
     */
    public String getSponsors_addr() {
        return sponsors_addr;
    }

    /**
     * 赞助商地址
     * @param sponsors_addr 赞助商地址
     */
    public void setSponsors_addr(String sponsors_addr) {
        this.sponsors_addr = sponsors_addr == null ? null : sponsors_addr.trim();
    }

    /**
     * 赞助商二维码
     * @return sponsors_qrcode 赞助商二维码
     */
    public String getSponsors_qrcode() {
        return sponsors_qrcode;
    }

    /**
     * 赞助商二维码
     * @param sponsors_qrcode 赞助商二维码
     */
    public void setSponsors_qrcode(String sponsors_qrcode) {
        this.sponsors_qrcode = sponsors_qrcode == null ? null : sponsors_qrcode.trim();
    }

}