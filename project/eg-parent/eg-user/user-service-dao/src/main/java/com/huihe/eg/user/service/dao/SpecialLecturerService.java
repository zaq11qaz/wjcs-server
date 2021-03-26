package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.SpecialLecturerEntity;
import com.huihe.eg.user.model.UserAppointmentEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SpecialLecturerService extends BaseFrameworkService<SpecialLecturerEntity, Long> {
    ResultParam updateCancel(SpecialLecturerEntity param, HttpServletRequest request, HttpServletResponse response);
}