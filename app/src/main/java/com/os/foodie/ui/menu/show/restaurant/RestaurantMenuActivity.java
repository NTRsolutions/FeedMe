package com.os.foodie.ui.menu.show.restaurant;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.ui.custom.DividerItemLineDecoration;
import com.os.foodie.ui.adapter.recyclerview.RestaurantMenuAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.menu.add.RestaurantMenuAddUpdateDishActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuActivity extends BaseActivity implements RestaurantMenuMvpView, View.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    private ArrayList<Dish> dishArrayList;
    private RestaurantMenuAdapter restaurantMenuAdapter;

    private RestaurantMenuMvpPresenter<RestaurantMenuMvpView> restaurantMenuMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        setUp();
        initPresenter();
        restaurantMenuMvpPresenter.onAttach(RestaurantMenuActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_restaurant_menu_recyclerview);
        fabAdd = (FloatingActionButton) findViewById(R.id.activity_restaurant_menu_fab_add);

        dishArrayList = new ArrayList<Dish>();

//        restaurantMenuAdapter = new RestaurantMenuAdapter(getContext(), restaurantMenuMvpPresenter, dishArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemLineDecoration(RestaurantMenuActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantMenuAdapter);

        fabAdd.setOnClickListener(this);
    }

    @Override
    protected void setUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this,R.mipmap.ic_home_up_orange));

//        dishArrayList = getIntent().getParcelableArrayListExtra(AppConstants.DISH_ARRAYLIST);
    }

    public void initPresenter() {

        restaurantMenuMvpPresenter = new RestaurantMenuPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if (fabAdd.getId() == v.getId()) {
            openAddDishActivity();
//            restaurantMenuMvpPresenter.getRestaurantMenuList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restaurantMenuMvpPresenter.getRestaurantMenuList();
    }

    @Override
    public void createRestaurantMenu(List<Dish> dishArrayList) {

        this.dishArrayList.clear();
        this.dishArrayList.addAll(dishArrayList);

        restaurantMenuAdapter.notifyDataSetChanged();
    }

    public void openAddDishActivity() {
        Intent intent = new Intent(RestaurantMenuActivity.this, RestaurantMenuAddUpdateDishActivity.class);
        startActivity(intent);
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
}