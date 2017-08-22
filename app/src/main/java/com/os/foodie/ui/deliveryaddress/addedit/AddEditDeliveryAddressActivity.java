package com.os.foodie.ui.deliveryaddress.addedit;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressRequest;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.feature.GpsLocation;
import com.os.foodie.feature.callback.GpsLocationCallback;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.disposables.CompositeDisposable;

public class AddEditDeliveryAddressActivity extends BaseActivity implements AddEditDeliveryAddressMvpView, View.OnClickListener, GpsLocationCallback {

    private EditText etFullName, etPhoneNumber, etPinCode, etFlatNo, etColony;
    private EditText etLandmark, etCity, etState, etCountry;
    private RippleAppCompatButton btSave;

    private int position;
    private Address address;
    private boolean isEdit;

    private LatLng latLng;
    private GpsLocation gpsLocation;
    private static final int GPS_REQUEST_CODE = 10;

    private ProgressDialog progressDialog;

    private AddEditDeliveryAddressMvpPresenter<AddEditDeliveryAddressMvpView> addEditDeliveryAddressMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_delivery_address);

        initPresenter();
//        addEditDeliveryAddressMvpPresenter = new AddEditDeliveryAddressPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        addEditDeliveryAddressMvpPresenter.onAttach(AddEditDeliveryAddressActivity.this);

        gpsLocation = new GpsLocation(this, this);

        initView();

        if (getIntent().hasExtra(AppConstants.DELIVERY_ADDRESS)) {

            isEdit = true;

            address = getIntent().getParcelableExtra(AppConstants.DELIVERY_ADDRESS);
            position = getIntent().getIntExtra(AppConstants.POSITION, -1);

            setAddresses(address);
        }

        if (isEdit) {
            getSupportActionBar().setTitle(getString(R.string.edit_delivery_address_activity_title));
        }
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        addEditDeliveryAddressMvpPresenter = new AddEditDeliveryAddressPresenter<>(appDataManager, compositeDisposable);
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        switch (v.getId()) {

            case R.id.activity_add_delivery_address_bt_save:

                if (isEdit) {

                    UpdateAddressRequest updateAddressRequest = new UpdateAddressRequest();

                    updateAddressRequest.setAddressId(address.getId());
                    updateAddressRequest.setUserId(address.getUserId());
                    updateAddressRequest.setFullName(etFullName.getText().toString().trim());
                    updateAddressRequest.setMobileNumber(etPhoneNumber.getText().toString().trim());
                    updateAddressRequest.setPincode(etPinCode.getText().toString().trim());
                    updateAddressRequest.setFlatNumber(etFlatNo.getText().toString().trim());
                    updateAddressRequest.setColony(etColony.getText().toString().trim());
                    updateAddressRequest.setLandmark(etLandmark.getText().toString().trim());
                    updateAddressRequest.setCity(etCity.getText().toString().trim());
                    updateAddressRequest.setCountry(etCountry.getText().toString().trim());
                    updateAddressRequest.setState(etState.getText().toString().trim());
                    updateAddressRequest.setUserId(AppController.get(this).getAppDataManager().getCurrentUserId());

                    addEditDeliveryAddressMvpPresenter.updateDeliverAddress(updateAddressRequest);

                } else {

                    AddDeliveryAddressRequest addDeliveryAddressRequest = new AddDeliveryAddressRequest();

                    addDeliveryAddressRequest.setUserId(AppController.get(this).getAppDataManager().getCurrentUserId());
                    addDeliveryAddressRequest.setFullName(etFullName.getText().toString().trim());
                    addDeliveryAddressRequest.setMobileNumber(etPhoneNumber.getText().toString().trim());
                    addDeliveryAddressRequest.setPincode(etPinCode.getText().toString().trim());
                    addDeliveryAddressRequest.setFlatNumber(etFlatNo.getText().toString().trim());
                    addDeliveryAddressRequest.setColony(etColony.getText().toString().trim());
                    addDeliveryAddressRequest.setLandmark(etLandmark.getText().toString().trim());
                    addDeliveryAddressRequest.setCity(etCity.getText().toString().trim());
                    addDeliveryAddressRequest.setCountry(etCountry.getText().toString().trim());
                    addDeliveryAddressRequest.setState(etState.getText().toString().trim());

                    addEditDeliveryAddressMvpPresenter.addDeliverAddress(addDeliveryAddressRequest);
                }

                break;
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onDestroy() {
        addEditDeliveryAddressMvpPresenter.dispose();
//        addEditDeliveryAddressMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit_delivery_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_close) {
            finish();
        } else if (item.getItemId() == R.id.action_location) {
            checkAndRequestGpsLocation();
        }

        return true;
    }

    private void initView() {

        etFullName = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_full_name);
        etPhoneNumber = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_phone_number);
        etPinCode = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_pin_code);
        etFlatNo = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_flat_no);
        etColony = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_colony);
        etLandmark = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_landmark);
        etCity = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_city);
        etState = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_state);
        etCountry = (EditText) findViewById(R.id.activity_add_edit_delivery_address_et_country);

        btSave = (RippleAppCompatButton) findViewById(R.id.activity_add_delivery_address_bt_save);

        setOnClickListener();
    }

    private void setAddresses(Address address) {

        etFullName.setText(address.getFullName());
        etPhoneNumber.setText(address.getMobileNumber());
        etPinCode.setText(address.getPincode());
        etFlatNo.setText(address.getFlatNumber());
        etColony.setText(address.getColony());
        etLandmark.setText(address.getLandmark());
        etCity.setText(address.getCity());
        etState.setText(address.getState());
        etCountry.setText(address.getCountry());
    }

    private void setOnClickListener() {
        btSave.setOnClickListener(this);
    }

    @Override
    public void onDeliveryAddressAdd(Address address) {

        Intent intent = new Intent();

        intent.putExtra(AppConstants.DELIVERY_ADDRESS, address);

        setResult(1, intent);
        finish();
    }

    @Override
    public void onDeliveryAddressEdit(Address address) {

        Intent intent = new Intent();

        intent.putExtra(AppConstants.POSITION, position);
        intent.putExtra(AppConstants.DELIVERY_ADDRESS, address);

        setResult(2, intent);
        finish();

    }

    @Override
    public void setAllAddress(ArrayList<android.location.Address> addresses) {

        progressDialog.dismiss();
        progressDialog.cancel();

        android.location.Address address = addresses.get(0);
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
//        etCurrentLocation.setText(fullAddress);
//
//        if (!locationInfoMvpPresenter.isLoggedIn()) {
//            locationInfoMvpPresenter.setLatLng(address.getLatitude() + "", address.getLongitude() + "");
//        }

        if (address.getCountryName() != null && !address.getCountryName().isEmpty()) {
            etCountry.setText(address.getCountryName());
        } else {
            etCountry.setText("");
        }

        if (address.getAdminArea() != null && !address.getAdminArea().isEmpty()) {
            etState.setText(address.getAdminArea());
        } else {
            etState.setText("");
        }

        if (address.getPostalCode() != null && !address.getPostalCode().isEmpty()) {
            etPinCode.setText(address.getPostalCode());
        } else {
            etPinCode.setText("");
        }

        if (address.getFeatureName() != null && !address.getFeatureName().isEmpty()) {
            etFlatNo.setText(address.getFeatureName());
        } else {
            etFlatNo.setText("");
        }

        if (address.getThoroughfare() != null && !address.getThoroughfare().isEmpty()) {
            etColony.setText(address.getThoroughfare());
        } else {
            etColony.setText("");
        }

        if (address.getSubAdminArea() != null && !address.getSubAdminArea().isEmpty()) {

            etCity.setText(address.getSubAdminArea());

        } else if (address.getLocality() != null && !address.getLocality().isEmpty()) {

            etCity.setText(address.getLocality());

        } else {

            etCity.setText("");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults != null && grantResults[0] == PermissionChecker.PERMISSION_GRANTED && requestCode == GPS_REQUEST_CODE) {
            requestGpsLocation();
        }
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
        addEditDeliveryAddressMvpPresenter.getGeocoderLocationAddress(this, latLng);
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }
}