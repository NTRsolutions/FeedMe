package com.os.foodie.ui.order.restaurant.list;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderRequest;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderResponse;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListRequest;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.data.network.model.orderlist.show.OrderList;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantOrderListPresenter<V extends RestaurantOrderListMvpView> extends BasePresenter<V> implements RestaurantOrderListMvpPresenter<V> {

    public RestaurantOrderListPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }

    @Override
    public void getOrderList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();
            Log.d("getOrderList", ">>Called");

            getCompositeDisposable().add(getDataManager()
                    .getOrderList(new GetOrderListRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetOrderListResponse>() {
                        @Override
                        public void accept(GetOrderListResponse getOrderListResponse) throws Exception {

                            getMvpView().hideLoading();
                            Log.d("getOrderList", ">>hideLoading");

                            if (getOrderListResponse.getResponse().getStatus() == 1) {

                                getMvpView().onOrderListReceived(getOrderListResponse);

                            } else {

                                getOrderListResponse.getResponse().setOrderList(new ArrayList<OrderList>());
                                getMvpView().onOrderListReceived(getOrderListResponse);
//                                getMvpView().onError(getOrderListResponse.getResponse().getMessage());
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
    public void acceptRejectOrder(String orderId, String status, final int position) {

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

                            if (acceptRejectOrderResponse.getResponse().getStatus() == 1) {

                                getMvpView().onAcceptReject(position);
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
}