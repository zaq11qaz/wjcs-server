package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.authorization.model.ButtonEntity;
import com.huihe.eg.authorization.service.dao.ButtonService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="按钮可用接口说明",description="按钮可用接口说明",tags = {"按钮"})
@RestController
@RequestMapping("button")
public class ButtonController extends BaseFrameworkController<ButtonEntity, Long> {

    @Resource
    private ButtonService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}