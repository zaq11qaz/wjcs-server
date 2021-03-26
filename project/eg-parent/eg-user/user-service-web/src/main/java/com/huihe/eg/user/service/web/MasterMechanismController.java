package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.MasterMechanismEntity;
import com.huihe.eg.user.model.MasterSetPriceEntity;
import com.huihe.eg.user.model.MasterSetPricePriceEntity;
import com.huihe.eg.user.mybatis.dao.MasterMechanismMapper;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceMapper;
import com.huihe.eg.user.mybatis.dao.MasterSetPricePriceMapper;
import com.huihe.eg.user.service.dao.MasterMechanismService;
import com.huihe.eg.user.service.web.boot.MasterMechanismSearchRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:31
 * @ Description：助学师机构信息
 * @ since: JDk1.8
 */
@Api(value = "助学师机构信息可用接口说明", description = "助学师机构信息可用接口说明", tags = {"助学师机构信息"})
@RestController
@RequestMapping("mastermechanism")
public class MasterMechanismController extends BaseFrameworkController<MasterMechanismEntity, Long> {

    @Resource
    private MasterMechanismService service;
    @Resource
    private MasterMechanismSearchRepository masterMechanismSearchRepository;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterSetPricePriceMapper masterSetPricePriceMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void init() {
        setBaseService(service);
    }

    @Override
    public ResultParam insert(@RequestBody MasterMechanismEntity map, HttpServletRequest request, HttpServletResponse response) {
        ResultParam insert = service.insert(map, request, response);
        if (insert.getCode() == 0) {
            masterMechanismSearchRepository.save(map);
        }
        return insert;
    }

    @Override
    public ResultParam delete(Long aLong, HttpServletRequest request, HttpServletResponse response) {
        ResultParam delete = super.delete(aLong, request, response);
        if (delete.getCode() == 0) {
            masterMechanismSearchRepository.deleteById(aLong);
        }
        return delete;
    }

    @Override
    public ResultParam update(@RequestBody MasterMechanismEntity map, HttpServletRequest request, HttpServletResponse response) {
        ResultParam update = service.update(map, request, response);
        masterMechanismSearchRepository.save(masterMechanismMapper.selectByPrimaryKey(map.getId()));
        return update;
    }

    /**
     * 后台 认证中心 机构认证审核
     *
     * @author zwx
     * @date 2020年6月24日21:24:55
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 机构认证审核")
    @PostMapping("mechanismAudit")
    public ResultParam mechanismAudit(@RequestBody MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = service.mechanismAudit(param, request, response);
        if (resultParam.getCode() == 0) {
            param = masterMechanismMapper.selectByPrimaryKey(param.getId());
            masterMechanismSearchRepository.save(param);
        }
        return resultParam;
    }

    /**
     * 查询机构详情
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "查询机构详情")
    @GetMapping("queryMechanismListPage")
    public ResultParam queryMechanismListPage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismListPage(param, request, response));
    }

    @ApiOperation(value = "查询机构详情new")
    @GetMapping("queryMechanismListPageNew")
    public ResultParam queryMechanismListPageNew(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismListPageNew(param, request, response));
    }

    /**
     * 查询机构详情统计
     *
     * @author zwx
     * @date 2020年6月28日21:34:52
     * @since JDK1.8
     */
    @ApiOperation(value = "查询机构详情统计")
    @GetMapping("queryMechanismStatistics")
    public ResultParam queryMechanismStatistics(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismStatistics(param, request, response));
    }

    /**
     * 查询附近机构
     *
     * @author zwx
     * @date 2020年7月3日16:15:04
     * @since JDK1.8
     */
    @ApiOperation(value = "查询附近机构")
    @GetMapping("queryNearbyListPage")
    public ResultParam queryNearbyListPage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryNearbyListPage(param, request, response));
    }

    /**
     * 机构英语统计
     *
     * @author: zwy
     * @date: 14:42 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构英语统计")
    @GetMapping("queryMasterMechanismCount")
    public ResultParam queryMasterMechanismCount(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterMechanismCount(param, request, response));
    }

    /**
     * 机构通过统计
     *
     * @author: zwy
     * @date: 14:42 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构通过统计")
    @GetMapping("masterMechanismCount")
    public ResultParam masterMechanismCount(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.masterMechanismCount(param, request, response));
    }

    /**
     * 条件查询
     *
     * @author: zwy
     * @date: 20:10 2020/7/9
     * @since: JDk1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * 查询机构列表
     *
     * @author: zwy
     * @date 2020/07/23 16:42
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询机构列表")
    @GetMapping("queryMechanismList")
    public ResultParam queryMechanismList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismList(param, request, response));
    }

    /**
     * 查询预留信息列表
     *
     * @author : zwy
     * 2020-08-27 11:25
     * @since JDK1.8
     */
    @ApiOperation(value = "查询预留信息列表")
    @GetMapping("queryAppointmentMechanismList")
    public ResultParam queryAppointmentMechanismList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryAppointmentMechanismList(param, request, response));
    }

    /**
     * 机构新增预留信息
     *
     * @author: zwy
     * @date: 11:52 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构新增预留信息")
    @PostMapping("insertAppointmentMechanismInfo")
    public ResultParam insertAppointmentMechanismInfo(@RequestBody MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertAppointmentMechanismInfo(param, request, response);
    }

    /**
     * 机构现在加盟
     *
     * @author: zwy
     * @date: 13:17 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构现在加盟")
    @PostMapping("insertMechanismInfo")
    public ResultParam insertMechanismInfo(@RequestBody MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertMechanismInfo(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/8 9:29
     * @ Description：查询机构是否有老师教室
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询机构是否有老师教室")
    @GetMapping("queryTeacherRoom")
    public ResultParam queryTeacherRoom(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryTeacherRoom(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 11:51
     * @ Description：新增机构全部es数据
     * @ since: JDk1.8
     */
    @ApiOperation(value = "新增机构全部es数据", httpMethod = "GET", notes = "新增机构全部es数据")
    @GetMapping({"insertEsMasterMechanism"})
    @RabbitListener(queues = "insertEsMasterMechanism")
    public void insertEsMasterMechanism() {
        MasterMechanismEntity entity = new MasterMechanismEntity();
        List<MasterMechanismEntity> masterMechanismEntities = masterMechanismMapper.query(entity);
        try {

            for (MasterMechanismEntity masterMechanismEntity : masterMechanismEntities) {
                if (masterMechanismEntity.getLatitude().compareTo(new BigDecimal(0)) > 0
                        && masterMechanismEntity.getLongitude().compareTo(new BigDecimal(0)) > 0
                        && masterMechanismEntity.getStatus() == 2
                ) {
                    String location = masterMechanismEntity.getLatitude() + "," + masterMechanismEntity.getLongitude();
                    if (StringUtil.isNotEmpty(location)) {
                        masterMechanismEntity.setLocation(location);
                    }
                }
                masterMechanismSearchRepository.save(masterMechanismEntity);
            }
            System.out.println("insertEsMasterMechanism完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/28 10:04
     * @ Description：删除机构全部es数据
     * @ since: JDk1.8
     */
    @ApiOperation(value = "删除机构全部es数据", httpMethod = "GET", notes = "删除机构全部es数据")
    @GetMapping({"deleteEsMasterMechanism"})
    public void deleteEsMasterMechanism() {
        try {
            masterMechanismSearchRepository.deleteAll();
            System.out.println("deleteEsMasterMechanism删除完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 11:55
     * @ Description：es 搜索 模糊查询
     * @ since: JDk1.8
     */
    @ApiOperation(value = "es 搜索 模糊查询", httpMethod = "GET", notes = "es 搜索 模糊查询")
    @GetMapping({"queryByEs"})
    public ResultParam queryByEs(MasterMechanismEntity masterMechanismEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterMechanismEntity> list = Lists.newArrayList();
        try {
            PageRequest page = PageRequest.of(Math.max(masterMechanismEntity.getCurrentPage() - 1, 0), masterMechanismEntity.getPageSize());


            BoolQueryBuilder builder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("status", "2"))
                    .filter(QueryBuilders.termQuery("mechanism_name", "*" + masterMechanismEntity.getMechanism_name() + "*"));
            Page<MasterMechanismEntity> search = masterMechanismSearchRepository.search(builder, page);
            list = Lists.newArrayList(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

    @ApiOperation(value = "es 搜索 模糊查询", httpMethod = "GET", notes = "es 搜索 模糊查询")
    @GetMapping({"queryMechanismByType"})
    public ResultParam queryMechanismByType(MasterMechanismEntity masterMechanismEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterMechanismEntity> list = Lists.newArrayList();
        try {

            if (masterMechanismEntity.getLatitude() == null && masterMechanismEntity.getLongitude() == null) {
                masterMechanismEntity.setLatitude(new BigDecimal("30.275928"));
                masterMechanismEntity.setLongitude(new BigDecimal("120.233749"));
            }

            PageRequest page = PageRequest.of(Math.max(masterMechanismEntity.getCurrentPage() - 1, 0), masterMechanismEntity.getPageSize());
            BoolQueryBuilder builder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("status", "2")
//                    .must(QueryBuilders.termQuery("type", "offline_mechanism")
                    );
            if (StringUtil.isNotEmpty(masterMechanismEntity.getType())) {
                //类型
                builder.must(QueryBuilders.matchPhraseQuery("type", masterMechanismEntity.getType()));
            }
            if (StringUtil.isNotEmpty(masterMechanismEntity.getCategories())) {
                //类目
                builder.must(QueryBuilders.matchPhraseQuery("categories", masterMechanismEntity.getCategories()));
            }
            if (StringUtil.isNotEmpty(masterMechanismEntity.getCategories_child())) {
                //子类目
                builder.filter(QueryBuilders.matchPhraseQuery("categories_child", "*" + masterMechanismEntity.getCategories_child() + "*"));
            }
            if (StringUtil.isNotEmpty(masterMechanismEntity.getMechanism_name())) {
                //名字
                builder.filter(QueryBuilders.matchPhraseQuery("mechanism_name", "*" + masterMechanismEntity.getMechanism_name() + "*"));
            }
            if (StringUtil.isNotEmpty(masterMechanismEntity.getMechanism_addr())) {
                builder.filter(QueryBuilders.matchPhraseQuery("mechanism_addr", "*" + masterMechanismEntity.getMechanism_addr() + "*"));
            }

            GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("location", masterMechanismEntity.getLatitude().doubleValue(), masterMechanismEntity.getLongitude().doubleValue())
                    .unit(DistanceUnit.METERS)
                    .order(SortOrder.ASC);

            NativeSearchQuery nativeSearchQuery = null;
            if (StringUtil.isNotEmpty(masterMechanismEntity.getSortName())) {
                if ("id".equalsIgnoreCase(masterMechanismEntity.getSortName())) {
                    nativeSearchQuery = new NativeSearchQueryBuilder()
                            .withQuery(builder)
                            .withSort(SortBuilders.fieldSort(masterMechanismEntity.getSortName()).order(SortOrder.DESC))
                            .withSort(sortBuilder)
                            .withPageable(page)
                            .build();
                } else {
                    nativeSearchQuery = new NativeSearchQueryBuilder()
                            .withQuery(builder)
                            .withSort(SortBuilders.fieldSort("sort_weight").order(SortOrder.DESC))
                            .withSort(SortBuilders.fieldSort(masterMechanismEntity.getSortName()).order(SortOrder.DESC))
                            .withSort(sortBuilder)
                            .withPageable(page)
                            .build();
                }
            } else {
                nativeSearchQuery = new NativeSearchQueryBuilder()
                        .withQuery(builder)
                        .withSort(SortBuilders.fieldSort("sort_weight").order(SortOrder.DESC))
                        .withSort(sortBuilder) //todo 经纬度排序
                        .withPageable(page)
                        .build();
            }

            Iterable<MasterMechanismEntity> search = masterMechanismSearchRepository.search(nativeSearchQuery);

            list = Lists.newArrayList(search);
            /*
            if (list.size() < 1) {
                MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
                mechanismEntity.setStatus(2);
                mechanismEntity.setPageSize(10);
                mechanismEntity.setCurrentPage(1);
                mechanismEntity.setIs_recommend(true);
                list = masterMechanismMapper.queryListPage(mechanismEntity);
            }

             */
            if (list.size() > 0) {
                /*
                //todo es计算经纬度
                if (masterMechanismEntity.getLatitude() != null && masterMechanismEntity.getLongitude() != null) {
                    list.forEach(l -> {
                        double calculate = GeoDistance.PLANE.calculate(l.getLatitude().doubleValue(), l.getLongitude().doubleValue(),
                                masterMechanismEntity.getLatitude().doubleValue(), masterMechanismEntity.getLongitude().doubleValue(), DistanceUnit.METERS);
                        l.setDistance(new BigDecimal(calculate + ""));
                    });
                }

                 */


                for (MasterMechanismEntity mechanismEntity : list) {

                    MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                    masterSetPriceEntity.setMechanism_id(mechanismEntity.getId());
                    masterSetPriceEntity.setStatus(2);
                    masterSetPriceEntity.setType("mechanism_offline");
                    masterSetPriceEntity.setIs_support_teach_paypal(true);
                    masterSetPriceEntity.setPageSize(3);
                    masterSetPriceEntity.setLongitude(masterMechanismEntity.getLongitude());
                    masterSetPriceEntity.setLatitude(masterMechanismEntity.getLatitude());
                    List<MasterSetPriceEntity> masterSetPriceEntities = masterSetPriceMapper.queryByMessage(masterSetPriceEntity);
                    HashMap<String, Object> map = Maps.newHashMap();
                    map.put("masterSetPriceEntity", masterSetPriceEntities);

                    if (masterSetPriceEntities != null && masterSetPriceEntities.size() > 0) {
                        MasterSetPricePriceEntity masterSetPricePriceEntity = new MasterSetPricePriceEntity();
                        masterSetPricePriceEntity.setMaster_set_price_id(masterSetPriceEntities.get(0).getId());
                        masterSetPricePriceEntity.setStatus(2);
                        map.put("priceList", masterSetPricePriceMapper.query(masterSetPricePriceEntity));
                    } else {
                        map.put("priceList", null);
                    }
                    mechanismEntity.setMap(map);
                }
            }
            /*
            if (list.size() > 1) {
                if (masterMechanismEntity.getLatitude() != null && masterMechanismEntity.getLongitude() != null && StringUtil.isEmpty(masterMechanismEntity.getSortName())) {
                    list = list.stream().sorted(Comparator.comparing(MasterMechanismEntity::getDistance)).collect(Collectors.toList());
                }
            }

             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

}