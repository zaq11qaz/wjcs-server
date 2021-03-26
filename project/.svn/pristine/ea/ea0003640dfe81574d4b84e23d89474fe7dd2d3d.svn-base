package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.LoginHistoryEntity;
import com.huihe.eg.user.service.dao.LoginHistoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录统计
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户登录统计可用接口说明",description="用户登录统计可用接口说明",tags = {"用户登录统计"})
@RestController
@RequestMapping("loginHistory")
public class LoginHistoryController extends BaseFrameworkController<LoginHistoryEntity, Long> {

    @Resource
    private LoginHistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}