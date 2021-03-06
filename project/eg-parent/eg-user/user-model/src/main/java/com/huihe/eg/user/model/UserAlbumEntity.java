package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户相册",description="用户相册属性说明")
public class UserAlbumEntity extends PageInfo {
    private static final long serialVersionUID = 494281857235862408L;
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
     * 图片路径
     */
    @ApiModelProperty(value="图片路径")
    private String photo;

    /**
     * 相册状态 1 公开 2 未公开
     */
    @ApiModelProperty(value="相册状态 1 公开 2 未公开",example="1")
    private Integer status;

    /**
     * 相册说明
     */
    @ApiModelProperty(value="相册说明")
    private String remark;

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
     * 图片路径
     * @return photo 图片路径
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 图片路径
     * @param photo 图片路径
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * 相册状态 1 公开 2 未公开
     * @return status 相册状态 1 公开 2 未公开
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 相册状态 1 公开 2 未公开
     * @param status 相册状态 1 公开 2 未公开
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 相册说明
     * @return remark 相册说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 相册说明
     * @param remark 相册说明
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}