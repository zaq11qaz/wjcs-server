package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.user.model.MasterSetPriceDisplayEntity;
import com.huihe.eg.user.model.MasterSetPriceEntity;
import com.huihe.eg.user.mybatis.dao.MasterMechanismMapper;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceDisplayMapper;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceMapper;
import com.huihe.eg.user.service.dao.MasterSetPriceDisplayService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;

@Service
public class MasterSetPriceDisplayServiceImpl extends BaseFrameworkServiceImpl<MasterSetPriceDisplayEntity, Long> implements MasterSetPriceDisplayService {

    @Resource
    private MasterSetPriceDisplayMapper mapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional
    public ResultParam insert(MasterSetPriceDisplayEntity masterSetPriceDisplayEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] ids = masterSetPriceDisplayEntity.getMaster_set_price_ids().split(",");
            String[] prices = masterSetPriceDisplayEntity.getLive_stream_prices().split(",");
            String[] singlePriceList = masterSetPriceDisplayEntity.getLiving_single_session_prices().split(",");

            for (int i = 0; i < ids.length; i++) {
                Long id = Long.parseLong(ids[i]);
                MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(id);
                masterSetPriceEntity.setLive_streaming_id(masterSetPriceDisplayEntity.getLive_streaming_id());
                masterSetPriceMapper.updateByPrimaryKeySelective(masterSetPriceEntity);
                if (!masterSetPriceDisplayEntity.getMechanism_id().equals(masterSetPriceEntity.getMechanism_id())) {
                    return ResultUtil.error(OrderEum.order_70028.getCode(), OrderEum.order_70028.getDesc());
                }
                masterSetPriceDisplayEntity.setTitle(masterSetPriceEntity.getTitle());
                masterSetPriceDisplayEntity.setLiving_single_session_price(new BigDecimal(singlePriceList[i]));
                masterSetPriceDisplayEntity.setMaster_set_price_id(id);
                masterSetPriceDisplayEntity.setTitle(masterSetPriceEntity.getTitle());
                masterSetPriceDisplayEntity.setLive_stream_price(new BigDecimal(prices[i]));
                masterSetPriceDisplayEntity.setMechanism_id(masterSetPriceDisplayEntity.getMechanism_id());
                masterSetPriceDisplayEntity.setOriginal_price(masterSetPriceEntity.getOriginal_price());
                masterSetPriceDisplayEntity.setPic(masterSetPriceEntity.getFace_url());
                ResultParam insert = super.insert(masterSetPriceDisplayEntity, request, response);
                if (insert.getCode() != 0) {
                    throw new Exception("插入直播列表格式错误");
                }
            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());

    }

    @Override
    public ResultParam update(MasterSetPriceDisplayEntity masterSetPriceDisplayEntity, HttpServletRequest request, HttpServletResponse response) {
        MasterSetPriceDisplayEntity masterSetPriceDisplayEntity2 = mapper.selectMasterSetPriceId(masterSetPriceDisplayEntity);
        masterSetPriceDisplayEntity.setId(masterSetPriceDisplayEntity2.getId());
        if (masterSetPriceDisplayEntity.getIs_live_streaming()!=null&&masterSetPriceDisplayEntity.getIs_live_streaming()){
            int i = mapper.updateCloseById(masterSetPriceDisplayEntity2.getLive_streaming_id());
        }
        if (masterSetPriceDisplayEntity.getStatus()!=null){
            MasterSetPriceDisplayEntity masterSetPriceDisplayEntity1 = mapper.selectByPrimaryKey(masterSetPriceDisplayEntity.getId());
            MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();

            if (masterSetPriceDisplayEntity.getStatus()==1){
                masterSetPriceEntity.setLive_streaming_id(0L);
            }else if (masterSetPriceDisplayEntity.getStatus()==2){
                masterSetPriceEntity.setLive_streaming_id(masterSetPriceDisplayEntity1.getLive_streaming_id());
            }
            masterSetPriceEntity.setId(masterSetPriceDisplayEntity1.getMaster_set_price_id());
            masterSetPriceEntity.setLive_streaming_id(masterSetPriceDisplayEntity1.getId());
            masterSetPriceMapper.updateByPrimaryKeySelective(masterSetPriceEntity);
        }
        int i = mapper.updateByPrimaryKeySelective(masterSetPriceDisplayEntity);
        return  ResultUtil.success();
    }
}