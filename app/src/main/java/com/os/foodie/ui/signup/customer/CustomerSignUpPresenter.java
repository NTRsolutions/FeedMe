package com.os.foodie.ui.signup.customer;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.network.model.fblogin.FacebookLoginRequest;
import com.os.foodie.data.network.model.fblogin.FacebookLoginResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CustomerSignUpPresenter<V extends CustomerSignUpMvpView> extends BasePresenter<V> implements CustomerSignUpMvpPresenter<V> {

    public CustomerSignUpPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public V getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void onCustomerSignUpClick(final String fbId, final String first_name, final String last_name, final String email, final String confirmEmail, final String password, String confirm_password, String countryCode, String phone, final String deviceId, final String deviceType, final String latitude, final String longitude, String language) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (first_name == null || first_name.trim().isEmpty()) {

                getMvpView().showError(1, getMvpView().getContext().getString(R.string.empty_first_name));
//                getMvpView().onError(R.string.empty_first_name);
                return;
            }
            if (first_name.trim().length() > 15) {

                getMvpView().showError(1, getMvpView().getContext().getString(R.string.long_first_name));
//                getMvpView().onError(R.string.long_first_name);
                return;
            }
            if (last_name == null || last_name.trim().isEmpty()) {

                getMvpView().showError(2, getMvpView().getContext().getString(R.string.empty_last_name));
//                getMvpView().onError(R.string.empty_last_name);
                return;
            }
            if (last_name.trim().length() > 15) {

                getMvpView().showError(2, getMvpView().getContext().getString(R.string.long_last_name));
//                getMvpView().onError(R.string.long_last_name);
                return;
            }
            if (email == null || email.isEmpty()) {

                getMvpView().showError(3, getMvpView().getContext().getString(R.string.empty_email));
//                getMvpView().onError(R.string.empty_email);
                return;
            }
            if (!ValidationUtils.isEmailValid(email)) {

                getMvpView().showError(3, getMvpView().getContext().getString(R.string.invalid_email));
//                getMvpView().onError(R.string.invalid_email);
                return;
            }
            if (confirmEmail == null || confirmEmail.isEmpty()) {

                getMvpView().showError(4, getMvpView().getContext().getString(R.string.empty_confirm_email));
//                getMvpView().onError(R.string.empty_email);
                return;
            }
            if (!email.equals(confirmEmail)) {

                getMvpView().showError(4, getMvpView().getContext().getString(R.string.not_match_email));
//                getMvpView().onError(R.string.not_match_password);
                return;
            }
            if (countryCode == null || countryCode.isEmpty()) {

                getMvpView().showError(5, getMvpView().getContext().getString(R.string.empty_country_code));
//                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (phone == null || phone.isEmpty()) {

                getMvpView().showError(6, getMvpView().getContext().getString(R.string.empty_phone));
//                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(phone)) {

                getMvpView().showError(6, getMvpView().getContext().getString(R.string.invalid_phone));
//                getMvpView().onError(R.string.invalid_phone);
                return;
            }
            if (password == null || password.isEmpty()) {

                getMvpView().showError(7, getMvpView().getContext().getString(R.string.empty_password));
//                getMvpView().onError(R.string.empty_password);
                return;
            }
            if (password.length() < 6) {

                getMvpView().showError(7, getMvpView().getContext().getString(R.string.minimum_password));
//                getMvpView().onError(R.string.minimum_password);
                return;
            }
            if (!(password.matches(".*[A-Za-z]+.*[0-9]+.*") || password.matches(".*[0-9]+.*[A-Za-z]+.*"))) {

                getMvpView().showError(7, getMvpView().getContext().getString(R.string.invalid_password));
//                getMvpView().onError(R.string.invalid_password);
                return;
            }
            if (confirm_password == null || confirm_password.isEmpty()) {

                getMvpView().showError(8, getMvpView().getContext().getString(R.string.empty_confirm_password));
//                getMvpView().onError(R.string.empty_confirm_password);
                return;
            }
            if (!password.equals(confirm_password)) {

                getMvpView().showError(8, getMvpView().getContext().getString(R.string.not_match_password));
//                getMvpView().onError(R.string.not_match_password);
                return;
            }

            if (getDataManager().getLanguage().equalsIgnoreCase(AppConstants.LANG_EN)) {
                language = AppConstants.LANG_ENG;
            } else {
                language = AppConstants.LANG_AR;
            }

            final CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest(fbId, first_name, last_name, email, password, countryCode, phone, deviceId, deviceType, latitude, longitude, language);

//            getMvpView().verifyOTP(new CustomerSignUpRequest(fbId, first_name, last_name, email, password, deviceId, deviceType, latitude, longitude, language));

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

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), customerSignUpResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (customerSignUpResponse.getResponse().getStatus() == 1) {
                                Log.d("OTP", ">>" + customerSignUpResponse.getResponse().getOtp());
//                                TODO OTP
//                                getMvpView().verifyOTP();
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
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void onFacebookLoginClick(final String fbId, final String first_name, final String last_name, final String email, String deviceId, String deviceType) {

        String language = null;

        if (getDataManager().getLanguage().equalsIgnoreCase(AppConstants.LANG_EN)) {
            language = AppConstants.LANG_ENG;
        } else {
            language = AppConstants.LANG_AR;
        }

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doFacebookLogin(new FacebookLoginRequest(email, fbId, deviceId, deviceType,language))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FacebookLoginResponse>() {
                    @Override
                    public void accept(FacebookLoginResponse facebookLoginResponse) throws Exception {

                        getMvpView().hideLoading();

                        if (facebookLoginResponse.getResponse().getIsDeleted() != null && facebookLoginResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                            Locale locale = new Locale(AppConstants.LANG_EN);
//                            Locale.setDefault(locale);
//
//                            Configuration config = new Configuration();
//                            config.locale = locale;
//
//                            getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                            Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getMvpView().getContext().startActivity(intent);

//                            getDataManager().setLanguage(AppConstants.LANG_EN);

                            setUserAsLoggedOut();

                            Toast.makeText(getMvpView().getContext(), facebookLoginResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                        }

                        if (facebookLoginResponse.getResponse().getStatus() == 0) {
                            getMvpView().onError(facebookLoginResponse.getResponse().getMessage());
                            getMvpView().setFacebookDetails(fbId, first_name, last_name, email);

                        } else {
                            getDataManager().setCurrentUserLoggedIn(true);
                            getDataManager().setCurrentUserId(facebookLoginResponse.getResponse().getUserId());
                            getDataManager().setCurrentUserType(facebookLoginResponse.getResponse().getUserType());
                            getDataManager().setCurrency(facebookLoginResponse.getResponse().getCurrency());
                            if (facebookLoginResponse.getResponse().getUserType().equals(AppConstants.CUSTOMER)) {
                                getDataManager().setCurrentUserName(facebookLoginResponse.getResponse().getFirstName() + " " + facebookLoginResponse.getResponse().getLastName());
                                getDataManager().setCustomerRestaurantId(facebookLoginResponse.getResponse().getRestaurantId());
                            } else {
                                getDataManager().setCurrentUserName(facebookLoginResponse.getResponse().getRestaurantName());
                                getDataManager().setRestaurantLogoURL(facebookLoginResponse.getResponse().getProfileImage());
                                Log.d("getProfileImage", ">>" + getDataManager().getRestaurantLogoURL());
                            }

//                            if (facebookLoginResponse.getResponse().getLanguage().equalsIgnoreCase(AppConstants.LANG_ENG)) {
//                                getDataManager().setLanguage(AppConstants.LANG_EN);
//                            } else {
//                                getDataManager().setLanguage(AppConstants.LANG_AR);
//                            }
//
//                            Locale locale = new Locale(getDataManager().getLanguage());
//                            Locale.setDefault(locale);
//
//                            Configuration config = new Configuration();
//                            config.locale = locale;
//
//                            getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                            if (facebookLoginResponse.getResponse().getIsProfileSet().equals("1")) {
                                getDataManager().setCurrentUserInfoInitialized(true);
                            } else {
                                getDataManager().setCurrentUserInfoInitialized(false);
                            }

                            decideNextActivity();
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

    @Override
    public void setError(int resId) {
        getMvpView().onError(resId);
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

    private void decideNextActivity() {

        Log.d("isCurrentUserLoggedIn", "true");

        if (getDataManager().isCurrentUserInfoInitialized()) {

            Log.d("isCurrentUserInfoInit", "true");

            if (getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER)) {

                Log.d("openCustomerHome", "CUSTOMER");

                getMvpView().openCustomerHomeActivity();

            } else {

                Log.d("RestaurantHome", "Res");

                getMvpView().openRestaurantHomeActivity();
            }

        } else {

            Log.d("isCurrentUserInfoInit", "false");

            if (getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER)) {

                Log.d("openLocationInfo", "CUSTOMER");

                getMvpView().openLocationInfoActivity();

            } else {

                Log.d("SetupRestaurantProfile", "Res");

                getMvpView().openSetupRestaurantProfileActivity();
            }
        }
    }
}