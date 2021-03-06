package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterMechanismEntity;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MasterMechanismMapper extends BaseFrameworkMapper<MasterMechanismEntity, Long> {
    /**
     * @mbggenerated 2020-06-22
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2020-06-22
     */
    @Override
    int insert(MasterMechanismEntity record);

    /**
     * @mbggenerated 2020-06-22
     */
    @Override
    int insertSelective(MasterMechanismEntity record);

    /**
     * @mbggenerated 2020-06-22
     */
    @Override
    MasterMechanismEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2020-06-22
     */
    @Override
    int updateByPrimaryKeySelective(MasterMechanismEntity record);

    /**
     * @mbggenerated 2020-06-22
     */
    @Override
    int updateByPrimaryKey(MasterMechanismEntity record);

    /**
     *     查询附近机构
     */
    List<MasterMechanismEntity> queryNearbyListPage(@Param("param") MasterMechanismEntity record);//查询助学师id

    /**
     *     //条件查询
     * @param param
     * @return
     */
    List<MasterMechanismEntity> queryByMessage(@Param("param") MasterMechanismEntity param);

    /**
     *     //英语总数
     * @param param
     * @return
     */
    Integer queryEnglishCount(@Param("param") MasterMechanismEntity param);

    /**
     *     //日语总数
      * @param param
     * @return
     */
    Integer queryJapaneseCount(@Param("param") MasterMechanismEntity param);

    /**
     *     //法语总数
      * @param param
     * @return
     */
    Integer queryFrenchCount(@Param("param") MasterMechanismEntity param);

    /**
     *     //月新增总数
      * @param masterMechanismEntity
     * @return
     */
    Integer queryMonthNewlyAdded(@Param("param") MasterMechanismEntity masterMechanismEntity);

    /**
     *     //月新增列表
      * @param masterMechanismEntity
     * @return
     */
    List<MasterMechanismEntity> queryMonthNewlyAddedList(@Param("param") MasterMechanismEntity masterMechanismEntity);

    /**
     * 查询预留信息
     * @param param
     * @return
     */
    List<MasterMechanismEntity> queryAppointmentMechanismList(@Param("param") MasterMechanismEntity param);


    List<MasterMechanismEntity> query2And5List(@Param("param") MasterMechanismEntity param);

    void updateCash(@Param("param") MasterMechanismEntity masterMechanismEntity);

    Integer queryAppointmentMechanismListCount(@Param("param") MasterMechanismEntity param);

    Integer query2And5ListCount(@Param("param") MasterMechanismEntity param);

    Integer queryByMessageCount(@Param("param") MasterMechanismEntity param);

    List<MasterMechanismEntity> queryMechanismList(@Param("param") MasterMechanismEntity entity, HttpServletRequest request, HttpServletResponse response);

    int updateStreamId(@Param("param") MasterMechanismEntity mechanismEntity);

    List<MasterMechanismEntity> queryMechanismActivityList(@Param("split") String[] split);
}