package com.os.foodie.ui.order.restaurant.detail;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.deliveryaddress.delete.DeleteAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.delete.DeleteAddressResponse;
import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressResponse;
import com.os.foodie.data.network.model.order.detail.OrderHistoryDetail;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListRequest;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressMvpPresenter;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressMvpView;
import com.os.foodie.utils.NetworkUtils;

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
}