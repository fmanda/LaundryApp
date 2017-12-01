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
import com.fma.laundryapp.model.ModelCustomer;
import com.fma.laundryapp.model.ModelOrder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by fma on 7/30/2017.
 */

public class OrderHoldAdapter extends RecyclerView.Adapter<OrderHoldAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<ModelOrder> orders;
    SelectHoldOrderListener mListener;

    public OrderHoldAdapter(@NonNull Context context, List<ModelOrder> orders) {
        this.context = context;
        this.orders = orders;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.order_hold_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.modelOrder = orders.get(i);
        viewHolder.txtOrderNo.setText("#" + viewHolder.modelOrder.getOrderno());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy H:mm", new Locale("id", "ID"));

        viewHolder.txtOrderDate.setText(formatter.format(viewHolder.modelOrder.getOrderdate()));
        viewHolder.txtOrderAmount.setText(CurrencyHelper.format(viewHolder.modelOrder.getAmount()));

        ModelCustomer modelCustomer = viewHolder.modelOrder.getCustomer();
        if (modelCustomer!=null)
            viewHolder.txtCustomer.setText(modelCustomer.getName());

    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        ModelOrder modelOrder;
        TextView txtOrderNo;
        TextView txtOrderDate;
        TextView txtOrderAmount;
        TextView txtCustomer;

        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderNo = (TextView) itemView.findViewById(R.id.txtOrderNo);
            txtOrderDate = (TextView) itemView.findViewById(R.id.txtOrderDate);
            txtOrderAmount = (TextView) itemView.findViewById(R.id.txtOrderAmount);
            txtCustomer = (TextView) itemView.findViewById(R.id.txtCustomer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener==null) return;
            mListener.onSelectHoldOrder(modelOrder);
        }
    }

    public interface SelectHoldOrderListener{
        void onSelectHoldOrder(ModelOrder modelOrder);
    }

    public void SetSelectHoldOrderListener(SelectHoldOrderListener selectHoldOrderListener){
        this.mListener = selectHoldOrderListener;
    }


}





