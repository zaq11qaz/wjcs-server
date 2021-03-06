package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterSetPriceEntity;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MasterSetPriceService extends BaseFrameworkService<MasterSetPriceEntity, Long> {
    Map<String,Object> queryCommodityList(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    void queryInfoListNew(List<MasterSetPriceEntity> list);

    List<MasterSetPriceEntity> queryRecommendList(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCommodityListByType(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

//    List<MasterSetPriceEntity> queryNearByCourse(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterSetPriceEntity> queryNearByCourse(List<MasterSetPriceEntity> list, HttpServletRequest request, HttpServletResponse response);

    List<MasterSetPriceEntity> queryCourseListPage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    Integer queryIsCourse(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryGroupInfo(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateActivityInfo(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    Object queryMasterSetPriceById(Long master_set_price_id);

    List<MasterSetPriceEntity> queryActivityListPage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterSetPriceEntity> queryActivityListPageByType(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterSetPriceEntity> queryMechanismDisplay(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterSetPriceEntity> queryMechanismDisplayIsLiving(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterSetPriceEntity> queryDetails(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertActivityMasterSetPrice(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterSetPriceEntity> queryActivityListPageByActivityId(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response);
}