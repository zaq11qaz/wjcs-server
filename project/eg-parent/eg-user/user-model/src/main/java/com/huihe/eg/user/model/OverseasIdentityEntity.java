package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

@ApiModel(value="境外身份认证",description="境外身份认证属性说明")
public class OverseasIdentityEntity extends PageInfo {
    private static final long serialVersionUID = 9124948368864545896L;
    /**
     * 主键低
     */
    @ApiModelProperty(value="主键低",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 城市
     */
    @ApiModelProperty(value="城市")
    private String city;

    /**
     * 城市code
     */
    @ApiModelProperty(value="城市code")
    private String city_code;

    /**
     * 拒绝理由
     */
    @ApiModelProperty(value="拒绝理由")
    private String refuse_contect;

    public String getRefuse_contect() {
        return refuse_contect;
    }

    public void setRefuse_contect(String refuse_contect) {
        this.refuse_contect = refuse_contect;
    }

    /**
     * 出国时间
     */
    @ApiModelProperty(value="出国时间")
    private Date go_abroad_time;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 认证状态:1通过，2待审核，3拒绝
     */
    @ApiModelProperty(value="认证状态:1通过，2待审核，3拒绝",example="1")
    private Integer status;

    /**
     * 正面照片
     */
    @ApiModelProperty(value="正面照片")
    private String card_front;

    /**
     * 反面照片
     */
    @ApiModelProperty(value="反面照片")
    private String card_back;

    /**
     * 手持证件照
     */
    @ApiModelProperty(value="手持证件照")
    private String card_onhand;

    /**
     * 简介
     */
    @ApiModelProperty(value="简介")
    private String brief;

    /**
     * 留学的学校
     */
    @ApiModelProperty(value="留学的学校")
    private String school_name;

    /**
     * 系
     */
    @ApiModelProperty(value="系")
    private String department;

    /**
     * 专业
     */
    @ApiModelProperty(value="专业")
    private String specialty;

    /**
     * 语言
     */
    @ApiModelProperty(value="语言")
    private String language;

    /**
     * 境外身份
     */
    @ApiModelProperty(value="境外身份")
    private String identity;

    /**
     * 籍贯
     */
    @ApiModelProperty(value="籍贯")
    private String native_place;

    /**
     * 所在的国家
     */
    @ApiModelProperty(value="所在的国家")
    private String in_country;

    /**
     * 单位、大使馆
     */
    @ApiModelProperty(value="单位、大使馆")
    private String unit;

    /**
     * 行业
     */
    @ApiModelProperty(value="行业")
    private String industry;

    /**
     * 
     */
    @ApiModelProperty(value="")
    private String slc_dates;

    /**
     * 身份类型 overseas_student 留学生、// overseas_chinese华侨、ethnic_chinese华裔、overseas_staff境外工作者//overseas_government_personnel 政府境外工作者、 // overseas_chinese_learners 中文爱好者、//language_teacher 语言老师、 overseas_nationality 国籍、 overseas_ merchant 境外商人
     */
    @ApiModelProperty(value="身份类型 overseas_student 留学生、// overseas_chinese华侨、ethnic_chinese华裔、overseas_staff境外工作者//overseas_government_personnel 政府境外工作者、 // overseas_chinese_learners 中文爱好者、//language_teacher 全职语言老师、// language_teacher_concurrent 兼职语言老师、 overseas_nationality 国籍、 overseas_merchant 境外商人、student 学生")
    private String identity_type;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 1全职2兼职
     */
    @ApiModelProperty(value="1全职2兼职",example="1")
    private Integer time_job;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String mail;
    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 申请开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="开始时间")
    private Date start_time;
    /**
     * 申请结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nick_name;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
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

    /**
     * 主键低
     * @return id 主键低
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键低
     * @param id 主键低
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户id
     * @return user_id 用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户id
     * @param user_id 用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 城市
     * @return city 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 城市
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 城市code
     * @return city_code 城市code
     */
    public String getCity_code() {
        return city_code;
    }

    /**
     * 城市code
     * @param city_code 城市code
     */
    public void setCity_code(String city_code) {
        this.city_code = city_code == null ? null : city_code.trim();
    }

    /**
     * 出国时间
     * @return go_abroad_time 出国时间
     */
    public Date getGo_abroad_time() {
        return go_abroad_time;
    }

    /**
     * 出国时间
     * @param go_abroad_time 出国时间
     */
    public void setGo_abroad_time(Date go_abroad_time) {
        this.go_abroad_time = go_abroad_time;
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
     * 认证状态:1通过，2待审核，3拒绝
     * @return status 认证状态:1通过，2待审核，3拒绝
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 认证状态:1通过，2待审核，3拒绝
     * @param status 认证状态:1通过，2待审核，3拒绝
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 正面照片
     * @return card_front 正面照片
     */
    public String getCard_front() {
        return card_front;
    }

    /**
     * 正面照片
     * @param card_front 正面照片
     */
    public void setCard_front(String card_front) {
        this.card_front = card_front == null ? null : card_front.trim();
    }

    /**
     * 反面照片
     * @return card_back 反面照片
     */
    public String getCard_back() {
        return card_back;
    }

    /**
     * 反面照片
     * @param card_back 反面照片
     */
    public void setCard_back(String card_back) {
        this.card_back = card_back == null ? null : card_back.trim();
    }

    /**
     * 手持证件照
     * @return card_onhand 手持证件照
     */
    public String getCard_onhand() {
        return card_onhand;
    }

    /**
     * 手持证件照
     * @param card_onhand 手持证件照
     */
    public void setCard_onhand(String card_onhand) {
        this.card_onhand = card_onhand == null ? null : card_onhand.trim();
    }

    /**
     * 简介
     * @return brief 简介
     */
    public String getBrief() {
        return brief;
    }

    /**
     * 简介
     * @param brief 简介
     */
    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    /**
     * 留学的学校
     * @return school_name 留学的学校
     */
    public String getSchool_name() {
        return school_name;
    }

    /**
     * 留学的学校
     * @param school_name 留学的学校
     */
    public void setSchool_name(String school_name) {
        this.school_name = school_name == null ? null : school_name.trim();
    }

    /**
     * 系
     * @return department 系
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 系
     * @param department 系
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * 专业
     * @return specialty 专业
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * 专业
     * @param specialty 专业
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    /**
     * 语言
     * @return language 语言
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 语言
     * @param language 语言
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    /**
     * 境外身份
     * @return identity 境外身份
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 境外身份
     * @param identity 境外身份
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * 籍贯
     * @return native_place 籍贯
     */
    public String getNative_place() {
        return native_place;
    }

    /**
     * 籍贯
     * @param native_place 籍贯
     */
    public void setNative_place(String native_place) {
        this.native_place = native_place == null ? null : native_place.trim();
    }

    /**
     * 所在的国家
     * @return in_country 所在的国家
     */
    public String getIn_country() {
        return in_country;
    }

    /**
     * 所在的国家
     * @param in_country 所在的国家
     */
    public void setIn_country(String in_country) {
        this.in_country = in_country == null ? null : in_country.trim();
    }

    /**
     * 单位、大使馆
     * @return unit 单位、大使馆
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 单位、大使馆
     * @param unit 单位、大使馆
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * 行业
     * @return industry 行业
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 行业
     * @param industry 行业
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * 
     * @return slc_dates 
     */
    public String getSlc_dates() {
        return slc_dates;
    }

    /**
     * 
     * @param slc_dates 
     */
    public void setSlc_dates(String slc_dates) {
        this.slc_dates = slc_dates == null ? null : slc_dates.trim();
    }

    /**
     * 身份类型 overseas_student 留学生、// overseas_chinese华侨、ethnic_chinese华裔、overseas_staff境外工作者//overseas_government_personnel 政府境外工作者、 // overseas_chinese_learners 中文爱好者、//language_teacher 语言老师、 overseas_nationality 国籍、 overseas_ merchant 境外商人
     * @return identity_type 身份类型 overseas_student 留学生、// overseas_chinese华侨、ethnic_chinese华裔、overseas_staff境外工作者//overseas_government_personnel 政府境外工作者、 // overseas_chinese_learners 中文爱好者、//language_teacher 语言老师、 overseas_nationality 国籍、 overseas_ merchant 境外商人
     */
    public String getIdentity_type() {
        return identity_type;
    }

    /**
     * 身份类型 overseas_student 留学生、// overseas_chinese华侨、ethnic_chinese华裔、overseas_staff境外工作者//overseas_government_personnel 政府境外工作者、 // overseas_chinese_learners 中文爱好者、//language_teacher 语言老师、 overseas_nationality 国籍、 overseas_ merchant 境外商人
     * @param identity_type 身份类型 overseas_student 留学生、// overseas_chinese华侨、ethnic_chinese华裔、overseas_staff境外工作者//overseas_government_personnel 政府境外工作者、 // overseas_chinese_learners 中文爱好者、//language_teacher 语言老师、 overseas_nationality 国籍、 overseas_ merchant 境外商人
     */
    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type == null ? null : identity_type.trim();
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
     * 1全职2兼职
     * @return time_job 1全职2兼职
     */
    public Integer getTime_job() {
        return time_job;
    }

    /**
     * 1全职2兼职
     * @param time_job 1全职2兼职
     */
    public void setTime_job(Integer time_job) {
        this.time_job = time_job;
    }

    /**
     * 手机号
     * @return mobile 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 邮箱
     * @return mail 邮箱
     */
    public String getMail() {
        return mail;
    }

    /**
     * 邮箱
     * @param mail 邮箱
     */
    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }
}