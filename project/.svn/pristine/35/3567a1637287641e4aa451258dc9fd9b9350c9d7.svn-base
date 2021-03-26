package com.huihe.eg.user.service.impl.pay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.model.pay.IosPayParam;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.ClassCardService;
import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.dao.UserOrderService;
import com.huihe.eg.user.service.dao.UserService;
import com.huihe.eg.user.service.dao.pay.IosPayService;
import com.huihe.eg.user.service.dao.pay.PayService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;

@Service
public class IosPayServiceImpl implements IosPayService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserCommodityMapper userCommodityMapper;
    @Resource
    private UserMemberMapper userMemberMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserMemberCardMapper userMemberCardMapper;
    @Resource
    private UserMemberLevelMapper userMemberLevelMapper;
    @Resource
    private UserOrderService orderService;
    @Resource
    private StudyPriceMapper studyPriceMapper;
    @Resource
    private StudyCardMapper studyCardMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ClassCardMapper classCardMapper;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private PayServiceImpl payService;


    //购买凭证验证地址
    private static final String CERTIFICATEURL = "https://buy.itunes.apple.com/verifyReceipt";

    //测试的购买凭证验证地址
    private static final String CERTIFICATEURLTEST = "https://sandbox.itunes.apple.com/verifyReceipt";

    /**
     * 重写X509TrustManager
     */
    private static final TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }
    };

    /**
     * 接收iOS端发过来的购买凭证
     *
     * @param
     */
    @Override
    public ResultParam setIapCertificate(IosPayParam iosPayParam, HttpServletRequest request, HttpServletResponse response) {
        if (iosPayParam.getUser_id() == null || iosPayParam.getUser_id() == 0 || StringUtils.isEmpty(iosPayParam.getReceipt())) {
            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
        }
        UserInfoEntity infoEntity = userInfoService.findById(iosPayParam.getUser_id(), request, response);
        if (infoEntity == null) {
            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
        }

        UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();//用户学习卡
        UserCommodityEntity userCommodityEntity = new UserCommodityEntity();//猫币商品
        UserMemberLevelEntity entity1 = new UserMemberLevelEntity();//会员商品
        UserMemberCardEntity userMemberCardEntity = new UserMemberCardEntity();//会员类型
        ClassCardEntity classCardEntity = new ClassCardEntity();
        RechargeRecordEntity entity = new RechargeRecordEntity();//充值账单
        StudyPriceEntity studyPriceEntity = new StudyPriceEntity();//学习卡
        entity.setReceipt_data(MD5Util.GetMD5Code(iosPayParam.getReceipt()));//ios处理
        List<RechargeRecordEntity> rechargeRecordEntities = rechargeRecordMapper.query(entity);
        if (rechargeRecordEntities != null && rechargeRecordEntities.size() > 0) {
            return ResultUtil.error(OrderEum.order_70010.getCode(), OrderEum.order_70010.getDesc());//重复订单
        }
        entity.setRcharge_type(iosPayParam.getType());//类型
        entity.setUser_id(iosPayParam.getUser_id());
        entity.setFlowing_no(iosPayParam.getTransaction_id());//ios订单号
        entity.setFinished(true);
        entity.setReceipt_data(MD5Util.GetMD5Code(iosPayParam.getUser_id().toString()));
        entity.setAccount(true);
        entity.setStatus(1);
        entity.setSource("ios");
        UserEntity userEntity = new UserEntity();
        userEntity.setUser_id(iosPayParam.getUser_id());
        userEntity.setPageSize(1);
        List<UserEntity> userEntities = userMapper.query(userEntity);
        if (userEntities != null && userEntities.size() > 0) {
            entity.setRcharge_account(userEntities.get(0).getLogin_name());
        }
        if ("member".equalsIgnoreCase(iosPayParam.getType())) {//会员充值
            userMemberCardEntity.setTransaction_id(iosPayParam.getProduct_id());
            userMemberCardEntity.setPageSize(1);
            List<UserMemberCardEntity> entityList = userMemberCardMapper.queryListPage(userMemberCardEntity);//查询对应会员卡信息
            if (entityList != null && entityList.size() > 0) {
                userMemberCardEntity = entityList.get(0);
                if (entityList.get(0).getIs_discount()) {//是否打折
                    entity.setAmount(entityList.get(0).getDiscount_amout());
                } else {
                    entity.setAmount(entityList.get(0).getAmount());
                }
                entity.setMember_duration(userMemberCardEntity.getDuration());
                entity1.setId(entityList.get(0).getLevel_id());
                entity1.setPageSize(1);
                List<UserMemberLevelEntity> list = userMemberLevelMapper.query(entity1);//会员等级查询
                if (list != null && list.size() > 0) {
                    entity1 = list.get(0);
                    entity.setMember_level(list.get(0).getMember_level());
                    entity.setRcharge_abstract(list.get(0).getMember_name() + entityList.get(0).getMember_name());
                }
            } else {
                return ResultUtil.error(OrderEum.order_70011.getCode(), OrderEum.order_70011.getDesc());
            }
        } else if ("account".equalsIgnoreCase(iosPayParam.getType())) {//猫币充值
            userCommodityEntity.setTransaction_id(iosPayParam.getProduct_id());
            List<UserCommodityEntity> userComs = userCommodityMapper.query(userCommodityEntity);//查询猫币商品信息
            if (userComs != null && userComs.size() > 0) {
                userCommodityEntity = userComs.get(0);
                entity.setRcharge_abstract(userCommodityEntity.getCommodity_name());//是否打折
                if (userCommodityEntity.getIs_discount()) {
                    entity.setAmount(userCommodityEntity.getDiscount_amout());
                } else {
                    entity.setAmount(userCommodityEntity.getAmount());
                }
            } else {
                return ResultUtil.error(OrderEum.order_70011.getCode(), OrderEum.order_70011.getDesc());
            }
        } else if ("study_card".equalsIgnoreCase(iosPayParam.getType())) {//学习卡充值
            studyPriceEntity.setTransaction_id(iosPayParam.getProduct_id());
            studyPriceEntity.setStatus(1);
            studyPriceEntity.setPageSize(1);
            List<StudyPriceEntity> studyPriceEntities = studyPriceMapper.queryListPage(studyPriceEntity);//
            if (studyPriceEntities != null && studyPriceEntities.size() > 0) {//查询学习卡数据
                studyPriceEntity = studyPriceEntities.get(0);
                if (studyPriceEntity.getSpecial_type()==1){
                    entity.setStudy_type(studyPriceEntity.getType()+"_special");
                }else {
                    entity.setStudy_type(studyPriceEntity.getType());

                }
                entity.setMember_duration(studyPriceEntity.getDuration());
                entity.setMember_level(studyPriceEntity.getMember_level());
                entity.setCourse_num(studyPriceEntity.getCourse_num());
                if (studyPriceEntity.getIs_discount()) {
                    entity.setAmount(studyPriceEntity.getDiscount_amout());
                } else {
                    entity.setAmount(studyPriceEntity.getAmout());
                }
                StudyCardEntity studyCardEntity = new StudyCardEntity();
                studyCardEntity.setType(studyPriceEntity.getType());
                studyCardEntity.setStatus(1);
                studyCardEntity.setPageSize(1);
                List<StudyCardEntity> studyCardEntities = studyCardMapper.queryListPage(studyCardEntity);
                if (studyCardEntities != null && studyCardEntities.size() > 0) {
                    userStudyCardEntity.setStudycard_id(studyCardEntities.get(0).getId());
                    entity.setRcharge_abstract(studyCardEntities.get(0).getCard_name());
                } else {
                    return ResultUtil.error(OrderEum.order_70011.getCode(), OrderEum.order_70011.getDesc());
                }
            } else {
                return ResultUtil.error(OrderEum.order_70011.getCode(), OrderEum.order_70011.getDesc());
            }

        }else if ("class_card".equalsIgnoreCase(iosPayParam.getType())) {
            classCardEntity = new ClassCardEntity();
            classCardEntity.setTransaction_id(iosPayParam.getProduct_id());
            classCardEntity.setStatus(1);
            classCardEntity.setPageSize(1);
            List<ClassCardEntity> classCardEntities = classCardMapper.queryListPage(classCardEntity);//
            if (classCardEntities != null && classCardEntities.size() > 0) {//查询学习卡数据
                classCardEntity = classCardEntities.get(0);
                entity.setClass_card_id(classCardEntity.getId());
                entity.setAmount(classCardEntity.getDiscount_amout());
                entity.setStudy_type(classCardEntity.getType());
                if ("minute".equals(classCardEntity.getType())) {
                    entity.setCourse_num(classCardEntity.getCurriculum_num() + classCardEntity.getGive_curriculum());
                } else {
                    entity.setCourse_num(classCardEntity.getMinute_num() + classCardEntity.getGive_minute());
                }
            } else {
                return ResultUtil.error(OrderEum.order_70011.getCode(), OrderEum.order_70011.getDesc());
            }
        }
        entity.setIs_teach_paypal(true);
        int ret = rechargeRecordMapper.insertSelective(entity);
        logger.info("" + ret);
        String url = CERTIFICATEURL;
        String status = "";
        final String certificateCode = iosPayParam.getReceipt();
        if (StringUtils.isNotEmpty(certificateCode) && ret > 0) {
            String str = sendHttpsCoon(url, certificateCode);
            Map<String, Object> map = JSONUtils.json2Map(str);
            //正式验证服务器返回21007 进行测试服务器通信
            if (map.containsKey("status") && "21007".equalsIgnoreCase(map.get("status").toString())) {
                //测试
                url = CERTIFICATEURLTEST;
                String strTest = sendHttpsCoon(url, certificateCode);
                map = JSONUtils.json2Map(strTest);
                if (map.containsKey("status") && map.get("status") != null) {
                    status = map.get("status").toString();
                }
            } else {
                if (map.containsKey("status") && map.get("status") != null) {
                    status = map.get("status").toString();
                }

            }
            logger.info("status:" + status);
            //ios返回结果解析
            if (!status.equals("") && "0".equalsIgnoreCase(status)) {
                if (map.containsKey("receipt")) {
                    Map<String, Object> receiptMap = JSONUtils.obj2Map(map.get("receipt"), null);
                    if (receiptMap != null && receiptMap.containsKey("bundle_id") && "com.huihejituan.curiousearth".equals(receiptMap.get("bundle_id")) && receiptMap.containsKey("in_app")) {
                        List<Map<String, Object>> maps = JSONUtils.json2MapList(receiptMap.get("in_app").toString());
                        if (maps != null && maps.size() > 0) {
                            for (Map<String, Object> map1 : maps) {
                                if (map1.containsKey("transaction_id") && map1.get("transaction_id").equals(iosPayParam.getTransaction_id())
                                        && map1.containsKey("product_id") && map1.get("product_id").equals(iosPayParam.getProduct_id())) {
                                    if ("member".equalsIgnoreCase(iosPayParam.getType())) {//会员充值
                                        return updateMember(iosPayParam.getUser_id(), entity1.getMember_level(), userMemberCardEntity.getDuration());
                                    } else if ("account".equalsIgnoreCase(iosPayParam.getType())) {//猫币充值
                                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                                        userInfoEntity.setUser_id(iosPayParam.getUser_id());
                                                                                        /*

                                        List<UserInfoEntity> userInfoEntities = userInfoMapper.query(userInfoEntity);
                                        if (userInfoEntities != null && userInfoEntities.size() > 0) {
                                            //猫币修改
                                            userInfoEntity.setCat_coin(userCommodityEntity.getValue_coin());
                                            int retupdateus = 0;
                                            synchronized (userInfoEntity) {
                                                retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//修改数据
                                            }
                                            if (!(retupdateus > 0)) {
                                                return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
                                            } else {
                                                //消息推送
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                                                pushMessageParam.setContent("购买成功,获得" + userCommodityEntity.getValue_coin() + "猫币");
                                                pushMessageParam.setPush_time(new Date());
                                                pushMessageParam.setType("pay");
                                                pushMessageParam.setType_id(entity.getId());
                                                pushMessageParam.setPush_type(iosPayParam.getType());
                                                pushMessageParam.setOpera_type("iospay");
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);

                                                //新增订单数据
                                                UserOrderEntity orderEntity = new UserOrderEntity();
                                                orderEntity.setPayment_id(iosPayParam.getUser_id());
                                                orderEntity.setType("recharge");
                                                orderEntity.setSource("ios");
                                                orderEntity.setStatus(2);
                                                orderEntity.setPay_count(userCommodityEntity.getValue_coin());
                                                orderService.insert(orderEntity, request, response);
                                            }
                                                                                         */
                                        }
                                    } else if ("study_card".equalsIgnoreCase(iosPayParam.getType())) {
                                        //查询是否购买过学习卡
                                        userStudyCardEntity.setType(entity.getStudy_type());
                                        userStudyCardEntity.setUser_id(iosPayParam.getUser_id());
                                        userStudyCardEntity.setStatus(2);
                                        userStudyCardEntity.setPageSize(1);
                                        List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);
                                        if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {//购买过
                                            userStudyCardEntity = userStudyCardEntities.get(0);
                                            //结束时间
                                            if (userStudyCardEntity.getStatus() == 1) {
                                                Date dt = new Date();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.MONTH, studyPriceEntity.getDuration());
                                                Date dt1 = rightNow.getTime();
                                                userStudyCardEntity.setEnd_time(dt1);
                                                userStudyCardEntity.setStart_time(dt);
                                            } else {
                                                Date dt = userStudyCardEntity.getEnd_time();
                                                Calendar rightNow = Calendar.getInstance();
                                                rightNow.setTime(dt);
                                                rightNow.add(Calendar.MONTH, studyPriceEntity.getDuration());
                                                Date dt1 = rightNow.getTime();
                                                userStudyCardEntity.setEnd_time(dt1);
                                            }
                                            Integer courceCount = userStudyCardEntity.getCourse_num() + studyPriceEntity.getCourse_num();
                                            userStudyCardEntity.setCourse_num(courceCount);
                                            int rets = userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity);
                                            if (rets > 0) {
                                                                                                /*

                                                //会员时长
                                                if (studyPriceEntity.getMember_level() != null && studyPriceEntity.getMember_level() != 0) {
                                                    updateMember(iosPayParam.getUser_id(), studyPriceEntity.getMember_level(), studyPriceEntity.getDuration());
                                                }

                                                //猫币
                                                if (studyPriceEntity.getGive_coin() != null && studyPriceEntity.getGive_coin() != 0) {
                                                    infoEntity.setCat_coin(new BigDecimal(studyPriceEntity.getGive_coin().toString()));
                                                    int retupdateus = 0;
                                                    synchronized (infoEntity) {
                                                        retupdateus = userInfoMapper.updateAddCatCoin(infoEntity);//修改数据
                                                    }
                                                    if (!(retupdateus > 0)) {
                                                        return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
                                                    } else {
                                                        //推送
                                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                                        pushMessageParam.setTarget_id(iosPayParam.getUser_id());
                                                        pushMessageParam.setContent("购买成功,获得" + studyPriceEntity.getGive_coin() + "猫币");
                                                        pushMessageParam.setPush_time(new Date());
                                                        pushMessageParam.setType("pay");
                                                        pushMessageParam.setType_id(entity.getId());
                                                        pushMessageParam.setPush_type(iosPayParam.getType());
                                                        pushMessageParam.setOpera_type("iospay");
                                                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);

                                                        //新增订单数据
                                                        UserOrderEntity orderEntity = new UserOrderEntity();
                                                        orderEntity.setPayment_id(iosPayParam.getUser_id());
                                                        orderEntity.setType("recharge");
                                                        orderEntity.setSource(iosPayParam.getType());
                                                        orderEntity.setStatus(2);
                                                        orderEntity.setPay_count(userCommodityEntity.getValue_coin());
                                                        orderService.insert(orderEntity, request, response);
                                                    }
                                                }
                                                */

                                            }
                                        } else {//未购买过
                                            userStudyCardEntity.setCourse_num(studyPriceEntity.getCourse_num());
                                            //结束时间+一个月
                                            Date dt = new Date();
                                            Calendar rightNow = Calendar.getInstance();
                                            rightNow.setTime(dt);
                                            rightNow.add(Calendar.MONTH, studyPriceEntity.getDuration());
                                            Date dt1 = rightNow.getTime();
                                            userStudyCardEntity.setEnd_time(dt1);
                                            userStudyCardEntity.setIs_teach_paypal(true);
                                            int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
                                            if (rets > 0) {
                                                                                                        /*

                                                //会员时长
                                                if (studyPriceEntity.getMember_level() != null && studyPriceEntity.getMember_level() != 0) {
                                                    updateMember(iosPayParam.getUser_id(), studyPriceEntity.getMember_level(), studyPriceEntity.getDuration());
                                                }
                                                //猫币
                                                if (studyPriceEntity.getGive_coin() != null && studyPriceEntity.getGive_coin() != 0) {
                                                    infoEntity.setCat_coin(new BigDecimal(studyPriceEntity.getGive_coin().toString()));
                                                    int retupdateus = 0;
                                                    synchronized (infoEntity) {
                                                        retupdateus = userInfoMapper.updateAddCatCoin(infoEntity);//修改数据
                                                    }
                                                    if (!(retupdateus > 0)) {
                                                        return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
                                                    } else {
                                                        //消息推送
                                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                                        pushMessageParam.setTarget_id(iosPayParam.getUser_id());
                                                        pushMessageParam.setContent("购买成功,获得" + studyPriceEntity.getGive_coin() + "猫币");
                                                        pushMessageParam.setPush_time(new Date());
                                                        pushMessageParam.setType("pay");
                                                        pushMessageParam.setType_id(entity.getId());
                                                        pushMessageParam.setPush_type(iosPayParam.getType());
                                                        pushMessageParam.setOpera_type("iospay");
                                                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);

                                                        //新增订单数据
                                                        UserOrderEntity orderEntity = new UserOrderEntity();
                                                        orderEntity.setPayment_id(iosPayParam.getUser_id());
                                                        orderEntity.setType("recharge");
                                                        orderEntity.setSource(iosPayParam.getType());
                                                        orderEntity.setStatus(2);
                                                        orderEntity.setPay_count(userCommodityEntity.getValue_coin());
                                                        orderService.insert(orderEntity, request, response);
                                                    }

                                                }
                                                                                                         */
                                            }
                                        }
                                    } else if ("class_card".equals(iosPayParam.getType())) {
                                        if (iosPayParam.getIs_experience()!=null && iosPayParam.getIs_experience()) {
                                            int course_num = 0;
                                            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                            userClassCardEntity.setUser_id(iosPayParam.getUser_id());
                                            userClassCardEntity.setType(classCardEntity.getType());
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
                                            userClassCardEntity.setCurriculum_num(classCardEntity.getCurriculum_num() - (8 - course_num)
                                                    + userClassCardEntity.getCurriculum_num());
                                            Date dt = new Date();
                                            Calendar rightNow = Calendar.getInstance();
                                            rightNow.setTime(dt);
                                            rightNow.add(Calendar.MONTH, 12);
                                            Date dt1 = rightNow.getTime();
                                            userClassCardEntity.setExpire_time(dt1);

                                            int i = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                            if (i == 0) {
                                                return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
                                            }
                                        } else {
                                            UserInfoEntity userInfoEntity = new UserInfoEntity();
                                            userInfoEntity.setUser_id(iosPayParam.getUser_id());
                                            List<UserInfoEntity> userInfoEntities = userInfoMapper.query(userInfoEntity);
                                            if (userInfoEntities != null && userInfoEntities.size() > 0) {       //用户信息不为空
                                                UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                                userClassCardEntity.setUser_id(iosPayParam.getUser_id());
                                                userClassCardEntity.setType(classCardEntity.getType());
                                                userClassCardEntity.setIs_experience(false);
                                                userClassCardEntity.setPageSize(1);
                                                List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                                if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                                    //购买过此种类型直播学习卡
                                                    userClassCardEntity = userClassCardEntities.get(0);
                                                    //结束时间
                                                    Date dt = new Date();
                                                    Calendar rightNow = Calendar.getInstance();
                                                    rightNow.setTime(dt);
                                                    rightNow.add(Calendar.YEAR, 1);
                                                    Date dt1 = rightNow.getTime();
                                                    userClassCardEntity.setExpire_time(dt1);
                                                    if (userClassCardEntity.getStatus() == 1) {
                                                        if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute分钟/curriculum课程
                                                            userClassCardEntity.setMinute_num(classCardEntity.getMinute_num() + classCardEntity.getGive_minute() + entity.getCourse_num());
                                                        } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                            userClassCardEntity.setCurriculum_num(classCardEntity.getCurriculum_num() + classCardEntity.getGive_curriculum() + entity.getCourse_num());
                                                        }
                                                        userClassCardEntity.setStatus(2);
                                                    } else {
                                                        int integer = 0;
                                                        if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute分钟/curriculum课程
                                                            integer = classCardEntity.getMinute_num() + classCardEntity.getGive_minute() + userClassCardEntity.getMinute_num();
                                                            userClassCardEntity.setMinute_num(integer);
                                                        } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                            integer = classCardEntity.getCurriculum_num()  + classCardEntity.getGive_curriculum() + userClassCardEntity.getCurriculum_num();
                                                            userClassCardEntity.setCurriculum_num(integer);
                                                        }
                                                    }
                                                    UserClassCardEntity entity2 = new UserClassCardEntity();
                                                    entity2.setUser_id(userClassCardEntity.getUser_id());
                                                    entity2.setDefault_use(false);
                                                    int ret1 = userClassCardMapper.updateDefaultUse(entity2);
                                                    if (ret1 > 0) {
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
                                                    if ("minute".equalsIgnoreCase(classCardEntity.getType())) {//minute分钟/curriculum课程
                                                        integer = classCardEntity.getMinute_num() * entity.getCourse_num() + classCardEntity.getGive_minute();
                                                        userClassCardEntity.setMinute_num(integer);
                                                    } else if ("curriculum".equalsIgnoreCase(classCardEntity.getType())) {
                                                        integer = classCardEntity.getCurriculum_num() * entity.getCourse_num() + classCardEntity.getGive_curriculum();
                                                        userClassCardEntity.setCurriculum_num(integer);
                                                    }
                                                    UserClassCardEntity entity3 = new UserClassCardEntity();
                                                    entity3.setUser_id(userClassCardEntity.getUser_id());
                                                    entity3.setDefault_use(false);
                                                    userClassCardMapper.updateDefaultUse(entity3);
                                                    userClassCardEntity.setDefault_use(true);
                                                    userClassCardMapper.insertSelective(userClassCardEntity);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        entity.setStatus(2);
                        entity.setFinished_time(new Date());
                        rechargeRecordMapper.updateByPrimaryKeySelective(entity);
                    } else {
                        return ResultUtil.error(OrderEum.order_70014.getCode(), OrderEum.order_70014.getDesc());
                    }
                }
                return ResultUtil.success("支付成功");
            }
//        }
        return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
    }

    /**
     * 发送请求
     *
     * @param url
     * @param
     * @return
     */
    private String sendHttpsCoon(String url, String code) {
        if (url.isEmpty()) {
            return null;
        }
        try {
            //设置SSLContext
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, new TrustManager[]{myX509TrustManager}, null);

            //打开连接
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            //设置套接工厂
            conn.setSSLSocketFactory(ssl.getSocketFactory());
            //加入数据
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");

            JSONObject obj = new JSONObject();
            obj.put("receipt-data", code);

            BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(obj.toString().getBytes());
            buffOutStr.flush();
            buffOutStr.close();

            //获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 充值会员处理
     *
     * @param user_id
     * @param member_level
     * @param Monthles
     * @return
     */
    @Override
    public ResultParam updateMember(Long user_id, int member_level, int Monthles) {
        UserMemberCardEntity userMemberCardEntity = new UserMemberCardEntity();
        userMemberCardEntity.setLevel_id((long) member_level);
        userMemberCardEntity.setDuration(Monthles);
        userMemberCardEntity.setStatus(1);
        userMemberCardEntity = userMemberCardMapper.queryListPage(userMemberCardEntity).get(0);
        BigDecimal give_coin = new BigDecimal(userMemberCardEntity.getGive_coin().toString());//查询充值送猫币数量

        UserMemberEntity userMemberEntity = new UserMemberEntity();
        userMemberEntity.setUser_id(user_id);
        List<UserMemberEntity> userMemberEntities = userMemberMapper.query(userMemberEntity);
        if (userMemberEntities != null && userMemberEntities.size() > 0) {//
            userMemberEntity.setId(userMemberEntities.get(0).getId());
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = new Date();
            if (userMemberEntities.get(0).getIs_member()) {//续费
                dt = userMemberEntities.get(0).getEnd_time();
                if (userMemberEntities.get(0).getMember_level() < member_level) {//会员等级
                    userMemberEntity.setMember_level(member_level);
                }
            } else {//开通
                userMemberEntity.setStart_time(new Date());//开始时间重新计算
                userMemberEntity.setIs_member(true);
                userMemberEntity.setMember_level(member_level);
            }
            //往后推时间
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.MONTH, Monthles);
            Date dt1 = rightNow.getTime();
            userMemberEntity.setEnd_time(dt1);
            int retupdate = userMemberMapper.updateByPrimaryKeySelective(userMemberEntity);
            if (!(retupdate > 0)) {
                return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
            } else {
                //用户修改畅聊次数
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(user_id);
                userInfoEntity.setIs_member(true);
                userInfoEntity.setUser_id(user_id);
                userInfoEntity.setMember_level(member_level);

                UserMemberLevelEntity userMemberLevelEntity = userMemberLevelMapper.selectByPrimaryKey(userMemberCardEntity.getLevel_id());


                userInfoEntity.setChatting_count(userMemberLevelEntity.getChatting_count() + userInfoEntity.getChatting_count());
                userInfoMapper.updateAddChattingCount(userInfoEntity);
                int retupdateus = 0;
                userInfoEntity.setCat_coin(userInfoEntity.getCat_coin().add(give_coin));
                synchronized (userInfoEntity) {//开会员加猫币逻辑
                    retupdateus = userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                }
                if (!(retupdateus > 0)) {
                    return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
                } else {
                    //推送
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setTarget_id(user_id);
                    pushMessageParam.setPush_time(new Date());
                    pushMessageParam.setType("pay");
                    pushMessageParam.setPush_type("member");
                    pushMessageParam.setOpera_type("pay");
                    pushMessageParam.setContent("购买成功");
                    pushMessageParam.setType_id(userMemberEntity.getId());
                    rabbitTemplate.convertAndSend("payPushMessage", pushMessageParam);
                }
            }
        } else {//开通
            userMemberEntity.setIs_member(true);
            userMemberEntity.setMember_level(member_level);
            userMemberEntity.setStart_time(new Date());
            //往后推时间
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.MONTH, Monthles);
            Date dt1 = rightNow.getTime();
            userMemberEntity.setEnd_time(dt1);
            int retinsert = userMemberMapper.insertSelective(userMemberEntity);//新增
            if (!(retinsert > 0)) {
                return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
            } else {
                //用户修改畅聊次数
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setIs_member(true);
                userInfoEntity.setMember_level(member_level);
                userInfoEntity.setUser_id(user_id);
                if (member_level == 1) {
                    userInfoEntity.setChatting_count(45);
                } else if (member_level == 2) {
                    userInfoEntity.setChatting_count(75);
                } else if (member_level == 3) {
                    userInfoEntity.setChatting_count(220);
                } else {
                    userInfoEntity.setChatting_count(0);
                }
                userInfoMapper.updateAddChattingCount(userInfoEntity);
                int retupdateus = 0;
                userInfoEntity.setCat_coin(userInfoEntity.getCat_coin().add(give_coin));
                synchronized (userInfoEntity) {//开会员加猫币逻辑
                    retupdateus = userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                }
                if (!(retupdateus > 0)) {
                    return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
                } else {
                    //推送
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setTarget_id(user_id);
                    pushMessageParam.setPush_time(new Date());
                    pushMessageParam.setType("pay");
                    pushMessageParam.setOpera_type("pay");
                    pushMessageParam.setPush_type("member");
                    pushMessageParam.setContent("购买成功");
                    pushMessageParam.setType_id(userMemberEntity.getId());
                    rabbitTemplate.convertAndSend("payPushMessage", pushMessageParam);
                }
            }
        }
        return ResultUtil.success();
    }

}

