package com.os.foodie.ui.payment.add;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardRequest;
import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddPaymentCardPresenter<V extends AddPaymentCardMvpView> extends BasePresenter<V> implements AddPaymentCardMvpPresenter<V> {

    public AddPaymentCardPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void addPaymentCard(AddPaymentCardRequest addPaymentCardRequest) {

        addPaymentCardRequest.setUserId(getDataManager().getCurrentUserId());

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            if (addPaymentCardRequest.getFirstName() == null || addPaymentCardRequest.getFirstName().isEmpty()) {
                getMvpView().onError(R.string.empty_first_name);
                return;
            }

            if (addPaymentCardRequest.getLastName() == null || addPaymentCardRequest.getLastName().isEmpty()) {
                getMvpView().onError(R.string.empty_last_name);
                return;
            }

            if (addPaymentCardRequest.getCreditCardNumber() == null || addPaymentCardRequest.getCreditCardNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_credit_card_number);
                return;
            }

            if (addPaymentCardRequest.getExpiryMonth() == null || addPaymentCardRequest.getExpiryMonth().isEmpty()) {
                getMvpView().onError(R.string.empty_expiry_month);
                return;
            }

            if (addPaymentCardRequest.getExpiryYear() == null || addPaymentCardRequest.getExpiryYear().isEmpty()) {
                getMvpView().onError(R.string.empty_expiry_year);
                return;
            }

            if (addPaymentCardRequest.getCvv2() == null || addPaymentCardRequest.getCvv2().isEmpty()) {
                getMvpView().onError(R.string.empty_cvv);
                return;
            }

            getCompositeDisposable().add(getDataManager()
                    .addPaymentCard(addPaymentCardRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddPaymentCardResponse>() {
                        @Override
                        public void accept(AddPaymentCardResponse addPaymentCardResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (addPaymentCardResponse.getResponse().getStatus() == 1) {

                                getMvpView().onPaymentAdded(addPaymentCardResponse);

                            } else {
                                getMvpView().onError(addPaymentCardResponse.getResponse().getMessage());
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
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}