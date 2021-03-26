package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.RechargeInfoEntity;
import com.huihe.eg.user.service.dao.RechargeInfoService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：zwy
 * @ Date       ：2020/10/9 17:48
 * @ Description：个人提现信息
 * @since: JDk1.8
 */
@Api(value="个人提现信息可用接口说明",description="个人提现信息可用接口说明",tags = {"个人提现信息"})
@RestController
@RequestMapping("rechargeInfo")
public class RechargeInfoController extends BaseFrameworkController<RechargeInfoEntity, Long> {

    @Resource
    private RechargeInfoService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}