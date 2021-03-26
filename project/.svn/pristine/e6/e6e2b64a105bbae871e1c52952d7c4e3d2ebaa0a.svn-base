package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.MessageGroupEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageGroupMapper extends BaseFrameworkMapper<MessageGroupEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insert(MessageGroupEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(MessageGroupEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    MessageGroupEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(MessageGroupEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(MessageGroupEntity record);
    /**
     *
     * @mbggenerated 2019-01-05
     */
    int updateLiveDuration(@Param("param") MessageGroupEntity record);
    /**
     *
     * @mbggenerated 2019-01-05
     */
    int updateWatchDuration(@Param("param") MessageGroupEntity record);
    /**
     * 分页查询收藏列表
     * @param var1
     * @return
     */
    List<MessageGroupEntity> queryMyListPage(@Param("param") MessageGroupEntity var1);
    /**
     *
     * @mbggenerated 2019-01-05
     */
    Integer queryMyClassCount(@Param("param") MessageGroupEntity record);
    //查询生活娱乐直播
    List<MessageGroupEntity> queryLifeListPage(@Param("param") MessageGroupEntity var1);


    //日课程
    Integer queryDayCount(@Param("param")MessageGroupEntity entity);
    //周课程
    Integer queryWeekCount(@Param("param")MessageGroupEntity entity);
    //月课程
    Integer queryMonthCount(@Param("param")MessageGroupEntity entity);
    //总课程
    Integer queryCountTotal(@Param("param") MessageGroupEntity entity);

    //即将开课
    Integer querySoonCount(@Param("param") MessageGroupEntity param);
    //未上课
    Integer queryEndClassCount(@Param("param") MessageGroupEntity param);
    //条件查询
    List<MessageGroupEntity> queryByMessage(@Param("param") MessageGroupEntity param);
}
