package com.fma.laundryapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.model.LookupProduct;
import com.fma.laundryapp.model.ModelModifier;
import com.fma.laundryapp.model.ModelProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerProduct {
    private Context context;

    public ControllerProduct(Context context) {
        this.context = context;
    }

    public List<LookupProduct> getProductListByFilter(String category, String filter){
        List<LookupProduct> products = new ArrayList<LookupProduct>();

        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();

        String sql = "select * from product where name like '%" + filter +"%'";
        if (category != "") sql += " and category = '" + category + "'";
        Cursor cursor = rdb.rawQuery(sql, null);
        while (cursor.moveToNext()){
            LookupProduct prod = new LookupProduct();
            prod.loadFromCursor(cursor);
            products.add(prod);
        }
        return products;
    }



    public List<ModelProduct> getProductList(){
        List<ModelProduct> products = new ArrayList<ModelProduct>();

        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("select * from product", null);
        while (cursor.moveToNext()){
            ModelProduct prod = new ModelProduct();
            prod.loadFromCursor(cursor);
            products.add(prod);
        }
        return products;
    }

    public Cursor getOptionModifierCursor(){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("select 1 as checked, name from product", null);
        return cursor;
    }

    public ModelProduct retrieveProduct(int id){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("select * from product where id='"
                + String.valueOf(id)  + "'", null);
        ModelProduct prod = new ModelProduct();
        if (cursor.moveToNext())
            prod.loadFromCursor(cursor);
        return prod;
    }

    public ArrayList<String> getListCategory(){
        ArrayList<String> list = new ArrayList<>();
        list.add("All Category");

        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        ModelProduct modelProduct = new ModelProduct();
        String sql = "select distinct category from " + modelProduct.getTableName() + " order by category";
        Cursor cursor = rdb.rawQuery(sql, null);

        while (cursor.moveToNext()){
            if (!cursor.getString(0).equals("")) list.add(cursor.getString(0));
        }
        return list;
    }

//    public void loadModifier(ModelProduct modelProduct){
//        DBHelper db = DBHelper.getInstance(context);
//        SQLiteDatabase rdb = db.getReadableDatabase();
//        ModelModifier modelmodifier = new ModelModifier();
//        String sql = "select * from " + modelmodifier.getTableName()
//                + " where product_id = " + String.valueOf(modelProduct.getId());
//        Cursor cursor = rdb.rawQuery(sql, null);
//        modelProduct.modifiers.clear();
//        while (cursor.moveToNext()){
//            modelmodifier = new ModelModifier();
//            modelmodifier.loadFromCursor(cursor);
//            modelProduct.addItem(modelmodifier);
//        }
//    }
}
