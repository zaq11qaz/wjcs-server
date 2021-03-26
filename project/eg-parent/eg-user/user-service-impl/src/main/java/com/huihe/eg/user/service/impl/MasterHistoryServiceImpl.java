package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterHistoryEntity;
import com.huihe.eg.user.mybatis.dao.MasterHistoryMapper;
import com.huihe.eg.user.service.dao.MasterHistoryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MasterHistoryServiceImpl extends BaseFrameworkServiceImpl<MasterHistoryEntity, Long> implements MasterHistoryService {

    @Resource
    private MasterHistoryMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public ResultParam insert(MasterHistoryEntity historyEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MasterHistoryEntity> entities = super.query(historyEntity, request, response);
            if(entities!=null&&entities.size()>0){
                MasterHistoryEntity masterHistoryEntity=entities.get(0);
                if(masterHistoryEntity.getStatus()==1){
                    masterHistoryEntity.setStatus(2);
                }else{
                    masterHistoryEntity.setStatus(1);
                }
                return super.update(masterHistoryEntity,request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.insert(historyEntity,request,response);
    }
}