package com.os.foodie.ui.forgotpassword;

import com.os.foodie.ui.base.MvpPresenter;

public interface ForgotPasswordMvpPresenter<V extends ForgotPasswordMvpView> extends MvpPresenter<V> {

    void resetPassword(String email);
}