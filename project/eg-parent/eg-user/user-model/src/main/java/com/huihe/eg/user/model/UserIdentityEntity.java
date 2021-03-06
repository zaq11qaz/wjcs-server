package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

@ApiModel(value="实名认证",description="实名认证属性说明")
public class UserIdentityEntity extends PageInfo {
    private static final long serialVersionUID = 8644113229475884175L;
    /**
     * 主键低
     */
    @ApiModelProperty(value="主键低",example="1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value="真实姓名")
    private String user_name;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value="身份证号码")
    private String id_card;

    /**
     * 认证状态 状态 
             1  同意
             2  等待审核
             3 拒绝
     */
    @ApiModelProperty(value="认证状态 状态 1  同意 2  等待审核 3 拒绝",example="1")
    private Integer status;

    /**
     * 身份证正面
     */
    @ApiModelProperty(value="身份证正面")
    private String id_frount;

    /**
     * 身份证背面
     */
    @ApiModelProperty(value="身份证背面")
    private String id_back;

    /**
     * 手持身份证
     */
    @ApiModelProperty(value="手持身份证")
    private String id_onhand;

    /**
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date apply_time;

    /**
     * 拒绝理由
     */
    @ApiModelProperty(value="拒绝理由")
    private String refuse_contect;

    public String getRefuse_contect() {
        return refuse_contect;
    }

    public void setRefuse_contect(String refuse_contect) {
        this.refuse_contect = refuse_contect;
    }

    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
    private Date update_time;

    /**
     * 是否付费
     */
    @ApiModelProperty(value="是否付费",example="false")
    private Boolean is_pay;

    /**
     * 国籍
     */
    @ApiModelProperty(value="国籍")
    private String nationality;
    /**
     * 申请开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="开始时间")
    private Date start_time;
    /**
     * 申请结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value="结束时间")
    private Date end_time;

    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nick_name;

    @ApiModelProperty(value="是否教付宝",example="false")
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

    /**
     * 主键低
     * @return id 主键低
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键低
     * @param id 主键低
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
     * 真实姓名
     * @return user_name 真实姓名
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * 真实姓名
     * @param user_name 真实姓名
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    /**
     * 身份证号码
     * @return id_card 身份证号码
     */
    public String getId_card() {
        return id_card;
    }

    /**
     * 身份证号码
     * @param id_card 身份证号码
     */
    public void setId_card(String id_card) {
        this.id_card = id_card == null ? null : id_card.trim();
    }

    /**
     * 认证状态 状态 
             1  同意
             2  等待审核
             3 拒绝
     * @return status 认证状态 状态 
             1  同意
             2  等待审核
             3 拒绝
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 认证状态 状态 
             1  同意
             2  等待审核
             3 拒绝
     * @param status 认证状态 状态 
             1  同意
             2  等待审核
             3 拒绝
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 身份证正面
     * @return id_frount 身份证正面
     */
    public String getId_frount() {
        return id_frount;
    }

    /**
     * 身份证正面
     * @param id_frount 身份证正面
     */
    public void setId_frount(String id_frount) {
        this.id_frount = id_frount == null ? null : id_frount.trim();
    }

    /**
     * 身份证背面
     * @return id_back 身份证背面
     */
    public String getId_back() {
        return id_back;
    }

    /**
     * 身份证背面
     * @param id_back 身份证背面
     */
    public void setId_back(String id_back) {
        this.id_back = id_back == null ? null : id_back.trim();
    }

    /**
     * 手持身份证
     * @return id_onhand 手持身份证
     */
    public String getId_onhand() {
        return id_onhand;
    }

    /**
     * 手持身份证
     * @param id_onhand 手持身份证
     */
    public void setId_onhand(String id_onhand) {
        this.id_onhand = id_onhand == null ? null : id_onhand.trim();
    }

    /**
     * 申请时间
     * @return apply_time 申请时间
     */
    public Date getApply_time() {
        return apply_time;
    }

    /**
     * 申请时间
     * @param apply_time 申请时间
     */
    public void setApply_time(Date apply_time) {
        this.apply_time = apply_time;
    }

    /**
     * 审核时间
     * @return update_time 审核时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 审核时间
     * @param update_time 审核时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 是否付费
     * @return is_pay 是否付费
     */
    public Boolean getIs_pay() {
        return is_pay;
    }

    /**
     * 是否付费
     * @param is_pay 是否付费
     */
    public void setIs_pay(Boolean is_pay) {
        this.is_pay = is_pay;
    }

    /**
     * 国籍
     * @return nationality 国籍
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * 国籍
     * @param nationality 国籍
     */
    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }
}