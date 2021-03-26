package com.huihe.eg.news.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.news.model.CuriosityEntity;
import com.huihe.eg.news.model.CuriosityViewpointEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CuriosityViewpointMapper extends BaseFrameworkMapper<CuriosityViewpointEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insert(CuriosityViewpointEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insertSelective(CuriosityViewpointEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    CuriosityViewpointEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKeySelective(CuriosityViewpointEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKey(CuriosityViewpointEntity record);

    /**
     * 查询精彩回应
     * zwx
     * 2019年1月15日
     */
    List<CuriosityViewpointEntity> queryHotListPage(@Param(value = "param")CuriosityViewpointEntity curiosityViewpointEntity);
    /**
     * 查看收藏的回应
     * zwx
     * 2019年1月15日
     */
    List<CuriosityViewpointEntity> queryCollectCuriosityListPage(@Param(value = "param")CuriosityViewpointEntity curiosityViewpointEntity);
    /**
     * 查询回应用户
     * zwx
     * 2019年3月25日14:06:25
     */

    List<Integer> queryViewpointUserListPage(@Param(value = "param")CuriosityViewpointEntity curiosityViewpointEntity);

    /**
     * 增加浏览次数
     * zwx
     * 2019年3月26日17:56:55
     */
    int updateBrowseCount(@Param(value = "param")CuriosityViewpointEntity curiosityViewpointEntity);
    /**
     * 只看图片
     * zwx
     * 2019年3月26日17:56:41
     */
    List<CuriosityViewpointEntity> queryPicListPage(@Param(value = "param")CuriosityViewpointEntity curiosityViewpointEntity);
    /**
     * 相关推荐
     * zwx
     * 2019年3月27日16:30:42
     */
    List<CuriosityViewpointEntity> queryRecommendationsListPage(@Param(value = "param")CuriosityViewpointEntity curiosityViewpointEntity);
    //查询今日发布数量
    Integer queryAddViewpointCount(@Param(value = "param") CuriosityViewpointEntity viewpointEntity);

}