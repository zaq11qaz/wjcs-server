package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserContactsEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserContactsService extends BaseFrameworkService<UserContactsEntity, Long> {

    /**
     * 发送邀请
     * @param param
     * @param request
     * @param response
     * @return
     */
     ResultParam sendInvitation(UserContactsEntity param, HttpServletRequest request, HttpServletResponse response) ;

}