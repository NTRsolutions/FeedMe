package com.os.foodie.ui.deliveryaddress.addedit;

import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.ui.base.MvpView;

public interface AddEditDeliveryAddressMvpView extends MvpView {

    void onDeliveryAddressAdd(Address address);

    void onDeliveryAddressEdit(Address address);
}