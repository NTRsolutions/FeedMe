package com.os.foodie.ui.main.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.ui.account.customer.CustomerAccountFragment;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressActivity;
import com.os.foodie.ui.home.customer.CustomerHomeFragment;
import com.os.foodie.ui.mybasket.MyBasketActivity;
import com.os.foodie.ui.setting.SettingsFragment;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.DialogUtils;

public class CustomerMainActivity extends BaseActivity implements CustomerMainMvpView, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private TextView tvCurrentUserName;

    private Toolbar toolbar;

    private CustomerMainMvpPresenter<CustomerMainMvpView> customerMainMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        customerMainMvpPresenter = new CustomerMainPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvCurrentUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_customer_main_tv_user_name);
        setCustomerName();

        replaceFragment(CustomerHomeFragment.newInstance(), CustomerHomeFragment.TAG);
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
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.customer_home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            replaceFragment(CustomerHomeFragment.newInstance(), CustomerHomeFragment.TAG);

        } else if (id == R.id.nav_account) {

            replaceFragment(CustomerAccountFragment.newInstance(), CustomerAccountFragment.TAG);

        } else if (id == R.id.nav_order_history) {

        } else if (id == R.id.nav_payment) {

        } else if (id == R.id.nav_my_bucket) {

            Intent intent = new Intent(this, MyBasketActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_delivery_address) {

            Intent intent = new Intent(this, DeliveryAddressActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {

            replaceFragment(SettingsFragment.newInstance(), SettingsFragment.TAG);

        } else if (id == R.id.nav_logout) {

            DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    customerMainMvpPresenter.setUserAsLoggedOut();

                    Intent intent = new Intent(CustomerMainActivity.this, WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            };

            DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            DialogUtils.showAlert(this, R.mipmap.ic_logout,
                    R.string.alert_dialog_title_logout, R.string.alert_dialog_text_logout,
                    getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
                    getResources().getString(R.string.alert_dialog_bt_no), negativeButton);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void setUp() {
    }

    public void replaceFragment(Fragment fragment, String TAG) {

        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
//                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .replace(R.id.content_customer_main_cl_fragment, fragment, TAG)
                .commit();
    }

    @Override
    protected void onDestroy() {
        customerMainMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {
//        if (drawer != null)
//            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
//                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
//            if (drawer != null)
//                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setCustomerName() {
        tvCurrentUserName.setText(customerMainMvpPresenter.getCurrentUserName());
    }
}