package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="招募师收益规则",description="招募师收益规则属性说明")
public class UserEarnRoleEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 是否启用 1待启用 2启用 3删除
     */
    @ApiModelProperty(value="是否启用 1待启用 2启用 3删除",example="1")
    private Integer status;

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
     * 名
     */
    @ApiModelProperty(value="名")
    private String name;

    /**
     * 每个机构赚取
     */
    @ApiModelProperty(value="每个机构赚取",example="1")
    private BigDecimal every_mechanism_earn;

    /**
     * 每个外教赚取
     */
    @ApiModelProperty(value="每个外教赚取",example="1")
    private BigDecimal every_master_earn;

    /**
     * 每个机构外教教赚取上限
     */
    @ApiModelProperty(value="每个机构外教教赚取上限",example="1")
    private BigDecimal every_master_earn_max;

    /**
     * 每个课程赚取
     */
    @ApiModelProperty(value="每个课程赚取",example="1")
    private BigDecimal every_commodity_earn;

    /**
     * 每个课程赚取上限
     */
    @ApiModelProperty(value="每个课程赚取上限",example="1")
    private BigDecimal every_commodity_earn_max;

    /**
     * 每多少人算一次
     */
    @ApiModelProperty(value="每多少人算一次",example="1")
    private Integer each_student;

    /**
     * 每算一次多少钱
     */
    @ApiModelProperty(value="每算一次多少钱",example="1")
    private BigDecimal each_earl;

    /**
     * 获得机构招募官出售课程利润 百分比
     */
    @ApiModelProperty(value="获得机构招募官出售课程利润 百分比")
    private Double mechanism_bonus;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date start_time;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    /**
     * 机构推荐官 mechanism_recommender 外教推荐官 master_recommender' mechanism_income 机构收入 user_income 用户收入
     */
    @ApiModelProperty(value="机构推荐官 mechanism_recommender 外教推荐官 master_recommender' mechanism_income 机构收入 user_income 用户收入")
    private String type;

    /**
     * 外教招募官首课奖励 百分比
     */
    @ApiModelProperty(value="外教招募官首课奖励 百分比")
    private Double first_course_earln;

    /**
     * 外教招募官 招募一个 美国多少钱
     */
    @ApiModelProperty(value="外教招募官 招募一个 美国多少钱",example="1")
    private BigDecimal each_master_usa;

    /**
     * 外教招募官 招募一个 新西兰 加拿大 多少钱
     */
    @ApiModelProperty(value="外教招募官 招募一个 新西兰 加拿大 多少钱",example="1")
    private BigDecimal each_master_newzealand_canada_australia;

    /**
     * 外教招募官 招募一个 英国多少钱
     */
    @ApiModelProperty(value="外教招募官 招募一个 英国多少钱",example="1")
    private BigDecimal each_master_britain;

    /**
     * 外教招募官 招募一个 南非 欧洲多少钱
     */
    @ApiModelProperty(value="外教招募官 招募一个 南非 欧洲多少钱",example="1")
    private BigDecimal each_master_southafrica_europe;

    /**
     * 外教招募官 招募一个 其他多少钱
     */
    @ApiModelProperty(value="外教招募官 招募一个 其他多少钱",example="1")
    private BigDecimal each_master_else;

    /**
     * 外教招募管获得出售课程 百分比
     */
    @ApiModelProperty(value="外教招募管获得出售课程 百分比",example="1")
    private BigDecimal master_bonus;

    /**
     * 结算时长
     */
    @ApiModelProperty(value="外教招募管获得出售课程 百分比",example="1")
    private Long duration;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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
     * 是否启用 1待启用 2启用 3删除
     * @return status 是否启用 1待启用 2启用 3删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 是否启用 1待启用 2启用 3删除
     * @param status 是否启用 1待启用 2启用 3删除
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 名
     * @return name 名
     */
    public String getName() {
        return name;
    }

    /**
     * 名
     * @param name 名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 每个机构赚取
     * @return every_mechanism_earn 每个机构赚取
     */
    public BigDecimal getEvery_mechanism_earn() {
        return every_mechanism_earn;
    }

    /**
     * 每个机构赚取
     * @param every_mechanism_earn 每个机构赚取
     */
    public void setEvery_mechanism_earn(BigDecimal every_mechanism_earn) {
        this.every_mechanism_earn = every_mechanism_earn;
    }

    /**
     * 每个外教赚取
     * @return every_master_earn 每个外教赚取
     */
    public BigDecimal getEvery_master_earn() {
        return every_master_earn;
    }

    /**
     * 每个外教赚取
     * @param every_master_earn 每个外教赚取
     */
    public void setEvery_master_earn(BigDecimal every_master_earn) {
        this.every_master_earn = every_master_earn;
    }

    /**
     * 每个机构外教教赚取上限
     * @return every_master_earn_max 每个机构外教教赚取上限
     */
    public BigDecimal getEvery_master_earn_max() {
        return every_master_earn_max;
    }

    /**
     * 每个机构外教教赚取上限
     * @param every_master_earn_max 每个机构外教教赚取上限
     */
    public void setEvery_master_earn_max(BigDecimal every_master_earn_max) {
        this.every_master_earn_max = every_master_earn_max;
    }

    /**
     * 每个课程赚取
     * @return every_commodity_earn 每个课程赚取
     */
    public BigDecimal getEvery_commodity_earn() {
        return every_commodity_earn;
    }

    /**
     * 每个课程赚取
     * @param every_commodity_earn 每个课程赚取
     */
    public void setEvery_commodity_earn(BigDecimal every_commodity_earn) {
        this.every_commodity_earn = every_commodity_earn;
    }

    /**
     * 每个课程赚取上限
     * @return every_commodity_earn_max 每个课程赚取上限
     */
    public BigDecimal getEvery_commodity_earn_max() {
        return every_commodity_earn_max;
    }

    /**
     * 每个课程赚取上限
     * @param every_commodity_earn_max 每个课程赚取上限
     */
    public void setEvery_commodity_earn_max(BigDecimal every_commodity_earn_max) {
        this.every_commodity_earn_max = every_commodity_earn_max;
    }

    /**
     * 每多少人算一次
     * @return each_student 每多少人算一次
     */
    public Integer getEach_student() {
        return each_student;
    }

    /**
     * 每多少人算一次
     * @param each_student 每多少人算一次
     */
    public void setEach_student(Integer each_student) {
        this.each_student = each_student;
    }

    /**
     * 每算一次多少钱
     * @return each_earl 每算一次多少钱
     */
    public BigDecimal getEach_earl() {
        return each_earl;
    }

    /**
     * 每算一次多少钱
     * @param each_earl 每算一次多少钱
     */
    public void setEach_earl(BigDecimal each_earl) {
        this.each_earl = each_earl;
    }

    /**
     * 获得机构招募官出售课程利润 百分比
     * @return mechanism_bonus 获得机构招募官出售课程利润 百分比
     */
    public Double getMechanism_bonus() {
        return mechanism_bonus;
    }

    /**
     * 获得机构招募官出售课程利润 百分比
     * @param mechanism_bonus 获得机构招募官出售课程利润 百分比
     */
    public void setMechanism_bonus(Double mechanism_bonus) {
        this.mechanism_bonus = mechanism_bonus;
    }

    /**
     * 开始时间
     * @return start_time 开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 开始时间
     * @param start_time 开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 结束时间
     * @return end_time 结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 结束时间
     * @param end_time 结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 机构推荐官 mechanism_recommender 外教推荐官 master_recommender' mechanism_income 机构收入 user_income 用户收入
     * @return type 机构推荐官 mechanism_recommender 外教推荐官 master_recommender' mechanism_income 机构收入 user_income 用户收入
     */
    public String getType() {
        return type;
    }

    /**
     * 机构推荐官 mechanism_recommender 外教推荐官 master_recommender' mechanism_income 机构收入 user_income 用户收入
     * @param type 机构推荐官 mechanism_recommender 外教推荐官 master_recommender' mechanism_income 机构收入 user_income 用户收入
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 外教招募官首课奖励 百分比
     * @return first_course_earln 外教招募官首课奖励 百分比
     */
    public Double getFirst_course_earln() {
        return first_course_earln;
    }

    /**
     * 外教招募官首课奖励 百分比
     * @param first_course_earln 外教招募官首课奖励 百分比
     */
    public void setFirst_course_earln(Double first_course_earln) {
        this.first_course_earln = first_course_earln;
    }

    /**
     * 外教招募官 招募一个 美国多少钱
     * @return each_master_usa 外教招募官 招募一个 美国多少钱
     */
    public BigDecimal getEach_master_usa() {
        return each_master_usa;
    }

    /**
     * 外教招募官 招募一个 美国多少钱
     * @param each_master_usa 外教招募官 招募一个 美国多少钱
     */
    public void setEach_master_usa(BigDecimal each_master_usa) {
        this.each_master_usa = each_master_usa;
    }

    /**
     * 外教招募官 招募一个 新西兰 加拿大 多少钱
     * @return each_master_newzealand_canada_australia 外教招募官 招募一个 新西兰 加拿大 多少钱
     */
    public BigDecimal getEach_master_newzealand_canada_australia() {
        return each_master_newzealand_canada_australia;
    }

    /**
     * 外教招募官 招募一个 新西兰 加拿大 多少钱
     * @param each_master_newzealand_canada_australia 外教招募官 招募一个 新西兰 加拿大 多少钱
     */
    public void setEach_master_newzealand_canada_australia(BigDecimal each_master_newzealand_canada_australia) {
        this.each_master_newzealand_canada_australia = each_master_newzealand_canada_australia;
    }

    /**
     * 外教招募官 招募一个 英国多少钱
     * @return each_master_britain 外教招募官 招募一个 英国多少钱
     */
    public BigDecimal getEach_master_britain() {
        return each_master_britain;
    }

    /**
     * 外教招募官 招募一个 英国多少钱
     * @param each_master_britain 外教招募官 招募一个 英国多少钱
     */
    public void setEach_master_britain(BigDecimal each_master_britain) {
        this.each_master_britain = each_master_britain;
    }

    /**
     * 外教招募官 招募一个 南非 欧洲多少钱
     * @return each_master_southafrica_europe 外教招募官 招募一个 南非 欧洲多少钱
     */
    public BigDecimal getEach_master_southafrica_europe() {
        return each_master_southafrica_europe;
    }

    /**
     * 外教招募官 招募一个 南非 欧洲多少钱
     * @param each_master_southafrica_europe 外教招募官 招募一个 南非 欧洲多少钱
     */
    public void setEach_master_southafrica_europe(BigDecimal each_master_southafrica_europe) {
        this.each_master_southafrica_europe = each_master_southafrica_europe;
    }

    /**
     * 外教招募官 招募一个 其他多少钱
     * @return each_master_else 外教招募官 招募一个 其他多少钱
     */
    public BigDecimal getEach_master_else() {
        return each_master_else;
    }

    /**
     * 外教招募官 招募一个 其他多少钱
     * @param each_master_else 外教招募官 招募一个 其他多少钱
     */
    public void setEach_master_else(BigDecimal each_master_else) {
        this.each_master_else = each_master_else;
    }

    /**
     * 外教招募管获得出售课程 百分比
     * @return master_bonus 外教招募管获得出售课程 百分比
     */
    public BigDecimal getMaster_bonus() {
        return master_bonus;
    }

    /**
     * 外教招募管获得出售课程 百分比
     * @param master_bonus 外教招募管获得出售课程 百分比
     */
    public void setMaster_bonus(BigDecimal master_bonus) {
        this.master_bonus = master_bonus;
    }
}