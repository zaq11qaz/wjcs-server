package com.huihe.eg.authorization.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户所拥有的按钮",description="用户所拥有的按钮属性说明")
public class UserButtonEntity extends PageInfo {
    /**
     * id 唯一
     */
    @ApiModelProperty(value="id 唯一",example="1")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String button_name;

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
     * 按钮的ID
     */
    @ApiModelProperty(value="按钮的ID",example="1")
    private Long button_id;

    /**
     * 用户的ID
     */
    @ApiModelProperty(value="用户的ID",example="1")
    private Long user_id;

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
     * 名称
     * @return button_name 名称
     */
    public String getButton_name() {
        return button_name;
    }

    /**
     * 名称
     * @param button_name 名称
     */
    public void setButton_name(String button_name) {
        this.button_name = button_name == null ? null : button_name.trim();
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
     * 按钮的ID
     * @return button_id 按钮的ID
     */
    public Long getButton_id() {
        return button_id;
    }

    /**
     * 按钮的ID
     * @param button_id 按钮的ID
     */
    public void setButton_id(Long button_id) {
        this.button_id = button_id;
    }

    /**
     * 用户的ID
     * @return user_id 用户的ID
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户的ID
     * @param user_id 用户的ID
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}