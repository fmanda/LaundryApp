package com.fma.laundryapp.facade;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fma.laundryapp.R;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.model.ModelCustomer;


/**
 * Created by fma on 7/30/2017.
 */


public class CustomerCreateActivity extends AppCompatActivity {
    private ModelCustomer customer;
    private EditText txtCustCode;
    private EditText txtCustName;
    private EditText txtCustCategory;
    private EditText txtCustAddress;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Delete").setIcon(R.drawable.ic_menu_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(0, 0, 0, "Done").setIcon(R.drawable.ic_menu_checkall)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }else if (item.getTitle().equals("Done")){
            this.onBtnProductListSave();
        }else if (item.getTitle().equals("Delete")){
            this.onBtnProductListDelete();
        }
        return super.onOptionsItemSelected(item);

    }

    private void onBtnProductListSave() {
        if (customer == null) customer = new ModelCustomer();

        try {
            customer.setCode(txtCustCode.getText().toString());
            customer.setName(txtCustName.getText().toString());
            customer.setCategory(txtCustCategory.getText().toString());
            customer.setAddress(txtCustAddress.getText().toString());
        }catch (Exception e){
            Log.d("exception", e.getMessage());
        }

        DBHelper db = DBHelper.getInstance(this);
        SQLiteDatabase trans = db.getWritableDatabase();
        customer.saveToDB(trans, true);

        Toast.makeText(this, "Customer berhasil diupdate", Toast.LENGTH_SHORT).show();
        this.finish();
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);

    }

    private void onBtnProductListDelete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Anda yakin menghapus data Customer ini?");
        builder.setPositiveButton("Confirm",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteCustomer();
                }
            });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void deleteCustomer(){
        if (customer == null) return;
        int id = customer.getId();
        if (id == 0) return;

        DBHelper db = DBHelper.getInstance(this);
        SQLiteDatabase trans = db.getWritableDatabase();
        customer.deleteFromDB(trans);

        Toast.makeText(this, "Customer berhasil dihapus", Toast.LENGTH_SHORT).show();
        this.finish();
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

            }
        }
        return super.dispatchTouchEvent( event );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        txtCustName = (EditText) findViewById(R.id.txtCustName);
        txtCustCode = (EditText) findViewById(R.id.txtCustCode);
        txtCustCategory = (EditText) findViewById(R.id.txtCustCategory);
        txtCustAddress = (EditText) findViewById(R.id.txtCustAddress);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle("Buat Customer Baru");
        }

        loadData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void loadData(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("customerFromList")) {
                customer = (ModelCustomer) intent.getSerializableExtra("customerFromList");
                getSupportActionBar().setTitle("Ubah Customer");
            }
        }

        if (customer == null){
            customer = new ModelCustomer();
        }else {
            txtCustCode.setText(customer.getCode());
            txtCustName.setText(customer.getName());
            txtCustCategory.setText(customer.getCategory());
            txtCustAddress.setText(customer.getAddress());

        }
    }

}
