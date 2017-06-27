package com.os.foodie.ui.order.restaurant.list;

import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.ui.base.MvpView;

public interface RestaurantOrderListMvpView extends MvpView {

    void onOrderListReceived(GetOrderListResponse getOrderListResponse);

    void onAcceptReject(int position);
}