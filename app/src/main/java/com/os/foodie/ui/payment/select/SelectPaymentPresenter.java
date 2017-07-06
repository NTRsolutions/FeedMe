package com.os.foodie.ui.payment.select;

import android.support.annotation.StringRes;
import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.data.network.model.checkout.CheckoutResponse;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardRequest;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SelectPaymentPresenter<V extends SelectPaymentMvpView> extends BasePresenter<V> implements SelectPaymentMvpPresenter<V> {

    public SelectPaymentPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getAllPaymentCard() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getAllPaymentCard(new GetAllPaymentCardRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetAllPaymentCardResponse>() {
                        @Override
                        public void accept(GetAllPaymentCardResponse getAllPaymentCardResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (getAllPaymentCardResponse.getResponse().getStatus() == 1) {

                                getMvpView().notifyDataSetChanged(getAllPaymentCardResponse);

                            } else {
                                getMvpView().notifyDataSetChanged(getAllPaymentCardResponse);
//                                getMvpView().onError(getAllPaymentCardResponse.getResponse().getMessage());
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

                                getMvpView().onCheckoutComplete(checkoutResponse.getResponse().getMessage());

                            } else {
                                getMvpView().onError(checkoutResponse.getResponse().getMessage());
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
    public void setError(@StringRes int resId) {
        getMvpView().onError(resId);
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}