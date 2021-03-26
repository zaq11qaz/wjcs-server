package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.thread.ThreadPool;
import com.huihe.eg.comm.AuthorizationEum;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.model.MessageGroupFlowEntity;
import com.huihe.eg.message.model.NoteUserEntity;
import com.huihe.eg.message.mybatis.dao.MessageGroupFlowMapper;
import com.huihe.eg.message.mybatis.dao.MessageGroupMapper;
import com.huihe.eg.message.service.dao.MessageGroupFlowService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.message.service.dao.callback.MessageCallbackFactoryBean;
import com.huihe.eg.message.service.impl.Thread.MixedFlowThread;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageGroupFlowServiceImpl extends BaseFrameworkServiceImpl<MessageGroupFlowEntity, Long> implements MessageGroupFlowService {

    @Resource
    private MessageGroupFlowMapper mapper;
    @Resource
    private MessageCallbackFactoryBean bean;
    @Resource
    private MessageGroupMapper messageGroupMapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
    @Override
    public ResultParam insert(MessageGroupFlowEntity param, HttpServletRequest request, HttpServletResponse response){
        try {
            MessageGroupFlowEntity messageGroupFlowEntity=new MessageGroupFlowEntity();
            messageGroupFlowEntity.setStatus(1);
            messageGroupFlowEntity.setGroup_id(param.getGroup_id());
            messageGroupFlowEntity.setStream_id(param.getStream_id());
            messageGroupFlowEntity.setPageSize(1);
            List<MessageGroupFlowEntity> messageGroupFlowEntities=mapper.queryListPage(messageGroupFlowEntity);
            if(messageGroupFlowEntities!=null&&messageGroupFlowEntities.size()>0){
                param.setStatus(1);
                param.setId(messageGroupFlowEntities.get(0).getId());
                int ret=mapper.updateByPrimaryKeySelective(param);
                if(ret>0){
                    MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
                    messageGroupEntity.setGroup_id(param.getGroup_id());
                    messageGroupEntity.setPageSize(1);
                    List<MessageGroupEntity> entities = messageGroupMapper.queryListPage(messageGroupEntity);
                    if(entities!=null&&entities.size()>0){
                        if(entities.get(0).getType()!=null&&("realtime_video".equalsIgnoreCase(entities.get(0).getType())|| "realtime_voice".equalsIgnoreCase(entities.get(0).getType()))){
                            MixedFlowThread messageServive = new MixedFlowThread();
                            messageServive.init(messageGroupEntity.getGroup_id(),messageGroupEntity.getType(),"app",this);
                            ThreadPool pool = ThreadPool.getInstance();
                            pool.execute(messageServive);
                        }
                        return ResultUtil.success();
                    }
                }
            }else{
                int ret=mapper.insertSelective(param);
                if(ret>0){
                    MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
                    messageGroupEntity.setGroup_id(param.getGroup_id());
                    messageGroupEntity.setPageSize(1);
                    List<MessageGroupEntity> entities = messageGroupMapper.queryListPage(messageGroupEntity);
                    if(entities!=null&&entities.size()>0){
                        if(entities.get(0).getType()!=null&&("realtime_video".equalsIgnoreCase(entities.get(0).getType())|| "realtime_voice".equalsIgnoreCase(entities.get(0).getType()))){
                            MixedFlowThread messageServive = new MixedFlowThread();
                            messageServive.init(messageGroupEntity.getGroup_id(),messageGroupEntity.getType(),"app",this);
                            ThreadPool pool = ThreadPool.getInstance();
                            pool.execute(messageServive);
                        }
                        return ResultUtil.success();
                    }
                }
            }
        }catch (Exception e){
            logger.info("MessageGroupFlowServiceImpl  insert");
        }
        return ResultUtil.error(AuthorizationEum.authorization_300014.getCode(), AuthorizationEum.authorization_300014  .getDesc());
    }
    @Override
    public List<MessageGroupFlowEntity> getGroupAllFlow(MessageGroupFlowEntity flowEntity){
        return mapper.queryListPage(flowEntity);
    }
}