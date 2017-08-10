package com.os.foodie.ui.account.restaurant;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountRequest;
import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountResponse;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsRequest;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

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

                            if (restaurantDetailsResponse.getResponse().getIsDeleted() != null && restaurantDetailsResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

                                Locale locale = new Locale(AppConstants.LANG_EN);
                                Locale.setDefault(locale);

                                Configuration config = new Configuration();
                                config.locale = locale;

                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), restaurantDetailsResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

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

            if (editRestaurantAccountRequest.getRestaurantName() == null || editRestaurantAccountRequest.getRestaurantName().trim().isEmpty()) {
                getMvpView().onError(R.string.empty_restaurant_name);
                return;
            }
            if (editRestaurantAccountRequest.getContactPersonName() == null || editRestaurantAccountRequest.getContactPersonName().trim().isEmpty()) {
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

                            if (editCustomerAccountDetailResponse.getResponse().getIsDeleted() != null && editCustomerAccountDetailResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

                                Locale locale = new Locale(AppConstants.LANG_EN);
                                Locale.setDefault(locale);

                                Configuration config = new Configuration();
                                config.locale = locale;

                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), editCustomerAccountDetailResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (editCustomerAccountDetailResponse.getResponse().getStatus() == 1) {
                                Log.d("Logo", ">>" + editCustomerAccountDetailResponse.getResponse().getLogo());

                                if (editCustomerAccountDetailResponse.getResponse().getLogo() != null && !editCustomerAccountDetailResponse.getResponse().getLogo().isEmpty()) {
                                    getDataManager().setRestaurantLogoURL(editCustomerAccountDetailResponse.getResponse().getLogo());
                                }

                                Log.d("getContactPersonName", ">>" + editCustomerAccountDetailResponse.getResponse().getContactPersonName());
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

    @Override
    public String getRestaurantLogoURL() {
        return getDataManager().getRestaurantLogoURL();
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}