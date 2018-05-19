package com.os.foodie.ui.menu.add;

import com.os.foodie.ui.base.MvpPresenter;

import java.io.File;
import java.util.HashMap;

public interface RestaurantMenuAddUpdateDishMvpPresenter<V extends RestaurantMenuAddUpdateDishMvpView> extends MvpPresenter<V> {

    void getCourseTypeList();

    void addRestaurantMenuItem(String dishId, String courseId, String name, String price, String description, String vegNonVeg, HashMap<String, File> menuImageFile,String itemNameArabic);

    void dispose();
}