package com.os.foodie.ui.merchantdetail;

import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface MerchantDetailMvpPresenter<V extends MerchantDetailMvpView> extends MvpPresenter<V> {

    void setMerchantDetails(SetMerchantDetailRequest merchantDetails, String confirmAccountNumber);

    void getMerchantDetails();

    void dispose();
}