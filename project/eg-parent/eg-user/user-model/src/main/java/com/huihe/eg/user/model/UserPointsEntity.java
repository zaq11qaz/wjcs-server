package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value="积分记录",description="积分记录属性说明")
public class UserPointsEntity extends PageInfo {
    private static final long serialVersionUID = -6996874064503661040L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Integer id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long usre_id;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型",example="1")
    private Integer point;

    /**
     * 来源
     */
    @ApiModelProperty(value="来源")
    private String type;

    /**
     * 来源
     */
    @ApiModelProperty(value="来源")
    private Boolean is_earnings;

    public Boolean getIs_earnings() {
        return is_earnings;
    }

    public void setIs_earnings(Boolean is_earnings) {
        this.is_earnings = is_earnings;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 主键id
     * @return id 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
     * @param id 主键id
     */
    public void setId(Integer id) {
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
     * 用户id
     * @return usre_id 用户id
     */
    public Long getUsre_id() {
        return usre_id;
    }

    /**
     * 用户id
     * @param usre_id 用户id
     */
    public void setUsre_id(Long usre_id) {
        this.usre_id = usre_id;
    }

    /**
     * 类型
     * @return point 类型
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * 类型
     * @param point 类型
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * 来源
     * @return type 来源
     */
    public String getType() {
        return type;
    }

    /**
     * 来源
     * @param type 来源
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}