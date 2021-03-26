package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.SignInService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserOrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignInServiceImpl extends BaseFrameworkServiceImpl<SignInEntity, Long> implements SignInService {

    @Resource
    private SignInMapper mapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserOrderService orderService;
    @Resource
    private UserOrderMapper userOrderMapper;
    @Resource
    private UserGoldTypeMapper userGoldTypeMapper;
    @Resource
    private UserPointsMapper userPointsMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(SignInEntity signInEntity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = null;
        try {
            if (mapper.queryToday(signInEntity.getUser_id()) != null && mapper.queryToday(signInEntity.getUser_id()) > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("result", false);
                map.put("remark", "已经签到过，不能重复签到");
                return ResultUtil.success(map);
            }
            resultParam = super.insert(signInEntity, request, response);
            if (resultParam != null && resultParam.getCode() == 0) {
                UserInfoEntity entity = new UserInfoEntity();
                entity.setUser_id(signInEntity.getUser_id());
                entity.setPageSize(1);
                List<UserInfoEntity> userInfoEntities = userInfoMapper.query(entity);
                if (userInfoEntities != null && userInfoEntities.size() > 0) {
                    entity.setCat_coin(signInEntity.getCat_coin());
                    int ret = userInfoMapper.updateAddCatCoin(entity);
                    if (ret > 0) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("result", true);
                        map.put("remark", "签到成功");
                        UserOrderEntity entity1 = new UserOrderEntity();
                        entity1.setPayment_id(signInEntity.getUser_id());
                        entity1.setType("sign");
                        entity1.setSource("user");
                        entity1.setStatus(2);
                        entity1.setPay_count(signInEntity.getCat_coin());
                        orderService.insert(entity1, request, response);
                        return ResultUtil.success(map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10028.getCode(), UserEum.user_10028.getDesc());
    }

    @Override
    public SignInEntity querySignDetail(SignInEntity entity, HttpServletRequest request, HttpServletResponse response) {
        SignInDayEntiy signInDayEntiy = mapper.countSevenDay(entity);
        int countDay = count_day(signInDayEntiy);
        SignInEntity signInEntity = new SignInEntity();
        signInEntity.setDay(countDay);
        if (signInDayEntiy != null && signInDayEntiy.getOne_day() > 0) {
            signInEntity.setToday_sign_in(true);
        }
        return signInEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultParam insertLogin(SignInEntity entity, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<>();
            Integer integer = mapper.queryToday(entity.getUser_id());
            if (integer > 0) {
                map.put("result", false);
                map.put("remark", "已经签到过，不能重复签到");
                return ResultUtil.success(map);
            }
            int i = mapper.insertSelective(entity);
            if (i > 0) {
                int countNum = this.count_day(mapper.countSevenDay(entity));
                UserGoldTypeEntity userGoldTypeEntity = new UserGoldTypeEntity();
                userGoldTypeEntity.setType("signIn");
                userGoldTypeEntity.setStatus(3);
                userGoldTypeEntity.setPageSize(1);
                if (countNum == 7) {
                    userGoldTypeEntity.setLevel(8);
                } else {
                    userGoldTypeEntity.setLevel(countNum);
                }
                userGoldTypeEntity = userGoldTypeMapper.queryListPage(userGoldTypeEntity).get(0);
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getUser_id());
                userInfoEntity.setPoints(userGoldTypeEntity.getGold_num().intValue());
                int res = userInfoMapper.updateAddPoint(userInfoEntity);
                if (res > 0) {
                    UserOrderEntity userOrderEntity = new UserOrderEntity();
                    userOrderEntity.setUser_id(entity.getUser_id());
                    userOrderEntity.setType("singIn");
                    userOrderEntity.setPay_count(userGoldTypeEntity.getGold_num());
//                    ResultParam insert = orderService.insert(userOrderEntity, request, response);
                    int i2 = userOrderMapper.insertSelective(userOrderEntity);
                    map.put("result", true);
                    map.put("remark", "签到成功");
                    UserPointsEntity userPointsEntity = new UserPointsEntity();
                    userPointsEntity.setUsre_id(entity.getUser_id());
                    userPointsEntity.setPoint(userGoldTypeEntity.getGold_num().intValue());
                    userPointsEntity.setType("sign");
                    int i1 = userPointsMapper.insertSelective(userPointsEntity);
                    if (i1 > 0) {
                        entity.setPoint(userGoldTypeEntity.getGold_num().intValue());
                        mapper.updateByPrimaryKeySelective(entity);
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setTarget_id(entity.getUser_id());
                        pushMessageParam.setPush_type("sign");
                        pushMessageParam.setOpera_type("sign");
                        pushMessageParam.setContent("签到成功，获得"+userGoldTypeEntity.getGold_num().intValue()+"积分");
                        pushMessageParam.setTitle("签到成功");
                        pushMessageParam.setType_id(entity.getId());
                        pushMessageParam.setType("sign");
                        pushMessageParam.setIs_teach_paypal(entity.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                        return ResultUtil.success(map);
                    }
                }
            }
            throw new Exception("签到失败");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10028.getCode(), UserEum.user_10028.getDesc());
    }

    @Override
    public Map<String, Object> queryTeachPaypalDetail(SignInEntity entity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            SignInDayEntiy signInDayEntiy = mapper.countSevenDay(entity);
            int countDay = this.count_day(signInDayEntiy);
            map.put("singInDay", countDay);

            if (signInDayEntiy != null && signInDayEntiy.getOne_day() > 0) {
                map.put("todaySignIn", true);
            } else {
                map.put("todaySignIn", false);
            }

            UserGoldTypeEntity userGoldTypeEntity = new UserGoldTypeEntity();
            userGoldTypeEntity.setStatus(3);
            userGoldTypeEntity.setType("signIn");
            map.put("goldList", userGoldTypeMapper.query(userGoldTypeEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public int count_day(SignInDayEntiy signInDayEntiy) {
        int countDay = 0;
        if (signInDayEntiy != null && signInDayEntiy.getOne_day() > 0) {
            countDay++;
        }
        if (signInDayEntiy != null && signInDayEntiy.getTwo_day() > 0) {
            countDay++;
            if (signInDayEntiy.getThree_day() > 0) {
                countDay++;
                if (signInDayEntiy.getFive_day() > 0) {
                    countDay++;
                    if (signInDayEntiy.getFive_day() > 0) {
                        countDay++;
                        if (signInDayEntiy.getSix_day() > 0) {
                            countDay++;
                            if (signInDayEntiy.getSeven_day() > 0) {
                                countDay++;
                            }
                        }
                    }
                }
            }
        }
        return countDay;
    }
}