package com.os.foodie.ui.welcome;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.asksignup.AskSignUpActivity;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.locationinfo.LocationInfoActivity;
import com.os.foodie.ui.login.LoginActivity;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfileFragment;
import com.os.foodie.ui.signup.customer.CustomerSignUpActivity;
import com.os.foodie.ui.slide.SlidePagerAdapter;
import com.os.foodie.ui.splash.SplashActivity;
import com.os.foodie.utils.AppConstants;

import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView, View.OnClickListener {

    private RippleAppCompatButton btLogIn, btSignUp;
    private TextView tvSkip, tvLanguage;

    private ViewPager viewPager;
    private LinearLayout llDots;
    private SlidePagerAdapter slidePagerAdapter;


    private WelcomeMvpPresenter<WelcomeMvpView> welcomeMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initPresenter();
        welcomeMvpPresenter.onAttach(this);

        slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());

        llDots = (LinearLayout) findViewById(R.id.activity_welcome_ll_dots);

        viewPager = (ViewPager) findViewById(R.id.activity_welcome_viewpager);

        btLogIn = (RippleAppCompatButton) findViewById(R.id.activity_welcome_bt_log_in);
        btSignUp = (RippleAppCompatButton) findViewById(R.id.activity_welcome_bt_sign_up);

        tvLanguage = (TextView) findViewById(R.id.activity_welcome_tv_language);
        tvSkip = (TextView) findViewById(R.id.activity_welcome_tv_skip);

        btLogIn.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
        tvLanguage.setOnClickListener(this);
        tvSkip.setOnClickListener(this);

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

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);

        welcomeMvpPresenter = new WelcomePresenter(appDataManager, compositeDisposable);
    }

    @Override
    protected void setUp() {

        if (welcomeMvpPresenter.getLanguage().equals(AppConstants.LANG_EN)) {
            tvLanguage.setText(getString(R.string.language_en));
        } else {
            tvLanguage.setText(getString(R.string.language_ar));
        }

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

        requestAllPermissions();
    }

    @Override
    public void openLogInActivity() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSignUpActivity() {
//        Intent intent = new Intent(WelcomeActivity.this, CustomerSignUpActivity.class);
        Intent intent = new Intent(WelcomeActivity.this, AskSignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btLogIn.getId()) {
            openLogInActivity();
        } else if (v.getId() == btSignUp.getId()) {
            openSignUpActivity();
        } else if (v.getId() == tvSkip.getId()) {
            Intent intent = new Intent(WelcomeActivity.this, LocationInfoActivity.class);
            startActivity(intent);
        } else if (v.getId() == tvLanguage.getId()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);

            builder.setTitle(getString(R.string.alert_dialog_title_change_language));

            String[] languages = {getString(R.string.language_en), getString(R.string.language_ar)};

            builder.setItems(languages, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    switch (which) {

                        case 0:
                            changeLanguage(AppConstants.LANG_EN);
                            tvLanguage.setText(getResources().getString(R.string.language_en));
//                            changeLanguage(AppConstants.LANG_ENG, getString(R.string.setting_dialog_change_language_en));
                            break;
                        case 1:
                            changeLanguage(AppConstants.LANG_AR);
                            tvLanguage.setText(getResources().getString(R.string.language_ar));
//                            changeLanguage(AppConstants.LANG_AR, getString(R.string.setting_dialog_change_language_ar));
                            break;
                    }
                }
            });

            builder.show();
        }
    }

    @Override
    protected void onDestroy() {
        welcomeMvpPresenter.dispose();
        super.onDestroy();
    }

    public void requestAllPermissions() {

        if (!hasPermission(Manifest.permission.READ_PHONE_STATE)
                || !hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                || !hasPermission(Manifest.permission.CAMERA)
                || !hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            String permissions[] = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

            requestPermissions(permissions, 10);
        }
    }

    public void changeLanguage(String languageCode) {

        welcomeMvpPresenter.changeLanguage(languageCode);

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d("onConfigurationChanged", "Called");

//        Locale locale = new Locale(languageCode);
//        Locale.setDefault(locale);
//
//        Configuration config = new Configuration();
//        config.locale = locale;
//
//        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppController.get(this).trackScreenView("FeedMe");
    }
}