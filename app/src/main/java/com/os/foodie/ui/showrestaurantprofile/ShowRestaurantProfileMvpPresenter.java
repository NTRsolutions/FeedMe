package com.os.foodie.ui.showrestaurantprofile;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileRequest;
import com.os.foodie.data.prefs.PreferencesHelper;
import com.os.foodie.ui.base.MvpPresenter;

import java.io.File;
import java.util.HashMap;

public interface ShowRestaurantProfileMvpPresenter<V extends ShowRestaurantProfileMvpView> extends MvpPresenter<V> {

    void getRestaurantProfile(String restaurantId);

    void dispose();
}