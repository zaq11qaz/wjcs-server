package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="外教添加出售课程",description="外教添加出售课程属性说明")
@Document(indexName = "price",type = "mastersetprice",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class MasterSetPriceEntity extends PageInfo {
    private static final long serialVersionUID = 3440183438081305177L;
    /**
     * 主键id
     */
    @Id
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 开始时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 修改时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 结束时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 课程大标题
     */
    @Field
    @ApiModelProperty(value="课程大标题")
    private String title;

    /**
     * 校区
     */
    @Field
    @ApiModelProperty(value="校区")
    private String school_district;

    /**
     * 商品类型
     */
    @Field
    @ApiModelProperty(value="商品类型")
    private String commodity_type;

    /**
     * 课节数
     */
    @Field
    @ApiModelProperty(value="课节数",example="1")
    private Integer course_num;

    /**
     * 序号
     */
    @Field
    @ApiModelProperty(value="序号",example="1")
    private Integer sorted;

    /**
     * 原价
     */
    @Field
    @ApiModelProperty(value="原价",example="1")
    private BigDecimal amout;

    /**
     * 服务类型 1 线上 2 线下
     */
    @Field
    @ApiModelProperty(value="服务类型 1 线上 2 线下",example="1")
    private Integer service_type;

    /**
     * 折扣
     */
    @Field
    @ApiModelProperty(value="折扣",example="1")
    private BigDecimal discount;

    /**
     * 折扣价
     */
    @Field
    @ApiModelProperty(value="折扣价",example="1")
    private BigDecimal discount_amout;

    /**
     * 活动价
     */
    @Field
    @ApiModelProperty(value="活动价",example="1")
    private BigDecimal activity_price;

    /**
     * 单节课价
     */
    @Field
    @ApiModelProperty(value="单节课价",example="1")
    private BigDecimal price_per_session;

    /**
     * 单节课价格
     */
    @Field
    @ApiModelProperty(value="单节课价格",example="1")
    private BigDecimal original_price;

    /**
     * 默认折扣价
     */
    @Field
    @ApiModelProperty(value="默认折扣价",example="1")
    private BigDecimal default_discount_price;

    /**
     * 助学师的user_id
     */
    @Field
    @ApiModelProperty(value="助学师的user_id",example="1")
    private Long user_id;

    /**
     * 教学大纲
     */
    @Field
    @ApiModelProperty(value="教学大纲")
    private String introduction_content;

    /**
     * 适用年龄
     */
    @Field
    @ApiModelProperty(value="适用年龄")
    private String apply_age;

    /**
     * 状态 2 生效 1 无效 3 草稿 4 不展示
     */
    @Field
    @ApiModelProperty(value="状态 2 生效 1 无效 3 草稿 4 不展示",example="1")
    private Integer status;

    /**
     * 课程时长
     */
    @Field
    @ApiModelProperty(value="课程时长",example="1")
    private Integer length_of_lesson;

    /**
     * 标题加视频列表
     */
    @Field
    @ApiModelProperty(value="标题加视频列表")
    private String titile_url;

    /**
     * 是否第一节免费
     */
    @Field
    @ApiModelProperty(value="是否第一节免费",example="false")
    private Boolean first_free;

    /**
     * 是否启蒙商品
     */
    @Field
    @ApiModelProperty(value="是否启蒙商品",example="false")
    private Boolean is_enlightenment;

    /**
     * 是否正在直播
     */
    @Field
    @ApiModelProperty(value="是否正在直播",example="false")
    private Boolean is_live_streaming;

    /**
     *  exclusive_courses 专属课程 recording 录播 private_education
     */
    @Field
    @ApiModelProperty(value=" exclusive_courses 专属课程 recording 录播 private_education")
    private String type;

    /**
     * 教学人数
     */
    @Field
    @ApiModelProperty(value="教学人数",example="1")
    private Integer connect_peoplenum;

    /**
     * 机构id
     */
    @Field
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 封面
     */
    @Field
    @ApiModelProperty(value="封面",example="1")
    private String face_url;

    /**
     * 图片简介
     */
    @Field
    @ApiModelProperty(value="图片简介",example="1")
    private String introduction_url;

    /**
     * 文字简介
     */
    @Field
    @ApiModelProperty(value="课程详情",example="1")
    private String introduction_text;

    /**
     * 参加活动列表
     */
    @Field
    @ApiModelProperty(value="参加活动列表",example="1")
    private String activity_list;

    /**
     * 封面
     */
    @Field
    @ApiModelProperty(value="购买人数",example="1")
    private Long pay_num;

    /**
     * 关联机构课程id
     */
    @Field
    @ApiModelProperty(value="关联机构课程id",example="1")
    private Long master_appointment_id;

    @Field
    @ApiModelProperty(value="标签",example="1")
    private String label;

    @Field
    @ApiModelProperty(value = "是否推荐课程" , example="true")
    private Boolean is_recommend;

    @Field
    @ApiModelProperty(value = "帐号" , example="1")
    private String login_name;

    @Field
    @ApiModelProperty(value = "语言" , example="1")
    private String language;

    @Field
    @ApiModelProperty(value = "是否公共专属课" , example="1")
    private Boolean is_public_exclusive;

    @Field
    @ApiModelProperty(value = "面向人群语言等级 Childhood儿童,Teenagers少年,University Student,大学生,Adult成人" , example="1")
    private String age_grade;

    @Field
    @ApiModelProperty(value = "是否参加活动" , example="1")
    private Boolean is_attend_activities;

    @Field
    @ApiModelProperty(value = "是否互通课 true 是 false 不是" , example="1")
    private Boolean is_interoperability;

    @Field
    @ApiModelProperty(value = "机构地址" , example="1")
    private String mechanism_addr;

    @Field
    @ApiModelProperty(value = "预约方式 appointment 预约 scheduling 排课" , example="1")
    private String appointment_type;

    @Field
    @ApiModelProperty(value = "是否支持教付宝" , example="1")
    private Boolean is_support_teach_paypal;

    @Field
    @ApiModelProperty(value = "价格参数列表" , example="1")
    private String price_list;

    @Field
    @ApiModelProperty(value = "购买后可用时长 月" , example="1")
    private Integer duration;

    @Field
    @ApiModelProperty(value = "组id" , example="1")
    private String group_id;

    @Field
    @ApiModelProperty(value = "活动id" , example="1")
    private Long activity_id;

    @Field
    @ApiModelProperty(value = "类目" , example="1")
    private String categories;

    /**
     * 排序权重
     */
    @Field
    @ApiModelProperty(value="排序权重",example="1")
    private Integer sort_weight;

    /**
     * 商品群组id
     */
    @Field
    @ApiModelProperty(value="商品群组id",example="1")
    private Long master_set_price_group_id;

    /**
     * 商品群组id
     */
    @Field
    @ApiModelProperty(value="是否设置教学大纲 true 是 false 不是",example="1")
    private Boolean is_default_syllabus;

    /**
     * 直播id 0没有
     */
    @Field
    @ApiModelProperty(value="直播id 0没有",example="1")
    private Long live_streaming_id;

    /**
     * 直播价格
     */
    @Field
    @ApiModelProperty(value="直播价格",example="1")
    private BigDecimal live_stream_price;

    /**
     * 直播单节课价格
     */
    @Field
    @ApiModelProperty(value="直播单节课价格",example="1")
    private BigDecimal living_single_session_price;

    /**
     * 是否教付宝
     */
    @Field
    @ApiModelProperty(value="是否教付宝",example="false")
    private Boolean is_teach_paypal;

    public String getSchool_district() {
        return school_district;
    }

    public void setSchool_district(String school_district) {
        this.school_district = school_district;
    }

    public String getCommodity_type() {
        return commodity_type;
    }

    public void setCommodity_type(String commodity_type) {
        this.commodity_type = commodity_type;
    }

    public String getApply_age() {
        return apply_age;
    }

    public void setApply_age(String apply_age) {
        this.apply_age = apply_age;
    }

    public Integer getLength_of_lesson() {
        return length_of_lesson;
    }

    public void setLength_of_lesson(Integer length_of_lesson) {
        this.length_of_lesson = length_of_lesson;
    }

    public Boolean getIs_enlightenment() {
        return is_enlightenment;
    }

    public void setIs_enlightenment(Boolean is_enlightenment) {
        this.is_enlightenment = is_enlightenment;
    }

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }


    public String getActivity_list() {
        return activity_list;
    }

    public void setActivity_list(String activity_list) {
        this.activity_list = activity_list;
    }

    public Integer getSorted() {
        return sorted;
    }

    public Boolean getIs_live_streaming() {
        return is_live_streaming;
    }

    public void setIs_live_streaming(Boolean is_live_streaming) {
        this.is_live_streaming = is_live_streaming;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public BigDecimal getLiving_single_session_price() {
        return living_single_session_price==null?new BigDecimal("0"):living_single_session_price;
    }

    public void setLiving_single_session_price(BigDecimal living_single_session_price) {
        this.living_single_session_price = living_single_session_price;
    }

    public BigDecimal getLive_stream_price() {
        return live_stream_price;
    }

    public void setLive_stream_price(BigDecimal live_stream_price) {
        this.live_stream_price = live_stream_price;
    }

    public Long getLive_streaming_id() {
        return live_streaming_id;
    }

    public void setLive_streaming_id(Long live_streaming_id) {
        this.live_streaming_id = live_streaming_id;
    }

    public BigDecimal getDefault_discount_price() {
        return default_discount_price;
    }

    public void setDefault_discount_price(BigDecimal default_discount_price) {
        this.default_discount_price = default_discount_price;
    }

    public Boolean getIs_default_syllabus() {
        return is_default_syllabus;
    }

    public void setIs_default_syllabus(Boolean is_default_syllabus) {
        this.is_default_syllabus = is_default_syllabus;
    }

    public Long getMaster_set_price_group_id() {
        return master_set_price_group_id;
    }

    public void setMaster_set_price_group_id(Long master_set_price_group_id) {
        this.master_set_price_group_id = master_set_price_group_id;
    }

    public Integer getSort_weight() {
        return sort_weight;
    }

    public void setSort_weight(Integer sort_weight) {
        this.sort_weight = sort_weight;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public BigDecimal getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(BigDecimal original_price) {
        this.original_price = original_price;
    }

    public BigDecimal getActivity_price() {
        return activity_price;
    }

    public void setActivity_price(BigDecimal activity_price) {
        this.activity_price = activity_price;
    }

    public Long getActivity_id() {
        return activity_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPrice_list() {
        return price_list;
    }

    public void setPrice_list(String price_list) {
        this.price_list = price_list;
    }

    public Boolean getIs_support_teach_paypal() {
        return is_support_teach_paypal;
    }

    public void setIs_support_teach_paypal(Boolean is_support_teach_paypal) {
        this.is_support_teach_paypal = is_support_teach_paypal;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getMechanism_addr() {
        return mechanism_addr;
    }

    public void setMechanism_addr(String mechanism_addr) {
        this.mechanism_addr = mechanism_addr;
    }

    public BigDecimal getPrice_per_session() {
        return price_per_session;
    }

    public void setPrice_per_session(BigDecimal price_per_session) {
        this.price_per_session = price_per_session;
    }

    public Boolean getIs_interoperability() {
        return is_interoperability;
    }

    public void setIs_interoperability(Boolean is_interoperability) {
        this.is_interoperability = is_interoperability;
    }

    public Boolean getIs_attend_activities() {
        return is_attend_activities;
    }

    public void setIs_attend_activities(Boolean is_attend_activities) {
        this.is_attend_activities = is_attend_activities;
    }

    public String getAge_grade() {
        return age_grade;
    }

    public void setAge_grade(String age_grade) {
        this.age_grade = age_grade;
    }

    public Boolean getIs_public_exclusive() {
        return is_public_exclusive;
    }

    public void setIs_public_exclusive(Boolean is_public_exclusive) {
        this.is_public_exclusive = is_public_exclusive;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private List<Long> ids;

    private List<Long> loginIDs;

    private List<Long> mechanism_ids;

    public List<Long> getMechanism_ids() {
        return mechanism_ids;
    }

    public void setMechanism_ids(List<Long> mechanism_ids) {
        this.mechanism_ids = mechanism_ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getLoginIDs() {
        return loginIDs;
    }

    public void setLoginIDs(List<Long> loginIDs) {
        this.loginIDs = loginIDs;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    private String full_name;

    private String mechanism_name;

    public String getMechanism_name() {
        return mechanism_name;
    }

    public void setMechanism_name(String mechanism_name) {
        this.mechanism_name = mechanism_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Boolean getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(Boolean is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private Map<String,Object> map;

    public Map<String,Object> getMap() {
        return map;
    }

    public void setMap(Map<String,Object> map) {
        this.map = map;
    }

    public String getIntroduction_text() {
        return introduction_text;
    }

    public void setIntroduction_text(String introduction_text) {
        this.introduction_text = introduction_text;
    }

    public String getIntroduction_url() {
        return introduction_url;
    }

    public void setIntroduction_url(String introduction_url) {
        this.introduction_url = introduction_url;
    }

    public Long getMaster_appointment_id() {
        return master_appointment_id;
    }

    public void setMaster_appointment_id(Long master_appointment_id) {
        this.master_appointment_id = master_appointment_id;
    }

    public Long getPay_num() {
        return pay_num;
    }

    public void setPay_num(Long pay_num) {
        this.pay_num = pay_num;
    }

    public String getFace_url() {
        return face_url;
    }

    public void setFace_url(String face_url) {
        this.face_url = face_url;
    }

    /**
     * 主键id
     * @return id 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 坐标纬度值
     */
    @Field(type = FieldType.Auto)
    @ApiModelProperty(value="坐标纬度值",example="1")
    private BigDecimal latitude;

    /**
     * 坐标经度值
     */
    @Field(type = FieldType.Auto)
    @ApiModelProperty(value="坐标经度值",example="1")
    private BigDecimal longitude;

    @GeoPointField
    @ApiModelProperty(value="坐标经纬度" )
    private String location;//位置坐标 lon经度 lat纬度

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
     * 课程大标题
     * @return title 课程大标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 课程大标题
     * @param title 课程大标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 课节数
     * @return course_num 课节数
     */
    public Integer getCourse_num() {
        return course_num;
    }

    /**
     * 课节数
     * @param course_num 课节数
     */
    public void setCourse_num(Integer course_num) {
        this.course_num = course_num;
    }

    /**
     * 原价
     * @return amout 原价
     */
    public BigDecimal getAmout() {
        return amout;
    }

    /**
     * 原价
     * @param amout 原价
     */
    public void setAmout(BigDecimal amout) {
        this.amout = amout;
    }

    /**
     * 服务类型 1 线上 2 线下
     * @return service_type 服务类型 1 线上 2 线下
     */
    public Integer getService_type() {
        return service_type;
    }

    /**
     * 服务类型 1 线上 2 线下
     * @param service_type 服务类型 1 线上 2 线下
     */
    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }

    /**
     * 折扣
     * @return discount 折扣
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 折扣
     * @param discount 折扣
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 折扣价
     * @return discount_amout 折扣价
     */
    public BigDecimal getDiscount_amout() {
        return discount_amout;
    }

    /**
     * 折扣价
     * @param discount_amout 折扣价
     */
    public void setDiscount_amout(BigDecimal discount_amout) {
        this.discount_amout = discount_amout;
    }

    /**
     * 助学师的user_id
     * @return user_id 助学师的user_id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 助学师的user_id
     * @param user_id 助学师的user_id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 教学大纲
     * @return introduction_content 教学大纲
     */
    public String getIntroduction_content() {
        return introduction_content;
    }

    /**
     * 教学大纲
     * @param introduction_content 教学大纲
     */
    public void setIntroduction_content(String introduction_content) {
        this.introduction_content = introduction_content == null ? null : introduction_content.trim();
    }

    /**
     * 状态 2 生效 1 无效 3 草稿 4 不展示
     * @return status 状态 2 生效 1 无效 3 草稿 4 不展示
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 2 生效 1 无效 3 草稿 4 不展示
     * @param status 状态 2 生效 1 无效 3 草稿 4 不展示
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 标题加视频列表
     * @return titile_url 标题加视频列表
     */
    public String getTitile_url() {
        return titile_url;
    }

    /**
     * 标题加视频列表
     * @param titile_url 标题加视频列表
     */
    public void setTitile_url(String titile_url) {
        this.titile_url = titile_url == null ? null : titile_url.trim();
    }

    /**
     * 是否第一节免费
     * @return first_free 是否第一节免费
     */
    public Boolean getFirst_free() {
        return first_free;
    }

    /**
     * 是否第一节免费
     * @param first_free 是否第一节免费
     */
    public void setFirst_free(Boolean first_free) {
        this.first_free = first_free;
    }

    /**
     *  exclusive_courses 专属课程 recording 录播 private_education
     * @return type  exclusive_courses 专属课程 recording 录播 private_education
     */
    public String getType() {
        return type;
    }

    /**
     *  exclusive_courses 专属课程 recording 录播 private_education
     * @param type  exclusive_courses 专属课程 recording 录播 private_education
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 教学人数
     * @return connect_peoplenum 教学人数
     */
    public Integer getConnect_peoplenum() {
        return connect_peoplenum;
    }

    /**
     * 教学人数
     * @param connect_peoplenum 教学人数
     */
    public void setConnect_peoplenum(Integer connect_peoplenum) {
        this.connect_peoplenum = connect_peoplenum;
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
}