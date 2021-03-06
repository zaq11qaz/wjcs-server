package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.MasterSetPriceService;
import com.huihe.eg.user.service.dao.UserRecommenderIncomeLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class MasterSetPriceServiceImpl extends BaseFrameworkServiceImpl<MasterSetPriceEntity, Long> implements MasterSetPriceService {

    @Resource
    private MasterSetPriceMapper mapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MasterSetPricePriceMapper masterSetPricePriceMapper;
    @Resource
    private UserGroupingMapper userGroupingMapper;
    @Resource
    private BusinessActivityMapper businessActivityMapper;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private BusinessActivityTypeMapper businessActivityTypeMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResultParam insert(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getId() != null && param.getId() > 0) {
                return super.update(param, request, response);
            }
            if (param.getUser_id() != null && param.getUser_id() != 0) {
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(param.getUser_id());
                masterInfoEntity.setStatus(2);
                masterInfoEntity.setPageSize(1);
                List<MasterInfoEntity> list = masterInfoMapper.queryListPage(masterInfoEntity);
                if (list == null || list.size() < 1) {
                    return ResultUtil.error(MasterEum.master_40020.getCode(), MasterEum.master_40020.getDesc());
                }
            }

            MasterMechanismEntity mechanismEntity = null;
            if (param.getMechanism_id() != null && param.getMechanism_id() != 0) {
                mechanismEntity = masterMechanismMapper.selectByPrimaryKey(param.getMechanism_id());
                param.setLatitude(mechanismEntity.getLatitude());
                param.setLongitude(mechanismEntity.getLongitude());
                if (StringUtil.isNotEmpty(mechanismEntity.getRecommender_id())) {
                    updateCash(param, request, response);
                }
            }


            if ((param.getIs_public_exclusive() != null && param.getIs_public_exclusive())) {
                param.setIs_recommend(true);
            }

            if (param.getIs_attend_activities() != null && param.getIs_attend_activities()) {
                ResultParam resultParam = this.insertActivity(param);
                if (resultParam.getCode() != 0) {
                    throw new Exception("??????????????????");
                }
            }

            int i = mapper.insertSelective(param);
            if (i > 0) {

                if (StringUtil.isNotEmpty(param.getPrice_list())) {
                    int index = 0;
                    List<String> strings = Arrays.asList(param.getPrice_list().split(","));
                    while (index < strings.size()) {
                        String[] paramList = strings.get(index).split("-");
                        MasterSetPricePriceEntity masterSetPricePriceEntity = new MasterSetPricePriceEntity();
                        masterSetPricePriceEntity.setMaster_set_price_id(param.getId());
                        int number = 0;
                        for (String s : paramList) {
                            switch (number) {
                                case 0:
                                    number++;
                                    masterSetPricePriceEntity.setUpper_limit(Integer.valueOf(s));
                                    continue;
                                case 1:
                                    number++;
                                    masterSetPricePriceEntity.setLower_limit(Integer.valueOf(s));
                                    continue;
                                case 2:
                                    number++;
                                    masterSetPricePriceEntity.setPrice(new BigDecimal(s));
                                    int res = masterSetPricePriceMapper.insertSelective(masterSetPricePriceEntity);
                                    if (res == 0) {
                                        throw new Exception("????????????????????????");
                                    } else {
                                        param.setOriginal_price(masterSetPricePriceEntity.getPrice());
                                        mapper.updateByPrimaryKeySelective(param);
                                    }
                            }
                        }
                        index++;
                    }
                }

                try {

                    if (param.getMechanism_id() != null && (param.getMechanism_id() == 4 || param.getMechanism_id() == 26)) {
                        Long group_id = null;
                        if (mechanismEntity != null) {
                            group_id = messageApiService.insertMasterGroup(param.getId(), param.getTitle(), mechanismEntity.getUser_id(), param.getFace_url());
                        } else {
                            group_id = messageApiService.insertMasterGroup(param.getId(), param.getTitle(), param.getUser_id(), param.getFace_url());
                        }
                        if (group_id != null && group_id != 0) {
                            param.setMaster_set_price_group_id(group_id);
                            mapper.updateByPrimaryKeySelective(param);
                        }
                    }
                } catch (Exception ignored) {

                }

            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    @Transactional
    public ResultParam update(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if ((param.getIs_public_exclusive() != null && param.getIs_public_exclusive())) {
                param.setIs_recommend(true);
            }

            if (param.getIs_attend_activities() != null && param.getIs_attend_activities()) {
                ResultParam resultParam = this.insertActivity(param);
                if (resultParam.getCode() != 0) {
                    throw new Exception("??????????????????");
                }
            }
            if (StringUtil.isNotEmpty(param.getPrice_list())) {
                int i = masterSetPricePriceMapper.deleteByMasterSetPriceId(param.getId());
                int index = 0;
                List<String> strings = Arrays.asList(param.getPrice_list().split(","));
                while (index < strings.size()) {
                    String[] paramList = strings.get(index).split("-");
                    MasterSetPricePriceEntity masterSetPricePriceEntity = new MasterSetPricePriceEntity();
                    masterSetPricePriceEntity.setMaster_set_price_id(param.getId());
                    int number = 0;
                    for (String s : paramList) {
                        switch (number) {
                            case 0:
                                number++;
                                masterSetPricePriceEntity.setUpper_limit(Integer.valueOf(s));
                                continue;
                            case 1:
                                number++;
                                masterSetPricePriceEntity.setLower_limit(Integer.valueOf(s));
                                continue;
                            case 2:
                                number++;
                                masterSetPricePriceEntity.setPrice(new BigDecimal(s));
                                int res = masterSetPricePriceMapper.insertSelective(masterSetPricePriceEntity);
                                if (res == 0) {
                                    throw new Exception("????????????????????????");
                                } else {
                                    param.setOriginal_price(masterSetPricePriceEntity.getPrice());
                                    mapper.updateByPrimaryKeySelective(param);
                                }
                        }
                    }
                    index++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return super.update(param, request, response);
    }

    private ResultParam insertActivity(MasterSetPriceEntity param) {
        BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
        businessActivityEntity.setEnd_time(param.getEnd_time());
        businessActivityEntity.setStart_time(param.getStart_time());
        businessActivityEntity.setPrice(param.getActivity_price());
        businessActivityEntity.setMaster_set_price_id(param.getId());
        businessActivityEntity.setType("salesCourse");
        int i = businessActivityMapper.insertSelective(businessActivityEntity);
        if (i > 0) {
            param.setDiscount_amout(param.getActivity_price());
            param.setActivity_id(businessActivityEntity.getId());
            return ResultUtil.success();
        } else {
            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
        }
    }

    private void updateCash(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        MasterMechanismEntity masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(param.getMechanism_id());
        if (StringUtil.isNotEmpty(masterMechanismEntity.getRecommender_id())) {
            UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterMechanismEntity.getRecommender_id());
            if ("mechanism_recommender".equals(userRecommenderEntity.getType())) {
                //??????
                UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                //?????????
                UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                userRecommenderIncomeLogEntity.setMechanism_id(masterMechanismEntity.getId());
                userRecommenderIncomeLogEntity.setIs_settlement(true);
                userRecommenderIncomeLogEntity.setCash(userEarnRoleEntity.getEvery_commodity_earn());
                userRecommenderIncomeLogEntity.setType("mechansim_appointment");
                userRecommenderIncomeLogEntity.setMastersetprice_id(param.getId());
                userRecommenderIncomeLogEntity.setRecommender_type("mechanism_recommender");
                userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                if (insert.getCode() == 0) {//?????????
                    //???????????????????????????
                    userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(userEarnRoleEntity.getEvery_mechanism_earn()));
                    userRecommenderMapper.updateByPrimaryKeySelective(userRecommenderEntity);
                    //??????????????????
                    UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
                    userInfoEntity.setCash(userRecommenderEntity.getEarnings_this_month().add(userEarnRoleEntity.getEvery_mechanism_earn()));
                    synchronized (userInfoEntity) {
                        userInfoMapper.updateCash(userInfoEntity);
                    }
                }
            }
        }
    }

    @Override
    public Map<String, Object> queryCommodityList(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        Integer total = mapper.queryOnSaleCount(param);
        if (param.getId() == null) {
            if (param.getUser_id() != null) {
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(param.getUser_id());
                userInfoEntity.setPageSize(1);
                List<UserInfoEntity> query = userInfoMapper.queryListPage(userInfoEntity);//??????user_id
                if (query != null && query.size() > 0) {
                    userInfoEntity = query.get(0);
                    if (userInfoEntity.getAdmin_id() == 0 && userInfoEntity.getMechanism_id() == 0) {
                        list = mapper.queryOnSale(param);
                    } else {
                        param.setUser_id(null);
                        if (userInfoEntity.getAdmin_id() != 0) {
                            param.setMechanism_id(userInfoEntity.getAdmin_id());
                        } else {
                            param.setMechanism_id(userInfoEntity.getMechanism_id());
                        }
                        list = mapper.queryOnSale(param);
                    }
                }
            } else {
                list = mapper.queryOnSale(param);
            }
        } else {
            list.add(mapper.selectByPrimaryKey(param.getId()));
            total = 1;
        }
        if (total > 0) {
            queryInfoList(list);
        }
        map.put("list", list);
        map.put("listCount", total);
        return map;
    }

    private void queryInfoList(List<MasterSetPriceEntity> list) {
        for (MasterSetPriceEntity masterSetPriceEntity : list) {
            Map<String, Object> map = Maps.newHashMap();

            MasterMechanismEntity masterMechanismEntity = null;
            List<UserInfoEntity> userInfoEntityList = null;
            if (masterSetPriceEntity.getMechanism_id() != 0) {
                masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id());
                if (masterMechanismEntity != null) {
                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                    userInfoEntity.setAdmin_id(masterMechanismEntity.getId());
                    userInfoEntity.setIs_help(true);
                    userInfoEntity.setPageSize(10);
                    userInfoEntity.setCurrentPage(1);
                    userInfoEntityList = userInfoMapper.queryListPage(userInfoEntity);
                    map.put("mechanismEntityMasterList", userInfoEntityList);
                } else {
                    map.put("mechanismEntityMasterList", null);
                }
                if (masterSetPriceEntity.getActivity_id() != 0 && "double_science_Activity".equals(masterSetPriceEntity.getType())) {
                    map.put("businessActivityTypeEntity", businessActivityTypeMapper.selectByPrimaryKey(masterSetPriceEntity.getActivity_id().intValue()));
                }
            }
            map.put("masterMechanismEntity", masterMechanismEntity);

            MasterAppointmentEntity masterAppointmentEntity = null;
            if (masterSetPriceEntity.getMaster_appointment_id() != 0) {
                masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(masterSetPriceEntity.getMaster_appointment_id());
            }
            map.put("masterAppointmentEntity", masterAppointmentEntity);

            UserInfoEntity userInfoEntity = null;
            if (masterSetPriceEntity.getUser_id() != 0) {
                userInfoEntity = userInfoMapper.selectByPrimaryKey(masterSetPriceEntity.getUser_id());
            }
            map.put("masterinfo", userInfoEntity);
            map.put("priceList", this.queryPrice(masterSetPriceEntity.getId()));

            BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
            businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
            businessActivityEntity.setStatus(2);
            map.put("activityEntityList", businessActivityMapper.query(businessActivityEntity));

//            map.put("activityEntityList", Lists.newArrayList());

            masterSetPriceEntity.setMap(map);
        }
    }

    @Override
    public void queryInfoListNew(List<MasterSetPriceEntity> list) {
        for (MasterSetPriceEntity masterSetPriceEntity : list) {
            Map<String, Object> map = Maps.newHashMap();
//            masterSetPriceEntity.setLive_streaming_id(mapper.selectByPrimaryKey(masterSetPriceEntity.getId()).getLive_streaming_id());
            MasterMechanismEntity masterMechanismEntity = null;
            List<UserInfoEntity> userInfoEntityList = null;
            if (masterSetPriceEntity.getMechanism_id() != 0) {
                masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id());
                if (masterMechanismEntity != null) {
                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                    userInfoEntity.setAdmin_id(masterMechanismEntity.getId());
                    userInfoEntity.setIs_help(true);
                    userInfoEntity.setPageSize(1);
                    userInfoEntity.setCurrentPage(1);
                    userInfoEntityList = userInfoMapper.queryListPage(userInfoEntity);
                    map.put("is_having_teacher", userInfoEntityList != null && userInfoEntityList.size() > 0);
                    map.put("mechanismEntityMasterList", null);
                }
            }
            map.put("masterMechanismEntity", masterMechanismEntity);

            MasterAppointmentEntity masterAppointmentEntity = null;
            if (masterSetPriceEntity.getMaster_appointment_id() != 0) {
                masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(masterSetPriceEntity.getMaster_appointment_id());
            }
            map.put("masterAppointmentEntity", masterAppointmentEntity);

            UserInfoEntity userInfoEntity = null;
            if (masterSetPriceEntity.getUser_id() != 0) {
                userInfoEntity = userInfoMapper.selectByPrimaryKey(masterSetPriceEntity.getUser_id());
            }
            map.put("masterinfo", userInfoEntity);
            map.put("priceList", this.queryPrice(masterSetPriceEntity.getId()));

            BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
            businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
            businessActivityEntity.setStatus(2);
            map.put("activityEntityList", businessActivityMapper.query(businessActivityEntity));
//            map.put("activityEntityList", Lists.newArrayList());
            masterSetPriceEntity.setMap(map);
        }
    }

    @Override
    public List<MasterSetPriceEntity> queryRecommendList(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        param.setStatus(2);
        List<MasterSetPriceEntity> list = mapper.queryRecommendList(param);
        if (list.size() > 0) {
            this.queryInfoList(list);
        }
        return list;
    }

    @Override
    public Map<String, Object> queryCommodityListByType(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        List<MasterSetPriceEntity> masterSetPriceEntities = mapper.queryListPage(param);
        Integer total = mapper.queryListPageCount(param);
        if (total > 0) {
            this.queryInfoList(masterSetPriceEntities);
        }
        map.put("total", total);
        map.put("masterSetPriceEntities", masterSetPriceEntities);
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (param.getFull_name() != null) {
                List<Long> list1 = masterInfoMapper.queryIdByFullName(param.getFull_name());
                if (list1 != null && list1.size() > 0) {
                    param.setIds(list1);
                }
            }
            if (param.getLogin_name() != null) {
                List<Long> list1 = userMapper.queryIdByLoginName(param.getLogin_name());
                if (list1 != null && list1.size() > 0) {
                    param.setLoginIDs(list1);
                }
            }
            List<MasterSetPriceEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total > 0) {
                this.queryInfoList(list);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
/*
    @Override
    public List<MasterSetPriceEntity> queryNearByCourse(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryByMessage(param);

            if (list == null || list.size() < 1) {
                MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                masterSetPriceEntity.setPageSize(10);
                masterSetPriceEntity.setStatus(2);
                masterSetPriceEntity.setIs_recommend(true);
                masterSetPriceEntity.setType(param.getType());
                list = mapper.queryListPage(param);
            }

            if (list != null && list.size() > 0) {
                this.queryInfoListNew(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

 */

    @Override
    public List<MasterSetPriceEntity> queryNearByCourse(List<MasterSetPriceEntity> list, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.queryInfoListNew(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public List<MasterSetPriceEntity> queryCourseListPage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            list = mapper.query(param);
            if (list != null && list.size() > 0) {
                for (MasterSetPriceEntity masterSetPriceEntity : list) {
                    Map<String, Object> map = Maps.newHashMap();
                    BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                    businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
                    businessActivityEntity.setStatus(2);
                    map.put("activityEntityList", businessActivityMapper.query(businessActivityEntity));
//                    map.put("activityEntityList", Lists.newArrayList());
                    masterSetPriceEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer queryIsCourse(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        int flag = 0;
        param.setAppointment_type("appointment");
        Integer appointmentCount = mapper.queryListPageCount(param);

        param.setAppointment_type("scheduling");
        Integer schedulingCount = mapper.queryListPageCount(param);

        if (appointmentCount > 0 && schedulingCount > 0) {
            flag = 3;
        } else if (appointmentCount > 0) {
            flag = 1;
        } else if (schedulingCount > 0) {
            flag = 2;
        }
        return flag;
    }

    @Override
    public ResultParam queryGroupInfo(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        MasterSetPriceEntity masterSetPriceEntity = null;
        try {
            UserGroupingEntity userGroupingEntity = new UserGroupingEntity();
            userGroupingEntity.setGroup_id(param.getGroup_id());
            userGroupingEntity.setPageSize(1);
            List<UserGroupingEntity> userGroupingEntities = userGroupingMapper.querylistPageByIdAsc(userGroupingEntity);
            if (userGroupingEntities != null && userGroupingEntities.size() > 0) {
                List<Long> ids = userGroupingMapper.queryUserIds(userGroupingEntity);
                userGroupingEntity = userGroupingEntities.get(0);
                masterSetPriceEntity = mapper.selectByPrimaryKey(userGroupingEntity.getMaster_set_price_id());
                HashMap<String, Object> map = Maps.newHashMap();
                map.put("firstUserInfo", JSONUtils.obj2Json(redisService.getStr(userGroupingEntity.getUser_id() + "userinfo")));
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setIds(ids);
                String[] avatarList = userInfoMapper.queryUserAvatar(userInfoEntity);
                map.put("avatarList", avatarList);
                map.put("mechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id()));
                map.put("end_time", userGroupingMapper.queryEndTime(userGroupingEntity));
                if (param.getUser_id() != null) {
                    UserGroupingEntity userGroupingEntity1 = new UserGroupingEntity();
                    userGroupingEntity1.setUser_id(param.getUser_id());
                    userGroupingEntity1.setGroup_id(param.getGroup_id());
                    map.put("is_participate", userGroupingMapper.queryListPageCount(userGroupingEntity1) > 0);
                } else {
                    map.put("is_participate", false);
                }
                masterSetPriceEntity.setMap(map);
            } else {
                return ResultUtil.error(UserEum.user_10053.getCode(), UserEum.user_10053.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(masterSetPriceEntity);
    }

    @Override
    public ResultParam updateActivityInfo(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        if (param.getIs_attend_activities() != null && !param.getIs_attend_activities()) {
            param.setActivity_id(0L);
        } else {
            this.insertActivity(param);
        }
        int i = mapper.updateByPrimaryKeySelective(param);
        if (i > 0) {
            return ResultUtil.success();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public Object queryMasterSetPriceById(Long master_set_price_id) {
        return JSONObject.toJSON(mapper.selectByPrimaryKey(master_set_price_id));
    }

    @Override
    public List<MasterSetPriceEntity> queryActivityListPage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
//            list = mapper.queryListPage(param);
            list = mapper.queryEqualsNotFu(param);
            if (list != null && list.size() > 0) {
                this.setActivityInfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MasterSetPriceEntity> queryActivityListPageByType(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryActivityListPageByType(param);
            if (list != null && list.size() > 0) {
                this.setActivityInfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MasterSetPriceEntity> queryMechanismDisplay(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryMechanismDisplay(param);
            if (list != null && list.size() > 0) {
                this.setActivityInfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MasterSetPriceEntity> queryMechanismDisplayIsLiving(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            param.setIs_live_streaming(true);
            list = mapper.queryMechanismDisplay(param);
            if (list != null && list.size() > 0) {
                this.setActivityInfo(list);
            } else {
                param.setIs_live_streaming(null);
                List<MasterSetPriceEntity> list1 = mapper.queryMechanismDisplay(param);
                list = Lists.newArrayList(list1.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MasterSetPriceEntity> queryDetails(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            if (param.getLive_streaming_id() != null && param.getLive_streaming_id() != 0) {
                list.add(mapper.selectByPrimaryKey(param.getId()));
            } else {
                list = mapper.queryMechanismDisplay(param);
            }
            if (list != null && list.size() > 0) {
                this.setActivityInfoDetail(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Transactional
    public ResultParam insertActivityMasterSetPrice(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isNotEmpty(param.getType())) {
                BusinessActivityTypeEntity businessActivityTypeEntity = businessActivityTypeMapper.selectByType(param.getType());
                BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                businessActivityEntity.setType(param.getType());
                param.setType("mechanism_offline");
                switch (businessActivityEntity.getType()) {
                    case "experience_specials":
                        param.setId(null);
                        param.setCourse_num(6);
                        param.setAmout(new BigDecimal("99"));
                        param.setDefault_discount_price(new BigDecimal("99"));
                        param.setDiscount_amout(new BigDecimal("0"));
                        param.setOriginal_price(new BigDecimal("0"));
                        param.setPrice_per_session(new BigDecimal("0"));
                        param.setIs_enlightenment(true);
                        param.setDiscount(new BigDecimal("0"));
//                            masterSetPriceEntity.setOriginal_price(new BigDecimal("99"));
//                        if (StringUtil.isNotEmpty(param.getActivity_list())) {
//                            param.setActivity_list(param.getActivity_list() + "," + businessActivityTypeEntity.getTags());
//                        } else {
//                            param.setActivity_list(param.getActivity_list() + businessActivityTypeEntity.getTags());
                        param.setActivity_list(businessActivityTypeEntity.getTags());
//                        }
                        ArrayList<String> strings = Lists.newArrayList(param.getTitile_url(), ",");
                        StringBuilder sb = new StringBuilder();
                        if (strings.size() > 6) {
                            for (int j = 0; j < 6; j++) {
                                if (j > 0) {
                                    sb.append(",").append(strings.get(j));
                                } else {
                                    sb.append(strings.get(j));
                                }

                            }
                            param.setTitile_url(sb.toString());
                        }
                        param.setIs_attend_activities(true);
                        int i2 = mapper.insertSelective(param);
                        if (i2 > 0) {
                            businessActivityEntity.setTags(businessActivityTypeEntity.getTags());
                            businessActivityEntity.setMaster_set_price_id(param.getId());
                            int i1 = businessActivityMapper.insertSelective(businessActivityEntity);
                            if (i1 == 0) {
                                throw new Exception("??????????????????");
                            }
                            return ResultUtil.success();
                        } else {
                            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                        }
                    default:  int i = mapper.insertSelective(param);;
                }

            }
            throw new Exception("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public List<MasterSetPriceEntity> queryActivityListPageByActivityId(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryActivityListPageByActivityId(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void setActivityInfo(List<MasterSetPriceEntity> list) {
        for (MasterSetPriceEntity masterSetPriceEntity : list) {
            BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
            businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("activityEntityList", businessActivityMapper.queryIn12(businessActivityEntity));
//            map.put("activityEntityList", Lists.newArrayList());
            map.put("masterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id()));
            masterSetPriceEntity.setMap(map);
        }
    }

    private void setActivityInfoDetail(List<MasterSetPriceEntity> list) {
        for (MasterSetPriceEntity masterSetPriceEntity : list) {
            BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
            businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("activityEntityList", businessActivityMapper.queryIn12(businessActivityEntity));
//            map.put("activityEntityList", Lists.newArrayList());
            map.put("masterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id()));
            masterSetPriceEntity.setMap(map);
        }
    }

    public List<MasterSetPricePriceEntity> queryPrice(Long id) {
        MasterSetPricePriceEntity masterSetPricePriceEntity = new MasterSetPricePriceEntity();
        masterSetPricePriceEntity.setMaster_set_price_id(id);
        return masterSetPricePriceMapper.queryPriceList(masterSetPricePriceEntity);
    }
}