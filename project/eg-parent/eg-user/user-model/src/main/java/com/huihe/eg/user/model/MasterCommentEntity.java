package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.cy.framework.util.StringUtil;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value = "打分评论列表", description = "打分评论列表属性说明")
public class MasterCommentEntity extends PageInfo {
    private static final long serialVersionUID = -1024067409247940473L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long user_id;

    /**
     * 状态 1 可用 2 不可用
     */
    @ApiModelProperty(value = "状态 1 可用 2 不可用", example = "1")
    private Integer status;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id", example = "1")
    private Long parent_id;

    /**
     * 是否是回复别人
     */
    @ApiModelProperty(value = "是否是回复别人", example = "false")
    private Boolean is_reply;

    /**
     * 是否是助学师发布
     */
    @ApiModelProperty(value = "是否是助学师发布", example = "false")
    private Boolean oneself;

    /**
     * 被回复的用户id
     */
    @ApiModelProperty(value = "被回复的用户id", example = "1")
    private Long reply_id;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String content;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分", example = "1")
    private BigDecimal score;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数", example = "1")
    private Integer like_count;

    /**
     * 助学师id
     */
    @ApiModelProperty(value = "助学师id", example = "1")
    private Long master_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date update_time;

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id", example = "1")
    private Long appointment_id;

    /**
     * 课堂id
     */
    @ApiModelProperty(value = "课堂id", example = "1")
    private Long group_id;

    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id", example = "1")
    private Long mechanism_id;

    /**
     * 课程订单id
     */
    @ApiModelProperty(value = "课程订单id", example = "1")
    private Long user_appointment_id;


    @ApiModelProperty(value = "map数据")
    private Map<String, Object> map;

    @ApiModelProperty(value = "商品id")
    private Long mastersetprice_id;

    @ApiModelProperty(value = "分享数")
    private Integer share_count;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "图片")
    private String photo_url;

    @ApiModelProperty(value = "课程质量")
    private Double course_quality;

    @ApiModelProperty(value = "环境")
    private Double environment;

    @ApiModelProperty(value = "性价比")
    private Double cost_effectiveness;

    @ApiModelProperty(value = "态度")
    private Double attitude;

    @ApiModelProperty(value = "师资力量")
    private Double faculty;

    @ApiModelProperty(value = "平均分")
    private Double average_score;

    @ApiModelProperty(value = "平均分")
    private Boolean anonymous;

    @ApiModelProperty(value = "机构名")
    private String mechanism_name;

    @ApiModelProperty(value = "用户昵称")
    private String nick_name;

    @ApiModelProperty(value = "商品名")
    private String commodities_name;


    public String getMechanism_name() {
        return mechanism_name;
    }

    public void setMechanism_name(String mechanism_name) {
        this.mechanism_name = mechanism_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getCommodities_name() {
        return commodities_name;
    }

    public void setCommodities_name(String commodities_name) {
        this.commodities_name = commodities_name;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public Double getCourse_quality() {
        return course_quality;
    }

    public void setCourse_quality(Double course_quality) {
        this.course_quality = course_quality;
    }

    public Double getEnvironment() {
        return environment;
    }

    public void setEnvironment(Double environment) {
        this.environment = environment;
    }

    public Double getCost_effectiveness() {
        return cost_effectiveness;
    }

    public void setCost_effectiveness(Double cost_effectiveness) {
        this.cost_effectiveness = cost_effectiveness;
    }

    public Double getAttitude() {
        return attitude;
    }

    public void setAttitude(Double attitude) {
        this.attitude = attitude;
    }

    public Double getFaculty() {
        return faculty;
    }

    public void setFaculty(Double faculty) {
        this.faculty = faculty;
    }

    public Double getAverage_score() {
        return average_score;
    }

    public void setAverage_score(Double average_score) {
        this.average_score = average_score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getShare_count() {
        return share_count;
    }

    public void setShare_count(Integer share_count) {
        this.share_count = share_count;
    }

    public Long getMastersetprice_id() {
        return mastersetprice_id;
    }

    public void setMastersetprice_id(Long mastersetprice_id) {
        this.mastersetprice_id = mastersetprice_id;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 主键
     *
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户ID
     *
     * @return user_id 用户ID
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户ID
     *
     * @param user_id 用户ID
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 状态 1 可用 2 不可用
     *
     * @return status 状态 1 可用 2 不可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 可用 2 不可用
     *
     * @param status 状态 1 可用 2 不可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 父id
     *
     * @return parent_id 父id
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * 父id
     *
     * @param parent_id 父id
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * 是否是回复别人
     *
     * @return is_reply 是否是回复别人
     */
    public Boolean getIs_reply() {
        return is_reply;
    }

    /**
     * 是否是回复别人
     *
     * @param is_reply 是否是回复别人
     */
    public void setIs_reply(Boolean is_reply) {
        this.is_reply = is_reply;
    }

    /**
     * 是否是助学师发布
     *
     * @return oneself 是否是助学师发布
     */
    public Boolean getOneself() {
        return oneself;
    }

    /**
     * 是否是助学师发布
     *
     * @param oneself 是否是助学师发布
     */
    public void setOneself(Boolean oneself) {
        this.oneself = oneself;
    }

    /**
     * 被回复的用户id
     *
     * @return reply_id 被回复的用户id
     */
    public Long getReply_id() {
        return reply_id;
    }

    /**
     * 被回复的用户id
     *
     * @param reply_id 被回复的用户id
     */
    public void setReply_id(Long reply_id) {
        this.reply_id = reply_id;
    }

    /**
     * 评论内容
     *
     * @return content 评论内容
     */
    public String getContent() {
        return StringUtil.isNotEmpty(content)? EmojiParser.parseToHtmlDecimal(content):content;
    }

    /**
     * 评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 评分
     *
     * @return score 评分
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 评分
     *
     * @param score 评分
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * 点赞数
     *
     * @return like_count 点赞数
     */
    public Integer getLike_count() {
        return like_count;
    }

    /**
     * 点赞数
     *
     * @param like_count 点赞数
     */
    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    /**
     * 助学师id
     *
     * @return master_id 助学师id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 助学师id
     *
     * @param master_id 助学师id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    /**
     * 创建时间
     *
     * @return create_time 创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间
     *
     * @param create_time 创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 修改时间
     *
     * @return update_time 修改时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 修改时间
     *
     * @param update_time 修改时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 课程id
     *
     * @return appointment_id 课程id
     */
    public Long getAppointment_id() {
        return appointment_id;
    }

    /**
     * 课程id
     *
     * @param appointment_id 课程id
     */
    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * 课堂id
     *
     * @return group_id 课堂id
     */
    public Long getGroup_id() {
        return group_id;
    }

    /**
     * 课堂id
     *
     * @param group_id 课堂id
     */
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    /**
     * 机构id
     *
     * @return mechanism_id 机构id
     */
    public Long getMechanism_id() {
        return mechanism_id;
    }

    /**
     * 机构id
     *
     * @param mechanism_id 机构id
     */
    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    /**
     * 课程id
     *
     * @return user_appointment_id 课程id
     */
    public Long getUser_appointment_id() {
        return user_appointment_id;
    }

    /**
     * 课程id
     *
     * @param user_appointment_id 课程id
     */
    public void setUser_appointment_id(Long user_appointment_id) {
        this.user_appointment_id = user_appointment_id;
    }
}