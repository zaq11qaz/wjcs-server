package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserAddressEntity;
import com.huihe.eg.user.mybatis.dao.UserAddressMapper;
import com.huihe.eg.user.service.dao.UserAddressService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl extends BaseFrameworkServiceImpl<UserAddressEntity, Long> implements UserAddressService {

    @Resource
    private UserAddressMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(UserAddressEntity userAddressEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserAddressEntity entity=new UserAddressEntity();
            entity.setUser_id(userAddressEntity.getUser_id());
            entity.setPageSize(1);
            List<UserAddressEntity> userAddressEntities=mapper.queryListPage(userAddressEntity);
            if(userAddressEntities!=null&&userAddressEntities.size()>0){
                userAddressEntity.setId(userAddressEntities.get(0).getId());
                return super.update(userAddressEntity, request, response);
            }else {
                return super.insert(userAddressEntity, request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("UserAddressServiceImpl  userAddressEntity");
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }
}