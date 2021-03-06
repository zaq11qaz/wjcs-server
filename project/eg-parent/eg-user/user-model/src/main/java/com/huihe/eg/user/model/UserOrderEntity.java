package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="订单",description="订单属性说明")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "order",type = "userorder",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class UserOrderEntity extends PageInfo {

    private static final long serialVersionUID = 7920470453272951019L;
    /**
     * 主键id
     */
    @Id
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 付钱用户id
     */
    @Field
    @ApiModelProperty(value="付钱用户id",example="1")
    private Long user_id;

    /**
     * (商品,内容)id
     */
    @ApiModelProperty(value="(商品,内容)id",example="1")
    @Field
    private Long pay_id;

    /**
     * 类型(curiosity,viewpoint,redenvelopes:红包,gift:礼物）
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="类型(curiosity,viewpoint,redenvelopes:红包,gift:礼物,task:任务,sign:签到,chat:畅聊,recharge:充值, video 视频聊天,watch_recording观看录播）")
    private String type;

    /**
     * 总数值
     */
    @ApiModelProperty(value="总数值",example="1")
    @Field
    private BigDecimal pay_count;

    /**
     * 收获用户id
     */
    @Field
    @ApiModelProperty(value="收获用户id",example="1")
    private Long payment_id;

    /**
     * 创建时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 支付时间
     */
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    @ApiModelProperty(value="支付时间")
    private Date pay_time;

    /**
     * 1未到账2已到账
     */
    @Field
    @ApiModelProperty(value="1未到账2已到账",example="1")
    private Integer status;

    /**
     * 订单号
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="订单号")
    private String order_no;

    /**
     * 分类id
     */
    @Field
    @ApiModelProperty(value="分类id",example="1")
    private Long category_id;

    /**
     * 礼物id(商品id)
     */
    @Field
    @ApiModelProperty(value="礼物id(商品id)",example="1")
    private Long type_id;

    /**
     * (礼物)数量
     */
    @Field
    @ApiModelProperty(value="(礼物)数量",example="1")
    private Integer gift_count;

    /**
     * 来源
     */
    @Field(type = FieldType.Text, indexAnalyzer = "ik_max_word")
    @ApiModelProperty(value="来源(note:友圈,chat:聊天,user:个人详情,rebate 回赠) " +
            " live_connection 直播连麦 live_broadcast  直播观看  video_connection  视频连麦   video_broadcast   视频观看   class 上课  video 视频聊天")
    private String source;
    @ApiModelProperty(value = "map")
    private Map<String,Object> map;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "筛选开始时间")
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    private Date start_time;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "筛选结束时间")
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    private Date end_time;
    @ApiModelProperty(value = "true:收入false:支出",example = "false")
    @Field
    private Boolean transaction_type;
    @ApiModelProperty(value = "图标")
    @Field
    private String logo_url;
    @ApiModelProperty(value = "备注")
    @Field
    private String remarks;
    @Field
    @ApiModelProperty(value = "收获用户id集合")
    private String payment_ids;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="筛选开始时间")
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    private Date screen_start_time;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="筛选结束时间")
    @Field( type = FieldType.Date,
            format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    )
    private Date screen_end_time;

    /**
     * 操作用户Id
     */
    @Field
    @ApiModelProperty(value = "操作用户Id",example = "1")
    private Long oper_id;

    public String getPayment_ids() {
        return payment_ids;
    }

    @Field
    @ApiModelProperty(value = "是否全群",example = "false")
    private Boolean is_group;

    @Field
    @ApiModelProperty(value = "nickNameIDs id集合",example = "1")
    private List<Long> nickNameIDs;

    @Field
    @ApiModelProperty(value = "昵称",example = "1")
    private String nick_name;


    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }


    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public List<Long>  getNickNameIDs() {
        return nickNameIDs;
    }

    public void setNickNameIDs(List<Long>  nickNameIDs) {
        this.nickNameIDs = nickNameIDs;
    }

    public void setPayment_ids(String payment_ids) {
        this.payment_ids = payment_ids;
    }

    public Boolean getIs_group() {
        return is_group;
    }

    public void setIs_group(Boolean is_group) {
        this.is_group = is_group;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public Boolean getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(Boolean transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Map<String, Object> getMap() {
        return map;
    }
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Date getScreen_start_time() {
        return screen_start_time;
    }

    public void setScreen_start_time(Date screen_start_time) {
        this.screen_start_time = screen_start_time;
    }

    public Date getScreen_end_time() {
        return screen_end_time;
    }

    public void setScreen_end_time(Date screen_end_time) {
        this.screen_end_time = screen_end_time;
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
     * 付钱用户id
     * @return user_id 付钱用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 付钱用户id
     * @param user_id 付钱用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * (商品,内容)id
     * @return pay_id (商品,内容)id
     */
    public Long getPay_id() {
        return pay_id;
    }

    /**
     * (商品,内容)id
     * @param pay_id (商品,内容)id
     */
    public void setPay_id(Long pay_id) {
        this.pay_id = pay_id;
    }

    /**
     * 类型(curiosity,viewpoint,redenvelopes:红包,gift:礼物）
     * @return type 类型(curiosity,viewpoint,redenvelopes:红包,gift:礼物）
     */
    public String getType() {
        return type;
    }

    /**
     * 类型(curiosity,viewpoint,redenvelopes:红包,gift:礼物）
     * @param type 类型(curiosity,viewpoint,redenvelopes:红包,gift:礼物）
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 总数值
     * @return pay_count 总数值
     */
    public BigDecimal getPay_count() {
        return pay_count;
    }

    /**
     * 总数值
     * @param pay_count 总数值
     */
    public void setPay_count(BigDecimal pay_count) {
        this.pay_count = pay_count;
    }

    /**
     * 收获用户id
     * @return payment_id 收获用户id
     */
    public Long getPayment_id() {
        return payment_id;
    }

    /**
     * 收获用户id
     * @param payment_id 收获用户id
     */
    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
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
     * 支付时间
     * @return pay_time 支付时间
     */
    public Date getPay_time() {
        return pay_time;
    }

    /**
     * 支付时间
     * @param pay_time 支付时间
     */
    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    /**
     * 1未到账2已到账
     * @return status 1未到账2已到账
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1未到账2已到账
     * @param status 1未到账2已到账
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 订单号
     * @return order_no 订单号
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 订单号
     * @param order_no 订单号
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 分类id
     * @return category_id 分类id
     */
    public Long getCategory_id() {
        return category_id;
    }

    /**
     * 分类id
     * @param category_id 分类id
     */
    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    /**
     * 礼物id(商品id)
     * @return type_id 礼物id(商品id)
     */
    public Long getType_id() {
        return type_id;
    }

    /**
     * 礼物id(商品id)
     * @param type_id 礼物id(商品id)
     */
    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    /**
     * (礼物)数量
     * @return gift_count (礼物)数量
     */
    public Integer getGift_count() {
        return gift_count;
    }

    /**
     * (礼物)数量
     * @param gift_count (礼物)数量
     */
    public void setGift_count(Integer gift_count) {
        this.gift_count = gift_count;
    }

    /**
     * 来源
     * @return source 来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 来源
     * @param source 来源
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}