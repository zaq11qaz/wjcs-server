package com.huihe.eg.comm;

public enum NewsEum {

    news_80000(8000, "主键id未传"), news_80001(80001, "用户id未传"),
    news_80002(80002, "操作用户id未传"),news_80003(80003, "好奇id未传"),
    news_80004(80004, "请选择封面"),news_80005(80005, "请上传内容图片"),
    news_80006(80006, "回应id未传"), news_80007(80007, "动态id未传"),
    news_80008(80008, "操作过于频繁，请稍后再试"),news_80009(80009, "正在审核中,审核完毕后可见")
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
    public static NewsEum getByCode(String code) {
        for (NewsEum resultCode : values()) {
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
    NewsEum(Integer code, String desc) {
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
