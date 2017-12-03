package com.fma.laundryapp.helper;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.fma.laundryapp.controller.ControllerSetting;
import com.fma.laundryapp.model.ModelSetting;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by fmanda on 08/21/17.
 */

public class PrinterHelper {
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;
//    OutputStream outputStream;
//    InputStream inputStream;
//    Thread workerThread;
//    byte[] readBuffer;
//    int readBufferPosition;
//    volatile boolean stopWorker;
    String value = "";
    Context context;
    String printerName = "EP5802AI";
    List<ModelSetting> settings;
    ControllerSetting setting;
    BluetoothPrinter mPrinter;

    int printerCharWidth = 32;

    public PrinterHelper(Context context){
        this.context = context;
        try {
            setting = new ControllerSetting(this.context);
            settings = setting.getSettings();
            printerCharWidth = Integer.valueOf(setting.getSettingStr(settings, "printer_char_width"));
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void setPrinterName(String printerName){
        this.printerName = printerName;
    }



    public boolean ConnectDevice(){
        if (bluetoothAdapter==null) bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {

            if(!bluetoothAdapter.isEnabled()){
                return false;
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if( (pairedDevices.size() > 0) && (!this.printerName.equals(""))) {
                for(BluetoothDevice device : pairedDevices){
                    if(device.getName().equals(printerName)) {
                        bluetoothDevice = device;
                        break;
                    }
                }
                if (bluetoothDevice == null) return false;
            }
            else{
                value+="No Printer Found";
                Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                return false;
            }
        }catch(Exception ex){
            value+=ex.toString()+ "\n" +" InitPrinter \n";
            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public String getDoubleSeparator(){
        String str = "";
        while (str.length()<printerCharWidth){
            str += "=";
        }
        return str;
    }

    public String getSingleSeparator(){
        String str = "";
        while (str.length()<printerCharWidth){
            str += "-";
        }
        return str;
    }

//    public void ClosePrinter() {
//        try {
//            outputStream.close();
//            bluetoothSocket.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

    public void PrintLine(String lineStr, Boolean withLineBreak){
        if (withLineBreak) lineStr += "\n";
        mPrinter.printText(lineStr);
//        try {
//            outputStream.write(lineStr.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void PrintLine(String lineStr){
        PrintLine(lineStr, Boolean.TRUE);
    }

    public void alignLeft() {
        mPrinter.setAlign(BluetoothPrinter.ALIGN_LEFT);
//        final byte[] AlignLeft = {27, 97,48};
//        String s = new String(AlignLeft);
//        return s;
    }

    public void alignCenter() {
        mPrinter.setAlign(BluetoothPrinter.ALIGN_CENTER);
//        final byte[] AlignCenter = {27, 97,49};
//        String s = new String(AlignCenter);
//        return s;
    }

    public void alignRight() {
        mPrinter.setAlign(BluetoothPrinter.ALIGN_RIGHT);
//        final byte[] AlignRight = {27, 97,50};
//        String s = new String(AlignRight);
//        return s;
    }

    public String mergeLeftRight(String sLeft, String sRight){
        int i = sLeft.length();
        int j = sRight.length();

        int paramInt = 0;
        while (paramInt < printerCharWidth - (i + j) )
        {
            sLeft += " ";
            paramInt += 1;
        }
        sLeft += sRight;
        return sLeft;
    }


}



