package com.os.foodie.ui.order.restaurant.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.os.foodie.ui.adapter.recyclerview.RestaurantOrderListAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RestaurantOrderListFragment extends BaseFragment implements RestaurantOrderListMvpView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "RestaurantOrderListFragment";

    private TextView tvAlert;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static RestaurantOrderListFragment restaurantOrderListFragment;

    private RecyclerView recyclerView;
    private ArrayList<OrderList> orderLists;

    private RestaurantOrderListAdapter restaurantOrderListAdapter;
    private RestaurantOrderListMvpPresenter<RestaurantOrderListMvpView> restaurantOrderListMvpPresenter;

    public RestaurantOrderListFragment() {
        // Required empty public constructor
    }

    public static RestaurantOrderListFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantOrderListFragment fragment = new RestaurantOrderListFragment();
        fragment.setArguments(args);
        restaurantOrderListFragment = fragment;
        return fragment;
    }

    public static RestaurantOrderListFragment getInstance() {
        return restaurantOrderListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_order_list, container, false);

        setHasOptionsMenu(true);

        initPresenter();
//        restaurantOrderListMvpPresenter = new RestaurantOrderListPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        restaurantOrderListMvpPresenter.onAttach(this);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_restaurant_order_list_srl);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_restaurant_order_list_recyclerview);
        tvAlert = (TextView) view.findViewById(R.id.fragment_restaurant_order_list_tv_alert);

        swipeRefreshLayout.setOnRefreshListener(this);

        orderLists = new ArrayList<OrderList>();

        restaurantOrderListAdapter = new RestaurantOrderListAdapter(getContext(), this, orderLists, restaurantOrderListMvpPresenter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantOrderListAdapter);

        restaurantOrderListMvpPresenter.getOrderList(null);

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        restaurantOrderListMvpPresenter = new RestaurantOrderListPresenter(appDataManager, compositeDisposable);

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
        restaurantOrderListMvpPresenter.dispose();
//        restaurantOrderListMvpPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void onOrderListReceived(GetOrderListResponse getOrderListResponse) {

        orderLists.clear();

        if (getOrderListResponse.getResponse().getOrderList() != null && !getOrderListResponse.getResponse().getOrderList().isEmpty()) {

//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            tvAlert.setVisibility(View.GONE);

//            for (int i = 0; i < getOrderListResponse.getResponse().getOrderList().size(); i++) {
//                getOrderListResponse.getResponse().getOrderList().get(i).setDeliveryTime(getOrderListResponse.getResponse().getDeliveryTime());
//            }

            orderLists.addAll(getOrderListResponse.getResponse().getOrderList());

            restaurantOrderListAdapter.setCurrency(CommonUtils.dataDecode(getOrderListResponse.getResponse().getCurrency()));
//            try {
//                restaurantOrderListAdapter.setCurrency(URLDecoder.decode(getOrderListResponse.getResponse().getCurrency(), "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

        } else {

//            swipeRefreshLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            tvAlert.setVisibility(View.VISIBLE);
        }

        restaurantOrderListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAcceptReject(int position) {

        orderLists.remove(position);
        restaurantOrderListAdapter.notifyDataSetChanged();

        if (orderLists != null && !orderLists.isEmpty()) {

            recyclerView.setVisibility(View.VISIBLE);
            tvAlert.setVisibility(View.GONE);

        } else {
            recyclerView.setVisibility(View.GONE);
            tvAlert.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20 && resultCode == 21) {

            onAcceptRejectOrderId(data.getStringExtra(AppConstants.ORDER_ID));
        }
    }

    public void onAcceptRejectOrderId(String orderId) {

        for (int i = 0; i < orderLists.size(); i++) {

            if (orderLists.get(i).getOrderId().equalsIgnoreCase(orderId)) {
                orderLists.remove(i);
            }
        }

        restaurantOrderListAdapter.notifyDataSetChanged();

        if (orderLists != null && !orderLists.isEmpty()) {

//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            tvAlert.setVisibility(View.GONE);

        } else {

//            swipeRefreshLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            tvAlert.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {

        restaurantOrderListMvpPresenter.getOrderList(swipeRefreshLayout);
    }

    public void newOrder() {

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_restaurant_main_cl_fragment);

        if (fragment instanceof RestaurantOrderListFragment) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    restaurantOrderListMvpPresenter.getOrderList(null);
                }
            });

        }
    }
}