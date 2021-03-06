package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.mybatis.dao.MasterAppointmentMapper;
import com.huihe.eg.user.service.dao.MasterAppointmentService;
import com.huihe.eg.user.service.web.boot.MasterAppointmentSearchRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 助学师时间预约设置记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "助学师时间预约设置可用接口说明", description = "助学师时间预约设置可用接口说明", tags = {"助学师时间预约设置"})
@RestController
@RequestMapping("masterAppointment")
public class MasterAppointmentController extends BaseFrameworkController<MasterAppointmentEntity, Long> {

    @Resource
    private MasterAppointmentService service;
    @Resource
    private MasterAppointmentSearchRepository masterAppointmentSearchRepository;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;

    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 教学中心-课程表
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "教学中心-课程表")
    @GetMapping("querySchedule")
    public ResultParam querySchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySchedule(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/7 9:52
     * @ Description：机构线下课课程表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "机构线下课课程表")
    @GetMapping("queryOfflineSchedule")
    public ResultParam queryOfflineSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryOfflineSchedule(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/7 9:52
     * @ Description：机构线下课课程表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "机构线下课课程表")
    @GetMapping("queryMechanismOfflineSchedule")
    public ResultParam queryMechanismOfflineSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismOfflineSchedule(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/23 16:34
     * @ Description：分页机构线下课课程表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "分页机构线下课课程表")
    @GetMapping("queryMechanismOfflineScheduleListPage")
    public ResultParam queryMechanismOfflineScheduleListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismOfflineScheduleListPage(param, request, response));
    }

    /**
     * 根据课程时间查询老师
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "根据课程时间查询老师")
    @GetMapping("queryAppointmentListPage")
    public ResultParam queryAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryAppointmentListPage(param, request, response);
    }

    /**
     * 查询老师是否有公开直播课程
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "查询老师是否有公开直播课程")
    @GetMapping("queryClassListPage")
    public ResultParam queryClassListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryClassListPage(param, request, response));
    }

    @ApiOperation(value = "查询老师是否有公开直播课程", notes = "查询老师是否有公开直播课程")
    @RequestMapping("queryIsShow")
    @ResponseBody
    public String queryIsShow(Long user_id, Long group_id) {
        this.init();
        String result = "";
        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
        masterAppointmentEntity.setGroup_id(group_id);
        masterAppointmentEntity.setMaster_id(user_id);
        masterAppointmentEntity.setType("open_class");
        List<MasterAppointmentEntity> masterAppointmentEntities = service.queryIsClassListPage(masterAppointmentEntity);
        if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
            result = JSONUtils.obj2Json(masterAppointmentEntities.get(0)).toString();
        }
        return result;
    }

    @ApiOperation(value = "查询老师是否有公开直播课程并离开", notes = "查询老师是否有公开直播课程并离开")
    @RequestMapping("queryIsShowClose")
    @ResponseBody
    public String queryIsShowClose(Long user_id, Long group_id) {
        this.init();
        String result = "";
        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
        masterAppointmentEntity.setOffset(BigDecimal.valueOf(8));
        masterAppointmentEntity.setStart_time(new Date());
        masterAppointmentEntity.setGroup_id(group_id);
        masterAppointmentEntity.setMaster_id(user_id);
        masterAppointmentEntity.setType("open_class");
        List<MasterAppointmentEntity> masterAppointmentEntities = service.queryIsClassListPageClose(masterAppointmentEntity);
        if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
            result = JSONUtils.obj2Json(masterAppointmentEntities.get(0)).toString();
        }
        return result;
    }

    /**
     * 取消课程
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "取消课程")
    @PostMapping("deleteAppointment")
    public ResultParam deleteAppointment(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.deleteAppointment(param, request, response);
    }


    @ApiOperation(value = "查询最新课程", notes = "查询最新课程")
    @RequestMapping("queryNewAppointment")
    @ResponseBody
    public String queryNewAppointment(Long group_id) {
        this.init();
        String str = "";
        MasterAppointmentEntity param = new MasterAppointmentEntity();
        param.setPageSize(1);
        param.setGroup_id(group_id);
        List<MasterAppointmentEntity> params = service.queryNewAppointment(param);
        if (params != null && params.size() > 0) {
            str = JSONUtils.obj2Json(params.get(0)).toString();
        }
        return str;
    }

    @ApiOperation(value = "修改存在录播", notes = "修改存在录播")
    @RequestMapping("updateRecording")
    @ResponseBody
    public void updateRecording(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        MasterAppointmentEntity param = new MasterAppointmentEntity();
        param.setPageSize(1);
        param.setId(id);
        param.setIs_recording(true);
        service.update(param, request, response);
    }

    @ApiOperation(value = "查询最新课程集合", notes = "查询最新课程集合")
    @RequestMapping("queryNewAppointments")
    @ResponseBody
    public String queryNewAppointments(Long group_id) {
        this.init();
        String str = "";
        Map<String, Object> map = new HashMap<>();
        MasterAppointmentEntity historyParam = new MasterAppointmentEntity();
        historyParam.setGroup_id(group_id);
        historyParam.setType("open_class");
        historyParam.setStatus(2);
        List<MasterAppointmentEntity> historyParams = service.queryNewAppointment(historyParam);//结束课程
        if (historyParams != null && historyParams.size() > 0) {
            map.put("history_info", JSONUtils.list2Json(historyParams).toString());
        }
        MasterAppointmentEntity beingParam = new MasterAppointmentEntity();
        beingParam.setGroup_id(group_id);
        beingParam.setStatus(3);
        beingParam.setType("open_class");
        List<MasterAppointmentEntity> beingParams = service.queryNewAppointment(beingParam);
        MasterAppointmentEntity willParam = new MasterAppointmentEntity();
        willParam.setGroup_id(group_id);
        willParam.setStatus(1);
        willParam.setType("open_class");
        List<MasterAppointmentEntity> willParams = service.queryNewAppointment(willParam);
        beingParams.addAll(willParams);
        if (beingParams.size() > 0) {
            map.put("begin_info", JSONUtils.list2Json(beingParams));
        }
        str = map.toString();
        return str;
    }

    /**
     * 查询历史课程
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "查询历史课程")
    @GetMapping("queryHistoryListPage")
    public ResultParam queryHistoryListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHistoryListPage(param, request, response));
    }

    @ApiOperation(value = "查询正在开播和待开播课程")
    @GetMapping("queryBeginListPage")
    public ResultParam queryBeginListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryBeginListPage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/18 18:12
     * @ Description：查询正在开播和待开播课程PC
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询正在开播和待开播课程PC")
    @GetMapping("queryBeginListPageCount")
    public ResultParam queryBeginListPageCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryBeginListPageCount(param, request, response));
    }

    @ApiOperation(value = "查询首页课程记录")
    @GetMapping("queryHomeListPage")
    public ResultParam queryHomeListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHomeListPage(param, request, response));
    }

    @RequestMapping("queryAppointmentInfo")
    @ResponseBody
    public String queryAppointmentInfo(Long Aappointment_id, HttpServletRequest request, HttpServletResponse response) {
        return JSONUtils.obj2Json(service.findById(Aappointment_id, request, response)).toString();
    }

    @RequestMapping("queryAppointmentCount")
    @ResponseBody
    public Integer queryAppointmentCount(Long group_id, HttpServletRequest request, HttpServletResponse response) {
        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
        masterAppointmentEntity.setGroup_id(group_id);
        return service.queryListPageCount(masterAppointmentEntity, request, response);
    }

    /**
     * 查询课程详情
     *
     * @author zwx
     * @date 2020年5月15日18:47:51
     * @since JDK1.8
     */

    @ApiOperation(value = "查询课程详情")
    @GetMapping("queryDetailsListPage")
    public ResultParam queryDetailsListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryDetailsListPage(param, request, response));
    }

    /**
     * 审核
     *
     * @author zwx
     * @date 2020年6月29日17:02:50
     * @since JDK1.8
     */
    @ApiOperation(value = "审核")
    @PostMapping("masterAppointmentAudit")
    public ResultParam masterAppointmentAudit(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.masterAppointmentAudit(param, request, response);
    }

    /**
     * 查询机构课程
     *
     * @author zwx
     * @date 2020年6月29日20:10:57
     * @since JDK1.8
     */

    @ApiOperation(value = "查询机构课程")
    @GetMapping("queryMechanismAppointmentListPage")
    public ResultParam queryMechanismAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismAppointmentListPage(param, request, response));
    }

    /**
     * 机构课程统计
     *
     * @author: zwy
     * @date: 15:16 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构课程统计")
    @GetMapping("mechanismCourseCount")
    public ResultParam mechanismCourseCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.mechanismCourseCount(param, request, response));
    }

    /**
     * 查询机构老师课程
     *
     * @author zwx
     * @date 2020年7月8日21:19:25
     * @since JDK1.8
     */
    @ApiOperation(value = "查询机构老师课程")
    @GetMapping("queryMechanismSchedule")
    public ResultParam queryMechanismSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismSchedule(param, request, response));
    }

    /**
     * 查询直播收益
     *
     * @author zwx
     * @date 2020年7月13日20:55:05
     * @since JDK1.8
     */
    @ApiOperation(value = "查询直播收益")
    @GetMapping("queryProfitListPage")
    public ResultParam queryProfitListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryProfitListPage(param, request, response));
    }

    /**
     * 查询上门课程列表
     *
     * @author: zwy
     * @date 2020/07/21 20:32
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询上门课程列表")
    @GetMapping("queryOfflineMaster")
    public ResultParam queryOfflineMaster(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryOfflineMaster(param, request, response));
    }

    /**
     * 查询课程统计
     *
     * @author: zwy
     * @date 2020/07/21 21:23
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询课程统计")
    @GetMapping("countAppoinment")
    public ResultParam countAppoinment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.countAppoinment(param, request, response));
    }

    /**
     * 根据时间查询各语言数量
     *
     * @author: zwy
     * @date 2020/07/21 21:23
     * @since: JDK1.8
     */
    @ApiOperation(value = "根据时间查询各语言数量")
    @GetMapping("queryEachType")
    public ResultParam queryEachType(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryEachType(param, request, response));
    }

    /**
     * 根据时间查询各类型数量
     *
     * @author: zwy
     * @date 2020/07/21 21:23
     * @since: JDK1.8
     */
    @ApiOperation(value = "根据时间查询各类型数量")
    @GetMapping("queryCourseCount")
    public ResultParam queryCourseCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCourseCount(param, request, response));
    }

    /**
     * 根据条件查询课程
     *
     * @author: zwy
     * @date 2020/07/21 21:23
     * @since: JDK1.8
     */
    @ApiOperation(value = "根据条件查询课程")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * 查询个人统计
     *
     * @author : zwy
     * 2020-08-10 10:51
     * @since JDK1.8
     */
    @ApiOperation(value = "查询个人统计")
    @GetMapping("queryCountNum")
    public ResultParam queryCountNum(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCountNum(param, request, response));
    }

    /**
     * 批量修改主页不展示
     */
    @ApiOperation(value = "批量修改主页不展示")
    @PostMapping("updateShowList")
    public ResultParam updateShowList(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateShowList(param, request, response);
    }

    /**
     * 查询是否可以设置特约课程
     */
    @ApiOperation(value = "查询是否可以设置特约课程")
    @GetMapping("checkIsSpecial")
    public ResultParam checkIsSpecial(Long user_id, HttpServletRequest request, HttpServletResponse response) {
        return service.checkIsSpecial(user_id, request, response);
    }

    /**
     * 机构设置专属课程老师
     */
    @ApiOperation(value = "机构设置专属课程老师")
    @PostMapping("updateCourseMaster")
    public ResultParam updateCourseMaster(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateCourseMaster(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/8 21:15
     * @ Description：课程收益统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "课程收益统计")
    @GetMapping("queryLiveCash")
    public ResultParam queryLiveCash(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryLiveCash(param, request, response));
    }

    /**
     * 结算课程
     *
     * @author: zwy
     * @date: 17:45 2020/9/9
     * @since: JDk1.8
     */
    @ApiOperation(value = "结算课程")
    @PostMapping("updateSettlement")
    public ResultParam updateSettlement(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateSettlement(param, request, response);
    }

    /**
     * 查询结算课程列表
     *
     * @author: zwy
     * @date: 17:45 2020/9/9
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询结算课程列表")
    @GetMapping("querySettlementList")
    public ResultParam querySettlementList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySettlementList(param, request, response));
    }

    /**
     * 查询开始和即将开播课程
     *
     * @author: zwy
     * @date: 17:45 2020/9/9
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询开始和即将开播课程")
    @GetMapping("queryBeginClass")
    public ResultParam queryBeginClass(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryBeginClass(param, request, response));
    }

    /**
     * 查询即将开始课程列表
     *
     * @author: zwy
     * @date: 17:45 2020/9/9
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询即将开始课程列表")
    @GetMapping("querySoonList")
    public ResultParam querySoonList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySoonList(param, request, response));
    }

    /**
     * 查询历史课程列表
     *
     * @author: zwy
     * @date: 17:45 2020/9/9
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询历史课程列表")
    @GetMapping("queryHistoryList")
    public ResultParam queryHistoryList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHistoryList(param, request, response));
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/12 10:25
     * @ Description：查询课程
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询课程")
    @GetMapping("queryMasterAppointmentListPage")
    public ResultParam queryMasterAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterAppointmentListPage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/21 9:51
     * @ Description：查询机构课程统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询机构课程统计")
    @GetMapping("queryMechanismAppCount")
    public ResultParam queryMechanismAppCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismAppCount(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/21 10:10
     * @ Description：机构课程管理
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构课程管理")
    @GetMapping("queryMechanismAppointment")
    public ResultParam queryMechanismAppointment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismAppointment(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/21 10:10
     * @ Description：test
     * @since: JDk1.8
     */
    @ApiOperation(value = "test")
    @GetMapping("getQueryExpireClass")
    public ResultParam getQueryExpireClass(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        service.getQueryExpireClass();
        return ResultUtil.success();
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/27 11:42
     * @ Description：点赞
     * @since: JDk1.8
     */
    @ApiOperation(value = "点赞", notes = "点赞")
    @RequestMapping("updateLikeCount")
    @ResponseBody
    public void updateLikeCount(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
        service.updateLikeCount(id, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/27 11:42
     * @ Description：取消点赞
     * @since: JDk1.8
     */
    @ApiOperation(value = "取消点赞", notes = "取消点赞")
    @RequestMapping("updateLikeCountCancel")
    @ResponseBody
    public void updateLikeCountCancel(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
        service.updateLikeCountCancel(id, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/27 11:42
     * @ Description：分享
     * @since: JDk1.8
     */
    @ApiOperation(value = "分享", notes = "分享")
    @RequestMapping("updateShareCount")
    @ResponseBody
    public void updateShareCount(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
        service.updateShareCount(id, request, response);
    } 
    
    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/29 10:24
     * @ Description：新增全部课程资料es数据
     * @ since: JDk1.8
     */
    @ApiOperation(value = "新增全部用户资料es数据", httpMethod = "GET", notes = "新增全部用户资料es数据")
    @GetMapping({"insertEsMasterAppointment"})
    @RabbitListener(queues = "insertEsMasterAppointment")
    public void insertEsMasterAppointment() {
        masterAppointmentSearchRepository.deleteAll();
        System.out.println("deleteAllEsMasterAppointment 插入完了");
        MasterAppointmentEntity entity = new MasterAppointmentEntity();
        List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentMapper.query(entity);
        try {
            for (MasterAppointmentEntity masterAppointmentEntity : masterAppointmentEntities) {
                masterAppointmentSearchRepository.save(masterAppointmentEntity);
            }
            System.out.println("insertEsMasterAppointment 插入完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/29 10:33
     * @ Description：es 搜索 模糊查询
     * @ since: JDk1.8
     */
    @ApiOperation(value = "es 搜索 模糊查询", httpMethod = "GET", notes = "es 搜索 模糊查询")
    @GetMapping({"queryByEs"})
    public ResultParam queryByEs(MasterAppointmentEntity masterAppointmentEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = Maps.newHashMap();
        List<MasterAppointmentEntity> list;
        try {
            PageRequest page = PageRequest.of(masterAppointmentEntity.getStartRow(), masterAppointmentEntity.getPageSize());

            //方法1--------------------------------------------------------
            BoolQueryBuilder builder = this.setBuilderInfo(masterAppointmentEntity);

            if (masterAppointmentEntity.getMechanism_id()!=null){
                builder.must(QueryBuilders.matchPhraseQuery("mechanism_id", masterAppointmentEntity.getMechanism_id()));
            }

            /*
            builder.filter(QueryBuilders.termQuery("title",masterAppointmentEntity.getTitle()))
            .filter(QueryBuilders.termQuery("full_name", masterAppointmentEntity.getTitle()))
            .filter(QueryBuilders.termQuery("group_name",masterAppointmentEntity.getTitle()));

             */
            builder.should(QueryBuilders.matchQuery("title","*" + masterAppointmentEntity.getTitle() + "*").operator(Operator.AND));

                    /*
                            .should(QueryBuilders.termQuery("full_name", "*" + masterAppointmentEntity.getTitle() + "*"))
                            .should(QueryBuilders.termQuery("group_name", "*" + masterAppointmentEntity.getTitle()+ "*")));

                     */

            Page<MasterAppointmentEntity> search = masterAppointmentSearchRepository.search(builder, page);
            list = Lists.newArrayList(search);
            map.put("titleList", list);

            builder  = this.setBuilderInfo(masterAppointmentEntity);

            builder.should(QueryBuilders.matchQuery("full_name","*" + masterAppointmentEntity.getTitle() + "*").operator(Operator.AND));

            search = masterAppointmentSearchRepository.search(builder, page);
            list = Lists.newArrayList(search);
            map.put("fullNameList", list);

            builder  = this.setBuilderInfo(masterAppointmentEntity);

            builder.should(QueryBuilders.matchQuery("group_name","*" + masterAppointmentEntity.getTitle() + "*").operator(Operator.AND));

            search = masterAppointmentSearchRepository.search(builder, page);
            list = Lists.newArrayList(search);
            map.put("groupNameList", list);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    private BoolQueryBuilder setBuilderInfo(MasterAppointmentEntity masterAppointmentEntity) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if (masterAppointmentEntity.getStatus()!=null) {
                builder.must(QueryBuilders.termQuery("status", masterAppointmentEntity.getStatus()));
        }
        return builder;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/2 13:24
     * @ Description：结算 查询是否合格
     * @ since: JDk1.8
     */
    @ApiOperation(value = "结算 查询是否合格", notes = "结算 查询是否合格")
    @PostMapping("queryCourseSettlement")
    public ResultParam queryCourseSettlement(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryCourseSettlement(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/5 17:18
     * @ Description：预约创建订单
     * @ since: JDk1.8
     */
    @ApiOperation(value = "预约创建订单", notes = "预约创建订单")
    @PostMapping("insertMechanismOfflineCourse")
    public ResultParam insertMechanismOfflineCourse(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertMechanismOfflineCourse(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/10 15:42
     * @ Description：教付宝查询机构收益
     * @ since: JDk1.8
     */
    @ApiOperation(value = "教付宝查询机构收益", notes = "教付宝查询机构收益")
    @PostMapping("queryPayPalEarn")
    public ResultParam queryPayPalEarn(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryPayPalEarn(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/10 16:03
     * @ Description：教付宝查询机构收益列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "教付宝查询机构收益列表", notes = "教付宝查询机构收益列表")
    @GetMapping("queryPayPalEarnList")
    public ResultParam queryPayPalEarnList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryPayPalEarnList(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/14 17:32
     * @ Description：排课
     * @ since: JDk1.8
     */
    @ApiOperation(value = "排课", notes = "排课")
    @PostMapping("insertMechanismCourse")
    public ResultParam insertMechanismCourse(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertMechanismCourse(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/14 20:02
     * @ Description：创建机构线下课
     * @ since: JDk1.8
     */
    @ApiOperation(value = "创建机构线下课", notes = "创建机构线下课")
    @PostMapping("insertOfflineCourse")
    public ResultParam insertOfflineCourse(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertOfflineCourse(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/28 13:14
     * @ Description：queryMechanismCourse
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询机构课程列表", notes = "查询机构课程列表")
    @GetMapping("queryMechanismCourse")
    public ResultParam queryMechanismCourse( MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryMechanismCourse(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/23 11:51
     * @ Description：根据id查询状态
     * @ since: JDk1.8
     */
    @ApiOperation(value = "根据id查询状态", notes = "根据id查询状态")
    @GetMapping("queryStatusById")
    public ResultParam queryStatusById( MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryStatusById(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/23 11:51
     * @ Description：复制课程
     * @ since: JDk1.8
     */
    @ApiOperation(value = "复制课程", notes = "复制课程")
    @PostMapping("insertCopyCourse")
    public ResultParam insertCopyCourse(@RequestBody MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertCopyCourse(param, request, response);
    }

}
