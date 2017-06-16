package com.os.foodie.ui.payment.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.ui.custom.RippleAppCompatButton;

public class AddPaymentCardActivity extends AppCompatActivity {
    private LinearLayout rlMain;
    private EditText firstNameEt;
    private EditText lastNameEt;
    private EditText cardNumberEt;
    private TextView monthEt;
    private TextView yearEt;
    private EditText cvvEt;
    private CheckedTextView termAndConditionCheckbox;
    private RippleAppCompatButton addBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_card);
        initView();
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
        rlMain = (LinearLayout) findViewById(R.id.rl_main);
        firstNameEt = (EditText) findViewById(R.id.activity_add_payment_card_et_first_name);
        lastNameEt = (EditText) findViewById(R.id.activity_add_payment_card_et_last_name);
        cardNumberEt = (EditText) findViewById(R.id.activity_add_payment_card_et_card_number);
        monthEt = (TextView) findViewById(R.id.activity_add_payment_card_et_month);
        yearEt = (TextView) findViewById(R.id.activity_add_payment_card_et_year);
        cvvEt = (EditText) findViewById(R.id.activity_add_payment_card_et_cvv);
        termAndConditionCheckbox = (CheckedTextView) findViewById(R.id.activity_add_payment_card_ctv_terms);
        addBt = (RippleAppCompatButton) findViewById(R.id.activity_add_payment_card_bt_add);
    }
}
