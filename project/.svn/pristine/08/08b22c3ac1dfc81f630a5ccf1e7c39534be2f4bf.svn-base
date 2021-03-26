package com.huihe.eg.news.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value="资讯",description="资讯属性说明")
public class NewsInformationInfoEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 图片
     */
    @ApiModelProperty(value="图片")
    private String picts;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    /**
     * 状态1.可查看2.不可
     */
    @ApiModelProperty(value="状态1.可查看2.不可",example="1")
    private Integer status;

    /**
     * 标签id
     */
    @ApiModelProperty(value="标签id",example="1")
    private Integer classfiy;

    /**
     * 主键ID
     * @return id 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     * @param id 主键ID
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
     * 图片
     * @return picts 图片
     */
    public String getPicts() {
        return picts;
    }

    /**
     * 图片
     * @param picts 图片
     */
    public void setPicts(String picts) {
        this.picts = picts == null ? null : picts.trim();
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
     * 状态1.可查看2.不可
     * @return status 状态1.可查看2.不可
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.可查看2.不可
     * @param status 状态1.可查看2.不可
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 标签id
     * @return classfiy 标签id
     */
    public Integer getClassfiy() {
        return classfiy;
    }

    /**
     * 标签id
     * @param classfiy 标签id
     */
    public void setClassfiy(Integer classfiy) {
        this.classfiy = classfiy;
    }
}