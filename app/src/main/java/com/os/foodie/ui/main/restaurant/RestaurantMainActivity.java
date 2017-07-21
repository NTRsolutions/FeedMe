package com.os.foodie.ui.main.restaurant;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.account.restaurant.RestaurantAccountFragment;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.discount.list.DiscountListFragment;
import com.os.foodie.ui.earning.EarningFragment;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuFragment;
import com.os.foodie.ui.merchantdetail.MerchantDetailFragments;
import com.os.foodie.ui.notification.NotificationFragments;
import com.os.foodie.ui.order.restaurant.history.RestaurantOrderHistoryFragment;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListFragment;
import com.os.foodie.ui.setting.SettingsFragment;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfileFragment;
import com.os.foodie.ui.showrestaurantprofile.ShowRestaurantProfileFragment;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.DialogUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;

public class RestaurantMainActivity extends BaseActivity implements RestaurantMainMvpView, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private TextView tvCurrentUserName;
    private CircleImageView ivRestaurantLogo;

    private boolean isBackPress;

    private Toolbar toolbar;

    public NavigationView navigationView;

    private RestaurantMainMvpPresenter<RestaurantMainMvpView> restaurantMainMvpPresenter;

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//
//        Log.d("onConfigurationChanged","Called");
//
//        getSupportFragmentManager().popBackStack(RestaurantOrderListFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
////        FragmentManager fm = getSupportFragmentManager();
////        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
////            fm.popBackStack();
////        }
//
//        super.onConfigurationChanged(newConfig);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main);

        initPresenter();
        restaurantMainMvpPresenter.onAttach(RestaurantMainActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ivRestaurantLogo = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_restaurant_main_civ_profile_pic);
        tvCurrentUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_restaurant_main_civ_restaurant_name);

        setRestaurantLogo();
        setCustomerName();

        setUp();

        if (AppController.get(this).getAppDataManager().isCurrentUserInfoInitialized()) {
//            replaceFragment(RestaurantOrderListFragment.newInstance(), RestaurantOrderListFragment.TAG);

//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .disallowAddToBackStack()
//                    .add(R.id.content_restaurant_main_cl_fragment, RestaurantOrderListFragment.newInstance(), RestaurantOrderListFragment.TAG)
//                    .commit();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager .beginTransaction();
            fragmentTransaction .replace(R.id.content_restaurant_main_cl_fragment, RestaurantOrderListFragment.newInstance());
//            fragmentTransaction .addToBackStack(RestaurantOrderListFragment.TAG);
            fragmentTransaction .commit();

        } else {

//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .disallowAddToBackStack()
//                    .add(R.id.content_restaurant_main_cl_fragment, RestaurantOrderListFragment.newInstance(), RestaurantOrderListFragment.TAG)
//                    .commit();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager .beginTransaction();
            fragmentTransaction.replace(R.id.content_restaurant_main_cl_fragment, RestaurantOrderListFragment.newInstance());
//            fragmentTransaction .addToBackStack(RestaurantOrderListFragment.TAG);
            fragmentTransaction .commit();

            replaceFragment(SetupRestaurantProfileFragment.newInstance(null), SetupRestaurantProfileFragment.TAG);

//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .disallowAddToBackStack()
//                    .add(R.id.content_restaurant_main_cl_fragment, SetupRestaurantProfileFragment.newInstance(null), SetupRestaurantProfileFragment.TAG)
//                    .commit();
        }
    }

    public void initPresenter() {
        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
//        restaurantMainMvpPresenter = new RestaurantMainPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        restaurantMainMvpPresenter = new RestaurantMainPresenter(appDataManager, compositeDisposable);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_restaurant_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_logout) {
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_account) {

            replaceFragment(RestaurantAccountFragment.newInstance(), RestaurantAccountFragment.TAG);

        } else if (id == R.id.nav_restaurant_profile) {

            if (AppController.get(this).getAppDataManager().isCurrentUserInfoInitialized())

                replaceFragment(ShowRestaurantProfileFragment.newInstance(), ShowRestaurantProfileFragment.TAG);

            else {
                replaceFragment(SetupRestaurantProfileFragment.newInstance(null), SetupRestaurantProfileFragment.TAG);
            }

        } else if (id == R.id.nav_order_history) {

            replaceFragment(RestaurantOrderHistoryFragment.newInstance(), RestaurantOrderHistoryFragment.TAG);

        } else if (id == R.id.nav_order_list) {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            replaceFragment(RestaurantOrderListFragment.newInstance(), RestaurantOrderListFragment.TAG);

        } else if (id == R.id.nav_menu_management) {

            replaceFragment(RestaurantMenuFragment.newInstance(), RestaurantMenuFragment.TAG);

        } else if (id == R.id.nav_discount_management) {

            replaceFragment(DiscountListFragment.newInstance(), DiscountListFragment.TAG);

        } else if (id == R.id.nav_view_earning) {

            replaceFragment(EarningFragment.newInstance(), EarningFragment.TAG);

        } else if (id == R.id.nav_merchant_details) {

            replaceFragment(MerchantDetailFragments.newInstance(), MerchantDetailFragments.TAG);

        } else if (id == R.id.nav_notification) {

            replaceFragment(NotificationFragments.newInstance(), NotificationFragments.TAG);

        } else if (id == R.id.nav_settings) {

            replaceFragment(SettingsFragment.newInstance(), SettingsFragment.TAG);

        } else if (id == R.id.nav_logout) {

            DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    restaurantMainMvpPresenter.logout();
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
    public void onClick(View v) {

    }

    @Override
    protected void setUp() {

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_restaurant_main_cl_fragment);

                if (fragment instanceof RestaurantOrderListFragment) {

                    isBackPress = true;

                    Log.d("fragment", ">>RestaurantOrderListFragment");

                    navigationView.setCheckedItem(R.id.nav_order_list);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_restaurant_order_list));

                } else if (fragment instanceof RestaurantAccountFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>RestaurantAccountFragment");

                    navigationView.setCheckedItem(R.id.nav_account);
                    getSupportActionBar().setTitle(getString(R.string.action_my_account));

                } else if (fragment instanceof SetupRestaurantProfileFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>SetupRestaurantProfileFragment");

                    navigationView.setCheckedItem(R.id.nav_restaurant_profile);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_restaurant_profile));

                } else if (fragment instanceof ShowRestaurantProfileFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>ShowRestaurantProfileFragment");

                    navigationView.setCheckedItem(R.id.nav_restaurant_profile);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_restaurant_profile));

                } else if (fragment instanceof RestaurantOrderHistoryFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>RestaurantOrderHistoryFragment");

                    navigationView.setCheckedItem(R.id.nav_order_history);
                    getSupportActionBar().setTitle(getString(R.string.action_order_history));

                } else if (fragment instanceof RestaurantMenuFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>RestaurantMenuFragment");

                    navigationView.setCheckedItem(R.id.nav_menu_management);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_restaurant_menu));

                } else if (fragment instanceof DiscountListFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>DiscountListFragment");

                    navigationView.setCheckedItem(R.id.nav_discount_management);
                    getSupportActionBar().setTitle(getString(R.string.action_discount_mangement));

                } else if (fragment instanceof EarningFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>EarningFragment");

                    navigationView.setCheckedItem(R.id.nav_view_earning);
                    getSupportActionBar().setTitle(getString(R.string.title_fragment_earning));

                } else if (fragment instanceof MerchantDetailFragments) {

                    isBackPress = false;

                    Log.d("fragment", ">>MerchantDetailFragments");

                    navigationView.setCheckedItem(R.id.nav_merchant_details);
                    getSupportActionBar().setTitle(getString(R.string.action_merchant_details));

                } else if (fragment instanceof NotificationFragments) {

                    isBackPress = false;

                    Log.d("fragment", ">>NotificationFragments");

                    navigationView.setCheckedItem(R.id.nav_notification);
                    getSupportActionBar().setTitle(getString(R.string.notification));

                } else if (fragment instanceof SettingsFragment) {

                    isBackPress = false;

                    Log.d("fragment", ">>SettingsFragment");

                    navigationView.setCheckedItem(R.id.nav_settings);
                    getSupportActionBar().setTitle(getString(R.string.action_settings));
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment, String TAG) {

//        getSupportFragmentManager().popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.content_restaurant_main_cl_fragment, fragment, TAG)
//                .addToBackStack(TAG)
//                .commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager .beginTransaction();
        fragmentTransaction .replace(R.id.content_restaurant_main_cl_fragment, fragment);
        fragmentTransaction .addToBackStack(TAG);
        fragmentTransaction .commit();
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .disallowAddToBackStack()
////                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                .replace(R.id.content_restaurant_main_cl_fragment, fragment, TAG)
//                .commit();
    }

    @Override
    protected void onDestroy() {
        restaurantMainMvpPresenter.dispose();
//        restaurantMainMvpPresenter.onDetach();
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

    public Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_restaurant_main_cl_fragment);
        return currentFragment;
    }

    public void setCustomerName() {
        tvCurrentUserName.setText(restaurantMainMvpPresenter.getCurrentUserName());
    }

    public void setRestaurantLogo() {

        Glide.with(this)
                .load(restaurantMainMvpPresenter.getRestaurantLogoURL())
//                .load("http://192.168.1.69/foodi/app/webroot//uploads/restaurant_images/restaurant_logo_1496410374.jpg")
//                .placeholder(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .error(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .into(ivRestaurantLogo);
    }

    public void navigateToSetRestaurantProfile(RestaurantProfileResponse restaurantProfileResponse) {
        replaceFragment(SetupRestaurantProfileFragment.newInstance(restaurantProfileResponse), SetupRestaurantProfileFragment.TAG);
    }

    public void navigateToShowRestaurantProfile() {
        replaceFragment(ShowRestaurantProfileFragment.newInstance(), ShowRestaurantProfileFragment.TAG);
    }

    @Override
    public void doLogout() {

//        restaurantMainMvpPresenter.setUserAsLoggedOut();

        Intent intent = new Intent(RestaurantMainActivity.this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}