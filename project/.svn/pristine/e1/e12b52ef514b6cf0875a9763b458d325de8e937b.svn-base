package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.StudyCardEntity;
import com.huihe.eg.user.model.StudyPriceEntity;
import com.huihe.eg.user.model.UserStudyCardEntity;
import com.huihe.eg.user.mybatis.dao.StudyCardMapper;
import com.huihe.eg.user.mybatis.dao.UserStudyCardMapper;
import com.huihe.eg.user.service.dao.StudyCardService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huihe.eg.user.service.dao.StudyPriceService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudyCardServiceImpl extends BaseFrameworkServiceImpl<StudyCardEntity, Long> implements StudyCardService {

    @Resource
    private StudyCardMapper mapper;
    @Resource
    private StudyPriceService studyPriceService;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public List<StudyCardEntity> query(StudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<StudyCardEntity> studyCardEntities=null;
        try {
            studyCardEntities=mapper.query(param);
            for (StudyCardEntity studyCardEntity: studyCardEntities){
                StudyPriceEntity studyPriceEntity= new StudyPriceEntity();
                studyPriceEntity.setType(studyCardEntity.getType());
                studyPriceEntity.setStatus(1);
                List<StudyPriceEntity> studyPriceEntities=studyPriceService.query(studyPriceEntity,request,response);
                studyCardEntity.setStudyPriceEntities(studyPriceEntities);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("StudyCardServiceImpl  query");
        }
        return studyCardEntities;
    }
    @Override
    public List<StudyCardEntity> queryMyStudyCard(StudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<StudyCardEntity> studyCardEntities=null;
        try {
            studyCardEntities=mapper.query(param);
            for (StudyCardEntity studyCardEntity: studyCardEntities){
                Map<String,Object> map=new HashMap<>();
                UserStudyCardEntity cardEntity=new UserStudyCardEntity();
                cardEntity.setUser_id(param.getUser_id());
                cardEntity.setType(studyCardEntity.getType());
                List<UserStudyCardEntity> userStudyCardEntities=userStudyCardMapper.queryListPage(cardEntity);
                map.put("userStudyCardEntities",userStudyCardEntities);
                studyCardEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("StudyCardServiceImpl  query");
        }
        return studyCardEntities;
    }

    @Override
    public  Map<String,Object> queryStudyCardList(StudyCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = Maps.newHashMap();
        try {
            List<StudyCardEntity> studyCardEntities = mapper.queryListPage(param);
            Integer total = mapper.queryListPageCount(param);
            for (StudyCardEntity studyCardEntity: studyCardEntities){
                StudyPriceEntity studyPriceEntity= new StudyPriceEntity();
                studyPriceEntity.setType(studyCardEntity.getType());
                List<StudyPriceEntity> studyPriceEntities=studyPriceService.query(studyPriceEntity,request,response);
                studyCardEntity.setStudyPriceEntities(studyPriceEntities);
            }
            map.put("rows",studyCardEntities);
            map.put("total", total);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("StudyCardServiceImpl  query");
        }
        return map;
    }

}