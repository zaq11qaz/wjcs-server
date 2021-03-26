package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="用户学习卡记录",description="用户学习卡记录属性说明")
public class UserStudyCardEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="")
    private Date create_time;

    /**
     * 
     */
    @ApiModelProperty(value="")
    private Date update_time;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    @ApiModelProperty(value="类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教  major_special 专业小班,cross_border_special 跨境小班,mother_tongue_special 母语小班")
    private String type;

    /**
     * 学习卡id
     */
    @ApiModelProperty(value="学习卡id",example="1")
    private Long studycard_id;

    /**
     * 剩余课节数
     */
    @ApiModelProperty(value="剩余课节数",example="1")
    private Integer course_num;

    /**
     * 1 过期 2 可用 3 取消
     */
    @ApiModelProperty(value="1 过期 2 可用 3 取消",example="1")
    private Integer status;

    /**
     * 报名助学师的id
     */
    @ApiModelProperty(value="报名助学师的id",example="1")
    private Long master_id;

    /**
     * 语言
     */
    @ApiModelProperty(value="语言")
    private String language;

    /**
     * 帐号
     */
    @ApiModelProperty(value="帐号",example="1")
    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    /**
     * 等级
     */
    @ApiModelProperty(value="等级",example="1")
    private Integer level;

    /**
     * 学习卡开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="学习卡开始时间")
    private Date start_time;

    /**
     * 学习结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="学习结束时间")
    private Date end_time;

    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    @ApiModelProperty(value = "绑定机构id")
    private Long mechanism_id;

    @ApiModelProperty(value = "线下绑定机构id")
    private Long binding_mechanism_id;

    /**
     * 帐号
     */
    @ApiModelProperty(value="帐号")
    private String login_name;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nick_name;

    /**
     * 是否一次性付 true是 false 不是
     */
    @ApiModelProperty(value="是否一次性付 true是 false 不是")
    private Boolean is_one_time_payment;

    /**
     * 是否交付宝 true是 false 不是
     */
    @ApiModelProperty(value="是否交付宝 true是 false 不是")
    private Boolean is_teach_paypal;

    /**
     * 是否付过押金 true是 false 不是
     */
    @ApiModelProperty(value="是否付过押金 true是 false 不是")
    private Boolean is_pay_deposit;

    /**
     * 是否互通 true 是 false 不是
     */
    @ApiModelProperty(value="是否互通 true 是 false 不是")
    private Boolean is_Interoperability;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    /**
     * 帐号集合
     */
    @ApiModelProperty(value="帐号集合")
    private List<Long> loginIds;

    /**
     * 帐号集合
     */
    @ApiModelProperty(value="帐号集合")
    private List<Long> nickNameIds;

    @ApiModelProperty(value="是否体验")
    private Boolean is_experience;

    @ApiModelProperty(value="三分之一结算标识")
    private Boolean settlement_one_third;

    @ApiModelProperty(value="三分之二结算标识")
    private Boolean settlement_two_thirds;

    @ApiModelProperty(value="全部结算标识")
    private Boolean settlement_all;

    @ApiModelProperty(value="是否可拼团 true 可 false 不可")
    private Boolean whether_grouping;

    @ApiModelProperty(value="原始课节数")
    private Integer original_course_num;

    @ApiModelProperty(value = "单节课价格")
    private BigDecimal each_lesson_price;

    @ApiModelProperty(value = "拼团id")
    private String user_group_id;

    @ApiModelProperty(value = "机构班级id")
    private Long mechanism_class_id;

    @ApiModelProperty(value = "商品组id")
    private Long master_set_price_group_id;

    public Boolean getIs_pay_deposit() {
        return is_pay_deposit;
    }

    public void setIs_pay_deposit(Boolean is_pay_deposit) {
        this.is_pay_deposit = is_pay_deposit;
    }

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public Boolean getWhether_grouping() {
        return whether_grouping;
    }

    public void setWhether_grouping(Boolean whether_grouping) {
        this.whether_grouping = whether_grouping;
    }

    public Long getMechanism_class_id() {
        return mechanism_class_id;
    }

    public void setMechanism_class_id(Long mechanism_class_id) {
        this.mechanism_class_id = mechanism_class_id;
    }

    public Long getMaster_set_price_group_id() {
        return master_set_price_group_id;
    }

    public void setMaster_set_price_group_id(Long master_set_price_group_id) {
        this.master_set_price_group_id = master_set_price_group_id;
    }

    public String getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(String user_group_id) {
        this.user_group_id = user_group_id;
    }

    public Boolean getSettlement_one_third() {
        return settlement_one_third;
    }

    public void setSettlement_one_third(Boolean settlement_one_third) {
        this.settlement_one_third = settlement_one_third;
    }

    public Boolean getSettlement_two_thirds() {
        return settlement_two_thirds;
    }

    public void setSettlement_two_thirds(Boolean settlement_two_thirds) {
        this.settlement_two_thirds = settlement_two_thirds;
    }

    public Boolean getSettlement_all() {
        return settlement_all;
    }

    public void setSettlement_all(Boolean settlement_all) {
        this.settlement_all = settlement_all;
    }

    public Long getBinding_mechanism_id() {
        return binding_mechanism_id;
    }

    public void setBinding_mechanism_id(Long binding_mechanism_id) {
        this.binding_mechanism_id = binding_mechanism_id;
    }

    public Boolean getIs_one_time_payment() {
        return is_one_time_payment;
    }

    public void setIs_one_time_payment(Boolean is_one_time_payment) {
        this.is_one_time_payment = is_one_time_payment;
    }

    public Boolean getIs_Interoperability() {
        return is_Interoperability;
    }

    public void setIs_Interoperability(Boolean is_Interoperability) {
        this.is_Interoperability = is_Interoperability;
    }

    public BigDecimal getEach_lesson_price() {
        return each_lesson_price;
    }

    public void setEach_lesson_price(BigDecimal each_lesson_price) {
        this.each_lesson_price = each_lesson_price;
    }

    public Integer getOriginal_course_num() {
        return original_course_num;
    }

    public void setOriginal_course_num(Integer original_course_num) {
        this.original_course_num = original_course_num;
    }

    public Boolean getIs_experience() {
        return is_experience;
    }

    public void setIs_experience(Boolean is_experience) {
        this.is_experience = is_experience;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public List<Long> getLoginIds() {
        return loginIds;
    }

    public void setLoginIds(List<Long> loginIds) {
        this.loginIds = loginIds;
    }

    public List<Long> getNickNameIds() {
        return nickNameIds;
    }

    public void setNickNameIds(List<Long> nickNameIds) {
        this.nickNameIds = nickNameIds;
    }

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
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
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
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
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @return type 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public String getType() {
        return type;
    }

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @param type 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 学习卡id
     * @return studycard_id 学习卡id
     */
    public Long getStudycard_id() {
        return studycard_id;
    }

    /**
     * 学习卡id
     * @param studycard_id 学习卡id
     */
    public void setStudycard_id(Long studycard_id) {
        this.studycard_id = studycard_id;
    }

    /**
     * 剩余课节数
     * @return course_num 剩余课节数
     */
    public Integer getCourse_num() {
        return course_num;
    }

    /**
     * 剩余课节数
     * @param course_num 剩余课节数
     */
    public void setCourse_num(Integer course_num) {
        this.course_num = course_num;
    }

    /**
     * 1 过期 2 可用 3 取消
     * @return status 1 过期 2 可用 3 取消
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 过期 2 可用 3 取消
     * @param status 1 过期 2 可用 3 取消
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 报名助学师的id
     * @return master_id 报名助学师的id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 报名助学师的id
     * @param master_id 报名助学师的id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
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
     * 等级
     * @return level 等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 等级
     * @param level 等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 学习卡开始时间
     * @return start_time 学习卡开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 学习卡开始时间
     * @param start_time 学习卡开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 学习结束时间
     * @return end_time 学习结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 学习结束时间
     * @param end_time 学习结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}