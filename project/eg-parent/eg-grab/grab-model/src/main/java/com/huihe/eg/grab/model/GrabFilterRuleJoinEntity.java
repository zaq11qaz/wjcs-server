package com.huihe.eg.grab.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Date;

@ApiModel(value="加入抓取的规则和任务",description="加入抓取的规则和任务属性说明")
public class GrabFilterRuleJoinEntity extends PageInfo {
    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    private String object_name;

    /**
     * key名称
     */
    @ApiModelProperty(value="key名称")
    private String key_name;

    /**
     * 创建时间时间
     */
    @ApiModelProperty(value="创建时间时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 过滤规则的id fk_grab_filter_rule_id
     */
    @ApiModelProperty(value="过滤规则的id fk_grab_filter_rule_id",example="1")
    private Long filter_rule_id;

    /**
     * 抓取任务的id fk_grab_task_id
     */
    @ApiModelProperty(value="抓取任务的id fk_grab_task_id",example="1")
    private Long task_id;

    /**
     * 层次 对应fk_grab_task_id的layer
     */
    @ApiModelProperty(value="层次 对应fk_grab_task_id的layer",example="1")
    private Integer layer;

    /**
     * 类型 1文本数据 2连接
     */
    @ApiModelProperty(value="类型 1文本数据 2连接",example="1")
    private Integer type;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
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
     * 对象名称
     * @return object_name 对象名称
     */
    public String getObject_name() {
        return object_name;
    }

    /**
     * 对象名称
     * @param object_name 对象名称
     */
    public void setObject_name(String object_name) {
        this.object_name = object_name == null ? null : object_name.trim();
    }

    /**
     * key名称
     * @return key_name key名称
     */
    public String getKey_name() {
        return key_name;
    }

    /**
     * key名称
     * @param key_name key名称
     */
    public void setKey_name(String key_name) {
        this.key_name = key_name == null ? null : key_name.trim();
    }

    /**
     * 创建时间时间
     * @return create_time 创建时间时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间时间
     * @param create_time 创建时间时间
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
     * 过滤规则的id fk_grab_filter_rule_id
     * @return filter_rule_id 过滤规则的id fk_grab_filter_rule_id
     */
    public Long getFilter_rule_id() {
        return filter_rule_id;
    }

    /**
     * 过滤规则的id fk_grab_filter_rule_id
     * @param filter_rule_id 过滤规则的id fk_grab_filter_rule_id
     */
    public void setFilter_rule_id(Long filter_rule_id) {
        this.filter_rule_id = filter_rule_id;
    }

    /**
     * 抓取任务的id fk_grab_task_id
     * @return task_id 抓取任务的id fk_grab_task_id
     */
    public Long getTask_id() {
        return task_id;
    }

    /**
     * 抓取任务的id fk_grab_task_id
     * @param task_id 抓取任务的id fk_grab_task_id
     */
    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    /**
     * 层次 对应fk_grab_task_id的layer
     * @return layer 层次 对应fk_grab_task_id的layer
     */
    public Integer getLayer() {
        return layer;
    }

    /**
     * 层次 对应fk_grab_task_id的layer
     * @param layer 层次 对应fk_grab_task_id的layer
     */
    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    /**
     * 类型 1文本数据 2连接
     * @return type 类型 1文本数据 2连接
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型 1文本数据 2连接
     * @param type 类型 1文本数据 2连接
     */
    public void setType(Integer type) {
        this.type = type;
    }

    private Document document;
    private Elements elements;
    private String value;
    private String key;
    private GrabFilterRuleEntity filterRuleEntity;

    public GrabFilterRuleEntity getFilterRuleEntity() {
        return filterRuleEntity;
    }

    public void setFilterRuleEntity(GrabFilterRuleEntity filterRuleEntity) {
        this.filterRuleEntity = filterRuleEntity;
    }

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