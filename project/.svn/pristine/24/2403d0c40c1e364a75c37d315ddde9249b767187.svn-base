package com.huihe.eg.authorization.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value="后台管理的用户/商户",description="后台管理的用户/商户属性说明")
public class ManagerUserEntity extends PageInfo {
    /**
     * id 唯一
     */
    @ApiModelProperty(value="id 唯一",example="1")
    private Long id;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 更新的时间
     */
    @ApiModelProperty(value="更新的时间")
    private Date update_time;

    /**
     * 添加的用户
     */
    @ApiModelProperty(value="添加的用户")
    private String add_user;

    /**
     * 修改的用户
     */
    @ApiModelProperty(value="修改的用户")
    private String update_user;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String pwd;

    /**
     * 最后登录的时间
     */
    @ApiModelProperty(value="最后登录的时间")
    private Date last_login_time;

    /**
     * 是否可用
     */
    @ApiModelProperty(value="是否可用",example="false")
    private Boolean available;

    /**
     * 帐号
     */
    @ApiModelProperty(value="帐号")
    private String login_name;

    @ApiModelProperty(value="管理员token")
    private String managerToken;

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    /**
     * type admin系统管理员 business商务中心  service客服 merchants招商  audit审核招商 agent代理商
     */
    @ApiModelProperty(value="type admin系统管理员 business商务中心  service客服 merchants招商  audit审核招商 agent代理商")
    private String short_name;

    @ApiModelProperty(value="用户身份对象")
    private ManagerIdentityEntity managerIdentityEntity;


    @ApiModelProperty(value="用户的菜单")
    private List<MenuEntity> menus;

    @ApiModelProperty(value="用户的菜单")
    private List<ApiUrlEntity> apiUrlEntities;

    /**
     * 是否商家
     */
    @ApiModelProperty(value="是否商家",example="false")
    private Boolean business;

    /**
     * 上一级的ID
     */
    @ApiModelProperty(value="上一级的ID",example="1")
    private Long parent_id;

    /**
     * 余额
     */
    @ApiModelProperty(value="余额",example="1")
    private BigDecimal balance;

    /**
     * 是否超级管理员
     */
    @ApiModelProperty(value="是否超级管理员",example="false")
    private Boolean admin;

    /**
     * 用户所拥有的身份
     */
    @ApiModelProperty(value="用户所拥有的身份")
    private String type;

    @ApiModelProperty(value="助教身份管理人数")
    private Long managers_student;

    public Long getManagers_student() {
        return managers_student;
    }

    public void setManagers_student(Long managers_student) {
        this.managers_student = managers_student;
    }

    public String getManagerToken() {
        return managerToken;
    }

    public void setManagerToken(String managerToken) {
        this.managerToken = managerToken;
    }

    public List<MenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuEntity> menus) {
        this.menus = menus;
    }

    public List<ApiUrlEntity> getApiUrlEntities() {
        return apiUrlEntities;
    }

    public void setApiUrlEntities(List<ApiUrlEntity> apiUrlEntities) {
        this.apiUrlEntities = apiUrlEntities;
    }

    public ManagerIdentityEntity getManagerIdentityEntity() {
        return managerIdentityEntity;
    }

    public void setManagerIdentityEntity(ManagerIdentityEntity managerIdentityEntity) {
        this.managerIdentityEntity = managerIdentityEntity;
    }

    /**
     * id 唯一
     * @return id id 唯一
     */
    public Long getId() {
        return id;
    }

    /**
     * id 唯一
     * @param id id 唯一
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 昵称
     * @return name 昵称
     */
    public String getName() {
        return name;
    }

    /**
     * 昵称
     * @param name 昵称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 更新的时间
     * @return update_time 更新的时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新的时间
     * @param update_time 更新的时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 添加的用户
     * @return add_user 添加的用户
     */
    public String getAdd_user() {
        return add_user;
    }

    /**
     * 添加的用户
     * @param add_user 添加的用户
     */
    public void setAdd_user(String add_user) {
        this.add_user = add_user == null ? null : add_user.trim();
    }

    /**
     * 修改的用户
     * @return update_user 修改的用户
     */
    public String getUpdate_user() {
        return update_user;
    }

    /**
     * 修改的用户
     * @param update_user 修改的用户
     */
    public void setUpdate_user(String update_user) {
        this.update_user = update_user == null ? null : update_user.trim();
    }

    /**
     * 手机号
     * @return mobile 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 密码
     * @return pwd 密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 密码
     * @param pwd 密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * 最后登录的时间
     * @return last_login_time 最后登录的时间
     */
    public Date getLast_login_time() {
        return last_login_time;
    }

    /**
     * 最后登录的时间
     * @param last_login_time 最后登录的时间
     */
    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    /**
     * 是否可用
     * @return available 是否可用
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * 是否可用
     * @param available 是否可用
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * type admin系统管理员 business商务中心  service客服 merchants招商  audit审核招商 agent代理商
     * @return short_name type admin系统管理员 business商务中心  service客服 merchants招商  audit审核招商 agent代理商
     */
    public String getShort_name() {
        return short_name;
    }

    /**
     * type admin系统管理员 business商务中心  service客服 merchants招商  audit审核招商 agent代理商
     * @param short_name type admin系统管理员 business商务中心  service客服 merchants招商  audit审核招商 agent代理商
     */
    public void setShort_name(String short_name) {
        this.short_name = short_name == null ? null : short_name.trim();
    }

    /**
     * 是否商家
     * @return business 是否商家
     */
    public Boolean getBusiness() {
        return business;
    }

    /**
     * 是否商家
     * @param business 是否商家
     */
    public void setBusiness(Boolean business) {
        this.business = business;
    }

    /**
     * 上一级的ID
     * @return parent_id 上一级的ID
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * 上一级的ID
     * @param parent_id 上一级的ID
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * 余额
     * @return balance 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 余额
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 是否超级管理员
     * @return admin 是否超级管理员
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * 是否超级管理员
     * @param admin 是否超级管理员
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * 
     * @return type 
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}