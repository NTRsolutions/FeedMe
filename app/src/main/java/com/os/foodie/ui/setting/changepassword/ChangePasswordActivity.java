package com.os.foodie.ui.setting.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.info.RestaurantInfoActivity;
import com.os.foodie.utils.AppConstants;

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordMvpView, View.OnClickListener {

    private EditText etCurrentPassword, etNewPassword, etConfirmNewPassword;
    private Button btChangePassword;

    private ChangePasswordMvpPresenter<ChangePasswordMvpView> changePasswordMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this,R.mipmap.ic_home_up_orange));

        changePasswordMvpPresenter = new ChangePasswordPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        changePasswordMvpPresenter.onAttach(ChangePasswordActivity.this);

        etCurrentPassword = (EditText) findViewById(R.id.activity_change_password_et_current_password);
        etNewPassword = (EditText) findViewById(R.id.activity_change_password_et_new_password);
        etConfirmNewPassword = (EditText) findViewById(R.id.activity_change_password_et_confirm_new_password);

        btChangePassword = (Button) findViewById(R.id.activity_change_password_bt_change_password);
        btChangePassword.setOnClickListener(this);
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
    protected void onDestroy() {

        changePasswordMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (btChangePassword.getId() == v.getId()) {
            changePasswordMvpPresenter.changePassword(etCurrentPassword.getText().toString(), etNewPassword.getText().toString(), etConfirmNewPassword.getText().toString());
        }
    }

    @Override
    public void onPasswordChanged() {

        etCurrentPassword.setText("");
        etNewPassword.setText("");
        etConfirmNewPassword.setText("");

//        finish();
    }
}
