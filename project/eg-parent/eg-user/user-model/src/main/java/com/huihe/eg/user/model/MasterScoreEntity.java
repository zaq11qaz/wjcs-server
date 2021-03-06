package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="助学师评分列表",description="助学师评分列表属性说明")
public class MasterScoreEntity extends PageInfo {
    private static final long serialVersionUID = 6440114982169409802L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 用户(学生)id
     */
    @ApiModelProperty(value="用户(学生)id",example="1")
    private Long user_id;

    /**
     * 助学师id
     */
    @ApiModelProperty(value="助学师id",example="1")
    private Long master_id;

    /**
     * 助学师类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    @ApiModelProperty(value="助学师类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教")
    private String type;

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
     * 评分数值
     */
    @ApiModelProperty(value="评分数值",example="1")
    private BigDecimal score;

    /**
     * 评论内容
     */
    @ApiModelProperty(value="评论内容")
    private String content;

    /**
     * 状态 1 可用 2 不可用
     */
    @ApiModelProperty(value="状态 1 可用 2 不可用",example="1")
    private Integer status;

    /**
     * 课程id
     */
    @ApiModelProperty(value="课程id",example="1")
    private Long appointment_id;

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
     * 用户(学生)id
     * @return user_id 用户(学生)id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户(学生)id
     * @param user_id 用户(学生)id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 助学师id
     * @return master_id 助学师id
     */
    public Long getMaster_id() {
        return master_id;
    }

    /**
     * 助学师id
     * @param master_id 助学师id
     */
    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    /**
     * 助学师类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @return type 助学师类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public String getType() {
        return type;
    }

    /**
     * 助学师类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @param type 助学师类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 评分数值
     * @return score 评分数值
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 评分数值
     * @param score 评分数值
     */
    public void setScore(BigDecimal score) {
        this.score = score;
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
     * 状态 1 可用 2 不可用
     * @return status 状态 1 可用 2 不可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 可用 2 不可用
     * @param status 状态 1 可用 2 不可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 课程id
     * @return appointment_id 课程id
     */
    public Long getAppointment_id() {
        return appointment_id;
    }

    /**
     * 课程id
     * @param appointment_id 课程id
     */
    public void setAppointment_id(Long appointment_id) {
        this.appointment_id = appointment_id;
    }
}