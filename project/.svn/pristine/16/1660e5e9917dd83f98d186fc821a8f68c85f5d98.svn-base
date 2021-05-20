package com.huihe.eg.authorization.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="大屏幕白名单",description="大屏幕白名单属性说明")
public class AuthorizationScreenWhiteEntity extends PageInfo {
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
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 1 不可用 2 可用
     */
    @ApiModelProperty(value="1 不可用 2 可用",example="1")
    private Integer status;

    /**
     * 请求ip
     */
    @ApiModelProperty(value="请求ip")
    private String ip;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String passWord;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

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
     * 1 不可用 2 可用
     * @return status 1 不可用 2 可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 不可用 2 可用
     * @param status 1 不可用 2 可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 请求ip
     * @return ip 请求ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 请求ip
     * @param ip 请求ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}