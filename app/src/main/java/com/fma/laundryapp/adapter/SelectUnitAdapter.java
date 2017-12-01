package com.fma.laundryapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.model.ModelUnit;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class SelectUnitAdapter extends RecyclerView.Adapter<SelectUnitAdapter.ViewHolder> {
    private Context context;
    private List<ModelUnit> units;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public SelectUnitAdapter(Context context, List<ModelUnit> units) {
        this.units = units;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.select_unit_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.modelUnit = units.get(i);
        viewHolder.txtUnitName.setText(viewHolder.modelUnit.getName());
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelUnit modelUnit;
        public TextView txtUnitName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUnitName = (TextView) itemView.findViewById(R.id.txtUnitName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(modelUnit);
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(ModelUnit modelUnit);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}


