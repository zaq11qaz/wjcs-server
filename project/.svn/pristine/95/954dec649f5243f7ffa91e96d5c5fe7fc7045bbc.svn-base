package com.huihe.eg.grab.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.grab.model.GrabFilterRuleEntity;
import com.huihe.eg.grab.service.dao.GrabFilterRuleService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="抓取规则可用接口说明",description="抓取规则可用接口说明",tags = {"抓取规则"})
@RestController
@RequestMapping("grabFilterRule")
public class GrabFilterRuleController extends BaseFrameworkController<GrabFilterRuleEntity, Long> {

    @Resource
    private GrabFilterRuleService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}