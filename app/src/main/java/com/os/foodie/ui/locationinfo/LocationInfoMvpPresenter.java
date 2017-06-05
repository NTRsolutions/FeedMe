package com.os.foodie.ui.locationinfo;

import android.content.Context;
import android.support.annotation.StringRes;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.ui.base.MvpPresenter;

public interface LocationInfoMvpPresenter<V extends LocationInfoMvpView> extends MvpPresenter<V> {

    void setCurrentUserInfoInitialized(boolean initialized);

    void setError(@StringRes int resId);

    void setError(String message);

    void getCountryList();

    void getCityList(String countryId);

    void setUserLocationInfo(LatLng latLng, String country, String city, String address);

    void getGeocoderLocationAddress(Context context, LatLng latLng);
}