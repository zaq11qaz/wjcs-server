package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.user.model.MasterInfoEntity;
import com.huihe.eg.user.mybatis.dao.MasterInfoMapper;
import com.huihe.eg.user.service.dao.MasterInfoService;
import com.huihe.eg.user.service.impl.mail.MailService;
import com.huihe.eg.user.service.web.boot.MasterInfoSearchRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 助学师基本资料
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "助学师基本资料可用接口说明", description = "助学师基本资料可用接口说明", tags = {"助学师基本资料"})
@RestController
@RequestMapping("masterInfo")
public class MasterInfoController extends BaseFrameworkController<MasterInfoEntity, Long> {

    @Resource
    private MasterInfoService service;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private MasterInfoSearchRepository masterInfoSearchRepository;
    @Resource
    private MailService mailService;

    @Override
    public void init() {
        setBaseService(service);
    }


    @Override
    public ResultParam update(@RequestBody  MasterInfoEntity map, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam =   super.update(map, request, response);
        if (resultParam.getCode()==0){
            masterInfoSearchRepository.save(masterInfoMapper.selectByPrimaryKey(map.getId()));
        }
        return resultParam;
    }

    @Override
    public ResultParam insert(@RequestBody MasterInfoEntity map, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam =  super.insert(map, request, response);
        if (resultParam.getCode()==0){
            masterInfoSearchRepository.save(masterInfoMapper.selectByPrimaryKey(map.getId()));
        }
        return resultParam;
    }

    /**
     * 后台 认证中心 助学师认证审核
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 助学师认证审核")
    @PostMapping("masterAudit")
    public ResultParam masterAudit(@RequestBody MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.masterAudit(param, request, response);
    }

    /**
     * 删除es
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "删除es")
    @GetMapping("deleteES")
    public ResultParam deleteES( MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        masterInfoSearchRepository.deleteAll();
        System.out.println("masterInfoEs删除完了");
        return ResultUtil.success();
    }

    /**
     * 后台 认证中心 助学师认证
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 助学师认证")
    @GetMapping("queryMasterAuthListPage")
    public ResultParam queryMasterAuthListPage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterAuthListPage(masterInfoEntity, request, response));
    }

    /**
     * 教学中心
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "教学中心")
    @GetMapping("queryTeachingCenter")
    public ResultParam queryTeachingCenter(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryTeachingCenter(param, request, response));
    }

    /**
     * 助学师首页助学师
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "助学师首页助学师")
    @GetMapping("queryHomeListPage")
    public ResultParam queryHomeListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryHomeListPage(param, request, response);
    }

    /**
     * 查询私教
     *
     * @author zwx
     * @date 2020年6月24日21:15:41
     * @since JDK1.8
     */
    @ApiOperation(value = "查询私教")
    @GetMapping("queryMasterListPage")
    public ResultParam queryMasterListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterListPage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/27 10:05
     * @ Description：查询私教new
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询私教new")
    @GetMapping("queryMasterListPageNew")
    public ResultParam queryMasterListPageNew(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterListPageNew(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/11 15:34
     * @ Description：查询私教PC
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询私教PC")
    @GetMapping("queryMasterListPagePC")
    public ResultParam queryMasterListPagePC(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterListPagePC(param, request, response));
    }

    /**
     * 机构添加老师
     *
     * @author zwx
     * @date 2020年6月25日14:53:52
     * @since JDK1.8
     */
    @ApiOperation(value = "机构添加老师")
    @PostMapping("masterMechanismInsert")
    public ResultParam masterMechanismInsert(@RequestBody MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.masterMechanismInsert(param, request, response);
    }

    /**
     * 查询机构助学师
     *
     * @author zwx
     * @date 2020年6月28日17:55:48
     * @since JDK1.8
     */
    @ApiOperation(value = "查询机构助学师")
    @GetMapping("queryMechanismMasters")
    public ResultParam queryMechanismMasters(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismMasters(param, request, response));
    }

    /**
     * 设置绑定账号
     *
     * @author zwx
     * @date 2020年6月25日14:53:52
     * @since JDK1.8
     */
    @ApiOperation(value = "设置绑定账号")
    @PostMapping("masterBindingSwitch")
    public ResultParam masterBindingSwitch(@RequestBody MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.masterBindingSwitch(param, request, response);
    }

    /**
     * 外教统计
     *
     * @author: zwy
     * @date: 16:47 2020/7/7
     * @since: JDk1.8
     */
    @ApiOperation(value = "外教统计")
    @GetMapping("countMaster")
    public ResultParam countMaster(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.countMaster(param, request, response));
    }

    /**
     * 助学师认证统计
     *
     * @author: zwy
     * @date: 16:50 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "助学师认证统计")
    @GetMapping("masterAuditCount")
    public ResultParam masterAuditCount(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.masterAuditCount(param, request, response));
    }

    /**
     * 助学师统计
     *
     * @author: zwy
     * @date: 17:27 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "上门私教统计", httpMethod = "GET", notes = "上门私教统计")
    @GetMapping("helperCount")
    public ResultParam helperCount(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.helperCount(param, request, response));
    }

    /**
     * 推荐助学师
     *
     * @author: zwx
     * @date: 2020年7月10日12:17:46
     * @since: JDk1.8
     */
    @ApiOperation(value = "推荐助学师", httpMethod = "GET", notes = "推荐助学师")
    @GetMapping("queryRecommendMasterInfo")
    public ResultParam queryRecommendMasterInfo(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRecommendMasterInfo(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/11 15:27
     * @ Description：推荐助学师PC
     * @ since: JDk1.8
     */
    @ApiOperation(value = "推荐助学师PC", httpMethod = "GET", notes = "推荐助学师PC")
    @GetMapping("queryRecommendMasterInfoPC")
    public ResultParam queryRecommendMasterInfoPC(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRecommendMasterInfoPC(param, request, response));
    }

    /**
     * 根据条件查询MasterInfo
     *
     * @author: zwy
     * @date: 11:20 2020/7/10
     * @since: JDk1.8
     */
    @ApiOperation(value = "根据条件查询MasterInfo", httpMethod = "GET", notes = "根据条件查询MasterInfo")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * 根据nickname查询userId
     *
     * @author: zwy
     * @date 2020/07/27 19:47
     * @since: JDK1.8
     */
    @ApiOperation(value = "根据fullname查询userId", httpMethod = "GET", notes = "根据nickname查询userId")
    @GetMapping("queryByFullName")
    public Map<String, Object> queryByFullName(@RequestParam("full_name") String full_name, HttpServletRequest request, HttpServletResponse response) {
        return service.queryByFullName(full_name, request, response);
    }


    /**
     * 根据条件查询每种语言人数
     *
     * @author: zwy
     * @date 2020/7/13 0013 13:44
     * @since: JDK1.8
     */
    @ApiOperation(value = "根据条件查询每种语言人数", httpMethod = "GET", notes = "根据条件查询每种语言人数")
    @GetMapping("queryEachType")
    public ResultParam queryEachType(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryEachType(param, request, response));
    }

    /**
     * 查询机构私教
     *
     * @author zwx
     * @date 2020年7月15日14:50:02
     * @since JDK1.8
     */
    @ApiOperation(value = "查询机构私教")
    @GetMapping("queryMechanismPrivateListPage")
    public ResultParam queryMechanismPrivateListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismPrivateListPage(param, request, response));
    }

    /**
     * 修改机构老师登录账号
     *
     * @author: zwy
     * @date 2020/07/21 14:01
     * @since: JDK1.8
     */
    @ApiOperation(value = "修改机构老师登录账号")
    @PostMapping("queryMasterLoginInfo")
    public ResultParam queryMasterLoginInfo(MasterInfoEntity param, HttpServletResponse request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterLoginInfo(param, request, response));
    }

    /**
     * 更新机构外教状态
     *
     * @author: zwy
     * @date 2020/07/21 15:40
     * @since: JDK1.8
     */
    @ApiOperation(value = "更新机构外教状态")
    @PostMapping(value = "upDateMasterStatus")
    public ResultParam upDateMasterStatus(@RequestBody Map<String, String> map, HttpServletResponse request, HttpServletResponse response) {
        ResultParam resultParam = service.upDateMasterStatus(map, request, response);
        if (resultParam.getCode()==0){
            masterInfoSearchRepository.save(masterInfoMapper.selectByPrimaryKey(Long.parseLong(map.get("id"))));
        }
        return resultParam;
    }

    /**
     * 查询机构添加助学师信息
     *
     * @author: zwy
     * @date 2020/07/21 19:19
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询机构添加助学师信息")
    @GetMapping("queryMasterTeacherInfo")
    public ResultParam queryMasterTeacherInfo(MasterInfoEntity masterInfoEntity, HttpServletResponse request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterTeacherInfo(masterInfoEntity, request, response));
    }

    /**
     * 更新认证信息
     *
     * @author: zwy
     * @date 2020/07/21 19:19
     * @since: JDK1.8
     */
    @ApiOperation(value = "更新认证信息")
    @PostMapping("updateMasterInfo")
    public ResultParam updateMasterInfo(@RequestBody MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.updateMasterInfo(masterInfoEntity, request, response));
    }

    /**
     * 助学师冻结列表
     *
     * @author: zwy
     * @date 2020/07/21 19:19
     * @since: JDK1.8
     */
    @ApiOperation(value = "助学师冻结列表")
    @GetMapping("queryPassMaster")
    public ResultParam queryPassMaster(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryPassMaster(masterInfoEntity, request, response));
    }

    /**
     * 助学师列表
     *
     * @author: zwy
     * @date 2020/07/21 19:19
     * @since: JDK1.8
     */
    @ApiOperation(value = "助学师列表")
    @GetMapping("queryMasterList")
    public ResultParam queryMasterList(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterList(masterInfoEntity, request, response));
    }

    /**
     * 助学师分类统计
     *
     * @author: zwy
     * @date 2020/07/21 19:19
     * @since: JDK1.8
     */
    @ApiOperation(value = "助学师分类统计")
    @GetMapping("querEachMasterCount")
    public ResultParam queryEachMasterCount(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querEachMasterCount(masterInfoEntity, request, response));
    }

    /**
     * @Description: 私教查询
     * @Param:
     * @return:
     * @Author: zwy
     * @Date: 2020/7/26
     */
    @ApiOperation(value = "私教查询")
    @GetMapping("queryPrivateMaster")
    public ResultParam queryPrivateMaster(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryPrivateMaster(masterInfoEntity, request, response));
    }

    /**
     * 推荐官邀请外教入驻接口
     *
     * @author: zwy
     * @date: 11:20 2020/9/3
     * @since: JDk1.8
     */
    @ApiOperation(value = "推荐官邀请外教入驻接口")
    @PostMapping("insertInviteMaster")
    public ResultParam insertInviteMaster(@RequestBody MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return service.insertInviteMaster(masterInfoEntity, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/11 18:07
     * @ Description：查询助学师列表
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询助学师列表")
    @GetMapping("querySingleMasterListPage")
    public ResultParam querySingleMasterListPage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySingleMasterListPage(masterInfoEntity, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/15 9:52
     * @ Description：更新首页视频
     * @since: JDk1.8
     */
    @ApiOperation(value = "更新首页视频")
    @PostMapping("updateFaceVideo")
    public ResultParam updateFaceVideo(@RequestBody MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.updateFaceVideo(masterInfoEntity, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/12 9:26
     * @ Description：修改机构所属为本机构
     * @since: JDk1.8
     */
    @ApiOperation(value = "修改机构所属为本机构")
    @PostMapping("updateMechanismID")
    public ResultParam updateMechanismID(@RequestBody MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return service.updateMechanismID(masterInfoEntity, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/12 9:26
     * @ Description：本机构老师统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "本机构老师统计")
    @GetMapping("queryMechanismCount")
    public ResultParam queryMechanismCount(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismCount(masterInfoEntity, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/17 14:18
     * @ Description：查询机构助学师列表
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询机构助学师列表")
    @GetMapping("queryMechanismHelperList")
    public ResultParam queryMechanismHelperList(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismHelperList(masterInfoEntity, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/24 11:00
     * @ Description：根据语言查询
     * @ since: JDk1.8
     */
    @ApiOperation(value = "根据语言查询")
    @GetMapping("queryByLanguage")
    public ResultParam queryByLanguage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByLanguage(masterInfoEntity, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/30 10:08
     * @ Description：
     * @ since: JDk1.8
     */
    @ApiOperation(value = "更新机构老师信息")
    @PostMapping("updateMechanismMasterInfo")
    public ResultParam updateMechanismMasterInfo(@RequestBody MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = service.updateMechanismMasterInfo(masterInfoEntity, request, response);
        if (resultParam.getCode()==0){
            masterInfoSearchRepository.save(masterInfoMapper.selectByPrimaryKey(masterInfoEntity.getId()));
        }
        return resultParam;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 13:33
     * @ Description：masterINfo insert es
     * @ since: JDk1.8
     */
    @ApiOperation(value = "新增全部用户资料es数据", httpMethod = "GET", notes = "新增全部用户资料es数据")
    @GetMapping({"insertEsMasterInfo"})
    @RabbitListener(queues = "insertEsMasterInfo")
    public void insertEsMasterInfo() {
        masterInfoSearchRepository.deleteAll();
        System.out.println("deleteAllEsMasterInfo 插入完了");
        MasterInfoEntity entity = new MasterInfoEntity();
        List<MasterInfoEntity> infoEntities = masterInfoMapper.query(entity);
        try {
            for (MasterInfoEntity infoEntity : infoEntities) {
                masterInfoSearchRepository.save(infoEntity);
            }
            System.out.println("insertEsMasterInfo 插入完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/19 13:17
     * @ Description：搜索教付保外教
     * @ since: JDk1.8
     */
    @ApiOperation(value = "搜索教付保外教", httpMethod = "GET", notes = "搜索外教")
    @GetMapping({"queryTeachPayByEs"})
    public ResultParam queryTeachPayByEs(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> list = Lists.newArrayList();
        try {
            PageRequest page = PageRequest.of(Math.max(masterInfoEntity.getCurrentPage() - 1, 0), masterInfoEntity.getPageSize());
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            if (masterInfoEntity.getMechanism_id()!=null){
                builder.must(QueryBuilders.matchPhraseQuery("mechanism_id", masterInfoEntity.getMechanism_id()));
            }
            if (masterInfoEntity.getStatus()!=null){
                builder.must(QueryBuilders.matchPhraseQuery("status", masterInfoEntity.getStatus()));
            }
            if (StringUtil.isNotEmpty(masterInfoEntity.getType())){
                builder.must(QueryBuilders.matchPhraseQuery("type", masterInfoEntity.getType()));
            }
            if (StringUtil.isNotEmpty(masterInfoEntity.getSpecialty())){
                builder.must(QueryBuilders.matchPhraseQuery("specialty", "*"+masterInfoEntity.getSpecialty()+"*"));
            }
            NativeSearchQuery nativeSearchQuery = null;
            if (StringUtil.isNotEmpty(masterInfoEntity.getSortName())) {
                    nativeSearchQuery = new NativeSearchQueryBuilder()
                            .withQuery(builder)
                            .withSort(SortBuilders.fieldSort(masterInfoEntity.getSortName()).order(SortOrder.DESC))
                            .withPageable(page)
                            .build();
            } else {
                nativeSearchQuery = new NativeSearchQueryBuilder()
                        .withQuery(builder)
                        .withSort(SortBuilders.fieldSort("is_recommend").order(SortOrder.DESC))
                        .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                        .withPageable(page)
                        .build();
            }
            Iterable<MasterInfoEntity> search = masterInfoSearchRepository.search(nativeSearchQuery);
            list = Lists.newArrayList(search);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 13:38
     * @ Description：queryByEs
     * @ since: JDk1.8
     */
    @ApiOperation(value = "搜索外教", httpMethod = "GET", notes = "搜索外教")
    @GetMapping({"queryByEs"})
    public ResultParam queryByEs(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> list = Lists.newArrayList();
        try {
            if (StringUtil.isNotEmpty(masterInfoEntity.getFull_name())) {
                if (mailService.isValidEmail(masterInfoEntity.getFull_name())) {

                    PageRequest page = PageRequest.of(Math.max(masterInfoEntity.getCurrentPage() - 1, 0), masterInfoEntity.getPageSize());

                    BoolQueryBuilder builder = QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("status", "2"))
                            .must(QueryBuilders.termQuery("type", "live_lecturer"))
                            .must(QueryBuilders.termQuery("mail", masterInfoEntity.getFull_name()));

                    Page<MasterInfoEntity> search = masterInfoSearchRepository.search(builder, page);
                    list = Lists.newArrayList(search);

                } else if (CommonUtils.isPhone(masterInfoEntity.getFull_name())) {
                    PageRequest page = PageRequest.of(0, 1);

                    BoolQueryBuilder builder = QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("status", "2"))
                            .must(QueryBuilders.termQuery("type", "live_lecturer"))
                            .must(QueryBuilders.termQuery("mobile", masterInfoEntity.getFull_name()));
                    Page<MasterInfoEntity> search = masterInfoSearchRepository.search(builder, page);
                    list = Lists.newArrayList(search);

                } else {
                    PageRequest page = PageRequest.of(masterInfoEntity.getStartRow(), masterInfoEntity.getPageSize());

                    BoolQueryBuilder builder = QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("status", "2"))
                            .must(QueryBuilders.termQuery("type", "live_lecturer"))

                            .should(QueryBuilders.boolQuery()
                                    .should(QueryBuilders.termQuery("full_name", "*" + masterInfoEntity.getFull_name() + "*")));

                    Page<MasterInfoEntity> search = masterInfoSearchRepository.search(builder, page);
                    list = Lists.newArrayList(search);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

}