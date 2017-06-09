package com.os.foodie.ui.account.customer;

import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.GetAccountDetailResponse;
import com.os.foodie.ui.base.MvpView;

public interface CustomerAccountMvpView extends MvpView {

void setAccountDetail(GetAccountDetailResponse getAccountDetailResponse);

    void editCustomerAccountDetail(EditCustomerAccountDetailResponse editCustomerAccountDetailResponse);
}