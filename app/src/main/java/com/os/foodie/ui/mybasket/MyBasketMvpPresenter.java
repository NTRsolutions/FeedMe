package com.os.foodie.ui.mybasket;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.widget.TextView;

import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface MyBasketMvpPresenter<V extends MyBasketMvpView> extends MvpPresenter<V> {

    void getMyBasketDetails(String userId, String restaurantId);

    void removeFromMyBasket(String userId, String itemId, String restaurantId, int position);

    void updateMyBasket(String userId, String itemId, String restaurantId, String qty, String price, final int position);

    void clearBasket(String restaurantId);

    void clearBasketRestaurant();

    void checkout(CheckoutRequest checkoutRequest);

    String getLanguage();

    void dispose();

    void onError(@StringRes int resId);

//    void dateTimePickerDialog(Context context, ViewCartResponse viewCartResponse, TextView tvScheduledTime);
}