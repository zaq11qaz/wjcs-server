package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.UserDeviceEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserDeviceService extends BaseFrameworkService<UserDeviceEntity, Long> {
    Boolean queryIfExist(UserDeviceEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserDeviceEntity> queryByMessage(UserDeviceEntity param, HttpServletRequest request, HttpServletResponse response);
}