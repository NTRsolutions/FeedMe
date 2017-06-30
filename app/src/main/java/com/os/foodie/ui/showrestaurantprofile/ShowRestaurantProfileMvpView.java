package com.os.foodie.ui.showrestaurantprofile;

import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.ui.base.MvpView;

public interface ShowRestaurantProfileMvpView extends MvpView {

    void setRestaurantProfileDetail(RestaurantProfileResponse restaurantProfileResponse);


}