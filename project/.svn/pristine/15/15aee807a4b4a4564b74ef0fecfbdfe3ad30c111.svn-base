package com.huihe.eg.user.model;

import com.cy.framework.util.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/21 16:28
 */
@ApiModel(value="搜索",description="搜索属性说明")
public class SearchParam {

    public SearchParam(String json) throws IOException{
        SearchParam searchParam= JSONUtils.json2Obj(json,SearchParam.class);
        this.latitude=searchParam.getLatitude();
        this.longitude=searchParam.getLongitude();
        this.search_type=searchParam.getSearch_type();
    }
    public SearchParam(){}
    /**
     * 精度
     */
    @ApiModelProperty(value="精度", example="1")
    private BigDecimal latitude;

    /**
     * 维度
     */
    @ApiModelProperty(value="维度",example="1")
    private BigDecimal longitude;

    @ApiModelProperty(value="匹配类型 nearby 附近的人  company 同事 school 同学  contacts 通讯录好友 hot 当红境友  hometown 老乡  recommend推荐")
    private String search_type;

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }
}
