package com.huihe.eg.push.upload;

import com.cy.framework.model.UploadParam;
import com.cy.framework.model.qiniu.QiNiuUploadParam;
import com.cy.framework.service.dao.qiniu.QiNiuService;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 上传
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="上传",description="上传",tags = {"上传"})
@RestController
@RequestMapping("upload")
public class UploadUserController extends BaseFrameworkController<ImageTransformer, Long> {
    /*

     * 采用file.Transto 来保存上传的文件
     */
    @Resource
    private UploadService uploadService;
    @Resource
    private QiNiuService qiNiuService;
    @Override
    public void init() {
        setBaseService(null);
    }

    /**
     * 本地上传
     * @author zwx
     * @date 2019年11月26日11:13:49
     * @since JDK1.8
     */
    @PostMapping(value = "/uploadPicture")
    public String uploadPictrueAll(@RequestParam("user_id")String user_id,
                                   @RequestParam("type")String type,
                                   @RequestParam("file") MultipartFile[] file,
                                 HttpServletRequest request, HttpServletResponse response) {
        //System.out.println(":::::;"+ JSONUtils.obj2Json(file));
       /* //转换 HttpServletRequest
        MultipartHttpServletRequest mulReq=(MultipartHttpServletRequest)request;
        //获取上传的文件
        CommonsMultipartFile file = (CommonsMultipartFile) mulReq.getFile("filePath");*/
        UploadParam param = new UploadParam(type,user_id);
        param.setFiles(Arrays.asList(file));
//        param.setType(type);
//        param.setId(user_id);
        List<String> listName = uploadService.comm(param);
        // 修改数据库
//         uploadFileService.updateBase(Integer.parseInt(user_id), type,
        // listName);
        System.out.println(listName);
        return JSONUtils.list2Json(listName);
    }
    /**
     * 七牛上传
     * @author zwx
     * @date 2019年11月26日11:13:49
     * @since JDK1.8
     */
    @ApiOperation(value = "七牛上传", httpMethod = "POST", notes = "七牛上传")
    @PostMapping(value = "/easyQiNiu")
    public ResultParam upload(QiNiuUploadParam param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        String data = null;
        if (param.getType() == null || param.getType().isEmpty()) {
            data = "类型未传";
            throw new DataException(1, "类型未传");
        }
        if (param.getFiles() == null || param.getFiles().size() <= 0) {
            throw new DataException(1, "请选择图片");
        }
        return ResultUtil.success(qiNiuService.upload(param, request, response));
    }
    /**
     * 七牛Token
     * @author zwx
     * @date 2019年11月26日11:13:49
     * @since JDK1.8
     */
    @ApiOperation(value = "七牛Token", httpMethod = "POST", notes = "七牛Token")
    @PostMapping(value = "/getToken")
    public ResultParam getToken(@RequestBody(required = false)  QiNiuUploadParam param){
        this.init();
        return ResultUtil.success( uploadService.getToken(param));
    }


}
