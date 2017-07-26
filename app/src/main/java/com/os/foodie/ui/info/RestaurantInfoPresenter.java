package com.os.foodie.ui.info;

import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantInfoPresenter<V extends RestaurantInfoMvpView> extends BasePresenter<V> implements RestaurantInfoMvpPresenter<V> {

    public RestaurantInfoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}