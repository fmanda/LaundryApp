package com.fma.laundryapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.model.ModelModifier;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ModifierPickAdapter extends ArrayAdapter<ModelModifier> {
    private Context context;
    private List<ModelModifier> modifiers;

    public ModifierPickAdapter(@NonNull Context context, List<ModelModifier> modifiers) {
        super(context, R.layout.modifier_pick_layout, modifiers);
        this.context = context;
        this.modifiers = modifiers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ModifierPickHolder holder;
        if( convertView == null ){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.modifier_pick_layout, parent, false);

            holder = new ModifierPickHolder(
                (CheckBox) convertView.findViewById(R.id.ckModifierName),
                (TextView) convertView.findViewById(R.id.txtModifierPrice)
            );
            convertView.setTag(holder);
        }else{
            holder = (ModifierPickHolder) convertView.getTag();
        }

        holder.modelModifier = modifiers.get(position);

        if (holder.modelModifier.getName() != "") {
            holder.ckModifierName.setText(holder.modelModifier.getName());
            holder.ckModifierName.setChecked(holder.modelModifier.checked);
            holder.txtModifierPrice.setText(String.valueOf(holder.modelModifier.getPrice()));
        }

        return convertView;
    }


    class ModifierPickHolder {
        ModelModifier modelModifier;
        CheckBox ckModifierName;
        TextView txtModifierPrice;

        ModifierPickHolder(final CheckBox ckModifier, TextView txtPrice){
            this.ckModifierName = ckModifier;
            this.txtModifierPrice = txtPrice;

            this.ckModifierName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelModifier.checked = ckModifierName.isChecked();
                }
            });
        }
    }

}



