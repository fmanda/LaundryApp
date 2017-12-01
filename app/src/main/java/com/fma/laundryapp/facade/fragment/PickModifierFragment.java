package com.fma.laundryapp.facade.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class PickModifierFragment extends DialogFragment {
    List<ModelModifier> modifiers;
    ModifierPickAdapter modifierPickAdapter;
    ListView listViewPickModifier;
    LookupProduct modelProduct;
    ModifierSelectListener modifierSelectListener;
    int qty = 1;
    TextView txtQty;
    TextView txtNotes;

    public interface ModifierSelectListener{
        void onFinishSelectModifider(LookupProduct product, Integer qty, String notes);
    }

    public void setSelectListener(ModifierSelectListener modifierSelectListener){
        this.modifierSelectListener = modifierSelectListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_modifier, container, false);
        getDialog().setTitle("Pilih Modifier");
        listViewPickModifier = (ListView) view.findViewById(R.id.listViewPickModifier);
        loadModifiers();

        Button btnYes = (Button) view.findViewById(R.id.btnYes);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        ImageButton btnIncrease = (ImageButton) view.findViewById(R.id.btnIncrease);
        ImageButton btnDecrease = (ImageButton) view.findViewById(R.id.btnDecrease);
        txtQty = (TextView) view.findViewById(R.id.txtQty);
        txtNotes = (TextView) view.findViewById(R.id.txtNotes);

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
                modifierSelectListener.onFinishSelectModifider(modelProduct, qty, txtNotes.getText().toString());
                dismiss();
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addQty(1);
            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addQty(-1);
            }
        });

//        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return view;
    }

    public void loadModifiers(){
//        modifiers = modelProduct.modifiers;
        modifierPickAdapter = new ModifierPickAdapter(getActivity(), modifiers);
        listViewPickModifier.setAdapter(modifierPickAdapter);
    }

    public void setProduct(LookupProduct modelProduct){
        this.modelProduct = modelProduct;
    }

    private void addQty(int qty){
        this.qty += qty;
        if (this.qty<1) this.qty=1;
        txtQty.setText(String.valueOf(this.qty));
    }

    @Override
    public void onStart() {
        super.onStart();
//        Dialog dialog = getDialog();
//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT);
    }



}
