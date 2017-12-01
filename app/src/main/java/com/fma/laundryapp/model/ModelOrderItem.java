package com.fma.laundryapp.model;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelOrderItem extends BaseModel implements Serializable {
    @TableField
    private int order_id;
    @TableField
    private double qty;
    @TableField
    private double price;
    @TableField
    private double discount;
    @TableField
    private double tax;
    @TableField
    private double total;
    @TableField
    private int product_id;
    @TableField
    private String notes;

    private ModelProduct product;
//    public List<ModelOrderModifier> modifiers = new ArrayList<ModelOrderModifier>();

    public ModelOrderItem(ModelProduct product, double qty ) {
        this.setQty(qty);
        this.setProduct(product);
    }

    public ModelOrderItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public ModelProduct getProduct() {
        return product;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

//    public void addModifier(ModelOrderModifier item){
//        this.modifiers.add(item);
//    }

//    public void removeModifier(ModelOrderModifier item){
//        this.modifiers.remove(item);
//    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

//    public Boolean hasModifiers(){
//        if (this.modifiers == null){
//            return false;
//        }else {
//            return (this.modifiers.size() > 0);
//        }
//    }

    public void setProduct(ModelProduct product) {
        this.product = product;
        this.product_id = product.getId();
        Double aPrice = 0.0;

        aPrice +=  product.getPrice();
//        for (ModelModifier modifier : product.modifiers ) {
//            if (modifier.checked) aPrice += modifier.getPrice();
//        }

        this.setPrice(aPrice);
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void saveToDBAll(SQLiteDatabase db) {
        Log.d("DB","OrderItem.saveToDBAll");
        super.saveToDB(db ,true);

        ModelOrderModifier tmp = new ModelOrderModifier();
        db.execSQL(tmp.generateSQLDelete("where orderitem_id = " + String.valueOf(getId())));

//        for (ModelOrderModifier modelOrderModifier : modifiers) {
//            modelOrderModifier.setId(0); //force insert
//            modelOrderModifier.setOrderitem_id(this.getId());
//            modelOrderModifier.saveToDB(db);
//        }

    }

    public void calculate() {
        this.total = this.qty * this.price;
    }


    private String product_uid;
    public void prepareUpload(SQLiteDatabase db) {
        if (product_id > 0) {
            ModelProduct modelProduct = new ModelProduct();
            modelProduct.loadFromDB(db, this.product_id);
            this.product_uid = modelProduct.getUid();
        }
    }

    public void reLoadAll(SQLiteDatabase db){
//        this.modifiers.clear();
        this.product = new ModelProduct();
        this.product.loadFromDB(db, this.product_id);
        String sql = "select * from ordermodifier where orderitem_id = " + String.valueOf(this.getId());

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ModelOrderModifier modelOrderModifier = new ModelOrderModifier();
            modelOrderModifier.loadFromCursor(cursor);
//            this.addModifier(modelOrderModifier);
        }
    }
}

