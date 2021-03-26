package com.huihe.eg.user.service.impl.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.RechargeInfoEntity;
import com.huihe.eg.user.mybatis.dao.RechargeInfoMapper;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.pay.AliPayParam;
import com.huihe.eg.user.mybatis.dao.RechargeRecordMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.service.dao.pay.AliPayService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;


/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 14:43
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private AliPayParam aliPayParam;
    private AlipayClient alipayClient;

    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RechargeInfoMapper rechargeInfoMapper;

    public AliPayServiceImpl() {
    }

    @Override
    public void init() {
        if (this.alipayClient == null) {
            /** 初始化 **/
            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();

            /** 支付宝网关 **/
            certAlipayRequest.setServerUrl(aliPayParam.getUrl());

            /** 应用id，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/190/201602493024 **/
            certAlipayRequest.setAppId(aliPayParam.getApp_id());

            /** 应用私钥, 如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602471154?ant_source=antsupport  **/
            certAlipayRequest.setPrivateKey(aliPayParam.getApp_private_key());

            /** 应用公钥证书路径，下载后保存位置的绝对路径  **/
            certAlipayRequest.setCertPath(aliPayParam.getApp_cert_path());

            /** 支付宝公钥证书路径，下载后保存位置的绝对路径 **/
            certAlipayRequest.setAlipayPublicCertPath(aliPayParam.getAlipay_cert_path());

            /** 支付宝根证书路径，下载后保存位置的绝对路径 **/
            certAlipayRequest.setRootCertPath(aliPayParam.getAlipay_root_cert_path());

            /** 设置签名类型 **/
            certAlipayRequest.setSignType(aliPayParam.getSign_type());

            /** 设置请求格式，固定值json **/
            certAlipayRequest.setFormat(aliPayParam.getFormat());

            /** 设置编码格式 **/
            certAlipayRequest.setCharset(aliPayParam.getCharset());

            try {
                alipayClient = new DefaultAlipayClient(certAlipayRequest);
            } catch (AlipayApiException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public AlipayClient getAlipayClient() {
        this.init();
        return this.alipayClient;
    }


    @Override
    public String getAlipayAuthUrl(String auth_url, String app_id, String scope, String redirect_uri, String state) {
        this.init();
        StringBuilder stringBuilder = new StringBuilder(this.aliPayParam.getAuth_url());
        try {
            stringBuilder.append("auth_url").append("?app_id=").append(app_id).append("&scope=").append(scope).append("&redirect_uri=").append(URLEncoder.encode(redirect_uri, "utf-8")).append("&state=").append(state);
            RechargeInfoEntity rechargeInfoEntity = new RechargeInfoEntity();
            rechargeInfoEntity.setState(state);
            rechargeInfoEntity.setIdentity_type("ALIPAY_USER_ID");
            rechargeInfoMapper.insertSelective(rechargeInfoEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.logger.warn("url=" + stringBuilder.toString());

        return stringBuilder.toString();
    }

    @Override
    public String getAlipayAuthUrl() {
        return this.getAlipayAuthUrl(this.aliPayParam.getAuth_url(), this.aliPayParam.getApp_id(), this.aliPayParam.getScope(), this.aliPayParam.getRedirect_url(), UUID.randomUUID().toString());
    }

    @Override
    public String getAliUserAuthUrl() throws AlipayApiException {
        return this.getAliUserAuthUrl(this.aliPayParam.getApi_name(), this.aliPayParam.getMethod(), this.aliPayParam.getApp_id(), this.aliPayParam.getApp_name(),
                this.aliPayParam.getBiz_type(), this.aliPayParam.getPid(), this.aliPayParam.getProduct_id(), this.aliPayParam.getScope(), UUID.randomUUID().toString(),
                this.aliPayParam.getAuth_type(), this.aliPayParam.getSign_type());
    }

    @Override
    public String getAliUserAuthUrl(String api_name, String method, String app_id, String app_name, String biz_type, String pid, String product_id, String scope, String target_id, String auth_type, String sign_type) throws AlipayApiException {
        this.init();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("api_name=").append(api_name).append("&method=").append(method).append("&app_id=").append(app_id).append("&app_name=").append(app_name)
                .append("&biz_type=").append(biz_type).append("&pid=").append(pid).append("&product_id=").append(product_id).append("&scope=").append(scope)
                .append("&target_id=").append(target_id).append("&auth_type=").append(auth_type).append("&sign_type=").append(sign_type);
        String sign = AlipaySignature.rsaSign(stringBuilder.toString(), this.aliPayParam.getApp_private_key(), this.aliPayParam.getCharset(), this.aliPayParam.getSign_type());
        stringBuilder.append("&sign=").append(sign);
        this.logger.warn("url=" + stringBuilder.toString());

        return stringBuilder.toString();
    }

    @Override
    public ResultParam aliPrepayment(RechargeRecordEntity entity, String payee_logon_id) throws AlipayApiException {
        this.init();

        AlipayFundAuthOrderAppFreezeRequest request = new AlipayFundAuthOrderAppFreezeRequest();

        AlipayFundAuthOrderAppFreezeModel model = new AlipayFundAuthOrderAppFreezeModel();
        model.setOrderTitle("教付宝预授权");
        model.setOutOrderNo(entity.getFlowing_no()); //替换为实际订单号
        model.setOutRequestNo(entity.getFlowing_no()); //替换为实际请求单号，保证每次请求都是唯一的
        model.setPayeeLogonId(aliPayParam.getPayeeLogonId());
        model.setProductCode("PRE_AUTH_ONLINE"); //PRE_AUTH_ONLINE为固定值，不要替换
        model.setAmount(entity.getAmount().setScale(1, BigDecimal.ROUND_UP).toString());
        model.setPayTimeout("3d");
//        model.setSceneCode( "O2O_AUTH_COMMON_SCENE" );
        //需要支持信用授权，该字段必传
//        model.setExtraParam( "{\"category\":\"POSTPAY_LOCAL_SERVICE\",\"outStoreCode\":\"charge001\",\"serviceId\":\"\",\"outStoreAlias\":\"教付宝预付款\"}" );  //outStoreAlias将在用户端信用守护、支付信息、账单详情页展示
        //选填字段，指定支付渠道
//        model.setEnablePayChannels("[{\"payChannelType\":\"PCREDIT_PAY\"},{\"payChannelType\":\"MONEY_FUND\"},{\"payChannelType\":\"CREDITZHIMA\"}]");

        request.setBizModel(model);
        request.setNotifyUrl(aliPayParam.getPrepayment_url());
        AlipayFundAuthOrderAppFreezeResponse response = alipayClient.sdkExecute(request);
        if (response.isSuccess()) {
            return ResultUtil.success(response.getBody());
        } else {
            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
        }
    }

    @Override
    public ResultParam aliPrepayment2Pay(RechargeRecordEntity entity, String payer_user_id) throws AlipayApiException {
        this.init();

        AlipayTradePayRequest request = new AlipayTradePayRequest();

        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setOutTradeNo(entity.getFlowing_no());  // 预授权转支付商户订单号，为新的商户交易流水号；如果重试发起扣款，商户订单号不要变；
        model.setProductCode("PRE_AUTH_ONLINE");  // 固定值PRE_AUTH_ONLINE
        model.setAuthNo(entity.getFlowing_no());  // 填写预授权冻结交易号
        model.setSubject("教付保预授权支付");  // 解冻转支付标题，用于展示在支付宝账单中
        model.setTotalAmount(entity.getAmount().toString());  // 结算支付金额
        model.setSellerId(aliPayParam.getPid());  // 填写卖家支付宝账户pid
        model.setBuyerId(entity.getPayer_userid());  // 填写预授权用户uid，通过预授权冻结接口返回的payer_user_id字段获取
//        model.setStoreId( "test_store_id" );  // 填写实际交易发生的终端编号，与预授权的outStoreCode保持一致即可
        model.setBody("预授权解冻转支付");  // 可填写备注信息
        model.setAuthConfirmMode("COMPLETE"); //必须使用COMPLETE,传入该值用户剩余金额会自动解冻

        request.setBizModel(model);
        request.setNotifyUrl(aliPayParam.getPrepayment_url()); //异步通知地址，必填，该接口只通过该参数进行异步通知

        AlipayTradePayResponse response = alipayClient.certificateExecute(request);
        if (response.isSuccess()) {
            logger.info("response: {}" + response.getBody());
            return ResultUtil.success(response.getBody());
        } else {
            return ResultUtil.error(1000000, "预授权转支付失败");
        }
    }

    @Override
    public synchronized void aliPrepayment2Cancel(RechargeRecordEntity recordEntity) throws AlipayApiException {
        this.init();
        AlipayFundAuthOperationCancelRequest request = new AlipayFundAuthOperationCancelRequest();
        AlipayFundAuthOperationCancelModel model = new AlipayFundAuthOperationCancelModel();
//        model.setAuthNo(recordEntity.getAuth_no()); //商户的授权资金订单号，与支付宝的授权资金订单号不能同时为空
//        model.setOperationId(recordEntity.getOperation_id()); //商户的授权资金订单号，与支付宝的授权资金订单号不能同时为空
        model.setAuthNo(recordEntity.getAuth_no()); //商户的授权资金订单号，与支付宝的授权资金订单号不能同时为空
        model.setOperationId(recordEntity.getOperation_id()); //商户的授权资金订单号，与支付宝的授权资金订单号不能同时为空
        model.setRemark("预授权撤销"); // 商户对本次撤销操作的附言描述，长度不超过100个字母或50个汉字
        request.setBizModel(model);
        request.setNotifyUrl(aliPayParam.getPrepayment_url());//异步通知地址，必填，该接口只通过该参数进行异步通知

        AlipayFundAuthOperationCancelResponse response = alipayClient.certificateExecute(request);
        logger.info("response: {}" + response.getBody());
        if (!response.getCode().equals("SUCCESS")) {
            throw new AlipayApiException("解冻失败");
        }
    }

    @Override
    public ResultParam aliPayTradeRefund(RechargeRecordEntity entity) {
        this.init();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(entity.getTrans_no());
        model.setTradeNo(entity.getTrans_no());
        model.setRefundAmount(entity.getAmount().toString());
        model.setRefundCurrency("CNY");
        model.setRefundReason("优惠券退款");
        try {
            AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
            if(response.isSuccess()){
                return ResultUtil.success();
            } else {
                System.out.println("调用失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(OrderEum.order_70029.getCode(), OrderEum.order_70029.getDesc());
    }

    @Override
    public String app(String order_code, String title, String passbackParams, BigDecimal bigDecimal) throws AlipayApiException {
        this.init();
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        if (StringUtil.isNotEmpty(passbackParams)) {
            model.setPassbackParams(passbackParams);
        }
        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else {
            model.setSubject(StringUtil.isEmpty(title) ? "订单-" + CommonUtils.generateOrderCode() : title);
            model.setOutTradeNo(order_code);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(bigDecimal.setScale(2, 4).toString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            alipayTradeAppPayRequest.setBizModel(model);
            alipayTradeAppPayRequest.setNotifyUrl(this.aliPayParam.getNotify_url());
            //this.alipayClient.execute(alipayTradeAppPayRequest).get();
            return this.alipayClient.sdkExecute(alipayTradeAppPayRequest).getBody();
        }
    }

    @Override
    public String wap(String order_code, String title, String passbackParams, BigDecimal bigDecimal) throws AlipayApiException {
        this.init();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        if (StringUtil.isNotEmpty(passbackParams)) {
            model.setPassbackParams(passbackParams);
        }

        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getReturn_url())) {
            throw new RuntimeException("返回的url不能为空");
        } else {
            model.setSubject(StringUtil.isEmpty(title) ? "订单-" + CommonUtils.generateOrderCode() : title);
            model.setOutTradeNo(order_code);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(bigDecimal.setScale(2, 4).toString());
            model.setProductCode("QUICK_WAP_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(this.aliPayParam.getNotify_url());
            request.setReturnUrl(this.aliPayParam.getReturn_url());
            return this.alipayClient.pageExecute(request).getBody();
        }
    }

    @Override
    public String pc(String order_code, String title, String passbackParams, BigDecimal bigDecimal) throws AlipayApiException {
        this.init();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        if (StringUtil.isNotEmpty(passbackParams)) {
            model.setPassbackParams(passbackParams);
        }

        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getReturn_url())) {
            throw new RuntimeException("返回的url不能为空");
        } else {
            model.setSubject(StringUtil.isEmpty(title) ? "订单-" + CommonUtils.generateOrderCode() : title);
            model.setOutTradeNo(order_code);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(bigDecimal.setScale(2, 4).toString());
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(this.aliPayParam.getNotify_url());
            request.setReturnUrl(this.aliPayParam.getReturn_url());
            return this.alipayClient.pageExecute(request).getBody();
        }
    }

    /**
     * 提现,兑换
     *
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String appExchange(String order_code, BigDecimal bigDecimal, String account) throws AlipayApiException {
        this.init();
        Map<String, Object> map = new HashMap<>();
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else {
            map.put("out_biz_no", order_code);
            map.put("payee_type", "ALIPAY_LOGONID");
            map.put("payee_account", account);
            map.put("amount", bigDecimal);
            alipayTradeAppPayRequest.setBizContent(map.toString());
            alipayTradeAppPayRequest.setNotifyUrl(this.aliPayParam.getNotify_url());
            return this.alipayClient.execute(alipayTradeAppPayRequest).getBody();
        }
    }

    /**
     * 退款
     *
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String appRefund(RechargeRecordEntity rechargeRecordEntity) throws AlipayApiException {
        this.init();
        Map<String, Object> map = new HashMap<>();
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        if (StringUtil.isEmpty(rechargeRecordEntity.getFlowing_no())) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else {
            map.put("out_trade_no", rechargeRecordEntity.getFlowing_no());
            map.put("refund_amount", rechargeRecordEntity.getAmount());
            map.put("refund_currency", "USD");
            alipayTradeAppPayRequest.setBizContent(map.toString());
            alipayTradeAppPayRequest.setNotifyUrl(this.aliPayParam.getNotify_url());
            return this.alipayClient.execute(alipayTradeAppPayRequest).getBody();
        }
    }

    @Override
    public String aliTransfer(String order_code, String title, String account, String name, String type, BigDecimal amount, Long user_id) throws AlipayApiException {
        this.init();
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else {
            //商户端的唯一订单号，对于同一笔转账请求，商户需保证该订单号唯一。
            map.put("out_biz_no", order_code);
            //订单总金额，单位为元，精确到小数点后两位，STD_RED_PACKET产品取值范围
            map.put("trans_amount", amount.setScale(2, 4).toString());
            //业务产品码，单笔无密转账到支付宝账户固定为: TRANS_ACCOUNT_NO_PWD； 单笔无密转账到银行卡固定为: TRANS_BANKCARD_NO_PWD; 收发现金红包固定为:STD_RED_PACKET；
            map.put("product_code", "TRANS_ACCOUNT_NO_PWD");
            //描述特定的业务场景，可传的参数如下：DIRECT_TRANSFER：单笔无密转账到支付宝/银行卡, B2C现金红包; PERSONAL_COLLECTION：C2C现金红包-领红包
            map.put("biz_scene", "DIRECT_TRANSFER");
            //转账业务的标题，用于在支付宝用户的账单里显示
            map.put("order_title", title);
            HashMap<String, Object> payee_info = new HashMap<>();
            payee_info.put("identity", account);
            //参与方的标识类型，目前支持如下类型：1、ALIPAY_USER_ID 支付宝的会员ID 2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式
            payee_info.put("identity_type", type);
            //参与方真实姓名，如果非空，将校验收款支付宝账号姓名一致性。当 identity_type=ALIPAY_LOGON_ID时，本字段必填。
            payee_info.put("name", name);
            //收款方信息
            map.put("payee_info", payee_info);
            //其他注释
            map.put("remark", title);

            AlipayFundTransUniTransferRequest alipayFundTransUniTransferRequest = new AlipayFundTransUniTransferRequest();
            alipayFundTransUniTransferRequest.setBizContent(JSONObject.fromObject(map).toString());
            alipayFundTransUniTransferRequest.setNotifyUrl(this.aliPayParam.getNotify_url());
            AlipayFundTransUniTransferResponse response = this.alipayClient.certificateExecute(alipayFundTransUniTransferRequest);
            if ("SUCCESS".equals(response.getStatus()) && "10000".equals(response.getCode())) {
                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                rechargeRecordEntity.setPageSize(1);
                rechargeRecordEntity.setFlowing_no(order_code);
                rechargeRecordEntity = rechargeRecordMapper.queryListPage(rechargeRecordEntity).get(0);
                rechargeRecordEntity.setFinished(true);
                rechargeRecordEntity.setStatus(2);
                rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecordEntity);

                synchronized (this) {
                    userInfoMapper.updateSubCash(user_id, amount);
                }
            }
            return response.getBody();
        }
    }

    @Override
    public String getAccess_token(RechargeRecordEntity rechargeRecordEntity) {
        this.init();
        RechargeInfoEntity rechargeInfoEntity = new RechargeInfoEntity();
        rechargeInfoEntity.setAuth_code(rechargeRecordEntity.getAuth_code());
        rechargeInfoEntity.setPageSize(1);
        List<RechargeInfoEntity> rechargeInfoEntities = rechargeInfoMapper.queryListPage(rechargeInfoEntity);
        if (rechargeInfoEntities != null && rechargeInfoEntities.size() > 0) {
            AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
            request.setCode(rechargeRecordEntity.getCode());
            request.setGrantType(rechargeRecordEntity.getGrantType());
            try {
                AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
                rechargeInfoEntity = rechargeInfoEntities.get(0);
                rechargeInfoEntity.setAccess_token(oauthTokenResponse.getAccessToken());
                rechargeInfoEntity.setAliPay(oauthTokenResponse.getUserId());
                rechargeInfoEntity.setUser_id(rechargeInfoEntity.getUser_id());
                rechargeInfoEntity.setIdentity_type("ALIPAY_USER_ID");
                int i = rechargeInfoMapper.updateByPrimaryKeySelective(rechargeInfoEntity);
                if (i > 0) {
                    return "success";
                }
            } catch (AlipayApiException e) {
                //处理异常
                e.printStackTrace();
            }
        }
        return "failure";
    }

    @Override
    public String insertAccessToken(Map<String, String> params) {
        try {
            RechargeInfoEntity rechargeInfoEntity = new RechargeInfoEntity();
            rechargeInfoEntity.setState(params.get("state"));
            rechargeInfoEntity.setPageSize(1);
            List<RechargeInfoEntity> rechargeInfoEntities = rechargeInfoMapper.queryListPage(rechargeInfoEntity);
            if (rechargeInfoEntities != null && rechargeInfoEntities.size() > 0) {

                rechargeInfoEntity = rechargeInfoEntities.get(0);
                rechargeInfoEntity.setAuth_code(params.get("auth_code"));
                rechargeInfoEntity.setScope(params.get("scope"));
                rechargeInfoMapper.insertSelective(rechargeInfoEntity);
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "failure";
    }

    @Override
    public String aliUserAuth(RechargeRecordEntity rechargeRecordEntity) throws AlipayApiException {
        this.init();
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        RechargeInfoEntity rechargeInfoEntity = new RechargeInfoEntity();
        if (rechargeRecordEntity.getCode() != null) {
            request.setCode(rechargeRecordEntity.getCode());
            request.setGrantType("authorization_code");
        }
        if (rechargeRecordEntity.getRefresh_token() != null) {
            rechargeInfoEntity.setUser_id(rechargeInfoEntity.getUser_id());
            rechargeInfoEntity.setPageSize(1);
            rechargeInfoEntity = rechargeInfoMapper.queryListPage(rechargeInfoEntity).get(0);
            request.setRefreshToken(rechargeRecordEntity.getRefresh_token());
            request.setGrantType("refresh_token");
        }
        AlipaySystemOauthTokenResponse response = alipayClient.certificateExecute(request);

        rechargeInfoEntity.setAliPay(response.getUserId());
        rechargeInfoEntity.setIdentity_type("ALIPAY_USER_ID");
        rechargeInfoEntity.setUser_id(rechargeRecordEntity.getUser_id());
        rechargeInfoEntity.setRefresh_token(rechargeInfoEntity.getRefresh_token());
        if (rechargeRecordEntity.getCode() != null) {
            rechargeInfoMapper.insertSelective(rechargeInfoEntity);
        } else {
            rechargeInfoMapper.updateByPrimaryKeySelective(rechargeInfoEntity);
        }
        System.out.println(response.getBody());
        return response.getUserId();

    }

}
