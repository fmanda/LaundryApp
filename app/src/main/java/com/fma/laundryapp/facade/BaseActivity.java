package com.fma.laundryapp.facade;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fma.laundryapp.R;
import com.fma.laundryapp.controller.ControllerSetting;

public class BaseActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtCompany;
    TextView txtUnit;
    TextView txtUserName;
    ControllerSetting controllerSetting;

    protected FrameLayout mainframe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mainframe = (FrameLayout) findViewById(R.id.mainframe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);

        txtCompany = (TextView) headerView.findViewById(R.id.txtCompany);
        txtUnit = (TextView) headerView.findViewById(R.id.txtUnit);
        txtUserName = (TextView) headerView.findViewById(R.id.txtUserName);

        controllerSetting = new ControllerSetting(this);
        txtCompany.setText(controllerSetting.getCompanyName());
        txtUnit.setText(controllerSetting.getUnitName());
        txtUserName.setText(controllerSetting.getUserName());

//        if (controllerSetting.getCompanyID() == 0){
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }else if (controllerSetting.getUnitID() == 0){
//            startActivity(new Intent(this, SelectUnitActivity.class));
//            finish();
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else
        if (id == R.id.nav_order) {
            startActivity(new Intent(this, OrderActivity.class));
        } else if (id == R.id.nav_products) {
            startActivity(new Intent(this, ProductActivity.class));
        } else if (id == R.id.nav_customers) {
            startActivity(new Intent(this, CustomerActivity.class));
        } else if (id == R.id.nav_local_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_order_preset) {
            startActivity(new Intent(this, PresetActivity.class));
        } else if (id == R.id.nav_order_history) {
            startActivity(new Intent(this, OrderHistoryActivity.class));
        } else if (id == R.id.nav_sync) {
            startActivity(new Intent(this, RestActivity.class));
        }
        this.finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
