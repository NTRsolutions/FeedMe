package com.os.foodie.ui.signup.customer;

import com.os.foodie.ui.base.MvpPresenter;

public interface CustomerSignUpMvpPresenter<V extends CustomerSignUpMvpView> extends MvpPresenter<V> {

    void onCustomerSignUpClick(String fbId, String fname, String lname, String email, String password, String confirm_password, String phone, String deviceId, String deviceType, String latitude, String longitude, String language);

    void onFacebookLoginClick(String fbId, String first_name, String last_name, String email, String deviceId, String deviceType);

    void setError(int resId);

    void dispose();
}