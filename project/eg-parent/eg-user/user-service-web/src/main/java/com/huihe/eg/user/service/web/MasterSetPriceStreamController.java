package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MasterMechanismEntity;
import com.huihe.eg.user.model.MasterSetPriceDisplayEntity;
import com.huihe.eg.user.model.MasterSetPriceEntity;
import com.huihe.eg.user.model.MasterSetPriceStreamEntity;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceDisplayMapper;
import com.huihe.eg.user.service.dao.MasterSetPriceStreamService;
import io.swagger.annotations.Api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "直播记录表可用接口说明", description = "直播记录表可用接口说明", tags = {"直播记录表"})
@RestController
@RequestMapping("masterSetPriceStream")
public class MasterSetPriceStreamController extends BaseFrameworkController<MasterSetPriceStreamEntity, Long> {

    @Resource
    private MasterSetPriceStreamService service;
    @Resource
    private MasterSetPriceController masterSetPriceController;
    @Resource
    private MasterMechanismController masterMechanismController;
    @Resource
    private MasterSetPriceDisplayMapper masterSetPriceDisplayMapper;

    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * 分页查询设置直播列表
     *
     * @author: zwy
     * @date: 14:35 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "分页查询设置直播列表")
    @GetMapping("queryMasterSetPriceListPage")
    public ResultParam queryMasterSetPriceListPage(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterSetPriceListPage(param, request, response));
    }

    /**
     * 查询直播详情
     *
     * @author: zwy
     * @date: 14:35 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询直播详情")
    @GetMapping("queryDetail")
    public ResultParam queryDetail(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryDetail(param, request, response);
    }

    /**
     * 开播
     *
     * @author: zwy
     * @date: 14:35 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "开播")
    @RequestMapping("updateOpenLiving")
    public ResultParam updateOpenLiving(@RequestBody MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterSetPriceStreamEntity masterSetPriceStreamEntity = service.findById(param.getId(), request, response);

            MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
            mechanismEntity.setLive_streaming_id(masterSetPriceStreamEntity.getId());
            mechanismEntity.setId(masterSetPriceStreamEntity.getMechanism_id());
            masterMechanismController.update(mechanismEntity, request, response);

            MasterSetPriceDisplayEntity masterSetPriceDisplayEntity = new MasterSetPriceDisplayEntity();
            masterSetPriceDisplayEntity.setLive_streaming_id(param.getId());
            MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
            masterSetPriceDisplayEntity.setStatus(2);
            List<MasterSetPriceDisplayEntity> query = masterSetPriceDisplayMapper.query(masterSetPriceDisplayEntity);
            if (query != null && query.size() > 0) {
                for (MasterSetPriceDisplayEntity setPriceDisplayEntity : query) {
                    masterSetPriceEntity.setId(setPriceDisplayEntity.getMaster_set_price_id());
                    masterSetPriceEntity.setLive_streaming_id(masterSetPriceStreamEntity.getId());
                    masterSetPriceController.update(masterSetPriceEntity, request, response);
                }
            }

            return service.updateOpenLiving(param, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    /**
     * 关播
     *
     * @author: zwy
     * @date: 14:35 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "关播")
    @PostMapping("updateCloseLiving")
    public ResultParam updateCloseLiving(@RequestBody MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterSetPriceStreamEntity masterSetPriceStreamEntity = service.queryListPage(param, request, response).get(0);
            MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
            mechanismEntity.setLive_streaming_id(0L);
            mechanismEntity.setId(masterSetPriceStreamEntity.getMechanism_id());
            masterMechanismController.update(mechanismEntity, request, response);

            MasterSetPriceDisplayEntity masterSetPriceDisplayEntity = new MasterSetPriceDisplayEntity();
            masterSetPriceDisplayEntity.setLive_streaming_id(param.getId());
            MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
            masterSetPriceDisplayEntity.setStatus(2);
            List<MasterSetPriceDisplayEntity> query = masterSetPriceDisplayMapper.query(masterSetPriceDisplayEntity);
            if (query != null && query.size() > 0) {
                for (MasterSetPriceDisplayEntity setPriceDisplayEntity : query) {
                    masterSetPriceEntity.setId(setPriceDisplayEntity.getMaster_set_price_id());
                    masterSetPriceEntity.setLive_streaming_id(0L);
                    masterSetPriceController.update(masterSetPriceEntity, request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.updateCloseLiving(param, request, response);
    }

    /**
     * 增加点击数量
     *
     * @author: zwy
     * @date: 14:35 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "增加点击数量")
    @PostMapping("updateAddClick")
    public ResultParam updateAddClick(@RequestBody MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateAddClick(param, request, response);
    }

    /**
     * @Description: 开播
     * @Param:
     * @return:
     * @Author: zwy
     * @Date: 2021/2/25
     */
    @ApiOperation(value = "开播")
    @RequestMapping("queryIsLiveStream")
    @ResponseBody
    public String queryIsLiveStream(Long user_id, Long id) {
        String result = "";
        MasterSetPriceStreamEntity masterSetPriceStreamEntity = null;
        try {
            masterSetPriceStreamEntity = service.queryIsLiveStream(user_id, id);
            if (masterSetPriceStreamEntity != null) {
                result = JSONUtils.obj2Json(masterSetPriceStreamEntity).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description:关播
     * @Param:
     * @return:
     * @Author: zwy
     * @Date: 2021/2/25
     */
    @ApiOperation(value = "关播")
    @RequestMapping("queryIsLiveStreamClose")
    @ResponseBody
    public String queryIsLiveStream(Long id) {
        return service.queryIsLiveStreamClose(id);
    }

    /**
     * @Description:首页接口
     * @Param:
     * @return:
     * @Author: zwy
     * @Date: 2021/2/25
     */
    @ApiOperation(value = "首页接口")
    @GetMapping("queryHomeListPage")
    public ResultParam queryHomeListPage(MasterSetPriceStreamEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryHomeListPage(param,request,response);
    }


}