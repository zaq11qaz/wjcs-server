package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.LiveApplyEntity;
import com.huihe.eg.user.model.UserAppointmentEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.mybatis.dao.LiveApplyMapper;
import com.huihe.eg.user.service.dao.LiveApplyService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LiveApplyServiceImpl extends BaseFrameworkServiceImpl<LiveApplyEntity, Long> implements LiveApplyService {

    @Resource
    private LiveApplyMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<LiveApplyEntity> queryListPage(LiveApplyEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<LiveApplyEntity> liveApplyEntities=null;
        try {
            liveApplyEntities=mapper.queryListPage(param);
            for (LiveApplyEntity liveApplyEntity:liveApplyEntities){
                Map<String,Object> map=new HashMap<>();
                UserInfoEntity infoEntity=userInfoService.findById(liveApplyEntity.getUser_id(),request,response);
                map.put("userinfo",infoEntity);
                liveApplyEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("LiveApplyServiceImpl queryListPage");
        }
        return liveApplyEntities;
    }
}