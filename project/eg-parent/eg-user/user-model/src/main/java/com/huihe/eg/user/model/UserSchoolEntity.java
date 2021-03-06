package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户学校经历",description="用户学校经历属性说明")
public class UserSchoolEntity extends PageInfo {
    private static final long serialVersionUID = 6279300936242614134L;
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
     * 学校名称
     */
    @ApiModelProperty(value="学校名称")
    private String school_name;

    /**
     * 入学时间
     */
    @ApiModelProperty(value="入学时间")
    private String start_time;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private String end_time;

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
     * 是否可见，1可见，0隐藏
     */
    @ApiModelProperty(value="是否可见，1可见，0隐藏",example="false")
    private Boolean is_visible;

    @ApiModelProperty(value = "操作用户",example = "1")
    private Long oper_id;

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
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
     * 学校名称
     * @return school_name 学校名称
     */
    public String getSchool_name() {
        return school_name;
    }

    /**
     * 学校名称
     * @param school_name 学校名称
     */
    public void setSchool_name(String school_name) {
        this.school_name = school_name == null ? null : school_name.trim();
    }

    /**
     * 入学时间
     * @return start_time 入学时间
     */
    public String getStart_time() {
        return start_time;
    }

    /**
     * 入学时间
     * @param start_time 入学时间
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time == null ? null : start_time.trim();
    }

    /**
     * 结束时间
     * @return end_time 结束时间
     */
    public String getEnd_time() {
        return end_time;
    }

    /**
     * 结束时间
     * @param end_time 结束时间
     */
    public void setEnd_time(String end_time) {
        this.end_time = end_time == null ? null : end_time.trim();
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
     * 是否可见，1可见，0隐藏
     * @return is_visible 是否可见，1可见，0隐藏
     */
    public Boolean getIs_visible() {
        return is_visible;
    }

    /**
     * 是否可见，1可见，0隐藏
     * @param is_visible 是否可见，1可见，0隐藏
     */
    public void setIs_visible(Boolean is_visible) {
        this.is_visible = is_visible;
    }
}