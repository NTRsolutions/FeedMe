package com.os.foodie.ui.order.restaurant.history;

import com.os.foodie.ui.base.MvpPresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpView;

public interface RestaurantOrderHistoryMvpPresenter<V extends RestaurantOrderHistoryMvpView> extends MvpPresenter<V> {

    void dispose();

    void getOrderHistory();

    String getCurrentUserType();
}