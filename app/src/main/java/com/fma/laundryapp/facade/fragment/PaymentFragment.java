package com.fma.laundryapp.facade.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.laundryapp.R;
import com.fma.laundryapp.controller.ControllerSetting;
import com.fma.laundryapp.facade.OrderActivity;
import com.fma.laundryapp.helper.CurrencyHelper;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.helper.OrderPrinterHelper;
import com.fma.laundryapp.model.ModelMoneyPreset;
import com.fma.laundryapp.model.ModelOrder;

import java.util.List;

/**
 * Created by fmanda on 08/10/17.
 */

public class PaymentFragment extends Fragment{
    private ModelOrder modelOrder;
    Button btnPreset0;
    Button btnPreset1;
    Button btnPreset2;
    Button btnPreset3;
    Button btnPreset4;
    Button btnReset;
    Button btnSavePayment;
    EditText txtCashAmt;
    EditText txtCardAmt;
    TextView txtTotalAmt;
    TextView txtPayAmt;
    TextView txtChangeAmt;
    TextWatcher txtCashAmtWatcher;
    TextWatcher txtCardAmtWatcher;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        btnPreset0 = (Button) view.findViewById(R.id.btnPreset0);
        btnPreset1 = (Button) view.findViewById(R.id.btnPreset1);
        btnPreset2 = (Button) view.findViewById(R.id.btnPreset2);
        btnPreset3 = (Button) view.findViewById(R.id.btnPreset3);
        btnPreset4 = (Button) view.findViewById(R.id.btnPreset4);
        btnReset = (Button) view.findViewById(R.id.btnReset);
        txtCashAmt = (EditText) view.findViewById(R.id.txtCashAmt);
        txtCardAmt = (EditText) view.findViewById(R.id.txtCardAmt);
        txtTotalAmt = (TextView) view.findViewById(R.id.txtTotalAmt);
        txtPayAmt = (TextView) view.findViewById(R.id.txtPayAmt);
        txtChangeAmt = (TextView) view.findViewById(R.id.txtChangeAmt);
        btnSavePayment = (Button) view.findViewById(R.id.btnSavePayment);

        txtCashAmtWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                double d = 0.0;
                try{
                    d = Double.parseDouble(s.toString());
                    modelOrder.setCashpayment(d);
                    calculateAmt(Boolean.FALSE);
                }catch(Exception e){
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        txtCardAmtWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                double d = 0.0;
                try{
                    d = Double.parseDouble(s.toString());
                    modelOrder.setCardpayment(d);
                    calculateAmt(Boolean.FALSE);
                }catch(Exception e){
//                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        txtCashAmt.addTextChangedListener(txtCashAmtWatcher);
        txtCardAmt.addTextChangedListener(txtCardAmtWatcher);

        btnSavePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePayment();
            }
        });

        return view;
    }

    private void savePayment() {
//        DBHelper db = new DBHelper(getActivity());
        DBHelper db = DBHelper.getInstance(getActivity());
        SQLiteDatabase trans = db.getWritableDatabase();

        modelOrder.setStatus(1);
        modelOrder.saveToDBAll(trans);

        OrderPrinterHelper printer = new OrderPrinterHelper(getActivity());
//        OrderPrinterHelper printer = OrderPrinterHelper.getInstance(getActivity());
        printer.PrintOrderPayment(modelOrder);

        startActivity(new Intent(getActivity(), OrderActivity.class));
    }

    private void calculateAmt(Boolean IsUpdateCashCard) {
        Double cashpayment =  modelOrder.getCashpayment() + modelOrder.getCardpayment();
        modelOrder.setChange(cashpayment - modelOrder.getAmount());
        if (cashpayment>modelOrder.getAmount()){
            modelOrder.setPayment(modelOrder.getAmount());
        }else {
            modelOrder.setPayment(cashpayment);
        }

        if (IsUpdateCashCard) {
            txtCashAmt.removeTextChangedListener(txtCashAmtWatcher);
            txtCardAmt.removeTextChangedListener(txtCardAmtWatcher);
            try {
                txtCashAmt.setText(modelOrder.getCashpayment().toString());
                txtCardAmt.setText(modelOrder.getCardpayment().toString());
            } finally {
                txtCashAmt.addTextChangedListener(txtCashAmtWatcher);
                txtCardAmt.addTextChangedListener(txtCardAmtWatcher);
            }
        }

        txtTotalAmt.setText(CurrencyHelper.format(modelOrder.getAmount()));
        txtPayAmt.setText(CurrencyHelper.format(cashpayment));
        txtChangeAmt.setText(CurrencyHelper.format(modelOrder.getChange()));
    }

    private void loadFromModel() {
        txtCashAmt.removeTextChangedListener(txtCashAmtWatcher);
        txtCardAmt.removeTextChangedListener(txtCardAmtWatcher);
        try{
            txtCashAmt.setText(modelOrder.getCashpayment().toString());
            txtCardAmt.setText(modelOrder.getCardpayment().toString());
        }finally {
            txtCashAmt.addTextChangedListener(txtCashAmtWatcher);
            txtCardAmt.addTextChangedListener(txtCardAmtWatcher);
        }

        calculateAmt(Boolean.TRUE);
        registerEventButton();
    }

    private void registerEventButton() {
        List<ModelMoneyPreset> presets = new ControllerSetting(getActivity()).getMoneyPreset(modelOrder.getAmount());
        int n = 0;

        for (ModelMoneyPreset preset : presets) {
            n++;
            switch (n){
                case 1 :
                    btnPreset1.setText(CurrencyHelper.format(preset.getMoneyval()));
                    btnPreset1.setTag(preset.getMoneyval());
                    break;
                case 2 :
                    btnPreset2.setText(CurrencyHelper.format(preset.getMoneyval()));
                    btnPreset2.setTag(preset.getMoneyval());
                    break;
                case 3 :
                    btnPreset3.setText(CurrencyHelper.format(preset.getMoneyval()));
                    btnPreset3.setTag(preset.getMoneyval());
                    break;
                case 4 :
                    btnPreset4.setText(CurrencyHelper.format(preset.getMoneyval()));
                    btnPreset4.setTag(preset.getMoneyval());
                    break;
                default : break;
            }
        }

        if (presets.size()<4){
            for (int i=4; i>=presets.size(); i--){
                switch (i){
                    case 1 : btnPreset1.setVisibility(View.GONE);
                    case 2 : btnPreset2.setVisibility(View.GONE);
                    case 3 : btnPreset3.setVisibility(View.GONE);
                    case 4 : btnPreset4.setVisibility(View.GONE);
                    default : break;
                }
            }
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag()==null) return;
                double payment = (double) v.getTag();
                modelOrder.setCashpayment(payment);
                calculateAmt(Boolean.TRUE);
            }
        };

        btnPreset1.setOnClickListener(onClickListener);
        btnPreset2.setOnClickListener(onClickListener);
        btnPreset3.setOnClickListener(onClickListener);
        btnPreset4.setOnClickListener(onClickListener);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelOrder.setCashpayment(0.0);
                modelOrder.setCashpayment(0.0);
                calculateAmt(Boolean.TRUE);
            }
        });

        btnPreset0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelOrder.setCashpayment(modelOrder.getAmount());
                calculateAmt(Boolean.TRUE);
            }
        });


    }

    public ModelOrder getModelOrder() {
        return modelOrder;
    }

    public void setModelOrder(ModelOrder modelOrder) {
        this.modelOrder = modelOrder;
        this.modelOrder.refreshAmount();
        loadFromModel();
    }



}
