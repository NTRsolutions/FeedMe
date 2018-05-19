package com.os.foodie.ui.showrestaurantprofile;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.viewpager.PhotoAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageView;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageViewBehavior;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayout;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayoutBehavior;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.TimeFormatUtils;
import com.wefika.flowlayout.FlowLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class ShowRestaurantProfileFragment extends BaseFragment implements ShowRestaurantProfileMvpView, View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String TAG = "ShowRestaurantProfileFragment";

    private Toolbar toolbar;

    private ViewPager viewPager;

    private FlowLayout flCuisines;
    private LinearLayout llPage;

    private FloatingActionImageView faivProfilePic;
    private FloatingActionLinearLayout fallDeliveryTime;

    private TextView tvRestaurantName, tvDeliveryTime, tvOpeningClosingHours, tvDeliveryCharges;
    private TextView tvMinimumOrderAmount, tvDeliveryAreas, tvOrderType, tvWorkingDays;
    private TextView tvPaymentType, tvAddress, tvCity, tvCounty/*, tvZipCode*/;

    private PhotoAdapter photoAdapter;

    private ArrayList<String> urlList = new ArrayList<>();

    private RestaurantMainActivity restaurantMainActivity;
    private RestaurantProfileResponse restaurantProfileDetail;

    private final int ADJUST_PADDING_DP = 110;
    private final int ADJUST_PADDING_MIN = 85;

    private ShowRestaurantProfileMvpPresenter<ShowRestaurantProfileMvpView> setupRestaurantProfileMvpPresenter;

    public static ShowRestaurantProfileFragment newInstance() {

        Bundle args = new Bundle();

        ShowRestaurantProfileFragment fragment = new ShowRestaurantProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_restaurant_profile, container, false);

        Fresco.initialize(getActivity());

        initView(view);

        initPresenter();
//        setupRestaurantProfileMvpPresenter = new ShowRestaurantProfilePresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        setupRestaurantProfileMvpPresenter.onAttach(this);

        setupRestaurantProfileMvpPresenter.getRestaurantProfile(AppController.get(getActivity()).getAppDataManager().getCurrentUserId());

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        setupRestaurantProfileMvpPresenter = new ShowRestaurantProfilePresenter(appDataManager, compositeDisposable);

    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void onClick(View v) {

    }

    private void initView(View rootView) {

        restaurantMainActivity = (RestaurantMainActivity) getActivity();

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        viewPager = (ViewPager) rootView.findViewById(R.id.activity_restaurant_details_viewpager);
        llPage = (LinearLayout) rootView.findViewById(R.id.activity_restaurant_details_ll_page);

        faivProfilePic = (FloatingActionImageView) rootView.findViewById(R.id.activity_restautant_details_faiv_profile_pic);
        fallDeliveryTime = (FloatingActionLinearLayout) rootView.findViewById(R.id.activity_restautant_details_fall_delivery_time);

        flCuisines = (FlowLayout) rootView.findViewById(R.id.content_restaurant_details_fl_cuisine_types);

        tvRestaurantName = (TextView) rootView.findViewById(R.id.content_restaurant_details_tv_restaurant_name);
        tvDeliveryTime = (TextView) rootView.findViewById(R.id.activity_restautant_details_fall_delivery_time_text);
        tvOpeningClosingHours = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_opening_closing_hour);
        tvDeliveryCharges = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_delivery_charges);
        tvMinimumOrderAmount = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_minimum_order_amount);
        tvDeliveryAreas = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_delivery_areas);
        tvOrderType = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_order_type);
        tvWorkingDays = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_working_days);
        tvPaymentType = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_payment_type);
        tvAddress = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_other_details);
        tvCity = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_city);
        tvCounty = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_county);
//        tvZipCode = (TextView) rootView.findViewById(R.id.fragment_show_restaurant_profile_content_tv_zip);

        initFloatingActionButtons();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        (restaurantMainActivity).setTitle(getString(R.string.title_fragment_restaurant_profile));
    }

    @Override
    public void onDestroy() {
        setupRestaurantProfileMvpPresenter.dispose();
//        setupRestaurantProfileMvpPresenter.onDetach();
        Fresco.shutDown();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_with_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {
            restaurantMainActivity.navigateToSetRestaurantProfile(restaurantProfileDetail);
        }

        return true;
    }

    @Override
    public void setRestaurantProfileDetail(RestaurantProfileResponse restaurantProfileDetail) {

        this.restaurantProfileDetail = restaurantProfileDetail;
        RestaurantProfileResponse.RestaurantDetail restaurantDetail = restaurantProfileDetail.getResponse().getRestaurantDetail();

        Glide.with(this)
                .load(restaurantDetail.getLogo())
//                .load("http://192.168.1.69/foodi/app/webroot//uploads/restaurant_images/restaurant_logo_1496410374.jpg")
//                .placeholder(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .error(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                .into(faivProfilePic);

        if (restaurantDetail.getDeliveryTime() != null && !restaurantDetail.getDeliveryTime().isEmpty()) {

            tvDeliveryTime.setText(restaurantDetail.getDeliveryTime());

        } else {

            ((ViewGroup) fallDeliveryTime.getParent()).removeView(fallDeliveryTime);
//            fallDeliveryTime.setVisibility(View.GONE);
//            tvDeliveryTime.setText("0");
        }

        if (setupRestaurantProfileMvpPresenter.getLanguage().equalsIgnoreCase(AppConstants.LANG_AR)) {
            tvRestaurantName.setText(restaurantDetail.getRestaurantNameArabic());
        } else {
            tvRestaurantName.setText(restaurantDetail.getRestaurantName());
        }
        tvOpeningClosingHours.setText(TimeFormatUtils.changeTimeFormat(restaurantDetail.getOpeningTime(), "HH:mm:ss", "hh:mm a") + " - " + TimeFormatUtils.changeTimeFormat(restaurantDetail.getClosingTime(), "HH:mm:ss", "hh:mm a"));
        tvDeliveryCharges.setText(CommonUtils.dataDecode(restaurantDetail.getCurrency()) + " " + restaurantDetail.getDeliveryCharge());
        tvMinimumOrderAmount.setText(CommonUtils.dataDecode(restaurantDetail.getCurrency()) + " " + restaurantDetail.getMinOrderAmount());
//        try {
//
////            tvDeliveryCharges.setText(URLDecoder.decode(restaurantDetail.getCurrency(), "UTF-8") + " " + restaurantDetail.getDeliveryCharge());
////            tvMinimumOrderAmount.setText(URLDecoder.decode(restaurantDetail.getCurrency(), "UTF-8") + " " + restaurantDetail.getMinOrderAmount());
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        tvDeliveryAreas.setText(restaurantDetail.getDeliveryZipcode());
        tvDeliveryAreas.setText(restaurantDetail.getDeliveryCitiesNames());
        tvOrderType.setText(restaurantDetail.getDeliveryType());
        String[] workingDaysAry = restaurantDetail.getWorkingDays().split(",");
        if (workingDaysAry.length == 7)
            tvWorkingDays.setText(getString(R.string.moday_to_sunday));
        else
            tvWorkingDays.setText(restaurantDetail.getWorkingDays());


        tvPaymentType.setText(restaurantDetail.getPaymentMethod());
        tvAddress.setText(restaurantDetail.getAddress());
        tvCity.setText(restaurantDetail.getCityName());
        tvCounty.setText(restaurantDetail.getCountryName());
//        tvZipCode.setText(restaurantDetail.getZipCode());

        urlList.clear();

        for (int i = 0; i < restaurantDetail.getImages().size(); i++) {
            urlList.add(restaurantDetail.getImages().get(i).getUrl());
        }

        photoAdapter = new PhotoAdapter(getActivity(), urlList);
        photoAdapter.notifyDataSetChanged();

        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(photoAdapter);

        if (urlList != null && !urlList.isEmpty()) {
            onPageSelected(viewPager.getCurrentItem());
        }

        String cuisines[] = restaurantDetail.getCuisineType().split(",");

        for (String cuisine : cuisines) {

            TextView tvCuisine = new TextView(getActivity());

            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

            int marginTop = (int) getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_top);

            layoutParams.setMarginStart((int) getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_start));
            layoutParams.setMargins(0, marginTop, 0, 0);

            tvCuisine.setLayoutParams(layoutParams);
            tvCuisine.setBackground(getResources().getDrawable(R.drawable.rectangle_round_corner_white_outline_black));

            float padding = getResources().getDimension(R.dimen.restaurant_tv_cuisine_padding);

            tvCuisine.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
            tvCuisine.setTextColor(getResources().getColor(R.color.black));
            tvCuisine.setTypeface(Typeface.SERIF);
            tvCuisine.setText(cuisine);

            flCuisines.addView(tvCuisine);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        llPage.removeAllViews();

        for (int i = 0; i < urlList.size(); i++) {

            ImageView ivPage = new ImageView(getActivity());

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

    public void initFloatingActionButtons() {

        Log.d("initFAB", ">>Called");

        ((CoordinatorLayout.LayoutParams) faivProfilePic.getLayoutParams()).setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_DP));
        ((CoordinatorLayout.LayoutParams) fallDeliveryTime.getLayoutParams()).setBehavior(new FloatingActionLinearLayoutBehavior(ADJUST_PADDING_MIN));
    }
}