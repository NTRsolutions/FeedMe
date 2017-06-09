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
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.ui.adapter.viewpager.PhotoAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageView;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageViewBehavior;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayout;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayoutBehavior;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.TimeFormatUtils;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;

public class ShowRestaurantProfileFragment extends BaseFragment implements ShowRestaurantProfileMvpView, View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String TAG = "ShowRestaurantProfileFragment";
    private FlowLayout flCuisines;
    private ShowRestaurantProfileMvpPresenter<ShowRestaurantProfileMvpView> setupRestaurantProfileMvpPresenter;
    private ViewPager activityRestaurantDetailsViewpager;
    private TextView contentRestaurantDetailsTvRestaurantName;
    private Toolbar toolbar;
    private FloatingActionImageView activityRestautantDetailsFaivProfilePic;
    private FloatingActionLinearLayout activityRestautantDetailsFallDeliveryTime;
    private TextView activityRestautantDetailsFallDeliveryTimeText;
    private TextView openingClosingHourTv;
    private TextView deliveryChargesTv;
    private TextView minimumOrderAmountTv;
    private TextView deliveryAreasTv;
    private TextView orderTypeTv;
    private TextView workingDaysTv;
    private TextView paymentTypeTv;
    private TextView addressTv;
    private TextView cityTv;
    private TextView countyTv;
    private TextView zipCodeTv;
    private PhotoAdapter photoAdapter;
    LinearLayout activityRestaurantDetailsLlPage;
    private ArrayList<String> urlList = new ArrayList<>();
    private final int ADJUST_PADDING_DP = 110;
    private final int ADJUST_PADDING_MIN = 85;
    RestaurantMainActivity restaurantMainActivity;
    RestaurantProfileResponse restaurantProfileDetail;
    public static ShowRestaurantProfileFragment newInstance() {

        Bundle args = new Bundle();

        ShowRestaurantProfileFragment fragment = new ShowRestaurantProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_show_restaurant_profile, container, false);
        initView(view);
        setupRestaurantProfileMvpPresenter = new ShowRestaurantProfilePresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        setupRestaurantProfileMvpPresenter.onAttach(this);
        setupRestaurantProfileMvpPresenter.getRestaurantProfile(AppController.get(getActivity()).getAppDataManager().getCurrentUserId());
        return view;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onClick(View v) {

    }


    private void initView(View rootView) {
        restaurantMainActivity = (RestaurantMainActivity) getActivity();
        activityRestaurantDetailsViewpager = (ViewPager) rootView.findViewById(R.id.activity_restaurant_details_viewpager);
        contentRestaurantDetailsTvRestaurantName = (TextView) rootView.findViewById(R.id.content_restaurant_details_tv_restaurant_name);
        activityRestaurantDetailsLlPage = (LinearLayout) rootView.findViewById(R.id.activity_restaurant_details_ll_page);
        flCuisines = (FlowLayout) rootView.findViewById(R.id.content_restaurant_details_fl_cuisine_types);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        activityRestautantDetailsFaivProfilePic = (FloatingActionImageView) rootView.findViewById(R.id.activity_restautant_details_faiv_profile_pic);
        activityRestautantDetailsFallDeliveryTime = (FloatingActionLinearLayout) rootView.findViewById(R.id.activity_restautant_details_fall_delivery_time);
        activityRestautantDetailsFallDeliveryTimeText = (TextView) rootView.findViewById(R.id.activity_restautant_details_fall_delivery_time_text);
        openingClosingHourTv = (TextView) rootView.findViewById(R.id.opening_closing_hour_tv);
        deliveryChargesTv = (TextView) rootView.findViewById(R.id.delivery_charges_tv);
        minimumOrderAmountTv = (TextView) rootView.findViewById(R.id.minimum_order_amount_tv);
        deliveryAreasTv = (TextView) rootView.findViewById(R.id.delivery_areas_tv);
        orderTypeTv = (TextView) rootView.findViewById(R.id.order_type_tv);
        workingDaysTv = (TextView) rootView.findViewById(R.id.working_days_tv);
        paymentTypeTv = (TextView) rootView.findViewById(R.id.payment_type_tv);
        addressTv = (TextView) rootView.findViewById(R.id.address_tv);
        cityTv = (TextView) rootView.findViewById(R.id.city_tv);
        countyTv = (TextView) rootView.findViewById(R.id.county_tv);
        zipCodeTv = (TextView) rootView.findViewById(R.id.zip_code_tv);
        initFloatingActionButtons();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        (restaurantMainActivity).setTitle(getString(R.string.restaurant_profile));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
        this.restaurantProfileDetail=restaurantProfileDetail;
        RestaurantProfileResponse.RestaurantDetail restaurantDetail = restaurantProfileDetail.getResponse().getRestaurantDetail();
        Glide.with(this)
                .load(restaurantDetail.getLogo())
//                .load("http://192.168.1.69/foodi/app/webroot//uploads/restaurant_images/restaurant_logo_1496410374.jpg")
//                .placeholder(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .error(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                .into(activityRestautantDetailsFaivProfilePic);
        activityRestautantDetailsFallDeliveryTimeText.setText(restaurantDetail.getDeliveryTime());
        contentRestaurantDetailsTvRestaurantName.setText(restaurantDetail.getRestaurantName());
        openingClosingHourTv.setText(TimeFormatUtils.changeTimeFormat(restaurantDetail.getOpeningTime(), "HH:mm:ss", "hh:mm a") + " - " + TimeFormatUtils.changeTimeFormat(restaurantDetail.getClosingTime(), "HH:mm:ss", "hh:mm a"));
        deliveryChargesTv.setText(restaurantDetail.getDeliveryCharge());
        minimumOrderAmountTv.setText(restaurantDetail.getMinOrderAmount());
        deliveryAreasTv.setText(restaurantDetail.getDeliveryZipcode());
        orderTypeTv.setText(restaurantDetail.getDeliveryType());
        workingDaysTv.setText(restaurantDetail.getWorkingDays());
        //  paymentTypeTv.setText(restaurantDetail.ty);
        addressTv.setText(restaurantDetail.getAddress());
        cityTv.setText(restaurantDetail.getCityName());
        countyTv.setText(restaurantDetail.getCountryName());
        zipCodeTv.setText(restaurantDetail.getZipCode());

        for (int i = 0; i < restaurantDetail.getImages().size(); i++) {
            urlList.add(restaurantDetail.getImages().get(i).getUrl());
        }
        photoAdapter = new PhotoAdapter(getActivity(), urlList);
        photoAdapter.notifyDataSetChanged();

        activityRestaurantDetailsViewpager.addOnPageChangeListener(this);
        activityRestaurantDetailsViewpager.setAdapter(photoAdapter);
        if (urlList != null && !urlList.isEmpty()) {
            onPageSelected(activityRestaurantDetailsViewpager.getCurrentItem());
        }
        String cuisines[] = restaurantDetail.getCuisineType().split(",");

        for (String cuisine : cuisines) {

            TextView tvCuisine = new TextView(getActivity());

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


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        activityRestaurantDetailsLlPage.removeAllViews();

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

            activityRestaurantDetailsLlPage.addView(ivPage);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void initFloatingActionButtons() {

        Log.d("initFAB", ">>Called");
        ((CoordinatorLayout.LayoutParams) activityRestautantDetailsFaivProfilePic.getLayoutParams()).setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_DP));

        ((CoordinatorLayout.LayoutParams) activityRestautantDetailsFallDeliveryTime.getLayoutParams()).setBehavior(new FloatingActionLinearLayoutBehavior(ADJUST_PADDING_MIN));
    }


}