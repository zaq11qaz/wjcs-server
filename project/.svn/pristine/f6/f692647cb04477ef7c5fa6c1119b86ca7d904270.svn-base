package com.huihe.eg.news.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="好奇、主题",description="好奇、主题属性说明")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "news",type = "curiosity",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class CuriosityEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID",example="1")
    private Long user_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 好奇人数
     */
    @ApiModelProperty(value="好奇人数",example="1")
    private Integer curiosity_count;

    /**
     * 状态1.正常2.违规不展示3推送
     */
    @ApiModelProperty(value="状态1.正常2.违规不展示3推送",example="1")
    private Integer status;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 标签id
     */
    @ApiModelProperty(value="标签id",example="1")
    private Long classfiy;

    /**
     * 是否赏金
     */
    @ApiModelProperty(value="是否赏金",example="false")
    private Boolean is_reward;

    /**
     * 回应次数
     */
    @ApiModelProperty(value="回应次数",example="1")
    private Integer viewpoint_count;

    /**
     * 地区
     */
    @ApiModelProperty(value="地区")
    private String address;

    /**
     * 赏金
     */
    @ApiModelProperty(value="赏金",example="1")
    private BigDecimal more_count;

    /**
     * 分享次数
     */
    @ApiModelProperty(value="分享次数",example="1")
    private Integer share_count;

    /**
     * 路径
     */
    @ApiModelProperty(value="路径")
    private String url;
    @ApiModelProperty(value = "全部/图片/视频")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    private int style;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    /**
     * 其他数据
     */
    @ApiModelProperty(value = "其他数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    /**
     * 操作用户Id
     */
    @ApiModelProperty(value = "操作用户Id",example = "1")
    private Long oper_id;

    public Long getOper_id() {
        return oper_id;
    }

    public void setOper_id(Long oper_id) {
        this.oper_id = oper_id;
    }

    @ApiModelProperty(value = "回应用户信息")
    private List<JSONObject> viewPointUserInfos;

    public List<JSONObject> getViewPointUserInfos() {
        return viewPointUserInfos;
    }

    public void setViewPointUserInfos(List<JSONObject> viewPointUserInfos) {
        this.viewPointUserInfos = viewPointUserInfos;
    }

    List<CuriosityViewpointEntity> curiosityViewpointEntities;
    public List<CuriosityViewpointEntity> getCuriosityViewpointEntities() {
        return curiosityViewpointEntities;
    }

    public void setCuriosityViewpointEntities(List<CuriosityViewpointEntity> curiosityViewpointEntities) {
        this.curiosityViewpointEntities = curiosityViewpointEntities;
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
     * 用户ID
     * @return user_id 用户ID
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 用户ID
     * @param user_id 用户ID
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
     * 好奇人数
     * @return curiosity_count 好奇人数
     */
    public Integer getCuriosity_count() {
        return curiosity_count;
    }

    /**
     * 好奇人数
     * @param curiosity_count 好奇人数
     */
    public void setCuriosity_count(Integer curiosity_count) {
        this.curiosity_count = curiosity_count;
    }

    /**
     * 状态1.正常2.违规不展示3推送
     * @return status 状态1.正常2.违规不展示3推送
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1.正常2.违规不展示3推送
     * @param status 状态1.正常2.违规不展示3推送
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 标签id
     * @return classfiy 标签id
     */
    public Long getClassfiy() {
        return classfiy;
    }

    /**
     * 标签id
     * @param classfiy 标签id
     */
    public void setClassfiy(Long classfiy) {
        this.classfiy = classfiy;
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
     * 回应次数
     * @return viewpoint_count 回应次数
     */
    public Integer getViewpoint_count() {
        return viewpoint_count;
    }

    /**
     * 回应次数
     * @param viewpoint_count 回应次数
     */
    public void setViewpoint_count(Integer viewpoint_count) {
        this.viewpoint_count = viewpoint_count;
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
     * 路径
     * @return url 路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 路径
     * @param url 路径
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}