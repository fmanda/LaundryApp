package com.fma.laundryapp.model;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelModifier extends BaseModel {
    @TableField
    private String uid;
    @TableField
    private String name;
    @TableField
    private Double price;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @TableField

    private int product_id;

    public Boolean checked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ModelModifier(){
        setPrice(0.0);
        checked = false;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
