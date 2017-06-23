package com.os.foodie.ui.fbsignup;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.model.FacebookSignUpModel;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.otp.OtpActivity;
import com.os.foodie.utils.AppConstants;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class FacebookSignUpActivity extends BaseActivity implements FacebookSignUpMvpView, View.OnClickListener {

    private EditText etRestaurantName, etPhone;
    private Button btSubmit;

    private static final int PERMISSION_CODE = 10;

    private FacebookSignUpModel facebookSignUpModel;
    private FacebookSignUpMvpPresenter<FacebookSignUpMvpView> facebookSignUpMvpPresenter;

    private CircleImageView ivRestaurantLogo;
    private String restaurantLogoPath = "", restaurantLogoName = "";
    private File restaurantLogoFile;

    private static final int CAMERA_REQUEST = 2;
    private static final int GALARY_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_sign_up);
        initPresenter();
        facebookSignUpMvpPresenter.onAttach(FacebookSignUpActivity.this);
        etRestaurantName = (EditText) findViewById(R.id.activity_facebook_sign_up_et_restaurant_name);
        etPhone = (EditText) findViewById(R.id.activity_facebook_sign_up_et_phone);
        btSubmit = (Button) findViewById(R.id.activity_facebook_sign_up_bt_submit);
        ivRestaurantLogo = (CircleImageView) findViewById(R.id.activity_facebook_sign_up_civ_restaurant_logo);
        setUp();
        ivRestaurantLogo.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
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

        facebookSignUpMvpPresenter = new FacebookSignUpPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btSubmit.getId()) {

            if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
                doSignUp();
            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_CODE);
            }
        } else if (v.getId() == ivRestaurantLogo.getId()) {

            if (hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                selectImage();
            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FacebookSignUpActivity.this);
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
        EasyImage.openCamera(FacebookSignUpActivity.this, CAMERA_REQUEST);
    }

    private void galleryIntent() {
        EasyImage.openGallery(FacebookSignUpActivity.this, GALARY_REQUEST);
    }

    public void doSignUp() {

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceId = telephonyManager.getDeviceId();

        String deviceType = "android";
        String language = "eng";

        facebookSignUpMvpPresenter.onSubmit(facebookSignUpModel, etRestaurantName.getText().toString(), etPhone.getText().toString(), deviceId, deviceType, "", "", language, createFileHashMap());
    }

    @Override
    public void verifyOTP() {
        Intent intent = new Intent(FacebookSignUpActivity.this, OtpActivity.class);
        startActivity(intent);
    }

    @Override
    public void verifyOTP(String otp) {

//                                TODO OTP
        Intent intent = new Intent(FacebookSignUpActivity.this, OtpActivity.class);
        intent.putExtra("OTP", otp);
        startActivity(intent);
    }

    @Override
    protected void setUp() {

        facebookSignUpModel = getIntent().getParcelableExtra(AppConstants.FACEBOOK_SIGN_UP_MODEL);

        if (facebookSignUpModel.getIsCustomer()) {
            etRestaurantName.setVisibility(View.GONE);
            ivRestaurantLogo.setVisibility(View.GONE);
        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                Glide.with(FacebookSignUpActivity.this)
                        .load(imageUri)
                        .into(ivRestaurantLogo);
            }

        });

    }

    public HashMap<String, File> createFileHashMap() {

        HashMap<String, File> fileMapTemp = new HashMap<String, File>();
        if (restaurantLogoFile != null)
            fileMapTemp.put("restaurant_logo", restaurantLogoFile);

        return fileMapTemp;
    }

    @Override
    protected void onDestroy() {
        facebookSignUpMvpPresenter.onDetach();
        super.onDestroy();
    }
}