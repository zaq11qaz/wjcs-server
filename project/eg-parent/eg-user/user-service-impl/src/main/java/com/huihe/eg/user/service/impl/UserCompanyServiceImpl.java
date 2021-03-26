package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserCompanyEntity;
import com.huihe.eg.user.mybatis.dao.UserCompanyMapper;
import com.huihe.eg.user.service.dao.UserCompanyService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class UserCompanyServiceImpl extends BaseFrameworkServiceImpl<UserCompanyEntity, Long> implements UserCompanyService {

    @Resource
    private UserCompanyMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public ResultParam insert(UserCompanyEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if(param.getUserCompanyEntities()!=null&&param.getUserCompanyEntities().size()>0){
                for (UserCompanyEntity entity:param.getUserCompanyEntities()){
                    super.insert(entity,request,response);
                }
                return ResultUtil.success();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.insert(param,request,response);
    }
}