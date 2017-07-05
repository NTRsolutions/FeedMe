package com.os.foodie.ui.order.restaurant.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.changeorderstatus.ChangeOrderStatusResponse;
import com.os.foodie.data.network.model.order.restaurant.detail.OrderHistoryDetail;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.MyBasketAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.dialogfragment.orderstatus.OrderStatusCallback;
import com.os.foodie.ui.dialogfragment.orderstatus.OrderStatusDialogFragment;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by monikab on 6/26/2017.
 */

public class OrderHistoryDetailActivity extends BaseActivity implements OrderHistoryMvpView, View.OnClickListener, OrderStatusCallback {

    private ImageView userImageIv;
    private TextView nameTv;
    private TextView orderNumberTv;
    private TextView orderTypeTv;
    private TextView paymentTypeTv;
    private TextView dateTv;
    private LinearLayout deliverAddressTv;
    private TextView deliveryAddressTv;
    private TextView orderStatusTv;
    private RecyclerView activityMyBasketRecyclerView;
    private LinearLayout activityMyBasketLlCheckout;
    private TextView activityMyBasketTvDiscountAmount;
    private LinearLayout activityMyBasketLlDeliveryCharges;
    private TextView activityMyBasketTvDeliveryCharges;
    private TextView activityMyBasketTvTotalAmount;
    private TextView phoneNumberTv;
    private OrderHistoryMvpPresenter<OrderHistoryMvpView> orderHistoryMvpPresenter;
    String orderId = "";
    AppDataManager appDataManager;
    Context mContext;
    private ArrayList<CartList> cartLists;
    private MyBasketAdapter myBasketAdapter;
    private LinearLayout activityMyBasketLlSubtotal;
    private TextView activityMyBasketTvSubtotal;
    OrderHistoryDetail orderHistoryDetail;
    private LinearLayout customerMoreOptionLl;
    private RippleAppCompatButton reviewBt;
    private RippleAppCompatButton repeatOrderBt;
    private TextView changeStatusTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_history_detail);
        initView();
    }

    private void initView() {
        mContext = this;
        initPresenter();
        orderHistoryMvpPresenter.onAttach(OrderHistoryDetailActivity.this);

        userImageIv = (ImageView) findViewById(R.id.user_image_iv);
        nameTv = (TextView) findViewById(R.id.name_tv);
        orderNumberTv = (TextView) findViewById(R.id.order_number_tv);
        orderTypeTv = (TextView) findViewById(R.id.order_type_tv);
        paymentTypeTv = (TextView) findViewById(R.id.payment_type_tv);
        dateTv = (TextView) findViewById(R.id.date_tv);
        deliverAddressTv = (LinearLayout) findViewById(R.id.deliver_address_tv);
        deliveryAddressTv = (TextView) findViewById(R.id.delivery_address_tv);
        orderStatusTv = (TextView) findViewById(R.id.order_status_tv);
        activityMyBasketRecyclerView = (RecyclerView) findViewById(R.id.activity_my_basket_recyclerview);
        activityMyBasketLlCheckout = (LinearLayout) findViewById(R.id.activity_my_basket_ll_checkout);
        activityMyBasketTvDiscountAmount = (TextView) findViewById(R.id.activity_my_basket_tv_discount_amount);
        activityMyBasketLlDeliveryCharges = (LinearLayout) findViewById(R.id.activity_my_basket_ll_delivery_charges);
        activityMyBasketTvDeliveryCharges = (TextView) findViewById(R.id.activity_my_basket_tv_delivery_charges);
        activityMyBasketTvTotalAmount = (TextView) findViewById(R.id.activity_my_basket_tv_total_amount);
        phoneNumberTv = (TextView) findViewById(R.id.phone_number_tv);


        cartLists = new ArrayList<CartList>();

        myBasketAdapter = new MyBasketAdapter(this, cartLists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        activityMyBasketRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        activityMyBasketRecyclerView.setLayoutManager(layoutManager);
        activityMyBasketRecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityMyBasketRecyclerView.setAdapter(myBasketAdapter);

        orderId = getIntent().getExtras().getString("order_id");

        orderHistoryMvpPresenter.getOrderHistoryDetail(orderId);

        activityMyBasketLlSubtotal = (LinearLayout) findViewById(R.id.activity_my_basket_ll_subtotal);
        activityMyBasketTvSubtotal = (TextView) findViewById(R.id.activity_my_basket_tv_subtotal);
        customerMoreOptionLl = (LinearLayout) findViewById(R.id.customer_more_option_ll);
        reviewBt = (RippleAppCompatButton) findViewById(R.id.review_bt);
        repeatOrderBt = (RippleAppCompatButton) findViewById(R.id.repeat_order_bt);
        changeStatusTv = (TextView) findViewById(R.id.change_status_tv);
        changeStatusTv.setOnClickListener(this);
        repeatOrderBt.setOnClickListener(this);
        reviewBt.setOnClickListener(this);
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
    public void setOrderHistoryDetail(OrderHistoryDetail orderHistoryDetail) {

        this.orderHistoryDetail = orderHistoryDetail;
        orderNumberTv.setText(orderHistoryDetail.getResponse().getOrderDetail().getOrderId());

        if (appDataManager.getCurrentUserId().equals(orderHistoryDetail.getResponse().getRestaurantId())) {

            nameTv.setText(getString(R.string.order_by) + " " + orderHistoryDetail.getResponse().getUserDetail().getFirstName() + " " + orderHistoryDetail.getResponse().getUserDetail().getLastName());
            userImageIv.setVisibility(View.GONE);
            phoneNumberTv.setText(orderHistoryDetail.getResponse().getUserDetail().getMobileNumber());
      /*      Glide.with(mContext)
                    .load(orderHistoryDetail.getResponse().getUserDetail().)
                    .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .error(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .into(userImageIv);*/
            reviewBt.setVisibility(View.GONE);
            repeatOrderBt.setVisibility(View.GONE);


        } else {
            phoneNumberTv.setText(orderHistoryDetail.getResponse().getMobileNumber1());
            nameTv.setText(orderHistoryDetail.getResponse().getRestaurantName());
            userImageIv.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(orderHistoryDetail.getResponse().getLogo())
                    .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .error(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .into(userImageIv);

        }

        orderTypeTv.setText(orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType());

        paymentTypeTv.setText(orderHistoryDetail.getResponse().getOrderDetail().getPaymentMethod());
        dateTv.setText(orderHistoryDetail.getResponse().getOrderDetail().getOrderDelieveryDate() + " at " + orderHistoryDetail.getResponse().getOrderDetail().getOrderDelieveryTime());
        if (orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType().equalsIgnoreCase("Deliver")) {
            deliveryAddressTv.setText(orderHistoryDetail.getResponse().getDeliveryAddress() + "\n" + getString(R.string.landmark) + ": " + orderHistoryDetail.getResponse().getLandmark());
            phoneNumberTv.setText(orderHistoryDetail.getResponse().getDelivery_mobile_number());
        } else {
            deliverAddressTv.setVisibility(View.GONE);
        }

        orderStatusTv.setText(orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus());

        cartLists.clear();
        cartLists.addAll(orderHistoryDetail.getResponse().getItemList());

        myBasketAdapter.notifyDataSetChanged();


        activityMyBasketTvDeliveryCharges.setText("$" + orderHistoryDetail.getResponse().getDeliveryCharge().replace(".00", ".0"));

        final String deliveryTypes[] = getResources().getStringArray(R.array.delivery_type);


        if (orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType().equalsIgnoreCase(deliveryTypes[0])) {

            activityMyBasketLlDeliveryCharges.setVisibility(View.GONE);

        } else if (orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType().equalsIgnoreCase(deliveryTypes[1])) {
            activityMyBasketLlDeliveryCharges.setVisibility(View.VISIBLE);
        }

        if (orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("decline") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("Picked") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("delivered"))
            changeStatusTv.setVisibility(View.GONE);
        else
            changeStatusTv.setVisibility(View.VISIBLE);

        updateTotalAmount();

    }

    @Override
    public void setOrderStatus(ChangeOrderStatusResponse changeOrderStatusResponse) {
        orderStatusTv.setText(changeOrderStatusResponse.getResponse().getCurrentStatus());
        changeOrderStatusResponse.getResponse().setCurrentStatus(changeOrderStatusResponse.getResponse().getCurrentStatus());

        if (orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("decline") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("Picked") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("delivered"))
            changeStatusTv.setVisibility(View.GONE);
        else
            changeStatusTv.setVisibility(View.VISIBLE);
    }


    public void updateTotalAmount() {

        float totalAmount = 0;

        for (int i = 0; i < cartLists.size(); i++) {

            CartList cartItem = cartLists.get(i);

            float price = Float.parseFloat(cartItem.getPrice());
            int quantity = Integer.parseInt(cartItem.getQty());

            totalAmount += price * quantity;
        }

        if (activityMyBasketLlDeliveryCharges.getVisibility() == View.VISIBLE) {

            activityMyBasketTvSubtotal.setText("$" + totalAmount);

            totalAmount += Float.parseFloat(orderHistoryDetail.getResponse().getDeliveryCharge());
            activityMyBasketLlSubtotal.setVisibility(View.VISIBLE);

        } else {
            activityMyBasketLlSubtotal.setVisibility(View.GONE);
        }

        activityMyBasketTvTotalAmount.setText("$" + totalAmount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_status_tv:
                OrderStatusDialogFragment dishListDialogFragment = new OrderStatusDialogFragment(orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType(), orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus());
                dishListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
                dishListDialogFragment.show(getSupportFragmentManager(), "OrderStatusDialogFragment");
                break;
        }
    }

    @Override
    public void OrderStatusReturn(String status) {
        orderHistoryMvpPresenter.ChangeOrderStatus(orderId, status);
    }
}
