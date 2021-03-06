package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.message.model.GroupRecordingEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GroupRecordingService extends BaseFrameworkService<GroupRecordingEntity, Long> {
    void endCourseNotice(Long appointment_id, HttpServletRequest request, HttpServletResponse response);
    void updateWatchCount( Long appointment_id, HttpServletRequest request, HttpServletResponse response);

    String queryRecordingUrl(Long appointment_id, HttpServletRequest request, HttpServletResponse response);

    String queryRecordingUrlTeachPaypal(Long appointment_id, Boolean is_teach_paypal, HttpServletRequest request, HttpServletResponse response);

}