package com.os.foodie.ui.earning;

import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class EarningPresenter<V extends EarningMvpView> extends BasePresenter<V> implements EarningMvpPresenter<V> {

    public EarningPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}