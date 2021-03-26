package com.huihe.eg.user.model.count;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：资产统计
 *
 * @author zhangjiacheng
 * @datetime 2019/3/7 15:12
 */
public class AssetsParam {
    /**
     *累计资产
     */
    private BigDecimal total_Acount;
    /**
     *次数
     */
    private Integer total_num;
    /**
     *学习卡类型
     */
    private String  study_type;
    /**
     *会员类型
     */
    private Integer  member_level;

    private String  rcharge_type;

    public String getRcharge_type() {
        return rcharge_type;
    }

    public void setRcharge_type(String rcharge_type) {
        this.rcharge_type = rcharge_type;
    }

    public Integer getMember_level() {
        return member_level;
    }

    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date statistics_time;

    public Date getStatistics_time() {
        return statistics_time;
    }

    public void setStatistics_time(Date statistics_time) {
        this.statistics_time = statistics_time;
    }

    public BigDecimal getTotal_Acount() {
        return total_Acount;
    }

    public void setTotal_Acount(BigDecimal total_Acount) {
        this.total_Acount = total_Acount;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getStudy_type() {
        return study_type;
    }

    public void setStudy_type(String study_type) {
        this.study_type = study_type;
    }
}
