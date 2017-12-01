package com.fma.laundryapp.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelSetting extends BaseModel {
    @TableField
    private String varname;
    @TableField
    private String varvalue;

    public ModelSetting(String varname, String varvalue){
        this.varname = varname;
        this.varvalue = varvalue;
    }

    public ModelSetting() {

    }

    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public String getVarvalue() {
        return varvalue;
    }

    public void setVarvalue(String varvalue) {
        this.varvalue = varvalue;
    }

    public static void initMetaData(SQLiteDatabase db) {
        //company_info
        new ModelSetting("company_name","[your-company-name]").saveToDB(db);
        new ModelSetting("company_address","[your company address]\n[your company address]").saveToDB(db);
        new ModelSetting("company_phone","[company-phone]").saveToDB(db);

        //printer
        new ModelSetting("printer_char_width","32").saveToDB(db);
        new ModelSetting("single_line_product","true").saveToDB(db);

        new ModelSetting("cashier_printer","").saveToDB(db);
        new ModelSetting("kitchen_printer","").saveToDB(db);
        new ModelSetting("print_to_kitchen","false").saveToDB(db);
        new ModelSetting("print_to_cashier","true").saveToDB(db);

        //struk
        new ModelSetting("print_customer_info","true").saveToDB(db);
        new ModelSetting("custom_header","").saveToDB(db);
        new ModelSetting("print_custom_header","false").saveToDB(db);
        new ModelSetting("print_footer","**** LUNAS ****").saveToDB(db);

//        new ModelSetting("company_id","405").saveToDB(db);
//        new ModelSetting("unit_id","1").saveToDB(db);
    }
}
