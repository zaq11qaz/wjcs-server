package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MechanismSchoolDistrictEntity;
import com.huihe.eg.user.mybatis.dao.MechanismSchoolDistrictMapper;
import com.huihe.eg.user.service.dao.MechanismSchoolDistrictService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MechanismSchoolDistrictServiceImpl extends BaseFrameworkServiceImpl<MechanismSchoolDistrictEntity, Long> implements MechanismSchoolDistrictService {

    @Resource
    private MechanismSchoolDistrictMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}