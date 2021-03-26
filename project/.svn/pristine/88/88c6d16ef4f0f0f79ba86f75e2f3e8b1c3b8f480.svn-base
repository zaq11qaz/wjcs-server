package com.huihe.eg.news.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value = "好奇观点", description = "好奇观点属性说明")
public class CuriosityViewpointEntity extends PageInfo {
    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long id;

    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long user_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 观点访问URL
     */
    @ApiModelProperty(value="观点访问URL")
    private String url;

    /**
     * 图片
     */
    @ApiModelProperty(value="图片")
    private String picts;

    /**
     * 好奇id
     */
    @ApiModelProperty(value="好奇id",example="1")
    private Long curiosity_id;

    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 点赞次数
     */
    @ApiModelProperty(value="点赞次数",example="1")
    private Integer like_count;

    /**
     * 评论次数
     */
    @ApiModelProperty(value="评论次数",example="1")
    private Integer comment_count;

    /**
     * 分享次数
     */
    @ApiModelProperty(value="分享次数",example="1")
    private Integer share_count;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 状态1.正常2.非正常
     */
    @ApiModelProperty(value="状态1.正常2.非正常",example="1")
    private Integer status;

    /**
     * 标签
     */
    @ApiModelProperty(value="标签",example="1")
    private Long classfiy;

    /**
     * 收藏次数
     */
    @ApiModelProperty(value="收藏次数",example="1")
    private Integer collect_count;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 封面类型
     */
    @ApiModelProperty(value="封面类型",example="1")
    private Integer style;

    /**
     * 封面
     */
    @ApiModelProperty(value="封面")
    private String cover;

    /**
     * 是否赏金
     */
    @ApiModelProperty(value="是否赏金",example="false")
    private Boolean is_reward;

    /**
     * 地区
     */
    @ApiModelProperty(value="地区")
    private String address;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序",example="1")
    private Integer sort;

    /**
     * 赏金
     */
    @ApiModelProperty(value="赏金",example="1")
    private BigDecimal more_count;

    /**
     * 查看次数
     */
    @ApiModelProperty(value="查看次数",example="1")
    private Integer browse_count;

    @ApiModelProperty(value = "其他数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @ApiModelProperty(value = "操作用户Id",example="1")
    private Long oper_id;

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
    }

    @ApiModelProperty(value = "赏金订单")
    private OrderEntity orderEntity;

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @ApiModelProperty(value = "图片集合")
    private List<String> PicStrings;
    @ApiModelProperty(value = "封面集合")
    private List<String> coverPics;

    public List<String> getPicStrings() {
        return PicStrings;
    }

    public void setPicStrings(List<String> picStrings) {
        PicStrings = picStrings;
    }

    public List<String> getCoverPics() {
        return coverPics;
    }
    public void setCoverPics(List<String> coverPics) {
        this.coverPics = coverPics;
    }
    @ApiModelProperty(value = "全部/图片/视频")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @ApiModelProperty(value = "所属好奇")
    public CuriosityEntity curiosityEntity;

    private Boolean is_teach_paypal;

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public CuriosityEntity getCuriosityEntity() {
        return curiosityEntity;
    }

    public void setCuriosityEntity(CuriosityEntity curiosityEntity) {
        this.curiosityEntity = curiosityEntity;
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return user_id 
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 
     * @param user_id 
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
     * 观点访问URL
     * @return url 观点访问URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 观点访问URL
     * @param url 观点访问URL
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 图片
     * @return picts 图片
     */
    public String getPicts() {
        return picts;
    }

    /**
     * 图片
     * @param picts 图片
     */
    public void setPicts(String picts) {
        this.picts = picts == null ? null : picts.trim();
    }

    /**
     * 好奇id
     * @return curiosity_id 好奇id
     */
    public Long getCuriosity_id() {
        return curiosity_id;
    }

    /**
     * 好奇id
     * @param curiosity_id 好奇id
     */
    public void setCuriosity_id(Long curiosity_id) {
        this.curiosity_id = curiosity_id;
    }

    /**
     * 内容
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 点赞次数
     * @return like_count 点赞次数
     */
    public Integer getLike_count() {
        return like_count;
    }

    /**
     * 点赞次数
     * @param like_count 点赞次数
     */
    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    /**
     * 评论次数
     * @return comment_count 评论次数
     */
    public Integer getComment_count() {
        return comment_count;
    }

    /**
     * 评论次数
     * @param comment_count 评论次数
     */
    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    /**
     * 分享次数
     * @return share_count 分享次数
     */
    public Integer getShare_count() {
        return share_count;
    }

    /**
     * 分享次数
     * @param share_count 分享次数
     */
    public void setShare_count(Integer share_count) {
        this.share_count = share_count;
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
     * 状态1.正常2.非正常
     * @return status 状态1.正常2.非正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.正常2.非正常
     * @param status 状态1.正常2.非正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 标签
     * @return classfiy 标签
     */
    public Long getClassfiy() {
        return classfiy;
    }

    /**
     * 标签
     * @param classfiy 标签
     */
    public void setClassfiy(Long classfiy) {
        this.classfiy = classfiy;
    }

    /**
     * 收藏次数
     * @return collect_count 收藏次数
     */
    public Integer getCollect_count() {
        return collect_count;
    }

    /**
     * 收藏次数
     * @param collect_count 收藏次数
     */
    public void setCollect_count(Integer collect_count) {
        this.collect_count = collect_count;
    }

    /**
     * 标题
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 封面类型
     * @return style 封面类型
     */
    public Integer getStyle() {
        return style;
    }

    /**
     * 封面类型
     * @param style 封面类型
     */
    public void setStyle(Integer style) {
        this.style = style;
    }

    /**
     * 封面
     * @return cover 封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * 封面
     * @param cover 封面
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * 是否赏金
     * @return is_reward 是否赏金
     */
    public Boolean getIs_reward() {
        return is_reward;
    }

    /**
     * 是否赏金
     * @param is_reward 是否赏金
     */
    public void setIs_reward(Boolean is_reward) {
        this.is_reward = is_reward;
    }

    /**
     * 地区
     * @return address 地区
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地区
     * @param address 地区
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 排序
     * @return sort 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 赏金
     * @return more_count 赏金
     */
    public BigDecimal getMore_count() {
        return more_count;
    }

    /**
     * 赏金
     * @param more_count 赏金
     */
    public void setMore_count(BigDecimal more_count) {
        this.more_count = more_count;
    }

    /**
     * 查看次数
     * @return browse_count 查看次数
     */
    public Integer getBrowse_count() {
        return browse_count;
    }

    /**
     * 查看次数
     * @param browse_count 查看次数
     */
    public void setBrowse_count(Integer browse_count) {
        this.browse_count = browse_count;
    }
}