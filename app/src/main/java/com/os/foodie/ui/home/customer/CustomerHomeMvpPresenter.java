package com.os.foodie.ui.home.customer;

import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.ui.base.MvpPresenter;

public interface CustomerHomeMvpPresenter<V extends CustomerHomeMvpView> extends MvpPresenter<V> {

    void getRestaurantList(Filters filters);

    void dispose();
}