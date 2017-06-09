package com.os.foodie.ui.details.restaurant;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.cart.add.AddToCartResponse;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartRequest;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartResponse;
import com.os.foodie.data.network.model.cart.update.UpdateCartRequest;
import com.os.foodie.data.network.model.cart.update.UpdateCartResponse;
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

    private boolean isFirstTime = true;

    public RestaurantDetailsPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantDetails(String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

//            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantDetails(new CustomerRestaurantDetailsRequest(getDataManager().getCurrentUserId(), restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CustomerRestaurantDetailsResponse>() {
                        @Override
                        public void accept(CustomerRestaurantDetailsResponse restaurantDetailsResponse) throws Exception {

//                            getMvpView().hideLoading();

                            if (restaurantDetailsResponse.getResponse().getStatus() == 1) {

                                if (isFirstTime) {
                                    getMvpView().setResponse(restaurantDetailsResponse);
                                    isFirstTime = false;
                                } else {
                                    getMvpView().resetResponse(restaurantDetailsResponse);
                                }

                            } else {
                                Log.d("getMessage", ">>Failed");
                                getMvpView().onError(restaurantDetailsResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
//                            getMvpView().hideLoading();
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

    @Override
    public void addItemToCart(AddToCartRequest addToCartRequest) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

//            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .addToCart(addToCartRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddToCartResponse>() {
                        @Override
                        public void accept(AddToCartResponse addToCartResponse) throws Exception {

//                            getMvpView().hideLoading();

                            if (addToCartResponse.getResponse().getStatus() == 1) {

                                Log.d("getMessage", ">>Success");
                                getMvpView().refreshDetails();

                            } else {
                                Log.d("getMessage", ">>Failed");
                                getMvpView().onError(addToCartResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
//                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void removeFromMyBasket(String userId, String itemId, final int position) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            Log.d("itemId", ">>" + itemId);

            getCompositeDisposable().add(getDataManager()
                    .removeFromCart(new RemoveFromCartRequest(userId, itemId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<RemoveFromCartResponse>() {
                        @Override
                        public void accept(RemoveFromCartResponse removeFromCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (removeFromCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().itemRemovedFromBasket(position);
//                                getMvpView().setMyBasket(viewCartResponse);

                            } else {
                                getMvpView().onError(removeFromCartResponse.getResponse().getMessage());
//                                getMvpView().onError("No Restaurant found");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void updateMyBasket(String userId, String itemId, final String quantity, final int position) {


        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            Log.d("itemId", ">>" + itemId);

            getCompositeDisposable().add(getDataManager()
                    .updateCart(new UpdateCartRequest(userId, itemId, quantity))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UpdateCartResponse>() {
                        @Override
                        public void accept(UpdateCartResponse updateCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (updateCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().updateMyBasket(position, quantity);
//                                getMvpView().setMyBasket(viewCartResponse);

                            } else {
                                getMvpView().onError(updateCartResponse.getResponse().getMessage());
//                                getMvpView().onError("No Restaurant found");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }
}