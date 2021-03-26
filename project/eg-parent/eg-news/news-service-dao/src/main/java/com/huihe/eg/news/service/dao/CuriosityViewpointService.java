package com.huihe.eg.news.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.news.model.CuriosityViewpointEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CuriosityViewpointService extends BaseFrameworkService<CuriosityViewpointEntity, Long> {
    /**
     * 查看收藏的回应
     * zwx
     * 2019年1月15日
     */
    List<CuriosityViewpointEntity> queryCollectCuriosityListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response);
    /**
     * 查询精彩回应
     * zwx
     * 2019年1月15日
     */
    List<CuriosityViewpointEntity> queryHotListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response);
    /**
     * 相关推荐
     * zwx
     * 2019年3月27日16:30:42
     */
    List<CuriosityViewpointEntity> queryRecommendationsListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response);
    /**
     * 查询发布数量
     * @param user_id
     * @return
     */
    Integer queryAddViewpointCount(Long user_id);

    /**
     * 根据id查询观点
     * @param id
     * @return
     */
    String queryViewpointById(Long id);
}