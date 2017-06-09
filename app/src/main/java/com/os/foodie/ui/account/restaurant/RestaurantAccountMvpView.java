package com.os.foodie.ui.account.restaurant;

import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountResponse;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.ui.base.MvpView;

public interface RestaurantAccountMvpView extends MvpView {

    void setRestaurantAccountDetail(CustomerRestaurantDetailsResponse customerRestaurantDetailsResponse);

    void editRestaurantAccountDetail(EditRestaurantAccountResponse editRestaurantAccountResponse);
}