package com.os.foodie.ui.locationinfo;

import android.location.Address;

import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.data.network.model.locationinfo.country.Country;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface LocationInfoMvpView extends MvpView {

//    void setCountryAdapter(List<Country> countries);
//
//    void setCityAdapter(List<City> cities);

    void onUserLocationInfoSaved();

    void setAllAddress(ArrayList<Address> addresses);
}