package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.UserFollowMechanismService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class UserFollowMechanismServiceImpl extends BaseFrameworkServiceImpl<UserFollowMechanismEntity, Long> implements UserFollowMechanismService {

    @Resource
    private UserFollowMechanismMapper mapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private UserGoldTypeMapper userGoldTypeMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserOrderService userOrderService;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(UserFollowMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        if (param.getMechanism_id()!=null&&param.getMechanism_id()!=0) {
            UserFollowMechanismEntity userFollowMechanismEntity = new UserFollowMechanismEntity();
            userFollowMechanismEntity.setUser_id(userFollowMechanismEntity.getUser_id());
            userFollowMechanismEntity.setMechanism_id(param.getMechanism_id());
            Integer integer = mapper.queryListPageCount(userFollowMechanismEntity);
            if (integer==0){
                UserGoldTypeEntity userGoldTypeEntity = new UserGoldTypeEntity();
                userGoldTypeEntity.setStatus(3);
                userGoldTypeEntity.setType("mechanism_authentication");
                userGoldTypeEntity = userGoldTypeMapper.queryListPage(userGoldTypeEntity).get(0);
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(param.getUser_id());
                userInfoEntity.setPoints(userGoldTypeEntity.getGold_num().intValue());
                userInfoMapper.updateAddPoint(userInfoEntity);
            }

            MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(userFollowMechanismEntity.getMechanism_id());
            param.setMechanism_name(mechanismEntity.getMechanism_name());
            param.setMechanism_addr(mechanismEntity.getMechanism_addr());
            param.setContact_telephone(mechanismEntity.getContact_telephone());
        }
        ResultParam insert = super.insert(param, request, response);
        if (insert.getCode()==0){
            UserOrderEntity userOrderEntity = new UserOrderEntity();
            userOrderEntity.setType("mechanism_authentication");
            userOrderEntity.setUser_id(param.getUser_id());
            ResultParam resultParam = userOrderService.insertPoint(userOrderEntity,request,response);

        }
        return insert;
    }

    @Override
    @Transactional
    public ResultParam insertForList(UserFollowMechanismEntity map, HttpServletRequest request, HttpServletResponse response) {
        MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
        mechanismEntity.setIs_recommend(true);
        mechanismEntity.setStatus(2);
        List<MasterMechanismEntity> query = masterMechanismMapper.query(mechanismEntity);
        for (MasterMechanismEntity masterMechanismEntity : query) {
            MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
            masterSetPriceEntity.setIs_recommend(true);
            masterSetPriceEntity.setStatus(2);
            List<MasterSetPriceEntity> query1 = masterSetPriceMapper.query(masterSetPriceEntity);
            if (query1!=null&&query1.size()>0){
                for (MasterSetPriceEntity setPriceEntity : query1) {
                    UserFollowMechanismEntity userFollowMechanismEntity = new UserFollowMechanismEntity();
                    userFollowMechanismEntity.setMaster_set_price_id(setPriceEntity.getId());
                    userFollowMechanismEntity.setStatus(3);
                    int i = new Random().nextInt(300);
                    System.out.println("random i "+i);
                    while (i>0){
                        userFollowMechanismEntity.setUser_id((long)(Math.round(10000)));
                        mapper.insertSelective(userFollowMechanismEntity);
                        i--;
                    }

                }
            }
            UserFollowMechanismEntity userFollowMechanismEntity = new UserFollowMechanismEntity();
            userFollowMechanismEntity.setMechanism_id(masterMechanismEntity.getId());
            userFollowMechanismEntity.setStatus(3);
            int i = new Random().nextInt(300);
            System.out.println("random i "+i);
            while (i>0){
                userFollowMechanismEntity.setUser_id((long)(Math.round(10000)));
                mapper.insertSelective(userFollowMechanismEntity);
                i--;
            }
        }
        return ResultUtil.success();
    }
}