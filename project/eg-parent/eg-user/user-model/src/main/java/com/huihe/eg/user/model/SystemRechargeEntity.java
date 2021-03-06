package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="后台充值猫币或学习卡记录",description="后台充值猫币或学习卡记录属性说明")
public class SystemRechargeEntity extends PageInfo {
    private static final long serialVersionUID = -4319005295478897263L;
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
     * 类别 minute分钟/curriculum课程 cat_coin猫币
     */
    @ApiModelProperty(value="类别 minute分钟/curriculum课程 cat_coin猫币")
    private String type;

    /**
     * 猫币数量，课时数/分钟数
     */
    @ApiModelProperty(value="猫币数量，课时数/分钟数",example="1")
    private Integer recharge_count;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 账号
     */
    @ApiModelProperty(value="账号")
    private String account;
    /**
     * map
     */
    @ApiModelProperty(value="map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 类别 minute分钟/curriculum课程 cat_coin猫币
     * @return type 类别 minute分钟/curriculum课程 cat_coin猫币
     */
    public String getType() {
        return type;
    }

    /**
     * 类别 minute分钟/curriculum课程 cat_coin猫币
     * @param type 类别 minute分钟/curriculum课程 cat_coin猫币
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 猫币数量，课时数/分钟数
     * @return recharge_count 猫币数量，课时数/分钟数
     */
    public Integer getRecharge_count() {
        return recharge_count;
    }

    /**
     * 猫币数量，课时数/分钟数
     * @param recharge_count 猫币数量，课时数/分钟数
     */
    public void setRecharge_count(Integer recharge_count) {
        this.recharge_count = recharge_count;
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
     * 账号
     * @return account 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 账号
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}