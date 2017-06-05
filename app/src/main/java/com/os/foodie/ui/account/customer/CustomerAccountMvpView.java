package com.os.foodie.ui.account.customer;

import com.os.foodie.data.network.model.account.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.EditCustomerAccountRequest;
import com.os.foodie.data.network.model.account.GetAccountDetailResponse;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface CustomerAccountMvpView extends MvpView {

void setAccountDetail(GetAccountDetailResponse getAccountDetailResponse);

    void editCustomerAccountDetail(EditCustomerAccountDetailResponse editCustomerAccountDetailResponse);
}