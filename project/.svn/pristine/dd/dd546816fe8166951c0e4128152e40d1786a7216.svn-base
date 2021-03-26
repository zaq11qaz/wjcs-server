package com.huihe.eg.mall.service.impl.sms;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/17 13:29
 */
public class SmsSendRequest {
    /**
     * 微联账号
     */
    private String sname;
    /**
     * 微联密码
     */
    private String spwd;
    /**
     *产品编号,通过该参数标识业务类型
     */
    private String sprdid;
    /**
     *信息内容, UTF-8编码，通常为70汉字以内，支持长短信，长短信按67字/条计费
     */
    private String smsg;
    /**
     *接收号码间用英文半角逗号“,”隔开，触发产品一次只能提交一个,其他产品一次不能超过10万个号码
     */
    private String sdst;
    /**
     *企业代码（扩展号，默认赋值空字符串）
     */
    private String scorpid;

    public SmsSendRequest(String sname, String spwd, String sprdid, String smsg, String sdst, String scorpid) {
        super();
        this.sname = sname;
        this.spwd = spwd;
        this.sprdid = sprdid;
        this.smsg = smsg;
        this.sdst = sdst;
        this.scorpid = scorpid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpwd() {
        return spwd;
    }

    public void setSpwd(String spwd) {
        this.spwd = spwd;
    }

    public String getSprdid() {
        return sprdid;
    }

    public void setSprdid(String sprdid) {
        this.sprdid = sprdid;
    }

    public String getSmsg() {
        return smsg;
    }

    public void setSmsg(String smsg) {
        this.smsg = smsg;
    }

    public String getSdst() {
        return sdst;
    }

    public void setSdst(String sdst) {
        this.sdst = sdst;
    }

    public String getScorpid() {
        return scorpid;
    }

    public void setScorpid(String scorpid) {
        this.scorpid = scorpid;
    }
}
