package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="渠道列表",description="渠道列表属性说明")
public class ChannelListEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Integer id;

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
     * 渠道名称
     */
    @ApiModelProperty(value="渠道名称")
    private String name;

    /**
     * 标识
     */
    @ApiModelProperty(value="标识")
    private String platform;

    /**
     * 推广链接
     */
    @ApiModelProperty(value="推广链接")
    private String url;

    /**
     * 公司名称
     */
    @ApiModelProperty(value="公司名称")
    private String company_name;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String descr;


    /**
     * 备注
     */
    @ApiModelProperty(value="'状态 1 未生效 2 生效 3 弃用',")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
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
     * 渠道名称
     * @return name 渠道名称
     */
    public String getName() {
        return name;
    }

    /**
     * 渠道名称
     * @param name 渠道名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 标识
     * @return platform 标识
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 标识
     * @param platform 标识
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 推广链接
     * @return url 推广链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 推广链接
     * @param url 推广链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 公司名称
     * @return company_name 公司名称
     */
    public String getCompany_name() {
        return company_name;
    }

    /**
     * 公司名称
     * @param company_name 公司名称
     */
    public void setCompany_name(String company_name) {
        this.company_name = company_name == null ? null : company_name.trim();
    }

    /**
     * 备注
     * @return descr 备注
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 备注
     * @param descr 备注
     */
    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }
}