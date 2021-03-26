package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MemberRecordEntity;
import com.huihe.eg.user.mybatis.dao.MemberRecordMapper;
import com.huihe.eg.user.service.dao.MemberRecordService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberRecordServiceImpl extends BaseFrameworkServiceImpl<MemberRecordEntity, Long> implements MemberRecordService {

    @Resource
    private MemberRecordMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}