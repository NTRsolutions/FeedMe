package com.os.foodie.ui.order.restaurant.detail;

import com.os.foodie.ui.base.MvpPresenter;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressMvpView;

public interface OrderHistoryMvpPresenter<V extends OrderHistoryMvpView> extends MvpPresenter<V> {

    public void getOrderHistoryDetail(String orderId);

    public void ChangeOrderStatus(String orderId, String status);
}