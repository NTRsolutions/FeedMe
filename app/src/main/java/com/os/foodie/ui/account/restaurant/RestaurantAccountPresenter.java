package com.os.foodie.ui.account.restaurant;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountRequest;
import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountResponse;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsRequest;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import java.io.File;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantAccountPresenter<V extends RestaurantAccountMvpView> extends BasePresenter<V> implements RestaurantAccountMvpPresenter<V> {

    public RestaurantAccountPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantAccountDetail(String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantDetails(new CustomerRestaurantDetailsRequest(getDataManager().getCurrentUserId(), restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CustomerRestaurantDetailsResponse>() {
                        @Override
                        public void accept(CustomerRestaurantDetailsResponse restaurantDetailsResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (restaurantDetailsResponse.getResponse().getStatus() == 1) {

                                Log.d("Logo", ">>" + restaurantDetailsResponse.getResponse().getLogo());
                                getMvpView().setRestaurantAccountDetail(restaurantDetailsResponse);

                            } else {
                                Log.d("getMessage", ">>Failed");
//                                getMvpView().onError(forgotPasswordResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else

        {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void editRestaurantAccountDetail(EditRestaurantAccountRequest editRestaurantAccountRequest, HashMap<String, File> fileMapTemp) {
        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (editRestaurantAccountRequest.getRestaurantName() == null || editRestaurantAccountRequest.getRestaurantName().isEmpty()) {
                getMvpView().onError(R.string.empty_restaurant_name);
                return;
            }
            if (editRestaurantAccountRequest.getContactPersonName() == null || editRestaurantAccountRequest.getContactPersonName().isEmpty()) {
                getMvpView().onError(R.string.empty_contact_person_name);
                return;
            }
//        if (!ValidationUtils.isNameValid(fname)) {
//            getMvpView().onError(R.string.invalid_first_name);
//            return;
//        }
            if (editRestaurantAccountRequest.getEmail() == null || editRestaurantAccountRequest.getEmail().isEmpty()) {
                getMvpView().onError(R.string.empty_email);
                return;
            }
            if (!ValidationUtils.isEmailValid(editRestaurantAccountRequest.getEmail())) {
                getMvpView().onError(R.string.invalid_email);
                return;
            }

            if (editRestaurantAccountRequest.getMobileNumber() == null || editRestaurantAccountRequest.getMobileNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(editRestaurantAccountRequest.getMobileNumber())) {
                getMvpView().onError(R.string.invalid_phone);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .editRestaurantAccount(editRestaurantAccountRequest, fileMapTemp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<EditRestaurantAccountResponse>() {
                        @Override
                        public void accept(EditRestaurantAccountResponse editCustomerAccountDetailResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (editCustomerAccountDetailResponse.getResponse().getStatus() == 1) {
//                                TODO OTP
//                                getMvpView().verifyOTP();
                                Log.d("Logo", ">>" + editCustomerAccountDetailResponse.getResponse().getLogo());
                                getDataManager().setRestaurantLogoURL(editCustomerAccountDetailResponse.getResponse().getLogo());
                                getDataManager().setCurrentUserName(editCustomerAccountDetailResponse.getResponse().getRestaurantName());
                                getMvpView().editRestaurantAccountDetail(editCustomerAccountDetailResponse);

                            } else {
                                getMvpView().onError(editCustomerAccountDetailResponse.getResponse().getMessage());
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }
}