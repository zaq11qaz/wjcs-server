package com.huihe.eg.grab.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Date;

@ApiModel(value="抓取规则",description="抓取规则属性说明")
public class GrabFilterRuleEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

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
     * 筛选规则
     */
    @ApiModelProperty(value="筛选规则")
    private String querySelector;

    /**
     * 取值规则 
     */
    @ApiModelProperty(value="取值规则 ")
    private String value_rule;

    /**
     * 需要删除过滤的标签 例如.ep-source cDGray 多个规则使用逗号隔开
     */
    @ApiModelProperty(value="需要删除过滤的标签 例如.ep-source cDGray 多个规则使用逗号隔开")
    private String remove_querySelector;

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
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 筛选规则
     * @return querySelector 筛选规则
     */
    public String getQuerySelector() {
        return querySelector;
    }

    /**
     * 筛选规则
     * @param querySelector 筛选规则
     */
    public void setQuerySelector(String querySelector) {
        this.querySelector = querySelector == null ? null : querySelector.trim();
    }

    /**
     * 取值规则 
     * @return value_rule 取值规则 
     */
    public String getValue_rule() {
        return value_rule;
    }

    /**
     * 取值规则 
     * @param value_rule 取值规则 
     */
    public void setValue_rule(String value_rule) {
        this.value_rule = value_rule == null ? null : value_rule.trim();
    }

    /**
     * 需要删除过滤的标签 例如.ep-source cDGray 多个规则使用逗号隔开
     * @return remove_querySelector 需要删除过滤的标签 例如.ep-source cDGray 多个规则使用逗号隔开
     */
    public String getRemove_querySelector() {
        return remove_querySelector;
    }

    /**
     * 需要删除过滤的标签 例如.ep-source cDGray 多个规则使用逗号隔开
     * @param remove_querySelector 需要删除过滤的标签 例如.ep-source cDGray 多个规则使用逗号隔开
     */
    public void setRemove_querySelector(String remove_querySelector) {
        this.remove_querySelector = remove_querySelector == null ? null : remove_querySelector.trim();
    }

    private Document document;
    private Elements elements;
    private String value;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}