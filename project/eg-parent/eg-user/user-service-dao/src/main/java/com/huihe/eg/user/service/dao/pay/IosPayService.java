package com.huihe.eg.user.service.dao.pay;

import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.pay.IosPayParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IosPayService {

    /**
     * 接收iOS端发过来的购买凭证
     *
     * @param
     */
    ResultParam setIapCertificate(IosPayParam iosPayParam, HttpServletRequest request, HttpServletResponse response);

    ResultParam  updateMember(Long user_id,int member_level,int Monthles);
}
