package com.fma.laundryapp.model;

/**
 * Created by fma on 7/30/2017.
 */

public class LookupProduct extends ModelProduct {
    private double qty;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    private boolean hidden;

    public LookupProduct() {
        this.qty = 0;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public void incQty(double inc){
        this.qty = this.qty + inc;
    }

}
