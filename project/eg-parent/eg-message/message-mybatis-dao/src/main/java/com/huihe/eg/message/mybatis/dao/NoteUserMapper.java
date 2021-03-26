package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.NoteUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface NoteUserMapper extends BaseFrameworkMapper<NoteUserEntity, Long> {
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
    int insert(NoteUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insertSelective(NoteUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    NoteUserEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKeySelective(NoteUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKey(NoteUserEntity record);

    /**
     *查看+1
     * 2019年1月12日
     * zwx
     */
    int updateClick(Long id);


    /**
     *查询发布数量
     * 2019年1月12日
     * zwx
     */
    int queryNoteAddCount(@Param("param") NoteUserEntity record);

    /**
     * 分页查询收藏列表
     * @param var1
     * @return
     */
    List<NoteUserEntity> queryCollectListPage(@Param("param") NoteUserEntity var1);
}