package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="友圈评论表",description="友圈评论表属性说明")
public class NoteCommentEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 动态id
     */
    @ApiModelProperty(value="动态id",example="1")
    private Long note_id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 评论内容
     */
    @ApiModelProperty(value="评论内容")
    private String content;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 点赞数
     */
    @ApiModelProperty(value="点赞数",example="1")
    private Integer like_count;

    /**
     * 状态1.通过2.被屏蔽
     */
    @ApiModelProperty(value="状态1.通过2.被屏蔽",example="1")
    private Integer status;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 父评论id
     */
    @ApiModelProperty(value="父评论id",example="1")
    private Long parent_id;

    /**
     * 是否回复
     */
    @ApiModelProperty(value="是否回复",example="false")
    private Boolean is_reply;

    /**
     * 被回复用户的id
     */
    @ApiModelProperty(value="被回复用户的id",example="1")
    private Long reply_id;

    @ApiModelProperty(value = "操作id",example = "1")
    private Long oper_id;
    @ApiModelProperty(value = "map其他")
    private Map<String,Object> map;

    @ApiModelProperty(value = "登录来源",example = "ios")
    private String platform;

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


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 动态id
     * @return note_id 动态id
     */
    public Long getNote_id() {
        return note_id;
    }

    /**
     * 动态id
     * @param note_id 动态id
     */
    public void setNote_id(Long note_id) {
        this.note_id = note_id;
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
     * 评论内容
     * @return content 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 评论内容
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
     * 点赞数
     * @return like_count 点赞数
     */
    public Integer getLike_count() {
        return like_count;
    }

    /**
     * 点赞数
     * @param like_count 点赞数
     */
    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    /**
     * 状态1.通过2.被屏蔽
     * @return status 状态1.通过2.被屏蔽
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.通过2.被屏蔽
     * @param status 状态1.通过2.被屏蔽
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 父评论id
     * @return parent_id 父评论id
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * 父评论id
     * @param parent_id 父评论id
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * 是否回复
     * @return is_reply 是否回复
     */
    public Boolean getIs_reply() {
        return is_reply;
    }

    /**
     * 是否回复
     * @param is_reply 是否回复
     */
    public void setIs_reply(Boolean is_reply) {
        this.is_reply = is_reply;
    }

    /**
     * 被回复用户的id
     * @return reply_id 被回复用户的id
     */
    public Long getReply_id() {
        return reply_id;
    }

    /**
     * 被回复用户的id
     * @param reply_id 被回复用户的id
     */
    public void setReply_id(Long reply_id) {
        this.reply_id = reply_id;
    }
}