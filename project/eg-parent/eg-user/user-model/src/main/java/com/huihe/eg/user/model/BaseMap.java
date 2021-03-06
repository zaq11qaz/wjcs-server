package com.huihe.eg.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/13 10:16
 * @ Description：后台管理通用返回对象
 * @ since: JDk1.8
 */
@ApiModel(value="后台管理通用返回对象",description="后台管理通用返回对象")
public class BaseMap implements Serializable {
    private static final long serialVersionUID = -4903939497581669987L;
    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private Object rows;

    /**
     * 总数
     */
    @ApiModelProperty(value="总数")
    private Integer total;

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
