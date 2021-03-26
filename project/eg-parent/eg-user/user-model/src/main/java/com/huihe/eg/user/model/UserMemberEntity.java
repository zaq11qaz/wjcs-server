package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

@ApiModel(value="会员信息",description="会员信息属性说明")
public class UserMemberEntity extends PageInfo {
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
     * 会员等级
     */
    @ApiModelProperty(value="会员等级 1畅聊会员 2铂金会员 3砖石会员",example="1")
    private Integer member_level;

    /**
     * 是否是会员
     */
    @ApiModelProperty(value="是否是会员",example="false")
    private Boolean is_member;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    @ApiModelProperty(value="map")
    private Map map;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * 会员开始时间
     */
    @ApiModelProperty(value="会员开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date start_time;

    /**
     * 会员结束时间
     */
    @ApiModelProperty(value="会员结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date end_time;
    @ApiModelProperty(value="统计时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date statistics_time;

    public Date getStatistics_time() {
        return statistics_time;
    }

    public void setStatistics_time(Date statistics_time) {
        this.statistics_time = statistics_time;
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
     * 会员等级
     * @return member_level 会员等级
     */
    public Integer getMember_level() {
        return member_level;
    }

    /**
     * 会员等级
     * @param member_level 会员等级
     */
    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
    }

    /**
     * 是否是会员
     * @return is_member 是否是会员
     */
    public Boolean getIs_member() {
        return is_member;
    }

    /**
     * 是否是会员
     * @param is_member 是否是会员
     */
    public void setIs_member(Boolean is_member) {
        this.is_member = is_member;
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
     * 会员开始时间
     * @return start_time 会员开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 会员开始时间
     * @param start_time 会员开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 会员结束时间
     * @return end_time 会员结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 会员结束时间
     * @param end_time 会员结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}