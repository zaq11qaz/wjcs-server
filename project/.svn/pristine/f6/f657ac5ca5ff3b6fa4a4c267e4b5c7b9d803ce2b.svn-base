package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.user.model.UserOrderEntity;
import com.huihe.eg.user.mybatis.dao.UserOrderMapper;
import com.huihe.eg.user.service.dao.UserOrderService;
import com.huihe.eg.user.service.web.boot.UserOrderSearchRepository;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 订单
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="订单可用接口说明",description="订单可用接口说明",tags = {"订单"})
@RestController
@RequestMapping("userOrder")
public class UserOrderController extends BaseFrameworkController<UserOrderEntity, Long> {

    @Resource
    private UserOrderService service;
    @Resource
    private UserOrderMapper orderMapper;
    @Resource
    private UserOrderSearchRepository userOrderSearchRepository;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    private TransportClient client;

    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询送礼物用户
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "查询送礼物用户", notes = "查询送礼物用户")
    @RequestMapping("queryUser")
    @ResponseBody
    public String queryUser(Long user_id, Long type_id, String type, String source) {
        this.init();
        String str = "";
        List<Long> longs = new ArrayList<>();
        UserOrderEntity orderEntity = new UserOrderEntity();
        orderEntity.setPayment_id(user_id);
        orderEntity.setType(type);
        orderEntity.setPay_id(type_id);
        orderEntity.setSource(source);
        List<UserOrderEntity> userOrderEntities = orderMapper.query(orderEntity);
        for (UserOrderEntity entity : userOrderEntities) {
            longs.add(entity.getUser_id());
        }
        if (longs.size() > 0) {
            str = JSONUtils.list2Json(longs);
        }
        return str;
    }

    /**
     * 收入榜
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "收入榜")
    @GetMapping("queryPaymentRankingListPage")
    public ResultParam queryPaymentRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryPaymentRankingListPage(param, request, response));
    }

    /**
     * 查询友圈的排行榜
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "查询友圈的排行榜")
    @GetMapping("queryPayRankingListPage")
    public ResultParam queryPayRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryPayRankingListPage(param, request, response));
    }

    /**
     * 贡献榜
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "贡献榜")
    @GetMapping("queryUserRankingListPage")
    public ResultParam queryUserRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryUserRankingListPage(param, request, response));
    }

    /**
     * 查询个人明细
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "查询个人明细")
    @GetMapping("queryDetailListPage")
    public ResultParam queryDetailListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryDetailListPage(param, request, response));
    }

    /**
     * 账单
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "账单")
    @GetMapping("queryDayListPage")
    public ResultParam queryDayListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryDayListPage(param, request, response);
    }

    /**
     * 加好友送礼物
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "加好友送礼物")
    @GetMapping("updateFreeFriends")
    public ResultParam updateFreeFriends(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateFreeFriends(param, request, response);
    }

    /**
     * 查询免费加好友次数
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "查询免费加好友次数")
    @GetMapping("queryFreeFriendsCount")
    public ResultParam queryFreeFriendsCount(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryFreeFriendsCount(param, request, response);
    }

    /**
     * 查询累计
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "查询累计")
    @GetMapping("queryListPageSum")
    public ResultParam queryListPageSum(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryListPageSum(param, request, response);
    }

    /**
     * 查询数量
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @Override
    @ApiOperation(value = "查询数量")
    @GetMapping("queryListPageCount")
    public ResultParam queryListPageCount(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return super.queryListPageCount(param, request, response);
    }

    /**
     * 查询个人明细
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "礼物中心")
    @GetMapping("queryGiftListPage")
    public ResultParam queryGiftListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryGiftListPage(param, request, response));
    }


    /**
     * 查  ++:全文检索（根据整个实体的所有属性，可能结果为0个）
     * @param
     * @return
     */
    @GetMapping("/esQuery")
    public List<UserOrderEntity> esQuery( String user_id) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(user_id);
        Iterable<UserOrderEntity> searchResult = userOrderSearchRepository.search(builder);
        Iterator<UserOrderEntity> iterator = searchResult.iterator();
        List<UserOrderEntity> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * 条件查 分页   分页
     * @return
     */
    @GetMapping("esQueryByUserId")
    public List<UserOrderEntity> searchStuDoc(UserOrderEntity userOrderEntity,HttpServletRequest request,HttpServletResponse response) {

        PageRequest page = PageRequest.of(userOrderEntity.getCurrentPage(),userOrderEntity.getPageSize());

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("user_id",userOrderEntity.getUser_id()));

        Page<UserOrderEntity> search = userOrderSearchRepository.search(builder, page);
        return Lists.newArrayList(search);

    }

    /**
     * 新增全部购买记录
     *
     * @author zwy
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "新增全部购买记录", httpMethod = "GET", notes = "新增全部购买记录")
    @GetMapping({"insertEsUserOrder"})
    @RabbitListener(queues = "insertEsUserOrder")
    public void insertEsUserOrder() {
        UserOrderEntity entity = new UserOrderEntity();
        List<UserOrderEntity> orderEntities = orderMapper.query(entity);
        try {
            for (UserOrderEntity orderEntity : orderEntities) {
                userOrderSearchRepository.save(orderEntity);
            }
            System.out.println("insertEsUserOrder 插入完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/24 13:09
     * @ Description：查询是否需要扣费
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询是否需要扣费")
    @GetMapping("queryNeedPayment")
    public ResultParam queryNeedPayment(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryNeedPayment(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/24 13:09
     * @ Description：条件查询
     * @since: JDk1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

}