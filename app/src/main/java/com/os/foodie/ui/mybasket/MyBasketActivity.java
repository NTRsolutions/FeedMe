package com.os.foodie.ui.mybasket;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.cart.view.MinOrderDiscount;
import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.data.network.model.checkout.CheckoutResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.MyBasketAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RecyclerTouchListener;
import com.os.foodie.ui.deliveryaddress.select.SelectDeliveryAddressActivity;
import com.os.foodie.ui.details.restaurant.RestaurantDetailsActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.ui.payment.select.SelectPaymentActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.DialogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class MyBasketActivity extends BaseActivity implements MyBasketMvpView, View.OnClickListener {

    private Calendar selectedCalendar;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private RelativeLayout rlMain, rlSchedule;
    private CheckedTextView ctvPickup, ctvDeliver;
    private EditText etNote;
    private LinearLayout llSubtotal, llDeliveryType, llDeliveryCharges, llDiscountAmount;
    private TextView tvDiscountAmount, tvDeliveryCharges, tvSubtotalAmount, tvTotalAmount, tvScheduledTime, tvEmptyBasket;
    private Button btCheckout;

    private RecyclerView recyclerView;
    private MyBasketAdapter myBasketAdapter;
    private ArrayList<CartList> cartLists;

    private Paint p = new Paint();

    private ViewCartResponse viewCartResponse;

    private String discountId = "", restaurantId = "";
    private float discountAmount;
    private final String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private MyBasketMvpPresenter<MyBasketMvpView> myBasketMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basket);

        initPresenter();
//        myBasketMvpPresenter = new MyBasketPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        myBasketMvpPresenter.onAttach(MyBasketActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        selectedCalendar = Calendar.getInstance();

        if (getIntent().hasExtra(AppConstants.RESTAURANT_ID)) {
            restaurantId = getIntent().getStringExtra(AppConstants.RESTAURANT_ID);
        }

        rlMain = (RelativeLayout) findViewById(R.id.activity_my_basket_rl_main);
        tvEmptyBasket = (TextView) findViewById(R.id.activity_my_basket_tv_empty_basket);

        etNote = (EditText) findViewById(R.id.activity_my_basket_et_note);

        ctvPickup = (CheckedTextView) findViewById(R.id.activity_my_basket_ctv_pickup);
        ctvDeliver = (CheckedTextView) findViewById(R.id.activity_my_basket_ctv_deliver);

        rlSchedule = (RelativeLayout) findViewById(R.id.activity_my_basket_rl_schedule);
        tvScheduledTime = (TextView) findViewById(R.id.activity_my_basket_tv_date_time);

        tvDiscountAmount = (TextView) findViewById(R.id.activity_my_basket_tv_discount_amount);
        tvDeliveryCharges = (TextView) findViewById(R.id.activity_my_basket_tv_delivery_charges);
        tvSubtotalAmount = (TextView) findViewById(R.id.activity_my_basket_tv_subtotal);
        tvTotalAmount = (TextView) findViewById(R.id.activity_my_basket_tv_total_amount);

        llSubtotal = (LinearLayout) findViewById(R.id.activity_my_basket_ll_subtotal);
        llDeliveryType = (LinearLayout) findViewById(R.id.activity_my_basket_ll_delivery_type);
        llDeliveryCharges = (LinearLayout) findViewById(R.id.activity_my_basket_ll_delivery_charges);
        llDiscountAmount = (LinearLayout) findViewById(R.id.activity_my_basket_ll_discount_amount);

        btCheckout = (Button) findViewById(R.id.activity_my_basket_bt_checkout);

        rlSchedule.setOnClickListener(this);
        btCheckout.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_my_basket_recyclerview);

        cartLists = new ArrayList<CartList>();
        viewCartResponse = new ViewCartResponse();

        myBasketAdapter = new MyBasketAdapter(this, cartLists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myBasketAdapter);

        RecyclerTouchListener.ClickListener clickListener = new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dialog_item_quantity, null);

                final Dialog mBottomSheetDialog = new Dialog(MyBasketActivity.this, R.style.AlertDialogBottomSlide);

                TextView tvItemName = (TextView) dialogView.findViewById(R.id.dialog_item_quantity_tv_item_name);
                TextView tvItemQuantity = (TextView) dialogView.findViewById(R.id.dialog_item_quantity_tv_item_quantity);
                TextView tvPrice = (TextView) dialogView.findViewById(R.id.dialog_item_quantity_tv_price);
                TextView tvCurrency = (TextView) dialogView.findViewById(R.id.dialog_item_quantity_tv_price_currency);

                tvCurrency.setText(myBasketAdapter.getCurrency());

                ImageView ivMinus = (ImageView) dialogView.findViewById(R.id.dialog_item_quantity_iv_minus);
                ImageView ivPlus = (ImageView) dialogView.findViewById(R.id.dialog_item_quantity_iv_plus);

                Button btUpdate = (Button) dialogView.findViewById(R.id.dialog_item_quantity_bt_update);

                final CartList cartItem = cartLists.get(position);

                tvItemName.setText(cartItem.getName());
                tvItemQuantity.setText(cartItem.getQty());

                float price = Float.parseFloat(cartItem.getPrice());
                int quantity = Integer.parseInt(cartItem.getQty());

                int totalAmount = 0;
                totalAmount += price * quantity;

                tvPrice.setText(totalAmount + "");

                final TextView tvItemQuantityTemp = tvItemQuantity;
                final TextView tvPriceTemp = tvPrice;
                final Button btUpdateTemp = btUpdate;

                ivMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        float price = Float.parseFloat(cartLists.get(position).getPrice());
                        int quantity = Integer.parseInt(tvItemQuantityTemp.getText().toString());

                        if (quantity > 0) {

                            quantity--;

                            int totalAmount = 0;
                            totalAmount += price * quantity;

                            Log.d("quantity", ">>" + quantity);
                            Log.d("price", ">>" + price);
                            Log.d("totalAmount", ">>" + totalAmount);

                            tvPriceTemp.setText(totalAmount + "");
                            tvItemQuantityTemp.setText(quantity + "");
                        }

                        if (quantity <= 0) {
                            btUpdateTemp.setText(getString(R.string.dialog_item_quantity_bt_remove_text));
                            btUpdateTemp.setTextColor(ContextCompat.getColor(MyBasketActivity.this, R.color.red));
                        }
                    }
                });

                ivPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        float price = Float.parseFloat(cartLists.get(position).getPrice());
                        int quantity = Integer.parseInt(tvItemQuantityTemp.getText().toString());

                        if (quantity >= 0) {

                            quantity++;

                            int totalAmount = 0;
                            totalAmount += price * quantity;

                            Log.d("quantity", ">>" + quantity);
                            Log.d("price", ">>" + price);
                            Log.d("totalAmount", ">>" + totalAmount);

                            tvPriceTemp.setText(totalAmount + "");
                            tvItemQuantityTemp.setText(quantity + "");

                            btUpdateTemp.setText(getString(R.string.dialog_item_quantity_bt_update_text));
                            btUpdateTemp.setTextColor(ContextCompat.getColor(MyBasketActivity.this, R.color.orange));
                        }
                    }
                });

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tvItemQuantityTemp.getText().toString().equals("0")) {
//                            cartLists.remove(position);
//
//                            myBasketAdapter.notifyDataSetChanged();
//                            updateTotalAmount();
                            mBottomSheetDialog.dismiss();
                            myBasketMvpPresenter.removeFromMyBasket(AppController.get(MyBasketActivity.this).getAppDataManager().getCurrentUserId(), cartLists.get(position).getDishId(), viewCartResponse.getResponse().getRestaurantId(), position);

                        } else {
//                            cartLists.get(position).setQty(tvItemQuantityTemp.getText().toString());
//
//                            myBasketAdapter.notifyDataSetChanged();
//                            updateTotalAmount();
                            mBottomSheetDialog.dismiss();
                            myBasketMvpPresenter.updateMyBasket(AppController.get(MyBasketActivity.this).getAppDataManager().getCurrentUserId(), cartLists.get(position).getDishId(), viewCartResponse.getResponse().getRestaurantId(), tvItemQuantityTemp.getText().toString(), cartLists.get(position).getPrice(), position);
                        }
                    }
                });

                mBottomSheetDialog.setContentView(dialogView); // your custom view.
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        };

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, clickListener));

        myBasketMvpPresenter.getMyBasketDetails(AppController.get(this).getAppDataManager().getCurrentUserId(), restaurantId);

        initSwipe();
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        myBasketMvpPresenter = new MyBasketPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_basket, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_clear_basket) {


            if (cartLists != null && cartLists.size() != 0) {
//                myBasketMvpPresenter.clearBasket();
                askForClearBasket();
            }
        }

        return true;
    }

    @Override
    public void setMyBasket(final ViewCartResponse viewCartResponse) {

        if (viewCartResponse.getResponse().getCartList() == null || viewCartResponse.getResponse().getCartList().size() == 0) {
            rlMain.setVisibility(View.GONE);
            tvEmptyBasket.setVisibility(View.VISIBLE);
            return;
        } else {
            rlMain.setVisibility(View.VISIBLE);
            tvEmptyBasket.setVisibility(View.GONE);
        }

        tvScheduledTime.setText(getString(R.string.my_basket_tv_date_time_text));
//        tvScheduledTime.setText(new SimpleDateFormat("dd-MM-yyyy").format(selectedCalendar.getTime()) + " at " + new SimpleDateFormat("hh:mm a").format(selectedCalendar.getTime()));

        this.viewCartResponse = viewCartResponse;

        cartLists.clear();
        cartLists.addAll(viewCartResponse.getResponse().getCartList());

        myBasketAdapter.notifyDataSetChanged();
        myBasketAdapter.setCurrency(CommonUtils.dataDecode(viewCartResponse.getResponse().getCurrency()));
//        try {
//            myBasketAdapter.setCurrency(URLDecoder.decode(viewCartResponse.getResponse().getCurrency(), "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        if (viewCartResponse.getResponse().getDeliveryCharge().contains(".00")) {
//            tvDeliveryCharges.setText("$" + viewCartResponse.getResponse().getDeliveryCharge().replace(".00", ""));
//        } else {
        tvDeliveryCharges.setText("+" + myBasketAdapter.getCurrency() + viewCartResponse.getResponse().getDeliveryCharge().replace(".00", ".0"));
//        }
//        tvDiscountAmount.setText(viewCartResponse.getResponse().());

        final String deliveryTypes[] = getResources().getStringArray(R.array.delivery_type);

        Log.d("getDeliveryType", ">>" + viewCartResponse.getResponse().getDeliveryType());

        if (viewCartResponse.getResponse().getDeliveryType().equalsIgnoreCase(deliveryTypes[0])) {

            llDeliveryCharges.setVisibility(View.GONE);
            llDeliveryType.setVisibility(View.GONE);

        } else if (viewCartResponse.getResponse().getDeliveryType().trim().equalsIgnoreCase(deliveryTypes[1])) {

            llDeliveryCharges.setVisibility(View.VISIBLE);
            llDeliveryType.setVisibility(View.GONE);

        } else if (viewCartResponse.getResponse().getDeliveryType().equalsIgnoreCase(deliveryTypes[2])) {

            llDeliveryType.setVisibility(View.VISIBLE);
            llDeliveryCharges.setVisibility(View.GONE);

            viewCartResponse.getResponse().setDeliveryType(deliveryTypes[0]);
            ctvPickup.setChecked(true);
            ctvDeliver.setChecked(false);

            ctvPickup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!ctvPickup.isChecked()) {
                        Log.d("ctvPickup", "isChecked");
                        llDeliveryCharges.setVisibility(View.GONE);
                        viewCartResponse.getResponse().setDeliveryType(deliveryTypes[0]);
                        updateTotalAmount();
                    }

                    ctvPickup.setChecked(true);
                    ctvDeliver.setChecked(false);
                }
            });

            ctvDeliver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!ctvDeliver.isChecked()) {
                        Log.d("ctvDeliver", "isChecked");
                        llDeliveryCharges.setVisibility(View.VISIBLE);
                        viewCartResponse.getResponse().setDeliveryType(deliveryTypes[1]);
                        updateTotalAmount();
                    }

                    ctvPickup.setChecked(false);
                    ctvDeliver.setChecked(true);
                }
            });
        }

        updateTotalAmount();
    }

    public float calcDishDiscount() {

        discountAmount = 0;

        float totalAmount = 0;

//        ArrayList<CartList> cartLists = (ArrayList<CartList>) viewCartResponse.getResponse().getCartList();

        if (cartLists != null && !cartLists.isEmpty()) {

            for (int i = 0; i < cartLists.size(); i++) {

                if (cartLists.get(i).getDiscountId() != null && !cartLists.get(i).getDiscountId().isEmpty()) {

                    float discount = Float.parseFloat(cartLists.get(i).getAvailableMaxDiscount());

                    float dishAmount = Float.parseFloat(cartLists.get(i).getPrice()) * Float.parseFloat(cartLists.get(i).getQty());

                    Log.d("dishAmount Before", ">>" + dishAmount);

                    float discountAmountTemp = dishAmount * discount / 100;

                    totalAmount += (dishAmount - discountAmountTemp);

                    Log.d("dishAmount After", ">>" + (dishAmount - discountAmountTemp));

                    discountAmount += discountAmountTemp;

                } else {

                    Log.d("dishAmount Before", ">>" + (Float.parseFloat(cartLists.get(i).getPrice()) * Float.parseFloat(cartLists.get(i).getQty())));

                    totalAmount += Float.parseFloat(cartLists.get(i).getPrice()) * Float.parseFloat(cartLists.get(i).getQty());

                    Log.d("dishAmount After", ">>" + (Float.parseFloat(cartLists.get(i).getPrice()) * Float.parseFloat(cartLists.get(i).getQty())));
                }
            }
        }


        Log.d("totalAmount Basket", ">>" + totalAmount);
        Log.d("discountAmount Basket", ">>" + discountAmount);

        return calcFinalDiscount(totalAmount);
    }

    public float calcFinalDiscount(float totalAmount) {

        ArrayList<MinOrderDiscount> minOrderDiscounts = (ArrayList<MinOrderDiscount>) viewCartResponse.getResponse().getMinOrderDiscounts();

        if (minOrderDiscounts != null && !minOrderDiscounts.isEmpty()) {

            float minOrderDiscount = 0;
            float minOrderDiscountAmount = 0;

            for (int i = 0; i < minOrderDiscounts.size(); i++) {

                if (Float.parseFloat(minOrderDiscounts.get(i).getMinOrderAmount()) <= totalAmount && minOrderDiscountAmount < Float.parseFloat(minOrderDiscounts.get(i).getMinOrderAmount())) {

                    discountId = minOrderDiscounts.get(i).getDiscountId();
                    minOrderDiscount = Float.parseFloat(minOrderDiscounts.get(i).getDiscountPercentage());
                    minOrderDiscountAmount = Float.parseFloat(minOrderDiscounts.get(i).getMinOrderAmount());
                }
            }

            Log.d("minOrderDiscount Final", ">>" + minOrderDiscount);

            if (minOrderDiscount != 0F) {
                discountAmount += totalAmount * minOrderDiscount / 100;
            }
        }

        Log.d("totalAmount Final", ">>" + totalAmount);
        Log.d("discountAmount Final", ">>" + discountAmount);

        return discountAmount;
    }

    @Override
    public void onBasketClear() {

        Intent intent = new Intent();
        intent.putExtra(AppConstants.RESTAURANT_ID, restaurantId);

        setResult(20, intent);
        finish();
        rlMain.setVisibility(View.GONE);
        tvEmptyBasket.setVisibility(View.VISIBLE);
    }

    public void updateTotalAmount() {

        if (cartLists == null || cartLists.size() == 0) {
            rlMain.setVisibility(View.GONE);
            tvEmptyBasket.setVisibility(View.VISIBLE);
            myBasketMvpPresenter.clearBasketRestaurant();

            Intent intent = new Intent();
            intent.putExtra(AppConstants.RESTAURANT_ID, restaurantId);

            setResult(20, intent);
            finish();

            return;
        } else {
            rlMain.setVisibility(View.VISIBLE);
            tvEmptyBasket.setVisibility(View.GONE);
        }

        float totalAmount = 0;

        Log.d("Total cartLists size", ">>" + cartLists.size());

        for (int i = 0; i < cartLists.size(); i++) {

            CartList cartItem = cartLists.get(i);

            float price = Float.parseFloat(cartItem.getPrice());
            int quantity = Integer.parseInt(cartItem.getQty());

            totalAmount += price * quantity;
        }

        boolean isSubtotal = false;
        float discountAmount = calcDishDiscount();

        if (discountAmount > 0) {

            tvSubtotalAmount.setText(myBasketAdapter.getCurrency() + totalAmount);

            isSubtotal = true;
            llSubtotal.setVisibility(View.VISIBLE);
            llDiscountAmount.setVisibility(View.VISIBLE);

            tvDiscountAmount.setText("-" + myBasketAdapter.getCurrency() + calcDishDiscount());

            totalAmount -= discountAmount;

        } else {
            llDiscountAmount.setVisibility(View.GONE);
        }

        if (llDeliveryCharges.getVisibility() == View.VISIBLE) {

            if (!isSubtotal) {
                tvSubtotalAmount.setText(myBasketAdapter.getCurrency() + totalAmount);
            }

            totalAmount += Float.parseFloat(viewCartResponse.getResponse().getDeliveryCharge());
            llSubtotal.setVisibility(View.VISIBLE);

        } else {

            if (!isSubtotal) {
                llSubtotal.setVisibility(View.GONE);
            }
        }

        tvTotalAmount.setText(myBasketAdapter.getCurrency() + totalAmount);
    }

    public void askForClearBasket() {

        DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                myBasketMvpPresenter.clearBasket(restaurantId);
            }
        };

        DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        DialogUtils.showAlert(this,
                R.string.alert_dialog_title_clear_basket, R.string.alert_dialog_text_clear_basket_2,
                getResources().getString(R.string.alert_dialog_bt_ok), positiveButton,
                getResources().getString(R.string.alert_dialog_bt_cancel), negativeButton);
    }

    @Override
    public void itemRemovedFromBasket(int position) {

        Log.d("cartLists size Bef", ">>" + cartLists.size());

        cartLists.remove(position);
//        myBasketAdapter.removeItem(position);
        myBasketAdapter.notifyDataSetChanged();

        Log.d("cartLists size Aft", ">>" + cartLists.size());

        updateTotalAmount();
    }

    @Override
    public void updateMyBasket(int position, String quantity, String totalQuantity, String totalAmount) {

        cartLists.get(position).setQty(quantity);

        myBasketAdapter.notifyDataSetChanged();

        updateTotalAmount();
    }

    @Override
    public void onCheckoutComplete(CheckoutResponse checkoutResponse) {

        Toast.makeText(this, checkoutResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(MyBasketActivity.this, CustomerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent intentOrderDetails = new Intent(MyBasketActivity.this, OrderHistoryDetailActivity.class);
        intentOrderDetails.putExtra("order_id", checkoutResponse.getResponse().getOrderId());
        intentOrderDetails.putExtra("showUpdateButton", false);

        Intent[] intents = {intent, intentOrderDetails};
        startActivities(intents);

    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void onDestroy() {
        myBasketMvpPresenter.dispose();
//        myBasketMvpPresenter.onDetach();
        super.onDestroy();
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT/* | ItemTouchHelper.RIGHT*/) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    myBasketMvpPresenter.removeFromMyBasket(AppController.get(MyBasketActivity.this).getAppDataManager().getCurrentUserId(), cartLists.get(position).getDishId(), viewCartResponse.getResponse().getRestaurantId(), position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX <= 0) {
                        p.setColor(ContextCompat.getColor(MyBasketActivity.this, R.color.orange));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_delete_2);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btCheckout.getId()) {

            DialogInterface.OnClickListener online = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doNext(1);
                    dialog.dismiss();
                }
            };

            DialogInterface.OnClickListener cod = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doNext(2);
                    dialog.dismiss();
                }
            };

            if (viewCartResponse.getResponse().getPaymentType().equalsIgnoreCase(AppConstants.ONLINE)) {

                DialogUtils.showAlert(MyBasketActivity.this,
                        R.string.alert_dialog_title_payment_method, R.string.alert_dialog_text_payment_method,
                        getResources().getString(R.string.alert_dialog_bt_online), online);

            } else if (viewCartResponse.getResponse().getPaymentType().equalsIgnoreCase(AppConstants.COD)) {

                DialogUtils.showAlert(MyBasketActivity.this,
                        R.string.alert_dialog_title_payment_method, R.string.alert_dialog_text_payment_method,
                        getResources().getString(R.string.alert_dialog_bt_cod), cod);

            } else {

                DialogUtils.showAlert(MyBasketActivity.this,
                        R.string.alert_dialog_title_payment_method, R.string.alert_dialog_text_payment_method,
                        getResources().getString(R.string.alert_dialog_bt_online), online,
                        getResources().getString(R.string.alert_dialog_bt_cod), cod);
            }

        } else if (v.getId() == rlSchedule.getId()) {
//            myBasketMvpPresenter.dateTimePickerDialog(this, viewCartResponse, tvScheduledTime);
            dateTimePickerDialog();
        }
    }

    public void doNext(int method) {

        CheckoutRequest checkoutRequest = new CheckoutRequest();

        String deliveryTypes[] = getResources().getStringArray(R.array.delivery_type);
        String deliveryType = viewCartResponse.getResponse().getDeliveryType();

        checkoutRequest.setDiscountId(discountId);
        checkoutRequest.setUserId(AppController.get(this).getAppDataManager().getCurrentUserId());
        checkoutRequest.setDeliveryType(deliveryType);

        checkoutRequest.setCardId("");
        checkoutRequest.setRestaurantId(restaurantId);
        checkoutRequest.setUserAddressId("");

        selectedCalendar.set(Calendar.SECOND, 59);

        Log.d("discountId", ">>" + discountId);
        Log.d("deliveryType", ">>" + deliveryType);
        Log.d("method", ">>" + method);

        if (method == 1) {

            if (deliveryType.equalsIgnoreCase(deliveryTypes[0])) {

                Log.d("Pick only", "ONLINE");

                checkoutRequest.setPaymentMethod(AppConstants.ONLINE);

                if (tvScheduledTime.getText().toString().isEmpty() || tvScheduledTime.getText().toString().equalsIgnoreCase(getString(R.string.my_basket_tv_date_time_text))) {
//                    checkoutRequest.setOrderDelieveryDate("");
//                    checkoutRequest.setOrderDelieveryTime("");

//                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(Calendar.getInstance().getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(Calendar.getInstance().getTime()));

                } else {

                    if (selectedCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                        myBasketMvpPresenter.onError(R.string.past_time);
                        return;
                    } else if (openingClosingTimeCheck()) {
                        return;
                    }

                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(selectedCalendar.getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(selectedCalendar.getTime()));
                }

                Intent intent = new Intent(MyBasketActivity.this, SelectPaymentActivity.class);
                intent.putExtra(AppConstants.CHECKOUT, checkoutRequest);
                startActivity(intent);

            } else if (deliveryType.equalsIgnoreCase(deliveryTypes[1])) {

                Log.d("Delivery", "ONLINE");

                checkoutRequest.setPaymentMethod(AppConstants.ONLINE);

                if (tvScheduledTime.getText().toString().isEmpty() || tvScheduledTime.getText().toString().equalsIgnoreCase(getString(R.string.my_basket_tv_date_time_text))) {
//                    checkoutRequest.setOrderDelieveryDate("");
//                    checkoutRequest.setOrderDelieveryTime("");

                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(Calendar.getInstance().getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(Calendar.getInstance().getTime()));

                } else {

                    if (selectedCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                        myBasketMvpPresenter.onError(R.string.past_time);
                        return;
                    } else if (openingClosingTimeCheck()) {
                        return;
                    }

                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(selectedCalendar.getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(selectedCalendar.getTime()));
                }

                Intent intent = new Intent(MyBasketActivity.this, SelectDeliveryAddressActivity.class);
                intent.putExtra(AppConstants.CHECKOUT, checkoutRequest);
                intent.putExtra(AppConstants.DELIVERY_ADDRESS, viewCartResponse.getResponse().getDeliveryZipCode());
                startActivity(intent);
            }

        } else {

            if (deliveryType.equalsIgnoreCase(deliveryTypes[0])) {

                Log.d("Pick only", "COD");

                checkoutRequest.setPaymentMethod(AppConstants.COD);

                if (tvScheduledTime.getText().toString().isEmpty() || tvScheduledTime.getText().toString().equalsIgnoreCase(getString(R.string.my_basket_tv_date_time_text))) {
//                    checkoutRequest.setOrderDelieveryDate("");
//                    checkoutRequest.setOrderDelieveryTime("");

                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(Calendar.getInstance().getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(Calendar.getInstance().getTime()));

                } else {

                    if (selectedCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                        myBasketMvpPresenter.onError(R.string.past_time);
                        return;
                    } else if (openingClosingTimeCheck()) {
                        return;
                    }

                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(selectedCalendar.getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(selectedCalendar.getTime()));
                }

                myBasketMvpPresenter.checkout(checkoutRequest);

            } else if (deliveryType.equalsIgnoreCase(deliveryTypes[1])) {

                Log.d("Delivery", "COD");

                checkoutRequest.setPaymentMethod(AppConstants.COD);

                if (tvScheduledTime.getText().toString().isEmpty() || tvScheduledTime.getText().toString().equalsIgnoreCase(getString(R.string.my_basket_tv_date_time_text))) {
//                    checkoutRequest.setOrderDelieveryDate("");
//                    checkoutRequest.setOrderDelieveryTime("");

                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(Calendar.getInstance().getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(Calendar.getInstance().getTime()));

                } else {

                    if (selectedCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                        myBasketMvpPresenter.onError(R.string.past_time);
                        return;
                    } else if (openingClosingTimeCheck()) {
                        return;
                    }

                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");

                    checkoutRequest.setOrderDelieveryDate(simpleDateFormatDate.format(selectedCalendar.getTime()));
                    checkoutRequest.setOrderDelieveryTime(simpleDateFormatTime.format(selectedCalendar.getTime()));
                }

                Intent intent = new Intent(MyBasketActivity.this, SelectDeliveryAddressActivity.class);
                intent.putExtra(AppConstants.CHECKOUT, checkoutRequest);
                intent.putExtra(AppConstants.DELIVERY_ADDRESS, viewCartResponse.getResponse().getDeliveryZipCode());
                startActivity(intent);
            }
        }
    }

    public void dateTimePickerDialog() {

//        this.tvScheduledTime = tvScheduledTime;

//        selectedCalendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

        final Calendar timeFrom = Calendar.getInstance();
        final Calendar timeTo = Calendar.getInstance();

        try {
            timeFrom.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getOpeningTime()));
            timeTo.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getClosingTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();

        toCalendar.setTimeInMillis(fromCalendar.getTimeInMillis() + (24 * 60 * 60 * 1000 * 7));

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                setTimePickerDialogListenerAction(MyBasketActivity.this.tvScheduledTime, selectedCalendar, timeFrom, timeTo, hourOfDay, minute);
            }
        };

        timePickerDialog = new TimePickerDialog(this, onTimeSetListener, fromCalendar.get(Calendar.HOUR_OF_DAY), fromCalendar.get(Calendar.MINUTE), false);

//        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
//                getString(R.string.alert_dialog_bt_ok),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == DialogInterface.BUTTON_POSITIVE) {
////                            dialog.cancel();
//                            setTimePickerDialogListenerAction(MyBasketActivity.this.tvScheduledTime, selectedCalendar, timeFrom, timeTo, selectedCalendar.get(Calendar.HOUR_OF_DAY), selectedCalendar.get(Calendar.MINUTE));
//                        }
//                    }
//                });


        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                setDatePickerDialogListenerAction(viewCartResponse, selectedCalendar, year, month, dayOfMonth);
            }
        };

        datePickerDialog = new DatePickerDialog(this, onDateSetListener, fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH), fromCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(fromCalendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(toCalendar.getTimeInMillis());

//        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
//                getString(R.string.alert_dialog_bt_ok),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == DialogInterface.BUTTON_POSITIVE) {
////                            dialog.cancel();
//                            setDatePickerDialogListenerAction(viewCartResponse, selectedCalendar, selectedCalendar.get(Calendar.YEAR), selectedCalendar.get(Calendar.MONTH), selectedCalendar.get(Calendar.DAY_OF_MONTH));
//                        }
//                    }
//                });

        datePickerDialog.show();
    }

    public void setDatePickerDialogListenerAction(ViewCartResponse viewCartResponse, Calendar selectedCalendar, int year, int month, int dayOfMonth) {


        Calendar tempCalendar = Calendar.getInstance();

        tempCalendar.set(Calendar.YEAR, year);
        tempCalendar.set(Calendar.MONTH, month);
        tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        boolean isOk = false;
        String[] selectedWeekDays = viewCartResponse.getResponse().getWorkingDays().split(",");

        for (int i = 0; i < weekDays.length; i++) {

            if (weekDays[tempCalendar.get(Calendar.DAY_OF_WEEK) - 1].equalsIgnoreCase(selectedWeekDays[i])) {
                isOk = true;
                break;
            }
        }

        if (isOk) {

            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, month);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            datePickerDialog.dismiss();
            timePickerDialog.show();

        } else {
            Log.d("Problem", "Restaurant don't open on this week day");
            myBasketMvpPresenter.onError(R.string.invalid_weekday);
        }
    }

    public void setTimePickerDialogListenerAction(TextView tvScheduledTime, Calendar selectedCalendar, Calendar timeFrom, Calendar timeTo, int hourOfDay, int minute) {

        Log.d("hourOfDay", ">>" + hourOfDay);
        Log.d("minute", ">>" + minute);

        Log.d("timeFrom HOUR_OF_DAY", ">>" + timeFrom.get(Calendar.HOUR_OF_DAY));
        Log.d("timeFrom MINUTE", ">>" + timeFrom.get(Calendar.MINUTE));

        Log.d("timeTo HOUR_OF_DAY", ">>" + timeTo.get(Calendar.HOUR_OF_DAY));
        Log.d("timeTo MINUTE", ">>" + timeTo.get(Calendar.MINUTE));

        if (hourOfDay < timeFrom.get(Calendar.HOUR_OF_DAY) || hourOfDay > timeTo.get(Calendar.HOUR_OF_DAY)) {

            Log.d("Problem", "select time under restaurant opening and closing hours");
            myBasketMvpPresenter.onError(R.string.invalid_time);
//            getMvpView().onError("Restaurant don/'t open on this time");

        } else if (hourOfDay == timeFrom.get(Calendar.HOUR_OF_DAY) && minute < timeFrom.get(Calendar.MINUTE)) {

            Log.d("Problem", "select time under restaurant opening and closing hours");
            myBasketMvpPresenter.onError(R.string.invalid_time);
//            getMvpView().onError("select time under restaurant opening and closing hours");

        } else if (hourOfDay == timeTo.get(Calendar.HOUR_OF_DAY) && minute > timeTo.get(Calendar.MINUTE)) {

            Log.d("Problem", "select time under restaurant opening and closing hours");
            myBasketMvpPresenter.onError(R.string.invalid_time);
//            getMvpView().onError("select time under restaurant opening and closing hours");

        } else {

            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedCalendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
            SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);

            if (selectedCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                myBasketMvpPresenter.onError(R.string.past_time);
                timePickerDialog.dismiss();
                return;
            }

            tvScheduledTime.setText(simpleDateFormatDate.format(selectedCalendar.getTime()) + " at " + simpleDateFormatTime.format(selectedCalendar.getTime()));

            timePickerDialog.dismiss();
        }
    }

    public boolean openingClosingTimeCheck() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);

        final Calendar timeFrom = Calendar.getInstance();
        final Calendar timeTo = Calendar.getInstance();

        try {
            timeFrom.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getOpeningTime()));
            timeTo.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getClosingTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectedCalendar.get(Calendar.HOUR_OF_DAY) < timeFrom.get(Calendar.HOUR_OF_DAY) || selectedCalendar.get(Calendar.HOUR_OF_DAY) > timeTo.get(Calendar.HOUR_OF_DAY)) {

            Log.d("Problem", "select time under restaurant opening and closing hours");
            myBasketMvpPresenter.onError(R.string.invalid_time);
//            getMvpView().onError("Restaurant don/'t open on this time");

            return true;

        } else if (selectedCalendar.get(Calendar.HOUR_OF_DAY) == timeFrom.get(Calendar.HOUR_OF_DAY) && selectedCalendar.get(Calendar.MINUTE) < timeFrom.get(Calendar.MINUTE)) {

            Log.d("Problem", "select time under restaurant opening and closing hours");
            myBasketMvpPresenter.onError(R.string.invalid_time);
//            getMvpView().onError("select time under restaurant opening and closing hours");

            return true;

        } else if (selectedCalendar.get(Calendar.HOUR_OF_DAY) == timeTo.get(Calendar.HOUR_OF_DAY) && selectedCalendar.get(Calendar.MINUTE) > timeTo.get(Calendar.MINUTE)) {

            Log.d("Problem", "select time under restaurant opening and closing hours");
            myBasketMvpPresenter.onError(R.string.invalid_time);

            return true;
        }

        return false;
    }

//
//    public void dateTimePickerDialog() {
//
//        final Calendar selectedCalendar = Calendar.getInstance();
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//
//        final Calendar timeFrom = Calendar.getInstance();
//        final Calendar timeTo = Calendar.getInstance();
//
//        try {
//            timeFrom.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getOpeningTime()));
//            timeTo.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getClosingTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Calendar fromCalendar = Calendar.getInstance();
//        Calendar toCalendar = Calendar.getInstance();
//
//        toCalendar.setTimeInMillis(fromCalendar.getTimeInMillis() + (24 * 60 * 60 * 1000 * 7));
//
//        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                Log.d("hourOfDay", ">>" + hourOfDay);
//
//                if (hourOfDay < timeFrom.get(Calendar.HOUR_OF_DAY) || minute < timeFrom.get(Calendar.MINUTE)
//                        || hourOfDay >= timeTo.get(Calendar.HOUR_OF_DAY) || minute >= timeTo.get(Calendar.MINUTE)) {
//
//                    Log.d("Problem", "select time under restaurant opening and closing hours");
//
//                } else {
//
//                    selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                    selectedCalendar.set(Calendar.MINUTE, minute);
//
//                }
//            }
//        };
//
//        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, fromCalendar.get(Calendar.HOUR_OF_DAY), fromCalendar.get(Calendar.MINUTE), false);
//
//        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                Calendar tempCalendar = Calendar.getInstance();
//
//                tempCalendar.set(Calendar.YEAR, year);
//                tempCalendar.set(Calendar.MONTH, month);
//                tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                boolean isOk = false;
//                String[] selectedWeekDays = viewCartResponse.getResponse().getWorkingDays().split(",");
//
//                for (int i = 0; i < weekDays.length; i++) {
//
//                    if (weekDays[tempCalendar.get(Calendar.DAY_OF_WEEK) - 1].equalsIgnoreCase(selectedWeekDays[i])) {
//                        isOk = true;
//                        break;
//                    }
//                }
//
//                if (isOk) {
//
//                    selectedCalendar.set(Calendar.YEAR, year);
//                    selectedCalendar.set(Calendar.MONTH, month);
//                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                    timePickerDialog.show();
//
//
//                } else {
//                    Log.d("Problem", "Restaurant don't open on this week day");
//                }
//            }
//        };
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH), fromCalendar.get(Calendar.DAY_OF_MONTH));
//
//        datePickerDialog.getDatePicker().setMinDate(fromCalendar.getTimeInMillis());
//        datePickerDialog.getDatePicker().setMaxDate(toCalendar.getTimeInMillis());
//    }
}