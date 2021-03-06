package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.model.MasterSetPriceStreamEntity;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceStreamMapper;
import com.huihe.eg.user.service.dao.MasterSetPriceDisplayService;
import com.huihe.eg.user.service.dao.MasterSetPriceStreamService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MasterSetPriceStreamServiceImpl extends BaseFrameworkServiceImpl<MasterSetPriceStreamEntity, Long> implements MasterSetPriceStreamService {

    @Resource
    private MasterSetPriceStreamMapper mapper;
    @Resource
    private MasterSetPriceDisplayMapper masterSetPriceDisplayMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterSetPriceDisplayService masterSetPriceDisplayService;
    @Resource
    private MessageApiService messageApiService;

    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional
    public ResultParam insert(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            param.setRoom_id("1400255047__" + param.getMechanism_id());
            int i = mapper.insertSelective(param);
            if (i > 0) {
                if (param.getMaster_set_price_ids() != null && param.getLive_stream_prices() != null) {
                    MasterSetPriceDisplayEntity masterSetPriceDisplayEntity = new MasterSetPriceDisplayEntity();
                    masterSetPriceDisplayEntity.setMaster_set_price_ids(param.getMaster_set_price_ids());
                    masterSetPriceDisplayEntity.setLive_stream_prices(param.getLive_stream_prices());
                    masterSetPriceDisplayEntity.setLive_streaming_id(param.getId());
                    masterSetPriceDisplayEntity.setMechanism_id(param.getMechanism_id());
                    masterSetPriceDisplayEntity.setLiving_single_session_prices(param.getLiving_single_session_prices());
                    ResultParam insert = masterSetPriceDisplayService.insert(masterSetPriceDisplayEntity, request, response);
                    if (insert.getCode() != 0) {
                        throw new Exception("????????????????????????");
                    }
                }
            }
            return ResultUtil.success(param.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    @Override
    public List<MasterSetPriceStreamEntity> queryMasterSetPriceListPage(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceStreamEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryMasterSetPriceListPage(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public MasterSetPriceStreamEntity queryIsLiveStream(Long user_id, Long id) {
        MasterSetPriceStreamEntity masterSetPriceStreamEntity = mapper.selectByPrimaryKey(id);

        /*
        MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
        mechanismEntity.setLive_streaming_id(masterSetPriceStreamEntity.getId());
        mechanismEntity.setId(masterSetPriceStreamEntity.getMechanism_id());
        int i = masterMechanismMapper.updateStreamId(mechanismEntity);

        MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
        masterSetPriceEntity.setId(masterSetPriceStreamEntity.getMaster_set_price_id());
        masterSetPriceEntity.setLive_streaming_id(masterSetPriceStreamEntity.getId());
        int i1 = masterSetPriceMapper.updateStreamId(masterSetPriceEntity);


         */



        MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterSetPriceStreamEntity.getMechanism_id());
        if (!mechanismEntity.getUser_id().equals(user_id)){
            return null;
        }
//        masterSetPriceStreamEntity.setMaster_id(user_id);
        masterSetPriceStreamEntity.setRoom_id("1400491548"+"_"+masterSetPriceStreamEntity.getMechanism_id()+"_"+user_id);
        mapper.updateByPrimaryKeySelective(masterSetPriceStreamEntity);
        return masterSetPriceStreamEntity;
    }

    @Override
    public String queryIsLiveStreamClose(Long id) {
        MasterSetPriceStreamEntity masterSetPriceStreamEntity = mapper.selectByPrimaryKey(id);

        MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
        mechanismEntity.setLive_streaming_id(0L);
        mechanismEntity.setId(masterSetPriceStreamEntity.getMechanism_id());
        int i = masterMechanismMapper.updateStreamId(mechanismEntity);

        MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
        masterSetPriceEntity.setId(masterSetPriceStreamEntity.getMaster_set_price_id());
        masterSetPriceEntity.setLive_streaming_id(0L);
        int i1 = masterSetPriceMapper.updateStreamId(masterSetPriceEntity);
        return "success";
    }

    @Override
    @Transactional
    public ResultParam updateOpenLiving(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            Date day=new Date();
            MasterSetPriceStreamEntity masterSetPriceStreamEntity = mapper.selectByPrimaryKey(param.getId());
            masterSetPriceStreamEntity.setStatus(2);
            masterSetPriceStreamEntity.setStart_time(day);
            int i2 = mapper.updateByPrimaryKeySelective(masterSetPriceStreamEntity);

            return ResultUtil.success();
//            throw new Exception("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    @Transactional
    public ResultParam updateCloseLiving(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            Date day=new Date();
            param.setEnd_time(day);
            param.setStatus(3);
            int i2 = mapper.updateByPrimaryKeySelective(param);
            return ResultUtil.success();
//            throw new Exception("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    public ResultParam queryDetail(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceStreamEntity> list = Lists.newArrayList();
        try {
            param.setPageSize(1);
            list = mapper.queryListPage(param);
            if (list != null && list.size() > 0) {
                this.setInfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

    @Override
    public ResultParam updateAddClick(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        int i = mapper.updateAddClick(param);
        return ResultUtil.success();
    }

    @Override
    public ResultParam queryHomeListPage(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceStreamEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryHomeListPage(param);
            if (list!=null&&list.size()>0){
                for (MasterSetPriceStreamEntity masterSetPriceStreamEntity : list) {
                    HashMap<String, Object> map = Maps.newHashMap();
                    map.put("masterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterSetPriceStreamEntity.getMechanism_id()));
                    map.put("replayUrl",null);
                    try {
                        map.put("replayUrl", messageApiService.queryRecordingUrlTeachPaypal(masterSetPriceStreamEntity.getId(),true));
                    }catch (Exception ignored){

                    }
                    masterSetPriceStreamEntity.setMap(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

    private void setInfo(List<MasterSetPriceStreamEntity> masterSetPriceStreamEntities) {
        for (MasterSetPriceStreamEntity masterSetPriceStreamEntity : masterSetPriceStreamEntities) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("MasterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterSetPriceStreamEntity.getMechanism_id()));
            masterSetPriceStreamEntity.setMap(map);
        }
    }
}