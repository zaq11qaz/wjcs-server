package com.huihe.eg.comm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "公用的返回参数说明")
public class CommResultParam {
    @ApiModelProperty(value = "管理权限模块参数说明")
    private AuthorizationEum managerEum;
    @ApiModelProperty(value = "用户模块参数说明")
    private UserEum userEum;
    private SystemEum systemEum;
    public AuthorizationEum getManagerEum() {
        return managerEum;
    }

    public void setManagerEum(AuthorizationEum managerEum) {
        this.managerEum = managerEum;
    }

    public UserEum getUserEum() {
        return userEum;
    }

    public void setUserEum(UserEum userEum) {
        this.userEum = userEum;
    }
}
