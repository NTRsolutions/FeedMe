package com.os.foodie.ui.deliveryaddress.show;

import com.os.foodie.ui.base.MvpPresenter;

public interface DeliveryAddressMvpPresenter<V extends DeliveryAddressMvpView> extends MvpPresenter<V> {

    public void getAddressList();

    public void deleteAddress(String addressId,int position);

    void dispose();
}