package com.os.foodie.ui.main.restaurant;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListRequest;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListResponse;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.data.network.model.login.LoginResponse;
import com.os.foodie.data.network.model.logout.LogoutRequest;
import com.os.foodie.data.network.model.logout.LogoutResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantMainPresenter<V extends RestaurantMainMvpView> extends BasePresenter<V> implements RestaurantMainMvpPresenter<V> {

    public RestaurantMainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public String getCurrentUserName() {
        return getDataManager().getCurrentUserName();
    }

    @Override
    public String getRestaurantLogoURL() {
        return getDataManager().getRestaurantLogoURL();
    }

    @Override
    public void logout() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .logout(new LogoutRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LogoutResponse>() {
                        @Override
                        public void accept(LogoutResponse logoutResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (logoutResponse.getResponse().getStatus() == 1) {

                                setUserAsLoggedOut();

                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                getMvpView().doLogout();

                            } else {
                                getMvpView().onError(R.string.some_error);
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}