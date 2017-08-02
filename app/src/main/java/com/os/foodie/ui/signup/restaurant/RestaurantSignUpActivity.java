package com.os.foodie.ui.signup.restaurant;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.login.LoginActivity;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.otp.OtpActivity;
import com.os.foodie.ui.signup.customer.CustomerSignUpActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.RealPathUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class RestaurantSignUpActivity extends BaseActivity implements RestaurantSignUpMvpView, View.OnClickListener {

    private CircleImageView icRestaurantLogo;
    private EditText etContactPersonName, etRestaurantName, etEmail, etPassword, etConfirmPassword, etPhone;
    private TextView tvLogIn, tvCustomerRegister;
    private Button btSignUp, btSignUpFacebook;

    private String fbId = "";

    private CallbackManager callbackManager;
    private static final List<String> PERMISSIONS = Arrays.asList("public_profile", "email", "user_location", "user_birthday", "user_friends", "user_about_me", "user_photos");

    private File restaurantLogoFile;
    private String restaurantLogoPath = "", restaurantLogoName = "";

    private static final int PERMISSION_CODE = 10;

    private static final int CAMERA_REQUEST = 2;
    private static final int GALARY_REQUEST = 3;

    private RestaurantSignUpMvpPresenter<RestaurantSignUpMvpView> restaurantSignUpMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_sign_up);

        initPresenter();
        restaurantSignUpMvpPresenter.onAttach(RestaurantSignUpActivity.this);

        callbackManager = CallbackManager.Factory.create();

        etContactPersonName = (EditText) findViewById(R.id.activity_restaurant_sign_up_et_contact_person_name);
        etRestaurantName = (EditText) findViewById(R.id.activity_restaurant_sign_up_et_restaurant_name);
        etEmail = (EditText) findViewById(R.id.activity_restaurant_sign_up_et_email);
        etPassword = (EditText) findViewById(R.id.activity_restaurant_sign_up_et_password);
        etConfirmPassword = (EditText) findViewById(R.id.activity_restaurant_sign_up_et_confirm_password);
        etPhone = (EditText) findViewById(R.id.activity_restaurant_sign_up_et_phone);

        btSignUp = (Button) findViewById(R.id.activity_restaurant_sign_up_bt_sign_up);
        btSignUpFacebook = (Button) findViewById(R.id.activity_restaurant_sign_up_bt_sign_up_facebook);

        tvLogIn = (TextView) findViewById(R.id.activity_restaurant_sign_up_tv_log_in);
        tvCustomerRegister = (TextView) findViewById(R.id.activity_restaurant_sign_up_tv_customer_register);

        icRestaurantLogo = (CircleImageView) findViewById(R.id.activity_facebook_sign_up_civ_restaurant_logo);

        btSignUp.setOnClickListener(this);
        btSignUpFacebook.setOnClickListener(this);

        tvLogIn.setOnClickListener(this);
        tvCustomerRegister.setOnClickListener(this);
        icRestaurantLogo.setOnClickListener(this);
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
        } else if (v.getId() == tvCustomerRegister.getId()) {
            openCustomerRegistrationActivity();
        } else if (v.getId() == icRestaurantLogo.getId()) {

            if (hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                selectImage();
            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

        }
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        restaurantSignUpMvpPresenter = new RestaurantSignUpPresenter(appDataManager, compositeDisposable);


//        restaurantSignUpMvpPresenter = new RestaurantSignUpPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    protected void setUp() {
    }

    @Override
    public void doSignUp() {

//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        final String deviceId = telephonyManager.getDeviceId();
        final String deviceId = restaurantSignUpMvpPresenter.getDeviceId();

        String deviceType = "android";
        String language = "eng";

        restaurantSignUpMvpPresenter.onRestaurantSignUpClick(fbId, etContactPersonName.getText().toString(), etRestaurantName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etConfirmPassword.getText().toString(), etPhone.getText().toString(), deviceId, deviceType, "", "", language, createFileHashMap());
    }

    @Override
    public void openLogInActivity() {
        Intent intent = new Intent(RestaurantSignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void verifyOTP() {
        Intent intent = new Intent(RestaurantSignUpActivity.this, OtpActivity.class);
        startActivity(intent);
    }

    @Override
    public void verifyOTP(String otp) {

//                                TODO OTP
        Intent intent = new Intent(RestaurantSignUpActivity.this, OtpActivity.class);
        intent.putExtra("OTP", otp);
        startActivity(intent);
    }

    @Override
    public void openCustomerRegistrationActivity() {
        Intent intent = new Intent(RestaurantSignUpActivity.this, CustomerSignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void setFacebookDetails(String id, String name, String email) {

//        etContactPersonName.setText("");
//        etRestaurantName.setText("");
//        etEmail.setText("");
//        etPassword.setText("");
//        etConfirmPassword.setText("");
//        etPhone.setText("");
//
//        fbId = id;
//        etContactPersonName.setText(name);
//        etEmail.setText(email);

        FacebookSignUpModel facebookSignUpModel = new FacebookSignUpModel(id, "", "", name, email, false);

        Intent intent = new Intent(RestaurantSignUpActivity.this, FacebookSignUpActivity.class);
        intent.putExtra(AppConstants.FACEBOOK_SIGN_UP_MODEL, facebookSignUpModel);
        startActivity(intent);
    }

    public void getFacebookDetail() {

        if (NetworkUtils.isNetworkConnected(this)) {
            callFacebookDetailsAPI();
        } else {
            restaurantSignUpMvpPresenter.setError(R.string.connection_error);
        }
    }

    public void callFacebookDetailsAPI() {

//        final String deviceId = CommonUtils.getDeviceId(this);
        final String deviceId = restaurantSignUpMvpPresenter.getDeviceId();
        final String deviceType = "android";


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

                                        try {
                                            String id = object.getString("id");
                                            String name = object.getString("name");
                                            String email = object.optString("email");
//                                            String first_name = object.getString("first_name");
//                                            String last_name = object.getString("last_name");
//                                            String username = name.replace(" ", "");
//                                            Uri fbImageUri = Uri.parse("https://graph.facebook.com/" + id + "/picture?width=600&height=600");
//                                            String profile_image = fbImageUri.toString();

                                            if (name == null) {
                                                name = "";
                                            }
                                            if (email == null) {
                                                email = "";
                                            }

                                            Log.d("object", ">>" + object.toString());
                                            restaurantSignUpMvpPresenter.onFacebookLoginClick(id, name, email, deviceId, deviceType);

                                        } catch (Exception ex) {
                                            restaurantSignUpMvpPresenter.setError(R.string.some_error);
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
                        System.out.println("Facebook cancelled");
//                        restaurantSignUpMvpPresenter.setError(R.string.api_retry_error);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        restaurantSignUpMvpPresenter.setError(R.string.some_error);
                        exception.toString();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                restaurantLogoPath = imageFile.getPath();
                restaurantLogoName = imageFile.getName();
                Uri imageUri = Uri.fromFile(imageFile);
                restaurantLogoFile = imageFile;
                Glide.with(RestaurantSignUpActivity.this)
                        .load(imageUri)
                        .into(icRestaurantLogo);
            }

        });

    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantSignUpActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

//                boolean result= hasPermission(Manifest.permission.CAMERA);

                if (items[item].equals("Take Photo")) {

//                    userChoosenTask = 1;

//                    if(result)
                    cameraIntent();

                } else if (items[item].equals("Choose from Gallery")) {

//                    userChoosenTask = 2;

//                    if(result)
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);*/
        EasyImage.openCamera(RestaurantSignUpActivity.this, CAMERA_REQUEST);
    }

    private void galleryIntent() {
        EasyImage.openGallery(RestaurantSignUpActivity.this, GALARY_REQUEST);
       /* Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(*//*intent*//*Intent.createChooser(intent, "Select File"), GALARY_REQUEST);*/
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//        File destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;

        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        File file = null;

        if (data != null) {

            try {
//                Log.d("getDataStr", ">>" + data.getData().toString());
//                Log.d("getDataPath", ">>" + data.getData().getPath());
//
//                File filee = new File(getRealPathFromURI(data.getData()));
//                file = filee;

                if (Build.VERSION.SDK_INT >= 19) {

                    String path = RealPathUtils.getRealPathFromURI_API19(this, data.getData());
                    file = new File(path);

                } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 18) {

                    String path = RealPathUtils.getRealPathFromURI_API11to18(this, data.getData());
                    file = new File(path);
                }

                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        ivPhotos.setImageBitmap(bm);
    }

    public HashMap<String, File> createFileHashMap() {

        HashMap<String, File> fileMapTemp = new HashMap<String, File>();
        if (restaurantLogoFile != null)
            fileMapTemp.put("restaurant_logo", restaurantLogoFile);

        return fileMapTemp;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
        int i = 0;
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                //mSelectImageClass = new SelectImageClass(mContext, getActivity(), "");
                // System.exit(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        restaurantSignUpMvpPresenter.dispose();
//        restaurantSignUpMvpPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void openLocationInfoActivity() {

        Intent intent = new Intent(RestaurantSignUpActivity.this, LocationInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openSetupRestaurantProfileActivity() {

        Intent intent = new Intent(RestaurantSignUpActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openCustomerHomeActivity() {

        Intent intent = new Intent(RestaurantSignUpActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openRestaurantHomeActivity() {

        Intent intent = new Intent(RestaurantSignUpActivity.this, RestaurantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showError(int key, String message) {

        switch (key) {

            case 1:

                etContactPersonName.setError(message);
                etContactPersonName.requestFocus();
                break;

            case 2:

                etRestaurantName.setError(message);
                etRestaurantName.requestFocus();
                break;

            case 3:

                etEmail.setError(message);
                etEmail.requestFocus();
                break;

            case 4:

                etPhone.setError(message);
                etPhone.requestFocus();
                break;

            case 5:

                etPassword.setError(message);
                etPassword.requestFocus();
                break;

            case 6:

                etConfirmPassword.setError(message);
                etConfirmPassword.requestFocus();
                break;
        }
    }


}