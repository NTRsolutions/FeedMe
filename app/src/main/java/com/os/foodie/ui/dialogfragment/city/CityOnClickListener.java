package com.os.foodie.ui.dialogfragment.city;

import android.os.Parcelable;

import com.os.foodie.data.network.model.locationinfo.city.City;

public interface CityOnClickListener extends Parcelable {

    void onClick(City city);
}