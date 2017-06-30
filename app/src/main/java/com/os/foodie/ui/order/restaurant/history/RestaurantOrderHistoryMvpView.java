package com.os.foodie.ui.order.restaurant.history;

import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.ui.base.MvpView;

public interface RestaurantOrderHistoryMvpView extends MvpView {

    void onOrderHistoryReceived(GetOrderListResponse getOrderListResponse);


}