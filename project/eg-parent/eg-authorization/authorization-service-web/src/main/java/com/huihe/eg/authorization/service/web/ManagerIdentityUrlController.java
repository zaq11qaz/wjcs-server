package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.authorization.model.ManagerIdentityUrlEntity;
import com.huihe.eg.authorization.service.dao.ManagerIdentityUrlService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="身份权限关联url可用接口说明",description="身份权限关联url可用接口说明",tags = {"身份权限关联url可用接口说明"})
@RestController
@RequestMapping("managerIdentityUrl")
public class ManagerIdentityUrlController extends BaseFrameworkController<ManagerIdentityUrlEntity, Long> {

    @Resource
    private ManagerIdentityUrlService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}