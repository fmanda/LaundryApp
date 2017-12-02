package com.fma.laundryapp.model;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

/**
 * Created by fma on 7/30/2017.
 */

public class ModelOrderPreset extends BaseModel implements Serializable{

    @TableField
    private String name;

    @TableField
    private Integer default_customer_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDefault_customer_id() {
        return default_customer_id;
    }

    public void setDefault_customer_id(Integer default_customer_id) {
        this.default_customer_id = default_customer_id;
    }

    public ModelOrderPreset(){

    }

    @Override
    public String getTableName() {
        return super.getTableName();
    }

    public static void initMetaData(SQLiteDatabase db) {
//        ModelOrderPreset modelOrderPreset = new ModelOrderPreset();
//        modelOrderPreset.setDefault_customer_id(0);
//        modelOrderPreset.setName("DINE IN");
//        modelOrderPreset.saveToDB(db);
//
//        modelOrderPreset.setId(0);
//        modelOrderPreset.setDefault_customer_id(0);
//        modelOrderPreset.setName("TAKE HOME");
//        modelOrderPreset.saveToDB(db);

//        new ModelOrderPreset(2000).saveToDB(db);
//        new ModelOrderPreset(5000).saveToDB(db);
//        new ModelOrderPreset(10000).saveToDB(db);
//        new ModelOrderPreset(20000).saveToDB(db);
//        new ModelOrderPreset(50000).saveToDB(db);
//        new ModelOrderPreset(100000).saveToDB(db);
//        new ModelOrderPreset(150000).saveToDB(db);
//        new ModelOrderPreset(200000).saveToDB(db);
//        new ModelOrderPreset(250000).saveToDB(db);
//        new ModelOrderPreset(300000).saveToDB(db);
    }
}
