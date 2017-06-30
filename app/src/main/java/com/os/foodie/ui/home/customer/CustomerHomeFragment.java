package com.os.foodie.ui.home.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.RestaurantListAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.filters.FiltersActivity;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.search.RestaurantSearchActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerHomeFragment extends BaseFragment implements CustomerHomeMvpView, View.OnClickListener {

    public static final String TAG = "CustomerHomeFragment";

    private TextView tvNoResult;

    private RecyclerView recyclerView;
    private ArrayList<RestaurantList> restaurantList;
    private RestaurantListAdapter restaurantListAdapter;

    private CustomerHomeMvpPresenter<CustomerHomeMvpView> customerHomeMvpPresenter;

    public CustomerHomeFragment() {
    }

    public static CustomerHomeFragment newInstance() {
        Log.d("newInstance","Called");
        Bundle args = new Bundle();
        CustomerHomeFragment fragment = new CustomerHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        setHasOptionsMenu(true);

        initPresenter();
//        customerHomeMvpPresenter = new CustomerHomePresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        customerHomeMvpPresenter.onAttach(this);

        tvNoResult = (TextView) view.findViewById(R.id.fragment_customer_home_tv_no_result);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_customer_home_recyclerview);

        restaurantList = new ArrayList<RestaurantList>();

        restaurantListAdapter = new RestaurantListAdapter(getContext(), restaurantList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantListAdapter);

        return view;
    }

    public void initPresenter(){

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        customerHomeMvpPresenter = new CustomerHomePresenter(appDataManager, compositeDisposable);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("getRestaurantList","Called");
        ((CustomerMainActivity) getActivity()).setTitle(getResources().getString(R.string.title_fragment_customer_home));
        customerHomeMvpPresenter.getRestaurantList(AppController.get(getActivity()).getFilters());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_customer_home, menu);
//
//        getActivity().setTitle("");
//
//        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
//
//        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                Log.d("onQueryTextSubmit", ">>" + query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                Log.d("TextChanged", ">>" + newText);
//
////                if (TextUtils.isEmpty(newText)) {
////                    adapter.filter("");
////                    listView.clearTextFilter();
////                } else {
////                    adapter.filter(newText);
////                }
//                return true;
//            }
//        });

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
//        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
//        EditText et = (EditText) searchView.findViewById(searchEditId);
//
//        et.setTextColor(Color.BLACK);
//        et.setHintTextColor(Color.BLACK);
//
//        ImageView searchIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
//        searchIcon.setImageResource(R.mipmap.ic_delete);
//
//        ImageView searchCloseIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
//        searchCloseIcon.setImageResource(R.mipmap.ic_close);
//
//        ImageView searchButton = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
//        searchButton.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_filter) {
            Intent intent = new Intent(getActivity(), FiltersActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_location) {
            Intent intent = new Intent(getActivity(), LocationInfoActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(getActivity(), RestaurantSearchActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void notifyDataSetChanged() {
        this.restaurantList.clear();
        restaurantListAdapter.notifyDataSetChanged();
        checkNoResult();
    }

    @Override
    public void notifyDataSetChanged(ArrayList<RestaurantList> restaurantList) {

//        Log.d("size", ">>" + restaurantList.get(0).getCuisineType());

        this.restaurantList.clear();
        restaurantListAdapter.notifyDataSetChanged();
        this.restaurantList.addAll(restaurantList);

        restaurantListAdapter.notifyDataSetChanged();
        checkNoResult();
    }

    public void checkNoResult() {
        if (restaurantList != null && !restaurantList.isEmpty()) {
            tvNoResult.setVisibility(View.GONE);
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        Log.d("onDestroyView","Called");
//        customerHomeMvpPresenter.onDetach();
        customerHomeMvpPresenter.dispose();
        super.onDestroyView();
    }
}