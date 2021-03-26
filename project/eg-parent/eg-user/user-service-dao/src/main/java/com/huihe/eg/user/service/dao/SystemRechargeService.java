package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.SystemRechargeEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface SystemRechargeService extends BaseFrameworkService<SystemRechargeEntity, Long> {

    Map<String,Object> queryCatCoinCount(SystemRechargeEntity param, HttpServletRequest request, HttpServletResponse response);

}