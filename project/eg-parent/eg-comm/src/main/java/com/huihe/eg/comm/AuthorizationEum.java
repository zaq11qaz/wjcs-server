package com.huihe.eg.comm;

public enum AuthorizationEum {
    authorization_30000(30000, "API未开放"),
    authorization_30001(30001, "API不存在"), authorization_30002(30002, "token未传"),
    authorization_30003(30003, "token失效或者不存在"), authorization_30004(30004, "没有权限"),
    authorization_30005(30005, "手机号格式错误"), authorization_30006(30006, "手机号不存在"),
    authorization_30007(30007, "密码错误"), authorization_30008(30008, "请输入手机号"),
    authorization_30009(30007, "请输入密码"), authorization_300010(300010, "请输入验证码"),
    authorization_300011(300011, "验证码已过期"), authorization_300012(300012, "验证码错误"),
    authorization_300013(300013, "余额不足"), authorization_300014(300014, "操作过于频繁,请稍后再试！"),
    authorization_300015(300015, "用户/商户不存在"),authorization_300016(300016, "账户不可用"),
    authorization_300017(300017, "认证API,请传入账号"),authorization_300018(300018, "认证API,请传入密码");
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
    public static AuthorizationEum getByCode(String code) {
        for (AuthorizationEum resultCode : values()) {
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
    private AuthorizationEum(Integer code, String desc) {
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
