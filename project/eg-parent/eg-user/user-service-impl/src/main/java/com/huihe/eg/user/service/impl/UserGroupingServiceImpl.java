package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.util.GenSerial;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.RechargeRecordService;
import com.huihe.eg.user.service.dao.UserGroupingService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserService;
import com.huihe.eg.user.service.impl.sms.SmsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserGroupingServiceImpl extends BaseFrameworkServiceImpl<UserGroupingEntity, Long> implements UserGroupingService {

    @Resource
    private UserGroupingMapper mapper;
    @Resource
    private SmsService smsService;
    @Resource
    private UserService userService;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private RechargeRecordService rechargeRecordService;
    @Resource
    private BusinessActivityMapper businessActivityMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResultParam  insertUserGrouping(UserGroupingEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getUser_id() != null && param.getUser_id() != 0 && StringUtil.isEmpty(param.getGroup_id())) {
                UserGroupingEntity userGroupingEntity = new UserGroupingEntity();
                userGroupingEntity.setUser_id(param.getUser_id());
                userGroupingEntity.setMaster_set_price_id(param.getMaster_set_price_id());
                userGroupingEntity.setPageSize(1);
                List<UserGroupingEntity> userGroupingEntities = mapper.querylistPageByIdAsc(userGroupingEntity);
                if (userGroupingEntities != null && userGroupingEntities.size() > 0) {
                    return ResultUtil.error(UserEum.user_10049.getCode(), UserEum.user_10049.getDesc());
                }

//            if (StringUtil.isEmpty(param.getGroup_id())) {
                String s = "";
                Integer res = 1;
                while (res != 0) {
                    s = GenSerial.generateNewCode(8, param.getUser_id().intValue(), 2, 2);
                    userGroupingEntity.setGroup_id(s);
                    res = mapper.queryListPageCount(userGroupingEntity);
                }
                param.setGroup_id(s);
                param.setIs_one_time_payment(userStudyCardMapper.selectByPrimaryKey(param.getStudy_card_id()).getIs_one_time_payment());
                Long id = getActivityId(param);
                if (id==0L){
                    return ResultUtil.error(OrderEum.order_70024.getCode(),OrderEum.order_70024.getDesc());
                }
                param.setActivity_id(id);
                ResultParam insert = super.insert(param, request, response);
                if (insert.getCode() == 0) {
                    UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                    userStudyCardEntity.setId(param.getStudy_card_id());
                    userStudyCardEntity.setUser_group_id(s);
                    int i = userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity);
                    return ResultUtil.success(s);
                }
            } else {
                if (StringUtil.isNotEmpty(param.getMobile())) {
                    if (StringUtil.isEmpty(param.getMobile())) {
                        return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
                    }
                    if (StringUtil.isEmpty(param.getVerification_code())) {
                        return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
                    }
                    if (CommonUtils.isPhone(param.getMobile())) {//验证登录名
                        ResultParam resultParam1 = smsService.validate(param.getMobile(), param.getVerification_code());
                        if (resultParam1.getCode() != 0) {
                            return resultParam1;
                        }
                    } else {
                        return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
                    }
                    UserGroupingEntity userGroupingEntity = new UserGroupingEntity();
                    userGroupingEntity.setGroup_id(param.getGroup_id());
                    int studentCount = mapper.queryListPageCount(userGroupingEntity);
                    if (studentCount >= 16) {
                        return ResultUtil.error(UserEum.user_10050.getCode(), UserEum.user_10050.getDesc());
                    } else if (studentCount < 1) {
                        return ResultUtil.error(UserEum.user_10051.getCode(), UserEum.user_10051.getDesc());
                    }
                    param.setPageSize(1);
                    userGroupingEntity = mapper.querylistPageByIdAsc(param).get(0);

                    UserEntity userEntity = new UserEntity();
                    userEntity.setLogin_name(param.getMobile());
                    userEntity.setRegister_platform("user_grouping");
                    ResultParam insert = userService.insertUserInfo(userEntity, request, response);
                    if (insert.getCode() == 0) {
                        UserGroupingEntity userGroupingEntity1 = new UserGroupingEntity();
                        userGroupingEntity1.setUser_id(userEntity.getUser_id());
                        userGroupingEntity1.setGroup_id(param.getGroup_id());
                        Integer integer = mapper.queryListPageCount(userGroupingEntity1);
                        if (integer > 0) {
                            return ResultUtil.error(UserEum.user_10049.getCode(), UserEum.user_10049.getDesc());
                        }

                        RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                        rechargeRecordEntity.setUser_id(userEntity.getUser_id());
                        rechargeRecordEntity.setStudycard_id(userGroupingEntity.getMaster_set_price_id());
                        rechargeRecordEntity.setUser_group_id(userGroupingEntity.getGroup_id());
                        ResultParam resultParam = rechargeRecordService.payOneCourse(rechargeRecordEntity, request, response);
                        if (resultParam.getCode() == 0) {
                            param.setUser_id(userEntity.getUser_id());
                            Long id = getActivityId(param);
                            param.setActivity_id(id);
                            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                            userStudyCardEntity.setUser_group_id(param.getGroup_id());
                            userStudyCardEntity.setUser_id(param.getUser_id());
                            userStudyCardEntity.setPageSize(1);
                            userStudyCardEntity = userStudyCardMapper.queryListPage(userStudyCardEntity).get(0);
                            Boolean is_one_time_payment = userStudyCardEntity.getIs_one_time_payment();
                            if (is_one_time_payment!=userStudyCardEntity.getIs_one_time_payment() && is_one_time_payment){
                                return ResultUtil.error(OrderEum.order_70025.getCode(),OrderEum.order_70025.getDesc());
                            }
                            param.setIs_one_time_payment(is_one_time_payment);
                            ResultParam insert1 = super.insert(param, request, response);
                            if (insert1.getCode() == 0) {
                                return ResultUtil.success(param.getGroup_id());
                            } else {
                                throw new Exception("插入平拼团数据失败");
                            }
                        } else {
                            throw new Exception("插入学习卡数据失败");
                        }
                    } else {
                        throw new Exception("插入用户数据失败");
                    }
                } else if (param.getUser_id() != null) {
                    UserGroupingEntity userGroupingEntity = new UserGroupingEntity();
                    userGroupingEntity.setUser_id(param.getUser_id());
                    userGroupingEntity.setGroup_id(param.getGroup_id());
                    Integer integer = mapper.queryListPageCount(userGroupingEntity);
                    if (integer > 0) {
                        return ResultUtil.error(UserEum.user_10049.getCode(), UserEum.user_10049.getDesc());
                    }
                    userGroupingEntity.setUser_id(null);
                    userGroupingEntity.setPageSize(1);
                    userGroupingEntity = mapper.querylistPageByIdAsc(userGroupingEntity).get(0);
                    RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                    rechargeRecordEntity.setUser_id(param.getUser_id());
                    rechargeRecordEntity.setStudycard_id(userGroupingEntity.getMaster_set_price_id());
                    rechargeRecordEntity.setUser_group_id(userGroupingEntity.getGroup_id());
                    ResultParam resultParam = rechargeRecordService.payOneCourse(rechargeRecordEntity, request, response);
                    if (resultParam.getCode() == 0) {
                        param.setUser_id(param.getUser_id());
                        param.setMaster_set_price_id(userGroupingEntity.getMaster_set_price_id());
                        Long id = getActivityId(param);
                        param.setActivity_id(id);
                        UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                        userStudyCardEntity.setUser_group_id(param.getGroup_id());
                        userStudyCardEntity.setUser_id(param.getUser_id());
                        userStudyCardEntity.setPageSize(1);
                        userStudyCardEntity = userStudyCardMapper.queryListPage(userStudyCardEntity).get(0);
                        Boolean is_one_time_payment = userStudyCardEntity.getIs_one_time_payment();
                        if (is_one_time_payment!=userStudyCardEntity.getIs_one_time_payment()&&is_one_time_payment){
                            return ResultUtil.error(OrderEum.order_70025.getCode(),OrderEum.order_70025.getDesc());
                        }else if (is_one_time_payment!=userStudyCardEntity.getIs_one_time_payment()&&!is_one_time_payment){
                            return ResultUtil.error(OrderEum.order_70026.getCode(),OrderEum.order_70026.getDesc());
                        }
                        param.setIs_one_time_payment(is_one_time_payment);
                        ResultParam insert1 = super.insert(param, request, response);
                        if (insert1.getCode() == 0) {
                            return ResultUtil.success(param.getGroup_id());
                        } else {
                            throw new Exception("插入拼团数据失败");
                        }
                    } else {
                        throw new Exception("插入学习卡数据失败");
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10048.getCode(), UserEum.user_10048.getDesc());
    }

    private Long getActivityId(UserGroupingEntity param) {
        BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
        businessActivityEntity.setMaster_set_price_id(param.getMaster_set_price_id());
        businessActivityEntity.setPageSize(1);
        businessActivityEntity.setType("grouping");
        businessActivityEntity.setStatus(2);
        List<BusinessActivityEntity> businessActivityEntities = businessActivityMapper.queryListPage(businessActivityEntity);
        if (businessActivityEntities!=null&&businessActivityEntities.size()>0){
            return businessActivityEntities.get(0).getId();
        }else {
            return 0L;
        }
    }

    @Override
    @Transactional
    public ResultParam updateSettlementCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id());
            if (StringUtil.isNotEmpty(userStudyCardEntity.getUser_group_id())) {
                UserGroupingEntity userGroupingEntity = new UserGroupingEntity();
                userGroupingEntity.setGroup_id(userStudyCardEntity.getUser_group_id());
                userGroupingEntity.setUser_id(rechargeRecordEntity.getUser_id());
                userGroupingEntity.setPageSize(1);
                int i = mapper.updatePresentLesson(userGroupingEntity);
                if (i > 0) {
                    userGroupingEntity = mapper.queryListPage(userGroupingEntity).get(0);
                    BusinessActivityEntity businessActivityEntity = businessActivityMapper.selectByPrimaryKey(userGroupingEntity.getActivity_id());
                    Integer count = mapper.queryMoreThanListPageCount(userGroupingEntity);
                    int num = businessActivityEntity.getNumber_of_people();
                    double ech_time_percentage = businessActivityEntity.getEach_time_percentage();
                    double ech_time_percentageMax = businessActivityEntity.getEach_time_percentage_max();
                    if (count >= num) {
                        if (num % count == 0) {
                            int multiplier = count / num;
                            double multiplierPrice = ech_time_percentage * multiplier;
                            multiplierPrice = Math.min(multiplierPrice, ech_time_percentageMax);
                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userStudyCardEntity.getUser_id());
                            BigDecimal multiply = userStudyCardEntity.getEach_lesson_price().multiply(new BigDecimal(multiplierPrice + ""));
                            userInfoEntity.setCash(userInfoEntity.getCash().add(multiply));
                            Integer integer = userInfoMapper.updateCash(userInfoEntity);
                            if (integer == 0){
                                throw new Exception("参加拼团失败");
                            }
                            List<UserGroupingEntity> list = mapper.queryListPageNotIn(userGroupingEntity);
                            for (UserGroupingEntity groupingEntity : list) {
                                userInfoEntity = userInfoMapper.selectByPrimaryKey(groupingEntity.getUser_id());
                                multiply = userStudyCardEntity.getEach_lesson_price().multiply(new BigDecimal(ech_time_percentage + ""));
                                userInfoEntity.setCash(userInfoEntity.getCash().add(multiply));
                                integer = userInfoMapper.updateCash(userInfoEntity);
                                if (integer==0){
                                    throw new Exception("参加拼团失败");
                                }
                            }
                        }
                    }
                }else {
                    throw new Exception("参加拼团失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam updateOnPaymentSettlementCash(UserGroupingEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<UserGroupingEntity> userGroupingEntities = mapper.queryGroupIdList(param);
            if (userGroupingEntities!=null&&userGroupingEntities.size()>0){
                for (UserGroupingEntity groupingEntity : userGroupingEntities) {
                    UserGroupingEntity userGroupingEntity = new UserGroupingEntity();
                    userGroupingEntity.setGroup_id(groupingEntity.getGroup_id());
                    Integer integer = mapper.queryListPageCount(userGroupingEntity);
                    BusinessActivityEntity businessActivityEntity = businessActivityMapper.selectByPrimaryKey(groupingEntity.getActivity_id());
                    Integer number_of_people = businessActivityEntity.getNumber_of_people();
                    if (integer>=number_of_people){
                        Double each_time_percentage = businessActivityEntity.getEach_time_percentage();
                        double b = new BigDecimal(integer/number_of_people*each_time_percentage).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                        rechargeRecordEntity.setUser_id(groupingEntity.getUser_id());
                        rechargeRecordEntity.setStudycard_id(businessActivityEntity.getMaster_set_price_id());
                        rechargeRecordEntity.setPageSize(1);
                        rechargeRecordEntity.setIs_one_time_payment(true);
                        rechargeRecordEntity.setType("full_purchase");
                        rechargeRecordEntity.setStatus(2);
                        rechargeRecordEntity = rechargeRecordMapper.queryListPage(rechargeRecordEntity).get(0);
                        BigDecimal finalCash = rechargeRecordEntity.getAmount().multiply(new BigDecimal(b)).setScale(2, BigDecimal.ROUND_HALF_UP);;
                        List<UserGroupingEntity> userGroupingEntities1 = mapper.queryListPage(userGroupingEntity);
                        for (UserGroupingEntity entity : userGroupingEntities1) {
                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getUser_id());
                            userInfoEntity.setCash(userInfoEntity.getCash().add(finalCash));
                            userInfoMapper.updateCash(userInfoEntity);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

}