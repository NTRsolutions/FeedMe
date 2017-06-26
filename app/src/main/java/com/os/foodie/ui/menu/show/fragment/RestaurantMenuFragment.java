package com.os.foodie.ui.menu.show.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.RestaurantMenuAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.menu.add.RestaurantMenuAddUpdateDishActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantMenuFragment extends BaseFragment implements RestaurantMenuMvpView, View.OnClickListener {

    public static final String TAG = "RestaurantMenuFragment";

    private TextView tvAlert;
    private FloatingActionButton fabAdd;

    private RecyclerView recyclerView;
    private ArrayList<Dish> dishArrayList;

    private RestaurantMenuAdapter restaurantMenuAdapter;
    private RestaurantMenuMvpPresenter<RestaurantMenuMvpView> restaurantMenuMvpPresenter;

    public RestaurantMenuFragment() {
        // Required empty public constructor
    }

    public static RestaurantMenuFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantMenuFragment fragment = new RestaurantMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

//        ((RestaurantMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.title_fragment_restaurant_menu));

        initPresenter();
//        restaurantMenuMvpPresenter = new RestaurantMenuPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        restaurantMenuMvpPresenter.onAttach(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_restaurant_menu_recyclerview);
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fragment_restaurant_menu_fab_add);
        tvAlert = (TextView) view.findViewById(R.id.fragment_restaurant_menu_tv_alert);

        dishArrayList = new ArrayList<Dish>();

        restaurantMenuAdapter = new RestaurantMenuAdapter(getContext(), restaurantMenuMvpPresenter, dishArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantMenuAdapter);

        fabAdd.setOnClickListener(this);

        return view;
    }

    public void initPresenter(){

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        restaurantMenuMvpPresenter = new RestaurantMenuPresenter(appDataManager, compositeDisposable);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onClick(View v) {

        if (fabAdd.getId() == v.getId()) {
            openAddDishActivity();
        }
    }

    public void openAddDishActivity() {
        Intent intent = new Intent(getActivity(), RestaurantMenuAddUpdateDishActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        restaurantMenuMvpPresenter.getRestaurantMenuList();
        ((RestaurantMainActivity) getActivity()).setTitle(getResources().getString(R.string.title_fragment_restaurant_menu));
    }

    @Override
    public void createRestaurantMenu(List<Dish> dishArrayList) {

        if (dishArrayList.size() == 0) {
            tvAlert.setVisibility(View.VISIBLE);
        } else {
            tvAlert.setVisibility(View.GONE);
        }

        this.dishArrayList.clear();
        this.dishArrayList.addAll(dishArrayList);

        restaurantMenuAdapter.notifyDataSetChanged();
        restaurantMenuMvpPresenter.getRestaurantMenuList();
    }

    @Override
    public void notifyDataSetChanged() {

        restaurantMenuAdapter.notifyDataSetChanged();

        for (int i = 0; i < dishArrayList.size(); i++) {
            Log.d("getName", ">>" + dishArrayList.get(i).getName());
            Log.d("getAvail", ">>" + dishArrayList.get(i).getAvail());
            Log.d("----------------", "---------------");
        }
    }

    @Override
    public void onMenuItemDelete(Dish dish) {
        dishArrayList.remove(dish);
        restaurantMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        restaurantMenuMvpPresenter.dispose();
//        restaurantMenuMvpPresenter.onDetach();
        super.onDestroyView();
    }
}