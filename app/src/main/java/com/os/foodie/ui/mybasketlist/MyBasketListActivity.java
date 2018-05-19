package com.os.foodie.ui.mybasketlist;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.cart.list.CartList;
import com.os.foodie.data.network.model.cart.list.GetCartListResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.MyBasketListAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.mybasket.MyBasketMvpView;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListPresenter;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class MyBasketListActivity extends BaseActivity implements MyBasketListMvpView {

    private RecyclerView recyclerView;
    private TextView tvEmptyBasketList;

    private ArrayList<CartList> cartLists;
    private MyBasketListAdapter myBasketListAdapter;

    private MyBasketListMvpPresenter<MyBasketListMvpView> myBasketListMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basket_list);

        initPresenter();
        myBasketListMvpPresenter.onAttach(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));
        getSupportActionBar().setTitle(getResources().getString(R.string.select_basket_title));

        cartLists = new ArrayList<CartList>();
        myBasketListAdapter = new MyBasketListAdapter(this, cartLists);

        recyclerView = (RecyclerView) findViewById(R.id.activity_my_basket_list_recyclerview);
        tvEmptyBasketList = (TextView) findViewById(R.id.activity_my_basket_list_tv_empty_basket);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myBasketListAdapter);

        myBasketListMvpPresenter.getCartList();
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        myBasketListMvpPresenter = new MyBasketListPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == 20) {

            if (data.hasExtra(AppConstants.RESTAURANT_ID)) {

                for (int i = 0; i < cartLists.size(); i++) {

                    if (cartLists.get(i).getRestaurantId().equals(data.getStringExtra(AppConstants.RESTAURANT_ID))) {

                        cartLists.remove(i);

                        if (cartLists.isEmpty()) {

                            recyclerView.setVisibility(View.GONE);
                            tvEmptyBasketList.setVisibility(View.VISIBLE);

                        } else {

                            recyclerView.setVisibility(View.VISIBLE);
                            tvEmptyBasketList.setVisibility(View.GONE);
                        }

                        myBasketListAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        myBasketListMvpPresenter.dispose();
        super.onDestroy();
    }

    @Override
    public void setCartList(GetCartListResponse cartListResponse) {

        cartLists.clear();

        if (cartListResponse.getResponse().getCartList() == null || cartListResponse.getResponse().getCartList().isEmpty()) {

            recyclerView.setVisibility(View.GONE);
            tvEmptyBasketList.setVisibility(View.VISIBLE);

        } else {

            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyBasketList.setVisibility(View.GONE);
        }

        cartLists.addAll(cartListResponse.getResponse().getCartList());
        myBasketListAdapter.notifyDataSetChanged();
    }
}
