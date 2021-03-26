package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.message.model.NoteCommentEntity;
import com.huihe.eg.message.model.NoteUserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface NoteUserService extends BaseFrameworkService<NoteUserEntity, Long> {

    /**
     * 查询发布数量
     * @param user_id
     * @return
     */
     Integer queryNoteAddCount(Long user_id);

    /**
     * 分页查询收藏类别
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
     List<NoteUserEntity> queryCollectListPage(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response);
    //查询友圈信息
     String queryNoteInfo(Long note_id,HttpServletRequest request, HttpServletResponse response);



}