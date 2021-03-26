package com.huihe.eg.message.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="用户消息列表",description="用户消息列表属性说明")
public class MessageUserEntity extends PageInfo {
    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键ID",example="1")
    private Long id;

    /**
     * 发送者id
     */
    @ApiModelProperty(value="发送者id",example="1")
    private Long send_id;

    /**
     * 接收者id
     */
    @ApiModelProperty(value="接收者id",example="1")
    private Long target_id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String message_type;

    /**
     * 列表的key
     */
    @ApiModelProperty(value="列表的key")
    private String message_key;

    /**
     * 状态1可用2不可用
     */
    @ApiModelProperty(value="状态1可用2不可用",example="1")
    private Integer status;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date update_time;

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
     * 发送者id
     * @return send_id 发送者id
     */
    public Long getSend_id() {
        return send_id;
    }

    /**
     * 发送者id
     * @param send_id 发送者id
     */
    public void setSend_id(Long send_id) {
        this.send_id = send_id;
    }

    /**
     * 接收者id
     * @return target_id 接收者id
     */
    public Long getTarget_id() {
        return target_id;
    }

    /**
     * 接收者id
     * @param target_id 接收者id
     */
    public void setTarget_id(Long target_id) {
        this.target_id = target_id;
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
     * 类型
     * @return message_type 类型
     */
    public String getMessage_type() {
        return message_type;
    }

    /**
     * 类型
     * @param message_type 类型
     */
    public void setMessage_type(String message_type) {
        this.message_type = message_type == null ? null : message_type.trim();
    }

    /**
     * 列表的key
     * @return message_key 列表的key
     */
    public String getMessage_key() {
        return message_key;
    }

    /**
     * 列表的key
     * @param message_key 列表的key
     */
    public void setMessage_key(String message_key) {
        this.message_key = message_key == null ? null : message_key.trim();
    }

    /**
     * 状态1可用2不可用
     * @return status 状态1可用2不可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态1可用2不可用
     * @param status 状态1可用2不可用
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
}