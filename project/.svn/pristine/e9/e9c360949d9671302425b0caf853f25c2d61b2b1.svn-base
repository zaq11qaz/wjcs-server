package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.UserChattingMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.service.dao.UserChattingService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.dao.UserOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserChattingServiceImpl extends BaseFrameworkServiceImpl<UserChattingEntity, Long> implements UserChattingService {

    @Resource
    private UserChattingMapper mapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserOrderService orderService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional
    public ResultParam insert(UserChattingEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfoEntity entity2 = userInfoService.findById(param.getOther(), request, response);
            if (entity2 != null && entity2.getIs_high_quality()) {
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(param.getUser_id());
                userInfoEntity.setPageSize(1);
                List<UserInfoEntity> users = userInfoMapper.queryListPage(userInfoEntity);
                if (users != null && users.size() > 0) {
                    UserInfoEntity entity = users.get(0);
                    if (entity.getChatting_count() > 0) {
                        UserInfoEntity infoEntity = new UserInfoEntity();
                        infoEntity.setUser_id(entity.getUser_id());
                        int retu = userInfoMapper.updateChattingCount(infoEntity);
                        if (retu > 0) {
                            return super.insert(param, request, response);
                        }
                    } else {
                        BigDecimal bigDecimal = new BigDecimal(100);
                        if (bigDecimal.compareTo(entity.getCat_coin()) < 0) {
                            UserInfoEntity infoEntity = new UserInfoEntity();
                            infoEntity.setUser_id(entity.getUser_id());
                            infoEntity.setCat_coin(bigDecimal);
                            int retu = userInfoMapper.updateReduceCatCoin(infoEntity);
                            if (retu > 0) {
                                UserOrderEntity entity1 = new UserOrderEntity();
                                entity1.setUser_id(param.getUser_id());
                                entity1.setPayment_id(param.getOther());
                                entity1.setType("chat");
                                entity1.setSource("chat");
                                entity1.setStatus(2);
                                entity1.setPay_count(bigDecimal);
                                orderService.insert(entity1, request, response);
                                return super.insert(param, request, response);
                            }
                        } else {
                            return ResultUtil.error(OrderEum.order_70008.getCode(), OrderEum.order_70008.getDesc());
                        }
                    }
                } else {
                    return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                }
            } else {
                return super.insert(param, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserChattingServiceImpl   insert");
        }
        return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
    }
}