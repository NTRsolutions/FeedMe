package com.os.foodie.ui.search;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListRequest;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListResponse;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantSearchPresenter<V extends RestaurantSearchMvpView> extends BasePresenter<V> implements RestaurantSearchMvpPresenter<V> {

    public RestaurantSearchPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantList(String keyword, Filters filters) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

//            getMvpView().showLoading();

            getCompositeDisposable().clear();

            if (getDataManager().isCurrentUserLoggedIn()) {

                getCompositeDisposable().add(getDataManager()
                        .getRestaurantList(
                                new GetRestaurantListRequest(getDataManager().getCurrentUserId(), keyword, "", "", "", filters))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GetRestaurantListResponse>() {
                            @Override
                            public void accept(GetRestaurantListResponse getRestaurantListResponse) throws Exception {

//                            getMvpView().hideLoading();

                                if (getRestaurantListResponse.getResponse().getIsDeleted() != null && getRestaurantListResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                    Locale locale = new Locale(AppConstants.LANG_EN);
//                                    Locale.setDefault(locale);
//
//                                    Configuration config = new Configuration();
//                                    config.locale = locale;
//
//                                    getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                    Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getMvpView().getContext().startActivity(intent);

//                                    getDataManager().setLanguage(AppConstants.LANG_EN);

                                    setUserAsLoggedOut();

                                    Toast.makeText(getMvpView().getContext(), getRestaurantListResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                if (getRestaurantListResponse.getResponse().getStatus() == 1) {

                                    if (getRestaurantListResponse.getResponse().getRestaurantList() != null) {
                                        Log.d("size", ">>" + getRestaurantListResponse.getResponse().getRestaurantList().size());
                                        Log.d("getMessage", ">>" + getRestaurantListResponse.getResponse().getMessage());
                                        getMvpView().notifyDataSetChanged((ArrayList<RestaurantList>) getRestaurantListResponse.getResponse().getRestaurantList());

                                    } else {
                                        Log.d("Error", ">>Err");
                                        getMvpView().notifyDataSetChanged();
//                                    getMvpView().onError(R.string.no_restaurant);
                                    }

                                } else {
                                    getMvpView().notifyDataSetChanged();
//                                getMvpView().onError(getRestaurantListResponse.getResponse().getMessage());
                                }

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
//                            getMvpView().hideLoading();
                                Log.d("Error", ">>ErrThorwed");
                                getMvpView().onError(R.string.api_default_error);
                                Log.d("Error", ">>Err" + throwable.getMessage());
                            }
                        }));

            } else {

                getCompositeDisposable().add(getDataManager()
                        .getRestaurantList(
                                new GetRestaurantListRequest("", keyword, getDataManager().getLatitude(), getDataManager().getLongitude(), getDataManager().getCityName(), filters))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GetRestaurantListResponse>() {
                            @Override
                            public void accept(GetRestaurantListResponse getRestaurantListResponse) throws Exception {

//                            getMvpView().hideLoading();

                                if (getRestaurantListResponse.getResponse().getIsDeleted() != null && getRestaurantListResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                    Locale locale = new Locale(AppConstants.LANG_EN);
//                                    Locale.setDefault(locale);
//
//                                    Configuration config = new Configuration();
//                                    config.locale = locale;
//
//                                    getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                    Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getMvpView().getContext().startActivity(intent);

//                                    getDataManager().setLanguage(AppConstants.LANG_EN);

                                    setUserAsLoggedOut();

                                    Toast.makeText(getMvpView().getContext(), getRestaurantListResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                if (getRestaurantListResponse.getResponse().getStatus() == 1) {

                                    if (getRestaurantListResponse.getResponse().getRestaurantList() != null) {
                                        Log.d("size", ">>" + getRestaurantListResponse.getResponse().getRestaurantList().size());
                                        Log.d("getMessage", ">>" + getRestaurantListResponse.getResponse().getMessage());
                                        getMvpView().notifyDataSetChanged((ArrayList<RestaurantList>) getRestaurantListResponse.getResponse().getRestaurantList());

                                    } else {
                                        Log.d("Error", ">>Err");
                                        getMvpView().notifyDataSetChanged();
//                                    getMvpView().onError(R.string.no_restaurant);
                                    }

                                } else {
                                    getMvpView().notifyDataSetChanged();
//                                getMvpView().onError(getRestaurantListResponse.getResponse().getMessage());
                                }

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
//                            getMvpView().hideLoading();
                                Log.d("Error", ">>ErrThorwed");
                                getMvpView().onError(R.string.api_default_error);
                                Log.d("Error", ">>Err" + throwable.getMessage());
                            }
                        }));
            }

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