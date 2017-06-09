package com.os.foodie.ui.account.restaurant;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.account.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.EditRestaurantAccountRequest;
import com.os.foodie.data.network.model.account.EditRestaurantAccountResponse;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class RestaurantAccountFragment extends BaseFragment implements RestaurantAccountMvpView, View.OnClickListener {

    public static final String TAG = "RestaurantAccountFragment";

    private RestaurantAccountMvpPresenter<RestaurantAccountMvpView> restaurantAccountMvpPresenter;
    private CircleImageView profileImageIv;
    private EditText restaurantNameEt;
    private EditText contactPersonNameEt;
    private EditText emailEt;
    private EditText phoneNumEt;
    private static final int CAMERA_REQUEST = 2;
    private static final int GALARY_REQUEST = 3;
    private RippleAppCompatButton activityRestaurantSaveProfile;
    RestaurantMainActivity restaurantMainActivity;
    private String restaurantLogoPath = "", restaurantLogoName = "";
    private File restaurantLogoFile;
    public RestaurantAccountFragment() {
    }

    public static RestaurantAccountFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantAccountFragment fragment = new RestaurantAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_restaurant_account, container, false);
        restaurantMainActivity=(RestaurantMainActivity) getActivity();
        initView(view);


        (restaurantMainActivity).setTitle(getActivity().getResources().getString(R.string.title_fragment_customer_home));

        restaurantAccountMvpPresenter = new RestaurantAccountPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        restaurantAccountMvpPresenter.onAttach(this);

        restaurantAccountMvpPresenter.getRestaurantAccountDetail(AppController.get(getActivity()).getAppDataManager().getCurrentUserId());
//        fabAdd.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        (restaurantMainActivity).setTitle(getString(R.string.action_my_account));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_with_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {
            activityRestaurantSaveProfile.setVisibility(View.VISIBLE);
            setHasOptionsMenu(false);
            setEditTextEnable(true);
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == activityRestaurantSaveProfile.getId()) {
            EditRestaurantAccountRequest editRestaurantAccountRequest = new EditRestaurantAccountRequest();
            editRestaurantAccountRequest.setContactPersonName(contactPersonNameEt.getText().toString().trim());
            editRestaurantAccountRequest.setRestaurantName(restaurantNameEt.getText().toString().trim());
            editRestaurantAccountRequest.setEmail(emailEt.getText().toString().trim());
            editRestaurantAccountRequest.setMobileNumber(phoneNumEt.getText().toString().trim());
            editRestaurantAccountRequest.setRestaurantId(AppController.get(getActivity()).getAppDataManager().getCurrentUserId());

            restaurantAccountMvpPresenter.editRestaurantAccountDetail(editRestaurantAccountRequest,createFileHashMap());
        }
        else if(v.getId()==profileImageIv.getId()){


            if (restaurantMainActivity.hasPermission(Manifest.permission.CAMERA) && restaurantMainActivity.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && restaurantMainActivity.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                selectImage();
            } else {
                restaurantMainActivity.requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void setRestaurantAccountDetail(CustomerRestaurantDetailsResponse getAccountDetailResponse) {

        restaurantNameEt.setText(getAccountDetailResponse.getResponse().getRestaurantName());
        contactPersonNameEt.setText(getAccountDetailResponse.getResponse().getContactPersonName());
        phoneNumEt.setText(getAccountDetailResponse.getResponse().getMobileNumber());
        emailEt.setText(getAccountDetailResponse.getResponse().getEmail());
    }

    @Override
    public void editRestaurantAccountDetail(EditRestaurantAccountResponse editRestaurantAccountResponse) {
        setHasOptionsMenu(true);
        activityRestaurantSaveProfile.setVisibility(View.GONE);
        ((RestaurantMainActivity) getActivity()).setCustomerName();
        restaurantMainActivity.setRestaurantLogo();
        setEditTextEnable(false);
    }

    private void initView(View view) {

        setHasOptionsMenu(true);

        profileImageIv = (CircleImageView) view.findViewById(R.id.profile_image_iv);
        restaurantNameEt = (EditText) view.findViewById(R.id.restaurant_name_et);
        contactPersonNameEt = (EditText) view.findViewById(R.id.contact_person_name_et);
        emailEt = (EditText) view.findViewById(R.id.email_et);
        phoneNumEt = (EditText) view.findViewById(R.id.phone_num_et);
        activityRestaurantSaveProfile = (RippleAppCompatButton) view.findViewById(R.id.activity_restaurant_save_profile);
        activityRestaurantSaveProfile.setOnClickListener(this);
        profileImageIv.setOnClickListener(this);

        Glide.with(this)
                .load(AppController.get(getActivity()).getAppDataManager().getRestaurantLogoURL())
//                .load("http://192.168.1.69/foodi/app/webroot//uploads/restaurant_images/restaurant_logo_1496410374.jpg")
//                .placeholder(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .error(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                .into(profileImageIv);
    }

    private void setEditTextEnable(boolean setEnable) {
        restaurantNameEt.setEnabled(setEnable);
        contactPersonNameEt.setEnabled(setEnable);
        emailEt.setEnabled(setEnable);
        phoneNumEt.setEnabled(setEnable);
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        EasyImage.openCamera(RestaurantAccountFragment.this, CAMERA_REQUEST);
    }

    private void galleryIntent() {
        EasyImage.openGallery(RestaurantAccountFragment.this, GALARY_REQUEST);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
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
                Glide.with(getActivity())
                        .load(imageUri)
                        .into(profileImageIv);
            }

        });

    }
    public HashMap<String, File> createFileHashMap() {

        HashMap<String, File> fileMapTemp = new HashMap<String, File>();
        if (restaurantLogoFile != null)
            fileMapTemp.put("restaurant_logo", restaurantLogoFile);

        return fileMapTemp;
    }
}