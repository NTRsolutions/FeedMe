package com.os.foodie.ui.mybasket;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface MyBasketMvpPresenter<V extends MyBasketMvpView> extends MvpPresenter<V> {

    void getMyBasketDetails(String userId);

    void removeFromMyBasket(String userId, String itemId, String restaurantId, int position);

    void updateMyBasket(String userId, String itemId, String restaurantId, String qty, String price, final int position);

    void clearBasket();

    void clearBasketRestaurant();

    void checkout(CheckoutRequest checkoutRequest);
}