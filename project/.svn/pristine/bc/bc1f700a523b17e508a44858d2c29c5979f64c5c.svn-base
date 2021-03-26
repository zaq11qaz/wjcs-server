package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.authorization.model.ManagerIdentityUrlEntity;
import com.huihe.eg.authorization.mybatis.dao.ManagerIdentityUrlMapper;
import com.huihe.eg.authorization.service.dao.ManagerIdentityUrlService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerIdentityUrlServiceImpl extends BaseFrameworkServiceImpl<ManagerIdentityUrlEntity, Long> implements ManagerIdentityUrlService {

    @Resource
    private ManagerIdentityUrlMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(ManagerIdentityUrlEntity managerIdentityUrlEntity, HttpServletRequest request, HttpServletResponse response) {
        List<ManagerIdentityUrlEntity> query = mapper.query(managerIdentityUrlEntity);
        if (query!=null&&query.size()>0){
            managerIdentityUrlEntity.setId(query.get(0).getId());
            return super.update(managerIdentityUrlEntity, request, response);
        }
        return super.insert(managerIdentityUrlEntity, request, response);
    }
}