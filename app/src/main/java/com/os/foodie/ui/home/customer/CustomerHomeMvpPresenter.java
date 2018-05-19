package com.os.foodie.ui.home.customer;

import android.support.v4.widget.SwipeRefreshLayout;

import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.ui.base.MvpPresenter;

public interface CustomerHomeMvpPresenter<V extends CustomerHomeMvpView> extends MvpPresenter<V> {

    void getRestaurantList(Filters filters, SwipeRefreshLayout swipeRefreshLayout);

    String getLanguage();

    void dispose();
}