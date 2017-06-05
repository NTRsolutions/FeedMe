package com.os.foodie.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.welcome.WelcomeActivity;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    private SplashMvpPresenter<SplashMvpView> splashMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initPresenter();
        splashMvpPresenter.onAttach(SplashActivity.this);
        splashMvpPresenter.waitAndGo();

//        Observable
//                .timer(AppConstants.SPLASH_DURATION, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//
//                        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//
////                        finish();
//                    }
//                });
    }


    public void initPresenter() {

        splashMvpPresenter = new SplashPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public void openLocationInfoActivity() {

        Intent intent = new Intent(SplashActivity.this, LocationInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openSetupRestaurantProfileActivity() {

        Intent intent = new Intent(SplashActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openCustomerHomeActivity() {

        Intent intent = new Intent(SplashActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openRestaurantHomeActivity() {

        Intent intent = new Intent(SplashActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openWelcomeActivity() {

        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void startSyncService() {
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void onDestroy() {

//        Locale swedishLocale = new Locale("en", "IN");
//        Currency currency = Currency.getInstance(swedishLocale);
//        Log.d("Currency", "Symbol: " + currency.getSymbol());

        splashMvpPresenter.onDetach();
        super.onDestroy();
    }
}