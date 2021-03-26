package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.google.common.collect.Lists;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.model.MechanismClassroomEntity;
import com.huihe.eg.user.mybatis.dao.MasterAppointmentMapper;
import com.huihe.eg.user.mybatis.dao.MechanismClassroomMapper;
import com.huihe.eg.user.service.dao.MechanismClassroomService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanismClassroomServiceImpl extends BaseFrameworkServiceImpl<MechanismClassroomEntity, Long> implements MechanismClassroomService {

    @Resource
    private MechanismClassroomMapper mapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public synchronized ResultParam insert(MechanismClassroomEntity param, HttpServletRequest request, HttpServletResponse response) {
        MechanismClassroomEntity mechanismClassroomEntity = new MechanismClassroomEntity();
        mechanismClassroomEntity.setMechanism_id(param.getMechanism_id());
        mechanismClassroomEntity.setName(param.getName());
        mechanismClassroomEntity.setPageSize(1);
        List<MechanismClassroomEntity> mechanismClassroomEntities = mapper.queryListPage(mechanismClassroomEntity);
        if (mechanismClassroomEntities != null && mechanismClassroomEntities.size() > 0) {
            param.setId(mechanismClassroomEntities.get(0).getId());
            return super.update(param, request, response);
        }
        return super.insert(param, request, response);
    }

    @Override
    public List<MechanismClassroomEntity> queryClassroomUnused(MechanismClassroomEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MechanismClassroomEntity> list = Lists.newArrayList();
        try {
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setMechanism_id(param.getMechanism_id());
            masterAppointmentEntity.setStart_time(param.getStart_time());
            List<String> stringList = masterAppointmentMapper.queryStartTimeIn(masterAppointmentEntity);
            if (stringList != null && stringList.size() > 0) {
                param.setStringList(stringList);
                List<MechanismClassroomEntity> inList = mapper.queryRoomInList(param);
                if (inList != null && inList.size() > 0) {
                    for (MechanismClassroomEntity mechanismClassroomEntity : inList) {
                        mechanismClassroomEntity.setBeing_used(true);
                    }
                    list.addAll(inList);
                }

                List<MechanismClassroomEntity> notInList = mapper.queryRoomNotInList(param);
                if (notInList != null && notInList.size() > 0) {
                    for (MechanismClassroomEntity mechanismClassroomEntity : notInList) {
                        mechanismClassroomEntity.setBeing_used(true);
                    }
                    list.addAll(notInList);
                }
            } else {
                list = mapper.query(param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}