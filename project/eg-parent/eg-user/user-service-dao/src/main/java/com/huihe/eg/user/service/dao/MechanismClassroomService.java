package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.MechanismClassroomEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MechanismClassroomService extends BaseFrameworkService<MechanismClassroomEntity, Long> {
    List<MechanismClassroomEntity> queryClassroomUnused(MechanismClassroomEntity mechanismClassroomEntity, HttpServletRequest request, HttpServletResponse response);
}