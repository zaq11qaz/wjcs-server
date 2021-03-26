package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.MasterSetPriceEntity;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceMapper;
import com.huihe.eg.user.service.dao.MasterSetPriceService;
import com.huihe.eg.user.service.web.boot.MasterSetPriceSearchRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:28
 * @ Description：外教添加出售课程
 * @ since: JDk1.8
 */
@Api(value = "外教添加出售课程可用接口说明", description = "外教添加出售课程可用接口说明", tags = {"外教添加出售课程"})
@RestController
@RequestMapping("masterSetPrice")
public class MasterSetPriceController extends BaseFrameworkController<MasterSetPriceEntity, Long> {

    @Resource
    private MasterSetPriceService service;
    @Resource
    private MasterSetPriceMapper mapper;
    @Resource
    private MasterSetPriceSearchRepository masterSetPriceSearchRepository;
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void init() {
        setBaseService(service);
    }

    @Override
    public ResultParam insert(@RequestBody MasterSetPriceEntity map, HttpServletRequest request, HttpServletResponse response) {
        ResultParam insert = super.insert(map, request, response);
        if (insert.getCode() == 0) {
            masterSetPriceSearchRepository.save(mapper.selectByPrimaryKey(map.getId()));
        }
        return insert;
    }

    @Override
    public ResultParam update(@RequestBody MasterSetPriceEntity map, HttpServletRequest request, HttpServletResponse response) {
        /*
        if (map.getLive_streaming_id() == null) {
            MasterSetPriceEntity masterSetPriceEntity = mapper.selectByPrimaryKey(map.getId());
            if (masterSetPriceEntity.getActivity_id() != 0 && masterSetPriceEntity.getStatus() != 3) {
                return ResultUtil.error(OrderEum.order_70017.getCode(), OrderEum.order_70017.getDesc());
            }
        }

         */

        ResultParam update = super.update(map, request, response);
        if (update.getCode() == 0) {
            masterSetPriceSearchRepository.save(mapper.selectByPrimaryKey(map.getId()));
        }
        return update;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 11:51
     * @ Description：新增机构全部es数据
     * @ since: JDk1.8
     */
    @ApiOperation(value = "新增商品es数据", httpMethod = "GET", notes = "新增商品es数据")
    @GetMapping({"insertEsMasterSetPrice"})
    @RabbitListener(queues = "insertEsMasterSetPrice")
    public void insertEsMasterMechanism() {
        MasterSetPriceEntity entity = new MasterSetPriceEntity();
        List<MasterSetPriceEntity> masterSetPriceEntities = mapper.query(entity);
        try {

            for (MasterSetPriceEntity masterSetPriceEntity : masterSetPriceEntities) {
                if (masterSetPriceEntity.getLatitude().compareTo(new BigDecimal(0)) > 0
                        && masterSetPriceEntity.getLongitude().compareTo(new BigDecimal(0)) > 0
                        && masterSetPriceEntity.getStatus() == 2
                ) {
                    String location = masterSetPriceEntity.getLatitude() + "," + masterSetPriceEntity.getLongitude();
                    if (StringUtil.isNotEmpty(location)) {
                        masterSetPriceEntity.setLocation(location);
                    }
                }
                masterSetPriceSearchRepository.save(masterSetPriceEntity);
            }
            System.out.println("insertEsMasterSetPrice完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "删除机构全部es数据", httpMethod = "GET", notes = "删除机构全部es数据")
    @GetMapping({"deleteEsMasterSetPrice"})
    public void deleteEsMasterSetPrice() {
        try {
            masterSetPriceSearchRepository.deleteAll();
            System.out.println("deleteEsMasterSetPrice删除完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询商品列表
     *
     * @author : zwy
     * 2020-08-26 10:38
     * @since JDK1.8
     */
    @ApiOperation(value = "查询商品列表")
    @GetMapping("queryCommodityList")
    public ResultParam queryCommodityList(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCommodityList(param, request, response));
    }

    /**
     * 查询推荐课程列表
     *
     * @author: zwy
     * @date: 14:35 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询推荐课程列表")
    @GetMapping("queryRecommendList")
    public ResultParam queryRecommendList(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRecommendList(param, request, response));
    }


    /**
     * 查询商品列表
     *
     * @author : zwy
     * 2020-08-26 10:38
     * @since JDK1.8
     */
    @ApiOperation(value = "查询商品列表")
    @GetMapping("queryCommodityListByType")
    public ResultParam queryCommodityListByType(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCommodityListByType(param, request, response));
    }

    /**
     * 条件查询
     *
     * @author: zwy
     * @date: 16:28 2020/9/3
     * @since: JDk1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/3 18:41
     * @ Description：查询附近推荐课程
     * @ since: JDk1.8
     */
    /*
    @ApiOperation(value = "查询附近推荐课程")
    @GetMapping("queryNearByCourse")
    public ResultParam queryNearByCourse(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryNearByCourse(param, request, response));
    }

     */

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/15 16:09
     * @ Description：查询商品列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询商品列表")
    @GetMapping("queryCourseListPage")
    public ResultParam queryCourseListPage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCourseListPage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/25 18:13
     * @ Description：查询是否有排课商品
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询是否有排课商品")
    @GetMapping("queryIsCourse")
    public ResultParam queryIsCourse(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryIsCourse(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/5 9:39
     * @ Description：查询拼团信息
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询拼团信息")
    @GetMapping("queryGroupInfo")
    public ResultParam queryGroupInfo(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryGroupInfo(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/5 13:27
     * @ Description：更新活动信息
     * @ since: JDk1.8
     */
    @ApiOperation(value = "更新活动信息")
    @PostMapping("updateActivityInfo")
    public ResultParam updateActivityInfo(@RequestBody MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateActivityInfo(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/27 13:11
     * @ Description：查询活动列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询参加活动列表")
    @GetMapping("queryActivityListPage")
    public ResultParam queryActivityListPage(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryActivityListPage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/27 14:27
     * @ Description：
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询某种类型的参加活动列表")
    @GetMapping("queryActivityListPageByType")
    public ResultParam queryActivityListPageByType(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryActivityListPageByType(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/5 13:27
     * @ Description：根据id查询商品对象
     * @ since: JDk1.8
     */
    @ApiOperation(value = "根据id查询商品对象")
    @RequestMapping("queryMasterSetPriceById")
    @ResponseBody
    public Object queryMasterSetPriceById(Long id) {
        return service.queryMasterSetPriceById(id);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/5 13:27
     * @ Description：查询机构橱窗商品
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询机构橱窗商品")
    @RequestMapping("queryMechanismDisplay")
    public ResultParam queryMechanismDisplay(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismDisplay(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/5 13:27
     * @ Description：添加活动启蒙商品
     * @ since: JDk1.8
     */
    @ApiOperation(value = "添加活动启蒙商品")
    @PostMapping("insertActivityMasterSetPrice")
    public ResultParam insertActivityMasterSetPrice(@RequestBody MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = service.insertActivityMasterSetPrice(param, request, response);
        if (resultParam.getCode()==0){
            masterSetPriceSearchRepository.save(param);
        }
        return resultParam;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/5 13:27
     * @ Description：查询正在直播机构橱窗商品
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询正在直播机构橱窗商品")
    @RequestMapping("queryMechanismDisplayIsLiving")
    public ResultParam queryMechanismDisplayIsLiving(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismDisplayIsLiving(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/5 13:27
     * @ Description：查询课程详情
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询课程详情")
    @RequestMapping("queryDetails")
    public ResultParam queryDetails(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryDetails(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/11 14:52
     * @ Description：后台管理查询
     * @ since: JDk1.8
     */
    @ApiOperation(value = "后台管理查询")
    @GetMapping("queryByEs")
    public ResultParam queryByEs(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            PageRequest page = PageRequest.of(Math.max(param.getCurrentPage() - 1, 0), param.getPageSize());
            BoolQueryBuilder builder = QueryBuilders.boolQuery();

            if (param.getStatus() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("status", param.getStatus()));
            }

            if (param.getId() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("id", param.getId()));
            }

            if (param.getIs_recommend() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("is_recommend", param.getIs_recommend()));
            }

            if (param.getIs_attend_activities() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("is_attend_activities", param.getIs_attend_activities()));
            }

            if (param.getIs_support_teach_paypal() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("is_support_teach_paypal", param.getIs_support_teach_paypal()));
            }

            if (StringUtil.isNotEmpty(param.getCategories())) {
                builder.must(QueryBuilders.matchPhraseQuery("categories", param.getCategories()));
            }
            if (param.getMechanism_id() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("mechanism_id", param.getMechanism_id()));
            }

            if (StringUtil.isNotEmpty(param.getTitle())) {
                //名字
                builder.filter(QueryBuilders.matchPhraseQuery("title", "*" + param.getTitle() + "*"));
            }

            NativeSearchQuery nativeSearchQuery = null;
            if (StringUtil.isEmpty(param.getSortName())) {
                nativeSearchQuery = new NativeSearchQueryBuilder()
                        .withQuery(builder)
                        .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
//                    .withSort(sortBuilder)
                        .withPageable(page)
                        .build();
            } else {
                nativeSearchQuery = new NativeSearchQueryBuilder()
                        .withQuery(builder)
                        .withSort(SortBuilders.fieldSort(param.getSortName()).order(SortOrder.DESC))
                        .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
//                    .withSort(sortBuilder)
                        .withPageable(page)
                        .build();
            }

            long totalElements = template.queryForPage(nativeSearchQuery, MasterSetPriceEntity.class).getTotalElements();

            Iterable<MasterSetPriceEntity> search = masterSetPriceSearchRepository.search(nativeSearchQuery);

            List<MasterSetPriceEntity> list = Lists.newArrayList(search);
            if (list.size() > 0) {
                service.queryInfoListNew(list);
            }
            map.put("rows", list);
            map.put("total", totalElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/11 14:52
     * @ Description：查询推荐课程列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询推荐课程列表")
    @GetMapping("queryNearByCourse")
    public ResultParam queryNearByCourse(MasterSetPriceEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSetPriceEntity> list = Lists.newArrayList();
        try {
            PageRequest page = PageRequest.of(Math.max(param.getCurrentPage() - 1, 0), param.getPageSize());
            BoolQueryBuilder builder = QueryBuilders.boolQuery();

            if (param.getStatus() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("status", param.getStatus()));
            }
            if (param.getIs_recommend() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("is_recommend", param.getIs_recommend()));
            }
            if (param.getId() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("id", param.getId()));
            }
            if (param.getIs_attend_activities() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("is_attend_activities", param.getIs_attend_activities()));
            }

            if (param.getIs_support_teach_paypal() != null) {
                builder.must(QueryBuilders.matchPhraseQuery("is_support_teach_paypal", param.getIs_support_teach_paypal()));
            }

            if (StringUtil.isNotEmpty(param.getCategories())) {
                builder.must(QueryBuilders.matchPhraseQuery("categories", param.getCategories()));
            }

            if (StringUtil.isNotEmpty(param.getType())) {
                builder.must(QueryBuilders.matchPhraseQuery("type", param.getType()));
            }

            if (StringUtil.isNotEmpty(param.getTitle())) {
                //名字
                builder.filter(QueryBuilders.matchPhraseQuery("title", "*" + param.getTitle() + "*"));
            }

            GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("location", param.getLatitude().doubleValue(), param.getLongitude().doubleValue())
                    .unit(DistanceUnit.METERS)
                    .order(SortOrder.ASC);


            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    .withQuery(builder)
                    .withSort(SortBuilders.fieldSort("is_recommend").order(SortOrder.DESC))
//                    .withSort(SortBuilders.fieldSort("is_attend_activities").order(SortOrder.DESC))
                    .withSort(sortBuilder)
                    .withPageable(page)
                    .build();


            Iterable<MasterSetPriceEntity> search = masterSetPriceSearchRepository.search(nativeSearchQuery);
            list = Lists.newArrayList(search);
            if (list.size() > 0) {
                list = service.queryNearByCourse(list, request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

}