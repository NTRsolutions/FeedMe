package com.os.foodie.ui.dialogfragment.restaurantreview;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.restaurantreview.RestaurantReviewRequest;
import com.os.foodie.data.network.model.restaurantreview.RestaurantReviewResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantReviewPresenter<V extends RestaurantReviewMvpView> extends BasePresenter<V> implements RestaurantReviewMvpPresenter<V> {

    public RestaurantReviewPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void SendRestaurantReview(RestaurantReviewRequest restaurantReviewRequest) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            restaurantReviewRequest.setUserId(getDataManager().getCurrentUserId());

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .sendRestaurantReview(restaurantReviewRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<RestaurantReviewResponse>() {
                        @Override
                        public void accept(RestaurantReviewResponse restaurantReviewResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (restaurantReviewResponse.getResponse().getStatus() == 1) {

                                getMvpView().onError(restaurantReviewResponse.getResponse().getMessage());
                                getMvpView().finish();

                            } else {
                                getMvpView().onError(restaurantReviewResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}