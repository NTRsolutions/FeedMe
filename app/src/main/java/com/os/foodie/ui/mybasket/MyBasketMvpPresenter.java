package com.os.foodie.ui.mybasket;

import com.os.foodie.ui.base.MvpPresenter;

public interface MyBasketMvpPresenter<V extends MyBasketMvpView> extends MvpPresenter<V> {

    void getMyBasketDetails(String userId);

    void removeFromMyBasket(String userId, String itemId, int position);

    void updateMyBasket(String userId, String itemId, String quantity, int position);
}