package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="通讯录",description="通讯录属性说明")
public class UserContactsEntity extends PageInfo {
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
     * 通讯录备注名
     */
    @ApiModelProperty(value="通讯录备注名")
    private String user_name;

    /**
     * 号码
     */
    @ApiModelProperty(value="号码")
    private String mobile;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 是否发送过短信
     */
    @ApiModelProperty(value="是否发送过短信",example="false")
    private Boolean is_send;

    @ApiModelProperty(value = "通讯录集合")
    private String contacts;

    public boolean isIs_resgiter() {
        return is_resgiter;
    }

    public void setIs_resgiter(boolean is_resgiter) {
        this.is_resgiter = is_resgiter;
    }

    @ApiModelProperty(value = "是否注册")
    private boolean is_resgiter;


    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
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
     * 通讯录备注名
     * @return user_name 通讯录备注名
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * 通讯录备注名
     * @param user_name 通讯录备注名
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    /**
     * 号码
     * @return mobile 号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 号码
     * @param mobile 号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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
     * 是否发送过短信
     * @return is_send 是否发送过短信
     */
    public Boolean getIs_send() {
        return is_send;
    }

    /**
     * 是否发送过短信
     * @param is_send 是否发送过短信
     */
    public void setIs_send(Boolean is_send) {
        this.is_send = is_send;
    }
}