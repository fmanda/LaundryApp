package com.fma.laundryapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.helper.CurrencyHelper;
import com.fma.laundryapp.helper.ImageHelper;
import com.fma.laundryapp.model.ModelProduct;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ProductListAdapter extends ArrayAdapter<ModelProduct> {
    private Context context;
    private List<ModelProduct> products;
    ImageHelper img = new ImageHelper(this.getContext());

    public ProductListAdapter(@NonNull Context context, List<ModelProduct> products) {
        super(context, R.layout.product_list_layout, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductHolder holder;
        if( convertView == null ){
            holder = new ProductHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_list_layout, parent, false);
            holder.productListName = (TextView) convertView.findViewById(R.id.productListName);
            holder.productListPrice = (TextView) convertView.findViewById(R.id.productListPrice);
            holder.productListImage = (ImageView) convertView.findViewById(R.id.productListImage);
            holder.linearLayoutProductList = (LinearLayout) convertView.findViewById(R.id.linearLayoutProductList);
            convertView.setTag(holder);
        }else{
            holder = (ProductHolder) convertView.getTag();
        }

        holder.product = products.get(position);

        holder.productListName.setText(holder.product.getName());
        holder.productListPrice.setText(CurrencyHelper.format(holder.product.getPrice()));
        holder.linearLayoutProductList.setTag(holder.product);

        img.setFileName(String.valueOf(holder.product.getId()));
        Bitmap bmp = img.load();
        if (bmp != null) {
            holder.productListImage.setImageBitmap(bmp);
        }else{
            holder.productListImage.setImageResource(R.color.emptyBackground);
        }


        return convertView;
    }

    class ProductHolder {
        ModelProduct product;
        TextView productListName;
        TextView productListPrice;
        ImageView productListImage;
        LinearLayout linearLayoutProductList;
    }
}


