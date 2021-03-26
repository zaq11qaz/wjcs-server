package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="机构招募师列表",description="机构招募师列表属性说明")
public class UserRecommenderEntity extends PageInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",example="1")
    private Long id;

    /**
     * 机构推荐官 mechanism_recommender 外教推荐官 master_recommender
     */
    @ApiModelProperty(value="机构推荐官 mechanism_recommender 外教推荐官 master_recommender")
    private String type;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 名
     */
    @ApiModelProperty(value="名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号",example="1")
    private Long phone;

    /**
     * 状态 1 等待审核 2 通过 3 拒绝 4 冻结
     */
    @ApiModelProperty(value="状态 1 等待审核 2 通过 3 拒绝 4 冻结",example="1")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    /**
     * 关联收益规则id
     */
    @ApiModelProperty(value="关联收益规则id",example="1")
    private Long rule_id;

    /**
     * 拒绝理由
     */
    @ApiModelProperty(value="拒绝理由")
    private String refuse_contect;

    /**
     * 冻结理由
     */
    @ApiModelProperty(value="冻结理由")
    private String frozen_contect;

    /**
     * 本月收益
     */
    @ApiModelProperty(value="本月收益",example="1")
    private BigDecimal earnings_this_month;

    @ApiModelProperty(value ="验证码" ,example="1")
    private String verification_code;

    @ApiModelProperty(value ="邀请人数" ,example="1")
    private Long invate_num;

    @ApiModelProperty(value ="map")
    private Map<String,Object> map;

    @ApiModelProperty(value ="注册方式")
    private Integer register_type;

    @ApiModelProperty(value ="邮箱")
    private String email;

    @ApiModelProperty(value = "名",example = "1")
    private String full_name;

    @ApiModelProperty(value = "fullName id集合",example = "1")
    private List ids;

    @ApiModelProperty(value = "liginName id集合",example = "1")
    private List loginIDs;

    @ApiModelProperty(value = "帐号",example = "1")
    private String Login_name;

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


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List getIds() {
        return ids;
    }

    public void setIds(List ids) {
        this.ids = ids;
    }

    public List getLoginIDs() {
        return loginIDs;
    }

    public void setLoginIDs(List loginIDs) {
        this.loginIDs = loginIDs;
    }

    public String getLogin_name() {
        return Login_name;
    }

    public void setLogin_name(String login_name) {
        Login_name = login_name;
    }

    public Integer getRegister_type() {
        return register_type;
    }

    public void setRegister_type(Integer register_type) {
        this.register_type = register_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Long getInvate_num() {
        return invate_num;
    }

    public void setInvate_num(Long invate_num) {
        this.invate_num = invate_num;
    }

    private String invate_code;

    public String getInvate_code() {
        return invate_code;
    }

    public void setInvate_code(String invate_code) {
        this.invate_code = invate_code;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
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
     * 机构推荐官 mechanism_recommender 外教推荐官 master_recommender
     * @return type 机构推荐官 mechanism_recommender 外教推荐官 master_recommender
     */
    public String getType() {
        return type;
    }

    /**
     * 机构推荐官 mechanism_recommender 外教推荐官 master_recommender
     * @param type 机构推荐官 mechanism_recommender 外教推荐官 master_recommender
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 手机号
     * @return phone 手机号
     */
    public Long getPhone() {
        return phone;
    }

    /**
     * 手机号
     * @param phone 手机号
     */
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    /**
     * 状态 1 等待审核 2 通过 3 拒绝 4 冻结
     * @return status 状态 1 等待审核 2 通过 3 拒绝 4 冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1 等待审核 2 通过 3 拒绝 4 冻结
     * @param status 状态 1 等待审核 2 通过 3 拒绝 4 冻结
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
     * 关联收益规则id
     * @return rule_id 关联收益规则id
     */
    public Long getRule_id() {
        return rule_id;
    }

    /**
     * 关联收益规则id
     * @param rule_id 关联收益规则id
     */
    public void setRule_id(Long rule_id) {
        this.rule_id = rule_id;
    }

    /**
     * 拒绝理由
     * @return refuse_contect 拒绝理由
     */
    public String getRefuse_contect() {
        return refuse_contect;
    }

    /**
     * 拒绝理由
     * @param refuse_contect 拒绝理由
     */
    public void setRefuse_contect(String refuse_contect) {
        this.refuse_contect = refuse_contect == null ? null : refuse_contect.trim();
    }

    /**
     * 冻结理由
     * @return frozen_contect 冻结理由
     */
    public String getFrozen_contect() {
        return frozen_contect;
    }

    /**
     * 冻结理由
     * @param frozen_contect 冻结理由
     */
    public void setFrozen_contect(String frozen_contect) {
        this.frozen_contect = frozen_contect == null ? null : frozen_contect.trim();
    }

    /**
     * 本月收益
     * @return earnings_this_month 本月收益
     */
    public BigDecimal getEarnings_this_month() {
        return earnings_this_month;
    }

    /**
     * 本月收益
     * @param earnings_this_month 本月收益
     */
    public void setEarnings_this_month(BigDecimal earnings_this_month) {
        this.earnings_this_month = earnings_this_month;
    }
}