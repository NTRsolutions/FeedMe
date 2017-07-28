package com.os.foodie.ui.order.restaurant.detail;

import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.ui.base.MvpPresenter;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressMvpView;

public interface OrderHistoryMvpPresenter<V extends OrderHistoryMvpView> extends MvpPresenter<V> {

    void getOrderHistoryDetail(String orderId);

    void ChangeOrderStatus(String orderId, String status);

    void reorder(String orderId, String restaurantId);

    boolean isCustomer();

    String getCurrency();

    String getCustomerRestaurantId();

    void acceptRejectOrder(String orderId, String status, int position);

    boolean isLoggedIn();

    void dispose();
}