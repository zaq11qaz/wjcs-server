package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.model.TimeZoneEntity;
import com.huihe.eg.user.model.UserClassEntity;
import com.huihe.eg.user.mybatis.dao.TimeZoneMapper;
import com.huihe.eg.user.mybatis.dao.UserClassMapper;
import com.huihe.eg.user.service.dao.MasterAppointmentService;
import com.huihe.eg.user.service.dao.UserClassService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserClassServiceImpl extends BaseFrameworkServiceImpl<UserClassEntity, Long> implements UserClassService {

    @Resource
    private UserClassMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private TimeZoneMapper timeZoneMapper;
    @Resource
    private MessageApiService messageApiService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<UserClassEntity> queryUserClassSchedule(UserClassEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserClassEntity> userAppointmentEntities = null;
        try {
            userAppointmentEntities = mapper.queryUserSchedule(param);
            this.setinfo(userAppointmentEntities, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl   queryUserSchedule");
        }
        return userAppointmentEntities;
    }

    @Override
    @Transactional
    public ResultParam insert(UserClassEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserClassEntity userClassEntity = new UserClassEntity();
            userClassEntity.setAppointment_id(param.getAppointment_id());
            userClassEntity.setUser_id(param.getUser_id());
            userClassEntity.setPageSize(1);
            List<UserClassEntity> userClassEntities = mapper.queryListPage(userClassEntity);
            if (userClassEntities == null || userClassEntities.size() < 1) {
                return super.insert(param, request, response);
            } else {
                return super.delete(userClassEntities.get(0).getId(), request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    @Override
    public List<UserClassEntity> queryHistoryListPage(UserClassEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserClassEntity> userClassEntities = null;
        try {
            userClassEntities = mapper.queryHistoryListPage(param);
            if (userClassEntities != null && userClassEntities.size() > 0) {
                for (UserClassEntity entity : userClassEntities) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                    map.put("masterAppointmentEntity", masterAppointmentEntity);
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userClassEntities;
    }

    @Override
    public Map<String, Object> queryHistoryListPageCount(UserClassEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserClassEntity> userAppointmentEntities = mapper.queryUserScheduleHistory(param);
            this.setinfo(userAppointmentEntities, request, response);

            Integer total = mapper.queryUserScheduleHistoryCount(param);
            map.put("rows", userAppointmentEntities);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private void setinfo(List<UserClassEntity> userAppointmentEntities, HttpServletRequest request, HttpServletResponse response) {
        if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
            for (UserClassEntity entity : userAppointmentEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                if (masterAppointmentEntity != null) {
                    String group_info = messageApiService.queryGroupInfo(masterAppointmentEntity.getGroup_id());
                    map.put("groupinfo", JSONObject.parseObject(group_info));
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);
                /*
                TimeZoneEntity timeZoneEntity = new TimeZoneEntity();
                timeZoneEntity.setId(entity.getTimezone_id());
                timeZoneEntity.setPageSize(1);
                List<TimeZoneEntity> timeZoneEntities = timeZoneMapper.queryListPage(timeZoneEntity);
                if (timeZoneEntities != null && timeZoneEntities.size() > 0) {
                    map.put("timezone", timeZoneEntities.get(0));
                }

                 */
                entity.setMap(map);
            }
        }
    }
}