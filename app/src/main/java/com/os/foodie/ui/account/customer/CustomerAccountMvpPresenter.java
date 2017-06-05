package com.os.foodie.ui.account.customer;

import com.os.foodie.data.network.model.account.EditCustomerAccountRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.ui.base.MvpPresenter;
import com.os.foodie.ui.home.customer.*;

public interface CustomerAccountMvpPresenter<V extends CustomerAccountMvpView> extends MvpPresenter<V> {

    void getCustomerAccountDetail();
    void editCustomerAccountDetail(EditCustomerAccountRequest editCustomerAccountRequest);
}