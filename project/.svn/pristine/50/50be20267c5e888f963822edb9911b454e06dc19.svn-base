package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="机构类目",description="机构类目属性说明")
public class MechanismCategoryEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 类目名
     */
    @ApiModelProperty(value="类目名")
    private String name;

    /**
     * 1 大类目 2 子类目
     */
    @ApiModelProperty(value="1 大类目 2 子类目",example="1")
    private Integer type;

    /**
     * 子类目父id
     */
    @ApiModelProperty(value="子类目父id",example="1")
    private Long parent_id;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序",example="1")
    private Integer weights;

    /**
     * 状态 1 不可用 2 可用
     */
    @ApiModelProperty(value="状态 1 不可用 2 可用",example="1")
    private Integer status;

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

    @ApiModelProperty(value = "map杂项")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 类目名
     * @return name 类目名
     */
    public String getName() {
        return name;
    }

    /**
     * 类目名
     * @param name 类目名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 1 大类目 2 子类目
     * @return type 1 大类目 2 子类目
     */
    public Integer getType() {
        return type;
    }

    /**
     * 1 大类目 2 子类目
     * @param type 1 大类目 2 子类目
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 子类目父id
     * @return parent_id 子类目父id
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * 子类目父id
     * @param parent_id 子类目父id
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * 排序
     * @return weights 排序
     */
    public Integer getWeights() {
        return weights;
    }

    /**
     * 排序
     * @param weights 排序
     */
    public void setWeights(Integer weights) {
        this.weights = weights;
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
}