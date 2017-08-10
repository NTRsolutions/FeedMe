package com.os.foodie.ui.order.restaurant.history;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.order.customer.history.CustomerOrderHistoryRequest;
import com.os.foodie.data.network.model.order.customer.history.CustomerOrderHistoryResponse;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderRequest;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderResponse;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListRequest;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpPresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpView;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantOrderHistoryPresenter<V extends RestaurantOrderHistoryMvpView> extends BasePresenter<V> implements RestaurantOrderHistoryMvpPresenter<V> {

    public RestaurantOrderHistoryPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void getOrderHistory(final SwipeRefreshLayout swipeRefreshLayout) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (swipeRefreshLayout == null) {
                getMvpView().showLoading();
            } else {
                swipeRefreshLayout.setRefreshing(true);
            }

            if (getDataManager().getCurrentUserType().equals(AppConstants.RESTAURANT)) {

                getCompositeDisposable().add(getDataManager()
                        .getOrderHistoryList(new GetOrderListRequest(getDataManager().getCurrentUserId()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GetOrderListResponse>() {
                            @Override
                            public void accept(GetOrderListResponse getOrderListResponse) throws Exception {

                                if (swipeRefreshLayout == null) {
                                    getMvpView().hideLoading();
                                } else {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                                if (getOrderListResponse.getResponse().getIsDeleted() != null && getOrderListResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

                                    Locale locale = new Locale(AppConstants.LANG_EN);
                                    Locale.setDefault(locale);

                                    Configuration config = new Configuration();
                                    config.locale = locale;

                                    getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                    Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getMvpView().getContext().startActivity(intent);

                                    getDataManager().setLanguage(AppConstants.LANG_EN);

                                    setUserAsLoggedOut();

                                    Toast.makeText(getMvpView().getContext(), getOrderListResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                if (getOrderListResponse.getResponse().getStatus() == 1) {

                                    getMvpView().onOrderHistoryReceived(getOrderListResponse);

                                } else {
                                    getMvpView().onOrderHistoryReceived(getOrderListResponse);
//                                    getMvpView().onError(getOrderListResponse.getResponse().getMessage());
                                }

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                if (swipeRefreshLayout == null) {
                                    getMvpView().hideLoading();
                                } else {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                                getMvpView().onError(R.string.api_default_error);
                                Log.d("Error", ">>Err" + throwable.getMessage());
                            }
                        }));
            } else {

                getCompositeDisposable().add(getDataManager()
                        .getCustomerOrderHistoryList(new CustomerOrderHistoryRequest(getDataManager().getCurrentUserId()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GetOrderListResponse>() {
                            @Override
                            public void accept(GetOrderListResponse getOrderListResponse) throws Exception {

                                if (swipeRefreshLayout == null) {
                                    getMvpView().hideLoading();
                                } else {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                                if (getOrderListResponse.getResponse().getIsDeleted() != null && getOrderListResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

                                    Locale locale = new Locale(AppConstants.LANG_EN);
                                    Locale.setDefault(locale);

                                    Configuration config = new Configuration();
                                    config.locale = locale;

                                    getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                    Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getMvpView().getContext().startActivity(intent);

                                    getDataManager().setLanguage(AppConstants.LANG_EN);

                                    setUserAsLoggedOut();

                                    Toast.makeText(getMvpView().getContext(), getOrderListResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                if (getOrderListResponse.getResponse().getStatus() == 1) {

                                    getMvpView().onOrderHistoryReceived(getOrderListResponse);

                                } else {
                                    getMvpView().onError(getOrderListResponse.getResponse().getMessage());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                if (swipeRefreshLayout == null) {
                                    getMvpView().hideLoading();
                                } else {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

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
    public String getCurrentUserType() {
        return getDataManager().getCurrentUserType();
    }
}