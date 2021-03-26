package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="广告banner图片信息",description="广告banner图片信息属性说明")
public class ExhibitionPicEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 类型startupadvertisement：启动页广告,banner:横幅     master_title助学师首页  look_banner 找人  discuss_banner讨论 mechanism_hot机构热门
     */
    @ApiModelProperty(value="类型startupadvertisement：启动页广告,banner:横幅     master_title助学师首页  look_banner 找人  discuss_banner讨论 mechanism_hot机构热门   ")
    private String type;

    /**
     * 图片
     */
    @ApiModelProperty(value="图片")
    private String pic;

    /**
     * 来源
     */
    @ApiModelProperty(value="来源")
    private String source_url;

    /**
     * 来源类型 note地球圈, news好奇,user用户,web页面,message聊天,master助学师,learningcard学习卡,member会员 recharge充值猫币  mechanism机构
     */
    @ApiModelProperty(value="来源类型 note地球圈, news好奇,user用户,web页面,message聊天,master助学师,learningcard学习卡,member会员 recharge充值猫币  mechanism机构")
    private String source_type;

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
     * 状态:1正在使用2 不使用
     */
    @ApiModelProperty(value="状态:1正在使用2 不使用",example="1")
    private Integer state;

    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long type_id;

    /**
     * 主题
     */
    @ApiModelProperty(value="主题")
    private String theme;

    @ApiModelProperty(value="查看次数")
    private Long viewing_times;

    @ApiModelProperty(value="点击次数")
    private Long click_times;


    /**
     * 广告位置
     */
    @ApiModelProperty(value="广告位置")
    private String banner_position;


    /**
     * 名
     */
    @ApiModelProperty(value="名")
    private String banner_name;

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

    public String getBanner_position() {
        return banner_position;
    }

    public void setBanner_position(String banner_position) {
        this.banner_position = banner_position;
    }

    public String getBanner_name() {
        return banner_name;
    }

    public void setBanner_name(String banner_name) {
        this.banner_name = banner_name;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Long getViewing_times() {
        return viewing_times;
    }

    public void setViewing_times(Long viewing_times) {
        this.viewing_times = viewing_times;
    }

    public Long getClick_times() {
        return click_times;
    }

    public void setClick_times(Long click_times) {
        this.click_times = click_times;
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
     * 类型startupadvertisement：启动页广告,banner:横幅     master_title助学师首页  look_banner 找人  discuss_banner讨论 
     * @return type 类型startupadvertisement：启动页广告,banner:横幅     master_title助学师首页  look_banner 找人  discuss_banner讨论 
     */
    public String getType() {
        return type;
    }

    /**
     * 类型startupadvertisement：启动页广告,banner:横幅     master_title助学师首页  look_banner 找人  discuss_banner讨论 
     * @param type 类型startupadvertisement：启动页广告,banner:横幅     master_title助学师首页  look_banner 找人  discuss_banner讨论 
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 图片
     * @return pic 图片
     */
    public String getPic() {
        return pic;
    }

    /**
     * 图片
     * @param pic 图片
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * 来源
     * @return source_url 来源
     */
    public String getSource_url() {
        return source_url;
    }

    /**
     * 来源
     * @param source_url 来源
     */
    public void setSource_url(String source_url) {
        this.source_url = source_url == null ? null : source_url.trim();
    }

    /**
     * 来源类型 note地球圈, news好奇,user用户,web页面,message聊天,master助学师,learningcard学习卡,member会员 recharge充值猫币
     * @return source_type 来源类型 note地球圈, news好奇,user用户,web页面,message聊天,master助学师,learningcard学习卡,member会员 recharge充值猫币
     */
    public String getSource_type() {
        return source_type;
    }

    /**
     * 来源类型 note地球圈, news好奇,user用户,web页面,message聊天,master助学师,learningcard学习卡,member会员 recharge充值猫币
     * @param source_type 来源类型 note地球圈, news好奇,user用户,web页面,message聊天,master助学师,learningcard学习卡,member会员 recharge充值猫币
     */
    public void setSource_type(String source_type) {
        this.source_type = source_type == null ? null : source_type.trim();
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
     * 状态:1正在使用2 不使用
     * @return state 状态:1正在使用2 不使用
     */
    public Integer getState() {
        return state;
    }

    /**
     * 状态:1正在使用2 不使用
     * @param state 状态:1正在使用2 不使用
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 
     * @return type_id 
     */
    public Long getType_id() {
        return type_id;
    }

    /**
     * 
     * @param type_id 
     */
    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    /**
     * 主题
     * @return theme 主题
     */
    public String getTheme() {
        return theme;
    }

    /**
     * 主题
     * @param theme 主题
     */
    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }
}