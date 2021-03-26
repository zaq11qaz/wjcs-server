package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.CouponEum;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class UserCouponServiceImpl extends BaseFrameworkServiceImpl<UserCouponEntity, Long> implements UserCouponService {

    @Resource
    private UserCouponMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private CouponListService couponListService;
    @Resource
    private CommodityCouponMapper commodityCouponMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private CouponListMapper couponListMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MechanismUserService mechanismUserService;
    @Resource
    private MechanismUserMapper mechanismUserMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam useCoupon(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            /**
             * 兑换码合法性效验
             */
            CouponListEntity couponListEntity = new CouponListEntity();
            couponListEntity.setCoupon_code(param.getCoupon_code());
            couponListEntity.setPageSize(1);
            List<CouponListEntity> query = couponListService.query(couponListEntity, request, response);
            if (query != null && query.size() > 0) {
                couponListEntity = query.get(0);
                if (couponListEntity.getStatus() == 2) {
                    couponListEntity.setStatus(1);//设置领取
                    couponListService.update(couponListEntity, request, response);
                } else {
                    return ResultUtil.error(CouponEum.coupon_30003.getCode(), CouponEum.coupon_30003.getDesc());
                }
            } else {
                return ResultUtil.error(CouponEum.coupon_30001.getCode(), CouponEum.coupon_30001.getDesc());
            }


            //是否有效
            CommodityCouponEntity commodityCouponEntity = commodityCouponMapper.selectByPrimaryKey(couponListEntity.getCoupon_id());
            if (commodityCouponEntity != null && commodityCouponEntity.getStatus() == 2) {
            } else {
                return ResultUtil.error(CouponEum.coupon_30005.getCode(), CouponEum.coupon_30005.getDesc());
            }
            // 是否领取过
            UserCouponEntity userCouponEntity = new UserCouponEntity();
            userCouponEntity.setType(couponListEntity.getCoupon_type());
            userCouponEntity.setCoupon_id(commodityCouponEntity.getId());
            userCouponEntity.setPageSize(1);
            List<UserCouponEntity> query1 = mapper.query(userCouponEntity);
            if (query1 != null && query1.size() > 0) {
                return ResultUtil.error(CouponEum.coupon_30006.getCode(), CouponEum.coupon_30006.getDesc());
            }
            //是否购买过学习卡
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            userStudyCardEntity.setUser_id(param.getUser_id());
            List<UserStudyCardEntity> query2 = userStudyCardMapper.query(userStudyCardEntity);
            if (query2 != null && query2.size() > 0 && "timepointcard".equals(couponListEntity.getCoupon_type())) {
                return ResultUtil.error(CouponEum.coupon_30010.getCode(), CouponEum.coupon_30010.getDesc());
            }

            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
            userClassCardEntity.setUser_id(param.getUser_id());
            List<UserClassCardEntity> query3 = userClassCardMapper.query(userClassCardEntity);
            if (query3 != null && query3.size() > 0 && "classexperience".equals(couponListEntity.getCoupon_type())) {
                return ResultUtil.error(CouponEum.coupon_30010.getCode(), CouponEum.coupon_30010.getDesc());
            }

            userCouponEntity.setUser_id(param.getUser_id());
            userCouponEntity.setOverdue_time(commodityCouponEntity.getEnd_time());
            userCouponEntity.setStatus(1);
            userCouponEntity.setCoupon_list_id(couponListEntity.getId());
            userCouponEntity.setIs_teach_paypal(true);
            mapper.insertSelective(userCouponEntity);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(1001, "网络繁忙");
    }

    @Override
    public List<UserCouponEntity> queryMyCoupon(UserCouponEntity userCouponEntity, HttpServletRequest request, HttpServletResponse response) {
        List<UserCouponEntity> list = Lists.newArrayList();
        try {
            list = mapper.query(userCouponEntity);
            if (list != null && list.size() > 0) {
                for (UserCouponEntity couponEntity : list) {
                    CommodityCouponEntity commodityCouponEntity = commodityCouponMapper.selectByPrimaryKey(couponEntity.getCoupon_id());
                    CouponListEntity couponListEntity = couponListMapper.selectByPrimaryKey(couponEntity.getCoupon_list_id());
                    commodityCouponEntity.setId(userCouponEntity.getCoupon_list_id());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("commodityCouponEntity", commodityCouponEntity);
                    map.put("couponListEntity", couponListEntity);
                    couponEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryByMessage(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (param.getFull_name() != null) {
                List<Long> list1 = masterInfoMapper.queryIdByFullName(param.getFull_name());
                if (list1 != null && list1.size() > 0) {
                    param.setName_ids(list1);
                }
            }
            if (param.getLogin_name() != null) {
                List<Long> list1 = userMapper.queryIdByLoginName(param.getLogin_name());
                if (list1 != null && list1.size() > 0) {
                    param.setLoginIDs(list1);
                }
            }
            List<UserCouponEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total > 0) {
                this.setUserInfo(list);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCoupListCount(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();

        try {
            Integer dayCount = mapper.queryDayCount(param);
            map.put("dayCount", dayCount);

            Integer weekCount = mapper.queryWeekCount(param);
            map.put("weekCount", weekCount);

            Integer monthCount = mapper.queryMonthCount(param);
            map.put("monthCount", monthCount);

            Integer totalCount = mapper.queryTotalCount(param);
            map.put("totalCount", totalCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    @Transactional
    public synchronized ResultParam insertUserCollection(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfoEntity userInfoEntity = null;
            userInfoEntity = userInfoMapper.selectByPrimaryKey(param.getUser_id());
            if (userInfoEntity.getIs_collection()) {
                return ResultUtil.error(UserEum.user_10047.getCode(), UserEum.user_10047.getDesc());
            } else {
                int i = userInfoMapper.updateIsCollection(userInfoEntity);
                if (i < 1) {
                    throw new Exception("修改用户状态失败");
                }
            }

            CommodityCouponEntity commodityCouponEntity = new CommodityCouponEntity();
            commodityCouponEntity.setStatus(2);
            commodityCouponEntity.setType("100off_coupon");
            commodityCouponEntity.setUser_id(param.getUser_id());
            ResultParam resultParam = this.insertUserCollectionList(commodityCouponEntity);
            if (resultParam.getCode() != 0) {
                throw new Exception("插入用户优惠券失败");
            }

            commodityCouponEntity.setType("50off_coupon");
            resultParam = this.insertUserCollectionList(commodityCouponEntity);
            if (resultParam.getCode() != 0) {
                throw new Exception("插入用户优惠券失败");
            }

            return ResultUtil.success("领取成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public Map<String, Object> queryUserCoupCount(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer sumCount = mapper.queryListPageCount(param);
            map.put("sumCount", sumCount);

            param.setStatus(1);
            Integer canUseCount = mapper.queryListPageCount(param);
            map.put("canUseCount", canUseCount);

            param.setStatus(3);
            Integer usedCount = mapper.queryListPageCount(param);
            map.put("usedCount", usedCount);

            param.setStatus(2);
            Integer expiredCount = mapper.queryListPageCount(param);
            map.put("expiredCount", expiredCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void updateCoupStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        if (rechargeRecordEntity.getCoupon_id() != null) {
            if (StringUtil.isNotEmpty(rechargeRecordEntity.getCoupon_ids())) {
                String[] ids = rechargeRecordEntity.getCoupon_ids().split(",");
                if (ids.length > 0) {
                    for (String id : ids) {
                        UserCouponEntity userCouponEntity1 = mapper.selectByPrimaryKey(Long.valueOf(id));
                        userCouponEntity1.setStatus(3);
                        mapper.updateByPrimaryKeySelective(userCouponEntity1);
                    }
                }
            }
            mapper.updateStatusUsed(rechargeRecordEntity.getCoupon_id(), rechargeRecordEntity.getMechanism_id());
        }
        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(rechargeRecordEntity.getUser_id());
        MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id());
        PushMessageParam pushMessageParam = new PushMessageParam();
        pushMessageParam.setType_id(rechargeRecordEntity.getId());
        pushMessageParam.setTarget_id(mechanismEntity.getUser_id());
        pushMessageParam.setPush_type("pay");
        pushMessageParam.setOpera_type("pay");
        pushMessageParam.setContent(userInfoEntity.getNick_name() + "购买了您的课程");
        pushMessageParam.setType("pay");
        pushMessageParam.setTitle("已出售课程");
        pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
    }

    @Override
    public ResultParam insertUserCoupon177(RechargeRecordEntity rechargeRecordEntity) {
        CommodityCouponEntity commodityCouponEntity = new CommodityCouponEntity();
        commodityCouponEntity.setStatus(2);
        commodityCouponEntity.setCourse_num(3);
        commodityCouponEntity.setType("voucher_redemption");
        commodityCouponEntity.setUser_id(rechargeRecordEntity.getUser_id());
        try {
            ResultParam resultParam = this.insertUserCollectionList(commodityCouponEntity);
            if (resultParam.getCode() == 0) {
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setType_id(rechargeRecordEntity.getId());
                pushMessageParam.setTarget_id(rechargeRecordEntity.getUser_id());
                pushMessageParam.setPush_type("buy_coupon");
                pushMessageParam.setOpera_type("buy_coupon");
                pushMessageParam.setContent("购买优惠券成功");
                pushMessageParam.setType("buy_coupon");
                pushMessageParam.setTitle("购买优惠券成功");
                pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.success();
    }

    @Override
    public ResultParam insertUserCoupon77(RechargeRecordEntity rechargeRecordEntity) {
        CommodityCouponEntity commodityCouponEntity = new CommodityCouponEntity();
        commodityCouponEntity.setStatus(2);
        commodityCouponEntity.setCourse_num(1);
        commodityCouponEntity.setType("voucher_redemption");
        commodityCouponEntity.setUser_id(rechargeRecordEntity.getUser_id());
        try {
            ResultParam resultParam = this.insertUserCollectionList(commodityCouponEntity);
            if (resultParam.getCode() == 0) {
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setType_id(rechargeRecordEntity.getId());
                pushMessageParam.setTarget_id(rechargeRecordEntity.getUser_id());
                pushMessageParam.setPush_type("buy_coupon");
                pushMessageParam.setOpera_type("buy_coupon");
                pushMessageParam.setContent("购买优惠券成功");
                pushMessageParam.setType("buy_coupon");
                pushMessageParam.setTitle("购买优惠券成功");
                pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam insertUserCoupon99(RechargeRecordEntity rechargeRecordEntity) {
        CommodityCouponEntity commodityCouponEntity = new CommodityCouponEntity();
        commodityCouponEntity.setStatus(2);
        commodityCouponEntity.setCourse_num(1);
        commodityCouponEntity.setType("deductions_coupon");
        commodityCouponEntity.setUser_id(rechargeRecordEntity.getUser_id());
        try {
            ResultParam resultParam = this.insertUserCollectionList(commodityCouponEntity);
            if (resultParam.getCode() == 0) {
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setType_id(rechargeRecordEntity.getId());
                pushMessageParam.setTarget_id(rechargeRecordEntity.getUser_id());
                pushMessageParam.setPush_type("buy_coupon");
                pushMessageParam.setOpera_type("buy_coupon");
                pushMessageParam.setContent("购买优惠券成功");
                pushMessageParam.setType("buy_coupon");
                pushMessageParam.setTitle("购买优惠券成功");
                pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam insertUserStudyCardByCoup(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserCouponEntity userCouponEntity = mapper.selectByPrimaryKey(rechargeRecordEntity.getCoupon_id());
            if (userCouponEntity.getStatus() == 1 && "voucher_redemption".equals(userCouponEntity.getType())) {
                userCouponEntity.setMaster_set_price_id(rechargeRecordEntity.getStudycard_id());

                userCouponEntity.setStatus(3);
                userCouponEntity.setMaster_set_price_id(rechargeRecordEntity.getUser_study_card_id());
                userCouponEntity.setMechanism_id(rechargeRecordEntity.getMechanism_id());
                UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();


                userStudyCardEntity.setUser_id(rechargeRecordEntity.getUser_id());
                userStudyCardEntity.setIs_experience(true);
                userStudyCardEntity.setPageSize(1);
                //设置类型
                userStudyCardEntity.setType("mechanism_offline");

                //可用
                userStudyCardEntity.setStatus(2);
                userStudyCardEntity.setStudycard_id(rechargeRecordEntity.getStudycard_id());
                //结束时间+ 2周
                Date dt = new Date();
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(dt);
                rightNow.add(Calendar.YEAR, 1);
                Date dt1 = rightNow.getTime();
                userStudyCardEntity.setEnd_time(dt1);
                userStudyCardEntity.setIs_teach_paypal(true);
                int i = userStudyCardMapper.insertSelective(userStudyCardEntity);
                if (i > 0) {
                    userCouponEntity.setUser_study_card_id(userStudyCardEntity.getId());
                    int i1 = mapper.updateByPrimaryKeySelective(userCouponEntity);
                    return ResultUtil.success();
                }
                return this.insertStudyCardByCoup(userCouponEntity, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    @Transactional
    public synchronized ResultParam insertStudyCardByCoup(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserCouponEntity userCouponEntity = mapper.selectByPrimaryKey(param.getId());
            if (!userCouponEntity.getUser_id().equals(param.getUser_id())) {
                return ResultUtil.error(UserEum.user_10062.getCode(), UserEum.user_10062.getDesc());
            }
            MasterSetPriceEntity masterSetPriceEntity1 = masterSetPriceMapper.selectByPrimaryKey(param.getMaster_set_price_id());
            if (!masterSetPriceEntity1.getIs_attend_activities()) {
                return ResultUtil.error(OrderEum.order_70035.getCode(), OrderEum.order_70035.getDesc());
            }
            UserCouponEntity userCouponEntity1 = new UserCouponEntity();
            userCouponEntity1.setUser_id(param.getUser_id());
            userCouponEntity1.setType("voucher_redemption");
            userCouponEntity1.setMechanism_id(masterSetPriceEntity1.getMechanism_id());
            userCouponEntity1.setPageSize(1);
            List<UserCouponEntity> userCouponEntities = mapper.queryListPage(userCouponEntity1);
            if (userCouponEntities != null && userCouponEntities.size() > 0) {
                return ResultUtil.error(UserEum.user_10065.getCode(), UserEum.user_10065.getDesc());
            }
            if (userCouponEntity.getStatus() == 3) {
                return ResultUtil.error(UserEum.user_10047.getCode(), UserEum.user_10047.getDesc());
            }

            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(param.getMaster_set_price_id());

            MechanismUserEntity mechanismUserEntity = new MechanismUserEntity();
            mechanismUserEntity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            mechanismUserEntity.setType("coupon");
            mechanismUserEntity.setUser_id(param.getUser_id());
            mechanismUserEntity.setPageSize(1);
            mechanismUserEntity.setStatus(2);
            List<MechanismUserEntity> mechanismUserEntities = mechanismUserMapper.queryListPage(mechanismUserEntity);
            /*
            if (mechanismUserEntities != null && mechanismUserEntities.size() > 0) {
                if (!mechanismUserEntities.get(0).getIs_new()) {
                    return ResultUtil.error(OrderEum.order_70033.getCode(), OrderEum.order_70033.getDesc());
                }else {
                    mechanismUserEntity = mechanismUserEntities.get(0);
                    mechanismUserEntity.setIs_new(false);
                    mechanismUserMapper.updateByPrimaryKeySelective(mechanismUserEntity);
                }
            } else {
                ResultParam insert = mechanismUserService.insert(mechanismUserEntity, request, response);
                if (insert.getCode() == 0) {
                    return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
                }
                return ResultUtil.error(OrderEum.order_70034.getCode(), OrderEum.order_70034.getDesc());
            }

             */


            userCouponEntity.setStatus(3);
            userCouponEntity.setMaster_set_price_id(param.getMaster_set_price_id());
            userCouponEntity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();

            if (param.getMechanism_id() != null && param.getMechanism_id() != 0) {
                userStudyCardEntity.setMechanism_id(param.getMechanism_id());
            }

            userStudyCardEntity.setUser_id(param.getUser_id());
            userStudyCardEntity.setIs_experience(true);
            userStudyCardEntity.setPageSize(1);
            //设置类型
            userStudyCardEntity.setType("mechanism_offline");

            //可用
            userStudyCardEntity.setStatus(2);
            userStudyCardEntity.setStudycard_id(param.getMaster_set_price_id());
            //结束时间+ 2周
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.YEAR, 1);
            Date dt1 = rightNow.getTime();
            userStudyCardEntity.setEnd_time(dt1);
            userStudyCardEntity.setIs_teach_paypal(true);
            int i = userStudyCardMapper.insertSelective(userStudyCardEntity);
            if (i > 0) {
                userCouponEntity.setUser_study_card_id(userStudyCardEntity.getId());
                int i1 = mapper.updateByPrimaryKeySelective(userCouponEntity);
                return ResultUtil.success();
            }
//                }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(CouponEum.coupon_30004.getCode(), CouponEum.coupon_30004.getDesc());
    }

    private ResultParam insertUserCollectionList(CommodityCouponEntity commodityCouponEntity) throws Exception {
        UserCouponEntity userCouponEntity = new UserCouponEntity();
        userCouponEntity.setUser_id(commodityCouponEntity.getUser_id());

        Integer course_num = 0;
        if (commodityCouponEntity.getCourse_num() != null) {
            course_num = commodityCouponEntity.getCourse_num();
            commodityCouponEntity.setCourse_num(null);
        }
        commodityCouponEntity.setPageSize(1);
        commodityCouponEntity = commodityCouponMapper.queryListPage(commodityCouponEntity).get(0);

        if (course_num != 0) {
            commodityCouponEntity.setCourse_num(course_num);
        }

        userCouponEntity.setCash(commodityCouponEntity.getCash());
        userCouponEntity.setCoupon_id(commodityCouponEntity.getId());
        userCouponEntity.setCourse_num(1);
        userCouponEntity.setType(commodityCouponEntity.getType());
        userCouponEntity.setCoup_name(commodityCouponEntity.getName());
        Date dt = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
//        rightNow.add(Calendar.MINUTE, commodityCouponEntity.getDuration().intValue());
        rightNow.add(Calendar.YEAR, 1);
        Date dt1 = rightNow.getTime();
        userCouponEntity.setOverdue_time(dt1);
        for (int i = 0; i < commodityCouponEntity.getCourse_num(); i++) {
            userCouponEntity.setId(null);
            userCouponEntity.setIs_teach_paypal(true);
            int res = mapper.insertSelective(userCouponEntity);
            if (res < 1) {
                throw new Exception("插入用户券失败");
            }
        }
        return ResultUtil.success();
    }

    @Override
    public List<UserCouponEntity> queryListPage(UserCouponEntity userCouponEntity, HttpServletRequest request, HttpServletResponse response) {
        List<UserCouponEntity> userCouponEntities = mapper.queryListPage(userCouponEntity);
        if (userCouponEntities != null && userCouponEntities.size() > 0) {
            this.setUserInfo(userCouponEntities);
        }
        return userCouponEntities;
    }

    private void setUserInfo(List<UserCouponEntity> userCouponEntities) {
        for (UserCouponEntity couponEntity : userCouponEntities) {
            Map<String, Object> map = new HashMap<>();
            //设置userinfo
//            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(couponEntity.getUser_id() + "userinfo")));
            map.put("commodityCouponEntity", commodityCouponMapper.selectByPrimaryKey(couponEntity.getCoupon_id()));
            couponEntity.setMap(map);
        }
    }

    /*@Override
    public ResultParam useCoupon(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserCouponEntity> userCouponEntity=mapper.queryListPage(param);
        if(userCouponEntity!=null&&userCouponEntity.size()>0){
            UserCouponEntity entity=userCouponEntity.get(0);
            if(entity.getStatus()==1){
                return ResultUtil.error(CouponEum.coupon_30003.getCode(), CouponEum.coupon_30003.getDesc());
            }else{
                if(entity.getOverdue_time().getTime()>System.currentTimeMillis()){
                    UserInfoEntity infoEntity=userInfoService.findById(param.getUser_id(),request,response);
                    if(infoEntity!=null) {
                        CouponListEntity couponListEntity=couponListService.findById(entity.getCoupon_id(),request,response);
                        if(couponListEntity!=null){
                            CommodityCouponEntity commodityCouponEntity= commodityCouponService.findById(couponListEntity.getCoupon_id(),request,response);
                            if(commodityCouponEntity!=null){
                                if(commodityCouponEntity.getType()!=null&&commodityCouponEntity.getType().equalsIgnoreCase("timepointcard")){
                                    infoEntity.setDuration(infoEntity.getDuration()+commodityCouponEntity.getDuration());
                                    Integer ret=userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                                    if(ret>0){
                                        entity.setStatus(3);
                                        return super.update(entity,request,response);
                                    }else{
                                        return ResultUtil.error(CouponEum.coupon_30005.getCode(), CouponEum.coupon_30005.getDesc());
                                    }
                                }else if(commodityCouponEntity.getType()!=null&&commodityCouponEntity.getType().equalsIgnoreCase("timepointcard")){

                                }
                            }else{
                                return ResultUtil.error(CouponEum.coupon_30005.getCode(), CouponEum.coupon_30005.getDesc());
                            }
                        }else{
                            return ResultUtil.error(CouponEum.coupon_30005.getCode(), CouponEum.coupon_30005.getDesc());
                        }
                    }else {
                        return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                    }
                }else{
                    return ResultUtil.error(CouponEum.coupon_30002.getCode(), CouponEum.coupon_30002.getDesc());
                }
            }
        }else{
            return ResultUtil.error(CouponEum.coupon_30005.getCode(), CouponEum.coupon_30005.getDesc());
        }
        return  ResultUtil.error(CouponEum.coupon_30005.getCode(), CouponEum.coupon_30005.getDesc());
    }
    */

}