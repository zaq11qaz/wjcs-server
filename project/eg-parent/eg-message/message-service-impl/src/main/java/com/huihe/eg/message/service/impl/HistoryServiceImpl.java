package com.huihe.eg.message.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.cloud.feign.UserApiService;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.message.model.HistoryEntity;
import com.huihe.eg.message.model.NoteCommentEntity;
import com.huihe.eg.message.model.NoteUserEntity;
import com.huihe.eg.message.mybatis.dao.HistoryMapper;
import com.huihe.eg.message.mybatis.dao.NoteCommentMapper;
import com.huihe.eg.message.mybatis.dao.NoteUserMapper;
import com.huihe.eg.message.service.dao.HistoryService;
import com.huihe.eg.message.service.dao.NoteUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class HistoryServiceImpl extends BaseFrameworkServiceImpl<HistoryEntity, Long> implements HistoryService {

    @Resource
    private HistoryMapper mapper;
    @Resource
    private NoteUserMapper NoteUserMapper;
    @Resource
    private NoteCommentMapper noteCommentMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisService redisService;
    @Resource
    private NoteUserService noteUserService;
    @Resource
    private UserApiService userApiService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(HistoryEntity historyEntity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = null;
        historyEntity.setPageSize(1);
        List<HistoryEntity> entities = mapper.queryListPage(historyEntity);
        if (entities != null && entities.size() > 0) {
            HistoryEntity historyEntity1 = new HistoryEntity();
            historyEntity1.setId(entities.get(0).getId());
            if (entities.get(0).getStatus() == 2) {
                historyEntity1.setStatus(1);
                switch (historyEntity.getHistory_type()) {
                    case "appointment":
                        userApiService.updateLikeCount(historyEntity.getHistory_id());
                        break;
                    case "comment":
                        userApiService.updateCommentLikeCount(historyEntity.getHistory_id());
                }
            } else if (entities.get(0).getStatus() == 1) {
                historyEntity1.setStatus(2);
                switch (historyEntity.getHistory_type()) {
                    case "appointment":
                        userApiService.updateLikeCountCancel(historyEntity.getHistory_id());
                        break;
                    case "comment":
                        userApiService.updateCommentLikeCountCancel(historyEntity.getHistory_id());
                }
            }

            resultParam = super.update(historyEntity1, request, response);
            if (null != resultParam && resultParam.getCode() == 0 && historyEntity1.getStatus() == 1 && !"appointment".equals(historyEntity.getHistory_type())) {
                pushOperMessage(historyEntity, request, response);
            }
            return resultParam;
        }
        resultParam = super.insert(historyEntity, request, response);

        if (null != resultParam && resultParam.getCode() == 0 && !"appointment".equals(historyEntity.getHistory_type())) {
            pushOperMessage(historyEntity, request, response);
        }
        if ("appointment".equals(historyEntity.getHistory_type())) {
            switch (historyEntity.getOperation_type()) {
                case "like":
                    userApiService.updateLikeCount(historyEntity.getHistory_id());
                    break;
                case "share":
                    userApiService.updateShareCount(historyEntity.getHistory_id());
                    break;
            }
        }
        if ("comment".equals(historyEntity.getHistory_type())) {
            switch (historyEntity.getOperation_type()) {
                case "like":
                    userApiService.updateCommentLikeCount(historyEntity.getHistory_id());
                    break;
                case "share":
                    userApiService.updateCommentShareCount(historyEntity.getHistory_id());
                    break;
            }
        }
        return resultParam;
    }


    public void pushOperMessage(HistoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "notelike");
        map.put("new_time", new Date());
        PushMessageParam pushMessageParam = new PushMessageParam();
        if ("like".equalsIgnoreCase(param.getOperation_type()) &&
                "note".equalsIgnoreCase(param.getHistory_type())) {
            NoteUserEntity entity = new NoteUserEntity();
            entity.setId(param.getHistory_id());
            entity.setPageSize(1);
            List<NoteUserEntity> list = noteUserService.query(entity, request, response);
            pushMessageParam.setTarget_id(list.get(0).getUser_id());
            //友圈新消息
            if (param.getUser_id() != null && param.getUser_id() != 0) {
                map.put("noteinfo", list.get(0));
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(param.getUser_id() + "userinfo")));
                String str = redisService.getStr(list.get(0).getUser_id() + "notenews");
                if (StringUtil.isNotEmpty(str)) {
                    List<String> list1 = JSONUtils.json2List(str, null);
                    list1.add(JSONUtils.obj2Json(map).toString());
                    redisService.set(list.get(0).getUser_id() + "notenews", JSONUtils.list2Json(list1).toString());
                } else {
                    List<String> list1 = new ArrayList<>();
                    list1.add(JSONUtils.obj2Json(map).toString());
                    redisService.set(list.get(0).getUser_id() + "notenews", JSONUtils.list2Json(list1).toString());
                }
            }
        } else if ("like".equalsIgnoreCase(param.getOperation_type()) &&
                "notecomment".equalsIgnoreCase(param.getHistory_type())) {
            NoteCommentEntity entity = new NoteCommentEntity();
            entity.setId(param.getHistory_id());
            entity.setPageSize(1);
            List<NoteCommentEntity> commentEntities = noteCommentMapper.query(entity);
            pushMessageParam.setTarget_id(commentEntities.get(0).getUser_id());
            //友圈新消息
            if (param.getUser_id() != null && param.getUser_id() != 0) {
                NoteUserEntity noteUserEntity = new NoteUserEntity();
                noteUserEntity.setId(commentEntities.get(0).getNote_id());
                noteUserEntity.setPageSize(1);
                List<NoteUserEntity> list = NoteUserMapper.query(noteUserEntity);
                map.put("noteinfo", list.get(0));
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(param.getUser_id() + "userinfo")));
                String str = redisService.getStr(commentEntities.get(0).getUser_id() + "notenews");
                if (StringUtil.isNotEmpty(str)) {
                    List<String> list1 = JSONUtils.json2List(str, null);
                    list1.add(JSONUtils.obj2Json(map).toString());
                    redisService.set(commentEntities.get(0).getUser_id() + "notenews", JSONUtils.list2Json(list1).toString());
                } else {
                    List<String> list1 = new ArrayList<>();
                    list1.add(JSONUtils.obj2Json(map).toString());
                    redisService.set(commentEntities.get(0).getUser_id() + "notenews", JSONUtils.list2Json(list1).toString());
                }
            }
        }
        if ("like".equalsIgnoreCase(param.getOperation_type())) {
            pushMessageParam.setPush_time(new Date());
            pushMessageParam.setType("note");
            pushMessageParam.setSend_id(param.getUser_id());
            pushMessageParam.setType_id(param.getHistory_id());
            pushMessageParam.setOpera_type(param.getHistory_type());
            pushMessageParam.setPush_type(param.getOperation_type());
            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
        }

    }


    /**
     * 查询互动
     *
     * @param user_id
     * @return
     */
    @Override
    public Integer queryNoteInteraction(Long user_id) {
        HistoryEntity entity = new HistoryEntity();
        entity.setUser_id(user_id);
        Map<String, Object> map = new HashMap<>();
        return mapper.queryNoteInteraction(entity);
    }

    @Override
    public Integer queryHistoryId(Long id) {
        HistoryEntity historyEntity = mapper.selectByPrimaryKey(id);
        return historyEntity.getHistory_id().intValue();
    }

    @Override
    public Boolean queryIsLike(Long user_id, Long app_id) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setUser_id(user_id);
        historyEntity.setHistory_id(app_id);
        historyEntity.setStatus(1);
        historyEntity.setOperation_type("like");
        historyEntity.setPageSize(1);
        List<HistoryEntity> historyEntities = mapper.queryListPage(historyEntity);
        return historyEntities != null && historyEntities.size() > 0;
    }

    /**
     * 分页查询
     *
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年1月7日
     */
    @Override
    public List<HistoryEntity> queryListPage(HistoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<HistoryEntity> historyEntities = null;
        try {
            historyEntities = mapper.queryListPage(param);
            for (HistoryEntity entity : historyEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historyEntities;
    }
}