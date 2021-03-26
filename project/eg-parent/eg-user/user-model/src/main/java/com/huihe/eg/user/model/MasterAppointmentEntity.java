package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huihe.eg.comm.elasticsearch.Field;
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

@ApiModel(value="助学师课节列表",description="助学师课节列表属性说明")
@Document(indexName = "masterapp",type = "masterappointment",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class MasterAppointmentEntity extends PageInfo {

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
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

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
     * 助学师的user_id
     */
    @Field
    @ApiModelProperty(value="助学师的user_id",example="1")
    private Long master_id;

    /**
     * 开始时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 状态1正常 2已结束
     */
    @Field
    @ApiModelProperty(value="状态 1正常 2已结束 3开课  4正在审核 5 已结算 6 线下课程预约 7 线下课程签到 8 待总结 9 待支付 10 已支付',",example="1")
    private Integer status;

    /**
     * 类型 single_class助学师课程 open_class公开直播课程  mechanism_offline机构线下课程  jointly_class 联名课程
     */
    @Field
    @ApiModelProperty(value="类型 single_class助学师课程 open_class公开直播课程  mechanism_offline机构线下课程  jointly_class 联名课程")
    private String type;

    /**
     * 与GMT的偏移量
     */
    @Field
    @ApiModelProperty(value="与GMT的偏移量",example="1")
    private BigDecimal offset;

    /**
     * 时区id
     */
    @Field
    @ApiModelProperty(value="时区id",example="1")
    private Long timezone_id;

    /**
     * 标题
     */
    @Field
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 群id
     */
    @Field
    @ApiModelProperty(value="群id",example="1")
    private Long group_id;

    /**
     * 封面
     */
    @Field
    @ApiModelProperty(value="封面")
    private String cover;

    /**
     * 面向人群语言等级 面向人群年龄级别  Childhood儿童,Teenagers少年,University Student,大学生,Adult成人
     */
    @Field
    @ApiModelProperty(value="面向人群语言等级 面向人群年龄级别  Childhood儿童,Teenagers少年,University Student,大学生,Adult成人")
    private String age_grade;

    /**
     * 面向人群语言等级 Zero basis零基础,Primary初级,Intermediate中级,Senior 高级;
     */
    @Field
    @ApiModelProperty(value="面向人群语言等级 Zero basis零基础,Primary初级,Intermediate中级,Senior 高级;")
    private String language_level;

    /**
     * 语言
     */
    @Field
    @ApiModelProperty(value="语言")
    private String language;

    /**
     *  直播群类别    online_video在线视频   online_voice在线语音
     */
    @Field
    @ApiModelProperty(value="直播群类别    online_video在线视频   online_voice在线语音")
    private String group_type;
    /**
     * TIM房间id
     */
    @Field
    @ApiModelProperty(value="TIM房间id")
    private String room_id;

    @Field
    @ApiModelProperty(value = "是否被预约",example = "false")
    private Boolean is_appointment;

    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    @ApiModelProperty(value = "集合")
    private String entities;

    @Field
    @ApiModelProperty(value = "时区")
    private String timecode;

    /**
     * 教学语言
     */
    @Field
    @ApiModelProperty(value="教学语言")
    private String teach_language;

    /**
     * 课堂类型
     */
    @Field
    @ApiModelProperty(value="课堂类型")
    private String classroom_type;
    /**
     * 简介图片
     */
    @Field
    @ApiModelProperty(value="简介图片")
    private String introduction_cover;
    /**
     * 简介
     */
    @Field
    @ApiModelProperty(value="简介")
    private String introduction_content;

    /**
     * 服务类型 线上:online/线下offline
     */
    @Field
    @ApiModelProperty(value = "服务类型 线上:online/线下offline")
    private String service_type;

    /**
     * 机构id
     */
    @Field
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 原价价格
     */
    @ApiModelProperty(value="原价价格",example="1")
    private BigDecimal amout;

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
     * 是否折扣
     */
    @Field
    @ApiModelProperty(value="是否折扣",example="false")
    private Boolean is_discount;

    /**
     * 教学大纲
     */
    @Field
    @ApiModelProperty(value="教学大纲")
    private String teach_program;
    /**
     * 坐标纬度值
     */
    @Field
    @ApiModelProperty(value="坐标纬度值",example="1")
    private BigDecimal latitude;

    /**
     * 坐标经度值
     */
    @Field
    @ApiModelProperty(value="坐标经度值",example="1")
    private BigDecimal longitude;

    /**
     * 推荐类型 1直播好课
     */
    @Field
    @ApiModelProperty(value="推荐类型 1直播好课",example="1")
    private Integer recommend_type;

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    @Field
    @ApiModelProperty(value="助学师类型 major 专业,cross_border 跨境,mother_tongue 母语,private_education 私教")
    private String identity_type;

    /**
     * 本地时区偏差值
     */
    @Field
    @ApiModelProperty(value="本地时区偏差值",example="1")
    private BigDecimal local_offset;

    /**
     * 是否青少年
     */
    @Field
    @ApiModelProperty(value="是否青少年",example="false")
    private Boolean is_teenagers;

    public Boolean getIs_teenagers() {
        return is_teenagers;
    }

    public void setIs_teenagers(Boolean is_teenagers) {
        this.is_teenagers = is_teenagers;
    }
    /**
     * user_id
     */
    @ApiModelProperty(value="user_id",example="1")
    private Long user_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 群id集合
     */
    private List<Long> group_ids;
    /**
     * 连麦人数
     */
    @Field
    @ApiModelProperty(value="连麦人数",example="1")
    private Integer connect_peoplenum;
    @Field
    @ApiModelProperty(value = "是否存在录播文件",example = "false")
    private Boolean is_recording;
    public Integer getRecommend_type() {
        return recommend_type;
    }

    @Field
    @ApiModelProperty(value = "是否是特约课程",example = "false")
    private Boolean is_special;

    @Field
    @ApiModelProperty(value = "是否首页展示",example = "false")
    private Boolean is_firstpage_show;

    @Field
    @ApiModelProperty(value = "关联商品id",example = "1")
    private Long master_set_price_id;

    @Field
    @ApiModelProperty(value = "名",example = "1")
    private String full_name;

    @ApiModelProperty(value = "fullName id集合",example = "1")
    private List<Long> ids;

    @ApiModelProperty(value = "fullName id集合",example = "1")
    private String study_card_ids;

    @ApiModelProperty(value = "liginName id集合",example = "1")
    private List<Long> loginIDs;

    @ApiModelProperty(value = "帐号",example = "1")
    private String Login_name;

    @ApiModelProperty(value = "收益",example = "1")
    private BigDecimal profit;

    @ApiModelProperty(value = "招募官收益抽成",example = "1")
    private BigDecimal masterRecommenderCash;

    public BigDecimal getMasterRecommenderCash() {
        return masterRecommenderCash;
    }

    @ApiModelProperty(value = "最终获取金额",example = "1")
    private BigDecimal finalEarn;

    @ApiModelProperty(value = "推荐人id",example = "1")
    private Long recommender_id;

    @ApiModelProperty(value = "日志id",example = "1")
    private Long log_id;

    @ApiModelProperty(value = "外教日志id",example = "1")
    private Long masterLog_id;

    @ApiModelProperty(value = "点赞数",example = "1")
    private Long like_count;

    @ApiModelProperty(value = "评论数",example = "1")
    private Long comment_count;

    @ApiModelProperty(value = "分享数",example = "1")
    private Long share_count;

    @Field
    @ApiModelProperty(value = "组名",example = "1")
    private String group_name;

    @Field
    @ApiModelProperty(value = "学习卡id",example = "1")
    private Long study_card_id;

    @Field
    @ApiModelProperty(value = "线下课程学生id",example = "1")
    private Long student_id;

    @Field
    @ApiModelProperty(value = "是否合格",example = "1")
    private Boolean is_qualified;

    @ApiModelProperty(value = "来源",example = "1")
    private String Source;

    @ApiModelProperty(value = "退款理由",example = "1")
    private String reason_for_refund;

    @ApiModelProperty(value = "教室名",example = "1")
    private String classroom;

    @ApiModelProperty(value = "创建方式 appointment 预约 scheduling 排课",example = "1")
    private String create_type;

    @ApiModelProperty(value = "当前课节数",example = "1")
    private Long number_of_lessons;

    @ApiModelProperty(value = "类目",example = "1")
    private String categories;
    
    @ApiModelProperty(value = "机构班级id",example = "1")
    private Long mechanism_class_id;

    @ApiModelProperty(value = "日期",example = "1")
    private String dates;

    /**
     * 是否教付宝
     */
    @Field
    @ApiModelProperty(value="是否教付宝",example="false")
    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public Long getMechanism_class_id() {
        return mechanism_class_id;
    }

    public void setMechanism_class_id(Long mechanism_class_id) {
        this.mechanism_class_id = mechanism_class_id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Long getNumber_of_lessons() {
        return number_of_lessons;
    }

    public void setNumber_of_lessons(Long number_of_lessons) {
        this.number_of_lessons = number_of_lessons;
    }

    public String getStudy_card_ids() {
        return study_card_ids;
    }

    public void setStudy_card_ids(String study_card_ids) {
        this.study_card_ids = study_card_ids;
    }

    public String getCreate_type() {
        return create_type;
    }

    public void setCreate_type(String create_type) {
        this.create_type = create_type;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getReason_for_refund() {
        return reason_for_refund;
    }

    public void setReason_for_refund(String reason_for_refund) {
        this.reason_for_refund = reason_for_refund;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public Boolean getIs_qualified() {
        return is_qualified;
    }

    public void setIs_qualified(Boolean is_qualified) {
        this.is_qualified = is_qualified;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public Long getStudy_card_id() {
        return study_card_id;
    }

    public void setStudy_card_id(Long study_card_id) {
        this.study_card_id = study_card_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Long getLike_count() {
        return like_count;
    }

    public void setLike_count(Long like_count) {
        this.like_count = like_count;
    }

    public Long getComment_count() {
        return comment_count;
    }

    public void setComment_count(Long comment_count) {
        this.comment_count = comment_count;
    }

    public Long getShare_count() {
        return share_count;
    }

    public void setShare_count(Long share_count) {
        this.share_count = share_count;
    }

    public Long getMasterLog_id() {
        return masterLog_id;
    }

    public void setMasterLog_id(Long masterLog_id) {
        this.masterLog_id = masterLog_id;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public Long getRecommender_id() {
        return recommender_id;
    }

    public void setRecommender_id(Long recommender_id) {
        this.recommender_id = recommender_id;
    }

    public BigDecimal getFinalEarn() {
        return finalEarn;
    }

    public void setFinalEarn(BigDecimal finalEarn) {
        this.finalEarn = finalEarn;
    }

    public void setMasterRecommenderCash(BigDecimal masterRecommenderCash) {
        this.masterRecommenderCash = masterRecommenderCash;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getLogin_name() {
        return Login_name;
    }

    public void setLogin_name(String login_name) {
        Login_name = login_name;
    }

    private List<Long> loginList;

    public List<Long> getLoginList() {
        return loginList;
    }

    public void setLoginList(List<Long> loginList) {
        this.loginList = loginList;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }

    public Boolean getIs_firstpage_show() {
        return is_firstpage_show;
    }

    public void setIs_firstpage_show(Boolean is_firstpage_show) {
        this.is_firstpage_show = is_firstpage_show;
    }

    public Boolean getIs_special() {
        return is_special;
    }

    public void setIs_special(Boolean is_special) {
        this.is_special = is_special;
    }

    public void setRecommend_type(Integer recommend_type) {
        this.recommend_type = recommend_type;
    }

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getIntroduction_cover() {
        return introduction_cover;
    }

    public void setIntroduction_cover(String introduction_cover) {
        this.introduction_cover = introduction_cover;
    }

    public String getIntroduction_content() {
        return introduction_content;
    }

    public void setIntroduction_content(String introduction_content) {
        this.introduction_content = introduction_content;
    }

    public String getClassroom_type() {
        return classroom_type;
    }

    public void setClassroom_type(String classroom_type) {
        this.classroom_type = classroom_type;
    }
    public String getTeach_program() {
        return teach_program;
    }

    public void setTeach_program(String teach_program) {
        this.teach_program = teach_program;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount_amout() {
        return discount_amout;
    }

    public void setDiscount_amout(BigDecimal discount_amout) {
        this.discount_amout = discount_amout;
    }

    public Boolean getIs_discount() {
        return is_discount;
    }

    public void setIs_discount(Boolean is_discount) {
        this.is_discount = is_discount;
    }

    public Boolean getIs_recording() {
        return is_recording;
    }

    public void setIs_recording(Boolean is_recording) {
        this.is_recording = is_recording;
    }

    public Integer getConnect_peoplenum() {
        return connect_peoplenum;
    }

    public void setConnect_peoplenum(Integer connect_peoplenum) {
        this.connect_peoplenum = connect_peoplenum;
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

    public List<Long> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(List<Long> group_ids) {
        this.group_ids = group_ids;
    }

    public BigDecimal getLocal_offset() {
        return local_offset;
    }

    public void setLocal_offset(BigDecimal local_offset) {
        this.local_offset = local_offset;
    }

    public String getTeach_language() {
        return teach_language;
    }

    public void setTeach_language(String teach_language) {
        this.teach_language = teach_language;
    }

    public String getTimecode() {
        return timecode;
    }

    public void setTimecode(String timecode) {
        this.timecode = timecode;
    }

    public String getEntities() {
        return entities;
    }

    public void setEntities(String entities) {
        this.entities = entities;
    }

    public Boolean getIs_appointment() {
        return is_appointment;
    }

    public void setIs_appointment(Boolean is_appointment) {
        this.is_appointment = is_appointment;
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
     * 助学师的user_id
     * @return master_id 助学师的user_id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 助学师的user_id
     * @param master_id 助学师的user_id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
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
     * 状态1正常 2已结束
     * @return status 状态1正常 2已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1正常 2已结束
     * @param status 状态1正常 2已结束 5草稿 6外教设置商品 ',
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型 single_class助学师课程 open_class公开直播课程
     * @return type 类型 single_class助学师课程 open_class公开直播课程
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 single_class助学师课程 open_class公开直播课程
     * @param type 类型 single_class助学师课程 open_class公开直播课程
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 与GMT的偏移量
     * @return offset 与GMT的偏移量
     */
    public BigDecimal getOffset() {
        return offset;
    }

    /**
     * 与GMT的偏移量
     * @param offset 与GMT的偏移量
     */
    public void setOffset(BigDecimal offset) {
        this.offset = offset;
    }

    /**
     * 时区id
     * @return timezone_id 时区id
     */
    public Long getTimezone_id() {
        return timezone_id;
    }

    /**
     * 时区id
     * @param timezone_id 时区id
     */
    public void setTimezone_id(Long timezone_id) {
        this.timezone_id = timezone_id;
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
     * 群id
     * @return group_id 群id
     */
    public Long getGroup_id() {
        return group_id;
    }

    /**
     * 群id
     * @param group_id 群id
     */
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
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
     * 面向人群年龄级别  Childhood/Juvenile/Youth/Middle_Age/Old_Aage
     * @return age_grade 面向人群年龄级别  Childhood/Juvenile/Youth/Middle_Age/Old_Aage
     */
    public String getAge_grade() {
        return age_grade;
    }

    /**
     * 面向人群年龄级别  Childhood/Juvenile/Youth/Middle_Age/Old_Aage
     * @param age_grade 面向人群年龄级别  Childhood/Juvenile/Youth/Middle_Age/Old_Aage
     */
    public void setAge_grade(String age_grade) {
        this.age_grade = age_grade == null ? null : age_grade.trim();
    }

    /**
     * 面向人群语言等级 Primary/Intermediate/Senior
     * @return language_level 面向人群语言等级 Primary/Intermediate/Senior
     */
    public String getLanguage_level() {
        return language_level;
    }

    /**
     * 面向人群语言等级 Primary/Intermediate/Senior
     * @param language_level 面向人群语言等级 Primary/Intermediate/Senior
     */
    public void setLanguage_level(String language_level) {
        this.language_level = language_level == null ? null : language_level.trim();
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
     *
     * @return group_type
     */
    public String getGroup_type() {
        return group_type;
    }

    /**
     *
     * @param group_type
     */
    public void setGroup_type(String group_type) {
        this.group_type = group_type == null ? null : group_type.trim();
    }
    /**
     * TIM房间id
     * @return room_id TIM房间id
     */
    public String getRoom_id() {
        return room_id;
    }

    /**
     * TIM房间id
     * @param room_id TIM房间id
     */
    public void setRoom_id(String room_id) {
        this.room_id = room_id == null ? null : room_id.trim();
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
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语
     * @return identity_type 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语
     */
    public String getIdentity_type() {
        return identity_type;
    }

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语
     * @param identity_type 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语
     */
    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type == null ? null : identity_type.trim();
    }

}