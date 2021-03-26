package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterSetPriceStreamEntity;
import com.huihe.eg.user.model.MasterSetPriceStreamEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MasterSetPriceStreamService extends BaseFrameworkService<MasterSetPriceStreamEntity, Long> {
    List<MasterSetPriceStreamEntity> queryMasterSetPriceListPage(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response);

    MasterSetPriceStreamEntity queryIsLiveStream(Long user_id, Long id);

    String queryIsLiveStreamClose(Long id);

    ResultParam updateOpenLiving(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateCloseLiving(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryDetail(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateAddClick(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryHomeListPage(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response);
}