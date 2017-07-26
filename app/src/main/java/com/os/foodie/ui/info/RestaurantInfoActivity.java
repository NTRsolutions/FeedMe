package com.os.foodie.ui.info;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.model.RestaurantDetails;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantInfoActivity extends BaseActivity implements RestaurantInfoMvpView, View.OnClickListener {

    private TextView tvTime, tvMinimumOrder, tvDeliveryType, tvAbout, tvPaymentType;
    private TextView tvDeliveryCharge, tvDeliveryTime, tvContactDetails, tvDescription;

    private RestaurantDetails restaurantDetails;

    private RestaurantInfoMvpPresenter<RestaurantInfoMvpView> restaurantInfoMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        getSupportActionBar().setTitle(restaurantDetails.getRestaurantName());

        initPresenter();
//        restaurantInfoMvpPresenter = new RestaurantInfoPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        restaurantInfoMvpPresenter.onAttach(this);

        tvTime = (TextView) findViewById(R.id.activity_restaurant_info_tv_time);
        tvMinimumOrder = (TextView) findViewById(R.id.activity_restaurant_info_tv_minimum_order);
        tvDeliveryType = (TextView) findViewById(R.id.activity_restaurant_info_tv_delivery_type);
        tvPaymentType = (TextView) findViewById(R.id.activity_restaurant_info_tv_payment_type);
        tvDeliveryCharge = (TextView) findViewById(R.id.activity_restaurant_info_tv_delivery_charges);
        tvDeliveryTime = (TextView) findViewById(R.id.activity_restaurant_info_tv_delivery_time);
        tvContactDetails = (TextView) findViewById(R.id.activity_restaurant_info_tv_email);
        tvAbout = (TextView) findViewById(R.id.activity_restaurant_info_tv_about);
        tvDescription = (TextView) findViewById(R.id.activity_restaurant_info_tv_description);

        setUp();
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        restaurantInfoMvpPresenter = new RestaurantInfoPresenter(appDataManager, compositeDisposable);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        restaurantInfoMvpPresenter.dispose();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    public void getExtras() {

        if (getIntent().hasExtra(AppConstants.RESTAURANT_DETAILS)) {
            restaurantDetails = getIntent().getParcelableExtra(AppConstants.RESTAURANT_DETAILS);
        }

    }

    @Override
    protected void setUp() {

        Log.d("getOpeningTime", ">>" + restaurantDetails.getOpeningTime());
        Log.d("getClosingTime", ">>" + restaurantDetails.getClosingTime());
        Log.d("getDeliveryType", ">>" + restaurantDetails.getDeliveryType());
        Log.d("getDeliveryCharge", ">>" + restaurantDetails.getDeliveryCharge());
        Log.d("getDeliveryTime", ">>" + restaurantDetails.getDeliveryTime());
        Log.d("getEmail", ">>" + restaurantDetails.getEmail());
        Log.d("getDescription", ">>" + restaurantDetails.getDescription());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar openingTime = Calendar.getInstance();
        Calendar closingTime = Calendar.getInstance();

        try {
            openingTime.setTime(simpleDateFormat.parse(restaurantDetails.getOpeningTime()));
            closingTime.setTime(simpleDateFormat.parse(restaurantDetails.getClosingTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        String openingArray[] = restaurantDetails.getOpeningTime().split(":");
//        String closingArray[] = restaurantDetails.getClosingTime().split(":");
//
//        String opening;
//        String closing;
//
////        opening = openingArray[0];
////        closing = closingArray[0];
//
//        opening = openingTime.get(Calendar.HOUR) + "";
//        closing = closingTime.get(Calendar.HOUR) + "";
//
//        int openingTemp = openingTime.get(Calendar.MINUTE);
//        int closingTemp = closingTime.get(Calendar.MINUTE);
//
//        if (openingTemp != 0) {
//            opening += ":" + openingArray[1];
//        }
//
//        if (closingTemp != 0) {
//            closing += ":" + closingArray[1];
//        }
//
//        openingTemp = openingTime.get(Calendar.AM_PM);
//        closingTemp = closingTime.get(Calendar.AM_PM);
//
//        opening += (openingTemp == 0) ? " AM" : " PM";
//        closing += (closingTemp == 0) ? " AM" : " PM";


        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm a");

        String opening = "";
        String closing = "";

        opening = simpleDateFormat1.format(openingTime.getTime());
        closing = simpleDateFormat1.format(closingTime.getTime());

        tvTime.setText(opening.toUpperCase() + " to " + closing.toUpperCase());

        String minOrderAmount = restaurantDetails.getMinOrderAmount();

        if (minOrderAmount != null) {
            if (minOrderAmount.contains(".00")) {
                minOrderAmount = minOrderAmount.replace(".00", "");
            }

            tvMinimumOrder.setText(CommonUtils.dataDecode(restaurantDetails.getCurrency()) + " " + minOrderAmount);
//            try {
//                tvMinimumOrder.setText(URLDecoder.decode(restaurantDetails.getCurrency(), "UTF-8") + " " + minOrderAmount);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        }

        tvDeliveryType.setText(restaurantDetails.getDeliveryType());
        tvPaymentType.setText(restaurantDetails.getPaymentMethod());
        //     tvDeliveryType.setText(getResources().getStringArray(R.array.order_types)[Integer.parseInt(restaurantDetails.getDeliveryType())/* - 1*/]);

        if (restaurantDetails.getDeliveryCharge() != null && !restaurantDetails.getDeliveryCharge().isEmpty()) {
            tvDeliveryCharge.setText(CommonUtils.dataDecode(restaurantDetails.getCurrency()) + " " + restaurantDetails.getDeliveryCharge());
//            try {
//                tvDeliveryCharge.setText(URLDecoder.decode(restaurantDetails.getCurrency(),"UTF-8") + " " + restaurantDetails.getDeliveryCharge());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        }

        tvDeliveryTime.setText(restaurantDetails.getDeliveryTime() + " min.");
        tvContactDetails.setText(restaurantDetails.getEmail() + "\n" + restaurantDetails.getMobileNumber());
        tvAbout.setText(getResources().getString(R.string.restaurant_info_tv_about_title_text) + " " + restaurantDetails.getRestaurantName());
        tvDescription.setText(restaurantDetails.getDescription());
    }
}