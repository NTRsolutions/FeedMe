package com.os.foodie.ui.asksignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.os.foodie.R;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.signup.customer.CustomerSignUpActivity;
import com.os.foodie.ui.signup.restaurant.RestaurantSignUpActivity;

public class AskSignUpActivity extends BaseActivity implements View.OnClickListener {

    private Button btAsCustomer, btAsRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_sign_up);

        getSupportActionBar().setTitle(getResources().getString(R.string.choose_sign_up_title));

        btAsCustomer = (Button) findViewById(R.id.activity_ask_sign_up_bt_sign_up_customer);
        btAsRestaurant = (Button) findViewById(R.id.activity_ask_sign_up_bt_sign_up_restaurant);

        btAsCustomer.setOnClickListener(this);
        btAsRestaurant.setOnClickListener(this);
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

    @Override
    protected void setUp() {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btAsCustomer.getId()) {

            Intent intent = new Intent(AskSignUpActivity.this, CustomerSignUpActivity.class);
            startActivity(intent);

        } else if (v.getId() == btAsRestaurant.getId()) {

            Intent intent = new Intent(AskSignUpActivity.this, RestaurantSignUpActivity.class);
            startActivity(intent);
        }
    }
}
