package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.ManagerEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.RechargeRecordMapper;
import com.huihe.eg.user.mybatis.dao.UserClassCardMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserMapper;
import com.huihe.eg.user.service.dao.SystemRechargeService;
import com.huihe.eg.user.service.dao.UserClassCardService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserClassCardServiceImpl extends BaseFrameworkServiceImpl<UserClassCardEntity, Long> implements UserClassCardService {

    @Resource
    private UserClassCardMapper mapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private SystemRechargeService systemRechargeService;
    @Resource
    private RedisService redisService;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam updateDefaultUse(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param == null || param.getUser_id() == null || param.getUser_id() == 0) {
                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
            }
            UserClassCardEntity entity = new UserClassCardEntity();
            entity.setUser_id(param.getUser_id());
            entity.setDefault_use(false);
            int integer = mapper.updateDefaultUse(entity);
            if (integer > 0) {
                param.setDefault_use(true);
                int ret = mapper.updateDefaultUse(param);
                if (ret > 0) {
                    return ResultUtil.success();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserClassCardServiceImpl  updateDefaultUse");
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    public ResultParam AddClass(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<UserClassCardEntity> userClassCardEntities = mapper.queryListPage(param);
            if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                if (param.getType() != null && "curriculum".equalsIgnoreCase(param.getType())) {
                    param = userClassCardEntities.get(0);
                    int curriculum = param.getCurriculum_num() + 1;
                    param.setCurriculum_num(curriculum);
                    return super.update(param, request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserClassCardServiceImpl  updateDefaultUse");
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    //后台-新增课程学习卡
    @Override
    public ResultParam newlyAddedCard(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setLogin_name(param.getPhone());
            userEntity.setPageSize(1);
            List<UserEntity> userEntities = userMapper.queryListPage(userEntity);//查询这个用户
            if (userEntities != null && userEntities.size() > 0) {
                if (param.getType() == null || "".equalsIgnoreCase(param.getType())) {
                    return ResultUtil.error(ManagerEum.manager_900011.getCode(), ManagerEum.manager_900011.getDesc());
                }
                SystemRechargeEntity systemRechargeEntity = new SystemRechargeEntity();//充值记录
                systemRechargeEntity.setAccount(param.getPhone());
                systemRechargeEntity.setUser_id(userEntities.get(0).getUser_id());
                systemRechargeEntity.setType(param.getType());
                if ("cat_coin".equalsIgnoreCase(param.getType())) {
                    systemRechargeEntity.setRecharge_count(param.getCat_coin().intValue());
                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                    userInfoEntity.setUser_id(userEntities.get(0).getUser_id());
                    userInfoEntity.setPageSize(1);
                    List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);
                    if (userInfoEntities != null && userInfoEntities.size() > 0) {
                        userInfoEntity = userInfoEntities.get(0);
                        BigDecimal cat_coin = userInfoEntity.getCat_coin().add(param.getCat_coin());//添加coin
                        userInfoEntity.setCat_coin(cat_coin);
                        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);//更新
                        systemRechargeService.insert(systemRechargeEntity, request, response);//插入记录
                        return ResultUtil.success();
                    } else {
                        return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                    }
                } else {
                    param.setUser_id(userEntities.get(0).getUser_id());
                    UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                    userClassCardEntity.setUser_id(userEntities.get(0).getUser_id());
                    userClassCardEntity.setType(param.getType());//类型
                    userClassCardEntity.setPageSize(1);
                    List<UserClassCardEntity> userClassCardEntities = mapper.queryListPage(userClassCardEntity);//查询这个卡
                    if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                        UserClassCardEntity entity = new UserClassCardEntity();
                        entity.setUser_id(userEntities.get(0).getUser_id());
                        entity.setDefault_use(false);//更新默认使用
                        mapper.updateDefaultUse(entity);
                        userClassCardEntity = userClassCardEntities.get(0);
                        userClassCardEntity.setDefault_use(true);
                        userClassCardEntity.setExpire_time(param.getExpire_time());
                        if (userClassCardEntity.getStatus() == 1) {//过期
                            if ("minute".equalsIgnoreCase(param.getType())) {
                                userClassCardEntity.setMinute_num(param.getMinute_num());
                                systemRechargeEntity.setRecharge_count(param.getMinute_num());
                            } else if ("curriculum".equalsIgnoreCase(param.getType())) {
                                systemRechargeEntity.setRecharge_count(param.getCurriculum_num());
                                userClassCardEntity.setCurriculum_num(param.getCurriculum_num());
                            }
                            userClassCardEntity.setStatus(2);
                        } else {//可用
                            userClassCardEntity.setExpire_time(param.getExpire_time());
                            if ("minute".equalsIgnoreCase(param.getType())) {
                                systemRechargeEntity.setRecharge_count(param.getMinute_num());
                                userClassCardEntity.setMinute_num(userClassCardEntity.getMinute_num() + param.getMinute_num());
                            } else if ("curriculum".equalsIgnoreCase(param.getType())) {
                                userClassCardEntity.setCurriculum_num(userClassCardEntity.getCurriculum_num() + param.getCurriculum_num());
                                systemRechargeEntity.setRecharge_count(param.getCurriculum_num());
                            }
                        }
                        systemRechargeService.insert(systemRechargeEntity, request, response);
                        return super.update(userClassCardEntity, request, response);
                    } else {
                        UserClassCardEntity entity = new UserClassCardEntity();
                        entity.setUser_id(userEntities.get(0).getUser_id());
                        entity.setDefault_use(false);
                        mapper.updateDefaultUse(entity);
                        if ("minute".equalsIgnoreCase(param.getType())) {
                            systemRechargeEntity.setRecharge_count(param.getMinute_num());
                        } else if ("curriculum".equalsIgnoreCase(param.getType())) {
                            systemRechargeEntity.setRecharge_count(param.getCurriculum_num());
                        }
                        systemRechargeService.insert(systemRechargeEntity, request, response);
                        param.setDefault_use(true);
                        return super.insert(param, request, response);
                    }
                }

            } else {
                return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserClassCardServiceImpl  newlyAddedCard");
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    public Map<String, Object> cardPayCount(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();

        try {
            if (param.getType() != null && ("minute".equals(param.getType()) || "curriculum".equals(param.getType()))) {
                map = new HashMap<>();
            }
            Integer dayPayTotal = mapper.queryDayTotal(param);
            map.put("dayPayTotal", dayPayTotal);//日售课时

            Integer weekPayTotal = mapper.queryWeekTotal(param);
            map.put("weekPayTotal", weekPayTotal);//周售课时

            Integer monthPayTotal = mapper.queryMonthTotal(param);
            map.put("monthPayTotal", monthPayTotal);//月售课时

            Integer payCountTotal = mapper.queryPayTotal(param);
            map.put("payCountTotal", payCountTotal);//总售课时
        } catch (Exception e) {
            e.printStackTrace();

        }
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            if (param.getLogin_name() != null && !"".equals(param.getLogin_name())) {
                List<Long> list1 = userMapper.queryIdByLoginName(param.getLogin_name());
                if (list1 != null) {
                    param.setLoginIds(list1);
                }
            }
            if (param.getNick_name() != null && !"".equals(param.getNick_name())) {
                List<Long> list1 = userInfoMapper.queryIdByNickName(param.getNick_name());
                if (list1 != null) {
                    param.setNickNameIds(list1);
                }
            }
            List<UserClassCardEntity> list  = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (list != null && list.size() > 0) {
                for (UserClassCardEntity userClassCardEntity : list) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(userClassCardEntity.getUser_id() + "userinfo")));
                    userClassCardEntity.setMap(map);
                }
            }
            map1.put("rows", list);
            map1.put("total", total);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return map1;
    }

    @Override
    public List<UserClassCardEntity> queryMyExperience(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserClassCardEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPage(param);
            if (list != null && list.size() > 0) {
                for (UserClassCardEntity userClassCardEntity : list) {
                    RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                    rechargeRecordEntity.setIs_experience(true);
                    rechargeRecordEntity.setUser_id(param.getUser_id());
                    rechargeRecordEntity.setRcharge_type("class_card");
                    List<RechargeRecordEntity> list1 = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("rechargeRecordEntity", list1);
                    userClassCardEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserClassCardServiceImpl  updateDefaultUse");
        }
        return list;
    }

    @Override
    public List<UserClassCardEntity> queryListPage(UserClassCardEntity userClassCardEntity, HttpServletRequest
            request, HttpServletResponse response) {
        List<UserClassCardEntity> List = super.queryListPage(userClassCardEntity, request, response);
        for (UserClassCardEntity classCardEntity : List) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(classCardEntity.getUser_id() + "userinfo")));
            classCardEntity.setMap(map);
        }
        return List;
    }
}