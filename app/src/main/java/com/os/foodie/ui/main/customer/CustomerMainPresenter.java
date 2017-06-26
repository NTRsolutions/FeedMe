package com.os.foodie.ui.main.customer;

import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerMainPresenter<V extends CustomerMainMvpView> extends BasePresenter<V> implements CustomerMainMvpPresenter<V> {

    public CustomerMainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public String getCurrentUserName() {
        return getDataManager().getCurrentUserName();
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}