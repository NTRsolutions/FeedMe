package com.os.foodie.ui.welcome;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.login.LoginActivity;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfileFragment;
import com.os.foodie.ui.signup.customer.CustomerSignUpActivity;
import com.os.foodie.ui.slide.SlidePagerAdapter;
import com.os.foodie.utils.AppConstants;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView, View.OnClickListener {

    private RippleAppCompatButton btLogIn, btSignUp;
//    private TextView tvSkip;

    private ViewPager viewPager;
    private LinearLayout llDots;
    private SlidePagerAdapter slidePagerAdapter;

//    private WelcomeMvpPresenter<WelcomeMvpView> welcomeMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        initPresenter();

        slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());

        llDots = (LinearLayout) findViewById(R.id.activity_welcome_ll_dots);

        viewPager = (ViewPager) findViewById(R.id.activity_welcome_viewpager);

        btLogIn = (RippleAppCompatButton) findViewById(R.id.activity_welcome_bt_log_in);
        btSignUp = (RippleAppCompatButton) findViewById(R.id.activity_welcome_bt_sign_up);

//        tvSkip = (TextView) findViewById(R.id.activity_welcome_tv_skip);

        btLogIn.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
//        tvSkip.setOnClickListener(this);

//        welcomeMvpPresenter.onAttach(this);

        setUp();
    }

    public void bottomDots(int position) {

        llDots.removeAllViews();
        for (int i = 0; i < AppConstants.TOTAL_SLIDE; i++) {

            TextView tvDot = new TextView(this);
            tvDot.setText(Html.fromHtml("&#8226;"));
            tvDot.setTextSize(35);

            if (position == i) {
                tvDot.setTextColor(getResources().getColor(R.color.orange));
            } else {
                tvDot.setTextColor(getResources().getColor(R.color.light_grey));
            }
            llDots.addView(tvDot);
        }
    }

//    public void initPresenter() {
//
//        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);
//        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper);
//
//        welcomeMvpPresenter = new WelcomePresenter(appDataManager, new CompositeDisposable());
//    }

    @Override
    protected void setUp() {

        viewPager.setAdapter(slidePagerAdapter);

        bottomDots(viewPager.getCurrentItem());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomDots(viewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void openLogInActivity() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSignUpActivity() {
        Intent intent = new Intent(WelcomeActivity.this, CustomerSignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btLogIn.getId()) {
            openLogInActivity();
        } else if (v.getId() == btSignUp.getId()) {
            openSignUpActivity();
        }
//        else if (v.getId() == tvSkip.getId()) {
//            Intent intent = new Intent(WelcomeActivity.this, LocationInfoActivity.class);
//            startActivity(intent);
//        }
    }

    @Override
    protected void onDestroy() {
//        welcomeMvpPresenter.onDetach();
        super.onDestroy();
    }
}