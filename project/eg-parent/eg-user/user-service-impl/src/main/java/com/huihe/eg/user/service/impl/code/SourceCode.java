package com.huihe.eg.user.service.impl.code;

public enum SourceCode{
    sr("sr", "JSAPI"), pc("pc", "NATIVE"), android("android", "APP"), ios("ios", "APP"), mobile("mobile", "MWEB")
    ,paccount("paccount","JSAPI"),teach_paypal("teach_paypal","JSAPI");
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
    public static SourceCode getByCode(Integer code) {
        for (SourceCode resultCode : values()) {
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
     SourceCode(String code, String desc) {
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
