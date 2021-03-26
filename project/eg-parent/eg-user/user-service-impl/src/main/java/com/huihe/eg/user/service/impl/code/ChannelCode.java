package com.huihe.eg.user.service.impl.code;

public enum ChannelCode {
    wxpay("wxpay", "微信支付"), alipay("alipay", "支付宝");
    /**
     * 结果码
     */
    private String code;

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
    public static ChannelCode getByCode(String code) {
        for (ChannelCode resultCode : values()) {
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
    ChannelCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
}
