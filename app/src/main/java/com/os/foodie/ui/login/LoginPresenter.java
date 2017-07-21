package com.os.foodie.ui.login;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.fblogin.FacebookLoginRequest;
import com.os.foodie.data.network.model.fblogin.FacebookLoginResponse;
import com.os.foodie.data.network.model.login.LoginRequest;
import com.os.foodie.data.network.model.login.LoginResponse;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import java.net.URLDecoder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    public LoginPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onLoginClick(String email, String password, String deviceId, String deviceType) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (email == null || email.isEmpty()) {
                getMvpView().onError(R.string.empty_email);
                return;
            }
            if (!ValidationUtils.isEmailValid(email)) {
                getMvpView().onError(R.string.invalid_email);
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

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .doLogin(new LoginRequest(email, password, deviceId, deviceType))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LoginResponse>() {
                        @Override
                        public void accept(LoginResponse loginResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (loginResponse.getResponse().getStatus() == 0) {
                                getMvpView().onError(loginResponse.getResponse().getMessage());
                            } else {

                                getDataManager().setCurrentUserLoggedIn(true);
                                getDataManager().setCurrentUserId(loginResponse.getResponse().getUserId());
                                getDataManager().setCurrentUserType(loginResponse.getResponse().getUserType());
//                                getDataManager().setCurrency(loginResponse.getResponse().getCurrency());
//
//                                URLDecoder.decode(restaurantProfileRequest.getCurrency(),"UTF-8")
                                getDataManager().setCurrency(URLDecoder.decode(loginResponse.getResponse().getCurrency(),"UTF-8"));

                                if (loginResponse.getResponse().getUserType().equals(AppConstants.CUSTOMER)) {
                                    getDataManager().setCurrentUserName(loginResponse.getResponse().getFirstName() + " " + loginResponse.getResponse().getLastName());
                                    getDataManager().setCustomerRestaurantId(loginResponse.getResponse().getRestaurantId());
                                } else {
                                    getDataManager().setCurrentUserName(loginResponse.getResponse().getRestaurantName());
                                    getDataManager().setRestaurantLogoURL(loginResponse.getResponse().getProfileImage());
                                    Log.d("getProfileImage", ">>" + loginResponse.getResponse().getProfileImage());
                                }

                                if (loginResponse.getResponse().getIsProfileSet().equals("1")) {
                                    getDataManager().setCurrentUserInfoInitialized(true);
                                } else {
                                    getDataManager().setCurrentUserInfoInitialized(false);
                                }

//                                getMvpView().onLoginSuccess(loginResponse.getResponse().getUserType());
                                decideNextActivity();
                            }

                            Log.d("Message", ">>" + loginResponse.getResponse().getMessage());

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

                        if (facebookLoginResponse.getResponse().getStatus() == 1) {

                            getDataManager().setCurrentUserLoggedIn(true);
                            getDataManager().setCurrentUserId(facebookLoginResponse.getResponse().getUserId());
                            getDataManager().setCurrentUserType(facebookLoginResponse.getResponse().getUserType());
//                            getDataManager().setCurrency(facebookLoginResponse.getResponse().getCurrency());
                            getDataManager().setCurrency(URLDecoder.decode(facebookLoginResponse.getResponse().getCurrency(),"UTF-8"));

                            if (facebookLoginResponse.getResponse().getUserType().equals(AppConstants.CUSTOMER)) {
                                getDataManager().setCurrentUserName(facebookLoginResponse.getResponse().getFirstName() + " " + facebookLoginResponse.getResponse().getLastName());
                                getDataManager().setCustomerRestaurantId(facebookLoginResponse.getResponse().getRestaurantId());
                            } else {
                                getDataManager().setCurrentUserName(facebookLoginResponse.getResponse().getRestaurantName());
                                getDataManager().setRestaurantLogoURL(facebookLoginResponse.getResponse().getProfileImage());
                                Log.d("getProfileImage", ">>" + getDataManager().getRestaurantLogoURL());
                            }

                            if (facebookLoginResponse.getResponse().getIsProfileSet().equals("1")) {
                                getDataManager().setCurrentUserInfoInitialized(true);
                            } else {
                                getDataManager().setCurrentUserInfoInitialized(false);
                            }

                            decideNextActivity();

                        } else {

                            getMvpView().setFacebookDetails(fbId, first_name,last_name, email);
//
//                            getMvpView().onError(facebookLoginResponse.getResponse().getMessage());
                        }

                        Log.d("Message", ">>" + facebookLoginResponse.getResponse().getMessage());

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
    public void setError(String message) {
        getMvpView().onErrorLong(message);
    }

    private void decideNextActivity() {

        Log.d("isCurrentUserLoggedIn", "true");

        if (getDataManager().isCurrentUserInfoInitialized()) {

            Log.d("isCurrentUserInfoInit", "true");

            if (getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER)) {

                Log.d("openCustomerHome", "CUSTOMER");

                getMvpView().openCustomerHomeActivity();

            } else {

                Log.d("RestaurantHome", "Res");

                getMvpView().openRestaurantHomeActivity();
            }

        } else {

            Log.d("isCurrentUserInfoInit", "false");

            if (getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER)) {

                Log.d("openLocationInfo", "CUSTOMER");

                getMvpView().openLocationInfoActivity();

            } else {

                Log.d("SetupRestaurantProfile", "Res");

                getMvpView().openSetupRestaurantProfileActivity();
            }
        }
    }

    @Override
    public String getDeviceId() {
        return getDataManager().getDeviceId();
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}