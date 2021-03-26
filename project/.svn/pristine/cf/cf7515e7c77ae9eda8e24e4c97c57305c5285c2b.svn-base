package com.huihe.eg.comm;

public enum SumEum {
    sum_60000(6000, "商户名称未传"), sum_60001(60001, "商户ID未传"),
    sum_60002(60002, "查询的节点未传"), sum_60003(60003, "查询的节点名称"),
    sum_60004(60004, "风险等级ID未传"), sum_60005(60005, "风险等级名称未传"),
    sum_60006(60006, "手机号未传"), sum_60007(60007, "角色不存在"),
    sum_60008(60008, "设备号未传"), sum_60009(60009, "设备标识未传");
    /**
     * 结果码
     */
    private Integer code;

    /**
     * 结果描述
     */
    private String desc;

    /**
     * 获取结果码
     *
     * @param code 待查询code
     * @return 对应的结果码
     */
    public static SumEum getByCode(String code) {
        for (SumEum resultCode : values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
    }

    /**
     * @param code
     * @param desc
     */
    SumEum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
}
