package com.huihe.eg.user.model;

import java.io.Serializable;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/9 13:17
 * @ Description：
 * @ since: JDk1.8
 */
public class GenerateQRCodeEntity implements Serializable {
    private static final long serialVersionUID = 8650384329949527501L;

    private String scene;

    private String page;

    private Long user_id;
    private Long activity_id;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"scene\":\"" + scene + '\"' +
                ", \"page\":\"" + page + '\"' +
                '}';
    }
}
