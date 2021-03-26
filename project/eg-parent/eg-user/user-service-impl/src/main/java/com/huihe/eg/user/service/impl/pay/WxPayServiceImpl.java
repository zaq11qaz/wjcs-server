package com.huihe.eg.user.service.impl.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.github.wxpay.sdk.WXPayConfig;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.pay.WxPayParam;
import com.huihe.eg.user.service.dao.pay.WxPayService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 14:44
 */
@Service
public class WxPayServiceImpl implements WxPayService {
    @Resource
    public WxPayParam wxPayParam;
    @Resource
    private RedisService redisService;
    public String url = "https://api.weixin.qq.com/sns/jscode2session";
    private final List<Integer> list = Arrays.asList(1, 2, 3, 4);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public WxPayServiceImpl() {
    }

    @Override
    public Map<String, Object> getWxData() {
        return null;
    }

    @Override
    public JSONObject getSessionKeyOropenid(String code) {
        String urls = this.url + "?appid=" + this.wxPayParam.getTappId() + "&secret=" + this.wxPayParam.getTappSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        this.logger.warn("wx jscode2session req url is :" + urls);
        String string = HttpsClientRequest.httpGet(urls, null, "UTF-8");
        this.logger.warn("wx jscode2session result is " + string);
        return StringUtil.isNotEmpty(string) ? JSONObject.parseObject(string) : null;
    }
    /*
    @Override
    public JSONObject getSessionKeyOropenid(String code) {
        String urls = this.url + "?appid=" + this.wxPayParam.getxAppid() + "&secret=" + this.wxPayParam.getxAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        this.logger.warn("wx jscode2session req url is :" + urls);
        String string = HttpsClientRequest.httpGet(urls, null, "UTF-8");
        this.logger.warn("wx jscode2session result is " + string);
        return StringUtil.isNotEmpty(string) ? JSONObject.parseObject(string) : null;
    }

     */

    @Override
    public JSONObject getSessionKeyOropenidTappid(String code) {
        String urls = this.url + "?appid=" + this.wxPayParam.getTappId() + "&secret=" + this.wxPayParam.getTappId() + "&js_code=" + code + "&grant_type=authorization_code";
        this.logger.warn("wx jscode2session req url is :" + urls);
        String string = HttpsClientRequest.httpGet(urls, null, "UTF-8");
        this.logger.warn("wx jscode2session result is " + string);
        return StringUtil.isNotEmpty(string) ? JSONObject.parseObject(string) : null;
    }

    @Override
    public JSONObject getSessionKeyOropenidTAppid(String code) {
        String urls = this.url + "?appid=" + this.wxPayParam.gettAppidH() + "&secret=" + this.wxPayParam.gettAppSecretH() + "&js_code=" + code + "&grant_type=authorization_code";
        this.logger.warn("wx jscode2session req url is :" + urls);
        String string = HttpsClientRequest.httpGet(urls, null, "UTF-8");
        this.logger.warn("wx jscode2session result is " + string);
        return StringUtil.isNotEmpty(string) ? JSONObject.parseObject(string) : null;
    }

    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return
     */
    @Override
    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        byte[] dataByte = Base64.decode(encryptedData);
        byte[] keyByte = Base64.decode(sessionKey);
        byte[] ivByte = Base64.decode(iv);
        try {
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(2, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (NoSuchAlgorithmException var13) {
            this.logger.error(var13.getMessage(), var13);
        } catch (NoSuchPaddingException var14) {
            this.logger.error(var14.getMessage(), var14);
        } catch (InvalidParameterSpecException var15) {
            this.logger.error(var15.getMessage(), var15);
        } catch (IllegalBlockSizeException var16) {
            this.logger.error(var16.getMessage(), var16);
        } catch (BadPaddingException var17) {
            this.logger.error(var17.getMessage(), var17);
        } catch (UnsupportedEncodingException var18) {
            this.logger.error(var18.getMessage(), var18);
        } catch (InvalidKeyException var19) {
            this.logger.error(var19.getMessage(), var19);
        } catch (InvalidAlgorithmParameterException var20) {
            this.logger.error(var20.getMessage(), var20);
        } catch (NoSuchProviderException var21) {
            this.logger.error(var21.getMessage(), var21);
        }

        return null;
    }

    @Override
    public String returnXml(String code, String message) {
        return "<xml> <return_code><![CDATA[" + code + "]]></return_code><return_msg><![CDATA[" + message + "]]></return_msg></xml>";
    }


    /**
     * 获取微信的支付配置
     *
     * @param type 1=公众号 h5 pc 网页支付 2=小程序 3 app
     * @return
     */
    @Override
    public WXPayConfig getWxPayConfig(Integer type, final String mach_id, final String key) {
        if (!this.list.contains(type)) {
            throw new RuntimeException("类型不可用");
        } else {
            final StringBuilder appId = new StringBuilder();
            StringBuilder appSecret = new StringBuilder();
            if (type == 1) {
                appId.append(this.wxPayParam.getgAppId());
                appSecret.append(this.wxPayParam.getgAppSecret());
            } else if (type == 2) {
                appId.append(this.wxPayParam.getxAppid());
                appSecret.append(this.wxPayParam.getxAppSecret());
            } else if (type == 3) {
                appId.append(this.wxPayParam.getTappId());
                appSecret.append(this.wxPayParam.getTappSecret());
                /*
            else if (type == 3) {
                appId.append(this.wxPayParam.getAppId());
                appSecret.append(this.wxPayParam.getAppSecret());

                 */
            }else if (type == 4) {
                appId.append(this.wxPayParam.gettAppidH());
                appSecret.append(this.wxPayParam.gettAppSecretH());
            }
            return new WXPayConfig() {
                @Override
                public String getAppID() {
                    return appId.toString();
                }

                @Override
                public String getMchID() {
                    return StringUtil.isEmpty(mach_id) ? WxPayServiceImpl.this.wxPayParam.getMachId() : mach_id;
                }

                @Override
                public String getKey() {
                    return StringUtil.isEmpty(key) ? WxPayServiceImpl.this.wxPayParam.getKey() : key;
                }

                @Override
                public InputStream getCertStream() {
                    try {
                        if (StringUtil.isNotEmpty(WxPayServiceImpl.this.wxPayParam.getCertPath())) {
                            File file = new File(WxPayServiceImpl.this.wxPayParam.getCertPath());
                            InputStream certStream = new FileInputStream(file);
                            byte[] b = new byte[(int) file.length()];
                            certStream.read(b);
                            certStream.close();
                            return new ByteArrayInputStream(b);
                        }
                    } catch (Exception ignored) {
                    }
                    return null;
                }

                @Override
                public int getHttpConnectTimeoutMs() {
                    return 0;
                }

                @Override
                public int getHttpReadTimeoutMs() {
                    return 0;
                }
            };
        }
    }

    @Override
    public WXPayConfig getWxPayConfig(Integer type) {
        return this.getWxPayConfig(type, (String) null, (String) null);
    }

    /**
     * 获取JSSDK签名
     * url:当前页面的完整URL，包括参数
     **/
    @Override
    public ResultParam getWxShareSign(String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String jsapi_ticket = redisService.getStr("jsapi_ticket");
        String access_token = redisService.getStr("access_token");
        if (jsapi_ticket != null && !jsapi_ticket.equals("")) {
            Map<String, String> ret = sign(jsapi_ticket, url);
            return ResultUtil.success(ret);
        } else {
            if (StringUtil.isNotEmpty(access_token)) {
                Map<String, Object> map = new HashMap<>();
                String str = HttpsClientRequest.httpsGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi", null);
                map = JSONUtils.obj2Map(str, null);
                if (map.containsKey("errcode") && "0".equalsIgnoreCase(map.get("errcode").toString())) {
                    jsapi_ticket = map.get("ticket").toString();
                    Map<String, String> ret = sign(jsapi_ticket, url);
                    return ResultUtil.success(ret);
                }
            } else {
                Map<String, Object> map = new HashMap<>();
                String string = HttpsClientRequest.httpsGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxPayParam.getgAppId() + "&secret=" + wxPayParam.getgAppSecret(), null);
                map = JSONUtils.obj2Map(string, null);
                if (map.containsKey("access_token")) {
                    access_token = map.get("access_token").toString();
                    long aLong = Long.parseLong(map.get("expires_in").toString());
                    redisService.set("access_token", access_token, aLong);
                    String str = HttpsClientRequest.httpsGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi", null);
                    map = JSONUtils.obj2Map(str, null);
                    if (map.containsKey("errcode") && "0".equalsIgnoreCase(map.get("errcode").toString())) {
                        jsapi_ticket = map.get("ticket").toString();
                        redisService.set("jsapi_ti cket", jsapi_ticket, aLong);
                        Map<String, String> ret = sign(jsapi_ticket, url);
                        return ResultUtil.success(ret);
                    }
                } else {
                    return ResultUtil.error(UserEum.user_10009.getCode(), UserEum.user_10009.getDesc());
                }
            }
        }
        return ResultUtil.error(UserEum.user_10009.getCode(), UserEum.user_10009.getDesc());
    }

    public Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes(StandardCharsets.UTF_8));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
