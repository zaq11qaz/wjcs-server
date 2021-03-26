package com.huihe.eg.message.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * TIM应答包实体类
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
public class TIMResultEntity {
    @JsonProperty("ActionStatus")
    private String actionStatus;//请求处理的结果，OK 表示处理成功，FAIL 表示失败
    @JsonProperty("ErrorInfo")
    private String errorInfo;//错误信息
    @JsonProperty("ErrorCode")
    private int errorCode;// 0 为回调成功，1 为回调出错

    @JsonProperty("ResultItem")
    private List<ResultItem> resultItems;

    public List<ResultItem> getResultItems() {
        return resultItems;
    }

    public void setResultItems(List<ResultItem> resultItems) {
        this.resultItems = resultItems;
    }

    public TIMResultEntity(String actionStatus, String errorInfo, int errorCode){
        this.actionStatus=actionStatus;
        this.errorInfo=errorInfo;
        this.errorCode=errorCode;

    }
    public TIMResultEntity(){
        actionStatus="OK";
        errorCode=0;
        errorInfo="";
    }
    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
 @Override
 public String toString(){
        return "actionStatus:"+actionStatus+";errorInfo:"+errorInfo+";errorCode:"+errorCode;
 }


}
