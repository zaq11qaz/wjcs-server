package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterCommentEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MasterCommentService extends BaseFrameworkService<MasterCommentEntity, Long> {
    void updateCommentLikeCount(Long id, HttpServletRequest request, HttpServletResponse response);

    void updateCommentLikeCountCancel(Long id, HttpServletRequest request, HttpServletResponse response);

    void updateCommentShareCount(Long id, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismAverageScore(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response);

    List<MasterCommentEntity> queryMechanismList(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryByMessage(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response);
}