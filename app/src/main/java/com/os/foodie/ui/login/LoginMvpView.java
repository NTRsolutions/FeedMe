package com.os.foodie.ui.login;


import com.os.foodie.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

    void openForgotPassword();
//
//    void onLoginSuccess(String userType);

    void openLocationInfoActivity();

    void openSetupRestaurantProfileActivity();

    void openCustomerHomeActivity();

    void openRestaurantHomeActivity();
}