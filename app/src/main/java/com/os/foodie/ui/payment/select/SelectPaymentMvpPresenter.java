package com.os.foodie.ui.payment.select;

import com.os.foodie.ui.base.MvpPresenter;

public interface SelectPaymentMvpPresenter<V extends SelectPaymentMvpView> extends MvpPresenter<V> {

    void getAllPaymentCard();
}