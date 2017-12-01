package com.fma.laundryapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.model.ModelModifier;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ModifierInputAdapter extends ArrayAdapter<ModelModifier> {
    private Context context;
    private List<ModelModifier> modifiers;

    public ModifierInputAdapter(@NonNull Context context, List<ModelModifier> modifiers) {
        super(context, R.layout.modifier_input_layout, modifiers);
        this.context = context;
        this.modifiers = modifiers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ModifierInputHolder holder;
        if( convertView == null ){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.modifier_input_layout, parent, false);


            holder = new ModifierInputHolder(
                (TextView) convertView.findViewById(R.id.txtModifierName),
                (TextView) convertView.findViewById(R.id.txtModifierPrice),
                (FloatingActionButton) convertView.findViewById(R.id.fabDelModifier)
            );

            convertView.setTag(holder);

        }else{
            holder = (ModifierInputHolder) convertView.getTag();
        }

        holder.modelModifier = modifiers.get(position);
        holder.fabDelModifier.setTag(holder.modelModifier);
        if (holder.modelModifier.getName() != "") {
            holder.txtModifierName.setText(holder.modelModifier.getName());
            holder.txtModifierPrice.setText(String.valueOf(holder.modelModifier.getPrice()));
        }

        return convertView;
    }


    class ModifierInputHolder {
        ModelModifier modelModifier;
        TextView txtModifierName;
        TextView txtModifierPrice;
        FloatingActionButton fabDelModifier;

        public void saveChanges() {
            modelModifier.setName(txtModifierName.getText().toString());
            try {
                Double d = Double.parseDouble(txtModifierPrice.getText().toString());
                modelModifier.setPrice(d);
            }catch (Exception e){

            }

        }

        public ModifierInputHolder(TextView txtName, TextView txtPrice, FloatingActionButton fab){
            this.txtModifierName = txtName;
            this.txtModifierPrice = txtPrice;
            this.fabDelModifier = fab;

            txtModifierName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    saveChanges();
                }
            });

            txtModifierPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    saveChanges();
                }
            });
        }
    }

}



