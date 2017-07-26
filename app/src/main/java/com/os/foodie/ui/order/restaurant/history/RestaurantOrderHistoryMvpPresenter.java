package com.os.foodie.ui.order.restaurant.history;

import android.support.v4.widget.SwipeRefreshLayout;

import com.os.foodie.ui.base.MvpPresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpView;

public interface RestaurantOrderHistoryMvpPresenter<V extends RestaurantOrderHistoryMvpView> extends MvpPresenter<V> {

    void dispose();

    void getOrderHistory(SwipeRefreshLayout swipeRefreshLayout);

    String getCurrentUserType();
}