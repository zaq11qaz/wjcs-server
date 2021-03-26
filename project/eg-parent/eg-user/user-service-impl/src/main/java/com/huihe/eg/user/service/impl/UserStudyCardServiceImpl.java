package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.MasterSetPricePriceService;
import com.huihe.eg.user.service.dao.StudyCardService;
import com.huihe.eg.user.service.dao.UserStudyCardService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserStudyCardServiceImpl extends BaseFrameworkServiceImpl<UserStudyCardEntity, Long> implements UserStudyCardService {

    @Resource
    private UserStudyCardMapper mapper;
    @Resource
    private StudyPriceMapper studyPriceMapper;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private StudyCardService studyCardService;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private ClassCardMapper classCardMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private MasterSetPricePriceService masterSetPricePriceService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<UserStudyCardEntity> queryStudyCore(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> userStudyCardEntities = Lists.newArrayList();
        try {
            userStudyCardEntities = mapper.queryListPageNotEqual0(param);
            UserStudyCardEntity userStudyCardEntity2 = new UserStudyCardEntity();
            userStudyCardEntity2.setStatus(2);
            userStudyCardEntity2.setUser_id(param.getUser_id());
            userStudyCardEntity2.setMaster_id(0L);
            userStudyCardEntity2.setMechanism_id(0L);
            List<UserStudyCardEntity> list = mapper.queryListPage(userStudyCardEntity2);

            //如果有可用 不处理
            if (list == null || list.size() == 0) {
                userStudyCardEntity2.setUser_id(0L);
                userStudyCardEntity2.setStatus(null);
                userStudyCardEntities.addAll(mapper.query(userStudyCardEntity2));//添加 查询假数据
            }
            for (UserStudyCardEntity entity : userStudyCardEntities) {
                Map<String, Object> map = Maps.newHashMap();
                StudyCardEntity studyCardEntity = new StudyCardEntity();
                studyCardEntity.setType(entity.getType());
                List<StudyCardEntity> studyCardEntities = studyCardService.query(studyCardEntity, request, response);
                map.put("cardinfo", studyCardEntities);
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setUser_id(entity.getUser_id());
                userAppointmentEntity.setStatus(2);
                userAppointmentEntity.setPageSize(1);
                userAppointmentEntity.setMaster_type(entity.getType());
                Integer monthClassCount = userAppointmentMapper.queryTypeCount(userAppointmentEntity);
                map.put("monthClassCount", monthClassCount + entity.getCourse_num());//本月课时
                map.put("monthSurplusClassCount", entity.getCourse_num());//本月剩余课时
                entity.setMap(map);
            }
            //直播课程学习卡
            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
            userClassCardEntity.setUser_id(param.getUser_id());
            userClassCardEntity.setStatus(2);
            List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
            if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                userStudyCardEntity.setType("live_card");
                Map<String, Object> map = new HashMap<>();
                Integer minute_count = 0;
                Integer curriculum_count = 0;
                for (UserClassCardEntity entity : userClassCardEntities) {
                    if ("minute".equalsIgnoreCase(entity.getType())) {
                        minute_count = entity.getMinute_num();
                    } else if ("curriculum".equalsIgnoreCase(entity.getType())) {
                        curriculum_count = entity.getCurriculum_num();
                    }
                }
                map.put("minute_count", minute_count);
                map.put("curriculum_count", curriculum_count);
                userStudyCardEntity.setMap(map);
                userStudyCardEntities.add(userStudyCardEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserStudyCardServiceImpl  query");
        }
        return userStudyCardEntities;
    }

    @Override
    public List<UserStudyCardEntity> queryUserByCard(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPage(param);
            for (UserStudyCardEntity userStudyCardEntity : list) {
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(userStudyCardEntity.getUser_id());
                Map<String, Object> map = Maps.newHashMap();
                map.put("userInfo", userInfoMapper.query(userInfoEntity).get(0));
                userStudyCardEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public Map<String, Object> queryStudyEachTime(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer dayPayCount = mapper.queryHelperDayCount(param);
            map.put("dayPayCount", dayPayCount);//日统计

            Integer weekPayCount = mapper.queryWeekCount(param);
            map.put("weekPayCount", weekPayCount);//周统计

            Integer monthPayCount = mapper.queryMonthCount(param);
            map.put("monthPayCount", monthPayCount);//月统计

            Integer payCount = mapper.queryPayCount(param);
            map.put("payCount", payCount);//总统计

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            if (param.getLogin_name() != null && !"".equals(param.getLogin_name())) {
                List<Long> list1 = userMapper.queryIdByLoginName(param.getLogin_name());
                if (list1 != null) {
                    param.setLoginIds(list1);
                }
            }
            if (param.getNick_name() != null && !"".equals(param.getNick_name())) {
                List<Long> list1 = userInfoMapper.queryIdByNickName(param.getNick_name());
                if (list1 != null) {
                    param.setNickNameIds(list1);
                }
            }
            List<UserStudyCardEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            for (UserStudyCardEntity userStudyCardEntity : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(userStudyCardEntity.getUser_id() + "userinfo")));
                userStudyCardEntity.setMap(map);
            }
            map1.put("rows", list);
            map1.put("total", total);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return map1;
    }

    @Override
    public Map<String, Object> queryStudyList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            List<UserStudyCardEntity> studyCardEntities = mapper.queryListPage(param);
            Integer total = mapper.queryListPageCount(param);
            if (total > 0) {
                for (UserStudyCardEntity studyCardEntity : studyCardEntities) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(studyCardEntity.getUser_id() + "userinfo")));
                    map.put("countTotal", studyCardEntities.size());
                    studyCardEntity.setMap(map);
                }
            }
            map1.put("rows", studyCardEntities);
            map1.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("StudyCardServiceImpl  query");
        }
        return map1;
    }

    @Override
    public List<UserStudyCardEntity> queryMyStudyCore(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            param.setStatus(2);
            if (param.getId() == null) {
                List<UserStudyCardEntity> list1 = mapper.query(param);
                Integer sum = 0;
                for (UserStudyCardEntity entity : list1) {
                    Map<String, Object> map = new HashMap<>();
                    StudyCardEntity studyCardEntity = new StudyCardEntity();
                    studyCardEntity.setType(entity.getType());
                    List<StudyCardEntity> studyCardEntities = studyCardService.query(studyCardEntity, request, response);
                    map.put("cardinfo", studyCardEntities);
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setUser_id(entity.getUser_id());
                    userAppointmentEntity.setStatus(2);
                    userAppointmentEntity.setPageSize(1);
                    userAppointmentEntity.setMaster_type(entity.getType());
                    Integer monthClassCount = userAppointmentMapper.queryTypeCount(userAppointmentEntity);
                    map.put("monthClassCount", monthClassCount + entity.getCourse_num());//本月课时
                    entity.setMap(map);
                    sum += entity.getCourse_num();
                }

                UserStudyCardEntity userStudyCardEntity1 = new UserStudyCardEntity();
                userStudyCardEntity1.setType("single_class");//助学师学习卡
                userStudyCardEntity1.setId(0L);
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setUser_id(param.getUser_id());
                userAppointmentEntity.setPageSize(1);
                userAppointmentEntity = userAppointmentMapper.queryNearCourse(userAppointmentEntity);//查询最近一节课id
                HashMap<String, Object> map2 = new HashMap<>();
                if (userAppointmentEntity != null) {
                    map2.put("userAppointmentEntity", userAppointmentEntity);
                } else {
                    map2.put("userAppointmentEntity", new HashMap<>());
                }
                userStudyCardEntity1.setMap(map2);

                UserStudyCardEntity userStudyCardEntity2 = new UserStudyCardEntity();
                userStudyCardEntity2.setType("private_education");//私教
                userStudyCardEntity2.setId(0L);
                UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
                userAppointmentEntity1.setUser_id(param.getUser_id());
                userAppointmentEntity1.setPageSize(1);
                userAppointmentEntity1.setMaster_type("private_education");
                userAppointmentEntity1 = userAppointmentMapper.queryNearCourse(userAppointmentEntity1);//查询最近一节课id
                HashMap<String, Object> map3 = new HashMap<>();
                if (userAppointmentEntity1 != null) {
                    map3.put("userAppointmentEntity", userAppointmentEntity1);
                } else {
                    map3.put("userAppointmentEntity", new HashMap<>());
                }
                userStudyCardEntity2.setMap(map3);

                param.setType("exclusive_courses");
                //专属
                List<UserStudyCardEntity> list2 = mapper.queryExclusiveCard(param);
                Integer exclusiveSum = this.setInfo(list2);
                Integer total = mapper.queryExclusiveCardCount(param);

                param.setType("recording");
                //精品
                List<UserStudyCardEntity> list3 = mapper.queryExclusiveCard(param);
                Integer exclusiveSum2 = this.setInfo(list3);
                Integer total2 = mapper.queryExclusiveCardCount(param);

                sum = sum - exclusiveSum - exclusiveSum2;
                userStudyCardEntity1.setCourse_num(sum);
                list.add(userStudyCardEntity1);
                list.add(userStudyCardEntity2);

                //直播课程学习卡
                UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                userClassCardEntity.setUser_id(param.getUser_id());
                userClassCardEntity.setStatus(2);
                List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                Integer minute_count = 0;
                Integer curriculum_count = 0;
                if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                    UserStudyCardEntity userStudyCardEntity3 = new UserStudyCardEntity();
                    userStudyCardEntity3.setType("live_card");
                    Map<String, Object> map1 = new HashMap<>();

                    for (UserClassCardEntity entity : userClassCardEntities) {
                        if ("minute".equalsIgnoreCase(entity.getType())) {
                            minute_count = entity.getMinute_num();
                        } else if ("curriculum".equalsIgnoreCase(entity.getType())) {
                            curriculum_count = entity.getCurriculum_num();
                        }
                    }
                    map1.put("minute_count", minute_count);
                    map1.put("curriculum_count", curriculum_count);
                    userStudyCardEntity3.setMap(map1);
                    userStudyCardEntity3.setId(0L);
                    list.add(userStudyCardEntity3);
                } else {
                    UserStudyCardEntity userStudyCardEntity3 = new UserStudyCardEntity();
                    userStudyCardEntity3.setType("live_card");
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("minute_count", minute_count);
                    map1.put("curriculum_count", curriculum_count);
                    userStudyCardEntity3.setMap(map1);
                    userStudyCardEntity3.setId(0L);
                    list.add(userStudyCardEntity3);
                }

                UserStudyCardEntity userStudyCardEntity4 = new UserStudyCardEntity();
                userStudyCardEntity4.setType("exclusive_courses");
                HashMap<String, Object> map = new HashMap<>();
//                map.put("exclusive_courses_list",list2);
                map.put("exclusive_courses_count", total);
                userStudyCardEntity4.setMap(map);
                list.add(userStudyCardEntity4);

                param.setType("recording");
                UserStudyCardEntity userStudyCardEntity5 = new UserStudyCardEntity();
                userStudyCardEntity5.setType("recording");
                HashMap<String, Object> map1 = new HashMap<>();
//                map1.put("recording_list",list3);
                map1.put("recording_list_count", total2);
                userStudyCardEntity5.setMap(map1);
                list.add(userStudyCardEntity5);

            } else {
                list = mapper.queryListPage(param);
                if (list != null && list.size() > 0) {
                    this.setinfoList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void setinfoList(List<UserStudyCardEntity> list) {
        for (UserStudyCardEntity userStudyCardEntity : list) {
            HashMap<String, Object> map = new HashMap<>();
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setUser_id(userStudyCardEntity.getUser_id());
            userAppointmentEntity.setStatus(2);

            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(userStudyCardEntity.getStudycard_id());
            if (masterSetPriceEntity == null) {
                continue;
            }

            MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
            if (masterSetPriceEntity.getMechanism_id() != 0) {
                mechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id());
                userAppointmentEntity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            }
            map.put("mechanismEntity", mechanismEntity);


            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            if (masterSetPriceEntity.getMaster_appointment_id() != 0) {
                masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(masterSetPriceEntity.getMaster_appointment_id());
            }
            map.put("masterAppointmentEntity", masterAppointmentEntity);

            JSONObject jsonObject = new JSONObject();
            if (masterSetPriceEntity.getUser_id() != 0) {
                jsonObject = JSONUtils.obj2Json(redisService.getStr(masterSetPriceEntity.getUser_id() + "userinfo"));
                userAppointmentEntity.setMaster_id(masterSetPriceEntity.getUser_id());
            }
            map.put("masterinfo", jsonObject);


            map.put("masterSetPriceEntity", masterSetPriceEntity);

            userAppointmentEntity.setPageSize(1);
            userAppointmentEntity = userAppointmentMapper.queryNearCourse(userAppointmentEntity);
            if (userAppointmentEntity != null) {
                map.put("userAppointmentEntity", userAppointmentEntity);
            } else {
                map.put("userAppointmentEntity", new HashMap<>());
            }

            userStudyCardEntity.setMap(map);
        }
    }

    @Override
    public List<UserStudyCardEntity> queryMyStudyCoreList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryExclusiveCard(param);
            this.setInfo(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<UserStudyCardEntity> queryMyExperience(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPage(param);
            if (list != null && list.size() > 0) {
                for (UserStudyCardEntity userStudyCardEntity : list) {
                    RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                    rechargeRecordEntity.setIs_experience(true);
                    rechargeRecordEntity.setUser_id(param.getUser_id());
                    rechargeRecordEntity.setRcharge_type("study_card");
                    List<RechargeRecordEntity> list1 = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("rechargeRecordEntity", list1);
                    userStudyCardEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryMyStudyCoreListPage(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserStudyCardEntity> userStudyCardEntities = this.queryListPage(param, request, response);
            Integer total = mapper.queryListPageCount(param);
            if (total > 0) {
                this.setinfoList(userStudyCardEntities);
            }
            map.put("rows", userStudyCardEntities);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<UserStudyCardEntity> queryExclusiveCoursesList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryExclusiveCoursesList(param);
            if (list != null && list.size() > 0) {
                this.setInfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<UserStudyCardEntity> queryMyStudyCoreNew(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            param.setStatus(2);
            if (param.getId() == null) {
                List<UserStudyCardEntity> list1 = mapper.query(param);
                Integer sum = 0;
                for (UserStudyCardEntity entity : list1) {
                    Map<String, Object> map = new HashMap<>();
                    StudyCardEntity studyCardEntity = new StudyCardEntity();
                    studyCardEntity.setType(entity.getType());
                    List<StudyCardEntity> studyCardEntities = studyCardService.query(studyCardEntity, request, response);
                    map.put("cardinfo", studyCardEntities);
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setUser_id(entity.getUser_id());
                    userAppointmentEntity.setStatus(2);
                    userAppointmentEntity.setPageSize(1);
                    userAppointmentEntity.setMaster_type(entity.getType());
                    Integer monthClassCount = userAppointmentMapper.queryTypeCount(userAppointmentEntity);
                    map.put("monthClassCount", monthClassCount + entity.getCourse_num());//本月课时
                    entity.setMap(map);
                    sum += entity.getCourse_num();
                }

                UserStudyCardEntity userStudyCardEntity1 = new UserStudyCardEntity();
                userStudyCardEntity1.setType("single_class");//助学师学习卡
                userStudyCardEntity1.setId(0L);
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setUser_id(param.getUser_id());
                userAppointmentEntity.setPageSize(1);
                userAppointmentEntity = userAppointmentMapper.queryNearCourse(userAppointmentEntity);//查询最近一节课id
                HashMap<String, Object> map2 = new HashMap<>();
                if (userAppointmentEntity != null) {
                    map2.put("userAppointmentEntity", userAppointmentEntity);
                } else {
                    map2.put("userAppointmentEntity", new HashMap<>());
                }
                userStudyCardEntity1.setMap(map2);

                UserStudyCardEntity userStudyCardEntity2 = new UserStudyCardEntity();
                userStudyCardEntity2.setType("private_education");//私教
                userStudyCardEntity2.setId(0L);
                UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
                userAppointmentEntity1.setUser_id(param.getUser_id());
                userAppointmentEntity1.setPageSize(1);
                userAppointmentEntity1.setMaster_type("private_education");
                userAppointmentEntity1 = userAppointmentMapper.queryNearCourse(userAppointmentEntity1);//查询最近一节课id
                HashMap<String, Object> map3 = new HashMap<>();
                if (userAppointmentEntity1 != null) {
                    map3.put("userAppointmentEntity", userAppointmentEntity1);
                } else {
                    map3.put("userAppointmentEntity", new HashMap<>());
                }
                userStudyCardEntity2.setMap(map3);

                param.setType("exclusive_courses");
                //专属
                List<UserStudyCardEntity> list2 = mapper.queryExclusiveCard(param);
                Integer exclusiveSum = this.setInfo(list2);
                Integer total = mapper.queryExclusiveCardCount(param);

                param.setType("recording");
                //精品
                List<UserStudyCardEntity> list3 = mapper.queryExclusiveCard(param);
                Integer exclusiveSum2 = this.setInfo(list3);
                Integer total2 = mapper.queryExclusiveCardCount(param);

                sum = sum - exclusiveSum - exclusiveSum2;
                userStudyCardEntity1.setCourse_num(sum);
                list.add(userStudyCardEntity1);
                list.add(userStudyCardEntity2);

                //直播课程学习卡
                UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                userClassCardEntity.setUser_id(param.getUser_id());
                userClassCardEntity.setStatus(2);
                List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                Integer minute_count = 0;
                Integer curriculum_count = 0;
                if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                    UserStudyCardEntity userStudyCardEntity3 = new UserStudyCardEntity();
                    userStudyCardEntity3.setType("live_card");
                    Map<String, Object> map1 = new HashMap<>();

                    for (UserClassCardEntity entity : userClassCardEntities) {
                        if ("minute".equalsIgnoreCase(entity.getType())) {
                            minute_count = entity.getMinute_num();
                        } else if ("curriculum".equalsIgnoreCase(entity.getType())) {
                            curriculum_count = entity.getCurriculum_num();
                        }
                    }
                    map1.put("minute_count", minute_count);
                    map1.put("curriculum_count", curriculum_count);
                    userStudyCardEntity3.setMap(map1);
                    userStudyCardEntity3.setId(0L);
                    list.add(userStudyCardEntity3);
                } else {
                    UserStudyCardEntity userStudyCardEntity3 = new UserStudyCardEntity();
                    userStudyCardEntity3.setType("live_card");
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("minute_count", minute_count);
                    map1.put("curriculum_count", curriculum_count);
                    userStudyCardEntity3.setMap(map1);
                    userStudyCardEntity3.setId(0L);
                    list.add(userStudyCardEntity3);
                }

                UserStudyCardEntity userStudyCardEntity4 = new UserStudyCardEntity();
                userStudyCardEntity4.setType("exclusive_courses");
                HashMap<String, Object> map = new HashMap<>();
//                map.put("exclusive_courses_list",list2);
                map.put("exclusive_courses_count", total);
                userStudyCardEntity4.setMap(map);
                list.add(userStudyCardEntity4);

                UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                userStudyCardEntity.setType("mechanism_offline");
                param.setType("mechanism_offline");
                Integer integer = mapper.queryExclusiveCardCount(param);
                HashMap<String, Object> map4 = new HashMap<>();
                map4.put("mechanism_offline_count", integer);
                userStudyCardEntity.setMap(map4);
                list.add(userStudyCardEntity);

                param.setType("recording");
                UserStudyCardEntity userStudyCardEntity5 = new UserStudyCardEntity();
                userStudyCardEntity5.setType("recording");
                HashMap<String, Object> map1 = new HashMap<>();
//                map1.put("recording_list",list3);
                map1.put("recording_list_count", total2);
                userStudyCardEntity5.setMap(map1);
                list.add(userStudyCardEntity5);

            } else {
                list = mapper.queryListPage(param);
                if (list != null && list.size() > 0) {
                    this.setinfoList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<UserStudyCardEntity> queryStudentList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPageNotEqual0(param);
            if (list != null && list.size() > 0) {
                for (UserStudyCardEntity userStudyCardEntity : list) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(userStudyCardEntity.getUser_id() + "userinfo")));
                    userStudyCardEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void queryStudyCardCourse() {
        try {
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            List<UserStudyCardEntity> list31 = mapper.queryNeedSettle13(userStudyCardEntity);

            List<UserStudyCardEntity> list32 = mapper.queryNeedSettle23(userStudyCardEntity);

            List<UserStudyCardEntity> listAll = mapper.queryNeedSettleAll(userStudyCardEntity);
            //todo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultParam queryPayInfo(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<Object, Object> map = Maps.newHashMap();
        try {
            param = mapper.selectByPrimaryKey(param.getId());
            UserStudyCardEntity userStudyCardEntity = mapper.selectByPrimaryKey(param.getId());
            BigDecimal payNum = masterSetPricePriceService.queryNeedPay(param);
            map.put("payNum", payNum);
            map.put("payCourseCount", userStudyCardEntity.getCourse_num());
//            map.put("need_pay",!userStudyCardEntity.getIs_one_time_payment());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public List<UserStudyCardEntity> queryIsPayed(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserStudyCardEntity> list = Lists.newArrayList();
        try {
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            userStudyCardEntity.setUser_id(param.getUser_id());
            userStudyCardEntity.setStudycard_id(param.getStudycard_id());
            userStudyCardEntity.setPageSize(1);
            List<UserStudyCardEntity> userStudyCardEntities = mapper.queryListPage(userStudyCardEntity);
            if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {
                userStudyCardEntity.setId(userStudyCardEntities.get(0).getId());
                list = this.queryExclusiveCoursesList(userStudyCardEntity, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryStudyEachTimeCount(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer dayPayCount = mapper.queryDayCountNum(param);
            map.put("dayPayCount", dayPayCount);//日统计

            Integer weekPayCount = mapper.queryWeekCountNum(param);
            map.put("weekPayCount", weekPayCount);//周统计

            Integer monthPayCount = mapper.queryMonthCountNum(param);
            map.put("monthPayCount", monthPayCount);//月统计

            Integer payCount = mapper.queryListPageCount(param);
            map.put("payCount", payCount);//总统计

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryStudentInfo(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserStudyCardEntity> list  = mapper.queryListPage(param);
            Integer total = mapper.queryListPageCount(param);
            if (total > 0) {
                this.setStudentInfo(list);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam UpdateProductInfo(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserStudyCardEntity userStudyCardEntity = mapper.selectByPrimaryKey(param.getUser_study_card_id());
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(param.getUser_study_card_id());
            userStudyCardEntity.setEach_lesson_price(masterSetPriceEntity.getOriginal_price());
            userStudyCardEntity.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            userStudyCardEntity.setLanguage(masterSetPriceEntity.getLanguage());
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.MONTH, masterSetPriceEntity.getDuration());
            Date dt1 = rightNow.getTime();
            userStudyCardEntity.setEnd_time(dt1);
            int i = mapper.updateByPrimaryKeySelective(userStudyCardEntity);
            return ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private void setStudentInfo(List<UserStudyCardEntity> list) {
        for (UserStudyCardEntity userStudyCardEntity : list) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(userStudyCardEntity.getStudycard_id()));
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setUser_id(userStudyCardEntity.getUser_id());
            userAppointmentEntity.setStudy_card_id(userStudyCardEntity.getId());
            userAppointmentEntity.setStatus(2);
            List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryListPage(userAppointmentEntity);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                map.put("userAppointmentEntity", userAppointmentEntities.get(0));
            } else {
                map.put("userAppointmentEntity", null);
            }
            map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(userStudyCardEntity.getUser_id()+"userinfo")));
            userStudyCardEntity.setMap(map);
        }
    }

    private Integer setInfo(List<UserStudyCardEntity> list2) {
        Integer exclusiveSum = 0;
        for (UserStudyCardEntity userStudyCardEntity : list2) {
            HashMap<String, Object> map = new HashMap<>();
            UserAppointmentEntity userAppointmentEntity2 = new UserAppointmentEntity();
            userAppointmentEntity2.setUser_id(userStudyCardEntity.getUser_id());
            userAppointmentEntity2.setStudy_card_id(userStudyCardEntity.getId());

            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(userStudyCardEntity.getStudycard_id());
            if (masterSetPriceEntity == null) {
                continue;
            }

            MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
            if (masterSetPriceEntity.getMechanism_id() != 0) {
                masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterSetPriceEntity.getMechanism_id());
                userAppointmentEntity2.setMechanism_id(masterSetPriceEntity.getMechanism_id());
            }
            map.put("mechanismEntity", masterMechanismEntity);

            /*
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            if (masterSetPriceEntity.getMaster_appointment_id() != 0) {
                masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(masterSetPriceEntity.getMaster_appointment_id());
            }

            map.put("masterAppointmentEntity", masterAppointmentEntity);
             */

            if (masterSetPriceEntity.getUser_id() != 0) {
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(masterSetPriceEntity.getUser_id() + "userinfo")));
                userAppointmentEntity2.setMaster_id(masterSetPriceEntity.getUser_id());
            } else {
                map.put("masterinfo", null);
            }
            map.put("masterSetPriceEntity", masterSetPriceEntity);

            userAppointmentEntity2.setPageSize(1);
            userAppointmentEntity2 = userAppointmentMapper.queryNearCourse(userAppointmentEntity2);
            if (userAppointmentEntity2 != null) {
                map.put("userAppointmentEntity", userAppointmentEntity2);
            } else {
                map.put("userAppointmentEntity", new HashMap<>());
            }

            exclusiveSum += userStudyCardEntity.getCourse_num();
            map.put("exclusiveSum", exclusiveSum);

            userStudyCardEntity.setMap(map);
        }
        return exclusiveSum;

    }


}