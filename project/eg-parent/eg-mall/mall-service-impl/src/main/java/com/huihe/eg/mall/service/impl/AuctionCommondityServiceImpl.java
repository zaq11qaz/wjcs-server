package com.huihe.eg.mall.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.mall.model.AuctionCommondityEntity;
import com.huihe.eg.mall.model.CommodityInfoEntity;
import com.huihe.eg.mall.model.MallTypeEntity;
import com.huihe.eg.mall.mybatis.dao.AuctionCommondityMapper;
import com.huihe.eg.mall.service.dao.AuctionCommondityService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.mall.service.dao.CommodityInfoService;
import com.huihe.eg.mall.service.dao.MallTypeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuctionCommondityServiceImpl extends BaseFrameworkServiceImpl<AuctionCommondityEntity, Long> implements AuctionCommondityService {

    @Resource
    private AuctionCommondityMapper mapper;
    @Resource
    private CommodityInfoService commodityInfoService;
    @Resource
    private MallTypeService mallTypeService;
    public void init() {
        setMapper(mapper);
    }
    /**
     * 分页查询
     *
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年9月10日
     */
    @Override
    public List<AuctionCommondityEntity> queryListPage(AuctionCommondityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<AuctionCommondityEntity> auctionCommondityEntities=null;
        try {
            auctionCommondityEntities=mapper.queryListPage(param);
            for (AuctionCommondityEntity entity:auctionCommondityEntities){
                Map<String,Object> map = new HashMap<>();
                CommodityInfoEntity commodityInfoEntity=commodityInfoService.findById(entity.getCommondity_id(),request,response);
                MallTypeEntity mallTypeEntity=mallTypeService.findById(entity.getType_id(),request,response);
                map.put("typeInfo",mallTypeEntity);
                map.put("commodityInfo",commodityInfoEntity);
                entity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("AuctionCommondityServiceImpl   queryListPage");
        }
        return auctionCommondityEntities;
    }
}