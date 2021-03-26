package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.message.model.MessageGroupEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MessageGroupService extends BaseFrameworkService<MessageGroupEntity, Long> {

    void updateGroupDuration(Long group_id,String type) throws Exception;
    ResultParam updateClassBeginsStatus(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MessageGroupEntity> queryMyListPage(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response);
    Integer queryMyClassCount(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryClassCount(MessageGroupEntity param,HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCourseCount(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MessageGroupEntity> queryByMessage(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateLivingBeginsStatus(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response);
}