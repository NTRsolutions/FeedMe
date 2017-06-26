package com.os.foodie.ui.main.restaurant;

import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantMainMvpPresenter<V extends RestaurantMainMvpView> extends MvpPresenter<V> {

    String getCurrentUserName();

    String getRestaurantLogoURL();

    void dispose();
}