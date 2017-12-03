package com.fma.laundryapp.facade;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.facade.fragment.CreateCustomerFragment;
import com.fma.laundryapp.facade.fragment.OrderFinishFragment;
import com.fma.laundryapp.facade.fragment.PickCustomerFragment;
import com.fma.laundryapp.model.ModelCustomer;
import com.fma.laundryapp.model.ModelOrder;

public class OrderFinishActivity extends AppCompatActivity {
    private ListView orderItemListView = null;
    Button btnBack;
    Button btnLookupCustomer;
    Button btnDelCustomer;
    OrderFinishFragment orderFinishFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finish);

        Intent intent = getIntent();
        ModelOrder order = (ModelOrder) intent.getSerializableExtra("orderFromProductList");

        orderFinishFragment = (OrderFinishFragment) getFragmentManager().findFragmentById(R.id.fragOrderFinish);
        orderFinishFragment.loadOrder(order);

        btnLookupCustomer = (Button) findViewById(R.id.btnLookupCustomer);
        btnDelCustomer = (Button) findViewById(R.id.btnDelCustomer);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnLookupCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialogCustomer();
            }
        });
        ModelCustomer modelCustomer = order.getCustomer();
        if (modelCustomer!=null){
            if (modelCustomer.getId()>0) btnLookupCustomer.setText(modelCustomer.getName());
        }

        btnDelCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCustomer(null);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void setCustomer(ModelCustomer modelCustomer){
        orderFinishFragment.modelOrder.setCustomer(modelCustomer);
        if (modelCustomer != null) {
            btnLookupCustomer.setText(modelCustomer.getName());
        }else{
            btnLookupCustomer.setText("  Pilih Customer");
        }
    }

    public void showDialogCustomer(){
        FragmentManager fm = getFragmentManager();
        PickCustomerFragment pickCustomerFragment = new PickCustomerFragment();
        pickCustomerFragment.SetCustomerSelectListener(new PickCustomerFragment.CustomerSelectListener() {
            @Override
            public void OnSelectCustomer(ModelCustomer modelCustomer) {
                setCustomer(modelCustomer);
            }

            @Override
            public void OnClickCreateCustomer() {
                showDialogCreateCustomer();
            }
        });
        pickCustomerFragment.show(fm, "Pilih Customer");
    }

    public void showDialogCreateCustomer(){
        FragmentManager fm = getFragmentManager();
        CreateCustomerFragment createCustomerFragment = new CreateCustomerFragment();
        createCustomerFragment.SetOnCreatedListener(new CreateCustomerFragment.CustomerCreateListener() {
            @Override
            public void OnCreatedCustomer(ModelCustomer modelCustomer) {
                setCustomer(modelCustomer);
            }
        });
        createCustomerFragment.show(fm, "Buat Customer Baru");
    }
}
