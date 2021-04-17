package com.huihe.eg.user.model;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/9 13:17
 * @ Description：
 * @ since: JDk1.8
 */
public class GenerateQRCodeEntity {
    private String scene;

    private String page;

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

    @Override
    public String toString() {
        return "{" +
                "\"scene\":\"" + scene + '\"' +
                ", \"page\":\"" + page + '\"' +
                '}';
    }
}
