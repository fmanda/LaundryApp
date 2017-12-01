package com.fma.laundryapp.model;

import java.io.Serializable;

public class ModelOrderModifier extends BaseModel implements Serializable {
    @TableField
    private int orderitem_id;
    @TableField
    private int modifier_id;
    @TableField
    private String modifier;
    @TableField
    private Double price;

    public ModelOrderModifier(ModelModifier modifier) {
        this.setModifier(modifier.getName());
        this.setPrice(modifier.getPrice());
        this.setModifier_id(modifier.getId());
    }

    public ModelOrderModifier() {
    }

    public int getOrderitem_id() {
        return orderitem_id;
    }

    public void setOrderitem_id(int orderitem_id) {
        this.orderitem_id = orderitem_id;
    }

    public int getModifier_id() {
        return modifier_id;
    }

    public void setModifier_id(int modifier_id) {
        this.modifier_id = modifier_id;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
