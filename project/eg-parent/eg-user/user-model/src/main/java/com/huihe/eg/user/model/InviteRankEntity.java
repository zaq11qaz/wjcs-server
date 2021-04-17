package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/8 15:53
 * @ Description：
 * @ since: JDk1.8
 */
@ApiModel(value = "排行榜对象",description = "排行榜对象" )
public class InviteRankEntity {
    /**
     * 邀请人数
     */
    @ApiModelProperty(value="邀请人数",example="1")
    private Long invite_count;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 获得钱数
     */
    @ApiModelProperty(value="获得钱数",example="1")
    private BigDecimal invite_earn;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称",example="1")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像",example="1")
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getInvite_count() {
        return invite_count;
    }

    public void setInvite_count(Long invite_count) {
        this.invite_count = invite_count;
    }

    public BigDecimal getInvite_earn() {
        return invite_earn;
    }

    public void setInvite_earn(BigDecimal invite_earn) {
        this.invite_earn = invite_earn;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
