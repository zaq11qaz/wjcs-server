package com.huihe.eg.comm;

public enum  MasterEum {

    master_40001(40001, "剩余时间不足"),master_40002(40002, "主键id未传"),
    master_40003(40003, "课程时间有冲突，请重试"),master_40004(40004, "您的课程时间不足,请前往购买"),
    master_40005(40005, "时区未获取"),master_40006(40006, "您未购买相应学习卡"),
    master_40007(40007, "课程不存在"),master_40008(40008, "预约失败"),
    master_40009(40009, "课程已结束"),master_40010(40010, "您的课程尚未开始,请稍后再试"),
    master_40011(40011, "已过可修改时间,无法删除"),master_40012(40012, "您尚未选择学习卡"),
    master_40013(40013, "该课程预约人数已达上限"),master_40014(40014, "外教身份已被冻结，请联系客服"),
    master_40015(40015,"小班课无法修改"),master_40016(40016,"今日特约课程达到上限"),
    master_40017(40017,"您还不是特约讲师"),master_40018(40018,"未购买专属课程无法预约"),
    master_40019(40019,"预约人数已满无法取消预约"),master_40020(40020,"请先成为助学师"),
    master_40021(40021,"您有未支付课程订单，请先结算"), master_40022(40022,"您已经预约过了"),
    master_40023(40023,"已经上过该课程无法取消预约，请联系客服取消"),master_40024(40024,"课程已总结过"),
    master_40025(40025,"暂无用户预约，无法写总结"),master_40026(40026,"该课程已排过"),
    master_40027(40027,"创建课程失败"),master_40028(40028,"主班级无法被合并"),
    master_40029(40029,"课程不存在,请稍后重试")



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
    public static MasterEum getByCode(String code) {
        for (MasterEum resultCode : values()) {
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
    MasterEum(Integer code, String desc) {
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
