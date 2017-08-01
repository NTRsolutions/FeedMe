package com.os.foodie.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.model.FacebookSignUpModel;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.fbsignup.FacebookSignUpActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.forgotpassword.ForgotPasswordActivity;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfileFragment;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.DialogUtils;
import com.os.foodie.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity implements LoginMvpView, View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btLogin, btFacebookLogin;
    private TextView tvForgotPassword;

    private static final int PERMISSION_REQUEST_CODE = 10;
    private static final int FORGOT_PASSWORD_REQUEST_CODE = 20;
    private static final int FORGOT_PASSWORD_RESPONSE_CODE = 21;

    private static final List<String> PERMISSIONS = Arrays.asList("public_profile", "email", "user_location", "user_birthday", "user_friends", "user_about_me", "user_photos");
    private CallbackManager callbackManager;

    private LoginMvpPresenter<LoginMvpView> loginMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initPresenter();
        loginMvpPresenter.onAttach(LoginActivity.this);

        etEmail = (EditText) findViewById(R.id.activity_login_et_email);
        etPassword = (EditText) findViewById(R.id.activity_login_et_password);

        tvForgotPassword = (TextView) findViewById(R.id.activity_login_tv_forgot_password);

        btLogin = (Button) findViewById(R.id.activity_login_bt_log_in);
        btFacebookLogin = (Button) findViewById(R.id.activity_login_bt_login_facebook);

        tvForgotPassword.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        btFacebookLogin.setOnClickListener(this);

//        printhashkey();
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        loginMvpPresenter = new LoginPresenter(appDataManager, compositeDisposable);
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void onDestroy() {
        loginMvpPresenter.dispose();
//        loginMvpPresenter.onDetach();
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

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == btLogin.getId()) {

            if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {

//                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                final String deviceId = telephonyManager.getDeviceId();

                final String deviceId = loginMvpPresenter.getDeviceId();
                final String deviceType = "android";

                loginMvpPresenter.onLoginClick(etEmail.getText().toString(), etPassword.getText().toString(), deviceId, deviceType);

            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }

        } else if (v.getId() == btFacebookLogin.getId()) {

            if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {

                getFacebookDetail();

            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        } else if (v.getId() == tvForgotPassword.getId()) {
            openForgotPassword();
        }
    }

    public void getFacebookDetail() {

        if (NetworkUtils.isNetworkConnected(this)) {
            callFacebookDetailsAPI();
        } else {
            loginMvpPresenter.setError(R.string.connection_error);
        }
    }

    public void callFacebookDetailsAPI() {

//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        final String deviceId = telephonyManager.getDeviceId();
        final String deviceId = loginMvpPresenter.getDeviceId();

//        final String deviceId = CommonUtils.getDeviceId(this);
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
                                            loginMvpPresenter.onFacebookLoginClick(id, first_name, last_name, email, deviceId, deviceType);
//                                            setFacebookDetails(id, first_name, last_name, email);

                                        } catch (Exception ex) {
//                                                    Utils.hideProgress();
                                            //     dialog.dismiss();
                                            ex.printStackTrace();
                                            loginMvpPresenter.setError(R.string.api_default_error);
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
                        // App code
                        System.out.println("Facebook cancelled");
//                        loginMvpPresenter.setError(R.string.api_retry_error);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
//                            Toast.makeText(CustomerSignUpActivity.this, exception.toString(), Toast.LENGTH_LONG).show();
                        loginMvpPresenter.setError(R.string.api_default_error);
                        exception.toString();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FORGOT_PASSWORD_REQUEST_CODE) {
            if (resultCode == FORGOT_PASSWORD_RESPONSE_CODE) {
                loginMvpPresenter.setError(data.getStringExtra(AppConstants.PASSWORD_RESET_MESSAGE));
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void openForgotPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivityForResult(intent, FORGOT_PASSWORD_REQUEST_CODE);
    }

//    @Override
//    public void onLoginSuccess(String userType) {
//
//        if (userType.equals(AppConstants.CUSTOMER)) {
//
//            Log.d("userType", ">>Customer");
//
//            Intent intent = new Intent(LoginActivity.this, LocationInfoActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//
//        } else {
//
//            Log.d("userType", ">>Restaurant");
//
//            Intent intent = new Intent(LoginActivity.this, RestaurantMainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }

    @Override
    public void openLocationInfoActivity() {

        Intent intent = new Intent(LoginActivity.this, LocationInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openSetupRestaurantProfileActivity() {

        Intent intent = new Intent(LoginActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openCustomerHomeActivity() {

        Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openRestaurantHomeActivity() {

        Intent intent = new Intent(LoginActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void setFacebookDetails(final String id, final String firstName, final String lastName, final String email) {

        DialogInterface.OnClickListener customer = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FacebookSignUpModel facebookSignUpModel = new FacebookSignUpModel(id, firstName, lastName, "", email, true);

                Intent intent = new Intent(LoginActivity.this, FacebookSignUpActivity.class);
                intent.putExtra(AppConstants.FACEBOOK_SIGN_UP_MODEL, facebookSignUpModel);
                startActivity(intent);
                dialog.dismiss();
            }
        };

        DialogInterface.OnClickListener restaurant = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FacebookSignUpModel facebookSignUpModel = new FacebookSignUpModel(id, "", "", firstName + " " + lastName, email, false);

                Intent intent = new Intent(LoginActivity.this, FacebookSignUpActivity.class);
                intent.putExtra(AppConstants.FACEBOOK_SIGN_UP_MODEL, facebookSignUpModel);
                startActivity(intent);
                dialog.dismiss();
            }
        };

        DialogUtils.showAlert(LoginActivity.this,
                R.string.select_registration_type_title, R.string.select_registration_type_msg,
                getResources().getString(R.string.customer), customer,
                getResources().getString(R.string.restaurant), restaurant);

    }

    @Override
    public void showError(int key, String message) {

        switch (key) {

            case 1:

                etEmail.setError(message);
                etEmail.requestFocus();
                break;

            case 2:

                etPassword.setError(message);
                etPassword.requestFocus();
                break;
        }
    }

//    public void printhashkey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.os.foodie", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//    }
}