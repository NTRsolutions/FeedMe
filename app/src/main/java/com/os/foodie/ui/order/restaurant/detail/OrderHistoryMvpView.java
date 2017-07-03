package com.os.foodie.ui.order.restaurant.detail;

import com.os.foodie.data.network.model.order.restaurant.detail.OrderHistoryDetail;
import com.os.foodie.ui.base.MvpView;

public interface OrderHistoryMvpView extends MvpView {

    public void setOrderHistoryDetail(OrderHistoryDetail orderHistoryDetail);

}