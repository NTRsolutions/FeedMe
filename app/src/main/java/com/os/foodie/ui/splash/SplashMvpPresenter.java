package com.os.foodie.ui.splash;

import android.content.Context;

import com.os.foodie.ui.base.MvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void waitAndGo(Context context);

    void dispose();
}