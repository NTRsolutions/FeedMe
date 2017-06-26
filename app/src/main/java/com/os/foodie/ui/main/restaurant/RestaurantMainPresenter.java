package com.os.foodie.ui.main.restaurant;

import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

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
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}