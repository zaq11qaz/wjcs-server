package com.huihe.eg.authorization.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="菜单信息",description="菜单信息属性说明")
public class MenuEntity extends PageInfo implements Comparable<MenuEntity>{
    /**
     * id 唯一
     */
    @ApiModelProperty(value="id 唯一",example="1")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 更新的时间
     */
    @ApiModelProperty(value="更新的时间")
    private Date update_time;

    /**
     * 内容描述
     */
    @ApiModelProperty(value="内容描述")
    private String descr;

    /**
     * 添加的用户
     */
    @ApiModelProperty(value="添加的用户")
    private String add_user;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String type;

    /**
     * 修改的用户
     */
    @ApiModelProperty(value="修改的用户")
    private String update_user;

    /**
     * 上一级的ID
     */
    @ApiModelProperty(value="上一级的ID",example="1")
    private Long parent_id;

    /**
     * 排序 越小越靠前
     */
    @ApiModelProperty(value="排序 越小越靠前",example="1")
    private Long seq;

    @ApiModelProperty(value="路由地址",example="1")
    private String routeUrl;

    @ApiModelProperty(value="是否开放",example="false")
    private Boolean opend;

    @ApiModelProperty(value = "是否收费",example = "1")
    private BigDecimal amount;

    @ApiModelProperty(value = "状态 1 不可用 2 可用",example = "1")
    private Integer status;

    @ApiModelProperty(value = "判断是否根元素",example = "1")
    public boolean isRoot() {
        return this.parent_id == 0;
    }
    @ApiModelProperty(value = "子元素",example = "1")
    private List<MenuEntity> childrens;
    @ApiModelProperty(value = "按钮集合",example = "1")
    private List<ButtonEntity> buttonEntities;


    @ApiModelProperty(value = "map数据")
    private Map map;

    @ApiModelProperty(value = "图片路径")
    private String image_url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List<MenuEntity> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MenuEntity> childrens) {
        this.childrens = childrens;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getOpend() {
        return opend;
    }

    public void setOpend(Boolean opend) {
        this.opend = opend;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public List<ButtonEntity> getButtonEntities() {
        return buttonEntities;
    }

    public void setButtonEntities(List<ButtonEntity> buttonEntities) {
        this.buttonEntities = buttonEntities;
    }

    /**
     * id 唯一
     * @return id id 唯一
     */
    public Long getId() {
        return id;
    }

    /**
     * id 唯一
     * @param id id 唯一
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 更新的时间
     * @return update_time 更新的时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新的时间
     * @param update_time 更新的时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 内容描述
     * @return descr 内容描述
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 内容描述
     * @param descr 内容描述
     */
    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    /**
     * 添加的用户
     * @return add_user 添加的用户
     */
    public String getAdd_user() {
        return add_user;
    }

    /**
     * 添加的用户
     * @param add_user 添加的用户
     */
    public void setAdd_user(String add_user) {
        this.add_user = add_user == null ? null : add_user.trim();
    }

    /**
     * 修改的用户
     * @return update_user 修改的用户
     */
    public String getUpdate_user() {
        return update_user;
    }

    /**
     * 修改的用户
     * @param update_user 修改的用户
     */
    public void setUpdate_user(String update_user) {
        this.update_user = update_user == null ? null : update_user.trim();
    }

    /**
     * 上一级的ID
     * @return parent_id 上一级的ID
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * 上一级的ID
     * @param parent_id 上一级的ID
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * 排序 越小越靠前
     * @return seq 排序 越小越靠前
     */
    public Long getSeq() {
        return seq;
    }

    /**
     * 排序 越小越靠前
     * @param seq 排序 越小越靠前
     */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    @Override
    public int compareTo(MenuEntity o) {
        if (this.getSeq() != null && o.getSeq() != null) {
            return this.getSeq().compareTo(o.getSeq());
        }
        return -1;
    }
}