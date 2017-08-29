package com.os.foodie.ui.otp;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.otp.resend.ResendOtpRequest;
import com.os.foodie.data.network.model.otp.resend.ResendOtpResponse;
import com.os.foodie.data.network.model.otp.verify.OtpVerificationRequest;
import com.os.foodie.data.network.model.otp.verify.OtpVerificationResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

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
    public void verify(String userId, String otp) {

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
                    .verifyOTP(new OtpVerificationRequest(userId, otp))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<OtpVerificationResponse>() {
                        @Override
                        public void accept(OtpVerificationResponse otpVerificationResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (otpVerificationResponse.getResponse().getIsDeleted() != null && otpVerificationResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), otpVerificationResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (otpVerificationResponse.getResponse().getStatus() == 1) {
                                Log.d("getUserId", ">>" + otpVerificationResponse.getResponse().getUserId());
//                                Log.d("getUserType", ">>" + otpVerificationResponse.getResponse().getUserType());
//                                getMvpView().onError("Done");

                                getDataManager().setCurrentUserLoggedIn(true);

                                if(otpVerificationResponse.getResponse().getIsFacebook().equalsIgnoreCase("no")){

                                    getDataManager().setFacebook(false);
                                } else {

                                    getDataManager().setFacebook(true);
                                }

                                getDataManager().setCurrentUserId(otpVerificationResponse.getResponse().getUserId());
                                getDataManager().setCurrentUserType(otpVerificationResponse.getResponse().getUserType());


                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                Locale locale = new Locale(getDataManager().getLanguage());
                                Locale.setDefault(locale);

                                Configuration config = new Configuration();
                                config.locale = locale;

                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());


                                getDataManager().setNotificationStatus("1");

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
    public void resendOtp(String userId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .resendOTP(new ResendOtpRequest(userId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResendOtpResponse>() {
                        @Override
                        public void accept(ResendOtpResponse resendOtpResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (resendOtpResponse.getResponse().getIsDeleted() != null && resendOtpResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), resendOtpResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (resendOtpResponse.getResponse().getStatus() == 1) {

                                Log.d("getUserId", ">>" + resendOtpResponse.getResponse().getMessage());
                                getMvpView().onOtpResend();

                            } else {
                                getMvpView().onError(resendOtpResponse.getResponse().getMessage());
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