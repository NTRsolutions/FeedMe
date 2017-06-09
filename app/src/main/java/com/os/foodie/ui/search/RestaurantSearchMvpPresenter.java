package com.os.foodie.ui.search;

import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantSearchMvpPresenter<V extends RestaurantSearchMvpView> extends MvpPresenter<V> {

    void getRestaurantList(String keyword, Filters filters);
}