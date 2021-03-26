package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.Map;

@ApiModel(value="境友圈记录列表",description="境友圈记录列表属性说明")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "message",type = "note",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class NoteUserEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 文本类容
     */
    @ApiModelProperty(value="文本类容")
    private String content;

    /**
     * 图片表情
     */
    @ApiModelProperty(value="图片表情")
    private String picts;

    /**
     * 地理位置
     */
    @ApiModelProperty(value="地理位置")
    private String location;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 状态1.完全公开2.仅好友可见
     */
    @ApiModelProperty(value="状态1.完全公开2.仅好友可见",example="1")
    private Integer status;

    /**
     * 点赞次数
     */
    @ApiModelProperty(value="点赞次数",example="1")
    private Integer like_count;

    /**
     * 评论数量
     */
    @ApiModelProperty(value="评论数量",example="1")
    private Integer comment_count;

    /**
     * 分享次数
     */
    @ApiModelProperty(value="分享次数",example="1")
    private Integer share_count;

    /**
     * 观看次数
     */
    @ApiModelProperty(value="观看次数",example="1")
    private Integer watch_count;

    /**
     * 排版类型
     */
    @ApiModelProperty(value="排版类型",example="1")
    private Integer style;

    /**
     * 封面
     */
    @ApiModelProperty(value="封面")
    private String cover;

    /**
     * 图片高
     */
    @ApiModelProperty(value="图片高",example="1")
    private Integer img_height;

    /**
     * 图片宽
     */
    @ApiModelProperty(value="图片宽",example="1")
    private Integer img_width;

    /**
     * 视频时长
     */
    @ApiModelProperty(value="视频时长")
    private String video_duration;

    /**
     * 类别
     */
    @ApiModelProperty(value="类别")
    private String classfiy;

    /**
     * 收藏次数
     */
    @ApiModelProperty(value="收藏次数",example="1")
    private Integer collect_count;

    @ApiModelProperty(value = "登录来源",example = "ios")
    private String platform;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 网页地址
     */
    @ApiModelProperty(value="网页地址")
    private String url;

    @ApiModelProperty(value = "操作用户id",example = "1")
    private Long oper_id;

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
    }

    @ApiModelProperty(value="map")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @ApiModelProperty(value = "是否已读")
    private Boolean is_read;

    @ApiModelProperty(value = "是否教付保")
    private Boolean is_teach_paypal;

    @ApiModelProperty(value = "商品id")
    private Long master_set_price_id;

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }

    public Boolean getIs_teach_paypal() {
        return is_teach_paypal;
    }

    public void setIs_teach_paypal(Boolean is_teach_paypal) {
        this.is_teach_paypal = is_teach_paypal;
    }

    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }

    /**
     * 主键ID
     * @return id 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户id
     * @return user_id 用户id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户id
     * @param user_id 用户id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 文本类容
     * @return content 文本类容
     */
    public String getContent() {
        return content;
    }

    /**
     * 文本类容
     * @param content 文本类容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 图片表情
     * @return picts 图片表情
     */
    public String getPicts() {
        return picts;
    }

    /**
     * 图片表情
     * @param picts 图片表情
     */
    public void setPicts(String picts) {
        this.picts = picts == null ? null : picts.trim();
    }

    /**
     * 地理位置
     * @return location 地理位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * 地理位置
     * @param location 地理位置
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
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
     * 状态1.完全公开2.仅好友可见
     * @return status 状态1.完全公开2.仅好友可见
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.完全公开2.仅好友可见
     * @param status 状态1.完全公开2.仅好友可见
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 评论数量
     * @return comment_count 评论数量
     */
    public Integer getComment_count() {
        return comment_count;
    }

    /**
     * 评论数量
     * @param comment_count 评论数量
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
     * 观看次数
     * @return watch_count 观看次数
     */
    public Integer getWatch_count() {
        return watch_count;
    }

    /**
     * 观看次数
     * @param watch_count 观看次数
     */
    public void setWatch_count(Integer watch_count) {
        this.watch_count = watch_count;
    }

    /**
     * 排版类型
     * @return style 排版类型
     */
    public Integer getStyle() {
        return style;
    }

    /**
     * 排版类型
     * @param style 排版类型
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
     * 图片高
     * @return img_height 图片高
     */
    public Integer getImg_height() {
        return img_height;
    }

    /**
     * 图片高
     * @param img_height 图片高
     */
    public void setImg_height(Integer img_height) {
        this.img_height = img_height;
    }

    /**
     * 图片宽
     * @return img_width 图片宽
     */
    public Integer getImg_width() {
        return img_width;
    }

    /**
     * 图片宽
     * @param img_width 图片宽
     */
    public void setImg_width(Integer img_width) {
        this.img_width = img_width;
    }

    /**
     * 视频时长
     * @return video_duration 视频时长
     */
    public String getVideo_duration() {
        return video_duration;
    }

    /**
     * 视频时长
     * @param video_duration 视频时长
     */
    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration == null ? null : video_duration.trim();
    }

    /**
     * 类别
     * @return classfiy 类别
     */
    public String getClassfiy() {
        return classfiy;
    }

    /**
     * 类别
     * @param classfiy 类别
     */
    public void setClassfiy(String classfiy) {
        this.classfiy = classfiy == null ? null : classfiy.trim();
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
     * 网页地址
     * @return url 网页地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 网页地址
     * @param url 网页地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}