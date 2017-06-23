package com.os.foodie.ui.payment.show;

import com.os.foodie.ui.base.MvpPresenter;

public interface PaymentMethodMvpPresenter<V extends PaymentMethodMvpView> extends MvpPresenter<V> {

    void getAllPaymentCard();

    void deletePaymentCard(String cardId, int position);
}