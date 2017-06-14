package com.os.foodie.ui.deliveryaddress.show;

import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressResponse;
import com.os.foodie.ui.base.MvpView;

public interface DeliveryAddressMvpView extends MvpView {

    public void setAddressList(GetAllAddressResponse getAllAddressResponse);

    public void onAddressDelete(int position);
}