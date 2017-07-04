package com.os.foodie.ui.setting;

import android.support.annotation.StringRes;

import com.os.foodie.ui.base.MvpPresenter;

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {

    void dispose();

    void SetNotificationStaus();

    void onError(@StringRes int resId);
}