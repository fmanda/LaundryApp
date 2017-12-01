package com.fma.laundryapp.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fma.laundryapp.R;
import com.fma.laundryapp.model.ModelCustomer;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private Context context;
    private List<ModelCustomer> customers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public CustomerListAdapter(Context context, List<ModelCustomer> customers) {
        this.context = context;
        this.customers = customers;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.customer_list_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.customer = customers.get(i);
        viewHolder.txtCustomerName.setText(viewHolder.customer.getName());
        viewHolder.txtCustomerAddress.setText(viewHolder.customer.getAddress());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelCustomer customer;
        public TextView txtCustomerName;
        public TextView txtCustomerAddress;
        public LinearLayout linearLayoutCustomer;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCustomerName = (TextView) itemView.findViewById(R.id.txtCustomerName);
            txtCustomerAddress = (TextView) itemView.findViewById(R.id.txtCustomerAddress);
            linearLayoutCustomer = (LinearLayout) itemView.findViewById(R.id.linearLayoutCustomer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}


