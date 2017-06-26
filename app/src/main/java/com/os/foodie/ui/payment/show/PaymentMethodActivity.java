package com.os.foodie.ui.payment.show;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.payment.getall.Card;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.PaymentMethodAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.payment.add.AddPaymentCardActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class PaymentMethodActivity extends BaseActivity implements PaymentMethodMvpView, View.OnClickListener {

    private FloatingActionButton btAddCard;
    private RecyclerView recyclerView;
    private TextView tvAlert;

    private static final int ADD_CARD_REQUEST_CODE = 1;
    private static final int ADD_CARD_RESPONSE_CODE = 2;

    private ArrayList<Card> cardArrayList;
    private PaymentMethodAdapter paymentMethodAdapter;

    private PaymentMethodMvpPresenter<PaymentMethodMvpView> paymentMethodMvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initPresenter();
//        paymentMethodMvpPresenter = new PaymentMethodPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        paymentMethodMvpPresenter.onAttach(this);

        initView();

        cardArrayList = new ArrayList<Card>();

        paymentMethodAdapter = new PaymentMethodAdapter(this, cardArrayList, paymentMethodMvpPresenter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(paymentMethodAdapter);

        paymentMethodMvpPresenter.getAllPaymentCard();
    }

    public void initPresenter(){

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        paymentMethodMvpPresenter = new PaymentMethodPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    private void initView() {

        btAddCard = (FloatingActionButton) findViewById(R.id.activity_payment_method_fab_add_card);
        recyclerView = (RecyclerView) findViewById(R.id.activity_payment_method_recyclerview);
        tvAlert = (TextView) findViewById(R.id.activity_payment_method_tv_alert);

        btAddCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.activity_payment_method_fab_add_card:
                Intent i = new Intent(PaymentMethodActivity.this, AddPaymentCardActivity.class);
                startActivityForResult(i, ADD_CARD_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void notifyDataSetChanged(GetAllPaymentCardResponse getAllPaymentCardResponse) {

        Log.d("Cards", ">>" + getAllPaymentCardResponse.getResponse().getCards().size());

        cardArrayList.clear();

        if (getAllPaymentCardResponse.getResponse().getCards() == null || getAllPaymentCardResponse.getResponse().getCards().isEmpty()) {

            tvAlert.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {

            tvAlert.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            cardArrayList.addAll(getAllPaymentCardResponse.getResponse().getCards());
        }

        paymentMethodAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPaymentCardDeleted(int position) {

        cardArrayList.remove(position);
        paymentMethodAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == ADD_CARD_RESPONSE_CODE) {

            cardArrayList.add((Card) data.getParcelableExtra(AppConstants.CARD));
            paymentMethodAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        paymentMethodMvpPresenter.dispose();
//        paymentMethodMvpPresenter.onDetach();
        super.onDestroy();
    }
}