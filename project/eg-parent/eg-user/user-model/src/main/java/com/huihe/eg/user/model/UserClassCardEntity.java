package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value="用户购买直播课程学习卡记录",description="用户购买直播课程学习卡记录属性说明")
public class UserClassCardEntity extends PageInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

    /**
     * 类别 minute分钟/curriculum课程
     */
    @ApiModelProperty(value="类别 minute分钟/curriculum课程,cat_coin后台添加猫币类型")
    private String type;

    /**
     * 1 过期 2 可用 3 取消
     */
    @ApiModelProperty(value="1 过期 2 可用 3 取消",example="1")
    private Integer status;

    /**
     * 帐号
     */
    @ApiModelProperty(value="帐号",example="1")
    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    /**
     * 课程数
     */
    @ApiModelProperty(value="课程数",example="1")
    private Integer curriculum_num;

    /**
     * 分钟数
     */
    @ApiModelProperty(value="分钟数",example="1")
    private Integer minute_num;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",example="1")
    private Long user_id;

    @ApiModelProperty(value = "map数据")
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 过期时间
     */
    @ApiModelProperty(value="过期时间")
    private Date expire_time;

    /**
     * 是否默认使用
     */
    @ApiModelProperty(value="是否默认使用",example="false")
    private Boolean default_use;
    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 帐号
     */
    @ApiModelProperty(value="帐号")
    private String login_name;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nick_name;

    /**
     * 帐号集合
     */
    @ApiModelProperty(value="帐号集合")
    private List<Long> loginIds;

    /**
     * 帐号集合
     */
    @ApiModelProperty(value="帐号集合")
    private List<Long> nickNameIds;

    /**
     * 帐号集合
     */
    @ApiModelProperty(value="帐号集合")
    private Boolean is_experience;

    public Boolean getIs_experience() {
        return is_experience;
    }

    public void setIs_experience(Boolean is_experience) {
        this.is_experience = is_experience;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public List<Long> getNickNameIds() {
        return nickNameIds;
    }

    public void setNickNameIds(List<Long> nickNameIds) {
        this.nickNameIds = nickNameIds;
    }

    public List<Long> getLoginIds() {
        return loginIds;
    }

    public void setLoginIds(List<Long> loginIds) {
        this.loginIds = loginIds;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * 分钟数
     */
    @ApiModelProperty(value="猫币数",example="1")
    private BigDecimal cat_coin;

    public BigDecimal getCat_coin() {
        return cat_coin;
    }

    public void setCat_coin(BigDecimal cat_coin) {
        this.cat_coin = cat_coin;
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
     * 类别 minute分钟/curriculum课程
     * @return type 类别 minute分钟/curriculum课程
     */
    public String getType() {
        return type;
    }

    /**
     * 类别 minute分钟/curriculum课程
     * @param type 类别 minute分钟/curriculum课程
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 1 过期 2 可用 3 取消
     * @return status 1 过期 2 可用 3 取消
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 过期 2 可用 3 取消
     * @param status 1 过期 2 可用 3 取消
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 课程数
     * @return curriculum_num 课程数
     */
    public Integer getCurriculum_num() {
        return curriculum_num;
    }

    /**
     * 课程数
     * @param curriculum_num 课程数
     */
    public void setCurriculum_num(Integer curriculum_num) {
        this.curriculum_num = curriculum_num;
    }

    /**
     * 分钟数
     * @return minute_num 分钟数
     */
    public Integer getMinute_num() {
        return minute_num;
    }

    /**
     * 分钟数
     * @param minute_num 分钟数
     */
    public void setMinute_num(Integer minute_num) {
        this.minute_num = minute_num;
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
     * 过期时间
     * @return expire_time 过期时间
     */
    public Date getExpire_time() {
        return expire_time;
    }

    /**
     * 过期时间
     * @param expire_time 过期时间
     */
    public void setExpire_time(Date expire_time) {
        this.expire_time = expire_time;
    }

    /**
     * 是否默认使用
     * @return default_use 是否默认使用
     */
    public Boolean getDefault_use() {
        return default_use;
    }

    /**
     * 是否默认使用
     * @param default_use 是否默认使用
     */
    public void setDefault_use(Boolean default_use) {
        this.default_use = default_use;
    }
}