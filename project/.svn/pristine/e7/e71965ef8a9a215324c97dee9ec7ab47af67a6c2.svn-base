package com.huihe.eg.mall.service.impl.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cy.framework.service.dao.lock.DistributedLockService;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.json.JsonUtilFastjson;
import com.cy.framework.util.qrcode.QRCodeUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.safe.MD5Util;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.mall.model.RechargeRecordEntity;
import com.huihe.eg.mall.model.UserLoginEntity;
import com.huihe.eg.mall.model.pay.AliPayParam;
import com.huihe.eg.mall.model.pay.WxH5PayParam;
import com.huihe.eg.mall.model.pay.WxPayParam;
import com.huihe.eg.mall.mybatis.dao.UserInfoMapper;
import com.huihe.eg.mall.mybatis.dao.UserLoginMapper;
import com.huihe.eg.mall.service.dao.RechargeRecordService;
import com.huihe.eg.mall.service.dao.pay.AliPayService;
import com.huihe.eg.mall.service.dao.pay.PayService;
import com.huihe.eg.mall.service.dao.pay.WxPayService;
import com.huihe.eg.mall.service.impl.code.SourceCode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 15:42
 */
@Service
public class PayServiceImpl implements PayService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private AliPayService aliPayService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private WxPayParam wxPayParam;
    @Resource
    private AliPayParam aliPayParam;
    @Resource
    private RechargeRecordService rechargeRecordService;
    @Resource
    private DistributedLockService lockService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserLoginMapper userMapper;

    /**
     * 支付宝支付
     *
     * @param entity
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional
    public String aliPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) throws DataException {
        String string = null;
        System.out.println(JSONUtils.obj2Json(entity));
        try {
            String title = getTitle(entity.getRcharge_type());
            String flowing_no = CommonUtils.generateFlowingCode();
            entity.setFlowing_no(flowing_no);
            ResultParam resultParam = rechargeRecordService.insert(entity, request, response);
            if (resultParam.getCode() != 0) {
                throw new Exception("创建订单失败");
            }
            switch (entity.getSource()) {
                case "ios":
                    string = aliPayService.app(flowing_no, "订单-" + title, null, entity.getAmount());
                    break;
                case "android":
                    string = aliPayService.app(flowing_no, "订单-" + title, null, entity.getAmount());
                    break;
                case "mobile":
                    string = aliPayService.wap(flowing_no, "订单-" + title, null, entity.getAmount());
                    break;
                case "pc":
                    string = aliPayService.pc(flowing_no, "订单-" + title, null, entity.getAmount());
                    break;
                case "exchange":
                    string = aliPayService.appExchange(flowing_no, entity.getAmount(), entity.getRcharge_account());
                    break;
                default:
                    throw new Exception("支付来源不存在");
            }
            logger.info("aliPayContent  ：  ", string);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataException(e.hashCode(), e.getMessage());
        }
        return string;
    }

    /**
     * 支付宝回调
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public String aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            JSONObject jsonObject = JSONObject.fromObject(params);
            logger.warn("alipay notify data is:" + jsonObject.toString());
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayParam.getAlipay_public_key(), aliPayParam.getCharset(), aliPayParam.getSign_type());
            //调用SDK验证签名
            if (signVerified) {//验证成功
                //商户订单号
                String out_trade_no = jsonObject.getString("out_trade_no");
                //支付宝交易号
                String trade_no = jsonObject.getString("trade_no");
                //交易状态
                String trade_status = jsonObject.getString("trade_status");

                if (StringUtil.isEmpty(trade_status)) {
                    throw new Exception("alipay trade_status result is null");
                } else {
                    logger.info("alipay trade_status : " + trade_status);
                }
                if (!trade_status.toUpperCase().equals("TRADE_SUCCESS")) {
                    throw new Exception(
                            "alipay trade_status result is:" + trade_status + ",is not success");
                }
                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                rechargeRecordEntity.setFlowing_no(out_trade_no);
                List<RechargeRecordEntity> entities = rechargeRecordService.query(rechargeRecordEntity, request, response);
                if (entities != null && entities.size() > 0) {
                    rechargeRecordEntity.setId(entities.get(0).getId());
                    rechargeRecordEntity.setTrans_no(trade_no);
                    rechargeRecordEntity.setFinished(true);
                    rechargeRecordEntity.setFinished_time(new Date());
                    rechargeRecordEntity.setStatus(2);
                    rechargeRecordEntity.setTrans_no(trade_no);
                    ResultParam resultParam = rechargeRecordService.update(rechargeRecordEntity, request, response);
                    if (resultParam.getCode() == 0) {
                        //回调处理
                    }
                } else {
                    logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                    return "failure";
                }
            } else {//验证失败
                logger.warn("alipay notify sign is false");
                return "failure";
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return "failure";
        }
        return "success";
    }

    /**
     * 微信支付
     * @param entity
     * @param request
     * @param response
     * @return
     */
   /* @Override
    public WXPayConfig wxPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        WXPayConfig wxPayConfig = wxPayService.getWxPayConfig(1);
        return wxPayConfig;
    }*/

    /**
     * 微信回调
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        String code = "SUCCESS";
        String message = "成功";
        String key = null;
        String lockName = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String string = null;
            BufferedReader reader = request.getReader();
            while ((string = reader.readLine()) != null) {
                stringBuilder.append(string);
            }
            if (stringBuilder.length() <= 0) {
                throw new Exception("数据为空");
            }
            logger.warn("微信通知:" + stringBuilder.toString());
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(stringBuilder.toString());
            if (!notifyMap.get("return_code").toLowerCase().equals("SUCCESS".toLowerCase())) {
                logger.warn(notifyMap.get("return_msg"));
            }
            lockName = "notifyWx" + notifyMap.get("out_trade_no");
            key = lockService.withLock(lockName, 1500, 1800);
//            RechargeRecordEntity payOrderEntity = (RechargeRecordEntity) orderInfoService.findById(notifyMap.get("out_trade_no"), null);
//            Integer integer = getType(payOrderEntity.getSource());
            WXPayConfig wxPayConfig = null;
            /*if (integer.compareTo(1) == 0) {
                wxPayConfig = getWxPay();
            } else {
                wxPayConfig = wxPayConfigService.getWxPayConfig(integer);
            }*/
            WXPay wxpay = new WXPay(wxPayConfig);
            /*if (payOrderEntity == null) {
                throw new Exception("订单不存在");
            }
            if (payOrderEntity.getStatus().compareTo(2) == 0) {
                throw new Exception("订单已经支付成功");
            }*/
            logger.warn("xmltopMap is :" + JsonUtilFastjson.toJSONString(notifyMap));
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                rechargeRecordEntity.setFlowing_no(notifyMap.get("transaction_id"));
                List<RechargeRecordEntity> entities = rechargeRecordService.query(rechargeRecordEntity, request, response);
                if (entities != null && entities.size() > 0) {
                    rechargeRecordEntity.setId(entities.get(0).getId());
                    rechargeRecordEntity.setTrans_no("");
                    rechargeRecordEntity.setFinished(true);
                    rechargeRecordEntity.setFinished_time(new Date());
                    rechargeRecordEntity.setStatus(2);
                    ResultParam resultParam = rechargeRecordService.update(rechargeRecordEntity, request, response);
                    if (resultParam.getCode() == 0) {
                        //回调处理
                    } else {
                        logger.warn("订单修改错误" + rechargeRecordEntity.getTrans_no());
                    }
                }
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                logger.warn("验签错误");
                code = "FAIL";
                message = "签名错误";
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            code = "FAIL";
            message = "数据错误";
        } finally {
            lockService.releaseLock(lockName, key);
        }
        /*
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
//            printWriter.write(wxPayConfigService.returnXml(code, message));
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            lockService.releaseLock(lockName, key);
        }

         */
        return null;
    }

    public String getTitle(String type) {
        String title = "";
        switch (type) {
            case "member":
                title = "会员充值";
                break;
            case "authentication":
                title = "实名认证充值";
                break;
            case "account":
                title = "账户充值";
                break;
            case "experience_volume":
                title = "体验卷";
                break;
        }
        return title;
    }

    @Override
    @Transactional
    public Map<String, String> wxPay(RechargeRecordEntity payOrderEntity, HttpServletRequest request, HttpServletResponse response) {
        String ipAddr = getIpAddr2(request);
        String flowing_no = CommonUtils.generateFlowingCode();
        payOrderEntity.setFlowing_no(flowing_no);
        Integer integer = getType(payOrderEntity.getSource());
        WXPayConfig wxPayConfig = null;
        if (integer.compareTo(1) == 0) {
            wxPayConfig = wxPayService.getWxPayConfig(integer);
        } else {
            wxPayConfig = wxPayService.getWxPayConfig(integer);
        }
        ResultParam resultParam = rechargeRecordService.insert(payOrderEntity, request, response);
        if (resultParam.getCode() != 0) {

        }

        WXPay wxpay = new WXPay(wxPayConfig);
        Map<String, String> data = new HashMap<>();
        data.put("body", "购买商品");
        data.put("out_trade_no", payOrderEntity.getFlowing_no());
//        data.put("fee_type", "CNY");
        data.put("total_fee", payOrderEntity.getAmount().multiply(BigDecimal.valueOf(100)).toBigInteger().toString());
        data.put("spbill_create_ip", ipAddr);
        data.put("notify_url", wxPayParam.getNotify_url());
        data.put("trade_type", SourceCode.valueOf(payOrderEntity.getSource()).getDesc());
        if (data.get("trade_type").equalsIgnoreCase("JSAPI")) {
            com.alibaba.fastjson.JSONObject string = wxPayService.getSessionKeyOropenid(payOrderEntity.getRcharge_valid());
            Map<String, Object> map = JSONUtils.obj2Map(string, null);
            //System.out.println(map.toString());
            data.put("openid", map.get("openid").toString());
            UserLoginEntity entity = new UserLoginEntity();
            entity.setUser_id(payOrderEntity.getUser_id());
            entity.setWx_openid(map.get("openid").toString());
            userMapper.updateByPrimaryKeySelective(entity);
        }
        try {
            data.put("sign", WXPayUtil.generateSignature(data, wxPayConfig.getKey(), WXPayConstants.SignType.MD5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> parameterMap2 = null;
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            logger.warn("wx unifiedOrder request is :" + JsonUtilFastjson.toJSONString(data));
            logger.warn("wx unifiedOrder result is :" + JsonUtilFastjson.toJSONString(resp));
            if (!resp.get("return_code").equalsIgnoreCase("SUCCESS")) {
                logger.info(JSONUtils.obj2Json(resp).toString());
                throw new DataException(null);
//                throw new DataException(resp.get("return_msg"));
            }
            parameterMap2 = new HashMap<>();
            //parameterMap2.put("prepayid", resp.get("prepay_id"));
            //parameterMap2.put("partnerid", resp.get("mch_id"));
            parameterMap2.put("appId", resp.get("appid"));
            parameterMap2.put("package", (payOrderEntity.getSource().equals("sr") ? "prepay_id=" + resp.get("prepay_id") : "Sign=WXPay"));
            parameterMap2.put("nonceStr", WXPayUtil.generateNonceStr());
            parameterMap2.put("signType", "MD5");
            parameterMap2.put("timeStamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
            if (data.get("trade_type").equalsIgnoreCase("NATIVE") && resp.containsKey("code_url")) {
                parameterMap2.put("qrcode", QRCodeUtil.createImageBase642(resp.get("code_url"), null, true));
            }
            parameterMap2.put("paySign", WXPayUtil.generateSignature(parameterMap2, wxPayConfig.getKey(), WXPayConstants.SignType.MD5));
            logger.warn("wx result data is :" + JsonUtilFastjson.toJSONString(parameterMap2));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataException(null);
//            throw new DataException(e.getMessage() == null ? "调用微信错误" : e.getMessage());
        }
        return parameterMap2;
    }


    private Integer getType(String source) {
        Integer type = null;
        if ("pc,mobile".contains(source)) {
            type = 1;
        } else if ("sr".contains(source)) {
            type = 2;
        } else if ("android,ios".contains(source)) {
            type = 3;
        }
        return type;
    }

    /**
     * 微信h5支付
     * zwx
     * 2019年5月27日13:56:01
     *
     * @param payOrderEntity
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional
    public WxH5PayParam wxH5Pay(RechargeRecordEntity payOrderEntity, HttpServletRequest request, HttpServletResponse response) {
        String ipAddr = getIpAddr2(request);
        String flowing_no = CommonUtils.generateFlowingCode();
        payOrderEntity.setFlowing_no(flowing_no);
        WxH5PayParam param = new WxH5PayParam();
        ResultParam resultParam = rechargeRecordService.insert(payOrderEntity, request, response);
        WxH5PayParam rest = null;
        if (resultParam != null && resultParam.getCode() == 0) {
            param.setAppid(wxPayParam.getAppId());
            param.setMch_id(wxPayParam.getMachId());
            param.setDevice_info("WEB");
            String randoms = CommonUtils.generateRandom(30, false, false, false,
                    false);
            param.setNonce_str(randoms);
            param.setBody("外教超市充值");
            param.setOut_trade_no(flowing_no);
            BigDecimal num1 = new BigDecimal(100);
            param.setTotal_fee(((payOrderEntity.getAmount().multiply(num1)).intValue())
                    + "");
            param.setSpbill_create_ip(ipAddr);
            param.setNotify_url(wxPayParam.getNotify_url());
            param.setTrade_type("MWEB");
            Map<String, Object> scene = new HashMap<>();
            Map<String, Object> info = new HashMap<>();
            info.put("type", "Wap");
            info.put("wap_url", "外教超市");
            info.put("package_name", "外教超市");
            scene.put("h5_info", info);
            param.setScene_info(scene.toString());
            Map<String, Object> map = JSONUtils.obj2Map(param, null);
            String signContent = CommonUtils.signContentGen(map, null);
            signContent += "&key=" + wxPayParam.getKey();
            String sign = MD5Util.GetMD5Code(signContent);
            param.setSign(sign);
            try {
                String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                String ret = HttpsClientRequest.xmlPost(url, param);
                rest = CommonUtils.getObjectFromXML(ret, WxH5PayParam.class);
                String[] except = {"sign"};
                map = JSONUtils.obj2Map(rest, null);
                signContent = CommonUtils.signContentGen(map, except);
                signContent += "&key=" + wxPayParam.getKey();
                sign = MD5Util.GetMD5Code(signContent);
                map.put("sign", sign);
                if (!sign.equalsIgnoreCase(rest.getSign())) {
                    logger.warn("Signature is not correct.");
                    rest = null;
                }
                map = JSONUtils.obj2Map(rest, null);
                signContent = CommonUtils.signContentGen(map, except);
                signContent += "&key=" + wxPayParam.getKey();
                sign = MD5Util.GetMD5Code(signContent);
                if (!sign.equalsIgnoreCase(rest.getSign())) {
                    logger.warn("Signature is not correct.");
                    rest = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("PayServiceImpl    wxH5Pay ");
            }
        }
        return rest;
    }

    /**
     * 退款
     *
     * @return
     * @throws AlipayApiException
     */
    public String appRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        String string = null;
        try {
            string = aliPayService.appRefund(entity);
        } catch (AlipayApiException e) {
            throw new DataException(e.hashCode(), e.getMessage());
        }
        return string;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     * <p>
     * 获取终端ip(高配版)
     *
     * @return ip
     */
    private String getIpAddr2(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }
}
