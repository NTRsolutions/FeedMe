package com.os.foodie.ui.signup.customer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.model.FacebookSignUpModel;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.fbsignup.FacebookSignUpActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.login.LoginActivity;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.otp.OtpActivity;
import com.os.foodie.ui.signup.restaurant.RestaurantSignUpActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerSignUpActivity extends BaseActivity implements CustomerSignUpMvpView, View.OnClickListener {

    private EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etCountryCode, etPhone;
    private TextView tvLogIn/*, tvRestaurantRegister*/;
    private Button btSignUp, btSignUpFacebook;

    private static final int PERMISSION_CODE = 10;
    private String fbId = "";

    private static final List<String> PERMISSIONS = Arrays.asList("public_profile", "email", "user_location", "user_birthday", "user_friends", "user_about_me", "user_photos");
    private CallbackManager callbackManager;

    private CustomerSignUpMvpPresenter<CustomerSignUpMvpView> customerSignUpMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initPresenter();
        customerSignUpMvpPresenter.onAttach(CustomerSignUpActivity.this);

        etFirstName = (EditText) findViewById(R.id.activity_customer_sign_up_et_first_name);
        etLastName = (EditText) findViewById(R.id.activity_customer_sign_up_et_last_name);
        etEmail = (EditText) findViewById(R.id.activity_customer_sign_up_et_email);
        etPassword = (EditText) findViewById(R.id.activity_customer_sign_up_et_password);
        etConfirmPassword = (EditText) findViewById(R.id.activity_customer_sign_up_et_confirm_password);
        etCountryCode = (EditText) findViewById(R.id.activity_customer_sign_up_et_country_code);
        etPhone = (EditText) findViewById(R.id.activity_customer_sign_up_et_phone);

        btSignUp = (Button) findViewById(R.id.activity_customer_sign_up_bt_sign_up);
        btSignUpFacebook = (Button) findViewById(R.id.activity_customer_sign_up_bt_sign_up_facebook);

        tvLogIn = (TextView) findViewById(R.id.activity_customer_sign_up_tv_log_in);
//        tvRestaurantRegister = (TextView) findViewById(R.id.activity_customer_sign_up_tv_restaurant_register);

        btSignUp.setOnClickListener(this);
        btSignUpFacebook.setOnClickListener(this);

        tvLogIn.setOnClickListener(this);
//        tvRestaurantRegister.setOnClickListener(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_with_close, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == R.id.action_close) {
//            finish();
//        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == btSignUp.getId()) {

            if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
                doSignUp();
            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_CODE);
            }

        } else if (v.getId() == btSignUpFacebook.getId()) {

            if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
                getFacebookDetail();
            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_CODE);
            }

        } else if (v.getId() == tvLogIn.getId()) {
            openLogInActivity();
//        } else if (v.getId() == tvRestaurantRegister.getId()) {
//            openRestaurantRegistrationActivity();
        }
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        customerSignUpMvpPresenter = new CustomerSignUpPresenter(appDataManager, compositeDisposable);

//        customerSignUpMvpPresenter = new CustomerSignUpPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    protected void setUp() {
    }

    public void getFacebookDetail() {

        if (NetworkUtils.isNetworkConnected(this)) {
            callFacebookDetailsAPI();
        } else {
            customerSignUpMvpPresenter.setError(R.string.connection_error);
        }
    }

    @Override
    public void setFacebookDetails(String id, String first_name, String last_name, String email) {

//        etFirstName.setText("");
//        etLastName.setText("");
//        etEmail.setText("");
//        etPassword.setText("");
//        etConfirmPassword.setText("");
//        etPhone.setText("");
//
//        fbId = id;
//        etFirstName.setText(first_name);
//        etLastName.setText(last_name);
//        etEmail.setText(email);

        FacebookSignUpModel facebookSignUpModel = new FacebookSignUpModel(id, first_name, last_name, "", email, true);

        Intent intent = new Intent(CustomerSignUpActivity.this, FacebookSignUpActivity.class);
        intent.putExtra(AppConstants.FACEBOOK_SIGN_UP_MODEL, facebookSignUpModel);
        startActivity(intent);
    }

    public void callFacebookDetailsAPI() {

//        final String deviceId = CommonUtils.getDeviceId(this);
        final String deviceId = customerSignUpMvpPresenter.getDeviceId();
        final String deviceType = "android";

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, PERMISSIONS);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
//                                Utils.showProgress(WelcomeActivity.this);
                        Log.d("loginResult", ">>" + loginResult.toString());
                        loginResult.getAccessToken().getUserId();
                        String mFacebookUserId = loginResult.getAccessToken().getUserId();

                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        System.out.println("object is " + object);
                                        try {
                                            String id = object.getString("id");
                                            String first_name = object.getString("first_name");
                                            String last_name = object.getString("last_name");
                                            String email = object.optString("email");
//                                            String name = object.getString("name");
//                                            String username = name.replace(" ", "");
//                                            Uri fbImageUri = Uri.parse("https://graph.facebook.com/" + id + "/picture?width=600&height=600");
//                                            String profile_image = fbImageUri.toString();

                                            if (first_name == null) {
                                                first_name = "";
                                            }
                                            if (last_name == null) {
                                                last_name = "";
                                            }
                                            if (email == null) {
                                                email = "";
                                            }

                                            Log.d("object", ">>" + object.toString());
                                            customerSignUpMvpPresenter.onFacebookLoginClick(id, first_name, last_name, email, deviceId, deviceType);
//                                            setFacebookDetails(id, first_name, last_name, email);

                                        } catch (Exception ex) {
                                            customerSignUpMvpPresenter.setError(R.string.some_error);
                                            ex.printStackTrace();
                                        }

                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,first_name,gender,last_name,birthday,link,email");
                        request.setParameters(parameters);
                        request.executeAsync();

                        LoginManager.getInstance().logOut();
                    }


                    @Override
                    public void onCancel() {
//                        customerSignUpMvpPresenter.setError(R.string.api_retry_error);
                        System.out.println("Facebook cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        customerSignUpMvpPresenter.setError(R.string.some_error);
                        exception.toString();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void doSignUp() {

//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        final String deviceId = telephonyManager.getDeviceId();
        final String deviceId = customerSignUpMvpPresenter.getDeviceId();

        String deviceType = "android";
        String language = "eng";

        customerSignUpMvpPresenter.onCustomerSignUpClick(fbId, etFirstName.getText().toString(), etLastName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etConfirmPassword.getText().toString(), etCountryCode.getText().toString(), etPhone.getText().toString(), deviceId, deviceType, "", "", language);
    }

    @Override
    public void openLogInActivity() {
        Intent intent = new Intent(CustomerSignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void verifyOTP() {

        Intent intent = new Intent(CustomerSignUpActivity.this, OtpActivity.class);
////        TODO Temporary (Remove)
//        intent.putExtra(AppConstants.CUSTOMER_SIGN_UP_RESPONSE, customerSignUpResponse);
        startActivity(intent);
    }

    @Override
    public void verifyOTP(String userId, String otp) {

//                                TODO OTP
        Intent intent = new Intent(CustomerSignUpActivity.this, OtpActivity.class);
        intent.putExtra(AppConstants.OTP, otp);
        intent.putExtra(AppConstants.USER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void openRestaurantRegistrationActivity() {
        Intent intent = new Intent(CustomerSignUpActivity.this, RestaurantSignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        customerSignUpMvpPresenter.dispose();
//        customerSignUpMvpPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void openLocationInfoActivity() {

        Intent intent = new Intent(CustomerSignUpActivity.this, LocationInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openSetupRestaurantProfileActivity() {

        Intent intent = new Intent(CustomerSignUpActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openCustomerHomeActivity() {

        Intent intent = new Intent(CustomerSignUpActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openRestaurantHomeActivity() {

        Intent intent = new Intent(CustomerSignUpActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showError(int key, String message) {

        switch (key) {

            case 1:

                etFirstName.setError(message);
                etFirstName.requestFocus();
                break;

            case 2:

                etLastName.setError(message);
                etLastName.requestFocus();
                break;

            case 3:

                etEmail.setError(message);
                etEmail.requestFocus();
                break;

            case 4:

                etCountryCode.setError(message);
                etCountryCode.requestFocus();
                break;

            case 5:

                etPhone.setError(message);
                etPhone.requestFocus();
                break;

            case 6:

                etPassword.setError(message);
                etPassword.requestFocus();
                break;

            case 7:

                etConfirmPassword.setError(message);
                etConfirmPassword.requestFocus();
                break;
        }
    }
}