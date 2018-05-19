package com.os.foodie.ui.setupprofile.restaurant;

import android.location.Address;

import com.os.foodie.data.network.model.citycountrylist.Country;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface SetupRestaurantProfileMvpView extends MvpView {

    void onCuisineTypeListReceive(ArrayList<CuisineType> cuisineTypes);

    void onRestaurantProfileSaved();

    void setAllAddress(ArrayList<Address> addresses);

    void setCityCountryListAdapter(ArrayList<Country> countries);

    void openCitySelector(ArrayList<City> cities);
    void onCityListReceived(ArrayList<City> cities);
}