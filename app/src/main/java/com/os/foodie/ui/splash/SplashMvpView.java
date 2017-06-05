package com.os.foodie.ui.splash;

import com.os.foodie.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void openLocationInfoActivity();

    void openSetupRestaurantProfileActivity();

    void openCustomerHomeActivity();

    void openRestaurantHomeActivity();

    void openWelcomeActivity();

    void startSyncService();
}