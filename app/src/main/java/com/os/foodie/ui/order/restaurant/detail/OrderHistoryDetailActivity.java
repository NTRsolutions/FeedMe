package com.os.foodie.ui.order.restaurant.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.order.restaurant.detail.OrderHistoryDetail;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.MyBasketAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class OrderHistoryDetailActivity extends BaseActivity implements OrderHistoryMvpView {

    private ImageView ivUserImage;

    private TextView ivName, ivOrderNumber, tvOrderType, tvPaymentType, tvDate;
    private TextView tvDeliveryAddress, tvOrderStatus, tvDiscountAmount, tvSubtotal;
    private TextView tvDeliveryCharges, tvTotalAmount, tvPhoneNumber;

    private LinearLayout llDeliverAddress, llCheckout;
    private LinearLayout llDeliveryCharges, llSubtotal;

    Context mContext;
    AppDataManager appDataManager;

    String orderId = "";

    private ArrayList<CartList> cartLists;

    OrderHistoryDetail orderHistoryDetail;

    private RecyclerView recyclerView;
    private MyBasketAdapter myBasketAdapter;

    private OrderHistoryMvpPresenter<OrderHistoryMvpView> orderHistoryMvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        mContext = this;

        initPresenter();
        orderHistoryMvpPresenter.onAttach(OrderHistoryDetailActivity.this);

        initView();

        recyclerView = (RecyclerView) findViewById(R.id.activity_order_history_detail_recyclerview);

        cartLists = new ArrayList<CartList>();

        myBasketAdapter = new MyBasketAdapter(this, cartLists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myBasketAdapter);

        orderId = getIntent().getExtras().getString("order_id");

        orderHistoryMvpPresenter.getOrderHistoryDetail(orderId);
    }

    private void initView() {

        ivUserImage = (ImageView) findViewById(R.id.activity_order_history_detail_iv_user_image);

        ivName = (TextView) findViewById(R.id.activity_order_history_detail_tv_name);
        ivOrderNumber = (TextView) findViewById(R.id.activity_order_history_detail_tv_order_number);
        tvOrderType = (TextView) findViewById(R.id.activity_order_history_detail_tv_order_type);
        tvPaymentType = (TextView) findViewById(R.id.activity_order_history_detail_tv_payment_type);
        tvDate = (TextView) findViewById(R.id.activity_order_history_detail_tv_date);
        tvDeliveryAddress = (TextView) findViewById(R.id.activity_order_history_detail_tv_delivery_address);
        tvOrderStatus = (TextView) findViewById(R.id.activity_order_history_detail_tv_order_status);
        tvDiscountAmount = (TextView) findViewById(R.id.activity_order_history_detail_tv_discount_amount);
        tvTotalAmount = (TextView) findViewById(R.id.activity_order_history_detail_tv_total_amount);
        tvDeliveryCharges = (TextView) findViewById(R.id.activity_order_history_detail_tv_delivery_charges);
        tvPhoneNumber = (TextView) findViewById(R.id.activity_order_history_detail_tv_phone_number);
        tvSubtotal = (TextView) findViewById(R.id.activity_order_history_detail_tv_subtotal);

        llCheckout = (LinearLayout) findViewById(R.id.activity_order_history_detail_ll_checkout);
        llDeliveryCharges = (LinearLayout) findViewById(R.id.activity_order_history_detail_ll_delivery_charges);
        llDeliverAddress = (LinearLayout) findViewById(R.id.activity_order_history_detail_tv_deliver_address);
        llSubtotal = (LinearLayout) findViewById(R.id.activity_order_history_detail_ll_subtotal);
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        orderHistoryMvpPresenter = new OrderHistoryPresenter<>(appDataManager, compositeDisposable);
    }

    @Override
    protected void setUp() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void setOrderHistoryDetail(OrderHistoryDetail orderHistoryDetail) {

        this.orderHistoryDetail = orderHistoryDetail;
        ivOrderNumber.setText(orderHistoryDetail.getResponse().getOrderDetail().getOrderId());

        if (appDataManager.getCurrentUserId().equals(orderHistoryDetail.getResponse().getRestaurantId())) {

            ivName.setText(getString(R.string.order_by) + " " + orderHistoryDetail.getResponse().getUserDetail().getFirstName() + " " + orderHistoryDetail.getResponse().getUserDetail().getLastName());
            ivUserImage.setVisibility(View.GONE);
            tvPhoneNumber.setText(orderHistoryDetail.getResponse().getUserDetail().getMobileNumber());

//      /*      Glide.with(mContext)
//                    .load(orderHistoryDetail.getResponse().getUserDetail().)
//                    .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
//                    .error(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
//                    .into(ivUserImage);*/

        } else {

            tvPhoneNumber.setText(orderHistoryDetail.getResponse().getMobileNumber1());
            ivName.setText(orderHistoryDetail.getResponse().getRestaurantName());

            ivUserImage.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(orderHistoryDetail.getResponse().getLogo())
                    .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .error(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .into(ivUserImage);
        }

        tvOrderType.setText(orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType());
        tvPaymentType.setText(orderHistoryDetail.getResponse().getOrderDetail().getPaymentMethod());

        tvDate.setText(orderHistoryDetail.getResponse().getOrderDetail().getOrderDelieveryDate() + " at " + orderHistoryDetail.getResponse().getOrderDetail().getOrderDelieveryTime());

        if (orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType().equalsIgnoreCase("Deliver")) {

            tvDeliveryAddress.setText(orderHistoryDetail.getResponse().getOrderDetail().getPaymentMethod());
        } else {
            llDeliverAddress.setVisibility(View.GONE);
        }

        tvOrderStatus.setText(orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus());

        cartLists.clear();
        cartLists.addAll(orderHistoryDetail.getResponse().getItemList());

        myBasketAdapter.notifyDataSetChanged();

        tvDeliveryCharges.setText("$" + orderHistoryDetail.getResponse().getDeliveryCharge().replace(".00", ".0"));

        final String deliveryTypes[] = getResources().getStringArray(R.array.delivery_type);

        if (orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType().equalsIgnoreCase(deliveryTypes[0])) {

            llDeliveryCharges.setVisibility(View.GONE);

        } else if (orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType().equalsIgnoreCase(deliveryTypes[1])) {

            llDeliveryCharges.setVisibility(View.VISIBLE);
        }

        updateTotalAmount();
    }

    public void updateTotalAmount() {

        float totalAmount = 0;

        for (int i = 0; i < cartLists.size(); i++) {

            CartList cartItem = cartLists.get(i);

            float price = Float.parseFloat(cartItem.getPrice());
            int quantity = Integer.parseInt(cartItem.getQty());

            totalAmount += price * quantity;
        }

        if (llDeliveryCharges.getVisibility() == View.VISIBLE) {

            tvSubtotal.setText("$" + totalAmount);

            totalAmount += Float.parseFloat(orderHistoryDetail.getResponse().getDeliveryCharge());
            llSubtotal.setVisibility(View.VISIBLE);

        } else {

            llSubtotal.setVisibility(View.GONE);
        }

        tvTotalAmount.setText("$" + totalAmount);
    }
}