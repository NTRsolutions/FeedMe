package com.os.foodie.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;

public interface MvpView {

    void showLoading();

    void hideLoading();

//    String getDeviceId();

//    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void onErrorLong(@StringRes int resId);

    void onErrorLong(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

    Context getContext();
}