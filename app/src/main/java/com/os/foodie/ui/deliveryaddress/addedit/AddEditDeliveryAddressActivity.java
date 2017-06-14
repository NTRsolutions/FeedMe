package com.os.foodie.ui.deliveryaddress.addedit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressRequest;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.utils.AppConstants;

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
        setContentView(R.layout.layout_add_delivery_address);

        addEditDeliveryAddressMvpPresenter = new AddEditDeliveryAddressPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        addEditDeliveryAddressMvpPresenter.onAttach(AddEditDeliveryAddressActivity.this);

        initView();

        if (getIntent().hasExtra(AppConstants.DELIVERY_ADDRESS)) {

            isEdit = true;

            address = getIntent().getParcelableExtra(AppConstants.DELIVERY_ADDRESS);
            position = getIntent().getIntExtra(AppConstants.POSITION, -1);

            setAddresses(address);
        }
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        switch (v.getId()) {

            case R.id.add_delivery_address_btn_save:

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
//                    updateAddressRequest.setCountry(etCountry.getText().toString().trim());
//                    updateAddressRequest.setState(etState.getText().toString().trim());
                    updateAddressRequest.setUserId(AppController.get(this).getAppDataManager().getCurrentUserId());

                    addEditDeliveryAddressMvpPresenter.updateDeliverAddress(updateAddressRequest);

                } else {

                    AddDeliveryAddressRequest addDeliveryAddressRequest = new AddDeliveryAddressRequest();

                    addDeliveryAddressRequest.setFullName(etFullName.getText().toString().trim());
                    addDeliveryAddressRequest.setMobileNumber(etPhoneNumber.getText().toString().trim());
                    addDeliveryAddressRequest.setPincode(etPinCode.getText().toString().trim());
                    addDeliveryAddressRequest.setFlatNumber(etFlatNo.getText().toString().trim());
                    addDeliveryAddressRequest.setColony(etColony.getText().toString().trim());
                    addDeliveryAddressRequest.setLandmark(etLandmark.getText().toString().trim());
                    addDeliveryAddressRequest.setCity(etCity.getText().toString().trim());
                    addDeliveryAddressRequest.setCountry(etCountry.getText().toString().trim());
                    addDeliveryAddressRequest.setState(etState.getText().toString().trim());
                    addDeliveryAddressRequest.setUserId(AppController.get(this).getAppDataManager().getCurrentUserId());

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
        super.onDestroy();
        addEditDeliveryAddressMvpPresenter.onDetach();
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

        etFullName = (EditText) findViewById(R.id.full_name_et);
        etPhoneNumber = (EditText) findViewById(R.id.phone_number_et);
        etPinCode = (EditText) findViewById(R.id.pin_code_et);
        etFlatNo = (EditText) findViewById(R.id.flat_no_et);
        etColony = (EditText) findViewById(R.id.colony_et);
        etLandmark = (EditText) findViewById(R.id.landmark_et);
        etCity = (EditText) findViewById(R.id.city_et);
        etState = (EditText) findViewById(R.id.state_et);
        etCountry = (EditText) findViewById(R.id.country_et);

        btSave = (RippleAppCompatButton) findViewById(R.id.add_delivery_address_btn_save);

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