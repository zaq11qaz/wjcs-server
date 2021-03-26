package com.huihe.eg.push.upload;

import com.cy.framework.model.UploadParam;
import com.cy.framework.model.qiniu.QiNiuUploadParam;
import com.cy.framework.model.qiniu.QinNiuParam;
import com.cy.framework.service.dao.RedisService;
import com.huihe.eg.comm.FinalConfigParam;
import com.huihe.eg.push.model.ServiceConfig;
import com.huihe.eg.push.model.UploadAudioParam;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.List;

@Component
public class UploadService {

    @Resource
    private ServiceConfig serviceConfig;
    @Resource
    private RedisService redisService;
    @Resource
    private QinNiuParam qinNiuParam;

    public static final int[] SIZES = { 50, 100, 200, 720, 0 };
    /**
     *
     * @Description: 图片上传
     * @return List<String> 返回类型
     * @author yangjing
     * @date 2017-2-17 下午5:38:47
     */
    public List<String> comm(UploadParam param) {
        List<String> listName = new LinkedList<>();
        try {
            String fileType = null;

            StringBuilder picts = new StringBuilder();
            StringBuilder pathName = new StringBuilder();
            picts.append("/home/img/tripPictstorage/");

            pathName.append(param.getId()).append("/").append(param.getType()).append("/");
            picts.append(pathName);
            System.out.println(picts);
            File file = new File(picts.toString());
            if (!file.exists()) {
                System.out.println(file.mkdirs());
            }
            if (param.getFiles() != null && param.getFiles().size() > 0) {
                for (MultipartFile multipartFile : param.getFiles()) {
                    String oldFileName = multipartFile.getOriginalFilename();
                    if ("blob".equalsIgnoreCase(oldFileName)) {
                        String[] str = Objects.requireNonNull(multipartFile.getContentType())
                                .split("/");
                        fileType = "." + str[str.length - 1];
                    } else {
                        fileType = oldFileName.substring(
                                oldFileName.lastIndexOf(".")).toLowerCase();
                    }
                    String fileName = UUID.randomUUID().toString() + fileType;
                    String newPicts = picts.toString() + fileName;
                    File f = new File(newPicts);
                    multipartFile.transferTo(f);
                    //producepritwo(f);

                    String namePath1 = pathName.toString() + fileName;
                    System.out.println(namePath1);
                    String[] namePatha=namePath1.split("\\.");
                    String name=namePatha[0];
                    String jpg=namePatha[1];
                    System.out.println(jpg);
                    System.out.println(name);
                    // 用线程
                    if("pic".equalsIgnoreCase(param.getType())){
                        LocalUploadThream localUploadThream = new LocalUploadThream();
                        localUploadThream.intitialize(f);
                        localUploadThream.start();
                    }
                    namePath1 = FinalConfigParam.SERVER_TYPE+FinalConfigParam.SERVER_URL+"/"+name+"."+jpg;

                    listName.add(namePath1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listName;
    }

    /**
     *
     * @Description: 图片产生200*200压缩图片
     * @return String 返回类型
     * @author yangjing
     * @date 2017-2-17 下午3:34:25
     */
    public void producepritwo(File srctImage) {
        ImageTransformer it = new ImageTransformer();
        String targetName = srctImage.getName();
        String parentPath = srctImage.getParent();
        String tgtName = "";
        try {
            it.setMaxPixel(SIZES[2]);
            tgtName = targetName.substring(0, targetName.lastIndexOf("."))
                    + "_200_200"
                    + targetName.substring(targetName.lastIndexOf("."));
            File targetImage = new File(parentPath + "/" + tgtName);
            it.doImageTransform(srctImage, targetImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<String> commAudio(UploadAudioParam param) {

        List<String> listName = new LinkedList<>();
        try {
            String fileType = null;
            StringBuilder picts = new StringBuilder();
            InputStream in = this.getClass().getResourceAsStream(
                    "/basic_config.properties");
            Properties pro = new Properties();
            pro.load(in);
            String proPtah = pro.getProperty("service_guidetravel_url");
            StringBuilder pathName = new StringBuilder();
            if (proPtah.contains("www")) {
                picts.append("//home//fileService//fileService//webapps//");
                pathName.append("tripPictstorage/");
            } else if (proPtah.contains("2.125")) {
                picts.append("//var//lib//picture-tomcat//apache-tomcat-8.0.24//webapps//");
                pathName.append("tripPictstorage/");
            }else if(proPtah.contains("localhost")) {
                picts.append("C://AnZhuang//apache-tomcat-7.0.53//webapps//");
                pathName.append("tripPictstorage/");
            }else if(proPtah.contains("2.158")){
                picts.append("C://AnZhuang//apache-tomcat-7.0.53//webapps//");
                pathName.append("tripPictstorage/");
            }else {
                picts.append("D://");
                pathName.append("tripPictstorage/");
            }
            pathName.append("user/").append(param.getUser_id()).append("/").append(param.getSpeech_type()).append("/");
            picts.append(pathName);
            File file = new File(picts.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            if (param.getBase64() != null && param.getBase64().size() > 0) {
                String str = param.getBase64().get(0);
                str = str.split("base64,")[1];
                //byte[] b = Base64.decode(str);
                String fileName = UUID.randomUUID().toString() + ".jpg";
                picts.append(pathName.toString()).append(fileName);
                FileOutputStream out = new FileOutputStream(new File(
                        picts.toString()));
                //out.write(b);
                out.flush();
                out.close();
                listName.add(pathName.toString() + fileName);
            } else if (param.getFile() != null && param.getFile().length > 0) {
                for (MultipartFile multipartFile : param.getFile()) {
                    String oldFileName = multipartFile.getOriginalFilename();
                    if ("blob".equalsIgnoreCase(oldFileName)) {
                        String[] str = Objects.requireNonNull(multipartFile.getContentType())
                                .split("/");
                        fileType = "." + str[str.length - 1];
                    } else {
                        fileType = oldFileName.substring(
                                oldFileName.lastIndexOf(".")).toLowerCase();
                    }
                    String fileName = UUID.randomUUID().toString() + fileType;
                    String newPicts = picts.toString() + fileName;
                    File f = new File(newPicts);
                    multipartFile.transferTo(f);
                    //producepritwo(f);
                    // 用线程
                    //LocalUploadThream localUploadThream = new LocalUploadThream();
                    //localUploadThream.intitialize(f);
                    //localUploadThream.start();
                    String namePath1 = pathName.toString() + fileName;
                    //namePath1 = serviceConfig.getIp()+":"+serviceConfig.getZuul()+"/eg-api/push/"+name+"_200_200."+jpg;
                    listName.add(namePath1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listName;
    }

    public String getToken(QiNiuUploadParam param) {
        String upToken = redisService.getStr("qiniuToken" + param.getType());
        if (upToken == null || upToken.isEmpty()) {
            Auth auth = Auth.create(qinNiuParam.getAccessKey(), qinNiuParam.getSecretKey());
            upToken = auth.uploadToken(qinNiuParam.getBucket(), param.getKey(), qinNiuParam.getExpires(), null);
            String string= auth.uploadToken(qinNiuParam.getBucket());
            //redisService.set("qiniuToken" + param.getType(), upToken, qinNiuParam.getExpires());
        }
        return upToken;
    }


}
