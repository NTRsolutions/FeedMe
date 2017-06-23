package com.os.foodie.ui.deliveryaddress.select;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.os.foodie.ui.adapter.recyclerview.SelectDeliveryAddressAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RecyclerTouchListener;
import com.os.foodie.ui.deliveryaddress.addedit.AddEditDeliveryAddressActivity;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class SelectDeliveryAddressActivity extends BaseActivity implements SelectDeliveryAddressMvpView, View.OnClickListener {

    private FloatingActionButton fabAddAddress;
    private Button btPayment;

    private int selectedPosition = -1;

    private ArrayList<Address> addresses;

    private RecyclerView recyclerView;
    private SelectDeliveryAddressAdapter selectDeliveryAddressAdapter;

    private SelectDeliveryAddressMvpPresenter<SelectDeliveryAddressMvpView> selectDeliveryAddressMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_address);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        selectDeliveryAddressMvpPresenter = new SelectDeliveryAddressPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        selectDeliveryAddressMvpPresenter.onAttach(this);

        fabAddAddress = (FloatingActionButton) findViewById(R.id.activity_select_delivery_address_fab_add_address);
        btPayment = (Button) findViewById(R.id.activity_select_delivery_address_bt_payment);

        addresses = new ArrayList<Address>();

        selectDeliveryAddressAdapter = new SelectDeliveryAddressAdapter(this, addresses, selectDeliveryAddressMvpPresenter);
        recyclerView = (RecyclerView) findViewById(R.id.activity_select_delivery_address_recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(selectDeliveryAddressAdapter);

        setUp();

        fabAddAddress.setOnClickListener(this);
        btPayment.setOnClickListener(this);

        selectDeliveryAddressMvpPresenter.getAddressList();
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

            Intent intent = new Intent(this, AddEditDeliveryAddressActivity.class);
            startActivityForResult(intent, 1);

        } else if (v.getId() == btPayment.getId()) {
        }
    }

    @Override
    protected void setUp() {

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                if (selectedPosition >= 0) {
                    addresses.get(selectedPosition).setChecked(false);
                    selectedPosition = -1;
                }

                selectedPosition = position;
                addresses.get(position).setChecked(true);
                selectDeliveryAddressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void setAddressList(GetAllAddressResponse getAllAddressResponse) {

        addresses.clear();
        addresses.addAll(getAllAddressResponse.getResponse().getAddress());

        selectDeliveryAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddressDelete(int position) {
        addresses.remove(position);
        selectDeliveryAddressAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && data != null) {

            Address address = data.getParcelableExtra(AppConstants.DELIVERY_ADDRESS);

            addresses.add(address);
            selectDeliveryAddressAdapter.notifyDataSetChanged();

        } else if (resultCode == 2 && data != null) {

            int position = data.getIntExtra(AppConstants.POSITION, -1);
            Address address = data.getParcelableExtra(AppConstants.DELIVERY_ADDRESS);

            addresses.set(position, address);
            selectDeliveryAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        selectDeliveryAddressMvpPresenter.onDetach();
        super.onDestroy();
    }
}
