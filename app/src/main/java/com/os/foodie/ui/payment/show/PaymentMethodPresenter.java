package com.os.foodie.ui.payment.show;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.payment.delete.DeletePaymentCardRequest;
import com.os.foodie.data.network.model.payment.delete.DeletePaymentCardResponse;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardRequest;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PaymentMethodPresenter<V extends PaymentMethodMvpView> extends BasePresenter<V> implements PaymentMethodMvpPresenter<V> {

    public PaymentMethodPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
                                getMvpView().onError(getAllPaymentCardResponse.getResponse().getMessage());
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
    public void deletePaymentCard(String cardId, final int position) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .deletePaymentCard(new DeletePaymentCardRequest(cardId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DeletePaymentCardResponse>() {
                        @Override
                        public void accept(DeletePaymentCardResponse deletePaymentCardResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (deletePaymentCardResponse.getResponse().getStatus() == 1) {

                                getMvpView().onPaymentCardDeleted(position);

                            } else {
                                getMvpView().onError(deletePaymentCardResponse.getResponse().getMessage());
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
}