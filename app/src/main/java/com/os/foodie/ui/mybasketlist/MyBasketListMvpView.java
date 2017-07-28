package com.os.foodie.ui.mybasketlist;

import com.os.foodie.data.network.model.cart.list.GetCartListResponse;
import com.os.foodie.ui.base.MvpView;

public interface MyBasketListMvpView extends MvpView {

    void setCartList(GetCartListResponse cartListResponse);
}