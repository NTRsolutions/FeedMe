package com.os.foodie.ui.search;

import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface RestaurantSearchMvpView extends MvpView {

    void notifyDataSetChanged();

    void notifyDataSetChanged(ArrayList<RestaurantList> restaurantList);
}