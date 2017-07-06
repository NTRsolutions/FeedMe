package com.os.foodie.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;

import java.util.Currency;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

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

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        splashMvpPresenter = new SplashPresenter(appDataManager, compositeDisposable);

//        splashMvpPresenter = new SplashPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public void openLocationInfoActivity() {

        splashMvpPresenter.dispose();
//        splashMvpPresenter.onDetach();

        Intent intent = new Intent(SplashActivity.this, LocationInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openSetupRestaurantProfileActivity() {

        splashMvpPresenter.dispose();
//        splashMvpPresenter.onDetach();

        Intent intent = new Intent(SplashActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openCustomerHomeActivity() {

        splashMvpPresenter.dispose();
//        splashMvpPresenter.onDetach();

        Intent intent = new Intent(SplashActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openRestaurantHomeActivity() {

        splashMvpPresenter.dispose();
//        splashMvpPresenter.onDetach();

        Intent intent = new Intent(SplashActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openWelcomeActivity() {

        splashMvpPresenter.dispose();
//        splashMvpPresenter.onDetach();

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
//        Log.d("Currency", "Currency Code: " + currency.getCurrencyCode());
//
//        String locale = getResources().getConfiguration().locale.getCountry();
//        Log.d("locale", ">>" + locale);
//        Log.d("getDisplayLanguage", ">>" + Locale.getDefault().getDisplayLanguage());
//
//        Locale localeTemp = new Locale("", locale);
////        Locale localeTemp = new Locale(Locale.getDefault().getDisplayLanguage(),locale);
//        Currency currencyTemp = Currency.getInstance(localeTemp);
//        Log.d("Currency", "Symbol: " + currencyTemp.getSymbol());
//
////        String locale = getResources().getConfiguration().locale.getCountry();
////        Log.d("getLocales size", ">>" + getResources().getConfiguration().getLocales().size());

        splashMvpPresenter.dispose();
//        splashMvpPresenter.onDetach();
        super.onDestroy();
    }
}