package com.os.foodie.ui.login;

import com.os.foodie.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onLoginClick(String email, String password, String deviceId, String deviceType);

    void onFacebookLoginClick(String fbId, String first_name, String last_name, String email, String deviceId, String deviceType);

    void setError(int resId);

    void setError(String message);
}