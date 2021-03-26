package com.huihe.eg.mall.service.impl.mail;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/18 15:05
 */
public class MailSendRequest implements Runnable{

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String email;// 收件人邮箱
    private String title;
    private String content;
    private String host;
    private String account;
    private String password;
    private String personal;
    @Override
    public void run() {
        Properties properties = System.getProperties();// 获取系统属性
        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        try {
            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            account, password); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(account,personal, "UTF-8"));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject(title);
            // 2.4设置邮件内容
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
            logger.warn("send email success");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public MailSendRequest(){};
    public MailSendRequest(String email,String title,String content,String host,String account,String password, String personal){
        super();
        this.email = email;
        this.title = title;
        this.content = content;
        this.host = host;
        this.account = account;
        this.password = password;
        this.personal = personal;
    }
}
