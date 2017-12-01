package com.fma.laundryapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.model.ModelPrinter;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class PrinterListAdapter extends RecyclerView.Adapter<PrinterListAdapter.ViewHolder> {
    private Context context;
    private List<ModelPrinter> printers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public PrinterListAdapter(Context context, List<ModelPrinter> printers) {
        this.context = context;
        this.printers = printers;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.printer_list_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.printer = printers.get(i);
        viewHolder.txtPrinterName.setText(viewHolder.printer.getName());
        viewHolder.txtPrinterMac.setText(viewHolder.printer.getMac());
    }

    @Override
    public int getItemCount() {
        return printers.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelPrinter printer;
        public TextView txtPrinterName;
        public TextView txtPrinterMac;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPrinterName = (TextView) itemView.findViewById(R.id.txtPrinterName);
            txtPrinterMac=(TextView) itemView.findViewById(R.id.txtPrinterMac);
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


