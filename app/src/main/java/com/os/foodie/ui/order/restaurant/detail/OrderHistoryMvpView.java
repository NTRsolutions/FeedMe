package com.os.foodie.ui.order.restaurant.detail;

import com.os.foodie.data.network.model.changeorderstatus.ChangeOrderStatusResponse;
import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressResponse;
import com.os.foodie.data.network.model.order.restaurant.detail.OrderHistoryDetail;
import com.os.foodie.ui.base.MvpView;

public interface OrderHistoryMvpView extends MvpView {

    public void setOrderHistoryDetail(OrderHistoryDetail orderHistoryDetail);

    public void setOrderStatus(ChangeOrderStatusResponse changeOrderStatusResponse);

    public void onReorderComplete();

    void onAcceptReject(String orderId);
}