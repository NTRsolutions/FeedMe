package com.os.foodie.ui.discount.list;

import android.content.Context;
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
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.discount.list.DiscountList;
import com.os.foodie.ui.adapter.recyclerview.DiscountListAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.discount.add.DiscountAddActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;

import java.util.ArrayList;

/**
 * Created by abhinava on 6/26/2017.
 */

public class DiscountListFragment extends BaseFragment implements DiscountListMvpView, View.OnClickListener {
    public static final String TAG = "DiscountListFragment";

    private TextView tvAlert;
    private RecyclerView recyclerView;

    private ArrayList<DiscountList> discountLists;
    private FloatingActionButton add_new_discount_fab;

    private DiscountListAdapter discountListAdapter;
    private DiscountListMvpPresenter<DiscountListMvpView> discountListMvpPresenter;
    Context context;

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

        context = getActivity();

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_restaurant_discount_list_recyclerview);
        add_new_discount_fab = (FloatingActionButton) view.findViewById(R.id.add_new_discount_fab);
        tvAlert = (TextView) view.findViewById(R.id.fragment_restaurant_discount_list_tv_alert);

        discountLists = new ArrayList<DiscountList>();

        add_new_discount_fab.setOnClickListener(this);

        initPresenter();
        discountListMvpPresenter.onAttach(this);

        return view;
    }

    public void initPresenter() {
        discountListMvpPresenter = new DiscountListPresenter<>(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        discountListMvpPresenter.onAttach(this);

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
        discountListMvpPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onClick(View v) {
        if (v == add_new_discount_fab) {
            Intent intent = new Intent(context, DiscountAddActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onShowDiscountList(ArrayList<DiscountList> discountLists) {
        this.discountLists = discountLists;

        if (discountLists.size() > 0) {
            discountListAdapter = new DiscountListAdapter(getContext(), discountListMvpPresenter, this.discountLists);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(getActivity()));
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