package com.os.foodie.ui.account.restaurant;

import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountRequest;
import com.os.foodie.ui.base.MvpPresenter;

import java.io.File;
import java.util.HashMap;

public interface RestaurantAccountMvpPresenter<V extends RestaurantAccountMvpView> extends MvpPresenter<V> {

    void getRestaurantAccountDetail(String restaurantId);

    void editRestaurantAccountDetail(EditRestaurantAccountRequest editRestaurantAccountRequest, HashMap<String, File> fileMap);

    String getRestaurantLogoURL();

    void dispose();
}