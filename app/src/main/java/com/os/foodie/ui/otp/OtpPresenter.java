package com.os.foodie.ui.otp;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.otp.OtpVerificationRequest;
import com.os.foodie.data.network.model.otp.OtpVerificationResponse;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OtpPresenter<V extends OtpMvpView> extends BasePresenter<V> implements OtpMvpPresenter<V> {

    public OtpPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

//    @Override
//    public void verify(String Otp, String userOtp) {
//        if (Otp.equals(userOtp)) {
//            getMvpView().onError("Right");
//            getMvpView().onVerify();
//        } else {
//            getMvpView().onError(R.string.incorrect_otp);
//        }
//    }

    @Override
    public void verify(String otp) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (otp == null || otp.isEmpty()) {
                getMvpView().onError(R.string.empty_otp);
                return;
            }
            if (otp.length() < 6) {
                getMvpView().onError(R.string.minimum_otp);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .verifyOTP(new OtpVerificationRequest(otp))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<OtpVerificationResponse>() {
                        @Override
                        public void accept(OtpVerificationResponse otpVerificationResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (otpVerificationResponse.getResponse().getStatus() == 1) {
                                Log.d("getUserId", ">>" + otpVerificationResponse.getResponse().getUserId());
//                                Log.d("getUserType", ">>" + otpVerificationResponse.getResponse().getUserType());
//                                getMvpView().onError("Done");

                                getDataManager().setCurrentUserLoggedIn(true);

                                getDataManager().setCurrentUserId(otpVerificationResponse.getResponse().getUserId());
                                getDataManager().setCurrentUserType(otpVerificationResponse.getResponse().getUserType());
                                if (otpVerificationResponse.getResponse().getUserType().equals(AppConstants.CUSTOMER)) {
                                    getDataManager().setCurrentUserName(otpVerificationResponse.getResponse().getFirstName() + " " + otpVerificationResponse.getResponse().getLastName());
                                    getDataManager().setCustomerRestaurantId("");
                                } else {
                                    getDataManager().setCurrentUserName(otpVerificationResponse.getResponse().getRestaurantName());
                                    getDataManager().setRestaurantLogoURL(otpVerificationResponse.getResponse().getProfileImage());
                                }

                                getMvpView().onVerify(otpVerificationResponse.getResponse().getUserType());

                            } else {
                                getMvpView().onError(R.string.incorrect_otp);
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
    public void showMessage(String string) {
        getMvpView().onErrorLong(string);
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}