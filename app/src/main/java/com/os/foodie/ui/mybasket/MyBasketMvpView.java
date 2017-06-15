package com.os.foodie.ui.mybasket;

import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.ui.base.MvpView;

public interface MyBasketMvpView extends MvpView {

    void setMyBasket(ViewCartResponse viewCartResponse);

//    void askForClearBasket();

    void onBasketClear();

    void itemRemovedFromBasket(int position);

    void updateMyBasket(int position, String quantity, String totalQuantity, String totalAmount);
}