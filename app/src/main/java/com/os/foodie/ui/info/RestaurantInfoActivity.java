package com.os.foodie.ui.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.model.RestaurantDetails;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.details.restaurant.RestaurantDetailsPresenter;
import com.os.foodie.utils.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RestaurantInfoActivity extends BaseActivity implements RestaurantInfoMvpView, View.OnClickListener {

    private TextView tvTime, tvMinimumOrder, tvDeliveryType, tvAbout;
    private TextView tvDeliveryCharge, tvDeliveryTime, tvEmail, tvDescription;

    private RestaurantDetails restaurantDetails;

    private RestaurantInfoMvpPresenter<RestaurantInfoMvpView> restaurantInfoMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.ic_home_up_orange));

        getSupportActionBar().setTitle(restaurantDetails.getRestaurantName());

        restaurantInfoMvpPresenter = new RestaurantInfoPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        restaurantInfoMvpPresenter.onAttach(this);

        tvTime = (TextView) findViewById(R.id.activity_restaurant_info_tv_time);
        tvMinimumOrder = (TextView) findViewById(R.id.activity_restaurant_info_tv_minimum_order);
        tvDeliveryType = (TextView) findViewById(R.id.activity_restaurant_info_tv_delivery_type);
        tvDeliveryCharge = (TextView) findViewById(R.id.activity_restaurant_info_tv_delivery_charges);
        tvDeliveryTime = (TextView) findViewById(R.id.activity_restaurant_info_tv_delivery_time);
        tvEmail = (TextView) findViewById(R.id.activity_restaurant_info_tv_email);
        tvAbout = (TextView) findViewById(R.id.activity_restaurant_info_tv_about);
        tvDescription = (TextView) findViewById(R.id.activity_restaurant_info_tv_description);

        setUp();
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

        String openingArray[] = restaurantDetails.getOpeningTime().split(":");
        String closingArray[] = restaurantDetails.getClosingTime().split(":");

        String opening;
        String closing;

//        opening = openingArray[0];
//        closing = closingArray[0];

        opening = openingTime.get(Calendar.HOUR) + "";
        closing = closingTime.get(Calendar.HOUR) + "";

        int openingTemp = openingTime.get(Calendar.MINUTE);
        int closingTemp = closingTime.get(Calendar.MINUTE);

        if (openingTemp != 0) {
            opening += ":" + openingArray[1];
        }

        if (closingTemp != 0) {
            closing += ":" + closingArray[1];
        }

        openingTemp = openingTime.get(Calendar.AM_PM);
        closingTemp = closingTime.get(Calendar.AM_PM);

        opening += (openingTemp == 0) ? " AM" : " PM";
        closing += (closingTemp == 0) ? " AM" : " PM";

        tvTime.setText(opening + " to " + closing);

        String minOrderAmount = restaurantDetails.getMinOrderAmount();

        if (minOrderAmount != null) {
            if (minOrderAmount.contains(".00")) {
                minOrderAmount = minOrderAmount.replace(".00", "");
            }

            tvMinimumOrder.setText("$" + minOrderAmount);
        }

        tvDeliveryType.setText(getResources().getStringArray(R.array.order_types)[Integer.parseInt(restaurantDetails.getDeliveryType()) - 1]);

        if (!restaurantDetails.getDeliveryCharge().isEmpty()) {
            tvDeliveryCharge.setText("$" + restaurantDetails.getDeliveryCharge());
        }

        tvDeliveryTime.setText(restaurantDetails.getDeliveryTime() + " min.");
        tvEmail.setText(restaurantDetails.getEmail());
        tvAbout.setText(getResources().getString(R.string.restaurant_info_tv_about_title_text) + " " + restaurantDetails.getRestaurantName());
        tvDescription.setText(restaurantDetails.getDescription());
    }
}