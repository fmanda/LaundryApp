package com.fma.laundryapp.helper;

import android.content.Context;

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

    public OrderPrinterHelper(Context context) {
        super(context);

        controllerCustomer = new ControllerCustomer(context);
        cashierPrinter = setting.getSettingStr(settings,"cashier_printer");
        kitchenPrinter = setting.getSettingStr(settings,"kitchen_printer");
        printToCashier = Boolean.parseBoolean(setting.getSettingStr(settings,"print_to_cashier"));
        printToKitchen = Boolean.parseBoolean(setting.getSettingStr(settings,"print_to_kitchen"));
        printCustomerInfo = Boolean.parseBoolean(setting.getSettingStr(settings,"print_customer_info"));
        singleLineProduct = Boolean.parseBoolean(setting.getSettingStr(settings,"single_line_product"));

    }

    public void PrintOrderPayment(ModelOrder modelOrder){
        if (printToCashier == Boolean.FALSE) return;
        setPrinterName(cashierPrinter);

        if (!ConnectPrinter()) return;


        ControllerProduct controllerproduct = new ControllerProduct(context);
        PrintLine(alignCenter(), Boolean.FALSE);

        //header
        PrintLine(setting.getSettingStr(settings, "company_name"));
        PrintLine(setting.getSettingStr(settings, "company_address"));
        PrintLine(setting.getSettingStr(settings, "company_phone"));

        //sub header
        PrintLine(alignLeft(), Boolean.TRUE);
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
        PrintLine(alignLeft(), Boolean.FALSE);
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
                PrintLine(alignLeft(), Boolean.FALSE);
                PrintLine(description);
                PrintLine(alignRight(), Boolean.FALSE);
                PrintLine(subtotal);

            }
        }

        //order summary
        PrintLine(getSingleSeparator());
        PrintLine(mergeLeftRight("TOTAL", CurrencyHelper.format(modelOrder.getAmount(),Boolean.FALSE) ));
        PrintLine(mergeLeftRight("BAYAR", CurrencyHelper.format(modelOrder.getTotalCustPayment(),Boolean.FALSE) ));
        PrintLine(mergeLeftRight("KEMBALI", CurrencyHelper.format(modelOrder.getChange() ) ));

        PrintLine("\n");
        PrintLine(alignCenter(), Boolean.FALSE);
        PrintLine(setting.getSettingStr(settings, "print_footer"));
        PrintLine("\n");

        ClosePrinter();
    }


}
