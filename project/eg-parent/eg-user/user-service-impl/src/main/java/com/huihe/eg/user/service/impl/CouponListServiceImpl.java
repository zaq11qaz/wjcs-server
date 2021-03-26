package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.CouponEum;
import com.huihe.eg.comm.SystemEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.CouponListService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CouponListServiceImpl extends BaseFrameworkServiceImpl<CouponListEntity, Long> implements CouponListService {

    @Resource
    private CouponListMapper mapper;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private ClassRecordMapper classRecordMapper;
    @Resource
    private CommodityCouponMapper commodityCouponMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserMapper userMapper;


    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<CouponListEntity> queryListPage(CouponListEntity couponListEntity, HttpServletRequest request, HttpServletResponse response) {
        List<CouponListEntity> couponListEntities = mapper.queryListPage(couponListEntity);
        if (couponListEntities != null && couponListEntities.size() > 0) {
            this.setUserInfo(couponListEntities);
        }
        return couponListEntities;
    }

    private void setUserInfo(List<CouponListEntity> couponListEntities) {
        for (CouponListEntity listEntity : couponListEntities) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("createUserInfo", JSONUtils.obj2Json(redisService.getStr(listEntity.getUser_id() + "userinfo")));
            if (listEntity.getGet_code_user_id() != 0) {
                map.put("getUserInfo", JSONUtils.obj2Json(redisService.getStr(listEntity.getGet_code_user_id() + "userinfo")));
            } else {
                map.put("getUserInfo", null);
            }
            listEntity.setMap(map);
        }
    }


    /*
    @Override
    public ResultParam useDiscountVolume(CouponListEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            ResultParam resultParam = checkIsNull(param);
            if (resultParam.getCode() != 0) {
                return resultParam;
            }
            List<CouponListEntity> couponListEntities = mapper.queryListPage(param);
            if (couponListEntities != null && couponListEntities.size() > 0) {
                if (couponListEntities.get(0).getStatus() == 1) {
                    return ResultUtil.error(CouponEum.coupon_30003.getCode(), CouponEum.coupon_30003.getDesc());
                }
                UserCouponEntity userCouponEntity = new UserCouponEntity();
                userCouponEntity.setUser_id(param.getUser_id());
                userCouponEntity.setCoupon_id(couponListEntities.get(0).getCoupon_id());
                userCouponEntity.setType(couponListEntities.get(0).getCoupon_type());
                int ret = userCouponMapper.insertSelective(userCouponEntity);
                if (ret > 0) {
                    CouponListEntity couponListEntity = couponListEntities.get(0);
                    couponListEntity.setStatus(1);
                    int res = mapper.updateByPrimaryKeySelective(couponListEntity);
                    if (res > 0) {
                        if (userCouponEntity.getType().equalsIgnoreCase("class_periods")) {//直播课堂锦鲤码
                            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                            userClassCardEntity.setUser_id(param.getUser_id());
                            userClassCardEntity.setType("curriculum");
                            userClassCardEntity.setPageSize(1);
                            List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);//查询所指定使用学习卡
                            if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                //购买过此种类型直播学习卡
                                userClassCardEntity = userClassCardEntities.get(0);
                                //结束时间
                                Date dt = new Date();
                                Calendar rightNow = Calendar.getInstance();
                                rightNow.setTime(dt);
                                rightNow.add(Calendar.YEAR, 1);
                                Date dt1 = rightNow.getTime();
                                userClassCardEntity.setExpire_time(dt1);
                                if (userClassCardEntity.getStatus() == 1) {
                                    userClassCardEntity.setCurriculum_num(1);
                                    userClassCardEntity.setStatus(2);
                                } else {
                                    Integer integer = userClassCardEntity.getCurriculum_num() + 1;
                                    userClassCardEntity.setCurriculum_num(integer);
                                }
                                UserClassCardEntity entity = new UserClassCardEntity();
                                entity.setUser_id(userClassCardEntity.getUser_id());
                                entity.setDefault_use(false);
                                int req = userClassCardMapper.updateDefaultUse(entity);
                                if (req > 0) {
                                    userClassCardEntity.setDefault_use(true);
                                    userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                }
                            } else {
                                Date dt = new Date();
                                Calendar rightNow = Calendar.getInstance();
                                rightNow.setTime(dt);
                                rightNow.add(Calendar.YEAR, 1);
                                Date dt1 = rightNow.getTime();
                                userClassCardEntity.setExpire_time(dt1);
                                userClassCardEntity.setCurriculum_num(1);
                                UserClassCardEntity entity = new UserClassCardEntity();
                                entity.setUser_id(userClassCardEntity.getUser_id());
                                entity.setDefault_use(false);
                                int req = userClassCardMapper.updateDefaultUse(entity);
                                if (req > 0) {
                                    userClassCardEntity.setDefault_use(true);
                                    userClassCardMapper.insertSelective(userClassCardEntity);
                                }
                            }
                        }

                    }
                }
                return ResultUtil.success();
            } else {
                return ResultUtil.error(CouponEum.coupon_30001.getCode(), CouponEum.coupon_30001.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("CouponListServiceImpl  useDiscountVolume");
        }
        return ResultUtil.error(CouponEum.coupon_30008.getCode(), CouponEum.coupon_30008.getDesc());
    }


     */

    @Override
    public ResultParam useVoucher(CouponListEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            ResultParam resultParam = checkIsNull(param);
            if (resultParam.getCode() != 0) {
                return resultParam;
            }
            CouponListEntity couponListEntity = mapper.queryByCouponCode(param);
            if (couponListEntity != null && couponListEntity.getCoupon_id() != null) {
                CommodityCouponEntity commodityCouponEntity = commodityCouponMapper.selectByPrimaryKey(couponListEntity.getCoupon_id());
                param.setCoupon_type(commodityCouponEntity.getType());
                param.setCoupon_id(commodityCouponEntity.getId());
                param.setCoupon_list_id(couponListEntity.getId());
                resultParam = checkIsUsed(param);
                if (resultParam.getCode() != 0) {
                    return resultParam;
                }
                switch (couponListEntity.getCoupon_type()) {
                    case "lucky_charm_key":
                        return this.insertVideoDuration(param);
                    case "1":
                        break;
                    default:
                        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(CouponEum.coupon_30001.getCode(), CouponEum.coupon_30001.getDesc());
    }

    private ResultParam checkIsUsed(CouponListEntity param) {
        UserCouponEntity userCouponEntity = new UserCouponEntity();
        userCouponEntity.setCoupon_list_id(param.getCoupon_list_id());
        userCouponEntity.setPageSize(1);
        List<UserCouponEntity> userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
        if (userCouponEntities != null && userCouponEntities.size() > 0) {
            userCouponEntity = userCouponEntities.get(0);
            if (userCouponEntity.getStatus() != 1) {
                return ResultUtil.error(CouponEum.coupon_30003.getCode(), CouponEum.coupon_30003.getDesc());
            }
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam updateStatus(CouponListEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getIds() != null && param.getIds().length > 0) {
                Integer[] ids = param.getIds();
                for (Integer id : ids) {
                    CouponListEntity couponListEntity = mapper.selectByPrimaryKey(id.longValue());
                    param.setDuration(couponListEntity.getDuration());
                    param.setStatus(1);
                    param.setId(id.longValue());
                    int integer = mapper.updateStatus(param);
                    if (integer > 0) {
                        return ResultUtil.success();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    public Map<String, Object> queryByMessage(CouponListEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (param.getFull_name() != null) {
                List<Long> list1 = masterInfoMapper.queryIdByFullName(param.getFull_name());
                if (list1 != null && list1.size() > 0) {
                    param.setName_ids(list1);
                }
            }
            if (param.getLogin_name() != null) {
                List<Long> list1 = userMapper.queryIdByLoginName(param.getLogin_name());
                if (list1 != null && list1.size() > 0) {
                    param.setLoginIDs(list1);
                }
            }
            List<CouponListEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total>0){
                this.setUserInfo(list);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    @Override
    public Map<String, Object> queryCoupListCount(CouponListEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = Maps.newHashMap();

        try {
        Integer dayCount = mapper.queryDayCount(param);
        map.put("dayCount",dayCount);

        Integer weekCount = mapper.queryWeekCount(param);
        map.put("weekCount",weekCount);

        Integer monthCount = mapper.queryMonthCount(param);
        map.put("monthCount",monthCount);

        Integer totalCount = mapper.queryTotalCount(param);
        map.put("totalCount",totalCount);

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private ResultParam insertVideoDuration(CouponListEntity param) {
        ClassRecordEntity classRecordEntity = new ClassRecordEntity();
        classRecordEntity.setUser_id(param.getUser_id());
        classRecordEntity.setCurriculum_id(param.getAppointment_id());
        List<ClassRecordEntity> classRecordEntities = classRecordMapper.queryListPage(classRecordEntity);
        if (classRecordEntities != null && classRecordEntities.size() > 0) {
            classRecordEntity = classRecordEntities.get(0);
            classRecordEntity.setStatus(3);
            classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
        }

        UserCouponEntity userCouponEntity = new UserCouponEntity();
        userCouponEntity.setUser_id(param.getUser_id());
        userCouponEntity.setCoupon_id(param.getCoupon_id());
        userCouponEntity.setType(param.getCoupon_type());
        userCouponEntity.setAppointment_id(classRecordEntity.getCurriculum_id());
        userCouponEntity.setStatus(3);
        userCouponEntity.setCoupon_list_id(param.getCoupon_list_id());
        userCouponEntity.setIs_teach_paypal(true);
        userCouponMapper.insertSelective(userCouponEntity);

        return ResultUtil.success();
    }


    private ResultParam checkIsNull(CouponListEntity param) {
        if (param.getCoupon_code() == null || param.getCoupon_code().equals("")) {
            return ResultUtil.error(CouponEum.coupon_30007.getCode(), CouponEum.coupon_30007.getDesc());
        }
        if (param.getUser_id() == null || param.getUser_id() == 0) {
            return ResultUtil.error(SystemEum.system_20004.getCode(), SystemEum.system_20004.getDesc());
        }
        return ResultUtil.success();
    }
}