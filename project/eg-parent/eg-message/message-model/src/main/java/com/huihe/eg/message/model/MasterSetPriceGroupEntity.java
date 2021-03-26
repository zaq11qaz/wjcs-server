package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="商品生成时创建组",description="商品生成时创建组属性说明")
public class MasterSetPriceGroupEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 随机8位数
     */
    @ApiModelProperty(value="组字符串",example="1")
    private Long group_id;

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
     * 状态 1 不可用 2 可用
     */
    @ApiModelProperty(value="状态 1 不可用 2 可用",example="1")
    private Integer status;

    /**
     * 学生人数
     */
    @ApiModelProperty(value="学生人数",example="1")
    private Long student_count;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id",example="1")
    private Long master_set_price_id;

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

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
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
     * 学生人数
     * @return student_count 学生人数
     */
    public Long getStudent_count() {
        return student_count;
    }

    /**
     * 学生人数
     * @param student_count 学生人数
     */
    public void setStudent_count(Long student_count) {
        this.student_count = student_count;
    }

    /**
     * 商品id
     * @return master_set_price_id 商品id
     */
    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    /**
     * 商品id
     * @param master_set_price_id 商品id
     */
    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }
}