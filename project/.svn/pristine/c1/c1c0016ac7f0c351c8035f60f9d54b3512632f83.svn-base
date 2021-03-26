package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.message.model.HistoryEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface HistoryService extends BaseFrameworkService<HistoryEntity, Long> {
    /**
     * 查询互动
     * @param user_id
     * @return
     */
    Integer queryNoteInteraction(Long user_id);

    /**
     * 查询操作人id
     * @param id
     * @return
     */
    Integer queryHistoryId(Long id);

    Boolean queryIsLike(Long user_id, Long app_id);
}