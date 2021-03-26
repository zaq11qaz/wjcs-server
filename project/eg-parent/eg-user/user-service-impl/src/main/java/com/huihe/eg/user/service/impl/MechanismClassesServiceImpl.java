package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.MechanismClassesService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MechanismClassesServiceImpl extends BaseFrameworkServiceImpl<MechanismClassesEntity, Long> implements MechanismClassesService {

    @Resource
    private MechanismClassesMapper mapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<MechanismClassesEntity> queryListPage(MechanismClassesEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MechanismClassesEntity> list = mapper.queryListPage(param);
        if (list != null && list.size() > 0) {
            if (param.getId() == null) {
                for (MechanismClassesEntity masterMechanismEntity : list) {
                    HashMap<String, Object> map = Maps.newHashMap();
                    MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(masterMechanismEntity.getMaster_set_price_id());
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    masterAppointmentEntity.setMechanism_class_id(masterMechanismEntity.getId());
                    masterAppointmentEntity.setStatus(1);
                    masterAppointmentEntity.setIdentity_type("teach_paypal_course");
                    Integer integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);
                    Integer course_num = masterSetPriceEntity.getCourse_num();
                    map.put("lessonCount", course_num);
                    map.put("needLessonCount", integer);
                    map.put("endLessonCount", masterAppointmentMapper.querylistpageNotIn1(masterAppointmentEntity));
                    map.put("masterSetPriceEntity", masterSetPriceEntity);
                    masterAppointmentEntity.setPageSize(1);
                    List<MasterAppointmentEntity> list1 = masterAppointmentMapper.queryListPageAsc(masterAppointmentEntity);
                    if (list1 != null && list1.size() > 0) {
                        map.put("beginTime", list1.get(0).getStart_time());
                    } else {
                        map.put("beginTime", null);
                    }
                    list1 = masterAppointmentMapper.queryListPage(masterAppointmentEntity);
                    if (list1 != null && list1.size() > 0) {
                        map.put("endTime", list1.get(0).getStart_time());
                    } else {
                        map.put("endTime", null);
                    }

                    UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                    userStudyCardEntity.setMechanism_class_id(masterMechanismEntity.getId());
                    List<UserStudyCardEntity> query = userStudyCardMapper.query(userStudyCardEntity);

                    List<Map<String, Object>> objects = Lists.newArrayList();
                    for (UserStudyCardEntity studyCardEntity : query) {
                        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(studyCardEntity.getUser_id());
                        HashMap<String, Object> map1 = Maps.newHashMap();
                        map1.put("user_id", userInfoEntity.getUser_id());
                        map1.put("nick_name", userInfoEntity.getNick_name());
                        map1.put("avatar", userInfoEntity.getAvatar());
                        objects.add(map1);
                    }
                    map.put("userInfoList", objects);

                    masterMechanismEntity.setMap(map);
                }
            } else {
                for (MechanismClassesEntity masterMechanismEntity : list) {
                    UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                    userStudyCardEntity.setMechanism_class_id(masterMechanismEntity.getId());
                    List<UserStudyCardEntity> query = userStudyCardMapper.query(userStudyCardEntity);
                    HashMap<String, Object> map = Maps.newHashMap();
                    MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(masterMechanismEntity.getMaster_set_price_id());
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    masterAppointmentEntity.setMechanism_class_id(masterMechanismEntity.getId());
                    masterAppointmentEntity.setStatus(1);
                    masterAppointmentEntity.setIdentity_type("teach_paypal_course");
                    Integer integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);
                    Integer course_num = masterSetPriceEntity.getCourse_num();
                    map.put("lessonCount", course_num);
                    map.put("needLessonCount", integer);
                    Integer integer1 = masterAppointmentMapper.querylistpageNotIn1(masterAppointmentEntity);
                    map.put("endLessonCount", masterAppointmentMapper.querylistpageNotIn1(masterAppointmentEntity));
                    map.put("masterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterMechanismEntity.getMechanism_id()));
                    map.put("masterSetPriceEntity", masterSetPriceEntity);
                    if (integer+integer1==course_num){
                       int i = mapper.updateIsAllTrue(masterMechanismEntity);
                    }
                    masterAppointmentEntity.setStatus(1);
                    if (param.getSearch_start_time() != null) {
                        masterAppointmentEntity.setStart_time(param.getSearch_start_time());
                    }
                    if (param.getSearch_end_time() != null) {
                        masterAppointmentEntity.setEnd_time(param.getSearch_end_time());
                    }
                    map.put("classSchedule", masterAppointmentMapper.queryMechanismSchedule(masterAppointmentEntity));
                    List<Map<String, Object>> objects = Lists.newArrayList();
                    for (UserStudyCardEntity studyCardEntity : query) {
                        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(studyCardEntity.getUser_id());
                        HashMap<String, Object> map1 = Maps.newHashMap();
                        map1.put("user_id", userInfoEntity.getUser_id());
                        map1.put("nick_name", userInfoEntity.getNick_name());
                        map1.put("avatar", userInfoEntity.getAvatar());
                        objects.add(map1);
                    }
                    map.put("userInfoList", objects);
                    masterMechanismEntity.setMap(map);
                }
            }

        }
        return list;
    }

    @Override
    @Transactional
    public synchronized ResultParam insertMechanismCourse(MechanismClassesEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            param.setIs_scheduling_over(true);
            mapper.updateByPrimaryKeySelective(param);

            boolean reIndex = false;
            MasterAppointmentEntity masterAppointmentEntity2 = new MasterAppointmentEntity();
            masterAppointmentEntity2.setStart_time(param.getStart_date());
            masterAppointmentEntity2.setEnd_time(param.getDate());
            masterAppointmentEntity2.setMechanism_class_id(param.getId());
            List<MasterAppointmentEntity> query = masterAppointmentMapper.query(masterAppointmentEntity2);
            if (query != null && query.size() > 0) {
                for (MasterAppointmentEntity masterAppointmentEntity : query) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
                    userAppointmentEntity.setPageSize(1);
                    userAppointmentMapper.deleteByPrimaryKey(userAppointmentMapper.queryListPage(userAppointmentEntity).get(0).getId());
                    int i = masterAppointmentMapper.deleteByPrimaryKey(masterAppointmentEntity.getId());
                    if (i == 0) {
                        throw new Exception("删除原有课程失败");
                    }
                }
                reIndex = true;
            }

            MechanismClassesEntity mechanismClassesEntity = mapper.selectByPrimaryKey(param.getId());
            MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(mechanismClassesEntity.getMechanism_id());
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(mechanismClassesEntity.getMaster_set_price_id());
//            List<String> titleList = Arrays.asList(masterSetPriceEntity.getTitile_url().split("#$*"));
            List<String> titleList = this.getTitleList(masterSetPriceEntity.getTitile_url());
            List<String> list = Arrays.asList(param.getWeekOfDays().split(","));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(param.getStart_date());
            int count = 0;

            while (calendar.getTime().compareTo(param.getDate()) <= 0 && count < titleList.size()) {
                if (!param.getIs_repeat()) {
                    if (count == list.size()) {
                        break;
                    }
                }
                calendar.add(Calendar.DATE, 1);
                MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                masterAppointmentEntity1.setMaster_set_price_id(masterSetPriceEntity.getId());
                masterAppointmentEntity1.setMechanism_class_id(mechanismClassesEntity.getId());
                masterAppointmentEntity1.setIdentity_type("teach_paypal_course");
                Integer integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity1);
                String title = titleList.get(integer);
                String start = "";
                String end = "";
                if ("week".equalsIgnoreCase(param.getType())) {
                    if (list.contains(this.getWeek(calendar))) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        start = (year + "-" + month + "-" + day + " " + param.getStart_time());
                        end = (year + "-" + month + "-" + day + " " + param.getEnd_time());
                        count++;
                    } else {
                        continue;
                    }
                } else if ("calendar".equalsIgnoreCase(param.getType())) {
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    if (list.contains(day + "")) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        start = (year + "-" + month + "-" + day + " " + param.getStart_time());
                        end = (year + "-" + month + "-" + day + " " + param.getEnd_time());
                        count++;
                    } else {
                        continue;
                    }
                } else {
                    return ResultUtil.error(OrderEum.order_70018.getCode(), OrderEum.order_70018.getDesc());
                }

                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                masterAppointmentEntity.setStart_time(dateformat.parse(start));
                masterAppointmentEntity.setEnd_time(dateformat.parse(end));
                masterAppointmentEntity.setMechanism_id(mechanismClassesEntity.getMechanism_id());
                masterAppointmentEntity.setMaster_set_price_id(mechanismClassesEntity.getMaster_set_price_id());
                masterAppointmentEntity.setMechanism_class_id(param.getId());
                masterAppointmentEntity.setIdentity_type("teach_paypal_course");
                masterAppointmentEntity.setService_type("offline");
                masterAppointmentEntity.setType("mechanism_offline");
                masterAppointmentEntity.setCreate_type("fixed_scheduling");
                masterAppointmentEntity.setLatitude(mechanismEntity.getLatitude());
                masterAppointmentEntity.setLongitude(mechanismEntity.getLongitude());
                masterAppointmentEntity.setMaster_id(param.getMaster_id());
                masterAppointmentEntity.setClassroom(mechanismClassesEntity.getClassroom_name());
                if (param.getStatus()!=null){
                    masterAppointmentEntity.setStatus(param.getStatus());
                }
                if (!reIndex) {
                    masterAppointmentEntity.setNumber_of_lessons((long) integer + 1);
                }
                masterAppointmentEntity.setTitle(title);
                int i = masterAppointmentMapper.insertSelective(masterAppointmentEntity);
                if (i > 0) {
                    UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                    userStudyCardEntity.setMechanism_class_id(mechanismClassesEntity.getId());
                    List<UserStudyCardEntity> list1 = userStudyCardMapper.query(userStudyCardEntity);
                    for (UserStudyCardEntity studyCardEntity : list1) {
                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                        userAppointmentEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
                        userAppointmentEntity.setStart_time(masterAppointmentEntity.getStart_time());
                        userAppointmentEntity.setEnd_time(masterAppointmentEntity.getEnd_time());
                        userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
                        userAppointmentEntity.setMaster_set_price_id(studyCardEntity.getStudycard_id());
                        userAppointmentEntity.setUser_id(studyCardEntity.getUser_id());
                        userAppointmentEntity.setIs_pay(true);
                        userAppointmentEntity.setMaster_type(masterAppointmentEntity.getType());
                        userAppointmentEntity.setStudy_card_id(studyCardEntity.getId());
                        int res = rechargeRecordMapper.updateNumberOfLesson(masterAppointmentEntity.getNumber_of_lessons(),studyCardEntity.getId());
                        if (!reIndex) {
                            userAppointmentEntity.setNumber_of_lessons(masterAppointmentEntity.getNumber_of_lessons());
                        }
                        if (param.getStatus()!=null){
                            userAppointmentEntity.setStatus(param.getStatus());
                        }
                        userAppointmentEntity.setTitle(title);
                        userAppointmentEntity.setLatitude(masterAppointmentEntity.getLatitude());
                        userAppointmentEntity.setLongitude(masterAppointmentEntity.getLongitude());
                        int i1 = userAppointmentMapper.insertSelective(userAppointmentEntity);
                        if (i1 == 0) {
                            throw new Exception("创建用户课程失败");
                        }
                    }
                }
            }
            if (reIndex) {
                List<MasterAppointmentEntity> list1 = masterAppointmentMapper.queryAsc(masterAppointmentEntity2);
                for (int i = 0; i < titleList.size(); i++) {
                    masterAppointmentEntity2 = list1.get(i);
                    masterAppointmentEntity2.setNumber_of_lessons((long) i);
                    masterAppointmentEntity2.setTitle(titleList.get(i));

//                    int i1 = masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity2);
                    int i1 = masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity2);
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(masterAppointmentEntity2.getId());
                    userAppointmentEntity.setNumber_of_lessons(masterAppointmentEntity2.getNumber_of_lessons());
                    userAppointmentEntity.setTitle(masterAppointmentEntity2.getTitle());
                    int i2 = userAppointmentMapper.updateByAppointMentId(userAppointmentEntity);
                }
            }

            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(MasterEum.master_40027.getCode(), MasterEum.master_40027.getDesc());
    }

    private List<String> getTitleList(String title) {
        List<String> arr = Lists.newArrayList();
        StringTokenizer st = new StringTokenizer(title, "#$*");

        while (st.hasMoreTokens()) {
            arr.add(st.nextToken());
        }
        return arr;
    }

    @Override
    @Transactional
    public synchronized ResultParam updateMergerClass(MechanismClassesEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> ids = Arrays.asList(param.getMerger_ids().split(","));
            if (ids.contains(param.getId().toString())){
                return ResultUtil.error(MasterEum.master_40028.getCode(),MasterEum.master_40028.getDesc());
            }
            List<UserStudyCardEntity> userStudyCardEntities = Lists.newArrayList();
            for (String id : ids) {
                UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                userStudyCardEntity.setMechanism_class_id(Long.parseLong(id));
                List<UserStudyCardEntity> query = userStudyCardMapper.query(userStudyCardEntity);
                if (query!=null&&query.size()>0){
                    for (UserStudyCardEntity studyCardEntity : query) {
                        int i = userAppointmentMapper.deleteByStudyCardId(studyCardEntity.getId());
                        studyCardEntity.setMechanism_class_id(param.getId());
                        int i1 = userStudyCardMapper.updateByPrimaryKeySelective(studyCardEntity);
                    }
                    userStudyCardEntities.addAll(query);
                }
                param.setMerger_id(Long.parseLong(id));
                param.setStatus(3);
                int i = mapper.updateMergerClass(param);
                int i1 = masterAppointmentMapper.deleteByMechanismClassId(Long.parseLong(id));
            }
            if (userStudyCardEntities.size() > 0) {
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                masterAppointmentEntity.setMechanism_class_id(param.getId());
                masterAppointmentEntity.setStatus(1);
                List<MasterAppointmentEntity> query = masterAppointmentMapper.query(masterAppointmentEntity);
                if (query != null && query.size() > 0) {
                    for (MasterAppointmentEntity entity : query) {
                        for (UserStudyCardEntity studyCardEntity : userStudyCardEntities) {
                            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                            userAppointmentEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
                            userAppointmentEntity.setStart_time(masterAppointmentEntity.getStart_time());
                            userAppointmentEntity.setEnd_time(masterAppointmentEntity.getEnd_time());
                            userAppointmentEntity.setStatus(2);
                            userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
                            userAppointmentEntity.setMaster_set_price_id(studyCardEntity.getStudycard_id());
                            userAppointmentEntity.setUser_id(studyCardEntity.getUser_id());
                            userAppointmentEntity.setIs_pay(true);
                            userAppointmentEntity.setMaster_type(masterAppointmentEntity.getType());
                            userAppointmentEntity.setStudy_card_id(studyCardEntity.getId());
                            userAppointmentEntity.setTitle(entity.getTitle());
                            userAppointmentEntity.setLatitude(masterAppointmentEntity.getLatitude());
                            userAppointmentEntity.setLongitude(masterAppointmentEntity.getLongitude());
                            int res = rechargeRecordMapper.updateNumberOfLesson(masterAppointmentEntity.getNumber_of_lessons(),studyCardEntity.getId());
                            int i1 = userAppointmentMapper.insertSelective(userAppointmentEntity);
                            if (i1 == 0) {
                                throw new Exception("创建用户课程失败");
                            }
                        }
                    }
                }
            }
            return ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private String getWeek(Calendar c) {
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        switch (weekday) {
            case 1:
                return ("周日");
            case 2:
                return ("周一");
            case 3:
                return ("周二");
            case 4:
                return ("周三");
            case 5:
                return ("周四");
            case 6:
                return ("周五");
            case 7:
                return ("周六");
            default:
                return "";
        }
    }
}