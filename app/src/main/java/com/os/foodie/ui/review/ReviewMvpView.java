package com.os.foodie.ui.review;

import com.os.foodie.data.network.model.reviews.GetReviewsResponse;
import com.os.foodie.ui.base.MvpView;

public interface ReviewMvpView extends MvpView {

    void onReviewListReceived(GetReviewsResponse reviewsResponse);
}