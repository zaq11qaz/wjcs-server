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
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.UserRecommenderIncomeLogService;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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

                logEntity.setMap(map);
            }
        }
        return logEntities;
    }

    @Override
    public ResultParam insert(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam;
        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
        userRecommenderIncomeLogEntity1.setRecommender_id(userRecommenderIncomeLogEntity.getRecommender_id());//推荐人id
        userRecommenderIncomeLogEntity1.setType(userRecommenderIncomeLogEntity.getType());//类型
        userRecommenderIncomeLogEntity1.setPageSize(1);
        switch (userRecommenderIncomeLogEntity.getType()) {
            case "mechanism"://机构
                userRecommenderIncomeLogEntity1.setMechanism_id(userRecommenderIncomeLogEntity.getMechanism_id());//机构id
                resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
                break;

            case "mechanism_master"://机构外教
                userRecommenderIncomeLogEntity1.setMechanism_id(userRecommenderIncomeLogEntity.getMechanism_id());//机构id
                userRecommenderIncomeLogEntity1.setMaster_id(userRecommenderIncomeLogEntity.getMaster_id());//外教id
                resultParam = this.checkMasterMax(userRecommenderIncomeLogEntity1);
                break;

            case "master"://外教
                userRecommenderIncomeLogEntity1.setMaster_id(userRecommenderIncomeLogEntity.getMaster_id());//外教id
                resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
                break;

            case "mechansim_appointment"://机构添加商品
                userRecommenderIncomeLogEntity1.setMechanism_id(userRecommenderIncomeLogEntity.getMechanism_id());
                userRecommenderIncomeLogEntity1.setMastersetprice_id(userRecommenderIncomeLogEntity.getMastersetprice_id());
                resultParam = this.checkAppointmentMax(userRecommenderIncomeLogEntity1);
                break;

            case "user"://邀请用户
                userRecommenderIncomeLogEntity1.setInvitation_id(userRecommenderIncomeLogEntity.getInvitation_id());
                resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
                break;

            case "sale_course"://出售课程
            case "mechanism_offline"://机构出售课程
            case "master_offline"://外教出售课程分成
            case "system_card": //出售平台学习卡分成
            case "sale_card": //直播间内购买学习卡分成
            case "mechanism_sale_offline": //直播间内购买学习卡分成
            case "master_sale_offline": //直播间内购买学习卡分成
                return super.insert(userRecommenderIncomeLogEntity, request, response);

            case "live"://直播
            case "master_live"://外教直播课程分成
            case "mechanism_live"://机构直播课程分成
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
     * 查询是否存在 更新
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
     * 查询外教金额是否超过上限
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private ResultParam checkMasterMax(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1) {
        ResultParam resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
        if (resultParam.getCode() == 0) {

            BigDecimal cashTotal = mapper.querySumMechanismMasterCash(userRecommenderIncomeLogEntity1);
            if (cashTotal.compareTo(queryMasterMaxEarn(userRecommenderIncomeLogEntity1)) < 0) {//如果小于最高收益
                return ResultUtil.success();
            }
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    /**
     * 查询机构课程是否上限
     *
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    private ResultParam checkAppointmentMax(UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1) {
        ResultParam resultParam = this.checkIsExist(userRecommenderIncomeLogEntity1);
        if (resultParam.getCode() == 0) {
            BigDecimal cashTotal = new BigDecimal(mapper.querySumMechanismMasterCash(userRecommenderIncomeLogEntity1).toString());
            if (cashTotal.compareTo(queryMechanismAppMaxEarn(userRecommenderIncomeLogEntity1)) < 0) {//如果小于最高收益
                return ResultUtil.success();
            }
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    /**
     * 检查邀请机构是否存在
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
     * 查询最高收益上限
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
     * 查询机构课程上限
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
                //如果结算
                if (param.getIs_settlement()!=null&&param.getIs_settlement()) {
                    //直播收益
                    userRecommenderIncomeLogEntity = query.get(0);
                    UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMaster_id());
                    if (userInfoEntity.getAdmin_id() != 0) {
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(masterMechanismMapper.selectByPrimaryKey(userInfoEntity.getAdmin_id()).getUser_id());
                    }
                    userInfoEntity.setCash(userInfoEntity.getCash().add(userRecommenderIncomeLogEntity.getCash()));
                    synchronized (userInfoEntity){
                        userInfoMapper.updateCash(userInfoEntity);
                    }
                    userRecommenderIncomeLogEntity.setIs_settlement(true);
                    mapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);

                    if (query1 != null && query1.size() > 0) {
                        userRecommenderIncomeLogEntity = query1.get(0);
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getRecommender_id()).getUser_id());
                        userInfoEntity.setCash(userInfoEntity.getCash().add(userRecommenderIncomeLogEntity.getCash()));
                         synchronized (userInfoEntity){
                        userInfoMapper.updateCash(userInfoEntity);
                    }
                        userRecommenderIncomeLogEntity.setIs_settlement(true);
                        mapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);
                    }

                    if (query2 != null && query2.size() > 0) {
                        userRecommenderIncomeLogEntity = query2.get(0);
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getRecommender_id()).getUser_id());
                        userInfoEntity.setCash(userInfoEntity.getCash().add(userRecommenderIncomeLogEntity.getCash()));
                         synchronized (userInfoEntity){
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
            if (total>0){
                setInfo(list);
            }
            map.put("rows", list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam queryMechanismCashTotalList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = Maps.newHashMap();
        try {
            List<UserRecommenderIncomeLogEntity> list = mapper.queryMechanismIdList(param);
            Integer total = mapper.queryMechanismIdListCount(param);
            if (total>0){
                for (UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity : list) {
                    userRecommenderIncomeLogEntity.setIs_settlement(true);
                    BigDecimal settlementCash = mapper.queryTotalCash(userRecommenderIncomeLogEntity);
                    userRecommenderIncomeLogEntity.setIs_settlement(false);
                    BigDecimal notSettlementCash = mapper.queryTotalCash(userRecommenderIncomeLogEntity);
                    Map<String,Object> map1 = Maps.newHashMap();
                    map1.put("settlementCash", settlementCash);
                    map1.put("notSettlementCash", notSettlementCash);
                    map1.put("mechanismOwnerInfo", JSONUtils.obj2Json(redisService.getStr(
                            masterMechanismMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMechanism_id()).getUser_id()+"userinfo")));
                    userRecommenderIncomeLogEntity.setMap(map1);

                }
            }
            map.put("rows", list);
            map.put("total", total);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success(map);
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
        pushMessageParam.setContent("出售课程收益已到账" + logEntity.getCash() + "元");
        pushMessageParam.setType("cash");
        pushMessageParam.setTitle("出售课程收益到账");
        pushMessageParam.setIs_teach_paypal(logEntity.getIs_teach_paypal());
        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);

        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);

        logEntity.setIs_settlement(true);
        mapper.updateByPrimaryKeySelective(logEntity);
        UserInfoEntity userInfoEntity;
        if (logEntity.getMechanism_id() != 0) {
            Long user_id = masterMechanismMapper.selectByPrimaryKey(logEntity.getMechanism_id()).getUser_id();
            userInfoEntity = userInfoMapper.selectByPrimaryKey(user_id);
        } else if (logEntity.getMaster_id()!=0){
            userInfoEntity = userInfoMapper.selectByPrimaryKey(logEntity.getMaster_id());
        }else {
            userInfoEntity = userInfoMapper.selectByPrimaryKey(logEntity.getUser_id());
        }
        /**
         * 出售课程收益
         */
        userInfoEntity.setCash(userInfoEntity.getCash().add(logEntity.getCash().multiply(new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString()))));//减去收益
        synchronized (userInfoEntity) {
            userInfoMapper.updateCash(userInfoEntity);
        }
    }

    /**
     * 机构推荐官收益
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
        pushMessageParam.setContent("已到账" + logEntity.getCash() + "元");
        pushMessageParam.setType("机构收益到账");
        pushMessageParam.setIs_teach_paypal(logEntity.getIs_teach_paypal());
        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
    }

    private void updateMasterCash(UserRecommenderIncomeLogEntity logEntity) {
        /**
         * 外教推荐官收益
         */
        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.selectByPrimaryKey(logEntity.getRecommender_id());

        userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(logEntity.getCash()));
        synchronized (userRecommenderEntity) {
            userRecommenderMapper.updateCash(userRecommenderEntity);
        }

        /**
         * 推荐余额
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
        pushMessageParam.setContent("已到账" + logEntity.getCash() + "元");
        pushMessageParam.setType("cash");
        pushMessageParam.setTitle("邀请外教课程分成到账");
        pushMessageParam.setIs_teach_paypal(logEntity.getIs_teach_paypal());
        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
    }
}