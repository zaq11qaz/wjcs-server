package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="用户预约助学师记录",description="用户预约助学师记录属性说明")
public class UserAppointmentEntity extends PageInfo {
    private static final long serialVersionUID = 6766612148808664503L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 助学师的user_id
     */
    @ApiModelProperty(value="助学师的user_id",example="1")
    private Long master_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 课节id
     */
    @ApiModelProperty(value="课节id",example="1")
    private Long appointment_id;

    /**
     * 状态 1 取消 2 待上课 3 课程完成  4 修改中 5 取消中 6线下课程待处理  7线下课程拒绝  8线下课程待支付 9线下课程签到
     */
    @ApiModelProperty(value="状态 1 取消 2 待上课 3 课程完成  4 修改中 5 取消中 6线下课程待处理  7线下课程拒绝  8线下课程待支付 9线下课程签到",example="1")
    private Integer status;

    /**
     * 课程开始时间
     */
    @ApiModelProperty(value="课程开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date start_time;

    /**
     * 课程结束时间
     */
    @ApiModelProperty(value="课程结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date end_time;

    /**
     * 提出修改的身份
     */
    @ApiModelProperty(value="提出修改的身份")
    private String update_identity;

    /**
     * 与GMT的偏移量
     */
    @ApiModelProperty(value="与GMT的偏移量",example="1")
    private BigDecimal offset;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 时区id
     */
    @ApiModelProperty(value="时区id",example="1")
    private Long timezone_id;

    /**
     * 将要修改成的课程id
     */
    @ApiModelProperty(value="将要修改成的课程id",example="1")
    private Long update_appointment_id;

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    @ApiModelProperty(value="助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教")
    private String master_type;

    /**
     * 语言
     */
    @ApiModelProperty(value="语言")
    private String language;

    /**
     * 收益
     */
    @ApiModelProperty(value="收益",example="1")
    private BigDecimal earnings;

    /**
     * 收益状态 1 未到账 2 已到账
     */
    @ApiModelProperty(value="收益状态 1 未到账 2 已到账",example="1")
    private Integer earnings_status;

    /**
     * 修改成的标题
     */
    @ApiModelProperty(value="修改成的标题")
    private String update_title;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarks;

    /**
     * 订单号
     */
    @ApiModelProperty(value="订单号")
    private String pipeline_num;

    /**
     * 服务类型 线上:online/线下offline
     */
    @ApiModelProperty(value="服务类型 线上:online/线下offline")
    private String service_type;

    /**
     * 线下课数
     */
    @ApiModelProperty(value="线下课数",example="1")
    private Integer offline_count;

    /**
     * 线下联系手机号
     */
    @ApiModelProperty(value="线下联系手机号")
    private String offline_mobile;

    /**
     * 线下学生数量
     */
    @ApiModelProperty(value="线下学生数量",example="1")
    private Integer student_count;

    /**
     * 线下上门地址
     */
    @ApiModelProperty(value="线下上门地址")
    private String offline_address;

    /**
     * 来源
     */
    @ApiModelProperty(value="来源")
    private String source;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 当前课时数
     */
    @ApiModelProperty(value="当前课时数",example="1")
    private Long number_of_lessons;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Long activity_id;

    /**
     * 坐标纬度值
     */
    @ApiModelProperty(value="坐标纬度值",example="1")
    private BigDecimal latitude;

    @ApiModelProperty(value="是否专属",example="false")
    private Boolean is_exclusive;

    @ApiModelProperty(value="使用券id",example="false")
    private Long coupon_id;

    @ApiModelProperty(value="使用券id",example="false")
    private Integer point;

    public Long getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Long coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Boolean getIs_exclusive() {
        return is_exclusive;
    }

    public void setIs_exclusive(Boolean is_exclusive) {
        this.is_exclusive = is_exclusive;
    }

    /**
     * 坐标经度值
     */
    @ApiModelProperty(value="坐标经度值",example="1")
    private BigDecimal longitude;

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Long getMechanism_id() {
        return mechanism_id;
    }

    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }
    @ApiModelProperty(value = "集合")
    private String userAppointmentEntities;

    public String getUserAppointmentEntities() {
        return userAppointmentEntities;
    }

    public void setUserAppointmentEntities(String userAppointmentEntities) {
        this.userAppointmentEntities = userAppointmentEntities;
    }
    @ApiModelProperty(value = "身份 master 助学师, user 学生 ")
    private String identity;

    public String getIdentity() {
        return identity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    @ApiModelProperty(value = "确认/拒绝",example = "false")
    private Boolean whether;

    public Boolean getWhether() {
        return whether;
    }

    public void setWhether(Boolean whether) {
        this.whether = whether;
    }
    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @ApiModelProperty(value = "是否申请还是回复",example = "false")
    private Boolean is_apply;

    public Boolean getIs_apply() {
        return is_apply;
    }

    public void setIs_apply(Boolean is_apply) {
        this.is_apply = is_apply;
    }

    @ApiModelProperty(value = "修改的类型  修改时间：update, 取消：cancel")
    private String update_type;

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


    public String getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(String update_type) {
        this.update_type = update_type;
    }

    @ApiModelProperty(value = "学习卡id",example = "1")
    private Long studyCard;

    public Long getStudyCard() {
        return studyCard;
    }

    public void setStudyCard(Long studyCard) {
        this.studyCard = studyCard;
    }

    @ApiModelProperty(value = "消息id",example = "1")
    private Long  masterNotice_id;

    public Long getMasterNotice_id() {
        return masterNotice_id;
    }

    public void setMasterNotice_id(Long masterNotice_id) {
        this.masterNotice_id = masterNotice_id;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date statistics_time;

    @ApiModelProperty(value = "绑定学习卡id",example = "1")
    private Long study_card_id;

    @ApiModelProperty(value = "该课程是否需要付钱 true 是 false 不是",example = "1")
    private Boolean is_pay;

    @ApiModelProperty(value = "绑定商品id",example = "1")
    private Long master_set_price_id;

    @ApiModelProperty(value = "是否评论",example = "1")
    private Boolean is_comment;

    public Long getNumber_of_lessons() {
        return number_of_lessons;
    }

    public void setNumber_of_lessons(Long number_of_lessons) {
        this.number_of_lessons = number_of_lessons;
    }

    public Boolean getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(Boolean is_comment) {
        this.is_comment = is_comment;
    }

    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }

    private String full_name;

    private String login_name;

    private List<Long> ids;

    private List<Long> loginIDs;

    public Boolean getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(Boolean is_pay) {
        this.is_pay = is_pay;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Long getStudy_card_id() {
        return study_card_id;
    }

    public void setStudy_card_id(Long study_card_id) {
        this.study_card_id = study_card_id;
    }

    public Date getStatistics_time() {
        return statistics_time;
    }

    public void setStatistics_time(Date statistics_time) {
        this.statistics_time = statistics_time;
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
     * 课节id
     * @return appointment_id 课节id
     */
    public Long getAppointment_id() {
        return appointment_id;
    }

    /**
     * 课节id
     * @param appointment_id 课节id
     */
    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * 状态 1 取消 2 待上课 3 课程完成  4 修改中 5 取消中 6线下课程待处理  7线下课程拒绝  8线下课程待支付 9线下课程签到
     * @return status 状态 1 取消 2 待上课 3 课程完成  4 修改中 5 取消中 6线下课程待处理  7线下课程拒绝  8线下课程待支付 9线下课程签到
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *状态 1 取消 2 待上课 3 课程完成  4 修改中 5 取消中 6线下课程待处理  7线下课程拒绝  8线下课程待支付 9线下课程签到
     * @param status 状态 1 取消 2 待上课 3 课程完成  4 修改中 5 取消中 6线下课程待处理  7线下课程拒绝  8线下课程待支付 9线下课程签到
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 课程开始时间
     * @return start_time 课程开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 课程开始时间
     * @param start_time 课程开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 课程结束时间
     * @return end_time 课程结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 课程结束时间
     * @param end_time 课程结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 提出修改的身份
     * @return update_identity 提出修改的身份
     */
    public String getUpdate_identity() {
        return update_identity;
    }

    /**
     * 提出修改的身份
     * @param update_identity 提出修改的身份
     */
    public void setUpdate_identity(String update_identity) {
        this.update_identity = update_identity == null ? null : update_identity.trim();
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
     * 将要修改成的课程id
     * @return update_appointment_id 将要修改成的课程id
     */
    public Long getUpdate_appointment_id() {
        return update_appointment_id;
    }

    /**
     * 将要修改成的课程id
     * @param update_appointment_id 将要修改成的课程id
     */
    public void setUpdate_appointment_id(Long update_appointment_id) {
        this.update_appointment_id = update_appointment_id;
    }

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @return master_type 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public String getMaster_type() {
        return master_type;
    }

    /**
     * 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @param master_type 助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education 私教 mechanism_offline 机构线下课程
     */
    public void setMaster_type(String master_type) {
        this.master_type = master_type == null ? null : master_type.trim();
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
     * 收益状态 1 未到账 2 已到账
     * @return earnings_status 收益状态 1 未到账 2 已到账
     */
    public Integer getEarnings_status() {
        return earnings_status;
    }

    /**
     * 收益状态 1 未到账 2 已到账
     * @param earnings_status 收益状态 1 未到账 2 已到账
     */
    public void setEarnings_status(Integer earnings_status) {
        this.earnings_status = earnings_status;
    }

    /**
     * 修改成的标题
     * @return update_title 修改成的标题
     */
    public String getUpdate_title() {
        return update_title;
    }

    /**
     * 修改成的标题
     * @param update_title 修改成的标题
     */
    public void setUpdate_title(String update_title) {
        this.update_title = update_title == null ? null : update_title.trim();
    }

    /**
     * 备注
     * @return remarks 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * 订单号
     * @return pipeline_num 订单号
     */
    public String getPipeline_num() {
        return pipeline_num;
    }

    /**
     * 订单号
     * @param pipeline_num 订单号
     */
    public void setPipeline_num(String pipeline_num) {
        this.pipeline_num = pipeline_num == null ? null : pipeline_num.trim();
    }

    /**
     * 服务类型 线上:online/线下offline
     * @return service_type 服务类型 线上:online/线下offline
     */
    public String getService_type() {
        return service_type;
    }

    /**
     * 服务类型 线上:online/线下offline
     * @param service_type 服务类型 线上:online/线下offline
     */
    public void setService_type(String service_type) {
        this.service_type = service_type == null ? null : service_type.trim();
    }

    /**
     * 线下课数
     * @return offline_count 线下课数
     */
    public Integer getOffline_count() {
        return offline_count;
    }

    /**
     * 线下课数
     * @param offline_count 线下课数
     */
    public void setOffline_count(Integer offline_count) {
        this.offline_count = offline_count;
    }

    /**
     * 线下联系手机号
     * @return offline_mobile 线下联系手机号
     */
    public String getOffline_mobile() {
        return offline_mobile;
    }

    /**
     * 线下联系手机号
     * @param offline_mobile 线下联系手机号
     */
    public void setOffline_mobile(String offline_mobile) {
        this.offline_mobile = offline_mobile == null ? null : offline_mobile.trim();
    }

    /**
     * 线下学生数量
     * @return student_count 线下学生数量
     */
    public Integer getStudent_count() {
        return student_count;
    }

    /**
     * 线下学生数量
     * @param student_count 线下学生数量
     */
    public void setStudent_count(Integer student_count) {
        this.student_count = student_count;
    }

    /**
     * 线下上门地址
     * @return offline_address 线下上门地址
     */
    public String getOffline_address() {
        return offline_address;
    }

    /**
     * 线下上门地址
     * @param offline_address 线下上门地址
     */
    public void setOffline_address(String offline_address) {
        this.offline_address = offline_address == null ? null : offline_address.trim();
    }

}