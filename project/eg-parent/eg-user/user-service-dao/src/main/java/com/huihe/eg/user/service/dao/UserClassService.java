package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.UserAppointmentEntity;
import com.huihe.eg.user.model.UserClassEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserClassService extends BaseFrameworkService<UserClassEntity, Long> {
    List<UserClassEntity> queryUserClassSchedule(UserClassEntity param, HttpServletRequest request, HttpServletResponse response);
    List<UserClassEntity> queryHistoryListPage(UserClassEntity param ,HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryHistoryListPageCount(UserClassEntity param, HttpServletRequest request, HttpServletResponse response);
}