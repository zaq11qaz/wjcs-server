package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.CurriculumRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CurriculumRecordMapper extends BaseFrameworkMapper<CurriculumRecordEntity, Long> {
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
    int insert(CurriculumRecordEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int insertSelective(CurriculumRecordEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    CurriculumRecordEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKeySelective(CurriculumRecordEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKey(CurriculumRecordEntity record);
    List<CurriculumRecordEntity> queryIsTimeConflict(@Param("param") CurriculumRecordEntity record);
    /**
     *
     * @mbggenerated 2019年10月19日13:54:47
     */
    int updateCurriculumStatus(@Param("param") CurriculumRecordEntity record);

    List<CurriculumRecordEntity> queryExpireClass(@Param("param") CurriculumRecordEntity record);
}