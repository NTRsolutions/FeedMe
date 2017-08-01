package com.os.foodie.ui.main.customer;

import com.os.foodie.ui.base.MvpPresenter;

public interface CustomerMainMvpPresenter<V extends CustomerMainMvpView> extends MvpPresenter<V> {

    String getCurrentUserName();

    boolean isCurrentUserLoggedIn();

    void changeDefaultLanguage();

    void logout();

    void dispose();
}