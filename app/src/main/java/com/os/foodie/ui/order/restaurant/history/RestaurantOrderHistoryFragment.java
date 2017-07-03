package com.os.foodie.ui.order.restaurant.history;

import android.os.Bundle;
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
import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.data.network.model.orderlist.show.OrderList;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.RestaurantOrderHistoryAdapter;
import com.os.foodie.ui.adapter.recyclerview.RestaurantOrderListAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpPresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpView;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListPresenter;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantOrderHistoryFragment extends BaseFragment implements RestaurantOrderHistoryMvpView {

    public static final String TAG = "RestaurantOrderListFragment";

    private TextView tvAlert;

    private RecyclerView recyclerView;
    private ArrayList<OrderList> orderLists;

    private RestaurantOrderHistoryAdapter restaurantOrderListAdapter;
    private RestaurantOrderHistoryMvpPresenter<RestaurantOrderHistoryMvpView> restaurantOrderhistoryMvpPresenter;

    public RestaurantOrderHistoryFragment() {
        // Required empty public constructor
    }

    public static RestaurantOrderHistoryFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantOrderHistoryFragment fragment = new RestaurantOrderHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_order_list, container, false);

        setHasOptionsMenu(true);

        initPresenter();
//        restaurantOrderListMvpPresenter = new RestaurantOrderListPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        restaurantOrderhistoryMvpPresenter.onAttach(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_restaurant_order_list_recyclerview);
        tvAlert = (TextView) view.findViewById(R.id.fragment_restaurant_order_list_tv_alert);

        orderLists = new ArrayList<OrderList>();

        restaurantOrderListAdapter = new RestaurantOrderHistoryAdapter(getContext(), orderLists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantOrderListAdapter);

        restaurantOrderhistoryMvpPresenter.getOrderHistory();

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        restaurantOrderhistoryMvpPresenter = new RestaurantOrderHistoryPresenter(appDataManager, compositeDisposable);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((RestaurantMainActivity) getActivity()).setTitle(getString(R.string.title_fragment_restaurant_order_list));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_blank, menu);
    }

    @Override
    public void onDestroyView() {
        restaurantOrderhistoryMvpPresenter.dispose();
//        restaurantOrderListMvpPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
    }


    @Override
    public void onOrderHistoryReceived(GetOrderListResponse getOrderListResponse) {


        orderLists.clear();

        if (getOrderListResponse.getResponse().getOrderList() != null && !getOrderListResponse.getResponse().getOrderList().isEmpty()) {

            recyclerView.setVisibility(View.VISIBLE);
            tvAlert.setVisibility(View.GONE);

//            for (int i = 0; i < getOrderListResponse.getResponse().getOrderList().size(); i++) {
//                getOrderListResponse.getResponse().getOrderList().get(i).setDeliveryTime(getOrderListResponse.getResponse().getDeliveryTime());
//            }

            orderLists.addAll(getOrderListResponse.getResponse().getOrderList());
        } else {
            recyclerView.setVisibility(View.GONE);
            tvAlert.setVisibility(View.VISIBLE);
        }

        restaurantOrderListAdapter.notifyDataSetChanged();
    }
}