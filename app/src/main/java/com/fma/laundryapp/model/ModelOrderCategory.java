package com.fma.laundryapp.model;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelOrderCategory extends BaseModel {
    @TableField
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @TableField
    private String name;
    @TableField
    private Integer company_id;
    @TableField
    private Integer unit_id;
    @TableField
    private String customfield_1;
    @TableField
    private String customfield_2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getCustomfield_1() {
        return customfield_1;
    }

    public void setCustomfield_1(String customfield_1) {
        this.customfield_1 = customfield_1;
    }

    public String getCustomfield_2() {
        return customfield_2;
    }

    public void setCustomfield_2(String customfield_2) {
        this.customfield_2 = customfield_2;
    }

    public String getCustomfield_3() {
        return customfield_3;
    }

    public void setCustomfield_3(String customfield_3) {
        this.customfield_3 = customfield_3;
    }

    @TableField

    private String customfield_3;

}
