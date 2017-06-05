package com.os.foodie.feature;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.os.foodie.feature.callback.GpsLocationCallback;

public class GpsLocation {

    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GpsLocationCallback gpsLocationCallback;

    public GpsLocation(Context context, GpsLocationCallback gpsLocationCallback) {
        this.context = context;
        this.gpsLocationCallback = gpsLocationCallback;
        initializeLocationManager();
        initializeLocationListener();
    }

    public void initializeLocationListener() {
        Log.d("initializeLocListener", ">>initializeLocListener");

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                String result = "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude();
                Log.d("locationListener", ">>" + result);

                gpsLocationCallback.onLocationChanged(location);
                locationManager.removeUpdates(locationListener);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("locationListener", ">>onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("locationListener", ">>onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("locationListener", ">>onProviderDisabled");
//                gpsLocationCallback.onFailed();
            }
        };
    }

    public void initializeLocationManager() {
        Log.d("initializeLocManager", ">>initializeLocManager");
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    public boolean isProviderEnabled(String provider) {
        Log.d("isProviderEnabled", ">>isProviderEnabled");

        if (locationManager != null) {
            if (locationManager.isProviderEnabled(provider)) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("MissingPermission")
    public void requestGpsLocation() {
        Log.d("requestGpsLocation", ">>requestGpsLocation");

        if (locationManager != null) {
            if (isProviderEnabled(LocationManager.GPS_PROVIDER) /*&& isProviderEnabled(LocationManager.NETWORK_PROVIDER)*/) {
                Log.d("requestGpsLocation", ">>ProviderEnabled");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                gpsLocationCallback.onFailed();
            }
        } else {
            gpsLocationCallback.onFailed();
        }
    }
}