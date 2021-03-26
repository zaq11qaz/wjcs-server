package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
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

@ApiModel(value="用户群列表记录",description="用户群列表记录属性说明")
@Document(indexName = "group",type = "messagegroup",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class MessageGroupEntity extends PageInfo {
    /**
     * 主键ID
     */
    @Id
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 群主id
     */
    @Field
    @ApiModelProperty(value="群主id",example="1")
    private Long owner_id;

    /**
     * 群名称
     */
    @Field
    @ApiModelProperty(value="群名称")
    private String group_name;

    /**
     * 群组类型
     */
    @Field
    @ApiModelProperty(value="群组类型")
    private String group_type;

    /**
     * 操作的用户
     */
    @Field
    @ApiModelProperty(value="操作的用户")
    private String operator_account;

    /**
     * 1.直播 2.停播
     */
    @Field
    @ApiModelProperty(value="1.直播 2.停播",example="1")
    private Integer status;

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
     * TIM群id
     */
    @Field
    @ApiModelProperty(value="TIM群id")
    private String group_id;

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
     * 群简介
     */
    @Field
    @ApiModelProperty(value="群简介")
    private String introduction;

    /**
     * 群公告
     */
    @Field
    @ApiModelProperty(value="群公告")
    private String notification;

    /**
     * 群头像
     */
    @Field
    @ApiModelProperty(value="群头像")
    private String faceUrl;

    /**
     * 所有人进讨论组需本人同意
     */
    @Field
    @ApiModelProperty(value="所有人进讨论组需本人同意",example="false")
    private Boolean advance_group;

    /**
     * 是否公开
     */
    @Field
    @ApiModelProperty(value="是否公开",example="false")
    private Boolean is_open;

    /**
     * 是否收费
     */
    @Field
    @ApiModelProperty(value="是否收费",example="false")
    private Boolean is_charge;

    /**
     * 猫币/人
     */
    @Field
    @ApiModelProperty(value="猫币/人",example="1")
    private BigDecimal fee_standard;

    /**
     * 进入密码
     */
    @Field
    @ApiModelProperty(value="进入密码")
    private String entry_password;

    /**
     * 群人数
     */
    @Field
    @ApiModelProperty(value="群人数",example="1")
    private Integer people_num;

    /**
     * 开播时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @ApiModelProperty(value="开播时间")
    private String show_time;

    /**
     * 收益
     */
    @Field
    @ApiModelProperty(value="收益",example="1")
    private BigDecimal earnings;

    /**
     * 参与人数
     */
    @Field
    @ApiModelProperty(value="参与人数",example="1")
    private Integer number_participants;

    /**
     * 推流地址
     */
    @Field
    @ApiModelProperty(value="推流地址")
    private String live_push_addr;

    /**
     * 语言
     */
    @Field
    @ApiModelProperty(value="语言")
    private String language;

    /**
     * 标签
     */
    @Field
    @ApiModelProperty(value="标签")
    private String label;

    /**
     * 类别  voice_chat普通语音  online_video在线视频   online_voice在线语音   realtime_video实时视频 realtime_voice实时语音,life_live生活直播,recreation_live娱乐直播
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="类别  voice_chat普通语音  online_video在线视频   online_voice在线语音   realtime_video实时视频 realtime_voice实时语音,life_live生活直播,recreation_live娱乐直播")
    private String type;

    /**
     * 推荐type:home首页 
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="推荐type:home首页 ")
    private String recommend_type;

    /**
     * 在线人数
     */
    @Field
    @ApiModelProperty(value="在线人数",example="1")
    private Integer online_num;

    /**
     * 门票收益
     */
    @Field
    @ApiModelProperty(value="门票收益",example="1")
    private BigDecimal tickets;

    /**
     * 连麦时长
     */
    @Field
    @ApiModelProperty(value="连麦时长",example="1")
    private Integer live_duration;

    /**
     * 观看时长
     */
    @Field
    @ApiModelProperty(value="观看时长",example="1")
    private Integer watch_duration;

    /**
     * 面向人群语言等级 面向人群年龄级别  Childhood儿童,Teenagers少年,University Student,大学生,Adult成人
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="面向人群语言等级 面向人群年龄级别  Childhood儿童,Teenagers少年,University Student,大学生,Adult成人")
    private String age_grade;

    /**
     * 面向人群语言等级 Zero basis零基础,Primary初级,Intermediate中级,Senior 高级;
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="面向人群语言等级 Zero basis零基础,Primary初级,Intermediate中级,Senior 高级;")
    private String language_level;

    @ApiModelProperty(value = "map其他")
    private Map<String,Object> map;

    @Field
    @ApiModelProperty(value = "是否首页筛选生活娱乐群",example = "false")
    private Boolean is_life;

    @ApiModelProperty(value = "帐号",example = "false")
    private String login_name;

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


    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Boolean getIs_life() {
        return is_life;
    }

    public void setIs_life(Boolean is_life) {
        this.is_life = is_life;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    @ApiModelProperty(value = "id集合",example = "[]")
    private List<Long> idList;

    private List<Long> ids;

    private List<Long>  loginIds;

    public List<Long>  getLoginIds() {
        return loginIds;
    }

    public void setLoginIds(List<Long>  loginIds) {
        this.loginIds = loginIds;
    }

    public List<Long>  getIds() {
        return ids;
    }

    public void setIds(List<Long>  ids) {
        this.ids = ids;
    }

    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
    /**
     * 主键ID
     * @return id 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 群主id
     * @return owner_id 群主id
     */
    public Long getOwner_id() {
        return owner_id;
    }

    /**
     * 群主id
     * @param owner_id 群主id
     */
    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    /**
     * 群名称
     * @return group_name 群名称
     */
    public String getGroup_name() {
        return group_name;
    }

    /**
     * 群名称
     * @param group_name 群名称
     */
    public void setGroup_name(String group_name) {
        this.group_name = group_name == null ? null : group_name.trim();
    }

    /**
     * 群组类型
     * @return group_type 群组类型
     */
    public String getGroup_type() {
        return group_type;
    }

    /**
     * 群组类型
     * @param group_type 群组类型
     */
    public void setGroup_type(String group_type) {
        this.group_type = group_type == null ? null : group_type.trim();
    }

    /**
     * 操作的用户
     * @return operator_account 操作的用户
     */
    public String getOperator_account() {
        return operator_account;
    }

    /**
     * 操作的用户
     * @param operator_account 操作的用户
     */
    public void setOperator_account(String operator_account) {
        this.operator_account = operator_account == null ? null : operator_account.trim();
    }

    /**
     * 1.直播 2.停播
     * @return status 1.直播 2.停播
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1.直播 2.停播
     * @param status 1.直播 2.停播
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
     * TIM群id
     * @return group_id TIM群id
     */
    public String getGroup_id() {
        return group_id;
    }

    /**
     * TIM群id
     * @param group_id TIM群id
     */
    public void setGroup_id(String group_id) {
        this.group_id = group_id == null ? null : group_id.trim();
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
     * 群简介
     * @return introduction 群简介
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 群简介
     * @param introduction 群简介
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * 群公告
     * @return notification 群公告
     */
    public String getNotification() {
        return notification;
    }

    /**
     * 群公告
     * @param notification 群公告
     */
    public void setNotification(String notification) {
        this.notification = notification == null ? null : notification.trim();
    }

    /**
     * 群头像
     * @return faceUrl 群头像
     */
    public String getFaceUrl() {
        return faceUrl;
    }

    /**
     * 群头像
     * @param faceUrl 群头像
     */
    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl == null ? null : faceUrl.trim();
    }

    /**
     * 所有人进讨论组需本人同意
     * @return advance_group 所有人进讨论组需本人同意
     */
    public Boolean getAdvance_group() {
        return advance_group;
    }

    /**
     * 所有人进讨论组需本人同意
     * @param advance_group 所有人进讨论组需本人同意
     */
    public void setAdvance_group(Boolean advance_group) {
        this.advance_group = advance_group;
    }

    /**
     * 是否公开
     * @return is_open 是否公开
     */
    public Boolean getIs_open() {
        return is_open;
    }

    /**
     * 是否公开
     * @param is_open 是否公开
     */
    public void setIs_open(Boolean is_open) {
        this.is_open = is_open;
    }

    /**
     * 是否收费
     * @return is_charge 是否收费
     */
    public Boolean getIs_charge() {
        return is_charge;
    }

    /**
     * 是否收费
     * @param is_charge 是否收费
     */
    public void setIs_charge(Boolean is_charge) {
        this.is_charge = is_charge;
    }

    /**
     * 猫币/人
     * @return fee_standard 猫币/人
     */
    public BigDecimal getFee_standard() {
        return fee_standard;
    }

    /**
     * 猫币/人
     * @param fee_standard 猫币/人
     */
    public void setFee_standard(BigDecimal fee_standard) {
        this.fee_standard = fee_standard;
    }

    /**
     * 进入密码
     * @return entry_password 进入密码
     */
    public String getEntry_password() {
        return entry_password;
    }

    /**
     * 进入密码
     * @param entry_password 进入密码
     */
    public void setEntry_password(String entry_password) {
        this.entry_password = entry_password == null ? null : entry_password.trim();
    }

    /**
     * 群人数
     * @return people_num 群人数
     */
    public Integer getPeople_num() {
        return people_num;
    }

    /**
     * 群人数
     * @param people_num 群人数
     */
    public void setPeople_num(Integer people_num) {
        this.people_num = people_num;
    }

    /**
     * 开播时间
     * @return show_time 开播时间
     */
    public String getShow_time() {
        return show_time;
    }

    /**
     * 开播时间
     * @param show_time 开播时间
     */
    public void setShow_time(String show_time) {
        this.show_time = show_time == null ? null : show_time.trim();
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
     * 参与人数
     * @return number_participants 参与人数
     */
    public Integer getNumber_participants() {
        return number_participants;
    }

    /**
     * 参与人数
     * @param number_participants 参与人数
     */
    public void setNumber_participants(Integer number_participants) {
        this.number_participants = number_participants;
    }

    /**
     * 推流地址
     * @return live_push_addr 推流地址
     */
    public String getLive_push_addr() {
        return live_push_addr;
    }

    /**
     * 推流地址
     * @param live_push_addr 推流地址
     */
    public void setLive_push_addr(String live_push_addr) {
        this.live_push_addr = live_push_addr == null ? null : live_push_addr.trim();
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
     * 标签
     * @return label 标签
     */
    public String getLabel() {
        return label;
    }

    /**
     * 标签
     * @param label 标签
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 类别  voice_chat普通语音  online_video在线视频   online_voice在线语音   realtime_video实时视频 realtime_voice实时语音,life_live生活直播,recreation_live娱乐直播
     * @return type 类别  voice_chat普通语音  online_video在线视频   online_voice在线语音   realtime_video实时视频 realtime_voice实时语音,life_live生活直播,recreation_live娱乐直播
     */
    public String getType() {
        return type;
    }

    /**
     * 类别  voice_chat普通语音  online_video在线视频   online_voice在线语音   realtime_video实时视频 realtime_voice实时语音,life_live生活直播,recreation_live娱乐直播
     * @param type 类别  voice_chat普通语音  online_video在线视频   online_voice在线语音   realtime_video实时视频 realtime_voice实时语音,life_live生活直播,recreation_live娱乐直播
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 推荐type:home首页 
     * @return recommend_type 推荐type:home首页 
     */
    public String getRecommend_type() {
        return recommend_type;
    }

    /**
     * 推荐type:home首页 
     * @param recommend_type 推荐type:home首页 
     */
    public void setRecommend_type(String recommend_type) {
        this.recommend_type = recommend_type == null ? null : recommend_type.trim();
    }

    /**
     * 在线人数
     * @return online_num 在线人数
     */
    public Integer getOnline_num() {
        return online_num;
    }

    /**
     * 在线人数
     * @param online_num 在线人数
     */
    public void setOnline_num(Integer online_num) {
        this.online_num = online_num;
    }

    /**
     * 门票收益
     * @return tickets 门票收益
     */
    public BigDecimal getTickets() {
        return tickets;
    }

    /**
     * 门票收益
     * @param tickets 门票收益
     */
    public void setTickets(BigDecimal tickets) {
        this.tickets = tickets;
    }

    /**
     * 连麦时长
     * @return live_duration 连麦时长
     */
    public Integer getLive_duration() {
        return live_duration;
    }

    /**
     * 连麦时长
     * @param live_duration 连麦时长
     */
    public void setLive_duration(Integer live_duration) {
        this.live_duration = live_duration;
    }

    /**
     * 观看时长
     * @return watch_duration 观看时长
     */
    public Integer getWatch_duration() {
        return watch_duration;
    }

    /**
     * 观看时长
     * @param watch_duration 观看时长
     */
    public void setWatch_duration(Integer watch_duration) {
        this.watch_duration = watch_duration;
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
}