package com.fma.laundryapp.facade;
import android.os.Bundle;

import com.fma.laundryapp.R;
import com.fma.laundryapp.controller.ControllerSetting;

/**
 * Created by fma on 7/30/2017.
 */

public class MainActivity extends BaseActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, this.mainframe);
        ControllerSetting controllerSetting = new ControllerSetting(this);


//
    }

}

