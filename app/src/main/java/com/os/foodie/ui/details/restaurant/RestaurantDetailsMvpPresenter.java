package com.os.foodie.ui.details.restaurant;

import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantDetailsMvpPresenter<V extends RestaurantDetailsMvpView> extends MvpPresenter<V> {

    void getRestaurantDetails(String restaurantId);

    void doLikeDislike(String restaurantId);
}