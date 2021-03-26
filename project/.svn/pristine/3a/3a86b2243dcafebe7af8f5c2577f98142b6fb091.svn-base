package com.huihe.eg.news.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.comm.SumEum;
import com.huihe.eg.news.model.BrowesHistoryEntity;
import com.huihe.eg.news.model.CuriosityClassfiyEntity;
import com.huihe.eg.news.model.HistoryEntity;
import com.huihe.eg.news.mybatis.dao.BrowesHistoryMapper;
import com.huihe.eg.news.service.dao.BrowesHistoryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

@Service
public class BrowesHistoryServiceImpl extends BaseFrameworkServiceImpl<BrowesHistoryEntity, Long> implements BrowesHistoryService {

    @Resource
    private BrowesHistoryMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    /**
     * 添加浏览记录时长
     * @param historyEntity
     * @param request
     * @param response
     * @return
     * 2019年3月9日17:38:43
     * zwx
     */
    @Override
    public ResultParam insert(BrowesHistoryEntity historyEntity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
            List<BrowesHistoryEntity> browesHistoryEntities=mapper.query(historyEntity);
            if(null!=browesHistoryEntities&&browesHistoryEntities.size()>0){
                return mapper.addRecord(historyEntity);
            }
            return super.insert(historyEntity,request,response);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("BrowesHistory  insert");
        }
        return null;
    }
}