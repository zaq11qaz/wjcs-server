package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MechanismExamTypeEntity;
import com.huihe.eg.user.service.dao.MechanismExamTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="考试类型可用接口说明",description="考试类型可用接口说明",tags = {"考试类型"})
@RestController
@RequestMapping("mechanismExamType")
public class MechanismExamTypeController extends BaseFrameworkController<MechanismExamTypeEntity, Long> {

    @Resource
    private MechanismExamTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}