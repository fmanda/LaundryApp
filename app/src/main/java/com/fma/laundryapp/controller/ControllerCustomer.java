package com.fma.laundryapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.model.ModelCustomer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerCustomer {
    private Context context;

    public ControllerCustomer(Context context) {
        this.context = context;
    }

    public ModelCustomer getCustomer(int id){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("select * from customer where id = " + String.valueOf(id), null);
        if (cursor.moveToNext()){
            ModelCustomer customer = new ModelCustomer();
            customer.loadFromCursor(cursor);
            return customer;
        }
        return null;
    }

    public ModelCustomer getCustomer(String uid){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("select * from customer where uid = " + String.valueOf(uid), null);
        if (cursor.moveToNext()){
            ModelCustomer customer = new ModelCustomer();
            customer.loadFromCursor(cursor);
            return customer;
        }
        return null;
    }

    public List<ModelCustomer> getCustomerList(){
        List<ModelCustomer> customers = new ArrayList<ModelCustomer>();

        DBHelper db = new DBHelper(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("select * from customer", null);
        while (cursor.moveToNext()){
            ModelCustomer customer = new ModelCustomer();
            customer.loadFromCursor(cursor);
            customers.add(customer);
        }
        return customers;
    }
}
