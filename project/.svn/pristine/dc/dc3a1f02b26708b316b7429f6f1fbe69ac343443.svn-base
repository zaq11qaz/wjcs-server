package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MechanismUserEntity;
import com.huihe.eg.user.service.dao.MechanismUserService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:30
 * @ Description：机构新老客户记录
 * @ since: JDk1.8
 */
@Api(value="机构新老客户记录可用接口说明",description="可用接口说明",tags = {"机构新老客户记录"})
@RestController
@RequestMapping("mechanismUser")
public class MechanismUserController extends BaseFrameworkController<MechanismUserEntity, Integer> {

    @Resource
    private MechanismUserService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}