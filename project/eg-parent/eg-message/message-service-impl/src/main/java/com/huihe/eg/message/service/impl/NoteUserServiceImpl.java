package com.huihe.eg.message.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.cloud.feign.UserApiService;
import com.huihe.eg.comm.NewsEum;
import com.huihe.eg.comm.util.EmojiUtil;
import com.huihe.eg.message.model.HistoryEntity;
import com.huihe.eg.message.model.MessageBlacklistEntity;
import com.huihe.eg.message.model.NoteCommentEntity;
import com.huihe.eg.message.model.NoteUserEntity;
import com.huihe.eg.message.mybatis.dao.HistoryMapper;
import com.huihe.eg.message.mybatis.dao.MessageBlacklistMapper;
import com.huihe.eg.message.mybatis.dao.NoteCommentMapper;
import com.huihe.eg.message.mybatis.dao.NoteUserMapper;
import com.huihe.eg.message.service.dao.NoteCommentService;
import com.huihe.eg.message.service.dao.NoteUserService;
import com.vdurmont.emoji.EmojiParser;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class NoteUserServiceImpl extends BaseFrameworkServiceImpl<NoteUserEntity, Long> implements NoteUserService {

    @Resource
    private NoteUserMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private NoteCommentService noteCommentService;
    @Resource
    private NoteCommentMapper noteCommentMapper;
    @Resource
    private HistoryMapper historyMapper;
    @Resource
    private UserApiService userApiService;
    @Resource
    private MessageBlacklistMapper messageBlacklistMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public Integer queryListPageCount(NoteUserEntity noteUserEntity, HttpServletRequest request, HttpServletResponse response) {
        if (noteUserEntity.getStatus()==null){
            noteUserEntity.setStatus(1);
        }
        return super.queryListPageCount(noteUserEntity, request, response);
    }

    @Override
    public ResultParam update(NoteUserEntity noteUserEntity, HttpServletRequest request, HttpServletResponse response) {
        try {

            if (noteUserEntity.getStatus() != null && noteUserEntity.getStatus() == 5) {
                MessageBlacklistEntity messageBlacklistEntity = new MessageBlacklistEntity();
                messageBlacklistEntity.setUser_id(noteUserEntity.getUser_id());
                messageBlacklistEntity.setBlacklist_status(3);
                int i = messageBlacklistMapper.insertSelective(messageBlacklistEntity);
            }
        } catch (Exception ignored) {

        }

        return super.update(noteUserEntity, request, response);
    }

    @Override
    public ResultParam insert(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        //????????????
		param.setContent(EmojiParser.parseToUnicode(param.getContent()));
        param.setContent(EmojiUtil.resolveToByteFromEmoji(param.getContent()));

        MessageBlacklistEntity messageBlacklistEntity = new MessageBlacklistEntity();
        messageBlacklistEntity.setUser_id(param.getUser_id());
        messageBlacklistEntity.setBlacklist_status(3);
        messageBlacklistEntity.setPageSize(1);
        List<MessageBlacklistEntity> messageBlacklistEntities = messageBlacklistMapper.queryListPage(messageBlacklistEntity);
        if (messageBlacklistEntities != null && messageBlacklistEntities.size() > 0) {
            return ResultUtil.error(NewsEum.news_80010.getCode(), NewsEum.news_80010.getDesc());
        }

        param.setStatus(3);
        ResultParam resultParam = super.insert(param, request, response);
        if (resultParam != null && resultParam.getCode() == 0) {
            param.setUrl("http://www.curiousmore.com:8768/eg-api/push/noteshare.html?note_id=" + param.getId());
            resultParam = super.update(param, request, response);
        }
        return resultParam;
    }

    /**
     * ????????????
     *
     * @Description: ????????????
     * @author zwx
     * @Date : 2019???1???7???
     */
    @Override
    public List<NoteUserEntity> queryListPage(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<NoteUserEntity> noteUserEntities = null;
        try {
            noteUserEntities = mapper.queryListPage(param);
            if (noteUserEntities != null && noteUserEntities.size() > 0) {
                for (NoteUserEntity noteUserEntity : noteUserEntities) {
                    //????????????
                    noteUserEntity.setContent(EmojiParser.parseToUnicode(noteUserEntity.getContent()));
                    Map<String, Object> map = new HashMap<>();
                    Object masterSetPriceEntity = null;
                    if (noteUserEntity.getMaster_set_price_id() != 0) {
                        masterSetPriceEntity = userApiService.queryMasterSetPriceById(noteUserEntity.getMaster_set_price_id());
                    }
                    map.put("masterSetPriceEntity", masterSetPriceEntity);

                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(noteUserEntity.getUser_id() + "userinfo")));//???????????????????????????
                    if (param.getOper_id() != null && param.getOper_id() != 0) {//????????????
                        HistoryEntity historyEntity = new HistoryEntity();
                        historyEntity.setHistory_id(noteUserEntity.getId());
                        historyEntity.setOperation_type("like");
                        historyEntity.setUser_id(param.getOper_id());
                        historyEntity.setHistory_type("note");
                        List<HistoryEntity> list = historyMapper.queryListPage(historyEntity);
                        map.put("is_like", list != null && list.size() > 0);
                    }
                    //??????????????????
                    NoteCommentEntity commentEntity = new NoteCommentEntity();
                    commentEntity.setNote_id(noteUserEntity.getId());
                    int commentCount = noteCommentService.queryListPageCount(commentEntity, request, response);
                    map.put("commentCount", commentCount);
                    //??????????????????
                    List<JSONObject> likeInfo = new ArrayList<>();
                    HistoryEntity history = new HistoryEntity();
                    history.setHistory_id(noteUserEntity.getId());
                    history.setOperation_type("like");
                    history.setHistory_type("note");
                    history.setStatus(1);
                    List<HistoryEntity> list1 = historyMapper.queryListPage(history);
                    if (list1 != null && list1.size() > 0) {
                        for (HistoryEntity entity : list1) {
                            JSONObject string = JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo"));
                            likeInfo.add(string);
                        }
                    }
                    //??????????????????????????????
                    List<Object> list2 = new ArrayList<>();
                    map.put("likeinfo", likeInfo);
                    String str = userApiService.userOrderQuery(noteUserEntity.getUser_id(), noteUserEntity.getId(), "gift", "note");
                    if (StringUtil.isNotEmpty(str)) {
                        List<Long> longs = JSONUtils.json2List(str, Long.class);
                        if (longs != null && longs.size() > 0) {
                            for (Long aLong : longs) {
                                list2.add(JSONUtils.obj2Json(redisService.getStr(aLong + "userinfo")));
                            }
                        }
                    }
                    map.put("giftCount", list2.size());//???????????????
                    Set<Object> set = new HashSet<>(list2);
                    map.put("giftinfo", set);//????????????
                    NoteCommentEntity noteCommentEntity = new NoteCommentEntity();
                    noteCommentEntity.setNote_id(noteUserEntity.getId());
                    noteCommentEntity.setStatus(1);
                    List<NoteCommentEntity> noteCommentEntities = noteCommentService.queryListPage(noteCommentEntity, request, response);
                    map.put("noteCommentEntities", noteCommentEntities);//??????
                    noteUserEntity.setMap(map);
                }
                if (param.getOper_id() != null && param.getOper_id() != 0) {//?????????
                    String noteNews = redisService.getStr(param.getOper_id() + "notenews");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteUserEntities;
    }

    /**
     * ????????????
     *
     * @Description: ????????????
     * @author zwx
     * @Date : 2019???1???7???
     */
    @Override
    public List<NoteUserEntity> query(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<NoteUserEntity> noteUserEntities = null;
        try {
            noteUserEntities = mapper.queryListPage(param);
            for (NoteUserEntity noteUserEntity : noteUserEntities) {
                //????????????
                noteUserEntity.setContent(EmojiParser.parseToUnicode(noteUserEntity.getContent()));

                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(noteUserEntity.getUser_id() + "userinfo")));
                //????????????
                if (param.getOper_id() != null && param.getOper_id() != 0) {
                    HistoryEntity historyEntity = new HistoryEntity();
                    historyEntity.setHistory_id(noteUserEntity.getId());
                    historyEntity.setOperation_type("like");
                    historyEntity.setUser_id(param.getOper_id());
                    historyEntity.setHistory_type("note");
                    List<HistoryEntity> list = historyMapper.queryListPage(historyEntity);
                    map.put("is_like", list != null && list.size() > 0);
                } else {
                    map.put("is_like", false);
                }
                //??????????????????
                List<JSONObject> likeInfo = new ArrayList<>();
                HistoryEntity history = new HistoryEntity();
                history.setHistory_id(noteUserEntity.getId());
                history.setOperation_type("like");
                history.setHistory_type("note");
                history.setStatus(1);
                List<HistoryEntity> list1 = historyMapper.queryListPage(history);
                if (list1 != null && list1.size() > 0) {
                    for (HistoryEntity entity : list1) {
                        JSONObject string = JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo"));
                        likeInfo.add(string);
                    }
                }
                //??????????????????????????????
                List<Object> list2 = new ArrayList<>();
                map.put("likeinfo", likeInfo);
                String str = userApiService.userOrderQuery(noteUserEntity.getUser_id(), noteUserEntity.getId(), "gift", "note");
                if (StringUtil.isNotEmpty(str)) {
                    List<Long> longs = JSONUtils.json2List(str, Long.class);
                    if (longs != null && longs.size() > 0) {
                        for (Long aLong : longs) {
                            list2.add(JSONUtils.obj2Json(redisService.getStr(aLong + "userinfo")));
                        }
                    }
                }
                map.put("giftCount", list2.size());
                Set<Object> set = new HashSet<>(list2);
                map.put("giftinfo", set);
                NoteCommentEntity noteCommentEntity = new NoteCommentEntity();
                noteCommentEntity.setNote_id(noteUserEntity.getId());
                noteCommentEntity.setStatus(1);
                List<NoteCommentEntity> noteCommentEntities = noteCommentService.queryListPage(noteCommentEntity, request, response);
                map.put("noteCommentEntities", noteCommentEntities);
                noteCommentEntity.setParent_id(0L);
                List<NoteCommentEntity> noteComments = noteCommentService.queryDetailsListPage(noteCommentEntity, request, response);
                map.put("noteDetailsComment", noteComments);
                noteUserEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteUserEntities;
    }

    /**
     * ??????????????????
     *
     * @param user_id
     * @return
     */
    @Override
    public Integer queryNoteAddCount(Long user_id) {
        NoteUserEntity noteUserEntity = new NoteUserEntity();
        noteUserEntity.setUser_id(user_id);
        return mapper.queryNoteAddCount(noteUserEntity);
    }

    /**
     * ????????????????????????
     * 2019???5???9???18:06:15
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<NoteUserEntity> queryCollectListPage(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<NoteUserEntity> noteUserEntities = null;
        try {
            noteUserEntities = mapper.queryCollectListPage(param);
            for (NoteUserEntity noteUserEntity : noteUserEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(noteUserEntity.getUser_id() + "userinfo")));
                //????????????
                HistoryEntity historyEntity = new HistoryEntity();
                historyEntity.setHistory_id(noteUserEntity.getId());
                historyEntity.setOperation_type("like");
                historyEntity.setUser_id(param.getOper_id());
                historyEntity.setHistory_type("note");
                List<HistoryEntity> list = historyMapper.queryListPage(historyEntity);
                map.put("is_like", list != null && list.size() > 0);
                //??????????????????
                List<JSONObject> likeInfo = new ArrayList<>();
                HistoryEntity history = new HistoryEntity();
                history.setHistory_id(noteUserEntity.getId());
                history.setOperation_type("like");
                history.setHistory_type("note");
                history.setStatus(1);
                List<HistoryEntity> list1 = historyMapper.queryListPage(history);
                if (list1 != null && list1.size() > 0) {
                    for (HistoryEntity entity : list1) {
                        JSONObject string = JSONUtils.obj2Json(redisService.getStr(noteUserEntity.getUser_id() + "userinfo"));
                        likeInfo.add(string);
                    }
                }
                //??????????????????????????????
                List<Object> list2 = new ArrayList<>();
                map.put("likeinfo", likeInfo);
                String str = userApiService.userOrderQuery(noteUserEntity.getUser_id(), noteUserEntity.getId(), "gift", "note");
                if (StringUtil.isNotEmpty(str)) {
                    List<Long> longs = JSONUtils.json2List(str, Long.class);
                    if (longs != null && longs.size() > 0) {
                        for (Long aLong : longs) {
                            list2.add(JSONUtils.obj2Json(redisService.getStr(aLong + "userinfo")));
                        }
                    }
                }
                map.put("giftCount", list2.size());
                Set<Object> set = new HashSet<>(list2);
                map.put("giftinfo", set);
                NoteCommentEntity noteCommentEntity = new NoteCommentEntity();
                noteCommentEntity.setNote_id(noteUserEntity.getId());
                noteCommentEntity.setStatus(1);
                List<NoteCommentEntity> noteCommentEntities = noteCommentService.queryListPage(noteCommentEntity, request, response);
                map.put("noteCommentEntities", noteCommentEntities);
                noteUserEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteUserEntities;
    }

    //??????????????????
    @Override
    public String queryNoteInfo(Long note_id, HttpServletRequest request, HttpServletResponse response) {
        NoteUserEntity noteUserEntity = new NoteUserEntity();
        noteUserEntity.setId(note_id);
        noteUserEntity.setPageSize(1);
        List<NoteUserEntity> noteUserEntities = super.query(noteUserEntity, request, response);
        String noteInfo = null;
        if (noteUserEntities != null && noteUserEntities.size() > 0) {
            noteInfo = JSONUtils.obj2Json(noteUserEntities.get(0)).toString();
        }
        return noteInfo;
    }

}