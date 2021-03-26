package com.huihe.eg.grab.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jsoup.nodes.Document;

import java.util.Date;

@ApiModel(value="抓取任务",description="抓取任务属性说明")
public class GrabTaskEntity extends PageInfo implements Cloneable {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 任务名称
     */
    @ApiModelProperty(value="任务名称")
    private String name;

    /**
     * 任务连接
     */
    @ApiModelProperty(value="任务连接")
    private String link;

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
     * 1初始化  2运行状态
     */
    @ApiModelProperty(value="1初始化  2运行状态",example="1")
    private Integer status;

    /**
     * 1表示抓取列表  2表示抓取列表和详情
     */
    @ApiModelProperty(value="1表示抓取列表  2表示抓取列表和详情",example="1")
    private Integer layer;

    /**
     * 当前层次
     */
    private volatile int currentLayer = 1;
    private String controller_name;
    private String method_name;
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayer(int currentLayer) {
        this.currentLayer = currentLayer;
    }

    public String getController_name() {
        return controller_name;
    }

    public void setController_name(String controller_name) {
        this.controller_name = controller_name;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
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
     * 任务名称
     * @return name 任务名称
     */
    public String getName() {
        return name;
    }

    /**
     * 任务名称
     * @param name 任务名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 任务连接
     * @return link 任务连接
     */
    public String getLink() {
        return link;
    }

    /**
     * 任务连接
     * @param link 任务连接
     */
    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
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
     * 1初始化  2运行状态
     * @return status 1初始化  2运行状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1初始化  2运行状态
     * @param status 1初始化  2运行状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 1表示抓取列表  2表示抓取列表和详情
     * @return layer 1表示抓取列表  2表示抓取列表和详情
     */
    public Integer getLayer() {
        return layer;
    }

    /**
     * 1表示抓取列表  2表示抓取列表和详情
     * @param layer 1表示抓取列表  2表示抓取列表和详情
     */
    public void setLayer(Integer layer) {
        this.layer = layer;
    }
}