package com.huihe.eg.message.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonInclude( JsonInclude.Include.NON_NULL)
public class ResultItem {
    @JsonProperty("To_Account")
    private  String to_Account;
    @JsonProperty("ResultCode")
    private int resultCode;
    @JsonProperty("ResultInfo")
    private String resultInfo;

    public String getTo_Account() {
        return to_Account;
    }
    public ResultItem(){
        to_Account="0";
        resultCode=0;
        resultInfo="";
    }
    public void setTo_Account(String to_Account) {
        this.to_Account = to_Account;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
