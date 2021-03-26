package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户登录统计",description="用户登录统计属性说明")
public class LoginHistoryEntity extends PageInfo {
    private static final long serialVersionUID = -4331063561336506733L;
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
     * 登陆时间
     */
    @ApiModelProperty(value="登陆时间")
    private Date login_time;

    /**
     * 登出时间
     */
    @ApiModelProperty(value="登出时间")
    private Date logout_time;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 状态1登录2登出
     */
    @ApiModelProperty(value="状态1登录2登出",example="1")
    private Integer status;



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
     * 登陆时间
     * @return login_time 登陆时间
     */
    public Date getLogin_time() {
        return login_time;
    }

    /**
     * 登陆时间
     * @param login_time 登陆时间
     */
    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    /**
     * 登出时间
     * @return logout_time 登出时间
     */
    public Date getLogout_time() {
        return logout_time;
    }

    /**
     * 登出时间
     * @param logout_time 登出时间
     */
    public void setLogout_time(Date logout_time) {
        this.logout_time = logout_time;
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
     * 状态1登录2登出
     * @return status 状态1登录2登出
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1登录2登出
     * @param status 状态1登录2登出
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}