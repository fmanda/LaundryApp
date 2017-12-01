package com.fma.laundryapp.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.helper.GsonRequest;
import com.fma.laundryapp.model.ModelCustomer;
import com.fma.laundryapp.model.ModelOrder;
import com.fma.laundryapp.model.ModelOrderCategory;
import com.fma.laundryapp.model.ModelProduct;

import java.util.List;

/**
 * Created by fma on 10/26/2017.
 */

public class ControllerRest {
    private Context context;
    private DBHelper db;
    private ControllerSetting controllerSetting;

    private String base_url = "http://10.0.2.1/";

//    final String customer_get_url = "http://10.0.2.1/customerof/405";
//    final String customer_post_url = "http://10.0.2.1/customer";
//    final String product_get_url = "http://10.0.2.1/productof/405/1";
//    final String product_post_url = "http://10.0.2.1/productclient";
//    final String ordercategory_get_url = "http://10.0.2.1/ordercategoryof/405";


    private int company_id;
    private int unit_id;

    private ControllerRequest controllerRequest;
    private ControllerCustomer controllerCustomer;
    private Listener listener;

    public String getURLCompanyByUser(){
        return (base_url + "companybyuser");
    }

    public String getURLUnits(){
        return base_url + "unitsof";
    }

    public String customer_get_url(){
        return base_url + "customerof/" + this.company_id;
    }

    public String customer_post_url(){
        return base_url + "customer";
    }

    public String product_get_url(){
        return base_url + "productof/" + this.company_id + "/" + this.unit_id;
    }

    public String ordercategory_get_url(){
        return base_url + "ordercategoryof/" + this.company_id ;
    }

    public String order_post_url(){
        return base_url + "orderfromclient";
    }

    public void DownloadAll(){
        this.DownloadProducts();
        this.DownloadCustomers();
    }

    public void UploadAll(){
        //mater first;
//        this.UploadProducts(); //product tidak di upload
//        this.UploadCustomers();
        this.UploadOrders();
    }


    public interface Listener {
        void onSuccess(String msg);
        void onError(String msg);
    }

    public void setListener(ControllerRest.Listener listener) {
        this.listener = listener;
    }

    public ControllerRest(Context context) {
        this.context = context;
        this.db = DBHelper.getInstance(context);
        this.controllerRequest = ControllerRequest.getInstance(context);
        this.controllerCustomer = new ControllerCustomer(context);
        this.company_id = new ControllerSetting(this.context).getCompanyID();
        this.unit_id = new ControllerSetting(this.context).getUnitID();
        this.controllerSetting = new ControllerSetting((this.context));
        this.base_url = controllerSetting.getSetting("rest_url").getVarvalue();
    }

    //CUSTOMER

    public void DownloadCustomers(){
        GsonRequest<ModelCustomer[]> gsonReq = new GsonRequest<ModelCustomer[]>(customer_get_url(), ModelCustomer[].class,
            new Response.Listener<ModelCustomer[]>() {
                @Override
                public void onResponse(ModelCustomer[] response) {
                    try {
                        for (ModelCustomer cust : response) {
                            cust.setIDFromUID(db.getReadableDatabase(), cust.getUid());
                            cust.saveToDB(db.getWritableDatabase());
                            listener.onSuccess(cust.getName() + " updated");
                        }
                    }catch(Exception e){
                        listener.onError(e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    listener.onError(error.toString());
                }
            }
        );
        this.controllerRequest.addToRequestQueue(gsonReq,customer_get_url());
    }

    public void UploadCustomers(){
        ControllerCustomer controllerCustomer = new ControllerCustomer(this.context);
        List<ModelCustomer> customers = controllerCustomer.getCustomerList();
        for (final ModelCustomer cust : customers) {
            cust.setCompany_id(this.company_id);
            cust.setUnit_id(this.unit_id);

            //test
            GsonRequest<ModelCustomer> gsonReq = new GsonRequest<ModelCustomer>(customer_post_url(), cust,
                new Response.Listener<ModelCustomer>() {
                    @Override
                    public void onResponse(ModelCustomer response) {
                        //update ID
                        try{
                            cust.setUid(response.getUid());
                            cust.saveToDB(db.getWritableDatabase());
                            listener.onSuccess(response.getName() + " updated");
                        }catch (Exception e){
                            listener.onError(e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                }
            );
            this.controllerRequest.addToRequestQueue(gsonReq,customer_post_url());
        }

    }

    public void DownloadOrderCategory(){
        GsonRequest<ModelOrderCategory[]> gsonReq = new GsonRequest<ModelOrderCategory[]>(ordercategory_get_url(), ModelOrderCategory[].class,
            new Response.Listener<ModelOrderCategory[]>() {
                @Override
                public void onResponse(ModelOrderCategory[] response) {
                    try {
                        for (ModelOrderCategory cat : response) {
                            cat.setIDFromUID(db.getReadableDatabase(), cat.getUid());
                            cat.saveToDB(db.getWritableDatabase());
                            listener.onSuccess(cat.getName() + " updated");
                        }
                    }catch(Exception e){
                        listener.onError(e.toString());
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError(error.toString());
                }
            }
        );
        this.controllerRequest.addToRequestQueue(gsonReq,ordercategory_get_url());
    }

    //PRODUCT
    public void DownloadProducts(){
        GsonRequest<ModelProduct[]> gsonReq = new GsonRequest<ModelProduct[]>(product_get_url(), ModelProduct[].class,
            new Response.Listener<ModelProduct[]>() {
                @Override
                public void onResponse(ModelProduct[] response) {
                    try {
                        for (ModelProduct prod : response) {
                            prod.setIDFromUID(db.getReadableDatabase(), prod.getUid());
                            prod.saveToDBAll(db.getWritableDatabase());
                            listener.onSuccess(prod.getName() + " updated");
                        }
                    }catch(Exception e){
                        listener.onError(e.toString());
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError(error.toString());
                }
            }
        );
        this.controllerRequest.addToRequestQueue(gsonReq,product_get_url());
    }


    public void UploadOrders(){
        ControllerOrder controllerorder = new ControllerOrder(this.context);
        List<ModelOrder> orders = controllerorder.getOrderList(false);
        final SQLiteDatabase db = DBHelper.getInstance(this.context).getReadableDatabase();
        final SQLiteDatabase dbw = DBHelper.getInstance(this.context).getWritableDatabase();

//        GsonBuilder gsonBuilder;
//        Gson gson;
//        gsonBuilder = new GsonBuilder().setDateFormat("yyyy-mm-dd hh:mm:ss");
//        gson = gsonBuilder.create();

        for (final ModelOrder modelOrder : orders) {
            modelOrder.prepareUpload(db);
            modelOrder.setCompany_id(this.company_id);
            modelOrder.setUnit_id(this.unit_id);
//            Toast.makeText(context, debug, Toast.LENGTH_SHORT).show();

            GsonRequest<ModelOrder> gsonReq = new GsonRequest<ModelOrder>(order_post_url(), modelOrder,
                new Response.Listener<ModelOrder>() {
                    @Override
                    public void onResponse(ModelOrder response) {
                        //update ID
                        try{
                            modelOrder.setuid(response.getuid());
                            modelOrder.saveToDB(dbw);
                            listener.onSuccess(response.getOrderno() + " updated");
                        }catch (Exception e){
                            listener.onError(e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                }
            );
            this.controllerRequest.addToRequestQueue(gsonReq,customer_post_url());
        }

        //test


    }


}
