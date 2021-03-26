package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserCompanyEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.service.dao.UserCompanyService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户公司信息
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户公司信息可用接口说明",description="用户公司信息可用接口说明",tags = {"用户公司信息"})
@RestController
@RequestMapping("userCompany")
public class UserCompanyController extends BaseFrameworkController<UserCompanyEntity, Long> {

    @Resource
    private UserCompanyService service;
    @Override
    public void init() {
        setBaseService(service);
    }

}