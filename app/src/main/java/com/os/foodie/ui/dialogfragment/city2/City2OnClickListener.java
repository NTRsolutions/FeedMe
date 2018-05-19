package com.os.foodie.ui.dialogfragment.city2;

import android.os.Parcelable;

import com.os.foodie.data.network.model.locationinfo.city.City;

import java.util.ArrayList;

public interface City2OnClickListener extends Parcelable {

    void onClick(ArrayList<City> citiesChecked);
}