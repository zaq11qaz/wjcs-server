package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="学习卡详情列表",description="学习卡详情列表属性说明")
public class StudyCardEntity extends PageInfo {
    private static final long serialVersionUID = 1088772984042340466L;
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 
     */
    @ApiModelProperty(value="")
    private Date create_time;

    /**
     * 
     */
    @ApiModelProperty(value="")
    private Date update_time;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String card_name;

    /**
     * 权益说明
     */
    @ApiModelProperty(value="权益说明")
    private String equity_explain;

    /**
     * 折扣
     */
    @ApiModelProperty(value="折扣",example="1")
    private BigDecimal discount;

    /**
     * 是否折扣
     */
    @ApiModelProperty(value="是否折扣",example="false")
    private Boolean is_discount;

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    @ApiModelProperty(value="类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教")
    private String type;

    /**
     * 1 上架 2下架
     */
    @ApiModelProperty(value="1 上架 2下架",example="1")
    private Integer status;

    /**
     * 等级
     */
    @ApiModelProperty(value="等级",example="1")
    private Integer level;

    @ApiModelProperty(value="学习卡价格列表")
    List<StudyPriceEntity> studyPriceEntities;

    public List<StudyPriceEntity> getStudyPriceEntities() {
        return studyPriceEntities;
    }

    public void setStudyPriceEntities(List<StudyPriceEntity> studyPriceEntities) {
        this.studyPriceEntities = studyPriceEntities;
    }
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    @ApiModelProperty(value = "数据map")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 名称
     * @return card_name 名称
     */
    public String getCard_name() {
        return card_name;
    }

    /**
     * 名称
     * @param card_name 名称
     */
    public void setCard_name(String card_name) {
        this.card_name = card_name == null ? null : card_name.trim();
    }

    /**
     * 权益说明
     * @return equity_explain 权益说明
     */
    public String getEquity_explain() {
        return equity_explain;
    }

    /**
     * 权益说明
     * @param equity_explain 权益说明
     */
    public void setEquity_explain(String equity_explain) {
        this.equity_explain = equity_explain == null ? null : equity_explain.trim();
    }

    /**
     * 折扣
     * @return discount 折扣
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 折扣
     * @param discount 折扣
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 是否折扣
     * @return is_discount 是否折扣
     */
    public Boolean getIs_discount() {
        return is_discount;
    }

    /**
     * 是否折扣
     * @param is_discount 是否折扣
     */
    public void setIs_discount(Boolean is_discount) {
        this.is_discount = is_discount;
    }

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @return type 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public String getType() {
        return type;
    }

    /**
     * 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     * @param type 类别 major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 1 上架 2下架
     * @return status 1 上架 2下架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 上架 2下架
     * @param status 1 上架 2下架
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 等级
     * @return level 等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 等级
     * @param level 等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
}