package com.os.foodie.ui.deliveryaddress.select;

import android.support.annotation.StringRes;

import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface SelectDeliveryAddressMvpPresenter<V extends SelectDeliveryAddressMvpView> extends MvpPresenter<V> {

    public void getAddressList();

    public void deleteAddress(String addressId,int position);

    void checkout(CheckoutRequest checkoutRequest);

    void setError(@StringRes int resId);

    void dispose();
}