package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MechanismExamEntity;
import com.huihe.eg.user.service.dao.MechanismExamService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="机构考试表可用接口说明",description="机构考试表可用接口说明",tags = {"机构考试表"})
@RestController
@RequestMapping("mechanismExam")
public class MechanismExamController extends BaseFrameworkController<MechanismExamEntity, Long> {

    @Resource
    private MechanismExamService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}