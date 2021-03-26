package com.huihe.eg.news.service.impl;
import com.alibaba.fastjson.JSON;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.news.model.CuriosityCommentEntity;
import com.huihe.eg.news.model.CuriosityEntity;
import com.huihe.eg.news.model.CuriosityViewpointEntity;
import com.huihe.eg.news.model.HistoryEntity;
import com.huihe.eg.news.mybatis.dao.CuriosityCommentMapper;
import com.huihe.eg.news.mybatis.dao.CuriosityMapper;
import com.huihe.eg.news.mybatis.dao.CuriosityViewpointMapper;
import com.huihe.eg.news.mybatis.dao.HistoryMapper;
import com.huihe.eg.news.service.dao.HistoryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import feign.Param;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryServiceImpl extends BaseFrameworkServiceImpl<HistoryEntity, Long> implements HistoryService {

    @Resource
    private HistoryMapper mapper;
    @Resource
    private CuriosityViewpointMapper viewpointMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private CuriosityCommentMapper curiosityCommentMapper;
    @Resource
    private CuriosityMapper curiosityMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(HistoryEntity historyEntity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
         try{
             if(historyEntity.getIdList()!=null&&historyEntity.getIdList().size()>0){
                 for (Long aLong:historyEntity.getIdList()){
                     historyEntity.setHistory_id(aLong);
                     List<HistoryEntity> entities = super.query(historyEntity, request, response);
                     if(entities!=null&&entities.size()>0){
                         HistoryEntity historyEntity1=entities.get(0);
                         if(historyEntity1.getStatus()==2){
                             historyEntity1.setStatus(1);
                         }else if(historyEntity1.getStatus()==1){
                             historyEntity1.setStatus(2);
                         }
                         resultParam=super.update(historyEntity1,request,response);
                         if(null!=resultParam&&resultParam.getCode()==0&&historyEntity1.getStatus()==1){
                             pushOperMessage(historyEntity);
                         }
                         return resultParam;
                     }
                     resultParam=super.insert(historyEntity, request, response);
                     if(null!=resultParam&&resultParam.getCode()==0){
                         pushOperMessage(historyEntity);
                     }
                     return resultParam;
                 }
             }else {
                 List<HistoryEntity> entities = super.query(historyEntity, request, response);
                 if(entities!=null&&entities.size()>0){
                     HistoryEntity historyEntity1=entities.get(0);
                     if(historyEntity1.getStatus()==2){
                         historyEntity1.setStatus(1);
                     }else if(historyEntity1.getStatus()==1){
                         historyEntity1.setStatus(2);
                     }
                     resultParam=super.update(historyEntity1,request,response);
                     if(null!=resultParam&&resultParam.getCode()==0&&historyEntity1.getStatus()==1){
                         pushOperMessage(historyEntity);
                     }
                     return resultParam;
                 }
                 resultParam=super.insert(historyEntity, request, response);
                 if(null!=resultParam&&resultParam.getCode()==0){
                     pushOperMessage(historyEntity);
                 }
                 return resultParam;
             }
         }catch (Exception e){
             e.printStackTrace();
             logger.info("new-History  insert param:"+ JSON.toJSONString(historyEntity).toString());
         }

        return resultParam;
    }

    public void pushOperMessage(HistoryEntity param){
        PushMessageParam pushMessageParam=new PushMessageParam();
        if("".equalsIgnoreCase(param.getOperation_type())&&
                "curiosityviewpoint".equalsIgnoreCase(param.getHistory_type())){
            CuriosityViewpointEntity entity= new CuriosityViewpointEntity();
            entity.setId(param.getHistory_id());
            entity.setPageSize(1);
            List<CuriosityViewpointEntity> list = viewpointMapper.query(entity);
            if(list!=null&&list.size()>0){
                if(list.get(0).getStyle()!=null||list.get(0).getStyle()!=0){
                    pushMessageParam.setView_type("pic");
                }else {
                    pushMessageParam.setView_type("video");
                }
            }
            pushMessageParam.setTarget_id(list.get(0).getUser_id());
            pushMessageParam.setType_id(param.getHistory_id());
        }else if("like".equalsIgnoreCase(param.getOperation_type())&&
                    "curiosityviewpointcomment".equalsIgnoreCase(param.getHistory_type())){
            CuriosityCommentEntity entity=new CuriosityCommentEntity();
            entity.setId(param.getHistory_id());
            entity.setPageSize(1);
            List<CuriosityCommentEntity> commentEntities=curiosityCommentMapper.query(entity);
            if(commentEntities!=null&&commentEntities.size()>0){
                pushMessageParam.setType_id(commentEntities.get(0).getViewpoint_id());
                CuriosityViewpointEntity entity1= new CuriosityViewpointEntity();
                entity.setId(commentEntities.get(0).getViewpoint_id());
                entity.setPageSize(1);
                List<CuriosityViewpointEntity> list = viewpointMapper.query(entity1);
                if(list.get(0).getStyle()!=null||list.get(0).getStyle()!=0){
                    pushMessageParam.setView_type("pic");
                }else {
                    pushMessageParam.setView_type("video");
                }
            }
            pushMessageParam.setTarget_id(commentEntities.get(0).getUser_id());
        }else if("curiosity".equalsIgnoreCase(param.getOperation_type())&&
                "curiosity".equalsIgnoreCase(param.getHistory_type())){
            CuriosityEntity entity=new CuriosityEntity();
            entity.setId(param.getHistory_id());
            entity.setPageSize(1);
            List<CuriosityEntity> curiosityEntities=curiosityMapper.query(entity);
            pushMessageParam.setTarget_id(curiosityEntities.get(0).getUser_id());
            pushMessageParam.setType_id(param.getHistory_id());
        }

        pushMessageParam.setPush_time(new Date());
        pushMessageParam.setType("curiosity");
        pushMessageParam.setSend_id(param.getUser_id());
        pushMessageParam.setPush_type(param.getOperation_type());
        pushMessageParam.setOpera_type(param.getHistory_type()+param.getOperation_type());
        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
        if(pushMessageParam.getSend_id()!=null&&pushMessageParam.getTarget_id()!=null&& !pushMessageParam.getSend_id().equals(pushMessageParam.getTarget_id())) {
            rabbitTemplate.convertAndSend("addNewsMessages", pushMessageParam);
        }

    }

    /**
     * 查询互动数量
     * @param user_id
     * @return
     */
    @Override
    public Integer queryCuriosityInteraction(Long user_id) {
        HistoryEntity entity=new HistoryEntity();
        entity.setUser_id(user_id);
        Integer curiosCount=mapper.queryCuriosityInteraction(entity);
        return curiosCount;
    }

    /**
     * 根据id查询相关人id
     * @param id
     * @return
     */
    @Override
    public Integer queryHistoryById(Long id) {
        Integer i = mapper.selectByPrimaryKey(id).getHistory_id().intValue();
        return i;
    }
}