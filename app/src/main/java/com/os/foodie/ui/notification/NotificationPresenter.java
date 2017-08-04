package com.os.foodie.ui.notification;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailRequest;
import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailResponse;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailRequest;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailResponse;
import com.os.foodie.data.network.model.notification.NotificationListResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.merchantdetail.MerchantDetailMvpPresenter;
import com.os.foodie.ui.merchantdetail.MerchantDetailMvpView;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NotificationPresenter<V extends NotificationMvpView> extends BasePresenter<V> implements NotificationMvpPresenter<V> {

    DataManager dataManager;

    public NotificationPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
        this.dataManager = dataManager;
    }

    @Override
    public void getNotificationList(String userId, String restaurantId, final SwipeRefreshLayout swipeRefreshLayout) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (swipeRefreshLayout == null) {
                getMvpView().showLoading();
            } else {
                swipeRefreshLayout.setRefreshing(true);
            }

            getCompositeDisposable().add(getDataManager()
                    .getNotification(userId, restaurantId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<NotificationListResponse>() {
                        @Override
                        public void accept(NotificationListResponse notificationListResponse) throws Exception {

                            if (swipeRefreshLayout == null) {
                                getMvpView().hideLoading();
                            } else {
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            if (notificationListResponse.getResponse().getStatus() == 1) {
                                //getMvpView().onError(notificationListResponse.getResponse().getMessage());
                                getMvpView().setNotificationList(notificationListResponse);

                            } else {
                                getMvpView().setNotificationList(notificationListResponse);
//                                getMvpView().onError(notificationListResponse.getResponse().getMessage());
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
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void readNotification(String notification_id) {
        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            // getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .readNotification(notification_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<NotificationListResponse>() {
                        @Override
                        public void accept(NotificationListResponse notificationListResponse) throws Exception {

                            //getMvpView().hideLoading();

                            if (notificationListResponse.getResponse().getStatus() == 1) {
                                //getMvpView().onError(notificationListResponse.getResponse().getMessage());
                                // getMvpView().setNotificationList(notificationListResponse);

                            } else {
                                // getMvpView().onError(notificationListResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //getMvpView().hideLoading();
                            // getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public DataManager getDataManager() {
        return dataManager;
    }
}