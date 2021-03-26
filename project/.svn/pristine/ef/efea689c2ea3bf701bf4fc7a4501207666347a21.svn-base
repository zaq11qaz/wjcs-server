package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import com.huihe.eg.comm.elasticsearch.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;

@ApiModel(value="用户加群记录",description="用户加群记录属性说明")
public class MessageJoinGroupEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 群id
     */
    @ApiModelProperty(value="群id",example="1")
    private Long group_id;

    /**
     * 加群用户的id
     */
    @ApiModelProperty(value="加群用户的id",example="1")
    private Long user_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 状态 1.通过 2.待通过
     */
    @ApiModelProperty(value="状态 1.通过 2.待通过 3.正在连麦   4离开",example="1")
    private Integer status;

    /**
     * 用户群名称
     */
    @ApiModelProperty(value="用户群名称")
    private String remark_name;

    /**
     * 入群方式Apply（申请入群）；Invited（邀请入群）
     */
    @ApiModelProperty(value="入群方式Apply（申请入群）；Invited（邀请入群）")
    private String join_type;

    /**
     * 操作者成员
     */
    @ApiModelProperty(value="操作者成员")
    private String operator_account;

    /**
     *  成员离开方式：Kicked-被踢；Quit-主动退群。
     */
    @ApiModelProperty(value=" 成员离开方式：Kicked-被踢；Quit-主动退群。")
    private String exit_type;
    @ApiModelProperty(value="map")
    private Map<String,Object> map;

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


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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
     * 群id
     * @return group_id 群id
     */
    public Long getGroup_id() {
        return group_id;
    }

    /**
     * 群id
     * @param group_id 群id
     */
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    /**
     * 加群用户的id
     * @return user_id 加群用户的id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 加群用户的id
     * @param user_id 加群用户的id
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
     * 状态 1.通过 2.待通过
     * @return status 状态 1.通过 2.待通过
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 1.通过 2.待通过
     * @param status 状态 1.通过 2.待通过
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 用户群名称
     * @return remark_name 用户群名称
     */
    public String getRemark_name() {
        return remark_name;
    }

    /**
     * 用户群名称
     * @param remark_name 用户群名称
     */
    public void setRemark_name(String remark_name) {
        this.remark_name = remark_name == null ? null : remark_name.trim();
    }

    /**
     * 入群方式Apply（申请入群）；Invited（邀请入群）
     * @return join_type 入群方式Apply（申请入群）；Invited（邀请入群）
     */
    public String getJoin_type() {
        return join_type;
    }

    /**
     * 入群方式Apply（申请入群）；Invited（邀请入群）
     * @param join_type 入群方式Apply（申请入群）；Invited（邀请入群）
     */
    public void setJoin_type(String join_type) {
        this.join_type = join_type == null ? null : join_type.trim();
    }

    /**
     * 操作者成员
     * @return operator_account 操作者成员
     */
    public String getOperator_account() {
        return operator_account;
    }

    /**
     * 操作者成员
     * @param operator_account 操作者成员
     */
    public void setOperator_account(String operator_account) {
        this.operator_account = operator_account == null ? null : operator_account.trim();
    }

    /**
     *  成员离开方式：Kicked-被踢；Quit-主动退群。
     * @return exit_type  成员离开方式：Kicked-被踢；Quit-主动退群。
     */
    public String getExit_type() {
        return exit_type;
    }

    /**
     *  成员离开方式：Kicked-被踢；Quit-主动退群。
     * @param exit_type  成员离开方式：Kicked-被踢；Quit-主动退群。
     */
    public void setExit_type(String exit_type) {
        this.exit_type = exit_type == null ? null : exit_type.trim();
    }
}