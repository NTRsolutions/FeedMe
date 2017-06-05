package com.os.foodie.ui.menu.show.restaurant;

import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.ui.base.MvpView;

import java.util.List;

public interface RestaurantMenuMvpView extends MvpView {

    void createRestaurantMenu(List<Dish> dishArrayList);

    void notifyDataSetChanged();

    void onMenuItemDelete(Dish dish);
}