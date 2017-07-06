package com.os.foodie.ui.review;

import com.os.foodie.ui.base.MvpPresenter;

public interface ReviewMvpPresenter<V extends ReviewMvpView> extends MvpPresenter<V> {

    void getReviews(String restaurantId);

    void dispose();
}