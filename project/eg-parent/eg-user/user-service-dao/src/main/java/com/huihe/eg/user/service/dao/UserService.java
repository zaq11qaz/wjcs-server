package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.GenerateQRCodeEntity;
import com.huihe.eg.user.model.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserService extends BaseFrameworkService<UserEntity, Long> {

    ResultParam login(UserEntity entity, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    ResultParam register(UserEntity entity, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    ResultParam insertUserInfo(UserEntity entity, HttpServletRequest request, HttpServletResponse response);

    /**
     * 清空签名
     * @param param
     */
    ResultParam removeSign(UserEntity param);

    void updateUserOnlineState(Map<String,Object> param);
    ResultParam verificationSecurityPass(UserEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam securityPassIsNull(UserEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam openIdLoginOrRegist(UserEntity param,HttpServletRequest request, HttpServletResponse response);
    ResultParam openIdLoginVerification(UserEntity param,HttpServletRequest request, HttpServletResponse response);
    ResultParam openIdVerification(UserEntity param,HttpServletRequest request, HttpServletResponse response);
    ResultParam openIdAppVerification(UserEntity param,HttpServletRequest request, HttpServletResponse response);
    ResultParam appleIdLoginOrRegist(UserEntity param,HttpServletRequest request, HttpServletResponse response);

    ResultParam queryByMessage(UserEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryRegistDayTotal(UserEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryByLoginName(String login_name, HttpServletRequest request, HttpServletResponse response);

    ResultParam setUserSign(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertMechanismMaster(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateMechanismMaster(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryUserInfoByLoginName(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertH5GetCoupon(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryMd5(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryRegistCount(UserEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam generateQRCode(GenerateQRCodeEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam getSessionKey(UserEntity entity, HttpServletRequest request, HttpServletResponse response);

}