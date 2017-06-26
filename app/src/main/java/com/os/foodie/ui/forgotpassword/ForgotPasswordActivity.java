package com.os.foodie.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordMvpView, View.OnClickListener {

    private EditText etEmail;
    private Button btSubmit;

    private static final int FORGOT_PASSWORD_RESPONSE_CODE = 21;

    private ForgotPasswordMvpPresenter<ForgotPasswordMvpView> forgotPasswordMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initPresenter();
        forgotPasswordMvpPresenter.onAttach(ForgotPasswordActivity.this);

        etEmail = (EditText) findViewById(R.id.activity_forgot_password_et_email);
        btSubmit = (Button) findViewById(R.id.activity_forgot_password_bt_submit);

        btSubmit.setOnClickListener(this);

    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        forgotPasswordMvpPresenter = new ForgotPasswordPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == btSubmit.getId()) {
            forgotPasswordMvpPresenter.resetPassword(etEmail.getText().toString());
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onDestroy() {
        forgotPasswordMvpPresenter.dispose();
//        forgotPasswordMvpPresenter.onDetach();
        super.onDestroy();
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
    public void onPasswordReset(String message) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.PASSWORD_RESET_MESSAGE, message);
        setResult(FORGOT_PASSWORD_RESPONSE_CODE, intent);
        finish();
    }
}