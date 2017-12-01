package com.fma.laundryapp.facade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.OrderHoldAdapter;
import com.fma.laundryapp.controller.ControllerOrder;
import com.fma.laundryapp.model.ModelOrder;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class OrderActivity extends BaseActivity {
    List<ModelOrder> orders;
    ControllerOrder controllerOrder;
    OrderHoldAdapter orderHoldAdapter;
    RecyclerView rvOrders;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_order, this.mainframe);

        rvOrders = (RecyclerView) findViewById(R.id.rvOrders);
        controllerOrder = new ControllerOrder(this);
        loadOrders();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateOrder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick();
            }
        });
    }

    public void loadOrders(){
        orders = controllerOrder.getOrderList(Boolean.TRUE);
        orderHoldAdapter = new OrderHoldAdapter(this, orders);
        rvOrders.setAdapter(orderHoldAdapter);
        rvOrders.setLayoutManager(new GridLayoutManager(this, 1));

        orderHoldAdapter.SetSelectHoldOrderListener(new OrderHoldAdapter.SelectHoldOrderListener() {
            @Override
            public void onSelectHoldOrder(ModelOrder modelOrder) {
                loadOrder(modelOrder);
            }
        });
    }

    private void loadOrder(ModelOrder modelOrder) {
        Intent intent = new Intent(this, OrderCreateActivity.class);
        intent.putExtra("modelOrder", modelOrder);
        startActivity(intent);
    }

    public void fabOnClick(){
        startActivity(new Intent(this, OrderCreateActivity.class));
    }
}
