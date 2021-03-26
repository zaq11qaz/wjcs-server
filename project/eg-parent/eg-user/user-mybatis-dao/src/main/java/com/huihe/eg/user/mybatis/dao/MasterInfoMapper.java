package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterInfoEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.model.count.MasterParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MasterInfoMapper extends BaseFrameworkMapper<MasterInfoEntity, Long> {
    /**
     * @mbggenerated 2019-06-12
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-12
     */
    @Override
    int insert(MasterInfoEntity record);

    /**
     * @mbggenerated 2019-06-12
     */
    @Override
    int insertSelective(MasterInfoEntity record);

    /**
     * @mbggenerated 2019-06-12
     */
    @Override
    MasterInfoEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-12
     */
    @Override
    int updateByPrimaryKeySelective(MasterInfoEntity record);

    /**
     * @mbggenerated 2019-06-12
     */
    @Override
    int updateByPrimaryKey(MasterInfoEntity record);

    //查询首页热门助学师
    List<MasterInfoEntity> queryHomeHotListPage(@Param("param") MasterInfoEntity record);

    int updateAddEarnings(@Param("param") MasterInfoEntity record);

    MasterParam queryTodayStatistics();//后台管理-今日提交

    MasterParam queryStatistics();//后台管理-累计

    MasterParam queryWeekStatistics();//后台管理-本周

    MasterParam queryMonthStatistics();//后台管理-本月

    List<MasterInfoEntity> queryMasterAuthListPage(@Param("param") MasterInfoEntity record);

    Integer queryMasterAuthCount(@Param("param") MasterInfoEntity record);

    /**
     * //查询专业/跨境/母语助学师 列表
     * @param record
     * @return
     */
    List<MasterInfoEntity> queryMasterListPage(@Param("param") MasterInfoEntity record);

    List<MasterInfoEntity> queryPrivateListPage(@Param("param") MasterInfoEntity record);//查询私教助学师

    Integer queryMasterListPageCount(@Param("param") MasterInfoEntity record);//查询师资力量

    Integer queryMasterCount(@Param("param") MasterInfoEntity record);//查询外教数量

    List<MasterInfoEntity> queryMasterInfo(@Param("param") MasterInfoEntity record);//查询助学师id

    List<MasterInfoEntity> queryRecommendMasterInfo(@Param("param") MasterInfoEntity record);//查询推荐助学师

    List<MasterInfoEntity> queryByMessage(@Param("param") MasterInfoEntity masterInfoEntity);//根据条件查询

    List<MasterInfoEntity> queryMechanismPrivateListPage(@Param("param") MasterInfoEntity masterInfoEntity);//查询机构助学师

    List<MasterInfoEntity> queryMasterInfoListPage(@Param("param") MasterInfoEntity masterInfoEntity);//查询机构助学师

    List<MasterInfoEntity> queryMasterLoginInfo(@Param("param") MasterInfoEntity masterInfoEntity);

    List<MasterInfoEntity> queryMasterInfoList(@Param("param") MasterInfoEntity masterInfoEntity);

    Integer qeuryHelperCount(@Param("param") MasterInfoEntity masterInfoEntity);

    List<MasterInfoEntity> queryList(@Param("param") MasterInfoEntity masterInfoEntity);//查询状态带6

    //英语人数
    Integer queryEnglishCount(@Param("param") MasterInfoEntity param);

    //法语人数
    Integer queryFrenchCount(@Param("param") MasterInfoEntity param);

    //日语人数
    Integer queryJapaneseCount(@Param("param") MasterInfoEntity param);

    //查询更新
    void updateIsSpecial(@Param("userId") long id);

    //查询机构外教数量
    Integer queryMasterCountTotal(@Param("param") MasterInfoEntity masterInfoEntity);

    //查询邀请外教人数
    List<MasterInfoEntity> queryMasterTotal(@Param("param") MasterInfoEntity masterInfoEntity);

    //更新首页视频
    int updateFaceVideo(@Param("param") MasterInfoEntity masterInfoEntity);

    List<MasterInfoEntity> queryPassMasterListPage(@Param("param") MasterInfoEntity masterInfoEntity);

    List<MasterInfoEntity> queryByFullName(String nick_name);

    List<Long> queryIdByFullName(@Param("full_name") String full_name);

    Integer queryByMessageCount(@Param("param") MasterInfoEntity param);

    void updateMechanismMasterStatus(@Param("param") MasterInfoEntity masterInfoEntity1);

    /**
     * 查询助学师数量
     * @param param
     * @return
     */
    Integer queryMasterCountNum(@Param("param") MasterInfoEntity param);

    /**
     * 查询助学师数量
     * @param masterInfoEntity
     * @return
     */
    Integer queryMasterListCount(@Param("param") MasterInfoEntity masterInfoEntity);

    /**
     * 更新机构
     * @param param
     * @return
     */
    Integer updateMechanismID(@Param("param") MasterInfoEntity param);

    Integer queryMasterInfoListPageCount(@Param("param") MasterInfoEntity masterInfoEntity);

    Integer queryRecommendMasterInfoCount(@Param("param") MasterInfoEntity param);

    Integer queryPrivateListPageCount(@Param("param") MasterInfoEntity param);

    Integer queryMasterListPageCountPC(@Param("param")MasterInfoEntity param);

    List<MasterInfoEntity> queryPrivateListPageWith3(@Param("param") MasterInfoEntity param);

    Integer updateMechanismMasterStatus3(@Param("param") MasterInfoEntity masterInfoEntity1);
}