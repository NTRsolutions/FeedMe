package com.os.foodie.ui.otp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.AppConstants;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class OtpActivity extends BaseActivity implements OtpMvpView, View.OnClickListener {

    private TextView tvWaitSeconds;
    private EditText etOtp;
    private Button btSubmit, btResend;

    private Disposable timer;

    private final int WAIT = 60;

    private String userId;

    //    Need Change to Restaurant
    private CustomerSignUpResponse restaurantSignUpResponse;
    private CustomerSignUpResponse customerSignUpResponse;

    private OtpMvpPresenter<OtpMvpView> otpMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initPresenter();
        otpMvpPresenter.onAttach(OtpActivity.this);

        tvWaitSeconds = (TextView) findViewById(R.id.activity_otp_tv_wait_seconds);
        etOtp = (EditText) findViewById(R.id.activity_otp_et_otp);
        btSubmit = (Button) findViewById(R.id.activity_otp_bt_submit);
        btResend = (Button) findViewById(R.id.activity_otp_bt_resend);

        btSubmit.setOnClickListener(this);
        btResend.setOnClickListener(this);

//                                TODO OTP
        if (getIntent().hasExtra(AppConstants.OTP)) {
//            etOtp.setText(getIntent().getStringExtra(AppConstants.OTP));
            userId = getIntent().getStringExtra(AppConstants.USER_ID);
        }

        resendWaitTime();
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

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        otpMvpPresenter = new OtpPresenter(appDataManager, compositeDisposable);

//        otpMvpPresenter = new OtpPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == btSubmit.getId()) {
            otpMvpPresenter.verify(userId, etOtp.getText().toString());
        } else if (v.getId() == btResend.getId()) {
            otpMvpPresenter.resendOtp(userId);
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onVerify(String userType) {

        if (userType.equals(AppConstants.CUSTOMER)) {

            Log.d("userType", ">>Customer");

            Intent intent = new Intent(OtpActivity.this, LocationInfoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {

            Log.d("userType", ">>Restaurant");

            Intent intent = new Intent(OtpActivity.this, RestaurantMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onOtpResend() {
        resendWaitTime();
    }

    @Override
    protected void onDestroy() {
        timer.dispose();
        otpMvpPresenter.dispose();
//        otpMvpPresenter.onDetach();
        super.onDestroy();
    }

    void resendWaitTime() {

        btResend.setEnabled(false);
        btResend.setOnClickListener(null);

        timer = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        Log.d("Long", ">>" + aLong);
                        tvWaitSeconds.setText((WAIT - aLong) + " " + getString(R.string.otp_tv_wait_seconds_text));

                        if (aLong >= WAIT) {

                            timer.dispose();
                            Log.d("10", ">>Done");

                            tvWaitSeconds.setText(getString(R.string.otp_tv_wait_seconds_resend_text));

                            btResend.setEnabled(true);
                            btResend.setOnClickListener(OtpActivity.this);
                        }
                    }
                });
    }
}