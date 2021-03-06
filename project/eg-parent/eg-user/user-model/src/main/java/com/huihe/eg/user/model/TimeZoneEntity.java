package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="时区设置列表",description="时区设置列表属性说明")
public class TimeZoneEntity extends PageInfo {
    private static final long serialVersionUID = 5566675138054581885L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
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
     * 标准时间代码
     */
    @ApiModelProperty(value="标准时间代码")
    private String time_code;

    /**
     * 与GMT的偏移量
     */
    @ApiModelProperty(value="与GMT的偏移量",example="1")
    private BigDecimal offset;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describe_info;

    /**
     * 时区
     */
    @ApiModelProperty(value="时区")
    private String timezone;

    /**
     * 平台 ios android
     */
    @ApiModelProperty(value="平台 ios android")
    private String platform;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
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
     * 标准时间代码
     * @return time_code 标准时间代码
     */
    public String getTime_code() {
        return time_code;
    }

    /**
     * 标准时间代码
     * @param time_code 标准时间代码
     */
    public void setTime_code(String time_code) {
        this.time_code = time_code == null ? null : time_code.trim();
    }

    /**
     * 与GMT的偏移量
     * @return offset 与GMT的偏移量
     */
    public BigDecimal getOffset() {
        return offset;
    }

    /**
     * 与GMT的偏移量
     * @param offset 与GMT的偏移量
     */
    public void setOffset(BigDecimal offset) {
        this.offset = offset;
    }

    /**
     * 描述
     * @return describe_info 描述
     */
    public String getDescribe_info() {
        return describe_info;
    }

    /**
     * 描述
     * @param describe_info 描述
     */
    public void setDescribe_info(String describe_info) {
        this.describe_info = describe_info == null ? null : describe_info.trim();
    }

    /**
     * 时区
     * @return timezone 时区
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * 时区
     * @param timezone 时区
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone == null ? null : timezone.trim();
    }

    /**
     * 平台 ios android
     * @return platform 平台 ios android
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 平台 ios android
     * @param platform 平台 ios android
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }
}