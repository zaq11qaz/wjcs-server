package com.huihe.eg.comm;

public enum CouponEum {

    coupon_30001(30001,"优惠卷不存在"),coupon_30002(30002,"优惠卷已过期"),
    coupon_30003(30003,"优惠卷已被使用"),coupon_30004(30004,"领取失败"),
    coupon_30005(30005,"优惠卷已失效"), coupon_30006(30006,"请勿重复领取"),
    coupon_30007(30007,"请输入优惠卷"),coupon_30008(30008,"兑换码无效"),
    coupon_30009(30009,"此处无法使用该兑换码"), coupon_30010(30010,"您已购买过学习卡，无法领取");


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
    public static CouponEum getByCode(String code) {
        for (CouponEum resultCode : values()) {
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
    CouponEum(Integer code, String desc) {
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
