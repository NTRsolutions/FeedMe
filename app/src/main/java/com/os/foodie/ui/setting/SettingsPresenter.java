package com.os.foodie.ui.setting;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.changelanguage.ChangeLanguageRequest;
import com.os.foodie.data.network.model.changelanguage.ChangeLanguageResponse;
import com.os.foodie.data.network.model.notification.SetNotificationResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements SettingsMvpPresenter<V> {

    public SettingsPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }

    @Override
    public void onError(@StringRes int resId) {
        getMvpView().onError(resId);
    }

    @Override
    public boolean isFacebook() {
        return getDataManager().isFacebook();
    }

    @Override
    public void changeLanguage(String languageCode) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            Log.d("getLanguage", ">>" + getDataManager().getLanguage());

            if (languageCode.equalsIgnoreCase(AppConstants.LANG_ENG) && getDataManager().getLanguage().equalsIgnoreCase(AppConstants.LANG_EN)) {

                getMvpView().onError(getMvpView().getContext().getString(R.string.language_en) + " " + getMvpView().getContext().getString(R.string.already_selected_language));
                return;

            } else if (getDataManager().getLanguage().equalsIgnoreCase(languageCode)) {

                getMvpView().onError(getMvpView().getContext().getString(R.string.language_ar) + " " + getMvpView().getContext().getString(R.string.already_selected_language));
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .changeLanguage(new ChangeLanguageRequest(getDataManager().getCurrentUserId(), languageCode))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ChangeLanguageResponse>() {
                        @Override
                        public void accept(ChangeLanguageResponse changeLanguageResponse) throws Exception {

                            Log.d("setNotificationStatus", ">>Response");

                            getMvpView().hideLoading();

                            if (changeLanguageResponse.getResponse().getIsDeleted() != null && changeLanguageResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), changeLanguageResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (changeLanguageResponse.getResponse().getStatus() == 1) {

                                if (getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.RESTAURANT)) {
                                    getDataManager().setCurrentUserName(changeLanguageResponse.getResponse().getName());
                                }

                                if (changeLanguageResponse.getResponse().getLanguage().equalsIgnoreCase(AppConstants.LANG_ENG)) {
                                    getDataManager().setLanguage(AppConstants.LANG_EN);
                                    getMvpView().changeLanguage(AppConstants.LANG_EN, getMvpView().getContext().getString(R.string.language_en));

                                } else {
                                    getDataManager().setLanguage(AppConstants.LANG_AR);
                                    getMvpView().changeLanguage(AppConstants.LANG_AR, getMvpView().getContext().getString(R.string.language_ar));
                                }

                            } else {
                                getMvpView().onError(changeLanguageResponse.getResponse().getMessage());
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
    public boolean isCustomer() {
        return getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER);
    }

    @Override
    public void setNotificationStatus() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            Log.d("setNotificationStatus", ">>Called");

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .setNotification(getDataManager().getCurrentUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SetNotificationResponse>() {
                        @Override
                        public void accept(SetNotificationResponse setNotificationResponse) throws Exception {

                            Log.d("setNotificationStatus", ">>Response");

                            getMvpView().hideLoading();

                            if (setNotificationResponse.getResponse().getIsDeleted() != null && setNotificationResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), setNotificationResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (setNotificationResponse.getResponse().getStatus() == 1) {

                                Log.d("getIsNotification", ">>" + setNotificationResponse.getResponse().getIsNotification());

                                getDataManager().setNotificationStatus(setNotificationResponse.getResponse().getIsNotification());
                                getMvpView().getNotificationStatus(setNotificationResponse.getResponse().getIsNotification());

                            } else {
                                getMvpView().onError(setNotificationResponse.getResponse().getMessage());
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
    public String getNotificationStatus() {
        return getDataManager().getNotificationStatus();
    }
}