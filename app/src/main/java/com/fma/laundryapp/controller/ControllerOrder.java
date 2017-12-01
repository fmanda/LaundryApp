package com.fma.laundryapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.model.ModelOrder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerOrder {
    private Context context;

    public ControllerOrder(Context context) {
        this.context = context;
    }


    public List<ModelOrder> getOrderList(Boolean isHoldOrderOnly){
        ControllerCustomer controllerCustomer = new ControllerCustomer(context);
        List<ModelOrder> orders = new ArrayList<ModelOrder>();

        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        String sql = "select * from orders limit 1000";
        if (isHoldOrderOnly) sql = "select * from orders where status=0";

        Cursor cursor = rdb.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ModelOrder modelOrder = new ModelOrder();
            modelOrder.loadFromCursor(cursor);
            if (modelOrder.getCustomer_id()>0){
                modelOrder.setCustomer(controllerCustomer.getCustomer(modelOrder.getCustomer_id()));
            }
            orders.add(modelOrder);
        }
        return orders;
    }



    public String generateNewNumber(){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        String newNumber = "";

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date d = c.getTime();

        int iorderno = 0;

//        newNumber += String.valueOf(c.get(Calendar.YEAR));
//        newNumber = newNumber.substring(newNumber.length()-2);
//        newNumber += String.valueOf(c.get(Calendar.MONTH));

        try {
            String sql = "select max(orderno) from orders where orderdate >= " + String.valueOf(d.getTime());
            Cursor cursor = rdb.rawQuery("select max(orderno) from orders ", null);
            if (cursor.moveToNext()) {
                String str = cursor.getString(0);
                //str = str.replace(newNumber,"");
//                str = str.substring(str.length()-4);
                if (!str.equals("")) iorderno = Integer.parseInt(str);
            }
        }
        catch(Exception e){
        }

        iorderno++;
        newNumber += String.format("%05d", iorderno);
        return newNumber;

    }
}
