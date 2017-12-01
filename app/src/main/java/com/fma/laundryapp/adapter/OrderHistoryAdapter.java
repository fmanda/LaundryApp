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

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<ModelOrder> orders;

    public OrderHistoryAdapter(@NonNull Context context, List<ModelOrder> orders) {
        this.context = context;
        this.orders = orders;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.order_history_layout, viewGroup, false);
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

        viewHolder.txtTotalPayment.setText("Bayar : " + CurrencyHelper.format(viewHolder.modelOrder.getTotalCustPayment()));
        viewHolder.txtChange.setText("Kembali : "+ CurrencyHelper.format(viewHolder.modelOrder.getChange()));

        ModelCustomer modelCustomer = viewHolder.modelOrder.getCustomer();
        if (modelCustomer!=null) {
            viewHolder.txtCustomer.setText(modelCustomer.getName());
        }

    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        ModelOrder modelOrder;
        TextView txtOrderNo;
        TextView txtOrderDate;
        TextView txtOrderAmount;

        TextView txtTotalPayment;
        TextView txtChange;
        TextView txtCustomer;

        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderNo = (TextView) itemView.findViewById(R.id.txtOrderNo);
            txtOrderDate = (TextView) itemView.findViewById(R.id.txtOrderDate);
            txtOrderAmount = (TextView) itemView.findViewById(R.id.txtOrderAmount);
            txtTotalPayment = (TextView) itemView.findViewById(R.id.txtTotalPayment);
            txtChange = (TextView) itemView.findViewById(R.id.txtChange);
            txtCustomer = (TextView) itemView.findViewById(R.id.txtCustomer);
        }
    }
}





