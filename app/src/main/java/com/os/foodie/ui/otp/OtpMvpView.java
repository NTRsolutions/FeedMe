package com.os.foodie.ui.otp;

import com.os.foodie.ui.base.MvpView;

public interface OtpMvpView extends MvpView {

    void onVerify(String userType);
}