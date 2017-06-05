package com.os.foodie.ui.home.customer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.ui.adapter.recyclerview.RestaurantListAdapter;
import com.os.foodie.ui.adapter.recyclerview.RestaurantMenuAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.custom.DividerItemLineDecoration;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuFragment;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpView;

import java.util.ArrayList;

public class CustomerHomeFragment extends BaseFragment implements CustomerHomeMvpView, View.OnClickListener {

    public static final String TAG = "CustomerHomeFragment";

    private RecyclerView recyclerView;
    private ArrayList<RestaurantList> restaurantList;
    private RestaurantListAdapter restaurantListAdapter;

    private CustomerHomeMvpPresenter<CustomerHomeMvpView> customerHomeMvpPresenter;

    public CustomerHomeFragment() {
    }

    public static CustomerHomeFragment newInstance() {
        Bundle args = new Bundle();
        CustomerHomeFragment fragment = new CustomerHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);

        customerHomeMvpPresenter = new CustomerHomePresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        customerHomeMvpPresenter.onAttach(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_customer_home_recyclerview);

        restaurantList = new ArrayList<RestaurantList>();

        restaurantListAdapter = new RestaurantListAdapter(getContext(), restaurantList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.addItemDecoration(new DividerItemLineDecoration(getActivity()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        customerHomeMvpPresenter.getRestaurantList();
        ((CustomerMainActivity) getActivity()).setTitle(getResources().getString(R.string.title_fragment_customer_home));
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void notifyDataSetChanged() {
        restaurantListAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged(ArrayList<RestaurantList> restaurantList) {

        Log.d("size", ">>" + restaurantList.get(0).getCuisineType());

        this.restaurantList.clear();
        restaurantListAdapter.notifyDataSetChanged();
        this.restaurantList.addAll(restaurantList);

        restaurantListAdapter.notifyDataSetChanged();
    }
}