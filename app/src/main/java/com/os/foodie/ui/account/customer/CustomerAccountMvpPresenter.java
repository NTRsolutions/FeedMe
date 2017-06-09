package com.os.foodie.ui.account.customer;

import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface CustomerAccountMvpPresenter<V extends CustomerAccountMvpView> extends MvpPresenter<V> {

    void getCustomerAccountDetail();
    void editCustomerAccountDetail(EditCustomerAccountRequest editCustomerAccountRequest);
}