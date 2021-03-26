package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.message.model.MessageJoinGroupEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MessageJoinGroupService extends BaseFrameworkService<MessageJoinGroupEntity, Long> {
    List<Long> queryGroupUserInfos(Long group_id);
    ResultParam updateStatus(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MessageJoinGroupEntity> queryMyListPage(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response);
    Map<String,Object> queryOnlineCount(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response);
}