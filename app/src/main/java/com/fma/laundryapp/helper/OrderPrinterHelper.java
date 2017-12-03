package com.fma.laundryapp.helper;

import android.content.Context;
import android.widget.Toast;

import com.fma.laundryapp.controller.ControllerCustomer;
import com.fma.laundryapp.controller.ControllerProduct;
import com.fma.laundryapp.model.ModelCustomer;
import com.fma.laundryapp.model.ModelOrder;
import com.fma.laundryapp.model.ModelOrderItem;
import com.fma.laundryapp.model.ModelProduct;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by fmanda on 08/22/17.
 */

public class OrderPrinterHelper extends PrinterHelper {

    String cashierPrinter;
    String kitchenPrinter;
    Boolean printToCashier;
    Boolean printToKitchen;
    Boolean printCustomerInfo;
    Boolean singleLineProduct;
    ControllerCustomer controllerCustomer;
    ControllerProduct controllerproduct;
    private static OrderPrinterHelper mInstance;
    private ModelOrder modelOrder;

    public OrderPrinterHelper(Context context) {
        super(context);

        controllerCustomer = new ControllerCustomer(context);
        cashierPrinter = setting.getSettingStr(settings,"cashier_printer");
        kitchenPrinter = setting.getSettingStr(settings,"kitchen_printer");
        printToCashier = Boolean.parseBoolean(setting.getSettingStr(settings,"print_to_cashier"));
        printToKitchen = Boolean.parseBoolean(setting.getSettingStr(settings,"print_to_kitchen"));
        printCustomerInfo = Boolean.parseBoolean(setting.getSettingStr(settings,"print_customer_info"));
        singleLineProduct = Boolean.parseBoolean(setting.getSettingStr(settings,"single_line_product"));
        controllerproduct = new ControllerProduct(context);

    }

    public static synchronized OrderPrinterHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new OrderPrinterHelper(context.getApplicationContext());
        }
        return mInstance;
    }


    public void PrintOrderPayment(ModelOrder modelOrder){
        if (printToCashier == Boolean.FALSE) return;
        setPrinterName(cashierPrinter);
        if (!ConnectDevice()) return;
        this.mPrinter = new BluetoothPrinter(bluetoothDevice);

        this.modelOrder = modelOrder;
        this.mPrinter.connectPrinter(new BluetoothPrinter.PrinterConnectListener() {
             @Override
             public void onConnected() {
                DoPrintOrderPayment();
                mPrinter.finish();
             }

             @Override
             public void onFailed() {
                 Toast.makeText(context, "Fail to Print", Toast.LENGTH_SHORT).show();
             }
         });

    }

    private void DoPrintOrderPayment(){
        alignCenter();
        //header
        PrintLine(setting.getSettingStr(settings, "company_name"));
        PrintLine(setting.getSettingStr(settings, "company_address"));
        PrintLine(setting.getSettingStr(settings, "company_phone"));
        //sub header
        alignLeft();
        PrintLine("NO   : " + modelOrder.getOrderno() );
        if (printCustomerInfo){
            ModelCustomer modelCustomer = controllerCustomer.getCustomer(modelOrder.getCustomer_id());
            if (modelCustomer!=null)
                PrintLine("CUST : " + modelCustomer.getName() );
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("id", "ID"));
        PrintLine("TGL  : " + df.format(modelOrder.getOrderdate()));
        PrintLine(getDoubleSeparator());

        //detail order
        alignLeft();
        for (ModelOrderItem item : modelOrder.getItems()) {
            ModelProduct product = item.getProduct();
            if (product == null) product = controllerproduct.retrieveProduct(item.getProduct_id());

            int qty = ((Double) item.getQty()).intValue();
            Double lineTotal = item.getTotal();
            String description = product.getName() + " x" + String.valueOf(qty) +" ";
            String subtotal = CurrencyHelper.format(lineTotal,Boolean.FALSE);

            if (singleLineProduct){
                PrintLine(mergeLeftRight(description, subtotal));
            }else {
                alignLeft();
                PrintLine(description);
                alignRight();
                PrintLine(subtotal);

            }
        }

        //order summary
        PrintLine(getSingleSeparator());
        PrintLine(mergeLeftRight("TOTAL", CurrencyHelper.format(modelOrder.getAmount(),Boolean.FALSE) ));
        PrintLine(mergeLeftRight("BAYAR", CurrencyHelper.format(modelOrder.getTotalCustPayment(),Boolean.FALSE) ));
        PrintLine(mergeLeftRight("KEMBALI", CurrencyHelper.format(modelOrder.getChange() ) ));
        PrintLine("\n");
        alignCenter();
        PrintLine(setting.getSettingStr(settings, "print_footer"));
        PrintLine("\n");

//        ClosePrinter();
    }



}
