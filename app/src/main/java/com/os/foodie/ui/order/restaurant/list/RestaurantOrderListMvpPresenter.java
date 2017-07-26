package com.os.foodie.ui.order.restaurant.list;

import android.support.v4.widget.SwipeRefreshLayout;

import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantOrderListMvpPresenter<V extends RestaurantOrderListMvpView> extends MvpPresenter<V> {

    void dispose();

    void getOrderList(SwipeRefreshLayout swipeRefreshLayout);

    void acceptRejectOrder(String orderId, String status, int position);
}