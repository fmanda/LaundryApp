package com.fma.laundryapp.model;

import java.util.Date;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelBalance extends BaseModel {
    @TableField
    private String uid;
    @TableField
    private Integer shift;
    @TableField
    private Double beginning;
    @TableField
    private Double ending;
    @TableField
    private Double income;
    @TableField
    private Double otherincome;
    @TableField
    private Double expense;
    @TableField
    private Date balancedate = new Date();
    @TableField
    private Date closeddate;
    @TableField
    private Integer isclosed;
}
