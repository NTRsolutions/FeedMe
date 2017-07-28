package com.os.foodie.ui.mybasketlist;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.cart.clear.ClearCartRequest;
import com.os.foodie.data.network.model.cart.clear.ClearCartResponse;
import com.os.foodie.data.network.model.cart.list.GetCartListRequest;
import com.os.foodie.data.network.model.cart.list.GetCartListResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyBasketListPresenter<V extends MyBasketListMvpView> extends BasePresenter<V> implements MyBasketListMvpPresenter<V> {

    public MyBasketListPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getCartList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getCartList(new GetCartListRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetCartListResponse>() {
                        @Override
                        public void accept(GetCartListResponse cartListResponse) throws Exception {

                            getMvpView().hideLoading();

//                            if (cartListResponse.getResponse().getStatus() == 1) {
//
//                                getMvpView().onError(clearCartResponse.getResponse().getMessage());
//
//                            } else {
//                                getMvpView().onError(clearCartResponse.getResponse().getMessage());
//                            }
                            getMvpView().setCartList(cartListResponse);
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
    public void dispose() {

        getMvpView().hideLoading();
        getCompositeDisposable().dispose();
    }
}