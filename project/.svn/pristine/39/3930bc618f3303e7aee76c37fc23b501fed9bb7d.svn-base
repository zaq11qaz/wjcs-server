package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.CurriculumRecordEntity;
import com.huihe.eg.user.mybatis.dao.CurriculumRecordMapper;
import com.huihe.eg.user.service.dao.CurriculumRecordService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CurriculumRecordServiceImpl extends BaseFrameworkServiceImpl<CurriculumRecordEntity, Long> implements CurriculumRecordService {

    @Resource
    private CurriculumRecordMapper mapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public ResultParam insert(CurriculumRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map=new HashMap<>();
        try {
            List<CurriculumRecordEntity> curriculumRecordEntities=mapper.queryIsTimeConflict(param);
            if(curriculumRecordEntities!=null&&curriculumRecordEntities.size()>0){
                map.put("is_conflict",true);
                map.put("curriculumRecordInfos",curriculumRecordEntities);
            }else{
                int ret=mapper.insertSelective(param);
                if(ret>0){
                    map.put("is_conflict",true);
                }else{
                    return ResultUtil.error(ResultEnum.result_1.getCode(),ResultEnum.result_1.getDesc());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("CurriculumRecordServiceImpl   insert");
        }
        return ResultUtil.success(map);
    }

    @Override
    public void insertCurriculum(Long user_id, String type, Long group_id,Integer status){
        try {
            CurriculumRecordEntity curriculumRecordEntity=new CurriculumRecordEntity();
            curriculumRecordEntity.setGroup_id(group_id);
            if(status==1){
                Date dt = new Date();
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(dt);
                rightNow.add(Calendar.MINUTE, 30);
                Date dt1 = rightNow.getTime();
                curriculumRecordEntity.setStart_time(dt);
                curriculumRecordEntity.setEnd_time(dt1);
                curriculumRecordEntity.setType(type);
                curriculumRecordEntity.setUser_id(user_id);
                List<CurriculumRecordEntity> curriculumRecordEntities=mapper.queryIsTimeConflict(curriculumRecordEntity);
                if(curriculumRecordEntities==null||curriculumRecordEntities.size()==0){
                    System.out.println("------1----"+curriculumRecordEntity.getStart_time());
                    mapper.updateCurriculumStatus(curriculumRecordEntity);
                    mapper.insertSelective(curriculumRecordEntity);
                }
            }else{
                 mapper.updateCurriculumStatus(curriculumRecordEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("CurriculumRecordServiceImpl   insert");
        }
    }

}