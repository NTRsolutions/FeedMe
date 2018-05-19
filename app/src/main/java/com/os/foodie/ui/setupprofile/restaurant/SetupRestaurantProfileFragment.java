package com.os.foodie.ui.setupprofile.restaurant;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.ApiConstants;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.citycountrylist.Country;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileRequest;
import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.data.prefs.PreferencesHelper;
import com.os.foodie.feature.GpsLocation;
import com.os.foodie.feature.callback.AddCuisineTypeCallback;
import com.os.foodie.feature.callback.GpsLocationCallback;
import com.os.foodie.model.WorkingDay;
import com.os.foodie.ui.adapter.autocomplete.CityCountryListAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.dialogfragment.city.CityListDialogFragment;
import com.os.foodie.ui.dialogfragment.city.CityOnClickListener;
import com.os.foodie.ui.dialogfragment.city2.City2ListDialogFragment;
import com.os.foodie.ui.dialogfragment.city2.City2OnClickListener;
import com.os.foodie.ui.dialogfragment.cuisine.list.CuisineTypeDialogFragment;
import com.os.foodie.ui.dialogfragment.workingdays.WorkingDaysDialogFragment;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ScreenUtils;
import com.os.foodie.utils.TimeFormatUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class SetupRestaurantProfileFragment extends BaseFragment implements AddCuisineTypeCallback, SetupRestaurantProfileMvpView, View.OnClickListener, GpsLocationCallback, City2OnClickListener,CityOnClickListener {

    public static final String TAG = "SetupRestaurantProfileFragment";

    private ImageView ivStep, ivPhotos;
    private GridLayout glPhotos;

    private EditText etCuisineType, etWorkingDays, etAddress, etZipCode, etCountry/*, etCity*/;
    private EditText etOpeningTime, etClosingTime, etMinimumOrderAmount, etDeliveryTime;
    private EditText etDeliveryCharges, etDeliveryZipCodes, etDescription;

    private TextView actvCity;

    private ImageView ivLocation;

    private Spinner spinnerDeliveryType, spinnerPaymentMethods;
    private Button btSave, btCancel;

    private Random random;
    private ProgressDialog progressDialog;
//    private LatLng latLng;

    private ArrayList<Integer> idList;
    private HashMap<Integer, File> fileMap;

    public ArrayList<CuisineType> cuisineTypesChecked;
    public ArrayList<WorkingDay> workingDays;

    private CuisineTypeDialogFragment cuisineTypeDialogFragment;

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int GALARY_REQUEST = 3;
    public static final int CUISINE_TYPES_REQUEST = 4;

    private static final String PLUS_IMAGE = "PLUS_IMAGE";
    private static final String REMOVE_IMAGE = "REMOVE_IMAGE";

    private LatLng latLng;
    private GpsLocation gpsLocation;

    private String cuisionTypesId = "", deleteImageIds = "";
    private RestaurantMainActivity restaurantMainActivity;

    private RestaurantProfileResponse restaurantProfileResponse;
    private List<RestaurantProfileResponse.Image> restaurantImage = new ArrayList<>();

    public static AddCuisineTypeCallback addCuisineTypeCallback;
    private SetupRestaurantProfileMvpPresenter<SetupRestaurantProfileMvpView> setupRestaurantProfileMvpPresenter;

    boolean isEditProfile = false;
    private static final int GPS_REQUEST_CODE = 10;
    String currencySymbol = "";

    private ArrayList<Country> countries;
    String cityIds = "";

//    private int selectedCountryPosition = -1;
//    private int selectedCityPosition = -1;

    private City city;

    ArrayList<com.os.foodie.data.network.model.locationinfo.city.City> cities;

    private ArrayList<com.os.foodie.data.network.model.locationinfo.city.City> selectedDeliveryAreas;

    private CityListDialogFragment cityListDialogFragment;

    public static SetupRestaurantProfileFragment newInstance(RestaurantProfileResponse restaurantProfileResponse) {

        Bundle args = new Bundle();
        if (restaurantProfileResponse != null)
            args.putSerializable("profileResponse", restaurantProfileResponse);
        SetupRestaurantProfileFragment fragment = new SetupRestaurantProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setup_restaurant_profile, container, false);

        setHasOptionsMenu(true);

        restaurantMainActivity = (RestaurantMainActivity) getActivity();

        addCuisineTypeCallback = this;

        initPresenter();
//        setupRestaurantProfileMvpPresenter = new SetupRestaurantProfilePresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        setupRestaurantProfileMvpPresenter.onAttach(this);

        random = new Random();
        gpsLocation = new GpsLocation(getActivity(), this);

//        latLng = new LatLng(0, 0);
        countries = new ArrayList<Country>();
        idList = new ArrayList<Integer>();
        fileMap = new HashMap<Integer, File>();

        cuisineTypesChecked = new ArrayList<CuisineType>();
        workingDays = new ArrayList<WorkingDay>();

        initializeWorkingDays();

        ivStep = (ImageView) view.findViewById(R.id.fragment_setup_restaurant_profile_iv_step);

        ivPhotos = (ImageView) view.findViewById(R.id.fragment_setup_restaurant_profile_iv_photo);
        glPhotos = (GridLayout) view.findViewById(R.id.fragment_setup_restaurant_profile_gl_photos);

        etCuisineType = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_cuisine_type);
        etWorkingDays = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_working_days);
        etAddress = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_address);
        etCountry = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_country);
//        etCity = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_city);
        etZipCode = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_zip);
        etOpeningTime = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_opening_hours);
        etClosingTime = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_closing_hours);
        etDeliveryTime = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_expected_delivery_time);
        etMinimumOrderAmount = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_min_order_amount);
        etDeliveryCharges = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_delivery_charges);
        etDeliveryZipCodes = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_delivery_areas);
        etDescription = (EditText) view.findViewById(R.id.fragment_setup_restaurant_profile_et_description);

        actvCity = (TextView) view.findViewById(R.id.fragment_setup_restaurant_profile_et_city);

        /*actvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedCityPosition = position;
                city = (City) parent.getItemAtPosition(position);
            }
        });
*/
        ivLocation = (ImageView) view.findViewById(R.id.fragment_setup_restaurant_profile_iv_address);
        ivLocation.bringToFront();
        ivLocation.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        spinnerDeliveryType = (Spinner) view.findViewById(R.id.fragment_setup_restaurant_profile_spinner_order_type);
        spinnerPaymentMethods = (Spinner) view.findViewById(R.id.fragment_setup_restaurant_profile_spinner_payment_type);

        btSave = (Button) view.findViewById(R.id.fragment_setup_restaurant_profile_bt_save);
        btCancel = (Button) view.findViewById(R.id.fragment_setup_restaurant_profile_bt_cancel);

//
//        actvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedCityPosition=position;
//            }
//        });

        /*etCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                //setCities(editable.toString());
            }
        });*/

        ivPhotos.setOnClickListener(this);
        etCuisineType.setOnClickListener(this);
        etWorkingDays.setOnClickListener(this);
//        etAddress.setOnClickListener(this);
        ivLocation.setOnClickListener(this);

        etOpeningTime.setOnClickListener(this);
        etClosingTime.setOnClickListener(this);

        etDeliveryZipCodes.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btCancel.setOnClickListener(this);

        actvCity.setOnClickListener(this);

        setupRestaurantProfileMvpPresenter.dismissDialog();
    //    setupRestaurantProfileMvpPresenter.getCityCountryList();

        setupRestaurantProfileMvpPresenter.getCityList();

        setProfileData();

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        setupRestaurantProfileMvpPresenter = new SetupRestaurantProfilePresenter(appDataManager, compositeDisposable);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == ivPhotos.getId()) {

            if (((RestaurantMainActivity) getActivity()).hasPermission(Manifest.permission.CAMERA) && ((RestaurantMainActivity) getActivity()).hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && ((RestaurantMainActivity) getActivity()).hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                selectImage();
            } else {
                ((RestaurantMainActivity) getActivity()).requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

        } else if (v.getId() == ivLocation.getId()) {
//        } else if (v.getId() == etAddress.getId()) {

//            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//            try {
//                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
//            } catch (GooglePlayServicesRepairableException e) {
//                e.printStackTrace();
//            } catch (GooglePlayServicesNotAvailableException e) {
//                e.printStackTrace();
//            }

            openPlaceAutocomplete();

        } else if (v.getId() == etCuisineType.getId()) {

            setupRestaurantProfileMvpPresenter.onCuisineTypeClick();

        } else if (v.getId() == etWorkingDays.getId()) {

            openWorkingDaysDialogFragment();

        } else if (v.getId() == etOpeningTime.getId()) {

            setupRestaurantProfileMvpPresenter.setTime(etOpeningTime, etClosingTime, true);

        } else if (v.getId() == etClosingTime.getId()) {

            setupRestaurantProfileMvpPresenter.setTime(etClosingTime, etOpeningTime, false);

        } else if (v.getTag() != null && v.getTag().equals(PLUS_IMAGE)) {

//            selectImage();
            ivPhotos.callOnClick();

        } else if (v.getTag() != null && v.getTag().equals(REMOVE_IMAGE)) {

            RelativeLayout relativeLayout = ((RelativeLayout) v.getParent());

            int position = (int) relativeLayout.getChildAt(0).getTag();

            Log.d("getTag", ">>" + ((RelativeLayout) v.getParent()).getTag());

            if (glPhotos.getChildCount() == 2) {

                idList.clear();
                fileMap.clear();

                glPhotos.removeAllViews();
                ivPhotos.setVisibility(View.VISIBLE);

            } else {

                idList.remove((Integer) ((RelativeLayout) v.getParent()).getTag());
                fileMap.remove(((RelativeLayout) v.getParent()).getTag());
                glPhotos.removeView((RelativeLayout) v.getParent());

                if (glPhotos.getChildCount() == 2) {
                    RelativeLayout view = (RelativeLayout) glPhotos.getChildAt(0);
                    view.getChildAt(1).setVisibility(View.GONE);
                }
            }

            if (position < restaurantImage.size()) {
                restaurantImage.remove(position);
                String imageId = restaurantProfileResponse.getResponse().getRestaurantDetail().getImages().get(position).getImageId();
                if (deleteImageIds.length() == 0)
                    deleteImageIds = imageId;
                else
                    deleteImageIds = deleteImageIds + "," + imageId;

            }

            Log.d("deleteImageIds", ">>" + deleteImageIds);
            Log.d("idList", ">>" + idList.size());
            Log.d("fileMap", ">>" + fileMap.size());

            if (glPhotos.getChildCount() == 5) {
                glPhotos.getChildAt(4).setVisibility(View.VISIBLE);
            }

        } else if (v.getId() == btSave.getId()) {

            HashMap<String, File> fileHashMap = createFileHashMap();
            SetupRestaurantProfileRequest restaurantProfileRequest = createRestaurantProfileRequest();


            if (city == null || city.getName() == null || !city.getName().equalsIgnoreCase(actvCity.getText().toString())) {

                setupRestaurantProfileMvpPresenter.saveRestaurantProfile(restaurantProfileRequest, fileHashMap, isEditProfile, null);

            } else {

                setupRestaurantProfileMvpPresenter.saveRestaurantProfile(restaurantProfileRequest, fileHashMap, isEditProfile, city);

            }

        } else if (v.getId() == btCancel.getId()) {
            restaurantMainActivity.navigateToShowRestaurantProfile();

        } else if (v.getId() == etDeliveryZipCodes.getId()) {

            openCitySelector(cities);
        }else if(v.getId() == actvCity.getId()){
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.CITY_LIST, cities);
            bundle.putParcelable(AppConstants.CITY_LISTENER, this);

            cityListDialogFragment = new CityListDialogFragment();
            cityListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
            cityListDialogFragment.setArguments(bundle);
            cityListDialogFragment.show(getChildFragmentManager(), "CityListDialogFragment");

        }
    }

    public void openPlaceAutocomplete() {

        if (NetworkUtils.isNetworkConnected(getActivity())) {

            try {

                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .build(getActivity());

                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

            } catch (GooglePlayServicesRepairableException e) {

                setupRestaurantProfileMvpPresenter.setError(getResources().getString(R.string.google_play_service_reinstall_error));
                Log.d("RepairableException", ">>" + e.getMessage().toString());

            } catch (GooglePlayServicesNotAvailableException e) {
                setupRestaurantProfileMvpPresenter.setError(getResources().getString(R.string.google_play_service_not_avail_error));
                Log.d("NotAvailableException", ">>" + e.getMessage().toString());
            }

        } else {
            setupRestaurantProfileMvpPresenter.setError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_blank, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == PLACE_PICKER_REQUEST) {
//
//            if (resultCode == RESULT_OK) {
//
//                Place place = PlacePicker.getPlace(getActivity(), data);
//
//                Log.d("place", ">>" + place.getAddress());
//                Log.d("LatLng", ">>" + place.getLatLng());
//
//                setupRestaurantProfileMvpPresenter.getGeocoderLocationAddress(getActivity(), place.getLatLng());
//            }
//        }

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(getActivity(), data);

                Log.i("onActivityResult", "getName: " + place.getName());
                Log.i("onActivityResult", "getAddress: " + place.getAddress());
                Log.i("onActivityResult", "getLocale: " + place.getLocale());
                Log.i("onActivityResult", "getLatLng: " + place.getLatLng().toString());

                latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

                setupRestaurantProfileMvpPresenter.getGeocoderLocationAddress(getActivity(), place.getLatLng());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.i("onActivityResult", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {

                Log.i("onActivityResult", ">>RESULT_CANCELED");
            }
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                addImageView(imageFile);
            }

        });
    }

//    /*   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//            Log.d("requestCode", ">>" + requestCode);
//            Log.d("resultCode", ">>" + resultCode);
//
//            if (requestCode == PLACE_PICKER_REQUEST) {
//
//                if (resultCode == RESULT_OK) {
//
//                    Place place = PlacePicker.getPlace(getActivity(), data);
//
//                    Log.d("place", ">>" + place.getAddress());
//                    Log.d("LatLng", ">>" + place.getLatLng());
//
//    //                latLng = place.getLatLng();
//    //                etAddress.setText(place.getAddress());
//
//                    setupRestaurantProfileMvpPresenter.getGeocoderLocationAddress(getActivity(), place.getLatLng());
//
//    //                etZipCode.setText(place.getLocale());
//                }
//
//            } else if (requestCode == CAMERA_REQUEST) {
//
//                if (resultCode == RESULT_OK) {
//                    onCaptureImageResult(data);
//                }
//
//            } else if (requestCode == GALARY_REQUEST) {
//
//                if (resultCode == RESULT_OK) {
//                    onSelectFromGalleryResult(data);
//                }
//            } else if (requestCode == CUISINE_TYPES_REQUEST) {
//
//                Log.d("CUISINE_TYPES_REQUEST", ">>Result");
//
//                if (resultCode == RESULT_OK) {
//
//                    ArrayList<CuisineType> cuisineTypesChecked = data.getParcelableArrayListExtra(AppConstants.CUISINE_TYPES_ARRAYLIST);
//
//                    for (CuisineType cuisineType : cuisineTypesChecked) {
//                        Log.d("CuisineType", ">>" + cuisineType.getName());
//                    }
//                }
//            }
//        }
//    */

    private void selectImage() {

        final CharSequence[] items = {getString(R.string.alert_dialog_text_photo_picker_camera), getString(R.string.alert_dialog_text_photo_picker_gallery), getString(R.string.alert_dialog_text_photo_picker_cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
        EasyImage.openCamera(SetupRestaurantProfileFragment.this, CAMERA_REQUEST);
    }

    private void galleryIntent() {
        EasyImage.openGallery(SetupRestaurantProfileFragment.this, GALARY_REQUEST);
    }

    void addImageView(File file) {

        ivPhotos.setVisibility(View.GONE);

        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 3), (ScreenUtils.getScreenWidth(getActivity()) / 3)));

        ImageView imageView = new ImageView(getActivity());

        imageView.setLayoutParams(new RelativeLayout.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 3), (ScreenUtils.getScreenWidth(getActivity()) / 3)));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Uri imageUri = Uri.fromFile(file);

        Glide.with(getActivity())
                .load(imageUri)
                .into(imageView);

//        imageView.setTag(imageView);
//        imageView.setOnClickListener(this);

        relativeLayout.addView(imageView);

        ImageView imageView1 = new ImageView(getActivity());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 16), (ScreenUtils.getScreenWidth(getActivity()) / 16));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        imageView1.setLayoutParams(layoutParams);
        imageView1.setImageResource(R.mipmap.ic_close);
        imageView1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black_transparent));
        imageView1.setTag(REMOVE_IMAGE);
        imageView1.setOnClickListener(this);

        relativeLayout.addView(imageView1);

        if (glPhotos.getChildCount() == 0) {

            ImageView imageViewAdd = new ImageView(getActivity());

            imageViewAdd.setLayoutParams(new ViewGroup.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 3), (ScreenUtils.getScreenWidth(getActivity()) / 3)));
            imageViewAdd.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewAdd.setImageResource(R.mipmap.img_plus_center);
            imageViewAdd.setTag(PLUS_IMAGE);
            imageViewAdd.setOnClickListener(this);
            glPhotos.addView(imageViewAdd);
        }

        Integer id = random.nextInt(1000);

        relativeLayout.setTag((Integer) id);

        idList.add(id);
        fileMap.put(id, file);

        glPhotos.addView(relativeLayout, glPhotos.getChildCount() - 1);

        if (glPhotos.getChildCount() == 6) {

            glPhotos.getChildAt(5).setVisibility(View.GONE);
        }

        Log.d("idList", ">>" + idList.size());
        Log.d("fileMap", ">>" + fileMap.size());

        imageView.setTag(glPhotos.getChildCount() - 2);

        if (glPhotos.getChildCount() > 2) {
            RelativeLayout view = (RelativeLayout) glPhotos.getChildAt(0);
            view.getChildAt(1).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCuisineTypeListReceive(ArrayList<CuisineType> cuisineTypes) {

        for (int i = 0; i < cuisineTypesChecked.size(); i++) {

            for (int j = 0; j < cuisineTypes.size(); j++) {

                if (cuisineTypesChecked.get(i).getId().equals(cuisineTypes.get(j).getId())) {

                    cuisineTypes.get(j).setChecked(true);
                    break;
                }
            }
        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.CUISINE_TYPES_ARRAYLIST, cuisineTypes);
        bundle.putBoolean(AppConstants.IS_FAB_NEEDED, true);

        cuisineTypeDialogFragment = new CuisineTypeDialogFragment();
        cuisineTypeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
//        cuisineTypeDialogFragment.setTargetFragment(cuisineTypeDialogFragment, CUISINE_TYPES_REQUEST);
        cuisineTypeDialogFragment.setArguments(bundle);
        cuisineTypeDialogFragment.show(getChildFragmentManager(), "CuisineTypeDialogFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RestaurantMainActivity) getActivity()).setTitle(getResources().getString(R.string.title_fragment_restaurant_profile));
    }

    @Override
    public void onRestaurantProfileSaved() {

        restaurantMainActivity.navigateToShowRestaurantProfile();
        AppController.get(getActivity()).getAppDataManager().setCurrentUserInfoInitialized(true);

//        Intent intent = new Intent(getActivity(), RestaurantMainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }

    public void setCuisineTypeList(ArrayList<CuisineType> cuisineTypesChecked) {
//        ArrayList<CuisineType> cuisineTypesChecked = getParcelableArrayListExtra(AppConstants.CUISINE_TYPES_ARRAYLIST);

        this.cuisineTypesChecked = cuisineTypesChecked;

        String cuisionTypes = "";

        for (int i = 0; i < cuisineTypesChecked.size(); i++) {

            if (i == 0) {
                cuisionTypes = cuisineTypesChecked.get(i).getName();
                cuisionTypesId = cuisineTypesChecked.get(i).getId();
            } else {
                cuisionTypes = cuisionTypes + "," + cuisineTypesChecked.get(i).getName();
                cuisionTypesId = cuisionTypesId + "," + cuisineTypesChecked.get(i).getId();
            }
        }

        etCuisineType.setText(cuisionTypes);
    }

    public void setWorkingDays(ArrayList<WorkingDay> checkedDays) {

//        String[] checkedDays = etWorkingDays.getText().toString().split(",");
//
        workingDays.clear();
        workingDays.addAll(checkedDays);

        Log.d("checkedDays", ">>" + checkedDays.size());
        Log.d("workingDays", ">>" + workingDays.size());

//        for (int i = 0; i < checkedDays.size(); i++) {
//
//            for (int j = 0; j < workingDays.size(); j++) {
//
//                if (checkedDays.get(i).getDay().equals(workingDays.get(j).getDay())) {
//
//                    workingDays.get(j).setChecked(true);
//
//                    break;
//                }
//            }
//        }

        String days = "";
        boolean firstChecked = true;

        for (int i = 0; i < checkedDays.size(); i++) {
            Log.d("checkedDays", ">>" + checkedDays.get(i).getDay());

            if (checkedDays.get(i).isChecked()) {
                Log.d("checkedDays", ">>isChecked");

                if (firstChecked) {
                    firstChecked = false;
                    days = checkedDays.get(i).getDay();
                } else {
                    days = days + "," + checkedDays.get(i).getDay();
                }
            }
        }

        Log.d("days", ">>" + days);

        etWorkingDays.setText(days);
    }

    public SetupRestaurantProfileRequest createRestaurantProfileRequest() {

        PreferencesHelper preferencesHelper = setupRestaurantProfileMvpPresenter.getPreferencesHelper();

        SetupRestaurantProfileRequest restaurantProfileRequest = new SetupRestaurantProfileRequest();

        restaurantProfileRequest.setRestaurantId(preferencesHelper.getCurrentUserId());
        restaurantProfileRequest.setCuisineType(cuisionTypesId);
        restaurantProfileRequest.setWorkingDays(etWorkingDays.getText().toString());
        //  restaurantProfileRequest.setWorkingDays(days);

        restaurantProfileRequest.setAddress(etAddress.getText().toString());
        restaurantProfileRequest.setCountry(etCountry.getText().toString());
//        restaurantProfileRequest.setCity(etCity.getText().toString());
//        restaurantProfileRequest.setCity(actvCity.getText().toString());

        if (city == null || city.getName() == null || !city.getName().equalsIgnoreCase(actvCity.getText().toString())) {
            restaurantProfileRequest.setCity(null);
        } else {
            restaurantProfileRequest.setCity(city.getCityId());
        }

        restaurantProfileRequest.setZipCode(etZipCode.getText().toString());

        if (!etOpeningTime.getText().toString().isEmpty()) {
            restaurantProfileRequest.setOpeningTime(TimeFormatUtils.changeTimeFormat(etOpeningTime.getText().toString(), "hh:mm a", "HH:mm"));
        } else {
            restaurantProfileRequest.setOpeningTime(null);
        }

        if (!etClosingTime.getText().toString().isEmpty()) {
            restaurantProfileRequest.setClosingTime(TimeFormatUtils.changeTimeFormat(etClosingTime.getText().toString(), "hh:mm a", "HH:mm"));
        } else {
            restaurantProfileRequest.setClosingTime(null);
        }
        restaurantProfileRequest.setMinOrderAmount(etMinimumOrderAmount.getText().toString());
        restaurantProfileRequest.setDeliveryTime(etDeliveryTime.getText().toString());
        restaurantProfileRequest.setDeliveryCharge(etDeliveryCharges.getText().toString());
//        restaurantProfileRequest.setDeliveryZipcode(etDeliveryZipCodes.getText().toString());

        restaurantProfileRequest.setDescription(etDescription.getText().toString());
//        restaurantProfileRequest.setCurrency(CommonUtils.dataEncode("₹"));
        restaurantProfileRequest.setCurrency(CommonUtils.dataEncode(currencySymbol));

//        try {
//            restaurantProfileRequest.setCurrency(URLEncoder.encode(currencySymbol, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        restaurantProfileRequest.setDeliveryType((spinnerDeliveryType.getSelectedItem()) + "");
//        restaurantProfileRequest.setDeliveryType((spinnerDeliveryType.getSelectedItemPosition() + 1) + "");
//        restaurantProfileRequest.setPaymentMethod((spinnerPaymentMethods.getSelectedItemPosition() + 1) + "");

        restaurantProfileRequest.setDeliveryType(getOrderType());
        restaurantProfileRequest.setPaymentMethod(getPaymentType());

        if (restaurantProfileResponse != null) {
            restaurantProfileRequest.setDeliveryZipcode(restaurantProfileResponse.getResponse().getRestaurantDetail().getDeliveryCitiesIds());
            restaurantProfileRequest.setLatitude(restaurantProfileResponse.getResponse().getRestaurantDetail().getLatitude());
            restaurantProfileRequest.setLongitude(restaurantProfileResponse.getResponse().getRestaurantDetail().getLongitude());

        } else {
            restaurantProfileRequest.setDeliveryZipcode(cityIds);
            restaurantProfileRequest.setLatitude(latLng.latitude + "");
            restaurantProfileRequest.setLongitude(latLng.longitude + "");
        }

        restaurantProfileRequest.setDeleteImageId(deleteImageIds);

        Log.d("deleteImageIds", ">>" + deleteImageIds);

        return restaurantProfileRequest;
    }

    public HashMap<String, File> createFileHashMap() {

        HashMap<String, File> fileMapTemp = new HashMap<String, File>();

        for (int i = 0; i < fileMap.size(); i++) {
            Log.d("idList", ">>" + "restaurant_image_" + (i + 1));
            fileMapTemp.put("restaurant_image_" + (i + 1), fileMap.get(idList.get(i)));

            Log.d("fileMapTemp", ">>" + fileMap.get(idList.get(i)));
        }


        return fileMapTemp;
    }

    public void setError(@StringRes int resId) {
        setupRestaurantProfileMvpPresenter.setError(resId);
    }

    public void setError(String message) {
        setupRestaurantProfileMvpPresenter.setError(message);
    }

    public void just() {

        HashMap<String, File> fileMapTemp = new HashMap<String, File>();

        for (int i = 0; i < fileMap.size(); i++) {
            Log.d("idList", ">>" + "restaurant_image_" + (i + 1));
            fileMapTemp.put("restaurant_image_" + (i + 1), fileMap.get(idList.get(i)));
        }

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject("{" +
                    "  \"restaurant_id\": \"7\"," +
                    "  \"cuisine_type\": \"1,2,3,4\"," +
                    "  \"min_order_amount\": \"70\"," +
                    "  \"payment_method\": \"1,2\"," +
                    "  \"delivery_charge\": \"100\"," +
                    "  \"delivery_time\": \"15 miniutes\"," +
                    "  \"working_days\": \"6\"," +
                    "  \"opening_time\": \"6:00\"," +
                    "  \"closing_time\": \"9:00\"," +
                    "  \"delivery_type\": \"1\"," +
                    "  \"address\": \"Test Address\"," +
                    "  \"zip_code\": \"302015\"," +
                    "  \"delivery_zipcode\": \"302015,7383943\"," +
                    "  \"description\": \"Test Description\"," +
                    "  \"latitude\": \"78.38953758\"," +
                    "  \"longitude\": \"24.9373474\"" +
                    "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Rx2AndroidNetworking.upload(ApiConstants.BASE_URL + "set_restaurant_profile")
                .addMultipartFile(fileMapTemp)
                .addMultipartParameter("data", jsonObject.toString())
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {

                        Log.d("JSONObject", ">>" + jsonObject.toString());
                    }
                });
    }

    public Map getMap() {

        Map<String, File> fileMap = new Map<String, File>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public File get(Object key) {
                return null;
            }

            @Override
            public File put(String key, File value) {
                return null;
            }

            @Override
            public File remove(Object key) {
                return null;
            }

            @Override
            public void putAll(@NonNull Map<? extends String, ? extends File> m) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set<String> keySet() {
                return null;
            }

            @NonNull
            @Override
            public Collection<File> values() {
                return null;
            }

            @NonNull
            @Override
            public Set<Entry<String, File>> entrySet() {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };

        return fileMap;
    }

    @Override
    public void addNewCuisineType(String cuisineId, String cuisineType) {
        cuisineTypeDialogFragment.addNewCuisineType(cuisineId, cuisineType);
    }

    public void openWorkingDaysDialogFragment() {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.WORKING_DAYS_ARRAYLIST, workingDays);

        WorkingDaysDialogFragment workingDaysDialogFragment = new WorkingDaysDialogFragment();
        workingDaysDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
        workingDaysDialogFragment.setArguments(bundle);
        workingDaysDialogFragment.show(getChildFragmentManager(), "WorkingDaysDialogFragment");
    }

    public void initializeWorkingDays() {

        String[] weekDays = getResources().getStringArray(R.array.working_days);

        for (int i = 0; i < weekDays.length; i++) {
            workingDays.add(new WorkingDay(weekDays[i], false));
        }
    }

    @Override
    public void setAllAddress(ArrayList<Address> addresses) {

        for (int i = 0; i < addresses.size(); i++) {

            Log.d("getCountryName", ">>" + addresses.get(i).getCountryName());
            Log.d("getSubAdminArea", ">>" + addresses.get(i).getSubAdminArea());
            Log.d("getLocality", ">>" + addresses.get(i).getLocality());
            Log.d("getPostalCode", ">>" + addresses.get(i).getPostalCode());
//            Log.d("getAddressLine", ">>" + addresses.get(i).getAddressLine(addresses.get(i).getMaxAddressLineIndex()-1));
        }

        Address address = addresses.get(0);
        String fullAddress = "";

        for (int i = 0; i < 2; i++) {

            Log.d("getAddressLine" + i, ">>" + address.getAddressLine(i));

            if (i == 0)
                fullAddress = address.getAddressLine(i);
//            else if (i <= 1)
            else if (i < address.getMaxAddressLineIndex())
                fullAddress += ", " + address.getAddressLine(i);
            else
                break;
        }

        if (restaurantProfileResponse != null) {

            restaurantProfileResponse.getResponse().getRestaurantDetail().setLatitude(address.getLatitude() + "");
            restaurantProfileResponse.getResponse().getRestaurantDetail().setLongitude(address.getLongitude() + "");

        } else {

            latLng = new LatLng(address.getLatitude(), address.getLongitude());
        }

        etAddress.setText(fullAddress);

        if (address.getCountryCode() != null && !address.getCountryCode().isEmpty()) {
            currencySymbol = Currency.getInstance(new Locale("", address.getCountryCode())).getSymbol();
            Log.d("currencySymbol", ">>" + currencySymbol);
//            try {
//                currencySymbol = URLEncoder.encode(currencySymbol,"UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        }


        if (address.getCountryName() != null && !address.getCountryName().isEmpty()) {

//            etCountry.setClickable(true);
//            etCountry.setEnabled(false);
//            etCountry.setFocusable(false);
//            etCountry.setLongClickable(false);

            etCountry.setText(address.getCountryName());

        } else {

//            etCountry.setClickable(false);
//            etCountry.setEnabled(true);
            etCountry.setText("");
//            etCountry.setFocusable(true);
//            etCountry.setLongClickable(true);
        }

//        if (address.getSubAdminArea() != null && !address.getSubAdminArea().isEmpty()) {
//
////            etCity.setClickable(true);
////            etCity.setEnabled(false);
//            etCity.setText(address.getSubAdminArea());
//
////            etCity.setFocusable(false);
////            etCity.setLongClickable(false);
//
//        } else if (address.getLocality() != null && !address.getLocality().isEmpty()) {
//
////            etCity.setClickable(true);
////            etCity.setEnabled(false);
//            etCity.setText(address.getLocality());
//
//        } else {
//
////            etCity.setClickable(false);
////            etCity.setEnabled(true);
//            etCity.setText("");
//
////            etCity.setFocusable(true);
////            etCity.setLongClickable(true);
//        }

        if (address.getPostalCode() != null && !address.getPostalCode().isEmpty()) {

//            etZipCode.setClickable(true);
//            etZipCode.setEnabled(false);
//            etZipCode.setFocusable(false);
//            etZipCode.setLongClickable(false);

            etZipCode.setText(address.getPostalCode());

            Log.d("getPostalCode", "Not Null");

        } else {

//            etZipCode.setClickable(false);
//            etZipCode.setEnabled(true);
            etZipCode.setText("");
//            etZipCode.setFocusable(true);
//            etZipCode.setLongClickable(true);
            Log.d("getPostalCode", "null");
        }

//        Log.d("getPostalCode", ">>" + address.getPostalCode());

     //   setCities(etCountry.getText().toString());
    }

    /*public void setCities(String editable) {

        for (int i = 0; i < countries.size(); i++) {

            if (countries.get(i).getName() != null && !countries.get(i).getName().isEmpty() && countries.get(i).getName().equalsIgnoreCase(editable)) {

                ArrayList<City> cities = (ArrayList<City>) countries.get(i).getCity();

                CityCountryListAdapter adapter = new CityCountryListAdapter(getActivity(), cities);

                actvCity.setThreshold(2);
                actvCity.setAdapter(adapter);

//                selectedCountryPosition = i;

                break;
            }
        }
    }*/

    @Override
    public void setCityCountryListAdapter(ArrayList<Country> countries) {
        this.countries = countries;

       // setCities(etCountry.getText().toString());

    /*    if (restaurantProfileResponse != null) {

            for (int i = 0; i < countries.size(); i++) {

                boolean flag = false;

                for (int j = 0; j < countries.get(i).getCity().size(); j++) {

                    if (countries.get(i).getCity().get(j).getCityId().equals(restaurantProfileResponse.getResponse().getRestaurantDetail().getCityId())) {

                        actvCity.setText(countries.get(i).getCity().get(j).getCityName());

                        city = countries.get(i).getCity().get(j);
//                    selectedCountryPosition = i;
//                    selectedCityPosition = j;

                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    break;
                }
            }
        }*/
    }

    @Override
    public void openCitySelector(ArrayList<com.os.foodie.data.network.model.locationinfo.city.City> cities) {

        if (restaurantProfileResponse!=null && restaurantProfileResponse.getResponse().getRestaurantDetail().getDeliveryCitiesIds() != null && !restaurantProfileResponse.getResponse().getRestaurantDetail().getDeliveryCitiesIds().isEmpty()) {

            String[] selectedCityIds = restaurantProfileResponse.getResponse().getRestaurantDetail().getDeliveryCitiesIds().split(",");

            for (int i = 0; i < selectedCityIds.length; i++) {

                for (int j = 0; j < cities.size(); j++) {

                    if (cities.get(j).getCityId().equals(selectedCityIds[i])) {

                        cities.get(j).setChecked(true);
                        break;
                    }
                }
            }
        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.CITY_LIST, cities);
        bundle.putParcelable(AppConstants.CITY_LISTENER, this);

        City2ListDialogFragment cityListDialogFragment = new City2ListDialogFragment();
        cityListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
        cityListDialogFragment.setArguments(bundle);
        cityListDialogFragment.show(getActivity().getSupportFragmentManager(), "City2ListDialogFragment");
    }

    @Override
    public void onCityListReceived(ArrayList<com.os.foodie.data.network.model.locationinfo.city.City> cities) {
        this.cities = cities;

        if (restaurantProfileResponse != null) {

            for (int i = 0; i < cities.size(); i++) {


                if (cities.get(i).getCityId().equals(restaurantProfileResponse.getResponse().getRestaurantDetail().getCityId())) {
                    city = cities.get(i);
                    actvCity.setText(city.getName());
                    break;
                }
            }
        }

    }

    public void setProfileData() {

        Bundle extra = getArguments();

        if (extra != null) {

            if ((RestaurantProfileResponse) extra.getSerializable("profileResponse") != null) {

                restaurantProfileResponse = (RestaurantProfileResponse) extra.getSerializable("profileResponse");

                isEditProfile = true;

                currencySymbol = restaurantProfileResponse.getResponse().getRestaurantDetail().getCurrency();
//                currencySymbol = "₹";

                currencySymbol = CommonUtils.dataDecode(currencySymbol);
//                try {
////                    currencySymbol = URLEncoder.encode(currencySymbol,"UTF-8");
//                    currencySymbol = URLDecoder.decode(currencySymbol, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }

                RestaurantProfileResponse.RestaurantDetail restaurantDetail = restaurantProfileResponse.getResponse().getRestaurantDetail();

                restaurantImage.addAll(restaurantDetail.getImages());

                for (int i = 0; i < restaurantDetail.getImages().size(); i++) {
                    addImageView(restaurantDetail.getImages().get(i).getUrl());
                }

                if (glPhotos.getChildCount() == 2) {
                    RelativeLayout view = (RelativeLayout) glPhotos.getChildAt(0);
                    view.getChildAt(1).setVisibility(View.GONE);
                }

                etDescription.setText(restaurantDetail.getDescription());
//                etDeliveryZipCodes.setText(restaurantDetail.getDeliveryZipcode());
                etDeliveryZipCodes.setText(restaurantDetail.getDeliveryCitiesNames());
                etDeliveryCharges.setText(restaurantDetail.getDeliveryCharge());
                etMinimumOrderAmount.setText(restaurantDetail.getMinOrderAmount());
                etDeliveryTime.setText(restaurantDetail.getDeliveryTime());
                etOpeningTime.setText(TimeFormatUtils.changeTimeFormat(restaurantDetail.getOpeningTime(), "HH:mm:ss", "hh:mm a"));
                etClosingTime.setText(TimeFormatUtils.changeTimeFormat(restaurantDetail.getClosingTime(), "HH:mm:ss", "hh:mm a"));
                etZipCode.setText(restaurantDetail.getZipCode());
//                etCity.setText(restaurantDetail.getCityName());
//                actvCity.setText(restaurantDetail.getCityName());
                etCountry.setText(restaurantDetail.getCountryName());
                etAddress.setText(restaurantDetail.getAddress());
                etWorkingDays.setText(restaurantDetail.getWorkingDays());
                etCuisineType.setText(restaurantDetail.getCuisineType());

                //setCities(etCountry.getText().toString());

                if (restaurantDetail.getDeliveryType().equalsIgnoreCase("Pick Only"))
                    spinnerDeliveryType.setSelection(0);
                else if (restaurantDetail.getDeliveryType().equalsIgnoreCase("Deliver"))
                    spinnerDeliveryType.setSelection(1);
                else
                    spinnerDeliveryType.setSelection(2);

                if (restaurantDetail.getPaymentMethod().equalsIgnoreCase("Online"))
                    spinnerPaymentMethods.setSelection(0);
                else if (restaurantDetail.getPaymentMethod().equalsIgnoreCase("Cash On Delivery"))
                    spinnerPaymentMethods.setSelection(1);
                else
                    spinnerPaymentMethods.setSelection(2);

                String cuisineTypeName[] = restaurantDetail.getCuisineType().split(",");
                String cuisineTypeNId[] = restaurantDetail.getCuisineTypeId().split(",");

                cuisionTypesId = restaurantDetail.getCuisineTypeId();

                for (int i = 0; i < cuisineTypeName.length; i++) {
                    CuisineType cuisineType = new CuisineType();
                    cuisineType.setName(cuisineTypeName[i]);
                    cuisineType.setId(cuisineTypeNId[i]);
                    cuisineTypesChecked.add(cuisineType);
                }

                for (int i = 0; i < workingDays.size(); i++) {
                    if (restaurantDetail.getWorkingDays().contains(workingDays.get(i).getDay()))
                        workingDays.get(i).setChecked(true);
                }

            } else {

                btCancel.setVisibility(View.INVISIBLE);
                ivStep.setVisibility(View.VISIBLE);

                checkAndRequestGpsLocation();
            }
        }
    }

    public void checkAndRequestGpsLocation() {

        if (gpsLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            if (((RestaurantMainActivity) getActivity()).hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {

                requestGpsLocation();

            } else {
//                ((RestaurantMainActivity) getActivity()).requestPermissionsSafely(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
            }

        } else {

            showGpsRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("onRequestPersResult", ">>Called");

        if (grantResults != null && grantResults[0] == PermissionChecker.PERMISSION_GRANTED && requestCode == GPS_REQUEST_CODE) {
            Log.d("GPS_REQUEST_CODE", ">>" + GPS_REQUEST_CODE);
            requestGpsLocation();
        }
    }

    public void showGpsRequest() {

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(getActivity());
        mAlertDialog.setTitle("Location Service Disabled")
                .setMessage("Please enable location service")
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }).show();
    }

    public void requestGpsLocation() {
        progressDialog = CommonUtils.showLoadingDialog(getActivity(), getString(R.string.progress_dialog_tv_message_text_address_fetch));
        gpsLocation.requestGpsLocation();
    }

    void addImageView(String url) {

        ivPhotos.setVisibility(View.GONE);

        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 3), (ScreenUtils.getScreenWidth(getActivity()) / 3)));

        ImageView imageView = new ImageView(getActivity());

        imageView.setLayoutParams(new RelativeLayout.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 3), (ScreenUtils.getScreenWidth(getActivity()) / 3)));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(getActivity())
                .load(url)
                .into(imageView);

        relativeLayout.addView(imageView);

        ImageView imageView1 = new ImageView(getActivity());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 16), (ScreenUtils.getScreenWidth(getActivity()) / 16));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        imageView1.setLayoutParams(layoutParams);
        imageView1.setImageResource(R.mipmap.ic_close);
        imageView1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black_transparent));
        imageView1.setTag(REMOVE_IMAGE);
        imageView1.setOnClickListener(this);

        relativeLayout.addView(imageView1);

        if (glPhotos.getChildCount() == 0) {

            ImageView imageViewAdd = new ImageView(getActivity());

            imageViewAdd.setLayoutParams(new ViewGroup.LayoutParams((ScreenUtils.getScreenWidth(getActivity()) / 3), (ScreenUtils.getScreenWidth(getActivity()) / 3)));
            imageViewAdd.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewAdd.setImageResource(R.mipmap.img_plus_center);
            imageViewAdd.setTag(PLUS_IMAGE);
            imageViewAdd.setOnClickListener(this);
            glPhotos.addView(imageViewAdd);
        }

        Integer id = random.nextInt(1000);

        relativeLayout.setTag((Integer) id);

        glPhotos.addView(relativeLayout, glPhotos.getChildCount() - 1);

        if (glPhotos.getChildCount() == 6) {

            glPhotos.getChildAt(5).setVisibility(View.GONE);
        }

        imageView.setTag(glPhotos.getChildCount() - 2);
        Log.d("idList", ">>" + idList.size());
        Log.d("fileMap", ">>" + fileMap.size());
    }

    public String getOrderType() {
        String orderType = "";


        if (spinnerDeliveryType.getSelectedItemPosition() == 0) {
            orderType = "Pick only";
        } else if (spinnerDeliveryType.getSelectedItemPosition() == 1) {
            orderType = "Deliver";
        } else {
            orderType = "Pick and deliver";
        }

        return orderType;
    }

    public String getPaymentType() {

        String paymentType = "";

        if (spinnerPaymentMethods.getSelectedItemPosition() == 0) {
            paymentType = "Online";
        } else if (spinnerPaymentMethods.getSelectedItemPosition() == 1) {
            paymentType = "Cash on delivery";
        } else {
            paymentType = "Online, Cash on delivery";
        }

        Log.d("paymentType", ">>" + paymentType);
        return paymentType;
    }

    @Override
    public void onDestroyView() {
        setupRestaurantProfileMvpPresenter.dispose();
//        setupRestaurantProfileMvpPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("Now Observe Location", ">>" + location.toString());

        progressDialog.dismiss();
        progressDialog.cancel();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        setupRestaurantProfileMvpPresenter.getGeocoderLocationAddress(getActivity(), latLng);
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
    public void onClick(ArrayList<com.os.foodie.data.network.model.locationinfo.city.City> citiesChecked) {

         cityIds = "";
        String cityNames = "";

        for (int i = 0; i < citiesChecked.size(); i++) {

            if (i == 0) {

                cityIds = citiesChecked.get(i).getCityId();
                cityNames = citiesChecked.get(i).getName();

            } else {

                cityIds += "," + citiesChecked.get(i).getCityId();
                cityNames += "," + citiesChecked.get(i).getName();

            }
            Log.d("citiesChecked " + i, ">>" + citiesChecked.get(i).getName());
        }
        if(restaurantProfileResponse!=null) {
            restaurantProfileResponse.getResponse().getRestaurantDetail().setDeliveryCitiesIds(cityIds);
            restaurantProfileResponse.getResponse().getRestaurantDetail().setDeliveryCitiesNames(cityNames);
        }

        etDeliveryZipCodes.setText(cityNames);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public final static Creator<SetupRestaurantProfileFragment> CREATOR = new Creator<SetupRestaurantProfileFragment>() {

        @SuppressWarnings({"unchecked"})
        @Override
        public SetupRestaurantProfileFragment createFromParcel(Parcel source) {
            return new SetupRestaurantProfileFragment();
        }

        @Override
        public SetupRestaurantProfileFragment[] newArray(int size) {
            return new SetupRestaurantProfileFragment[size];
        }
    };

    @Override
    public void onClick(com.os.foodie.data.network.model.locationinfo.city.City city) {
        this.city = city;
        actvCity.setText(city.getName());
        cityListDialogFragment.dismiss();
    }
}