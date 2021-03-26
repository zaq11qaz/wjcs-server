package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.ClassRecordMapper;
import com.huihe.eg.user.mybatis.dao.UserClassCardMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.service.dao.ClassRecordService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.MasterAppointmentService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassRecordServiceImpl extends BaseFrameworkServiceImpl<ClassRecordEntity, Long> implements ClassRecordService {

    @Resource
    private ClassRecordMapper mapper;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private RedisService redisService;
    @Resource
    private UserInfoMapper userInfoMapper;


    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(ClassRecordEntity classRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(classRecordEntity.getUser_id());
        if (userInfoEntity.getWorker_identity() != 0) {
            classRecordEntity.setStatus(4);
        }
        return super.insert(classRecordEntity, request, response);
    }

    @Override
    public ResultParam queryProgressiveGroup(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
            userClassCardEntity.setUser_id(param.getUser_id());
            userClassCardEntity.setStatus(2);
            List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
            LinkedList<UserClassCardEntity> linkedList = Lists.newLinkedList(userClassCardEntities);
            for (UserClassCardEntity userClassCardEntity1 : linkedList) {
                if ("minute".equalsIgnoreCase(userClassCardEntity1.getType())) {
                    if (userClassCardEntity1.getMinute_num() <= 0) {
                        userClassCardEntities.remove(userClassCardEntity1);
                    }
                } else {
                    if (userClassCardEntity1.getCurriculum_num() <= 0) {
                        userClassCardEntities.remove(userClassCardEntity1);
                    }
                }
            }
            /*String group_info=messageApiService.queryGroupInfo(param.getGroup_id());
            map.put("group_info", JSONUtils.obj2Json(group_info));*/
            map.put("card_info", userClassCardEntities);
            //map.put("is_progressive ",aBoolean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ClassRecordServiceImpl");
        }
        return ResultUtil.success(map);
    }

    //等待评论列表
    @Override
    public List<ClassRecordEntity> queryStayListPage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<ClassRecordEntity> classRecordEntities = null;
        try {
            classRecordEntities = mapper.queryStayListPage(param);
            if (classRecordEntities != null && classRecordEntities.size() > 0) {
                for (ClassRecordEntity classRecordEntity : classRecordEntities) {
                    Map<String, Object> map = new HashMap<>();
                    String group_info = messageApiService.queryGroupInfo(classRecordEntity.getGroup_id());
                    map.put("groupinfo", JSONObject.parseObject(group_info));
                    MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(classRecordEntity.getCurriculum_id(), request, response);//直播课堂
                    map.put("masterAppointmentInfo", masterAppointmentEntity);
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getMaster_id() + "userinfo")));
                    classRecordEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ClassRecordServiceImpl");
        }
        return classRecordEntities;
    }

    @Override
    public List<ClassRecordEntity> queryHistoryListPage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<ClassRecordEntity> classRecordEntities = null;
        try {
            classRecordEntities = mapper.queryHistoryListPage(param);
            for (ClassRecordEntity classRecordEntity : classRecordEntities) {
                Map<String, Object> map = new HashMap<>();
                String group_info = messageApiService.queryGroupInfo(classRecordEntity.getGroup_id());
                map.put("groupinfo", JSONObject.parseObject(group_info));
                MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(classRecordEntity.getCurriculum_id(), request, response);//直播课堂评论
                map.put("masterAppointmentInfo", masterAppointmentEntity);
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(classRecordEntity.getUser_id() + "userinfo")));
                classRecordEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ClassRecordServiceImpl");
        }
        return classRecordEntities;
    }

    @Override
    public Map<String, Object> queryByMessage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (param.getNick_name() != null && !"".equals(param.getNick_name())) {
                List<Long> list1 = userInfoMapper.queryIdByNickName(param.getNick_name());
                if (list1 != null && list1.size() > 0) {
                    param.setNickNameIDs(list1);
                }
            }
            List<ClassRecordEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total > 0) {
                setInfoList(list, request, response);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam insertViewRecordings(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getId() != null && param.getId() != 0) {
                int i = mapper.updateFreeMinute(param);
                if (i > 0) {
                    return ResultUtil.success(param.getId());
                }
            } else {
                param.setFree_minute(0);
                int i = mapper.insertSelective(param);
                if (i > 0) {
                    return ResultUtil.success(param.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public List<ClassRecordEntity> queryListPage(ClassRecordEntity classRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        List<ClassRecordEntity> classRecordEntities = mapper.queryListPage(classRecordEntity);
        if (classRecordEntities != null && classRecordEntities.size() > 0) {
            setInfoList(classRecordEntities, request, response);
        }
        return classRecordEntities;
    }

    private void setInfoList(List<ClassRecordEntity> classRecordEntities, HttpServletRequest request, HttpServletResponse response) {
        for (ClassRecordEntity recordEntity : classRecordEntities) {
            Map<String, Object> map = Maps.newHashMap();
            MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(recordEntity.getCurriculum_id(), request, response);//直播课堂评论
            map.put("masterAppointmentInfo", masterAppointmentEntity);
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(recordEntity.getUser_id() + "userinfo")));
            recordEntity.setMap(map);
        }
    }
}