package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.ClassRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassRecordMapper extends BaseFrameworkMapper<ClassRecordEntity, Long> {
    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int insert(ClassRecordEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int insertSelective(ClassRecordEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    ClassRecordEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKeySelective(ClassRecordEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKey(ClassRecordEntity record);

    /**
     * 等待评论列表
     * 2020年5月8日14:14:28
     * zwx
     * @param entity
     * @return
     */
    List<ClassRecordEntity> queryStayListPage(@Param("param") ClassRecordEntity entity);
    /**
     * 查询历史直播课堂扣费记录
     * 2020年5月14日16:16:35
     * zwx
     * @param entity
     * @return
     */
    List<ClassRecordEntity> queryHistoryListPage(@Param("param")ClassRecordEntity entity);
    /**
     * 查询直播课堂扣费数量
     * 2020年5月14日16:16:35
     * zwx
     * @param entity
     * @return
     */
    Integer queryPayCount(@Param("param")ClassRecordEntity entity);

    //查询本月
    Integer queryThisMonthCount(@Param("param") ClassRecordEntity classRecordEntity);

    List<ClassRecordEntity> queryByMessage(@Param("param") ClassRecordEntity param);

    Integer queryByMessageCount(@Param("param") ClassRecordEntity param);

    int updateFreeMinute(@Param("param") ClassRecordEntity param);
}