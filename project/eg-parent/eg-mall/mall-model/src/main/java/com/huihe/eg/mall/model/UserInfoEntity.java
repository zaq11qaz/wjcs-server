package com.huihe.eg.mall.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户基本资料",description="用户基本资料属性说明")
public class UserInfoEntity extends PageInfo {
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 用户创建时间
     */
    @ApiModelProperty(value="用户创建时间")
    private Date create_time;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value="用户昵称")
    private String nick_name;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 积分
     */
    @ApiModelProperty(value="积分",example="1")
    private Integer integral;

    /**
     * 拍币
     */
    @ApiModelProperty(value="拍币",example="1")
    private Integer auction_coin;

    /**
     * 赠币
     */
    @ApiModelProperty(value="赠币",example="1")
    private Integer gift_coin;

    /**
     * 购物币
     */
    @ApiModelProperty(value="购物币",example="1")
    private Integer shopping_coin;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;

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
     * 用户创建时间
     * @return create_time 用户创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 用户创建时间
     * @param create_time 用户创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 用户昵称
     * @return nick_name 用户昵称
     */
    public String getNick_name() {
        return nick_name;
    }

    /**
     * 用户昵称
     * @param nick_name 用户昵称
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name == null ? null : nick_name.trim();
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
     * 头像
     * @return avatar 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 积分
     * @return integral 积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 积分
     * @param integral 积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 拍币
     * @return auction_coin 拍币
     */
    public Integer getAuction_coin() {
        return auction_coin;
    }

    /**
     * 拍币
     * @param auction_coin 拍币
     */
    public void setAuction_coin(Integer auction_coin) {
        this.auction_coin = auction_coin;
    }

    /**
     * 赠币
     * @return gift_coin 赠币
     */
    public Integer getGift_coin() {
        return gift_coin;
    }

    /**
     * 赠币
     * @param gift_coin 赠币
     */
    public void setGift_coin(Integer gift_coin) {
        this.gift_coin = gift_coin;
    }

    /**
     * 购物币
     * @return shopping_coin 购物币
     */
    public Integer getShopping_coin() {
        return shopping_coin;
    }

    /**
     * 购物币
     * @param shopping_coin 购物币
     */
    public void setShopping_coin(Integer shopping_coin) {
        this.shopping_coin = shopping_coin;
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
}