package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.UserRecommenderIncomeLogService;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRecommenderIncomeLogServiceImpl extends BaseFrameworkServiceImpl<UserRecommenderIncomeLogEntity, Long> implements UserRecommenderIncomeLogService {

    @Resource
    private UserRecommenderIncomeLogMapper mapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private SpecialLecturerMapper specialLecturerMapper;
    @Resource
    private BusinessActivityTypeMapper businessActivityTypeMapper;


    @Override
    public List<UserRecommenderIncomeLogEntity> queryListPage(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderIncomeLogEntity> logEntities = mapper.queryListPage(userRecommenderIncomeLogEntity);
        if (logEntities != null && logEntities.size() > 0) {
            for (UserRecommenderIncomeLogEntity logEntity : logEntities) {
                HashMap<String, Object> map = new HashMap<>();
                MasterMechanismEntity mechanismEntity = null;
                if (logEntity.getMechanism_id() != 0) {
                    mechanismEntity = masterMechanismMapper.selectByPrimaryKey(logEntity.getMechanism_id());
                }
                map.put("mechanismEntity", mechanismEntity);

                MasterInfoEntity masterInfoEntity = null;
                if (logEntity.getMaster_id() != 0) {
                    masterInfoEntity = masterInfoMapper.selectByPrimaryKey(logEntity.getMaster_id());
                }
                map.put("masterInfoEntity", masterInfoEntity);

                MasterAppointmentEntity masterAppointmentEntity = null;
                if (logEntity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(logEntity.getAppointment_id());
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);

                if (logEntity.getUser_id()!=0){
                    map.put("recommenderUserInfo", JSONUtils.obj2Json(redisService.getStr(logEntity.getUser_id()+"userinfo")));
                }else {
                    map.put("recommenderUserInfo", null);
                }

                if (logEntity.getInvitation_id()!=0){
                    map.put("invitationUserInfo", JSONUtils.obj2Json(redisService.getStr(logEntity.getInvitation_id()+"userinfo")));
                }else {
                    map.put("invitationUserInfo", null);
                }

                logEntity.setMap(map);
            }
        }
        return logEntities;
    }

    @Override
    public ResultParam insert(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam;
        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
        userRecommenderIncomeLogEntity1.setRecommender_id(userRecommenderIncomeLogEntity.getRecommender_id());//?????????id
        userRecommenderIncomeLogEntity1.setType(userRecommenderIncomeLogEntity.getType());//??????
        userRecommenderIncomeLogEntity1.setPageSize(1);
        switch (userRecommenderIncomeLogEntity.getType()) {
            case "mechanism"://??????
                userRecommenderIncomeLogEntity1.setMechanism_id(userRecommenderIncomeLogEntity.getMechanism_id());//??????id
                resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
                break;

            case "mechanism_master"://????????????
                userRecommenderIncomeLogEntity1.setMechanism_id(userRecommenderIncomeLogEntity.getMechanism_id());//??????id
                userRecommenderIncomeLogEntity1.setMaster_id(userRecommenderIncomeLogEntity.getMaster_id());//??????id
                resultParam = this.checkMasterMax(userRecommenderIncomeLogEntity1);
                break;

            case "master"://??????
                userRecommenderIncomeLogEntity1.setMaster_id(userRecommenderIncomeLogEntity.getMaster_id());//??????id
                resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
                break;

            case "mechansim_appointment"://??????????????????
                userRecommenderIncomeLogEntity1.setMechanism_id(userRecommenderIncomeLogEntity.getMechanism_id());
                userRecommenderIncomeLogEntity1.setMastersetprice_id(userRecommenderIncomeLogEntity.getMastersetprice_id());
                resultParam = this.checkAppointmentMax(userRecommenderIncomeLogEntity1);
                break;

            case "user"://????????????
                userRecommenderIncomeLogEntity1.setInvitation_id(userRecommenderIncomeLogEntity.getInvitation_id());
                resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
                break;

            case "sale_course"://????????????
            case "mechanism_offline"://??????????????????
            case "master_offline"://????????????????????????
            case "system_card": //???????????????????????????
            case "sale_card": //?????????????????????????????????
            case "mechanism_sale_offline": //?????????????????????????????????
            case "master_sale_offline": //?????????????????????????????????
                return super.insert(userRecommenderIncomeLogEntity, request, response);

            case "live"://??????
            case "master_live"://????????????????????????
            case "mechanism_live"://????????????????????????
                userRecommenderIncomeLogEntity1.setMaster_id(userRecommenderIncomeLogEntity.getMaster_id());
                userRecommenderIncomeLogEntity1.setAppointment_id(userRecommenderIncomeLogEntity.getAppointment_id());
                resultParam = this.checkIsExistUpdate(userRecommenderIncomeLogEntity1, userRecommenderIncomeLogEntity);
                break;
            default:
                return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
        }
        if (resultParam.getCode() != 0 || userRecommenderIncomeLogEntity.getId() != null) {
            return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
        }
        return super.insert(userRecommenderIncomeLogEntity, request, response);
    }

    /**
     * ?????????????????? ??????
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private ResultParam checkIsExistUpdate(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1, UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity) {
        List<UserRecommenderIncomeLogEntity> logEntities = mapper.queryListPage(userRecommenderIncomeLogEntity1);
        if (logEntities != null && logEntities.size() > 0) {
            userRecommenderIncomeLogEntity.setId(logEntities.get(0).getId());
            int i = mapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);
            return ResultUtil.success();
        }
        mapper.insertSelective(userRecommenderIncomeLogEntity);
        return ResultUtil.success();

    }

    /**
     * ????????????????????????????????????
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private ResultParam checkMasterMax(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1) {
        ResultParam resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
        if (resultParam.getCode() == 0) {

            BigDecimal cashTotal = mapper.querySumMechanismMasterCash(userRecommenderIncomeLogEntity1);
            if (cashTotal.compareTo(queryMasterMaxEarn(userRecommenderIncomeLogEntity1)) < 0) {//????????????????????????
                return ResultUtil.success();
            }
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    /**
     * ??????????????????????????????
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private ResultParam checkAppointmentMax(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1) {
        ResultParam resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
        if (resultParam.getCode() == 0) {
            BigDecimal cashTotal = new BigDecimal(mapper.querySumMechanismMasterCash(userRecommenderIncomeLogEntity1).toString());
            if (cashTotal.compareTo(queryMechanismAppMaxEarn(userRecommenderIncomeLogEntity1)) < 0) {//????????????????????????
                return ResultUtil.success();
            }
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    /**
     * ??????????????????????????????
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private ResultParam checkIsExist(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1) {
        List<UserRecommenderIncomeLogEntity> query = mapper.queryListPage(userRecommenderIncomeLogEntity1);
        if (query != null && query.size() > 0) {
            return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
        }
        return ResultUtil.success();
    }

    /**
     * ????????????????????????
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private BigDecimal queryMasterMaxEarn(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1) {
        UserRecommenderEntity userRecommenderEntity = new UserRecommenderEntity();
        userRecommenderEntity.setUser_id(userRecommenderIncomeLogEntity1.getRecommender_id());
        userRecommenderEntity.setPageSize(1);
        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderMapper.queryListPage(userRecommenderEntity).get(0).getRule_id());
        return userEarnRoleEntity.getEvery_master_earn_max();
    }

    /**
     * ????????????????????????
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private BigDecimal queryMechanismAppMaxEarn(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1) {
        UserRecommenderEntity userRecommenderEntity = new UserRecommenderEntity();
        userRecommenderEntity.setUser_id(userRecommenderIncomeLogEntity1.getRecommender_id());
        userRecommenderEntity.setPageSize(1);
        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderMapper.queryListPage(userRecommenderEntity).get(0).getRule_id());
        return userEarnRoleEntity.getEvery_commodity_earn_max();
    }

    @Override
    public List<UserRecommenderIncomeLogEntity> queryMyMechanismRecommenderList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderIncomeLogEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryMechanismList(param);
            if (list!=null&&list.size()>0){
                for (UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity : list) {
                    UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                    userRecommenderIncomeLogEntity1.setCreate_time(userRecommenderIncomeLogEntity.getCreate_time());
                    userRecommenderIncomeLogEntity1.setUser_id(param.getUser_id());
                    if (param.getIs_settlement()!=null){
                        userRecommenderIncomeLogEntity1.setIs_settlement(userRecommenderIncomeLogEntity.getIs_settlement());
                    }
                    BigDecimal addCash = mapper.queryMonthAddCash(userRecommenderIncomeLogEntity1);
                    BigDecimal subCash = mapper.queryMonthSubCash(userRecommenderIncomeLogEntity1);
                    Map<String,Object> map = Maps.newHashMap();
                    map.put("addCash", addCash);
                    map.put("subCash", subCash);
                    userRecommenderIncomeLogEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<UserRecommenderIncomeLogEntity> queryDetails(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderIncomeLogEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryMechanismList(param);
            if (list != null && list.size() > 0) {
                this.setInfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryCountByTime(MasterAppointmentEntity entity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            userRecommenderIncomeLogEntity.setCreate_time(entity.getStart_time());
            userRecommenderIncomeLogEntity.setUpdate_time(entity.getEnd_time());
            userRecommenderIncomeLogEntity.setAppointment_id(entity.getId());
            userRecommenderIncomeLogEntity.setType("sale_course");
            BigDecimal courseCash = mapper.queryByTimeCash(userRecommenderIncomeLogEntity);
            Integer courseTotal = mapper.queryByTimeCount(userRecommenderIncomeLogEntity);
            map.put("courseCash", courseCash);
            map.put("courseTotal", courseTotal);

            userRecommenderIncomeLogEntity.setType("sale_card");
            BigDecimal cardCash = mapper.queryByTimeCash(userRecommenderIncomeLogEntity);
            Integer cardTotal = mapper.queryByTimeCount(userRecommenderIncomeLogEntity);
            map.put("cardCash", cardCash);
            map.put("cardTotal", cardTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam queryCourseDetails(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderIncomeLogEntity> list = Lists.newArrayList();
        try {
            if (param.getAppointment_id() != null && param.getAppointment_id() != 0) {
                UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                userRecommenderIncomeLogEntity.setAppointment_id(param.getAppointment_id());
                userRecommenderIncomeLogEntity.setType("live");
                userRecommenderIncomeLogEntity.setPageSize(1);
                List<UserRecommenderIncomeLogEntity> query = mapper.queryListPage(userRecommenderIncomeLogEntity);

                userRecommenderIncomeLogEntity.setType("master_live");
                List<UserRecommenderIncomeLogEntity> query1 = mapper.queryListPage(userRecommenderIncomeLogEntity);

                userRecommenderIncomeLogEntity.setType("mechanism_live");
                List<UserRecommenderIncomeLogEntity> query2 = mapper.queryListPage(userRecommenderIncomeLogEntity);
                //????????????
                if (param.getIs_settlement() != null && param.getIs_settlement()) {
                    //????????????
                    userRecommenderIncomeLogEntity = query.get(0);
                    UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMaster_id());
                    if (userInfoEntity.getAdmin_id() != 0) {
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(masterMechanismMapper.selectByPrimaryKey(userInfoEntity.getAdmin_id()).getUser_id());
                    }
                    userInfoEntity.setCash(userInfoEntity.getCash().add(userRecommenderIncomeLogEntity.getCash()));
                    synchronized (userInfoEntity) {
                        userInfoMapper.updateCash(userInfoEntity);
                    }
                    userRecommenderIncomeLogEntity.setIs_settlement(true);
                    mapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);

                    if (query1 != null && query1.size() > 0) {
                        userRecommenderIncomeLogEntity = query1.get(0);
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getRecommender_id()).getUser_id());
                        userInfoEntity.setCash(userInfoEntity.getCash().add(userRecommenderIncomeLogEntity.getCash()));
                        synchronized (userInfoEntity) {
                            userInfoMapper.updateCash(userInfoEntity);
                        }
                        userRecommenderIncomeLogEntity.setIs_settlement(true);
                        mapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);
                    }

                    if (query2 != null && query2.size() > 0) {
                        userRecommenderIncomeLogEntity = query2.get(0);
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getRecommender_id()).getUser_id());
                        userInfoEntity.setCash(userInfoEntity.getCash().add(userRecommenderIncomeLogEntity.getCash()));
                        synchronized (userInfoEntity) {
                            userInfoMapper.updateCash(userInfoEntity);
                        }
                        userRecommenderIncomeLogEntity.setIs_settlement(true);
                        mapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);
                    }
                } else {
                    list.addAll(query);
                    list.addAll(query1);
                    list.addAll(query2);
                    return ResultUtil.success(list);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(MasterEum.master_40007.getCode(), MasterEum.master_40007.getDesc());
    }

    @Override
    public Map<String, Object> queryMechanismManagerList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserRecommenderIncomeLogEntity> list = mapper.queryMechanismList(param);
            Integer total = mapper.queryMechanismListCount(param);
            map.put("total", total);
            if (total > 0) {
                setInfo(list);
            }
            map.put("rows", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam queryMechanismCashTotalList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserRecommenderIncomeLogEntity> list = mapper.queryMechanismIdList(param);
            Integer total = mapper.queryMechanismIdListCount(param);
            if (total > 0) {
                for (UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity : list) {
                    userRecommenderIncomeLogEntity.setIs_settlement(true);
                    BigDecimal settlementCash = mapper.queryTotalCash(userRecommenderIncomeLogEntity);
                    userRecommenderIncomeLogEntity.setIs_settlement(false);
                    BigDecimal notSettlementCash = mapper.queryTotalCash(userRecommenderIncomeLogEntity);
                    Map<String, Object> map1 = Maps.newHashMap();
                    map1.put("settlementCash", settlementCash);
                    map1.put("notSettlementCash", notSettlementCash);
                    map1.put("mechanismOwnerInfo", JSONUtils.obj2Json(redisService.getStr(
                            masterMechanismMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMechanism_id()).getUser_id() + "userinfo")));
                    userRecommenderIncomeLogEntity.setMap(map1);

                }
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    @Transactional
    public void updateInviteCouponCash(UserRecommenderIncomeLogEntity param) {
        try {
//            List<UserRecommenderIncomeLogEntity> list = mapper.queryMoreThanWeek(param);
            param.setIs_settlement(false);
            param.setType("invite_coupon");
            List<UserRecommenderIncomeLogEntity> list = mapper.queryCanSettel(param);
            if (list != null && list.size() > 0) {
                for (UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity : list) {
                    BusinessActivityTypeEntity businessActivityTypeEntity = businessActivityTypeMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getActivity_id().intValue());
                    int bound = userRecommenderIncomeLogEntity.getInvite_count()/(
                            businessActivityTypeEntity.getNumber_of_people()==0?1:businessActivityTypeEntity.getNumber_of_people());
                    int remainder = userRecommenderIncomeLogEntity.getInvite_count()%businessActivityTypeEntity.getNumber_of_people();
                    BigDecimal cash = new BigDecimal(bound+"").multiply(businessActivityTypeEntity.getEach_time_percentage());
                    UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getUser_id());
                    userInfoEntity.setCash(userInfoEntity.getCash().add(cash));
                    Integer integer = userInfoMapper.updateCash(userInfoEntity);
                    userRecommenderIncomeLogEntity.setInvite_count(remainder);
                    int i1 = mapper.updateSettleTrue(userRecommenderIncomeLogEntity);
                    userRecommenderIncomeLogEntity.setCash(new BigDecimal(0));
                    userRecommenderIncomeLogEntity.setInvite_count(0);
                    userRecommenderIncomeLogEntity.setInvitation_id(0L);
                    int i2 = mapper.insertSelective(userRecommenderIncomeLogEntity);
                    int i = rechargeRecordMapper.updateSettlement(userRecommenderIncomeLogEntity.getMastersetprice_id());
                }
            }
            logger.info("????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        ResultUtil.success();
    }

    @Override
    public ResultParam queryRanking(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        ArrayList<InviteRankEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryRanking(param);
            for (InviteRankEntity inviteRankEntity : list) {
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(inviteRankEntity.getUser_id());
                inviteRankEntity.setNickName(userInfoEntity.getNick_name());
                inviteRankEntity.setPic(userInfoEntity.getAvatar());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

    @Override
    public Map<String, Object> queryDetailList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserRecommenderIncomeLogEntity> list = mapper.queryListPage(param);
            Integer integer = mapper.queryListPageCount(param);
            if (integer > 0) {
                for (UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity : list) {
                    HashMap<String, Object> map1 = Maps.newHashMap();
                    map1.put("inviteUserInfo", JSONUtils.obj2Json(redisService.getStr(userRecommenderIncomeLogEntity.getUser_id() + "userinfo")));
                    map1.put("invitationUserInfo", JSONUtils.obj2Json(redisService.getStr(userRecommenderIncomeLogEntity.getInvitation_id() + "userinfo")));
                    userRecommenderIncomeLogEntity.setMap(map1);
                }
            }
            map.put("total", integer);
            map.put("rows", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    @Transactional
    public synchronized ResultParam detail(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = mapper.selectByPrimaryKey(param.getId());
            if (userRecommenderIncomeLogEntity.getIs_settlement()) {
                return ResultUtil.error(OrderEum.order_70041.getCode(), OrderEum.order_70041.getDesc());
            }
            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getUser_id());
            userInfoEntity.setCash(userInfoEntity.getCash().add(userRecommenderIncomeLogEntity.getCash()));
            Integer integer = userInfoMapper.updateCash(userInfoEntity);
            if (integer == 0) {
                throw new Exception("????????????");
            }
            userRecommenderIncomeLogEntity.setIs_settlement(true);
            int i = mapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);
            if (i == 0) {
                throw new Exception("????????????");
            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(OrderEum.order_70040.getCode(), OrderEum.order_70040.getDesc());
    }

    @Override
    public ResultParam insertTransfer(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
        userRecommenderIncomeLogEntity.setType(entity.getRcharge_type());//??????\
        userRecommenderIncomeLogEntity.setUser_id(entity.getUser_id());
        userRecommenderIncomeLogEntity.setCash(entity.getAmount().negate());
        userRecommenderIncomeLogEntity.setCash_describe(entity.getRcharge_abstract());
        userRecommenderIncomeLogEntity.setRecharge_record_id(entity.getId());
        userRecommenderIncomeLogEntity.setIs_settlement(true);

        int i = mapper.insertSelective(userRecommenderIncomeLogEntity);
        return ResultUtil.success(i);
    }

    private void setInfo(List<UserRecommenderIncomeLogEntity> list) {
        for (UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity : list) {
            HashMap<String, Object> map = new HashMap<>();
            MasterMechanismEntity mechanismEntity = null;
            if (userRecommenderIncomeLogEntity.getMechanism_id() != 0) {
                mechanismEntity = masterMechanismMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMechanism_id());
            }
            map.put("mechanismEntity", mechanismEntity);

            MasterInfoEntity masterInfoEntity = null;
            if (userRecommenderIncomeLogEntity.getMaster_id() != 0) {
                masterInfoEntity = masterInfoMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMaster_id());
            }
            map.put("masterInfoEntity", masterInfoEntity);

            MasterAppointmentEntity masterAppointmentEntity = null;
            if (userRecommenderIncomeLogEntity.getAppointment_id() != 0) {
                masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getAppointment_id());
            }
            map.put("masterAppointmentEntity", masterAppointmentEntity);

            MasterSetPriceEntity masterSetPriceEntity = null;
            if (userRecommenderIncomeLogEntity.getMastersetprice_id() != 0) {
                masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMastersetprice_id());
            }
            map.put("masterSetPriceEntity", masterSetPriceEntity);

            RechargeRecordEntity rechargeRecordEntity = null;
            if (userRecommenderIncomeLogEntity.getRecharge_record_id() != 0) {
                rechargeRecordEntity = rechargeRecordMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getRecharge_record_id());
            }
            map.put("rechargeRecordEntity", rechargeRecordEntity);

            UserEarnRoleEntity userEarnRoleEntity = null;
            SpecialLecturerEntity specialLecturerEntity = null;
            if (userRecommenderIncomeLogEntity.getRole_id() != 0) {
                if ("live".equals(userRecommenderIncomeLogEntity.getType())) {
                    specialLecturerEntity = specialLecturerMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getRole_id());
                } else {
                    userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getRole_id());
                }
            }
            map.put("userLiveRoleEntity", specialLecturerEntity);
            map.put("userEarnRoleEntity", userEarnRoleEntity);

            JSONObject jsonObject = new JSONObject();
            if (userRecommenderIncomeLogEntity.getUser_id() != 0) {
                jsonObject = JSONUtils.obj2Json(redisService.getStr(userRecommenderIncomeLogEntity.getUser_id() + "userinfo"));
            }
            map.put("userInfoEntity", jsonObject);

            JSONObject inviteUserEntity = new JSONObject();
            if (userRecommenderIncomeLogEntity.getInvitation_id() != 0) {
                inviteUserEntity = JSONUtils.obj2Json(redisService.getStr(userRecommenderIncomeLogEntity.getInvitation_id() + "userinfo"));
            }
            map.put("inviteUserEntity", inviteUserEntity);


            userRecommenderIncomeLogEntity.setMap(map);
        }
    }

    @Override
    public Map<String, Object> queryMechanismRecommenderCash(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(param.getUser_id());
            map.put("cash", userInfoEntity.getCash());

            BigDecimal dayCash = mapper.queryDayCash(param);
            map.put("dayCash", dayCash);

            BigDecimal weekCash = mapper.queryWeekCash(param);
            map.put("weekCash", weekCash);

            BigDecimal monthCash = mapper.queryMonthCash(param);
            map.put("monthCash", monthCash);

            BigDecimal totalCash = mapper.queryTotalCash(param);
            map.put("totalCash", totalCash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<UserRecommenderIncomeLogEntity> queryMechanismCashList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderIncomeLogEntity> list = mapper.queryMechanismCashList(param);
        if (list != null && list.size() > 0) {
            for (UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity : list) {
                HashMap<String, Object> map = new HashMap<>();
                MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
                if ("mechanism_recommender".equals(param.getRecommender_type())) {
                    mechanismEntity = masterMechanismMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMechanism_id());
                }
                map.put("mechanismEntity", mechanismEntity);

                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                if ("master_recommender".equals(param.getRecommender_type())) {
                    masterInfoEntity = masterInfoMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMaster_id());
                }
                map.put("masterInfoEntity", masterInfoEntity);

                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (userRecommenderIncomeLogEntity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getAppointment_id());
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);

                MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                if (userRecommenderIncomeLogEntity.getMastersetprice_id() != 0) {
                    masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getAppointment_id());
                }
                map.put("masterSetPriceEntity", masterSetPriceEntity);

                userRecommenderIncomeLogEntity.setMap(map);
            }
        }
        return list;
    }

    @Override
    public List<UserRecommenderIncomeLogEntity> queryInviteCashList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderIncomeLogEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryInviteUserList(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryInviteCash(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            BigDecimal dayCash = mapper.queryDayInviteCash(param);
            map.put("dayCash", dayCash);

            BigDecimal monthCash = mapper.queryMonthInviteCash(param);
            map.put("monthCash", monthCash);

            BigDecimal totalCash = mapper.queryTotalInviteCash(param);
            map.put("totalCash", totalCash);

            param.setIs_settlement(true);
            BigDecimal settlementCash = mapper.queryTotalInviteCash(param);
            map.put("settlementCash", settlementCash);

            param.setIs_settlement(false);
            BigDecimal noSettlementCash = mapper.queryTotalInviteCash(param);
            map.put("noSettlementCash", noSettlementCash);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam querySettlementLog(UserRecommenderIncomeLogEntity param) {
        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
        userRecommenderIncomeLogEntity.setIs_settlement(false);
        List<UserRecommenderIncomeLogEntity> logEntities = mapper.querySettelList(userRecommenderIncomeLogEntity);
        if (logEntities != null && logEntities.size() > 0) {
            for (UserRecommenderIncomeLogEntity logEntity : logEntities) {
                switch (logEntity.getType()) {
                    case "mechanism_offline":
                        this.updateMasterCash(logEntity);
                        break;
                    case "master_offline":
                        this.updateMechanismCash(logEntity);
                        break;
                    case "sale_course":
                        this.updateSaleCourseCash(logEntity);
                        break;
                    default:
                }
            }
        }
        return ResultUtil.success();
    }

    private void updateSaleCourseCash(UserRecommenderIncomeLogEntity logEntity) {
        PushMessageParam pushMessageParam = new PushMessageParam();
        pushMessageParam.setType_id(logEntity.getId());
        pushMessageParam.setTarget_id(logEntity.getMaster_id());
        pushMessageParam.setPush_type("cash");
        pushMessageParam.setOpera_type("saleCash");
        pushMessageParam.setContent("???????????????????????????" + logEntity.getCash() + "???");
        pushMessageParam.setType("cash");
        pushMessageParam.setTitle("????????????????????????");
        pushMessageParam.setIs_teach_paypal(logEntity.getIs_teach_paypal());
        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);

        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);

        logEntity.setIs_settlement(true);
        mapper.updateByPrimaryKeySelective(logEntity);
        UserInfoEntity userInfoEntity;
        if (logEntity.getMechanism_id() != 0) {
            Long user_id = masterMechanismMapper.selectByPrimaryKey(logEntity.getMechanism_id()).getUser_id();
            userInfoEntity = userInfoMapper.selectByPrimaryKey(user_id);
        } else if (logEntity.getMaster_id() != 0) {
            userInfoEntity = userInfoMapper.selectByPrimaryKey(logEntity.getMaster_id());
        } else {
            userInfoEntity = userInfoMapper.selectByPrimaryKey(logEntity.getUser_id());
        }
        /**
         * ??????????????????
         */
        userInfoEntity.setCash(userInfoEntity.getCash().add(logEntity.getCash().multiply(new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString()))));//????????????
        synchronized (userInfoEntity) {
            userInfoMapper.updateCash(userInfoEntity);
        }
    }

    /**
     * ?????????????????????
     *
     * @param logEntity
     */
    private void updateMechanismCash(UserRecommenderIncomeLogEntity logEntity) {

        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.selectByPrimaryKey(logEntity.getRecharge_record_id());
        userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(logEntity.getCash()));
        synchronized (userRecommenderEntity) {
            userRecommenderMapper.updateCash(userRecommenderEntity);
        }

        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
        userInfoEntity.setCash(userInfoEntity.getCash().add(logEntity.getCash()));
        synchronized (userInfoEntity) {
            userInfoMapper.updateCash(userInfoEntity);
        }

        PushMessageParam pushMessageParam = new PushMessageParam();
        pushMessageParam.setType_id(logEntity.getId());
        pushMessageParam.setTarget_id(userRecommenderEntity.getUser_id());
        pushMessageParam.setPush_type("cash");
        pushMessageParam.setOpera_type("cash");
        pushMessageParam.setContent("?????????" + logEntity.getCash() + "???");
        pushMessageParam.setType("??????????????????");
        pushMessageParam.setIs_teach_paypal(logEntity.getIs_teach_paypal());
        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
    }

    private void updateMasterCash(UserRecommenderIncomeLogEntity logEntity) {
        /**
         * ?????????????????????
         */
        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.selectByPrimaryKey(logEntity.getRecommender_id());

        userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(logEntity.getCash()));
        synchronized (userRecommenderEntity) {
            userRecommenderMapper.updateCash(userRecommenderEntity);
        }

        /**
         * ????????????
         */
        UserInfoEntity recommenderUserInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
        recommenderUserInfoEntity.setCash(logEntity.getCash());
        synchronized (recommenderUserInfoEntity) {
            userInfoMapper.updateCash(recommenderUserInfoEntity);
        }

        PushMessageParam pushMessageParam = new PushMessageParam();
        pushMessageParam.setType_id(logEntity.getId());
        pushMessageParam.setTarget_id(userRecommenderEntity.getUser_id());
        pushMessageParam.setPush_type("cash");
        pushMessageParam.setOpera_type("cash");
        pushMessageParam.setContent("?????????" + logEntity.getCash() + "???");
        pushMessageParam.setType("cash");
        pushMessageParam.setTitle("??????????????????????????????");
        pushMessageParam.setIs_teach_paypal(logEntity.getIs_teach_paypal());
        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
    }
}