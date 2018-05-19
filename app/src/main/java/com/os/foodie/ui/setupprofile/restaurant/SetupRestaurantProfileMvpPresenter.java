package com.os.foodie.ui.setupprofile.restaurant;

import android.content.Context;
import android.location.Location;
import android.support.annotation.StringRes;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileRequest;
import com.os.foodie.data.prefs.PreferencesHelper;
import com.os.foodie.ui.base.MvpPresenter;

import java.io.File;
import java.util.HashMap;

public interface SetupRestaurantProfileMvpPresenter<V extends SetupRestaurantProfileMvpView> extends MvpPresenter<V> {

    void setCurrentUserInfoInitialized(boolean initialized);

    void onCuisineTypeClick();

    void saveRestaurantProfile(SetupRestaurantProfileRequest restaurantProfileRequest, HashMap<String, File> fileMap, boolean tag, City city);

    void getGeocoderLocationAddress(Context context, LatLng latLng);

    PreferencesHelper getPreferencesHelper();

    void setTime(EditText etTimeToSet, EditText etTimeToCompare, boolean isOpeningTime);

    void setError(@StringRes int resId);

    void setError(String message);

    void dismissDialog();

    boolean isCurrentUserInfoInitialized();

    void getCityCountryList();

    void deleteRestaurantImage(String imageId);

    void getCityList();

    void dispose();
}