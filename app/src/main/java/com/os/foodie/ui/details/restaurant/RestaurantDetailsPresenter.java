package com.os.foodie.ui.details.restaurant;

import android.support.annotation.StringRes;
import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.cart.add.AddToCartResponse;
import com.os.foodie.data.network.model.cart.clear.ClearCartRequest;
import com.os.foodie.data.network.model.cart.clear.ClearCartResponse;
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

            getMvpView().showLoading();

            if (getDataManager().isCurrentUserLoggedIn()) {

                getCompositeDisposable().add(getDataManager()
                        .getRestaurantDetails(new CustomerRestaurantDetailsRequest(getDataManager().getCurrentUserId(), restaurantId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CustomerRestaurantDetailsResponse>() {
                            @Override
                            public void accept(CustomerRestaurantDetailsResponse restaurantDetailsResponse) throws Exception {

                                getMvpView().hideLoading();

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
                                getMvpView().hideLoading();
                                getMvpView().onError(R.string.api_default_error);
                                Log.d("Error", ">>Err" + throwable.getMessage());
                            }
                        }));

            } else {

                getCompositeDisposable().add(getDataManager()
                        .getRestaurantDetails(new CustomerRestaurantDetailsRequest("", restaurantId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CustomerRestaurantDetailsResponse>() {
                            @Override
                            public void accept(CustomerRestaurantDetailsResponse restaurantDetailsResponse) throws Exception {

                                getMvpView().hideLoading();

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
                                getMvpView().hideLoading();
                                getMvpView().onError(R.string.api_default_error);
                                Log.d("Error", ">>Err" + throwable.getMessage());
                            }
                        }));
            }

        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void doLikeDislike(String restaurantId) {

        if (!getDataManager().isCurrentUserLoggedIn()) {

            getMvpView().onError(R.string.not_logged_in);
            return;
        }

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
                                boolean isLike = (likeDislikeResponse.getResponse().getIsLike().equals("0")) ? false : true;

                                Log.d("isLike", ">>" + isLike);

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
    public void addItemToCart(final int position, final AddToCartRequest addToCartRequest) {

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
                                Log.d("getMessage", ">>Success");
//                                getMvpView().refreshDetails(position, addToCartRequest.getQty(), addToCartResponse);
                                getMvpView().updateMyBasket(position, addToCartRequest.getQty(), addToCartResponse.getResponse().getData().getTotalCartQuantity(), addToCartResponse.getResponse().getData().getTotalCartAmount());


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
    public void removeFromMyBasket(String userId, String itemId, String restaurantId, final int position) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            Log.d("itemId", ">>" + itemId);

            getCompositeDisposable().add(getDataManager()
                    .removeFromCart(new RemoveFromCartRequest(userId, itemId, restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<RemoveFromCartResponse>() {
                        @Override
                        public void accept(RemoveFromCartResponse removeFromCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (removeFromCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().itemRemovedFromBasket(position, removeFromCartResponse.getResponse().getTotalCartQuantity(), removeFromCartResponse.getResponse().getTotalCartAmount());
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
    public void updateMyBasket(String userId, String restaurantId, String itemId, final String quantity, String price, final int position) {


        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            Log.d("itemId", ">>" + itemId);

            getCompositeDisposable().add(getDataManager()
                    .updateCart(new UpdateCartRequest(userId, itemId, restaurantId, quantity, price))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UpdateCartResponse>() {
                        @Override
                        public void accept(UpdateCartResponse updateCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (updateCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().updateMyBasket(position, quantity, updateCartResponse.getResponse().getData().getTotalCartQuantity(), updateCartResponse.getResponse().getData().getTotalCartAmount());
////                                getMvpView().setMyBasket(viewCartResponse);

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

//    @Override
//    public void clearBasket() {
//
//        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {
//
//            getMvpView().showLoading();
//
//            getCompositeDisposable().add(getDataManager()
//                    .clearCart(new ClearCartRequest(getDataManager().getCurrentUserId()))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<ClearCartResponse>() {
//                        @Override
//                        public void accept(ClearCartResponse clearCartResponse) throws Exception {
//
//                            getMvpView().hideLoading();
//                            getDataManager().setCustomerRestaurantId("");
//
//                            if (clearCartResponse.getResponse().getStatus() == 1) {
//
//                                Log.d("Cart", ">>1");
//                                getMvpView().onError(clearCartResponse.getResponse().getMessage());
//
//                            } else {
//
//                                Log.d("Cart", ">>0");
//                                getMvpView().onError(clearCartResponse.getResponse().getMessage());
//                            }
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            getMvpView().hideLoading();
//                            getMvpView().onError(R.string.api_default_error);
//                            Log.d("Error", ">>Err" + throwable.getMessage());
//                        }
//                    }));
//        } else {
//            getMvpView().onError(R.string.connection_error);
//        }
//    }

    @Override
    public String getCustomerRestaurantId() {
        return getDataManager().getCustomerRestaurantId();
    }

    @Override
    public void setCustomerRestaurantId(String restaurantId) {
        getDataManager().setCustomerRestaurantId(restaurantId);
    }

    @Override
    public boolean isCurrentUserLoggedIn() {
        return getDataManager().isCurrentUserLoggedIn();
    }

    @Override
    public void onError(@StringRes int resId) {
        getMvpView().onError(resId);
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}