package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.model.MasterNoticeEntity;
import com.huihe.eg.user.model.UserAppointmentEntity;
import com.huihe.eg.user.mybatis.dao.MasterNoticeMapper;
import com.huihe.eg.user.service.dao.MasterAppointmentService;
import com.huihe.eg.user.service.dao.MasterNoticeService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huihe.eg.user.service.dao.UserAppointmentService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterNoticeServiceImpl extends BaseFrameworkServiceImpl<MasterNoticeEntity, Long> implements MasterNoticeService {

    @Resource
    private MasterNoticeMapper mapper;
    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private RedisService redisService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<MasterNoticeEntity> queryListPage(MasterNoticeEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterNoticeEntity> masterNoticeEntities = null;
        try {
            masterNoticeEntities = mapper.queryListPage(param);
            for (MasterNoticeEntity entity : masterNoticeEntities) {
                Map<String, Object> map = new HashMap<>();
                if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                    UserAppointmentEntity userAppointmentEntities = userAppointmentService.findById(entity.getAppointment_id(), request, response);
                    map.put("userAppointmentInfo", userAppointmentEntities);
                    if (userAppointmentEntities != null && userAppointmentEntities.getUpdate_appointment_id() != null
                            && userAppointmentEntities.getUpdate_appointment_id() != 0) {
                        MasterAppointmentEntity userAppointmentEntity = masterAppointmentService.findById(userAppointmentEntities.getUpdate_appointment_id(), request, response);
                        map.put("updateAppointmentEntity", userAppointmentEntity);
                    }
                }
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterNoticeServiceImpl queryListPage");
        }
        return masterNoticeEntities;
    }

    @Override
    public ResultParam queryIsRead(MasterNoticeEntity param, HttpServletRequest request, HttpServletResponse response) {
        boolean aBoolean = false;
        try {
            List<MasterNoticeEntity> masterNoticeEntities = mapper.queryListPage(param);
            for (MasterNoticeEntity entity : masterNoticeEntities) {
                if (param.getMaster_id() != null && param.getMaster_id() != 0) {
                    if ("masterread".equalsIgnoreCase(entity.getRead_type())) {
                        aBoolean = true;
                    }
                } else if (param.getUser_id() != null && param.getUser_id() != 0) {
                    if ("userread".equalsIgnoreCase(entity.getRead_type())) {
                        aBoolean = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterNoticeServiceImpl queryListPage");
        }
        return ResultUtil.success(aBoolean);
    }

    @Override
    public ResultParam updateIsRead(MasterNoticeEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            /**
             * 如果小班课逻辑
             */
            /*
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setId(param.getAppointment_id());
            masterAppointmentEntity.setPageSize(10);
            List<MasterAppointmentEntity> query = masterAppointmentService.queryListPage(masterAppointmentEntity, request, response);
            if (query!=null&&query.size()>0){
                Integer connect_peoplenum = query.get(0).getConnect_peoplenum();
                if (connect_peoplenum>1&& "master".equals(param.getIdentity())){
                    MasterAppointmentEntity masterAppointmentEntity1 = query.get(0);
                    masterAppointmentEntity1.setStatus(1);
                    masterAppointmentService.update(masterAppointmentEntity1,request,response);
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(param.getAppointment_id());
                    userAppointmentEntity.setStatus(2);
                    List<UserAppointmentEntity> query1 = userAppointmentService.query(userAppointmentEntity, request, response);
                    if (query1!=null&&query1.size()>0){
                        for (UserAppointmentEntity appointmentEntity : query1) {
                            appointmentEntity.setStatus(1);
                            userAppointmentService.update(appointmentEntity,request,response);
                        }
                    }
                }
            }

             */

            int ret = mapper.updateIsRead(param);
            if (ret > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterNoticeServiceImpl queryListPage");
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }
}