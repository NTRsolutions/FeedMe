package com.os.foodie.ui.splash;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.AppConstants;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    public SplashPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    private void decideNextActivity() {

//        Disposable dis = new Disposable() {
//            @Override
//            public void dispose() {
//                getMvpView().hideLoading();
//            }
//
//            @Override
//            public boolean isDisposed() {
//                return false;
//            }
//        };

        if (getDataManager().isCurrentUserLoggedIn()/* == true*/) {

            Log.d("isCurrentUserLoggedIn", "true");

            if (getDataManager().isCurrentUserInfoInitialized()) {

                Log.d("isCurrentUserInfoInit", "true");

                if (getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER)) {

                    Log.d("openCustomerHome", "CUSTOMER");

                    getMvpView().openCustomerHomeActivity();

                } else {

                    Log.d("RestaurantHome", "Res");

                    getMvpView().openRestaurantHomeActivity();
                }

            } else {

                Log.d("isCurrentUserInfoInit", "false");

                if (getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER)) {

                    Log.d("openLocationInfo", "CUSTOMER");

                    getMvpView().openLocationInfoActivity();

                } else {

                    Log.d("SetupRestaurantProfile", "Res");

                    getMvpView().openSetupRestaurantProfileActivity();
                }
            }

        } else {

            Log.d("isCurrentUserLoggedIn", "false");

            getMvpView().openWelcomeActivity();
        }
    }

    @Override
    public void waitAndGo(Context context) {

        getDataManager().setLatLng("", "");
        getDataManager().setCityId("");

        if (getDataManager().getLanguage() == null || getDataManager().getLanguage().isEmpty()) {
            getDataManager().setLanguage(AppConstants.LANG_EN);
            Log.d("setLanguage", ">>" + getDataManager().getLanguage());
        }
        Log.d("Current Language", ">>" + getDataManager().getLanguage());

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        getDataManager().setDeviceId(refreshedToken);

        Locale locale = new Locale(getDataManager().getLanguage());
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        getCompositeDisposable().add(Observable
                .timer(AppConstants.SPLASH_DURATION, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        decideNextActivity();
                    }
                }));
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}