package com.huihe.eg.message.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.safe.MD5Util;
import com.cy.framework.util.thread.ThreadPool;
import com.huihe.eg.cloud.feign.UserApiService;
import com.huihe.eg.message.model.GroupRecordingEntity;
import com.huihe.eg.message.model.GroupVideoEntity;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.mybatis.dao.GroupRecordingMapper;
import com.huihe.eg.message.mybatis.dao.GroupVideoMapper;
import com.huihe.eg.message.service.dao.GroupRecordingService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.huihe.eg.message.service.impl.Thread.EditMediaThread;
import com.huihe.eg.message.service.impl.Thread.EstablishRecordThread;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GroupRecordingServiceImpl extends BaseFrameworkServiceImpl<GroupRecordingEntity, Long> implements GroupRecordingService {

    @Resource
    private GroupRecordingMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserApiService userApiService;
    @Resource
    private GroupVideoMapper groupVideoMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public void endCourseNotice( Long appointment_id, HttpServletRequest request, HttpServletResponse response){
        try{
            System.out.println("GroupRecordingServiceImpl   endCourseNotice");
            EditMediaThread messageServive = new EditMediaThread();
            messageServive.init(appointment_id,mapper,groupVideoMapper);
            ThreadPool pool = ThreadPool.getInstance();
            pool.execute(messageServive);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("GroupRecordingServiceImpl   endCourseNotice");
        }
    }
    @Override
    public List<GroupRecordingEntity> queryListPage(GroupRecordingEntity param, HttpServletRequest request, HttpServletResponse response){
        List<GroupRecordingEntity> groupRecordingEntities=null;
        try {
            groupRecordingEntities=mapper.queryListPage(param);
            for (GroupRecordingEntity entity : groupRecordingEntities){
                Map<String,Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id()+"userinfo")));
                String appointmentinfo=userApiService.queryAppointmentInfo(entity.getAppointment_id());
                appointmentinfo = appointmentinfo.replaceAll("null", "\"\"");
                map.put("appointmentinfo", JSONUtils.json2Map(appointmentinfo));
                GroupVideoEntity groupVideoEntity=new GroupVideoEntity();
                groupVideoEntity.setTask_id(entity.getTask_id());
                List<GroupVideoEntity> groupVideoEntities=groupVideoMapper.queryListPage(groupVideoEntity);
                map.put("groupVideoEntities",groupVideoEntities);
                entity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("MessageGroupServiceImpl    queryListPage");
        }
        return groupRecordingEntities;
    }
    @Override
    public void updateWatchCount( Long appointment_id, HttpServletRequest request, HttpServletResponse response){
        try{
            GroupRecordingEntity groupRecordingEntity=new GroupRecordingEntity();
            groupRecordingEntity.setAppointment_id(appointment_id);
            mapper.updateWatchCount(groupRecordingEntity);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("GroupRecordingServiceImpl   endCourseNotice");
        }
    }

    @Override
    public String queryRecordingUrl(Long appointment_id, HttpServletRequest request, HttpServletResponse response) {
        return mapper.queryByAppointmentId(appointment_id);
    }
}