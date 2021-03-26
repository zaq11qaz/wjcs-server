package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.CouponEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.CommodityCouponMapper;
import com.huihe.eg.user.mybatis.dao.CouponListMapper;
import com.huihe.eg.user.mybatis.dao.UserCouponMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.service.dao.CommodityCouponService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserCouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.huihe.eg.comm.util.GenSerial.generateCodes;

@Service
public class CommodityCouponServiceImpl extends BaseFrameworkServiceImpl<CommodityCouponEntity, Long> implements CommodityCouponService {

    @Resource
    private CommodityCouponMapper mapper;
    @Resource
    private CouponListMapper couponListMapper;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserCouponMapper userCouponMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }


    /**
     * 判断插入更新操作
     */
    @Override
    public ResultParam insert(CommodityCouponEntity commodityCouponEntity, HttpServletRequest request, HttpServletResponse response) {
        CommodityCouponEntity commodityCouponEntity1 = new CommodityCouponEntity();
        commodityCouponEntity1.setName(commodityCouponEntity.getName());
        commodityCouponEntity1.setPageSize(1);
        List<CommodityCouponEntity> query = mapper.queryListPage(commodityCouponEntity1);
        if (query != null && query.size() > 0) {
            commodityCouponEntity.setId(query.get(0).getId());
            return super.update(commodityCouponEntity, request, response);
        }
        return super.insert(commodityCouponEntity, request, response);
    }

    /**
     * 用户领取券
     * 2019年6月10日14:44:13
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam ReceiveCoupon(CommodityCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            /**
             * 直接领取
             */
            CommodityCouponEntity commodityCouponEntity = mapper.selectByPrimaryKey(param.getId());
            if (commodityCouponEntity.getType() != null && "timepointcard".equalsIgnoreCase(commodityCouponEntity.getType())) {
                //新增领取记录
                UserCouponEntity userCouponEntity = new UserCouponEntity();
                userCouponEntity.setUser_id(param.getUser_id());
                userCouponEntity.setCoupon_id(commodityCouponEntity.getId());
                List<UserCouponEntity> userCouponEntities = userCouponService.queryListPage(userCouponEntity, request, response);
                if (userCouponEntities != null && userCouponEntities.size() > 0) {
                    return ResultUtil.error(CouponEum.coupon_30006.getCode(), CouponEum.coupon_30006.getDesc());
                } else {
                    UserInfoEntity infoEntity = userInfoMapper.selectByPrimaryKey(param.getUser_id());
                    Integer integer = infoEntity.getDuration() + commodityCouponEntity.getDuration().intValue();
                    infoEntity.setDuration(integer);
                    userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                    userCouponEntity.setStatus(1);
                    userCouponEntity.setType(commodityCouponEntity.getType());
                    userCouponEntity.setCoupon_id(commodityCouponEntity.getId());//商品券的id
                    userCouponEntity.setOverdue_time(commodityCouponEntity.getEnd_time());
                    userCouponEntity.setCourse_num(commodityCouponEntity.getCourse_num());
                    return userCouponService.insert(userCouponEntity, request, response);
                }
            }
            if (commodityCouponEntity.getType() != null && "classexperience".equalsIgnoreCase(commodityCouponEntity.getType())) {
                //新增领取记录
                UserCouponEntity userCouponEntity = new UserCouponEntity();
                userCouponEntity.setUser_id(param.getUser_id());
                userCouponEntity.setCoupon_id(commodityCouponEntity.getId());
                List<UserCouponEntity> userCouponEntities = userCouponService.queryListPage(userCouponEntity, request, response);
                if (userCouponEntities != null && userCouponEntities.size() > 0) {
                    return ResultUtil.error(CouponEum.coupon_30006.getCode(), CouponEum.coupon_30006.getDesc());
                } else {
                    userCouponEntity.setStatus(1);
                    userCouponEntity.setType(commodityCouponEntity.getType());
                    userCouponEntity.setCoupon_id(commodityCouponEntity.getId());//商品券的id
                    userCouponEntity.setOverdue_time(commodityCouponEntity.getEnd_time());
                    userCouponEntity.setCourse_num(commodityCouponEntity.getCourse_num());
                    return userCouponService.insert(userCouponEntity, request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("CommodityCouponServiceImpl   ReceiveCoupon");
        }
        return ResultUtil.error(CouponEum.coupon_30004.getCode(), CouponEum.coupon_30004.getDesc());
    }

    @Override
    public ResultParam insertCouponList(CommodityCouponEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            CommodityCouponEntity commodityCouponEntity = mapper.selectByPrimaryKey(param.getId());
            commodityCouponEntity.setNumber(param.getNumber());
            commodityCouponEntity.setUser_id(param.getUser_id());
            if (commodityCouponEntity.getStatus() == 2) {

                CouponListEntity couponListEntity = new CouponListEntity();
                couponListEntity.setStatus(commodityCouponEntity.getStatus());
                couponListEntity.setCoupon_id(commodityCouponEntity.getId());
                couponListEntity.setCoupon_type(commodityCouponEntity.getType());
                couponListEntity.setUser_id(commodityCouponEntity.getUser_id());
                couponListEntity.setDuration(param.getDuration());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse("2038-01-01 00:00:00");
                couponListEntity.setOverdue_time(date);
                Set<String> codes = generateCodes(null, commodityCouponEntity.getNumber(), 8, (int) Math.floor(Math.random() * 9), 2, 3);
                for (String code : codes) {
                    couponListEntity.setCoupon_code(code);
                    couponListMapper.insertSelective(couponListEntity);
                }
                return ResultUtil.success();
            } else {
                return ResultUtil.error(CouponEum.coupon_30005.getCode(), CouponEum.coupon_30005.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(1001, "失败");
    }

    @Override
    @Transactional
    public BigDecimal getFinalAmount(RechargeRecordEntity entity) {
        BigDecimal bigDecimal = entity.getAmount();
        try {
            CommodityCouponEntity commodityCouponEntity = mapper.selectByPrimaryKey(entity.getCoupon_id());
            if (commodityCouponEntity != null) {
                if (commodityCouponEntity.getStatus() == 2) {
                    if ("fulldiscount".equals(commodityCouponEntity.getType())) {
                        changeStatus(entity.getUser_id(), entity.getCoupon_id());
                        if (commodityCouponEntity.getFull_amount().compareTo(entity.getAmount()) <= 0) {
                            bigDecimal = entity.getAmount().subtract(commodityCouponEntity.getCash());
                            if (bigDecimal.compareTo(new BigDecimal("0")) < 1) {
                                bigDecimal = new BigDecimal("0");
                            }
                        }
                    }
                    if ("discount".equals(commodityCouponEntity.getType())) {
                        bigDecimal = entity.getAmount().multiply(commodityCouponEntity.getDiscount());
                        changeStatus(entity.getUser_id(), entity.getCoupon_id());
                    }
                }
            } else {
                if (entity.getCoupon_id() != null) {
                    UserCouponEntity userCouponEntity = userCouponMapper.selectByPrimaryKey(entity.getCoupon_id());
                    if (userCouponEntity.getStatus() == 1) {
                        if (entity.getIs_one_time_payment()) {
                            if ("100off_coupon".equals(userCouponEntity.getType())) {
                                bigDecimal = new BigDecimal("0");
                            }
                            if ("50off_coupon".equals(userCouponEntity.getType())) {
                                bigDecimal = entity.getAmount().multiply(new BigDecimal("0.5"));
                            }
                        }
//                    userCouponEntity.setStatus(3);
//                    userCouponEntity.setMechanism_id(entity.getMechanism_id());
//                    userCouponMapper.updateByPrimaryKeySelective(userCouponEntity);
                    }
                } else {
                    BigDecimal cash = new BigDecimal("0");
                    if (StringUtil.isNotEmpty(entity.getCoupon_ids())) {
                        String[] ids = entity.getCoupon_ids().split(",");
                        if (ids.length > 0) {
                            for (String id : ids) {
                                UserCouponEntity userCouponEntity1 = userCouponMapper.selectByPrimaryKey(Long.valueOf(id));
                                if (userCouponEntity1.getStatus() == 1 && "voucher_redemption".equals(userCouponEntity1.getType())){
                                    cash = entity.getAmount();
                                    break;
                                }
                                if (userCouponEntity1.getStatus() == 1 && userCouponEntity1.getUser_id().equals(entity.getUser_id())
                                        && userCouponEntity1.getType().equals("deductions_coupon")) {
                                    cash = cash.add(mapper.selectByPrimaryKey(userCouponEntity1.getCoupon_id()).getCash());
                                }
                            }
                        }
                    }
                    bigDecimal = entity.getAmount().subtract(cash);
                }
            }
            return bigDecimal;
        } catch (
                Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return entity.getAmount();
    }

    /**
     * 检查注册码是否可用
     *
     * @param coupon_code
     * @return
     */
    public CouponListEntity CheckCouponCode(String coupon_code) {
        CouponListEntity couponListEntity = new CouponListEntity();
        couponListEntity.setCoupon_code(coupon_code);
        List<CouponListEntity> query = couponListMapper.query(couponListEntity);
        if (query != null && query.size() > 0) {
            couponListEntity = query.get(0);
        } else {
            return null;
        }
        return couponListEntity;
    }

    public void changeStatus(long user_id, long coupon_id) {
        UserCouponEntity userCouponEntity = new UserCouponEntity();
        userCouponEntity.setCoupon_id(coupon_id);
        userCouponEntity.setUser_id(user_id);
        userCouponEntity.setPageSize(1);
        userCouponEntity = userCouponMapper.query(userCouponEntity).get(0);
        userCouponEntity.setStatus(3);
        userCouponMapper.updateByPrimaryKeySelective(userCouponEntity);
    }


}