package com.fma.laundryapp.facade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.OrderPresetAdapter;
import com.fma.laundryapp.controller.ControllerSetting;
import com.fma.laundryapp.model.ModelOrderPreset;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class PresetActivity extends BaseActivity implements OrderPresetAdapter.ItemClickListener {

    private List<ModelOrderPreset> modelOrderPresets;
    private ControllerSetting controllerSetting = new ControllerSetting(this);
    OrderPresetAdapter orderPresetAdapter;
    RecyclerView recyclerView;

    public void fabOnClick(){
        Intent intent = new Intent(this, PresetCreateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_preset, this.mainframe);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreatePreset);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick();
            }
        });

        controllerSetting = new ControllerSetting(this);
        modelOrderPresets = controllerSetting.getOrderPreset();
        orderPresetAdapter = new OrderPresetAdapter(this, modelOrderPresets);

        recyclerView = (RecyclerView) this.findViewById(R.id.rvOrderPresets);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(orderPresetAdapter);

        orderPresetAdapter.setClickListener(this);
    }

    public void showEditor(ModelOrderPreset modelOrderPreset){
        Intent intent = new Intent(this, PresetCreateActivity.class);
        intent.putExtra("modelOrderPreset", modelOrderPreset);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        showEditor(modelOrderPresets.get(position));
    }
}
