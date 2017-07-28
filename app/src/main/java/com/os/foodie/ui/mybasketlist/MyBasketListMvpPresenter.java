package com.os.foodie.ui.mybasketlist;

import com.os.foodie.ui.base.MvpPresenter;

public interface MyBasketListMvpPresenter<V extends MyBasketListMvpView> extends MvpPresenter<V> {

    void getCartList();

    void dispose();
}
