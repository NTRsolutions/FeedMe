package com.os.foodie.ui.deliveryaddress.addedit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressRequest;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.home.customer.CustomerHomePresenter;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class AddEditDeliveryAddressActivity extends BaseActivity implements AddEditDeliveryAddressMvpView, View.OnClickListener {

    private EditText etFullName, etPhoneNumber, etPinCode, etFlatNo, etColony;
    private EditText etLandmark, etCity, etState, etCountry;
    private RippleAppCompatButton btSave;

    private int position;
    private Address address;
    private boolean isEdit;

    private AddEditDeliveryAddressMvpPresenter<AddEditDeliveryAddressMvpView> addEditDeliveryAddressMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_delivery_address);

        initPresenter();
//        addEditDeliveryAddressMvpPresenter = new AddEditDeliveryAddressPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        addEditDeliveryAddressMvpPresenter.onAttach(AddEditDeliveryAddressActivity.this);

        initView();

        if (getIntent().hasExtra(AppConstants.DELIVERY_ADDRESS)) {

            isEdit = true;

            address = getIntent().getParcelableExtra(AppConstants.DELIVERY_ADDRESS);
            position = getIntent().getIntExtra(AppConstants.POSITION, -1);

            setAddresses(address);
        }

        if(isEdit){
            getSupportActionBar().setTitle(getString(R.string.edit_delivery_address_activity_title));
        }
    }

    public void initPresenter(){

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
        getMenuInflater().inflate(R.menu.menu_with_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_close) {
            finish();
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
}