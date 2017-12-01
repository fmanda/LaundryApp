package com.fma.laundryapp.facade;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.fma.laundryapp.R;
import com.fma.laundryapp.adapter.ModifierInputAdapter;
import com.fma.laundryapp.controller.ControllerProduct;
import com.fma.laundryapp.helper.DBHelper;
import com.fma.laundryapp.helper.ImageHelper;
import com.fma.laundryapp.model.ModelModifier;
import com.fma.laundryapp.model.ModelProduct;

import java.io.IOException;


/**
 * Created by fma on 7/30/2017.
 */


public class ProductCreateActivity extends AppCompatActivity {
    private ModelProduct prod;
    private EditText txtSKU;
    private EditText txtProductName;
//    private EditText txtBarcode;
    private EditText txtSatuan;
//    private EditText txtCategory;
    private EditText txtPrice;
    private EditText txtPPN;
    private static final int SELECT_IMAGE = 0;
    public Bitmap bitmap;
//    public ImageView imgProduct;
//    private ListView listViewModifier;
//    private ModifierInputAdapter modifierInputAdapter;
    private CheckBox chkAllowDecimal;

    private ControllerProduct controllerProduct = new ControllerProduct(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Delete").setIcon(R.drawable.ic_menu_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(0, 0, 0, "Done").setIcon(R.drawable.ic_menu_checkall)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }else if (item.getTitle().equals("Done")){
            this.onBtnProductListSave();
        }else if (item.getTitle().equals("Delete")){
            this.onBtnProductListDelete();
        }
        return super.onOptionsItemSelected(item);

    }

    private void onBtnProductListSave() {
        if (prod == null) prod = new ModelProduct();

        try {
            prod.setSku(txtSKU.getText().toString());
            prod.setName(txtProductName.getText().toString());
//            prod.setBarcode(txtBarcode.getText().toString());
            prod.setUom(txtSatuan.getText().toString());
//            prod.setCategory(txtCategory.getText().toString());
            prod.setPrice(Double.parseDouble(txtPrice.getText().toString()));
            prod.setTax(Integer.parseInt(txtPPN.getText().toString()));
            prod.setAllow_decimal(0);
            if (chkAllowDecimal.isChecked()) prod.setAllow_decimal(1);

        }catch (Exception e){
            Log.d("exception", e.getMessage());
        }

        DBHelper db = DBHelper.getInstance(this);
        SQLiteDatabase trans = db.getWritableDatabase();
        prod.saveToDBAll(trans);


        //img


        if (bitmap != null) {
            ImageHelper img = new ImageHelper(this);
            img.setFileName(String.valueOf(prod.getId()));
            img.save(bitmap);
        }

        Toast.makeText(this, "Product berhasil diupdate", Toast.LENGTH_SHORT).show();
        this.finish();
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);

    }

    private void onBtnProductListDelete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Anda yakin menghapus data product ini?");
        builder.setPositiveButton("Confirm",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteProduct();
                }
            });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void deleteProduct(){
        if (prod == null) return;
        int id = prod.getId();
        if (id == 0) return;

        DBHelper db = DBHelper.getInstance(this);
        SQLiteDatabase trans = db.getWritableDatabase();
        prod.deleteFromDB(trans);

        Toast.makeText(this, "Product berhasil dihapus", Toast.LENGTH_SHORT).show();
        this.finish();
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        txtSKU = (EditText) findViewById(R.id.txtSKU);
        txtProductName = (EditText) findViewById(R.id.txtProductName);
//        txtBarcode = (EditText) findViewById(R.id.txtBarcode);
        txtSatuan = (EditText) findViewById(R.id.txtSatuan);
//        txtCategory = (EditText) findViewById(R.id.txtCategory);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtPPN = (EditText) findViewById(R.id.txtPPN);
        chkAllowDecimal = (CheckBox) findViewById(R.id.chkAllowDecimal);
//        imgProduct = (ImageView) findViewById(R.id.imgProduct);



        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle("Buat Product Baru");
        }

//        listViewModifier = (ListView) findViewById(R.id.listViewModifier);

        loadData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void loadData(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("productFromList")) {
                prod = (ModelProduct) intent.getSerializableExtra("productFromList");
//                controllerProduct.loadModifier(prod);
                getSupportActionBar().setTitle("Ubah Product");
            }
        }

        if (prod == null){
            prod = new ModelProduct();
        }else {
            txtSKU.setText(prod.getSku());
            txtProductName.setText(prod.getName());
//            txtBarcode.setText(prod.getBarcode());
            txtSatuan.setText(prod.getUom());
//            txtCategory.setText(prod.getCategory());
            txtPrice.setText(String.valueOf(prod.getPrice()));
            txtPPN.setText(String.valueOf(prod.getTax()));
            chkAllowDecimal.setChecked(prod.getAllow_decimal() == 1);

            ImageHelper img = new ImageHelper(this);
            img.setFileName(String.valueOf(prod.getId()));
            bitmap = img.load();
//            imgProduct.setImageBitmap(bitmap);
        }

        //loaditems

//        modifierInputAdapter = new ModifierInputAdapter(this, prod.modifiers);
//        listViewModifier.setAdapter(modifierInputAdapter);
    }

    public void onClickBtnImgProduct(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE){
            if (resultCode == Activity.RESULT_OK){
                if (data != null) {
                    try{
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 300,300);
//                        imgProduct.setImageBitmap(bitmap);

                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public void onClickAddModifier(View v){
//        prod.addItem(new ModelModifier());
//        modifierInputAdapter.notifyDataSetChanged();
//    }
//
//    public void onDelModifierClick(View v){
//        ModelModifier modelModifier = (ModelModifier) v.getTag();
//        prod.removeItem(modelModifier);
//        modifierInputAdapter.notifyDataSetChanged();
//    }

}
