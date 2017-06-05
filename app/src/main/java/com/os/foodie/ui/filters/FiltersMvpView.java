package com.os.foodie.ui.filters;

import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface FiltersMvpView extends MvpView {

    void onCuisineTypeListReceive(ArrayList<CuisineType> cuisineTypes);
}