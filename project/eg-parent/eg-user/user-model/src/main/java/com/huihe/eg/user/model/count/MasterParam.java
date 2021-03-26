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
public class MasterParam {
    /**
     *累计数量
     */
    private Integer total_num;

    /**
     *审核通过数量
     */
    private Integer adopt_num;
    /**
     *审核拒绝数量
     */
    private Integer refuse_num;
    /**
     *待审核数量
     */
    private Integer stay_num;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date statistics_time;

    public Date getStatistics_time() {
        return statistics_time;
    }

    public void setStatistics_time(Date statistics_time) {
        this.statistics_time = statistics_time;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public Integer getAdopt_num() {
        return adopt_num;
    }

    public void setAdopt_num(Integer adopt_num) {
        this.adopt_num = adopt_num;
    }

    public Integer getRefuse_num() {
        return refuse_num;
    }

    public void setRefuse_num(Integer refuse_num) {
        this.refuse_num = refuse_num;
    }

    public Integer getStay_num() {
        return stay_num;
    }

    public void setStay_num(Integer stay_num) {
        this.stay_num = stay_num;
    }
}
