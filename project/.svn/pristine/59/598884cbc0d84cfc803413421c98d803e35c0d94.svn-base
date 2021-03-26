package com.huihe.eg.grab.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.grab.model.GrabFilterRuleJoinEntity;
import com.huihe.eg.grab.service.dao.GrabFilterRuleJoinService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="加入抓取的规则和任务可用接口说明",description="加入抓取的规则和任务可用接口说明",tags = {"加入抓取的规则和任务"})
@RestController
@RequestMapping("grabFilterRuleJoin")
public class GrabFilterRuleJoinController extends BaseFrameworkController<GrabFilterRuleJoinEntity, Long> {

    @Resource
    private GrabFilterRuleJoinService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}