package com.os.foodie.ui.signup.customer;

import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.ui.base.MvpView;

public interface CustomerSignUpMvpView extends MvpView {

    void doSignUp();

//    void openOtpVerification(String otp);

    void openLogInActivity();

    void verifyOTP();

    void verifyOTP(String otp);

    void openRestaurantRegistrationActivity();

    void setFacebookDetails(String id, String first_name, String last_name, String email);

    void openLocationInfoActivity();

    void openSetupRestaurantProfileActivity();

    void openCustomerHomeActivity();

    void openRestaurantHomeActivity();
}