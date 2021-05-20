package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="机构校区表",description="机构校区表属性说明")
public class MechanismSchoolDistrictEntity extends PageInfo {
    /**
     * id
     */
    @ApiModelProperty(value="id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 状态 1不可用 2 可用
     */
    @ApiModelProperty(value="状态 1不可用 2 可用",example="1")
    private Integer status;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 校区名
     */
    @ApiModelProperty(value="校区名")
    private String school_district;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 坐标纬度值
     */
    @ApiModelProperty(value="坐标纬度值",example="1")
    private BigDecimal latitude;

    /**
     * 坐标经度值
     */
    @ApiModelProperty(value="坐标经度值",example="1")
    private BigDecimal longitude;

    /**
     * id
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     * @param id id
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
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 状态 1不可用 2 可用
     * @return status 状态 1不可用 2 可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1不可用 2 可用
     * @param status 状态 1不可用 2 可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 机构id
     * @return mechanism_id 机构id
     */
    public Long getMechanism_id() {
        return mechanism_id;
    }

    /**
     * 机构id
     * @param mechanism_id 机构id
     */
    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    /**
     * 校区名
     * @return school_district 校区名
     */
    public String getSchool_district() {
        return school_district;
    }

    /**
     * 校区名
     * @param school_district 校区名
     */
    public void setSchool_district(String school_district) {
        this.school_district = school_district == null ? null : school_district.trim();
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
     * 坐标纬度值
     * @return latitude 坐标纬度值
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 坐标纬度值
     * @param latitude 坐标纬度值
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * 坐标经度值
     * @return longitude 坐标经度值
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 坐标经度值
     * @param longitude 坐标经度值
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}