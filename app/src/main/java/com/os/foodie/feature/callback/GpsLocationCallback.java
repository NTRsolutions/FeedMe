package com.os.foodie.feature.callback;

import android.location.Location;

public interface GpsLocationCallback {

    void onLocationChanged(Location location);

    void onFailed();
}