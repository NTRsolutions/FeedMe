package com.os.foodie.ui.details.restaurant;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.data.network.model.details.Image;
import com.os.foodie.data.network.model.details.Menu;
import com.os.foodie.model.RestaurantDetails;
import com.os.foodie.ui.adapter.recyclerview.CourseAdapter;
import com.os.foodie.ui.adapter.viewpager.PhotoAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.DividerItemLineDecoration;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageView;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageViewBehavior;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayout;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayoutBehavior;
import com.os.foodie.ui.info.RestaurantInfoActivity;
import com.os.foodie.utils.AppConstants;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;

public class RestaurantDetailsActivity extends BaseActivity implements RestaurantDetailsMvpView, View.OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout llPage;
    private FloatingActionImageView faivProfilePic, faivWebsite;
    private FloatingActionLinearLayout fallDeliveryTime, fallLikes;

    private FlowLayout flCuisines;
    private TextView tvRestaurantName, tvDiscount, tvDeliveryTime, tvLikes;
    private ImageView ivLikes;

    private String restaurantId;
    private ArrayList<String> urlList;
    private ArrayList<Object> objectArrayList;

    private ViewPager viewPager;
    private PhotoAdapter photoAdapter;

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    private final int ADJUST_PADDING_DP = 110;
    private final int ADJUST_PADDING_MIN = 85;

    private RestaurantDetails restaurantDetails;

    private RestaurantDetailsMvpPresenter<RestaurantDetailsMvpView> restaurantDetailsMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.ic_home_up_white));

        Fresco.initialize(this);

        if (getIntent().hasExtra(AppConstants.RESTAURANT_ID)) {
            restaurantId = getIntent().getStringExtra(AppConstants.RESTAURANT_ID);
        }

//        restaurantDetailsResponse = null;
        restaurantDetails = new RestaurantDetails();

        urlList = new ArrayList<>();
        objectArrayList = new ArrayList<>();

        restaurantDetailsMvpPresenter = new RestaurantDetailsPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        restaurantDetailsMvpPresenter.onAttach(this);

        photoAdapter = new PhotoAdapter(this, urlList);

        llPage = (LinearLayout) findViewById(R.id.activity_restaurant_details_ll_page);
        viewPager = (ViewPager) findViewById(R.id.activity_restaurant_details_viewpager);

        flCuisines = (FlowLayout) findViewById(R.id.content_restaurant_details_fl_cuisine_types);

        tvRestaurantName = (TextView) findViewById(R.id.content_restaurant_details_tv_restaurant_name);

        tvDeliveryTime = (TextView) findViewById(R.id.activity_restautant_details_fall_delivery_time_text);
        tvLikes = (TextView) findViewById(R.id.activity_restautant_details_faiv_likes_text);
        ivLikes = (ImageView) findViewById(R.id.activity_restautant_details_faiv_likes_image);

        recyclerView = (RecyclerView) findViewById(R.id.content_restaurant_details_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);

        initFloatingActionButtons();

        restaurantDetailsMvpPresenter.getRestaurantDetails(restaurantId);
    }

    public void initFloatingActionButtons() {

        Log.d("initFAB", ">>Called");

        faivProfilePic = (FloatingActionImageView) findViewById(R.id.activity_restautant_details_faiv_profile_pic);
        ((CoordinatorLayout.LayoutParams) faivProfilePic.getLayoutParams()).setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_DP));

//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) imageView.getLayoutParams();
//        params.setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_DP));

        faivWebsite = (FloatingActionImageView) findViewById(R.id.activity_restautant_details_faiv_website);
        ((CoordinatorLayout.LayoutParams) faivWebsite.getLayoutParams()).setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_MIN));

//        CoordinatorLayout.LayoutParams params2 = (CoordinatorLayout.LayoutParams) imageView2.getLayoutParams();
//        params2.setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_MIN));

        fallLikes = (FloatingActionLinearLayout) findViewById(R.id.activity_restautant_details_faiv_likes);
        ((CoordinatorLayout.LayoutParams) fallLikes.getLayoutParams()).setBehavior(new FloatingActionLinearLayoutBehavior(ADJUST_PADDING_MIN));

//        CoordinatorLayout.LayoutParams params1 = (CoordinatorLayout.LayoutParams) floatingActionLinearLayout1.getLayoutParams();
//        params1.setBehavior(new FloatingActionLinearLayoutBehavior(ADJUST_PADDING_MIN));

        fallDeliveryTime = (FloatingActionLinearLayout) findViewById(R.id.activity_restautant_details_fall_delivery_time);
        ((CoordinatorLayout.LayoutParams) fallDeliveryTime.getLayoutParams()).setBehavior(new FloatingActionLinearLayoutBehavior(ADJUST_PADDING_MIN));

//        CoordinatorLayout.LayoutParams params3 = (CoordinatorLayout.LayoutParams) floatingActionLinearLayout.getLayoutParams();
//        params3.setBehavior(new FloatingActionLinearLayoutBehavior(ADJUST_PADDING_MIN));

        fallLikes.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_info) {

            if (restaurantDetails != null) {
                Intent intent = new Intent(this, RestaurantInfoActivity.class);
                intent.putExtra(AppConstants.RESTAURANT_DETAILS, restaurantDetails);
                startActivity(intent);
            }
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == fallLikes.getId()) {
            restaurantDetailsMvpPresenter.doLikeDislike(restaurantId);
        }

    }

    @Override
    protected void setUp() {

    }

    @Override
    public void setResponse(CustomerRestaurantDetailsResponse restaurantDetailsResponse) {

//        this.restaurantDetailsResponse = new CustomerRestaurantDetailsResponse();
//        this.restaurantDetails = restaurantDetailsResponse;
        setRestaurantDetails(restaurantDetailsResponse);


        if (restaurantDetailsResponse.getResponse().getIsLike().equals("1")) {
            ivLikes.setImageResource(R.mipmap.ic_like_true);
        } else {
            ivLikes.setImageResource(R.mipmap.ic_like_false);
        }

        Log.d("getDishList size", ">>" + restaurantDetailsResponse.getResponse().getMenu().size());
//        Log.d("keySet", ">>" + restaurantDetailsResponse.getResponse().getDishes().keySet().toString());

        ArrayList<String> urls = new ArrayList<String>();

        for (int i = 0; i < restaurantDetailsResponse.getResponse().getImages().size(); i++) {
            urls.add(restaurantDetailsResponse.getResponse().getImages().get(i).getUrl());
        }

        this.urlList.clear();
        this.urlList.addAll(urls);

        photoAdapter.notifyDataSetChanged();

        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(photoAdapter);

        if (urlList != null && !urlList.isEmpty()) {
            onPageSelected(viewPager.getCurrentItem());
        }


        Glide.with(this)
                .load(restaurantDetailsResponse.getResponse().getLogo())
//                .placeholder(getResources().getDrawable(R.mipmap.ic_logo))
                .into(faivProfilePic);

        tvRestaurantName.setText(restaurantDetailsResponse.getResponse().getRestaurantName());
        tvDeliveryTime.setText(restaurantDetailsResponse.getResponse().getDeliveryTime());
//        tvLikes.setText(restaurantDetailsResponse.getResponse().getLatitude());
//        tvDiscount.setText(restaurantDetailsResponse.getResponse().g());


        String cuisines[] = restaurantDetailsResponse.getResponse().getCuisineType().split(",");

        for (String cuisine : cuisines) {

            TextView tvCuisine = new TextView(this);

            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

            int marginTop = (int) getResources().getDimension(R.dimen.recyclerview_restaurant_tv_cuisine_margin_top);

            layoutParams.setMarginStart((int) getResources().getDimension(R.dimen.recyclerview_restaurant_tv_cuisine_margin_start));
            layoutParams.setMargins(0, marginTop, 0, 0);

            tvCuisine.setLayoutParams(layoutParams);
            tvCuisine.setBackground(getResources().getDrawable(R.drawable.rectangle_round_corner_white_outline_black));

            float padding = getResources().getDimension(R.dimen.recyclerview_restaurant_tv_cuisine_padding);

            tvCuisine.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
            tvCuisine.setTextColor(getResources().getColor(R.color.black));
            tvCuisine.setTypeface(Typeface.SERIF);
            tvCuisine.setText(cuisine);

            flCuisines.addView(tvCuisine);
        }

        ArrayList<Menu> menu = (ArrayList<Menu>) restaurantDetailsResponse.getResponse().getMenu();

        for (int i = 0; i < menu.size(); i++) {

            objectArrayList.add(menu.get(i).getCourseType());
            objectArrayList.addAll(menu.get(i).getDish());
        }

        courseAdapter = new CourseAdapter(this, objectArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.addItemDecoration(new DividerItemLineDecoration(this));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseAdapter);
    }

    @Override
    public void updateLikeDislike(boolean isLike) {

        if (isLike) {
            ivLikes.setImageResource(R.mipmap.ic_like_true);
        } else {
            ivLikes.setImageResource(R.mipmap.ic_like_false);
        }
    }

    public void setRestaurantDetails(CustomerRestaurantDetailsResponse restaurantDetailsResponse) {

        restaurantDetails.setId(restaurantDetailsResponse.getResponse().getId());
        restaurantDetails.setRestaurantName(restaurantDetailsResponse.getResponse().getRestaurantName());
        restaurantDetails.setMinOrderAmount(restaurantDetailsResponse.getResponse().getMinOrderAmount());
        restaurantDetails.setContactPersonName(restaurantDetailsResponse.getResponse().getContactPersonName());
        restaurantDetails.setAddress(restaurantDetailsResponse.getResponse().getAddress());
        restaurantDetails.setLatitude(restaurantDetailsResponse.getResponse().getLatitude());
        restaurantDetails.setLongitude(restaurantDetailsResponse.getResponse().getLongitude());
        restaurantDetails.setZipCode(restaurantDetailsResponse.getResponse().getZipCode());
        restaurantDetails.setOpeningTime(restaurantDetailsResponse.getResponse().getOpeningTime());
        restaurantDetails.setClosingTime(restaurantDetailsResponse.getResponse().getClosingTime());
        restaurantDetails.setDescription(restaurantDetailsResponse.getResponse().getDescription());
        restaurantDetails.setCuisineType(restaurantDetailsResponse.getResponse().getCuisineType());
        restaurantDetails.setWorkingDays(restaurantDetailsResponse.getResponse().getWorkingDays());
        restaurantDetails.setMobileNumber(restaurantDetailsResponse.getResponse().getMobileNumber());
        restaurantDetails.setEmail(restaurantDetailsResponse.getResponse().getEmail());
        restaurantDetails.setDeliveryTime(restaurantDetailsResponse.getResponse().getDeliveryTime());
        restaurantDetails.setDeliveryCharge(restaurantDetailsResponse.getResponse().getDeliveryCharge());
        restaurantDetails.setDeliveryType(restaurantDetailsResponse.getResponse().getDeliveryType());
        restaurantDetails.setDeliveryZipcode(restaurantDetailsResponse.getResponse().getDeliveryZipcode());
    }

    @Override
    protected void onDestroy() {
        restaurantDetailsMvpPresenter.onDetach();
        super.onDestroy();
        Fresco.shutDown();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        llPage.removeAllViews();

        for (int i = 0; i < urlList.size(); i++) {

            ImageView ivPage = new ImageView(RestaurantDetailsActivity.this);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = (int) getResources().getDimension(R.dimen.restaurant_details_iv_page_margin);
            layoutParams.setMargins(margin, margin, margin, margin);

            ivPage.setLayoutParams(layoutParams);

            if (i == position) {
                ivPage.setImageResource(R.mipmap.ic_page_active);
            } else {
                ivPage.setImageResource(R.mipmap.ic_page_inactive);
            }

            llPage.addView(ivPage);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}