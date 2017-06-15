package com.os.foodie.ui.mybasket;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.ui.adapter.recyclerview.MyBasketAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RecyclerTouchListener;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressActivity;
import com.os.foodie.utils.DialogUtils;

import java.util.ArrayList;

public class MyBasketActivity extends BaseActivity implements MyBasketMvpView, View.OnClickListener {

    private RelativeLayout rlMain;
    private CheckedTextView ctvPickup, ctvDeliver;
    private EditText etNote;
    private LinearLayout llDeliveryType, llDeliveryCharges;
    private TextView tvDiscountAmount, tvDeliveryCharges, tvTotalAmount, tvEmptyBasket;
    private Button btCheckout;

    private RecyclerView recyclerView;
    private MyBasketAdapter myBasketAdapter;
    private ArrayList<CartList> cartLists;

    private ViewCartResponse viewCartResponse;

    private MyBasketMvpPresenter<MyBasketMvpView> myBasketMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basket);

        myBasketMvpPresenter = new MyBasketPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        myBasketMvpPresenter.onAttach(MyBasketActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        rlMain = (RelativeLayout) findViewById(R.id.activity_my_basket_rl_main);
        tvEmptyBasket = (TextView) findViewById(R.id.activity_my_basket_tv_empty_basket);

        etNote = (EditText) findViewById(R.id.activity_my_basket_et_note);

        ctvPickup = (CheckedTextView) findViewById(R.id.activity_my_basket_ctv_pickup);
        ctvDeliver = (CheckedTextView) findViewById(R.id.activity_my_basket_ctv_deliver);

        tvDiscountAmount = (TextView) findViewById(R.id.activity_my_basket_tv_discount_amount);
        tvDeliveryCharges = (TextView) findViewById(R.id.activity_my_basket_tv_delivery_charges);
        tvTotalAmount = (TextView) findViewById(R.id.activity_my_basket_tv_total_amount);

        llDeliveryType = (LinearLayout) findViewById(R.id.activity_my_basket_ll_delivery_type);
        llDeliveryCharges = (LinearLayout) findViewById(R.id.activity_my_basket_ll_delivery_charges);

        btCheckout = (Button) findViewById(R.id.activity_my_basket_bt_checkout);
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
                            btUpdateTemp.setText("Remove Item");
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

                            btUpdateTemp.setText("Update");
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

        myBasketMvpPresenter.getMyBasketDetails(AppController.get(this).getAppDataManager().getCurrentUserId());
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
                myBasketMvpPresenter.clearBasket();
            }
        }

        return true;
    }

    @Override
    public void setMyBasket(ViewCartResponse viewCartResponse) {

        if (viewCartResponse.getResponse().getCartList() == null || viewCartResponse.getResponse().getCartList().size() == 0) {
            rlMain.setVisibility(View.GONE);
            tvEmptyBasket.setVisibility(View.VISIBLE);
            return;
        } else {
            rlMain.setVisibility(View.VISIBLE);
            tvEmptyBasket.setVisibility(View.GONE);
        }

        this.viewCartResponse = viewCartResponse;

        cartLists.clear();
        cartLists.addAll(viewCartResponse.getResponse().getCartList());

        myBasketAdapter.notifyDataSetChanged();

//        if (viewCartResponse.getResponse().getDeliveryCharge().contains(".00")) {
//            tvDeliveryCharges.setText("$" + viewCartResponse.getResponse().getDeliveryCharge().replace(".00", ""));
//        } else {
        tvDeliveryCharges.setText("$" + viewCartResponse.getResponse().getDeliveryCharge().replace(".00", ""));
//        }
//        tvDiscountAmount.setText(viewCartResponse.getResponse().());

        String deliveryTypes[] = getResources().getStringArray(R.array.delivery_type);

        if (viewCartResponse.getResponse().getDeliveryType().equalsIgnoreCase(deliveryTypes[0])) {

            llDeliveryCharges.setVisibility(View.GONE);
            llDeliveryType.setVisibility(View.GONE);

        } else if (viewCartResponse.getResponse().getDeliveryType().equalsIgnoreCase(deliveryTypes[1])) {

            llDeliveryCharges.setVisibility(View.GONE);
            llDeliveryType.setVisibility(View.VISIBLE);

        } else if (viewCartResponse.getResponse().getDeliveryType().equalsIgnoreCase(deliveryTypes[2])) {

            llDeliveryType.setVisibility(View.VISIBLE);
            llDeliveryCharges.setVisibility(View.GONE);

            ctvPickup.setChecked(true);
            ctvDeliver.setChecked(false);

            ctvPickup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!ctvPickup.isChecked()) {
                        Log.d("ctvPickup", "isChecked");
                        llDeliveryCharges.setVisibility(View.GONE);
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
                        updateTotalAmount();
                    }

                    ctvPickup.setChecked(false);
                    ctvDeliver.setChecked(true);
                }
            });
        }

        updateTotalAmount();
    }

    @Override
    public void onBasketClear() {
        rlMain.setVisibility(View.GONE);
        tvEmptyBasket.setVisibility(View.VISIBLE);
    }

    public void updateTotalAmount() {

        if (cartLists == null || cartLists.size() == 0) {
            rlMain.setVisibility(View.GONE);
            tvEmptyBasket.setVisibility(View.VISIBLE);
            return;
        } else {
            rlMain.setVisibility(View.VISIBLE);
            tvEmptyBasket.setVisibility(View.GONE);
        }

        float totalAmount = 0;

        for (int i = 0; i < cartLists.size(); i++) {

            CartList cartItem = cartLists.get(i);

            float price = Float.parseFloat(cartItem.getPrice());
            int quantity = Integer.parseInt(cartItem.getQty());

            totalAmount += price * quantity;
        }

        if (llDeliveryCharges.getVisibility() == View.VISIBLE) {
            totalAmount += Float.parseFloat(viewCartResponse.getResponse().getDeliveryCharge());
        }

        tvTotalAmount.setText("$" + totalAmount);
    }
//
//    @Override
//    public void askForClearBasket() {
//
////        DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////
////                dialog.dismiss();
//////                                        customerMainMvpPresenter.setUserAsLoggedOut();
//////
//////                Intent intent = new Intent(CustomerMainActivity.this, WelcomeActivity.class);
//////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//////                startActivity(intent);
////                clearBasket();
////            }
////        };
////
////        DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.dismiss();
////            }
////        };
////
////        DialogUtils.showAlert(this, R.mipmap.ic_logout,
////                R.string.alert_dialog_title_logout, R.string.alert_dialog_text_logout,
////                getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
////                getResources().getString(R.string.alert_dialog_bt_no), negativeButton);
//    }

    @Override
    public void itemRemovedFromBasket(int position) {
        cartLists.remove(position);
        myBasketAdapter.notifyDataSetChanged();
        updateTotalAmount();
    }

    @Override
    public void updateMyBasket(int position, String quantity, String totalQuantity, String totalAmount) {

        cartLists.get(position).setQty(quantity);

        myBasketAdapter.notifyDataSetChanged();

//        updateTotalAmount();

        if (Integer.parseInt(totalQuantity) > 0) {

            if (llDeliveryCharges.getVisibility() == View.VISIBLE) {
                totalAmount = Float.parseFloat(totalAmount) + Float.parseFloat(viewCartResponse.getResponse().getDeliveryCharge()) + "";
            }

            tvTotalAmount.setText("$" + totalAmount);

//            rlBasketDetails.setVisibility(View.VISIBLE);
        }
//        else {
//            rlBasketDetails.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void onDestroy() {
        myBasketMvpPresenter.onDetach();
        super.onDestroy();
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

            DialogUtils.showAlert(MyBasketActivity.this,
                    R.string.alert_dialog_title_payment_method, R.string.alert_dialog_text_payment_method,
                    getResources().getString(R.string.alert_dialog_bt_online), online,
                    getResources().getString(R.string.alert_dialog_bt_cod), cod);
        }
    }

    public void doNext(int method) {

        String deliveryTypes[] = getResources().getStringArray(R.array.delivery_type);
        String deliveryType = viewCartResponse.getResponse().getDeliveryType();

        if (method == 1) {

            if (deliveryType.equals(deliveryTypes[0])) {

                Log.d("Pick only","Online");

//                Intent intent = new Intent(MyBasketActivity.this, DeliveryAddressActivity.class);
//                startActivity(intent);

            } else if (deliveryType.equals(deliveryTypes[1])) {

                Log.d("Delivery","Online");

                Intent intent = new Intent(MyBasketActivity.this, DeliveryAddressActivity.class);
                startActivity(intent);
            }

        } else {

            if (deliveryType.equals(deliveryTypes[0])) {

                Log.d("Pick only","COD");

//                Intent intent = new Intent(MyBasketActivity.this, DeliveryAddressActivity.class);
//                startActivity(intent);

            } else if (deliveryType.equals(deliveryTypes[1])) {

                Log.d("Delivery","COD");

                Intent intent = new Intent(MyBasketActivity.this, DeliveryAddressActivity.class);
                startActivity(intent);
            }
        }
    }
}
