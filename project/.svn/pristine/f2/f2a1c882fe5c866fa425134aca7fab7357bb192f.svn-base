package com.huihe.eg.user.service.impl;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.util.json.JSONUtils;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.model.UserMemberEntity;
import com.huihe.eg.user.model.UserStudyCardEntity;
import com.huihe.eg.user.model.count.ChartParam;
import com.huihe.eg.user.model.count.MemberParam;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserMemberMapper;
import com.huihe.eg.user.mybatis.dao.UserStudyCardMapper;
import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.dao.UserMemberService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMemberServiceImpl extends BaseFrameworkServiceImpl<UserMemberEntity, Long> implements UserMemberService {

    @Resource
    private UserMemberMapper mapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private RedisService redisService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public Map<String,Object> memberCount(UserMemberEntity  param) {
        Map<String,Object> map = null;
        try {
            List<MemberParam> memberParam = mapper.memberCount();
            List<MemberParam> memberProportion = mapper.memberCountProportion(param);
            List<ChartParam> chartParam = mapper.memberStatistics(param);
            List<MemberParam> memberTotal=mapper.memberTotalCount();
            List<MemberParam> memberTeday=mapper.memberTedayCount();
            List<MemberParam> memberExpire=mapper.memberExpireCount();
            List<MemberParam> memberOnline=mapper.memberOnlineCount();
            map = new HashMap<>();
            map.put("memberTotal",memberTotal);//会员数量
            map.put("memberExpire",memberExpire);//今日过期
            map.put("memberTeday",memberTeday);//今日注册
            map.put("member",memberParam);//会员统计
            map.put("memberOnline",memberOnline);//当前在线
            map.put("chartParam",chartParam);//折线图统计
            map.put("memberProportion",memberProportion);//比例图
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> memberRegister(UserMemberEntity memberEntity, HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> memberList = new ArrayList<>();
        List<UserMemberEntity> list = super.queryListPage(memberEntity, request, response);
        if (list != null && list.size() > 0){
            for (UserMemberEntity userMemberEntity : list){
                Map<String,Object> map = new HashMap<>();
                map.put("memberInfo",userMemberEntity);
                UserInfoEntity userInfoEntity = userInfoService.findById(userMemberEntity.getUser_id(), request, response);
                map.put("userInfoEntity",userInfoEntity);
                memberList.add(map);
            }
        }
        return memberList;
    }

    /**
     * 每日凌晨处理

     */
    @Override
    public void membershipExpired(){
        try {
            //会员过期处理
            UserMemberEntity userMemberEntity=new UserMemberEntity();
            userMemberEntity.setIs_member(true);
            List<UserMemberEntity> userMemberEntities=mapper.query(userMemberEntity);
            for (UserMemberEntity entity:userMemberEntities) {
                //已过期
                if(entity.getEnd_time().getTime()<=System.currentTimeMillis()){
                    UserInfoEntity userInfoEntity=new UserInfoEntity();
                    userInfoEntity.setUser_id(entity.getUser_id());
                    userInfoEntity.setIs_member(false);
                    userInfoEntity.setMember_level(0);
                    userInfoEntity.setChatting_count(0);
                    userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                    entity.setIs_member(false);
                    mapper.updateByPrimaryKeySelective(entity);
                }
            }
            //学习卡过期处理
            UserStudyCardEntity userStudyCardEntity=new UserStudyCardEntity();
            userStudyCardEntity.setStatus(2);
            List<UserStudyCardEntity> userStudyCardEntities=userStudyCardMapper.query(userStudyCardEntity);
            for (UserStudyCardEntity entity:userStudyCardEntities) {
                if(entity.getEnd_time().getTime()<=System.currentTimeMillis()){
                    entity.setStatus(1);
                    userStudyCardMapper.updateByPrimaryKeySelective(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每月1号处理

     */
    @Override
    public void membermonthly(){
        try {//处理畅聊次数
            UserMemberEntity userMemberEntity=new UserMemberEntity();
            userMemberEntity.setIs_member(true);
            List<UserMemberEntity> userMemberEntities=mapper.query(userMemberEntity);
            for (UserMemberEntity entity:userMemberEntities) {
                if(entity.getIs_member()){
                    UserInfoEntity userInfoEntity=new UserInfoEntity();
                    userInfoEntity.setUser_id(entity.getUser_id());
                    userInfoEntity.setChatting_count(0);
                    userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                    if(entity.getMember_level()==1){
                        userInfoEntity.setChatting_count(45);
                    }else if(entity.getMember_level()==2){
                        userInfoEntity.setChatting_count(75);
                    }else{
                        userInfoEntity.setChatting_count(220);
                    }
                    userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserMemberEntity> queryListPage(UserMemberEntity userMemberEntity, HttpServletRequest request, HttpServletResponse response) {
        List<UserMemberEntity> userMemberEntities = mapper.queryListPage(userMemberEntity);
        if (userMemberEntities!=null&&userMemberEntities.size()>0){
            this.setInfo(userMemberEntities, request, response);
        }
        return userMemberEntities;
    }

    private void setInfo(List<UserMemberEntity> userMemberEntities, HttpServletRequest request, HttpServletResponse response) {
        for (UserMemberEntity memberEntity : userMemberEntities) {
            Map<String,Object> map = new HashMap<>();
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(memberEntity.getUser_id() + "userinfo")));
            memberEntity.setMap(map);
        }
    }

}