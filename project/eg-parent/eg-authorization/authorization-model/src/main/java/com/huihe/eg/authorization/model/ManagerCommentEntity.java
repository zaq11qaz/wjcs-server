package com.huihe.eg.authorization.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="客服评论表",description="客服评论表属性说明")
public class ManagerCommentEntity extends PageInfo {
    /**
     * id
     */
    @ApiModelProperty(value="id",example="1")
    private Long id;

    /**
     * manageruser主键id
     */
    @ApiModelProperty(value="manageruser主键id",example="1")
    private Long manager_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 评分
     */
    @ApiModelProperty(value="评分")
    private Double score;

    /**
     * 评论内容
     */
    @ApiModelProperty(value="评论内容")
    private String content;

    /**
     * id
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * manageruser主键id
     * @return manager_id manageruser主键id
     */
    public Long getManager_id() {
        return manager_id;
    }

    /**
     * manageruser主键id
     * @param manager_id manageruser主键id
     */
    public void setManager_id(Long manager_id) {
        this.manager_id = manager_id;
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
     * 评分
     * @return score 评分
     */
    public Double getScore() {
        return score;
    }

    /**
     * 评分
     * @param score 评分
     */
    public void setScore(Double score) {
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
}