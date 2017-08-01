package com.os.foodie.ui.signup.restaurant;

import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.ui.base.MvpView;

public interface RestaurantSignUpMvpView extends MvpView {

    void doSignUp();

    void openLogInActivity();

    void verifyOTP();

    void verifyOTP(String otp);

    void openCustomerRegistrationActivity();

    void setFacebookDetails(String id, String name, String email);


    void openLocationInfoActivity();

    void openSetupRestaurantProfileActivity();

    void openCustomerHomeActivity();

    void openRestaurantHomeActivity();

    void showError(int key, String message);
}