package com.fma.laundryapp.facade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.CustomerListAdapter;
import com.fma.laundryapp.controller.ControllerCustomer;
import com.fma.laundryapp.model.ModelCustomer;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class CustomerActivity extends BaseActivity implements CustomerListAdapter.ItemClickListener {

    private List<ModelCustomer> customers;
    private ControllerCustomer controllerCustomer = new ControllerCustomer(this);
    CustomerListAdapter customerListAdapter;
    RecyclerView recyclerView;

    public void fabOnClick(){
        Intent intent = new Intent(this, CustomerCreateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_customer, this.mainframe);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateCustomer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick();
            }
        });

        controllerCustomer = new ControllerCustomer(this);
        customers = controllerCustomer.getCustomerList();
        customerListAdapter = new CustomerListAdapter(this, customers);

        recyclerView = (RecyclerView) this.findViewById(R.id.rvCustomers);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(customerListAdapter);

        customerListAdapter.setClickListener(this);
    }

    public void showEditor(ModelCustomer customer){
        Intent intent = new Intent(this, CustomerCreateActivity.class);
        intent.putExtra("customerFromList", customer);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        showEditor(customers.get(position));
    }
}
