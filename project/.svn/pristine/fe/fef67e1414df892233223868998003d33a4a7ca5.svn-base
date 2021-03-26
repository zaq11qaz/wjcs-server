package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserRatingEntity;
import com.huihe.eg.user.mybatis.dao.UserRatingMapper;
import com.huihe.eg.user.service.dao.UserRatingService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserRatingServiceImpl extends BaseFrameworkServiceImpl<UserRatingEntity, Long> implements UserRatingService {

    @Resource
    private UserRatingMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(UserRatingEntity param, HttpServletRequest request, HttpServletResponse response){
        List<UserRatingEntity> userRatingEntities=null;
        try {
            param.setPageSize(1);
            param.setStatus(1);
            userRatingEntities=mapper.queryListPage(param);
            if(userRatingEntities!=null&&userRatingEntities.size()>0){
                return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("UserRatingServiceImpl   insert");
        }

        return super.insert(param,request,response);
    }
}