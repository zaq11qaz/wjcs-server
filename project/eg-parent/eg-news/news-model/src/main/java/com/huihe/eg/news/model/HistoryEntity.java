package com.huihe.eg.news.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel(value="操作历史纪录",description="操作历史纪录属性说明")
public class HistoryEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID",example="1")
    private Long user_id;

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
     * 操作类型1点赞:like2分享:share
     */
    @ApiModelProperty(value="操作类型1点赞:like2分享:share3收藏:collect 4好奇关注curiosity")
    private String operation_type;

    /**
     * 操作对象的id
     */
    @ApiModelProperty(value="操作对象的id",example="1")
    private Long history_id;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 状态1.点赞2.取消点赞
     */
    @ApiModelProperty(value="状态1.点赞2.取消点赞",example="1")
    private Integer status;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型  curiosityviewpoint:回应点赞 curiosity:好奇(关注)  curiosityviewpointcollect:收藏回应 curiosityviewpointcomment:评论点赞")
    private String history_type;
    /**
     * 好奇集合
     */
    @ApiModelProperty(value="好奇集合")
    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }


    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

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
     * 用户ID
     * @return user_id 用户ID
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户ID
     * @param user_id 用户ID
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
     * 操作类型1点赞:like2分享:share
     * @return operation_type 操作类型1点赞:like2分享:share
     */
    public String getOperation_type() {
        return operation_type;
    }

    /**
     * 操作类型1点赞:like2分享:share
     * @param operation_type 操作类型1点赞:like2分享:share
     */
    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type == null ? null : operation_type.trim();
    }

    /**
     * 操作对象的id
     * @return history_id 操作对象的id
     */
    public Long getHistory_id() {
        return history_id;
    }

    /**
     * 操作对象的id
     * @param history_id 操作对象的id
     */
    public void setHistory_id(Long history_id) {
        this.history_id = history_id;
    }

    /**
     * 地址
     * @return address 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 状态1.点赞2.取消点赞
     * @return status 状态1.点赞2.取消点赞
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.点赞2.取消点赞
     * @param status 状态1.点赞2.取消点赞
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型
     * @return history_type 类型
     */
    public String getHistory_type() {
        return history_type;
    }

    /**
     * 类型
     * @param history_type 类型
     */
    public void setHistory_type(String history_type) {
        this.history_type = history_type == null ? null : history_type.trim();
    }
}