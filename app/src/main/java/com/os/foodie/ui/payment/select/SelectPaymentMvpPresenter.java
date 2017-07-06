package com.os.foodie.ui.payment.select;

import android.support.annotation.StringRes;

import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface SelectPaymentMvpPresenter<V extends SelectPaymentMvpView> extends MvpPresenter<V> {

    void getAllPaymentCard();

    void checkout(CheckoutRequest checkoutRequest);

    void setError(@StringRes int resId);

    void dispose();
}