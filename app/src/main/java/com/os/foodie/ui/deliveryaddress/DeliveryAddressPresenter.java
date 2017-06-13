package com.os.foodie.ui.deliveryaddress;

import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class DeliveryAddressPresenter<V extends DeliveryAddressMvpView> extends BasePresenter<V> implements DeliveryAddressMvpPresenter<V> {

    public DeliveryAddressPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}