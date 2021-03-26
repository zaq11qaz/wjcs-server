package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 类描述：用户近7天签到情况
 *
 * @author zhangjiacheng
 * @datetime 2019/3/14 10:35
 */
@ApiModel(value="用户近7天签到情况",description="用户近7天签到情况")
public class SignInDayEntiy {
    @ApiModelProperty(value="第一天",example="1")
    private int one_day =0;
    @ApiModelProperty(value="第二天",example="1")
    private int two_day =0;
    @ApiModelProperty(value="第三天",example="1")
    private int three_day =0;
    @ApiModelProperty(value="第四天",example="1")
    private int four_day =0;
    @ApiModelProperty(value="第五天",example="1")
    private int five_day =0;
    @ApiModelProperty(value="第六天",example="1")
    private int six_day =0;
    @ApiModelProperty(value="第七天",example="1")
    private int seven_day =0;


    public int getOne_day() {
        return one_day;
    }

    public void setOne_day(int one_day) {
        this.one_day = one_day;
    }

    public int getTwo_day() {
        return two_day;
    }

    public void setTwo_day(int two_day) {
        this.two_day = two_day;
    }

    public int getThree_day() {
        return three_day;
    }

    public void setThree_day(int three_day) {
        this.three_day = three_day;
    }

    public int getFour_day() {
        return four_day;
    }

    public void setFour_day(int four_day) {
        this.four_day = four_day;
    }

    public int getFive_day() {
        return five_day;
    }

    public void setFive_day(int five_day) {
        this.five_day = five_day;
    }

    public int getSix_day() {
        return six_day;
    }

    public void setSix_day(int six_day) {
        this.six_day = six_day;
    }

    public int getSeven_day() {
        return seven_day;
    }

    public void setSeven_day(int seven_day) {
        this.seven_day = seven_day;
    }
}
