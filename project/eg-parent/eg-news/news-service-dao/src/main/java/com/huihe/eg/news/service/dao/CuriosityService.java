package com.huihe.eg.news.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.news.model.CuriosityEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface CuriosityService extends BaseFrameworkService<CuriosityEntity, Long> {
    /* 好奇首页
     * @param param
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    List<CuriosityEntity> queryHomeListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询好奇内容(关注)
     *
     * @param param oper_id
     * @return List<CuriosityEntity>
     * 2019年1月15日
     * zwx
     */
    List<CuriosityEntity>  queryCuriosityListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询推荐内容
     *
     * @param param oper_id
     * @return List<CuriosityEntity>
     * 2019年1月16日
     * zwx
     */
    List<CuriosityEntity> queryRecommendCuriosityListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 周排行
     *
     * @param param
     * @return List<CuriosityEntity>
     * 2019年1月16日
     * zwx
     */
    List<CuriosityEntity> queryWeekRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 月排行
     *
     * @param param
     * @return List<CuriosityEntity>
     * 2019年1月16日
     * zwx
     */
    List<CuriosityEntity> queryMonthRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 热门/总排行
     *
     * @param param
     * @return List<CuriosityEntity>
     * 2019年1月16日
     * zwx
     */
    List<CuriosityEntity> queryHotRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 猜你好奇
     *
     * @param param
     * @return List<CuriosityEntity>
     * 2019年1月16日
     * zwx
     */
    List<CuriosityEntity> queryGuessListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 内容审核
     *
     * @param param
     * @return List<CuriosityEntity>
     * 2019年1月16日
     * zwx
     */
    List<CuriosityEntity> queryAuditListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 分类下好奇
     *
     * @Description: 分类下好奇
     * @author zwx
     * @Date : 2019年2月21日15:02:42
     */
    List<CuriosityEntity> queryCuriostyByClassfiyListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询发布数量
     * @param user_id
     * @return
     */
    Integer queryAddCount(Long user_id);

}