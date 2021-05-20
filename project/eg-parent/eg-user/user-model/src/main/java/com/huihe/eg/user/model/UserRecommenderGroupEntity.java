package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="推荐官群组",description="推荐官群组属性说明")
public class UserRecommenderGroupEntity extends PageInfo {
    private static final long serialVersionUID = -2684640554662290517L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 1 不可用 2 可用 3 删除
     */
    @ApiModelProperty(value="1 不可用 2 可用 3 删除",example="1")
    private Integer status;

    /**
     * 组名
     */
    @ApiModelProperty(value="组名")
    private String group_name;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 推荐官管理员id
     */
    @ApiModelProperty(value="推荐官管理员id",example="1")
    private Long admin_id;

    /**
     * 总订单
     */
    @ApiModelProperty(value="总订单",example="1")
    private Integer total_orders;

    /**
     * 今日总订单
     */
    @ApiModelProperty(value="今日总订单",example="1")
    private Integer today_orders;

    /**
     * map
     */
    @ApiModelProperty(value="map",example="1")
    private Map<String,Object> map;

    /**
     * 今日分享数
     */
    @ApiModelProperty(value="今日分享数",example="1")
    private Integer today_share_count;

    /**
     * 总分享数
     */
    @ApiModelProperty(value="总分享数",example="1")
    private Integer total_share_count;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动id",example="1")
    private Long activity_id;

    /**
     * 群内人数
     */
    @ApiModelProperty(value="群内人数",example="1")
    private Integer group_user_count;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 1 不可用 2 可用 3 删除
     * @return status 1 不可用 2 可用 3 删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 不可用 2 可用 3 删除
     * @param status 1 不可用 2 可用 3 删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 组名
     * @return group_name 组名
     */
    public String getGroup_name() {
        return group_name;
    }

    /**
     * 组名
     * @param group_name 组名
     */
    public void setGroup_name(String group_name) {
        this.group_name = group_name == null ? null : group_name.trim();
    }

    /**
     * 类型
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 推荐官管理员id
     * @return admin_id 推荐官管理员id
     */
    public Long getAdmin_id() {
        return admin_id;
    }

    /**
     * 推荐官管理员id
     * @param admin_id 推荐官管理员id
     */
    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    /**
     * 总订单
     * @return total_orders 总订单
     */
    public Integer getTotal_orders() {
        return total_orders;
    }

    /**
     * 总订单
     * @param total_orders 总订单
     */
    public void setTotal_orders(Integer total_orders) {
        this.total_orders = total_orders;
    }

    /**
     * 今日总订单
     * @return today_orders 今日总订单
     */
    public Integer getToday_orders() {
        return today_orders;
    }

    /**
     * 今日总订单
     * @param today_orders 今日总订单
     */
    public void setToday_orders(Integer today_orders) {
        this.today_orders = today_orders;
    }

    /**
     * 今日分享数
     * @return today_share_count 今日分享数
     */
    public Integer getToday_share_count() {
        return today_share_count;
    }

    /**
     * 今日分享数
     * @param today_share_count 今日分享数
     */
    public void setToday_share_count(Integer today_share_count) {
        this.today_share_count = today_share_count;
    }

    /**
     * 总分享数
     * @return total_share_count 总分享数
     */
    public Integer getTotal_share_count() {
        return total_share_count;
    }

    /**
     * 总分享数
     * @param total_share_count 总分享数
     */
    public void setTotal_share_count(Integer total_share_count) {
        this.total_share_count = total_share_count;
    }

    /**
     * 活动id
     * @return activity_id 活动id
     */
    public Long getActivity_id() {
        return activity_id;
    }

    /**
     * 活动id
     * @param activity_id 活动id
     */
    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    /**
     * 群内人数
     * @return group_user_count 群内人数
     */
    public Integer getGroup_user_count() {
        return group_user_count;
    }

    /**
     * 群内人数
     * @param group_user_count 群内人数
     */
    public void setGroup_user_count(Integer group_user_count) {
        this.group_user_count = group_user_count;
    }
}