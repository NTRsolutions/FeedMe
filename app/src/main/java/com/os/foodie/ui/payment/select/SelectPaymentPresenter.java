package com.os.foodie.ui.payment.select;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
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
}