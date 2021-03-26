package com.huihe.eg.news.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.news.model.CuriosityClassfiyEntity;
import com.huihe.eg.news.model.CuriosityEntity;
import com.huihe.eg.news.mybatis.dao.CuriosityClassfiyMapper;
import com.huihe.eg.news.mybatis.dao.CuriosityMapper;
import com.huihe.eg.news.service.dao.CuriosityClassfiyService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuriosityClassfiyServiceImpl extends BaseFrameworkServiceImpl<CuriosityClassfiyEntity, Long> implements CuriosityClassfiyService {

    @Resource
    private CuriosityClassfiyMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private CuriosityMapper curiosityMapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<CuriosityClassfiyEntity> queryListPage(CuriosityClassfiyEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityClassfiyEntity> classfiyEntities=null;
        Integer gather_count=0;
        try {
            classfiyEntities=mapper.queryListPage(param);
            for (CuriosityClassfiyEntity entity:classfiyEntities){
                CuriosityEntity curiosityEntity=new CuriosityEntity();
                curiosityEntity.setClassfiy(entity.getId());
                gather_count=curiosityMapper.queryGatherCount(curiosityEntity);
                entity.setGather_count(gather_count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classfiyEntities;

    }

}