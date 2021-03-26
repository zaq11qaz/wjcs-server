package com.huihe.eg.comm;

public enum ManagerEum {

    manager_9000(9000, "菜单名称未传"), manager_90001(90001, "请输入账号"),
    manager_90002(90002, "请输入密码"), manager_90003(90003, "请输入昵称"),
    manager_90004(90004, "请传入用户ID"), manager_90005(90005, "请选择角色"),
    manager_90006(90006, "账号已存在"), manager_90007(90007, "角色不存在"),
    manager_90008(90008, "菜单ID或按钮ID至少传一项"),manager_90009(90009, "菜单不存在"),
    manager_900010(900010, "按钮不存在"),manager_900011(900011, "类型不存在");
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
    public static ManagerEum getByCode(String code) {
        for (ManagerEum resultCode : values()) {
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
    private ManagerEum(Integer code, String desc) {
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
