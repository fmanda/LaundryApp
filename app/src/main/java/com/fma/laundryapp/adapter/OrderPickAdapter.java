package com.fma.laundryapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fma.laundryapp.R;
import com.fma.laundryapp.helper.CurrencyHelper;
import com.fma.laundryapp.helper.ImageHelper;
import com.fma.laundryapp.model.LookupProduct;

import java.util.List;

public class OrderPickAdapter extends RecyclerView.Adapter<OrderPickAdapter.ViewHolder> {

    private List<LookupProduct> products;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    ImageHelper img;

    // data is passed into the constructor
    public OrderPickAdapter(Context context, List<LookupProduct> data) {
        img = new ImageHelper(context);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.products = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_pick_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LookupProduct product = products.get(position);

        holder.productListName.setText(product.getName());
        holder.productListPrice.setText(CurrencyHelper.format(product.getPrice(), Boolean.TRUE));

        Double qty = product.getQty();

        holder.productListQty.setText(String.format("%.1f", qty));
        holder.productListQty.append(" " + product.getUom());

        if (product.getQty() != 0) {
            holder.productListQty.setVisibility(View.VISIBLE);
            holder.productListImage.setVisibility(View.GONE);
//            holder.btnDecQtyProduct.setVisibility(View.VISIBLE);
//            holder.cvProduct.setCardBackgroundColor(Color.RED );
//            holder.cvProduct.setCardBackgroundColor( ContextCompat.getColor(this.context,
//                    R.color.colorLightOrange ));
            holder.cvProduct.setCardBackgroundColor( ContextCompat.getColor(this.context,
                    R.color.colorRowHighlight ));
        }else{
            holder.cvProduct.setCardBackgroundColor(Color.WHITE );

            img.setFileName(String.valueOf(product.getId()));
            Bitmap bmp = img.load();
            if (bmp != null) {
                holder.productListImage.setImageBitmap(bmp);
            }else{
                holder.productListImage.setImageResource(R.color.emptyBackground);
            }
            holder.productListQty.setVisibility(View.GONE);
            holder.productListImage.setVisibility(View.VISIBLE);
//            holder.btnDecQtyProduct.setVisibility(View.GONE);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return products.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView productListName;
        public TextView productListPrice;
        public TextView productListQty;
        public ImageView productListImage;
//        public ImageButton btnDecQtyProduct;
        public LinearLayout linearLayoutProductList;
        public CardView cvProduct;

        public ViewHolder(View itemView) {
            super(itemView);

            productListName = (TextView) itemView.findViewById(R.id.productListName);
            productListPrice = (TextView) itemView.findViewById(R.id.productListPrice);
            productListQty = (TextView) itemView.findViewById(R.id.productListQty);
            productListImage = (ImageView) itemView.findViewById(R.id.productListImage);
//            btnDecQtyProduct = (ImageButton) itemView.findViewById(R.id.btnDecQtyProduct);
            linearLayoutProductList = (LinearLayout) itemView.findViewById(R.id.linearLayoutProductList);
            cvProduct = (CardView) itemView.findViewById(R.id.cvProduct);

//            btnDecQtyProduct.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mClickListener != null) mClickListener.onItemLongClick(v, getAdapterPosition());
            return true;
        }
    }

    // convenience method for getting data at click position
    public LookupProduct getItem(int id) {
        return products.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

}