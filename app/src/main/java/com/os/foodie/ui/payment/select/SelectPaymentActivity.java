package com.os.foodie.ui.payment.select;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.payment.getall.Card;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.SelectPaymentAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RecyclerTouchListener;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.mybasket.MyBasketActivity;
import com.os.foodie.ui.payment.add.AddPaymentCardActivity;
import com.os.foodie.ui.payment.show.PaymentMethodActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class SelectPaymentActivity extends BaseActivity implements SelectPaymentMvpView, View.OnClickListener {

    private FloatingActionButton fabAddCard;
    private RecyclerView recyclerView;
    private TextView tvAlert;
    private Button btPay;

    private int selectedPosition = -1;
//    private RadioButton radioButton;

    private static final int ADD_CARD_REQUEST_CODE = 1;
    private static final int ADD_CARD_RESPONSE_CODE = 2;

    private ArrayList<Card> cardArrayList;
    private SelectPaymentAdapter selectPaymentAdapter;

    private SelectPaymentMvpPresenter<SelectPaymentMvpView> selectPaymentMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initPresenter();
//        selectPaymentMvpPresenter = new SelectPaymentPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        selectPaymentMvpPresenter.onAttach(this);

        initView();

        cardArrayList = new ArrayList<Card>();

//        for (int i = 0; i < 20; i++) {
//
//            Card card = new Card();
//
//            card.setCardId(i + "");
//            card.setCreditCardNumber("49828374" + i);
//            card.setCreditCardId("sdafsdfasd");
//            card.setCardType("Visa");
//
//            cardArrayList.add(card);
//        }

        selectPaymentAdapter = new SelectPaymentAdapter(this, cardArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(selectPaymentAdapter);

        selectPaymentMvpPresenter.getAllPaymentCard();
    }

    public void initPresenter(){

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        selectPaymentMvpPresenter = new SelectPaymentPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    private void initView() {

        fabAddCard = (FloatingActionButton) findViewById(R.id.activity_select_payment_fab_add_card);
        recyclerView = (RecyclerView) findViewById(R.id.activity_select_payment_recyclerview);
        tvAlert = (TextView) findViewById(R.id.activity_select_payment_tv_alert);
        btPay = (Button) findViewById(R.id.activity_select_payment_bt_search);

//        btAddCard.setOnClickListener(this);
        setOnClick();
    }

    public void setOnClick() {

        fabAddCard.setOnClickListener(this);
        btPay.setOnClickListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                if (/*radioButton != null && */selectedPosition >= 0) {
//                    radioButton.setChecked(false);
                    cardArrayList.get(selectedPosition).setChecked(false);
                    selectedPosition = -1;
                }

//                radioButton = (RadioButton) view.findViewById(R.id.recyclerview_select_payment_rb_select);
//                radioButton.setChecked(true);
                selectedPosition = position;

                cardArrayList.get(position).setChecked(true);

                selectPaymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (btPay.getId() == v.getId()) {

        } else if (fabAddCard.getId() == v.getId()) {

            Intent i = new Intent(SelectPaymentActivity.this, AddPaymentCardActivity.class);
            startActivityForResult(i, ADD_CARD_REQUEST_CODE);
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void notifyDataSetChanged(GetAllPaymentCardResponse getAllPaymentCardResponse) {

        Log.d("Cards", ">>" + getAllPaymentCardResponse.getResponse().getCards().size());

        cardArrayList.clear();

        if (getAllPaymentCardResponse.getResponse().getCards() == null || getAllPaymentCardResponse.getResponse().getCards().isEmpty()) {

            tvAlert.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            btPay.setVisibility(View.GONE);

        } else {

            tvAlert.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            btPay.setVisibility(View.VISIBLE);

            cardArrayList.addAll(getAllPaymentCardResponse.getResponse().getCards());
        }

        selectPaymentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == ADD_CARD_RESPONSE_CODE) {

            cardArrayList.add((Card) data.getParcelableExtra(AppConstants.CARD));
            selectPaymentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        selectPaymentMvpPresenter.dispose();
//        selectPaymentMvpPresenter.onDetach();
        super.onDestroy();
    }
}