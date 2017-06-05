package com.os.foodie.ui.details.restaurant;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsRequest;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.data.network.model.like.LikeDislikeRequest;
import com.os.foodie.data.network.model.like.LikeDislikeResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantDetailsPresenter<V extends RestaurantDetailsMvpView> extends BasePresenter<V> implements RestaurantDetailsMvpPresenter<V> {

    public RestaurantDetailsPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantDetails(String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantDetails(new CustomerRestaurantDetailsRequest(getDataManager().getCurrentUserId(), restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CustomerRestaurantDetailsResponse>() {
                        @Override
                        public void accept(CustomerRestaurantDetailsResponse restaurantDetailsResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (restaurantDetailsResponse.getResponse().getStatus() == 1) {

                                getMvpView().setResponse(restaurantDetailsResponse);

                            } else {
                                Log.d("getMessage", ">>Failed");
//                                getMvpView().onError(forgotPasswordResponse.getResponse().getMessage());
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
    public void doLikeDislike(String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .doLikeDislike(new LikeDislikeRequest(getDataManager().getCurrentUserId(), restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LikeDislikeResponse>() {
                        @Override
                        public void accept(LikeDislikeResponse likeDislikeResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (likeDislikeResponse.getResponse().getStatus() == 1) {

                                Log.d("getIsLike", ">>" + likeDislikeResponse.getResponse().getIsLike());
                                boolean isLike = (likeDislikeResponse.getResponse().getIsLike() == "0") ? false : true;

                                getMvpView().updateLikeDislike(isLike);

                            } else {
                                Log.d("getMessage", ">>Failed");
                                getMvpView().onError(likeDislikeResponse.getResponse().getMessage());
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
}