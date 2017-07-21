package com.os.foodie.ui.main.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.account.customer.CustomerAccountFragment;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressActivity;
import com.os.foodie.ui.home.customer.CustomerHomeFragment;
import com.os.foodie.ui.mybasket.MyBasketActivity;
import com.os.foodie.ui.notification.NotificationFragments;
import com.os.foodie.ui.order.restaurant.history.RestaurantOrderHistoryFragment;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListFragment;
import com.os.foodie.ui.payment.show.PaymentMethodActivity;
import com.os.foodie.ui.setting.SettingsFragment;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.DialogUtils;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerMainActivity extends BaseActivity implements CustomerMainMvpView, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private TextView tvCurrentUserName;

    private boolean isBackPress;

    private Toolbar toolbar;

    public NavigationView navigationView;

    private CustomerMainMvpPresenter<CustomerMainMvpView> customerMainMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initPresenter();
//        customerMainMvpPresenter = new CustomerMainPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        customerMainMvpPresenter.onAttach(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvCurrentUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_customer_main_tv_user_name);
        setCustomerName();

        setUp();

//        replaceFragment(CustomerHomeFragment.newInstance(), CustomerHomeFragment.TAG);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .disallowAddToBackStack()
//                .add(R.id.content_customer_main_cl_fragment, CustomerHomeFragment.newInstance(), CustomerHomeFragment.TAG)
//                .commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_customer_main_cl_fragment, CustomerHomeFragment.newInstance());
//            fragmentTransaction .addToBackStack(RestaurantOrderListFragment.TAG);
        fragmentTransaction.commit();
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        customerMainMvpPresenter = new CustomerMainPresenter(appDataManager, compositeDisposable);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (isBackPress) {
                finish();
            }

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

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            replaceFragment(CustomerHomeFragment.newInstance(), CustomerHomeFragment.TAG);

        } else if (id == R.id.nav_account) {

            replaceFragment(CustomerAccountFragment.newInstance(), CustomerAccountFragment.TAG);

        } else if (id == R.id.nav_order_history) {

            replaceFragment(RestaurantOrderHistoryFragment.newInstance(), RestaurantOrderHistoryFragment.TAG);

        } else if (id == R.id.nav_payment) {

            Intent intent = new Intent(this, PaymentMethodActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_my_basket) {

            Intent intent = new Intent(this, MyBasketActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_delivery_address) {

            Intent intent = new Intent(this, DeliveryAddressActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_notification) {

            replaceFragment(NotificationFragments.newInstance(), NotificationFragments.TAG);

        } else if (id == R.id.nav_settings) {

            replaceFragment(SettingsFragment.newInstance(), SettingsFragment.TAG);

        } else if (id == R.id.nav_logout) {

            DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    customerMainMvpPresenter.logout();
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

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_customer_main_cl_fragment);

                if (fragment instanceof CustomerHomeFragment) {

                    isBackPress = true;

                    Log.d("fragment", ">>CustomerHomeFragment");

                    navigationView.setCheckedItem(R.id.nav_home);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_customer_home));

                } else if (fragment instanceof RestaurantOrderHistoryFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>RestaurantOrderHistoryFragment");

                    navigationView.setCheckedItem(R.id.nav_order_history);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_restaurant_order_list));

                } else if (fragment instanceof CustomerAccountFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>CustomerAccountFragment");

                    navigationView.setCheckedItem(R.id.nav_account);
                    getSupportActionBar().setTitle(getString(R.string.action_my_account));

                } else if (fragment instanceof NotificationFragments) {

                    isBackPress = false;

                    Log.d("fragment", ">>NotificationFragments");

                    navigationView.setCheckedItem(R.id.nav_notification);
                    getSupportActionBar().setTitle(getString(R.string.notification));

                } else if (fragment instanceof SettingsFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>SettingsFragment");

                    navigationView.setCheckedItem(R.id.nav_settings);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_settings));

                }
            }
        });
    }

    public void replaceFragment(Fragment fragment, String TAG) {

//        getSupportFragmentManager().popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.content_customer_main_cl_fragment, fragment, TAG)
//                .addToBackStack(TAG)
//                .commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_customer_main_cl_fragment, fragment);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        customerMainMvpPresenter.dispose();
//        customerMainMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {
//        if (drawer != null)
//            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onFragmentDetached(String TAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
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

    @Override
    public void doLogout() {
//        customerMainMvpPresenter.setUserAsLoggedOut();

        Intent intent = new Intent(CustomerMainActivity.this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}