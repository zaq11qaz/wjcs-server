package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterCommentServiceImpl extends BaseFrameworkServiceImpl<MasterCommentEntity, Long> implements MasterCommentService {

    @Resource
    private MasterCommentMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserOrderService userOrderService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public synchronized ResultParam insert(MasterCommentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            masterAppointmentService.updateCommentCount(param.getAppointment_id(), request, response);
            if (param.getUser_appointment_id() != null && param.getUser_appointment_id() != 0) {
                UserAppointmentEntity userAppointmentEntity = userAppointmentMapper.selectByPrimaryKey(param.getUser_appointment_id());
                if (userAppointmentEntity.getIs_comment()){
                    return ResultUtil.error(OrderEum.order_70023.getCode(),OrderEum.order_70023.getDesc());
                }
                userAppointmentEntity.setIs_comment(true);
                userAppointmentMapper.updateByPrimaryKeySelective(userAppointmentEntity);
            }

            if (param.getMechanism_id() != null && param.getMechanism_id() != 0) {
                MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                masterCommentEntity.setMechanism_id(param.getMechanism_id());
                Map<String, Object> map = this.queryMechanismAverageScore(masterCommentEntity, request, response);
                MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(param.getMechanism_id());
                mechanismEntity.setAvg_rating((Double) map.get("average_score"));
                masterMechanismMapper.updateByPrimaryKeySelective(mechanismEntity);
            }
            ResultParam insert = super.insert(param, request, response);
            if (insert.getCode()==0){
                UserOrderEntity userOrderEntity = new UserOrderEntity();
                userOrderEntity.setType("course_comments");
                userOrderEntity.setUser_id(param.getUser_id());
                ResultParam resultParam = userOrderService.insertPoint(userOrderEntity,request,response);
            }
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    /**
     * 助学师评论
     *
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年6月18日13:32:04
     */
    @Override
    public List<MasterCommentEntity> queryListPage(MasterCommentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterCommentEntity> masterCommentEntities = null;
        try {
            masterCommentEntities = mapper.queryListPage(param);
            for (MasterCommentEntity masterCommentEntity : masterCommentEntities) {
                if (StringUtil.isNotEmpty(masterCommentEntity.getContent())) {
                    //转成表情
                    masterCommentEntity.setContent(EmojiParser.parseToUnicode(masterCommentEntity.getContent()));
                }
                Map<String, Object> map = new HashMap<>();
                if (masterCommentEntity.getGroup_id() != 0) {
                    JSONObject jsonObject = new JSONObject();
                    if (masterCommentEntity.getGroup_id() != 0) {
                        String group_info = messageApiService.queryGroupInfo(masterCommentEntity.getGroup_id());
                        jsonObject = JSONObject.parseObject(group_info);
                    }
                    map.put("groupinfo", jsonObject);
                }
                if (masterCommentEntity.getAppointment_id() != 0) {
                    MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(masterCommentEntity.getAppointment_id(), request, response);//直播课堂评论
                    map.put("masterAppointmentInfo", masterAppointmentEntity);
                    UserAppointmentEntity userAppointmentEntity = userAppointmentService.findById(masterCommentEntity.getAppointment_id(), request, response);//助学师评论
                    map.put("appointmentinfo", userAppointmentEntity);
                }
                if (masterCommentEntity.getMastersetprice_id() != 0) {
                    MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(masterCommentEntity.getMastersetprice_id());
                    map.put("masterSetPriceEntity", masterSetPriceEntity);
                }
                UserInfoEntity infoEntity = userInfoService.findById(masterCommentEntity.getUser_id(), request, response);
                map.put("userinfo", infoEntity);
                MasterCommentEntity commentEntity = new MasterCommentEntity();
                commentEntity.setParent_id(masterCommentEntity.getId());
                List<MasterCommentEntity> masterCommentEntityList = mapper.queryListPage(commentEntity);
                map.put("subcount", mapper.queryListPageCount(commentEntity));
                if (masterCommentEntityList != null && masterCommentEntityList.size() > 0) {
                    for (MasterCommentEntity entity : masterCommentEntityList) {
                        Map<String, Object> map1 = new HashMap<>();
                        UserInfoEntity userInfo = userInfoService.findById(entity.getUser_id(), request, response);
                        map1.put("userinfo", userInfo);
                        Long subReply_id = 0L;
                        if (entity.getIs_reply() != null && entity.getIs_reply()) {
                            subReply_id = entity.getReply_id();
                        }
                        UserInfoEntity replyInfo = userInfoService.findById(subReply_id, request, response);
                        map1.put("replyinfo", replyInfo);
                        entity.setMap(map1);
                    }
                }
                map.put("subComment", masterCommentEntityList);
                masterCommentEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterComment       queryListPage");
        }
        return masterCommentEntities;
    }


    @Override
    public void updateCommentLikeCount(Long id, HttpServletRequest request, HttpServletResponse response) {
        mapper.updateCommentLikeCount(id);
    }

    @Override
    public void updateCommentLikeCountCancel(Long id, HttpServletRequest request, HttpServletResponse response) {
        mapper.updateCommentLikeCountCancel(id);
    }

    @Override
    public void updateCommentShareCount(Long id, HttpServletRequest request, HttpServletResponse response) {
        mapper.updateCommentShareCount(id);
    }

    @Override
    public Map<String, Object> queryMechanismAverageScore(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer commentCount = mapper.queryListPageCount(masterCommentEntity);
            map.put("commentCount", commentCount);

            masterCommentEntity = mapper.queryAvgScore(masterCommentEntity);
            map.put("course_quality", masterCommentEntity.getCourse_quality());
            map.put("environment", masterCommentEntity.getEnvironment());
            map.put("faculty", masterCommentEntity.getFaculty());
            map.put("cost_effectiveness", masterCommentEntity.getCost_effectiveness());
            map.put("attitude", masterCommentEntity.getAttitude());
            map.put("average_score", masterCommentEntity.getAverage_score());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MasterCommentEntity> queryMechanismList(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterCommentEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPage(masterCommentEntity);
            if (list != null && list.size() > 0) {
                for (MasterCommentEntity commentEntity : list) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(commentEntity.getUser_id() + "userinfo")));
                    commentEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ResultParam queryByMessage(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isNotEmpty(masterCommentEntity.getMechanism_name())){
                MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
                mechanismEntity.setMechanism_name(masterCommentEntity.getMechanism_name());
                mechanismEntity.setPageSize(1);
                List<MasterMechanismEntity> list1 = masterMechanismMapper.queryListPage(mechanismEntity);
                if (list1!=null&&list1.size()>0){
                    masterCommentEntity.setMechanism_id(list1.get(0).getId());
                }else {
                    return ResultUtil.error(UserEum.user_10059.getCode() , UserEum.user_10059.getDesc() );
                }
            }

            if (StringUtil.isNotEmpty(masterCommentEntity.getCommodities_name())){
                MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                masterSetPriceEntity.setTitle(masterCommentEntity.getCommodities_name());
                masterSetPriceEntity.setPageSize(1);
                List<MasterSetPriceEntity> list1 = masterSetPriceMapper.queryListPage(masterSetPriceEntity);
                if (list1!=null&&list1.size()>0){
                    masterCommentEntity.setMastersetprice_id(list1.get(0).getId());
                }else {
                    return ResultUtil.error(UserEum.user_10060.getCode() , UserEum.user_10060.getDesc() );
                }
            }

            if (StringUtil.isNotEmpty(masterCommentEntity.getNick_name())){
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setNick_name(masterCommentEntity.getNick_name());
                userInfoEntity.setPageSize(1);
                List<UserInfoEntity> list1 = userInfoMapper.queryListPage(userInfoEntity);
                if (list1!=null&&list1.size()>0){
                    masterCommentEntity.setUser_id(list1.get(0).getUser_id());
                }else {
                    return ResultUtil.error(UserEum.user_10061.getCode() , UserEum.user_10061.getDesc() );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(super.queryManagerListPage(masterCommentEntity, request, response));
    }
}