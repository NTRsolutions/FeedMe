package com.os.foodie.ui.filters;

import com.os.foodie.ui.base.MvpPresenter;

public interface FiltersMvpPresenter<V extends FiltersMvpView> extends MvpPresenter<V> {

    void onCuisineTypeClick();

    void dispose();
}