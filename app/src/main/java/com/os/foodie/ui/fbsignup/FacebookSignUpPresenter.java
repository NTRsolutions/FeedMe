package com.os.foodie.ui.fbsignup;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpRequest;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpResponse;
import com.os.foodie.model.FacebookSignUpModel;
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

public class FacebookSignUpPresenter<V extends FacebookSignUpMvpView> extends BasePresenter<V> implements FacebookSignUpMvpPresenter<V> {

    public FacebookSignUpPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onSubmit(FacebookSignUpModel facebookSignUpModel, String restaurantName, String restaurantNameArabic, String countryCode, String phone, String deviceId, String deviceType, String latitude, String longitude, String language, HashMap<String, File> fileMap) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (!facebookSignUpModel.getIsCustomer()) {

                if (fileMap.size() == 0) {
                    getMvpView().onError(R.string.mandatory_logo);
                    return;
                }
            }

            if (countryCode == null || countryCode.isEmpty()) {
                getMvpView().onError(R.string.empty_country_code);
                return;
            }
            if (phone == null || phone.isEmpty()) {
                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(phone)) {
                getMvpView().onError(R.string.invalid_phone);
                return;
            }

            if (facebookSignUpModel.getIsCustomer()) {

                final CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest(facebookSignUpModel.getFbId(), facebookSignUpModel.getFirstName(), facebookSignUpModel.getLastName(), facebookSignUpModel.getEmail(), "", countryCode, phone, deviceId, deviceType, latitude, longitude, language);

                getMvpView().showLoading();

                getCompositeDisposable().add(getDataManager()
                        .doCustomerSignUp(customerSignUpRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CustomerSignUpResponse>() {
                            @Override
                            public void accept(CustomerSignUpResponse customerSignUpResponse) throws Exception {

                                getMvpView().hideLoading();

                                if (customerSignUpResponse.getResponse().getIsDeleted() != null && customerSignUpResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                    Locale locale = new Locale(AppConstants.LANG_EN);
//                                    Locale.setDefault(locale);
//
//                                    Configuration config = new Configuration();
//                                    config.locale = locale;
//
//                                    getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                    Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getMvpView().getContext().startActivity(intent);

//                                    getDataManager().setLanguage(AppConstants.LANG_EN);

                                    setUserAsLoggedOut();

                                    Toast.makeText(getMvpView().getContext(), customerSignUpResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                if (customerSignUpResponse.getResponse().getStatus() == 1) {
                                    Log.d("OTP", ">>" + customerSignUpResponse.getResponse().getOtp());
                                    getMvpView().verifyOTP(customerSignUpResponse.getResponse().getUserId(), customerSignUpResponse.getResponse().getOtp());

                                } else {
                                    getMvpView().onError(customerSignUpResponse.getResponse().getMessage());
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

                if (restaurantName == null || restaurantName.isEmpty()) {
                    getMvpView().onError(R.string.empty_restaurant_name);
                    return;
                }
                if (restaurantNameArabic == null || restaurantNameArabic.isEmpty()) {
                    getMvpView().onError(R.string.empty_restaurant_name_arabic);
                    return;
                }

                final RestaurantSignUpRequest restaurantSignUpRequest = new RestaurantSignUpRequest(facebookSignUpModel.getFbId(), facebookSignUpModel.getContactPersonName(), restaurantName, facebookSignUpModel.getEmail(), "", countryCode, phone, deviceId, deviceType, latitude, longitude, language, restaurantNameArabic);

                getMvpView().showLoading();

                getCompositeDisposable().add(getDataManager()
                        .doRestaurantSignUp(restaurantSignUpRequest, fileMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<RestaurantSignUpResponse>() {
                            @Override
                            public void accept(RestaurantSignUpResponse restaurantSignUpResponse) throws Exception {

                                getMvpView().hideLoading();

                                if (restaurantSignUpResponse.getResponse().getIsDeleted() != null && restaurantSignUpResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                    Locale locale = new Locale(AppConstants.LANG_EN);
//                                    Locale.setDefault(locale);
//
//                                    Configuration config = new Configuration();
//                                    config.locale = locale;
//
//                                    getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                    Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getMvpView().getContext().startActivity(intent);

//                                    getDataManager().setLanguage(AppConstants.LANG_EN);

                                    setUserAsLoggedOut();

                                    Toast.makeText(getMvpView().getContext(), restaurantSignUpResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                if (restaurantSignUpResponse.getResponse().getStatus() == 1) {
                                    Log.d("OTP", ">>" + restaurantSignUpResponse.getResponse().getOtp());
                                    getMvpView().verifyOTP(restaurantSignUpResponse.getResponse().getUserId(), restaurantSignUpResponse.getResponse().getOtp());

                                } else {
                                    getMvpView().onError(restaurantSignUpResponse.getResponse().getMessage());
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
            }
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public String getDeviceId() {
        return getDataManager().getDeviceId();
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}