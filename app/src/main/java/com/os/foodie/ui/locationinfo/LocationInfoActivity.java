package com.os.foodie.ui.locationinfo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.citycountrylist.Country;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.data.network.model.locationinfo.get.Response;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.feature.callback.GpsLocationCallback;
import com.os.foodie.ui.adapter.autocomplete.CityCountryListAdapter;
import com.os.foodie.ui.adapter.autocomplete.CityListAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.feature.GpsLocation;
import com.os.foodie.ui.dialogfragment.city.CityListDialogFragment;
import com.os.foodie.ui.dialogfragment.city.CityOnClickListener;
import com.os.foodie.ui.dialogfragment.cuisine.list.CuisineTypeDialogFragment;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class LocationInfoActivity extends BaseActivity implements LocationInfoMvpView, View.OnClickListener, GpsLocationCallback, CityOnClickListener {

//    private Spinner spinnerCountry, spinnerCity;

    private ImageView ivStep;
    //    private EditText etCountry/*, etCity*/;
    private EditText etCurrentLocation;
    private EditText etCity;
    //    private ImageView ivLocation;
    private Button btSubmit;

    private CityListDialogFragment cityListDialogFragment;

//    private ArrayList<Country> countries;

    //    private ArrayList<String> countries;
    private ArrayList<City> cities;

    private LatLng latLng;
    private GpsLocation gpsLocation;
    private LocationInfoMvpPresenter<LocationInfoMvpView> locationInfoMvpPresenter;

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int GPS_REQUEST_CODE = 10;

    private ProgressDialog progressDialog;

    private City city;

//    int selectedCityPosition = -1;
//    int selectedCountryPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);

        getSupportActionBar().setTitle(getString(R.string.location_info_activity_title));

        initPresenter();
        locationInfoMvpPresenter.onAttach(LocationInfoActivity.this);
//        locationInfoMvpPresenter.setCurrentUserInfoInitialized(false);

        city = new City();
        gpsLocation = new GpsLocation(this, this);
//        countries = new ArrayList<Country>();
        cities = new ArrayList<City>();

        ivStep = (ImageView) findViewById(R.id.activity_location_info_iv_step);

        etCity = (EditText) findViewById(R.id.activity_location_info_actv_city);

//        etCountry = (EditText) findViewById(R.id.activity_location_info_et_country);

//        etCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                selectedCityPosition = position;
//                city = (City) parent.getItemAtPosition(position);
//            }
//        });

//        etCountry.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                setCities(editable.toString());
//
////                for (int i = 0; i < countries.size(); i++) {
////
////                    if (countries.get(i).getName().equalsIgnoreCase(editable.toString())) {
////
////                        ArrayList<City> cities = (ArrayList<City>) countries.get(i).getCity();
////                        String[] cityList = new String[cities.size()];
////
////                        for (int j = 0; j < cities.size(); j++) {
////
////                            cityList[j] = new String(cities.get(j).getCityName());
////                        }
////
//////                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, arr);
////                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocationInfoActivity.this, android.R.layout.select_dialog_item, (String[]) cities.toArray());
////
////                        actvCity.setThreshold(2);
////                        actvCity.setAdapter(adapter);
////                        break;
////                    }
////                }
//            }
//        });

        etCurrentLocation = (EditText) findViewById(R.id.activity_location_info_et_current_location);

//        ivLocation = (ImageView) findViewById(R.id.activity_location_info_iv_location);
//        ivLocation.bringToFront();
//        ivLocation.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        btSubmit = (Button) findViewById(R.id.activity_location_info_bt_submit);

//        locationInfoMvpPresenter.getCountryList();

//        locationInfoMvpPresenter.getCityCountryList();
        locationInfoMvpPresenter.getCityList();
        setUp();

//        ivLocation.setOnClickListener(this);
        etCity.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!locationInfoMvpPresenter.getCurrentUserInfoInitialized() && etCurrentLocation.getText().toString().isEmpty()) {
            checkAndRequestGpsLocation();
        }
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        locationInfoMvpPresenter = new LocationInfoPresenter(appDataManager, compositeDisposable);
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

        hideKeyboard();

//        if (v.getId() == ivLocation.getId()) {
        if (v.getId() == etCity.getId()) {

//            openPlaceAutocomplete();

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.CITY_LIST, cities);
            bundle.putParcelable(AppConstants.CITY_LISTENER, this);

            cityListDialogFragment = new CityListDialogFragment();
            cityListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
            cityListDialogFragment.setArguments(bundle);
            cityListDialogFragment.show(getSupportFragmentManager(), "CityListDialogFragment");

        } else if (v.getId() == btSubmit.getId()) {

            if (locationInfoMvpPresenter.isLoggedIn()) {

//                String country = etCountry.getText().toString();
//                String city = actvCity.getText().toString();
//                String cityId = countries.get(selectedCountryPosition).getCity().get(selectedCityPosition).getCityId();
                String cityId = city.getCityId();
                String address = etCurrentLocation.getText().toString();

                if (!city.getName().equalsIgnoreCase(etCity.getText().toString())) {
//                    city = new City();

                    locationInfoMvpPresenter.setUserLocationInfo(latLng, "", null, address, null);
                } else {

                    locationInfoMvpPresenter.setUserLocationInfo(latLng, "", cityId, address, city);
                }

            } else {

                if (city.getName() == null || !city.getName().equalsIgnoreCase(etCity.getText().toString())) {
//                    city = new City();

                    locationInfoMvpPresenter.setCityId(null, null);
                } else {

                    locationInfoMvpPresenter.setCityId(city.getCityId(), city);
//                locationInfoMvpPresenter.setCityName(actvCity.getText().toString(), selectedCityPosition);
                }
            }
        }
    }

    @Override
    protected void setUp() {

        if (locationInfoMvpPresenter.isLoggedIn() && !locationInfoMvpPresenter.getCurrentUserInfoInitialized()) {
            ivStep.setVisibility(View.VISIBLE);
        }

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
        if (progressDialog == null)
            progressDialog = CommonUtils.showLoadingDialog(this, getString(R.string.progress_dialog_tv_message_text_address_fetch));
        else {
            if (!progressDialog.isShowing())
                progressDialog = CommonUtils.showLoadingDialog(this, getString(R.string.progress_dialog_tv_message_text_address_fetch));
        }
        gpsLocation.requestGpsLocation();
    }

    @Override
    protected void onDestroy() {
        locationInfoMvpPresenter.dispose();
//        locationInfoMvpPresenter.onDetach();
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

                latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

                locationInfoMvpPresenter.getGeocoderLocationAddress(this, place.getLatLng());
//                etCurrentLocation.setText(place.getAddress());

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

        if (grantResults != null && grantResults[0] == PermissionChecker.PERMISSION_GRANTED && requestCode == GPS_REQUEST_CODE) {
            requestGpsLocation();
        }
    }

    public void showGpsRequest() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);
        mAlertDialog.setTitle(R.string.alert_dialog_title_location_disabled)
                .setMessage(R.string.alert_dialog_text_location_disabled)
                .setPositiveButton(R.string.alert_dialog_text_location_disabled_enable, new DialogInterface.OnClickListener() {
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
        progressDialog.cancel();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        locationInfoMvpPresenter.getGeocoderLocationAddress(this, latLng);
//        observable = Observable.just(location);
//        disposable = observable.subscribe(consumer);
//        disposable.dispose();
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }


    @Override
    public void setCityAdapter(ArrayList<City> cities) {

        this.cities = cities;

        if (locationInfoMvpPresenter.getCurrentUserInfoInitialized()) {

            locationInfoMvpPresenter.getUserLocationDetail();
        }

//        CityListAdapter adapter = new CityListAdapter(LocationInfoActivity.this, cities);
//
//        actvCity.setThreshold(0);
//        actvCity.setAdapter(adapter);
    }

//    @Override
//    public void setCityCountryListAdapter(ArrayList<Country> countries) {
//        this.countries = countries;
//
//        if (locationInfoMvpPresenter.getCurrentUserInfoInitialized()) {
//
//            locationInfoMvpPresenter.getUserLocationDetail();
//        }
//
//        setCities(etCountry.getText().toString());
//    }

    @Override
    public void onUserLocationInfoSaved() {

        Intent intent = new Intent(LocationInfoActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void setInitilizedLocation(Response response) {

//        ArrayList<City> cities = (ArrayList<City>) countries.get(selectedCountryPosition).getCity();
//
//        for (int i = 0; i < countries.size(); i++) {
//
//            boolean flag = false;
//
//            for (int j = 0; j < countries.get(i).getCity().size(); j++) {
//
//                if (countries.get(i).getCity().get(j).getCityId().equals(response.getCityId())) {
//
//                    actvCity.setText(countries.get(i).getCity().get(j).getCityName());
//
//                    city = countries.get(i).getCity().get(j);
//
//                    flag = true;
//                    break;
//                }
//            }
//
//            if (flag) {
//                break;
//            }
//        }
//
//        etCountry.setText(response.getCountry());

        for (int j = 0; j < cities.size(); j++) {

            if (cities.get(j).getCityId().equals(response.getCityId())) {

                etCity.setText(cities.get(j).getName());

                city = cities.get(j);

                break;
            }
        }

        etCurrentLocation.setText(response.getAddress());

        latLng = new LatLng(Double.parseDouble(response.getLatitude()), Double.parseDouble(response.getLongitude()));
    }

    @Override
    public void setAllAddress(ArrayList<Address> addresses) {

        progressDialog.dismiss();
        progressDialog.cancel();

        Address address = addresses.get(0);
        String fullAddress = "";

        Log.d("addresses", ">>" + Arrays.toString(addresses.toArray()));
        Log.d("addresses", ">>" + Arrays.toString(addresses.toArray()));

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

        if (!locationInfoMvpPresenter.isLoggedIn()) {
            locationInfoMvpPresenter.setLatLng(address.getLatitude() + "", address.getLongitude() + "");
        }

//        if (address.getCountryName() != null && !address.getCountryName().isEmpty()) {
////            etCountry.setEnabled(false);
//            etCountry.setText(address.getCountryName());
//        } else {
////            etCountry.setEnabled(true);
//            etCountry.setText("");
//        }

   /*     if (address.getSubAdminArea() != null && !address.getSubAdminArea().isEmpty()) {

//            etCity.setEnabled(false);
            actvCity.setText(address.getSubAdminArea());

        } else if (address.getLocality() != null && !address.getLocality().isEmpty()) {

//            etCity.setEnabled(false);
            actvCity.setText(address.getLocality());

        } else {

//            etCity.setEnabled(true);
            actvCity.setText("");
        }*/

//        setCities(etCountry.getText().toString());
    }

    @Override
    public void onClick(City city) {
        this.city = city;
        etCity.setText(city.getName());
        cityListDialogFragment.dismiss();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public final static Creator<LocationInfoActivity> CREATOR = new Creator<LocationInfoActivity>() {

        @SuppressWarnings({"unchecked"})
        @Override
        public LocationInfoActivity createFromParcel(Parcel source) {
            return new LocationInfoActivity();
        }

        @Override
        public LocationInfoActivity[] newArray(int size) {
            return new LocationInfoActivity[size];
        }
    };

//    public void setCities(String editable) {
//
//        for (int i = 0; i < countries.size(); i++) {
//
//            if (countries.get(i).getName() != null && !countries.get(i).getName().isEmpty() && countries.get(i).getName().equalsIgnoreCase(editable)) {
//
//                ArrayList<City> cities = (ArrayList<City>) countries.get(i).getCity();
//
//                CityCountryListAdapter adapter = new CityCountryListAdapter(LocationInfoActivity.this, cities);
//
//                actvCity.setThreshold(2);
//                actvCity.setAdapter(adapter);
//
////                actvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
////                    }
////                });
//
////                selectedCountryPosition = i;
//
//                break;
//            }
//        }
//    }
}