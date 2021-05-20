package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserRecommenderEntity;
import com.huihe.eg.user.model.UserRecommenderGroupInterlinkEntity;
import com.huihe.eg.user.mybatis.dao.UserRecommenderGroupInterlinkMapper;
import com.huihe.eg.user.mybatis.dao.UserRecommenderGroupMapper;
import com.huihe.eg.user.mybatis.dao.UserRecommenderMapper;
import com.huihe.eg.user.service.dao.UserRecommenderGroupInterlinkService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class UserRecommenderGroupInterlinkServiceImpl extends BaseFrameworkServiceImpl<UserRecommenderGroupInterlinkEntity, Long> implements UserRecommenderGroupInterlinkService {

    @Resource
    private UserRecommenderGroupInterlinkMapper mapper;
    @Resource
    private UserRecommenderGroupMapper userRecommenderGroupMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional
    public ResultParam insert(UserRecommenderGroupInterlinkEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isNotEmpty(param.getRecommender_ids())) {
                String[] split = param.getRecommender_ids().split(",");
                for (String s : split) {
                    UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.selectByPrimaryKey(Long.valueOf(s));
                    param.setRecommender_id(userRecommenderEntity.getId());
                    param.setUser_id(userRecommenderEntity.getUser_id());
                    int i = mapper.insertSelective(param);
                    if (i>0){
                        int i1 = userRecommenderGroupMapper.addGroupCount(param.getGroup_id());
                        if (i1==0){
                            throw new Exception("群组+1失败");
                        }
                    }else {
                        throw new Exception("插入推荐官群组关联表失败");
                    }
                }
                return ResultUtil.success();
            }
            return super.insert(param, request, response);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }
}