package com.os.foodie.ui.payment.show;

import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardResponse;
import com.os.foodie.ui.base.MvpView;

public interface PaymentMethodMvpView extends MvpView {

    void notifyDataSetChanged(GetAllPaymentCardResponse getAllPaymentCardResponse);

    void onPaymentCardDeleted(int position);
}