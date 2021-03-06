package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.message.model.NoteCommentEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public interface NoteCommentService extends BaseFrameworkService<NoteCommentEntity, Long> {

    /**
     * 查询发布数量
     * @param user_id
     * @return
     */
    Integer queryCommentAddCount(Long user_id);
    /**
     * 动态详情所有评论查询
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年1月7日
     */

    List<NoteCommentEntity> queryDetailsListPage(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryManagerListPageSystem(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response);
}