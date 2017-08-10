package com.os.foodie.ui.order.restaurant.detail;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.cart.add.AddToCartResponse;
import com.os.foodie.data.network.model.changeorderstatus.ChangeOrderStatusResponse;
import com.os.foodie.data.network.model.order.restaurant.detail.OrderHistoryDetail;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderRequest;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderResponse;
import com.os.foodie.data.network.model.reorder.ReorderRequest;
import com.os.foodie.data.network.model.reorder.ReorderResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderHistoryPresenter<V extends OrderHistoryMvpView> extends BasePresenter<V> implements OrderHistoryMvpPresenter<V> {

    public OrderHistoryPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getOrderHistoryDetail(String orderId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getOrderHistoryDetail(orderId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<OrderHistoryDetail>() {
                        @Override
                        public void accept(OrderHistoryDetail getOrderListResponse) throws Exception {

                            getMvpView().hideLoading();

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

                                getMvpView().setOrderHistoryDetail(getOrderListResponse);

                            } else {
                                getMvpView().onError(getOrderListResponse.getResponse().getMessage());
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
    public void ChangeOrderStatus(String orderId, String status) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .changeOrderStatus(orderId, status)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ChangeOrderStatusResponse>() {
                        @Override
                        public void accept(ChangeOrderStatusResponse getOrderListResponse) throws Exception {

                            getMvpView().hideLoading();

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

                                getMvpView().setOrderStatus(getOrderListResponse);

                            } else {
                                getMvpView().onError(getOrderListResponse.getResponse().getMessage());
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
    public void reorder(String orderId, final String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .reorder(new ReorderRequest(orderId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ReorderResponse>() {
                        @Override
                        public void accept(ReorderResponse reorderResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (reorderResponse.getResponse().getIsDeleted() != null && reorderResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), reorderResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (reorderResponse.getResponse().getStatus() == 1) {

                                getDataManager().setCustomerRestaurantId(restaurantId);
                                getMvpView().onReorderComplete(restaurantId);

                            } else {
                                getMvpView().onError(reorderResponse.getResponse().getMessage());
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
    public String getCustomerRestaurantId() {
        return getDataManager().getCustomerRestaurantId();
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }

    @Override
    public String getCurrency() {
        return getDataManager().getCurrency();
    }

    @Override
    public boolean isCustomer() {

        if (getDataManager().getCurrentUserType().equals(AppConstants.CUSTOMER)) {

            return true;
        }

        return false;
    }


    @Override
    public void acceptRejectOrder(final String orderId, String status, final int position) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .acceptRejectOrder(new AcceptRejectOrderRequest(orderId, status))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AcceptRejectOrderResponse>() {
                        @Override
                        public void accept(AcceptRejectOrderResponse acceptRejectOrderResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (acceptRejectOrderResponse.getResponse().getIsDeleted() != null && acceptRejectOrderResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), acceptRejectOrderResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (acceptRejectOrderResponse.getResponse().getStatus() == 1) {

                                getMvpView().onAcceptReject(orderId);
                                getMvpView().onError(acceptRejectOrderResponse.getResponse().getMessage());

                            } else {
                                getMvpView().onError(acceptRejectOrderResponse.getResponse().getMessage());
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
    public boolean isLoggedIn() {
        return getDataManager().isCurrentUserLoggedIn();
    }
}