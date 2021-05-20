package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.MechanismExamScoreEntity;
import com.huihe.eg.user.mybatis.dao.MechanismExamScoreMapper;
import com.huihe.eg.user.service.dao.MechanismExamScoreService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class MechanismExamScoreServiceImpl extends BaseFrameworkServiceImpl<MechanismExamScoreEntity, Long> implements MechanismExamScoreService {

    @Resource
    private MechanismExamScoreMapper mapper;
    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional
    public ResultParam insertExamScore(MechanismExamScoreEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] upper_limits = param.getUpper_limits().split(",");
            String[] lower_limits = param.getLower_limits().split(",");
            String[] levels = param.getLevels().split(",");
            for (int i = 0; i < levels.length; i++) {
                param.setLevel(levels[i].toUpperCase());
                param.setLower_limit(Integer.parseInt(lower_limits[i]));
                param.setUpper_limit(Integer.parseInt(upper_limits[i]));
                mapper.insertSelective(param);
            }
            return ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }
}