package com.fma.laundryapp.model;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

/**
 * Created by fma on 7/30/2017.
 */

public class ModelMoneyPreset extends BaseModel implements Serializable{
    public double getMoneyval() {
        return moneyval;
    }

    public void setMoneyval(double moneyval) {
        this.moneyval = moneyval;
    }

    @TableField
    private Double moneyval;

    public ModelMoneyPreset(double moneyval){
        this.setMoneyval(moneyval);
    }

    @Override
    public String getTableName() {
        return super.getTableName();
    }

    public static void initMetaData(SQLiteDatabase db) {
        new ModelMoneyPreset(1000).saveToDB(db);
        new ModelMoneyPreset(2000).saveToDB(db);
        new ModelMoneyPreset(5000).saveToDB(db);
        new ModelMoneyPreset(10000).saveToDB(db);
        new ModelMoneyPreset(20000).saveToDB(db);
        new ModelMoneyPreset(50000).saveToDB(db);
        new ModelMoneyPreset(100000).saveToDB(db);
        new ModelMoneyPreset(150000).saveToDB(db);
        new ModelMoneyPreset(200000).saveToDB(db);
        new ModelMoneyPreset(250000).saveToDB(db);
        new ModelMoneyPreset(300000).saveToDB(db);
    }
}
