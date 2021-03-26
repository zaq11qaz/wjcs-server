package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.message.model.MasterSetPriceGroupEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MasterSetPriceGroupService extends BaseFrameworkService<MasterSetPriceGroupEntity, Long> {

    Long insertMasterGroup(Long id, String title, Long user_id,String face_url, HttpServletRequest request, HttpServletResponse response);

    String insertEnjoyGroup(Long id, Long group_id, HttpServletRequest request, HttpServletResponse response);
}