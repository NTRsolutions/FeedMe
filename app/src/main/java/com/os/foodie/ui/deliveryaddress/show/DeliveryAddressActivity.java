package com.os.foodie.ui.deliveryaddress.show;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressResponse;
import com.os.foodie.ui.adapter.recyclerview.DeliveryAddressAdapter;
import com.os.foodie.ui.base.BaseActivity;

import java.util.ArrayList;

public class DeliveryAddressActivity extends BaseActivity implements DeliveryAddressMvpView, View.OnClickListener {

    private Button btAddNewAddress;

    private ArrayList<Address> addresses;

    private RecyclerView recyclerView;
    private DeliveryAddressAdapter deliveryAddressAdapter;

    private DeliveryAddressMvpPresenter<DeliveryAddressMvpView> deliveryAddressMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        deliveryAddressMvpPresenter = new DeliveryAddressPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        deliveryAddressMvpPresenter.onAttach(this);

        btAddNewAddress = (Button) findViewById(R.id.activity_delivery_address_bt_add_new_address);

        addresses = new ArrayList<Address>();

        deliveryAddressAdapter = new DeliveryAddressAdapter(this, addresses, deliveryAddressMvpPresenter);
        recyclerView = (RecyclerView) findViewById(R.id.activity_delivery_address_recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deliveryAddressAdapter);

        deliveryAddressMvpPresenter.getAddressList();
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

    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onDestroy() {
        deliveryAddressMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setAddressList(GetAllAddressResponse getAllAddressResponse) {

        addresses.clear();
        addresses.addAll(getAllAddressResponse.getResponse().getAddress());

        deliveryAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddressDelete(int position) {
        addresses.remove(position);
        deliveryAddressAdapter.notifyDataSetChanged();
    }
}