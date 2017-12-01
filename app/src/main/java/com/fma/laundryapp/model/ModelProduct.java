package com.fma.laundryapp.model;

import android.database.sqlite.SQLiteDatabase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ModelProduct extends BaseModel implements Serializable{
    @TableField
    private String uid;
    @TableField
    private String name;
    @TableField( fieldName = "sku")
    private String sku;
    @TableField
    private Date date_modified;
    @TableField
    private String barcode;
    @TableField
    private Double price;
    @TableField
    private int tax;
    @TableField
    private String category;
    @TableField
    private Integer company_id;
    @TableField
    private String uom;
    @TableField
    private String img;
    @TableField
    private Integer allow_decimal;

//    public List<ModelModifier> modifiers = new ArrayList<ModelModifier>();

    public ModelProduct(){
    }

    public ModelProduct(int id, String productName, Double productPrice) {
        this.name = productName;
        this.price = productPrice;
        this.id = id;
    }

    @Override
    public String getTableName() {
        return super.getTableName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String guid) {
        this.uid = guid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Date getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Date date_modified) {
        this.date_modified = date_modified;
    }

//    public void addItem(ModelModifier item){
//        this.modifiers.add(item);
//    }
//
//    public void removeItem(ModelModifier item){
//        this.modifiers.remove(item);
//    }

    public void saveToDBAll(SQLiteDatabase db) {
        //transaction
        db.beginTransaction();
        try {
            super.saveToDB(db ,true);

            ModelModifier tmp = new ModelModifier();
            db.execSQL(tmp.generateSQLDelete("where product_id = " + String.valueOf(getId())));

//            for (ModelModifier modifier : modifiers) {
//                modifier.setId(0); //force insert
//                modifier.setProduct_id(this.getId());
//                modifier.saveToDB(db);
//            }

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }

//    public Boolean hasModifiers(){
//        return (this.modifiers.size() > 0);
//    }

//    public int selectedModifiers(){
//        int i = 0;
//        for (ModelModifier modifier : modifiers) {
//            if (modifier.checked)i++;
//        }
//        return i;
//    }

//    public void clearSelectedModifiers(){
//        for (ModelModifier modifier : modifiers) {
//            modifier.checked = false;
//        }
//    }

//    public String getDebugModifiers() {
//        String str = "";
//        for (ModelModifier modifier : modifiers) {
//            if (modifier.checked) str += modifier.getName();
//        }
//        return str;
//    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getAllow_decimal() {
        return allow_decimal;
    }

    public void setAllow_decimal(Integer allow_decimal) {
        this.allow_decimal = allow_decimal;
    }
}
