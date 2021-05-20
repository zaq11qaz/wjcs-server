package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/29 11:22
 * @ Description：活动信息对象
 * @ since: JDk1.8
 */
@ApiModel(value="活动信息对象",description="活动信息对象")
public class ActivityInfoEntity implements Serializable {
    private static final long serialVersionUID = 5949843586255126906L;
    /**
     * 标题
     */
    @ApiModelProperty(value="标题",example="1")
    private String title;
    /**
     * 分享描述
     */
    @ApiModelProperty(value="分享描述",example="1")
    private String share_description;
    /**
     * 描述
     */
    @ApiModelProperty(value="描述",example="1")
    private String descr;
    /**
     * 主键id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Long id;
    /**
     * 点击次数
     */
    @ApiModelProperty(value="点击次数",example="1")
    private Long click_count;
    /**
     * 购买次数
     */
    @ApiModelProperty(value="购买次数",example="1")
    private Long pay_count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShare_description() {
        return share_description;
    }

    public void setShare_description(String share_description) {
        this.share_description = share_description;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClick_count() {
        return click_count;
    }

    public void setClick_count(Long click_count) {
        this.click_count = click_count;
    }

    public Long getPay_count() {
        return pay_count;
    }

    public void setPay_count(Long pay_count) {
        this.pay_count = pay_count;
    }
}
