package com.huihe.eg.comm.sum;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 队列统计使用
 */
public class QueueSum implements Serializable {
    private static final long serialVersionUID = 2302259907906135040L;
    /**
     * 查询高风险命中的次数
     */
    @ApiModelProperty(value = "查询高风险命中的次数", example = "1")
    private Long query_risk_high;

    /**
     * 命中中风险数量
     */
    @ApiModelProperty(value = "命中中风险数量", example = "1")
    private Long query_risk_middle;

    /**
     * 查询命中低风险的次数
     */
    @ApiModelProperty(value = "查询命中低风险的次数", example = "1")
    private Long query_risk_low;

    /**
     * 新增用户数
     */
    @ApiModelProperty(value = "新增用户数", example = "1")
    private Long query_new_add;

    /**
     * 查询实名认证的次数
     */
    @ApiModelProperty(value = "查询实名认证的次数", example = "1")
    private Long query_real_count;

    /**
     * 查询贷款审核的次数
     */
    @ApiModelProperty(value = "查询贷款审核的次数", example = "1")
    private Long query_loan_count;

    /**
     * 新增手机号码
     */
    @ApiModelProperty(value = "新增手机号码", example = "1")
    private Long query_add_mobile;

    /**
     * 商户ID
     */
    @ApiModelProperty(value = "商户ID", example = "1")
    private Long businessman_id;

    /**
     * 商户的名称
     */
    @ApiModelProperty(value = "商户的名称")
    private String businessman_name;

    /**
     * 节点
     */
    @ApiModelProperty(value = "节点")
    private String node;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String node_name;

    /**
     * 查询高风险命中的次数
     *
     * @return query_risk_high 查询高风险命中的次数
     */
    public Long getQuery_risk_high() {
        return query_risk_high;
    }

    /**
     * 查询高风险命中的次数
     *
     * @param query_risk_high 查询高风险命中的次数
     */
    public void setQuery_risk_high(Long query_risk_high) {
        this.query_risk_high = query_risk_high;
    }

    /**
     * 命中中风险数量
     *
     * @return query_risk_middle 命中中风险数量
     */
    public Long getQuery_risk_middle() {
        return query_risk_middle;
    }

    /**
     * 命中中风险数量
     *
     * @param query_risk_middle 命中中风险数量
     */
    public void setQuery_risk_middle(Long query_risk_middle) {
        this.query_risk_middle = query_risk_middle;
    }

    /**
     * 查询命中低风险的次数
     *
     * @return query_risk_low 查询命中低风险的次数
     */
    public Long getQuery_risk_low() {
        return query_risk_low;
    }

    /**
     * 查询命中低风险的次数
     *
     * @param query_risk_low 查询命中低风险的次数
     */
    public void setQuery_risk_low(Long query_risk_low) {
        this.query_risk_low = query_risk_low;
    }

    /**
     * 新增用户数
     *
     * @return query_new_add 新增用户数
     */
    public Long getQuery_new_add() {
        return query_new_add;
    }

    /**
     * 新增用户数
     *
     * @param query_new_add 新增用户数
     */
    public void setQuery_new_add(Long query_new_add) {
        this.query_new_add = query_new_add;
    }

    /**
     * 查询实名认证的次数
     *
     * @return query_real_count 查询实名认证的次数
     */
    public Long getQuery_real_count() {
        return query_real_count;
    }

    /**
     * 查询实名认证的次数
     *
     * @param query_real_count 查询实名认证的次数
     */
    public void setQuery_real_count(Long query_real_count) {
        this.query_real_count = query_real_count;
    }

    /**
     * 查询贷款审核的次数
     *
     * @return query_loan_count 查询贷款审核的次数
     */
    public Long getQuery_loan_count() {
        return query_loan_count;
    }

    /**
     * 查询贷款审核的次数
     *
     * @param query_loan_count 查询贷款审核的次数
     */
    public void setQuery_loan_count(Long query_loan_count) {
        this.query_loan_count = query_loan_count;
    }

    /**
     * 新增手机号码
     *
     * @return query_add_mobile 新增手机号码
     */
    public Long getQuery_add_mobile() {
        return query_add_mobile;
    }

    /**
     * 新增手机号码
     *
     * @param query_add_mobile 新增手机号码
     */
    public void setQuery_add_mobile(Long query_add_mobile) {
        this.query_add_mobile = query_add_mobile;
    }

    /**
     * 商户ID
     *
     * @return businessman_id 商户ID
     */
    public Long getBusinessman_id() {
        return businessman_id;
    }

    /**
     * 商户ID
     *
     * @param businessman_id 商户ID
     */
    public void setBusinessman_id(Long businessman_id) {
        this.businessman_id = businessman_id;
    }

    /**
     * 商户的名称
     *
     * @return businessman_name 商户的名称
     */
    public String getBusinessman_name() {
        return businessman_name;
    }

    /**
     * 商户的名称
     *
     * @param businessman_name 商户的名称
     */
    public void setBusinessman_name(String businessman_name) {
        this.businessman_name = businessman_name == null ? null : businessman_name.trim();
    }

    /**
     * 节点
     *
     * @return node 节点
     */
    public String getNode() {
        return node;
    }

    /**
     * 节点
     *
     * @param node 节点
     */
    public void setNode(String node) {
        this.node = node == null ? null : node.trim();
    }

    /**
     * 节点名称
     *
     * @return node_name 节点名称
     */
    public String getNode_name() {
        return node_name;
    }

    /**
     * 节点名称
     *
     * @param node_name 节点名称
     */
    public void setNode_name(String node_name) {
        this.node_name = node_name == null ? null : node_name.trim();
    }
}