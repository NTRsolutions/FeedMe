package com.os.foodie.ui.deliveryaddress.select;

import com.os.foodie.ui.base.MvpPresenter;

public interface SelectDeliveryAddressMvpPresenter<V extends SelectDeliveryAddressMvpView> extends MvpPresenter<V> {

    public void getAddressList();

    public void deleteAddress(String addressId,int position);
}