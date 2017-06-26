package com.os.foodie.ui.menu.show.restaurant;

import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantMenuMvpPresenter<V extends RestaurantMenuMvpView> extends MvpPresenter<V> {

    void getRestaurantMenuList();

    void changeStatusRestaurantMenuList(Dish dish/*String dishId, String status*/);

    void deleteRestaurantMenuList(Dish dish);

    void dispose();
}