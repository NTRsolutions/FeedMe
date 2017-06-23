package com.os.foodie.ui.payment.add;

import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardRequest;
import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardResponse;
import com.os.foodie.ui.base.MvpView;

public interface AddPaymentCardMvpView extends MvpView {

    void onPaymentAdded(AddPaymentCardResponse addPaymentCardResponse);
}