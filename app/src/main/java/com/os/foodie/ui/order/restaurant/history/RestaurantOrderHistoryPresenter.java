package com.os.foodie.ui.order.restaurant.history;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderRequest;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderResponse;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListRequest;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpPresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpView;
import com.os.foodie.utils.NetworkUtils;

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
    public void getOrderHistory() {
        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getOrderHistoryList(new GetOrderListRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetOrderListResponse>() {
                        @Override
                        public void accept(GetOrderListResponse getOrderListResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (getOrderListResponse.getResponse().getStatus() == 1) {

                                getMvpView().onOrderHistoryReceived(getOrderListResponse);

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
}