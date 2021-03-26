package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.model.UserMemberEntity;
import com.huihe.eg.user.model.count.MemberParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserMemberService extends BaseFrameworkService<UserMemberEntity, Long> {
    Map<String,Object> memberCount(UserMemberEntity param);
    List<Map<String,Object>> memberRegister(UserMemberEntity memberEntity,HttpServletRequest request, HttpServletResponse response);
    void membershipExpired();
    void membermonthly();
}