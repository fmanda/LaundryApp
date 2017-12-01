package com.fma.laundryapp.facade;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fma.laundryapp.R;
import com.fma.laundryapp.facade.fragment.PaymentFragment;
import com.fma.laundryapp.model.ModelOrder;

public class PaymentActivity extends AppCompatActivity {

    PaymentFragment fragPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle("Order Payment");

        }

        fragPayment = (PaymentFragment) getFragmentManager().findFragmentById(R.id.fragPayment);
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);

    }

    public void loadData(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("modelOrder")) {
                ModelOrder modelOrder = (ModelOrder) intent.getSerializableExtra("modelOrder");
                fragPayment.setModelOrder(modelOrder);
            }
        }
    }


}
