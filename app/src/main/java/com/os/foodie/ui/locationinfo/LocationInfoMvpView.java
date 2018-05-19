package com.os.foodie.ui.locationinfo;

import android.location.Address;

import com.os.foodie.data.network.model.citycountrylist.Country;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.data.network.model.locationinfo.get.Response;
import com.os.foodie.ui.base.MvpView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface LocationInfoMvpView extends MvpView {

//    void setCountryAdapter(List<Country> countries);
//
    void setCityAdapter(ArrayList<City> cities);

//    void setCityCountryListAdapter(ArrayList<Country> countries);

    void onUserLocationInfoSaved();

    void setInitilizedLocation(Response response);

    void setAllAddress(ArrayList<Address> addresses);
}