package com.os.foodie.data.network.model.deliveryaddress.zip;

import com.os.foodie.data.network.model.citycountrylist.CityCountryListResponse;
import com.os.foodie.data.network.model.locationinfo.get.GetUserLocationDetailResponse;

public class LocationAndCityCountryListResponse {

    private CityCountryListResponse cityCountryListResponse;
    private GetUserLocationDetailResponse userLocationDetailResponse;

    public LocationAndCityCountryListResponse() {
    }

    public LocationAndCityCountryListResponse(CityCountryListResponse cityCountryListResponse, GetUserLocationDetailResponse userLocationDetailResponse) {
        this.cityCountryListResponse = cityCountryListResponse;
        this.userLocationDetailResponse = userLocationDetailResponse;
    }

    public CityCountryListResponse getCityCountryListResponse() {
        return cityCountryListResponse;
    }

    public void setCityCountryListResponse(CityCountryListResponse cityCountryListResponse) {
        this.cityCountryListResponse = cityCountryListResponse;
    }

    public GetUserLocationDetailResponse getUserLocationDetailResponse() {
        return userLocationDetailResponse;
    }

    public void setUserLocationDetailResponse(GetUserLocationDetailResponse userLocationDetailResponse) {
        this.userLocationDetailResponse = userLocationDetailResponse;
    }
}