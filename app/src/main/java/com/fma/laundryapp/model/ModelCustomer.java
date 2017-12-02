package com.fma.laundryapp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fma on 7/30/2017.
 */

public class ModelCustomer extends BaseModel implements Serializable{
    @TableField
    private String uid;
    @TableField
    private String name;
    @TableField
    private String code;
    @TableField
    private Date date_modified;
    @TableField
    private String address;
    @TableField
    private String category;
    @TableField
    private Integer company_id;
    @TableField
    private Integer unit_id;
    @TableField
    private String phone_number;

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
        this.unit_id = unit_id;
    }

    public ModelCustomer(){
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Date date_modified) {
        this.date_modified = date_modified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String getTableName() {
        return super.getTableName();
    }


}
