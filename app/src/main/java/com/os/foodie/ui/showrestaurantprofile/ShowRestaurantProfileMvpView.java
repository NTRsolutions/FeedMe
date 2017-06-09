package com.os.foodie.ui.showrestaurantprofile;

import android.location.Address;

import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface ShowRestaurantProfileMvpView extends MvpView {

    void setRestaurantProfileDetail(RestaurantProfileResponse getAccountDetailResponse);


}