package com.os.foodie.ui.details.restaurant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.data.network.model.details.Dish;
import com.os.foodie.data.network.model.details.Menu;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.model.RestaurantDetails;
import com.os.foodie.ui.adapter.recyclerview.CourseAdapter;
import com.os.foodie.ui.adapter.viewpager.PhotoAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RecyclerTouchListener;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageView;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.FloatingActionImageViewBehavior;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayout;
import com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout.FloatingActionLinearLayoutBehavior;
import com.os.foodie.ui.info.RestaurantInfoActivity;
import com.os.foodie.ui.mybasket.MyBasketActivity;
import com.os.foodie.ui.search.RestaurantSearchActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.DialogUtils;
import com.os.foodie.utils.GetPathFromUrl;
import com.wefika.flowlayout.FlowLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantDetailsActivity extends BaseActivity implements RestaurantDetailsMvpView, View.OnClickListener, ViewPager.OnPageChangeListener {

    private RelativeLayout rlBasketDetails;
    private TextView tvTotalQuantity, tvTotalAmount;
    private Button btViewBasket;

    private LinearLayout llPage, llDiscount;
    private FloatingActionImageView faivProfilePic, faivShare;

    private FloatingActionLinearLayout fallDeliveryTime, fallLikes;
    private FlowLayout flCuisines;
    private RatingBar ratingBar;
    private TextView tvRestaurantName, tvReview, tvDiscount, tvDeliveryTime, tvLikes;
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
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_white));

        Fresco.initialize(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        if (getIntent().hasExtra(AppConstants.RESTAURANT_ID)) {
            restaurantId = getIntent().getStringExtra(AppConstants.RESTAURANT_ID);
        }

//        restaurantDetailsResponse = null;
        restaurantDetails = new RestaurantDetails();

        urlList = new ArrayList<>();
        objectArrayList = new ArrayList<>();

        initPresenter();
//        restaurantDetailsMvpPresenter = new RestaurantDetailsPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        restaurantDetailsMvpPresenter.onAttach(this);

        photoAdapter = new PhotoAdapter(this, urlList);

        rlBasketDetails = (RelativeLayout) findViewById(R.id.activity_restaurant_details_rl_basket);
        tvTotalQuantity = (TextView) findViewById(R.id.activity_restaurant_details_tv_total_quantity);
        tvTotalAmount = (TextView) findViewById(R.id.activity_restaurant_details_tv_total_amount);
        btViewBasket = (Button) findViewById(R.id.activity_restaurant_details_bt_view_basket);

        llDiscount = (LinearLayout) findViewById(R.id.content_restaurant_details_ll_discount);

        llPage = (LinearLayout) findViewById(R.id.activity_restaurant_details_ll_page);
        viewPager = (ViewPager) findViewById(R.id.activity_restaurant_details_viewpager);

        flCuisines = (FlowLayout) findViewById(R.id.content_restaurant_details_fl_cuisine_types);

        ratingBar = (RatingBar) findViewById(R.id.content_restaurant_details_rb_rating);
        tvRestaurantName = (TextView) findViewById(R.id.content_restaurant_details_tv_restaurant_name);

        tvDeliveryTime = (TextView) findViewById(R.id.activity_restautant_details_fall_delivery_time_text);
        tvReview = (TextView) findViewById(R.id.content_restaurant_details_tv_reviews);

        tvLikes = (TextView) findViewById(R.id.activity_restautant_details_faiv_likes_text);
        ivLikes = (ImageView) findViewById(R.id.activity_restautant_details_faiv_likes_image);

        recyclerView = (RecyclerView) findViewById(R.id.content_restaurant_details_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);

        initFloatingActionButtons();

        btViewBasket.setOnClickListener(this);
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        restaurantDetailsMvpPresenter = new RestaurantDetailsPresenter(appDataManager, compositeDisposable);
    }

    @Override
    protected void onResume() {
        super.onResume();

        restaurantDetailsMvpPresenter.getRestaurantDetails(restaurantId);
    }

    public void initFloatingActionButtons() {

        Log.d("initFAB", ">>Called");

        faivProfilePic = (FloatingActionImageView) findViewById(R.id.activity_restautant_details_faiv_profile_pic);
        ((CoordinatorLayout.LayoutParams) faivProfilePic.getLayoutParams()).setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_DP));

//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) imageView.getLayoutParams();
//        params.setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_DP));

        faivShare = (FloatingActionImageView) findViewById(R.id.activity_restautant_details_faiv_share);
        ((CoordinatorLayout.LayoutParams) faivShare.getLayoutParams()).setBehavior(new FloatingActionImageViewBehavior(ADJUST_PADDING_MIN));

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
        faivShare.setOnClickListener(this);
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
        } else if (v.getId() == btViewBasket.getId()) {
            Intent intent = new Intent(RestaurantDetailsActivity.this, MyBasketActivity.class);
            startActivity(intent);
        } else if (v.getId() == faivShare.getId()) {
            Log.d("faivShare", ">>OnClick");
//            SharingImageToGmail("Title", "Description", restaurantDetails.getImageUrl());
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void setResponse(CustomerRestaurantDetailsResponse restaurantDetailsResponse) {

//        this.restaurantDetailsResponse = new CustomerRestaurantDetailsResponse();
//        this.restaurantDetails = restaurantDetailsResponse;

        llDiscount.setVisibility(View.GONE);

        setRestaurantDetails(restaurantDetailsResponse);

        if (restaurantDetailsResponse.getResponse().getIsLike() == 1) {
            ivLikes.setImageResource(R.mipmap.ic_like_true);
        } else {
            ivLikes.setImageResource(R.mipmap.ic_like_false);
        }

        tvLikes.setText(restaurantDetailsResponse.getResponse().getLikeCount().toString());

        if (restaurantDetailsResponse.getResponse().getAvgRating() != null && !restaurantDetailsResponse.getResponse().getAvgRating().isEmpty()) {

            ratingBar.setRating(Float.parseFloat(restaurantDetailsResponse.getResponse().getAvgRating()));
        }


        tvReview.setText("Reviews(" + restaurantDetailsResponse.getResponse().getReviewCount() + ")");

//        tvReview.setText(restaurantDetailsResponse.getResponse().getLikeCount().toString());

        Log.d("getDishList size", ">>" + restaurantDetailsResponse.getResponse().getMenu().size());
//        Log.d("keySet", ">>" + restaurantDetailsResponse.getResponse().getDishes().keySet().toString());

        if (restaurantDetailsResponse.getResponse().getMenu().get(0).getDish().size() <= 0) {

            restaurantDetailsResponse.getResponse().getMenu().remove(0);
        }

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

        Log.d("getLogo", ">>" + restaurantDetailsResponse.getResponse().getLogo());

        Glide.with(this)
                .load(restaurantDetailsResponse.getResponse().getLogo())
                .placeholder(ContextCompat.getDrawable(this, R.mipmap.img_placeholder))
                .error(ContextCompat.getDrawable(this, R.mipmap.img_placeholder))
                .into(faivProfilePic);

        tvRestaurantName.setText(restaurantDetailsResponse.getResponse().getRestaurantName());
        tvDeliveryTime.setText(restaurantDetailsResponse.getResponse().getDeliveryTime());
//        tvLikes.setText(restaurantDetailsResponse.getResponse().getLatitude());
//        tvDiscount.setText(restaurantDetailsResponse.getResponse().g());

        flCuisines.removeAllViews();

        String cuisines[] = restaurantDetailsResponse.getResponse().getCuisineType().split(",");

        for (final String cuisine : cuisines) {

            TextView tvCuisine = new TextView(this);

            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

            int marginTop = (int) getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_top);

            layoutParams.setMarginStart((int) getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_start));
            layoutParams.setMargins(0, marginTop, 0, 0);

            tvCuisine.setLayoutParams(layoutParams);
            tvCuisine.setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_round_corner_white_outline_black));

            float padding = getResources().getDimension(R.dimen.restaurant_tv_cuisine_padding);

            tvCuisine.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
            tvCuisine.setTextColor(getResources().getColor(R.color.black));
            tvCuisine.setTypeface(Typeface.SERIF);
            tvCuisine.setText(cuisine);

            tvCuisine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RestaurantDetailsActivity.this, RestaurantSearchActivity.class);
                    intent.putExtra(AppConstants.CUISINE_SEARCH, cuisine);
                    startActivity(intent);
                }
            });

            flCuisines.addView(tvCuisine);
        }

        objectArrayList.clear();

        ArrayList<Menu> menu = (ArrayList<Menu>) restaurantDetailsResponse.getResponse().getMenu();

        for (int i = 0; i < menu.size(); i++) {

            objectArrayList.add(menu.get(i).getCourseType());
            objectArrayList.addAll(menu.get(i).getDish());
        }

        courseAdapter = new CourseAdapter(this, objectArrayList, restaurantId, restaurantDetailsMvpPresenter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseAdapter);

        setOnItemTouch();

        courseAdapter.notifyDataSetChanged();
//        courseAdapter.calcBasketDetails();

        if (!restaurantDetailsMvpPresenter.getCustomerRestaurantId().isEmpty() && !restaurantDetailsResponse.getResponse().getTotalQuantity().equals("0") && restaurantDetailsMvpPresenter.getCustomerRestaurantId().equals(restaurantId)) {
//        if (courseAdapter.getTotalQuantity() > 0) {
//
//            tvTotalQuantity.setText(String.valueOf(courseAdapter.getTotalQuantity()));
//            tvTotalAmount.setText("$" + courseAdapter.getTotalAmount());

            tvTotalQuantity.setText("Quantity: " + restaurantDetailsResponse.getResponse().getTotalQuantity());
            tvTotalAmount.setText("$" + restaurantDetailsResponse.getResponse().getTotalAmount());

            Log.d("VISIBLE", ">>Yes" + restaurantDetailsMvpPresenter.getCustomerRestaurantId());

            rlBasketDetails.setVisibility(View.VISIBLE);
        } else {
            rlBasketDetails.setVisibility(View.GONE);

            Log.d("GONE", ">>" + restaurantDetailsMvpPresenter.getCustomerRestaurantId());
        }
    }

    @Override
    public void resetResponse(CustomerRestaurantDetailsResponse restaurantDetailsResponse) {

        setRestaurantDetails(restaurantDetailsResponse);
        llDiscount.setVisibility(View.GONE);

//        if (restaurantDetailsResponse.getResponse().getIsLike().equals("1")) {

        if (restaurantDetailsResponse.getResponse().getIsLike() == 1) {
            ivLikes.setImageResource(R.mipmap.ic_like_true);
        } else {
            ivLikes.setImageResource(R.mipmap.ic_like_false);
        }

        tvLikes.setText(restaurantDetailsResponse.getResponse().getLikeCount().toString());

        if (restaurantDetailsResponse.getResponse().getMenu().get(0).getDish().size() <= 0) {
            restaurantDetailsResponse.getResponse().getMenu().remove(0);
        }

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
//                .placeholder(ContextCompat.getDrawable(this,R.mipmap.ic_logo))
                .into(faivProfilePic);

        tvRestaurantName.setText(restaurantDetailsResponse.getResponse().getRestaurantName());
        tvDeliveryTime.setText(restaurantDetailsResponse.getResponse().getDeliveryTime());
//        tvLikes.setText(restaurantDetailsResponse.getResponse().getLatitude());
//        tvDiscount.setText(restaurantDetailsResponse.getResponse().g());

        flCuisines.removeAllViews();

        String cuisines[] = restaurantDetailsResponse.getResponse().getCuisineType().split(",");

        for (final String cuisine : cuisines) {

            TextView tvCuisine = new TextView(this);

            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

            int marginTop = (int) getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_top);

            layoutParams.setMarginStart((int) getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_start));
            layoutParams.setMargins(0, marginTop, 0, 0);

            tvCuisine.setLayoutParams(layoutParams);
            tvCuisine.setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_round_corner_white_outline_black));

            float padding = getResources().getDimension(R.dimen.restaurant_tv_cuisine_padding);

            tvCuisine.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
            tvCuisine.setTextColor(getResources().getColor(R.color.black));
            tvCuisine.setTypeface(Typeface.SERIF);
            tvCuisine.setText(cuisine);

            tvCuisine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RestaurantDetailsActivity.this, RestaurantSearchActivity.class);
                    intent.putExtra(AppConstants.CUISINE_SEARCH, cuisine);
                    startActivity(intent);
                }
            });

            flCuisines.addView(tvCuisine);
        }

        objectArrayList.clear();

        ArrayList<Menu> menu = (ArrayList<Menu>) restaurantDetailsResponse.getResponse().getMenu();

        for (int i = 0; i < menu.size(); i++) {

            objectArrayList.add(menu.get(i).getCourseType());
            objectArrayList.addAll(menu.get(i).getDish());
        }

        courseAdapter.notifyDataSetChanged();
//        courseAdapter.calcBasketDetails();

        if (!restaurantDetailsMvpPresenter.getCustomerRestaurantId().isEmpty() && !restaurantDetailsResponse.getResponse().getTotalQuantity().equals("0") && restaurantDetailsMvpPresenter.getCustomerRestaurantId().equals(restaurantId)) {
//        if (courseAdapter.getTotalQuantity() > 0) {

            tvTotalQuantity.setText("Quantity: " + restaurantDetailsResponse.getResponse().getTotalQuantity());
            tvTotalAmount.setText("$" + restaurantDetailsResponse.getResponse().getTotalAmount());

            rlBasketDetails.setVisibility(View.VISIBLE);
        } else {
            rlBasketDetails.setVisibility(View.GONE);
        }
    }

    public void setOnItemTouch() {

        RecyclerTouchListener.ClickListener clickListener = new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                Log.d("getCustomerRestaurantId", ">>" + restaurantDetailsMvpPresenter.getCustomerRestaurantId());

                if (objectArrayList.get(position) instanceof Dish) {

                    if (restaurantDetailsMvpPresenter.getCustomerRestaurantId().isEmpty() || restaurantDetailsMvpPresenter.getCustomerRestaurantId().equals(restaurantId)) {

                        final boolean isUpdate;

                        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View dialogView = inflater.inflate(R.layout.dialog_item_quantity, null);

                        final Dialog mBottomSheetDialog = new Dialog(RestaurantDetailsActivity.this, R.style.AlertDialogBottomSlide);

                        TextView tvItemName = (TextView) dialogView.findViewById(R.id.dialog_item_quantity_tv_item_name);
                        TextView tvItemQuantity = (TextView) dialogView.findViewById(R.id.dialog_item_quantity_tv_item_quantity);
                        TextView tvPrice = (TextView) dialogView.findViewById(R.id.dialog_item_quantity_tv_price);

                        ImageView ivMinus = (ImageView) dialogView.findViewById(R.id.dialog_item_quantity_iv_minus);
                        ImageView ivPlus = (ImageView) dialogView.findViewById(R.id.dialog_item_quantity_iv_plus);

                        Button btUpdate = (Button) dialogView.findViewById(R.id.dialog_item_quantity_bt_update);

                        Dish dish = (Dish) objectArrayList.get(position);

                        tvItemName.setText(dish.getName());
                        tvItemQuantity.setText(dish.getQty());

                        if (dish.getQty().equals("0")) {
                            btUpdate.setText("Add");
                            isUpdate = false;
                        } else {
                            btUpdate.setText("Update");
                            isUpdate = true;
                        }

                        float price = Float.parseFloat(dish.getPrice());
                        int quantity = Integer.parseInt(dish.getQty());

                        int totalAmount = 0;
                        totalAmount += price * quantity;

                        tvPrice.setText(totalAmount + "");

                        final TextView tvItemQuantityTemp = tvItemQuantity;
                        final TextView tvPriceTemp = tvPrice;
                        final Button btUpdateTemp = btUpdate;

                        final View.OnClickListener onClickListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (tvItemQuantityTemp.getText().toString().equals("0")) {
//                            cartLists.remove(position);
//
//                            myBasketAdapter.notifyDataSetChanged();
//                            updateTotalAmount();
                                    mBottomSheetDialog.dismiss();
                                    restaurantDetailsMvpPresenter.removeFromMyBasket(AppController.get(RestaurantDetailsActivity.this).getAppDataManager().getCurrentUserId(), ((Dish) objectArrayList.get(position)).getDishId(), restaurantId, position);

                                } else {
//                            cartLists.get(position).setQty(tvItemQuantityTemp.getText().toString());
//
//                            myBasketAdapter.notifyDataSetChanged();
//                            updateTotalAmount();
                                    mBottomSheetDialog.dismiss();
                                    if (isUpdate) {
                                        restaurantDetailsMvpPresenter.updateMyBasket(AppController.get(RestaurantDetailsActivity.this).getAppDataManager().getCurrentUserId(), restaurantId, ((Dish) objectArrayList.get(position)).getDishId(), tvItemQuantityTemp.getText().toString(), ((Dish) objectArrayList.get(position)).getPrice(), position);
                                    } else {

                                        if (restaurantDetailsMvpPresenter.getCustomerRestaurantId().isEmpty()) {
                                            restaurantDetailsMvpPresenter.setCustomerRestaurantId(restaurantId);
                                        }

                                        AddToCartRequest addToCartRequest = new AddToCartRequest();

                                        addToCartRequest.setDishId(((Dish) objectArrayList.get(position)).getDishId());
                                        addToCartRequest.setUserId(AppController.get(RestaurantDetailsActivity.this).getAppDataManager().getCurrentUserId());
                                        addToCartRequest.setRestaurantId(restaurantId);
                                        addToCartRequest.setPrice(((Dish) objectArrayList.get(position)).getPrice());
                                        addToCartRequest.setQty((Integer.parseInt(tvItemQuantityTemp.getText().toString())) + "");

                                        restaurantDetailsMvpPresenter.addItemToCart(position, addToCartRequest);

//                                    restaurantDetailsMvpPresenter.updateMyBasket(AppController.get(RestaurantDetailsActivity.this).getAppDataManager().getCurrentUserId(), ((Dish) objectArrayList.get(position)).getDishId(), tvItemQuantityTemp.getText().toString(), position);
                                    }
                                }
                            }
                        };

                        ivMinus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                float price = Float.parseFloat(((Dish) objectArrayList.get(position)).getPrice());
                                int quantity = Integer.parseInt(tvItemQuantityTemp.getText().toString());

                                if (quantity > 0) {

                                    quantity--;

                                    int totalAmount = 0;
                                    totalAmount += price * quantity;

                                    Log.d("quantity", ">>" + quantity);
                                    Log.d("price", ">>" + price);
                                    Log.d("totalAmount", ">>" + totalAmount);

                                    tvPriceTemp.setText(totalAmount + "");
                                    tvItemQuantityTemp.setText(quantity + "");
                                }

                                if (quantity <= 0) {
                                    if (isUpdate) {
                                        btUpdateTemp.setText("Remove Item");
                                        btUpdateTemp.setTextColor(ContextCompat.getColor(RestaurantDetailsActivity.this, R.color.red));
                                    } else {
                                        btUpdateTemp.setOnClickListener(null);
                                    }
                                }
                            }
                        });

                        ivPlus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                float price = Float.parseFloat(((Dish) objectArrayList.get(position)).getPrice());
                                int quantity = Integer.parseInt(tvItemQuantityTemp.getText().toString());

                                if (quantity >= 0) {

                                    btUpdateTemp.setOnClickListener(onClickListener);

                                    quantity++;

                                    int totalAmount = 0;
                                    totalAmount += price * quantity;

                                    Log.d("quantity", ">>" + quantity);
                                    Log.d("price", ">>" + price);
                                    Log.d("totalAmount", ">>" + totalAmount);

                                    tvPriceTemp.setText(totalAmount + "");
                                    tvItemQuantityTemp.setText(quantity + "");

                                    if (isUpdate) {
                                        btUpdateTemp.setText("Update");
                                    } else {
                                        btUpdateTemp.setText("Add");
                                    }

                                    btUpdateTemp.setTextColor(ContextCompat.getColor(RestaurantDetailsActivity.this, R.color.orange));
                                }
                            }
                        });

                        if (dish.getQty().equals("0")) {

                            btUpdate.setOnClickListener(null);
                        } else {
                            btUpdate.setOnClickListener(onClickListener);
                        }

                        mBottomSheetDialog.setContentView(dialogView); // your custom view.
                        mBottomSheetDialog.setCancelable(true);
                        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                        mBottomSheetDialog.show();
                    } else {

                        DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                restaurantDetailsMvpPresenter.clearBasket();
                            }
                        };

                        DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        };

                        DialogUtils.showAlert(RestaurantDetailsActivity.this,
                                R.string.alert_dialog_title_clear_basket, R.string.alert_dialog_text_clear_basket,
                                getResources().getString(R.string.alert_dialog_bt_ok), positiveButton,
                                getResources().getString(R.string.alert_dialog_bt_cancel), negativeButton);
                    }

                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        };

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, clickListener));
    }

    @Override
    public void updateLikeDislike(boolean isLike) {

        if (isLike) {
            tvLikes.setText("" + (Integer.parseInt(tvLikes.getText().toString()) + 1));
            ivLikes.setImageResource(R.mipmap.ic_like_true);
        } else {
            tvLikes.setText("" + (Integer.parseInt(tvLikes.getText().toString()) - 1));
            ivLikes.setImageResource(R.mipmap.ic_like_false);
        }
    }

//    @Override
//    public void refreshDetails(int position, String quantity, AddToCartResponse addToCartResponse) {
//
////        restaurantDetailsMvpPresenter.getRestaurantDetails(restaurantId);
//    }

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
        restaurantDetails.setImageUrl(restaurantDetailsResponse.getResponse().getLogo());
    }

    @Override
    protected void onDestroy() {
        restaurantDetailsMvpPresenter.dispose();
//        restaurantDetailsMvpPresenter.onDetach();
        Fresco.shutDown();
        super.onDestroy();
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

    @Override
    public void itemRemovedFromBasket(int position, String totalQuantity, String totalAmount) {
//        objectArrayList.remove(position);

        String dishId = ((Dish) objectArrayList.get(position)).getDishId();

        for (int i = 0; i < objectArrayList.size(); i++) {

            if (objectArrayList.get(i) instanceof Dish && ((Dish) objectArrayList.get(i)).getDishId().equals(dishId)) {
                ((Dish) objectArrayList.get(i)).setQty("0");
            }
        }

        courseAdapter.notifyDataSetChanged();
//        updateTotalAmount();

        if (Integer.parseInt(totalQuantity) > 0) {

            tvTotalQuantity.setText("Quantity: " + totalQuantity);
            tvTotalAmount.setText("$" + totalAmount);

            rlBasketDetails.setVisibility(View.VISIBLE);
        } else {
            rlBasketDetails.setVisibility(View.GONE);
            restaurantDetailsMvpPresenter.setCustomerRestaurantId("");
        }
    }

    @Override
    public void updateMyBasket(int position, String quantity, String totalQuantity, String totalAmount) {

        String dishId = ((Dish) objectArrayList.get(position)).getDishId();

        for (int i = 0; i < objectArrayList.size(); i++) {

            if (objectArrayList.get(i) instanceof Dish && ((Dish) objectArrayList.get(i)).getDishId().equals(dishId)) {
                ((Dish) objectArrayList.get(i)).setQty(quantity);
            }
        }

//        ((Dish) objectArrayList.get(position)).setQty(quantity);
        courseAdapter.notifyDataSetChanged();
//        updateTotalAmount();

        if (Integer.parseInt(totalQuantity) > 0) {

            tvTotalQuantity.setText("Quantity: " + totalQuantity);
            tvTotalAmount.setText("$" + totalAmount);

            rlBasketDetails.setVisibility(View.VISIBLE);
        } else {
            rlBasketDetails.setVisibility(View.GONE);
        }
    }

    public void updateTotalAmount() {

//        courseAdapter.calcBasketDetails();
//
//        if (courseAdapter.getTotalQuantity() > 0) {
//
//            tvTotalQuantity.setText(String.valueOf(courseAdapter.getTotalQuantity()));
//            tvTotalAmount.setText("$" + courseAdapter.getTotalAmount());
//
//            rlBasketDetails.setVisibility(View.VISIBLE);
//        } else {
//            rlBasketDetails.setVisibility(View.GONE);
//        }
    }


    public void shareToFb(final String title, String fbDescription, final String imageUrl) {
//       /* try {
//            byte[] data = Base64.decode(fbDescription);
//
//            fbDescription = new String(data, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
        List<String> permissionNeeds = Arrays.asList("publish_actions");
//        List<String> permissionNeeds = Arrays.asList("email","media","href");
        // Set permissions

        LoginManager loginManager = LoginManager.getInstance();

        loginManager.logInWithPublishPermissions(this, permissionNeeds);
//        LoginManager.getInstance().logInWithReadPermissions(RestaurantDetailsActivity.this, permissionNeeds);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();


        Log.d("permissionNeeds", ">>" + permissionNeeds.toArray());

        if (accessToken == null) {

            final String finalFbDescription = fbDescription;
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d("accessToken onSuccess", "shareOnFacebook");
                    shareOnFacebook(title, finalFbDescription, imageUrl);
//                    sharePhotoToFacebook(title, finalFbDescription, imageUrl);
                }

                @Override
                public void onCancel() {
                    Log.d("accessToken onCancel", "Called");
                }

                @Override
                public void onError(FacebookException exception) {
                    Log.d("accessToken onError", "Called");
                    LoginManager.getInstance().logOut();
                }
            });
        } else {
            Log.d("shareOnFacebook", "Called");
            shareOnFacebook(title, fbDescription, imageUrl);
//            sharePhotoToFacebook(title, fbDescription, imageUrl);
        }
    }
//
//    private void sharePhotoToFacebook(String title, String fbDescription, String imageUrl) {
//
////        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//
////        SharePhoto photo = new SharePhoto.Builder()
////                .setImageUrl(Uri.parse(imageUrl))
////                .setCaption(title + "\n\n" + fbDescription)
////                .build();
////
////        SharePhotoContent content = new SharePhotoContent.Builder()
////                .addPhoto(photo)
////                .build();
////
////        ShareApi.share(content, null);
//        Log.d("sharePhotoToFacebook", "Called");
//
//        if (ShareDialog.canShow(ShareLinkContent.class)) {
//
//            Log.d("canShow", "Called");
//
////            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//////                    .setContentTitle(title)
//////                    .setImageUrl(Uri.parse(imageUrl))
//////                    .setContentDescription(fbDescription)
////                            .setContentUrl(Uri.parse(imageUrl))
////                    .build();
//
//            SharePhoto photo = new SharePhoto.Builder()
//                    .setCaption(title)
//                    .setImageUrl(Uri.parse(imageUrl))
//                    .build();
////
////            ShareContent content = new SharePhotoContent.Builder()
////                    .addPhoto(photo)
////                    .build();
////
////            ShareDialog shareDialog = new ShareDialog(RestaurantDetailsActivity.this);
////            shareDialog.show(content, ShareDialog.Mode.FEED);
//
//
//            ShareContent shareContent = new ShareMediaContent.Builder()
//                    .addMedium(photo)
//                    .build();
//
//            ShareDialog shareDialog = new ShareDialog(RestaurantDetailsActivity.this);
//            shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
//        }
//    }

    private void shareOnFacebook(String title, String fbDescription, String imageUrl) {

        //imageUrl = GetPathFromUrl.get_Path(imageUrl);
        com.facebook.share.widget.ShareDialog shareDialog = new com.facebook.share.widget.ShareDialog(RestaurantDetailsActivity.this);
        ShareLinkContent shareContent;

        if (imageUrl.length() > 0) {
            Log.d("imageUrl", "yes");
            shareContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentDescription(fbDescription)
                    .setContentUrl(Uri.parse("https://media.treehugger.com/assets/images/2016/03/woodland_trail.jpg.662x0_q70_crop-scale.jpg"))
                    //.setImageUrl(Uri.parse(imageUrl))
                    .build();
        } else {
            Log.d("imageUrl", "no");
            shareContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentDescription(fbDescription)
                    .setContentUrl(Uri.parse("https://media.treehugger.com/assets/images/2016/03/woodland_trail.jpg.662x0_q70_crop-scale.jpg"))
                    .build();
        }

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("registerCallback onSuccess", "Called");
                Toast.makeText(RestaurantDetailsActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.d("registerCallback onCancel", "Called");
                Toast.makeText(RestaurantDetailsActivity.this, "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("registerCallback onError", ">>" + exception.getMessage());
                Toast.makeText(RestaurantDetailsActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    //.setImageUrl()
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();
            shareDialog.show(shareContent);
        }

        //shareDialog.show(shareContent, com.facebook.share.widget.ShareDialog.Mode.FEED);
    }

//
//    public void SharingImageToGmail(String title, String description, String imageUrl) {
//
////        String imagepath = GetPathFromUrl.get_Path(imageUrl);
//
//        String imagepath = imageUrl;
//        String shareTripStr = description;
//
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.setPackage(AppConstants.PACKAGE_GMAIL);
//        try {
////            Uri bmpUri = getLocalBitmapUri(faivProfilePic);
////            if (bmpUri != null) {
//
//            if(imagepath.length()>0){
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Foodi: " + tvRestaurantName.getText().toString() + "");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! we're going to " + tvRestaurantName.getText().toString() + " please visit: " + restaurantDetails.getImageUrl());
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imagepath);
//                shareIntent.setType("image/*");
//                startActivity(shareIntent);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//
//
//    public Uri getLocalBitmapUri(ImageView imageView) {
//        // Extract Bitmap from ImageView drawable
//        Drawable drawable = imageView.getDrawable();
//        Bitmap bmp = null;
//        if (drawable instanceof BitmapDrawable) {
//            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        } else {
//            return null;
//        }
//        // Store image to default external storage directory
//        Uri bmpUri = null;
//        try {
//            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
//            FileOutputStream out = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.close();
//            bmpUri = Uri.fromFile(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }
//
//
//    public void SharingImageToTwitter(String title, String description, String imageUrl) {
//
//        String imagepath = GetPathFromUrl.get_Path(imageUrl);
//        String shareTripStr = description;
//
//        boolean isAppinstalled = CommonUtils.isAppInstalled(AppConstants.PACKAGE_TWITTER, RestaurantDetailsActivity.this);
//        if (isAppinstalled) {
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.setPackage(AppConstants.PACKAGE_TWITTER);
//            try {
//
//                Uri bmpUri = getLocalBitmapUri(faivProfilePic);
//                if (bmpUri != null) {
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Foodi: " + tvRestaurantName.getText().toString() + "");
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! we're going to " + tvRestaurantName.getText().toString() + " please visit: " + restaurantDetails.getImageUrl());
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                    shareIntent.setType("image/*");
//                    startActivity(shareIntent);
//                }
//
//            } catch (Exception e) {
//                Toast.makeText(RestaurantDetailsActivity.this, "Please Install Twitter App", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//        } else {
//
//            Toast.makeText(RestaurantDetailsActivity.this, "Please Install Twitter App", Toast.LENGTH_SHORT).show();
//            Intent shareIntent = new Intent();
//            shareIntent = new Intent(Intent.ACTION_VIEW);
//            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            shareIntent.setData(Uri.parse(AppConstants.playStoreURL + AppConstants.PACKAGE_TWITTER));
//
//            startActivity(shareIntent);
//        }
//
//    }
//
//
//    public void SharingImageToMessage(String title, String description, String imageUrl) {
//        String imagepath = GetPathFromUrl.get_Path(imageUrl);
//        String shareTripStr = description;
//        try {
//            Uri bmpUri = getLocalBitmapUri(faivProfilePic);
//            String uri = "smsto:";
//            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
//            sendIntent.putExtra("sms_body", "Hey! we're going to " + tvRestaurantName.getText().toString() + " please visit: " + restaurantDetails.getImageUrl());
//            sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//            startActivity(sendIntent);
//
//        } catch (Exception e) {
//
//        }
//
//    }
//

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}