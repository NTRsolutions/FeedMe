package com.os.foodie.ui.order.restaurant.list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.model.TempModelRestaurantOrder;
import com.os.foodie.ui.adapter.recyclerview.RestaurantMenuAdapter;
import com.os.foodie.ui.adapter.recyclerview.RestaurantOrderListAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuFragment;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpPresenter;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpView;

import java.util.ArrayList;

public class RestaurantOrderListFragment extends BaseFragment implements RestaurantOrderListMvpView {

    public static final String TAG = "RestaurantOrderListFragment";

    private TextView tvAlert;

    private RecyclerView recyclerView;
    private ArrayList<TempModelRestaurantOrder> tempModelRestaurantOrders;

    private RestaurantOrderListAdapter restaurantOrderListAdapter;
    private RestaurantOrderListMvpPresenter<RestaurantOrderListMvpView> restaurantOrderListMvpPresenter;

    public RestaurantOrderListFragment() {
        // Required empty public constructor
    }

    public static RestaurantOrderListFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantOrderListFragment fragment = new RestaurantOrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_order_list, container, false);

        restaurantOrderListMvpPresenter = new RestaurantOrderListPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        restaurantOrderListMvpPresenter.onAttach(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_restaurant_order_list_recyclerview);
        tvAlert = (TextView) view.findViewById(R.id.fragment_restaurant_order_list_tv_alert);

        tempModelRestaurantOrders = new ArrayList<TempModelRestaurantOrder>();

        for (int i = 0; i < 10; i++) {

            TempModelRestaurantOrder tempModelRestaurantOrder = new TempModelRestaurantOrder();

            tempModelRestaurantOrder.setOrderId("10" + i);
            tempModelRestaurantOrder.setItemName("Chicken Tanduri");
            tempModelRestaurantOrder.setDeliveryTime("0" + i + ":30 pm");
            tempModelRestaurantOrder.setOrderType("Chicken Tanduri");
            tempModelRestaurantOrder.setDiscount("5%");
            tempModelRestaurantOrder.setPrice("$100");

            tempModelRestaurantOrders.add(tempModelRestaurantOrder);
        }

        restaurantOrderListAdapter = new RestaurantOrderListAdapter(getContext(), tempModelRestaurantOrders);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantOrderListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        restaurantMenuMvpPresenter.getRestaurantMenuList();
        ((RestaurantMainActivity) getActivity()).setTitle(getString(R.string.title_fragment_restaurant_order_list));
    }

    @Override
    public void onDestroyView() {
        restaurantOrderListMvpPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
    }
}