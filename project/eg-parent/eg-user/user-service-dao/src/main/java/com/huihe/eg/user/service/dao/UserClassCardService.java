package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserClassCardEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserClassCardService extends BaseFrameworkService<UserClassCardEntity, Long> {

    ResultParam updateDefaultUse(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam AddClass(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam newlyAddedCard(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response);
    Map<String,Object> cardPayCount(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response);
    Map<String, Object> queryByMessage(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserClassCardEntity> queryMyExperience(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response);
}