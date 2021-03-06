package com.huihe.eg.user.service.impl.pay;

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
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.model.pay.AliPayParam;
import com.huihe.eg.user.model.pay.WxH5PayParam;
import com.huihe.eg.user.model.pay.WxPayParam;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;
import com.huihe.eg.user.service.dao.pay.AliPayService;
import com.huihe.eg.user.service.dao.pay.IosPayService;
import com.huihe.eg.user.service.dao.pay.PayService;
import com.huihe.eg.user.service.dao.pay.WxPayService;
import com.huihe.eg.user.service.impl.code.SourceCode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

import static com.huihe.eg.comm.util.GetIp.getIpAddr2;
import static com.huihe.eg.comm.util.StringXmlUtil.xml2ToMap;
import static java.math.BigDecimal.ROUND_DOWN;

/**
 * ????????????
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 15:42
 */
@Service
public class PayServiceImpl implements PayService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
    private IosPayService iosPayService;
    @Resource
    private DistributedLockService lockService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserOrderService orderService;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private CommodityCouponService commodityCouponService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ClassCardMapper classCardMapper;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private ClassCardService ClassCardService;
    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private MasterSetPriceDisplayMapper masterSetPriceDisplayMapper;
    @Resource
    private MasterSetPriceStreamMapper masterSetPriceStreamMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private UserStudyCardService userStudyCardService;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterMechanismService masterMechanismService;
    @Resource
    private MasterInfoService masterInfoService;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;
    @Resource
    private RechargeInfoMapper rechargeInfoMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private UserPointsMapper userPointsMapper;
    @Resource
    private BusinessActivityMapper businessActivityMapper;
    @Resource
    private BusinessActivityTypeMapper businessActivityTypeMapper;
    @Resource
    private UserGroupingService userGroupingService;
    @Resource
    private BusinessActivityTypeService businessActivityTypeService;


    /**
     * ???????????????
     *
     * @param entity
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional
    public synchronized String aliPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) throws DataException {
        String string = null;
        System.out.println(JSONUtils.obj2Json(entity));
        try {
            String title = this.getTitle(entity.getRcharge_type());
            String flowing_no = "";

            /*
            if (entity.getIs_experience() == null || !entity.getIs_experience()) {
                if (entity.getCoupon_id() != null && entity.getCoupon_id() != 0) {
                    BigDecimal b = commodityCouponService.getFinalAmount(entity);
                    entity.setAmount(b);
                }

             */
            ResultParam resultParam = ResultUtil.success();
            if (StringUtil.isEmpty(entity.getFlowing_no())) {
                flowing_no = CommonUtils.generateFlowingCode();
                entity.setFlowing_no(flowing_no);
                entity.setPay_type("ali");
                entity.setStatus(0);
                resultParam = rechargeRecordService.insert(entity, request, response);
            }

            if (resultParam.getCode() != 0) {
                throw new Exception("??????????????????");
            }
                /*
            } else {
                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                if (entity.getIs_one_time_payment() != null && !entity.getIs_one_time_payment()) {
                    rechargeRecordEntity.setAppointment_id(entity.getAppointment_id());
                    rechargeRecordEntity.setIs_one_time_payment(false);
                    rechargeRecordEntity.setCourse_num(1);
                } else {
                    rechargeRecordEntity.setIs_experience(entity.getIs_experience());
                    rechargeRecordEntity.setRcharge_type(entity.getRcharge_type());
                }

                rechargeRecordEntity.setPageSize(1);
                rechargeRecordEntity.setUser_id(entity.getUser_id());
                if (entity.getMaster_id() != null && entity.getMaster_id() != 0) {
                    rechargeRecordEntity.setMaster_id(entity.getMaster_id());
                }
                if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                    rechargeRecordEntity.setMechanism_id(entity.getMechanism_id());
                }
                List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
                if (list != null && list.size() > 0) {
                    rechargeRecordEntity = list.get(0);
                    flowing_no = rechargeRecordEntity.getFlowing_no();
                } else {
                    return string;
                }
            }

                 */
            switch (entity.getSource()) {
                case "ios":
                case "android":
                    string = aliPayService.app(flowing_no, "??????-" + title, null, entity.getAmount());
                    break;
                case "mobile":
                    string = aliPayService.wap(flowing_no, "??????-" + title, null, entity.getAmount());
                    break;
                case "pc":
                    string = aliPayService.pc(flowing_no, "??????-" + title, null, entity.getAmount());
                    break;
                case "exchange":
                    string = aliPayService.appExchange(flowing_no, entity.getAmount(), entity.getRcharge_account());
                    break;
                default:
                    throw new Exception("?????????????????????");
            }
            logger.info("aliPayContent  ???  ", string);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataException(e.hashCode(), e.getMessage());
        }
        return string;
    }

    @Override
    public ResultParam aliPayTradeRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        return aliPayService.aliPayTradeRefund(entity);
    }

    @Override
    public ResultParam wxPayRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        String ipAddr = getIpAddr2(request);
        String nonce_str = WXPayUtil.generateNonceStr();
        Integer integer = this.getType(entity.getSource());
        WXPayConfig wxPayConfig = wxPayService.getWxPayConfig(integer);
        ResultParam resultParam = ResultUtil.success();
        if (resultParam.getCode() != 0) {
            return resultParam;
        }
        WXPay wxpay = new WXPay(wxPayConfig);
        Map<String, String> data = new HashMap<>();
        data.put("appid", wxPayConfig.getAppID());
        data.put("mch_id", wxPayParam.getMachId());
        data.put("nonce_str", nonce_str);
        data.put("body", "????????????");
        data.put("out_trade_no", entity.getFlowing_no());
//        data.put("fee_type", "CNY");
        data.put("total_fee", entity.getAmount().multiply(BigDecimal.valueOf(100)).toBigInteger().toString());
        data.put("spbill_create_ip", ipAddr);
        data.put("notify_url", wxPayParam.getNotify_url());
        data.put("trade_type", SourceCode.valueOf(entity.getSource()).getDesc());

        try {
            data.put("sign", WXPayUtil.generateSignature(data, wxPayParam.getKey(), WXPayConstants.SignType.MD5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> parameterMap2 = Maps.newHashMap();
        try {
            logger.warn("wx unifiedOrder request is :" + JsonUtilFastjson.toJSONString(data));
            Map<String, String> resp = wxpay.unifiedOrder(data);
            logger.warn("wx unifiedOrder result is :" + JsonUtilFastjson.toJSONString(resp));
            if (!"SUCCESS".equalsIgnoreCase(resp.get("return_code"))) {
                logger.info(JSONUtils.obj2Json(resp).toString());
                throw new DataException(null);
//                throw new DataException(resp.get("return_msg"));
            }
            parameterMap2.put("sign", data.get("sign"));
            parameterMap2.put("appid", resp.get("appid"));
            parameterMap2.put("mch_id", resp.get("mch_id"));
            parameterMap2.put("noncestr", WXPayUtil.generateNonceStr());
            parameterMap2.put("out_refund_no", WXPayUtil.generateNonceStr());
            parameterMap2.put("refund_fee", entity.getAmount().multiply(new BigDecimal("100")).toString());
            parameterMap2.put("total_fee", entity.getAmount().multiply(new BigDecimal("100")).toString());
            parameterMap2.put("refund_desc", "?????????????????????");
            //parameterMap2.put("signType", "MD5");
            parameterMap2.put("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
//            String s = WXPayUtil.mapToXml(parameterMap2);
            try {
                String s1 = HttpsClientRequest.xmlPost("https://api.mch.weixin.qq.com/secapi/pay/refund", parameterMap2);
                Map<String, String> resultMap = WXPayUtil.xmlToMap(s1);
                //???????????????xml
                if ("SUCCESS".equals(resultMap.get("return_code")) && "OK".equals(resultMap.get("return_msg "))) {
                    return ResultUtil.success();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataException(null);
//            throw new DataException(e.getMessage() == null ? "??????????????????" : e.getMessage());

        }
        return ResultUtil.error(OrderEum.order_70029.getCode(), OrderEum.order_70029.getDesc());
    }

    /**
     * ???????????????
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public String aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = getParam(request);
            JSONObject jsonObject = JSONObject.fromObject(params);
            logger.warn("alipay notify data is:" + jsonObject.toString());
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayParam.getAlipay_public_key(), aliPayParam.getCharset(), aliPayParam.getSign_type());
            //??????SDK????????????
            if (signVerified) {//????????????
                String out_trade_no;
                //???????????????
                out_trade_no = jsonObject.getString("out_trade_no");
                //??????????????????
                String trade_no = jsonObject.getString("trade_no");
                String out_request_no = "";
                if (jsonObject.containsKey("trade_status")) {
                    //????????????
                    String trade_status = jsonObject.getString("trade_status");

                    if (StringUtil.isEmpty(trade_status)) {
                        throw new Exception("alipay trade_status result is null");
                    } else {
                        logger.info("alipay trade_status : " + trade_status);
                    }
                    if (!"TRADE_SUCCESS".equals(trade_status.toUpperCase())) {
                        throw new Exception(
                                "alipay trade_status result is:" + trade_status + ",is not success");
                    }
                    RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                    rechargeRecordEntity.setFlowing_no(out_trade_no);
                    rechargeRecordEntity.setPageSize(1);
                    List<RechargeRecordEntity> entities = rechargeRecordService.queryListPage(rechargeRecordEntity, request, response);
                    if (entities != null && entities.size() > 0) {
                        rechargeRecordEntity.setId(entities.get(0).getId());
                        rechargeRecordEntity.setTrans_no(trade_no);
                        rechargeRecordEntity.setOut_request_no(out_request_no);
                        rechargeRecordEntity.setFinished(true);
                        rechargeRecordEntity.setFinished_time(new Date());
                        rechargeRecordEntity.setStatus(2);
                        if (jsonObject.containsKey("buyer_pay_amount")) {
                            rechargeRecordEntity.setAmount(new BigDecimal(jsonObject.getString("buyer_pay_amount")));
                        }
                        ResultParam resultParam = rechargeRecordService.update(rechargeRecordEntity, request, response);
                        if (resultParam.getCode() == 0) {
                            if ("member".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//????????????
                                resultParam = iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                if (resultParam != null && resultParam.getCode() == 0) {
                                    UserInfoEntity entity = new UserInfoEntity();
                                    entity.setUser_id(entities.get(0).getUser_id());
                                    entity.setMember_level(entities.get(0).getMember_level());
                                    entity.setIs_member(true);
                                    int rets = userInfoMapper.updateByPrimaryKeySelective(entity);
                                    if (!(rets > 0)) {
                                        return "failure";
                                    }
                                } else {
                                    return "failure";
                                }
                            } else if ("join_live_class".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//???????????????
                                UserInfoEntity userInfoEntity = new UserInfoEntity();
                                userInfoEntity.setUser_id(entities.get(0).getUser_id());
                                userInfoEntity.setPageSize(1);
                                List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);
                                if (userInfoEntities != null && userInfoEntities.size() > 0) {       //?????????????????????
                                    ClassCardEntity classCardEntity = ClassCardService.findById(entities.get(0).getClass_card_id(), request, response);
                                    if (classCardEntity != null) {
                                        UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                        userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                                        userClassCardEntity.setType(classCardEntity.getType());
                                        userClassCardEntity.setPageSize(1);
                                        List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                        if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                            //????????????????????????????????????
                                            userClassCardEntity = userClassCardEntities.get(0);
                                            //????????????
                                            Date dt = new Date();
                                            Calendar rightNow = Calendar.getInstance();
                                            rightNow.setTime(dt);
                                            rightNow.add(Calendar.YEAR, 1);
                                            Date dt1 = rightNow.getTime();
                                            userClassCardEntity.setExpire_time(dt1);
                                            if (userClassCardEntity.getStatus() == 1) {
                                                userClassCardEntity.setCurriculum_num(classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum());
                                                userClassCardEntity.setStatus(2);
                                            } else {
                                                int integer = 0;
                                                integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum() + userClassCardEntity.getCurriculum_num();
                                                userClassCardEntity.setCurriculum_num(integer);
                                            }
                                            UserClassCardEntity entity = new UserClassCardEntity();
                                            entity.setUser_id(userClassCardEntity.getUser_id());
                                            entity.setDefault_use(false);
                                            int ret = userClassCardMapper.updateDefaultUse(entity);
                                            if (ret > 0) {
                                                userClassCardEntity.setDefault_use(true);
                                                userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                            }
                                        } else {
                                            Date dt = new Date();
                                            Calendar rightNow = Calendar.getInstance();
                                            rightNow.setTime(dt);
                                            rightNow.add(Calendar.YEAR, 1);
                                            Date dt1 = rightNow.getTime();
                                            userClassCardEntity.setExpire_time(dt1);
                                            int integer = 0;
                                            integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum();
                                            userClassCardEntity.setCurriculum_num(integer);
                                            userClassCardEntity.setDefault_use(true);
                                            UserClassCardEntity entity = new UserClassCardEntity();
                                            entity.setUser_id(userClassCardEntity.getUser_id());
                                            entity.setDefault_use(false);
                                            int ret = userClassCardMapper.updateDefaultUse(entity);
                                            if (ret > 0) {
                                                userClassCardEntity.setDefault_use(true);
                                                userClassCardMapper.insertSelective(userClassCardEntity);
                                            }
                                        }
                                    } else {
                                        return "failure";
                                    }
                                }
                            } else if ("account".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//????????????
                                UserInfoEntity userInfoEntity = new UserInfoEntity();
                                userInfoEntity.setUser_id(entities.get(0).getUser_id());
                                userInfoEntity.setPageSize(1);
                                List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);
                                if (userInfoEntities != null && userInfoEntities.size() > 0) {
                                    BigDecimal bignum1 = new BigDecimal("100");
                                    userInfoEntity.setCat_coin(entities.get(0).getAmount().multiply(bignum1));
                                    int retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                                    if (!(retupdateus > 0)) {
                                        return "failure";
                                    } else {
                                        //????????????
                                        UserOrderEntity orderEntity = new UserOrderEntity();
                                        orderEntity.setPayment_id(userInfoEntity.getUser_id());
                                        orderEntity.setType("recharge");
                                        orderEntity.setSource("alipay");
                                        orderEntity.setStatus(2);
                                        orderEntity.setPay_count(entities.get(0).getAmount().multiply(bignum1));
                                        orderService.insert(orderEntity, request, response);
                                        //??????
                                        orderEntity.setPageSize(1);
                                        Long i = orderService.queryListPage(orderEntity, request, response).get(0).getUser_id();
                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                        pushMessageParam.setType_id(i);
                                        pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                        pushMessageParam.setPush_time(new Date());
                                        pushMessageParam.setType("pay");
                                        pushMessageParam.setOpera_type("pay");
                                        pushMessageParam.setContent("????????????");
                                        pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                                    }
                                }
                            } else if ("study_card".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//???????????????
                                if ("mechanism_offline".equals(entities.get(0).getStudy_type())) {
                                    UserOrderEntity userOrderEntity = new UserOrderEntity();
                                    userOrderEntity.setType("buy_course_return");
                                    userOrderEntity.setUser_id(entities.get(0).getUser_id());
                                    userOrderEntity.setPay_count(entities.get(0).getAmount());
                                    ResultParam resultParam1 = orderService.insertPoint(userOrderEntity, request, response);
                                    if (entities.get(0).getIs_one_time_payment() || "live_stream_single".equals(entities.get(0).getType())) {
//                                        masterMechanismService.updateOfflineCash(entities.get(0),request,response);


                                        if (entities.get(0).getUser_study_card_id() != 0) {
                                            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entities.get(0).getUser_study_card_id());
                                            userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                                        }

                                        if (entities.get(0).getPoints() != 0) {
                                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entities.get(0).getUser_id());
                                            userInfoEntity.setPoints(entities.get(0).getPoints());
                                            userInfoMapper.updateSubPoint(userInfoEntity);
                                            UserPointsEntity userPointsEntity = new UserPointsEntity();
                                            userPointsEntity.setUsre_id(entities.get(0).getUser_id());
                                            userPointsEntity.setPoint(-entities.get(0).getPoints());
                                            userPointsEntity.setType("buy_course");
                                            int i1 = userPointsMapper.insertSelective(userPointsEntity);
                                        }
                                        this.updateSigleCourse(entities.get(0));
                                        userAppointmentService.updateStatus(entities.get(0), request, response);
                                        if ("live_stream_single".equals(entities.get(0).getType())) {
//                                            int i = masterSetPriceStreamMapper.updateAddEarnings(entities.get(0).getLive_streaming_id(),entities.get(0).getAmount());
                                            int i = masterSetPriceStreamMapper.updatePayNum(entities.get(0).getLive_streaming_id());
                                        }
                                    } else if ("single_session_deposit".equalsIgnoreCase(entities.get(0).getType())) {
                                        rechargeRecordService.payOneCourse(entities.get(0), request, response);
                                    } else {
                                        if ("live_stream_full".equals(entities.get(0).getType())) {
                                            int i = masterSetPriceStreamMapper.updatePayNum(entities.get(0).getLive_streaming_id());
                                        }
                                        userInfoService.updateUserPoint(entities.get(0), request, response);
                                        userCouponService.updateCoupStatus(entities.get(0), request, response);
                                        this.updatePayNum(entities.get(0));
                                        MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entities.get(0).getStudycard_id());
                                        UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                                        userStudyCardEntity.setMechanism_id(entities.get(0).getMechanism_id());
                                        userStudyCardEntity.setUser_id(entities.get(0).getUser_id());
                                        userStudyCardEntity.setType(masterSetPriceEntity.getType());
                                        userStudyCardEntity.setStudycard_id(entities.get(0).getStudycard_id());
                                        userStudyCardEntity.setCourse_num(entities.get(0).getCourse_num());
                                        userStudyCardEntity.setIs_experience(masterSetPriceEntity.getIs_interoperability());
                                        if (masterSetPriceEntity.getIs_interoperability()) {
                                            userStudyCardEntity.setEach_lesson_price(entities.get(0).getAmount());
                                        }
                                        //????????????+?????????
                                        Date dt = new Date();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());
                                        Date dt1 = rightNow.getTime();
                                        userStudyCardEntity.setEnd_time(dt1);
                                        userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num());
                                        userStudyCardEntity.setWhether_grouping(this.queryIsCanGroupIng(entities.get(0).getStudycard_id()));
                                        userStudyCardEntity.setIs_teach_paypal(true);
                                        userStudyCardEntity.setIs_one_time_payment(true);
                                        int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
                                        if (rets > 0) {
                                            masterMechanismService.updateCash(entities.get(0), request, response);
                                        }
                                        entities.get(0).setUser_study_card_id(userStudyCardEntity.getId());
                                        rechargeRecordMapper.updateByPrimaryKeySelective(entities.get(0));

                                        userAppointmentService.updateStatus(entities.get(0), request, response);
                                        this.updateFullCourse(entities.get(0));
                                        return "success";
                                    }

                                    /**
                                     * ?????????????????????
                                     */
                                    if (entities.get(0).getIs_experience()) {
                                        this.updatePayNum(entities.get(0));
                                        UserStudyCardEntity userStudyCardEntity1 = new UserStudyCardEntity();
                                        userStudyCardEntity1.setUser_id(entities.get(0).getUser_id());
                                        userStudyCardEntity1.setIs_experience(true);
                                        userStudyCardEntity1.setPageSize(1);
                                        userStudyCardEntity1 = userStudyCardMapper.queryListPage(userStudyCardEntity1).get(0);

                                        userStudyCardEntity1.setCourse_num((entities.get(0).getCourse_num() - 8 + userStudyCardEntity1.getCourse_num()));
                                        userStudyCardEntity1.setStatus(2);
                                        Date dt = new Date();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.MONTH, 12);
                                        Date dt1 = rightNow.getTime();
                                        userStudyCardEntity1.setEnd_time(dt1);
                                        userStudyCardEntity1.setStart_time(dt);
                                        userStudyCardEntity1.setStatus(2);
                                        userStudyCardEntity1.setIs_experience(false);
                                        userStudyCardEntity1.setOriginal_course_num(entities.get(0).getCourse_num());
                                        userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity1);
                                        return "success";
                                    }

                                    if (entities.get(0).getIs_one_time_payment()) {
//                                    this.updatePayNum(entities.get(0));
                                        masterAppointmentService.updateSingleCourseCash(entities.get(0), request, response);
                                        return "success";
                                    }

                                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                                    userInfoEntity.setUser_id(entities.get(0).getUser_id());//??????userid
                                    userInfoEntity.setPageSize(1);//??????userid
                                    List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);//??????userinfo
                                    if (userInfoEntities != null && userInfoEntities.size() > 0) {//?????????????????????
                                        UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                                        userStudyCardEntity.setType(entities.get(0).getStudy_type());//????????????
                                        userStudyCardEntity.setUser_id(userInfoEntity.getUser_id());//userid
                                        userStudyCardEntity.setStatus(2);//??????
                                        userStudyCardEntity.setPageSize(1);//1???
                                        List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);//???????????????
                                        if (userStudyCardEntities != null && userStudyCardEntities.size() > 0
                                                && entities.get(0).getMaster_id() == 0 && entities.get(0).getMechanism_id() == 0) {//??????????????????
                                            userStudyCardEntity = userStudyCardEntities.get(0);
                                            //????????????
                                            if (userStudyCardEntity.getStatus() == 1) {//????????????1
                                                Date dt = new Date();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());// getMember_duration??????????????????(???)
                                                Date dt1 = rightNow.getTime();
                                                userStudyCardEntity.setEnd_time(dt1);
                                                userStudyCardEntity.setStart_time(dt);
                                            } else {
                                                Date dt = userStudyCardEntity.getEnd_time();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());// getMember_duration??????????????????(???)
                                                Date dt1 = rightNow.getTime();
                                                userStudyCardEntity.setEnd_time(dt1);
                                            }
                                            Integer courceCount = userStudyCardEntity.getCourse_num() + entities.get(0).getCourse_num();
                                            userStudyCardEntity.setCourse_num(courceCount);
                                            this.updateCash(entities.get(0), request, response);
                                            userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num() + userStudyCardEntity.getOriginal_course_num());
                                            int rets = userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity);
                                            if (rets > 0) {
                                                if (entities.get(0).getMember_level() != 0) {
                                                    //????????????
                                                    //iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                                }
                                        /*//??????
                                        StudyPriceEntity studyPriceEntity = new StudyPriceEntity();
                                        studyPriceEntity.setType(entities.get(0).getStudy_type());
                                        studyPriceEntity.setCourse_num(entities.get(0).getCourse_num());
                                        studyPriceEntity.setDuration(entities.get(0).getMember_duration());
                                        studyPriceEntity.setPageSize(1);
                                        List<StudyPriceEntity> studyPriceEntities = studyPriceMapper.queryListPage(studyPriceEntity);
                                        if (studyPriceEntities != null && studyPriceEntities.size() > 0) {
                                            userInfoEntity.setCat_coin(studyPriceEntities.get(0).getGive_coin());
                                            int retupdateus = 0;
                                            synchronized (userInfoEntity) {
                                                retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                                            }
                                            if (!(retupdateus > 0)) {
                                                return "failure";
                                            } else {
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                                pushMessageParam.setContent("????????????,??????" + entities.get(0).getAmount().multiply(studyPriceEntities.get(0).getGive_coin()) + "??????");
                                                pushMessageParam.setPush_time(new Date());
                                                pushMessageParam.setType("pay");
                                                pushMessageParam.setOpera_type("pay");
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);//??????
                                                UserOrderEntity orderEntity = new UserOrderEntity();
                                                orderEntity.setPayment_id(userInfoEntity.getUser_id());
                                                orderEntity.setType("recharge");
                                                orderEntity.setSource("alipay");
                                                orderEntity.setStatus(2);
                                                orderEntity.setPay_count(studyPriceEntities.get(0).getGive_coin());
                                                orderService.insert(orderEntity, request, response);//????????????
                                            }
                                        }*/
                                            }
                                        } else {
                                            if (entities.get(0).getMaster_id() != 0) {
                                                userStudyCardEntity.setMaster_id(entities.get(0).getMaster_id());
                                                masterInfoService.updateCash(entities.get(0), request, response);
                                                this.updatePayNum(entities.get(0));
                                            }
                                            if (entities.get(0).getMechanism_id() != 0) {
                                                userStudyCardEntity.setMechanism_id(entities.get(0).getMechanism_id());
                                                masterMechanismService.updateCash(entities.get(0), request, response);
                                                this.updatePayNum(entities.get(0));
                                            }
                                            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entities.get(0).getStudycard_id());
                                            userStudyCardEntity.setStudycard_id(entities.get(0).getStudycard_id());
                                            userStudyCardEntity.setCourse_num(entities.get(0).getCourse_num());
                                            //????????????+?????????
                                            Date dt = new Date();
                                            Calendar rightNow = Calendar.getInstance();
                                            rightNow.setTime(dt);
                                            rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());
                                            Date dt1 = rightNow.getTime();
                                            userStudyCardEntity.setEnd_time(dt1);
                                            userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num());
                                            this.updateCash(entities.get(0), request, response);
                                            userStudyCardEntity.setBinding_mechanism_id(entities.get(0).getBinding_mechanism_id() ==
                                                    null ? 0 : entities.get(0).getBinding_mechanism_id());
                                            userStudyCardEntity.setIs_Interoperability(masterSetPriceEntity.getIs_interoperability());
                                            userStudyCardEntity.setIs_teach_paypal(true);
                                            int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
                                            entities.get(0).setUser_study_card_id(userStudyCardEntity.getId());
                                            rechargeRecordMapper.updateByPrimaryKeySelective(entities.get(0));
                                            if (rets > 0) {
                                        /*if (entities.get(0).getMember_level() != 0) {
                                            //????????????
                                            iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                        }
                                        //??????
                                        StudyPriceEntity studyPriceEntity = new StudyPriceEntity();
                                        studyPriceEntity.setType(entities.get(0).getStudy_type());
                                        studyPriceEntity.setCourse_num(entities.get(0).getCourse_num());
                                        studyPriceEntity.setDuration(entities.get(0).getMember_duration());
                                        studyPriceEntity.setPageSize(1);
                                        List<StudyPriceEntity> studyPriceEntities = studyPriceMapper.queryListPage(studyPriceEntity);
                                        if (studyPriceEntities != null && studyPriceEntities.size() > 0) {
                                            userInfoEntity.setCat_coin(studyPriceEntities.get(0).getGive_coin());
                                            int retupdateus = 0;
                                            synchronized (userInfoEntity) {
                                                retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                                            }
                                            if (!(retupdateus > 0)) {
                                                return "failure";
                                            } else {
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                                pushMessageParam.setContent("????????????,??????" + studyPriceEntities.get(0).getGive_coin() + "??????");
                                                pushMessageParam.setPush_time(new Date());
                                                pushMessageParam.setType("pay");
                                                pushMessageParam.setOpera_type("pay");
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                                                UserOrderEntity orderEntity = new UserOrderEntity();
                                                orderEntity.setPayment_id(entities.get(0).getUser_id());
                                                orderEntity.setType("recharge");
                                                orderEntity.setSource("alipay");
                                                orderEntity.setStatus(2);
                                                orderEntity.setPay_count(studyPriceEntities.get(0).getGive_coin());
                                                orderService.insert(orderEntity, request, response);
                                            }
                                        }*/

                                            }
                                        }
                                    }
                                }
                            } else if ("changeClasses".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                ResultParam resultParam1 = userStudyCardService.UpdateProductInfo(entities.get(0), request, response);
                            } else if ("experience_volume".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                CommodityCouponEntity commodityCouponEntity = new CommodityCouponEntity();
                                commodityCouponEntity.setUser_id(entities.get(0).getUser_id());
//                            commodityCouponEntity.setType("classexperience");
                                commodityCouponEntity.setId(entities.get(0).getCoupon_id());
                                commodityCouponService.ReceiveCoupon(commodityCouponEntity, request, response);
                            } else if ("class_card".equalsIgnoreCase(entities.get(0).getRcharge_type())) {

                                if (entities.get(0).getIs_experience()) {
                                    int course_num = 0;
                                    UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                    userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                                    userClassCardEntity.setType(entities.get(0).getStudy_type());
                                    userClassCardEntity.setPageSize(1);
                                    userClassCardEntity = userClassCardMapper.queryListPage(userClassCardEntity).get(0);
                                    course_num = userClassCardEntity.getCurriculum_num();

                                    UserClassCardEntity userClassCardEntity1 = new UserClassCardEntity();
                                    userClassCardEntity1.setUser_id(userClassCardEntity.getUser_id());
                                    userClassCardEntity1.setType(userClassCardEntity.getType());
                                    userClassCardEntity1.setIs_experience(false);
                                    userClassCardEntity1.setPageSize(1);
                                    List<UserClassCardEntity> list = userClassCardMapper.queryListPage(userClassCardEntity1);
                                    if (list != null && list.size() > 0) {
                                        userClassCardMapper.deleteByPrimaryKey(userClassCardEntity.getId());
                                        userClassCardEntity = list.get(0);
                                    } else {
                                        userClassCardEntity.setIs_experience(false);
                                    }
                                    userClassCardEntity.setStatus(2);
                                    userClassCardEntity.setCurriculum_num(entities.get(0).getCourse_num() - (8 - course_num) + userClassCardEntity.getCurriculum_num());
                                    Date dt = new Date();
                                    Calendar rightNow = Calendar.getInstance();
                                    rightNow.setTime(dt);
                                    rightNow.add(Calendar.MONTH, 12);
                                    Date dt1 = rightNow.getTime();
                                    userClassCardEntity.setExpire_time(dt1);

                                    int i = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                    if (i > 0) {
                                        return "success";
                                    }
                                } else {
                                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                                    userInfoEntity.setUser_id(entities.get(0).getUser_id());
                                    List<UserInfoEntity> userInfoEntities = userInfoMapper.query(userInfoEntity);
                                    if (userInfoEntities != null && userInfoEntities.size() > 0) {       //?????????????????????
                                        ClassCardEntity classCardEntity = ClassCardService.findById(entities.get(0).getClass_card_id(), request, response);
                                        if (classCardEntity != null) {
                                            this.updateCash(entities.get(0), request, response);
                                            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                            userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                                            userClassCardEntity.setType(classCardEntity.getType());
                                            userClassCardEntity.setPageSize(1);
                                            List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                            if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                                //????????????????????????????????????
                                                userClassCardEntity = userClassCardEntities.get(0);
                                                //????????????
                                                Date dt = new Date();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.YEAR, 1);
                                                Date dt1 = rightNow.getTime();
                                                userClassCardEntity.setExpire_time(dt1);
                                                if (userClassCardEntity.getStatus() == 1) {
                                                    if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                        userClassCardEntity.setMinute_num(classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute());
                                                    } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                        userClassCardEntity.setCurriculum_num(classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum());
                                                    }
                                                    userClassCardEntity.setStatus(2);
                                                } else {
                                                    int integer = 0;
                                                    if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                        integer = classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute() + userClassCardEntity.getMinute_num();
                                                        userClassCardEntity.setMinute_num(integer);
                                                    } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                        integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum() + userClassCardEntity.getCurriculum_num();
                                                        userClassCardEntity.setCurriculum_num(integer);
                                                    }
                                                }
                                                UserClassCardEntity entity = new UserClassCardEntity();
                                                entity.setUser_id(userClassCardEntity.getUser_id());
                                                entity.setDefault_use(false);
                                                int ret = userClassCardMapper.updateDefaultUse(entity);
                                                if (ret > 0) {
                                                    userClassCardEntity.setDefault_use(true);
                                                    userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                }
                                            } else {
                                                Date dt = new Date();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.YEAR, 1);
                                                Date dt1 = rightNow.getTime();
                                                userClassCardEntity.setExpire_time(dt1);
                                                int integer = 0;
                                                if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                    integer = classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute();
                                                    userClassCardEntity.setMinute_num(integer);
                                                } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                    integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum();
                                                    userClassCardEntity.setCurriculum_num(integer);
                                                }
                                                UserClassCardEntity entity = new UserClassCardEntity();
                                                entity.setUser_id(userClassCardEntity.getUser_id());
                                                entity.setDefault_use(false);
                                                userClassCardMapper.updateDefaultUse(entity);
                                                userClassCardEntity.setDefault_use(true);
                                                userClassCardMapper.insertSelective(userClassCardEntity);
                                            }
                                        }
                                    } else {
                                        return "failure";
                                    }
                                }
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("prepayment")) {
                                userAppointmentService.updateStatus(entities.get(0), request, response);

                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("offline_class")) {
                                UserAppointmentEntity userAppointmentEntity = userAppointmentService.findById(entities.get(0).getAppointment_id(), request, response);
                                if (userAppointmentEntity != null) {
                                    userAppointmentEntity.setStatus(2);
                                    userAppointmentMapper.updateByPrimaryKeySelective(userAppointmentEntity);
                                } else {
                                    logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                                    return "failure";
                                }

                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("specialty_sheets")) {
                                RechargeRecordEntity rechargeRecordEntity1 = new RechargeRecordEntity();
                                rechargeRecordEntity1.setUser_study_card_id(entities.get(0).getUser_study_card_id());
                                List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(rechargeRecordEntity1);
                                for (RechargeRecordEntity recordEntity : list) {
                                    recordEntity.setIs_turning_long_orders(true);
                                    rechargeRecordMapper.updateByPrimaryKeySelective(recordEntity);
                                }
                                rechargeRecordService.updateStudyCardStatus(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("deposit")) {
                                int i = rechargeRecordService.updateRchStatus(entities.get(0), request, response);
                                ResultParam resultParam1 = userInfoMapper.updateUserPayPreAmountByUserId(entities.get(0));
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("coupon_198")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon198(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("coupon_177")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon177(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("coupon_77")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon77(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("deductions_coupon")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon99(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("mechanism_offline")) {

                            /*
                            MasterAppointmentEntity masterAppointmentEntity=masterAppointmentService .findById(entities.get(0).getAppointment_id(),request,response);
                            if(masterAppointmentEntity!=null){
                                Integer integer=masterAppointmentEntity.getConnect_peoplenum()+1;
                                masterAppointmentEntity.setConnect_peoplenum(integer);
                                masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                                userAppointmentEntity.setUser_id(entities.get(0).getUser_id());
                                userAppointmentEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
                                userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
                                userAppointmentEntity.setMechanism_id(masterAppointmentEntity.getMechanism_id());
                                userAppointmentEntity.setStatus(2);
                                userAppointmentEntity.setMaster_type(masterAppointmentEntity.getType());
                                userAppointmentEntity.setEarnings(entities.get(0).getAmount());
                                int ret=userAppointmentMapper.insertSelective(userAppointmentEntity);
                                if(ret==0){
                                    logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                                    return "failure";
                                }
                            }else{
                                logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                                return "failure";
                            }
                             */
                            }
                        }
                    } else {
                        logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                        return "failure";
                    }
                } else {
                    return "failure";
                }
            } else {//????????????
                logger.warn("alipay notify sign is false");
                return "failure";
            }
        } catch (
                Exception e) {
            logger.warn(e.getMessage(), e);
            return "failure";
        }
        return "success";
    }

    private void updateFullCourse(RechargeRecordEntity rechargeRecordEntity) {
        rechargeRecordEntity.setType("full_purchase");
        rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecordEntity);
    }

    private void updateSigleCourse(RechargeRecordEntity rechargeRecordEntity) {
        rechargeRecordEntity.setType("single_session");
        rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecordEntity);
    }

    private Boolean queryIsCanGroupIng(Long studycard_id) {
        BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
        businessActivityEntity.setMaster_set_price_id(studycard_id);
        businessActivityEntity.setType("grouping");
        businessActivityEntity.setStatus(2);
        businessActivityEntity.setPageSize(1);
        return (businessActivityMapper.queryListPage(businessActivityEntity)).size() > 0;
    }

    @Override
    public void updatePayNum(RechargeRecordEntity rechargeRecordEntity) {
        MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id());
        masterSetPriceEntity.setPay_num(masterSetPriceEntity.getPay_num() + 1);
        masterSetPriceMapper.updateByPrimaryKeySelective(masterSetPriceEntity);
    }

   /* @Override
    public WXPayConfig wxPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        WXPayConfig wxPayConfig = wxPayService.getWxPayConfig(1);
        return wxPayConfig;
    }*/

    public static String GetMapToXML(Map<String, String> param) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (Map.Entry<String, String> entry : param.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">");
            sb.append(entry.getValue());
            sb.append("</").append(entry.getKey()).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * ????????????
     *  @param request
     * @param response
     * @return
     */
    @Override
    public Long wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        String code = "SUCCESS";
        String message = "??????";
        String key = null;
        String lockName = null;
        Map<String, String> return_data = new HashMap<String, String>();
        Long id = 0L;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String string = null;
            BufferedReader reader = request.getReader();
            while ((string = reader.readLine()) != null) {
                stringBuilder.append(string);
            }
            if (stringBuilder.length() <= 0) {
                throw new Exception("????????????");
            }
            logger.warn("????????????:" + stringBuilder.toString());
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(stringBuilder.toString());
            if (!notifyMap.get("return_code").toLowerCase().equals("SUCCESS".toLowerCase())) {
                logger.warn(notifyMap.get("return_msg"));
            }
            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setFlowing_no(notifyMap.get("out_trade_no"));
            rechargeRecordEntity.setPageSize(1);
            WXPay wxpay = null;
            List<RechargeRecordEntity> entities = rechargeRecordService.queryListPage(rechargeRecordEntity, request, response);
            if (entities != null && entities.size() > 0) {
                if (!entities.get(0).getFinished()) {
                    id = entities.get(0).getId();
                    lockName = "notifyWx" + notifyMap.get("out_trade_no");
                    key = lockService.withLock(lockName, 1500, 1800);
                    //RechargeRecordEntity payOrderEntity = (RechargeRecordEntity) orderInfoService.findById(notifyMap.get("out_trade_no"), null);
                    //Integer integer = getType(payOrderEntity.getSource());

                    Integer integer = getType(entities.get(0).getSource());
                    WXPayConfig wxPayConfig = wxPayService.getWxPayConfig(integer);
                    wxpay = new WXPay(wxPayConfig);
                    logger.warn("xmltopMap is :" + JsonUtilFastjson.toJSONString(notifyMap));
                    if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                        rechargeRecordEntity.setId(entities.get(0).getId());
                        rechargeRecordEntity.setTrans_no(notifyMap.get("transaction_id"));
                        rechargeRecordEntity.setFinished(true);
                        rechargeRecordEntity.setFinished_time(new Date());
                        rechargeRecordEntity.setStatus(2);
                        ResultParam resultParam = rechargeRecordService.update(rechargeRecordEntity, request, response);
                        if (resultParam.getCode() == 0) {
                            if ("member".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                resultParam = iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                if (resultParam != null && resultParam.getCode() == 0) {
                                    UserInfoEntity entity = new UserInfoEntity();
                                    entity.setUser_id(entities.get(0).getUser_id());
                                    entity.setMember_level(entities.get(0).getMember_level());
                                    entity.setIs_member(true);
                                    int rets = userInfoMapper.updateByPrimaryKeySelective(entity);
                                }
                            } else if ("account".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                UserInfoEntity userInfoEntity = new UserInfoEntity();
                                userInfoEntity.setUser_id(entities.get(0).getUser_id());
                                List<UserInfoEntity> userInfoEntities = userInfoMapper.query(userInfoEntity);
                                if (userInfoEntities != null && userInfoEntities.size() > 0) {
                                    BigDecimal bignum1 = new BigDecimal("100");
                                    userInfoEntity.setCat_coin(entities.get(0).getAmount().multiply(bignum1));
                                    int retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);
                                    if (!(retupdateus > 0)) {
                                        UserOrderEntity orderEntity = new UserOrderEntity();
                                        orderEntity.setPayment_id(userInfoEntity.getUser_id());
                                        orderEntity.setType("recharge");
                                        orderEntity.setSource("wxpay");
                                        orderEntity.setStatus(2);
                                        orderEntity.setPay_count(entities.get(0).getAmount().multiply(bignum1));
                                        orderService.insert(orderEntity, request, response);

                                        Long i = orderService.query(orderEntity, request, response).get(0).getUser_id();
                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                        pushMessageParam.setType_id(i);
                                        pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                        pushMessageParam.setPush_time(new Date());
                                        pushMessageParam.setType("pay");
                                        pushMessageParam.setOpera_type("pay");
                                        pushMessageParam.setContent("????????????");
                                        pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
//                                        return code;
                                    }
                                }
                            } else if ("study_card".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                                                    /*

                                if ("mechanism_offline".equals(entities.get(0).getStudy_type())) {
                                    if ("teach_paypal".equalsIgnoreCase(entities.get(0).getSource()) || entities.get(0).getIs_one_time_payment()) {
                                        if (entities.get(0).getCourse_num() == 1) {
//                                        masterMechanismService.updateOfflineCash(entities.get(0),request,response);

                                            if (entities.get(0).getUser_study_card_id() != 0) {
                                                UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entities.get(0).getUser_study_card_id());
                                                userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                                            }

                                            if (entities.get(0).getPoints() != 0) {
                                                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entities.get(0).getUser_id());
                                                userInfoEntity.setPoints(entities.get(0).getPoints());
                                                userInfoMapper.updateSubPoint(userInfoEntity);
                                                UserPointsEntity userPointsEntity = new UserPointsEntity();
                                                userPointsEntity.setUsre_id(entities.get(0).getUser_id());
                                                userPointsEntity.setPoint(-entities.get(0).getPoints());
                                                userPointsEntity.setType("buy_course");
                                                int i1 = userPointsMapper.insertSelective(userPointsEntity);
                                            }

                                            userAppointmentService.updateStatus(entities.get(0), request, response);
                                        }

                                     */
                                if ("mechanism_offline".equals(entities.get(0).getStudy_type())) {
                                    UserOrderEntity userOrderEntity = new UserOrderEntity();
                                    userOrderEntity.setType("buy_course_return");
                                    userOrderEntity.setPay_count(entities.get(0).getAmount());
                                    userOrderEntity.setUser_id(entities.get(0).getUser_id());
                                    ResultParam resultParam1 = orderService.insertPoint(userOrderEntity, request, response);
                                    if (entities.get(0).getIs_one_time_payment() || "live_stream_single".equals(entities.get(0).getType())) {
                                        //                                        masterMechanismService.updateOfflineCash(entities.get(0),request,response);
                                        if (entities.get(0).getUser_study_card_id() != 0) {
                                            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entities.get(0).getUser_study_card_id());
                                            userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                                        }

                                        if (entities.get(0).getPoints() != 0) {
                                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entities.get(0).getUser_id());
                                            userInfoEntity.setPoints(entities.get(0).getPoints());
                                            userInfoMapper.updateSubPoint(userInfoEntity);
                                            UserPointsEntity userPointsEntity = new UserPointsEntity();
                                            userPointsEntity.setUsre_id(entities.get(0).getUser_id());
                                            userPointsEntity.setPoint(-entities.get(0).getPoints());
                                            userPointsEntity.setType("buy_course");
                                            int i1 = userPointsMapper.insertSelective(userPointsEntity);
                                        }
                                        this.updateSigleCourse(entities.get(0));
                                        userAppointmentService.updateStatus(entities.get(0), request, response);
                                        userGroupingService.updateSettlementCash(entities.get(0), request, response);
                                        if ("live_stream_single".equals(entities.get(0).getType())) {
//                                            int i = masterSetPriceStreamMapper.updateAddEarnings(entities.get(0).getLive_streaming_id(),entities.get(0).getAmount());
                                        }
                                    } else if ("single_session_deposit".equalsIgnoreCase(entities.get(0).getType())) {
                                        rechargeRecordService.payOneCourse(entities.get(0), request, response);
                                    } else {
                                        if ("live_stream_full".equals(entities.get(0).getType())) {
                                            int i = masterSetPriceStreamMapper.updatePayNum(entities.get(0).getLive_streaming_id());
                                        }
                                        userCouponService.updateCoupStatus(entities.get(0), request, response);
                                        this.updatePayNum(entities.get(0));
                                        MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entities.get(0).getStudycard_id());
                                        UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                                        userStudyCardEntity.setMechanism_id(entities.get(0).getMechanism_id());
                                        userStudyCardEntity.setUser_id(entities.get(0).getUser_id());
                                        userStudyCardEntity.setType(masterSetPriceEntity.getType());
                                        userStudyCardEntity.setStudycard_id(entities.get(0).getStudycard_id());
                                        userStudyCardEntity.setCourse_num(entities.get(0).getCourse_num());
                                        userStudyCardEntity.setIs_experience(masterSetPriceEntity.getIs_interoperability());
                                        if (masterSetPriceEntity.getIs_interoperability()) {
                                            userStudyCardEntity.setEach_lesson_price(entities.get(0).getAmount());
                                        }
                                        //????????????+?????????
                                        Date dt = new Date();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());
                                        Date dt1 = rightNow.getTime();
                                        userStudyCardEntity.setEnd_time(dt1);
                                        userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num());
                                        userStudyCardEntity.setWhether_grouping(this.queryIsCanGroupIng(entities.get(0).getStudycard_id()));
                                        userStudyCardEntity.setIs_teach_paypal(true);
                                        userStudyCardEntity.setIs_one_time_payment(true);
                                        int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
                                        if (rets > 0) {
                                            masterMechanismService.updateCash(entities.get(0), request, response);
                                        }
                                        entities.get(0).setUser_study_card_id(userStudyCardEntity.getId());
                                        rechargeRecordMapper.updateByPrimaryKeySelective(entities.get(0));
//                                    userAppointmentService.updateStatus(entities.get(0), request, response);
//                                        return "success";
                                        this.updateFullCourse(entities.get(0));

                                    }

                                    /**
                                     * ?????????????????????
                                     */
                                    if (entities.get(0).getIs_experience()) {
                                        UserStudyCardEntity userStudyCardEntity1 = new UserStudyCardEntity();
                                        userStudyCardEntity1.setUser_id(entities.get(0).getUser_id());
                                        userStudyCardEntity1.setIs_experience(true);
                                        userStudyCardEntity1.setPageSize(1);
                                        userStudyCardEntity1 = userStudyCardMapper.queryListPage(userStudyCardEntity1).get(0);

                                        userStudyCardEntity1.setCourse_num((entities.get(0).getCourse_num() - 8 + userStudyCardEntity1.getCourse_num()));
                                        userStudyCardEntity1.setStatus(2);
                                        Date dt = new Date();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.MONTH, 12);
                                        Date dt1 = rightNow.getTime();
                                        userStudyCardEntity1.setEnd_time(dt1);
                                        userStudyCardEntity1.setStart_time(dt);
                                        userStudyCardEntity1.setStatus(2);
                                        userStudyCardEntity1.setIs_experience(false);
                                        userStudyCardEntity1.setOriginal_course_num(entities.get(0).getCourse_num());
                                        userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity1);
//                                        return code;

                                    }
                                    if (entities.get(0).getIs_one_time_payment()) {
                                        this.updatePayNum(entities.get(0));
                                        masterAppointmentService.updateSingleCourseCash(entities.get(0), request, response);
//                                        return "success";
                                    } else {
                                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                                        userInfoEntity.setUser_id(entities.get(0).getUser_id());//??????userid
                                        userInfoEntity.setPageSize(1);//??????userid
                                        List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);//??????userinfo
                                        if (userInfoEntities != null && userInfoEntities.size() > 0) {//?????????????????????
                                            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                                            userStudyCardEntity.setType(entities.get(0).getStudy_type());//????????????
                                            userStudyCardEntity.setUser_id(userInfoEntity.getUser_id());//userid
                                            userStudyCardEntity.setStatus(2);//??????
                                            userStudyCardEntity.setPageSize(1);//1???
                                            List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);//???????????????
                                            if (userStudyCardEntities != null && userStudyCardEntities.size() > 0 && entities.get(0).getMaster_id() == 0 && entities.get(0).getMechanism_id() == 0) {//??????????????????
                                                userStudyCardEntity = userStudyCardEntities.get(0);
                                                //????????????
                                                if (userStudyCardEntity.getStatus() == 1) {//????????????1
                                                    Date dt = new Date();
                                                    Calendar rightNow = Calendar.getInstance();
                                                    rightNow.setTime(dt);
                                                    rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());// getMember_duration??????????????????(???)
                                                    Date dt1 = rightNow.getTime();
                                                    userStudyCardEntity.setEnd_time(dt1);
                                                    userStudyCardEntity.setStart_time(dt);
                                                } else {
                                                    Date dt = userStudyCardEntity.getEnd_time();
                                                    Calendar rightNow = Calendar.getInstance();
                                                    rightNow.setTime(dt);
                                                    rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());// getMember_duration??????????????????(???)
                                                    Date dt1 = rightNow.getTime();
                                                    userStudyCardEntity.setEnd_time(dt1);
                                                }
                                                Integer courceCount = userStudyCardEntity.getCourse_num() + entities.get(0).getCourse_num();
                                                userStudyCardEntity.setCourse_num(courceCount);
                                                userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num() + userStudyCardEntity.getOriginal_course_num());
                                                this.updateCash(entities.get(0), request, response);
                                                int rets = userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity);
                                                if (rets > 0) {
                                                    if (entities.get(0).getMember_level() != 0) {
                                                        //????????????
                                                        //iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                                    }
                                        /*//??????
                                        StudyPriceEntity studyPriceEntity = new StudyPriceEntity();
                                        studyPriceEntity.setType(entities.get(0).getStudy_type());
                                        studyPriceEntity.setCourse_num(entities.get(0).getCourse_num());
                                        studyPriceEntity.setDuration(entities.get(0).getMember_duration());
                                        studyPriceEntity.setPageSize(1);
                                        List<StudyPriceEntity> studyPriceEntities = studyPriceMapper.queryListPage(studyPriceEntity);
                                        if (studyPriceEntities != null && studyPriceEntities.size() > 0) {
                                            userInfoEntity.setCat_coin(studyPriceEntities.get(0).getGive_coin());
                                            int retupdateus = 0;
                                            synchronized (userInfoEntity) {
                                                retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                                            }
                                            if (!(retupdateus > 0)) {
                                                return "failure";
                                            } else {
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                                pushMessageParam.setContent("????????????,??????" + entities.get(0).getAmount().multiply(studyPriceEntities.get(0).getGive_coin()) + "??????");
                                                pushMessageParam.setPush_time(new Date());
                                                pushMessageParam.setType("pay");
                                                pushMessageParam.setOpera_type("pay");
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);//??????
                                                UserOrderEntity orderEntity = new UserOrderEntity();
                                                orderEntity.setPayment_id(userInfoEntity.getUser_id());
                                                orderEntity.setType("recharge");
                                                orderEntity.setSource("alipay");
                                                orderEntity.setStatus(2);
                                                orderEntity.setPay_count(studyPriceEntities.get(0).getGive_coin());
                                                orderService.insert(orderEntity, request, response);//????????????
                                            }
                                        }*/
                                                }
                                            } else {
                                                if (entities.get(0).getMaster_id() != 0) {
                                                    userStudyCardEntity.setMaster_id(entities.get(0).getMaster_id());
                                                    masterInfoService.updateCash(entities.get(0), request, response);
                                                    this.updatePayNum(entities.get(0));
                                                }
                                                if (entities.get(0).getMechanism_id() != 0) {
                                                    userStudyCardEntity.setMechanism_id(entities.get(0).getMechanism_id());
                                                    masterMechanismService.updateCash(entities.get(0), request, response);
                                                    this.updatePayNum(entities.get(0));
                                                }
                                                MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entities.get(0).getStudycard_id());
                                                userStudyCardEntity.setStudycard_id(entities.get(0).getStudycard_id());
                                                userStudyCardEntity.setCourse_num(entities.get(0).getCourse_num());
                                                //????????????+?????????
                                                Date dt = new Date();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());
                                                Date dt1 = rightNow.getTime();
                                                userStudyCardEntity.setEnd_time(dt1);
                                                userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num());
                                                userStudyCardEntity.setBinding_mechanism_id(entities.get(0).getBinding_mechanism_id() ==
                                                        null ? 0 : entities.get(0).getBinding_mechanism_id());
                                                userStudyCardEntity.setIs_Interoperability(masterSetPriceEntity.getIs_interoperability());
                                                this.updateCash(entities.get(0), request, response);
                                                userStudyCardEntity.setIs_teach_paypal(true);
                                                int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
                                                entities.get(0).setUser_study_card_id(userStudyCardEntity.getId());
                                                rechargeRecordMapper.updateByPrimaryKeySelective(entities.get(0));
                                                if (rets > 0) {
                                        /*if (entities.get(0).getMember_level() != 0) {
                                            //????????????
                                            iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                        }
                                        //??????
                                        StudyPriceEntity studyPriceEntity = new StudyPriceEntity();
                                        studyPriceEntity.setType(entities.get(0).getStudy_type());
                                        studyPriceEntity.setCourse_num(entities.get(0).getCourse_num());
                                        studyPriceEntity.setDuration(entities.get(0).getMember_duration());
                                        studyPriceEntity.setPageSize(1);
                                        List<StudyPriceEntity> studyPriceEntities = studyPriceMapper.queryListPage(studyPriceEntity);
                                        if (studyPriceEntities != null && studyPriceEntities.size() > 0) {
                                            userInfoEntity.setCat_coin(studyPriceEntities.get(0).getGive_coin());
                                            int retupdateus = 0;
                                            synchronized (userInfoEntity) {
                                                retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                                            }
                                            if (!(retupdateus > 0)) {
                                                return "failure";
                                            } else {
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                                pushMessageParam.setContent("????????????,??????" + studyPriceEntities.get(0).getGive_coin() + "??????");
                                                pushMessageParam.setPush_time(new Date());
                                                pushMessageParam.setType("pay");
                                                pushMessageParam.setOpera_type("pay");
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                                                UserOrderEntity orderEntity = new UserOrderEntity();
                                                orderEntity.setPayment_id(entities.get(0).getUser_id());
                                                orderEntity.setType("recharge");
                                                orderEntity.setSource("alipay");
                                                orderEntity.setStatus(2);
                                                orderEntity.setPay_count(studyPriceEntities.get(0).getGive_coin());
                                                orderService.insert(orderEntity, request, response);
                                            }
                                        }*/

                                                }
                                            }
                                        }
                                    }
                                }
//                                return code;
                            } else if ("changeClasses".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                ResultParam resultParam1 = userStudyCardService.UpdateProductInfo(entities.get(0), request, response);
                            } else if ("experience_volume".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                CommodityCouponEntity commodityCouponEntity = new CommodityCouponEntity();
                                commodityCouponEntity.setUser_id(entities.get(0).getUser_id());
                                commodityCouponEntity.setType("classexperience");
                                commodityCouponService.ReceiveCoupon(commodityCouponEntity, request, response);
//                                return code;
                            } else if ("class_card".equalsIgnoreCase(entities.get(0).getRcharge_type())) {

                                if (entities.get(0).getIs_experience()) {
                                    int course_num = 0;
                                    UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                    userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                                    userClassCardEntity.setType(entities.get(0).getStudy_type());
                                    userClassCardEntity.setPageSize(1);
                                    userClassCardEntity = userClassCardMapper.queryListPage(userClassCardEntity).get(0);
                                    course_num = userClassCardEntity.getCurriculum_num();

                                    UserClassCardEntity userClassCardEntity1 = new UserClassCardEntity();
                                    userClassCardEntity1.setUser_id(userClassCardEntity.getUser_id());
                                    userClassCardEntity1.setType(userClassCardEntity.getType());
                                    userClassCardEntity1.setIs_experience(false);
                                    userClassCardEntity1.setPageSize(1);
                                    List<UserClassCardEntity> list = userClassCardMapper.queryListPage(userClassCardEntity1);
                                    if (list != null && list.size() > 0) {
                                        userClassCardMapper.deleteByPrimaryKey(userClassCardEntity.getId());
                                        userClassCardEntity = list.get(0);
                                    } else {
                                        userClassCardEntity.setIs_experience(false);
                                    }
                                    userClassCardEntity.setStatus(2);
                                    userClassCardEntity.setCurriculum_num(entities.get(0).getCourse_num() - (8 - course_num) + userClassCardEntity.getCurriculum_num());
                                    Date dt = new Date();
                                    Calendar rightNow = Calendar.getInstance();
                                    rightNow.setTime(dt);
                                    rightNow.add(Calendar.MONTH, 12);
                                    Date dt1 = rightNow.getTime();
                                    userClassCardEntity.setExpire_time(dt1);

                                    int i = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                    if (i > 0) {
                                    }
//                                    return code;
                                } else {

                                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                                    userInfoEntity.setUser_id(entities.get(0).getUser_id());
                                    List<UserInfoEntity> userInfoEntities = userInfoMapper.query(userInfoEntity);
                                    if (userInfoEntities != null && userInfoEntities.size() > 0) {       //?????????????????????
                                        ClassCardEntity classCardEntity = ClassCardService.findById(entities.get(0).getClass_card_id(), request, response);
                                        if (classCardEntity != null) {
                                            this.updateCash(entities.get(0), request, response);
                                            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                            userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                                            userClassCardEntity.setType(classCardEntity.getType());
                                            userClassCardEntity.setPageSize(1);
                                            List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                            if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                                //????????????????????????????????????
                                                userClassCardEntity = userClassCardEntities.get(0);
                                                //????????????
                                                Date dt = new Date();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.YEAR, 1);
                                                Date dt1 = rightNow.getTime();
                                                userClassCardEntity.setExpire_time(dt1);
                                                if (userClassCardEntity.getStatus() == 1) {
                                                    if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                        userClassCardEntity.setMinute_num(classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute());
                                                    } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                        userClassCardEntity.setCurriculum_num(classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum());
                                                    }
                                                    userClassCardEntity.setStatus(2);
                                                } else {
                                                    int inte = 0;
                                                    if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                        inte = classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute() + userClassCardEntity.getMinute_num();
                                                        userClassCardEntity.setMinute_num(inte);
                                                    } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                        inte = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum() + userClassCardEntity.getCurriculum_num();
                                                        userClassCardEntity.setCurriculum_num(inte);
                                                    }
                                                }
                                                UserClassCardEntity entity = new UserClassCardEntity();
                                                entity.setUser_id(userClassCardEntity.getUser_id());
                                                entity.setDefault_use(false);
                                                int ret = userClassCardMapper.updateDefaultUse(entity);
                                                if (ret > 0) {
                                                    userClassCardEntity.setDefault_use(true);
                                                    userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                }
                                            } else {
                                                Date dt = new Date();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.YEAR, 1);
                                                Date dt1 = rightNow.getTime();
                                                userClassCardEntity.setExpire_time(dt1);
                                                int inte = 0;
                                                if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                    inte = classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute();
                                                    userClassCardEntity.setMinute_num(inte);
                                                } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                    inte = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum();
                                                    userClassCardEntity.setCurriculum_num(inte);
                                                }
                                                UserClassCardEntity entity = new UserClassCardEntity();
                                                entity.setUser_id(userClassCardEntity.getUser_id());
                                                entity.setDefault_use(false);
                                                int ret = userClassCardMapper.updateDefaultUse(entity);
                                                if (ret > 0) {
                                                    userClassCardEntity.setDefault_use(true);
                                                    userClassCardMapper.insertSelective(userClassCardEntity);
                                                }
                                            }
                                        }
                                    }
                                }
//                                return code;
                            } else if ("offline_class".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                UserAppointmentEntity userAppointmentEntity = userAppointmentService.findById(entities.get(0).getAppointment_id(), request, response);
                                if (userAppointmentEntity != null) {
                                    userAppointmentEntity.setStatus(2);
                                    userAppointmentMapper.updateByPrimaryKeySelective(userAppointmentEntity);
                                } else {
                                    logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                                }
//                                return code;
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("specialty_sheets")) {
                                RechargeRecordEntity rechargeRecordEntity1 = new RechargeRecordEntity();
                                rechargeRecordEntity1.setUser_study_card_id(entities.get(0).getUser_study_card_id());
                                List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(rechargeRecordEntity1);
                                for (RechargeRecordEntity recordEntity : list) {
                                    recordEntity.setIs_turning_long_orders(true);
                                    rechargeRecordMapper.updateByPrimaryKeySelective(recordEntity);
                                }
                                rechargeRecordService.updateStudyCardStatus(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("deposit")) {
                                int i = rechargeRecordService.updateRchStatus(entities.get(0), request, response);
                                ResultParam resultParam1 = userInfoMapper.updateUserPayPreAmountByUserId(entities.get(0));
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("coupon_198")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon198(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("coupon_177")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon177(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("coupon_77")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon77(entities.get(0), request, response);
                            } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("deductions_coupon")) {
                                ResultParam resultParam1 = userCouponService.insertUserCoupon99(entities.get(0), request, response);
                            } else if ("mechanism_offline".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                                MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(entities.get(0).getAppointment_id(), request, response);
                                if (masterAppointmentEntity != null) {
                                    masterAppointmentEntity.setConnect_peoplenum(masterAppointmentEntity.getConnect_peoplenum() + 1);
                                    masterAppointmentService.update(masterAppointmentEntity, request, response);
                                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                                    userAppointmentEntity.setUser_id(entities.get(0).getUser_id());
                                    userAppointmentEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
                                    userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
                                    userAppointmentEntity.setMechanism_id(masterAppointmentEntity.getMechanism_id());
                                    userAppointmentEntity.setStatus(2);
                                    userAppointmentEntity.setMaster_type(masterAppointmentEntity.getType());
                                    userAppointmentEntity.setEarnings(entities.get(0).getAmount());
                                    int ret = userAppointmentMapper.insertSelective(userAppointmentEntity);
                                    if (ret == 0) {
                                        logger.warn("????????????" + ret);
                                        code = "FAIL";
                                        message = "????????????";
                                    }
//                                    return code;
                                } else {
                                    logger.warn("????????????" + entities.get(0).getAppointment_id());
                                    code = "FAIL";
                                    message = "????????????";

                                }
                            }
                        } else {
                            logger.warn("??????????????????" + rechargeRecordEntity.getTrans_no());
                        }

                    } else {
                        // ????????????????????????????????????sign?????????????????????????????????
                        logger.warn("????????????");
                        code = "FAIL";
                        message = "????????????";
                    }
                }

            } else {
                logger.warn("???????????????");
                code = "FAIL";
                message = "????????????";
            }


        } catch (
                Exception e) {
            logger.warn(e.getMessage(), e);
            code = "FAIL";
            message = "????????????";
        } finally {
            lockService.releaseLock(lockName, key);
        }

        return_data.put("return_code", code);
        return_data.put("return_msg", message);

        PrintWriter printWriter = null;
        try {
            response.setHeader("Content-type", "application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.reset();
            printWriter = response.getWriter();
            printWriter.write(wxPayService.returnXml(code, message));
            printWriter.flush();
        } catch (Exception e) {
//            logger.warn(e.getMessage(), e);
            System.out.println("??????????????????" + return_data);

        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
            lockService.releaseLock(lockName, key);
        }

        return id;
//        return GetMapToXML(return_data);

//        return "success";
    }

    void updateCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse
            response) {
        if (rechargeRecordEntity.getAppointment_id() != 0) {
            masterAppointmentService.updateCash(rechargeRecordEntity, request, response);
        }
    }

    public String getTitle(String type) {
        String title = "";
        switch (type) {
            case "member":
                title = "????????????";
                break;
            case "authentication":
                title = "??????????????????";
                break;
            case "account":
                title = "????????????";
                break;
            case "experience_volume":
                title = "?????????";
                break;
            case "transfer":
                title = "????????????";
                break;
            default:
                title = "";
        }
        return title;
    }

    @Override
    @Transactional
    public ResultParam wxPay(RechargeRecordEntity payOrderEntity, HttpServletRequest request, HttpServletResponse
            response) {
        String ipAddr = getIpAddr2(request);
        String nonce_str = WXPayUtil.generateNonceStr();
        Integer integer = this.getType(payOrderEntity.getSource());
        WXPayConfig wxPayConfig = wxPayService.getWxPayConfig(integer);
        ResultParam resultParam = ResultUtil.success();
        if ((payOrderEntity.getIs_experience() == null || !payOrderEntity.getIs_experience())) {
            if (StringUtil.isEmpty(payOrderEntity.getFlowing_no())) {
                String flowing_no = CommonUtils.generateFlowingCode();
                payOrderEntity.setFlowing_no(flowing_no);
                payOrderEntity.setPay_type("wx");
                if (payOrderEntity.getId() == null) {
                    payOrderEntity.setStatus(0);
                    resultParam = rechargeRecordService.insert(payOrderEntity, request, response);
                }
            }
        } else {
            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setPageSize(1);
            rechargeRecordEntity.setIs_experience(payOrderEntity.getIs_experience());
            rechargeRecordEntity.setUser_id(payOrderEntity.getUser_id());
            rechargeRecordEntity.setRcharge_type(payOrderEntity.getRcharge_type());
            if (payOrderEntity.getMaster_id() != null && payOrderEntity.getMaster_id() != 0) {
                rechargeRecordEntity.setMaster_id(payOrderEntity.getMaster_id());
            }
            if (payOrderEntity.getMechanism_id() != null && payOrderEntity.getMechanism_id() != 0) {
                rechargeRecordEntity.setMechanism_id(payOrderEntity.getMechanism_id());
            }
            List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
            if (list != null && list.size() > 0) {
                payOrderEntity.setFlowing_no(list.get(0).getFlowing_no());
            } else {
                throw new DataException(null);
            }
        }
        if (resultParam.getCode() != 0) {
            return resultParam;
        }
        WXPay wxpay = new WXPay(wxPayConfig);
        Map<String, String> data = new HashMap<>();
        data.put("appid", wxPayConfig.getAppID());
        data.put("mch_id", wxPayParam.getMachId());
        data.put("nonce_str", nonce_str);
        data.put("body", "????????????");
        data.put("out_trade_no", payOrderEntity.getFlowing_no());
//        data.put("fee_type", "CNY");
        data.put("total_fee", payOrderEntity.getAmount().multiply(BigDecimal.valueOf(100)).toBigInteger().toString());
        data.put("spbill_create_ip", ipAddr);
        data.put("notify_url", wxPayParam.getNotify_url());
        data.put("trade_type", SourceCode.valueOf(payOrderEntity.getSource()).getDesc());

        if ("JSAPI".equalsIgnoreCase(data.get("trade_type")) && !"teach_paypal".equals(payOrderEntity.getSource())) {
            String openid = this.getOpenIdByCode(payOrderEntity.getRcharge_valid());
            data.put("openid", openid);
            UserEntity entity = new UserEntity();
            entity.setUser_id(payOrderEntity.getUser_id());
            entity.setWx_openid(openid);
            userMapper.updateByPrimaryKeySelective(entity);
        }
        if ("JSAPI".equalsIgnoreCase(data.get("trade_type")) && "teach_paypal".equals(payOrderEntity.getSource())) {
            String openid = this.getTOpenIdByCode(payOrderEntity.getRcharge_valid());
            data.put("openid", openid);
            UserEntity entity = new UserEntity();
            entity.setUser_id(payOrderEntity.getUser_id());
            entity.setWx_openid(openid);
            userMapper.updateByPrimaryKeySelective(entity);
        }

        try {
            data.put("sign", WXPayUtil.generateSignature(data, wxPayParam.getKey(), WXPayConstants.SignType.MD5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> parameterMap2 = Maps.newHashMap();
        if (StringUtil.isEmpty(payOrderEntity.getRcharge_valid())) {
            try {
                logger.warn("wx unifiedOrder request is :" + JsonUtilFastjson.toJSONString(data));
                Map<String, String> resp = wxpay.unifiedOrder(data);
                logger.warn("wx unifiedOrder result is :" + JsonUtilFastjson.toJSONString(resp));
                if (!"SUCCESS".equalsIgnoreCase(resp.get("return_code"))) {
                    logger.info(JSONUtils.obj2Json(resp).toString());
                    throw new DataException(null);
//                throw new DataException(resp.get("return_msg"));
                }
                parameterMap2.put("appid", resp.get("appid"));
                parameterMap2.put("partnerid", resp.get("mch_id"));
                parameterMap2.put("prepayid", resp.get("prepay_id"));
                parameterMap2.put("package", ("teach_paypal".equals(payOrderEntity.getSource()) ? "prepay_id=" + resp.get("prepay_id") : "Sign=WXPay"));
                parameterMap2.put("noncestr", WXPayUtil.generateNonceStr());
                //parameterMap2.put("signType", "MD5");
                parameterMap2.put("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
                if ("NATIVE".equalsIgnoreCase(data.get("trade_type")) && resp.containsKey("code_url")) {
                    parameterMap2.put("qrcode", QRCodeUtil.createImageBase642(resp.get("code_url"), null, true));
                }
                parameterMap2.put("paySign", WXPayUtil.generateSignature(parameterMap2, wxPayParam.getKey(), WXPayConstants.SignType.MD5));
                parameterMap2.put("prepay_id", resp.get("prepay_id"));//
                logger.warn("wx result data is :" + JsonUtilFastjson.toJSONString(parameterMap2));
            } catch (Exception e) {
                e.printStackTrace();
                throw new DataException(null);
//            throw new DataException(e.getMessage() == null ? "??????????????????" : e.getMessage());
            }
        } else {
            try {
                logger.warn("wx unifiedOrder request is :" + JsonUtilFastjson.toJSONString(data));
                Map<String, String> resp = wxpay.unifiedOrder(data);
                logger.warn("wx unifiedOrder result is :" + JsonUtilFastjson.toJSONString(resp));
                if (!"SUCCESS".equalsIgnoreCase(resp.get("return_code"))) {
                    logger.info(JSONUtils.obj2Json(resp).toString());
                    throw new DataException(null);
//                throw new DataException(resp.get("return_msg"));
                }
                parameterMap2.put("appId", resp.get("appid"));
                parameterMap2.put("package", ("teach_paypal".equals(payOrderEntity.getSource()) ? "prepay_id=" + resp.get("prepay_id") : "Sign=WXPay"));
                parameterMap2.put("nonceStr", WXPayUtil.generateNonceStr());
                parameterMap2.put("timeStamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
                parameterMap2.put("signType", "MD5");

                parameterMap2.put("paySign", WXPayUtil.generateSignature(parameterMap2, wxPayParam.getKey(), WXPayConstants.SignType.MD5));
                parameterMap2.put("prepay_id", resp.get("prepay_id"));//
                logger.warn("wx result data is :" + JsonUtilFastjson.toJSONString(parameterMap2));
            } catch (Exception e) {
                e.printStackTrace();
                throw new DataException(null);
//            throw new DataException(e.getMessage() == null ? "??????????????????" : e.getMessage());
            }
        }
        return ResultUtil.success(parameterMap2);
    }

    private String getOpenIdByCode(String code) {
        com.alibaba.fastjson.JSONObject string = wxPayService.getSessionKeyOropenid(code);
        Map<String, Object> map = JSONUtils.obj2Map(string, null);
        //System.out.println(map.toString());
        return map.get("openid").toString();
    }

    private String getTOpenIdByCode(String code) {
        com.alibaba.fastjson.JSONObject string = wxPayService.getSessionKeyOropenidTAppid(code);
        Map<String, Object> map = JSONUtils.obj2Map(string, null);
        //System.out.println(map.toString());
        return map.get("openid").toString();
    }


    private Integer getType(String source) {
        Integer type = null;
        if ("pc,mobile".contains(source)) {
            type = 1;
        } else if ("sr".contains(source)) {
            type = 2;
        } else if ("android,ios".contains(source)) {
            type = 3;
        } else if ("teach_paypal".contains(source)) {
            type = 4;
        }
        return type;
    }

    /**
     * ??????h5??????
     * zwx
     * 2019???5???27???13:56:01
     *
     * @param payOrderEntity
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional
    public WxH5PayParam wxH5Pay(RechargeRecordEntity payOrderEntity, HttpServletRequest
            request, HttpServletResponse response) {
        String ipAddr = getIpAddr2(request);
        String flowing_no = CommonUtils.generateFlowingCode();
        payOrderEntity.setFlowing_no(flowing_no);
        WxH5PayParam param = new WxH5PayParam();
        payOrderEntity.setStatus(0);
        ResultParam resultParam = rechargeRecordService.insert(payOrderEntity, request, response);
        WxH5PayParam rest = null;
        if (resultParam != null && resultParam.getCode() == 0) {
            param.setAppid(wxPayParam.getAappId());
            param.setMch_id(wxPayParam.getMachId());
            param.setDevice_info("WEB");
            String randoms = CommonUtils.generateRandom(30, false, false, false,
                    false);
            param.setNonce_str(randoms);
            param.setBody("??????????????????");
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
            info.put("wap_url", "????????????");
            info.put("package_name", "????????????");
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
     * ??????
     *
     * @return
     * @throws AlipayApiException
     */
    @Override
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
     * ????????????
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws DataException
     */
    @Override
    public ResultParam aliTransfer(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        String string = null;
        try {
            String title = (entity.getRcharge_abstract() + entity.getAmount().toString());
            String flowing_no = CommonUtils.generateFlowingCode();
            entity.setFlowing_no(flowing_no);
            ResultParam resultParam = rechargeRecordService.insert(entity, request, response);
            if (resultParam.getCode() != 0) {
                throw new Exception("??????????????????");
            }
            RechargeInfoEntity rechargeInfoEntity = new RechargeInfoEntity();
            if (rechargeInfoEntity.getUser_id() != null && rechargeInfoEntity.getUser_id() != 0) {
                rechargeInfoEntity.setUser_id(rechargeInfoEntity.getUser_id());
            } else {
                rechargeInfoEntity.setMechanism_id(rechargeInfoEntity.getMechanism_id());
            }
            rechargeInfoEntity.setPageSize(1);
            rechargeInfoEntity = rechargeInfoMapper.queryListPage(rechargeInfoEntity).get(0);
            ResultParam resultParam1 = userInfoService.checkIsCanPay(entity);
            if (resultParam1.getCode() == 0) {
                string = aliPayService.aliTransfer(flowing_no, title, rechargeInfoEntity.getAliPay(), rechargeInfoEntity.getName(), rechargeInfoEntity.
                        getIdentity_type(), entity.getAmount(), entity.getUser_id());
                rechargeRecordService.insert(entity, request, response);
                return ResultUtil.success(string);
            } else {
                return resultParam1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public String H5aliAuth(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        String string = null;
        try {
            string = aliPayService.getAlipayAuthUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    @Override
    public String H5aliGetToken(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse
            response) {
        try {
            return aliPayService.getAccess_token(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    @Override
    public String H5aliPayAuthNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = getParam(request);
            JSONObject jsonObject = JSONObject.fromObject(params);
            logger.warn("alipay auth notify data is:" + jsonObject.toString());
            return aliPayService.insertAccessToken(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    @Override
    public ResultParam aliUserAuth(RechargeRecordEntity rechargeInfoEntity, HttpServletRequest
            request, HttpServletResponse response) {
        try {
            return ResultUtil.success(aliPayService.aliUserAuth(rechargeInfoEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public ResultParam aliUserAuthUrl(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse
            response) {
        try {
            return ResultUtil.success(aliPayService.getAliUserAuthUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    @Transactional
    public ResultParam wxTransfer(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse
            response) {
        if (StringUtil.isEmpty(entity.getPay_pass())) {
            return ResultUtil.error(OrderEum.order_70042.getCode(), OrderEum.order_70042.getDesc());
        }
//        String ipAddr = getIpAddr2(request);
        String flowing_no = CommonUtils.generateFlowingCode();
        entity.setFlowing_no(flowing_no);
        entity.setRcharge_type("transfer");
        entity.setRcharge_abstract("????????????");
        ResultParam resultParam = rechargeRecordService.insert(entity, request, response);
        try {
            ResultParam resultParam1 = userInfoService.checkIsCanPay(entity);
            if (resultParam1.getCode() != 0) {
                return resultParam1;
            }
            RechargeInfoEntity rechargeInfoEntity = new RechargeInfoEntity();
            if (rechargeInfoEntity.getUser_id() != null && rechargeInfoEntity.getUser_id() != 0) {
                rechargeInfoEntity.setUser_id(rechargeInfoEntity.getUser_id());
            } else {
                rechargeInfoEntity.setMechanism_id(rechargeInfoEntity.getMechanism_id());
            }
            rechargeInfoEntity.setPageSize(1);
            List<RechargeInfoEntity> rechargeInfoEntities = rechargeInfoMapper.queryListPage(rechargeInfoEntity);
            if (rechargeInfoEntities != null && rechargeInfoEntities.size() > 0) {
                wxPayParam.init();
                WXPay wxpay = new WXPay(wxPayParam);
                Map<String, String> data = new HashMap<String, String>();
                //app_id
//                param.setAppid(wxPayParam.getappId());
                //mch_appid
                data.put("mch_appid", wxPayParam.gettAppidH());
                //mch_id
                data.put("mchid", wxPayParam.getMchID());
                String randoms = CommonUtils.generateRandom(30, false, false, false,
                        false);
                //nonce_str
                data.put("nonce_str", randoms);
                //amount
                BigDecimal multiply = entity.getAmount().multiply(new BigDecimal("100")).setScale(0, ROUND_DOWN);
                data.put("amount", multiply.toString());
                //partner_trade_no
                data.put("partner_trade_no", randoms);
                //check_name
                data.put("check_name", "NO_CHECK");
                //wxopen_id
                data.put("openid", rechargeInfoEntities.get(0).getWxPay());
                //desc
                data.put("desc", "??????????????????");
                //sign
                String s1 = WXPayUtil.generateSignature(data, wxPayParam.getKey(), WXPayConstants.SignType.MD5);
                data.put("sign", s1);

                final String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
                String s = wxpay.requestWithCert(url, data, wxPayParam.getHttpConnectTimeoutMs(), wxPayParam.getHttpReadTimeoutMs());
                Map<String, String> map = xml2ToMap(s);
                if (map.containsKey("err_code_des")) {
                    return ResultUtil.error(OrderEum.order_70039.getCode(), map.get("err_code_des"));
                }

                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getUser_id());
                userInfoEntity.setCash(userInfoEntity.getCash().subtract(multiply));
                Integer integer = userInfoMapper.updateCash(userInfoEntity);
                if (integer > 0) {
                    entity.setFlowing_no(randoms);
                    entity.setAmount(multiply);
                    entity.setFinished(true);
                    entity.setStatus(2);
                    int i = rechargeRecordMapper.updateByPrimaryKeySelective(entity);
                    if (i > 0) {
                        ResultParam resultParam2 = userRecommenderIncomeLogService.insertTransfer(entity, request, response);
                        return ResultUtil.success();
                    }
                }
                throw new Exception("????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(OrderEum.order_70015.getCode(), OrderEum.order_70015.getDesc());
    }

    @Override
    public ResultParam aliPrepayment(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse
            response) throws AlipayApiException {
        MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
        mechanismEntity.setId(entity.getMechanism_id());
        mechanismEntity.setPageSize(1);
        mechanismEntity.setType("teach_paypal");
        mechanismEntity = masterMechanismService.queryListPage(mechanismEntity, request, response).get(0);
        entity.setAmount(this.getFinalAmount(entity));
        entity.setFlowing_no(CommonUtils.generateFlowingCode());
        entity.setOut_request_no(CommonUtils.generateFlowingCode());
        int i = rechargeRecordMapper.updateByPrimaryKeySelective(entity);
        if (i > 0) {
            if (entity.getAmount().equals(new BigDecimal("0"))) {
                String notify = this.getNotify(entity, request, response);
                if (notify.equals("success")) {
                    return ResultUtil.success();
                }
            }
            return aliPayService.aliPrepayment(entity, mechanismEntity.getPayee_logon_id());
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private BigDecimal getFinalAmount(RechargeRecordEntity entity) {
        BigDecimal finalAmount = entity.getAmount();
        if (entity.getCoupon_id() != null && entity.getCoupon_id() != 0) {
            finalAmount = commodityCouponService.getFinalAmount(entity);
        }
        if (entity.getPoints() != null && entity.getPoints() != 0 && finalAmount.compareTo(new BigDecimal("0")) > 0) {
            BigDecimal point = new BigDecimal(entity.getPoints()).divide(new BigDecimal("100"), 1);
            finalAmount = finalAmount.subtract(point);
        }
        return finalAmount;
    }

    @Override
    public ResultParam aliPrepayment2Pay(RechargeRecordEntity entity, HttpServletRequest
            request, HttpServletResponse response) {
        try {
//            MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(entity.getAppointment_id());
//            entity.setPageSize(1);
            List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(entity);
            if (list != null && list.size() > 0) {
                entity = list.get(0);
            } else {
                return ResultUtil.error(OrderEum.order_70013.getCode(), OrderEum.order_70013.getDesc());
            }

            userAppointmentService.updateStatus(entity, request, response);
            /*
            MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
            mechanismEntity.setId(masterAppointmentEntity.getMechanism_id());
            mechanismEntity.setPageSize(1);
            mechanismEntity = masterMechanismService.queryListPage(mechanismEntity, request, response).get(0);

             */

            return aliPayService.aliPrepayment2Pay(entity, aliPayParam.getPayeeLogonId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public void aliPrepayment2Cancel(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse
            response) {
        try {
            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setRcharge_type("prepayment");
            rechargeRecordEntity.setFinished(true);
            rechargeRecordEntity.setStatus(1);
            List<RechargeRecordEntity> list = rechargeRecordMapper.queryTimeOUtLIst(rechargeRecordEntity);
            if (list != null && list.size() > 0) {
                for (RechargeRecordEntity recordEntity : list) {
                    aliPayService.aliPrepayment2Cancel(recordEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultParam aliPrepaymentNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            // ??????????????????
            Map<String, String> params = getParam(request);
            if (params.get("status").equals("SUCCESS")) {
//                String notifyType = params.get("notify_type");
                String out_trade_no = params.get("out_order_no");
                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                rechargeRecordEntity.setFlowing_no(out_trade_no);
                rechargeRecordEntity.setPageSize(1);
                rechargeRecordEntity = rechargeRecordMapper.queryListPage(rechargeRecordEntity).get(0);
                rechargeRecordEntity.setPayer_userid(params.get("payer_user_id"));
                rechargeRecordEntity.setAuth_no(params.get("auth_no"));
                rechargeRecordEntity.setOperation_id(params.get("operation_id"));
                rechargeRecordEntity.setStatus(2);
                rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecordEntity);
                // ?????????????????????????????????????????????
                userAppointmentService.updateStatus(rechargeRecordEntity, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam payPre(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        if (entity.getUser_id() == null || entity.getUser_id() == 0) {
            return ResultUtil.error(OrderEum.order_70047.getCode(), OrderEum.order_70047.getDesc());
        }
        BigDecimal finalAmount = new BigDecimal("0");
        BigDecimal originalAmount = new BigDecimal("0");
        try {
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entity.getStudycard_id());
            if ("coupon_198".equals(entity.getRcharge_type())) {
                if (StringUtil.isNotEmpty(entity.getSelected_id())) {
                    String[] split = entity.getSelected_id().split(",");
                    UserCouponEntity userCouponEntity1 = new UserCouponEntity();
                    for (String s : split) {
                        MasterSetPriceEntity masterSetPriceEntity1 = masterSetPriceMapper.selectByPrimaryKey(Long.valueOf(s));
                        userCouponEntity1.setUser_id(entity.getUser_id());
                        userCouponEntity1.setType("voucher_redemption_12");
                        userCouponEntity1.setMechanism_id(masterSetPriceEntity1.getMechanism_id());
                        userCouponEntity1.setPageSize(1);
                        List<UserCouponEntity> userCouponEntities = userCouponMapper.queryListPage(userCouponEntity1);
                        if (userCouponEntities != null && userCouponEntities.size() > 0) {
                            return ResultUtil.error(UserEum.user_10065.getCode(), UserEum.user_10065.getDesc());
                        }
                    }
                }
            }

            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setUser_id(entity.getUser_id());
            if ("study_card".equalsIgnoreCase(entity.getRcharge_type())) {
                rechargeRecordEntity.setUser_appointment_id(entity.getUser_appointment_id());
            } else if ("coupon_177".equalsIgnoreCase(entity.getRcharge_type()) || "coupon_77".equalsIgnoreCase(entity.getRcharge_type())
            ) {
                rechargeRecordEntity.setRcharge_type(entity.getRcharge_type());
            }
            if ("mechanismOffline".equalsIgnoreCase(entity.getStudy_type())) {
                rechargeRecordEntity.setStudycard_id(entity.getStudycard_id());
                rechargeRecordEntity.setStudy_type(entity.getStudy_type());
            }
            if (StringUtil.isNotEmpty(entity.getType())) {
                rechargeRecordEntity.setType(entity.getType());
            }
            rechargeRecordEntity.setPageSize(1);
            List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
            if (list != null && list.size() > 0 && !"deductions_coupon".equalsIgnoreCase(entity.getRcharge_type())
                    && !"changeClasses".equalsIgnoreCase(entity.getRcharge_type()) && !"deposit".equalsIgnoreCase(entity.getRcharge_type())
                    && !"coupon_198".equalsIgnoreCase(entity.getRcharge_type())) {
                if (list.get(0).getStatus() == 2) {
                    return ResultUtil.error(UserEum.user_10055.getCode(), UserEum.user_10055.getDesc());
                }
            }

            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entity.getUser_study_card_id());
            /*
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setUser_id(entity.getUser_id());
            userAppointmentEntity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            ResultParam resultParam = userAppointmentService.insertMechanismUser(userAppointmentEntity, request, response);

             */

            /*
            if (resultParam.getCode()==0) {
                entity.setIs_free(true);
            }

             */

            if (masterSetPriceEntity != null) {
                entity.setCourse_num(masterSetPriceEntity.getCourse_num());
                entity.setMember_duration(masterSetPriceEntity.getDuration());
                entity.setTitle(masterSetPriceEntity.getTitle());
            }
            switch (entity.getRcharge_type()) {
                case "deposit":
                    RechargeRecordEntity rechargeRecordEntity1 = new RechargeRecordEntity();
                    rechargeRecordEntity1.setUser_id(entity.getUser_id());
                    rechargeRecordEntity1.setFinished(true);
                    rechargeRecordEntity1.setRcharge_type("deposit");
                    rechargeRecordEntity1.setPageSize(1);
                    rechargeRecordEntity1.setMember_level(0);
                    List<RechargeRecordEntity> list1 = rechargeRecordMapper.queryListPage(rechargeRecordEntity1);
                    if (list1 != null && list1.size() > 0) {
                        entity.setInvite_code(list1.get(0).getInvite_code());
                    }
                    finalAmount = businessActivityTypeMapper.selectByPrimaryKey(entity.getActivity_id().intValue()).getPay_deposit();
                    if (finalAmount.compareTo(new BigDecimal("0")) <= 0) {
                        return ResultUtil.error(OrderEum.order_70048.getCode(), OrderEum.order_70048.getDesc());
                    }
                    break;
                case "changeClasses":
                    if (!userStudyCardEntity.getUser_id().equals(entity.getUser_id())) {
                        return ResultUtil.error(UserEum.user_10064.getCode(), UserEum.user_10064.getDesc());
                    }
                    entity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
                    BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                    businessActivityEntity.setMaster_set_price_id(entity.getStudycard_id());
                    businessActivityEntity.setPageSize(1);
                    businessActivityEntity.setStatus(2);
                    List<BusinessActivityEntity> businessActivityEntities = businessActivityMapper.queryListPage(businessActivityEntity);
                    if (businessActivityEntities == null || businessActivityEntities.size() == 0) {
                        return ResultUtil.error(OrderEum.order_70035.getCode(), OrderEum.order_70035.getDesc());
                    }
                    finalAmount = userStudyCardEntity.getEach_lesson_price()
                            .subtract(masterSetPriceEntity.getOriginal_price())
                            .multiply(new BigDecimal("3"));
                    int i = finalAmount.compareTo(new BigDecimal("0"));
                    if (i == 0) {
                        String notify = this.getNotify(entity, request, response);
                        if (notify.equals("success")) {
                            return ResultUtil.error(OrderEum.order_70037.getCode(), OrderEum.order_70037.getDesc());
                        } else {
                            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                        }
                    } else if (i < 0) {
                        entity.setAmount(finalAmount.abs());
                        ResultParam resultParam = rechargeRecordService.tradeRefund(entity, request, response);
                        String notify = this.getNotify(entity, request, response);
                        if (notify.equals("success")) {
                            return ResultUtil.error(OrderEum.order_70037.getCode(), OrderEum.order_70037.getDesc());
                        } else {
                            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                        }
                    } else {
                        entity.setCoupon_id(0L);
                        entity.setPoints(0);
                    }
                    break;
                case "specialty_sheets":
                    finalAmount = userStudyCardEntity.getEach_lesson_price().multiply(
                            new BigDecimal(userStudyCardEntity.getCourse_num()));
                    if ("mechanism".equals(entity.getPaying_for_identity())) {
                        finalAmount = finalAmount.multiply(new BigDecimal("0.25"));
                    }
                    break;
                case "study_card":
                    if (StringUtil.isNotEmpty(entity.getType())) {
                        if ("live_stream_full".equalsIgnoreCase(entity.getType())) {
                            MasterSetPriceDisplayEntity masterSetPriceDisplayEntity = new MasterSetPriceDisplayEntity();
                            masterSetPriceDisplayEntity.setMaster_set_price_id(entity.getStudycard_id());
                            masterSetPriceDisplayEntity.setLive_streaming_id(entity.getLive_streaming_id());
                            masterSetPriceDisplayEntity.setPageSize(1);
                            masterSetPriceDisplayEntity = masterSetPriceDisplayMapper.queryListPage(masterSetPriceDisplayEntity).get(0);
                            /*
                            if ("live_stream_single".equalsIgnoreCase(entity.getType())) {
                                finalAmount = masterSetPriceDisplayEntity.getLiving_single_session_price();
                                if (finalAmount.compareTo(new BigDecimal("0")) == 0) {
                                    finalAmount = masterSetPriceEntity.getOriginal_price();
                                }
                                entity.setCoupon_id(0L);
                                entity.setPoints(0);
                                entity.setCourse_num(masterSetPriceEntity.getCourse_num());
                            } else if ("live_stream_full".equalsIgnoreCase(entity.getType())) {
                             */

                            finalAmount = masterSetPriceDisplayEntity.getLive_stream_price();
                        } else if ("single_live_session_deposit".equalsIgnoreCase(entity.getType())) {
                            if (entity.getStudycard_id() != null) {
                                UserStudyCardEntity userStudyCardEntity2 = new UserStudyCardEntity();
                                userStudyCardEntity2.setUser_id(entity.getUser_id());
                                userStudyCardEntity2.setStudycard_id(entity.getStudycard_id());
                                userStudyCardEntity2.setPageSize(1);
                                List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity2);
                                if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {
//                                    return ResultUtil.error(UserEum.user_10052.getCode(), UserEum.user_10052.getDesc());
                                    //todo
                                }
                            }
                            MasterSetPriceDisplayEntity masterSetPriceDisplayEntity = new MasterSetPriceDisplayEntity();
                            masterSetPriceDisplayEntity.setMaster_set_price_id(entity.getStudycard_id());
                            masterSetPriceDisplayEntity.setLive_streaming_id(entity.getLive_streaming_id());
                            masterSetPriceDisplayEntity.setPageSize(1);
                            masterSetPriceDisplayEntity = masterSetPriceDisplayMapper.queryListPage(masterSetPriceDisplayEntity).get(0);
                            finalAmount = masterSetPriceDisplayEntity.getLiving_single_session_price();
                            finalAmount = finalAmount.multiply(new BigDecimal("3"));
                            entity.setCoupon_id(0L);
                            entity.setPoints(0);
                        } else if ("single_session_deposit".equalsIgnoreCase(entity.getType())) {
                            finalAmount = masterSetPriceEntity.getOriginal_price();
                            finalAmount = finalAmount.multiply(new BigDecimal("3"));
                            entity.setCoupon_id(0L);
                            entity.setPoints(0);
                        } else {
                            return ResultUtil.error(UserEum.user_10060.getCode(), UserEum.user_10060.getDesc());
                        }
                    } else {
                        if (!entity.getIs_one_time_payment()) {
                            finalAmount = masterSetPriceEntity.getDiscount_amout();
                            if (finalAmount.compareTo(new BigDecimal("0")) == 0) {
                                finalAmount = masterSetPriceEntity.getDefault_discount_price();
                            }
//                            entity.setCoupon_id(0L);
//                            entity.setPoints(0);
                            entity.setCourse_num(masterSetPriceEntity.getCourse_num());
                        } else {
                            finalAmount = userStudyCardEntity.getEach_lesson_price();
                            entity.setCourse_num(1);
                        }
                        if (masterSetPriceEntity.getIs_attend_activities()) {
                            entity.setType("activity_course");
                        }
                    }
                    originalAmount = finalAmount;
                    entity.setAmount(finalAmount);

                    if (entity.getCoupon_id() != null || (entity.getPoints() != null && entity.getPoints() != 0) || StringUtil.isNotEmpty(entity.getCoupon_ids())) {
                        finalAmount = commodityCouponService.getFinalAmount(entity);
                        if (finalAmount.compareTo(new BigDecimal("0")) <= 0) {
                            ResultParam resultParam = ResultUtil.success();
                            try {
                                rechargeRecordEntity.setCoupon_id(Long.parseLong(rechargeRecordEntity.getCoupon_ids()));
                                rechargeRecordEntity.setIs_teach_paypal(true);
                                resultParam = userCouponService.insertUserStudyCardByCoup(rechargeRecordEntity, request, response);
                            } catch (Exception ignored) {
                            }

                            if (resultParam.getCode() == 0) {
                                entity.setType("voucher_redemption");
                                entity.setRcharge_abstract("???????????????");
                                return ResultUtil.error(OrderEum.order_70037.getCode(), OrderEum.order_70037.getDesc());
                            }
                            String notify = this.getNotify(entity, request, response);
                            if (notify.equals("success")) {
                                return ResultUtil.error(OrderEum.order_70037.getCode(), OrderEum.order_70037.getDesc());
                            } else {
                                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                            }
                        }
                    }
                    if (entity.getPoints() != null && entity.getPoints() != 0) {
                        finalAmount = finalAmount.subtract(new BigDecimal(entity.getPoints() / 100 + ""));
                        if (finalAmount.compareTo(originalAmount.multiply(new BigDecimal("0.75"))) <= 0) {
                            return ResultUtil.error(UserEum.user_10058.getCode(), UserEum.user_10058.getDesc());
                        }
                    }
                    break;
                /**
                 * ????????????
                 */
                case "coupon_177":
                case "coupon_198":

                    Integer course_num = 0;
                    if (entity.getCourse_num() != null) {
                        course_num = entity.getCourse_num();
                    }
                    String[] split = new String[0];
                    if (StringUtil.isNotEmpty(entity.getSelected_id())) {
                        split = entity.getSelected_id().split(",");
                    }

                    if (course_num > 1 || (entity.getIs_one_time_payment() != null && entity.getIs_one_time_payment())) {
                        finalAmount = businessActivityTypeService.queryNeedPay(entity, request, response);
                        if (finalAmount.compareTo(new BigDecimal("0"))<=0) {
                            return ResultUtil.error(OrderEum.order_70046.getCode(), OrderEum.order_70046.getDesc());
                        }
                    } else if (split.length < course_num) {
                        return ResultUtil.error(OrderEum.order_70045.getCode(), OrderEum.order_70045.getDesc());
                    } else {
                        return ResultUtil.error(OrderEum.order_70044.getCode(), OrderEum.order_70044.getDesc());
                    }

                    if (entity.getActivity_id() != null && entity.getActivity_id() != 0) {
                        finalAmount = userInfoService.checkPayDeposit(entity.getUser_id(), finalAmount);
                    }
//                    finalAmount = new BigDecimal("0.01");
                    break;
                /**
                 * ??????????????????
                 */
                case "coupon_77":
                    /*
                    Integer count = this.queryCoupNum(entity);
                    if (count==1||count>3){
                        retu  rn ResultUtil.error(OrderEum.order_70027.getCode(),OrderEum.order_70027.getDesc());
                    }

                     */
                    finalAmount = new BigDecimal("77");
//                    finalAmount = new BigDecimal("0.01");
                    break;
                /**
                 * ?????????
                 */
                case "deductions_coupon":
                    /*
                    Integer count = this.queryCoupNum(entity);
                    if (count==1||count>3){
                        return ResultUtil.error(OrderEum.order_70027.getCode(),OrderEum.order_70027.getDesc());
                    }

                     */
                    finalAmount = new BigDecimal("99");
//                    finalAmount = new BigDecimal("0.01");
                    break;
                default:
                    return ResultUtil.error(OrderEum.order_70021.getCode(), OrderEum.order_70021.getDesc());
            }
            entity.setAmount(finalAmount);
            if (entity.getPay_type().equals("ali")) {
                return ResultUtil.success(this.aliPay(entity, request, response));
            } else {
                return this.wxPay(entity, request, response);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private Integer queryCoupNum(RechargeRecordEntity entity) {
        UserCouponEntity userCouponEntity = new UserCouponEntity();
        userCouponEntity.setUser_id(entity.getUser_id());
        userCouponEntity.setType("voucher_redemption");
        return userCouponMapper.queryListPageCount(userCouponEntity);

    }

    @Override
    @Transactional
    public synchronized ResultParam fundAuthOrderUnFreeze(RechargeRecordEntity entity, HttpServletRequest
            request, HttpServletResponse response) throws AlipayApiException {
        if (StringUtil.isEmpty(entity.getOperation_id()) && StringUtil.isEmpty(entity.getAuth_no())) {
            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setAppointment_id(entity.getAppointment_id());
            rechargeRecordEntity.setUser_id(entity.getUser_id());
            rechargeRecordEntity.setPageSize(1);
            rechargeRecordEntity.setFinished(true);
            List<RechargeRecordEntity> list = rechargeRecordMapper.queryTimeOUtLIst(rechargeRecordEntity);
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            userStudyCardEntity.setId(entity.getId());
            userStudyCardMapper.updateAddCourseNum(userStudyCardEntity);
            if (list != null && list.size() > 0) {
                for (RechargeRecordEntity recordEntity : list) {
                    recordEntity.setFinished(false);
                    aliPayService.aliPrepayment2Cancel(recordEntity);
                    rechargeRecordMapper.updateByPrimaryKeySelective(recordEntity);

                }
                if (entity.getCoupon_id() != null && entity.getCoupon_id() != 0) {
                    int i = userCouponMapper.updateStatusUnUsed(entity.getCoupon_id());
                }
                if (entity.getPoints() != null && entity.getPoints() != 0) {
                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                    userInfoEntity.setUser_id(entity.getUser_id());
                    userInfoEntity.setPoints(entity.getPoints());
                    int i = userInfoMapper.updateAddPoint(userInfoEntity);
                }
            }
        } else {
            aliPayService.aliPrepayment2Cancel(entity);
        }
        return ResultUtil.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    String getNotify(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            param.setFlowing_no(CommonUtils.generateFlowingCode());
            param.setPay_type("ali");
            param.setStatus(0);
            ResultParam insert = rechargeRecordService.insert(param, request, response);
            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setFlowing_no(param.getFlowing_no());
            rechargeRecordEntity.setPageSize(1);
            List<RechargeRecordEntity> entities = rechargeRecordService.queryListPage(rechargeRecordEntity, request, response);
            if (entities != null && entities.size() > 0) {
                rechargeRecordEntity.setId(entities.get(0).getId());
                rechargeRecordEntity.setTrans_no(param.getFlowing_no());
                rechargeRecordEntity.setFinished(true);
                rechargeRecordEntity.setFinished_time(new Date());
                rechargeRecordEntity.setStatus(2);
                ResultParam resultParam = rechargeRecordService.update(rechargeRecordEntity, request, response);
                if (resultParam.getCode() == 0) {
                    if ("member".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//????????????
                        resultParam = iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                        if (resultParam != null && resultParam.getCode() == 0) {
                            UserInfoEntity entity = new UserInfoEntity();
                            entity.setUser_id(entities.get(0).getUser_id());
                            entity.setMember_level(entities.get(0).getMember_level());
                            entity.setIs_member(true);
                            int rets = userInfoMapper.updateByPrimaryKeySelective(entity);
                            if (!(rets > 0)) {
                                return "failure";
                            }
                        } else {
                            return "failure";
                        }
                    } else if ("join_live_class".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//???????????????
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(entities.get(0).getUser_id());
                        userInfoEntity.setPageSize(1);
                        List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);
                        if (userInfoEntities != null && userInfoEntities.size() > 0) {       //?????????????????????
                            ClassCardEntity classCardEntity = ClassCardService.findById(entities.get(0).getClass_card_id(), request, response);
                            if (classCardEntity != null) {
                                UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                                userClassCardEntity.setType(classCardEntity.getType());
                                userClassCardEntity.setPageSize(1);
                                List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                    //????????????????????????????????????
                                    userClassCardEntity = userClassCardEntities.get(0);
                                    //????????????
                                    Date dt = new Date();
                                    Calendar rightNow = Calendar.getInstance();
                                    rightNow.setTime(dt);
                                    rightNow.add(Calendar.YEAR, 1);
                                    Date dt1 = rightNow.getTime();
                                    userClassCardEntity.setExpire_time(dt1);
                                    if (userClassCardEntity.getStatus() == 1) {
                                        userClassCardEntity.setCurriculum_num(classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum());
                                        userClassCardEntity.setStatus(2);
                                    } else {
                                        int integer = 0;
                                        integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum() + userClassCardEntity.getCurriculum_num();
                                        userClassCardEntity.setCurriculum_num(integer);
                                    }
                                    UserClassCardEntity entity = new UserClassCardEntity();
                                    entity.setUser_id(userClassCardEntity.getUser_id());
                                    entity.setDefault_use(false);
                                    int ret = userClassCardMapper.updateDefaultUse(entity);
                                    if (ret > 0) {
                                        userClassCardEntity.setDefault_use(true);
                                        userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                    }
                                } else {
                                    Date dt = new Date();
                                    Calendar rightNow = Calendar.getInstance();
                                    rightNow.setTime(dt);
                                    rightNow.add(Calendar.YEAR, 1);
                                    Date dt1 = rightNow.getTime();
                                    userClassCardEntity.setExpire_time(dt1);
                                    int integer = 0;
                                    integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum();
                                    userClassCardEntity.setCurriculum_num(integer);
                                    userClassCardEntity.setDefault_use(true);
                                    UserClassCardEntity entity = new UserClassCardEntity();
                                    entity.setUser_id(userClassCardEntity.getUser_id());
                                    entity.setDefault_use(false);
                                    int ret = userClassCardMapper.updateDefaultUse(entity);
                                    if (ret > 0) {
                                        userClassCardEntity.setDefault_use(true);
                                        userClassCardMapper.insertSelective(userClassCardEntity);
                                    }
                                }
                            } else {
                                return "failure";
                            }
                        }
                    } else if ("account".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//????????????
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(entities.get(0).getUser_id());
                        userInfoEntity.setPageSize(1);
                        List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);
                        if (userInfoEntities != null && userInfoEntities.size() > 0) {
                            BigDecimal bignum1 = new BigDecimal("100");
                            userInfoEntity.setCat_coin(entities.get(0).getAmount().multiply(bignum1));
                            int retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                            if (!(retupdateus > 0)) {
                                return "failure";
                            } else {
                                //????????????
                                UserOrderEntity orderEntity = new UserOrderEntity();
                                orderEntity.setPayment_id(userInfoEntity.getUser_id());
                                orderEntity.setType("recharge");
                                orderEntity.setSource("alipay");
                                orderEntity.setStatus(2);
                                orderEntity.setPay_count(entities.get(0).getAmount().multiply(bignum1));
                                orderService.insert(orderEntity, request, response);
                                //??????
                                orderEntity.setPageSize(1);
                                Long i = orderService.queryListPage(orderEntity, request, response).get(0).getUser_id();
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(i);
                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                pushMessageParam.setPush_time(new Date());
                                pushMessageParam.setType("pay");
                                pushMessageParam.setOpera_type("pay");
                                pushMessageParam.setContent("????????????");
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            }
                        }
                    } else if ("study_card".equalsIgnoreCase(entities.get(0).getRcharge_type())) {//???????????????
                        if ("mechanism_offline".equals(entities.get(0).getStudy_type())) {
                            if (entities.get(0).getIs_one_time_payment() || "live_stream_single".equals(entities.get(0).getType())) {
//                                        masterMechanismService.updateOfflineCash(entities.get(0),request,response);

                                if (entities.get(0).getUser_study_card_id() != 0) {
                                    UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entities.get(0).getUser_study_card_id());
                                    userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                                }

                                if (entities.get(0).getPoints() != 0) {
                                    UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entities.get(0).getUser_id());
                                    userInfoEntity.setPoints(entities.get(0).getPoints());
                                    userInfoMapper.updateSubPoint(userInfoEntity);
                                    UserPointsEntity userPointsEntity = new UserPointsEntity();
                                    userPointsEntity.setUsre_id(entities.get(0).getUser_id());
                                    userPointsEntity.setPoint(-entities.get(0).getPoints());
                                    userPointsEntity.setType("buy_course");
                                    int i2 = userPointsMapper.insertSelective(userPointsEntity);
                                }
                                this.updateSigleCourse(entities.get(0));
                                userAppointmentService.updateStatus(entities.get(0), request, response);
                                ResultParam resultParam1 = userGroupingService.updateSettlementCash(entities.get(0), request, response);
                            } else {
                                userCouponService.updateCoupStatus(entities.get(0), request, response);
                                this.updatePayNum(entities.get(0));
                                MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entities.get(0).getStudycard_id());
                                UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                                userStudyCardEntity.setMechanism_id(entities.get(0).getMechanism_id());
                                userStudyCardEntity.setUser_id(entities.get(0).getUser_id());
                                userStudyCardEntity.setType(masterSetPriceEntity.getType());
                                userStudyCardEntity.setStudycard_id(entities.get(0).getStudycard_id());
                                userStudyCardEntity.setCourse_num(entities.get(0).getCourse_num());
                                userStudyCardEntity.setIs_experience(masterSetPriceEntity.getIs_interoperability());
                                if (masterSetPriceEntity.getIs_interoperability()) {
                                    userStudyCardEntity.setEach_lesson_price(entities.get(0).getAmount());
                                }
                                //????????????+?????????
                                Date dt = new Date();
                                Calendar rightNow = Calendar.getInstance();
                                rightNow.setTime(dt);
                                rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());
                                Date dt1 = rightNow.getTime();
                                userStudyCardEntity.setEnd_time(dt1);
                                userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num());
                                userStudyCardEntity.setWhether_grouping(this.queryIsCanGroupIng(entities.get(0).getStudycard_id()));
                                userStudyCardEntity.setIs_teach_paypal(true);
                                userStudyCardEntity.setIs_one_time_payment(true);
                                int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
                                if (rets > 0) {
                                    masterMechanismService.updateCash(entities.get(0), request, response);
                                }
                                entities.get(0).setUser_study_card_id(userStudyCardEntity.getId());
                                rechargeRecordMapper.updateByPrimaryKeySelective(entities.get(0));

                                userAppointmentService.updateStatus(entities.get(0), request, response);
                                this.updateFullCourse(entities.get(0));
                                return "success";
                            }

                            /**
                             * ?????????????????????
                             */
                            if (entities.get(0).getIs_experience()) {
                                this.updatePayNum(entities.get(0));
                                UserStudyCardEntity userStudyCardEntity1 = new UserStudyCardEntity();
                                userStudyCardEntity1.setUser_id(entities.get(0).getUser_id());
                                userStudyCardEntity1.setIs_experience(true);
                                userStudyCardEntity1.setPageSize(1);
                                userStudyCardEntity1 = userStudyCardMapper.queryListPage(userStudyCardEntity1).get(0);

                                userStudyCardEntity1.setCourse_num((entities.get(0).getCourse_num() - 8 + userStudyCardEntity1.getCourse_num()));
                                userStudyCardEntity1.setStatus(2);
                                Date dt = new Date();
                                Calendar rightNow = Calendar.getInstance();
                                rightNow.setTime(dt);
                                rightNow.add(Calendar.MONTH, 12);
                                Date dt1 = rightNow.getTime();
                                userStudyCardEntity1.setEnd_time(dt1);
                                userStudyCardEntity1.setStart_time(dt);
                                userStudyCardEntity1.setStatus(2);
                                userStudyCardEntity1.setIs_experience(false);
                                userStudyCardEntity1.setOriginal_course_num(entities.get(0).getCourse_num());
                                userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity1);
                                return "success";
                            }

                            if (entities.get(0).getIs_one_time_payment()) {
//                                    this.updatePayNum(entities.get(0));
                                masterAppointmentService.updateSingleCourseCash(entities.get(0), request, response);
                                return "success";
                            }

                            UserInfoEntity userInfoEntity = new UserInfoEntity();
                            userInfoEntity.setUser_id(entities.get(0).getUser_id());//??????userid
                            userInfoEntity.setPageSize(1);//??????userid
                            List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);//??????userinfo
                            if (userInfoEntities != null && userInfoEntities.size() > 0) {//?????????????????????
                                UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                                userStudyCardEntity.setType(entities.get(0).getStudy_type());//????????????
                                userStudyCardEntity.setUser_id(userInfoEntity.getUser_id());//userid
                                userStudyCardEntity.setStatus(2);//??????
                                userStudyCardEntity.setPageSize(1);//1???
                                List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);//???????????????
                                if (userStudyCardEntities != null && userStudyCardEntities.size() > 0 && entities.get(0).getMaster_id() == 0 && entities.get(0).getMechanism_id() == 0) {//??????????????????
                                    userStudyCardEntity = userStudyCardEntities.get(0);
                                    //????????????
                                    if (userStudyCardEntity.getStatus() == 1) {//????????????1
                                        Date dt = new Date();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());// getMember_duration??????????????????(???)
                                        Date dt1 = rightNow.getTime();
                                        userStudyCardEntity.setEnd_time(dt1);
                                        userStudyCardEntity.setStart_time(dt);
                                    } else {
                                        Date dt = userStudyCardEntity.getEnd_time();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());// getMember_duration??????????????????(???)
                                        Date dt1 = rightNow.getTime();
                                        userStudyCardEntity.setEnd_time(dt1);
                                    }
                                    Integer courceCount = userStudyCardEntity.getCourse_num() + entities.get(0).getCourse_num();
                                    userStudyCardEntity.setCourse_num(courceCount);
                                    this.updateCash(entities.get(0), request, response);
                                    userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num() + userStudyCardEntity.getOriginal_course_num());
                                    int rets = userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity);
                                    if (rets > 0) {
                                        if (entities.get(0).getMember_level() != 0) {
                                            //????????????
                                            //iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                        }
                                        /*//??????
                                        StudyPriceEntity studyPriceEntity = new StudyPriceEntity();
                                        studyPriceEntity.setType(entities.get(0).getStudy_type());
                                        studyPriceEntity.setCourse_num(entities.get(0).getCourse_num());
                                        studyPriceEntity.setDuration(entities.get(0).getMember_duration());
                                        studyPriceEntity.setPageSize(1);
                                        List<StudyPriceEntity> studyPriceEntities = studyPriceMapper.queryListPage(studyPriceEntity);
                                        if (studyPriceEntities != null && studyPriceEntities.size() > 0) {
                                            userInfoEntity.setCat_coin(studyPriceEntities.get(0).getGive_coin());
                                            int retupdateus = 0;
                                            synchronized (userInfoEntity) {
                                                retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                                            }
                                            if (!(retupdateus > 0)) {
                                                return "failure";
                                            } else {
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                                pushMessageParam.setContent("????????????,??????" + entities.get(0).getAmount().multiply(studyPriceEntities.get(0).getGive_coin()) + "??????");
                                                pushMessageParam.setPush_time(new Date());
                                                pushMessageParam.setType("pay");
                                                pushMessageParam.setOpera_type("pay");
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);//??????
                                                UserOrderEntity orderEntity = new UserOrderEntity();
                                                orderEntity.setPayment_id(userInfoEntity.getUser_id());
                                                orderEntity.setType("recharge");
                                                orderEntity.setSource("alipay");
                                                orderEntity.setStatus(2);
                                                orderEntity.setPay_count(studyPriceEntities.get(0).getGive_coin());
                                                orderService.insert(orderEntity, request, response);//????????????
                                            }
                                        }*/
                                    }
                                } else {
                                    if (entities.get(0).getMaster_id() != 0) {
                                        userStudyCardEntity.setMaster_id(entities.get(0).getMaster_id());
                                        masterInfoService.updateCash(entities.get(0), request, response);
                                        this.updatePayNum(entities.get(0));
                                    }
                                    if (entities.get(0).getMechanism_id() != 0) {
                                        userStudyCardEntity.setMechanism_id(entities.get(0).getMechanism_id());
                                        masterMechanismService.updateCash(entities.get(0), request, response);
                                        this.updatePayNum(entities.get(0));
                                    }
                                    MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entities.get(0).getStudycard_id());
                                    userStudyCardEntity.setStudycard_id(entities.get(0).getStudycard_id());
                                    userStudyCardEntity.setCourse_num(entities.get(0).getCourse_num());
                                    //????????????+?????????
                                    Date dt = new Date();
                                    Calendar rightNow = Calendar.getInstance();
                                    rightNow.setTime(dt);
                                    rightNow.add(Calendar.MONTH, entities.get(0).getMember_duration());
                                    Date dt1 = rightNow.getTime();
                                    userStudyCardEntity.setEnd_time(dt1);
                                    userStudyCardEntity.setOriginal_course_num(entities.get(0).getCourse_num());
                                    this.updateCash(entities.get(0), request, response);
                                    userStudyCardEntity.setBinding_mechanism_id(entities.get(0).getBinding_mechanism_id() ==
                                            null ? 0 : entities.get(0).getBinding_mechanism_id());
                                    userStudyCardEntity.setIs_Interoperability(masterSetPriceEntity.getIs_interoperability());
                                    userStudyCardEntity.setIs_teach_paypal(true);
                                    int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
                                    entities.get(0).setUser_study_card_id(userStudyCardEntity.getId());
                                    rechargeRecordMapper.updateByPrimaryKeySelective(entities.get(0));
                                    if (rets > 0) {
                                        /*if (entities.get(0).getMember_level() != 0) {
                                            //????????????
                                            iosPayService.updateMember(entities.get(0).getUser_id(), entities.get(0).getMember_level(), entities.get(0).getMember_duration());
                                        }
                                        //??????
                                        StudyPriceEntity studyPriceEntity = new StudyPriceEntity();
                                        studyPriceEntity.setType(entities.get(0).getStudy_type());
                                        studyPriceEntity.setCourse_num(entities.get(0).getCourse_num());
                                        studyPriceEntity.setDuration(entities.get(0).getMember_duration());
                                        studyPriceEntity.setPageSize(1);
                                        List<StudyPriceEntity> studyPriceEntities = studyPriceMapper.queryListPage(studyPriceEntity);
                                        if (studyPriceEntities != null && studyPriceEntities.size() > 0) {
                                            userInfoEntity.setCat_coin(studyPriceEntities.get(0).getGive_coin());
                                            int retupdateus = 0;
                                            synchronized (userInfoEntity) {
                                                retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                                            }
                                            if (!(retupdateus > 0)) {
                                                return "failure";
                                            } else {
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                                pushMessageParam.setContent("????????????,??????" + studyPriceEntities.get(0).getGive_coin() + "??????");
                                                pushMessageParam.setPush_time(new Date());
                                                pushMessageParam.setType("pay");
                                                pushMessageParam.setOpera_type("pay");
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                                                UserOrderEntity orderEntity = new UserOrderEntity();
                                                orderEntity.setPayment_id(entities.get(0).getUser_id());
                                                orderEntity.setType("recharge");
                                                orderEntity.setSource("alipay");
                                                orderEntity.setStatus(2);
                                                orderEntity.setPay_count(studyPriceEntities.get(0).getGive_coin());
                                                orderService.insert(orderEntity, request, response);
                                            }
                                        }*/

                                    }
                                }
                            }
                        }
                    } else if ("changeClasses".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                        ResultParam resultParam1 = userStudyCardService.UpdateProductInfo(entities.get(0), request, response);
                    } else if ("experience_volume".equalsIgnoreCase(entities.get(0).getRcharge_type())) {
                        CommodityCouponEntity commodityCouponEntity = new CommodityCouponEntity();
                        commodityCouponEntity.setUser_id(entities.get(0).getUser_id());
//                            commodityCouponEntity.setType("classexperience");
                        commodityCouponEntity.setId(entities.get(0).getCoupon_id());
                        commodityCouponService.ReceiveCoupon(commodityCouponEntity, request, response);
                    } else if ("class_card".equalsIgnoreCase(entities.get(0).getRcharge_type())) {

                        if (entities.get(0).getIs_experience()) {
                            int course_num = 0;
                            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                            userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                            userClassCardEntity.setType(entities.get(0).getStudy_type());
                            userClassCardEntity.setPageSize(1);
                            userClassCardEntity = userClassCardMapper.queryListPage(userClassCardEntity).get(0);
                            course_num = userClassCardEntity.getCurriculum_num();

                            UserClassCardEntity userClassCardEntity1 = new UserClassCardEntity();
                            userClassCardEntity1.setUser_id(userClassCardEntity.getUser_id());
                            userClassCardEntity1.setType(userClassCardEntity.getType());
                            userClassCardEntity1.setIs_experience(false);
                            userClassCardEntity1.setPageSize(1);
                            List<UserClassCardEntity> list = userClassCardMapper.queryListPage(userClassCardEntity1);
                            if (list != null && list.size() > 0) {
                                userClassCardMapper.deleteByPrimaryKey(userClassCardEntity.getId());
                                userClassCardEntity = list.get(0);
                            } else {
                                userClassCardEntity.setIs_experience(false);
                            }
                            userClassCardEntity.setStatus(2);
                            userClassCardEntity.setCurriculum_num(entities.get(0).getCourse_num() - (8 - course_num) + userClassCardEntity.getCurriculum_num());
                            Date dt = new Date();
                            Calendar rightNow = Calendar.getInstance();
                            rightNow.setTime(dt);
                            rightNow.add(Calendar.MONTH, 12);
                            Date dt1 = rightNow.getTime();
                            userClassCardEntity.setExpire_time(dt1);

                            int i = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                            if (i > 0) {
                                return "success";
                            }
                        } else {
                            UserInfoEntity userInfoEntity = new UserInfoEntity();
                            userInfoEntity.setUser_id(entities.get(0).getUser_id());
                            List<UserInfoEntity> userInfoEntities = userInfoMapper.query(userInfoEntity);
                            if (userInfoEntities != null && userInfoEntities.size() > 0) {       //?????????????????????
                                ClassCardEntity classCardEntity = ClassCardService.findById(entities.get(0).getClass_card_id(), request, response);
                                if (classCardEntity != null) {
                                    this.updateCash(entities.get(0), request, response);
                                    UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                    userClassCardEntity.setUser_id(entities.get(0).getUser_id());
                                    userClassCardEntity.setType(classCardEntity.getType());
                                    userClassCardEntity.setPageSize(1);
                                    List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                    if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                        //????????????????????????????????????
                                        userClassCardEntity = userClassCardEntities.get(0);
                                        //????????????
                                        Date dt = new Date();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.YEAR, 1);
                                        Date dt1 = rightNow.getTime();
                                        userClassCardEntity.setExpire_time(dt1);
                                        if (userClassCardEntity.getStatus() == 1) {
                                            if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                userClassCardEntity.setMinute_num(classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute());
                                            } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                userClassCardEntity.setCurriculum_num(classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum());
                                            }
                                            userClassCardEntity.setStatus(2);
                                        } else {
                                            int integer = 0;
                                            if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                                integer = classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute() + userClassCardEntity.getMinute_num();
                                                userClassCardEntity.setMinute_num(integer);
                                            } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum() + userClassCardEntity.getCurriculum_num();
                                                userClassCardEntity.setCurriculum_num(integer);
                                            }
                                        }
                                        UserClassCardEntity entity = new UserClassCardEntity();
                                        entity.setUser_id(userClassCardEntity.getUser_id());
                                        entity.setDefault_use(false);
                                        int ret = userClassCardMapper.updateDefaultUse(entity);
                                        if (ret > 0) {
                                            userClassCardEntity.setDefault_use(true);
                                            userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                        }
                                    } else {
                                        Date dt = new Date();
                                        Calendar rightNow = Calendar.getInstance();
                                        rightNow.setTime(dt);
                                        rightNow.add(Calendar.YEAR, 1);
                                        Date dt1 = rightNow.getTime();
                                        userClassCardEntity.setExpire_time(dt1);
                                        int integer = 0;
                                        if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute??????/curriculum??????
                                            integer = classCardEntity.getMinute_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_minute();
                                            userClassCardEntity.setMinute_num(integer);
                                        } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                            integer = classCardEntity.getCurriculum_num() * entities.get(0).getCourse_num() + classCardEntity.getGive_curriculum();
                                            userClassCardEntity.setCurriculum_num(integer);
                                        }
                                        UserClassCardEntity entity = new UserClassCardEntity();
                                        entity.setUser_id(userClassCardEntity.getUser_id());
                                        entity.setDefault_use(false);
                                        userClassCardMapper.updateDefaultUse(entity);
                                        userClassCardEntity.setDefault_use(true);
                                        userClassCardMapper.insertSelective(userClassCardEntity);
                                    }
                                }
                            } else {
                                return "failure";
                            }
                        }
                    } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("prepayment")) {
                        userAppointmentService.updateStatus(entities.get(0), request, response);

                    } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("offline_class")) {
                        UserAppointmentEntity userAppointmentEntity = userAppointmentService.findById(entities.get(0).getAppointment_id(), request, response);
                        if (userAppointmentEntity != null) {
                            userAppointmentEntity.setStatus(2);
                            userAppointmentMapper.updateByPrimaryKeySelective(userAppointmentEntity);
                        } else {
                            logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                            return "failure";
                        }

                    } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("specialty_sheets")) {
                        RechargeRecordEntity rechargeRecordEntity1 = new RechargeRecordEntity();
                        rechargeRecordEntity1.setUser_study_card_id(entities.get(0).getUser_study_card_id());
                        List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(rechargeRecordEntity1);
                        for (RechargeRecordEntity recordEntity : list) {
                            recordEntity.setIs_turning_long_orders(true);
                            rechargeRecordMapper.updateByPrimaryKeySelective(recordEntity);
                        }
                        rechargeRecordService.updateStudyCardStatus(entities.get(0), request, response);
                    } else if (entities.get(0).getRcharge_type().equalsIgnoreCase("mechanism_offline")) {

                            /*
                            MasterAppointmentEntity masterAppointmentEntity=masterAppointmentService .findById(entities.get(0).getAppointment_id(),request,response);
                            if(masterAppointmentEntity!=null){
                                Integer integer=masterAppointmentEntity.getConnect_peoplenum()+1;
                                masterAppointmentEntity.setConnect_peoplenum(integer);
                                masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                                userAppointmentEntity.setUser_id(entities.get(0).getUser_id());
                                userAppointmentEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
                                userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
                                userAppointmentEntity.setMechanism_id(masterAppointmentEntity.getMechanism_id());
                                userAppointmentEntity.setStatus(2);
                                userAppointmentEntity.setMaster_type(masterAppointmentEntity.getType());
                                userAppointmentEntity.setEarnings(entities.get(0).getAmount());
                                int ret=userAppointmentMapper.insertSelective(userAppointmentEntity);
                                if(ret==0){
                                    logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                                    return "failure";
                                }
                            }else{
                                logger.warn("alipay cashFinidhed result is :" + JsonUtilFastjson.toJSONString(entities));
                                return "failure";
                            }
                             */
                    }
                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "failure";
        }
        return "success";
    }


    private Map<String, String> getParam(HttpServletRequest request) {
        //???????????????POST??????????????????
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //???????????????????????????????????????????????????
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }


}
