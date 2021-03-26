package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.google.common.collect.Lists;
import com.huihe.eg.user.model.ClassCardEntity;
import com.huihe.eg.user.model.MasterNoticeEntity;
import com.huihe.eg.user.model.StudyPriceEntity;
import com.huihe.eg.user.model.UserClassCardEntity;
import com.huihe.eg.user.mybatis.dao.ClassCardMapper;
import com.huihe.eg.user.mybatis.dao.StudyPriceMapper;
import com.huihe.eg.user.mybatis.dao.UserClassCardMapper;
import com.huihe.eg.user.service.dao.ClassCardService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.StudyPriceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassCardServiceImpl extends BaseFrameworkServiceImpl<ClassCardEntity, Long> implements ClassCardService {

    @Resource
    private StudyPriceService studyPriceService;
    @Resource
    private ClassCardMapper mapper;
    @Resource
    private UserClassCardMapper userClassCardMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<ClassCardEntity> queryMyClassCard(ClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<ClassCardEntity> classCardEntities = null;
        try {
            classCardEntities = mapper.queryMyClassCard();
            for (ClassCardEntity classCardEntity : classCardEntities) {
                UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                userClassCardEntity.setUser_id(param.getUser_id());
                userClassCardEntity.setType(classCardEntity.getType());
                if (param.getCurriculum_num()!=null){
                    userClassCardEntity.setCurriculum_num(param.getCurriculum_num());
                }
                if (param.getMinute_num()!=null){
                    userClassCardEntity.setMinute_num(param.getMinute_num());
                }
                userClassCardEntity.setStatus(param.getStatus());
                userClassCardEntity.setCurrentPage(1);
                userClassCardEntity.setPageSize(10);
                List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                if (userClassCardEntities==null||userClassCardEntities.size()<1){
                    UserClassCardEntity userClassCardEntity1 = new UserClassCardEntity();
                    userClassCardEntity1.setUser_id(0L);
                    userClassCardEntity1.setType(classCardEntity.getType());
                    userClassCardEntities = userClassCardMapper.query(userClassCardEntity1);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("userClassCard", userClassCardEntities);
                classCardEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ClassCardServiceImpl   queryMyClassCard");
        }
        return classCardEntities;
    }

    @Override
    public List<ClassCardEntity> queryClassCardList(ClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<ClassCardEntity> classCardEntities = null;
        try {
            classCardEntities = mapper.queryListPage(param);
            for (ClassCardEntity classCardEntity : classCardEntities) {
                StudyPriceEntity studyPriceEntity = new StudyPriceEntity();
                studyPriceEntity.setType(classCardEntity.getType());
                List<StudyPriceEntity> studyPriceEntities = studyPriceService.query(studyPriceEntity, request, response);
                classCardEntity.setStudyPriceEntities(studyPriceEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ClassCardServiceImpl   queryMyClassCard");
        }
        return classCardEntities;
    }

}