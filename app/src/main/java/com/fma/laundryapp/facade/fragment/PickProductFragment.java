package com.fma.laundryapp.facade.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.OrderPickAdapter;
import com.fma.laundryapp.controller.ControllerOrder;
import com.fma.laundryapp.controller.ControllerProduct;
import com.fma.laundryapp.facade.OrderCreateActivity;
import com.fma.laundryapp.model.LookupProduct;
import com.fma.laundryapp.model.ModelOrder;
import com.fma.laundryapp.model.ModelOrderItem;

import java.util.List;

/**
 * Created by fmanda on 08/10/17.
 */

public class PickProductFragment extends Fragment implements  OrderPickAdapter.ItemClickListener{
    public List<LookupProduct> products;
    public ModelOrder orders = new ModelOrder();
    public ControllerProduct controllerProduct;
    OrderPickAdapter orderAdapter;
    RecyclerView recyclerView;

    View view;
    OrderCreateActivity parent;

    public void setParent(OrderCreateActivity parent){
        this.parent = parent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pick_product, container, false);
        controllerProduct = new ControllerProduct(getActivity());
        orders.setOrderno(new ControllerOrder(getActivity()).generateNewNumber());
        products = controllerProduct.getProductListByFilter("","");
        restoreProductSelection();

        orderAdapter = new OrderPickAdapter(getActivity(), products);
        orderAdapter.setClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvProducts);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        recyclerView.setAdapter(orderAdapter);

        refreshAdapter();

        return view;
    }

    public void restoreProductSelection() {
        for (LookupProduct product : products) {
            for (ModelOrderItem orderItem : orders.getItems()) {
                if (product.getId() == orderItem.getProduct().getId() ) {
                    product.setQty(orderItem.getQty());
                }
            }
        }
    }


    public void refreshAdapter(int position){
        refreshAdapter();
    }

    public void refreshAdapter(){
        recyclerView.getAdapter().notifyDataSetChanged();
        if (this.parent != null) parent.summaryOrder();
    }

    public void showDialogUpdateQty(LookupProduct product){
        controllerProduct = new ControllerProduct(getActivity());
//        if (!product.hasModifiers()) {
//            controllerProduct.loadModifier(product);
//        }else{
//            product.clearSelectedModifiers();
//        }

//        FragmentManager fm = getFragmentManager();
//        PickModifierFragment pickModifierFragment = new PickModifierFragment();
//        pickModifierFragment.setProduct(product);
//        pickModifierFragment.setSelectListener(new PickModifierFragment.ModifierSelectListener() {
//            @Override
//            public void onFinishSelectModifider(LookupProduct product, Integer qty, String notes) {
//                updateQtyProduct(product,qty, notes);
//                refreshAdapter();
//            }
//        });
//
//        pickModifierFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
//        pickModifierFragment.show(fm, "Pilih Modifier");
        FragmentManager fm = getFragmentManager();
        SetProductQTYFragment setProductQTYFragment = new SetProductQTYFragment();
        setProductQTYFragment.setProduct(product);
        setProductQTYFragment.setSelectListener(new SetProductQTYFragment.ProductQTYListener() {
            @Override
            public void onProductQTYChanged(LookupProduct product, Double qty, String notes) {
                updateQtyProduct(product,qty, notes);
                refreshAdapter();
            }
        });


        setProductQTYFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        setProductQTYFragment.show(fm, "Piih QTY");
    }

    public void updateQtyProduct(LookupProduct product, double inc, String notes){
//        product.incQty(inc);
        product.setQty(inc);
        orders.addItem(product, inc, notes);
    }

    public void setMode(Boolean isTablet){
        int numberOfColumns = 1;
        if (isTablet) {
            numberOfColumns = 3;
        };
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
    }

    @Override
    public void onItemClick(View view, int position) {
//        if (view.getId() == R.id.btnDecQtyProduct) {
//            updateQtyProduct(products.get(position), -1, "");
//        }else{
//            updateQtyProduct(products.get(position), 1, "");
//        }
//        refreshAdapter(position);
        showDialogUpdateQty(products.get(position));
    }

    @Override
    public void onItemLongClick(View view, int position) {
       showDialogUpdateQty(products.get(position));
    }


    public void loadModelOrder(ModelOrder modelOrder) {
        this.orders.copyObject(modelOrder);
    }
}
