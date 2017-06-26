package com.os.foodie.ui.order.restaurant.list;

import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantOrderListMvpPresenter<V extends RestaurantOrderListMvpView> extends MvpPresenter<V> {

    void dispose();
}