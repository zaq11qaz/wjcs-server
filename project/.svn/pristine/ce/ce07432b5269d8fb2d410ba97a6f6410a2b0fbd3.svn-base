package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MechanismExamItemsEntity;
import com.huihe.eg.user.service.dao.MechanismExamItemsService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="考试项目可用接口说明",description="考试项目可用接口说明",tags = {"考试项目"})
@RestController
@RequestMapping("mechanismExamItems")
public class MechanismExamItemsController extends BaseFrameworkController<MechanismExamItemsEntity, Long> {

    @Resource
    private MechanismExamItemsService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}