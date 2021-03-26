package com.huihe.eg.news.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value="浏览记录",description="属性说明")
public class BrowesHistoryEntity extends PageInfo {
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
     * 分类id
     */
    @ApiModelProperty(value="分类id",example="1")
    private Long classfiy;

    /**
     * 浏览次数
     */
    @ApiModelProperty(value="浏览次数",example="1")
    private Integer browse_count;

    /**
     * 浏览时长
     */
    @ApiModelProperty(value="浏览时长",example="1")
    private Integer duration_time;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 区域
     */
    @ApiModelProperty(value="区域")
    private String address;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    @ApiModelProperty(value = "单次时长",example="1")
    private int times;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
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
     * 分类id
     * @return classfiy 分类id
     */
    public Long getClassfiy() {
        return classfiy;
    }

    /**
     * 分类id
     * @param classfiy 分类id
     */
    public void setClassfiy(Long classfiy) {
        this.classfiy = classfiy;
    }

    /**
     * 浏览次数
     * @return browse_count 浏览次数
     */
    public Integer getBrowse_count() {
        return browse_count;
    }

    /**
     * 浏览次数
     * @param browse_count 浏览次数
     */
    public void setBrowse_count(Integer browse_count) {
        this.browse_count = browse_count;
    }

    /**
     * 浏览时长
     * @return duration_time 浏览时长
     */
    public Integer getDuration_time() {
        return duration_time;
    }

    /**
     * 浏览时长
     * @param duration_time 浏览时长
     */
    public void setDuration_time(Integer duration_time) {
        this.duration_time = duration_time;
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
     * 区域
     * @return address 区域
     */
    public String getAddress() {
        return address;
    }

    /**
     * 区域
     * @param address 区域
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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