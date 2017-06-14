package com.os.foodie.ui.deliveryaddress.addedit;

import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.ui.base.MvpPresenter;
import com.os.foodie.ui.forgotpassword.ForgotPasswordMvpView;

public interface AddDeliveryAddressMvpPresenter<V extends AddDeliveryAddressMvpView> extends MvpPresenter<V> {

    void addDeliverAddress(AddDeliveryAddressRequest addDeliveryAddressRequest);
}