package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.google.common.collect.Lists;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.cloud.feign.NewsApiService;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.UserGoldTypeService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserGoldTypeServiceImpl extends BaseFrameworkServiceImpl<UserGoldTypeEntity, Long> implements UserGoldTypeService {

    @Resource
    private UserGoldTypeMapper mapper;
    @Resource
    private UserIdentityMapper userIdentityMapper;
    @Resource
    private NewsApiService newsApiService;
    @Resource
    private MessageApiService messageApiService;

    @Resource
    private OverseasIdentityMapper overseasIdentityMapper;
    @Resource
    private UserOrderMapper orderMapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<UserGoldTypeEntity> query(UserGoldTypeEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserGoldTypeEntity> typeEntities=null;
        try {
            typeEntities=mapper.query(param);
            if(typeEntities!=null&&typeEntities.size()>0){
                for (UserGoldTypeEntity userGoldTypeEntity:typeEntities){
                    if("invitatio".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        userGoldTypeEntity.setCanbeled_count(count);
                        userGoldTypeEntity.setCompleted_frequency(0);
                        userGoldTypeEntity.setIs_complete(false);
                    }
                    if("realname_authentication".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            //实名认证
                            UserIdentityEntity userIdentityEntity=new UserIdentityEntity();
                            userIdentityEntity.setUser_id(param.getOper_id());
                            userIdentityEntity.setStatus(1);
                            userIdentityEntity.setPageSize(1);
                            List<UserIdentityEntity> userIdentityEntities=userIdentityMapper.query(userIdentityEntity);
                            if(userIdentityEntities!=null&&userIdentityEntities.size()>0){
                                userGoldTypeEntity.setCompleted_frequency(1);
                                userGoldTypeEntity.setIs_complete(true);
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }

                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setStatus(2);
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }
                    if("abroad_authentication".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            //境外认证
                            OverseasIdentityEntity identityEntity=new OverseasIdentityEntity();
                            identityEntity.setUser_id(param.getOper_id());
                            identityEntity.setStatus(1);
                            identityEntity.setPageSize(1);
                            List<OverseasIdentityEntity> userIdentityEntities=overseasIdentityMapper.query(identityEntity);
                            if(userIdentityEntities!=null&&userIdentityEntities.size()>0){
                                userGoldTypeEntity.setCompleted_frequency(1);
                                userGoldTypeEntity.setIs_complete(true);
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }
                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }
                    if("curious_interaction".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            Integer curiosInteraction=newsApiService.queryCuriosityInteraction(param.getOper_id());
                            if(curiosInteraction!=null&&curiosInteraction>0){
                                if(curiosInteraction<userGoldTypeEntity.getFrequency()){
                                    userGoldTypeEntity.setCompleted_frequency(curiosInteraction);
                                    userGoldTypeEntity.setIs_complete(false);
                                }else{
                                    userGoldTypeEntity.setCompleted_frequency(userGoldTypeEntity.getFrequency());
                                    userGoldTypeEntity.setIs_complete(true);
                                }
                                //查询可领取得次数
                                UserOrderEntity entity=new UserOrderEntity();
                                entity.setPayment_id(param.getOper_id());
                                entity.setType("task");
                                entity.setType_id(userGoldTypeEntity.getId());
                                List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                                Integer count=0;
                                if(userOrderEntities!=null&&userOrderEntities.size()>0){
                                    count=userOrderEntities.size();
                                }
                                if(userGoldTypeEntity.getCompleted_frequency() > count){
                                    Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                                    userGoldTypeEntity.setCanbeled_count(num);
                                }else{
                                    userGoldTypeEntity.setCanbeled_count(0);
                                }
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }
                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }

                    if("release_curious".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            Integer curiosCount=newsApiService.queryAddCount(param.getOper_id());
                            if(curiosCount!=null&&curiosCount>0){
                                if(curiosCount<userGoldTypeEntity.getFrequency()){
                                    userGoldTypeEntity.setCompleted_frequency(curiosCount);
                                    userGoldTypeEntity.setIs_complete(false);
                                }else{
                                    userGoldTypeEntity.setCompleted_frequency(userGoldTypeEntity.getFrequency());
                                    userGoldTypeEntity.setIs_complete(true);
                                }
                                //查询可领取得次数
                                UserOrderEntity entity=new UserOrderEntity();
                                entity.setPayment_id(param.getOper_id());
                                entity.setType("task");
                                entity.setType_id(userGoldTypeEntity.getId());
                                List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                                Integer count=0;
                                if(userOrderEntities!=null&&userOrderEntities.size()>0){
                                    count=userOrderEntities.size();
                                }
                                if(userGoldTypeEntity.getCompleted_frequency() > count){
                                    Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                                    userGoldTypeEntity.setCanbeled_count(num);
                                }else{
                                    userGoldTypeEntity.setCanbeled_count(0);
                                }
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }
                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }
                    if("curious_viewpoint".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            Integer viewpointCount=newsApiService.queryAddViewpointCount(param.getOper_id());
                            if(viewpointCount!=null&&viewpointCount>0){
                                if(viewpointCount<userGoldTypeEntity.getFrequency()){
                                    userGoldTypeEntity.setCompleted_frequency(viewpointCount);
                                    userGoldTypeEntity.setIs_complete(false);
                                }else{
                                    userGoldTypeEntity.setCompleted_frequency(userGoldTypeEntity.getFrequency());
                                    userGoldTypeEntity.setIs_complete(true);
                                }
                                //查询可领取得次数
                                UserOrderEntity entity=new UserOrderEntity();
                                entity.setPayment_id(param.getOper_id());
                                entity.setType("task");
                                entity.setType_id(userGoldTypeEntity.getId());
                                List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                                Integer count=0;
                                if(userOrderEntities!=null&&userOrderEntities.size()>0){
                                    count=userOrderEntities.size();
                                }
                                if(userGoldTypeEntity.getCompleted_frequency() > count){
                                    Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                                    userGoldTypeEntity.setCanbeled_count(num);
                                }else{
                                    userGoldTypeEntity.setCanbeled_count(0);
                                }
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }
                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }
                    if("release_note".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            Integer noteAddCount=messageApiService.queryNoteAddCount(param.getOper_id());
                            if(noteAddCount!=null&&noteAddCount>0){
                                if(noteAddCount<userGoldTypeEntity.getFrequency()){
                                    userGoldTypeEntity.setCompleted_frequency(noteAddCount);
                                    userGoldTypeEntity.setIs_complete(false);
                                }else{
                                    userGoldTypeEntity.setCompleted_frequency(userGoldTypeEntity.getFrequency());
                                    userGoldTypeEntity.setIs_complete(true);
                                }
                                //查询可领取得次数
                                UserOrderEntity entity=new UserOrderEntity();
                                entity.setPayment_id(param.getOper_id());
                                entity.setType("task");
                                entity.setType_id(userGoldTypeEntity.getId());
                                List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                                Integer count=0;
                                if(userOrderEntities!=null&&userOrderEntities.size()>0){
                                    count=userOrderEntities.size();
                                }
                                if(userGoldTypeEntity.getCompleted_frequency() > count){
                                    Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                                    userGoldTypeEntity.setCanbeled_count(num);
                                }else{
                                    userGoldTypeEntity.setCanbeled_count(0);
                                }
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }
                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }
                    if("note_comment".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            Integer noteCommentCount=messageApiService.queryCommentAddCount(param.getOper_id());
                            if(noteCommentCount!=null&&noteCommentCount>0){
                                if(noteCommentCount<userGoldTypeEntity.getFrequency()){
                                    userGoldTypeEntity.setCompleted_frequency(noteCommentCount);
                                    userGoldTypeEntity.setIs_complete(false);
                                }else{
                                    userGoldTypeEntity.setCompleted_frequency(userGoldTypeEntity.getFrequency());
                                    userGoldTypeEntity.setIs_complete(true);
                                }
                                //查询可领取得次数
                                UserOrderEntity entity=new UserOrderEntity();
                                entity.setPayment_id(param.getOper_id());
                                entity.setType("task");
                                entity.setType_id(userGoldTypeEntity.getId());
                                List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                                Integer count=0;
                                if(userOrderEntities!=null&&userOrderEntities.size()>0){
                                    count=userOrderEntities.size();
                                }
                                if(userGoldTypeEntity.getCompleted_frequency() > count){
                                    Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                                    userGoldTypeEntity.setCanbeled_count(num);
                                }else{
                                    userGoldTypeEntity.setCanbeled_count(0);
                                }
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }
                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }
                    if("note_interaction".equalsIgnoreCase(userGoldTypeEntity.getType())){
                        if(param.getOper_id()!=null&&param.getOper_id()!=0){
                            Integer interactionCount=messageApiService.queryNoteInteraction(param.getOper_id());
                            if(interactionCount!=null&&interactionCount>0){
                                if(interactionCount<userGoldTypeEntity.getFrequency()){
                                    userGoldTypeEntity.setCompleted_frequency(interactionCount);
                                    userGoldTypeEntity.setIs_complete(false);
                                }else{
                                    userGoldTypeEntity.setCompleted_frequency(userGoldTypeEntity.getFrequency());
                                    userGoldTypeEntity.setIs_complete(true);
                                }
                                //查询可领取得次数
                                UserOrderEntity entity=new UserOrderEntity();
                                entity.setPayment_id(param.getOper_id());
                                entity.setType("task");
                                entity.setType_id(userGoldTypeEntity.getId());
                                List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                                Integer count=0;
                                if(userOrderEntities!=null&&userOrderEntities.size()>0){
                                    count=userOrderEntities.size();
                                }
                                if(userGoldTypeEntity.getCompleted_frequency() > count){
                                    Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                                    userGoldTypeEntity.setCanbeled_count(num);
                                }else{
                                    userGoldTypeEntity.setCanbeled_count(0);
                                }
                            }else{
                                userGoldTypeEntity.setCompleted_frequency(0);
                                userGoldTypeEntity.setIs_complete(false);
                                userGoldTypeEntity.setCanbeled_count(0);
                            }
                        }else{
                            userGoldTypeEntity.setCompleted_frequency(0);
                            userGoldTypeEntity.setIs_complete(false);
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                        //查询可领取得次数
                        UserOrderEntity entity=new UserOrderEntity();
                        entity.setPayment_id(param.getOper_id());
                        entity.setType("task");
                        entity.setType_id(userGoldTypeEntity.getId());
                        List<UserOrderEntity> userOrderEntities=orderMapper.query(entity);
                        Integer count=0;
                        if(userOrderEntities!=null&&userOrderEntities.size()>0){
                            count=userOrderEntities.size();
                        }
                        if(userGoldTypeEntity.getCompleted_frequency() > count){
                            Integer num=userGoldTypeEntity.getCompleted_frequency()-count;
                            userGoldTypeEntity.setCanbeled_count(num);
                        }else{
                            userGoldTypeEntity.setCanbeled_count(0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  typeEntities;
    }

    @Override
    public List<UserGoldTypeEntity> queryTeachPaypal(UserGoldTypeEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserGoldTypeEntity> list = Lists.newArrayList();
        try {
            list = mapper.query(param);
            UserOrderEntity userOrderEntity = new UserOrderEntity();
            for (UserGoldTypeEntity userGoldTypeEntity : list) {
                userOrderEntity.setPayment_id(param.getOper_id());
                userOrderEntity.setType(param.getType());

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}