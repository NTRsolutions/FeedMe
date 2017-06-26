package com.os.foodie.ui.payment.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardRequest;
import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardResponse;
import com.os.foodie.data.network.model.payment.getall.Card;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.mybasket.MyBasketActivity;
import com.os.foodie.ui.mybasket.MyBasketPresenter;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class AddPaymentCardActivity extends BaseActivity implements AddPaymentCardMvpView, View.OnClickListener {

    private LinearLayout llMain;
    private EditText etFirstName, etLastName, etCardNumber, etMonth, etYear, etCVV;
    private CheckedTextView cbTerms;
    private RippleAppCompatButton btAdd;

    private static final int ADD_CARD_RESPONSE_CODE = 2;

    private AddPaymentCardMvpPresenter<AddPaymentCardMvpView> addPaymentCardMvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_card);

        initPresenter();
//        addPaymentCardMvpPresenter = new AddPaymentCardPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        addPaymentCardMvpPresenter.onAttach(AddPaymentCardActivity.this);

        initView();
    }

    public void initPresenter(){

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        addPaymentCardMvpPresenter = new AddPaymentCardPresenter(appDataManager, compositeDisposable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_close) {
            finish();
        }

        return true;
    }

    private void initView() {

        llMain = (LinearLayout) findViewById(R.id.rl_main);

        etFirstName = (EditText) findViewById(R.id.activity_add_payment_card_et_first_name);
        etLastName = (EditText) findViewById(R.id.activity_add_payment_card_et_last_name);
        etCardNumber = (EditText) findViewById(R.id.activity_add_payment_card_et_card_number);
        etMonth = (EditText) findViewById(R.id.activity_add_payment_card_et_month);
        etYear = (EditText) findViewById(R.id.activity_add_payment_card_et_year);
        etCVV = (EditText) findViewById(R.id.activity_add_payment_card_et_cvv);

        cbTerms = (CheckedTextView) findViewById(R.id.activity_add_payment_card_ctv_terms);

        btAdd = (RippleAppCompatButton) findViewById(R.id.activity_add_payment_card_bt_add);
        btAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (btAdd.getId() == v.getId()) {

            AddPaymentCardRequest addPaymentCardRequest = createAddPaymentCardRequest();
            addPaymentCardMvpPresenter.addPaymentCard(addPaymentCardRequest);
        }
    }

    public AddPaymentCardRequest createAddPaymentCardRequest() {

        AddPaymentCardRequest addPaymentCardRequest = new AddPaymentCardRequest();

        addPaymentCardRequest.setFirstName(etFirstName.getText().toString());
        addPaymentCardRequest.setLastName(etLastName.getText().toString());
        addPaymentCardRequest.setCreditCardNumber(etCardNumber.getText().toString());
        addPaymentCardRequest.setExpiryMonth(etMonth.getText().toString());
        addPaymentCardRequest.setExpiryYear(etYear.getText().toString());
        addPaymentCardRequest.setCvv2(etCVV.getText().toString());

        return addPaymentCardRequest;
    }

    @Override
    public void onPaymentAdded(AddPaymentCardResponse addPaymentCardResponse) {

        Card card = new Card();

        card.setCardId(addPaymentCardResponse.getResponse().getCardId());
        card.setCreditCardNumber(addPaymentCardResponse.getResponse().getCreditCardNumber());
        card.setCardType(addPaymentCardResponse.getResponse().getCardType());
        card.setCreditCardId(addPaymentCardResponse.getResponse().getCreditCardId());

        Intent intent = new Intent();
        intent.putExtra(AppConstants.CARD, card);

        setResult(ADD_CARD_RESPONSE_CODE, intent);
        finish();
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onDestroy() {
        addPaymentCardMvpPresenter.dispose();
//        addPaymentCardMvpPresenter.onDetach();
        super.onDestroy();
    }
}