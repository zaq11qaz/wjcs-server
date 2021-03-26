package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.user.model.UserDeviceEntity;
import com.huihe.eg.user.mybatis.dao.UserDeviceMapper;
import com.huihe.eg.user.service.dao.UserDeviceService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDeviceServiceImpl extends BaseFrameworkServiceImpl<UserDeviceEntity, Long> implements UserDeviceService {

    @Resource
    private UserDeviceMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public ResultParam insert(UserDeviceEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if(param!=null){
                if(param.getUser_id()!=null&&param.getUser_id()!=0){
                    UserDeviceEntity userDeviceEntity=new UserDeviceEntity();
                    userDeviceEntity.setUser_id(param.getUser_id());
                    userDeviceEntity.setPageSize(1);
                    List<UserDeviceEntity> userDeviceEntities=mapper.queryListPage(userDeviceEntity);
                    if(userDeviceEntities!=null&&userDeviceEntities.size()>0){
                        param.setId(userDeviceEntities.get(0).getId());
                        return super.update(param,request,response);
                    }else{
                        return super.insert(param,request,response);
                    }
                }else{
                    UserDeviceEntity userDeviceEntity=new UserDeviceEntity();
                    userDeviceEntity.setToken(param.getToken());
                    userDeviceEntity.setClientid(param.getClientid());
                    userDeviceEntity.setPageSize(1);
                    List<UserDeviceEntity> userDeviceEntities=mapper.queryListPage(userDeviceEntity);
                    if(userDeviceEntities==null||userDeviceEntities.size()==0){
                        return super.insert(param,request,response);
                    }
                }
            }else {
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public Boolean queryIfExist(UserDeviceEntity param, HttpServletRequest request, HttpServletResponse response) {
        boolean b = false;
        try {
            List<UserDeviceEntity> userDeviceEntities = mapper.query(param);
            if (userDeviceEntities!=null&&userDeviceEntities.size()>0){
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public List<UserDeviceEntity> queryByMessage(UserDeviceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserDeviceEntity> list = null;
        try {
            list = mapper.queryByMessage(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}