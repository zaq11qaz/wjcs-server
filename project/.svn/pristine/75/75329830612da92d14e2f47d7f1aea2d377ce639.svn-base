package com.huihe.eg.authorization.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="后台管理操作记录",description="后台管理操作记录属性说明")
public class LogEntity extends PageInfo {
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
     * 请求方式
     */
    @ApiModelProperty(value="请求方式")
    private String method;

    /**
     * 请求路径
     */
    @ApiModelProperty(value="请求路径")
    private String requestURI;

    /**
     * 请求参数
     */
    @ApiModelProperty(value="请求参数")
    private String param;

    /**
     * 请求用户id
     */
    @ApiModelProperty(value="请求用户id",example="1")
    private Long user_id;

    /**
     * 原来的参数
     */
    @ApiModelProperty(value="原来的参数")
    private String old_param;

    public Long getId() {
        return id;
    }

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
     * 请求方式
     * @return method 请求方式
     */
    public String getMethod() {
        return method;
    }

    /**
     * 请求方式
     * @param method 请求方式
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 请求路径
     * @return requestURI 请求路径
     */
    public String getRequestURI() {
        return requestURI;
    }

    /**
     * 请求路径
     * @param requestURI 请求路径
     */
    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI == null ? null : requestURI.trim();
    }

    /**
     * 请求参数
     * @return param 请求参数
     */
    public String getParam() {
        return param;
    }

    /**
     * 请求参数
     * @param param 请求参数
     */
    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    /**
     * 请求用户id
     * @return user_id 请求用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 请求用户id
     * @param user_id 请求用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 原来的参数
     * @return old_param 原来的参数
     */
    public String getOld_param() {
        return old_param;
    }

    /**
     * 原来的参数
     * @param old_param 原来的参数
     */
    public void setOld_param(String old_param) {
        this.old_param = old_param == null ? null : old_param.trim();
    }
}