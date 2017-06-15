package com.os.foodie.ui.order.restaurant.list;

import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantOrderListPresenter<V extends RestaurantOrderListMvpView> extends BasePresenter<V> implements RestaurantOrderListMvpPresenter<V> {

    public RestaurantOrderListPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}