package com.huihe.eg.user.model.pay;

public class IosPayParam {

    private Long user_id;
        private String receipt;//凭证
    //学习卡价格表
    private String product_id;
    private String transaction_id;
    private String type;
    private Integer purchase_quantity;//购买数量
    private Boolean is_experience;

    public Boolean getIs_experience() {
        return is_experience;
    }

    public void setIs_experience(Boolean is_experience) {
        this.is_experience = is_experience;
    }

    public Integer getPurchase_quantity() {
        return purchase_quantity;
    }

    public void setPurchase_quantity(Integer purchase_quantity) {
        this.purchase_quantity = purchase_quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
