package com.fma.laundryapp.facade.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.CustomerListAdapter;
import com.fma.laundryapp.controller.ControllerCustomer;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.model.ModelCustomer;

import java.util.List;

/**
 * Created by fma on 8/13/2017.
 */

public class CreateCustomerFragment extends DialogFragment {

    private ModelCustomer customer;
    private EditText txtCustCode;
    private EditText txtCustName;
    private EditText txtCustTelp;
    private EditText txtCustAddress;
    ControllerCustomer controllerCustomer;


    private CustomerCreateListener customerCreateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_customer, container, false);
        getDialog().setTitle("Buat Customer Baru");
        LinearLayout lhDialogCreateCustomer = (LinearLayout) view.findViewById(R.id.lhDialogCreateCustomer);
        lhDialogCreateCustomer.setVisibility(View.VISIBLE);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnCreate = (Button) view.findViewById(R.id.btnCreate);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCustomer();
            }
        });


        controllerCustomer = new ControllerCustomer(getActivity());
        txtCustName = (EditText) view.findViewById(R.id.txtCustName);
        txtCustCode = (EditText) view.findViewById(R.id.txtCustCode);
        txtCustTelp = (EditText) view.findViewById(R.id.txtCustTelp);
        txtCustAddress = (EditText) view.findViewById(R.id.txtCustAddress);

        txtCustCode.setText(controllerCustomer.generateNewNumber());
        txtCustName.requestFocus();

        customer = new ModelCustomer();

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
    }

    public void test() {
        Dialog dialog = getDialog();
        Toast.makeText(dialog.getContext(), "test", Toast.LENGTH_SHORT).show();
        customerCreateListener.OnCreatedCustomer(null);
    }



    public interface CustomerCreateListener{
        void OnCreatedCustomer(ModelCustomer modelCustomer);
    }

    public void SetOnCreatedListener(CustomerCreateListener customerCreateListener){
        this.customerCreateListener = customerCreateListener;
    }

    public void SaveCustomer() { //event ketika button di click
        if (customer == null) customer = new ModelCustomer();

        try {
            customer.setCode(txtCustCode.getText().toString());
            customer.setName(txtCustName.getText().toString());
            customer.setPhone_number(txtCustTelp.getText().toString());
            customer.setAddress(txtCustAddress.getText().toString());
        }catch (Exception e){
            Log.d("exception", e.getMessage());
        }

        DBHelper db = DBHelper.getInstance(getActivity());
        SQLiteDatabase trans = db.getWritableDatabase();
        customer.saveToDB(trans, true);

        Toast.makeText(getActivity(), "New Customer updated", Toast.LENGTH_SHORT).show();
        customerCreateListener.OnCreatedCustomer(customer);
        dismiss();
    }

}
