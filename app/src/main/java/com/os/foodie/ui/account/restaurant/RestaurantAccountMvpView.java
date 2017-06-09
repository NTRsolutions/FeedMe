package com.os.foodie.ui.account.restaurant;

import com.os.foodie.data.network.model.account.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.EditRestaurantAccountResponse;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.ui.base.MvpView;

public interface RestaurantAccountMvpView extends MvpView {

void setRestaurantAccountDetail(CustomerRestaurantDetailsResponse getAccountDetailResponse);

    void editRestaurantAccountDetail(EditRestaurantAccountResponse editRestaurantAccountResponse);
}