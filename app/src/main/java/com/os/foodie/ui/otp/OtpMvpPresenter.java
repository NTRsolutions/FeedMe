package com.os.foodie.ui.otp;

import android.support.annotation.StringRes;

import com.os.foodie.ui.base.MvpPresenter;

public interface OtpMvpPresenter<V extends OtpMvpView> extends MvpPresenter<V> {

//    void verify(String Otp, String userOtp);

    void verify(String otp);

    void showMessage(String string);
}