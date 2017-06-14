package com.os.foodie.ui.deliveryaddress.addedit;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.data.network.model.forgotpassword.ForgotPasswordRequest;
import com.os.foodie.data.network.model.forgotpassword.ForgotPasswordResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.forgotpassword.ForgotPasswordMvpPresenter;
import com.os.foodie.ui.forgotpassword.ForgotPasswordMvpView;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddDeliveryAddressPresenter<V extends AddDeliveryAddressMvpView> extends BasePresenter<V> implements AddDeliveryAddressMvpPresenter<V> {

    public AddDeliveryAddressPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void addDeliverAddress(AddDeliveryAddressRequest addDeliveryAddressRequest) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (addDeliveryAddressRequest.getFullName() == null || (addDeliveryAddressRequest.getFullName().isEmpty())) {
                getMvpView().onError(R.string.empty_fullname);
                return;
            }
            if (addDeliveryAddressRequest.getMobileNumber() == null || addDeliveryAddressRequest.getMobileNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(addDeliveryAddressRequest.getMobileNumber())) {
                getMvpView().onError(R.string.invalid_phone);
                return;
            }
            if (addDeliveryAddressRequest.getPincode() == null || addDeliveryAddressRequest.getPincode().isEmpty()) {
                getMvpView().onError(R.string.empty_zip_code);
                return;
            }

            if (addDeliveryAddressRequest.getFlatNumber() == null || addDeliveryAddressRequest.getFlatNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_flat);
                return;
            }
            if (addDeliveryAddressRequest.getColony() == null || addDeliveryAddressRequest.getColony().isEmpty()) {
                getMvpView().onError(R.string.empty_colony);
                return;
            }
            if (addDeliveryAddressRequest.getCity() == null || addDeliveryAddressRequest.getCity().isEmpty()) {
                getMvpView().onError(R.string.empty_city);
                return;
            }
            if (addDeliveryAddressRequest.getState() == null || addDeliveryAddressRequest.getState().isEmpty()) {
                getMvpView().onError(R.string.empty_state);
                return;
            }
            if (addDeliveryAddressRequest.getCountry() == null || addDeliveryAddressRequest.getCountry().isEmpty()) {
                getMvpView().onError(R.string.empty_country);
                return;
            }


            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .addDeliveryAddress(addDeliveryAddressRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ForgotPasswordResponse>() {
                        @Override
                        public void accept(ForgotPasswordResponse forgotPasswordResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (forgotPasswordResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + forgotPasswordResponse.getResponse().getMessage());
                                getMvpView().onDeliveryAddressAdd(forgotPasswordResponse.getResponse().getMessage());
//                                getMvpView().onPasswordReset("Password Reset");

                            } else {
                                getMvpView().onError(forgotPasswordResponse.getResponse().getMessage());
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