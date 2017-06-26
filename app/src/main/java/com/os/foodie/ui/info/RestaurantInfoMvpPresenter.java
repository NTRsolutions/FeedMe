package com.os.foodie.ui.info;

import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantInfoMvpPresenter<V extends RestaurantInfoMvpView> extends MvpPresenter<V> {

    void dispose();
}