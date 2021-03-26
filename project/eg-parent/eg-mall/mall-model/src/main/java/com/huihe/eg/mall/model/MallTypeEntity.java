package com.huihe.eg.mall.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="商品分类",description="商品分类属性说明")
public class MallTypeEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 节点等级
     */
    @ApiModelProperty(value="节点等级",example="1")
    private Integer node_level;

    /**
     * 父节点
     */
    @ApiModelProperty(value="父节点",example="1")
    private Long parent_note;

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
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
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
     * 节点等级
     * @return node_level 节点等级
     */
    public Integer getNode_level() {
        return node_level;
    }

    /**
     * 节点等级
     * @param node_level 节点等级
     */
    public void setNode_level(Integer node_level) {
        this.node_level = node_level;
    }

    /**
     * 父节点
     * @return parent_note 父节点
     */
    public Long getParent_note() {
        return parent_note;
    }

    /**
     * 父节点
     * @param parent_note 父节点
     */
    public void setParent_note(Long parent_note) {
        this.parent_note = parent_note;
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
}