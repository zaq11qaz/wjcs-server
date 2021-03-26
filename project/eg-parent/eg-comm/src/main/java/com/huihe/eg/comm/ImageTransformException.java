package com.huihe.eg.comm;

/**
        * 含义：图片转换异常
        * @author zhufei 2014-7-9 上午9:46:28
        */
public class ImageTransformException extends Exception {

    /**
     * 功能描述：图像缩略化处理的异常类
     * @author zhufei 2014-7-9 上午9:50:14
     */
    private static final long serialVersionUID = 3357343205238632841L;
    //异常消息
    private String errMsg = "";

    /**
     * 类描述：生成异常消息
     * @param errMsg 错误消息
     * @author zhufei 2014-7-25 下午9:14:22
     */
    public ImageTransformException(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     * 方法描述：获取异常消息
     * @return 异常消息
     * @author zhufei 2014-7-25 下午9:14:44
     */
    public String getMsg() {
        return "JpegToolException:" + this.errMsg;
    }
}

