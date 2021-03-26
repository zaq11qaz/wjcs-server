package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.message.model.MessageFriendEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MessageFriendService extends BaseFrameworkService<MessageFriendEntity, Long> {

    List<MessageFriendEntity> queryUserFirendInfo(MessageFriendEntity messageFriendEntity, HttpServletRequest request, HttpServletResponse response) ;

}