package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.message.model.GroupWhitelistedEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GroupWhitelistedService extends BaseFrameworkService<GroupWhitelistedEntity, Long> {
    ResultParam queryIsAdoptWhitelisted(GroupWhitelistedEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam deleteList(String ids, HttpServletRequest request, HttpServletResponse response);
    List<Long> queryGroupIds(Long user_id);
}