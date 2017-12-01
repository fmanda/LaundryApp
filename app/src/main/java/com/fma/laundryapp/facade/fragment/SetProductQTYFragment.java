package com.fma.laundryapp.facade.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.ModifierPickAdapter;
import com.fma.laundryapp.model.LookupProduct;
import com.fma.laundryapp.model.ModelModifier;

import java.util.List;

/**
 * Created by fma on 8/13/2017.
 */

public class SetProductQTYFragment extends DialogFragment implements View.OnClickListener {
    LookupProduct modelProduct;
    ProductQTYListener productQTYListener;
    TextView txtNotes;
    EditText txtQty;
    Double qty = 1.0;

    public interface ProductQTYListener{
        void onProductQTYChanged(LookupProduct product, Double qty, String notes);
    }

    public void setSelectListener(ProductQTYListener productQTYListener){
        this.productQTYListener = productQTYListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setqtyproduct, container, false);
        getDialog().setTitle("Input QTY Product");

        Button btnYes = (Button) view.findViewById(R.id.btnYes);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        txtQty = (EditText) view.findViewById(R.id.txtQty);
        txtNotes = (TextView) view.findViewById(R.id.txtNotes);

        txtQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
//        txtQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            }
//        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                modelProduct.clearSelectedModifiers();
                dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                productQTYListener.onProductQTYChanged(modelProduct, qty, txtNotes.getText().toString());
                dismiss();
            }
        });

//        btnIncrease.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                addQty(1);
//            }
//        });
//
//        btnDecrease.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                addQty(-1);
//            }
//        });

//        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        Button btn_num_0 = (Button) view.findViewById(R.id.btn_num_0);
        Button btn_num_1 = (Button) view.findViewById(R.id.btn_num_1);
        Button btn_num_2 = (Button) view.findViewById(R.id.btn_num_2);
        Button btn_num_3 = (Button) view.findViewById(R.id.btn_num_3);
        Button btn_num_4 = (Button) view.findViewById(R.id.btn_num_4);
        Button btn_num_5 = (Button) view.findViewById(R.id.btn_num_5);
        Button btn_num_6 = (Button) view.findViewById(R.id.btn_num_6);
        Button btn_num_7 = (Button) view.findViewById(R.id.btn_num_7);
        Button btn_num_8 = (Button) view.findViewById(R.id.btn_num_8);
        Button btn_num_9 = (Button) view.findViewById(R.id.btn_num_9);
        Button btn_num_back = (Button) view.findViewById(R.id.btn_num_back);
        Button btn_num_clear = (Button) view.findViewById(R.id.btn_num_clear);
        Button btn_num_plus1 = (Button) view.findViewById(R.id.btn_num_plus1);
        Button btn_num_plus05 = (Button) view.findViewById(R.id.btn_num_plus05);
        Button btn_num_plus01 = (Button) view.findViewById(R.id.btn_num_plus01);

        btn_num_0.setOnClickListener(this);
        btn_num_1.setOnClickListener(this);
        btn_num_2.setOnClickListener(this);
        btn_num_3.setOnClickListener(this);
        btn_num_4.setOnClickListener(this);
        btn_num_5.setOnClickListener(this);
        btn_num_6.setOnClickListener(this);
        btn_num_7.setOnClickListener(this);
        btn_num_8.setOnClickListener(this);
        btn_num_9.setOnClickListener(this);
        btn_num_back.setOnClickListener(this);
        btn_num_clear.setOnClickListener(this);
        btn_num_plus1.setOnClickListener(this);
        btn_num_plus05.setOnClickListener(this);
        btn_num_plus01.setOnClickListener(this);

        return view;
    }


    public void setProduct(LookupProduct modelProduct){
        this.modelProduct = modelProduct;
    }

    private void addQty(double qty){
        this.qty += qty;
        if (this.qty<1) this.qty=1.0;
        txtQty.setText(String.valueOf(this.qty));
    }

    @Override
    public void onStart() {
        super.onStart();
//        Dialog dialog = getDialog();
//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            txtQty.append(((TextView) v).getText());
            return;
        }
        switch (v.getId()) {
            case R.id.btn_num_clear: { // handle clear button
                txtQty.setText(null);
            }
            break;
            case R.id.btn_num_back: { // handle backspace button
                // delete one character
                Editable editable = txtQty.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }
            }
            break;
            case R.id.btn_num_plus1: { // handle clear button
                Double qty = Double.parseDouble(txtQty.getText().toString())  + 1;
                txtQty.setText(qty.toString());
            }
            break;
            case R.id.btn_num_plus05: { // handle clear button
                Double qty = Double.parseDouble(txtQty.getText().toString())  + 0.5;
                txtQty.setText(qty.toString());
            }
            break;
            case R.id.btn_num_plus01: { // handle clear button
                Double qty = Double.parseDouble(txtQty.getText().toString())  + 0.1;
                txtQty.setText(qty.toString());
            }
            break;
        }

    }
}