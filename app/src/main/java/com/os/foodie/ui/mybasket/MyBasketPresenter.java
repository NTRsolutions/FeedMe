package com.os.foodie.ui.mybasket;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.cart.clear.ClearCartRequest;
import com.os.foodie.data.network.model.cart.clear.ClearCartResponse;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartRequest;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartResponse;
import com.os.foodie.data.network.model.cart.update.UpdateCartRequest;
import com.os.foodie.data.network.model.cart.update.UpdateCartResponse;
import com.os.foodie.data.network.model.cart.view.ViewCartRequest;
import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.data.network.model.checkout.CheckoutResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

public class MyBasketPresenter<V extends MyBasketMvpView> extends BasePresenter<V> implements MyBasketMvpPresenter<V> {

    public MyBasketPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getMyBasketDetails(String userId, String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getCartDetails(new ViewCartRequest(userId, restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ViewCartResponse>() {
                        @Override
                        public void accept(ViewCartResponse viewCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (viewCartResponse.getResponse().getIsDeleted() != null && viewCartResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), viewCartResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (viewCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().setMyBasket(viewCartResponse);

                            } else if (viewCartResponse.getResponse().getStatus() == 2) {

//                                getMvpView().onError(viewCartResponse.getResponse().getMessage());
                                getMvpView().setMyBasket(viewCartResponse);

//                                TODO Clear Basket
//                                getMvpView().askForClearBasket();

                            } else {

//                                getMvpView().onError(viewCartResponse.getResponse().getMessage());
                                getMvpView().setMyBasket(viewCartResponse);
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
    public void removeFromMyBasket(String userId, String itemId, final String restaurantId, final int position) {

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

                            if (removeFromCartResponse.getResponse().getIsDeleted() != null && removeFromCartResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), removeFromCartResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

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
    public void updateMyBasket(String userId, String itemId, String restaurantId, final String qty, String price, final int position) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            Log.d("itemId", ">>" + itemId);

            getCompositeDisposable().add(getDataManager()
                    .updateCart(new UpdateCartRequest(userId, itemId, restaurantId, qty, price))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UpdateCartResponse>() {
                        @Override
                        public void accept(UpdateCartResponse updateCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (updateCartResponse.getResponse().getIsDeleted() != null && updateCartResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), updateCartResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (updateCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().updateMyBasket(position, qty, updateCartResponse.getResponse().getData().getTotalCartQuantity(), updateCartResponse.getResponse().getData().getTotalCartAmount());
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

    @Override
    public void clearBasket(String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .clearCart(new ClearCartRequest(getDataManager().getCurrentUserId(),restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ClearCartResponse>() {
                        @Override
                        public void accept(ClearCartResponse clearCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (clearCartResponse.getResponse().getIsDeleted() != null && clearCartResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), clearCartResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (clearCartResponse.getResponse().getStatus() == 1) {

                                getDataManager().setCustomerRestaurantId("");
                                getMvpView().onBasketClear();
                                getMvpView().onError(clearCartResponse.getResponse().getMessage());

                            } else {
                                getMvpView().onError(clearCartResponse.getResponse().getMessage());
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

    public void clearBasketRestaurant() {
        getDataManager().setCustomerRestaurantId("");
    }

    @Override
    public void checkout(CheckoutRequest checkoutRequest) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .checkoout(checkoutRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CheckoutResponse>() {
                        @Override
                        public void accept(CheckoutResponse checkoutResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (checkoutResponse.getResponse().getStatus() == 1) {

                                getDataManager().setCustomerRestaurantId("");

                                Log.d("getResponse", ">>" + checkoutResponse.getResponse().getMessage());

//                                getMvpView().onError(checkoutResponse.getResponse().getMessage());
                                getMvpView().onCheckoutComplete(checkoutResponse);
//                                getMvpView().onCheckoutComplete(checkoutResponse.getResponse().getMessage());

                            } else {
                                getMvpView().onError(checkoutResponse.getResponse().getMessage());
//                                getMvpView().onError("No Restaurant found");
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

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }

    @Override
    public void onError(@StringRes int resId) {
        getMvpView().onError(resId);
    }
}