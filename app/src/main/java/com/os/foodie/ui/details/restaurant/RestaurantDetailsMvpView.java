package com.os.foodie.ui.details.restaurant;

import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.ui.base.MvpView;

public interface RestaurantDetailsMvpView extends MvpView {

    void setResponse(CustomerRestaurantDetailsResponse restaurantDetailsResponse);

    void updateLikeDislike(boolean isLike);
}