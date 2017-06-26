package com.os.foodie.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.RestaurantSearchAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.DividerItemLineDecoration;
import com.os.foodie.ui.filters.FiltersActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantSearchActivity extends BaseActivity implements RestaurantSearchMvpView, SearchView.OnQueryTextListener {

    private TextView tvNoResult;
    private RecyclerView recyclerView;
    private ArrayList<RestaurantList> restaurantList;
    private RestaurantSearchAdapter restaurantSearchAdapter;

    private RestaurantSearchMvpPresenter<RestaurantSearchMvpView> restaurantSearchMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_search);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initPresenter();
//        restaurantSearchMvpPresenter = new RestaurantSearchPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        restaurantSearchMvpPresenter.onAttach(this);

        tvNoResult = (TextView) findViewById(R.id.activity_customer_search_tv_no_result);

        recyclerView = (RecyclerView) findViewById(R.id.activity_customer_search_recyclerview);
        restaurantList = new ArrayList<RestaurantList>();
        restaurantSearchAdapter = new RestaurantSearchAdapter(getContext(), restaurantList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new DividerItemLineDecoration(this));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantSearchAdapter);

        restaurantSearchMvpPresenter.getRestaurantList("", AppController.get(this).getFilters());

        setUp();

//        ImageView ivRes = (ImageView) findViewById(R.id.image);
//
//        Glide.with(this)
//                .load("http://192.168.1.69/foodi/app/webroot//uploads/restaurant_images/restaurant_image_11494918033.9151.jpg")
//                .asBitmap()
//                .into(ivRes);
    }

    public void initPresenter(){

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        restaurantSearchMvpPresenter = new RestaurantSearchPresenter(appDataManager, compositeDisposable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_restaurant_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(myActionMenuItem);
        MenuItemCompat.collapseActionView(myActionMenuItem);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        ImageView goButton = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn);
        goButton.setColorFilter(ContextCompat.getColor(this, R.color.grey));

        ImageView searchCloseIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setColorFilter(ContextCompat.getColor(this, R.color.grey));

        ImageView searchIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.mipmap.ic_search);

        EditText et = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        et.setTextColor(Color.BLACK);
        et.setHintTextColor(ContextCompat.getColor(this, R.color.grey));

        searchView.setOnQueryTextListener(this);

//        mQueryTextView = (SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
//
//        LinearLayout mSearchEditFrame = (LinearLayout) searchView.findViewById(R.id.search_edit_frame);
//        View mSearchPlate = searchView.findViewById(R.id.search_plate);
//        View mSubmitArea = searchView.findViewById(R.id.submit_area);
//        View mSubmitButton = searchView.findViewById(R.id.search_go_btn);
////        ImageView mCloseButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
//        View mVoiceButton = searchView.findViewById(R.id.search_voice_btn);
////        ImageView mSearchHintIcon = (ImageView) searchView.findViewById(R.id.search_mag_icon);
//
//        mSearchEditFrame.setBackgroundColor(Color.BLACK);
//        mSearchPlate.setBackgroundColor(Color.BLACK);
//        mSubmitArea.setBackgroundColor(Color.BLACK);
//        mSubmitButton.setBackgroundColor(Color.BLACK);
//        mVoiceButton.setBackgroundColor(Color.BLACK);
//
//
//        ImageView searchButton = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
//        searchButton.setImageResource(R.mipmap.ic_launcher);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        restaurantSearchMvpPresenter.clearRequests();
        restaurantSearchMvpPresenter.getRestaurantList(query, AppController.get(this).getFilters());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        restaurantSearchMvpPresenter.clearRequests();
        restaurantSearchMvpPresenter.getRestaurantList(newText, AppController.get(this).getFilters());
        return false;
    }

    @Override
    public void notifyDataSetChanged() {
        setUp();
        this.restaurantList.clear();
        restaurantSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged(ArrayList<RestaurantList> restaurantList) {

        setUp();
        this.restaurantList.clear();
        this.restaurantList.addAll(restaurantList);

        restaurantSearchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setUp() {

        if (restaurantList.isEmpty()) {
            tvNoResult.setVisibility(View.GONE);
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        restaurantSearchMvpPresenter.dispose();
//        restaurantSearchMvpPresenter.onDetach();
        super.onDestroy();
    }
}