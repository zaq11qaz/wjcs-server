package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.StudyCardEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StudyCardService extends BaseFrameworkService<StudyCardEntity, Long> {
    List<StudyCardEntity> queryMyStudyCard(StudyCardEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryStudyCardList(StudyCardEntity param, HttpServletRequest request, HttpServletResponse response);
}