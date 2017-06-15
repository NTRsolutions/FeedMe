package com.os.foodie.ui.payment.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.ui.custom.RippleAppCompatButton;

/**
 * Created by monikab on 6/14/2017.
 */

public class AddPaymentCard extends AppCompatActivity {
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
        setContentView(R.layout.layout_add_card);
        initView();
    }

    private void initView() {
        rlMain = (LinearLayout) findViewById(R.id.rl_main);
        firstNameEt = (EditText) findViewById(R.id.first_name_et);
        lastNameEt = (EditText) findViewById(R.id.last_name_et);
        cardNumberEt = (EditText) findViewById(R.id.card_number_et);
        monthEt = (TextView) findViewById(R.id.month_et);
        yearEt = (TextView) findViewById(R.id.year_et);
        cvvEt = (EditText) findViewById(R.id.cvv_et);
        termAndConditionCheckbox = (CheckedTextView) findViewById(R.id.term_and_condition_checkbox);
        addBt = (RippleAppCompatButton) findViewById(R.id.add_bt);
    }
}
