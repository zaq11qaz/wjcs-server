package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@ApiModel(value="助学师机构信息",description="助学师机构信息属性说明")
@Document(indexName = "mechanism",type = "mastermechanism",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class MasterMechanismEntity extends PageInfo {
    /**
     * 主键id
     */
    @Id
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 修改时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @ApiModelProperty(value="修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    /**
     * 机构名称
     */
    @Field
    @ApiModelProperty(value="机构名称")
    private String mechanism_name;

    /**
     * 创建用户id
     */
    @Field
    @ApiModelProperty(value="创建用户id",example="1")
    private Long user_id;

    /**
     * 直播id
     */
    @Field
    @ApiModelProperty(value="直播id",example="1")
    private Long live_streaming_id;

    /**
     * 地址
     */
    @Field(type = FieldType.Text)
    @ApiModelProperty(value="地址")
    private String mechanism_addr;

    /**
     * logo
     */
    @Field
    @ApiModelProperty(value="logo")
    private String mechanism_logo;

    /**
     * 机构电话
     */
    @Field
    @ApiModelProperty(value="机构电话")
    private String mechanism_telephone;

    /**
     * 语言
     */
    @Field
    @ApiModelProperty(value="语言")
    private String mechanism_language;

    /**
     * 优势
     */
    @Field
    @ApiModelProperty(value="优势")
    private String mechanism_advantage;

    /**
     * 简介图片
     */
    @Field
    @ApiModelProperty(value="简介图片")
    private String introduction_pic;

    /**
     * 简介文本
     */
    @Field
    @ApiModelProperty(value="简介文本")
    private String introduction_content;

    /**
     * 联系人
     */
    @Field
    @ApiModelProperty(value="联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @Field
    @ApiModelProperty(value="联系电话")
    private String contact_telephone;

    /**
     * 联系方式
     */
    @Field
    @ApiModelProperty(value="联系方式")
    private String contact_information;

    /**
     * 联系人职称
     */
    @Field
    @ApiModelProperty(value="联系人职称")
    private String contacts_title;

    /**
     * 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结
     */
    @Field
    @ApiModelProperty(value="状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结",example="1")
    private Integer status;

    /**
     * 证明材料
     */
    @Field
    @ApiModelProperty(value="证明材料")
    private String support_means;

    /**
     * 机构编号
     */
    @Field
    @ApiModelProperty(value="机构编号")
    private String mechanism_no;

    /**
     * 排序权重
     */
    @Field
    @ApiModelProperty(value="排序权重",example="1")
    private Integer sort_weight;

    /**
     * 坐标纬度值
     */
    @Field(type = FieldType.Auto)
    @ApiModelProperty(value="坐标纬度值",example="1")
    private BigDecimal latitude;

    /**
     * 拒绝理由
     */
    @Field
    @ApiModelProperty(value="拒绝理由",example="1")
    private String refuse_contect;

    public String getRefuse_contect() {
        return refuse_contect;
    }

    public void setRefuse_contect(String refuse_contect) {
        this.refuse_contect = refuse_contect;
    }

    /**
     * 坐标经度值
     */
    @Field(type = FieldType.Auto)
    @ApiModelProperty(value="坐标经度值",example="1")
    private BigDecimal longitude;

    @ApiModelProperty(value="距离",example="1")
    private BigDecimal distance;

    @ApiModelProperty(value="门面图",example="1")
    private String facade_view;

    public String getFacade_view() {
        return facade_view;
    }

    public void setFacade_view(String facade_view) {
        this.facade_view = facade_view;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    /**
     *  map数据
     *  */
    @ApiModelProperty(value="map数据")
    private Map<String,Object> map;

    /**
     *  绑定关联推荐人user_id
     *  */
    @Field
    @ApiModelProperty(value="推荐官邀请码")
    private String recommender_id;

    public String getRecommender_id() {
        return recommender_id;
    }

    public void setRecommender_id(String recommender_id) {
        this.recommender_id = recommender_id;
    }

    @Field
    @ApiModelProperty(value="是否推荐")
    private Boolean is_recommend;

    @Field
    @ApiModelProperty(value="是否支持教付宝")
    private Boolean is_support_teach_paypal;

    @ApiModelProperty(value="收益",example="1")
    private BigDecimal cash;

    @ApiModelProperty(value="验证码")
    private String verification_code;

    @Field
    @ApiModelProperty(value="类目")
    private String categories;

    @Field
    @ApiModelProperty(value="子类目")
    private String categories_child;

    @ApiModelProperty(value="提现帐号")
    private String payee_logon_id;

    @ApiModelProperty(value="老用户资源课数")
    private Integer resource_classes_old;

    @ApiModelProperty(value="新用户资源课数")
    private Integer resource_classes_new;

    @ApiModelProperty(value="平均分")
    private Double avg_rating;

    /**
     * 是否教付宝
     */
    @Field
    @ApiModelProperty(value="是否教付宝",example="false")
    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }


    public Long getLive_streaming_id() {
        return live_streaming_id;
    }

    public void setLive_streaming_id(Long live_streaming_id) {
        this.live_streaming_id = live_streaming_id;
    }

    public Double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(Double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public Integer getResource_classes_old() {
        return resource_classes_old;
    }

    public void setResource_classes_old(Integer resource_classes_old) {
        this.resource_classes_old = resource_classes_old;
    }

    public Integer getResource_classes_new() {
        return resource_classes_new;
    }

    public void setResource_classes_new(Integer resource_classes_new) {
        this.resource_classes_new = resource_classes_new;
    }

    public Boolean getIs_support_teach_paypal() {
        return is_support_teach_paypal;
    }

    public void setIs_support_teach_paypal(Boolean is_support_teach_paypal) {
        this.is_support_teach_paypal = is_support_teach_paypal;
    }

    public String getPayee_logon_id() {
        return payee_logon_id;
    }

    public void setPayee_logon_id(String payee_logon_id) {
        this.payee_logon_id = payee_logon_id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategories_child() {
        return categories_child;
    }

    public void setCategories_child(String categories_child) {
        this.categories_child = categories_child;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Boolean getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(Boolean is_recommend) {
        this.is_recommend = is_recommend;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 类型 online_mechanism 线上机构 ,offline_mechanism  线下机构
     */
    @ApiModelProperty(value="类型 online_mechanism 线上机构 ,offline_mechanism  线下机构" )
    private String type;

    /**
     * 距离 米
     */
    @ApiModelProperty(value="距离 米" )
    private Double gap;

    /**
     * 距离 米
     */
    @GeoPointField
    @ApiModelProperty(value="坐标经纬度" )
    private String location;//位置坐标 lon经度 lat纬度

    public Double getGap() {
        return gap;
    }

    public void setGap(Double gap) {
        this.gap = gap;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
     * 机构名称
     * @return mechanism_name 机构名称
     */
    public String getMechanism_name() {
        return mechanism_name;
    }

    /**
     * 机构名称
     * @param mechanism_name 机构名称
     */
    public void setMechanism_name(String mechanism_name) {
        this.mechanism_name = mechanism_name == null ? null : mechanism_name.trim();
    }

    /**
     * 创建用户id
     * @return user_id 创建用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 创建用户id
     * @param user_id 创建用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 地址
     * @return mechanism_addr 地址
     */
    public String getMechanism_addr() {
        return mechanism_addr;
    }

    /**
     * 地址
     * @param mechanism_addr 地址
     */
    public void setMechanism_addr(String mechanism_addr) {
        this.mechanism_addr = mechanism_addr == null ? null : mechanism_addr.trim();
    }

    /**
     * logo
     * @return mechanism_logo logo
     */
    public String getMechanism_logo() {
        return mechanism_logo;
    }

    /**
     * logo
     * @param mechanism_logo logo
     */
    public void setMechanism_logo(String mechanism_logo) {
        this.mechanism_logo = mechanism_logo == null ? null : mechanism_logo.trim();
    }

    /**
     * 机构电话
     * @return mechanism_telephone 机构电话
     */
    public String getMechanism_telephone() {
        return mechanism_telephone;
    }

    /**
     * 机构电话
     * @param mechanism_telephone 机构电话
     */
    public void setMechanism_telephone(String mechanism_telephone) {
        this.mechanism_telephone = mechanism_telephone == null ? null : mechanism_telephone.trim();
    }

    /**
     * 语言
     * @return mechanism_language 语言
     */
    public String getMechanism_language() {
        return mechanism_language;
    }

    /**
     * 语言
     * @param mechanism_language 语言
     */
    public void setMechanism_language(String mechanism_language) {
        this.mechanism_language = mechanism_language == null ? null : mechanism_language.trim();
    }

    /**
     * 优势
     * @return mechanism_advantage 优势
     */
    public String getMechanism_advantage() {
        return mechanism_advantage;
    }

    /**
     * 优势
     * @param mechanism_advantage 优势
     */
    public void setMechanism_advantage(String mechanism_advantage) {
        this.mechanism_advantage = mechanism_advantage == null ? null : mechanism_advantage.trim();
    }

    /**
     * 简介图片
     * @return introduction_pic 简介图片
     */
    public String getIntroduction_pic() {
        return introduction_pic;
    }

    /**
     * 简介图片
     * @param introduction_pic 简介图片
     */
    public void setIntroduction_pic(String introduction_pic) {
        this.introduction_pic = introduction_pic == null ? null : introduction_pic.trim();
    }

    /**
     * 简介文本
     * @return introduction_content 简介文本
     */
    public String getIntroduction_content() {
        return introduction_content;
    }

    /**
     * 简介文本
     * @param introduction_content 简介文本
     */
    public void setIntroduction_content(String introduction_content) {
        this.introduction_content = introduction_content == null ? null : introduction_content.trim();
    }

    /**
     * 联系人
     * @return contacts 联系人
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * 联系人
     * @param contacts 联系人
     */
    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    /**
     * 联系电话
     * @return contact_telephone 联系电话
     */
    public String getContact_telephone() {
        return contact_telephone;
    }

    /**
     * 联系电话
     * @param contact_telephone 联系电话
     */
    public void setContact_telephone(String contact_telephone) {
        this.contact_telephone = contact_telephone == null ? null : contact_telephone.trim();
    }

    /**
     * 联系方式
     * @return contact_information 联系方式
     */
    public String getContact_information() {
        return contact_information;
    }

    /**
     * 联系方式
     * @param contact_information 联系方式
     */
    public void setContact_information(String contact_information) {
        this.contact_information = contact_information == null ? null : contact_information.trim();
    }

    /**
     * 联系人职称
     * @return contacts_title 联系人职称
     */
    public String getContacts_title() {
        return contacts_title;
    }

    /**
     * 联系人职称
     * @param contacts_title 联系人职称
     */
    public void setContacts_title(String contacts_title) {
        this.contacts_title = contacts_title == null ? null : contacts_title.trim();
    }

    /**
     * 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结
     * @return status 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结
     * @param status 状态1 正在审核 , 2 审核通过 3 审核拒绝  4 注销 5 冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 证明材料
     * @return support_means 证明材料
     */
    public String getSupport_means() {
        return support_means;
    }

    /**
     * 证明材料
     * @param support_means 证明材料
     */
    public void setSupport_means(String support_means) {
        this.support_means = support_means == null ? null : support_means.trim();
    }

    /**
     * 机构编号
     * @return mechanism_no 机构编号
     */
    public String getMechanism_no() {
        return mechanism_no;
    }

    /**
     * 机构编号
     * @param mechanism_no 机构编号
     */
    public void setMechanism_no(String mechanism_no) {
        this.mechanism_no = mechanism_no == null ? null : mechanism_no.trim();
    }

    /**
     * 排序权重
     * @return sort_weight 排序权重
     */
    public Integer getSort_weight() {
        return sort_weight;
    }

    /**
     * 排序权重
     * @param sort_weight 排序权重
     */
    public void setSort_weight(Integer sort_weight) {
        this.sort_weight = sort_weight;
    }

    /**
     * 坐标纬度值
     * @return latitude 坐标纬度值
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 坐标纬度值
     * @param latitude 坐标纬度值
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * 坐标经度值
     * @return longitude 坐标经度值
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 坐标经度值
     * @param longitude 坐标经度值
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return type 
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}