package com.os.foodie.ui.signup.restaurant;

import android.support.annotation.StringRes;
import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.fblogin.FacebookLoginRequest;
import com.os.foodie.data.network.model.fblogin.FacebookLoginResponse;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpRequest;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import java.io.File;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantSignUpPresenter<V extends RestaurantSignUpMvpView> extends BasePresenter<V> implements RestaurantSignUpMvpPresenter<V> {

    public RestaurantSignUpPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onRestaurantSignUpClick(String fbId, String contactPersonName, String restaurantName, String email, String password, String confirm_password, String phone, String deviceId, String deviceType, String latitude, String longitude, String language, HashMap<String, File> fileMap) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {
            if (fileMap.size() == 0) {
                getMvpView().onError(R.string.mandatory_logo);
                return;
            }
            if (contactPersonName == null || contactPersonName.isEmpty()) {
                getMvpView().onError(R.string.empty_contact_person_name);
                return;
            }
            if (restaurantName == null || restaurantName.isEmpty()) {
                getMvpView().onError(R.string.empty_restaurant_name);
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

            final RestaurantSignUpRequest restaurantSignUpRequest = new RestaurantSignUpRequest(fbId, contactPersonName, restaurantName, email, password, phone, deviceId, deviceType, latitude, longitude, language);

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .doRestaurantSignUp(restaurantSignUpRequest, fileMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<RestaurantSignUpResponse>() {
                        @Override
                        public void accept(RestaurantSignUpResponse restaurantSignUpResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (restaurantSignUpResponse.getResponse().getStatus() == 1) {
                                Log.d("OTP", ">>" + restaurantSignUpResponse.getResponse().getOtp());
//                                TODO OTP
//                                getMvpView().verifyOTP();
                                getMvpView().verifyOTP(restaurantSignUpResponse.getResponse().getOtp());

                            } else {
                                getMvpView().onError(restaurantSignUpResponse.getResponse().getMessage());
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
    public void onFacebookLoginClick(final String fbId, final String contactPersonName, final String email, String deviceId, String deviceType) {

//        getMvpView().onError("Res fb sign up done");

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
                            getMvpView().setFacebookDetails(fbId, contactPersonName, email);
                        } else {
                            getDataManager().setCurrentUserLoggedIn(true);
                            getDataManager().setCurrentUserId(facebookLoginResponse.getResponse().getUserId());
                            getDataManager().setCurrentUserType(facebookLoginResponse.getResponse().getUserType());
                            getDataManager().setCurrency(facebookLoginResponse.getResponse().getCurrency());
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
    public void setError(@StringRes int resId) {
        getMvpView().onError(resId);
    }

    @Override
    public String getDeviceId() {
        return getDataManager().getDeviceId();
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
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
}