package com.os.foodie.ui.home.customer;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListRequest;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListResponse;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuRequest;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CustomerHomePresenter<V extends CustomerHomeMvpView> extends BasePresenter<V> implements CustomerHomeMvpPresenter<V> {

    public CustomerHomePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantList(Filters filters) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantList(new GetRestaurantListRequest(getDataManager().getCurrentUserId(), "", filters))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetRestaurantListResponse>() {
                        @Override
                        public void accept(GetRestaurantListResponse getRestaurantListResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (getRestaurantListResponse.getResponse().getStatus() == 1) {

                                if (getRestaurantListResponse.getResponse().getRestaurantList() != null) {
                                    Log.d("size", ">>" + getRestaurantListResponse.getResponse().getRestaurantList().size());
                                    getMvpView().notifyDataSetChanged((ArrayList<RestaurantList>) getRestaurantListResponse.getResponse().getRestaurantList());

                                } else {
                                    Log.d("Error", ">>Err");
//                                    getMvpView().onError(R.string.no_restaurant);
                                    getMvpView().notifyDataSetChanged();
                                }

                            } else {
//                                getMvpView().onError("No Restaurant found");
                                getMvpView().notifyDataSetChanged();
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
        getCompositeDisposable().dispose();
    }
}