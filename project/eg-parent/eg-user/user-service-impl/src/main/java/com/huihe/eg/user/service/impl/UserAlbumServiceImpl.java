package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserAlbumEntity;
import com.huihe.eg.user.mybatis.dao.UserAlbumMapper;
import com.huihe.eg.user.service.dao.UserAlbumService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAlbumServiceImpl extends BaseFrameworkServiceImpl<UserAlbumEntity, Long> implements UserAlbumService {

    @Resource
    private UserAlbumMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public ResultParam insert(UserAlbumEntity param, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        List<UserAlbumEntity> userAlbumEntities=null;
        try {
            UserAlbumEntity userAlbumEntity=new UserAlbumEntity();
            userAlbumEntity.setUser_id(param.getUser_id());
            userAlbumEntities=mapper.query(userAlbumEntity);
            if(userAlbumEntities!=null&&userAlbumEntities.size()>0){
                param.setId(userAlbumEntities.get(0).getId());
                return super.update(param,request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAlbumServiceImpl insert");
        }
        return super.insert(param,request,response);
    }
}