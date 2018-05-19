package com.os.foodie.ui.menu.add;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.coursetype.list.Course;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.feature.callback.AddCourseTypeCallback;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.dialogfragment.course.list.CourseTypeDialogFragment;
import com.os.foodie.utils.AppConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class RestaurantMenuAddUpdateDishActivity extends BaseActivity implements AddCourseTypeCallback, RestaurantMenuAddUpdateDishMvpView, View.OnClickListener {

    private CircleImageView civDishImage;
    private EditText etCourseType, etItemName,etItemNameArabic, etItemUnitPrice, etIngredientsDetail;
    private RadioButton rbVeg, rbNonVeg;
    private RadioGroup radioGroup;
    private Button btSave;

    private Dish dish;
    private Course course;
    private CourseTypeDialogFragment courseTypeDialogFragment;

    private static final int CAMERA_REQUEST = 2;
    private static final int GALARY_REQUEST = 3;

    private RestaurantMenuAddUpdateDishMvpPresenter<RestaurantMenuAddUpdateDishMvpView> restaurantMenuAddUpdateDishMvpPresenter;
    private File menuImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu_add_update_dish);

        setUp();
        initPresenter();
        restaurantMenuAddUpdateDishMvpPresenter.onAttach(this);

        civDishImage = (CircleImageView) findViewById(R.id.activity_restaurant_menu_add_update_dish_civ_profile_image);

        etItemName = (EditText) findViewById(R.id.activity_restaurant_menu_add_update_dish_et_item_name);
        etItemNameArabic = (EditText) findViewById(R.id.activity_restaurant_menu_add_update_dish_et_item_name_arabic);

        etItemUnitPrice = (EditText) findViewById(R.id.activity_restaurant_menu_add_update_dish_et_item_unit_price);
        etIngredientsDetail = (EditText) findViewById(R.id.activity_restaurant_menu_add_update_dish_et_ingredients_details);

        rbVeg = (RadioButton) findViewById(R.id.activity_restaurant_menu_add_update_dish_rbt_veg);
        rbNonVeg = (RadioButton) findViewById(R.id.activity_restaurant_menu_add_update_dish_rbt_non_veg);

        radioGroup = (RadioGroup) findViewById(R.id.activity_restaurant_menu_add_update_dish_radio_group);

        etCourseType = (EditText) findViewById(R.id.activity_restaurant_menu_add_update_dish_et_course_type);

        btSave = (Button) findViewById(R.id.activity_restaurant_menu_add_update_dish_bt_save);

        if (getIntent().hasExtra(AppConstants.EDIT_DISH)) {

            dish = getIntent().getParcelableExtra(AppConstants.EDIT_DISH);

            course = new Course();
            course.setId(dish.getCourseId());

            Glide.with(this)
                    .load(dish.getDishImage())
                    .error(R.mipmap.img_placeholder)
                    .into(civDishImage);

            etItemName.setText(dish.getName());
            etItemNameArabic.setText(dish.getNameArabic());
            etCourseType.setText(dish.getCourseName());
            etItemUnitPrice.setText(dish.getPrice());
            etIngredientsDetail.setText(dish.getDescription());

            if (dish.getVegNonveg().equalsIgnoreCase(AppConstants.VEG)) {
                radioGroup.check(rbVeg.getId());
            } else {
                radioGroup.check(rbNonVeg.getId());
            }
        }

        civDishImage.setOnClickListener(this);
        etCourseType.setOnClickListener(this);
        btSave.setOnClickListener(this);
    }

    @Override
    protected void setUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
//        restaurantMenuAddUpdateDishMvpPresenter = new RestaurantMenuAddUpdateDishPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        restaurantMenuAddUpdateDishMvpPresenter = new RestaurantMenuAddUpdateDishPresenter<>(appDataManager, compositeDisposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == etCourseType.getId()) {

            restaurantMenuAddUpdateDishMvpPresenter.getCourseTypeList();

        } else if (v.getId() == btSave.getId()) {

            String courseId;

            if (course == null || course.getId().isEmpty()) {
                courseId = null;
            } else {
                courseId = course.getId();
            }

            String itemName = etItemName.getText().toString();
            String itemNameArabic = etItemNameArabic.getText().toString();
            String itemPrice = etItemUnitPrice.getText().toString();
            String itemDescription = etIngredientsDetail.getText().toString();

            View radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            int idx = radioGroup.indexOfChild(radioButton);
            RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
            String vegNonVeg = r.getText().toString();

            if (vegNonVeg.equalsIgnoreCase("Veg")) {
                vegNonVeg = AppConstants.VEG;
            } else {
                vegNonVeg = AppConstants.NONVEG;
            }

//            Log.d("courseId", ">>" + courseId);
//            Log.d("itemName", ">>" + itemName);
//            Log.d("itemPrice", ">>" + itemPrice);
//            Log.d("itemDescription", ">>" + itemDescription);
//            Log.d("radioGroup", ">>" + vegNonVeg);

            String dishId = "";

            if (dish != null) {
                dishId = dish.getDishId();
//
//                if (course == null) {
//                    courseId = dish.getCourseId();
//                }
            }

            restaurantMenuAddUpdateDishMvpPresenter.addRestaurantMenuItem(dishId, courseId, itemName, itemPrice, itemDescription, vegNonVeg, createFileHashMap(),itemNameArabic);

        } else if (v.getId() == civDishImage.getId()) {

            if (hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                selectImage();
            } else {
                requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void selectImage() {

        final CharSequence[] items = {getString(R.string.alert_dialog_text_photo_picker_camera), getString(R.string.alert_dialog_text_photo_picker_gallery), getString(R.string.alert_dialog_text_photo_picker_cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_dialog_title_photo_picker);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getString(R.string.alert_dialog_text_photo_picker_camera))) {

                    cameraIntent();

                } else if (items[item].equals(getString(R.string.alert_dialog_text_photo_picker_gallery))) {

                    galleryIntent();

                } else if (items[item].equals(getString(R.string.alert_dialog_text_photo_picker_cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        EasyImage.openCamera(this, CAMERA_REQUEST);
    }

    private void galleryIntent() {
        EasyImage.openGallery(this, GALARY_REQUEST);
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

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                Uri imageUri = Uri.fromFile(imageFile);

                menuImageFile = imageFile;

                Glide.with(RestaurantMenuAddUpdateDishActivity.this)
                        .load(imageUri)
//                        .placeholder(ContextCompat.getDrawable(getActivity(), R.mipmap.img_placeholder))
//                        .error(ContextCompat.getDrawable(getActivity(), R.mipmap.img_placeholder))
                        .into(civDishImage);
            }
        });
    }

    public HashMap<String, File> createFileHashMap() {

        HashMap<String, File> fileMapTemp = new HashMap<String, File>();
        if (menuImageFile != null)
            fileMapTemp.put("dish_image", menuImageFile);

        return fileMapTemp;
    }

    @Override
    public void onCourseTypeReceived(ArrayList<Course> courseList) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.COURSE_TYPES_ARRAYLIST, courseList);

        courseTypeDialogFragment = new CourseTypeDialogFragment();
        courseTypeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
        courseTypeDialogFragment.setArguments(bundle);
        courseTypeDialogFragment.show(getSupportFragmentManager(), "CourseTypeDialogFragment");
    }

    public void setCourseType(Course course) {
        this.course = course;
        etCourseType.setText(course.getName());
    }

    @Override
    protected void onDestroy() {
        restaurantMenuAddUpdateDishMvpPresenter.dispose();
//        restaurantMenuAddUpdateDishMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onMenuItemAdded() {
        finish();
    }

    @Override
    public void addNewCourseType(String courseId, String courseType) {
        courseTypeDialogFragment.addNewCourseType(courseId, courseType);
    }
}