package com.os.foodie.ui.otp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class OtpActivity extends BaseActivity implements OtpMvpView, View.OnClickListener {

    private EditText etOtp;
    private Button btSubmit;

//    private boolean isCustomer;

    private CustomerSignUpResponse customerSignUpResponse;
    //    Need Change to Restaurant
    private CustomerSignUpResponse restaurantSignUpResponse;

    private OtpMvpPresenter<OtpMvpView> otpMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initPresenter();
        otpMvpPresenter.onAttach(OtpActivity.this);

        etOtp = (EditText) findViewById(R.id.activity_otp_et_otp);
        btSubmit = (Button) findViewById(R.id.activity_otp_bt_submit);

        btSubmit.setOnClickListener(this);

//                                TODO OTP
        if(getIntent().hasExtra("OTP")){
            etOtp.setText(getIntent().getStringExtra("OTP"));
        }
//        getExtras();
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

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        otpMvpPresenter = new OtpPresenter(appDataManager, compositeDisposable);

//        otpMvpPresenter = new OtpPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

//    public void getExtras() {
////        if (getIntent().hasExtra(AppConstants.CUSTOMER_OTP_VERIFICATION)) {
////
////            isCustomer = true;
////            otp = getIntent().getStringExtra(AppConstants.CUSTOMER_OTP_VERIFICATION);
////
////        } else {
////
////            isCustomer = false;
////            otp = getIntent().getStringExtra(AppConstants.RESTAURANT_OTP_VERIFICATION);
////        }
//
//        if (getIntent().hasExtra(AppConstants.CUSTOMER_SIGN_UP_RESPONSE)) {
//
//            isCustomer = true;
//            customerSignUpResponse = getIntent().getParcelableExtra(AppConstants.CUSTOMER_SIGN_UP_RESPONSE);
//            otpMvpPresenter.showMessage(customerSignUpResponse.getResponse().getMessage());
//
//            Log.d("getMessage", ">>" + customerSignUpResponse.getResponse().getMessage());
//
//            otp = customerSignUpResponse.getResponse().getOtp();
//
//        } else {
//
//            isCustomer = false;
//            restaurantSignUpResponse = getIntent().getParcelableExtra(AppConstants.RESTAURANT_SIGN_UP_RESPONSE);
//            otpMvpPresenter.showMessage(restaurantSignUpResponse.getResponse().getMessage());
//
////            otp = restaurantSignUpResponse.getResponse().getOtp();
//        }
//
//        etOtp.setText(otp);
//    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == btSubmit.getId()) {
            otpMvpPresenter.verify(etOtp.getText().toString());
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onVerify(String userType) {

        if (userType.equals(AppConstants.CUSTOMER)) {

            Log.d("userType", ">>Customer");

            Intent intent = new Intent(OtpActivity.this, LocationInfoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {

            Log.d("userType", ">>Restaurant");

            Intent intent = new Intent(OtpActivity.this, RestaurantMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        otpMvpPresenter.dispose();
//        otpMvpPresenter.onDetach();
        super.onDestroy();
    }
}