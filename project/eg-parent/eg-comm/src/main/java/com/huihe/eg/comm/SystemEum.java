package com.huihe.eg.comm;

public enum SystemEum {
    system_20000(2000, "请输入风险名称"), system_20001(20001, "请传入规则ID"),
    system_20002(20002, "请输入"), system_20003(20003, "请输入昵称"),
    system_20004(20004, "请传入用户ID"), system_20005(20005, "请选择角色"),
    system_20006(20006, "账号已存在"), system_20007(20007, "规则不存在"),
    system_20008(20008, "系统不支持");
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
    public static SystemEum getByCode(String code) {
        for (SystemEum resultCode : values()) {
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
    SystemEum(Integer code, String desc) {
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
