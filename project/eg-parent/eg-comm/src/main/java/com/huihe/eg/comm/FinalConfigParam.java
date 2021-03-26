/**
 * @author zhufei 2014-7-10 下午6:31:34
 */
package com.huihe.eg.comm;

import java.math.BigDecimal;
import java.util.TimeZone;

/**
 * 含义：常量的定义
 *
 * @author zhufei 2014-7-10 下午6:31:34
 */
public class FinalConfigParam {
    public static final String DATE_FORMAT_STYLE = "yyyy-MM-dd"; // 日期的输出样式
    public static final String MONTH_DAY_FORMAT_STYLE = "MM-dd HH:mm:ss";// 当月日期输出样式
    public static final String DATETIME_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss"; // 日期的输出样式
    public static final String DATETIME_FORMAT_STYLE_SSS = "yyyy-MM-dd HH:mm:ss.SSS"; // 日期的输出样式
    public static final String DATETIME_FORMAT_STYLE_SS_SSS = "yyyyMMddHHmmssSSS"; // 日期的输出样式
    public static final String TIME_FORMAT_STYLE = "HH:mm:ss"; // 当天时间的输出样式
    public static final String TIME_FORMAT_SYSTLE = "HH:mm";// 时间输出
    public static final String KEY = "zjfxdzswyxgs";// 公司的唯一key
    public static final String USER_ID_KEY = "user_id_key";// 公司的唯一key
    public static final String SUCCESS = "SUCCESS";// 成功
    public static final String FAILURE = "failure";// 失败
    public static final String CONTROLLER = "controller";//controller
    public static final String ACTION = "action";// 方法
    public static final String QUEUE = "queue";// 队列
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+8");// 中国时区
    public static final int MIN = 111111;
    public static final int ACQUIRETIMEOUT = 5000;//分布式锁，请求五秒算超时
    public static final int TIMEOUT = 6500;//分布式锁，锁的失效时间4.5秒后释放
    public static final int MAX = 999999;
    public static final int USER_ID = 666;//用户id
    public static final int STORE_ID = 888;//商家id
    public static final int PRODUCT_ID = 68;//商品id
    public static final String ORDER_CODE = "GT";// 订单的键
    public static final String FLOWING_CODE = "FK";// 流水号
    public static final String PAY_CODE = "PT";// 流水号
    public static final String ORDER_KEY = "GK";// 组合订单的key 唯一
    public static final String FLOWING_KEY = "KT";// 组合订单的key 唯一
    public static final long EXPIRE = (60 * 60) * (24 * 15);// token失效的时间 默认是24*15  15天
    public static final int LUCKY_KEY = 1000000001;// 号码的初始值
    public static final String SERVER_NAME = "servername";// 服务器的服务名称
    // "localhost"
    public static final String SERVER_PORT = "8768";// 服务器端口
    public static final String SERVER_TYPE = "http:";// 服务的请求类型 "http"
    public static final String SERVER_PROJECT = "serverproj";// 项目工程名
    public static final String SERVER_URL = "www.curiousmore.com";// 服务器的证实路径
    public static final String SERVER_REQ_URL = "serverrequrl";// 服务器的证实路径
    public static final String SERVER_REQ_METHOD = "service";
    public static final String LOGIN_KEYYES = "1";// 已登录
    public static final String LOGIN_KEYNO = "2";// 未登录
    public static final String[] picType = {".jpg", ".jpeg", ".gif", ".bmp", ".png"};
    public static final String PICTURE_SERVER_ADDRESS ="http://img.curiousmore.com:8768/";
    // 百度地图AK
    public static final String BAIDUMAP_AK = "2d9636b0a6319c0f9c23033be48f4010";
    // 根据地址查询坐标信息
    public static final String BAIDUMAP_LOCATIONAPI = "http://api.map.baidu.com/geocoder/v2/?" + "ak=" + BAIDUMAP_AK
            + "&output=json&address=";
    // 根据坐标查询地址
    public static final String BAIDUMAP_DELOCATEAPI = "http://api.map.baidu.com/geocoder/v2/?" + "ak=" + BAIDUMAP_AK
            + "&output=json&pois=0&location=";
    public static final BigDecimal ACTIVITIESEARLN = new BigDecimal("0.85");
    public static final BigDecimal NOTACTIVITIESEARLN = new BigDecimal("0.95");

}
