package com.os.foodie.ui.fbsignup;

import com.os.foodie.ui.base.MvpView;

public interface FacebookSignUpMvpView extends MvpView {

    void verifyOTP();
    void verifyOTP(String otp);
}