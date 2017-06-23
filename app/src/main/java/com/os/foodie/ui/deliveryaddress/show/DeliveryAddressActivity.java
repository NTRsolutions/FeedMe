package com.os.foodie.ui.deliveryaddress.show;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.os.foodie.ui.deliveryaddress.addedit.AddEditDeliveryAddressActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class DeliveryAddressActivity extends BaseActivity implements DeliveryAddressMvpView, View.OnClickListener {

    private FloatingActionButton fabAddAddress;
//    private Button btPayment;

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

        fabAddAddress = (FloatingActionButton) findViewById(R.id.activity_delivery_address_fab_add_address);
//        btPayment = (Button) findViewById(R.id.activity_delivery_address_bt_payment);

        addresses = new ArrayList<Address>();

        deliveryAddressAdapter = new DeliveryAddressAdapter(this, addresses, deliveryAddressMvpPresenter);
        recyclerView = (RecyclerView) findViewById(R.id.activity_delivery_address_recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deliveryAddressAdapter);

        fabAddAddress.setOnClickListener(this);
//        btPayment.setOnClickListener(this);

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

        if (v.getId() == fabAddAddress.getId()) {

            Intent intent = new Intent(DeliveryAddressActivity.this, AddEditDeliveryAddressActivity.class);
            startActivityForResult(intent, 1);

        }
//        else if (v.getId() == btPayment.getId()) {
//        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && data != null) {

            Address address = data.getParcelableExtra(AppConstants.DELIVERY_ADDRESS);

            addresses.add(address);
            deliveryAddressAdapter.notifyDataSetChanged();

        } else if (resultCode == 2 && data != null) {

            int position = data.getIntExtra(AppConstants.POSITION, -1);
            Address address = data.getParcelableExtra(AppConstants.DELIVERY_ADDRESS);

            addresses.set(position, address);
            deliveryAddressAdapter.notifyDataSetChanged();
        }
    }
}