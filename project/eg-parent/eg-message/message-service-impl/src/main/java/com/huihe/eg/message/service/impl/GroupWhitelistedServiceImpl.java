package com.huihe.eg.message.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.message.model.GroupWhitelistedEntity;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.mybatis.dao.GroupWhitelistedMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.message.service.dao.GroupWhitelistedService;
import com.huihe.eg.message.service.dao.MessageGroupService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GroupWhitelistedServiceImpl extends BaseFrameworkServiceImpl<GroupWhitelistedEntity, Long> implements GroupWhitelistedService {

    @Resource
    private GroupWhitelistedMapper mapper;
    @Resource
    private MessageGroupService messageGroupService;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    /**
     * 通过白名单查询是否加群
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam queryIsAdoptWhitelisted(GroupWhitelistedEntity param, HttpServletRequest request, HttpServletResponse response){
        boolean aBoolean=false;
        try {
            param.setPageSize(1);
            List<GroupWhitelistedEntity> groupWhitelistedEntityList=mapper.queryListPage(param);
            if(groupWhitelistedEntityList!=null&&groupWhitelistedEntityList.size()>0){
                aBoolean=true;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("GroupWhitelistedServiceImpl  queryIsAdoptWhitelisted");
        }
        return ResultUtil.success(aBoolean);
    }
    @Override
    public List<GroupWhitelistedEntity> queryListPage(GroupWhitelistedEntity param, HttpServletRequest request, HttpServletResponse response){
        List<GroupWhitelistedEntity> groupWhitelistedEntities=null;
        try {
            groupWhitelistedEntities=mapper.queryListPage(param);
            for (GroupWhitelistedEntity entity : groupWhitelistedEntities){
                MessageGroupEntity messageGroupEntity=messageGroupService.findById(entity.getGroup_id(),request,response);
                if(messageGroupEntity!=null){
                    Map<String,Object> map=new HashMap<>();
                    Map<String,Object> mapUser=new HashMap<>();
                    mapUser.put("userinfo", JSONUtils.obj2Json(redisService.getStr(messageGroupEntity.getOwner_id() + "userinfo")));
                    messageGroupEntity.setMap(mapUser);
                    map.put("groupInfo",messageGroupEntity);
                    entity.setMap(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("GroupWhitelistedServiceImpl  queryListPage");
        }
        return groupWhitelistedEntities;
    }
    @Override
    public ResultParam insert(GroupWhitelistedEntity param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Long> longs= JSONUtils.json2List(param.getGroup_ids(),Long.class);
            for (Long aLong:longs){
                param.setGroup_id(aLong);//设置群id
                List<GroupWhitelistedEntity> groupWhitelistedEntities=mapper.queryListPage(param);//根据群id和用户id查询这条记录
                if(groupWhitelistedEntities!=null&&groupWhitelistedEntities.size()>0){
                    continue;
                }
                mapper.insertSelective(param);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("GroupWhitelistedServiceImpl  queryListPage");
        }
        return ResultUtil.success();
    }
    /**
     * 删除多条数据
     *
     * @param ids      删除的id
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam deleteList(String ids, HttpServletRequest request, HttpServletResponse response) {
        if (ids == null || ids.equals("")) {
            throw new DataException(ResultEnum.result_1.getCode(), "ID未传");
        }
        List<Long> longs=JSONUtils.json2List(ids,Long.class);
        init();
        int error = 0;
        int success = 0;
        for (Long id : longs) {
            if (mapper.deleteByPrimaryKey(id) > 0) {
                success++;
                continue;
            }
            error++;
        }
        return ResultUtil.success("成功删除：" + success + "条数据，删除失败：" + error + " 条数据");
    }
    @Override
    public List<Long> queryGroupIds(Long user_id){
        GroupWhitelistedEntity groupWhitelistedEntity=new GroupWhitelistedEntity();
        groupWhitelistedEntity.setUser_id(user_id);
        List<Long> longs=mapper.queryGroupIds(groupWhitelistedEntity);
        System.out.println(longs);
        return longs;

    }
}