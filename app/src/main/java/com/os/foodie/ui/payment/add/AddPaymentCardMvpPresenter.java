package com.os.foodie.ui.payment.add;

import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface AddPaymentCardMvpPresenter<V extends AddPaymentCardMvpView> extends MvpPresenter<V> {

    void addPaymentCard(AddPaymentCardRequest addPaymentCardRequest);
}