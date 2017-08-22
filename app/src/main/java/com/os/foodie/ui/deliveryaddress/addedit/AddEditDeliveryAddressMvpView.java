package com.os.foodie.ui.deliveryaddress.addedit;

import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface AddEditDeliveryAddressMvpView extends MvpView {

    void onDeliveryAddressAdd(Address address);

    void onDeliveryAddressEdit(Address address);

    void setAllAddress(ArrayList<android.location.Address> addresses);
}