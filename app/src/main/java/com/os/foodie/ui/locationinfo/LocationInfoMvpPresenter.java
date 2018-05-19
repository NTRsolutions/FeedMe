package com.os.foodie.ui.locationinfo;

import android.content.Context;
import android.support.annotation.StringRes;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.ui.base.MvpPresenter;

public interface LocationInfoMvpPresenter<V extends LocationInfoMvpView> extends MvpPresenter<V> {

    void setCurrentUserInfoInitialized(boolean initialized);

    boolean getCurrentUserInfoInitialized();

    boolean isLoggedIn();

    void setLatLng(String latitude, String longitude);

    void setCityId(String cityName, City city);

//    void getCityCountryList();

    void setError(@StringRes int resId);

    void setError(String message);

    void getCountryList();

    void getCityList();

    void setUserLocationInfo(LatLng latLng, String country, String city, String address, City cityObj);

    void getGeocoderLocationAddress(Context context, LatLng latLng);

    void getUserLocationDetail();

    void dispose();
}