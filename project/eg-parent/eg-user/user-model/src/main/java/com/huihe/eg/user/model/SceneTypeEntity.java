package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="课程类别",description="课程类别属性说明")
public class SceneTypeEntity extends PageInfo {
    private static final long serialVersionUID = 7309886090358530840L;
    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long id;

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
     * 类型  topic_exchange 话题交流  scene_broadcast 场景直播
     */
    @ApiModelProperty(value="类型  topic_exchange 话题交流  scene_broadcast 场景直播")
    private String type;

    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 状态 1 下架 2可用
     */
    @ApiModelProperty(value="状态 1 下架 2可用",example="1")
    private Integer status;

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
     * 类型  topic_exchange 话题交流  scene_broadcast 场景直播
     * @return type 类型  topic_exchange 话题交流  scene_broadcast 场景直播
     */
    public String getType() {
        return type;
    }

    /**
     * 类型  topic_exchange 话题交流  scene_broadcast 场景直播
     * @param type 类型  topic_exchange 话题交流  scene_broadcast 场景直播
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 内容
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 状态 1 下架 2可用
     * @return status 状态 1 下架 2可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 下架 2可用
     * @param status 状态 1 下架 2可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}