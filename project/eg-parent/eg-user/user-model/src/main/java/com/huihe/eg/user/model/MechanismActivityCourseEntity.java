package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/15 11:32
 * @ Description：机构活动商品对象
 * @ since: JDk1.8
 */
@ApiModel(value="机构活动商品对象",description="机构活动商品对象")
public class MechanismActivityCourseEntity implements Serializable {
    private static final long serialVersionUID = -3511288281409156867L;
    /**
     * id
     */
    @ApiModelProperty(value="id")
    private String id;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;
    /**
     * 课时长度
     */
    @ApiModelProperty(value="课时长度")
    private String length_of_lesson;
    /**
     * 课时数
     */
    @ApiModelProperty(value="课时数")
    private String course_num;
    /**
     * 适用年龄
     */
    @ApiModelProperty(value="适用年龄")
    private String apply_age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength_of_lesson() {
        return length_of_lesson;
    }

    public void setLength_of_lesson(String length_of_lesson) {
        this.length_of_lesson = length_of_lesson;
    }

    public String getCourse_num() {
        return course_num;
    }

    public void setCourse_num(String course_num) {
        this.course_num = course_num;
    }

    public String getApply_age() {
        return apply_age;
    }

    public void setApply_age(String apply_age) {
        this.apply_age = apply_age;
    }
}
