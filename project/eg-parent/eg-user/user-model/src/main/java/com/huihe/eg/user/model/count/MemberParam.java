package com.huihe.eg.user.model.count;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/3/7 15:12
 */
public class MemberParam {
    /**
     *累计注册会员
     */
    private Integer total_num;
    /**
     *会员数量
     */
    private Integer member_total_num;
    /**
     *今日注册会员
     */
    private Integer teday_num;
    /**
     *今日到期会员
     */
    private Integer today_expire_num;
    /**
     *当前在线会员
     */
    private Integer online_num;
    /**
     *会员等级
     */
    private Integer member_level;
    /**
     * 统计日期
     */
    /**
     *会员数量
     */
    private Integer member_num;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date statistics_time;

    public Date getStatistics_time() {
        return statistics_time;
    }

    public void setStatistics_time(Date statistics_time) {
        this.statistics_time = statistics_time;
    }

    public Integer getMember_level() {
        return member_level;
    }

    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
    }

    public Integer getMember_total_num() {
        return member_total_num;
    }

    public void setMember_total_num(Integer member_total_num) {
        this.member_total_num = member_total_num;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public Integer getTeday_num() {
        return teday_num;
    }

    public void setTeday_num(Integer teday_num) {
        this.teday_num = teday_num;
    }

    public Integer getToday_expire_num() {
        return today_expire_num;
    }

    public void setToday_expire_num(Integer today_expire_num) {
        this.today_expire_num = today_expire_num;
    }

    public Integer getOnline_num() {
        return online_num;
    }

    public void setOnline_num(Integer online_num) {
        this.online_num = online_num;
    }

    public Integer getMember_num() {
        return member_num;
    }

    public void setMember_num(Integer member_num) {
        this.member_num = member_num;
    }
}
