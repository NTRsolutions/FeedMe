package com.os.foodie.ui.locationinfo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.feature.callback.GpsLocationCallback;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.feature.GpsLocation;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;

public class LocationInfoActivity extends BaseActivity implements LocationInfoMvpView, View.OnClickListener, GpsLocationCallback {

//    private Spinner spinnerCountry, spinnerCity;

    private EditText etCountry, etCity;
    private EditText etCurrentLocation;
    private Button btSubmit;

    //    private ArrayList<String> countries;
//    private ArrayList<String> cities;

    private LatLng latLng;
    private GpsLocation gpsLocation;
    private LocationInfoMvpPresenter<LocationInfoMvpView> locationInfoMvpPresenter;

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int GPS_REQUEST_CODE = 10;

//    private Consumer consumer;
//    private Observable observable;
//    private Disposable disposable;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);

        setUp();

        initPresenter();
        locationInfoMvpPresenter.onAttach(LocationInfoActivity.this);
        locationInfoMvpPresenter.setCurrentUserInfoInitialized(false);

        gpsLocation = new GpsLocation(this, this);

        etCountry = (EditText) findViewById(R.id.activity_location_info_et_country);
        etCity = (EditText) findViewById(R.id.activity_location_info_et_city);

        etCurrentLocation = (EditText) findViewById(R.id.activity_location_info_et_current_location);
        btSubmit = (Button) findViewById(R.id.activity_location_info_bt_submit);

//        locationInfoMvpPresenter.getCountryList();

        etCurrentLocation.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (etCurrentLocation.getText().toString().isEmpty()) {
            checkAndRequestGpsLocation();
        }
    }

    public void initPresenter() {

        locationInfoMvpPresenter = new LocationInfoPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == etCurrentLocation.getId()) {

            openPlaceAutocomplete();

        } else if (v.getId() == btSubmit.getId()) {

//            String country = ((Country) spinnerCountry.getSelectedItem()).getName();
//            String city = ((City) spinnerCity.getSelectedItem()).getName();
            String country = etCountry.getText().toString();
            String city = etCity.getText().toString();
            String address = etCurrentLocation.getText().toString();

            locationInfoMvpPresenter.setUserLocationInfo(latLng, country, city, address);
        }
    }

    @Override
    protected void setUp() {

//        consumer = new Consumer<Location>() {
//            @Override
//            public void accept(Location location) throws Exception {
//
//                Log.d("country", ">>accept");
//
//                ArrayList<String> addressArrayList = new ArrayList<>();
//                Geocoder geocoder = new Geocoder(LocationInfoActivity.this, Locale.getDefault());
//                String fullAddress = "";
//
//                try {
//
//                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//                    if (addressList != null && addressList.size() > 0) {
//
//                        Address address = addressList.get(0);
//                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                            if (i <= 1)
//                                fullAddress = fullAddress + address.getAddressLine(i) + " ";
//                            else
//                                break;
//                        }
//
////                        String postalCode = address.getPostalCode();
////                        String city = address.getLocality();
////                        String state = address.getAdminArea();
////                        String country = address.getCountryName();
////
////                        Log.d("postalCode", ">>" + postalCode);
////                        Log.d("city", ">>" + city);
////                        Log.d("state", ">>" + state);
////                        Log.d("country", ">>" + country);
//                        Log.d("address_str", ">>" + fullAddress);
//                        etCurrentLocation.setText(fullAddress);
//
//                        progressDialog.dismiss();
////                        etCurrentLocation.setSelection(etCurrentLocation.getText().length());
////                        etCurrentLocation.clearFocus();
//                    }
//
//                } catch (IOException e) {
//                    Log.d("IOException", ">>" + e.getMessage());
//                }
//
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
//            }
//        };
    }

    public void checkAndRequestGpsLocation() {

        if (gpsLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {

                requestGpsLocation();

            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
            }

        } else {
            showGpsRequest();
        }
    }

    public void requestGpsLocation() {
        progressDialog = CommonUtils.showLoadingDialog(this, "Getting Your Address");
        gpsLocation.requestGpsLocation();
    }

    @Override
    protected void onDestroy() {
        locationInfoMvpPresenter.onDetach();
        super.onDestroy();
    }

//    @Override
//    public void setCountryAdapter(final List<Country> countries) {
//
//        spinnerCountry = (Spinner) findViewById(R.id.activity_location_info_spinner_country);
//
//        CountrySpinnerAdapter countrySpinnerAdapter = new CountrySpinnerAdapter(LocationInfoActivity.this, countries);
//
//        spinnerCountry.setAdapter(countrySpinnerAdapter);
//        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
////                Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
//
//                locationInfoMvpPresenter.getCityList(countries.get(position).getCountryId());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
//
//    @Override
//    public void setCityAdapter(List<City> cities) {
//
//        spinnerCity = (Spinner) findViewById(R.id.activity_location_info_spinner_city);
//
//        CitySpinnerAdapter citySpinnerAdapter = new CitySpinnerAdapter(LocationInfoActivity.this, cities);
//
//        spinnerCity.setAdapter(citySpinnerAdapter);
//        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
////                Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }

    public void openPlaceAutocomplete() {

        if (NetworkUtils.isNetworkConnected(LocationInfoActivity.this)) {

            try {
//                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
//                        .build();

                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                        .setFilter(typeFilter)
                        .build(this);

                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

            } catch (GooglePlayServicesRepairableException e) {

                locationInfoMvpPresenter.setError(getResources().getString(R.string.google_play_service_reinstall_error));
                Log.d("RepairableException", ">>" + e.getMessage().toString());

            } catch (GooglePlayServicesNotAvailableException e) {
                locationInfoMvpPresenter.setError(getResources().getString(R.string.google_play_service_not_avail_error));
                Log.d("NotAvailableException", ">>" + e.getMessage().toString());
            }
        } else {
            locationInfoMvpPresenter.setError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("onActivityResult", "getName: " + place.getName());
                Log.i("onActivityResult", "getAddress: " + place.getAddress());
                Log.i("onActivityResult", "getLocale: " + place.getLocale());
                Log.i("onActivityResult", "getLatLng: " + place.getLatLng().toString());

                etCurrentLocation.setText(place.getAddress());
//                etCurrentLocation.setSelection(etCurrentLocation.getText().length());
//                etCurrentLocation.clearFocus();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("onActivityResult", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i("onActivityResult", ">>RESULT_CANCELED");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GPS_REQUEST_CODE) {
            requestGpsLocation();
        }
    }

    public void showGpsRequest() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);
        mAlertDialog.setTitle("Location Service Disabled")
                .setMessage("Please enable location service")
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("Now Observe Location", ">>" + location.toString());

        progressDialog.dismiss();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        locationInfoMvpPresenter.getGeocoderLocationAddress(this, latLng);
//        observable = Observable.just(location);
//        disposable = observable.subscribe(consumer);
//        disposable.dispose();
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
    }

    @Override
    public void onUserLocationInfoSaved() {

        Intent intent = new Intent(LocationInfoActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void setAllAddress(ArrayList<Address> addresses) {

        progressDialog.dismiss();

        Address address = addresses.get(0);
        String fullAddress = "";

        for (int i = 0; i < 2; i++) {

            Log.d("getAddressLine" + i, ">>" + address.getAddressLine(i));

            if (i == 0)
                fullAddress = address.getAddressLine(i);
            else if (i < address.getMaxAddressLineIndex())
                fullAddress += ", " + address.getAddressLine(i);
            else
                break;
        }

        latLng = new LatLng(address.getLatitude(), address.getLongitude());
        etCurrentLocation.setText(fullAddress);

        if (address.getCountryName() != null && !address.getCountryName().isEmpty()) {
            etCountry.setEnabled(false);
            etCountry.setText(address.getCountryName());
        } else {
            etCountry.setEnabled(true);
            etCountry.setText("");
        }

        if (address.getSubAdminArea() != null && !address.getSubAdminArea().isEmpty()) {
            etCity.setEnabled(false);
            etCity.setText(address.getSubAdminArea());
        } else {
            etCity.setEnabled(true);
            etCity.setText("");
        }
    }
}