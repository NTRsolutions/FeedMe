package com.os.foodie.ui.setting;

import android.support.annotation.StringRes;

import com.os.foodie.ui.base.MvpPresenter;

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {

    void dispose();

    void setNotificationStatus();

    String getNotificationStatus();

    void onError(@StringRes int resId);

    void setLanguage(String languageCode);

    boolean isCustomer();
}