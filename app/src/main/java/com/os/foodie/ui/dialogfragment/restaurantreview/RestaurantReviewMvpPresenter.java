package com.os.foodie.ui.dialogfragment.restaurantreview;

import com.os.foodie.data.network.model.restaurantreview.RestaurantReviewRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantReviewMvpPresenter<V extends RestaurantReviewMvpView> extends MvpPresenter<V> {

    void SendRestaurantReview(RestaurantReviewRequest restaurantReviewRequest);

    void dispose();
}