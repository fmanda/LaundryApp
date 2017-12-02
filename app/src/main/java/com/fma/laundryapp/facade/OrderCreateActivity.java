package com.fma.laundryapp.facade;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.CategoryListAdapter;
import com.fma.laundryapp.controller.ControllerCustomer;
import com.fma.laundryapp.controller.ControllerProduct;
import com.fma.laundryapp.controller.ControllerSetting;
import com.fma.laundryapp.facade.fragment.OrderFinishFragment;
import com.fma.laundryapp.facade.fragment.PickProductFragment;
import com.fma.laundryapp.helper.CurrencyHelper;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.model.ModelCustomer;
import com.fma.laundryapp.model.ModelOrder;
import com.fma.laundryapp.model.ModelOrderPreset;

import java.util.List;


/**
 * Created by fma on 7/30/2017.
 */


public class OrderCreateActivity extends AppCompatActivity implements CategoryListAdapter.ItemClickListener{
    PickProductFragment pickProductFragment;
    OrderFinishFragment orderFinishFragment;
//    EditText txtSearchProductList;
    LinearLayout pnlCreateOrderSearch;
    ControllerProduct controllerProduct;
    RecyclerView rvCategory;
    public String categoryFilter = "";
    List<String> categories;
    SearchView txtSearchProduct;
    ModelOrder modelorder;
    TabLayout tabsPreset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        pnlCreateOrderSearch = (LinearLayout) findViewById(R.id.pnlCreateOrderSearch);
        orderFinishFragment = (OrderFinishFragment) getFragmentManager().findFragmentById(R.id.fragOrderFinish);
        pickProductFragment = (PickProductFragment) getFragmentManager().findFragmentById(R.id.fragPickProduct);

        pickProductFragment.setParent(this);
        pickProductFragment.setMode(isTablet());
        modelorder = pickProductFragment.orders;

        tabsPreset = (TabLayout) findViewById(R.id.tabsPreset);

        if (getSupportActionBar() != null){
            if (isTablet()){
                getSupportActionBar().hide();
            }else{
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                getSupportActionBar().setDisplayShowCustomEnabled(true);
                getSupportActionBar().setTitle("Buat Order Baru");
            }
        }

//        txtSearchProductList = (EditText) findViewById(R.id.txtSearchProductList);
//        txtSearchProductList.addTextChangedListener(new TextWatcher() {
//            Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
//            Runnable workRunnable;
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                handler.removeCallbacks(workRunnable);
//                workRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        searchProduct();
//                    }
//                };
//                handler.postDelayed(workRunnable, 500 /*delay*/);
//            }
//        });

        txtSearchProduct = (SearchView) findViewById(R.id.txtSearchProduct);
        txtSearchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchProduct(s);
                return false;
            }
        });

        //test category
        controllerProduct = new ControllerProduct(this);
        categories = controllerProduct.getListCategory();

//        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
//        if (rvCategory != null){
//            CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this,
//                    controllerProduct.getListCategory());
//            rvCategory.setAdapter(categoryListAdapter);
//            rvCategory.setLayoutManager(new GridLayoutManager(this, 1));
//            categoryListAdapter.setClickListener(this);
//        }

        //tab
        generateCategoryTab();
        generatePresetTab();
        loadData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent==null) return;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("modelOrder")) {
                SQLiteDatabase db = DBHelper.getInstance(this).getReadableDatabase();

                ModelOrder modelOrder = (ModelOrder) intent.getSerializableExtra("modelOrder");
                modelOrder.reLoadAll(db);
                pickProductFragment.loadModelOrder(modelOrder);
                pickProductFragment.restoreProductSelection();
                pickProductFragment.refreshAdapter();
                getSupportActionBar().setTitle("Update Order");
            }
        }



    }

    private void hideOnScreenKeyboard(View v) {
        if (v==null) return;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    private void generateCategoryTab() {
        TabLayout tabsCategory = (TabLayout) findViewById(R.id.tabsCategory);
        if (tabsCategory==null) return;
        tabsCategory.setPadding(0,0,0,0);

        tabsCategory.removeAllTabs();

        for (String category : categories) {
            TabLayout.Tab tab = tabsCategory.newTab();
            tab.setText(category);
            tabsCategory.addTab(tab);
        }


        tabsCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterCategory(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


    }

    private void generatePresetTab() {

//        if (tabsPreset==null) return;
//        tabsPreset.setPadding(0,0,0,0);
//
//        tabsPreset.removeAllTabs();
//
//        ControllerSetting controllerSetting = new ControllerSetting(this);
//        List<ModelOrderPreset> modelOrderPresets = controllerSetting.getOrderPreset();
//
//        for (ModelOrderPreset modelOrderPreset : modelOrderPresets) {
//            TabLayout.Tab tab = tabsPreset.newTab();
//            tab.setText(modelOrderPreset.getName());
//            tab.setTag(modelOrderPreset);
//            tabsPreset.addTab(tab);
//        }
//
//        if (tabsPreset.getTabCount() == 0) {
//            tabsPreset.setVisibility(View.GONE);
//        }else{
//            setOrderPreset();
//        }
//
//        tabsPreset.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                setOrderPreset();
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {}
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {}
//        });


    }

    private void setOrderPreset() {
        TabLayout.Tab tab = tabsPreset.getTabAt(tabsPreset.getSelectedTabPosition());
        if (tab==null) return;
        ModelOrderPreset modelOrderPreset = (ModelOrderPreset) tab.getTag();
        if (modelOrderPreset==null) return;
        if (modelOrderPreset.getDefault_customer_id()>0){
            ModelCustomer modelCustomer = new ControllerCustomer(this).getCustomer(
                    modelOrderPreset.getDefault_customer_id()
            );
            if (modelCustomer==null) return;
            modelorder.setCustomer(modelCustomer);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, "Done").setIcon(R.drawable.ic_menu_checkall)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            return backToBrowseOrder();
        }else if (item.getTitle() == "Done"){
            this.onBtnProductListSave(null);
        }
        return super.onOptionsItemSelected(item);

    }

    public boolean backToBrowseOrder(){
        this.finish();
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    hideOnScreenKeyboard(v);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


    public void onBtnProductListSave(View view){
        Intent intent;
        if (!isTablet()) {
            intent = new Intent(this, OrderFinishActivity.class);
            intent.putExtra("orderFromProductList", pickProductFragment.orders);
            startActivity(intent);
        }else{
            Toast.makeText(this, "make order tablet here", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isTablet(){
        Boolean tabletMode = false;
        if (orderFinishFragment != null) {
            tabletMode = orderFinishFragment.isInLayout();
        }
        return tabletMode;
    }

    public void summaryOrder(){
        if (isTablet()){
            orderFinishFragment.loadOrder(pickProductFragment.orders);
        }

        TextView txtSummaryProductList = (TextView) findViewById(R.id.txtSummaryProductList);
        txtSummaryProductList.setText("Total: " + CurrencyHelper.format(pickProductFragment.orders.getSummary()) );
    }

    private void searchProduct(String productName){
        pickProductFragment.products.clear();
        pickProductFragment.products.addAll(
                pickProductFragment.controllerProduct.getProductListByFilter(
                        categoryFilter,  productName.toString()) );
        pickProductFragment.restoreProductSelection();
        pickProductFragment.refreshAdapter();
    }


    @Override
    public void onItemClick(View view, int position) {
        filterCategory(position);
    }

    private void filterCategory(int position) {
        categoryFilter = "";
        if (position>0) categoryFilter = categories.get(position);
        searchProduct("");
    }


}
