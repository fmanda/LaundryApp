package com.fma.laundryapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.helper.CurrencyHelper;
import com.fma.laundryapp.model.ModelOrderItem;
import com.fma.laundryapp.model.ModelOrderModifier;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<ModelOrderItem> items;

    public OrderItemListAdapter(@NonNull Context context, List<ModelOrderItem> products) {
        this.context = context;
        this.items = products;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.order_item_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.item = this.items.get(i);
        Double qty = viewHolder.item.getQty();
        Double price = viewHolder.item.getTotal();

        viewHolder.txtOrderItemName.setText(
                viewHolder.item.getProduct().getName() + " x" + String.valueOf(qty.intValue())
        );

        viewHolder.txtOrderItemNotes.setText(viewHolder.item.getNotes());
        viewHolder.txtOrderItemModifier.setText("");

//        for (ModelOrderModifier modifier : viewHolder.item.modifiers){
//            if (!viewHolder.txtOrderItemModifier.getText().equals("")) viewHolder.txtOrderItemModifier.append("\n");
//            viewHolder.txtOrderItemModifier.append(modifier.getModifier());
//        }

        if (viewHolder.txtOrderItemModifier.getText().equals("")) viewHolder.txtOrderItemModifier.setVisibility(View.GONE);
        if (viewHolder.txtOrderItemNotes.getText().equals("")) viewHolder.txtOrderItemNotes.setVisibility(View.GONE);

        viewHolder.txtOrderItemTotal.setText(CurrencyHelper.format(qty*price));

    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        public ModelOrderItem item;
        public TextView txtOrderItemNotes;
        public TextView txtOrderItemModifier;
        public TextView txtOrderItemName;
        TextView txtOrderItemTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderItemTotal = (TextView) itemView.findViewById(R.id.txtOrderItemTotal);
            txtOrderItemName = (TextView) itemView.findViewById(R.id.txtOrderItemName);
            txtOrderItemModifier = (TextView) itemView.findViewById(R.id.txtOrderItemModifier);
            txtOrderItemNotes = (TextView) itemView.findViewById(R.id.txtOrderItemNotes);
        }
    }

}
