package com.os.foodie.ui.discount.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.discount.list.DiscountList;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.DiscountListAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.discount.add.DiscountAddActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class DiscountListFragment extends BaseFragment implements DiscountListMvpView, View.OnClickListener {

    public static final String TAG = "DiscountListFragment";

    private TextView tvAlert;
    private RecyclerView recyclerView;

    private ArrayList<DiscountList> discountLists;
    private FloatingActionButton fabAddNewDiscount;

    private DiscountListAdapter discountListAdapter;
    private DiscountListMvpPresenter<DiscountListMvpView> discountListMvpPresenter;

    public DiscountListFragment() {
        // Required empty public constructor
    }

    public static DiscountListFragment newInstance() {
        Bundle args = new Bundle();
        DiscountListFragment fragment = new DiscountListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_discount_list, container, false);

        setHasOptionsMenu(true);

        initPresenter();
        discountListMvpPresenter.onAttach(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_restaurant_discount_list_recyclerview);
        fabAddNewDiscount = (FloatingActionButton) view.findViewById(R.id.fragment_restaurant_discount_list_fab_add_discount);
        tvAlert = (TextView) view.findViewById(R.id.fragment_restaurant_discount_list_tv_alert);

        discountLists = new ArrayList<DiscountList>();

        fabAddNewDiscount.setOnClickListener(this);

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        discountListMvpPresenter = new DiscountListPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public void onResume() {
        super.onResume();
        discountListMvpPresenter.DiscountListing();
        ((RestaurantMainActivity) getActivity()).setTitle(getString(R.string.action_discount_mangement));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_blank, menu);
    }

    @Override
    public void onDestroyView() {
        discountListMvpPresenter.dispose();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void onClick(View v) {

        if (v == fabAddNewDiscount) {
            Intent intent = new Intent(getActivity(), DiscountAddActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onShowDiscountList(ArrayList<DiscountList> discountLists) {

        this.discountLists = discountLists;

        if (discountLists.size() > 0) {

            discountListAdapter = new DiscountListAdapter(getContext(), discountListMvpPresenter, this.discountLists);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(discountListAdapter);
            tvAlert.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } else {

            tvAlert.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshList() {
        discountListMvpPresenter.DiscountListing();
    }
}