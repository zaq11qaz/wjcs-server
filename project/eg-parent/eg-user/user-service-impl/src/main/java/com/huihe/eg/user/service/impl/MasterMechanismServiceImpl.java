package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.MasterMechanismService;
import com.huihe.eg.user.service.dao.UserRecommenderIncomeLogService;
import com.huihe.eg.user.service.dao.UserService;
import com.huihe.eg.user.service.impl.sms.SmsService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterMechanismServiceImpl extends BaseFrameworkServiceImpl<MasterMechanismEntity, Long> implements MasterMechanismService {

    @Resource
    private MasterMechanismMapper mapper;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private ClassRecordMapper classRecordMapper;
    @Resource
    private UserOrderMapper userOrderMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;
    @Resource
    private UserRecommenderIncomeLogMapper userRecommenderIncomeLogMapper;
    @Resource
    private SmsService smsService;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private BusinessActivityTypeMapper businessActivityTypeMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private MechanismClassroomMapper mechanismClassroomMapper;
    @Resource
    private BusinessActivityMapper businessActivityMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }


    @Override
    public synchronized ResultParam insert(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        this.replaceCategories(param);
        if (param.getUser_id() != null && param.getUser_id() != 0) {
            MasterMechanismEntity entity = new MasterMechanismEntity();
            entity.setUser_id(param.getUser_id());
            entity.setPageSize(1);
            List<MasterMechanismEntity> list = mapper.queryMechanismList(entity, request, response);
            if (list != null && list.size() > 0) {
                param.setId(list.get(0).getId());
                param.setStatus(1);
                param.setRefuse_contect("");
                return super.update(param, request, response);
            }
            String mechanism_telephone = param.getMechanism_telephone();
            param.setMechanism_no("JG" + mechanism_telephone);
        }
        return super.insert(param, request, response);
    }

    @Override
    public ResultParam update(MasterMechanismEntity masterMechanismEntity, HttpServletRequest request, HttpServletResponse response) {
        this.replaceCategories(masterMechanismEntity);
        return super.update(masterMechanismEntity, request, response);
    }

    @Override
    public Map<String, Object> queryByMessage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterMechanismEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryMechanismList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterMechanismEntity> list = mapper.query2And5List(param);
            Integer total = mapper.query2And5ListCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryAppointmentMechanismList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterMechanismEntity> list = mapper.queryAppointmentMechanismList(param);
            Integer total = mapper.queryAppointmentMechanismListCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public synchronized ResultParam insertAppointmentMechanismInfo(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.replaceCategories(param);
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setMobile(param.getContact_telephone());
            userInfoEntity.setPageSize(1);
            List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);
            if (userInfoEntities != null && userInfoEntities.size() > 0 && userInfoEntities.get(0).getMechanism_id() != 0) {
                return ResultUtil.error(UserEum.user_10016.getCode(), UserEum.user_10016.getDesc());
            }
//            if (StringUtil.isEmpty(param.getVerification_code())) {//???????????????
//                return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
//            }
//            ResultParam resultParam = smsService.validate(param.getContact_telephone(), param.getVerification_code());//??????redis????????????
//            if (resultParam.getCode() != 0) {//??????
//                return resultParam;
//            } else {
            if (StringUtil.isNotEmpty(param.getRecommender_id())) {
                UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(param.getRecommender_id());
                if (userRecommenderEntity == null || "master_recommender".equals(userRecommenderEntity.getType())) {
                    return ResultUtil.error(UserEum.user_10040.getCode(), UserEum.user_10040.getDesc());
                }
            }
            param.setPageSize(1);
            List<MasterMechanismEntity> query = mapper.queryListPage(param);
            if (query != null && query.size() > 0) {
                param.setId(query.get(0).getId());
                return ResultUtil.error(UserEum.user_10039.getCode(), UserEum.user_10039.getDesc());
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setLogin_name(param.getContact_telephone());
            if (param.getContacts() != null) {
                userEntity.setNick_name(param.getContacts());
            }
            userEntity.setInvitation_code(param.getRecommender_id() + "");
            userEntity.setRegister_platform("mechanism_master");
            String str = "";
            if (StringUtil.isNotEmpty(param.getLogin_pass())) {
                str = MD5Util.GetMD5Code(param.getContact_telephone() + param.getLogin_pass());
            } else {
                str = MD5Util.GetMD5Code(param.getContact_telephone() + "123456");
            }
            userEntity.setLogin_pass(str);
            int i = mapper.insertSelective(param);
            userEntity.setMechanism_id(param.getId());
            userEntity.setAdmin_id(0L);
            ResultParam insert = userService.insert(userEntity, request, response);
            if (insert.getCode() == 0) {
                param.setStatus(2);
                userEntity = userMapper.queryListPage(userEntity).get(0);
                param.setUser_id(userEntity.getUser_id());
                int i1 = mapper.updateByPrimaryKeySelective(param);
                return ResultUtil.success();
            } else {
                throw new DateTimeException("??????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private void replaceCategories(MasterMechanismEntity param) {
        if (StringUtil.isNotEmpty(param.getCategories())) {
            param.setCategories(param.getCategories().replaceAll(",", "/"));
        }
        if (StringUtil.isNotEmpty(param.getCategories())) {
            param.setCategories_child(param.getCategories_child().replaceAll(",", "/"));
        }
    }

    @Override
    public synchronized ResultParam insertMechanismInfo(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.replaceCategories(param);
            if (StringUtil.isEmpty(param.getVerification_code())) {//???????????????
                return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
            }
            ResultParam resultParam = smsService.validate(param.getContact_telephone(), param.getVerification_code());//??????redis????????????
            if (resultParam.getCode() != 0) {//??????
                return resultParam;
            } else {
                param.setPageSize(1);
                List<MasterMechanismEntity> masterMechanismEntities = mapper.queryListPage(param);
                if (masterMechanismEntities != null && masterMechanismEntities.size() > 0) {
                    param.setId(masterMechanismEntities.get(0).getId());
                    return super.update(param, request, response);
                } else {
                    if (StringUtil.isNotEmpty(param.getRecommender_id())) {
                        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(param.getRecommender_id());
                        if (userRecommenderEntity == null || "master_recommender".equals(userRecommenderEntity.getType())) {
                            return ResultUtil.error(UserEum.user_10040.getCode(), UserEum.user_10040.getDesc());
                        }
                    }

                    UserEntity userEntity = new UserEntity();
                    userEntity.setMechanism_id(param.getId());
                    userEntity.setLogin_name(param.getContact_telephone());
                    userEntity.setNick_name(param.getContacts());
                    userEntity.setInvitation_code(param.getRecommender_id() + "");
                    userEntity.setRegister_platform("mechanism_master");
                    ResultParam insert = userService.insert(userEntity, request, response);
                    if (insert.getCode() == 0) {
                        userEntity.setPageSize(1);
                        userEntity = userMapper.queryListPage(userEntity).get(0);
                        param.setUser_id(userEntity.getUser_id());
                        ResultParam insert1 = super.insert(param, request, response);
                        if (insert1.getCode() == 0) {
                            return ResultUtil.success();
                        }
                    } else {
                        return ResultUtil.error(UserEum.user_10000.getCode(), UserEum.user_10000.getDesc());
                    }
                }
                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void updateCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            userStudyCardEntity.setUser_id(rechargeRecordEntity.getUser_id());
            userStudyCardEntity.setType(rechargeRecordEntity.getStudy_type());
            userStudyCardEntity.setMaster_id(rechargeRecordEntity.getMaster_id());
            userStudyCardEntity.setMechanism_id(rechargeRecordEntity.getMechanism_id());
            userStudyCardEntity.setStudycard_id(rechargeRecordEntity.getStudycard_id());
            userStudyCardEntity.setPageSize(1);
            userStudyCardEntity = userStudyCardMapper.queryListPage(userStudyCardEntity).get(0);
            if (userStudyCardEntity.getBinding_mechanism_id() != 0) {
                rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().multiply(new BigDecimal("0.95")));
            } else {
                rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().multiply(new BigDecimal("0.85")));
            }
            MasterMechanismEntity mechanismEntity = mapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id());
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();

            if (mechanismEntity != null && !"".equals(mechanismEntity.getRecommender_id())) {
                //??????????????????????????????
                UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(mechanismEntity.getRecommender_id());
                //???????????????id
                userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                BigDecimal mechanism_bonus = new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString());
                rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().subtract(mechanism_bonus));

                UserRecommenderIncomeLogEntity RecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                RecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                RecommenderIncomeLogEntity.setMechanism_id(rechargeRecordEntity.getMechanism_id());
                RecommenderIncomeLogEntity.setCash(mechanism_bonus);
                RecommenderIncomeLogEntity.setIs_settlement(false);
                RecommenderIncomeLogEntity.setType("mechanism_offline");
                RecommenderIncomeLogEntity.setCash_describe("??????????????????????????????");
                RecommenderIncomeLogEntity.setRecommender_type("mechanism_recommender");
                RecommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                RecommenderIncomeLogEntity.setAppointment_id(rechargeRecordEntity.getAppointment_id());
                RecommenderIncomeLogEntity.setRecharge_record_id(rechargeRecordEntity.getId());
                RecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                ResultParam insert = userRecommenderIncomeLogService.insert(RecommenderIncomeLogEntity, request, response);
                if (insert.getCode() == 0) {
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setPush_type("mechanism_offline");
                    pushMessageParam.setOpera_type("mechanism_offline");
                    pushMessageParam.setType("mechanism_offline");
                    pushMessageParam.setType_id(RecommenderIncomeLogEntity.getId());
                    pushMessageParam.setSend_id(0L);
                    pushMessageParam.setTarget_id(userRecommenderEntity.getUser_id());
                    pushMessageParam.setContent("??????????????????????????????");
                    pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                    pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                }
                userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
            }

            userRecommenderIncomeLogEntity.setCash(rechargeRecordEntity.getAmount());
            userRecommenderIncomeLogEntity.setType("sale_course");
            userRecommenderIncomeLogEntity.setCash_describe("????????????????????????");
            userRecommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
            userRecommenderIncomeLogEntity.setIs_settlement(false);
            userRecommenderIncomeLogEntity.setRecharge_record_id(rechargeRecordEntity.getId());
            userRecommenderIncomeLogEntity.setMechanism_id(rechargeRecordEntity.getMechanism_id());
            if (userRecommenderIncomeLogEntity.getRecommender_id() == null) {
                userRecommenderIncomeLogEntity.setRecommender_id(0L);
            }
            userRecommenderIncomeLogEntity.setRole_id(0L);
            ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
            if (insert.getCode() == 0) {
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setPush_type("sale_course");
                pushMessageParam.setOpera_type("sale_course");
                pushMessageParam.setType("sale_course");
                pushMessageParam.setType_id(userRecommenderIncomeLogEntity.getId());
                pushMessageParam.setSend_id(0L);
                pushMessageParam.setTarget_id(mechanismEntity.getUser_id());
                pushMessageParam.setContent("????????????????????????");
                pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }


    /**
     * ??????
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam mechanismAudit(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = mapper.updateByPrimaryKeySelective(param);
            if (ret > 0) {
                UserInfoEntity infoEntity = new UserInfoEntity();
                infoEntity.setUser_id(param.getUser_id());
                MasterMechanismEntity masterMechanismEntity = mapper.selectByPrimaryKey(param.getId());
                map = JSONUtils.generatorMap("????????????", true);
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setTarget_id(masterMechanismEntity.getUser_id());
                pushMessageParam.setType("authentication");
                pushMessageParam.setOpera_type("mechanismAuthentication");
                pushMessageParam.setPush_type("authentication");
                pushMessageParam.setTitle("??????????????????");
                pushMessageParam.setType_id(masterMechanismEntity.getId());
                if (param.getStatus() == 2) {
                    infoEntity.setMechanism_id(masterMechanismEntity.getId());
                    userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                    pushMessageParam.setContent("???????????????????????????");
                    if (StringUtil.isNotEmpty(masterMechanismEntity.getRecommender_id())) {
                        pushMessageParam.setTarget_id(userRecommenderMapper.queryInvateCode(masterMechanismEntity.getRecommender_id()).getUser_id());
                        pushMessageParam.setContent("??????????????????: " + masterMechanismEntity.getMechanism_name() + "?????????");
                        this.updateEarn(masterMechanismEntity, request, response);
                        smsService.sendPassSms(masterMechanismEntity.getContact_telephone(), pushMessageParam.getContent());
                    }
                } else {
                    if (StringUtil.isNotEmpty(masterMechanismEntity.getRecommender_id())) {
                        pushMessageParam.setTarget_id(userRecommenderMapper.queryInvateCode(masterMechanismEntity.getRecommender_id()).getUser_id());
                        pushMessageParam.setContent("??????????????????: " + masterMechanismEntity.getMechanism_name() + "?????????");
                        smsService.sendPassSms(masterMechanismEntity.getContact_telephone(), pushMessageParam.getContent());
                    } else {
                        pushMessageParam.setContent("???????????????????????????");
                    }
                }
                //??????????????????
                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("updateAuthMessage", pushMessageParam);
            } else {
                map = JSONUtils.generatorMap("????????????", false);
            }
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    private void updateEarn(MasterMechanismEntity masterMechanismEntity, HttpServletRequest request, HttpServletResponse response) {
        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterMechanismEntity.getRecommender_id());
        if ("mechanism_recommender".equals(userRecommenderEntity.getType())) {
            //??????
            UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
            //?????????
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
            userRecommenderIncomeLogEntity.setMechanism_id(masterMechanismEntity.getId());
            userRecommenderIncomeLogEntity.setCash(userEarnRoleEntity.getEvery_mechanism_earn());
            userRecommenderIncomeLogEntity.setType("mechanism");
            userRecommenderIncomeLogEntity.setCash_describe("????????????????????????");
            userRecommenderIncomeLogEntity.setRecommender_type("mechanism_recommender");
            userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
            ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
            if (insert.getCode() == 0) {//?????????
                //???????????????????????????
                userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(userEarnRoleEntity.getEvery_mechanism_earn()));
                userRecommenderEntity.setInvate_num(userRecommenderEntity.getInvate_num() + 1);
                userRecommenderMapper.updateByPrimaryKeySelective(userRecommenderEntity);
                //??????????????????
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
                userInfoEntity.setCash(userInfoEntity.getCash().add(userEarnRoleEntity.getEvery_mechanism_earn()));
                synchronized (userInfoEntity) {
                    userInfoMapper.updateCash(userInfoEntity);
                }
            }
        }
    }

    /**
     * ??????????????????
     * zwx
     * 2020???6???24???21:08:59
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MasterMechanismEntity> queryMechanismListPage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterMechanismEntity> masterInfoEntities = null;
        try {
            masterInfoEntities = mapper.queryListPage(param);//??????????????????
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                setInfo(masterInfoEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryListPage");
        }
        return masterInfoEntities;
    }

    @Override
    public List<MasterMechanismEntity> queryMechanismListPageNew(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterMechanismEntity> masterInfoEntities = null;
        try {
            masterInfoEntities = mapper.queryListPage(param);//??????????????????
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                this.setInfoNew(masterInfoEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryListPage");
        }
        return masterInfoEntities;
    }

    @Override
    public void updateOfflineCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public Map<String, Object> queryTeacherRoom(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        try {
            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
            masterInfoEntity.setMechanism_id(param.getId());
            masterInfoEntity.setStatus(2);
            Integer integer = masterInfoMapper.queryListPageCount(masterInfoEntity);
            map.put("having_master", integer > 0);

            MechanismClassroomEntity mechanismClassroomEntity = new MechanismClassroomEntity();
            mechanismClassroomEntity.setMechanism_id(param.getId());
            mechanismClassroomEntity.setStatus(2);
            Integer integer1 = mechanismClassroomMapper.queryListPageCount(mechanismClassroomEntity);
            map.put("having_classroom", integer1 > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MasterMechanismEntity> queryMechanismActivityList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterMechanismEntity> list = Lists.newArrayList();
        try {
            BusinessActivityTypeEntity businessActivityTypeEntity = businessActivityTypeMapper.selectByPrimaryKey(param.getId().intValue());
            String[] split = businessActivityTypeEntity.getEntry_mechanism_ids().split(",");
            if (!split[0].equals("0")) {
                list = mapper.queryMechanismActivityList(split);
                if (list != null && list.size() > 0) {
                    for (MasterMechanismEntity mechanismEntity : list) {
                        BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                        businessActivityEntity.setMechanism_id(mechanismEntity.getId());
                        businessActivityEntity.setBusiness_activity_type_id(businessActivityTypeEntity.getId().longValue());
                        Map<String, Object> map = Maps.newHashMap();
                        map.put("courseList", businessActivityMapper.queryActivityList(businessActivityEntity));
                        mechanismEntity.setMap(map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void setInfoNew(List<MasterMechanismEntity> list) {
        for (MasterMechanismEntity mechanismEntity : list) {
            Map<String, Object> map = Maps.newHashMap();

            //???????????????
            MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
            masterSetPriceEntity.setMechanism_id(mechanismEntity.getId());
            Integer sale_num = masterSetPriceMapper.queryListPageCount(masterSetPriceEntity);
            map.put("sale_num", sale_num);

            //????????????
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setIs_help(true);
            userInfoEntity.setAdmin_id(mechanismEntity.getId());
            Integer teachers_num = userInfoMapper.queryListPageCount(userInfoEntity);
            map.put("teachers_num", teachers_num);

            //??????????????????
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setMechanism_id(mechanismEntity.getId());
            masterAppointmentEntity.setStatus(1);
            Integer dayBeginCount = masterAppointmentMapper.queryDayCountAfterNow(masterAppointmentEntity);
            map.put("dayBeginCount", dayBeginCount);

            //???????????????
            Integer beginCount = masterAppointmentMapper.queryHomeListPageNearCountTotal(masterAppointmentEntity);
            map.put("beginCount", beginCount);

            //???????????????
            masterAppointmentEntity.setStatus(null);
            Integer historyCount = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);
            map.put("historyCount", historyCount);

            //????????????
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            userRecommenderIncomeLogEntity.setMechanism_id(mechanismEntity.getId());
            userRecommenderIncomeLogEntity.setRecommender_id(0L);
            userRecommenderIncomeLogEntity.setIs_settlement(true);
            BigDecimal totalIncome = userRecommenderIncomeLogMapper.queryTotalCash(userRecommenderIncomeLogEntity);
            map.put("totalIncome", totalIncome);

            //????????????
            BigDecimal DayEarnings = userRecommenderIncomeLogMapper.queryDayCash(userRecommenderIncomeLogEntity);
            map.put("DayEarnings", DayEarnings);
            mechanismEntity.setMap(map);
        }

    }

    private void setInfo(List<MasterMechanismEntity> masterInfoEntities) {
        for (MasterMechanismEntity entity : masterInfoEntities) {
            Map<String, Object> map = new HashMap<>();
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setMechanism_id(entity.getId());
            Integer sale_num = masterAppointmentMapper.queryBeginListPageCount(masterAppointmentEntity);//??????????????????????????????????????????
            map.put("sale_num", sale_num);//???????????????
            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
            masterInfoEntity.setStatus(2);
            masterInfoEntity.setMechanism_id(entity.getId());
            Integer teachers_num = masterInfoMapper.queryMasterListPageCount(masterInfoEntity);
            map.put("teachers_num", teachers_num);//????????????
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setMechanism_id(entity.getId());
            Integer dayBeginCount = userAppointmentMapper.queryDayBeginCount(userAppointmentEntity);
            map.put("dayBeginCount", dayBeginCount);//??????????????????
            userAppointmentEntity.setEarnings_status(2);
            Double totalIncome = userAppointmentMapper.queryCumulativeEarnings(userAppointmentEntity);
            map.put("totalIncome", totalIncome);//????????????
            Integer DayEarnings = userAppointmentMapper.queryDayCumulativeEarnings(userAppointmentEntity);
            map.put("DayEarnings", DayEarnings);//????????????
            UserAppointmentEntity appointmentEntity = new UserAppointmentEntity();
            appointmentEntity.setMechanism_id(entity.getId());
            Integer beginCount = userAppointmentMapper.queryBeginCount(userAppointmentEntity);
            map.put("beginCount", beginCount);//????????????
            appointmentEntity.setStatus(3);
            Integer historyCount = userAppointmentMapper.queryHistoryListPageCount(userAppointmentEntity);
            map.put("historyCount", historyCount);//???????????????
            entity.setMap(map);
        }
    }

    /**
     * ????????????????????????
     * zwx
     * 2020???6???28???21:34:31
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MasterMechanismEntity> queryMechanismStatistics(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterMechanismEntity> masterInfoEntities = null;
        try {
            masterInfoEntities = mapper.queryListPage(param);
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                for (MasterMechanismEntity entity : masterInfoEntities) {
                    Map<String, Object> map = new HashMap<>();
                    //????????????
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setStatus(2);
                    masterInfoEntity.setMechanism_id(entity.getId());
                    Integer teachers_num = masterInfoMapper.queryMasterListPageCount(masterInfoEntity);
                    //????????????
                    map.put("teachers_num", teachers_num);
                    masterInfoEntity.setType("major");
                    Integer major_master_num = masterInfoMapper.queryMasterListPageCount(masterInfoEntity);
                    //?????????????????????
                    map.put("major_master_num", major_master_num);
                    masterInfoEntity.setType("cross_border");
                    Integer cross_border_num = masterInfoMapper.queryMasterListPageCount(masterInfoEntity);
                    //?????????????????????
                    map.put("cross_border_num", cross_border_num);
                    masterInfoEntity.setType("mother_tongue");
                    Integer mother_tongue_num = masterInfoMapper.queryMasterListPageCount(masterInfoEntity);
                    //?????????????????????
                    map.put("mother_tongue_num", mother_tongue_num);
                    masterInfoEntity.setType("private_education");
                    Integer private_num = masterInfoMapper.queryMasterCount(masterInfoEntity);
                    //????????????
                    map.put("private_num", private_num);
                    masterInfoEntity.setType("live_lecturer");
                    Integer live_num = masterInfoMapper.queryMasterCount(masterInfoEntity);
                    //??????????????????
                    map.put("live_num", live_num);

                    //????????????
                    MasterAppointmentEntity appointmentEntity = new MasterAppointmentEntity();
                    appointmentEntity.setMechanism_id(entity.getId());
                    appointmentEntity.setType("mechanism_offline");
                    Integer mechanism_offline_num = masterAppointmentMapper.queryListPageCount(appointmentEntity);
                    //???????????????
                    map.put("mechanism_offline_num", mechanism_offline_num);
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    masterAppointmentEntity.setMechanism_id(entity.getId());
                    masterAppointmentEntity.setType("jointly_class");
                    Integer jointly_class_num = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);
                    //???????????????
                    map.put("jointly_class_num", jointly_class_num);
                    //??????????????????
                    map.put("sale_total_num", jointly_class_num + mechanism_offline_num);

                    //?????????
                    MasterAppointmentEntity single_classAppointmentEntity = new MasterAppointmentEntity();
                    single_classAppointmentEntity.setMechanism_id(entity.getId());
                    single_classAppointmentEntity.setType("single_class");
                    Integer single_class_num = masterAppointmentMapper.queryBeginListPageCount(single_classAppointmentEntity);
                    //??????????????????
                    map.put("single_class_num", single_class_num);
                    MasterAppointmentEntity open_classAppointmentEntity = new MasterAppointmentEntity();
                    open_classAppointmentEntity.setMechanism_id(entity.getId());
                    open_classAppointmentEntity.setType("open_class");
                    Integer open_class_num = masterAppointmentMapper.queryBeginListPageCount(open_classAppointmentEntity);
                    //???????????????
                    map.put("open_class_num", open_class_num);
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setMechanism_id(entity.getId());
                    userAppointmentEntity.setMaster_type("private_education");
                    Integer private_educationCount = userAppointmentMapper.queryBeginCount(userAppointmentEntity);
                    //???????????????
                    map.put("private_educationCount", private_educationCount);
                    //?????????????????????
                    map.put("schedule_total_num", private_educationCount + open_class_num + single_class_num);
                    //????????????
                    MasterAppointmentEntity single_class_history = new MasterAppointmentEntity();
                    single_class_history.setMechanism_id(entity.getId());
                    single_class_history.setType("single_class");
                    Integer single_class_history_num = masterAppointmentMapper.queryHistoryListPageCount(single_class_history);
                    //????????????????????????----------------
                    map.put("single_class_history_num", single_class_history_num);
                    MasterAppointmentEntity open_class_history = new MasterAppointmentEntity();
                    open_class_history.setMechanism_id(entity.getId());
                    open_class_history.setType("open_class");
                    Integer open_class_history_num = masterAppointmentMapper.queryHistoryListPageCount(open_class_history);
                    //?????????????????????------------------
//                    map.put("open_class_history_num", open_class_history_num);
                    map.put("open_class_history_num", open_class_history_num);

                    UserAppointmentEntity private_education_history = new UserAppointmentEntity();
                    private_education_history.setMechanism_id(entity.getId());
                    private_education_history.setMaster_type("private_education");
                    Integer private_education_historyCount = userAppointmentMapper.queryHistoryListPageCount(private_education_history);
                    //?????????????????????
                    map.put("private_education_historyCount", private_education_historyCount);

                    //??????????????????-----------------
//                    map.put("history_total_num", single_class_history_num + open_class_history_num + private_education_historyCount);
                    MasterAppointmentEntity masterAppointmentEntityAll = new MasterAppointmentEntity();
                    masterAppointmentEntityAll.setMechanism_id(param.getId());
                    Integer history_total_num = masterAppointmentMapper.queryHistoryListPageCount(masterAppointmentEntityAll);
                    map.put("history_total_num", history_total_num + private_education_historyCount);


                    UserAppointmentEntity appointmentEntity1 = new UserAppointmentEntity();
                    appointmentEntity1.setMechanism_id(param.getId());
                    appointmentEntity1.setEarnings_status(2);
                    appointmentEntity1.setMaster_type("private_education");
                    //????????????
                    Double private_educationEarnings = userAppointmentMapper.queryCumulativeEarnings(appointmentEntity1);
                    appointmentEntity1.setMaster_type("major");
                    //?????????????????????
                    Double majorEarnings = userAppointmentMapper.queryCumulativeEarnings(appointmentEntity1);
                    appointmentEntity1.setMaster_type("cross_border");
                    //?????????????????????
                    Double cross_borderEarnings = userAppointmentMapper.queryCumulativeEarnings(appointmentEntity1);
                    appointmentEntity1.setMaster_type("mother_tongue");
                    //?????????????????????
                    Double mother_tongueEarnings = userAppointmentMapper.queryCumulativeEarnings(appointmentEntity1);
                    Double masterEarnings = mother_tongueEarnings + cross_borderEarnings + majorEarnings;
                    //???????????????
                    map.put("masterEarnings", masterEarnings);
                    //????????????
                    map.put("private_educationEarnings", private_educationEarnings);
                    appointmentEntity1.setMaster_type("mechanism_offline");
                    //????????????
                    Double mechanism_offlineEarnings = userAppointmentMapper.queryCumulativeEarnings(appointmentEntity1);
                    //????????????
                    map.put("mechanism_offlineEarnings", mechanism_offlineEarnings);
                    MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                    masterAppointmentEntity1.setMechanism_id(entity.getId());
                    masterAppointmentEntity1.setType("open_class");
                    List<MasterAppointmentEntity> appointmentEntities = masterAppointmentMapper.query(masterAppointmentEntity1);
                    double aDouble_total = 0.0;
                    for (MasterAppointmentEntity entity1 : appointmentEntities) {
                        double profit_total = 0.0;
                        ClassRecordEntity classRecordCurriculum = new ClassRecordEntity();
                        classRecordCurriculum.setCurriculum_id(entity1.getId());
                        classRecordCurriculum.setType("curriculum");
                        Integer curriculum_count = classRecordMapper.queryListPageCount(classRecordCurriculum);
                        //10?????????1???
                        profit_total = (double) curriculum_count * 10;
                        ClassRecordEntity classRecord = new ClassRecordEntity();
                        classRecord.setCurriculum_id(entity1.getId());
                        classRecord.setType("minute");
                        List<ClassRecordEntity> classRecordMinutes = classRecordMapper.queryListPage(classRecord);
                        //???????????????
                        for (ClassRecordEntity classRecordEntity : classRecordMinutes) {
                            int classRecordMinute = classRecordEntity.getFree_minute() - 5;
                            //0.8????????????
                            profit_total = profit_total + 0.8 * classRecordMinute;
                        }
                        BigDecimal bigDecimal = new BigDecimal(0);
                        UserOrderEntity userOrderEntit = new UserOrderEntity();
                        userOrderEntit.setSource("user");
                        userOrderEntit.setPay_id(entity1.getId());
                        //???????????????????????????
                        List<UserOrderEntity> userOrderEntities = userOrderMapper.queryUserRankingListPage(userOrderEntit);
                        //?????????
                        if (userOrderEntities != null && userOrderEntities.size() > 0) {
                            //????????????
                            userOrderEntit = userOrderEntities.get(0);
                            bigDecimal = userOrderEntit.getPay_count();
                        }
                        if (bigDecimal == null) {
                            bigDecimal = new BigDecimal(0);
                        }
                        //?????????+=?????????+?????????
                        aDouble_total = aDouble_total + Double.parseDouble(bigDecimal.toString()) + profit_total;
                    }
                    //????????????
                    map.put("liveEarnings", aDouble_total);
                    //?????????
                    map.put("totalEarnings", aDouble_total + masterEarnings + private_educationEarnings + mechanism_offlineEarnings);
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryListPage");
        }
        return masterInfoEntities;
    }

    /**
     * ??????????????????
     * zwx
     * 2020???6???24???21:08:59
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MasterMechanismEntity> queryNearbyListPage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterMechanismEntity> masterInfoEntities = null;
        try {
            masterInfoEntities = mapper.queryNearbyListPage(param);
            if (masterInfoEntities == null || masterInfoEntities.size() == 0) {
                param.setSort_weight(0);
                masterInfoEntities = mapper.queryNearbyListPage(param);
            }
            setInfo(masterInfoEntities);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryListPage");
        }
        return masterInfoEntities;
    }

    @Override
    public Map<String, Object> queryMasterMechanismCount(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            Integer countTotal = mapper.queryListPageCount(param);
            //??????
            map.put("countTotal", countTotal);

            param.setStatus(2);
            Integer passTotal = mapper.queryListPageCount(param);
            //????????????
            map.put("passTotal", passTotal);

            param.setMechanism_language("English");
            Integer englishCount = mapper.queryEnglishCount(param);
            //??????????????????
            map.put("EnglishCount", englishCount);

            param.setMechanism_language("Japanese");
            Integer JapaneseCount = mapper.queryJapaneseCount(param);
            //??????????????????
            map.put("JapaneseCount", JapaneseCount);

            param.setMechanism_language("French");
            Integer FrenchCount = mapper.queryFrenchCount(param);
            //??????????????????
            map.put("FrenchCount", FrenchCount);

            param.setIs_recommend(true);
            Integer recommendTotal = mapper.queryListPageCount(param);
            map.put("recommendTotal", recommendTotal);
            //??????????????????
            map.put("OtherCount", countTotal - englishCount - JapaneseCount - FrenchCount);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public Map<String, Object> masterMechanismCount(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer total = mapper.queryListPageCount(param);
            map.put("total", total);

            param.setStatus(2);
            Integer agreeCount = mapper.queryListPageCount(param);
            map.put("agreeCount", agreeCount);

            param.setStatus(1);
            Integer waitCount = mapper.queryListPageCount(param);
            map.put("waitCount", waitCount);

            param.setStatus(3);
            Integer refuseCount = mapper.queryListPageCount(param);
            map.put("refuseCount", refuseCount);

            param.setMechanism_language("English");
            Integer englishCount = mapper.queryEnglishCount(param);
            map.put("englishCount", englishCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}