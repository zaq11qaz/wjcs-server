package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="机构考试表",description="机构考试表属性说明")
public class MechanismExamEntity extends PageInfo {
    private static final long serialVersionUID = 6187774945795406573L;
    /**
     * id
     */
    @ApiModelProperty(value="id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechansim_id;

    /**
     * 名
     */
    @ApiModelProperty(value="名")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 考试项目
     */
    @ApiModelProperty(value="考试项目")
    private String exam_items;

    /**
     * 状态 1 不可用 2 可用
     */
    @ApiModelProperty(value="状态 1 不可用 2 可用",example="1")
    private Integer status;

    /**
     * 校区
     */
    @ApiModelProperty(value="校区")
    private String school;

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
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 机构id
     * @return mechansim_id 机构id
     */
    public Long getMechansim_id() {
        return mechansim_id;
    }

    /**
     * 机构id
     * @param mechansim_id 机构id
     */
    public void setMechansim_id(Long mechansim_id) {
        this.mechansim_id = mechansim_id;
    }

    /**
     * 名
     * @return name 名
     */
    public String getName() {
        return name;
    }

    /**
     * 名
     * @param name 名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 类型
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 考试项目
     * @return exam_items 考试项目
     */
    public String getExam_items() {
        return exam_items;
    }

    /**
     * 考试项目
     * @param exam_items 考试项目
     */
    public void setExam_items(String exam_items) {
        this.exam_items = exam_items == null ? null : exam_items.trim();
    }

    /**
     * 状态 1 不可用 2 可用
     * @return status 状态 1 不可用 2 可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 不可用 2 可用
     * @param status 状态 1 不可用 2 可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 校区
     * @return school 校区
     */
    public String getSchool() {
        return school;
    }

    /**
     * 校区
     * @param school 校区
     */
    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }
}