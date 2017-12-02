package com.fma.laundryapp.facade;

import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.laundryapp.R;
import com.fma.laundryapp.controller.ControllerSetting;
import com.fma.laundryapp.facade.fragment.SelectPrinterFragment;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.model.ModelPrinter;
import com.fma.laundryapp.model.ModelProduct;
import com.fma.laundryapp.model.ModelSetting;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class SettingActivity extends BaseActivity implements SelectPrinterFragment.SelectPrinterListener {
    ControllerSetting controllerSetting = new ControllerSetting(this);
    List<ModelSetting> settings;
    TextView txtCompanyName;
    TextView txtCompanyAddress;
    TextView txtCompanyPhone;
    Switch swPrintCashier;
    Switch swPrintKitchen;
    Button btnSetCashierPrinter;
    Button btnSetKitchenPrinter;
    TextView txtCashierPrinter;
    TextView txtKitchenPrinter;
    TextView txtPrintCharWidth;
    TextView txtFooter;
    TextView txtCustomHeader;
    Switch swCustomHeader;
    Switch swCustomerInfo;
    Switch swSingleProduct;
    Button btnSave;
    Button btnReset;
    Button btnDummy;
    TabLayout tabSetting;
    LinearLayout layoutGeneral;
    LinearLayout layoutAdvanced;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_setting, this.mainframe);

        registerAllView();
        switchPage();


        loadData();
    }

    private void registerAllView() {
        txtCompanyName =(TextView) findViewById(R.id.txtCompanyName);
        txtCompanyAddress =(TextView) findViewById(R.id.txtCompanyAddress);
        txtCompanyPhone =(TextView) findViewById(R.id.txtCompanyPhone);
        swPrintCashier =(Switch) findViewById(R.id.swPrintCashier);
        swPrintKitchen =(Switch) findViewById(R.id.swPrintKitchen);
        btnSetCashierPrinter =(Button) findViewById(R.id.btnSetCashierPrinter);
        btnSetKitchenPrinter =(Button) findViewById(R.id.btnSetKitchenPrinter);
        txtCashierPrinter =(TextView) findViewById(R.id.txtCashierPrinter);
        txtKitchenPrinter =(TextView) findViewById(R.id.txtKitchenPrinter);
        txtPrintCharWidth =(TextView) findViewById(R.id.txtPrintCharWidth);
        txtFooter =(TextView) findViewById(R.id.txtFooter);
        txtCustomHeader =(TextView) findViewById(R.id.txtCustomHeader);
        swCustomHeader =(Switch) findViewById(R.id.swCustomHeader);
        swCustomerInfo =(Switch) findViewById(R.id.swCustomerInfo);
        swSingleProduct =(Switch) findViewById(R.id.swSingleProduct);
        btnSave =(Button) findViewById(R.id.btnSave);
        btnReset =(Button) findViewById(R.id.btnReset);
        btnDummy =(Button) findViewById(R.id.btnDummy);
        tabSetting =(TabLayout) findViewById(R.id.tabSetting);
        layoutGeneral =(LinearLayout) findViewById(R.id.layoutGeneral);
        layoutAdvanced =(LinearLayout) findViewById(R.id.layoutAdvanced);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDB();
            }
        });

        btnDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dummyDB();
            }
        });

        tabSetting.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchPage();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnSetCashierPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPrinter("cashier_printer");
            }
        });
        btnSetKitchenPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPrinter("kitchen_printer");
            }
        });

    }

    private void switchPage(){
        Integer tabposition = tabSetting.getSelectedTabPosition();

        if (tabposition==0){
            layoutGeneral.setVisibility(View.VISIBLE);
            layoutAdvanced.setVisibility(View.GONE);
        }else{
            layoutAdvanced.setVisibility(View.VISIBLE);
            layoutGeneral.setVisibility(View.GONE);
        }
    }

    public void resetDB(){
        DBHelper db = DBHelper.getInstance(this);
        db.resetDatabase(db.getWritableDatabase());
        loadData();
        Toast.makeText(this, "Local DB reset", Toast.LENGTH_SHORT).show();
    }

    private void dummyDB(){
        DBHelper db = DBHelper.getInstance(this);
        ModelProduct.generateSampleData(db.getWritableDatabase());
        loadData();
        Toast.makeText(this, "Dummy Data Pump", Toast.LENGTH_SHORT).show();
    }

    private void saveToDB(){
        updateData();
        DBHelper db = DBHelper.getInstance(this);
        SQLiteDatabase trans = db.getWritableDatabase();
        try {
            for (ModelSetting setting : settings) {
                setting.saveToDB(trans);
            }
            Toast.makeText(this, "Setting Updated", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void selectPrinter(String varname){
        FragmentManager fm = getFragmentManager();
        ModelSetting settingprinter = getSetting(varname);
        if (settingprinter == null) return;

        SelectPrinterFragment selectPrinter = new SelectPrinterFragment();
        selectPrinter.setSelectListener(this, settingprinter);
        selectPrinter.show(fm, "Pilih Printer");
    }

    public void loadData(){
        try {
            settings = controllerSetting.getSettings(); //reload here
            txtCompanyName.setText(getSettingStr("company_name"));
            txtCompanyAddress.setText(getSettingStr("company_address"));
            txtCompanyPhone.setText(getSettingStr("company_phone"));
            txtCashierPrinter.setText(getSettingStr("cashier_printer"));
            txtKitchenPrinter.setText(getSettingStr("kitchen_printer"));
            swPrintCashier.setChecked(Boolean.valueOf(getSettingStr("print_to_cashier")));
            swPrintKitchen.setChecked(Boolean.valueOf(getSettingStr("print_to_kitchen")));
            swCustomHeader.setChecked(Boolean.valueOf(getSettingStr("print_custom_header")));
            swCustomerInfo.setChecked(Boolean.valueOf(getSettingStr("print_customer_info")));
            swSingleProduct.setChecked(Boolean.valueOf(getSettingStr("single_line_product")));
            txtFooter.setText(getSettingStr("print_footer"));

            if (getSettingStr("printer_char_width").equals("")){
                txtPrintCharWidth.setText("32");
            }else{
                txtPrintCharWidth.setText(getSettingStr("printer_char_width"));
            }

        }catch(Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setSetting(String varname, String varvalue){
        ModelSetting setting = getSetting(varname);
        if (setting != null) setting.setVarvalue(varvalue);
    }

    private String getSettingStr(String varname){
        ModelSetting setting = getSetting(varname);
        if (setting != null) {
            return setting.getVarvalue();
        }else{
            return "";
        }
    }

    private ModelSetting getSetting(String varname){
        try {
            for (ModelSetting setting : settings) {
                if (setting.getVarname().equals(varname))
                    return setting;
            }
        }catch(Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void updateData(){
        try {
            setSetting("company_name", txtCompanyName.getText().toString());
            setSetting("company_address", txtCompanyAddress.getText().toString());
            setSetting("company_phone", txtCompanyPhone.getText().toString());
            setSetting("cashier_printer", txtCashierPrinter.getText().toString());
            setSetting("kitchen_printer", txtKitchenPrinter.getText().toString());
            setSetting("print_footer", txtFooter.getText().toString());
            setSetting("printer_char_width", txtPrintCharWidth.getText().toString());
            setSetting("print_to_cashier", Boolean.toString(swPrintCashier.isChecked()));
            setSetting("print_to_kitchen", Boolean.toString(swPrintKitchen.isChecked()));
            setSetting("print_custom_header", Boolean.toString(swCustomHeader.isChecked()));
            setSetting("print_customer_info", Boolean.toString(swCustomerInfo.isChecked()));
            setSetting("single_line_product", Boolean.toString(swSingleProduct.isChecked()));
        }catch(Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSelectPrinter(ModelSetting setting,ModelPrinter printer) {
        setting.setVarvalue(printer.getName());
        if (setting.getVarname().equals("kitchen_printer"))
            txtKitchenPrinter.setText(setting.getVarvalue());
        if (setting.getVarname().equals("cashier_printer"))
            txtCashierPrinter.setText(setting.getVarvalue());
    }
}

