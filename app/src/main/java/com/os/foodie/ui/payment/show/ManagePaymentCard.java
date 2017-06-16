package com.os.foodie.ui.payment.show;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.ui.adapter.recyclerview.CardAdapter;
import com.os.foodie.ui.payment.add.AddPaymentCardActivity;

public class ManagePaymentCard extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton btAddCard;
    private RecyclerView recyclerView;
    private TextView tvAlert;
    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_payment_card);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        initView();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(this, null, "manage");
        recyclerView.setAdapter(cardAdapter);
    }

    private void initView() {

        btAddCard = (FloatingActionButton) findViewById(R.id.activity_manage_payment_card_bt_add_card);
        recyclerView = (RecyclerView) findViewById(R.id.activity_manage_payment_card_recyclerview);
        tvAlert = (TextView) findViewById(R.id.activity_manage_payment_card_tv_alert);

        btAddCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_manage_payment_card_bt_add_card:
                Intent i = new Intent(ManagePaymentCard.this, AddPaymentCardActivity.class);
                startActivity(i);
                break;
        }
    }
}
