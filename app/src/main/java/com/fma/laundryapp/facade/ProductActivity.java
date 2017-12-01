package com.fma.laundryapp.facade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.ProductListAdapter;
import com.fma.laundryapp.controller.ControllerProduct;
import com.fma.laundryapp.model.ModelProduct;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ProductActivity extends BaseActivity {
    private ListView productListView = null;
    private List<ModelProduct> products;
    private ControllerProduct controllerProduct = new ControllerProduct(this);
//    private ModelProduct product;

    public void fabOnClick(){
        Intent intent = new Intent(this, ProductCreateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_product, this.mainframe);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateProduct);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick();
            }
        });
//        fab.setVisibility(View.GONE);

        productListView = (ListView) findViewById(R.id.productListView);
        productListView.setItemsCanFocus(true);
        products = controllerProduct.getProductList();
        productListView.setAdapter(new ProductListAdapter(this, products));

    }

    public void onProductClick(View view){
        ModelProduct product = (ModelProduct) view.getTag();
        Intent intent = new Intent(this, ProductCreateActivity.class);
        intent.putExtra("productFromList", product);
        startActivity(intent);
//        Toast.makeText(this, String.valueOf(product.getId()), Toast.LENGTH_SHORT).show();

    }
}
