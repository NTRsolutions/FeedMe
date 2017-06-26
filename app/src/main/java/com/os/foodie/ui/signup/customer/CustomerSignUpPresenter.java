package com.os.foodie.ui.signup.customer;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.network.model.fblogin.FacebookLoginRequest;
import com.os.foodie.data.network.model.fblogin.FacebookLoginResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CustomerSignUpPresenter<V extends CustomerSignUpMvpView> extends BasePresenter<V> implements CustomerSignUpMvpPresenter<V> {

    public CustomerSignUpPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public V getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void onCustomerSignUpClick(final String fbId, final String first_name, final String last_name, final String email, final String password, String confirm_password, String phone, final String deviceId, final String deviceType, final String latitude, final String longitude, final String language) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (first_name == null || first_name.isEmpty()) {
                getMvpView().onError(R.string.empty_first_name);
                return;
            }
            if (last_name == null || last_name.isEmpty()) {
                getMvpView().onError(R.string.empty_last_name);
                return;
            }
//        if (!ValidationUtils.isNameValid(fname)) {
//            getMvpView().onError(R.string.invalid_first_name);
//            return;
//        }
            if (email == null || email.isEmpty()) {
                getMvpView().onError(R.string.empty_email);
                return;
            }
            if (!ValidationUtils.isEmailValid(email)) {
                getMvpView().onError(R.string.invalid_email);
                return;
            }
            if (phone == null || phone.isEmpty()) {
                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(phone)) {
                getMvpView().onError(R.string.invalid_phone);
                return;
            }
            if (password == null || password.isEmpty()) {
                getMvpView().onError(R.string.empty_password);
                return;
            }
            if (password.length() < 6) {
                getMvpView().onError(R.string.minimum_password);
                return;
            }
            if (confirm_password == null || confirm_password.isEmpty()) {
                getMvpView().onError(R.string.empty_confirm_password);
                return;
            }
            if (!password.equals(confirm_password)) {
                getMvpView().onError(R.string.not_match_password);
                return;
            }

            final CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest(fbId, first_name, last_name, email, password, phone, deviceId, deviceType, latitude, longitude, language);

//            getMvpView().verifyOTP(new CustomerSignUpRequest(fbId, first_name, last_name, email, password, deviceId, deviceType, latitude, longitude, language));

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .doCustomerSignUp(customerSignUpRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CustomerSignUpResponse>() {
                        @Override
                        public void accept(CustomerSignUpResponse customerSignUpResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (customerSignUpResponse.getResponse().getStatus() == 1) {
                                Log.d("OTP", ">>" + customerSignUpResponse.getResponse().getOtp());
//                                TODO OTP
//                                getMvpView().verifyOTP();
                                getMvpView().verifyOTP(customerSignUpResponse.getResponse().getOtp());

                            } else {
                                getMvpView().onError(customerSignUpResponse.getResponse().getMessage());
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
    public void onFacebookLoginClick(final String fbId, final String first_name, final String last_name, final String email, String deviceId, String deviceType) {

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doFacebookLogin(new FacebookLoginRequest(email, fbId, deviceId, deviceType))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FacebookLoginResponse>() {
                    @Override
                    public void accept(FacebookLoginResponse facebookLoginResponse) throws Exception {

                        getMvpView().hideLoading();

                        if (facebookLoginResponse.getResponse().getStatus() == 0) {
                            getMvpView().onError(facebookLoginResponse.getResponse().getMessage());
                            getMvpView().setFacebookDetails(fbId, first_name, last_name, email);

                        } else {
                            getMvpView().onError(R.string.fb_user_already_exist);
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
    }

    @Override
    public void setError(int resId) {
        getMvpView().onError(resId);
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}