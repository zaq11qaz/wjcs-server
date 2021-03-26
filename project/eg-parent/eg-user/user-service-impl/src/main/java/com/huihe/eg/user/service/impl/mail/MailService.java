package com.huihe.eg.user.service.impl.mail;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.MailParam;
import com.huihe.eg.user.model.UserEntity;
import com.huihe.eg.user.mybatis.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/18 15:04
 */
@Service
public class MailService {
    @Resource
    private MailParam param;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResultParam sendMail(String mail,String type){
        if (StringUtil.isEmpty(mail)) {
            throw new DataException(UserEum.user_10019.getCode(), UserEum.user_10019.getDesc());
        }
        if("register".equalsIgnoreCase(type)){
            UserEntity entity=new UserEntity();
            entity.setLogin_name(mail);
            entity.setPageSize(1);
            List<UserEntity> userEntities=userMapper.query(entity);
            if(userEntities!=null&&userEntities.size()>0){
                return ResultUtil.error(UserEum.user_10016.getCode(), UserEum.user_10016.getDesc());
            }
        }
        if (!isValidEmail(mail)) {
            throw new DataException(UserEum.user_10017.getCode(), UserEum.user_10017.getDesc());
        }
        String code = redisService.getStr("mail:" + mail);
        if (StringUtil.isNotEmpty(code)) {
            throw new DataException(UserEum.user_10009.getCode(), UserEum.user_10009.getDesc());
        }
        code = String.valueOf(CommonUtils.getRandNum(1000, 9999));
        System.out.println(code);
        String content = param.getContent().replace("{code}", code);
        logger.info("response after request result is :"+content);
        MailSendRequest sendRequest = new MailSendRequest(mail,param.getTitle(),content,param.getHost(),param.getAccount(),param.getPassword(),param.getPersonal());
        sendRequest.run();
        redisService.set("mail:" + mail, code, param.getSeconds());
        return ResultUtil.success();
    }

    /**
     * 验证
     *
     * @param mobile
     * @param vali_code
     */
    public ResultParam validate(String mobile, String vali_code) {
        String code = redisService.getStr("mail:" + mobile);
        if (StringUtil.isEmpty(code)) {
            return ResultUtil.error(UserEum.user_10010.getCode(), UserEum.user_10010.getDesc());
        }
        if (!code.equals(vali_code)) {
            return ResultUtil.error(UserEum.user_10011.getCode(), UserEum.user_10011.getDesc());
        }
        return ResultUtil.success(true);
    }


    /**
     * 验证邮箱号码是否正确
     * @param email
     * @return
     */
    public  boolean isValidEmail(String email) {
        String reg = "\\w+@(\\w+\\.){1,3}\\w+";
        Pattern pattern = Pattern.compile(reg);
        boolean flag = false;
        if (email != null) {
            Matcher matcher = pattern.matcher(email);
            flag = matcher.matches();
        }
        return flag;
    }
    public static void main(String[] args) {

            System.out.println(100+":"+MD5Util.GetMD5Code("US0100123456"));

    }

    public void sendPassMail(String mail, String content) {

        content = param.getPassContentI().replace("{content}", content);
        logger.info("response after request result is :"+content);
        MailSendRequest sendRequest = new MailSendRequest(mail,param.getTitle(),content,param.getHost(),param.getAccount(),param.getPassword(),param.getPersonal());
        sendRequest.run();
        redisService.set("mail:" + mail, content, param.getSeconds());

    }
}
