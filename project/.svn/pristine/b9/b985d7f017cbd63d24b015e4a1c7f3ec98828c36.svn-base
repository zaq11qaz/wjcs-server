package com.huihe.eg.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.mall.model.AuctionCommondityEntity;
import com.huihe.eg.mall.model.AuctionRecordEntity;
import com.huihe.eg.mall.mybatis.dao.AuctionCommondityMapper;
import com.huihe.eg.mall.mybatis.dao.AuctionRecordMapper;
import com.huihe.eg.mall.service.dao.AuctionCommondityService;
import com.huihe.eg.mall.service.dao.AuctionRecordService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAEncTSEnc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
public class AuctionRecordServiceImpl extends BaseFrameworkServiceImpl<AuctionRecordEntity, Long> implements AuctionRecordService {

    @Resource
    private AuctionRecordMapper mapper;
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    @Resource
    private AuctionCommondityService auctionCommondityService;
    @Resource
    private AuctionCommondityMapper auctionCommondityMapper;
    @Resource
    private RedisService redisService;
    public void init() {
        setMapper(mapper);
    }
    public synchronized ResultParam auction(AuctionRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        try {
            int ret=mapper.insertSelective(param);
            if(ret>0){
                AuctionCommondityEntity entity=new AuctionCommondityEntity();
                entity.setId(param.getAuction_commondity_id());
                entity.setCurrent_price(param.getOffer_price());
                auctionCommondityService.update(entity,request,response);
                String str=redisService.getStr(param.getAuction_commondity_id().toString()+"auctionCommondity");
                if(str!=null&&str!=""){
                    redisService.remove(param.getAuction_commondity_id().toString()+"auctionCommondity");
                    redisService.set(param.getAuction_commondity_id().toString()+"auctionCommondity", JSONObject.toJSONString(super.findById(param.getId(),request,response)));
                }else{
                    redisService.set(param.getAuction_commondity_id().toString()+"auctionCommondity", JSONObject.toJSONString(super.findById(param.getId(),request,response)));
                }
                simpMessageSendingOperations.convertAndSend("/topic/"+param.getAuction_commondity_id(), param);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info(" AuctionRecordServiceImpl   auction");
        }
        return ResultUtil.success();
    }
    /**
     * 分页查询
     *
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年9月10日
     */
    @Override
    public List<AuctionRecordEntity> queryListPage(AuctionRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        List<AuctionRecordEntity> auctionRecordEntities=null;
        try {
            auctionRecordEntities=mapper.queryListPage(param);
            for (AuctionRecordEntity auctionRecordEntity:auctionRecordEntities){
                Map<String,Object> map=new HashMap<>();
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(auctionRecordEntity.getUser_id() + "malluserinfo")));
                auctionRecordEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("AuctionRecordServiceImpl   queryListPage");
        }
        return auctionRecordEntities;
    }
    @Scheduled(cron="0/10 * * * * ?")
    public void insertESCuriosity(){
        AuctionCommondityEntity entity=new AuctionCommondityEntity();
        entity.setStatus(2);
        List<AuctionCommondityEntity> auctionCommondityEntities=auctionCommondityMapper.query(entity);
        for (AuctionCommondityEntity commondityEntity:auctionCommondityEntities){
            String str=redisService.getStr(commondityEntity.getId().toString()+"auctionCommondity");
            if(str!=null&&str!=""){
                System.out.println(JSONUtils.obj2Json(str));
                AuctionRecordEntity auctionRecordEntity=JSONUtils.json2Obj(str,AuctionRecordEntity.class);
                if(System.currentTimeMillis()-auctionRecordEntity.getCreate_time().getTime()>10000){
                    auctionRecordEntity.setStatus(3);
                    mapper.updateByPrimaryKeySelective(auctionRecordEntity);
                    commondityEntity.setStatus(3);
                    int ret= auctionCommondityMapper.updateByPrimaryKeySelective(commondityEntity);
                    if(ret>0){
                        simpMessageSendingOperations.convertAndSend("/topic/"+auctionRecordEntity.getAuction_commondity_id(), auctionRecordEntity);
                        redisService.remove(commondityEntity.getId().toString()+"auctionCommondity");
                    }
                }
            }
        }
    }
}