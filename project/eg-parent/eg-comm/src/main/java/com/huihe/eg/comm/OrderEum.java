package com.huihe.eg.comm;

import org.springframework.boot.SpringApplication;

public enum OrderEum {
    order_70000(7000, "金额未传"), order_70001(70001, "金额最少0.01"),
    order_70002(70002, "查询的节点未传"), order_70003(70003, "查询的节点名称"),
    order_70004(70004, "风险等级ID未传"), order_70005(70005, "风险等级名称未传"),
    order_70006(70006, "手机号未传"), order_70007(70007, "角色不存在"),
    order_70008(70008, "余额不足"),order_70009(70009, "扣除失败"),
    order_70010(70010, "订单已存在"),order_70011(70011, "商品不存在"),
    order_70012(70012, "红包已领取"),order_70013(70013, "订单不存在"),
    order_70014(70014, "订单失败"),order_70015(70015, "未绑定微信，提现失败"),
    order_70016(70016, "购买失败,请联系客服"),order_70017(70017, "活动商品无法下架或修改"),
    order_70018(70018, "类型错误"),order_70019(70019, "您已经确认过了"),
    order_70020(70020, "时间非法"),order_70021(70021, "类型错误"),
    order_70022(70022, "该商品未参加单节课活动"),order_70023(70023, "您已经评论过了"),
    order_70024(70024, "该课程未参加拼团活动"),order_70025(70025, "该团仅全额购可参加"),
    order_70026(70026, "该团仅单节付可参加"), order_70027(70027, "该商品只能购买一次"),
    order_70028(70028, "所选课程非本机构"),order_70029(70029, "退款失败，请联系客服"),
    order_70030(70030, "未购买过所选优惠券"),order_70031(70031, "订单已退款"),
    order_70032(70032, "超过七天退款时间"), order_70033(70033, "非该机构新课，无法预约"),
    order_70034(70034, "等待机构确认新老客中"),order_70035(70035, "该课程未参加换课活动"),
    order_70036(70036, "该课程已经添加过"),order_70037(70037, "支付成功"),
    order_70038(70038, "优惠券不支持退款"),order_70039(70039, "提现失败"),
    order_70040(70040, "结算失败"),order_70041(70041, "该课程已经结算过"),
    order_70042(70042, "支付密码未设"),  order_70043(70043, "支付密码错误"),
    order_70044(70044, "最少选择两科"),order_70045(70045, "选择科目大于购买科目"),
    order_70046(70046, "多于设定购买科目数"),    order_70047(70047, "尚未登录"),
    order_70048(70048, "尚未开启秒杀"),order_70049(70049, "尚未登录"),
    order_70050(70050, "未购买无法分享"),order_70051(70051, "单次超过100上限"),

    ;


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
    public static OrderEum getByCode(String code) {
        for (OrderEum resultCode : values()) {
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
    OrderEum(Integer code, String desc) {
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
