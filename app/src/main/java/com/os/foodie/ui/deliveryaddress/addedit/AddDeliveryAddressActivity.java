package com.os.foodie.ui.deliveryaddress.addedit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;

public class AddDeliveryAddressActivity extends BaseActivity implements AddDeliveryAddressMvpView, View.OnClickListener {


    private AddDeliveryAddressMvpPresenter<AddDeliveryAddressMvpView> addressMvpViewAddDeliveryAddressMvpPresenter;
    private EditText phoneNumberEt;
    private EditText pinCodeEt;
    private EditText flatNoEt;
    private EditText colonyEt;
    private EditText landmarkEt;
    private EditText cityEt;
    private EditText stateEt;
    private EditText countryEt;
    private RippleAppCompatButton addDeliveryAddressBtnSave;
    AddDeliveryAddressRequest addDeliveryAddressRequest;
    private EditText fullNameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_delivery_address);

        initPresenter();
        addressMvpViewAddDeliveryAddressMvpPresenter.onAttach(AddDeliveryAddressActivity.this);


        initView();
    }

    public void initPresenter() {

        addressMvpViewAddDeliveryAddressMvpPresenter = new AddDeliveryAddressPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.add_delivery_address_btn_save:
                addDeliveryAddressRequest = new AddDeliveryAddressRequest();
                addDeliveryAddressRequest.setFullName(fullNameEt.getText().toString().trim());
                addDeliveryAddressRequest.setMobileNumber(phoneNumberEt.getText().toString().trim());
                addDeliveryAddressRequest.setPincode(pinCodeEt.getText().toString().trim());
                addDeliveryAddressRequest.setFlatNumber(flatNoEt.getText().toString().trim());
                addDeliveryAddressRequest.setColony(colonyEt.getText().toString().trim());
                addDeliveryAddressRequest.setLandmark(landmarkEt.getText().toString().trim());
                addDeliveryAddressRequest.setCity(cityEt.getText().toString().trim());
                addDeliveryAddressRequest.setCountry(countryEt.getText().toString().trim());
                addDeliveryAddressRequest.setState(stateEt.getText().toString().trim());
                addDeliveryAddressRequest.setUserId(AppController.get(this).getAppDataManager().getCurrentUserId());
                addressMvpViewAddDeliveryAddressMvpPresenter.addDeliverAddress(addDeliveryAddressRequest);
                break;
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addressMvpViewAddDeliveryAddressMvpPresenter.onDetach();
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
        phoneNumberEt = (EditText) findViewById(R.id.phone_number_et);
        pinCodeEt = (EditText) findViewById(R.id.pin_code_et);
        flatNoEt = (EditText) findViewById(R.id.flat_no_et);
        colonyEt = (EditText) findViewById(R.id.colony_et);
        landmarkEt = (EditText) findViewById(R.id.landmark_et);
        cityEt = (EditText) findViewById(R.id.city_et);
        stateEt = (EditText) findViewById(R.id.state_et);
        countryEt = (EditText) findViewById(R.id.country_et);
        addDeliveryAddressBtnSave = (RippleAppCompatButton) findViewById(R.id.add_delivery_address_btn_save);
        setOnClickListener();
        fullNameEt = (EditText) findViewById(R.id.full_name_et);
    }


    private void setOnClickListener() {
        addDeliveryAddressBtnSave.setOnClickListener(this);
    }

    @Override
    public void onDeliveryAddressAdd(String message) {

    }
}