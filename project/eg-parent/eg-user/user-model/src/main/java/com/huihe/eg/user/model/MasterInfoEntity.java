package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.cy.framework.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huihe.eg.comm.elasticsearch.Field;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="助学师基本资料",description="助学师基本资料属性说明")
@Document(indexName = "master",type = "masterInfo",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class MasterInfoEntity extends PageInfo {
    private static final long serialVersionUID = -3843766714482762779L;
    /**
     * 主键id
     */
    @Id
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 国籍
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="国籍")
    private String nationality;

    /**
     * 是否开放
     */
    @Field
    @ApiModelProperty(value="是否开放",example="1")
    private  Integer is_open;

    /**
     * 用户id
     */
    @Field
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 掌握语言
     */
    @Field
    @ApiModelProperty(value="掌握语言")
    private String language;

    /**
     * 创建时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 个人介绍
     */
    @Field
    @ApiModelProperty(value="个人介绍")
    private String introduction;

    /**
     * 子类目
     */
    @Field
    @ApiModelProperty(value="子类目")
    private String categories_child;

    /**
     * 机构名
     */
    @ApiModelProperty(value="机构名")
    private String mechanism_name;

    /**
     *  分级
     */
    @Field
    @ApiModelProperty(value=" 分级",example="1")
    private Integer grade;

    /**
     * 评分
     */
    @Field
    @ApiModelProperty(value="评分",example="1")
    private BigDecimal score;

    /**
     * 所在地
     */
    @Field
    @ApiModelProperty(value="所在地")
    private String address;

    /**
     * 修改时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @ApiModelProperty(value="修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    /**
     * 母语
     */
    @Field
    @ApiModelProperty(value="母语")
    private String mother_tongue;

    /**
     * 性别 1男2女
     */
    @Field
    @ApiModelProperty(value="性别 1男2女",example="1")
    private Integer sex;

    /**
     * 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销
     */
    @Field
    @ApiModelProperty(value="状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结 6 未启用",example="1")
    private Integer status;

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  advertising_position 广告位 live_lecturer直播讲师
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  advertising_position 广告位 live_lecturer直播讲师")
    private String type;

    /**
     * 身份
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="身份")
    private String identity;

    /**
     * 毕业学校
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="毕业学校")
    private String graduation_school;

    /**
     * 专业
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="专业")
    private String specialty;

    /**
     * 手机号
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="邮箱")
    private String mail;

    /**
     * 教学语言
     */
    @Field
    @ApiModelProperty(value="教学语言")
    private String teach_language;

    /**
     * 相关资料
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="相关资料")
    private String relevant_information;

    /**
     * 母语展示视频
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="母语展示视频")
    private String mother_tongue_url;

    /**
     * 掌握语言展示视频
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="掌握语言展示视频")
    private String language_url;

    /**
     * 教学语言展示视频
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="教学语言展示视频")
    private String teach_language_url;

    /**
     * 要求类型
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="要求类型")
    private String requirement_type;

    /**
     * 学历
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="学历")
    private String education;

    /**
     * 首页推荐类型语种 languages 推荐 recommend 热门 hot advertising_position 广告位
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="首页推荐类型语种 languages 推荐 recommend 热门 hot advertising_position 广告位")
    private String recommend_type;

    /**
     * 首页布局
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="首页布局",example="1")
    private Integer layout;

    /**
     * 收益
     */
    @ApiModelProperty(value="收益",example="1")
    private BigDecimal earnings;

    /**
     * 首页推荐视频
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="首页推荐视频")
    private String recommend_video;

    /**
     * 封面
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="封面")
    private String cover;

    /**
     * 活动推荐类型
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="活动推荐类型")
    private String activity_type;

    /**
     * 人气数值
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="人气数值",example="1")
    private Integer popularity;

    /**
     * 线上内容
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="线上内容")
    private String online;

    /**
     * 线下内容
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="线下内容")
    private String offline;

    /**
     * 服务类型 1 线上 2 线下 3线上/线下
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="服务类型 1 线上 2 线下 3线上/线下",example="1")
    private Integer service_type;

    /**
     * 服务人群
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="服务人群")
    private String serve_people;

    /**
     * 语言等级
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="语言等级")
    private String language_level;

    /**
     * 服务范围/km
     */
    @Field
    @ApiModelProperty(value="服务范围/km",example="1")
    private BigDecimal serve_range;

    /**
     * 现居住地
     */
    @Field
    @ApiModelProperty(value="现居住地")
    private String nhom_address;

    /**
     * 简介
     */
    @Field
    @ApiModelProperty(value="简介")
    private String introduction_content;

    /**
     * 简介图片
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="简介图片")
    private String introduction_cover;

    /**
     * 最大学生数
     */
    @Field
    @ApiModelProperty(value="最大学生数",example="1")
    private Integer max_student_count;

    /**
     * 联系方式
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="联系方式")
    private String contact_information;

    /**
     * 收费标准
     */
    @Field
    @ApiModelProperty(value="收费标准",example="1")
    private BigDecimal charging_standard;


    /**
     * 线下收费标准
     */
    @Field
    @ApiModelProperty(value="线下收费标准",example="1")
    private BigDecimal charging_standard_offline;

    /**
     * 最低购买数
     */
    @Field
    @ApiModelProperty(value="最低购买数",example="1")
    private Integer minimum_pay;

    public BigDecimal getCharging_standard_offline() {
        return charging_standard_offline;
    }

    public void setCharging_standard_offline(BigDecimal charging_standard_offline) {
        this.charging_standard_offline = charging_standard_offline;
    }

    public Integer getMinimum_pay() {
        return minimum_pay;
    }

    public void setMinimum_pay(Integer minimum_pay) {
        this.minimum_pay = minimum_pay;
    }

    /**
     * 证件照
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="证件照")
    private String photo;

    /**
     * 全名
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="全名")
    private String full_name;

    /**
     * 年龄
     */
    @Field
    @ApiModelProperty(value="年龄",example="1")
    private Integer master_age;

    /**
     * 拒绝理由
     */
    @ApiModelProperty(value ="拒绝理由",example = "1")
    private String refuse_contect;

    /**
     * 冻结理由
     */
    @ApiModelProperty(value = "冻结理由",example = "1")
    private String frozen_contect;

    public String getFrozen_contect() {
        return frozen_contect;
    }

    public void setFrozen_contect(String frozen_contect) {
        this.frozen_contect = frozen_contect;
    }

    /**
     * 线下课程时长
     */
    @ApiModelProperty(value="线下课程时长",example="1")
    private Integer course_duration;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;
    /**
     * 是否可以自行修改教师资料
     */
    @ApiModelProperty(value="是否可以自行修改教师资料",example="false")
    private Boolean is_self_modification;
    /**
     * 类型集合
     */
    @ApiModelProperty(value="类型集合")
    private String stringList;

    public String getStringList() {
        return stringList;
    }

    public void setStringList(String stringList) {
        this.stringList = stringList;
    }

    /**
     * 登录名
     */
    @ApiModelProperty(value="登录名")
    private String login_name;
    /**
     * 登录密码
     */
    @ApiModelProperty(value="登录密码")
    private String login_pass;

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

    @ApiModelProperty(value="是否是特约",example="false")
    private Boolean is_special;

    @ApiModelProperty(value="邀请官推荐码",example="false")
    private String invitees_id;

    public String getInvitees_id() {
        return invitees_id;
    }

    public void setInvitees_id(String invitees_id) {
        this.invitees_id = invitees_id;
    }

    @ApiModelProperty(value="邀请人id",example="false")
    private Boolean is_recommend;

    @ApiModelProperty(value = "fullName id集合",example = "1")
    private List<Long> ids;

    @ApiModelProperty(value = "liginName id集合",example = "1")
    private List<Long> loginIDs;

    /**
     * 中教 外教
     */
    @ApiModelProperty(value="中教 外教",example="1")
    private String country_type;

    public String getMechanism_name() {
        return mechanism_name;
    }

    public void setMechanism_name(String mechanism_name) {
        this.mechanism_name = mechanism_name;
    }

    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public String getCountry_type() {
        return country_type;
    }

    public void setCountry_type(String country_type) {
        this.country_type = country_type;
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

    public Boolean getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(Boolean is_recommend) {
        this.is_recommend = is_recommend;
    }

    public Boolean getIs_special() {
        return is_special;
    }

    public void setIs_special(Boolean is_special) {
        this.is_special = is_special;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
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

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_pass() {
        return login_pass;
    }

    public void setLogin_pass(String login_pass) {
        this.login_pass = login_pass;
    }

    @ApiModelProperty(value = "价格",example = "1")
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

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
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nick_name;

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getNick_name() {
        return nick_name;
    }
    @ApiModelProperty(value = "id集合",example = "[]")
    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
    @ApiModelProperty(value="搜索类")
    @Field
    private SearchParam searchParam;

    public SearchParam getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(SearchParam searchParam) {
        this.searchParam = searchParam;
    }
    @ApiModelProperty(value="验证码")
    private String verification_code;

    @ApiModelProperty(value="标签")
    private String labels;

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Integer getIs_open() {
        return is_open;
    }

    public void setIs_open(Integer is_open) {
        this.is_open = is_open;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
    /**
        * 是否查询全部
     */
    @ApiModelProperty(value="是否查询全部",example="false")
    private Boolean is_all;

    public String getCategories_child() {
        return categories_child;
    }

    public void setCategories_child(String categories_child) {
        this.categories_child = categories_child;
    }

    public Boolean getIs_all() {
        return is_all;
    }

    public void setIs_all(Boolean is_all) {
        this.is_all = is_all;
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
     * 国籍
     * @return nationality 国籍
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * 国籍
     * @param nationality 国籍
     */
    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
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
     * 掌握语言
     * @return language 掌握语言
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 掌握语言
     * @param language 掌握语言
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
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
     * 个人介绍
     * @return introduction 个人介绍
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 个人介绍
     * @param introduction 个人介绍
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     *  分级
     * @return grade  分级
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     *  分级
     * @param grade  分级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 评分
     * @return score 评分
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 评分
     * @param score 评分
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * 所在地
     * @return address 所在地
     */
    public String getAddress() {
        return address;
    }

    /**
     * 所在地
     * @param address 所在地
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
     * 母语
     * @return mother_tongue 母语
     */
    public String getMother_tongue() {
        return mother_tongue;
    }

    /**
     * 母语
     * @param mother_tongue 母语
     */
    public void setMother_tongue(String mother_tongue) {
        this.mother_tongue = mother_tongue == null ? null : mother_tongue.trim();
    }

    /**
     * 性别 1男2女
     * @return sex 性别 1男2女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 性别 1男2女
     * @param sex 性别 1男2女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结 6 未启用
     * @return status 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结 6 未启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结 6 未启用
     * @param status 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结 6 未启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  advertising_position 广告位
     * @return type 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  advertising_position 广告位
     */
    public String getType() {
        return type;
    }

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  advertising_position 广告位
     * @param type 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  advertising_position 广告位
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 身份
     * @return identity 身份
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 身份
     * @param identity 身份
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * 毕业学校
     * @return graduation_school 毕业学校
     */
    public String getGraduation_school() {
        return graduation_school;
    }

    /**
     * 毕业学校
     * @param graduation_school 毕业学校
     */
    public void setGraduation_school(String graduation_school) {
        this.graduation_school = graduation_school == null ? null : graduation_school.trim();
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

    /**
     * 教学语言
     * @return teach_language 教学语言
     */
    public String getTeach_language() {
        return teach_language;
    }

    /**
     * 教学语言
     * @param teach_language 教学语言
     */
    public void setTeach_language(String teach_language) {
        this.teach_language = teach_language == null ? null : teach_language.trim();
    }

    /**
     * 相关资料
     * @return relevant_information 相关资料
     */
    public String getRelevant_information() {
        return relevant_information;
    }

    /**
     * 相关资料
     * @param relevant_information 相关资料
     */
    public void setRelevant_information(String relevant_information) {
        this.relevant_information = relevant_information == null ? null : relevant_information.trim();
    }

    /**
     * 母语展示视频
     * @return mother_tongue_url 母语展示视频
     */
    public String getMother_tongue_url() {
        return mother_tongue_url;
    }

    /**
     * 母语展示视频
     * @param mother_tongue_url 母语展示视频
     */
    public void setMother_tongue_url(String mother_tongue_url) {
        this.mother_tongue_url = mother_tongue_url == null ? null : mother_tongue_url.trim();
    }

    /**
     * 掌握语言展示视频
     * @return language_url 掌握语言展示视频
     */
    public String getLanguage_url() {
        return language_url;
    }

    /**
     * 掌握语言展示视频
     * @param language_url 掌握语言展示视频
     */
    public void setLanguage_url(String language_url) {
        this.language_url = language_url == null ? null : language_url.trim();
    }

    /**
     * 教学语言展示视频
     * @return teach_language_url 教学语言展示视频
     */
    public String getTeach_language_url() {
        return teach_language_url;
    }

    /**
     * 教学语言展示视频
     * @param teach_language_url 教学语言展示视频
     */
    public void setTeach_language_url(String teach_language_url) {
        this.teach_language_url = teach_language_url == null ? null : teach_language_url.trim();
    }

    /**
     * 要求类型
     * @return requirement_type 要求类型
     */
    public String getRequirement_type() {
        return requirement_type;
    }

    /**
     * 要求类型
     * @param requirement_type 要求类型
     */
    public void setRequirement_type(String requirement_type) {
        this.requirement_type = requirement_type == null ? null : requirement_type.trim();
    }

    /**
     * 学历
     * @return education 学历
     */
    public String getEducation() {
        return education;
    }

    /**
     * 学历
     * @param education 学历
     */
    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    /**
     * 首页推荐类型语种 languages 推荐 recommend 热门 hot advertising_position 广告位
     * @return recommend_type 首页推荐类型语种 languages 推荐 recommend 热门 hot advertising_position 广告位
     */
    public String getRecommend_type() {
        return recommend_type;
    }

    /**
     * 首页推荐类型语种 languages 推荐 recommend 热门 hot advertising_position 广告位
     * @param recommend_type 首页推荐类型语种 languages 推荐 recommend 热门 hot advertising_position 广告位
     */
    public void setRecommend_type(String recommend_type) {
        this.recommend_type = recommend_type == null ? null : recommend_type.trim();
    }

    /**
     * 首页布局
     * @return layout 首页布局
     */
    public Integer getLayout() {
        return layout;
    }

    /**
     * 首页布局
     * @param layout 首页布局
     */
    public void setLayout(Integer layout) {
        this.layout = layout;
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
     * 首页推荐视频
     * @return recommend_video 首页推荐视频
     */
    public String getRecommend_video() {
        return recommend_video;
    }

    /**
     * 首页推荐视频
     * @param recommend_video 首页推荐视频
     */
    public void setRecommend_video(String recommend_video) {
        this.recommend_video = recommend_video == null ? null : recommend_video.trim();
    }

    /**
     * 封面
     * @return cover 封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * 封面
     * @param cover 封面
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * 活动推荐类型
     * @return activity_type 活动推荐类型
     */
    public String getActivity_type() {
        return activity_type;
    }

    /**
     * 活动推荐类型
     * @param activity_type 活动推荐类型
     */
    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type == null ? null : activity_type.trim();
    }

    /**
     * 人气数值
     * @return popularity 人气数值
     */
    public Integer getPopularity() {
        return popularity;
    }

    /**
     * 人气数值
     * @param popularity 人气数值
     */
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    /**
     * 线上内容
     * @return online 线上内容
     */
    public String getOnline() {
        return online;
    }

    /**
     * 线上内容
     * @param online 线上内容
     */
    public void setOnline(String online) {
        this.online = online == null ? null : online.trim();
    }

    /**
     * 线下内容
     * @return offline 线下内容
     */
    public String getOffline() {
        return offline;
    }

    /**
     * 线下内容
     * @param offline 线下内容
     */
    public void setOffline(String offline) {
        this.offline = offline == null ? null : offline.trim();
    }

    /**
     * 服务类型 1 线上 2 线下 3线上/线下
     * @return service_type 服务类型 1 线上 2 线下 3线上/线下
     */
    public Integer getService_type() {
        return service_type;
    }

    /**
     * 服务类型 1 线上 2 线下 3线上/线下
     * @param service_type 服务类型 1 线上 2 线下 3线上/线下
     */
    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }

    /**
     * 服务人群
     * @return serve_people 服务人群
     */
    public String getServe_people() {
        return serve_people;
    }

    /**
     * 服务人群
     * @param serve_people 服务人群
     */
    public void setServe_people(String serve_people) {
        this.serve_people = serve_people == null ? null : serve_people.trim();
    }

    /**
     * 语言等级
     * @return language_level 语言等级
     */
    public String getLanguage_level() {
        return language_level;
    }

    /**
     * 语言等级
     * @param language_level 语言等级
     */
    public void setLanguage_level(String language_level) {
        this.language_level = language_level == null ? null : language_level.trim();
    }

    /**
     * 服务范围/km
     * @return serve_range 服务范围/km
     */
    public BigDecimal getServe_range() {
        return serve_range;
    }

    /**
     * 服务范围/km
     * @param serve_range 服务范围/km
     */
    public void setServe_range(BigDecimal serve_range) {
        this.serve_range = serve_range;
    }

    /**
     * 现居住地
     * @return nhom_address 现居住地
     */
    public String getNhom_address() {
        return nhom_address;
    }

    /**
     * 现居住地
     * @param nhom_address 现居住地
     */
    public void setNhom_address(String nhom_address) {
        this.nhom_address = nhom_address == null ? null : nhom_address.trim();
    }

    /**
     * 简介
     * @return introduction_content 简介
     */
    public String getIntroduction_content() {
        return introduction_content;
    }

    /**
     * 简介
     * @param introduction_content 简介
     */
    public void setIntroduction_content(String introduction_content) {
        this.introduction_content = introduction_content == null ? null : introduction_content.trim();
    }

    /**
     * 简介图片
     * @return introduction_cover 简介图片
     */
    public String getIntroduction_cover() {
        return introduction_cover;
    }

    /**
     * 简介图片
     * @param introduction_cover 简介图片
     */
    public void setIntroduction_cover(String introduction_cover) {
        this.introduction_cover = introduction_cover == null ? null : introduction_cover.trim();
    }

    /**
     * 最大学生数
     * @return max_student_count 最大学生数
     */
    public Integer getMax_student_count() {
        return max_student_count;
    }

    /**
     * 最大学生数
     * @param max_student_count 最大学生数
     */
    public void setMax_student_count(Integer max_student_count) {
        this.max_student_count = max_student_count;
    }

    /**
     * 联系方式
     * @return contact_information 联系方式
     */
    public String getContact_information() {
        return contact_information;
    }

    /**
     * 联系方式
     * @param contact_information 联系方式
     */
    public void setContact_information(String contact_information) {
        this.contact_information = contact_information == null ? null : contact_information.trim();
    }

    /**
     * 收费标准
     * @return charging_standard 收费标准
     */
    public BigDecimal getCharging_standard() {
        return charging_standard;
    }

    /**
     * 收费标准
     * @param charging_standard 收费标准
     */
    public void setCharging_standard(BigDecimal charging_standard) {
        this.charging_standard = charging_standard;
    }

    /**
     * 证件照
     * @return photo 证件照
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 证件照
     * @param photo 证件照
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * 全名
     * @return full_name 全名
     */
    public String getFull_name() {
        return StringUtil.isNotEmpty(full_name)?EmojiParser.parseToUnicode(full_name):full_name;
    }

    /**
     * 全名
     * @param full_name 全名
     */
    public void setFull_name(String full_name) {
        this.full_name = full_name == null ? null : full_name.trim();
    }

    /**
     * 年龄
     * @return master_age 年龄
     */
    public Integer getMaster_age() {
        return master_age;
    }

    /**
     * 年龄
     * @param master_age 年龄
     */
    public void setMaster_age(Integer master_age) {
        this.master_age = master_age;
    }

    /**
     * 线下课程时长
     * @return course_duration 线下课程时长
     */
    public Integer getCourse_duration() {
        return course_duration;
    }

    /**
     * 线下课程时长
     * @param course_duration 线下课程时长
     */
    public void setCourse_duration(Integer course_duration) {
        this.course_duration = course_duration;
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

    public Boolean getIs_self_modification() {
        return is_self_modification;
    }

    public void setIs_self_modification(Boolean is_self_modification) {
        this.is_self_modification = is_self_modification;
    }

    public String getRefuse_contect() {
        return refuse_contect;
    }

    public void setRefuse_contect(String refuse_contect) {
        this.refuse_contect = refuse_contect;
    }
}