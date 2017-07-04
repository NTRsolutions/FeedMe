package com.os.foodie.ui.merchantdetail;

import com.os.foodie.ui.base.MvpPresenter;

public interface MerchantDetailMvpPresenter<V extends MerchantDetailMvpView> extends MvpPresenter<V> {

    void getRestaurantList();

    void dispose();
}