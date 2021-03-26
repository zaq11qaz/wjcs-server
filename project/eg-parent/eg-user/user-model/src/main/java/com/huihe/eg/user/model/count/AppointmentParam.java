package com.huihe.eg.user.model.count;

import java.math.BigDecimal;

/**
 * 类描述：课时统计
 *
 * @author zhangjiacheng
 * @datetime 2019/3/7 15:12
 */
public class AppointmentParam {
    private String appointment_time;
    private Integer master_count;
    private Integer appointment_count;
    private BigDecimal earnings_sum;


    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public Integer getMaster_count() {
        return master_count;
    }

    public void setMaster_count(Integer master_count) {
        this.master_count = master_count;
    }

    public Integer getAppointment_count() {
        return appointment_count;
    }

    public void setAppointment_count(Integer appointment_count) {
        this.appointment_count = appointment_count;
    }

    public BigDecimal getEarnings_sum() {
        return earnings_sum;
    }

    public void setEarnings_sum(BigDecimal earnings_sum) {
        this.earnings_sum = earnings_sum;
    }
}
