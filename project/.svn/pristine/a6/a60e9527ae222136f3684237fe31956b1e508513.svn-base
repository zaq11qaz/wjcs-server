package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MechanismSchoolDistrictEntity;
import com.huihe.eg.user.service.dao.MechanismSchoolDistrictService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="机构校区表可用接口说明",description="机构校区表可用接口说明",tags = {"机构校区表"})
@RestController
@RequestMapping("mechanismSchoolDistrict")
public class MechanismSchoolDistrictController extends BaseFrameworkController<MechanismSchoolDistrictEntity, Long> {

    @Resource
    private MechanismSchoolDistrictService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}