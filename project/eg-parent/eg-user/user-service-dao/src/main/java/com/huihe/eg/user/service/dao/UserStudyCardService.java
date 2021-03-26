package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserStudyCardEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserStudyCardService extends BaseFrameworkService<UserStudyCardEntity, Long> {
    List<UserStudyCardEntity> queryStudyCore(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryUserByCard(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryStudyEachTime(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryStudyList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryMyStudyCore(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryMyStudyCoreList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryMyExperience(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMyStudyCoreListPage(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryExclusiveCoursesList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryMyStudyCoreNew(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryStudentList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    void queryStudyCardCourse();

    ResultParam queryPayInfo(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserStudyCardEntity> queryIsPayed(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryStudyEachTimeCount(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryStudentInfo(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam UpdateProductInfo(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);
}