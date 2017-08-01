package com.os.foodie.ui.deliveryaddress.select;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.data.network.model.checkout.CheckoutResponse;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.DeliveryAddressAdapter;
import com.os.foodie.ui.adapter.recyclerview.SelectDeliveryAddressAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RecyclerTouchListener;
import com.os.foodie.ui.deliveryaddress.addedit.AddEditDeliveryAddressActivity;
import com.os.foodie.ui.deliveryaddress.addedit.AddEditDeliveryAddressPresenter;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressActivity;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.mybasket.MyBasketActivity;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.ui.payment.select.SelectPaymentActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.disposables.CompositeDisposable;

public class SelectDeliveryAddressActivity extends BaseActivity implements SelectDeliveryAddressMvpView, View.OnClickListener {

    //    private CoordinatorLayout clMain;
    private TextView tvAlert;
    private FloatingActionButton fabAddAddress;
    private Button btPayment;

    private int selectedPosition = -1;

    private ArrayList<Address> addresses;

    private RecyclerView recyclerView;
    private SelectDeliveryAddressAdapter selectDeliveryAddressAdapter;

    private CheckoutRequest checkoutRequest;
    private String deliveryAreas[];

    private SelectDeliveryAddressMvpPresenter<SelectDeliveryAddressMvpView> selectDeliveryAddressMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_address);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initPresenter();
//        selectDeliveryAddressMvpPresenter = new SelectDeliveryAddressPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        selectDeliveryAddressMvpPresenter.onAttach(this);

//        clMain = (CoordinatorLayout) findViewById(R.id.activity_select_delivery_address_cl_main);
        tvAlert = (TextView) findViewById(R.id.activity_select_delivery_address_tv_empty_alert);

        fabAddAddress = (FloatingActionButton) findViewById(R.id.activity_select_delivery_address_fab_add_address);
        btPayment = (Button) findViewById(R.id.activity_select_delivery_address_bt_payment);

        addresses = new ArrayList<Address>();
        checkoutRequest = new CheckoutRequest();

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

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        selectDeliveryAddressMvpPresenter = new SelectDeliveryAddressPresenter<>(appDataManager, compositeDisposable);
    }

    @Override
    public void onCheckoutComplete(CheckoutResponse checkoutResponse) {

        Toast.makeText(this, checkoutResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(SelectDeliveryAddressActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent intentOrderDetails = new Intent(SelectDeliveryAddressActivity.this, OrderHistoryDetailActivity.class);
        intentOrderDetails.putExtra("order_id", checkoutResponse.getResponse().getOrderId());
        intentOrderDetails.putExtra("showUpdateButton", false);

        Intent[] intents = {intent, intentOrderDetails};
        startActivities(intents);
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

            if (selectedPosition == -1) {

                selectDeliveryAddressMvpPresenter.setError(R.string.select_address);

            } else {

                boolean inRange = false;

                for (int i = 0; i < deliveryAreas.length; i++) {

                    if (addresses.get(selectedPosition).getPincode().equalsIgnoreCase(deliveryAreas[i])) {
                        inRange = true;
                        break;
                    }
                }

                if (!inRange) {
                    selectDeliveryAddressMvpPresenter.setError(R.string.not_in_range);
                    return;
                }

                String paymentMethod = checkoutRequest.getPaymentMethod();

                if (paymentMethod.equalsIgnoreCase(AppConstants.COD)) {

//                checkoutRequest.setCardId("");
                    checkoutRequest.setUserAddressId(addresses.get(selectedPosition).getId());

                    selectDeliveryAddressMvpPresenter.checkout(checkoutRequest);

                } else {
                    checkoutRequest.setUserAddressId(addresses.get(selectedPosition).getId());
                    Log.d("getUserAddressId", ">>" + addresses.get(selectedPosition).getId());

                    Intent intent = new Intent(this, SelectPaymentActivity.class);
                    intent.putExtra(AppConstants.CHECKOUT, checkoutRequest);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void setUp() {

        if (getIntent().hasExtra(AppConstants.CHECKOUT)) {
            checkoutRequest = getIntent().getParcelableExtra(AppConstants.CHECKOUT);

            deliveryAreas = getIntent().getStringExtra(AppConstants.DELIVERY_ADDRESS).split(",");
            Log.d("deliveryAreas", ">>" + Arrays.toString(deliveryAreas));
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                Log.d("selectedPosition Before", ">>" + selectedPosition);

                if (selectedPosition >= 0) {
                    addresses.get(selectedPosition).setChecked(false);
                    selectedPosition = -1;
                }

                selectedPosition = position;
                addresses.get(position).setChecked(true);
                selectDeliveryAddressAdapter.notifyDataSetChanged();

                Log.d("selectedPosition After", ">>" + selectedPosition);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void setAddressList(GetAllAddressResponse getAllAddressResponse) {

        addresses.clear();

        if (getAllAddressResponse.getResponse().getAddress() != null && !getAllAddressResponse.getResponse().getAddress().isEmpty()) {

            addresses.addAll(getAllAddressResponse.getResponse().getAddress());

//            recyclerView.setVisibility(View.VISIBLE);
//            btPayment.setVisibility(View.VISIBLE);
//            tvAlert.setVisibility(View.GONE);
            setVisibility(View.VISIBLE, View.VISIBLE, View.GONE);

        } else {
//                recyclerView.setVisibility(View.GONE);
//                btPayment.setVisibility(View.GONE);
//                tvAlert.setVisibility(View.VISIBLE);
            setVisibility(View.GONE, View.INVISIBLE, View.VISIBLE);
        }

        selectDeliveryAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddressDelete(int position) {

//        addresses.get(selectedPosition).setChecked(false);
//        selectDeliveryAddressAdapter.notifyItemChanged(selectedPosition);
        selectedPosition = -1;

        addresses.remove(position);
        selectDeliveryAddressAdapter.notifyDataSetChanged();

        if (addresses != null && !addresses.isEmpty()) {

//            recyclerView.setVisibility(View.VISIBLE);
//            btPayment.setVisibility(View.VISIBLE);
//            tvAlert.setVisibility(View.GONE);
            setVisibility(View.VISIBLE, View.VISIBLE, View.GONE);

        } else {
//                recyclerView.setVisibility(View.GONE);
//                btPayment.setVisibility(View.GONE);
//                tvAlert.setVisibility(View.VISIBLE);
            setVisibility(View.GONE, View.INVISIBLE, View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (selectedPosition != -1) {
            addresses.get(selectedPosition).setChecked(false);
            selectDeliveryAddressAdapter.notifyItemChanged(selectedPosition);
            selectedPosition = -1;
        }

        if (resultCode == 1 && data != null) {

            Address address = data.getParcelableExtra(AppConstants.DELIVERY_ADDRESS);

            addresses.add(address);
            selectDeliveryAddressAdapter.notifyDataSetChanged();

            if (addresses != null && !addresses.isEmpty()) {

//                recyclerView.setVisibility(View.VISIBLE);
//                btPayment.setVisibility(View.VISIBLE);
//                tvAlert.setVisibility(View.GONE);
                setVisibility(View.VISIBLE, View.VISIBLE, View.GONE);

            } else {
//                recyclerView.setVisibility(View.GONE);
//                btPayment.setVisibility(View.GONE);
//                tvAlert.setVisibility(View.VISIBLE);
                setVisibility(View.GONE, View.INVISIBLE, View.VISIBLE);
            }

        } else if (resultCode == 2 && data != null) {

            int position = data.getIntExtra(AppConstants.POSITION, -1);
            Address address = data.getParcelableExtra(AppConstants.DELIVERY_ADDRESS);

            addresses.set(position, address);
            selectDeliveryAddressAdapter.notifyDataSetChanged();
        }
    }

    public void setVisibility(int visibility1, int visibility2, int visibility3) {
        recyclerView.setVisibility(visibility1);
        btPayment.setVisibility(visibility2);
        tvAlert.setVisibility(visibility3);

        Log.d("visibility1", ">>" + visibility1);
        Log.d("visibility2", ">>" + visibility2);
        Log.d("visibility2", ">>" + visibility3);
    }

    @Override
    protected void onDestroy() {
        selectDeliveryAddressMvpPresenter.dispose();
//        selectDeliveryAddressMvpPresenter.onDetach();
        super.onDestroy();
    }
}