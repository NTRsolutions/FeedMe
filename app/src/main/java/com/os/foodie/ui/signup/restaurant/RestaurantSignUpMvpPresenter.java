package com.os.foodie.ui.signup.restaurant;

import android.support.annotation.StringRes;

import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.base.MvpPresenter;

import java.io.File;
import java.util.HashMap;

public interface RestaurantSignUpMvpPresenter<V extends RestaurantSignUpMvpView> extends MvpPresenter<V> {

    void onRestaurantSignUpClick(String fbId, String contactPersonName, String restaurantName, String email, String confirmEmail, String password, String confirm_password, String countryCode, String phone, String deviceId, String deviceType, String latitude, String longitude, String language, HashMap<String, File> fileMap,String restaurantNameArabic);

    void onFacebookLoginClick(String fbId, String contactPersonName, String email, String deviceId, String deviceType);

    void setError(@StringRes int resId);

    String getDeviceId();

    void dispose();
}