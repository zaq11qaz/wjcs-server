package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.comm.util.EmojiUtil;
import com.huihe.eg.comm.util.ExcelFormatUtil;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.model.count.AssetsParam;
import com.huihe.eg.user.model.count.ChartParam;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;
import com.huihe.eg.user.service.impl.pay.PayServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class RechargeRecordServiceImpl extends BaseFrameworkServiceImpl<RechargeRecordEntity, Long> implements RechargeRecordService {

    @Resource
    private RechargeRecordMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private RechargeRecordService rechargeRecordService;
    @Resource
    private UserRecommenderGroupInterlinkMapper userRecommenderGroupInterlinkMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserOrderService orderService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MasterCommentMapper masterCommentMapper;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource
    private UserRecommenderIncomeLogMapper userRecommenderIncomeLogMapper;
    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private UserStudyCardService userStudyCardService;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private PayServiceImpl payService;
    @Resource
    private MasterSetPricePriceMapper masterSetPricePriceMapper;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private BusinessActivityMapper businessActivityMapper;
    @Resource
    private BusinessActivityTypeMapper businessActivityTypeMapper;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private MechanismClassesMapper mechanismClassesMapper;
    @Resource
    private MasterSetPriceDisplayMapper masterSetPriceDisplayMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRecommenderGroupMapper userRecommenderGroupMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public Map<String, Object> studyCardCount(RechargeRecordEntity param) {
        Map<String, Object> map = null;
        try {
            List<ChartParam> chartParam = mapper.studyCardStatistics(param);
            List<AssetsParam> chartTotal = mapper.studyCardProportionStatistics(param);
            List<AssetsParam> statisticsTotal = mapper.studyCardTotalProportionStatistics();
            List<AssetsParam> statisticsToday = mapper.studyCardTodayProportionStatistics();
            map = new HashMap<>();
            map.put("chartTotal", chartTotal);//?????????????????????
            map.put("chartParam", chartParam);//???????????????
            map.put("statisticsTotal", statisticsTotal);//?????????
            map.put("statisticsToday", statisticsToday);//????????????
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> memberAssetsCount(RechargeRecordEntity param) {
        Map<String, Object> map = null;
        try {
            List<ChartParam> chartParam = mapper.memberAssetsStatistics(param);
            List<AssetsParam> chartTotal = mapper.memberProportionStatistics(param);
            List<AssetsParam> statisticsTotal = mapper.memberTotalProportionStatistics();
            List<AssetsParam> statisticsToday = mapper.memberTodayProportionStatistics();
            map = new HashMap<>();
            map.put("chartTotal", chartTotal);//??????????????????
            map.put("chartParam", chartParam);//???????????????
            map.put("statisticsTotal", statisticsTotal);//?????????
            map.put("statisticsToday", statisticsToday);//????????????
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> AssetsCount(RechargeRecordEntity param) {
        Map<String, Object> map = null;
        try {
            ChartParam chartParam = mapper.accountAssetsStatistics(param);
            AssetsParam statisticsTotal = mapper.assetsTotalProportionStatistics(param);
            AssetsParam statisticsToday = mapper.assetsTodayProportionStatistics(param);
            map = new HashMap<>();
            map.put("chartParam", chartParam);//???????????????
            map.put("statisticsTotal", statisticsTotal);//?????????
            map.put("statisticsToday", statisticsToday);//????????????
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> totalAssetsCount(RechargeRecordEntity param) {
        Map<String, Object> map = null;
        try {
            List<ChartParam> chartParam = mapper.totalAssetsStatistics(param);
            List<AssetsParam> chartTotal = mapper.totalProportionStatistics(param);
            List<AssetsParam> statisticsTotal = mapper.totalTotalProportionStatistics();
            List<AssetsParam> statisticsToday = mapper.totalTodayProportionStatistics();
            map = new HashMap<>();
            map.put("chartTotal", chartTotal);//???????????????
            map.put("chartParam", chartParam);//???????????????
            map.put("statisticsTotal", statisticsTotal);//?????????
            map.put("statisticsToday", statisticsToday);//????????????
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public Map<String, Object> queryPayCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;

        try {
            param.setFinished(true);
            map = new HashMap<>();
            BigDecimal dayPayTotal = mapper.queryDayTotal(param);
            map.put("dayPayTotal", dayPayTotal);//????????????

            BigDecimal weekPayTotal = mapper.queryWeekTotal(param);
            map.put("weekPayTotal", weekPayTotal);//?????????

            BigDecimal monthPayTotal = mapper.queryMonthTotal(param);
            map.put("monthPayTotal", monthPayTotal);//?????????

            BigDecimal payCountTotal = mapper.queryPayTotal(param);
            map.put("payCountTotal", payCountTotal);//?????????

            Integer payTotal = mapper.queryListPageCount(param);
            map.put("payTotal", payTotal);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryTodayFirstTimePay(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            param.setFinished(true);

            param.setSource("ios");
            Integer iosFirstPayCount = getFirstPayCount(param);
            map.put("iosFirstPayCount", iosFirstPayCount);

            param.setSource("android");
            Integer androidFirstPayCount = getFirstPayCount(param);
            map.put("androidFirstPayCount", androidFirstPayCount);

            param.setSource("pc");
            Integer pcFirstPayCount = getFirstPayCount(param);
            map.put("pcFirstPayCount", pcFirstPayCount);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryTodayPayTotal(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {

            param.setFinished(true);

            param.setSource("ios");
            BigDecimal iosDayTotal = mapper.queryDayTotal(param);
            map.put("iosPayDayTotal", iosDayTotal);

            param.setSource("android");
            BigDecimal androidDayTotal = mapper.queryDayTotal(param);
            map.put("androidPayDayTotal", androidDayTotal);

            param.setSource("pc");
            BigDecimal pcDayTotal = mapper.queryDayTotal(param);
            map.put("pcPayDayTotal", pcDayTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryBuyCourse(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<RechargeRecordEntity> list = mapper.queryListPage(param);
            if (list != null && list.size() > 0) {
                map.put("recording", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<RechargeRecordEntity> queryMyRecordList(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<RechargeRecordEntity> list = Lists.newArrayList();
        if (param.getId() == null) {
            list = mapper.queryOrderList(param);
        } else {
            list.add(mapper.selectByPrimaryKey(param.getId()));
        }
        this.setListInfo(list);
        return list;
    }

    private void setListInfo(List<RechargeRecordEntity> list) {
        for (RechargeRecordEntity rechargeRecordEntity : list) {
            MasterSetPriceEntity masterSetPriceEntity = null;
            if (rechargeRecordEntity.getStudy_type().equalsIgnoreCase("mechanism_offline")) {
                masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id());
            }
            HashMap<String, Object> map = new HashMap<>();
            if (masterSetPriceEntity != null) {
                MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                if (masterSetPriceEntity.getMechanism_id() != 0) {
                    masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id());
                }
                map.put("mechanismEntity", masterMechanismEntity);

                MasterAppointmentEntity masterAppointmentEntity = null;
                if (masterSetPriceEntity.getMaster_appointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(masterSetPriceEntity.getMaster_appointment_id());
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);

                if (masterSetPriceEntity.getUser_id() != 0) {
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(masterSetPriceEntity.getUser_id() + "userinfo")));
                } else {
                    map.put("masterinfo", null);
                }
            }
            if (StringUtil.isNotEmpty(rechargeRecordEntity.getInvite_code())) {
                UserInfoEntity userInfoEntity = userInfoMapper.queryByInviteCode(rechargeRecordEntity.getInvite_code());
                map.put("inviteMaster", userInfoEntity);
                if (rechargeRecordEntity.getActivity_id()!=null&&rechargeRecordEntity.getActivity_id()!=0) {
                    UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(userInfoEntity.getInvite_code());
                    userRecommenderEntity.setActivity_id(rechargeRecordEntity.getActivity_id());
                    UserRecommenderGroupInterlinkEntity userRecommenderGroupInterlinkEntity = new UserRecommenderGroupInterlinkEntity();
                    userRecommenderGroupInterlinkEntity.setUser_id(userInfoEntity.getUser_id());
                    userRecommenderGroupInterlinkEntity.setRecommender_id(userRecommenderEntity.getId());
                    userRecommenderGroupInterlinkEntity.setStatus(2);
                    userRecommenderGroupInterlinkEntity.setPageSize(1);
                    List<UserRecommenderGroupInterlinkEntity> userRecommenderGroupInterlinkEntities = userRecommenderGroupInterlinkMapper.queryListPage(userRecommenderGroupInterlinkEntity);
                    if (userRecommenderGroupInterlinkEntities!=null&&userRecommenderGroupInterlinkEntities.size()>0){
                        UserRecommenderGroupEntity userRecommenderGroupEntity = userRecommenderGroupMapper.selectByPrimaryKey(userRecommenderGroupInterlinkEntities.get(0).getGroup_id());
                        map.put("userRecommenderGroupEntity", userRecommenderGroupEntity);
                        UserRecommenderEntity userRecommenderEntity1 = userRecommenderMapper.selectByPrimaryKey(userRecommenderGroupEntity.getAdmin_id());
                        map.put("groupAdminInfo", userRecommenderEntity1);
                    }
                }
            } else {
                map.put("inviteMaster", null);
            }
            if (!map.containsKey("userRecommenderGroupEntity")){
                map.put("userRecommenderGroupEntity", null);
            }
            if (!map.containsKey("groupAdminInfo")){
                map.put("groupAdminInfo", null);
            }
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(rechargeRecordEntity.getUser_id() + "userinfo")));

            map.put("masterSetPriceEntity", masterSetPriceEntity);

            MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
            masterCommentEntity.setPageSize(1);
            masterCommentEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
            List<MasterCommentEntity> masterCommentEntities = masterCommentMapper.queryListPage(masterCommentEntity);
            map.put("isComment", masterCommentEntities != null && masterCommentEntities.size() > 0);

            if (StringUtil.isNotEmpty(rechargeRecordEntity.getSelected_id())){
                String[] split = rechargeRecordEntity.getSelected_id().split(",");
                List<MasterSetPriceEntity> masterSetPriceEntities = masterSetPriceMapper.queryByIdList(split);
                map.put("selectMasterSetPriceEntitys", masterSetPriceEntities);
            }else {
                map.put("selectMasterSetPriceEntitys", null);
            }
            rechargeRecordEntity.setMap(map);
        }
    }

    private void setMechanismListInfo(List<RechargeRecordEntity> list) {
        for (RechargeRecordEntity rechargeRecordEntity : list) {
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id());
            if (masterSetPriceEntity != null) {
                HashMap<String, Object> map = new HashMap<>();

                MasterAppointmentEntity masterAppointmentEntity = null;
                if (masterSetPriceEntity.getMaster_appointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(masterSetPriceEntity.getMaster_appointment_id());
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);

                UserAppointmentEntity userAppointmentEntity = null;
                if (masterSetPriceEntity.getMaster_appointment_id() != 0) {
                    userAppointmentEntity = userAppointmentMapper.selectByPrimaryKey(rechargeRecordEntity.getUser_appointment_id());
                }
                map.put("userAppointmentEntity", userAppointmentEntity);

                if (masterSetPriceEntity.getUser_id() != 0) {
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(masterSetPriceEntity.getUser_id() + "userinfo")));
                } else {
                    map.put("masterinfo", null);
                }
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(rechargeRecordEntity.getUser_id() + "userinfo")));

                if (masterSetPriceEntity.getActivity_id() != null) {
                    BusinessActivityEntity activityEntity = businessActivityMapper.selectByPrimaryKey(masterSetPriceEntity.getActivity_id());
                    map.put("activityEntity", activityEntity);
                } else {
                    map.put("activityEntity", null);
                }
                map.put("masterSetPriceEntity", masterSetPriceEntity);

                MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                masterCommentEntity.setPageSize(1);
                masterCommentEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                List<MasterCommentEntity> masterCommentEntities = masterCommentMapper.queryListPage(masterCommentEntity);
                map.put("isComment", masterCommentEntities != null && masterCommentEntities.size() > 0);
                rechargeRecordEntity.setMap(map);
            }
        }
    }

    private void setUserListInfo(List<RechargeRecordEntity> list) {
        for (RechargeRecordEntity rechargeRecordEntity : list) {
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id());
            if (masterSetPriceEntity != null) {
                HashMap<String, Object> map = new HashMap<>();

                MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                if (masterSetPriceEntity.getMechanism_id() != 0) {
                    masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id());
                }
                map.put("mechanismEntity", masterMechanismEntity);

                MasterAppointmentEntity masterAppointmentEntity = null;
                if (rechargeRecordEntity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(rechargeRecordEntity.getAppointment_id());
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);

                if (rechargeRecordEntity.getMaster_id() != 0) {
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(rechargeRecordEntity.getMaster_id() + "userinfo")));
                } else {
                    map.put("masterinfo", null);
                }
                map.put("masterSetPriceEntity", masterSetPriceEntity);

                MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                masterCommentEntity.setPageSize(1);
                masterCommentEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                List<MasterCommentEntity> masterCommentEntities = masterCommentMapper.queryListPage(masterCommentEntity);
                map.put("isComment", masterCommentEntities != null && masterCommentEntities.size() > 0);
                rechargeRecordEntity.setMap(map);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam updateRechargeRecordStatus(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        RechargeRecordEntity rechargeRecordEntity = mapper.selectByPrimaryKey(param.getId());
        if (rechargeRecordEntity != null) {
            if (param.getMechanism_id() != null && param.getMechanism_id() != 0) {
                if (rechargeRecordEntity.getMechanism_id().equals(param.getMechanism_id())) {
                    param.setStatus(3);
                    mapper.updateByPrimaryKeySelective(param);
                    MasterMechanismEntity masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id());
                    if (StringUtil.isNotEmpty(masterMechanismEntity.getRecommender_id())) {
                        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterMechanismEntity.getRecommender_id());
                        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                        BigDecimal multiply = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString()));
                        rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().subtract(multiply));
                        //?????????
                        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                        userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                        userRecommenderIncomeLogEntity.setMechanism_id(masterMechanismEntity.getId());
                        userRecommenderIncomeLogEntity.setIs_settlement(true);
                        userRecommenderIncomeLogEntity.setCash(multiply);
                        userRecommenderIncomeLogEntity.setType("mechanism_offline");
                        userRecommenderIncomeLogEntity.setCash_describe("????????????????????????");
                        userRecommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                        userRecommenderIncomeLogEntity.setRecommender_type("mechanism_recommender");
                        ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                        if (insert.getCode() == 0) {
                            userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);
                            BigDecimal amount = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString()));
                            masterMechanismEntity.setCash(rechargeRecordEntity.getAmount());
                            userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                            userRecommenderIncomeLogEntity1.setMechanism_id(masterMechanismEntity.getId());
                            userRecommenderIncomeLogEntity1.setIs_settlement(true);
                            userRecommenderIncomeLogEntity1.setCash(rechargeRecordEntity.getAmount());
                            userRecommenderIncomeLogEntity1.setType("mechanism_sale_offline");
                            userRecommenderIncomeLogEntity1.setCash_describe("??????????????????");
                            userRecommenderIncomeLogEntity1.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                            userRecommenderIncomeLogEntity1.setRole_id(userEarnRoleEntity.getId());
                            ResultParam insert1 = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity1, request, response);
                            //???????????????????????????
                            userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(multiply));
                            userRecommenderMapper.updateByPrimaryKeySelective(userRecommenderEntity);
                            //??????????????????
                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
                            userInfoEntity.setCash(userInfoEntity.getCash().add(multiply));
                            synchronized (userInfoEntity) {
                                userInfoMapper.updateCash(userInfoEntity);
                            }
                            userInfoEntity = userInfoMapper.selectByPrimaryKey(masterMechanismEntity.getUser_id());
                            userInfoEntity.setCash(userInfoEntity.getCash().add(rechargeRecordEntity.getAmount()));
                            synchronized (userInfoEntity) {
                                userInfoMapper.updateCash(userInfoEntity);
                            }
                        }
                    } else {
                        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);
                        BigDecimal amount = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString()));
                        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                        userRecommenderIncomeLogEntity1.setMechanism_id(masterMechanismEntity.getId());
                        userRecommenderIncomeLogEntity1.setIs_settlement(true);
                        userRecommenderIncomeLogEntity1.setCash(rechargeRecordEntity.getAmount());
                        userRecommenderIncomeLogEntity1.setType("mechanism_sale_offline");
                        userRecommenderIncomeLogEntity1.setCash_describe("??????????????????");
                        userRecommenderIncomeLogEntity1.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                        userRecommenderIncomeLogEntity1.setRole_id(userEarnRoleEntity.getId());
                        ResultParam insert1 = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity1, request, response);
                        if (insert1.getCode() == 0) {
                            //??????????????????
                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(masterMechanismEntity.getUser_id());
                            userInfoEntity.setCash(userInfoEntity.getCash().add(amount));
                            synchronized (userInfoEntity) {
                                userInfoMapper.updateCash(userInfoEntity);
                            }
                        }
                    }
                    masterMechanismMapper.updateCash(masterMechanismEntity);
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(UserEum.user_10038.getCode(), UserEum.user_10038.getDesc());
                }
            } else if (param.getMaster_id() != null && param.getMaster_id() != 0) {
                if (rechargeRecordEntity.getMaster_id().equals(param.getMaster_id())) {
                    param.setStatus(3);
                    mapper.updateByPrimaryKeySelective(param);
                    MasterInfoEntity masterInfoEntity = masterInfoMapper.selectByPrimaryKey(rechargeRecordEntity.getMaster_id());
                    if (StringUtil.isNotEmpty(masterInfoEntity.getInvitees_id())) {
                        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterInfoEntity.getInvitees_id());
                        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                        BigDecimal multiply = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString()));
                        rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().subtract(multiply));
                        //?????????
                        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                        userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getId());
                        userRecommenderIncomeLogEntity.setIs_settlement(true);
                        userRecommenderIncomeLogEntity.setCash(rechargeRecordEntity.getAmount());
                        userRecommenderIncomeLogEntity.setType("master_offline");
                        userRecommenderIncomeLogEntity.setCash_describe("?????????????????????");
                        userRecommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                        userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                        ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                        if (insert.getCode() == 0) {
                            //?????????
                            userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);
                            BigDecimal amount = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getMaster_bonus().toString()));
                            userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                            userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getId());
                            userRecommenderIncomeLogEntity.setIs_settlement(true);
                            userRecommenderIncomeLogEntity.setCash(amount);
                            userRecommenderIncomeLogEntity.setType("master_sale_offline");
                            userRecommenderIncomeLogEntity.setCash_describe("?????????????????????");
                            userRecommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                            userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                            ResultParam insert1 = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                            //??????????????????
                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
                            userInfoEntity.setCash(userInfoEntity.getCash().add(multiply));
                            synchronized (userInfoEntity) {
                                userInfoMapper.updateCash(userInfoEntity);
                            }
                            userInfoEntity = userInfoMapper.selectByPrimaryKey(masterInfoEntity.getUser_id());
                            userInfoEntity.setCash(userInfoEntity.getCash().add(amount));
                            synchronized (userInfoEntity) {
                                userInfoMapper.updateCash(userInfoEntity);
                            }
                        }
                    } else {
                        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);
                        BigDecimal amount = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getMaster_bonus().toString()));
                        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                        userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getId());
                        userRecommenderIncomeLogEntity.setIs_settlement(true);
                        userRecommenderIncomeLogEntity.setCash(amount);
                        userRecommenderIncomeLogEntity.setType("master_sale_offline");
                        userRecommenderIncomeLogEntity.setCash_describe("?????????????????????");
                        userRecommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                        userRecommenderIncomeLogEntity.setRole_id(0L);
                        ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                        if (insert.getCode() == 0) {
                            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(masterInfoEntity.getUser_id());
                            userInfoEntity.setCash(userInfoEntity.getCash().add(amount));
                            synchronized (userInfoEntity) {
                                userInfoMapper.updateCash(userInfoEntity);
                            }
                        }
                    }
                }
            }
            return ResultUtil.error(UserEum.user_10037.getCode(), UserEum.user_10037.getDesc());
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam insertCatCoinRecharge(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        try {
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUser_id(param.getUser_id());
            List<UserInfoEntity> userInfoEntities = userInfoMapper.query(userInfoEntity);
            if (userInfoEntities != null && userInfoEntities.size() > 0) {
                int i1 = mapper.insertSelective(param);
                if (i1 > 0) {
                    BigDecimal bignum1 = new BigDecimal("100");
                    userInfoEntity.setCat_coin(param.getAmount().multiply(bignum1));
                    int retupdateus = userInfoMapper.updateAddCatCoin(userInfoEntity);//????????????
                    if (retupdateus > 0) {
                        //????????????
                        UserOrderEntity orderEntity = new UserOrderEntity();
                        orderEntity.setPayment_id(userInfoEntity.getUser_id());
                        orderEntity.setType("recharge");
                        orderEntity.setSource("alipay");
                        orderEntity.setStatus(2);
                        orderEntity.setPay_count(param.getAmount().multiply(bignum1));
                        orderService.insert(orderEntity, request, response);
                        //??????
                        Long i = orderService.query(orderEntity, request, response).get(0).getUser_id();
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(i);
                        pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                        pushMessageParam.setPush_time(new Date());
                        pushMessageParam.setType("pay");
                        pushMessageParam.setOpera_type("pay");
                        pushMessageParam.setContent("????????????");
                        pushMessageParam.setTitle("????????????");
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        return ResultUtil.success();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public Map<String, Object> queryRecommendListCount(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {

            Integer dayCount = mapper.queryDayCount(param);
            map.put("dayCount", dayCount);

            Integer weekCount = mapper.queryWeekCount(param);
            map.put("weekCount", weekCount);

            Integer monthCount = mapper.queryMonthCount(param);
            map.put("monthCount", monthCount);

            Integer countTotal = mapper.queryListPageCount(param);
            map.put("countTotal", countTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryMyEarn(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            BigDecimal todayEarnTotal = new BigDecimal("0");
            BigDecimal earnTotal = new BigDecimal("0");
            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(param.getUser_id());
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();

            /**
             * ????????????
             */
            //user
            BigDecimal selfEarnToday;
            BigDecimal selfEarnTotal;

            userRecommenderIncomeLogEntity.setMaster_id(param.getUser_id());
            selfEarnTotal = userRecommenderIncomeLogMapper.querySelfCashTotal(userRecommenderIncomeLogEntity);
            selfEarnToday = userRecommenderIncomeLogMapper.querySelfCashToday(userRecommenderIncomeLogEntity);
            userRecommenderIncomeLogEntity.setUser_id(param.getUser_id());
            userRecommenderIncomeLogEntity.setMaster_id(null);
            selfEarnTotal = selfEarnTotal.add(userRecommenderIncomeLogMapper.querySelfCashTotal(userRecommenderIncomeLogEntity));
            selfEarnToday = selfEarnToday.add(userRecommenderIncomeLogMapper.querySelfCashToday(userRecommenderIncomeLogEntity));

            map.put("selfEarnTotal", selfEarnTotal);
            earnTotal = earnTotal.add(selfEarnTotal);
            map.put("selfEarnToday", selfEarnToday);
            todayEarnTotal = todayEarnTotal.add(selfEarnToday);

            /**
             * ????????????????????????????????????
             */
            if (userInfoEntity.getMechanism_recommender_id() != 0) {
                userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();

                userRecommenderIncomeLogEntity.setRecommender_id(userInfoEntity.getMechanism_recommender_id());
                userRecommenderIncomeLogEntity.setIs_settlement(true);
                BigDecimal mechanismRecommenderTotalEarn = userRecommenderIncomeLogMapper.querySelfCashTotal(userRecommenderIncomeLogEntity);
                BigDecimal mechanismRecommenderTodayEarn = userRecommenderIncomeLogMapper.querySelfCashToday(userRecommenderIncomeLogEntity);

                map.put("mechanismRecommenderTodayEarn", mechanismRecommenderTodayEarn);
                map.put("mechanismRecommenderEarn", mechanismRecommenderTotalEarn);
                todayEarnTotal = todayEarnTotal.add(mechanismRecommenderTodayEarn);
                earnTotal = earnTotal.add(mechanismRecommenderTotalEarn);
            } else {
                map.put("mechanismRecommenderEarn", 0);
                map.put("mechanismRecommenderTodayEarn", 0);
            }

            /**
             * ?????????????????????
             */
            if (userInfoEntity.getMaster_recommender_id() != 0) {
                UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                userRecommenderIncomeLogEntity1.setRecommender_id(userInfoEntity.getMaster_recommender_id());
                userRecommenderIncomeLogEntity1.setIs_settlement(true);
                BigDecimal masterRecommenderEarn = userRecommenderIncomeLogMapper.querySelfCashTotal(userRecommenderIncomeLogEntity1);
                BigDecimal masterRecommenderTodayEarn = userRecommenderIncomeLogMapper.querySelfCashToday(userRecommenderIncomeLogEntity1);

                map.put("masterRecommenderEarn", masterRecommenderEarn);
                map.put("masterRecommenderTodayEarn", masterRecommenderTodayEarn);
                todayEarnTotal = todayEarnTotal.add(masterRecommenderTodayEarn);
                earnTotal = earnTotal.add(masterRecommenderEarn);
            } else {
                map.put("masterRecommenderEarn", 0);
                map.put("masterRecommenderTodayEarn", 0);
            }

            /**
             * ??????id??????
             */
            if (userInfoEntity.getMechanism_id() != 0) {
                UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                userRecommenderIncomeLogEntity1.setMechanism_id(userInfoEntity.getMechanism_id());
                userRecommenderIncomeLogEntity1.setRecommender_id(0L);
                userRecommenderIncomeLogEntity1.setIs_settlement(true);
                BigDecimal mechanismEarnTotal = userRecommenderIncomeLogMapper.querySelfCashTotal(userRecommenderIncomeLogEntity1);
                BigDecimal mechanismEarnToday = userRecommenderIncomeLogMapper.querySelfCashToday(userRecommenderIncomeLogEntity1);

                //map.put("mechanismEarnMonthList", mechanismEarnMonthList);
                map.put("mechanismEarnToday", mechanismEarnToday);
                map.put("mechanismEarnTotal", mechanismEarnTotal);
                todayEarnTotal = todayEarnTotal.add(mechanismEarnToday);
                earnTotal = earnTotal.add(mechanismEarnTotal);
            } else {
                map.put("mechanismEarnToday", 0);
                map.put("mechanismEarnTotal", 0);
            }
            map.put("todayEarnTotal", todayEarnTotal);
            map.put("earnTotal", earnTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private MasterAppointmentEntity setMasterAppInfo(MasterAppointmentEntity masterAppointmentEntity1) {
        Calendar calendar = Calendar.getInstance();
        masterAppointmentEntity1.setEnd_time(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);//?????????????????????
        masterAppointmentEntity1.setStart_time(calendar.getTime());
        masterAppointmentEntity1.setOffset(new BigDecimal("8"));
        return masterAppointmentEntity1;
    }

    @Override
    public List<RechargeRecordEntity> querySaleCourse(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        List<RechargeRecordEntity> list = Lists.newArrayList();
        try {
            list = mapper.querySaleCourse(param);
            for (RechargeRecordEntity rechargeRecordEntity : list) {
                HashMap<String, Object> map = new HashMap<>();
                MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                if (rechargeRecordEntity.getStudycard_id() != 0) {
                    masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id());
                }
                map.put("masterSetPriceEntity", masterSetPriceEntity);

                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                if (rechargeRecordEntity.getAppointment_id() != 0) {
                    userAppointmentEntity = userAppointmentMapper.selectByPrimaryKey(rechargeRecordEntity.getAppointment_id());
                }
                map.put("userAppointmentEntity", userAppointmentEntity);

                rechargeRecordEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryMechanismCash(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            BigDecimal dayCash = mapper.queryDayCash(param);
            map.put("dayCash", dayCash);

            BigDecimal monthCash = new BigDecimal(mapper.queryMonthCash(param).toString());
            map.put("monthCash", monthCash);

            BigDecimal totalCash = new BigDecimal(mapper.queryTotalCash(param).toString());
            map.put("totalCash", totalCash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCardPayList(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            List<RechargeRecordEntity> list = mapper.queryCardList(param);
            Integer total = mapper.queryCardListCount(param);
            if (total > 0) {
                setUserInfo(list);
            }
            map1.put("rows", list);
            map1.put("total", total);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map1;
    }

    private void setUserInfo(List<RechargeRecordEntity> list) {
        for (RechargeRecordEntity rechargeRecordEntity : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(rechargeRecordEntity.getUser_id() + "userinfo")));
            rechargeRecordEntity.setMap(map);
        }
    }

    @Override
    public Map<String, Object> queryCardPayCount(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer dayCount = mapper.queryCardDayCount(param);
            map.put("dayCount", dayCount);

            Integer weekCount = mapper.queryCardWeekCount(param);
            map.put("weekCount", weekCount);

            Integer monthCount = mapper.queryCardMonthCount(param);
            map.put("monthCount", monthCount);

            Integer countTotal = mapper.queryCardListCount(param);
            map.put("countTotal", countTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCardPayListByNickName(RechargeRecordEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (param.getNick_name() != null && !"".equals(param.getNick_name())) {
                List<Long> list1 = userInfoMapper.queryIdByNickName(param.getNick_name());
                if (list1 != null && list1.size() > 0) {
                    param.setNickNameIDs(list1);
                }
            }
            List<RechargeRecordEntity> list = mapper.queryCardPayListByNickName(param);
            Integer total = mapper.queryCardPayListByNickNameCount(param);
            if (total > 0) {
                setUserInfo(list);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam queryByMessage(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (StringUtil.isNotEmpty(param.getNick_name())) {
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setNick_name(param.getNick_name());
                userInfoEntity.setPageSize(1);
                List<UserInfoEntity> list = userInfoMapper.queryListPage(userInfoEntity);
                if (list != null && list.size() > 0) {
                    param.setUser_id(list.get(0).getUser_id());
                } else {
                    return ResultUtil.error(UserEum.user_10061.getCode(), UserEum.user_10061.getDesc());
                }
            }

            if (StringUtil.isNotEmpty(param.getLogin_name())) {
                UserEntity userEntity = new UserEntity();
                userEntity.setStatus(2);
                userEntity.setPageSize(1);
                userEntity.setLogin_name(param.getLogin_name());
                List<UserEntity> list1 = userMapper.queryListPage(userEntity);
                if (list1 != null && list1.size() > 0) {
                    param.setUser_id(list1.get(0).getUser_id());
                }
            }

            if (StringUtil.isNotEmpty(param.getMechanism_name())) {
                MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
                mechanismEntity.setMechanism_name(param.getMechanism_name());
                mechanismEntity.setPageSize(1);
                mechanismEntity.setType("teach_paypal");
                List<MasterMechanismEntity> list = masterMechanismMapper.queryListPage(mechanismEntity);
                if (list != null && list.size() > 0) {
                    param.setMechanism_id(list.get(0).getId());
                } else {
                    return ResultUtil.error(UserEum.user_10059.getCode(), UserEum.user_10059.getDesc());
                }
            }

            if (StringUtil.isNotEmpty(param.getCommodities_name())) {
                MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                masterSetPriceEntity.setTitle(param.getCommodities_name());
                masterSetPriceEntity.setPageSize(1);
                List<MasterSetPriceEntity> list = masterSetPriceMapper.queryListPage(masterSetPriceEntity);
                if (list != null && list.size() > 0) {
                    param.setStudycard_id(list.get(0).getId());
                } else {
                    return ResultUtil.error(UserEum.user_10060.getCode(), UserEum.user_10060.getDesc());
                }
            }

            List<RechargeRecordEntity> list = mapper.queryListPage(param);
            int total = mapper.queryListPageCount(param);
            if (total > 0) {
                this.setListInfo(list);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public ResultParam insertExperienceCard(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {

            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setUser_id(param.getUser_id());
            rechargeRecordEntity.setRcharge_type(param.getRcharge_type());
            rechargeRecordEntity.setPageSize(2);
            rechargeRecordEntity.setIs_experience(true);
            List<RechargeRecordEntity> list = mapper.queryIfByCard(rechargeRecordEntity);

            if ("study_card".equals(param.getRcharge_type())) {
                List<RechargeRecordEntity> list1 = mapper.queryListPage(rechargeRecordEntity);
                if ((list1.size() >= 2 && list.size() == 1) || (list1.size() >= 1 && list.size() == 0) || list.size() > 1) {
                    return ResultUtil.error(UserEum.user_10045.getCode(), UserEum.user_10045.getDesc());
                }
            } else {
                if (list != null && list.size() > 0) {
                    return ResultUtil.error(UserEum.user_10045.getCode(), UserEum.user_10045.getDesc());
                }
            }

            mapper.insertSelective(param);

            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            if (param.getMechanism_id() != null && param.getMechanism_id() != 0) {
                userStudyCardEntity.setMechanism_id(param.getMechanism_id());
            }

            if (param.getMaster_id() != null && param.getMaster_id() != 0) {
                userStudyCardEntity.setMaster_id(param.getMaster_id());
            }

            userStudyCardEntity.setUser_id(param.getUser_id());
            userStudyCardEntity.setIs_experience(true);
            userStudyCardEntity.setPageSize(1);
            //????????????
            userStudyCardEntity.setType(param.getStudy_type());

            //??????
            userStudyCardEntity.setStatus(2);
            userStudyCardEntity.setStudycard_id(param.getStudycard_id());
            userStudyCardEntity.setCourse_num(8);
            userStudyCardEntity.setIs_experience(true);
            //????????????+ 2???
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.WEEK_OF_YEAR, 2);
            Date dt1 = rightNow.getTime();
            userStudyCardEntity.setEnd_time(dt1);
            param.setFlowing_no(CommonUtils.generateFlowingCode());
            param.setIs_experience(true);
            int i1 = mapper.insertSelective(param);
            userStudyCardEntity.setIs_teach_paypal(true);
            int i = userStudyCardMapper.insertSelective(userStudyCardEntity);
            if (i > 0 && i1 > 0) {
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public ResultParam insertExperienceLiveCard(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setUser_id(param.getUser_id());
            rechargeRecordEntity.setRcharge_type(param.getRcharge_type());
            rechargeRecordEntity.setPageSize(1);
            rechargeRecordEntity.setIs_experience(true);
            List<RechargeRecordEntity> list = mapper.queryIfByCard(rechargeRecordEntity);
            if (list != null && list.size() > 0) {
                return ResultUtil.error(UserEum.user_10045.getCode(), UserEum.user_10045.getDesc());
            }

            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
            userClassCardEntity.setType(param.getStudy_type());
            userClassCardEntity.setUser_id(param.getUser_id());
            userClassCardEntity.setCurriculum_num(8);
            userClassCardEntity.setIs_experience(true);
            //????????????+ 2???
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.WEEK_OF_YEAR, 2);
            dt = rightNow.getTime();
            userClassCardEntity.setExpire_time(dt);

            param.setFlowing_no(CommonUtils.generateFlowingCode());
            param.setIs_experience(true);
            int i1 = mapper.insertSelective(param);
            int i = userClassCardMapper.insertSelective(userClassCardEntity);
            if (i > 0 && i1 > 0) {
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public Map<String, Object> queryMyRecordListCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<RechargeRecordEntity> list = this.queryMyRecordList(param, request, response);
            Integer total = mapper.queryOrderListCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<RechargeRecordEntity> queryListPage(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest
            request, HttpServletResponse response) {
        List<RechargeRecordEntity> list = super.queryListPage(rechargeRecordEntity, request, response);
        if (list != null && list.size() > 0) {
            for (RechargeRecordEntity recordEntity : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(recordEntity.getUser_id() + "userinfo")));
                if (rechargeRecordEntity.getMechanism_id() != null && rechargeRecordEntity.getMechanism_id() != 0) {
                    map.put("mechanismInfo", masterMechanismMapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id()));
                    if (rechargeRecordEntity.getAppointment_id() != 0 && rechargeRecordEntity.getAppointment_id() != null) {
                        MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(recordEntity.getAppointment_id());
                        map.put("masterInfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getMaster_id() + "userinfo")));
                    }
                }
                if ("study_card".equals(recordEntity.getStudy_type())) {
                    map.put("studyCardEntity", userStudyCardMapper.selectByPrimaryKey(recordEntity.getStudycard_id()));
                } else {
                    map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(recordEntity.getStudycard_id()));
                }
                recordEntity.setMap(map);
            }
        }
        return list;
    }

    public Integer getFirstPayCount(RechargeRecordEntity param) {
        Integer i = 0;
        List<RechargeRecordEntity> list = mapper.queryDayList(param);
        if (list != null && list.size() > 0) {
            for (RechargeRecordEntity rechargeRecordEntity : list) {
                RechargeRecordEntity rechargeRecordEntity1 = new RechargeRecordEntity();
                rechargeRecordEntity1.setUser_id(rechargeRecordEntity.getUser_id());
                if (mapper.queryListPageCount(rechargeRecordEntity1) == 0) {
                    i++;
                }
            }
        }
        return i;
    }

    @Override
    @Transactional
    public synchronized ResultParam payOneCourse(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        try {
            /*
            if (entity.getStudycard_id() != null ) {
                UserStudyCardEntity userStudyCardEntity2 = new UserStudyCardEntity();
                userStudyCardEntity2.setUser_id(entity.getUser_id());
                userStudyCardEntity2.setStudycard_id(entity.getStudycard_id());
                userStudyCardEntity2.setPageSize(1);
                List<UserStudyCardEntity> list = userStudyCardMapper.queryListPage(userStudyCardEntity2);
                if (list != null && list.size() > 0 ) {
                    return ResultUtil.error(UserEum.user_10052.getCode(), UserEum.user_10052.getDesc());
                }
            }

             */

            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entity.getStudycard_id());

            entity.setAmount(new BigDecimal("0"));
            entity.setCourse_num(masterSetPriceEntity.getCourse_num());
            entity.setStudy_type(masterSetPriceEntity.getType());
            entity.setRcharge_type("study_card");
            entity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            entity.setMaster_id(masterSetPriceEntity.getUser_id());
            entity.setIs_one_time_payment(true);
            entity.setFlowing_no(CommonUtils.generateFlowingCode());
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            if (entity.getMechanism_id() != 0) {
                userStudyCardEntity.setMechanism_id(entity.getMechanism_id());
            } else {
                userStudyCardEntity.setMaster_id(entity.getMaster_id());
            }
            userStudyCardEntity.setUser_id(entity.getUser_id());
            userStudyCardEntity.setType(entity.getStudy_type());
            userStudyCardEntity.setStudycard_id(entity.getStudycard_id());
            userStudyCardEntity.setCourse_num(entity.getCourse_num());
            userStudyCardEntity.setIs_one_time_payment(false);
            userStudyCardEntity.setMaster_set_price_group_id(masterSetPriceEntity.getMaster_set_price_group_id());
            if (masterSetPriceEntity.getOriginal_price().compareTo(new BigDecimal("0")) == 0) {
                return ResultUtil.error(OrderEum.order_70022.getCode(), OrderEum.order_70022.getDesc());
            }
            userStudyCardEntity.setEach_lesson_price(masterSetPriceEntity.getOriginal_price());

            if (StringUtil.isNotEmpty(entity.getUser_group_id())) {
                userStudyCardEntity.setUser_group_id(entity.getUser_group_id());
            }

            if (entity.getBinding_mechanism_id() != null) {
                userStudyCardEntity.setBinding_mechanism_id(entity.getBinding_mechanism_id());
            }
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.MONTH, masterSetPriceEntity.getDuration());
            Date dt1 = rightNow.getTime();
            userStudyCardEntity.setEnd_time(dt1);
            userStudyCardEntity.setOriginal_course_num(masterSetPriceEntity.getCourse_num());

            if ("fixed_scheduling".equalsIgnoreCase(masterSetPriceEntity.getAppointment_type())) {
                userStudyCardEntity.setMechanism_class_id(insertMechanismClass(masterSetPriceEntity));
            }
            userStudyCardEntity.setIs_teach_paypal(true);
            userStudyCardEntity.setIs_pay_deposit(true);
            int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
            entity.setUser_study_card_id(userStudyCardEntity.getId());
            int i = mapper.insertSelective(entity);
            if (rets > 0) {
                payService.updatePayNum(entity);
                if (masterSetPriceEntity.getMaster_set_price_group_id() != 0) {
                    messageApiService.insertEnjoyGroup(entity.getUser_id(), masterSetPriceEntity.getMaster_set_price_group_id());
                }
                UserStudyCardEntity userStudyCardEntity1 = new UserStudyCardEntity();
                userStudyCardEntity1.setId(userStudyCardEntity.getId());

                /*
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setType_id(userStudyCardEntity.getId());
                pushMessageParam.setTarget_id(masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id()).getUser_id());
                pushMessageParam.setPush_type("buy_course");
                pushMessageParam.setOpera_type("buy_course");
                pushMessageParam.setContent(userInfoMapper.selectByPrimaryKey(userStudyCardEntity.getUser_id()).getNick_name() + "?????????????????????");
                pushMessageParam.setType("buy_course");
                pushMessageParam.setTitle("??????????????????");
                pushMessageParam.setView_type("mechanism");
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);

                 */
                return ResultUtil.success(userStudyCardService.queryExclusiveCoursesList(userStudyCardEntity1, request, response));
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(OrderEum.order_70016.getCode(), OrderEum.order_70016.getDesc());
    }

    @Override
    @Transactional
    public synchronized ResultParam payOneCourseLiveStream(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        try {

            MasterSetPriceDisplayEntity masterSetPriceDisplayEntity = new MasterSetPriceDisplayEntity();
            masterSetPriceDisplayEntity.setMaster_set_price_id(entity.getStudycard_id());
            masterSetPriceDisplayEntity.setLive_streaming_id(entity.getLive_streaming_id());
            masterSetPriceDisplayEntity.setPageSize(1);
            masterSetPriceDisplayEntity = masterSetPriceDisplayMapper.queryListPage(masterSetPriceDisplayEntity).get(0);
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(masterSetPriceDisplayEntity.getMaster_set_price_id());

            entity.setAmount(new BigDecimal("0"));
            entity.setCourse_num(masterSetPriceEntity.getCourse_num());
            entity.setStudy_type(masterSetPriceEntity.getType());
            entity.setRcharge_type("study_card");
            entity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            entity.setMaster_id(masterSetPriceEntity.getUser_id());
            entity.setIs_one_time_payment(true);
            entity.setFlowing_no(CommonUtils.generateFlowingCode());
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            if (entity.getMechanism_id() != 0) {
                userStudyCardEntity.setMechanism_id(entity.getMechanism_id());
            } else {
                userStudyCardEntity.setMaster_id(entity.getMaster_id());
            }
            userStudyCardEntity.setUser_id(entity.getUser_id());
            userStudyCardEntity.setType(entity.getStudy_type());
            userStudyCardEntity.setStudycard_id(entity.getStudycard_id());
            userStudyCardEntity.setCourse_num(entity.getCourse_num());
            userStudyCardEntity.setIs_one_time_payment(false);
            userStudyCardEntity.setMaster_set_price_group_id(masterSetPriceEntity.getMaster_set_price_group_id());
            userStudyCardEntity.setEach_lesson_price(masterSetPriceDisplayEntity.getLiving_single_session_price());

            if (StringUtil.isNotEmpty(entity.getUser_group_id())) {
                userStudyCardEntity.setUser_group_id(entity.getUser_group_id());
            }

            if (entity.getBinding_mechanism_id() != null) {
                userStudyCardEntity.setBinding_mechanism_id(entity.getBinding_mechanism_id());
            }
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.MONTH, masterSetPriceEntity.getDuration());
            Date dt1 = rightNow.getTime();
            userStudyCardEntity.setEnd_time(dt1);
            userStudyCardEntity.setOriginal_course_num(masterSetPriceEntity.getCourse_num());

            if ("fixed_scheduling".equalsIgnoreCase(masterSetPriceEntity.getAppointment_type())) {
                userStudyCardEntity.setMechanism_class_id(insertMechanismClass(masterSetPriceEntity));
            }
            userStudyCardEntity.setIs_teach_paypal(true);
            int rets = userStudyCardMapper.insertSelective(userStudyCardEntity);
            entity.setUser_study_card_id(userStudyCardEntity.getId());
            int i = mapper.insertSelective(entity);
            if (rets > 0) {
                payService.updatePayNum(entity);
                if (masterSetPriceEntity.getMaster_set_price_group_id() != 0) {
                    messageApiService.insertEnjoyGroup(entity.getUser_id(), masterSetPriceEntity.getMaster_set_price_group_id());
                }
                UserStudyCardEntity userStudyCardEntity1 = new UserStudyCardEntity();
                userStudyCardEntity1.setId(userStudyCardEntity.getId());

                /*
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setType_id(userStudyCardEntity.getId());
                pushMessageParam.setTarget_id(masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id()).getUser_id());
                pushMessageParam.setPush_type("buy_course");
                pushMessageParam.setOpera_type("buy_course");
                pushMessageParam.setContent(userInfoMapper.selectByPrimaryKey(userStudyCardEntity.getUser_id()).getNick_name() + "?????????????????????");
                pushMessageParam.setType("buy_course");
                pushMessageParam.setTitle("??????????????????");
                pushMessageParam.setView_type("mechanism");
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);

                 */
                return ResultUtil.success(userStudyCardService.queryExclusiveCoursesList(userStudyCardEntity1, request, response));
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(OrderEum.order_70016.getCode(), OrderEum.order_70016.getDesc());
    }

    @Override
    public ResultParam queryUpdateCourseDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entity.getUser_study_card_id());
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entity.getStudycard_id());
            if (!userStudyCardEntity.getUser_id().equals(entity.getUser_id())) {
                return ResultUtil.error(UserEum.user_10064.getCode(), UserEum.user_10064.getDesc());
            }
            BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
            businessActivityEntity.setMaster_set_price_id(entity.getStudycard_id());
            businessActivityEntity.setPageSize(1);
            businessActivityEntity.setStatus(2);
            List<BusinessActivityEntity> businessActivityEntities = businessActivityMapper.queryListPage(businessActivityEntity);
            if (businessActivityEntities == null || businessActivityEntities.size() == 0) {
                return ResultUtil.error(OrderEum.order_70035.getCode(), OrderEum.order_70035.getDesc());
            }
            BigDecimal finalAmount = userStudyCardEntity.getEach_lesson_price()
                    .subtract(masterSetPriceEntity.getOriginal_price())
                    .multiply(new BigDecimal("3"));
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("need_pay", finalAmount.compareTo(new BigDecimal("0")) > 0);
            map.put("amount", finalAmount.abs());
            return ResultUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public Map<String, Object> queryDayCash(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            BigDecimal bigDecimal = mapper.queryDayCash(param);
            map.put("dayCash", bigDecimal);

            Integer integer = mapper.queryDayCount(param);
            map.put("dayCount", integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam queryPayUserList(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        List<PayUserInfoEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryPayUserList(entity);
            if (list != null && list.size() > 0) {
                for (PayUserInfoEntity payUserInfoEntity : list) {
                    payUserInfoEntity.setPhone(payUserInfoEntity.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
                    RechargeRecordEntity rechargeRecordEntity = mapper.selectByPrimaryKey(payUserInfoEntity.getId());
                    if (StringUtil.isNotEmpty(rechargeRecordEntity.getInvite_code())){
                        UserInfoEntity userInfoEntity = userInfoMapper.queryByInviteCode(rechargeRecordEntity.getInvite_code());
                        userInfoEntity.setNick_name(EmojiParser.parseToAliases(userInfoEntity.getNick_name()));
                        userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
                        payUserInfoEntity.setInviteName(userInfoEntity.getNick_name());
                        UserRecommenderEntity userRecommenderEntity = new UserRecommenderEntity();
                        userRecommenderEntity.setUser_id(userInfoEntity.getUser_id());
                        userRecommenderEntity.setType("share_recommender");
                        userRecommenderEntity.setPageSize(1);
                        List<UserRecommenderEntity> list1 = userRecommenderMapper.queryListPage(userRecommenderEntity);
                        if (list1!=null && list1.size()>0){
                            userRecommenderEntity = list1.get(0);
                            if (userRecommenderEntity.getGroup_id()!=0) {
                                UserRecommenderGroupEntity userRecommenderGroupEntity = userRecommenderGroupMapper.selectByPrimaryKey(userRecommenderEntity.getGroup_id());
                                payUserInfoEntity.setInviteGroupName(userRecommenderGroupEntity.getGroup_name());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

    @Override
    public int updateRchStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        int i = 0;
        try {
            i = mapper.updateRchStatus(rechargeRecordEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public ResultParam queryActivityDetail(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            entity.setFinished(true);
            entity.setIs_robot(false);
            Integer payCount = mapper.queryListPageCount(entity);
            map.put("payCount",payCount);

            map.put("onlineCount",entity.getOnLineCount());

            UserRecommenderGroupEntity userRecommenderGroupEntity = new UserRecommenderGroupEntity();
            userRecommenderGroupEntity.setActivity_id(entity.getActivity_id());
            userRecommenderGroupEntity.setStatus(2);
            List<UserRecommenderGroupEntity> userRecommenderGroupEntityList = userRecommenderGroupMapper.query(userRecommenderGroupEntity);
            map.put("userRecommenderGroupEntityList", userRecommenderGroupEntityList);

            UserRecommenderEntity userRecommenderEntity = new UserRecommenderEntity();
            userRecommenderEntity.setStatus(2);
            List<UserRecommenderEntity> userRecommenderEntityList = userRecommenderMapper.query(userRecommenderEntity);
            map.put("userRecommenderEntityList", userRecommenderEntityList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public Map<String, Object> queryBuyCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            Long click_count = businessActivityTypeMapper.selectByPrimaryKey(param.getActivity_id().intValue()).getClick_count();
            map.put("visitCount", click_count);
            param.setStatus(2);
            param.setFinished(true);
            Integer buyCount = mapper.queryListPageCount(param);
            map.put("buyCount", buyCount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam exportExcel(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            /*
            List<Map<String, Object>> data = ExcelFormatUtil.getData();
            String sheetName = "?????????";
            String[] headers = {"ID","??????","??????","??????"};
            String exportExcelName = "student";
            ExcelFormatUtil.exportExcel(sheetName, data, headers, exportExcelName);

             */
            ExcelFormatUtil.export(response);

        }catch (Exception e){
            e.printStackTrace();
        }
//        return ResultUtil.success();
        return  null;
    }


    private Long insertMechanismClass(MasterSetPriceEntity masterSetPriceEntity) throws Exception {
        MechanismClassesEntity mechanismClassesEntity = new MechanismClassesEntity();
        mechanismClassesEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
        mechanismClassesEntity.setPageSize(1);
        List<MechanismClassesEntity> list = mechanismClassesMapper.queryListPageAsc(mechanismClassesEntity);
        if (list != null && list.size() > 0) {
            mechanismClassesEntity = list.get(0);
            if (mechanismClassesEntity.getStudent_count() < mechanismClassesEntity.getStudent_count_max()) {
                int i = mechanismClassesMapper.updateStudentCount(mechanismClassesEntity);
                if (i > 0) {
                    return mechanismClassesEntity.getId();
                } else {
                    throw new Exception("????????????????????????");
                }
            }
        }
        MechanismClassesEntity mechanismClassesEntity1 = new MechanismClassesEntity();
        mechanismClassesEntity1.setStudent_count_max(masterSetPriceEntity.getConnect_peoplenum());
        mechanismClassesEntity1.setMechanism_id(masterSetPriceEntity.getMechanism_id());
        mechanismClassesEntity1.setMaster_set_price_id(masterSetPriceEntity.getId());
        mechanismClassesEntity1.setName(masterSetPriceEntity.getTitle() + (mechanismClassesMapper.queryListPageCount(mechanismClassesEntity1) + 1));
        int i = mechanismClassesMapper.insertSelective(mechanismClassesEntity1);
        if (i > 0) {
            return mechanismClassesEntity1.getId();
        } else {
            throw new Exception("????????????????????????");
        }
    }

    @Override
    public List<RechargeRecordEntity> queryMechanismList(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        List<RechargeRecordEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryMoreThan1(entity);
            if (list != null && list.size() > 0) {
                if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                    this.setMechanismListInfo(list);
                } else {
                    this.setUserListInfo(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudyCardStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(rechargeRecordEntity.getUser_study_card_id());
        userStudyCardEntity.setIs_one_time_payment(false);
        userStudyCardEntity.setSettlement_all(true);
        int i = userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity);
        if (i > 0) {
            PushMessageParam pushMessageParam = new PushMessageParam();
            if ("user".equals(rechargeRecordEntity.getPaying_for_identity())) {
                pushMessageParam.setTarget_id(rechargeRecordEntity.getUser_id());
            } else if ("mechanism".equals(rechargeRecordEntity.getPaying_for_identity())) {
                MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id());
                pushMessageParam.setTarget_id(mechanismEntity.getUser_id());
            }
            pushMessageParam.setType_id(rechargeRecordEntity.getId());
            pushMessageParam.setPush_type("specialty_sheets");
            pushMessageParam.setOpera_type("specialty_sheets");
            pushMessageParam.setContent("?????????????????????");
            pushMessageParam.setType("specialty_sheets");
            pushMessageParam.setTitle("?????????????????????");
            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
        }

    }

    @Override
    public Map<String, Object> queryPayDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entity.getStudycard_id());
            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getUser_id());
            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entity.getUser_study_card_id());
            BigDecimal price;

            if (!entity.getIs_one_time_payment()) {
                price = masterSetPriceEntity.getDiscount_amout();
                if (price.compareTo(new BigDecimal("0")) == 0) {
                    price = masterSetPriceEntity.getDefault_discount_price();
                }
                map.put("points", 0);
            } else {
                price = userStudyCardEntity.getEach_lesson_price();
                map.put("points", userInfoEntity.getPoints());
            }
            map.put("originalPrice", price);

            UserCouponEntity userCouponEntity = new UserCouponEntity();
            userCouponEntity.setUser_id(entity.getUser_id());
            if (entity.getIs_one_time_payment() != null && entity.getIs_one_time_payment()) {
                BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
                businessActivityEntity.setType("experience_specials");
                businessActivityEntity.setStatus(2);
                businessActivityEntity.setPageSize(1);
                List<BusinessActivityEntity> businessActivityEntities = businessActivityMapper.queryListPage(businessActivityEntity);
                if (businessActivityEntities != null && businessActivityEntities.size() > 0) {
                    userCouponEntity.setStatus(1);
                    userCouponEntity.setMechanism_id(0L);
                    List<UserCouponEntity> userCouponEntities = userCouponMapper.queryDetailListPage(userCouponEntity);
                    map.put("userCouponEntities", userCouponEntities);
                } else {
                    map.put("userCouponEntities", null);
                }
            } else {
                map.put("userCouponEntities", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryPayLiveStreamDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (entity.getLive_streaming_id() == null || entity.getLive_streaming_id() == 0) {
                MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entity.getStudycard_id());
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getUser_id());
//                UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(entity.getUser_study_card_id());
                BigDecimal price;

                if (!entity.getIs_one_time_payment()) {
                    price = masterSetPriceEntity.getDiscount_amout();
                    if (price.compareTo(new BigDecimal("0")) == 0) {
                        price = masterSetPriceEntity.getDefault_discount_price();
                    }
                    map.put("points", userInfoEntity.getPoints());
                } else {
                    price = masterSetPriceEntity.getOriginal_price();
                    price = price.multiply(new BigDecimal("3"));
                    map.put("points", 0);
                }
                map.put("originalPrice", price);

                UserCouponEntity userCouponEntity = new UserCouponEntity();
                if (entity.getIs_one_time_payment() != null && !entity.getIs_one_time_payment()) {
                    BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                    businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
                    businessActivityEntity.setType("deductions_coupon");
                    businessActivityEntity.setStatus(2);
                    businessActivityEntity.setPageSize(1);
                    List<BusinessActivityEntity> businessActivityEntities = businessActivityMapper.queryListPage(businessActivityEntity);
                    List<UserCouponEntity> userCouponEntities = new ArrayList<>();
                    if (businessActivityEntities != null && businessActivityEntities.size() > 0) {
                        userCouponEntity.setStatus(1);
                        userCouponEntity.setUser_id(entity.getUser_id());
                        userCouponEntity.setMechanism_id(0L);
                        userCouponEntity.setType("deductions_coupon");
                        userCouponEntity.setPageSize(businessActivityEntities.get(0).getCoupon_time());
                        userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
                    }
                    if (masterSetPriceEntity.getIs_attend_activities()) {
                        userCouponEntity.setStatus(1);
                        userCouponEntity.setUser_id(entity.getUser_id());
                        userCouponEntity.setPageSize(1);
                        userCouponEntity.setType("voucher_redemption");
                        userCouponEntities.addAll(userCouponMapper.queryListPage(userCouponEntity));
                    }
                    map.put("userCouponEntities", userCouponEntities);
                } else {
                    map.put("userCouponEntities", null);
                }
            } else {
                MasterSetPriceDisplayEntity masterSetPriceDisplayEntity = new MasterSetPriceDisplayEntity();
                masterSetPriceDisplayEntity.setMaster_set_price_id(entity.getStudycard_id());
                masterSetPriceDisplayEntity.setLive_streaming_id(entity.getLive_streaming_id());
                masterSetPriceDisplayEntity.setPageSize(1);
                masterSetPriceDisplayEntity = masterSetPriceDisplayMapper.queryListPage(masterSetPriceDisplayEntity).get(0);
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getUser_id());
                MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(masterSetPriceDisplayEntity.getMaster_set_price_id());

                BigDecimal price;

                if (entity.getIs_one_time_payment()) {
                    price = masterSetPriceDisplayEntity.getLiving_single_session_price();
                    price = price.multiply(new BigDecimal("3"));
                    if (price.compareTo(new BigDecimal("0")) == 0) {
                        price = masterSetPriceEntity.getDefault_discount_price();
                    }
                    map.put("points", 0);
                } else {
                    price = masterSetPriceDisplayEntity.getLive_stream_price();
                    map.put("points", userInfoEntity.getPoints());
                }
                map.put("originalPrice", price);

                UserCouponEntity userCouponEntity = new UserCouponEntity();
                if (entity.getIs_one_time_payment()) {
                    map.put("userCouponEntities", null);
                } else {
                    List<UserCouponEntity> userCouponEntities = Lists.newArrayList();
                    BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                    businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
                    businessActivityEntity.setType("deductions_coupon");
                    businessActivityEntity.setStatus(2);
                    businessActivityEntity.setPageSize(1);
                    List<BusinessActivityEntity> businessActivityEntities = businessActivityMapper.queryListPage(businessActivityEntity);
                    if (businessActivityEntities != null && businessActivityEntities.size() > 0) {
                        userCouponEntity.setStatus(1);
                        userCouponEntity.setMechanism_id(0L);
                        userCouponEntity.setUser_id(entity.getUser_id());
                        userCouponEntity.setType("deductions_coupon");
                        userCouponEntity.setPageSize(businessActivityEntities.get(0).getCoupon_time());
                        userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
                        map.put("userCouponEntities", userCouponEntities);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<RechargeRecordEntity> queryMechanismOfflineDetails(RechargeRecordEntity
                                                                           entity, HttpServletRequest request, HttpServletResponse response) {
        List<RechargeRecordEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryOrderList(entity);
            if (list != null && list.size() > 0) {
                for (RechargeRecordEntity rechargeRecordEntity : list) {
                    if (rechargeRecordEntity.getIs_free()) {
                        rechargeRecordEntity.setAmount(new BigDecimal("0"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryMechanismOfflineDetailsCount(RechargeRecordEntity
                                                                         entity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            entity.setStatus(2);
            entity.setStudy_type("mechanism_offline");
            Integer noSettlement = mapper.queryListPageCount(entity);
            map.put("noSettlement", noSettlement);

            entity.setStatus(3);
            Integer settled = mapper.queryListPageCount(entity);
            map.put("settled", settled);

            BigDecimal earnTotal = mapper.queryPayTotal(entity);
            map.put("earnTotal", earnTotal);

            BigDecimal earnDay = mapper.queryDayTotal(entity);
            map.put("earnDay", earnDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<RechargeRecordEntity> queryInfoDetail(RechargeRecordEntity entity, HttpServletRequest
            request, HttpServletResponse response) {
        List<RechargeRecordEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPage(entity);
            if (list != null && list.size() > 0) {
                this.setMechanismListInfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<RechargeRecordEntity> queryListPageByStatus(RechargeRecordEntity entity, HttpServletRequest
            request, HttpServletResponse response) {
        List<RechargeRecordEntity> list = mapper.queryListPageByStatus(entity);
        if (list != null && list.size() > 0) {
            for (RechargeRecordEntity rechargeRecordEntity : list) {
                HashMap<String, Object> map = Maps.newHashMap();
                if (rechargeRecordEntity.getMechanism_id() != 0) {
                    map.put("masterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id()));
                } else {
                    map.put("masterMechanismEntity", null);
                }
                if ("mechanism_offline".equalsIgnoreCase(entity.getStudy_type())) {
                    if (rechargeRecordEntity.getStudycard_id() != 0) {
                        map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id()));
                    } else {
                        map.put("masterSetPriceEntity", null);
                    }
                }
                map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                rechargeRecordEntity.setMap(map);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public ResultParam tradeRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse
            response) {
        try {
            ResultParam resultParam = ResultUtil.success();
            int need = 0;
            RechargeRecordEntity rechargeRecordEntity = mapper.selectByPrimaryKey(entity.getId());
            if (entity.getUser_id().equals(rechargeRecordEntity.getUser_id())) {
                if (entity.getStatus() == 2) {
                    if ("coupon_177".equals(entity.getType()) || "coupon_77".equals(entity.getType())) {
                        return ResultUtil.error(OrderEum.order_70038.getCode(), OrderEum.order_70038.getDesc());
                    }
                    UserCouponEntity userCouponEntity = new UserCouponEntity();
                    userCouponEntity.setStatus(1);
                    userCouponEntity.setUser_id(entity.getUser_id());
                    userCouponEntity.setType(entity.getType());
                    if ("coupon_177".equals(entity.getType())) {
                        need = 3;
                    }
                    List<UserCouponEntity> userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
                    if (userCouponEntities != null && userCouponEntities.size() > need) {
                        if (this.checkTimeOut(entity)) {
                            return ResultUtil.error(OrderEum.order_70032.getCode(), OrderEum.order_70032.getDesc());
                        }
                        if ("wx".equalsIgnoreCase(entity.getPay_type())) {
                            resultParam = payService.wxPayRefund(entity, request, response);
                        } else if ("ali".equalsIgnoreCase(entity.getPay_type())) {
                            resultParam = payService.aliPayTradeRefund(entity, request, response);
                        }
                        if (resultParam.getCode() == 0) {
                            entity.setStatus(8);
                            userCouponEntity = userCouponEntities.get(0);
                            userCouponEntity.setStatus(2);
                            for (UserCouponEntity couponEntity : userCouponEntities) {
                                int i1 = userCouponMapper.updateByPrimaryKeySelective(couponEntity);
                                if (i1 < 0) {
                                    throw new Exception("????????????????????????");
                                }
                            }
                            int i = mapper.updateByPrimaryKeySelective(entity);
                            if (i < 0) {
                                throw new Exception("????????????????????????");
                            }
                            return resultParam;
                        }
                    } else {
                        return ResultUtil.error(OrderEum.order_70030.getCode(), OrderEum.order_70030.getDesc());
                    }
                } else {
                    return ResultUtil.error(OrderEum.order_70031.getCode(), OrderEum.order_70031.getDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(OrderEum.order_70029.getCode(), OrderEum.order_70029.getDesc());
    }


    private boolean checkTimeOut(RechargeRecordEntity entity) {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        // ??????????????????
        cal.setTime(date);
        switch (entity.getRcharge_type()) {
            case "coupon_177":
            case "coupon_77":
                cal.add(Calendar.DATE, 7);
                break;
            case "deductions_coupon":
                cal.add(Calendar.DATE, 30);
                entity.setAmount(entity.getAmount().multiply(new BigDecimal("0.7")));
                break;
        }
        return cal.getTime().compareTo(entity.getFinished_time()) >= 0;
    }

}