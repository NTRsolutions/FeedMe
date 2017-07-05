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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.changeorderstatus.ChangeOrderStatusResponse;
import com.os.foodie.data.network.model.order.restaurant.detail.OrderHistoryDetail;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.MyBasketAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.dialogfragment.orderstatus.OrderStatusCallback;
import com.os.foodie.ui.dialogfragment.orderstatus.OrderStatusDialogFragment;
import com.os.foodie.ui.dialogfragment.restaurantreview.RestaurantReviewDialogFragment;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class OrderHistoryDetailActivity extends BaseActivity implements OrderHistoryMvpView, View.OnClickListener, OrderStatusCallback {

    private ImageView ivUserImage;

    private TextView tvName, tvOrderNumber, tvOrderType, tvPaymentType, tvDate;
    private TextView tvDeliveryAddress, tvOrderStatus, tvDiscountAmount;
    private TextView tvTotalAmount, tvDeliveryCharges, tvPhoneNumber;
    private TextView tvChangeStatus, tvSubtotal;

    private LinearLayout llDeliverAddress, llCheckout, llDeliveryCharges;
    private LinearLayout llCustomerMoreOption, llSubtotal;

    private RippleAppCompatButton btReview, btRepeatOrder;

    private RecyclerView recyclerView;

    private Context mContext;

    private String orderId = "";
    private boolean showUpdateButton = false;

    private AppDataManager appDataManager;

    private ArrayList<CartList> cartLists;
    private MyBasketAdapter myBasketAdapter;

    private OrderHistoryDetail orderHistoryDetail;

    private OrderHistoryMvpPresenter<OrderHistoryMvpView> orderHistoryMvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initPresenter();
        orderHistoryMvpPresenter.onAttach(OrderHistoryDetailActivity.this);

        initView();

        cartLists = new ArrayList<CartList>();

        myBasketAdapter = new MyBasketAdapter(this, cartLists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myBasketAdapter);

        orderId = getIntent().getExtras().getString("order_id");

        showUpdateButton = getIntent().getExtras().getBoolean("showUpdateButton");

        orderHistoryMvpPresenter.getOrderHistoryDetail(orderId);
    }

    private void initView() {

        mContext = this;

        ivUserImage = (ImageView) findViewById(R.id.user_image_iv);

        tvName = (TextView) findViewById(R.id.name_tv);
        tvOrderNumber = (TextView) findViewById(R.id.order_number_tv);
        tvOrderType = (TextView) findViewById(R.id.order_type_tv);
        tvPaymentType = (TextView) findViewById(R.id.payment_type_tv);
        tvDate = (TextView) findViewById(R.id.date_tv);
        tvDeliveryAddress = (TextView) findViewById(R.id.delivery_address_tv);
        tvOrderStatus = (TextView) findViewById(R.id.order_status_tv);
        tvDiscountAmount = (TextView) findViewById(R.id.activity_my_basket_tv_discount_amount);
        tvPhoneNumber = (TextView) findViewById(R.id.phone_number_tv);
        tvDeliveryCharges = (TextView) findViewById(R.id.activity_my_basket_tv_delivery_charges);
        tvTotalAmount = (TextView) findViewById(R.id.activity_my_basket_tv_total_amount);
        tvChangeStatus = (TextView) findViewById(R.id.change_status_tv);
        tvSubtotal = (TextView) findViewById(R.id.activity_my_basket_tv_subtotal);

        llDeliverAddress = (LinearLayout) findViewById(R.id.deliver_address_tv);
        llCheckout = (LinearLayout) findViewById(R.id.activity_my_basket_ll_checkout);
        llDeliveryCharges = (LinearLayout) findViewById(R.id.activity_my_basket_ll_delivery_charges);
        llSubtotal = (LinearLayout) findViewById(R.id.activity_my_basket_ll_subtotal);
        llCustomerMoreOption = (LinearLayout) findViewById(R.id.customer_more_option_ll);

        btReview = (RippleAppCompatButton) findViewById(R.id.review_bt);
        btRepeatOrder = (RippleAppCompatButton) findViewById(R.id.repeat_order_bt);

        recyclerView = (RecyclerView) findViewById(R.id.activity_my_basket_recyclerview);

        tvChangeStatus.setOnClickListener(this);
        btRepeatOrder.setOnClickListener(this);
        btReview.setOnClickListener(this);
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
        tvOrderNumber.setText(orderHistoryDetail.getResponse().getOrderDetail().getOrderId());

        if (appDataManager.getCurrentUserId().equals(orderHistoryDetail.getResponse().getRestaurantId())) {

            tvName.setText(getString(R.string.order_by) + " " + orderHistoryDetail.getResponse().getUserDetail().getFirstName() + " " + orderHistoryDetail.getResponse().getUserDetail().getLastName());
            ivUserImage.setVisibility(View.GONE);
            tvPhoneNumber.setText(orderHistoryDetail.getResponse().getUserDetail().getMobileNumber());

      /*      Glide.with(mContext)
                    .load(orderHistoryDetail.getResponse().getUserDetail().)
                    .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .error(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder))
                    .into(ivUserImage);*/


        } else {

            tvPhoneNumber.setText(orderHistoryDetail.getResponse().getMobileNumber1());
            tvName.setText(orderHistoryDetail.getResponse().getRestaurantName());
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
            tvDeliveryAddress.setText(orderHistoryDetail.getResponse().getDeliveryAddress() + "\n" + getString(R.string.landmark) + ": " + orderHistoryDetail.getResponse().getLandmark());
            tvPhoneNumber.setText(orderHistoryDetail.getResponse().getDelivery_mobile_number());
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

        if (orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("decline") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("Picked") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("delivered")) {

            if (showUpdateButton)
                tvChangeStatus.setVisibility(View.VISIBLE);
            else
                tvChangeStatus.setVisibility(View.GONE);

        } else {

            if (showUpdateButton)
                tvChangeStatus.setVisibility(View.VISIBLE);
            else
                tvChangeStatus.setVisibility(View.GONE);
        }

        if (orderHistoryMvpPresenter.isCustomer() && ((orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("Picked") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("delivered")))) {

            btReview.setVisibility(View.VISIBLE);
            btRepeatOrder.setVisibility(View.VISIBLE);
        }

//        if(orderHistoryDetail.getResponse().get)
//        btReview.setVisibility(View.GONE);

        updateTotalAmount();
    }

    @Override
    public void setOrderStatus(ChangeOrderStatusResponse changeOrderStatusResponse) {

        tvOrderStatus.setText(changeOrderStatusResponse.getResponse().getCurrentStatus());

        changeOrderStatusResponse.getResponse().setCurrentStatus(changeOrderStatusResponse.getResponse().getCurrentStatus());

        if (orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("decline") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("Picked") || orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus().equalsIgnoreCase("delivered"))
            tvChangeStatus.setVisibility(View.GONE);
        else
            tvChangeStatus.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.repeat_order_bt:

//                AddToCartRequest addToCartRequest = new AddToCartRequest();
//
//                addToCartRequest.setUserId();
//                addToCartRequest.setQty(orderHistoryDetail.getResponse().get);
//
//                orderHistoryMvpPresenter.addItemToCart();

                break;

            case R.id.change_status_tv:

                OrderStatusDialogFragment dishListDialogFragment = new OrderStatusDialogFragment(orderHistoryDetail.getResponse().getOrderDetail().getDeliveryType(), orderHistoryDetail.getResponse().getOrderDetail().getOrderStatus());

                dishListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
                dishListDialogFragment.show(getSupportFragmentManager(), "OrderStatusDialogFragment");

                break;

            case R.id.review_bt:

                RestaurantReviewDialogFragment restaurantReviewDialogFragment = new RestaurantReviewDialogFragment();

                Bundle bundle = new Bundle();

                bundle.putString("restaurant_image", orderHistoryDetail.getResponse().getLogo());
                bundle.putString("restaurant_name", orderHistoryDetail.getResponse().getRestaurantName());
                bundle.putString("restaurant_id", orderHistoryDetail.getResponse().getRestaurantId());
                bundle.putString("order_id", orderHistoryDetail.getResponse().getOrderDetail().getOrderId());

                restaurantReviewDialogFragment.setArguments(bundle);

                restaurantReviewDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
                restaurantReviewDialogFragment.show(getSupportFragmentManager(), "RestaurantReviewDialogFragment");

                break;
        }
    }

    @Override
    public void OrderStatusReturn(String status) {
        orderHistoryMvpPresenter.ChangeOrderStatus(orderId, status);
    }
}