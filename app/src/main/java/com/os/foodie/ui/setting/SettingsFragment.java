package com.os.foodie.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.setting.changepassword.ChangePasswordActivity;
import com.os.foodie.ui.setting.staticpages.StaticPageActivity;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SettingsFragment extends BaseFragment implements SettingsMvpView, View.OnClickListener {

    public static final String TAG = "SettingsFragment";

    private Switch switchNotification;

    private LinearLayout llNotification, llChangePassword, llContactUs;
    private LinearLayout llTermsOfUse, llPrivacyPolicy, llFaq, llRateUs;

    private SettingsMvpPresenter<SettingsMvpView> settingsMvpPresenter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        setHasOptionsMenu(true);

        initPresenter();
        settingsMvpPresenter.onAttach(this);

        switchNotification = (Switch) view.findViewById(R.id.fragment_settings_switch_notification);

        llNotification = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_notification);
        llChangePassword = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_change_password);
        llContactUs = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_contact_us);
        llTermsOfUse = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_terms_of_use);
        llPrivacyPolicy = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_privacy_policy);
        llFaq = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_faq);
        llRateUs = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_rate_us);

        llNotification.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llTermsOfUse.setOnClickListener(this);
        llPrivacyPolicy.setOnClickListener(this);
        llFaq.setOnClickListener(this);
        llRateUs.setOnClickListener(this);

        onLoad();

        return view;
    }

    private void onLoad() {

        if (AppController.get(getActivity()).getAppDataManager().getNotificationStatus().equals("1")) {
            switchNotification.setChecked(true);
        } else {
            switchNotification.setChecked(false);
        }
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        settingsMvpPresenter = new SettingsPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof CustomerMainActivity) {
            ((CustomerMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.title_fragment_settings));
        } else {
            ((RestaurantMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.title_fragment_settings));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_blank, menu);
    }

    @Override
    public void onClick(View v) {

        if (llNotification.getId() == v.getId()) {
            settingsMvpPresenter.SetNotificationStaus();

        } else if (llChangePassword.getId() == v.getId()) {

            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);

        } else if (llContactUs.getId() == v.getId()) {

            Intent intent1 = new Intent(Intent.ACTION_SEND);

            intent1.setType("plain/text");

            intent1.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.client_email)});
            intent1.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_tv_contact_us_tag_text));
            intent1.putExtra(Intent.EXTRA_TEXT, "");

            startActivity(Intent.createChooser(intent1, "Send Email"));

        } else if (llTermsOfUse.getId() == v.getId()) {

            Intent intent = new Intent(getActivity(), StaticPageActivity.class);

            intent.putExtra(AppConstants.SLUG, AppConstants.TERMS_AND_CONDITION_SLUG);
            intent.putExtra(AppConstants.PAGE_NAME, AppConstants.TERMS_AND_CONDITION_PAGE_NAME);

            startActivity(intent);

        } else if (llPrivacyPolicy.getId() == v.getId()) {

            Intent intent = new Intent(getActivity(), StaticPageActivity.class);

            intent.putExtra(AppConstants.SLUG, AppConstants.PRIVACY_POLICY_SLUG);
            intent.putExtra(AppConstants.PAGE_NAME, AppConstants.PRIVACY_POLICY_PAGE_NAME);

            startActivity(intent);

        } else if (llFaq.getId() == v.getId()) {

            Intent intent = new Intent(getActivity(), StaticPageActivity.class);

            intent.putExtra(AppConstants.SLUG, AppConstants.FAQ_SLUG);
            intent.putExtra(AppConstants.PAGE_NAME, AppConstants.FAQ_PAGE_NAME);

            startActivity(intent);

        } else if (llRateUs.getId() == v.getId()) {

            try {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.play_store_url)));
                startActivity(viewIntent);
            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...", Toast.LENGTH_LONG).show();
                settingsMvpPresenter.onError(R.string.some_error);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void onDestroyView() {
        settingsMvpPresenter.dispose();
        super.onDestroyView();
    }

    @Override
    public void getNotificationStatus(String status) {

        AppController.get(getActivity()).getAppDataManager().setNotificationStatus(status);

        if (status.equals("0")) {
            switchNotification.setChecked(false);
        } else {
            switchNotification.setChecked(true);
        }
    }
}