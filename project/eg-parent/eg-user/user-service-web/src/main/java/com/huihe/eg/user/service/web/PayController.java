package com.huihe.eg.user.service.web;

import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.util.EmojiUtil;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.model.pay.IosPayParam;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.RechargeRecordService;
import com.huihe.eg.user.service.dao.pay.IosPayService;
import com.huihe.eg.user.service.dao.pay.PayService;
import com.huihe.eg.user.service.dao.pay.WxPayService;
import com.huihe.eg.user.service.web.webSocket.SpringWebSocketHandler;
import com.huihe.eg.user.service.web.webSocket.WebSocketController;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 支付
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "支付",description = "支付",tags = {"支付"})
@RequestMapping("pay")
@RestController
public class PayController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PayService service;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private IosPayService iosPayService;
    @Resource
    private RechargeRecordService rechargeRecordService;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserRecommenderGroupMapper userRecommenderGroupMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource//这个注解会从Spring容器拿出Bean
    private SpringWebSocketHandler springWebSocketHandler;
    @Resource//这个注解会从Spring容器拿出Bean
    private WebSocketController webSocketController;
    @Resource//这个注解会从Spring容器拿出Bean
    private RechargeRecordRobotLogMapper rechargeRecordRobotLogMapper;

    /**
     * 支付宝支付
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "支付宝支付", httpMethod = "POST")
    @RequestMapping(value = "aliPay",method = RequestMethod.POST)
    public ResultParam aliPay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.aliPay(entity,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/5 15:16
     * @ Description：支付宝退款
     * @ since: JDk1.8
     */
    @ApiOperation(value = "支付宝退款", httpMethod = "POST")
    @RequestMapping(value = "aliPayTradeRefund",method = RequestMethod.POST)
    public ResultParam aliPayTradeRefund(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliPayTradeRefund(entity,request,response);
    }
    /**
     * 支付宝回调
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "支付宝回调",httpMethod = "POST")
    @RequestMapping(value = "aliPayNotify")
    public String aliPayNotify(HttpServletRequest request, HttpServletResponse response){
        return service.aliPayNotify(request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/5 15:16
     * @ Description：微信退款
     * @ since: JDk1.8
     */
    @ApiOperation(value = "微信退款", httpMethod = "POST")
    @RequestMapping(value = "wxPayRefund",method = RequestMethod.POST)
    public ResultParam wxPayRefund(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.wxPayRefund(entity,request,response);
    }

    /**
     * 微信支付
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "微信支付", httpMethod = "POST")
    @RequestMapping(value = "wxPay",method = RequestMethod.POST)
    public ResultParam wxPay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.wxPay(entity,request,response);
    }
    /**
     * 微信回调
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "微信回调",httpMethod = "GET")
    @RequestMapping(value = "wxPayNotify",method = RequestMethod.POST)
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response){
//        try {
//            ResultParam resultParam = this.pushNewMessage(request,response);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        Long i = service.wxPayNotify(request,response);
        this.pushNewMessage(i);
    }

    public void pushNewMessage(Long id) {
        try {
            RechargeRecordEntity rechargeRecordEntity = rechargeRecordMapper.selectByPrimaryKey(id);
                if (rechargeRecordEntity.getFinished()) {
                    List<PayUserInfoEntity> payUserInfoEntities = rechargeRecordMapper.queryPayUserList(rechargeRecordEntity);
                    PayUserInfoEntity payUserInfoEntity = payUserInfoEntities.get(0);
                    if (StringUtil.isNotEmpty(rechargeRecordEntity.getInvite_code())){
                        UserInfoEntity userInfoEntity = userInfoMapper.queryByInviteCode(rechargeRecordEntity.getInvite_code());
                        userInfoEntity.setNick_name(EmojiParser.parseToAliases(userInfoEntity.getNick_name()));
                        userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
                        payUserInfoEntity.setInviteName(userInfoEntity.getNick_name());
                        UserRecommenderEntity userRecommenderEntity = new UserRecommenderEntity();
                        userRecommenderEntity.setUser_id(userInfoEntity.getUser_id());
                        userRecommenderEntity.setType("share_recommender");
                        userRecommenderEntity.setPageSize(1);
                        List<UserRecommenderEntity> list = userRecommenderMapper.queryListPage(userRecommenderEntity);
                        if (list!=null && list.size()>0){
                            userRecommenderEntity = list.get(0);
                            if (userRecommenderEntity.getGroup_id()!=0) {
                                UserRecommenderGroupEntity userRecommenderGroupEntity = userRecommenderGroupMapper.selectByPrimaryKey(userRecommenderEntity.getGroup_id());
                                payUserInfoEntity.setInviteGroupName(userRecommenderGroupEntity.getGroup_name());
                            }
                        }
                    }
                    SpringWebSocketHandler.sendMessage(payUserInfoEntity.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "ios内购验证")
    @PostMapping(value = "iosPayNotify")
    public ResultParam iosPayNotify(@RequestBody IosPayParam param, HttpServletRequest request, HttpServletResponse response){
        return iosPayService.setIapCertificate(param,request,response);
    }

    @ApiOperation(value = "微信h5支付")
    @PostMapping(value = "wxH5Pay")
    public ResultParam wxH5Pay(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.wxH5Pay(param,request,response));
    }

    @ApiOperation(value = "退款")
    @PostMapping(value = "appRefund")
    public ResultParam appRefund(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.appRefund(param,request,response));
    }
    @ApiOperation(value = "获取wx分享签名")
    @GetMapping(value = "getWxShareSign")
    public ResultParam getWxShareSign(@RequestParam String url , HttpServletRequest request, HttpServletResponse response){
        return wxPayService.getWxShareSign(url);
    }

    /**
     * 支付宝转账
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "支付宝转账", httpMethod = "POST")
    @RequestMapping(value = "aliTransfer",method = RequestMethod.POST)
    public ResultParam aliTransfer(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliTransfer(entity,request,response);
    }

    /**
     * 支付宝拉取权限
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "H5支付宝拉取权限", httpMethod = "POST")
    @RequestMapping(value = "H5aliAuth",method = RequestMethod.POST)
    public ResultParam H5aliAuth(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.H5aliAuth(entity,request,response));
    }

    /**
     * 获得唯一标识
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "H5获得唯一标识", httpMethod = "POST")
    @RequestMapping(value = "H5aliGetToken",method = RequestMethod.POST)
    public ResultParam H5aliGetToken(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.H5aliGetToken(entity,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/29 11:47
     * @ Description：支付宝授权回调
     * @since: JDk1.8
     */
    @ApiOperation(value = "H5支付宝授权回调",httpMethod = "POST")
    @RequestMapping(value = "aliPayAuthNotify")
    public String H5aliPayAuthNotify(HttpServletRequest request, HttpServletResponse response){
        return service.H5aliPayAuthNotify(request,response);
    }
    
    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/29 15:56
     * @ Description：用户登陆授权url
     * @since: JDk1.8
     */
    @ApiOperation(value = "用户登陆授权url",httpMethod = "POST")
    @RequestMapping(value = "aliUserAuthUrl")
    public ResultParam aliUserAuthUrl(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliUserAuthUrl( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/29 15:56
     * @ Description：用户登录授权
     * @since: JDk1.8
     */
    @ApiOperation(value = "用户登录授权",httpMethod = "POST")
    @RequestMapping(value = "aliUserAuth")
    public ResultParam aliUserAuth(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliUserAuth( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/17 16:00
     * @ Description：微信转账
     * @ since: JDk1.8
     */
    @ApiOperation(value = "wx转账提现",httpMethod = "POST")
    @RequestMapping(value = "wxTransfer")
    public ResultParam wxTransfer(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.wxTransfer( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/7 16:16
     * @ Description：阿里预支付
     * @ since: JDk1.8
     */
    @ApiOperation(value = "阿里预支付",httpMethod = "POST")
    @RequestMapping(value = "aliPrepayment")
    public ResultParam aliPrepayment(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        try {
            return service.aliPrepayment( entity,request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/14 10:43
     * @ Description：支付宝预授权回调
     * @ since: JDk1.8
     */
    @ApiOperation(value = "支付宝预授权回调",httpMethod = "POST")
    @RequestMapping(value = "aliPrepaymentNotify")
    public ResultParam aliPrepaymentNotify( HttpServletRequest request, HttpServletResponse response){
        return service.aliPrepaymentNotify(request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/7 19:09
     * @ Description：授权转支付
     * @ since: JDk1.8
     */
    @ApiOperation(value = "授权转支付",httpMethod = "POST")
    @RequestMapping(value = "aliPrepayment2Pay")
    public ResultParam aliPrepayment2Pay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliPrepayment2Pay( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 17:46
     * @ Description：
     * @ since: JDk1.8
     */
    @ApiOperation(value = "支付前置",httpMethod = "POST")
    @RequestMapping(value = "payPre")
    public ResultParam payPre(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.payPre( entity,request, response);
    }    
    
    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/25 16:17
     * @ Description：取消支付
     * @ since: JDk1.8
     */
    @ApiOperation(value = "取消支付",httpMethod = "POST")
    @RequestMapping(value = "fundAuthOrderUnFreeze")
    public ResultParam fundAuthOrderUnFreeze (@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        try {
            return service.fundAuthOrderUnFreeze( entity,request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/25 16:17
     * @ Description：取消支付
     * @ since: JDk1.8
     */
    @ApiOperation(value = "插假数据",httpMethod = "POST")
    @RequestMapping(value = "insertRobot")
    @Transactional
    public synchronized ResultParam insertRobot (@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        try {
            if (entity.getCount()>100){
                return ResultUtil.error(OrderEum.order_70051.getCode(),OrderEum.order_70051.getDesc());
            }
            for (int i = 0; i < entity.getCount(); i++) {
                rechargeRecordMapper.insertRobot(entity);
                webSocketController.pushNewMessage(entity.getId());

            }
            RechargeRecordRobotLogEntity rechargeRecordRobotLogEntity = new RechargeRecordRobotLogEntity();
            rechargeRecordRobotLogEntity.setActivity_id(entity.getActivity_id());
            rechargeRecordRobotLogEntity.setMobile(entity.getLogin_name());
            rechargeRecordRobotLogEntity.setAdd_count(entity.getCount().intValue());
            rechargeRecordRobotLogMapper.insertSelective(rechargeRecordRobotLogEntity);
            return ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    

}
