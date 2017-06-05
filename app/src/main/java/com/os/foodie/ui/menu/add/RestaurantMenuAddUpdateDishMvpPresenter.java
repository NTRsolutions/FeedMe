package com.os.foodie.ui.menu.add;

import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantMenuAddUpdateDishMvpPresenter<V extends RestaurantMenuAddUpdateDishMvpView> extends MvpPresenter<V> {

    void getCourseTypeList();

    void addRestaurantMenuItem(String dishId, String courseId, String name, String price, String description, String vegNonVeg);
}